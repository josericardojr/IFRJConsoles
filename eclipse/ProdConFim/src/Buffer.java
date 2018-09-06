
public class Buffer {

	int value;
	boolean available;
	
	public Buffer() {
		value = 0;
		available = false;
	}
	
	public void put(int v) {
		value = v;
		available = true;
	}
	
	public int get() {
		available = false;
		return value;	
	}
	
	public boolean isAvailable() {
		return available;
	}
}
