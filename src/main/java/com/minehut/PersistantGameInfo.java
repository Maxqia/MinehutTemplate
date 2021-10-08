package com.minehut;

import java.util.UUID;

import org.bukkit.Location;

// to be stored somewhere permanent
public class PersistantGameInfo {
	UUID playerID;
	Location topLeft;
	Location bottomRight;
	
	public PersistantGameInfo(UUID playerID) {
		super();
		this.playerID = playerID;
	}
}
