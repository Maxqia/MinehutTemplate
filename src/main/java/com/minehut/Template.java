package com.minehut;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Template extends JavaPlugin {
	
	public static Template template = null;
	public static Template getPlugin() {
		return template;
	}

	HashMap<Player, GameInfo> gameInfoMap = new HashMap<Player, GameInfo>();
	HashMap<OfflinePlayer, PersistantGameInfo> pgInfoMap = new HashMap<OfflinePlayer, PersistantGameInfo>();
	
	public PersistantGameInfo createOrGetPGInfo(Player player) {
		if (!Template.getPlugin().pgInfoMap.containsKey(player)) {
			Template.getPlugin().pgInfoMap.put(player, new PersistantGameInfo(player.getUniqueId()));
			player.sendMessage("New pgInfo");
		}
		return Template.getPlugin().pgInfoMap.get(player);
	}
	
	public void loadPGInfo() {
		ConfigurationSection cs = this.getConfig().getConfigurationSection("pgInfo");
		if (cs == null) return;
		for (String key : cs.getKeys(false)) {
			// todo safety?
			ConfigurationSection csKey = cs.getConfigurationSection(key);
			csKey.set("topLeft.world", value);
			PersistantGameInfo pgInfo = (PersistantGameInfo) cs.getObject(key, PersistantGameInfo.class);
			pgInfoMap.put(Bukkit.getServer().getOfflinePlayer(pgInfo.playerID), pgInfo);
		}
	}
	
	public void savePGInfo() {
		ConfigurationSection cs = this.getConfig().createSection("pgInfo");
		for (Entry<OfflinePlayer, PersistantGameInfo> pgInfo : pgInfoMap.entrySet()) {
			ConfigurationSection csKey = cs.getConfigurationSection(key);
			csKey.set("topLeft.world", value);
			
			cs.set(pgInfo.getKey().getUniqueId().toString(), pgInfo.getValue());
		}

		this.saveConfig();
	}

	@Override
	public void onEnable() {
		template = this;
		// register commands
		this.getCommand("start").setExecutor(new StartCommand());
		this.getCommand("pos1").setExecutor(new Pos1Command());
		this.getCommand("pos2").setExecutor(new Pos2Command());

		// register event handler
		getServer().getPluginManager().registerEvents(new GameEvents(), this);
		System.out.println("Enabled Template Plugin");
		this.loadPGInfo();
	}
	
	@Override
	public void onDisable() {
		this.savePGInfo();
	}
}
