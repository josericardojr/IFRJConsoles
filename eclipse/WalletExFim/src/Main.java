
public class Main {

	public static void main(String[] args) {
		Wallet wallet = new Wallet();
		int nThreads = 10;
		int correctResult = 10000 * nThreads;
		
		WalletThread [] walletThreads = new 
				WalletThread[nThreads];
		
		for (int i = 0; i < nThreads; i++) {
			walletThreads[i] = 
					new WalletThread(wallet);
			walletThreads[i].start();
		}
		
		
		for (int i = 0; i < nThreads; i++) {
			try {
				walletThreads[i].join();
			} catch (InterruptedException ie) {
				// TODO: handle exception
			}
		}
		
		System.out.println("Valor Esperado: " + correctResult);
		System.out.println("Valor Atual: " + wallet.getMoney());

	}

}
