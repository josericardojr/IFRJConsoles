import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

	Buffer buffer;
	int id;
	
	
	public Consumer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
	}
	
	@Override
	public void run() {
		while (true) {
			
			if (buffer.isAvailable()) {
				int v = buffer.get();
			
				System.out.println("Consumer " + id + 
						" consumiu o valor " + v);
			}
			
			try {
				sleep(100 + (int)(Math.random() * 1000.0));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
