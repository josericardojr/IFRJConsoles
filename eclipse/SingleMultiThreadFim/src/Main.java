
public class Main {
	
	public static void processor(float[] A, float[] B, float[] res, int elementos){
		
		for (int i = 0; i < elementos; i++){
			res[i] = (float) Math.pow(A[i], B[i]);
		}
	}
	
	public static void inicializa(float[] vet, int max){
		
		for (int i = 0; i < vet.length; i++)
			vet[i] = (float) (Math.random() * (float)max);
	}
	
	
	public static void main(String args[]){
		
		int size = 1000000;
		int nThreads = 1;
		
		float [] vetA = new float[size];
		float [] vetB = new float[size];
		float [] vetC = new float[size];
		
		inicializa(vetA, 2);
		inicializa(vetB, 3);
		
		System.out.println("Iniciando processamento");
		
		long start = System.currentTimeMillis();
		processor(vetA, vetB, vetC, size);
		long seqTime = System.currentTimeMillis() - start;
		System.out.println("Processamento finalizado");
		
		System.out.println("Iniciando processamento parelo");
		
		inicializa(vetA, 4);
		inicializa(vetB, 5);
		
		start = System.currentTimeMillis();
		ThreadWorker threads[] = new ThreadWorker[nThreads];
		int nElementsThread = size / nThreads;
		for (int i = 0; i < nThreads; i++) {
			int offset = i * nElementsThread;
			threads[i] = new ThreadWorker(vetA, vetB, vetC, nElementsThread, 
					offset);
			threads[i].start();
		}

				
		try {
			for (int i = 0; i < nThreads; i++)
				threads[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long paralTime = System.currentTimeMillis() - start;
		
		
		System.out.println("Finalizado processamento parelo");
		
		System.out.println("Seq. Time: " + seqTime);
		System.out.println("Parallel Time: " + paralTime);
	}

}
