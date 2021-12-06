import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	
	public static void main(String[] args) {
		int generation = 0;
		Board board = new Board();
		Simulation simulation = new Simulation(board, 3);
		
		
		JFrame frame = new JFrame("Game of Life");
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(800,800));
		frame.add(board, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		JPanel pnlGUI = new JPanel();
		pnlGUI.setLayout(new FlowLayout(FlowLayout.LEFT));
		Button btnStart = new Button("Start");
		Button btnStop = new Button("Stop");
		JLabel lblGeneration = new JLabel();
		lblGeneration.setText("Generation: " + generation);
		pnlGUI.add(btnStart);
		pnlGUI.add(btnStop);
		pnlGUI.add(lblGeneration);
		frame.add(pnlGUI, BorderLayout.SOUTH);
		frame.pack();
		
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				simulation.startSimulation();	
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				simulation.stopSimulation();
			}
		});
	}

}
