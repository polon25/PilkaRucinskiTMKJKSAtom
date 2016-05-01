package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

public class Simulation /**implements Runnable*/{//by Jacek Pi�ka
	
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
	
	volatile ArrayList<Particle> atoms = new ArrayList<Particle>();
	volatile ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	public Simulation(Options options/**String element, double m, double V, String shape, double A*/){
		System.out.println("Rozpoczynam tworzenie simulation");
		float molMass=0;
		float elementMass=0;
		float density=0;
		a=options.a;
		if(options.element.equals("Uran")){
			molMass=uranMolMass;
			density=19050;
		}
		else if(options.element.equals("Uran")){
			molMass=plutonMolMass;
			density=19816;
		}
		mol=(float)(options.m/1000)/molMass;
		System.out.println("Liczba moli: "+mol);
		numberOfAtoms=(int) ((mol*6.02*Math.pow(10.0,8.0)));//must be pow(10.0,23)
		System.out.println("ilo�� atom�w: "+numberOfAtoms);
		distance=a/Math.pow(numberOfAtoms, 1.0/3.0);
		elementMass=(float) (molMass/(6.02*Math.pow(10.0,8.0)));
		
		float elementV=(float) ((elementMass/density));
		System.out.println("Obj�tos� atomu: "+elementV);
		//distance=2*(Math.pow(elementV*3/(3.14*4),1.0/3.0)); <- It should work, but it doesn't
		System.out.println("Odleg�o�� mi�dzy atomami: "+distance);
		
		//Adding atomic net
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
		for(int i=0; i<numberOfAtoms-1; i++){
			if(atoms.get(i).x==atoms.get(i+1).x){
				System.out.println("B��d!");
			}
		}
		System.out.println("Sze�cian ma wymiary: "+nx+" "+ny+" "+nz);
		Random r = new Random();
		Particle startAtom = atoms.get(r.nextInt(numberOfAtoms));
		neutrons.add(new Particle(startAtom.x,startAtom.y,startAtom.z,0,true));
		numberOfNeutrons++;
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
							System.out.println("Ilo�� atom�w: "+numberOfAtoms);
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
										energyMeV+=200*Math.pow(10, 15);
										numberOfFission++;
										for(int k=0; k<2;k++){ //add 2 neutrons
											neutrons.add(new Particle(atomx.get(nA), atomy.get(nA), atomz.get(nA), r.nextInt(6)+1, false));
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
							System.out.println("Liczba neutron�w: "+numberOfNeutrons);
							for (int i=0; i<neutrons.size(); i++){
								if(neutrons.get(i).x>a/2||neutrons.get(i).x<-a/2||neutrons.get(i).y>a/2
										||neutrons.get(i).y<-a/2||neutrons.get(i).z>a/2||neutrons.get(i).z<-a/2){
								neutrons.remove(i);
								numberOfNeutrons--;
								i--;
							}
						}
						System.out.println("Liczba rozpad�w: "+numberOfFission);
						energy=(float) (energyMeV*1.602*Math.pow(10,-13));
						if(energyMeV>maxEnergyMeV){
							maxEnergyMeV=energyMeV;
							maxEnergy=(float) (maxEnergyMeV*1.602*Math.pow(10,-13));
						}
						System.out.println("E("+time+"us): "+energy+"J, "+energyMeV+"MeV");
						/**if(neutrons.size()<1){
						System.out.println("Brak neutron�w!");
						break;
						}**/
						time++;
					//}
				//}
			//});
		//}
	}
}
