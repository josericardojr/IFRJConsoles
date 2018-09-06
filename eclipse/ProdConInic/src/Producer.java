import java.util.concurrent.Semaphore;

public class Producer extends Thread {
	Buffer buffer;
	Semaphore semaphore;
	int id;
	
	public Producer(Buffer buffer, int id, Semaphore s) {
		this.buffer = buffer;
		this.id = id;
		this.semaphore = s;
	}
	
	@Override
	public void run() {
		while (true) {
			
			
			int v = (int) (Math.random() * 1000.0);
			
			try {
				semaphore.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!buffer.isAvailable()) {
				buffer.put(v);
			
				System.out.println("Producer " + id + 
					" produziu o valor " + v);
			}
			semaphore.release();
			
			try {
				sleep(100 + (int)(Math.random() * 100.0));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
