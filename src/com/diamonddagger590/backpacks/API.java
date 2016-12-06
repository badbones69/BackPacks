package com.diamonddagger590.backpacks;

import java.io.File;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class API {
	public static ListHandler listHandler = ListHandler.getInstance();
	static API instance = new API();

	public static API getInstance() {
		return instance;
	}
	
	public void createPlayerFile(Player pl, Plugin p){
		File playerData = listHandler.getPlayerFile(pl, p);
		if(!playerData.exists()){
			try{
				listHandler.getFile(pl, p).set("Info.DisplayName", pl.getDisplayName());
				listHandler.savePlayerData(pl, p);
				listHandler.getFile(pl, p).set("Info.BackpackLevel", "1");
				listHandler.savePlayerData(pl, p);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
	}


}
