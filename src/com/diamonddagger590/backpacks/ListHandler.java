package com.diamonddagger590.backpacks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ListHandler { 
	//create the listhandler info
	static ListHandler instance = new ListHandler();

	public static ListHandler getInstance() {
		return instance;
	}
	
	Plugin p;
	FileConfiguration config;
	FileConfiguration playerFile;

	File configfile;
	File PlayerData;
	
	public void setup(Plugin p) {
		//create a datafolder if it doesnt exist
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		configfile = new File(p.getDataFolder(), "config.yml");
		if (!configfile.exists()) {
			try{
        		File en = new File(p.getDataFolder(), "/config.yml");
         		InputStream E = getClass().getResourceAsStream("/config.yml");
         		copyFile(E, en);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
		config = YamlConfiguration.loadConfiguration(configfile);
		
		PlayerData = new File(p.getDataFolder() + "/PlayerData");
        if (!PlayerData.exists()) {
            PlayerData.mkdir();
        }
	}
	public FileConfiguration getConfig(){
		return config;
	}
	public FileConfiguration getPlayerData(Player p){
		File getplayerFile = new File(PlayerData + "/" + p.getUniqueId() + ".yml");
		return YamlConfiguration.loadConfiguration(getplayerFile);
	}
	public File getPlayerFile(Player p){
		File getplayerFile = new File(PlayerData + "/" + p.getUniqueId() + ".yml");
		return getplayerFile;
	}
	public void savePlayerData(Player p){
		try {
			File saveplayerFile = new File(PlayerData + "/" + p.getUniqueId() + ".yml");
			playerFile.save(saveplayerFile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Could not save to the PlayerData folder!");
		}
	}
	public void saveConfig(){
		try {
			config.save(configfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Could not save config.yml!");
		}
	}
	
	public static void copyFile(InputStream in, File out) throws Exception { // https://bukkit.org/threads/extracting-file-from-jar.16962/
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
	
	

}
