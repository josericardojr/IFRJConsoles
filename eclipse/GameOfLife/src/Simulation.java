
public class Simulation extends Thread {
	
	Board board;
	volatile boolean simulationIsRunning = false;
	CellProcessor[] processors;
	int numThreads;
	
	public Simulation(Board board, int threads) {
		this.board = board;
		numThreads = threads;
		
		processors = new CellProcessor[numThreads];
	}
	
	public void startSimulation() {
		simulationIsRunning = true;
		start();
	}
	
	public void stopSimulation() {
		simulationIsRunning = false;
	}
	
	@Override
	public void run() {
		super.run();
		
		while (simulationIsRunning) {
			
			// Atualizar memoria local
			runThreads();
			
			// Atulizar board
			try {
				for (int i = 0; i < numThreads; i++)
					processors[i].join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for (int i = 0; i < numThreads; i++)
				processors[i].updateLocalToBoard();
			
			// Desenhar
			board.repaint();
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void runThreads() {
		int size = Board.DIMENSION / numThreads;
		
		for (int i = 0; i < numThreads; i++) {
			int offset = i * size;
			processors[i] = new CellProcessor(board, offset, offset, size, size);
			processors[i].start();
		}
	}

}
