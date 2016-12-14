package com.diamonddagger590.backpacks;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
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
	public static List<String> worlds = new ArrayList<String>();
	
	@Override
	public void onEnable(){ //when server boots up
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoining(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		listHandler.setup(this); //setup list handler class
		setupEconomy();
		for(World w : Bukkit.getServer().getWorlds()){
			if(!(Main.listHandler.getDisabledFile().contains("DisabledWorlds." + w.getName()))){
				Main.listHandler.getDisabledFile().set("DisabledWorlds." + w.getName(), "false");
				Main.listHandler.saveDisabled();
			}
			if(Main.listHandler.getDisabledFile().getString("DisabledWorlds." + w.getName()).equals("true")){
				worlds.add(w.getName());
			}else{
				continue;
			}
		}
	}
	
	public static String color(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bp")){}
			if(args.length == 0){
				if(!(sender instanceof Player)){
					sender.sendMessage("&cOnly Players can run these commands");
					return true;
				}
				Player p = (Player) sender;
				for(String w : worlds){
					String world = p.getWorld().getName();
					if(world.equals(w)){
						return true;
					}
					else{
						continue;
					}
				}
				Commands.BP(p, this);
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("upgrade")){
				if(!(sender instanceof Player)){
					sender.sendMessage("&cOnly Players can run these commands");
					return true;
				}
				Player p = (Player) sender;
				if(worlds.size() != 0){
					for(String w : worlds){
						String world = p.getWorld().getName();
						if(world.equals(w)){
							return true;
						}
						else{
							continue;
						}
					}
				}
				Commands.BPUpgrade(p, this);
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("bp.*") || sender.hasPermission("bp.reload")){
					Main.listHandler.reloadConfig();
					Main.listHandler.reloadUUIDFile();
					Main.listHandler.reloadDisabledFile();
					sender.sendMessage(Main.color(Main.listHandler.getConfig().getString("Reloaded")));
					worlds.clear();
					for(World w : Bukkit.getServer().getWorlds()){
						if(!(Main.listHandler.getDisabledFile().contains("DisabledWorlds." + w.getName()))){
								Main.listHandler.getDisabledFile().set("DisabledWorlds." + w.getName(), "false");
								Main.listHandler.saveDisabled();
						}
						if((Main.listHandler.getDisabledFile().getString("DisabledWorlds." + w.getName()).equals("true"))){
							worlds.add(w.getName());
						}
						else{
							continue;
						}
					}
					
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
					sender.sendMessage(Main.color("&7[&6BP Upgrade&7]&3 /bp upgrade"));
					sender.sendMessage(Main.color("&3    -Upgrades your backpack to the next tier"));
					sender.sendMessage(Main.color("&7[&6BP See&7]&3 /bpsee [PlayerName]"));
					sender.sendMessage(Main.color("&3    -Allows you to look at or edit a players backpack based on perms"));
					sender.sendMessage(Main.color("&7[&6BP Reload&7]&3 /bp reload"));
					sender.sendMessage(Main.color("&3    -Reloads all files related to BP"));
					sender.sendMessage(Main.color("&e---------------------------"));
				}
			}
		if(cmd.getName().equals("bpsee")){
			if(!(sender instanceof Player)){
				sender.sendMessage("&cOnly Players can run these commands");
				return true;
			}
			Player p = (Player) sender;
			if(worlds.size() != 0){
				for(String w : worlds){
					String world = p.getWorld().getName();
					if(world.equals(w)){
						return true;
					}
					else{
						continue;
					}
				}
			}
			if(args.length == 1){
				Commands.BPsee((Player) sender, args[0], this);
				return true;
			}
		}
		return false;
	}
	
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

}
