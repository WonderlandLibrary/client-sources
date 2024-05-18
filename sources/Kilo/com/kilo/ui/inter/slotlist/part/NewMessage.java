package com.kilo.ui.inter.slotlist.part;

import com.kilo.render.TextureImage;
import com.kilo.util.ActivityType;
import com.kilo.util.Resources;

public class NewMessage extends Activity{

	public NewMessage(String id, String iconLocation, String kiloName, String minecraftName, String ip, Message message) {
		super(id, ActivityType.new_message, iconLocation, kiloName, minecraftName, ip, message.message);
	}
}
