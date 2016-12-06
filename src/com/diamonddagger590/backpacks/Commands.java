package com.diamonddagger590.backpacks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Commands {
	
	public static void BP(Player p, Plugin pl){
		if(p.hasPermission("bp.open") || p.hasPermission("bp.*")){
			int backpackSize = Main.listHandler.getFile(p, pl).getInt("BackpackLevel");
			backpackSize = backpackSize * 9;
			Inventory inv = Bukkit.createInventory(null, backpackSize, "&eBackPack");
			for(int i = 0; i<backpackSize; i++){
				if(Main.listHandler.getFile(p, pl).contains("BackpackSlot" + Integer.toString(i + 1))){
					ItemStack item = Main.listHandler.getFile(p, pl).getItemStack("BackPackSlot" + Integer.toString(i + 1));
					inv.setItem(i, item);
				}
				else{
					continue;
				}
			}
			p.openInventory(inv);
		}
	}

}
