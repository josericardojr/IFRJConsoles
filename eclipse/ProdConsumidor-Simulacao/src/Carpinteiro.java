
public class Carpinteiro implements Runnable {
	private final Deposito deposito;
	private String nome;

	public Carpinteiro(Deposito deposito, String nome) { 
		this.deposito = deposito; 
		this.nome = nome;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Madeira item = deposito.consumir(); // Pode bloquear
				// Simula o tempo para construir o brinquedo
				Thread.sleep((long) (Math.random() * 2000 + 1500)); 
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
