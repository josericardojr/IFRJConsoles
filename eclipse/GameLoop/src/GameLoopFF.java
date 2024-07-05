
public class GameLoopFF {
	private long startTime = 0; 
	private long deltaTime = 0;
	
	// Tick para imprimir a posicao do jogador a cada segundo
	long segTick = 0;
	
	// Indica se está na hora de imprimir a posição do jogador
	boolean canPrint = false;
	
	// Posicao do personagem
	private long posX = 0;
	
	// Frequencia
	private int glFreq = 0;
	
	public GameLoopFF(int _freq_ms) {
		glFreq = _freq_ms;
	}
	
	public void startGL() {
		posX = 0;
		
		while (true) {
			startTimer();
			
			
			readInput();
			update();
			render();
			
			endTimer();
			
			sync();
			
			

		}
	}
	
	private void sync() {
		int _diff = Math.max(0, (int)((long)glFreq - deltaTime));
		
		try {
			Thread.sleep(_diff);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		System.out.println("Após 33 ms o Jogador está na posição (X): " 
			+ posX);
	} 
	

	private void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	private void endTimer() {
		deltaTime = System.currentTimeMillis() - startTime;
	}
}
