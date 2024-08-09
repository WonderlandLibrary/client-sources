package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.AntiAliasingUtility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "Tracers", category = Category.RENDER)
public class Tracers extends Module {
    public NumberSetting lineWidth = new NumberSetting("Line Width", 1, 0.1f, 2.0f, 0.1f);
    public ColorSetting colorSetting = new ColorSetting("Color", Color.WHITE.getRGB());
    public ColorSetting friendColorSetting = new ColorSetting("Friend Color", Color.GREEN.getRGB());

    @EventTarget
    public void onRender(EventRender3D eventRender3D) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity != mc.player && entity instanceof EntityPlayer) {
                Color color = DarkMoon.getInstance().getFriendManager().isFriend(entity.getName()) ? friendColorSetting.getColor() : colorSetting.getColor();
                double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.getRenderPartialTicks() - mc.getRenderManager().renderPosX;
                double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.getRenderPartialTicks() - mc.getRenderManager().renderPosY;
                double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.getRenderPartialTicks() - mc.getRenderManager().renderPosZ;
                float red = color.getRed() / 255F;
                float green = color.getGreen() / 255F;
                float blue = color.getBlue() / 255F;
                float alpha = color.getAlpha() / 255F;
                GL11.glPushMatrix();
                boolean old = mc.gameSettings.viewBobbing;
                mc.gameSettings.viewBobbing = false;
                mc.entityRenderer.setupCameraTransform(eventRender3D.getPartialTicks(), 2);
                mc.gameSettings.viewBobbing = old;
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glLineWidth(lineWidth.get());
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                AntiAliasingUtility.hook(true, false, false);
                GL11.glDepthMask(false);
                GlStateManager.color(red, green, blue, alpha);
                GL11.glBegin(GL11.GL_LINE_STRIP);
                Vec3d vec = new Vec3d(0, 0, 1).rotatePitch((float) -(Math.toRadians(mc.player.rotationPitch))).rotateYaw((float) -Math.toRadians(mc.player.rotationYaw));
                GL11.glVertex3d(vec.x, vec.y + mc.player.getEyeHeight(), vec.z);
                GL11.glVertex3d(x, y, z);
                GL11.glEnd();
                AntiAliasingUtility.unhook(true, false, false);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
                GlStateManager.resetColor();
            }
        }
    }
}
