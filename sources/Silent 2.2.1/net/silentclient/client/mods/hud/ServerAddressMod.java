package net.silentclient.client.mods.hud;

import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class ServerAddressMod extends HudMod {

	public ServerAddressMod() {
		super("Server Address", ModCategory.MODS, "silentclient/icons/mods/serveraddress.png", false);
	}
	
	@Override
	public String getText() {
		String ip;
		
		if(mc.getCurrentServerData() != null) {
			ip = mc.getCurrentServerData().serverIP;
		} else {
			ip = "SinglePlayer";
		}
		
		return ip;
	}
}
