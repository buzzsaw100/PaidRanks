package eu.wServers.messageofdeath.PaidRanks;

import java.util.ArrayList;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;

public class Ranks {
	public static PaidRanks plugin;

	public Ranks(PaidRanks instance) {
		plugin = instance;
	}

	public static PaidRanks getPlugin() {
		return plugin;
	}

	public static FileConfiguration getEnglish() {
		return PaidRanks.English;
	}

	public static List<?> getLadders() {
		return getConfig().getStringList("Ladders");
	}

	public static FileConfiguration getConfig() {
		return PaidRanks.Rank;
	}

	public static Vault getVault() {
		return new Vault();
	}

	public static FileConfiguration getRank() {
		return PaidRanks.Rank;
	}

	public static Economy getEco() {
		return PaidRanks.economy;
	}

	public static Permission getPer() {
		return PaidRanks.permission;
	}

	public static boolean getPayRanks() {
		return getRank().getBoolean("Config.oldpayranks");
	}

	public static boolean getLaddersBoolean() {
		return getRank().getBoolean("Config.ladders");
	}

	public static ArrayList<String> getGroups() {
		return (ArrayList<String>) getRank().getStringList("OldPayRanks.groupslist");
	}

	public static double getPrices(String group) {
		return getRank().getDouble("OldPayRanks.Prices." + group);
	}

	public static String getDefaultPermission() {
		return getRank().getString("Config.permission");
	}

	public static String getNoPermission() {
		return getEnglish().getString("English.No_Permission").replaceAll(
				"(&([a-fk-or0-9]))", "§$2");
	}

	public static String getPermission(String ladder) {
		return getRank().getString("Permissions." + ladder);
	}

	public static String getInvalidArgs() {
		return getEnglish().getString("English.Invalid_Args").replaceAll(
				"(&([a-fk-or0-9]))", "§$2");
	}

	public static String getNoAccount() {
		return getEnglish().getString("English.Does_Not_Have_An_Account")
				.replaceAll("(&([a-fk-or0-9]))", "§$2");
	}

	public static String getHigh() {
		return getEnglish().getString("English.Highest_Rank_Possible")
				.replaceAll("(&([a-fk-or0-9]))", "§$2");
	}

	public static String getRankupPlayer(String rank) {
		return getEnglish().getString("English.On_Rank_Up_Sent_To_Player")
				.replaceAll("(&([a-fk-or0-9]))", "§$2").replace("+rank", rank);
	}

	public static String getRankupBroadcast(String rank, String name) {
		return getEnglish().getString("English.On_Rank_Up_Broadcasting")
				.replaceAll("(&([a-fk-or0-9]))", "§$2").replace("+rank", rank)
				.replace("+name", name);
	}

	public static String getNoMoney(String money, String rank) {
		return getEnglish().getString("English.Not_Enough_Money")
				.replaceAll("(&([a-fk-or0-9]))", "§$2")
				.replace("+price", money).replace("+rank", rank);
	}
}