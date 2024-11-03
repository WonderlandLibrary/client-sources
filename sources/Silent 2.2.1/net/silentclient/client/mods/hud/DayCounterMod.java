package net.silentclient.client.mods.hud;

import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class DayCounterMod extends HudMod {
	public DayCounterMod() {
		super("Day Counter", ModCategory.MODS, "silentclient/icons/mods/daycounter.png");
	}
	
	@Override
	public String getText() {
		if(mc.theWorld != null && mc.theWorld.getWorldInfo() != null) {
			long time = mc.theWorld.getWorldInfo().getWorldTotalTime() / 24000L;
			
			return time + " " + getPostText();
		}else {
			return "0 " + getPostText();
		}
	}
	
	@Override
	public String getDefautPostText() {
		return "Days";
	}
}
