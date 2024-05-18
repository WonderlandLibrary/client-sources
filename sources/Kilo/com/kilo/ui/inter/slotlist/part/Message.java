package com.kilo.ui.inter.slotlist.part;

import com.kilo.manager.DatabaseManager;
import com.kilo.render.TextureImage;
import com.kilo.util.Resources;

public class Message {

	public String minecraftName, iconURL, message;
	public TextureImage icon;
	
	public Message(String minecraftName, String message, String iconURL) {
		this.minecraftName = minecraftName;
		this.message = message;
		this.iconURL = iconURL;
		
		this.icon = Resources.downloadTexture(iconURL);
	}
}
