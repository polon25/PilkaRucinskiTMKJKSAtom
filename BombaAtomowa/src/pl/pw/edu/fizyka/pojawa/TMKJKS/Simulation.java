package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Random;

public class Simulation implements Runnable{
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
	
	ArrayList<Particle> atoms = new ArrayList<Particle>();
	ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	public Simulation(String element, double m, double V, String shape, double A){
		System.out.println("Rozpoczynam tworzenie simulation");
		float molMass=0;
		float elementMass=0;
		float density=0;
		a=A;
		if(element.equals("Uran")){
			molMass=uranMolMass;
			density=19050;
		}
		else if(element.equals("Uran")){
			molMass=plutonMolMass;
			density=19816;
		}
		mol=(float)(m/1000)/molMass;
		System.out.println("Liczba moli: "+mol);
		numberOfAtoms=(int) ((mol*6.02*Math.pow(10.0,9.0)));//must be pow(10.0,23)
		System.out.println("iloœæ atomów: "+numberOfAtoms);
		elementMass=(float) (molMass/(6.02*Math.pow(10.0,9.0)));
		
		float elementV=(float) ((elementMass/density));
		System.out.println("Objêtosæ atomu: "+elementV);
		distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0));
		System.out.println("Odleg³oœæ miêdzy atomami: "+distance);
		
		float x=(float)-a/2; float y=(float)-a/2; float z=(float)-a/2;
		for(int i=0; i<numberOfAtoms; i++){
			atoms.add(new Particle(x,y,z,0,false));
			if(x>=a/2&&y>=a/2){
				x=0;
				y=0;
				z+=distance;
				//System.out.println("Nowy z");
			}
			else if(x>=a/2){
				x=0;
				y+=distance;
				//System.out.println("Nowy y");
			}
			else{
				x+=distance;
				//System.out.println("Nowy x");
			}
		}
		Random r = new Random();
		Particle startAtom = atoms.get(r.nextInt(numberOfAtoms));
		neutrons.add(new Particle(startAtom.x,startAtom.y,startAtom.z,0,true));
		numberOfNeutrons++;
	}

	public void run(){
		Random r = new Random();
		for(int l=0; l<100; l++){
			energyMeV=0;
			energy=0;
			int nN=numberOfNeutrons;
			ArrayList<Float> atomx = new ArrayList<Float>();
			ArrayList<Float> atomy = new ArrayList<Float>();
			ArrayList<Float> atomz = new ArrayList<Float>();
			int nA=0;//number of used atom, which was removed
			System.out.println("Iloœæ atomów: "+numberOfAtoms);
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
			for (int i=0; i<nN; i++){//second for = removing "used" neutrons
				if (neutrons.get(i).change==1||neutrons.get(i).change==2){//when neutron hit atom
					boolean fission=false;
					if(neutrons.get(i).change==1)
						fission=true;
					neutrons.remove(i); //remove neutron
					i--;
					nN--;
					numberOfNeutrons--;
					if(fission){
						first=false;
						energyMeV+=200*Math.pow(10, 14);
						numberOfFission++;
						for(int k=0; k<2;k++){ //add 2 neutrons
							neutrons.add(new Particle(atomx.get(nA), atomy.get(nA), atomz.get(nA), r.nextInt(6)+1, false));
							//System.out.println("Doda³em wspó³rzêdne");
							//adding new neutron with array of used atom's x,y,z
							Particle thisNeutron=neutrons.get(neutrons.size()-1);
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
							numberOfNeutrons++;
						}
						nA++;
					}
				}
			}
			System.out.println("Liczba neutronów: "+numberOfNeutrons);
			for (int i=0; i<neutrons.size(); i++){
				if(neutrons.get(i).x>a/2||neutrons.get(i).x<-a/2||neutrons.get(i).y>a/2||neutrons.get(i).y<-a/2||
						neutrons.get(i).z>a/2||neutrons.get(i).z<-a/2){
					neutrons.remove(i);
					numberOfNeutrons--;
					i--;
				}
			}
			System.out.println("Liczba rozpadów: "+numberOfFission);
			energy=(float) (energyMeV*1.602*Math.pow(10,-13));
			if(energyMeV>maxEnergyMeV){
				maxEnergyMeV=energyMeV;
				maxEnergy=(float) (maxEnergyMeV*1.602*Math.pow(10,-13));
			}
			System.out.println("E("+time+"us): "+energy+"J, "+energyMeV+"MeV");
			if(neutrons.size()<1){
				System.out.println("Brak neutronów!");
				System.out.println("Energia maksymalna: "+maxEnergy+"J");
				break;
			}
			time++;
		}
	}
}
