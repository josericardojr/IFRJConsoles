import java.util.ArrayList;
import java.util.List;

public class Main {

	static List<Particle> particles = new ArrayList<Particle>();
	static Quadtree quad;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		quad = new Quadtree(0, 0, 20, 20);

		int N_Particles = 10;
		
		for (int i = 0; i < N_Particles; i++) {
			float x = (float) Math.random() * 20;
			float y = (float) Math.random() * 20;
			
			Particle p = new Particle();
			p.x = x; p.y = y;
			particles.add(p);
		}
		
		for (int i = 0; i < particles.size(); i++)
			quad.build(particles, i);
	}

}
