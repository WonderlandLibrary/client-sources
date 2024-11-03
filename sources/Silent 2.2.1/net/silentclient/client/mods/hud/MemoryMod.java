package net.silentclient.client.mods.hud;

import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class MemoryMod extends HudMod {
	
	public MemoryMod() {
		super("Memory", ModCategory.MODS, "silentclient/icons/mods/memory.png");
	}
	
	@Override
	public String getText() {
		return getPostText() + ": 100%";
	}

	@Override
	public String getTextForRender() {
		long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
		return getPostText() + ": " + Long.valueOf(usedMemory * 100L / maxMemory) + "%";
	}

	@Override
	public String getDefautPostText() {
		return "Mem";
	}

}
