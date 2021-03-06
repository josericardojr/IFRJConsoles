import java.util.concurrent.Semaphore;

public class Producer extends Thread {
	Buffer buffer;
	int id;
	
	public Producer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
	}
	
	@Override
	public void run() {
		while (true) {
			
			
			int v = (int) (Math.random() * 1000.0);
			
			if (!buffer.isAvailable()) {
				buffer.put(v);
			
				System.out.println("Producer " + id + 
					" produziu o valor " + v);
			}
			
			try {
				sleep(100 + (int)(Math.random() * 100.0));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
