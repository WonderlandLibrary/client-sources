package net.silentclient.client.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.mods.settings.GeneralMod;

public class Watermark {
	public void render() {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int x = 0;
		int y = 0;
		switch (Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Silent Logo Location").getValString()) {
			case "Bottom Right Corner":
				x = scaledResolution.getScaledWidth() - 115;
				y = scaledResolution.getScaledHeight() - 23;
				break;
			case "Bottom Left Corner":
				x = 5;
				y = scaledResolution.getScaledHeight() - 23;
				break;
			case "Top Right Corner":
				x = scaledResolution.getScaledWidth() - 115;
				y = 3;
				break;
			case "Top Left Corner":
				x = 5;
				y = 3;
				break;
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("silentclient/logos/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 110, 21, 110.0F, 21.0F);

		versionRender();
	}

	public void versionRender() {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int x = 0;
		int y = 0;
		int width = Client.getInstance().getSilentFontRenderer().getStringWidth(Client.getInstance().getFullVersion(), 10, SilentFontRenderer.FontType.TITLE) + 5;
		switch (Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Silent Logo Location").getValString()) {
			case "Bottom Right Corner":
				x = 5;
				y = scaledResolution.getScaledHeight() - 23 + (21 / 2) - 5 + 3;
				break;
			case "Bottom Left Corner":
				x = scaledResolution.getScaledWidth() - width;
				y = scaledResolution.getScaledHeight() - 23 + (21 / 2) - 5 + 3;
				break;
			case "Top Right Corner":
				x = 5;
				y = 3 + (21 / 2) - 5 - 3;
				break;
			case "Top Left Corner":
				x = scaledResolution.getScaledWidth() - width;
				y = 3 + (21 / 2) - 5 - 3;
				break;
		}

		Client.getInstance().getSilentFontRenderer().drawString(Client.getInstance().getFullVersion(), x, y, 10, SilentFontRenderer.FontType.TITLE);
	}
	
	public void render(int x, int y) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("silentclient/logos/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 110, 21, 110.0F, 21.0F);
	}
}
