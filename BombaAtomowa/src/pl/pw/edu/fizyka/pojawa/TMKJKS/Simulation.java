package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class Simulation /**implements Runnable*/{//by Jacek Pilka
	
	/*****************************************************
	 *                                                   *
	 * When I Wrote It, Only God and I Knew the Meaning; *
	 *                  Now God Alone Knows              *
	 *                                                   *
	 *****************************************************/
	
	volatile public float energy=0;//J
	volatile public float maxEnergy=0;//J
	volatile float energyMeV =0;//MeV
	volatile float maxEnergyMeV=0;//MeV
	volatile double distance=0;
	volatile float mol=0;
	volatile float uranMolMass=238; //g/mol
	volatile float plutonMolMass=244; //g/mol
	volatile int numberOfAtoms=0;
	volatile int numberOfNeutrons=0;
	volatile int time=0;//us
	volatile boolean first=true;//Is it first moment?
	volatile double a=0;
	volatile double r=0;
	volatile String shape = "Ball";
	volatile boolean reflectMaterial = false;
	
	volatile ArrayList<Particle> atoms = new ArrayList<Particle>();
	volatile ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	public Simulation(Options options){
		System.out.println("Simulation start");
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
		System.out.println("iloœæ atomów: "+numberOfAtoms);
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
		for(float z=(float) (-a/2); z<a/2; z+=distance){
			nz++;
			nx=0;
			ny=0;
			for(float y=(float) (-a/2); y<a/2; y+=distance){
				ny++;
				nx=0;
				for(float x=(float) (-a/2); x<a/2; x+=distance){
					nx++;
					atoms.add(new Particle(x,y,z,0,false));
				}
			}
		}
		System.out.println("Szsccian ma wymiary: "+nx+" "+ny+" "+nz);
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

	public void run(){
		//synchronized(this){
			//SwingUtilities.invokeLater(new Runnable() {
				//public void run() {
					//synchronized(this){
						Random r = new Random();
						energyMeV=0;
						energy=0;
						int nN=numberOfNeutrons;
						ArrayList<Float> atomx = new ArrayList<Float>();
						ArrayList<Float> atomy = new ArrayList<Float>();
						ArrayList<Float> atomz = new ArrayList<Float>();
						int nA=0;//number of used atom, which was removed
						System.out.println("Number of atoms: "+numberOfAtoms);
						System.out.println("Number of neutrons: "+numberOfNeutrons);
						for(int i=0; i<nN; i++){//every neutron
							for (int j=0; j<numberOfAtoms; j++){//searching for neutrons hitting atom
								neutrons.get(i).interact(atoms.get(j));
								if(neutrons.get(i).change==1){
									atomx.add(atoms.get(j).x);
									atomy.add(atoms.get(j).y);
									atomz.add(atoms.get(j).z);
									atoms.remove(j);//remove atom
									numberOfAtoms--;
									break;
								}
							}
							switch(neutrons.get(i).direction){//direction of neutron
								case 1:
									neutrons.get(i).x+=distance;
									break;
								case 2:
									neutrons.get(i).x-=distance;
									break;
								case 3:
									neutrons.get(i).y+=distance;
									break;
								case 4:
									neutrons.get(i).y-=distance;
									break;
								case 5:
									neutrons.get(i).z+=distance;
									break;
								case 6:
									neutrons.get(i).z-=distance;
									break;
								default:
									break;
							}
						}
						int numberOfFission=0;
						
						//Second for = removing "used" neutrons
						for (int i=0; i<nN; i++){
							if (neutrons.get(i).change==1||neutrons.get(i).change==2){//when neutron hit atom
								boolean fission=false;
							if(neutrons.get(i).change==1)
								fission=true;
							neutrons.remove(i); //remove neutron
							i--;
							nN--;
							numberOfNeutrons--;
							
							if(fission){//add 2 neutrons + energy
									first=false;
									energyMeV+=200*Math.pow(10, 15);
									numberOfFission++;
									for(int k=0; k<2;k++){ //add 2 neutrons
										neutrons.add(new Particle(atomx.get(nA), atomy.get(nA), atomz.get(nA), r.nextInt(6)+1, false));
										//adding new neutron with array of used atom's x,y,z
										Particle thisNeutron=neutrons.get(neutrons.size()-1);
										if(shape.equals("Cube")){
											switch(thisNeutron.direction){//direction of new neutron
												case 1:
													thisNeutron.x+=distance;
													break;
												case 2:
													thisNeutron.x-=distance;
													break;
												case 3:
													thisNeutron.y+=distance;
													break;
												case 4:
													thisNeutron.y-=distance;
													break;
												case 5:
													thisNeutron.z+=distance;
													break;
												case 6:
													thisNeutron.z-=distance;
													break;
												default:
													break;
											}
										}
										else if(shape.equals("Ball")){
											switch(thisNeutron.direction){//direction of new neutron
												case 1:
													thisNeutron.x+=distance;
													break;
												case 2:
													thisNeutron.x-=distance;
													break;
												case 3:
													thisNeutron.y+=3.14/3.0;
													break;
												case 4:
													thisNeutron.y-=3.14/3.0;
													break;
												case 5:
													thisNeutron.z+=3.14/3.0;
													break;
												case 6:
													thisNeutron.z-=3.14/3.0;
													break;
												default:
													break;
											}
										}
										numberOfNeutrons++;
									}
									nA++;
								}
							}
						}
						
						//Boundary problem
						for (int i=0; i<neutrons.size(); i++){
							if(reflectMaterial){
								if(neutrons.get(i).direction%2==0)
									neutrons.get(i).direction-=1;
								else
									neutrons.get(i).direction+=1;
							}
							else{
								if(shape.equals("Cube")){
									if(neutrons.get(i).x>a/2||neutrons.get(i).x<-a/2||neutrons.get(i).y>a/2
											||neutrons.get(i).y<-a/2||neutrons.get(i).z>a/2||neutrons.get(i).z<-a/2){
										neutrons.remove(i);
										numberOfNeutrons--;
										i--;
									}
								}
								else if(shape.equals("Ball")){
									if(neutrons.get(i).x>this.r||neutrons.get(i).x<-this.r){
										neutrons.remove(i);
										numberOfNeutrons--;
										i--;
									}
									else if(neutrons.get(i).y>2.0*3.14){
										neutrons.get(i).y=0;
									}
									else if(neutrons.get(i).y<0){
										neutrons.get(i).y=(float) (2*3.14);
									}
									else if(neutrons.get(i).z>3.14){
										neutrons.get(i).z=0;
									}
									else if(neutrons.get(i).z<0){
										neutrons.get(i).z=(float) 3.14;
									}
								}
							}
						}
						System.out.println("Number of fission: "+numberOfFission);
						
						//Energy
						energy=(float) (energyMeV*1.602*Math.pow(10,-13));
						if(energyMeV>maxEnergyMeV){
							maxEnergyMeV=energyMeV;
							maxEnergy=(float) (maxEnergyMeV*1.602*Math.pow(10,-13));
						}
						System.out.println("E("+time+"us): "+energy+"J, "+energyMeV+"MeV");
						/**if(neutrons.size()<1){
						System.out.println("Brak neutronów!");
						break;
						}**/
						time++;
					//}
				//}
			//});
		//}
	}
}