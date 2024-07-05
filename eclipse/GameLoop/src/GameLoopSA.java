
public class GameLoopSA {
	private long startTime = 0; 
	private long deltaTime = 0;
	
	// Tick para imprimir a posicao do jogador a cada segundo
	long segTick = 0;
	
	// Indica se está na hora de imprimir a posição do jogador
	boolean canPrint = false;
	
	// Posicao do personagem
	private long posX = 0;
	
	public GameLoopSA() {
		
	}
	
	public void startGL() {
		posX = 0;
		
		while (true) {
			
			startTimer();
			
			readInput();
			update();
			render();
			
			endTimer();
		}
	}
	
	protected void readInput() {
		
	}

	protected void update() {
		segTick += deltaTime;
		posX += 1;
		
		if (segTick >= 1000) {
			segTick -= 1000;
			canPrint = true;
		}
	}
	
	protected void render() {
		
		if (canPrint) {
			System.out.println("Após 1 seg o Jogador está na posição (X): " 
				+ posX);
			canPrint = false;
		}
	}
	

	private void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	private void endTimer() {
		deltaTime = System.currentTimeMillis() - startTime;
	}
}
