import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		Semaphore semaphore = new Semaphore(1);
		
		Producer p1 = new Producer(buffer, 1, semaphore);
		Producer p2 = new Producer(buffer, 2, semaphore);
		Consumer c1 = new Consumer(buffer, 1, semaphore);
		Consumer c2 = new Consumer(buffer, 2, semaphore);
		
		p1.start();
		p2.start();
		c1.start();
		c2.start();

	}

}
