package com.diamonddagger590.backpacks;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


import net.milkbowl.vault.economy.Economy;




public class Main extends JavaPlugin{
	public Main instance = this;
	public File pluginFolder = instance.getDataFolder();
	public static ListHandler listHandler = ListHandler.getInstance();
	public static API api = API.getInstance();
	public static Economy economy = null;
	@Override
	//when server boots up
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoining(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		//setup list handler class
		listHandler.setup(this);
		setupEconomy();
	}
	
	public void onDisable(){
	}
	
	public static String color(String msg){
		  return ChatColor.translateAlternateColorCodes('&', msg);
		}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bp")){
			if(args.length == 0){
				Commands.BP((Player) sender, this);
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("upgrade")){
				Commands.BPUpgrade((Player) sender, this);
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("bp.*") || sender.hasPermission("bp.reload")){
					Main.listHandler.reloadConfig();
					Main.listHandler.reloadUUIDFile();
					sender.sendMessage(Main.color(Main.listHandler.getConfig().getString("Reloaded")));
					return true;
				}
				sender.sendMessage(Main.color(Main.listHandler.getConfig().getString("PluginPrefix") + Main.listHandler.getConfig().getString("NoPerms")));
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("help")){
				if(sender.hasPermission("bp.help")|| sender.hasPermission("bp.*")){
					sender.sendMessage(Main.color("&e--------------------------"));
					sender.sendMessage(Main.color("&7[&6BP&7]&3 /bp"));
					sender.sendMessage(Main.color("&3    -Opens your backpack"));
					sender.sendMessage(Main.color("&7[&6BP Upgrade&7]&3 /ce upgrade"));
					sender.sendMessage(Main.color("&3    -Upgrades your backpack to the next tier"));
					sender.sendMessage(Main.color("&7[&6BP See&7]&3 /bpsee [PlayerName]"));
					sender.sendMessage(Main.color("&3    -Allows you to look at or edit a players backpack based on perms"));
					sender.sendMessage(Main.color("&7[&6BP Reload&7]&3 /bp reload"));
					sender.sendMessage(Main.color("&3    -Reloads all files related to BP"));
					sender.sendMessage(Main.color("&e---------------------------"));
				}
			}
			
		}
		if(cmd.getName().equals("bpsee")){
			if(args.length == 1){
				Commands.BPsee((Player) sender, args[0], this);
				return true;
			}
		}
		return false;
	}
	
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}


