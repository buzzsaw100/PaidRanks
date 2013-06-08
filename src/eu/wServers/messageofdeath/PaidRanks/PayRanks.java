package eu.wServers.messageofdeath.PaidRanks;

import eu.wServers.messageofdeath.PaidRanks.YamlDatabase;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PayRanks extends JavaPlugin {

	public static YamlDatabase english;
	public static YamlDatabase rankprices;
	public Log log;
	public static Vault vault;
	
	@Override
	public void onEnable() {
		log = new Log(this);
		vault = new Vault(this);
		english = new YamlDatabase(this, "English");
		rankprices = new YamlDatabase(this, "rankprices");
		english.onStartUp();
		rankprices.onStartUp();
		log.info("is now enabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(!(sender instanceof Player))return false;
		Player player = (Player) sender;
		rankprices.reload();
		if (cmd.getName().equalsIgnoreCase("rankup")) {
			if (args.length > 1) {
				player.sendMessage(Ranks.getInvalidArgs());
				return false;
			}
			if (args.length == 0) {
				if (Ranks.isDefaultEnabled()) {
					if (player.hasPermission(Ranks.getDefaultPermission()))
						rankUp(player, Ranks.getDefaultGroups(), null, false);
					else
						player.sendMessage(Ranks.getNoPermission() + " No permission for /rankup");
				} else {
					player.sendMessage(Ranks.getInvalidArgs() + " Default ladder not enabled in the rankprices.yml");
				}
			}
			if (args.length == 1) {
				if (Ranks.isLaddersEnabled()) {
					if(Ranks.doesLadderExist(args[0])) {
						if(player.hasPermission(Ranks.getLadderPermission(args[0]))) {
							rankUp(player, Ranks.getLadderGroups(args[0]), args[0], true);
						}else{
							player.sendMessage(Ranks.getNoPermission() + " No permission for /rankup " + args[0]);
						}
					}else{
						player.sendMessage(ChatColor.GOLD+ "Ladders: " + Ranks.getLadders().toString().replace("[", "").replace("]", ""));
					}
				} else {
					player.sendMessage(Ranks.getInvalidArgs() + " Ladders are not enabled in the rankprices.yml");
				}
			}
		}
		return false;
	}
	
	public void rankUp(Player player, ArrayList<String> groups, String ladderName, boolean ladder) {
		String rank = vault.getMainGroup(player);
		if(Ranks.isInGroupList(rank, groups)) {
			if(Ranks.getNextGroup(rank, groups) != null) {
				String rankx = Ranks.getNextGroup(rank, groups);
				String permission = Ranks.canRankUp(rankx, ladder == true ? ladderName : null);
				boolean canRank = permission == null;
				log.severe(permission);
				log.severe("" + canRank);
				double price = ladder == false ? Ranks.getDefaultPrice(rankx) : Ranks.getLadderPrice(ladderName, rankx);
				if(vault.hasAccount(player.getName())) {
					if(vault.hasEnough(player.getName(), price)) {
						if(canRank == true ? true : player.hasPermission(permission)) {
							vault.charge(player.getName(), price);
							vault.removeGroup(player.getLocation().getWorld(), player.getName(), rank);
							vault.setGroup(player.getLocation().getWorld(), player.getName(), rankx);
							player.sendMessage(Ranks.getRankupPlayer(rankx));
							getServer().broadcastMessage(Ranks.getRankupBroadcast(rankx, player.getName()));
							return;
						}else{
							player.sendMessage(Ranks.getNoPermission() + " No permission for the group " + rankx + (ladder == false ? " in the default ladder." : " in the ladder " + ladderName  + "."));
							return;
						}
					}else{
						player.sendMessage(Ranks.getNoMoney(String.valueOf(price), rankx));
						return;
					}
				}else{
					player.sendMessage(Ranks.getNoAccount());
					vault.newAccount(player.getName());
					return;
				}
			}else{
				player.sendMessage(Ranks.getHigh());
			}
		}else{
			player.sendMessage(Ranks.getNotInGroupList(Ranks.getDefaultGroups().get(0)));
		}
	}
}
