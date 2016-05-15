package pl.pw.edu.fizyka.pojawa.TMKJKS;

public class Particle {//by Jacek Piłka
	public float x;
	public float y;
	public float z;
	public int direction; //Where neutrons go?
	boolean first=false; //Is it first neutron (for not to be taked by atom)
	int change=0; //Something change?
	public Particle(float X, float Y, float Z, int D, boolean First){
		x=X;
		y=Y;
		z=Z;
		direction=D;
		first=First;
	}
	public void interact(Particle particle, int r, double distanceX, double distanceY, double distanceZ){//0-nothing, 1-fission of atom, 2-atom takes neutron
		if (Math.abs(this.x-particle.x)<distanceX&&Math.abs(this.y-particle.y)<distanceY&&
				Math.abs(this.z-particle.z)<distanceZ){
			if(r<85||first)
				change=1;
			else
				change=2;
		}
	}
}
