package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.Random;

public class Particle {//by Jacek Pi³ka
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
	public void interact(Particle particle, int r){//0-nothing, 1-fission of atom, 2-atom takes neutron
		if (this.x==particle.x&&this.y==particle.y&&this.z==particle.z){
			if(r<85||first)
				change=1;
			else
				change=2;
		}
	}
}
