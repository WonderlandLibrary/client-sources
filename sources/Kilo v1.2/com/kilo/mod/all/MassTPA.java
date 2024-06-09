package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.users.Player;
import com.kilo.users.UserHandler;

public class MassTPA extends Module {
	
	private boolean sending = false;
	
	public MassTPA(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onEnable() {
		super.onEnable();
		if (!sending) {
			sending = true;
			new Thread() {
				@Override
				public void run() {
					for(Player p : UserHandler.players.values()) {
						if (!mc.thePlayer.getCommandSenderName().equalsIgnoreCase(p.gameProfile.getName())) {
							mc.thePlayer.sendChatMessage("/tpahere "+p.gameProfile.getName());
						}
						
						try { sleep(800); } catch (Exception e) {}
					}
					sending = false;
				}
			}.start();
		}
		
		onDisable();
	}
}
