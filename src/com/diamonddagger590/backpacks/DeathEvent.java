package com.diamonddagger590.backpacks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DeathEvent implements Listener{
	Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("BackPacks");
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		World w = p.getWorld();
		String wname = w.getName();
		if(p.hasPermission("bp.nodrop") || p.hasPermission("bp.noworlddrop." + wname) || p.hasPermission("bp.noworlddrop.*")){
			return;
		}
		else{
			int backpackSize = Main.listHandler.getFile(p, pl).getInt("Info.BackpackLevel");
			int backpackInventorySize = backpackSize * 9;
			for(int i = 0; i<backpackInventorySize; i++){
				int number = i + 1;
				if(Main.listHandler.getFile(p, pl).contains("Info.BackPackSlot" + Integer.toString((number)))){
					ItemStack item = Main.listHandler.getFile(p, pl).getItemStack("Info.BackPackSlot" + Integer.toString((number)));
					Location loc = p.getLocation();
					w.dropItemNaturally(loc, item);
					Main.listHandler.getFile(p, pl).set("Info.BackPackSlot" + (number), null);
					Main.listHandler.savePlayerData(p, pl);
				}
			}
			
			return;
		}
	}
}
