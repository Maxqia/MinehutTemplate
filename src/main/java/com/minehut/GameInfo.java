package com.minehut;

import java.time.ZonedDateTime;

import org.bukkit.Location;

public class GameInfo {
	ZonedDateTime start; // todo timezones?
	Location originalLocation;
	PersistantGameInfo persistantGameInfo;


	public GameInfo(PersistantGameInfo persistantGameInfo, ZonedDateTime start, Location originalLocation) {
		super();
		this.start = start;
		this.originalLocation = originalLocation;
		this.persistantGameInfo = persistantGameInfo;
	}
}
