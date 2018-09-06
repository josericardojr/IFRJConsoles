
public class Wallet {

	private int money;
	
	public Wallet() {
		money = 0;
	}
	
	public int getMoney() { 
		return money;
	}
	
	public void addMoney(int m) {
		for (int i = 0; i < m; i++)
			money++;
	}
}
