package eu.wServers.messageofdeath.PaidRanks;

import org.bukkit.World;

public class Per {
	public String name;
	public String group;
	public String secondgroup;
	public World world;

	public Per(String name, World world, String group, String secondgroup) {
		this.group = group;
		this.name = name;
		this.world = world;
		this.secondgroup = secondgroup;
	}

	public boolean inGroup() {
		return Ranks.getPer().playerInGroup(this.world, this.name, this.group);
	}

	public void setGroup() {
		Ranks.getPer().playerAddGroup(this.world, this.name, this.group);
	}

	public void removeGroup() {
		Ranks.getPer().playerRemoveGroup(this.world, this.name,
				this.secondgroup);
	}
}