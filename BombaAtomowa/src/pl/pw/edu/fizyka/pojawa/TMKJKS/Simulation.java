package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.ArrayList;

public class Simulation {
	float Energy =0;
	float maxEnergy=0;
	double distance=0;
	float mol=0;
	float uranMolMass=238; //g/mol
	float plutonMolMass=244; //g/mol
	int numberOfParticles=0;
	
	ArrayList<Particle> atoms = new ArrayList<Particle>();
	ArrayList<Particle> neutrons = new ArrayList<Particle>();
	
	Simulation(String element, double density, double m, double V, String shape, double a){
		float molMass=0;
		float elementMass;
		if(element.equals("Uran")){
			molMass=uranMolMass;
		}
		else if(element.equals("Uran")){
			molMass=plutonMolMass;
		}
		mol=(float)(m/1000)/molMass;
		numberOfParticles=(int) (mol*6.02*Math.pow(10.0,23.0));
		elementMass=(float) (molMass/(6.02*Math.pow(10.0,23.0)));
		
		float elementV=(float) (elementMass/density);
		distance=2*(Math.pow(elementV*3/(3.14*4),1/3));
		
		float x=0; float y=0;
		for(int i=0; i<numberOfParticles; i++){
			atoms.add(new Particle(x,y));
			if(x>=a){
				x+=distance;
			}
			else{
				x=0;
				y+=distance;
			}
		}
		//TODO Add neutron + simulation
	}
}
