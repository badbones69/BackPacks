package com.diamonddagger590.backpacks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ListHandler { 
	
	private static ListHandler instance = new ListHandler();//create the listhandler info

	public static ListHandler getInstance() {
		return instance;
	}
	
	Plugin p;
	
	FileConfiguration config;
	FileConfiguration playerFile;

	File configfile;
	File PlayerData;
	
	File UUIDFile;
	FileConfiguration uuidfile;
	
	File DisabledFile;
	FileConfiguration disabled;
	
	public void setup(Plugin p) {
		this.p = p;
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
		
		UUIDFile = new File(p.getDataFolder(), "UUIDFile.yml");
		if (!UUIDFile.exists()) {
			try{
        		File en = new File(p.getDataFolder(), "/UUIDFile.yml");
         		InputStream E = getClass().getResourceAsStream("/UUIDFile.yml");
         		copyFile(E, en);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
		uuidfile = YamlConfiguration.loadConfiguration(UUIDFile);
		
		DisabledFile = new File(p.getDataFolder(), "DisabledWorlds.yml");
		if (!UUIDFile.exists()) {
			try{
        		File en = new File(p.getDataFolder(), "/DisabledWorlds.yml");
         		InputStream E = getClass().getResourceAsStream("/DisabledWorlds.yml");
         		copyFile(E, en);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
		disabled = YamlConfiguration.loadConfiguration(DisabledFile);
		
		PlayerData = new File(p.getDataFolder() + "/PlayerData");
        if (!PlayerData.exists()) {
            PlayerData.mkdir();
        }
	}
	public FileConfiguration getConfig(){
		return config;
	}
	
	public FileConfiguration getUUIDFile(){
		return uuidfile;
	}
	
	public FileConfiguration getDisabledFile(){
		return disabled;
	}
	
	public FileConfiguration getFile(Player pl) {
		 File userdata = new File(p.getDataFolder(), File.separator + "PlayerData");
		 File f = new File(userdata, File.separator + pl.getUniqueId() + ".yml");
		 playerFile = YamlConfiguration.loadConfiguration(f);
		return playerFile;
	}
	public FileConfiguration getFile(UUID uuid) {
		 File userdata = new File(p.getDataFolder(), File.separator + "PlayerData");
		 File f = new File(userdata, File.separator + uuid + ".yml");
		 playerFile = YamlConfiguration.loadConfiguration(f);
		return playerFile;
	}
	
	public File getPlayerFile(Player pl){
		 File userdata = new File(p.getDataFolder(), File.separator + "PlayerData");
		 File f = new File(userdata, File.separator + pl.getUniqueId() + ".yml");
		return f;
	}
	
	public void savePlayerData(Player pl){
		try {
			 File userdata = new File(p.getDataFolder(), File.separator + "PlayerData");
			 File f = new File(userdata, File.separator + pl.getUniqueId() + ".yml");
			playerFile.save(f);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Could not save to the PlayerData folder!");
		}
	}
	
	public void savePlayerData(UUID uuid){
		try {
			 File userdata = new File(p.getDataFolder(), File.separator + "PlayerData");
			 File f = new File(userdata, File.separator + uuid + ".yml");
			playerFile.save(f);
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
	
	public void saveUUIDFile(){
		try {
			uuidfile.save(UUIDFile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Could not save UUIDFile.yml!");
		}
	}
	
	public void saveDisabled(){
		try {
			disabled.save(DisabledFile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Could not save DisabledWorlds.yml!");
		}
	}
	
	public void reloadConfig(){
		config = YamlConfiguration.loadConfiguration(configfile);
	}
	
	public void reloadUUIDFile(){
		uuidfile = YamlConfiguration.loadConfiguration(UUIDFile);
	}
	
	public void reloadDisabledFile(){
		disabled = YamlConfiguration.loadConfiguration(DisabledFile);
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