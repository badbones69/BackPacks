package com.diamonddagger590.backpacks;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin{
	public Main instance = this;
	public File pluginFolder = instance.getDataFolder();
	public static ListHandler listHandler = ListHandler.getInstance();
	public static API api = API.getInstance();
	@Override
	//when server boots up
	public void onEnable(){
		
		//setup list handler class
		listHandler.setup(this);
	}
	public void onDisable(){
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("save")){
			api.createPlayerFile((Player) sender, this);
			return true;
		}
		if(cmd.getName().equals("bp")){
			Commands.BP((Player) sender, this);
			return true;
		}
		return false;
	}
}


