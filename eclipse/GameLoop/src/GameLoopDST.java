import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameLoopDST extends JPanel implements Runnable {
	
	// Posicao do personagem
	private float posX = 0;

	private float totalTime = 0;
	private boolean isStarted = false;
	private long startGame = 0;
	JLabel txtFPS = new JLabel();
	JLabel txtElapsedTime = new JLabel();
	
	public boolean isStarted() {return isStarted; }
	
	
	public GameLoopDST() {
		JPanel p = new JPanel();
	    p.setLayout(new GridLayout(1,2));
		p.add(txtFPS);
		p.add(txtElapsedTime);
		
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
	}
	
	public void startGL() {
		isStarted = true;
		startGame = System.currentTimeMillis();
		Thread _t = new Thread(this);
		_t.start();
	}
	
	public void stopGL() {
		isStarted = false;
	}
	
	private void gameLoop() {
		posX = 0;
		float deltaTime = 0;
		totalTime = 0;
		
		while (isStarted) {
			long startTime = System.currentTimeMillis();
			
			readInput();
			update(deltaTime);
			render();
			
			// Calcular o deltaTime
			long endTime = System.currentTimeMillis();
			deltaTime = (float)(endTime - startTime) / 1000f;
			
			calculateMetrics(deltaTime);
		}
	}
	
	
	protected void readInput() {
		
	}
	
	

	protected void update(float _dt) {
		
		int _sleep = 10 + (int)(Math.random() * 40.0f); 
		
		try {
			Thread.sleep(_sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		posX += 50.0f * _dt;
	}
	
	protected void render() {
		repaint();
	}
	
	private float elapsedTime = 0;
	private int interactions = 0;
	private void calculateMetrics(float deltaTime) {
		elapsedTime += deltaTime;
		totalTime += elapsedTime;
		interactions++;
		
		if (elapsedTime > 1.0f) {
			elapsedTime -= 1.0f;
			txtFPS.setText("FPS: " + interactions);
			interactions = 0;
		}
		
		txtElapsedTime.setText(String.format("Elapsed Time: %.0f sec.", 
				(System.currentTimeMillis() - startGame) / 1000f));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval((int)posX, 50, 50, 50);
	}

	@Override
	public void run() {
		gameLoop();
	}
}
