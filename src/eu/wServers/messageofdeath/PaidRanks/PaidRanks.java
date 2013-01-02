package eu.wServers.messageofdeath.PaidRanks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.permission.plugins.Permission_GroupManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PaidRanks extends JavaPlugin {
	
	public static Permission_GroupManager Permissions = null;
	static boolean UsePermissions;
	public final Logger logger = Logger.getLogger("Minecraft");
	public File RankFile;
	public File EnglishFile;
	public static FileConfiguration Rank;
	public static FileConfiguration English;
	ArrayList<String> ladders = new ArrayList<String>();
	public int Hashlen;
	public static Economy economy = null;
	public static Permission permission = null;

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info("[" + pdfFile.getName() + "] v" + pdfFile.getVersion()
				+ " has been enabled.");
		this.RankFile = new File(getDataFolder(), "rankprices.yml");
		this.EnglishFile = new File(getDataFolder(), "English.yml");
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Rank = new YamlConfiguration();
		English = new YamlConfiguration();
		loadYamls();
		setupHash();
		setupEconomy();
		setupPermission();
	}
	
	public static String getGroup(Player player) {
		int i = permission.getPlayerGroups(player).length - 1;
		return permission.getPlayerGroups(player)[i];
	}

	@SuppressWarnings("rawtypes")
	private Boolean setupEconomy() {
		RegisteredServiceProvider economyProvider = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		if (economy != null)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}
	
	@SuppressWarnings("rawtypes")
	public Boolean setupPermission() {
		RegisteredServiceProvider permissionProvider = getServer()
				.getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		if (permission != null)
			return Boolean.valueOf(true);
		return Boolean.valueOf(false);
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info("[" + pdfFile.getName() + "] has been disabled.");
	}

	public void setupHash() {
		this.ladders = ((ArrayList<String>) Rank.getStringList("Ladders"));
	}

	public boolean lookupLadder(String ladder) {
		boolean d = false;
		int i = this.ladders.size() - 1;
		while (i != -1) {
			if (((String) this.ladders.get(i)).equalsIgnoreCase(ladder)) {
				d = true;
				i = 0;
			}
			i--;
		}
		return d;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(!(sender instanceof Player))return false;
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("rankup")) {
			if (args.length > 1) {
				player.sendMessage(getGroup(player));//Ranks.getInvalidArgs()
				return false;
			}
			if (args.length == 0) {
				if (Ranks.getPayRanks()) {
					if (player.hasPermission(Ranks.getDefaultPermission()))
						rankUp(player, null);
					else
						player.sendMessage(Ranks.getNoPermission());
				} else {
					player.sendMessage(Ranks.getInvalidArgs());
				}
			}
			if (args.length == 1) {
				if (Ranks.getLaddersBoolean()) {
					String ladder = args[0];
					if (lookupLadder(ladder)) {
						if (player.hasPermission(Ranks.getPermission(ladder)))
							rankUp(player, ladder);
						else
							player.sendMessage(Ranks.getNoPermission());
					} else
						player.sendMessage(ChatColor.GOLD
								+ "Ladders: "
								+ this.ladders.toString().replace("[", "")
										.replace("]", ""));
				} else {
					player.sendMessage(Ranks.getInvalidArgs());
				}
			}
		}
		return false;
	}
	
	public String rank;
	
	public boolean isNextGroup(String currentRank, ArrayList<String> groups) {
		boolean d = false;
		String rank = null;
		try {
			if(groups == null) {
				groups = Ranks.getGroups();
			}
			int i = groups.size() - 1;
			while(i > -1) {
				if(groups.get(i).equalsIgnoreCase(currentRank)) {
					d = true;
					rank = groups.get(i + 1);
					break;
				}
				i--;
			}
		}catch(Exception e) {
			d = false;
		}
		if(d == true) {
			this.rank = rank;
			return true;
		}
		return false;
	}
	
	public void rankUp(Player player, String ladder) {
		String rank = getGroup(player);
		ArrayList<String> d = null;
		if(ladder != null) {
			try {
				d = (ArrayList<String>)Rank.getStringList(ladder);
			}catch(NullPointerException e) {
				d = null;
			}
		}
		if(isNextGroup(rank, d)) {
			String rankx = this.rank;
			this.rank = null;
			Per per = new Per(player.getName(), player.getWorld(), rankx, rank);
			double price = d == null ? Ranks.getPrices(rankx) : Rank.getDouble("Prices." + ladder + "." + rankx);
			Eco eco = new Eco(player.getName(), price);
			if(eco.hasAccount()) {
				if(eco.hasEnough()) {
					eco.charge();
					per.removeGroup();
					per.setGroup();
					player.sendMessage(Ranks.getRankupPlayer(rankx));
					getServer().broadcastMessage(Ranks.getRankupBroadcast(rankx, player.getName()));
					return;
				}else{
					player.sendMessage(Ranks.getNoMoney(String.valueOf(price), rankx));
					return;
				}
			}else{
				player.sendMessage(Ranks.getNoAccount());
				eco.newAccount();
				return;
			}
		}else{
			player.sendMessage(Ranks.getHigh());
		}
	}
	
	public void saveYamls() {
		try {
			Rank.save(this.RankFile);
			English.save(this.EnglishFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadYamls() {
		try {
			Rank.load(this.RankFile);
			English.load(this.EnglishFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void firstRun() throws Exception {
		if (!this.RankFile.exists()) {
			this.RankFile.getParentFile().mkdirs();
			copy(getResource("rankprices.yml"), this.RankFile);
		}
		if (!this.EnglishFile.exists()) {
			this.EnglishFile.getParentFile().mkdirs();
			copy(getResource("English.yml"), this.EnglishFile);
		}
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
			saveYamls();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}