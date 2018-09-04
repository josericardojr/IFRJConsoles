
public class Main {
	
	public static void somaVet(int[] A, int[] B, int[] res, int elementos){
		
		for (int i = 0; i < elementos; i++){
			res[i] = A[i] / (B[i] + 1);
		}
	}
	
	public static void inicializa(int[] vet, int max){
		
		for (int i = 0; i < vet.length; i++)
			vet[i] = (int) (Math.random() * max);
	}
	
	
	public static void main(String args[]){
		
		int size = 100000000;
		int maxThread = 8;
		
		SomaThread [] threads = new SomaThread[maxThread];
		
		int [] vetA = new int[size];
		int [] vetB = new int[size];
		int [] vetC = new int[size];
		
		inicializa(vetA, 100);
		inicializa(vetB, 200);
		
		long start = System.currentTimeMillis();
		somaVet(vetA, vetB, vetC, size);
		long seqTime = System.currentTimeMillis() - start;
		
		System.out.println("Iniciando processamento");
		start = System.currentTimeMillis();
		int nElements = size / maxThread;
		for (int i = 0; i < maxThread; i++){
			threads[i] = new SomaThread(vetA, vetB, vetC, 
					nElements, i * nElements);
			threads[i].start();
		}
		
		
		for (int i = 0; i < maxThread; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long timeParal = System.currentTimeMillis() - start;
		float speedUp = (float) seqTime / (float) timeParal;
		
		System.out.println("Max Proc: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Processamento finalizado");
		System.out.println("Processamento Seq: " + seqTime);
		System.out.println("Processamento Paral: " + timeParal);
		System.out.println("Speed up: " + speedUp);
	}

}
