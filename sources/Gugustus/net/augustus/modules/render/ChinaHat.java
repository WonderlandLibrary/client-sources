package net.augustus.modules.render;

import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.lorious.MathUtils;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ChinaHat extends Module {
    public ChinaHat() {
        super("ChinaHat", Color.GREEN, Categorys.RENDER);
    }

    @EventTarget
    public void onRender3D(EventRender3D eventRender3D) {
        int clr = ColorUtils.getGradientOffset(new Color(255, 255, 255), ColorUtils.getRainbow(4.0f, 0.5f, 1.0f), (double)Math.abs(System.currentTimeMillis() / 16L) / 50.0 + 1.0 - 0.25).getRGB();
        if (ChinaHat.mc.gameSettings.thirdPersonView != 0) {
            for (int i = 0; i < 400; ++i) {
                ChinaHat.drawHat(ChinaHat.mc.thePlayer, 0.009 + (double)i * 0.0014, ChinaHat.mc.timer.elapsedPartialTicks, 12, 2.0f, 2.2f - (float)i * 7.85E-4f - 0.03f, clr);
            }
        }
    }
    public static void drawHat(Entity entity, double radius, float partialTicks, int points, float width, float yAdd, int color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2929);
        GL11.glBegin(3);
        double x = MathUtils.interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        double y = MathUtils.interpolate(entity.prevPosY + (double)yAdd, entity.posY + (double)yAdd, partialTicks) - RenderManager.viewerPosY;
        double z = MathUtils.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;
        GL11.glColor4f((float)new Color(color).getRed() / 255.0f, (float)new Color(color).getGreen() / 255.0f, (float)new Color(color).getBlue() / 255.0f, 0.15f);
        for (int i = 0; i <= points; ++i) {
            GL11.glVertex3d(x + radius * Math.cos((double)i * Math.PI * 2.0 / (double)points), y, z + radius * Math.sin((double)i * Math.PI * 2.0 / (double)points));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
}
