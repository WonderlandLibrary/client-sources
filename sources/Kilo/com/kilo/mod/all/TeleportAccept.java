package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.ChatUtil;
import com.kilo.util.Util;

public class TeleportAccept extends Module {
	
	public TeleportAccept(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Deny", "Deny all \"/tpa\" requests", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onChatReceive(String s) {
		if (ChatUtil.clearFormat(s).toLowerCase().contains("requested to teleport")) {
			if (Util.makeBoolean(getOptionValue("deny"))) {
				mc.thePlayer.sendChatMessage("/tpdeny");
			} else {
				mc.thePlayer.sendChatMessage("/tpaccept");
			}
		}
	}
}
