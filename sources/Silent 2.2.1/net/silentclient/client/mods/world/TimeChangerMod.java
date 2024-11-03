package net.silentclient.client.mods.world;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class TimeChangerMod extends Mod {
	public TimeChangerMod() {
		super("Time Changer", ModCategory.MODS, "silentclient/icons/mods/timechanger.png");
	}
	
	@Override
	public void setup() {
		this.addSliderSetting("Time", this, 0, 0, 15000, true);
	}

}
