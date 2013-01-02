package eu.wServers.messageofdeath.PaidRanks;

public class Eco {
	public String name;
	public double amount;

	public Eco(String name, double amount) {
		this.name = name;
		this.amount = amount;
	}

	public void charge() {
		Ranks.getEco().withdrawPlayer(this.name, this.amount);
	}

	public boolean hasEnough() {
		return Ranks.getEco().has(this.name, this.amount);
	}

	public boolean hasAccount() {
		return Ranks.getEco().hasAccount(this.name);
	}

	public void newAccount() {
		Ranks.getEco().createPlayerAccount(this.name);
	}

	public String getFormat() {
		return Ranks.getEco().format(this.amount);
	}
}