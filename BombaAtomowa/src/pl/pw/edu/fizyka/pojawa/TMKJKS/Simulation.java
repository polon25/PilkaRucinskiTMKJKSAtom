package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;

/**
 * 
 * @author Jacek Piłka
 *
 *	Numerical simulation of fission
 *
 */

public class Simulation extends SwingWorker<Void, Void>{
	
	Random rand = new Random();
	
	float energy=0;//J
	public float maxEnergy=0;//J
	float energyMeV =0;//MeV
	float maxEnergyMeV=0;//MeV
	double energykTNT=0;
	double maxEnergykTNT=0;
	double distance=0;
	float mol=0;
	float uranMolMass=238; //g/mol
	float plutonMolMass=244; //g/mol
	double numberOfAtoms=0;
	int numberOfNeutrons=0;
	int time=0;//us
	boolean first=true;//Is it first moment?
	double a=0;
	double r=0;
	String shape = "Ball";
	boolean reflectMaterial = false;
	int numberOfCollisions=0;
	int numberOfFission=0;
	
	Interface Interface;
	SIPrefixes prefixes = new SIPrefixes();
	
	double atomsFactor=1;//atoms in real=k*atoms in simulation
	
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<Double> energies = new ArrayList<Double>();
	ArrayList<Double> numbersOfAtoms = new ArrayList<Double>();
	ArrayList<Double> numbersOfNeutrons = new ArrayList<Double>();
	ArrayList<Double> numbersOfFissions = new ArrayList<Double>();
	
	ArrayList<Particle> atoms = new ArrayList<Particle>();
	ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	public Simulation(Options options, Interface Interface){
		this.Interface=Interface;
		
		float molMass=0;
		float elementMass=0;
		float density=0;
		a=options.a;
		r=options.r;
		
		//Setting element
		if(options.element.equals(resourceBundle.getString("options.element1"))){
			molMass=uranMolMass;
			density=19050;
		}
		else if(options.element.equals(resourceBundle.getString("options.element2"))){
			molMass=plutonMolMass;
			density=19816;
		}
		
		//Setting shape
		if(options.materialShape.equals(resourceBundle.getString("options.shape1"))){
			shape="Ball";
		}
		else if(options.materialShape.equals(resourceBundle.getString("options.shape2"))){
			shape="Cube";
		}
		
		reflectMaterial=options.reflectMaterial;
		
		//Setting number of Atoms
		mol=(float)(options.m*1000)/molMass;
		numberOfAtoms=(mol*6.02*Math.pow(10.0,options.accuracyFactor));//<- Simplification
		double trueNumberOfAtoms=mol*6.02*Math.pow(10.0,23.0);//How many atoms are in real?
		atomsFactor=trueNumberOfAtoms/numberOfAtoms;
		elementMass=(float) (molMass/(6.02*Math.pow(10.0,23.0)));
		
		float elementV=(float) ((elementMass/density)*atomsFactor);
		
		//Adding atomic net
		if (shape.equals("Cube")){
			System.out.println("Simulate cube");
			distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0));
			makeCube();
		}
		else if (shape.equals("Ball")){
			distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0));
			makeBall();
		}
		numberOfAtoms=atoms.size();
		atomsFactor=trueNumberOfAtoms/numberOfAtoms;
		Random r = new Random();
		Particle startAtom = atoms.get(r.nextInt(atoms.size()));
		neutrons.add(new Particle(startAtom.x,startAtom.y,startAtom.z,0,0,0,true));
		numberOfNeutrons++;
		System.out.println("Atoms size: "+atoms.size());
	}
	
	//Making net functions

	void makeCube(){
		for(float z=(float) (-a/2); z<=a/2; z+=distance){
			for(float y=(float) (-a/2); y<=a/2; y+=distance){
				for(float x=(float) (-a/2); x<=a/2; x+=distance){
					atoms.add(new Particle(x,y,z,0,0,0,false));
				}
			}
		}
	}
	
	void makeBall(){//In spherical coordinates
		int nx=0;
		
		for(float r=0; r<this.r; r+=distance){
			for(float phi=0; phi<2*3.14; phi+=(3.14/(3.0*nx))){
				for(float theta=0; theta<3.14; theta+=(3.14/(3.0*nx))){
					atoms.add(new Particle(r,phi,theta,0,0,0,false));
				}
			}
			nx++;
		}
	}
	
	void moveNeutron(Particle neutron){
		neutron.x+=neutron.dx;
		neutron.y+=neutron.dy;
		neutron.z+=neutron.dz;
		
	}
	
	boolean boundaryProblemCube(Particle neutron){
		if(neutron.x>a/2||neutron.x<-a/2||neutron.y>a/2
				||neutron.y<-a/2||neutron.z>a/2||neutron.z<-a/2){
			if(reflectMaterial){
				if(neutron.x>a/2||neutron.x<-a/2)
					neutron.dx*=-1;
				else if(neutron.y>a/2||neutron.y<-a/2)
					neutron.dy*=-1;
				else
					neutron.dz*=-1;
			}
			else{
				return true;
			}
		}
		return false;
	}
	
	boolean boundaryProblemBall(Particle neutron){
		if(neutron.x>r||neutron.x<0){
			if(reflectMaterial){
				neutron.dx*=-1;
				if (neutron.x<0)
					neutron.y+=3.14;
			}
			else{
				return true;
			}
		}
		else if(neutron.y>=2.0*3.14){
			neutron.y=0;
		}
		else if(neutron.y<0){
			neutron.y=(float) (2*3.14);
		}
		else if(neutron.z>=3.14){
			neutron.z=0;
		}
		else if(neutron.z<0){
			neutron.z=(float) 3.14;
		}
		else{
			return false;
		}
		
		if(rand.nextBoolean())
			neutron.x+=neutron.dx;
		else
			neutron.x-=neutron.dx;
		
		return false;
	}
	
	void calculateEnergy(){
		energy=(float) (energyMeV*1.602*Math.pow(10,-13));
		energykTNT=energy*Math.pow(10.0, -9.0)/4184;
		if(energyMeV>maxEnergyMeV){
			maxEnergyMeV=energyMeV;
			maxEnergy=(float) (maxEnergyMeV*1.602*Math.pow(10,-13));
			maxEnergykTNT=maxEnergy*Math.pow(10.0, -9.0)/4184;
		}
	}
	
	void fission(Random rand, int i, int j){
		energyMeV+=200*atomsFactor;
		int newNeutrons = rand.nextInt(1)+2;
		
		for(int k=0; k<newNeutrons;k++){//Add 2-3 neutrons
			float dx=0;
			float dy=0;
			float dz=0;
			if (shape.equals("Cube")){
				dx=(float)(Math.pow(-1, rand.nextInt(1))*distance*rand.nextFloat());
				dy=(float) (Math.pow(-1, rand.nextInt(1))*Math.sqrt(Math.pow(distance,2)-Math.pow(dx,2))*rand.nextFloat());
				dz=(float) (Math.pow(-1, rand.nextInt(1))*Math.sqrt(Math.pow(distance,2)-Math.pow(dx,2)-Math.pow(dy,2)));
			}
			else if(shape.equals("Ball")){
				dx=(float)(Math.pow(-1, rand.nextInt(1))*distance*rand.nextFloat());
				float dxx=(float) Math.abs(dx*rand.nextFloat());
				float dxy=(float) (Math.sqrt(Math.pow(dx,2)-Math.pow(dxx,2))*rand.nextFloat());
				float dxz=(float) Math.sqrt(Math.pow(dx,2)-Math.pow(dxx,2)-Math.pow(dxy,2));
				dy=(float) (Math.pow(-1, rand.nextInt(1))*Math.acos(dxz/dx));
				dz=(float) (Math.pow(-1, rand.nextInt(1))*Math.atan(dxy/dxx));
			}
			neutrons.add(new Particle(atoms.get(j).x, atoms.get(j).y, atoms.get(j).z, dx, dy, dz, false));
		}
		atoms.remove(j);//remove atom
		neutrons.remove(i);
	}
	
	void collisionAtom(int i, Random rand){
		double distanceX=0, distanceY=0, distanceZ=0;
		if (shape.equals("Cube")){
			distanceX=distance; distanceY=distance; distanceZ=distance;
		}
		else{
			distanceX=distance; distanceY=3.14/(3.0*Math.round((float)(neutrons.get(i).x/distance))); 
			distanceZ=3.14/(3.0*Math.round((float)(neutrons.get(i).x/distance)));
		}
		for (int j=0; j<atoms.size(); j++){
			neutrons.get(i).interact(atoms.get(j), rand.nextInt(100), distanceX, distanceY, distanceZ);
			if(neutrons.get(i).change==1){
				fission(rand, i, j);
				numberOfAtoms--;
				numberOfCollisions++;
				numberOfFission++;
				numberOfNeutrons--;
				i--;
				break;
			}
			else if(neutrons.get(i).change==2){
				neutrons.remove(i);
				numberOfCollisions++;
				numberOfNeutrons--;
				i--;
				break;
			}
		}
	}
	
	boolean endSimulation(){
		if(neutrons.size()<1){
			System.out.println("Lack of neutrons!");
			this.cancel(true);
			return true;
		}
		else if(atoms.size()<1){
			System.out.println("End of atoms!");
			this.cancel(true);
			return true;
		}
		if(time>1000){
			System.out.println("End of time!");
			this.cancel(true);
			return true;
		}
		return false;
	}
	
	public Void doInBackground(){
		while(!isCancelled()){
			energyMeV=0;
			numberOfCollisions=0;
			numberOfFission=0;
			numberOfNeutrons=neutrons.size();
		
			//For every neutron
		
			for(int i=0; i<numberOfNeutrons; i++){//every neutron
			
				moveNeutron(neutrons.get(i));//Moving neutron
			
				//Boundary Problem
			
				if(shape.equals("Cube")){
					if(boundaryProblemCube(neutrons.get(i))){
						neutrons.remove(i);
						i--;
						numberOfNeutrons--;
						continue;
					}
				}
				else if(shape.equals("Ball")){
					if(boundaryProblemBall(neutrons.get(i))){
						neutrons.remove(i);
						i--;
						numberOfNeutrons--;
						continue;
					}
				}
			
				//Searching for neutrons hitting atom
				collisionAtom(i, rand);
			}
			//Energy
			calculateEnergy();
			
			time++;
			energies.add(energykTNT);
			numbersOfAtoms.add(numberOfAtoms*atomsFactor);
			numbersOfNeutrons.add(numberOfNeutrons*atomsFactor);
			numbersOfFissions.add(numberOfFission*atomsFactor);
			data.add(time+"\t"+energy+"\t"+numberOfAtoms*atomsFactor
					+"\t"+numberOfNeutrons*atomsFactor+"\t"
					+numberOfFission*atomsFactor);
			
			if(endSimulation())
				break;	
		}
		this.cancel(true);
		Interface.energy.validate();
		return null;
	}
}