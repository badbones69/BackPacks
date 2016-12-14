package com.diamonddagger590.backpacks;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener{
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		World w = p.getWorld();
		String wname = w.getName();
		if(Main.listHandler.getDisabledFile().getString("DisabledWorlds." + p.getWorld().getName()).equals("true") && Main.listHandler.getConfig().getBoolean("Config.DeathDropsDisabledWorlds") == true){
			return;
		}
		if(p.hasPermission("bp.nodrop") || p.hasPermission("bp.noworlddrop." + wname) || p.hasPermission("bp.*")){
			return;
		}else{
			int backpackSize = Main.listHandler.getFile(p).getInt("Info.BackpackLevel");
			int backpackInventorySize = backpackSize * 9;
			for(int i = 0; i<backpackInventorySize; i++){
				int number = i + 1;
				if(Main.listHandler.getFile(p).contains("Info.BackPackSlot" + Integer.toString((number)))){
					ItemStack item = Main.listHandler.getFile(p).getItemStack("Info.BackPackSlot" + Integer.toString((number)));
					Location loc = p.getLocation();
					w.dropItemNaturally(loc, item);
					Main.listHandler.getFile(p).set("Info.BackPackSlot" + (number), null);
					Main.listHandler.savePlayerData(p);
				}
			}
			
			return;
		}
	}
	
}