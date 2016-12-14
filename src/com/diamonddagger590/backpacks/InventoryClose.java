package com.diamonddagger590.backpacks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClose implements Listener {
	
	
	@SuppressWarnings("deprecation")
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
					Main.listHandler.getFile(p).set("Info.BackPackSlot" + (i + 1), item);
					Main.listHandler.savePlayerData(p);
				}else{
					Main.listHandler.getFile(p).set("Info.BackPackSlot" + (i + 1), null);
					Main.listHandler.savePlayerData(p);
					continue;
				}
			}
			return;
		}
		String title2 = inv.getTitle();
		String s = title2;
		int length = title2.length();
		String ss[] = s.split(" ", length);
		if(ss.length < 3){
			return;
		}
		title2 = ss[0] + " " + ss[1] + " ";
		UUID uuid;
		if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(ss[2]))){
			uuid = Bukkit.getPlayer(ss[2]).getUniqueId();
		}else{
			uuid = Bukkit.getOfflinePlayer(ss[2]).getUniqueId();
		}
		if(title2.equals(Main.color(Main.listHandler.getConfig().getString("BackPackEditingTitle").replaceAll("%Player%", "")))){
			int size = inv.getSize();
			for(int i = 0; i < size; i++){
				ItemStack item = inv.getItem(i);
				if(!(item == null)){
					Main.listHandler.getFile(uuid).set("Info.BackPackSlot" + (i + 1), item);
					Main.listHandler.savePlayerData(uuid);
				}else{
					Main.listHandler.getFile(uuid).set("Info.BackPackSlot" + (i + 1), null);
					Main.listHandler.savePlayerData(uuid);
					continue;
				}
			}
			return;
		}
		return;
	}
	
}