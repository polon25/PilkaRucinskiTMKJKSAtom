package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.SwingWorker;

public class Simulation extends SwingWorker<Void, Void>{//by Jacek Piłka
	
	float energy=0;//J
	public float maxEnergy=0;//J
	float energyMeV =0;//MeV
	float maxEnergyMeV=0;//MeV
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
	
	double atomsFactor=1;//atoms in real=k*atoms in simulation
	double neutronsFactor=1;
	
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<Float> energies = new ArrayList<Float>();
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
		if(options.element.equals(resourceBundle.getString("options.name1"))){
			molMass=uranMolMass;
			density=19050;
		}
		else if(options.element.equals(resourceBundle.getString("options.name2"))){
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
		numberOfAtoms=(mol*6.02*Math.pow(10.0,5.0));//<- Simplification
		double trueNumberOfAtoms=mol*6.02*Math.pow(10.0,23.0);//How many atoms are in real?
		atomsFactor=trueNumberOfAtoms/numberOfAtoms;
		neutronsFactor=atomsFactor;
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
		atomsFactor=trueNumberOfAtoms/atoms.size();
		Random r = new Random();
		Particle startAtom = atoms.get(r.nextInt(atoms.size()));
		neutrons.add(new Particle(startAtom.x,startAtom.y,startAtom.z,0,true));
		numberOfNeutrons++;
	}
	
	//Making net functions

	void makeCube(){
		for(float z=(float) (-a/2); z<=a/2; z+=distance){
			for(float y=(float) (-a/2); y<=a/2; y+=distance){
				for(float x=(float) (-a/2); x<=a/2; x+=distance){
					atoms.add(new Particle(x,y,z,0,false));
				}
			}
		}
	}
	
	void makeBall(){//In spherical coordinates
		int nx=0;
		
		for(float r=0; r<this.r; r+=distance){
			nx++;
			for(float phi=0; phi<=2*3.14; phi+=(3.14/(3.0*nx))){
				for(float theta=0; theta<=2*3.14; theta+=(3.14/(3.0*nx))){
					atoms.add(new Particle(r,phi,theta,0,false));
				}
			}
		}
	}
	
	void moveNeutron(Particle neutron){
		double distanceX=0, distanceY=0, distanceZ=0;
		if (shape.equals("Cube")){
			distanceX=distance; distanceY=distance; distanceZ=distance;
		}
		else if(shape.equals("Ball")){
			distanceX=distance; distanceY=3.14/(3.0*(neutron.x/distance)); distanceZ=3.14/(3.0*(neutron.x/distance));
		}	
		switch(neutron.direction){//direction of neutron
		case 1:
			neutron.x+=distanceX;
			break;
		case 2:
			neutron.x-=distanceX;
			break;
		case 3:
			neutron.y+=distanceY;
			break;
		case 4:
			neutron.y-=distanceY;
			break;
		case 5:
			neutron.z+=distanceZ;
			break;
		case 6:
			neutron.z-=distanceZ;
			break;
		default:
			System.out.println("Direction ERROR: "+neutron.direction);
			break;
		}
	}
	
	boolean boundaryProblemCube(Particle neutron){
		if(neutron.x>a/2||neutron.x<-a/2||neutron.y>a/2
				||neutron.y<-a/2||neutron.z>a/2||neutron.z<-a/2){
			if(reflectMaterial){
				if(neutron.direction%2==0)
					neutron.direction-=1;
				else
					neutron.direction+=1;
			}
			else{
				return true;
			}
		}
		return false;
	}
	
	boolean boundaryProblemBall(Particle neutron){
		if(neutron.x>r||neutron.x<-r){
			if(reflectMaterial){
				if(neutron.direction%2==0)
					neutron.direction-=1;
				else
					neutron.direction+=1;
				}
			else{
				return true;
			}
		}
		else if(neutron.y>2.0*3.14){
			neutron.y=0;
		}
		else if(neutron.y<0){
			neutron.y=(float) (2*3.14);
		}
		else if(neutron.z>3.14){
			neutron.z=0;
		}
		else if(neutron.z<0){
			neutron.z=(float) 3.14;
		}
		return false;
	}
	
	void calculateEnergy(){
		energy=(float) (energyMeV*1.602*Math.pow(10,-13));
		if(energyMeV>maxEnergyMeV){
			maxEnergyMeV=energyMeV;
			maxEnergy=(float) (maxEnergyMeV*1.602*Math.pow(10,-13));
		}
	}
	
	void fission(Random rand, int i, int j){
		energyMeV+=200*atomsFactor;
		for(int k=0; k<2;k++)//Add 2 neutrons
			neutrons.add(new Particle(atoms.get(j).x, atoms.get(j).y, atoms.get(j).z, rand.nextInt(6)+1, false));
		atoms.remove(j);//remove atom
		neutrons.remove(i);
	}
	
	void collisionAtom(int i, Random rand){
		double distanceX=0, distanceY=0, distanceZ=0;
		if (shape.equals("Cube")){
			distanceX=distance; distanceY=distance; distanceZ=distance;
		}
		else{
			distanceX=distance; distanceY=3.14/(3.0*(neutrons.get(i).x/distance)); distanceZ=3.14/(3.0*(neutrons.get(i).x/distance));
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
	
	public Void doInBackground(){
		while(!isCancelled()){
			Random rand = new Random();
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
						break;
					}
				}
				else if(shape.equals("Ball")){
					if(boundaryProblemBall(neutrons.get(i))){
						neutrons.remove(i);
						break;
					}
				}
			
				//Searching for neutrons hitting atom
			
				collisionAtom(i, rand);
			}
			//Energy
			calculateEnergy();
			if(neutrons.size()<1){
				System.out.println("Lack of neutrons!");
				this.cancel(true);
				break;
			}
			if(atoms.size()<1){
				System.out.println("End of atoms!");
				this.cancel(true);
				break;
			}
			if(numberOfCollisions==0){
				System.out.println("No Collisions!");
				this.cancel(true);
				break;
			}
			time++;
			energies.add(energy);
			numbersOfAtoms.add(numberOfAtoms);
			numbersOfNeutrons.add(numberOfNeutrons*neutronsFactor);
			numbersOfFissions.add(numberOfFission*neutronsFactor);
			data.add(time+"\t"+energy);
		}
		this.cancel(true);
		Interface.energy.validate();
		return null;
	}
}