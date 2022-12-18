import java.util.ArrayList;
import java.util.List;

public class Quadtree {
	
	public static final int MAX_PARTICLES = 3;
	public static final int TL = 0;
	public static final int TR = 1;
	public static final int BL = 2;
	public static final int BR = 3;
	
	float x;
	float y;
	float w;
	float h;
	
	
	List<Integer> listPart = new ArrayList<Integer>();
	Quadtree[] childs = new Quadtree[4];
	
	public Quadtree(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	
	public void build(List<Particle> _p, int idx) {
		
		Particle _part = _p.get(idx);
		
		if (listPart.size() < MAX_PARTICLES) {
			listPart.add(idx);
			
			printQuad(_part, this);
		} else {
			
			if ( (_part.x < x + (w / 2)) && (_part.y < y + (h / 2)) ) {
				if (childs[TL] == null) 
					childs[TL] = new Quadtree(x, y, w / 2.0f, h / 2.0f);
				
				childs[TL].build(_p, idx);
			} else if ( (_part.x > (x + w) - (w / 2)) && (_part.y < (y + h) - (h / 2)) ) {
				if (childs[TR] == null) 
					childs[TR] = new Quadtree(x + w / 2.0f, y, w / 2.0f, h / 2.0f);
				
				childs[TR].build(_p, idx);
			} else if (_part.x < x + (w / 2) && (_part.y > (y + h) - (h / 2)) ){
				if (childs[BL] == null) 
					childs[BL] = new Quadtree(x, y + h / 2.0f, w / 2.0f, h / 2.0f);
				
				childs[BL].build(_p, idx);
			} else {
				if (childs[BR] == null) 
					childs[BR] = new Quadtree(x + w / 2.0f, y + h / 2.0f, w / 2.0f, h / 2.0f);
				
				childs[BR].build(_p, idx);
			}
		}
	}


	private void printQuad(Particle _p, Quadtree _q) {
		System.out.println("Particle (" + _p.x + "," + _p.y + ") = " +
				"Quad (" + _q.x + "," + _q.y + "," + _q.w + "," + _q.h + ")");
	}
}
