package com.diamonddagger590.backpacks;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoining implements Listener {
	
	private API api = API.getInstance();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		api.createPlayerFile(e.getPlayer());
		UUID uuid = e.getPlayer().getUniqueId();
		if(!Main.listHandler.getUUIDFile().contains(uuid.toString())){
			Main.listHandler.getUUIDFile().set(uuid.toString(), e.getPlayer().getName());
			Main.listHandler.saveUUIDFile();
		}
	}

}