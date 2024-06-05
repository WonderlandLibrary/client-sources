package win.sightclient.script.values.classes;

import net.minecraft.client.Minecraft;

public class FontRendererMC {

	public int drawString(String text, float x, float y, int color, boolean dropShadow) {
		return Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, dropShadow);
	}
}
