import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame implements ActionListener {
	private JButton startButton = new JButton("Start");
	private JButton quitButton = new JButton("Quit");
	private JButton pauseButton = new JButton("Pause");
	private GameLoopDST gameLoop = new GameLoopDST();
	   
	public Main() {
	      super("Fixed Timestep Game Loop Test");
	      Container cp = getContentPane();
	      cp.setLayout(new BorderLayout());
	      JPanel p = new JPanel();
	      p.setLayout(new GridLayout(1,3));
	      p.add(startButton);
	      p.add(pauseButton);
	      p.add(quitButton);
	      cp.add(gameLoop, BorderLayout.CENTER);
	      cp.add(p, BorderLayout.SOUTH);
	      setSize(500, 500);
	      
	      startButton.addActionListener(this);
	      quitButton.addActionListener(this);
	}

	public static void main(String[] args) {
		Main _game = new Main();
		_game.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			boolean _isStarted = gameLoop.isStarted();
			
			if (_isStarted) {
				gameLoop.stopGL();
				((JButton)e.getSource()).setText("Start");
			} else {
				gameLoop.startGL();
				((JButton)e.getSource()).setText("Stop");
			}
		} else if (e.getSource() == quitButton) {
			gameLoop.stopGL();
			System.exit(0);
		}
	}
}
