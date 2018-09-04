
public class Main {
	
	public static void somaVet(int[] A, int[] B, int[] res, int elementos){
		
		for (int i = 0; i < elementos; i++){
			res[i] = A[i] + B[i];
		}
	}
	
	public static void inicializa(int[] vet, int max){
		
		for (int i = 0; i < vet.length; i++)
			vet[i] = (int) (Math.random() * max);
	}
	
	
	public static void main(String args[]){
		
		int size = 100;
		
		int [] vetA = new int[size];
		int [] vetB = new int[size];
		int [] vetC = new int[size];
		
		inicializa(vetA, 100);
		inicializa(vetB, 200);
		
		System.out.println("Iniciando processamento");
		somaVet(vetA, vetB, vetC, size);		
		System.out.println("Processamento finalizado");
	}

}
