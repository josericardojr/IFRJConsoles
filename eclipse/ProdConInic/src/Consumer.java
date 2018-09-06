import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

	Buffer buffer;
	Semaphore semaphore;
	int id;
	
	
	public Consumer(Buffer buffer, int id, Semaphore s) {
		this.buffer = buffer;
		this.id = id;
		this.semaphore = s;
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				semaphore.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (buffer.isAvailable()) {
				int v = buffer.get();
			
				System.out.println("Consumer " + id + 
						" consumiu o valor " + v);
			}
			semaphore.release();
			
			try {
				sleep(100 + (int)(Math.random() * 1000.0));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
