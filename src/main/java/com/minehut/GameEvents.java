package com.minehut;

import java.time.Duration;
import java.time.ZonedDateTime;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class GameEvents implements Listener {
	@EventHandler
    public void EntityDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player &&
				Template.getPlugin().gameInfoMap.containsKey(event.getEntity())) {
	        DamageCause cause = event.getCause();
	        Player player = (Player) event.getEntity();
	       
	        if(cause.equals(DamageCause.LAVA)) {
	        	GameInfo info = Template.getPlugin().gameInfoMap.remove(player);
	        	event.setCancelled(true);
	        	player.teleport(info.originalLocation);
	        	Duration diff = Duration.between(info.start, ZonedDateTime.now());
	        	String durString = String.format("%d minutes and %2d seconds", diff.getSeconds() / 60, (diff.getSeconds() % 60));
	            player.sendMessage("You went " + durString + " without falling!");
	        }
		}
    }
}
