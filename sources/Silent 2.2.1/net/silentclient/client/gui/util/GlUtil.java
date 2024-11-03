package net.silentclient.client.gui.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class GlUtil {
	private static Minecraft mc = Minecraft.getMinecraft();

    public static void scissor(final float x, final float y, final float x1, final float y1) {
        final int scaleFactor = getScaleFactor();
        GL11.glScissor((int)(x * scaleFactor), (int)(mc.displayHeight - y1 * scaleFactor), (int)((x1 - x) * scaleFactor), (int)((y1 - y) * scaleFactor));
    }


    public static int getScaleFactor() {
        int scaleFactor = 1;
        final boolean isUnicode = mc.isUnicode();
        int guiScale = mc.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }

        while (scaleFactor < guiScale && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        return scaleFactor;
    }
}
