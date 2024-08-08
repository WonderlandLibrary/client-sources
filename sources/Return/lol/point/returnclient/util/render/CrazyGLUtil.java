package lol.point.returnclient.util.render;

import lol.point.returnclient.util.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CrazyGLUtil implements MinecraftInstance {

    public static void drawCircle(Entity entity, double radius) {
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glDepthMask(false);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GlStateManager.disableCull();

        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY) + Math.sin(System.currentTimeMillis() / 200.0) + 0.8;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;

        final Color color = new Color(255, 255, 255);
        final float colorRed = color.getRed() / 255.0F;
        final float colorGreen = color.getGreen() / 255.0F;
        final float colorBlue = color.getBlue() / 255.0F;

        for (int i = 0; i <= 64; i++) {
            final double angle = 2 * Math.PI * i / 64;
            final double vecX = x + radius * Math.cos(angle);
            final double vecZ = z + radius * Math.sin(angle);

            GL11.glColor4f(colorRed, colorGreen, colorBlue, 0);
            GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 200.0) / 2.0F, vecZ);
            GL11.glColor4f(colorRed, colorGreen, colorBlue, 0.85F);
            GL11.glVertex3d(vecX, y, vecZ);
        }

        GL11.glEnd();

        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.enableCull();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

}
