package com.diamonddagger590.backpacks;

import java.io.File;
import java.io.InputStream;

import org.bukkit.entity.Player;


public class API {
	public static ListHandler listHandler = ListHandler.getInstance();
	static API instance = new API();

	public static API getInstance() {
		return instance;
	}
	
	public void createPlayerFile(Player p){
		File playerData = listHandler.getPlayerFile(p);
		if(!playerData.exists()){
			try{
        		File en = new File(playerData, "/" + p.getUniqueId() + ".yml");
         		InputStream E = getClass().getResourceAsStream("/" + p.getUniqueId() + ".yml");
         		ListHandler.copyFile(E, en);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
		listHandler.getPlayerData(p).set("DisplayName", p.getDisplayName());
		listHandler.getPlayerData(p).set("BackpackLevel", "1");
		listHandler.savePlayerData(p);
	}


}
