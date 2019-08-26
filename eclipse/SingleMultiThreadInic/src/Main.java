
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
		
		int size = 10000;
		
		float [] vetA = new float[size];
		float [] vetB = new float[size];
		float [] vetC = new float[size];
		
		inicializa(vetA, 2);
		inicializa(vetB, 3);
		
		System.out.println("Iniciando processamento");
		processor(vetA, vetB, vetC, size);
		System.out.println("Processamento finalizado");
		
	}

}
