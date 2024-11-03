package net.silentclient.client.mods.hud;

import net.minecraft.client.Minecraft;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class FPSMod extends HudMod {
	
	public FPSMod() {
		super("FPS", ModCategory.MODS, "silentclient/icons/mods/fps.png");
	}
	
	@Override
	public String getText() {
		return "0000 " + getPostText();
	}
	
	@Override
	public String getTextForRender() {
		return Minecraft.getDebugFPS() + " " + getPostText();
	}
	
	@Override
	public String getDefautPostText() {
		return "FPS";
	}
}
