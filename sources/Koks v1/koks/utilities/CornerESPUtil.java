package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 10:01
 */
public class CornerESPUtil {

    private final RenderUtils renderUtils = new RenderUtils();
    private final Minecraft mc = Minecraft.getMinecraft();

    public void drawCorners(double x, double y, double z, float xOffset, float yOffset, float length, float width) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScaled(-0.03, -0.03, -0.03);
        GL11.glRotated(mc.getRenderManager().playerViewY, 0, -1, 0);
        GlStateManager.disableDepth();

        Color color = new Color(0xFFFFFFFF);

        // Top-Left (SIDE)
        renderUtils.drawRect(7,  -xOffset, -yOffset, -xOffset + width, -yOffset + length, color);
        // Top-Left (TOP)
        renderUtils.drawRect(7,   -xOffset, -yOffset, -xOffset + length, -yOffset + width, color);

        // Top-Right (SIDE)
        renderUtils.drawRect(7,   xOffset, -yOffset, xOffset - width, -yOffset + length, color);
        // Top-Right (TOP)
        renderUtils.drawRect(7,   xOffset, -yOffset, xOffset - length, -yOffset + width, color);

        // Bottom-Left (SIDE)
        renderUtils.drawRect(7,   -xOffset, yOffset, -xOffset + width, yOffset - length, color);
        // Bottom-Left (TOP)
        renderUtils.drawRect(7,   -xOffset, yOffset, -xOffset + length, yOffset - width, color);

        // Bottom-Right (SIDE)
        renderUtils.drawRect(7,   xOffset, yOffset, xOffset - width, yOffset - length, color);
        // Bottom-Right (TOP)
        renderUtils.drawRect(7,   xOffset, yOffset, xOffset - length, yOffset - width, color);

        GlStateManager.enableDepth();
        GL11.glPopMatrix();
    }

}