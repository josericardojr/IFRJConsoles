
public class WalletThread extends Thread {
	
	private Wallet wallet;
	
	public WalletThread(Wallet wallet) {
		this.wallet = wallet;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		wallet.addMoney(10000);
	}
}
