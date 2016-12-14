package com.diamonddagger590.backpacks;

import java.io.File;

import org.bukkit.entity.Player;


public class API {
	
	public static ListHandler listHandler = ListHandler.getInstance();
	private static API instance = new API();

	public static API getInstance() {
		return instance;
	}
	
	public void createPlayerFile(Player pl){
		File playerData = listHandler.getPlayerFile(pl);
		if(!playerData.exists()){
			try{
				listHandler.getFile(pl).set("Info.DisplayName", pl.getDisplayName());
				listHandler.savePlayerData(pl);
				listHandler.getFile(pl).set("Info.BackpackLevel", 1);
				listHandler.savePlayerData(pl);
         	}catch (Exception e) {
         		e.printStackTrace();
         	}
		}
	}

}