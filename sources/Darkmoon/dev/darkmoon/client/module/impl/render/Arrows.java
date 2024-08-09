package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.player.FreeCam;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "Arrows", category = Category.RENDER)
public class Arrows extends Module {
    public BooleanSetting glow = new BooleanSetting("Glowing", true);
    NumberSetting glowValue = new NumberSetting("Glow-Range", 5F, 5F, 15F, 1F, () -> glow.get());
    NumberSetting sizeValue = new NumberSetting("Size", 0.8F, 0.4F, 1F, 0.05F);
    NumberSetting rangeSizeValue = new NumberSetting("Range", 20F, 10F, 50F, 1F);
    BooleanSetting metres = new BooleanSetting("Metres", false);


    @EventTarget
    public void onRender2D(EventRender2D event) {
        ScaledResolution sr = new ScaledResolution(mc);
        float size = 50;
        float xOffset = sr.getScaledWidth() / 2F - 24.5F;
        float yOffset = sr.getScaledHeight() / 2F - 25.2F;
        mc.world.playerEntities.forEach(entity -> {
            Color col = DarkMoon.getInstance().getFriendManager().isFriend(entity.getName()) ? Color.GREEN : ColorUtility.getColor(90);

            if (isValid(entity)) {
                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
                double cos = Math.cos(mc.player.rotationYaw * (Math.PI * 2 / 360));
                double sin = Math.sin(mc.player.rotationYaw * (Math.PI * 2 / 360));
                double rotY = -(z * cos - x * sin);
                double rotX = -(x * cos + z * sin);
                Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
                Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
                float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);
                double xPos = ((rangeSizeValue.get()) * Math.cos(Math.toRadians(angle))) + xOffset + size / 2;
                double y = ((rangeSizeValue.get()) * Math.sin(Math.toRadians(angle))) + yOffset + size / 2;
                GL11.glPushMatrix();
                GlStateManager.translate(xPos - 6, y + 4, 0);
                if (metres.get()) {
                    Fonts.nunitoBold12.drawCenteredString(String.format("%.1f", mc.player.getDistanceSq(entity)) + "m", 8, 2, Color.WHITE.getRGB());
                }
                GL11.glPopMatrix();

                GlStateManager.translate(xPos, y, 0);

                GlStateManager.rotate(angle, 0, 0, 1);

                GlStateManager.disableBlend();
                GlStateManager.scale(sizeValue.get(), sizeValue.get(), sizeValue.get());
                RenderUtility.drawTriangleArrow(0 - 5F, 0F, 5F, 10F, col.brighter().getRGB(), col.darker().getRGB());
                if (glow.get()) {
                    RenderUtility.drawBlurredShadow(-3F, -5F, 12F, 10F, glowValue.getInt(), col);
                }
                GlStateManager.enableBlend();

                GlStateManager.popMatrix();
            }
        });
    }


    public boolean isValid(Entity entity) {
        return entity != mc.player;
    }
}
