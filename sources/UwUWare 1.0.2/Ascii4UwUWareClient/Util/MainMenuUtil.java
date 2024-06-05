package Ascii4UwUWareClient.Util;

import Ascii4UwUWareClient.UI.Font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class MainMenuUtil {
	
	public static void drawString(final String text, final int xPos, final int yPos, final int color) {
		GlStateManager.pushMatrix();
		FontLoaders.Comfortaa80.drawCenteredString(text, xPos, yPos, color);
		GlStateManager.popMatrix();
	}

}
