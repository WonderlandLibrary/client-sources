package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class Scissor {
    public static void start(int x, int y, int width, int height) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
                x * sr.getScaleFactor(),
                (sr.getScaledHeight() - y) * sr.getScaleFactor() - height * sr.getScaleFactor(),
                width * sr.getScaleFactor(),
                height * sr.getScaleFactor()
        );
    }

    public static void end() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
