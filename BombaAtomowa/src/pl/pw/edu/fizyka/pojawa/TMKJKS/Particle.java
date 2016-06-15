package pl.pw.edu.fizyka.pojawa.TMKJKS;

/**
 * 
 * @author Jacek Piłka
 *
 *	Particle - atom or neutron
 *
 */

public class Particle {
	public float x;
	public float y;
	public float z;
	public float dx;
	public float dy;
	public float dz;
	
	boolean first=false; //Is it first neutron (for not to be taked by atom)
	int change=0; //Something change?
	public Particle(float X, float Y, float Z, float DX, float DY, float DZ, boolean First){
		x=X;
		y=Y;
		z=Z;
		dx=DX;
		dy=DY;
		dz=DZ;
		first=First;
	}
	public void interact(Particle particle, int r, double distanceX, double distanceY, double distanceZ){
		//0-nothing, 1-fission of atom, 2-atom takes neutron, 3-elastic collision
		if (Math.abs(this.x-particle.x)<distanceX&&Math.abs(this.y-particle.y)<distanceY&&
				Math.abs(this.z-particle.z)<distanceZ){
			if(r<85||first)
				change=1;
			else if (r>99)
				change=3;
			else
				change=2;
		}
	}
}
