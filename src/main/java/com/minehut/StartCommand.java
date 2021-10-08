package com.minehut;

import java.time.ZonedDateTime;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

	
	public static final Material BLOCK_TYPE = Material.OAK_LEAVES; // todo configuration?
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (!Template.getPlugin().pgInfoMap.containsKey(player)) {
				player.sendMessage("Please set positions before starting the minigame!");
				return false;
			}
			PersistantGameInfo pgInfo = Template.getPlugin().pgInfoMap.get(player);
			GameInfo gameInfo = new GameInfo(pgInfo, ZonedDateTime.now(), player.getLocation());
			Template.getPlugin().gameInfoMap.put(player, gameInfo);
			//gameInfo.originalLocation = player.getLocation();
			
			fill(pgInfo.topLeft, pgInfo.bottomRight);
			
			Location teleportLocation = new Location(player.getWorld(), 
					(pgInfo.topLeft.getX() + pgInfo.bottomRight.getX()) / 2,
					Math.max(pgInfo.topLeft.getY(), pgInfo.bottomRight.getY())+1,
					(pgInfo.topLeft.getZ() + pgInfo.bottomRight.getZ()) / 2);
			player.teleport(teleportLocation);
			player.sendMessage("Game started, Good luck!");
		}
		return true;
	}
	
	public void fill(Location topLeft, Location bottomRight) {
		assert(topLeft.getWorld() == bottomRight.getWorld());
		World world = topLeft.getWorld();
		
		// todo sort topLeft & bottomRight?
		for (long x = (long) Math.floor(Math.min(topLeft.getX(), bottomRight.getX())); 
				x <= (long) Math.floor(Math.max(topLeft.getX(), bottomRight.getX())); 
				x++) {
			for (long y = (long) Math.floor(Math.min(topLeft.getY(), bottomRight.getY())); 
					y <= (long) Math.floor(Math.max(topLeft.getY(), bottomRight.getY())); 
					y++) {
				for (long z = (long) Math.floor(Math.min(topLeft.getZ(), bottomRight.getZ())); 
						z <= (long) Math.floor(Math.max(topLeft.getZ(), bottomRight.getZ())); 
						z++) {
					new Location(world, x, y, z).getBlock().setType(BLOCK_TYPE);
				}
			}
		}
	}

}
