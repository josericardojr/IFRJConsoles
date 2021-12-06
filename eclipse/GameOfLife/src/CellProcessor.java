
public class CellProcessor extends Thread {

	Board board;
	volatile boolean isRunning = false;
	int startOffsetX, startOffsetY;
	int countX, countY;
	int [] localCells;
	
	public CellProcessor(Board board, int startX, int startY,
			int countX, int countY) {
		this.board = board;
		this.startOffsetX = startX; this.startOffsetY = startY;
		this.countX = countX; this.countY = countY;
		
		localCells = new int[countX * countY];
	}
	
	private void setLocalCellState(int x, int y, int state) {
		localCells[y * countX + x] = state;
	}
	
	private int getLocalCellState(int x, int y) {
		return localCells[y * countX + x];
	}
	
	public void updateLocalToBoard() {
		for (int i = 0; i < countY; i++) {
			
			for (int k = 0; k < countX; k++) {
				int localCellState = getLocalCellState(k, i);
				
				board.setCellState(startOffsetX + k, startOffsetY + i,
						localCellState);
			}
		}
	}
	@Override
	public void run() {
		super.run();
			
		for (int i = 0; i < countY; i++) {

			for (int k = 0; k < countX; k++) {

				int nCountAlive = 0;
				int cellState = 
						board.getCellState(startOffsetX + k, startOffsetY + i);
				setLocalCellState(k, i, cellState);

				// Iteracao local entre os vizinhos
				for (int ny = -1; ny < 2; ny++) {

					for (int nx = -1; nx < 2; nx++) {

						int idx = k + nx;
						int idy = i + ny;

						// indice x e y dos vizinhos fora do limite
						if (idx < 0 && idy < 0) continue;
						// desconsiderar a celula atual, somente os vizinhos
						if (idx == k && idy == i) continue;

						if (idx < 0) idx = 0; if (idx >= countX) idx = countX - 1;
						if (idy < 0) idy = 0; if (idy >= countY) idy = countY - 1;


						// Armazenar a quantidade de celulas vizinhas que estão vivas
						if (board.getCellState(startOffsetX + idx, startOffsetY + idy) == Board.ALIVE) 
							nCountAlive++;
					}
				}


				// Atualizacao da celula

				// Caso 1
				if (cellState == Board.ALIVE && (nCountAlive == 2 || nCountAlive == 3)) {
					continue;
				} else {
					// Caso 2
					if (cellState == Board.ALIVE && nCountAlive < 2) {
						setLocalCellState(k, i, Board.DEAD);
					} else {
						// Caso 3
						if (cellState == Board.ALIVE && nCountAlive >= 4) {
							setLocalCellState(k, i, Board.DEAD);
						} else {
							// Caso 4
							if (cellState == Board.DEAD && nCountAlive == 3) {
								setLocalCellState(k, i, Board.ALIVE);
							}
						}
					}
				}

			}
		}
	}
}
