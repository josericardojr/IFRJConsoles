import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Board extends JPanel implements 
	ComponentListener, MouseListener {

	static final int DIMENSION = 90;
	static final int DIMENSION_SQUARED = DIMENSION * DIMENSION;
	static final int CELL_DIMENSION = 8;
	static final int ALIVE = 1;
	static final int DEAD = 0;
	
	int cells[] = new int[DIMENSION_SQUARED];
	
	public void startPattern1() {
		for (int i = 0; i < DIMENSION; i++) {
			for (int k = 0; k < DIMENSION; k++) {
				if (i == k) setCellState(k, i, ALIVE);
				if (k == (DIMENSION - 1 - i)) setCellState(k, i, ALIVE);
			}
		}
	}
	
	public Board() {
		addComponentListener(this);
		addMouseListener(this);
		
		setSize(500,  500);
		startPattern1();
	}
	
	private void restartGame() {
		for (int i = 0; i < DIMENSION_SQUARED; i++) {
			cells[i] = DEAD;
		}	
	}
	
	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		
		for (int i = 0; i < DIMENSION; i++) {
			
			for (int k = 0; k < DIMENSION; k++) {
				if (cells[i * DIMENSION + k] == ALIVE) arg0.setColor(Color.GREEN);
				else arg0.setColor(Color.GRAY);
				
				arg0.fillRect(k * CELL_DIMENSION, i * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
				
				arg0.setColor(Color.BLACK);
				arg0.drawRect(k * CELL_DIMENSION, i * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
			}
		}
	}
	

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		System.out.println(arg0);
		System.out.println(getWidth());
		
	}
	
	public void setCellState(int x, int y, int state) {
		cells[y * DIMENSION + x] = state;
	}
	
	public int getCellState(int x, int y) {
		return cells[y * DIMENSION + x];
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		int idx = x / CELL_DIMENSION;
		int idy = y / CELL_DIMENSION;
		
		if (idx < DIMENSION && idy < DIMENSION) {
			cells[idy * DIMENSION + idx] = ALIVE;
		}
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
