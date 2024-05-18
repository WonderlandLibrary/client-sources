package com.kilo.ui.inter.slotlist.part;

import com.kilo.render.TextureImage;
import com.kilo.util.ActivityType;
import com.kilo.util.Resources;

public class Activity {

	public String id;
	public ActivityType type;
	public String kiloName, minecraftName, ip, message;
	public TextureImage icon;
	
	public Activity(String id, ActivityType type, String iconURL, String kiloName, String minecraftName, String ip, String message) {
		this.id = id;
		this.type = type;
		this.icon = Resources.downloadTexture(iconURL);
		this.kiloName = kiloName;
		this.minecraftName = minecraftName;
		this.ip = ip;
		this.message = message;
		
	}
}