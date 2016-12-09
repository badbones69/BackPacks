package com.diamonddagger590.backpacks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClickEvent implements Listener{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		String title = inv.getTitle();
		String s = title;
		int length = title.length();
		String ss[] = s.split(" ", length);
		if(ss.length < 3){
			return;
		}
		title = ss[0] + " " + ss[1] + " ";
		if(title.equals(Main.color(Main.listHandler.getConfig().getString("BackPackViewingTitle").replaceAll("%Player%", "")))){
			e.setCancelled(true);
			return;
		}
	}
	

}
