import java.awt.Color;

public class ColorFetcher implements Runnable {
	ColorFetcher( double xc, double yc, int n, int[][] colArray, int t, double size) {
		this.xc = xc;
		this.yc = yc;
		this.n = n;
		this.colArray = colArray;
		this.t = t;
		this.size = size;
	}


    private double xc ;
	private double yc;
	private int n;
	private int[][] colArray;
	private int t;
	private double size;
	
	@Override
	public void run() {
	  int start = t * n/4;
	  int end  = (t+1) * (n/4);
	  
	  
	  for (int i =start; i < end; i++) {
	      for (int j = 0; j < n; j++) {
	          double x0 = xc - size/2 + size*i/n;
	          double y0 = yc - size/2 + size*j/n;
	          colArray[i][j] =  getColor(mand(new Complex(x0, y0), 255));
	      }
	  }
	}

	private static int getColor(short t){
    	 
    	
    	if (t  == 0 ) return new Color(255,255,255).getRGB();
    	if (t == 255) return new Color(0,0,0).getRGB();
    	
    	float h = t % 60;
    	
    	float s = 0.0f;
    	if (t < 200){
    		s = 0.005f * (200 - t);
    	}
    	
    	return Color.HSBtoRGB(h/60, 1.0f - s , 1.0f);
    }

	private static short mand(Complex z0, int max) {
        Complex z = z0;
        for (short t = 0; t < max; t++) {
        	
        	double zR2 = z.re * z.re;
        	double zI2 = z.im * z.im;
        	
            if (zR2 + zI2 > 4.0) return t;
            
            double newIm = z.re * z.im;
            newIm += newIm;
            newIm += z0.im;
            
            double newRe = zR2 - zI2 + z0.re;
            z = new Complex(newRe, newIm);
        }
        return 255;
    }
}
