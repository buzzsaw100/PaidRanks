package eu.wServers.messageofdeath.PaidRanks;

import org.bukkit.World;

public class Vault {
	public Eco getEconomy(String name, double amount) {
		return new Eco(name, amount);
	}

	public Per getPermission(String name, World world, String rank,
			String secondrank) {
		return new Per(name, world, rank, secondrank);
	}
}