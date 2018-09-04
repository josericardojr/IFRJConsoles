
public class SomaThread extends Thread {
	
	int A[];
	int B[];
	int C[];
	int numElements;
	int offset;
	
	public SomaThread(int _A[], int _B[], int _C[], 
			int nElements, int _offset) {
		A = _A;
		B = _B;
		C = _C;
		numElements = nElements;
		offset = _offset;
	}
	
	@Override
	public void run() {
		super.run();
		
		for (int i = 0; i < numElements; i++){
			C[offset + i] = A[offset + i] / (B[offset + i]+1);
		}
	}
}
