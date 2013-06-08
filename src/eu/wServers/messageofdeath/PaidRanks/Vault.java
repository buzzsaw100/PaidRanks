package eu.wServers.messageofdeath.PaidRanks;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Vault {
	
	private Economy economy;
	private Permission permission;
	private JavaPlugin plugin;
	
	@SuppressWarnings("rawtypes")
	private Boolean setupEconomy() {
		RegisteredServiceProvider economyProvider = plugin.getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		if (economy != null)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}
	
	@SuppressWarnings("rawtypes")
	private Boolean setupPermission() {
		RegisteredServiceProvider permissionProvider = plugin.getServer()
				.getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		if (permission != null)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}
	
	public Vault(JavaPlugin plugin) {
		this.plugin = plugin;
		setupEconomy();
		setupPermission();
	}
	
	//************************** Economy *********************************
	
	public void charge(String name, double amount) {
		economy.withdrawPlayer(name, amount);
	}

	public boolean hasEnough(String name, double amount) {
		return economy.has(name, amount);
	}

	public boolean hasAccount(String name) {
		return economy.hasAccount(name);
	}

	public void newAccount(String name) {
		economy.createPlayerAccount(name);
	}

	public String getFormat(double amount) {
		return economy.format(amount);
	}
	
	//************************** Permissions *********************************

	public boolean inGroup(World world, String name, String group) {
		return permission.playerInGroup(world, name, group);
	}

	public void setGroup(World world, String name, String group) {
		permission.playerAddGroup(world, name, group);
	}
	
	public String getMainGroup(Player player) {
		int i = permission.getPlayerGroups(player).length - 1;
		return permission.getPlayerGroups(player)[i];
	}
	
	public String getGroup(Player player, int group) {
		return permission.getPlayerGroups(player)[group];
	}
	
	public int amountOfGroups(Player player) {
		return permission.getPlayerGroups(player).length;
	}

	public void removeGroup(World world, String name, String group) {
		permission.playerRemoveGroup(world, name, group);
	}
}