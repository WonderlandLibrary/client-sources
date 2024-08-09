package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.AntiAliasingUtility;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "ChinaHat", category = Category.RENDER)
public class ChinaHat extends Module {
    public BooleanSetting outline = new BooleanSetting("Outline", true);
    public NumberSetting lineWidth = new NumberSetting("Line Width", 1, 0.1f, 5, 0.1f, () -> outline.get());

    @EventTarget
    public void onRender(EventRender3D event) {
        if (mc.gameSettings.thirdPersonView == 0 || !mc.player.isEntityAlive()) return;

        double ix = - (mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks());
        double iy = - (mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks());
        double iz = - (mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks());

        float x = (float) (mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks());
        float y = (float) (mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks()) + mc.player.height + 0.01F - (mc.player.isSneaking() ? 0.2f : 0);
        float z = (float) (mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks());

        GlStateManager.pushMatrix();
        GL11.glDepthMask(false);
        GlStateManager.enableDepth();

        GL11.glRotatef(-(mc.player.rotationYaw), 0, 1, 0);

        mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 2);

        GlStateManager.translate(ix, iy, iz);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        AntiAliasingUtility.hook(true, false, false);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        {
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            Color c1 = Arraylist.getColor(1);
            GL11.glColor4f(c1.getRed() / 255f, c1.getGreen() / 255f, c1.getBlue() / 255f, 100 / 255f);
            GL11.glVertex3f(x, y + 0.35f, z);

            for (int i = 0; i <= 360;i++) {
                double x1 = Math.cos(i * Math.PI / 180) * 0.66;
                double z1 = Math.sin(i * Math.PI / 180) * 0.66;
                Color c = Arraylist.getColor(i);
                GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 100 / 255f);
                GL11.glVertex3d(x + x1, y, z + z1);
            }
            GL11.glEnd();
        }

        if (outline.get()) {
            {
                GL11.glLineWidth(lineWidth.get());
                GL11.glBegin(GL11.GL_LINE_LOOP);
                for (int i = 0; i <= 360; i++) {
                    double x1 = Math.cos(i * Math.PI / 180) * 0.66;
                    double z1 = Math.sin(i * Math.PI / 180) * 0.66;
                    Color c = Arraylist.getColor(i);
                    GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 1);
                    GL11.glVertex3d(x + x1, y, z + z1);
                }
                GL11.glEnd();
            }
        }
        AntiAliasingUtility.unhook(true, false, false);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glEnable(GL11.GL_CULL_FACE);
        mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
        GlStateManager.resetColor();
        GL11.glDepthMask(true);
        GlStateManager.popMatrix();
    }
}
