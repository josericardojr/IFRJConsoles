import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class MB extends JPanel {
	private static final long serialVersionUID = 1L;
    private static double xc   = -0.6784989999254943;
    private static double yc   = 0.35136497669350003;
    private static double size = 3;
    private static int n;
	private static double zoom;
	private static JTextField  x;
	private static JTextField  y;
	private static JTextField zoomBox;
	private static BufferedImage picture;
    private static MB mandelBrot;
	private MB(){
		repaint();
	}
	
    public static void main(String[] args)  {
    	int processors = Runtime.getRuntime().availableProcessors();
    	  
    	JFrame f = new JFrame("Mandelbrot Set"); 
        JToggleButton AutoZoom = buildSouthPanel(processors, f);    
        
        buildNorthPanel(f);
        
        n   = 1300;   // create n-by-n image

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mandelBrot = new MB();
        mandelBrot.addMouseListener(new MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent e) {
        			double dx = ((e.getX() - n/2) * size/ n);
					double dy = ((e.getY() - n/2) * size/ n) ;
					xc += dx;
					yc -= dy;
					System.out.println(xc + " " + yc);
				if (SwingUtilities.isRightMouseButton(e)){
					size = size * 2;
				} else {
					size = size /2;
				}
				RedrawLabels();
				mandelBrot.repaint();
			}
		});
        
        f.add (mandelBrot);
        f.setSize(n-60, n + 40 );
        f.setVisible(true);
        
        while(true){
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        	if (AutoZoom.isSelected()){
				size = size /1.05;
				RedrawLabels();
				mandelBrot.repaint();
        	}
        }
        
    }

	private static JToggleButton buildSouthPanel(int processors, JFrame f) {
		JPanel southPanel = new JPanel(new GridLayout(1, 8));
        JLabel cores = new JLabel("CPU cores: " + processors);
        JLabel zoomLabel = new JLabel("Zoom: ");
        zoomBox = new JTextField(16);
        JLabel xl = new JLabel("x: ");
        x = new JTextField(16);
        JLabel yl = new JLabel("y: ");
        y = new JTextField(16);
        JToggleButton AutoZoom = new JToggleButton("Auto Zoom");
        JButton Reset = new JButton("Reset");

        Reset.addActionListener(e -> {
			size = 3;
			xc   = -0.6784989999254943;
			yc   = 0.35136497669350003;
			mandelBrot.repaint();
		});
        x.addActionListener(e -> {
			try {
				xc = Double.parseDouble(x.getText());
			} catch (NumberFormatException e1) {
				xc = 0;
				x.setText(0 + "");
			}
			mandelBrot.repaint();
		});
        y.addActionListener(e -> {
			try {
				yc = Double.parseDouble(y.getText());
			} catch (NumberFormatException e1) {
				yc = 0;
				y.setText(0 + "");
			}
			mandelBrot.repaint();
		});
        zoomBox.addActionListener(e -> {
			try {
				zoom = Double.parseDouble(zoomBox.getText());
				size = 1/zoom;
			} catch (NumberFormatException e1) {
				size = 3;
				zoom = 1/size;
				zoomBox.setText(zoom + "");
			}
			mandelBrot.repaint();
		});
        
        cores.setHorizontalAlignment(SwingConstants.CENTER);
        zoomLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        xl.setHorizontalAlignment(SwingConstants.RIGHT);
        yl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        southPanel.add(cores);
        southPanel.add(zoomLabel);
        southPanel.add(zoomBox);
        southPanel.add(xl);
        southPanel.add(x);
        southPanel.add(yl);
        southPanel.add(y);
        southPanel.add(AutoZoom);
        southPanel.add(Reset);

        f.add(southPanel, BorderLayout.SOUTH);
        RedrawLabels();
		return AutoZoom;
	}

	private static void buildNorthPanel(JFrame f) {
		JPanel northPanel = new JPanel();
        
        
        JButton seahorses = new JButton("Seahorses");
        seahorses.addActionListener(e -> {
			size = 0.000768 ;
			xc = -0.7458011359798999;
			yc = 0.1358265072322056;
			RedrawLabels();
			mandelBrot.repaint();
		});
        JButton elephants = new JButton("Elephants");
        elephants.addActionListener(e -> {
			size = 0.0168 ;
			xc = 0.40974919718989034;
			yc = 0.2049853943617693;
			RedrawLabels();
			mandelBrot.repaint();
		});
        JButton spirals = new JButton("Spirals");
        spirals.addActionListener(e -> {
			size = 0.0000168 ;
			xc = -0.7752815537115518;
			yc = 0.1150061428593655;
			RedrawLabels();
			mandelBrot.repaint();
		});
        
        northPanel.add(seahorses);
        northPanel.add(elephants);
        northPanel.add(spirals);
        f.add(northPanel, BorderLayout.NORTH);
	}
    
    private static void RedrawLabels(){
    	zoom = (1/ size);
    	zoomBox.setText(zoom + "");
    	x.setText(xc + "");
    	y.setText(yc + "");
    }
    
    public void paintComponent(Graphics g){
	
		  n   = Math.min(getWidth(), getHeight()); 
		  n = n - (n % 4);
		  picture = new BufferedImage(n, n,BufferedImage.TYPE_INT_ARGB);
		  
		  int[][] colArray = new int[n][n];
		
		  //new mandelbrot.ColorFetcher(xc, yc, n, colArray , t , size);
		  Thread[] threads = new Thread[4];
		  
		  for ( int t = 0 ; t < threads.length ; t++){
			  threads[t] = new Thread(new ColorFetcher(xc, yc, n, colArray, t , size));
			  threads[t].start();
		  }
		  try{
			  for (Thread thread : threads) {
				  thread.join();
			  }
		  } catch(InterruptedException e){
			  e.printStackTrace();
		  }
		  
		  //draw picture
		  for (int i = 0; i < n; i++) {
		      for (int j = 0; j < n; j++) {
		    	  MB.picture.setRGB(i, n-1-j, colArray[i][j]);
		      }
		  }     
		  
		  g.drawImage(picture, 0, 0, null);
		  System.out.println("drawing..." + size * 1000);
	}
}
