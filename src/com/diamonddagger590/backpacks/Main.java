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
		if(cmd.getName().equals("bp")){
			if(args.length == 0){
				Commands.BP((Player) sender, this);
				return true;
			}
			if(args.length == 1 && args[0].equals("upgrade")){
				Commands.BPUpgrade((Player) sender, this);
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


