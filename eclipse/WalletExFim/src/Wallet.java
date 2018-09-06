import java.util.concurrent.Semaphore;

public class Wallet {

	private int money;
	private Semaphore semaforo;
	
	public Wallet() {
		money = 0;
		semaforo = new Semaphore(1);
	}
	
	public int getMoney() { 
		return money;
	}
	
	public void addMoney(int m) {
		
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < m; i++)
			money++;
		
		semaforo.release();
	}
}
