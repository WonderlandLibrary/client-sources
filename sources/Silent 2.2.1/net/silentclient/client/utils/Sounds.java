package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.GeneralMod;

public class Sounds {
	public static void playButtonSound() {
		if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Silent Button Sounds").getValBoolean()) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}
}
