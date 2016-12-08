package com.diamonddagger590.backpacks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryClose implements Listener {
	Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("BackPacks");
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		String title = ChatColor.stripColor(inv.getTitle());
		if(title.equals((Main.color(Main.listHandler.getConfig().getString("BackPackTitle").replaceAll("%Player%", e.getPlayer().getName()))))){
			int size = inv.getSize();
			Player p = (Player) e.getPlayer();
			for(int i = 0; i < size; i++){
				ItemStack item = inv.getItem(i);
				if(!(item == null)){
					Main.listHandler.getFile(p, pl).set("Info.BackPackSlot" + (i + 1), item);
					Main.listHandler.savePlayerData(p, pl);
				}
				else{
					Main.listHandler.getFile(p, pl).set("Info.BackPackSlot" + (i + 1), null);
					Main.listHandler.savePlayerData(p, pl);
					continue;
				}
			}
		}
	}
}
