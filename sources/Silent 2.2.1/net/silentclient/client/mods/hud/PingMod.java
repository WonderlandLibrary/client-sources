package net.silentclient.client.mods.hud;

import net.silentclient.client.Client;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class PingMod extends HudMod {
		
	public PingMod() {
		super("Ping", ModCategory.MODS, "silentclient/icons/mods/ping.png");
	}
	
	@Override
	public String getText() {
		return "100 " + getPostText();
	}
	
	@Override
	public String getTextForRender() {
		if (mc.getCurrentServerData() != null && !mc.isIntegratedServerRunning()) {
			return Client.getInstance().ping + " " + getPostText();
		} else {
	        return "0 " + getPostText();
	    }
	}
	
	@Override
	public String getDefautPostText() {
		return "ms";
	}
}
