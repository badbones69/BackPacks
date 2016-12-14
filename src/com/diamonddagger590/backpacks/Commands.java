package com.diamonddagger590.backpacks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Commands {
	
	public static Player getPlayer(String target, Player targ){
		if(targ == null){
			targ = Bukkit.getPlayer(target).getPlayer();
			return targ;
		}
		return targ;
	}
	
	public static void BP(Player p){
		if(p.hasPermission("bp.open") || p.hasPermission("bp.*")){
			int backpackSize = Main.listHandler.getFile(p).getInt("Info.BackpackLevel");
			int backpackInventorySize = backpackSize * 9;
			Inventory inv = Bukkit.createInventory(null, backpackInventorySize, Main.color(Main.listHandler.getConfig().getString("BackPackTitle").replaceAll("%Player%", p.getName())));
			for(int i = 0; i<backpackInventorySize; i++){
				int number = i + 1;
				if(Main.listHandler.getFile(p).contains("Info.BackPackSlot" + (number))){
					ItemStack item = Main.listHandler.getFile(p).getItemStack("Info.BackPackSlot" + (number));
					inv.setItem(i, item);
				}
				else{
					continue;
				}
			}
			p.openInventory(inv);
			return;
		}
		else{
			p.sendMessage(Main.color(Main.listHandler.getConfig().getString("NoPerms")));
			return;
		}
	}
	
	public static void BPUpgrade(Player p){
		int maxSize = Main.listHandler.getConfig().getInt("Config.MaxBackPackLevel");
		FileConfiguration file = Main.listHandler.getFile(p);
		if(file.contains("Info.BackpackLevel")){
			int backpacklevel = file.getInt("Info.BackpackLevel");
			if(backpacklevel > maxSize){
				file.set("Info.BackpackLevel", maxSize);
				Main.listHandler.savePlayerData(p);
			}
			backpacklevel = backpacklevel + 1;
			if(p.hasPermission("bp.upgrade." + Integer.toString(backpacklevel)) || p.hasPermission("bp.*") || p.hasPermission("bp.upgrade.*")){
				double money = Main.economy.getBalance(p);
				double cost = Main.listHandler.getConfig().getDouble("Config.BackPackCost%i%".replaceAll("%i%", Integer.toString(backpacklevel)));
				if(money < cost){
					p.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix")) + Main.color(Main.listHandler.getConfig().getString("NotEnoughMoney1")));
					p.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix")) + Main.color(Main.listHandler.getConfig().getString("NotEnoughMoney2").replaceAll("%Money%", Double.toString(cost))));
					return;
				}
				else{
					file.set("Info.BackpackLevel", backpacklevel);
					Main.listHandler.savePlayerData(p);
					Main.economy.withdrawPlayer(p, cost);
					p.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix")) + Main.color(Main.listHandler.getConfig().getString("SuccessfulUpgrade").replaceAll("%SlotNumber%", Integer.toString(backpacklevel * 9))));
					return;
				}
			}
			else{
				p.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix")) + Main.listHandler.getConfig().getString("NoPerms"));
				return;
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void BPsee(Player p, String target){
		if(p.hasPermission("bpsee.edit") || p.hasPermission("bp.*")){
			UUID uuid;
			if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))){
				uuid = Bukkit.getOfflinePlayer(target).getUniqueId();
			}
			else{
				uuid = Bukkit.getOfflinePlayer(target).getUniqueId();
			}
			if(!Main.listHandler.getUUIDFile().contains(uuid.toString())){
				return;
			}
			String name = Main.listHandler.getUUIDFile().getString(uuid.toString());
			int backpackSize = Main.listHandler.getFile(uuid).getInt("Info.BackpackLevel");
			int backpackInventorySize = backpackSize * 9;
			Inventory inv = Bukkit.createInventory(null, backpackInventorySize, Main.color(Main.listHandler.getConfig().getString("BackPackEditingTitle").replaceAll("%Player%", name)));
			for(int i = 0; i<backpackInventorySize; i++){
				int number = i + 1;
				if(Main.listHandler.getFile(uuid).contains("Info.BackPackSlot" + (number))){
					ItemStack item = Main.listHandler.getFile(uuid).getItemStack("Info.BackPackSlot" + (number));
					inv.setItem(i, item);
				}
				else{
					continue;
				}
			}
			p.openInventory(inv);
			return;
		
		}
		if(p.hasPermission("bpsee.view") || p.hasPermission("bp.*")){
			UUID uuid;
			if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))){
				uuid = Bukkit.getOfflinePlayer(target).getUniqueId();
			}
			else{
				uuid = Bukkit.getOfflinePlayer(target).getUniqueId();
			}
			if(!Main.listHandler.getUUIDFile().contains(uuid.toString())){
				return;
			}
			String name = Main.listHandler.getUUIDFile().getString(uuid.toString());
			int backpackSize = Main.listHandler.getFile(uuid).getInt("Info.BackpackLevel");
			int backpackInventorySize = backpackSize * 9;
			Inventory inv = Bukkit.createInventory(null, backpackInventorySize, Main.color(Main.listHandler.getConfig().getString("BackPackViewingTitle").replaceAll("%Player%", name)));
			for(int i = 0; i<backpackInventorySize; i++){
				int number = i + 1;
				if(Main.listHandler.getFile(uuid).contains("Info.BackPackSlot" + (number))){
					ItemStack item = Main.listHandler.getFile(uuid).getItemStack("Info.BackPackSlot" + (number));
					inv.setItem(i, item);
				}
				else{
					continue;
				}
			}
			p.openInventory(inv);
			return;
		
		}
		else{
			p.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix") + Main.listHandler.getConfig().getString("NoPerms")));
			return;
		}
	}

}