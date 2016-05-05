package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.SwingWorker;

public class Simulation extends SwingWorker<Void, Void>{//by Jacek Pilka
	
	/*****************************************************
	 *                                                   *
	 * When I Wrote It, Only God and I Knew the Meaning; *
	 *                  Now God Alone Knows              *
	 *                                                   *
	 *****************************************************/
	
	public float energy=0;//J
	public float maxEnergy=0;//J
	float energyMeV =0;//MeV
	float maxEnergyMeV=0;//MeV
	double distance=0;
	float mol=0;
	float uranMolMass=238; //g/mol
	float plutonMolMass=244; //g/mol
	int numberOfAtoms=0;
	int numberOfNeutrons=0;
	int time=0;//us
	boolean first=true;//Is it first moment?
	double a=0;
	double r=0;
	String shape = "Ball";
	boolean reflectMaterial = false;
	int numberOfCollisions=0;
	int numberOfFission=0;
	
	double atomsFactor=1;//atoms in real=k*atoms in simulation
	
	ArrayList<Particle> atoms = new ArrayList<Particle>();
	ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	public Simulation(Options options){
		float molMass=0;
		float elementMass=0;
		float density=0;
		a=options.a;
		r=options.r;
		System.out.println("Wartosc a: "+a);
		System.out.println("Wartosc r: "+r);
		
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
		mol=(float)(options.m/1000)/molMass;
		System.out.println("Liczba moli: "+mol);
		numberOfAtoms=(int) ((mol*6.02*Math.pow(10.0,8.0)));//must be pow(10.0,23)
		double trueNumberOfAtoms=mol*6.02*Math.pow(10.0,23.0);//How many atoms are in real?
		atomsFactor=trueNumberOfAtoms/numberOfAtoms;
		System.out.println("AtomsFactor = "+atomsFactor);
		System.out.println("ilosc� atomow: "+numberOfAtoms);
		elementMass=(float) (molMass/(6.02*Math.pow(10.0,8.0)));
		
		float elementV=(float) ((elementMass/density));
		System.out.println("Objêtosæ atomu: "+elementV);
		
		//Adding atomic net
		if (shape.equals("Cube")){
			System.out.println("Simulate cube");
			distance=a/Math.pow(numberOfAtoms, 1.0/3.0);
			//distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0)); <- It should work, but it doesn't
			makeCube();
		}
		else if (shape.equals("Ball")){
			System.out.println("Simulate ball");
			distance=r/(numberOfAtoms/(2*3.14*3.14));
			//distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0)); <- It should work, but it doesn't
			makeBall();
		}
		System.out.println("Distance between atoms: "+distance);
		for(int i=0; i<numberOfAtoms-1; i++){
			if(atoms.get(i).x==atoms.get(i+1).x){
				System.out.println("B³¹d!");
			}
		}
		Random r = new Random();
		Particle startAtom = atoms.get(r.nextInt(numberOfAtoms));
		neutrons.add(new Particle(startAtom.x,startAtom.y,startAtom.z,0,true));
		numberOfNeutrons++;
		System.out.println("Stworzylem neutron");
	}
	
	//Making net functions
	
	void makeCube(){
		int nx=0,ny=0,nz=0;
		for(float z=(float) (-a/2); z<=a/2; z+=distance){
			nz++;
			nx=0;
			ny=0;
			for(float y=(float) (-a/2); y<=a/2; y+=distance){
				ny++;
				nx=0;
				for(float x=(float) (-a/2); x<=a/2; x+=distance){
					nx++;
					atoms.add(new Particle(x,y,z,0,false));
				}
			}
		}
		System.out.println("Szescian ma wymiary: "+nx+" "+ny+" "+nz);
	}
	
	void makeBall(){//In spherical coordinates
		int nx=0,ny=0,nz=0;
		for(float theta=0; theta<=3.14; theta+=3.14/3.0){
			nz++;
			nx=0;
			ny=0;
			for(float phi=0; phi<=2*3.14; phi+=3.14/3.0){//Co Pi/3
				ny++;
				nx=0;
				for(float r=0; r<this.r; r+=distance){
					nx++;
					atoms.add(new Particle(r,phi,theta,0,false));
				}
			}
		}
		System.out.println("Kula ma wymiary: "+nx+" "+ny+" "+nz);
	}
	
	void moveNeutron(Particle neutron){
		double distanceX=0, distanceY=0, distanceZ=0;
		if (shape.equals("Cube")){
			distanceX=distance; distanceY=distance; distanceZ=distance;
		}
		else if(shape.equals("Ball")){
			distanceX=distance; distanceY=3.14/3.0; distanceZ=3.14/3.0;
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
			System.out.println("Neutron direction ERROR: "+neutron.direction);
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
		System.out.println("E("+time+"us): "+energy+"J, "+energyMeV+"MeV, Maximum: "+maxEnergy+"J");
	}
	
	void fission(Random rand, int i, int j){
		energyMeV+=200*atomsFactor;
		for(int k=0; k<2;k++)//Add 2 neutrons
			neutrons.add(new Particle(atoms.get(j).x, atoms.get(j).y, atoms.get(j).z, rand.nextInt(6)+1, false));
		atoms.remove(j);//remove atom
		neutrons.remove(i);
	}
	
	void collisionAtom(int i, Random rand){
		for (int j=0; j<atoms.size(); j++){
			neutrons.get(i).interact(atoms.get(j), rand.nextInt(100));
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
			energy=0;
			numberOfCollisions=0;
			numberOfFission=0;
			numberOfNeutrons=neutrons.size();
		
			System.out.println("Number of atoms: "+numberOfAtoms);
			System.out.println("Number of neutrons: "+numberOfNeutrons);
		
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
		
			System.out.println("Number of collisions: "+numberOfCollisions);
			System.out.println("Number of fissions: "+numberOfFission);
			
			//Energy
			calculateEnergy();
			if(neutrons.size()<1){
				System.out.println("Brak neutron�w!");
				this.cancel(true);
			}
			time++;	
		}
		return null;
	}
}