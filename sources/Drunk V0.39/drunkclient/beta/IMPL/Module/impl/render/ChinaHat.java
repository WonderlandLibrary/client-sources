/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender3D;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.UTILS.render.Render2DUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ChinaHat
extends Module {
    public ChinaHat() {
        super("ChinaHat", new String[0], Type.RENDER, "Giving player nice hat");
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        if (ChinaHat.mc.gameSettings.thirdPersonView == 0) return;
        int i = 0;
        while (i < 400) {
            ChinaHat.drawHat(Minecraft.thePlayer, 0.001 + (double)i * 0.0014, ChinaHat.mc.timer.elapsedPartialTicks, 50, 1.5f, 2.2f - (float)i * 7.85E-4f - 0.01f, new Color(162, 4, 4, 255).getRGB());
            ++i;
        }
    }

    public static void drawHat(Entity entity, double radius, float partialTicks, int points, float width, float yAdd, int color) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)width);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2929);
        GL11.glBegin((int)3);
        double x = Render2DUtils.interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        double y = Render2DUtils.interpolate(entity.prevPosY + (double)yAdd, entity.posY + (double)yAdd, partialTicks) - RenderManager.viewerPosY;
        double z = Render2DUtils.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;
        GL11.glColor4f((float)((float)new Color(color).getRed() / 255.0f), (float)((float)new Color(color).getGreen() / 255.0f), (float)((float)new Color(color).getBlue() / 255.0f), (float)0.15f);
        int i = 0;
        while (true) {
            if (i > points) {
                GL11.glEnd();
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
                GL11.glEnable((int)2929);
                GL11.glEnable((int)3553);
                GL11.glPopMatrix();
                return;
            }
            GL11.glVertex3d((double)(x + radius * Math.cos((double)i * Math.PI * 2.0 / (double)points)), (double)y, (double)(z + radius * Math.sin((double)i * Math.PI * 2.0 / (double)points)));
            ++i;
        }
    }
}

