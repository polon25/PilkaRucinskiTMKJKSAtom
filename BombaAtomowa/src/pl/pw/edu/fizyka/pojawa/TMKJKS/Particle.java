package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.Random;

public class Particle {
	public float x;
	public float y;
	public float z;
	public int direction;
	boolean first=false;
	int change=0;
	public Particle(float X, float Y, float Z, int D, boolean First){
		x=X;
		y=Y;
		z=Z;
		direction=D;
		first=First;
	}
	public void interact(Particle particle){//0-nothing, 1-participation of atom, 2-atom takes neutron
		if (this.x==particle.x&&this.y==particle.y&&this.z==particle.z){
			Random r=new Random();
			if(r.nextInt(100)<100||first)
				change=1;
			else
				change=2;
		}
	}
}
