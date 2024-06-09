package us.loki.legit.modules.impl.Other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import us.loki.legit.modules.*;

public class Dab extends Module {

	int dabTimer;
	
	public Dab() {
		super("Dab","Dab", 0, Category.OTHER);
	}

	@Override
	public void onUpdate() {
		if (this.isEnabled()) {
		}
		super.onUpdate();
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
	public static EntityPlayerSP getPlayer() {
        getMinecraft();
        return Minecraft.thePlayer;
    }
	public static GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }

}
