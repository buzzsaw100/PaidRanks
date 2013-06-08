package eu.wServers.messageofdeath.PaidRanks;

import eu.wServers.messageofdeath.PaidRanks.YamlDatabase;

import java.util.ArrayList;

import org.bukkit.ChatColor;


public class Ranks {

	public static YamlDatabase getConfig() {
		return PayRanks.rankprices;
	}

	public static YamlDatabase getEnglish() {
		return PayRanks.english;
	}
	
	public static Vault getVault() {
		return PayRanks.vault;
	}
	
	//Both
	
	public static String canRankUp(String group, String ladder) {
		if(ladder == null)
			return getConfig().getString("DefaultPaidRanks." + group + ".Permission", null);
		else
			return getConfig().getString("NewLadders." + ladder + "." + group + ".Permission", null);
	}
	
	public static String getNextGroup(String currentRank, ArrayList<String> groups) {
		boolean stop = false;
		for(String ranks : groups) {
			if(stop == false) {
				if(ranks.equalsIgnoreCase(currentRank))
					stop = true;
				continue;
			}else{
				return ranks;
			}
		}
		return null;
	}
	
	public static boolean isInGroupList(String currentRank, ArrayList<String> groups) {
		for(String rank : groups) {
			if(rank.equalsIgnoreCase(currentRank)) {
				return true;
			}
		}
		return false;
	}
	
	// Default groups

	public static boolean isDefaultEnabled() {
		return getConfig().getBoolean("DefaultPaidRanks.Enabled", false);
	}
	
	public static String getDefaultPermission() {
		return getConfig().getString("DefaultPaidRanks.Permission", null);
	}
	
	public static double getDefaultPrice(String group) {
		return getConfig().getDouble("DefaultPaidRanks." + group + ".Price", 0);
	}
	
	public static ArrayList<String> getDefaultGroups() {
		ArrayList<String> ranks = new ArrayList<String>();
		for(String rank : getConfig().getSection("DefaultPaidRanks")) {
			if(rank.equalsIgnoreCase("Enabled") || rank.equalsIgnoreCase("Permission"))
				continue;
			ranks.add(rank);
		}
		return ranks;
	}
	
	//************************ Ladders ****************************

	public static boolean isLaddersEnabled() {
		return getConfig().getBoolean("NewLadders.Enabled", false);
	}
	
	public static ArrayList<String> getLadders() {
		ArrayList<String> ranks = new ArrayList<String>();
		for(String rank : getConfig().getSection("NewLadders")) {
			if(rank.equalsIgnoreCase("Enabled") || rank.equalsIgnoreCase("Permission"))
				continue;
			ranks.add(rank);
		}
		return ranks;
	}
	
	public static boolean doesLadderExist(String ladder) {
		for(String ladders : Ranks.getLadders()) {
			if(ladders.equalsIgnoreCase(ladder)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getLadderGroups(String ladder) {
		ArrayList<String> ranks = new ArrayList<String>();
		for(String rank : getConfig().getSection("NewLadders." + ladder)) {
			if(rank.equalsIgnoreCase("Enabled") || rank.equalsIgnoreCase("Permission"))
				continue;
			ranks.add(rank);
		}
		return ranks;
	}
	
	public static double getLadderPrice(String ladder, String group) {
		return getConfig().getDouble("NewLadders." + ladder + "." + group + ".Price", 0);
	}
	
	public static String getLadderPermission(String ladder) {
		return getConfig().getString("NewLadders." + ladder + ".Permission", null);
	}
	
	//************************ English File *************************

	public static String getNoPermission() {
		return ChatColor.translateAlternateColorCodes('&',getEnglish().getString("English.No_Permission", null));
	}
	
	public static String getNotInGroupList(String rank) {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.Not_In_Group_List", null).replace("+rank", rank));
	}

	public static String getInvalidArgs() {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.Invalid_Args", null));
	}

	public static String getNoAccount() {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.Does_Not_Have_An_Account", null));
	}

	public static String getHigh() {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.Highest_Rank_Possible", null));
	}

	public static String getRankupPlayer(String rank) {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.On_Rank_Up_Sent_To_Player", null)).replace("+rank", rank);
	}

	public static String getRankupBroadcast(String rank, String name) {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.On_Rank_Up_Broadcasting", null))
				.replace("+rank", rank).replace("+name", name);
	}

	public static String getNoMoney(String money, String rank) {
		return ChatColor.translateAlternateColorCodes('&', getEnglish().getString("English.Not_Enough_Money", null))
				.replace("+price", money).replace("+rank", rank);
	}
}