
public class ThreadWorker extends Thread {

	float A[];
	float B[];
	float C[];
	int nElements;
	int nOffset;
	
	public ThreadWorker(float _A[], float _B[], float _C[], 
			int elements, int _offset) {
		A = _A;
		B = _B;
		C = _C;
		
		nElements = elements;
		nOffset = _offset;
	}
	
	@Override
	public void run() {
		super.run();
		
		for (int i = 0; i < nElements; i++) {
			C[nOffset + i] = (float) Math.pow(A[nOffset + i], B[nOffset + i]);
		}
	}
}
