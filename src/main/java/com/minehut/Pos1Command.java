package com.minehut;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Pos1Command implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			PersistantGameInfo pgInfo = Template.getPlugin().createOrGetPGInfo(player);
			pgInfo.topLeft = player.getLocation();
			player.sendMessage("Set Pos1");
			Template.getPlugin().savePGInfo();
		}
		return true;
	}

}
