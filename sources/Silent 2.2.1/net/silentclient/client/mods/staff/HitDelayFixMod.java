package net.silentclient.client.mods.staff;

import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventClickMouse;
import net.silentclient.client.mixin.accessors.MinecraftAccessor;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class HitDelayFixMod extends Mod {
	public HitDelayFixMod() {
		super("Hit Delay Fix", ModCategory.MODS, null);
	}
	
	@EventTarget
	public void onMouseClick(EventClickMouse event) {
		if(event.getButton() == 0) {
			((MinecraftAccessor) mc).setLeftClickCounter(0);
		}
	}
}
