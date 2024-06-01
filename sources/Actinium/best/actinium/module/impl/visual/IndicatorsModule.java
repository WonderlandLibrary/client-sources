package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.render.ColorUtil;
import best.actinium.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import static net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.util.Comparator;

@ModuleInfo(
        name = "Indicators",
        description = "Insane",
        category = ModuleCategory.VISUAL
)
public class IndicatorsModule extends Module {
    private float distance = 0f;
    private String displayName;

    public double getRotations(double eX, double eZ, double x, double z) {
        double xDiff = eX - x;
        double zDiff = eZ - z;
        double yaw = -(atan2(xDiff, zDiff) * 57.29577951308232);
        return yaw;
    }
    
    @Callback
    public void onRender2d(Render2DEvent event) {
        FontRenderer font = mc.fontRendererObj;
        ScaledResolution t = new ScaledResolution(mc);
        for (Entity entity : mc.theWorld.loadedEntityList) {
            String name = entity.getName();
            if (name.equals("Fireball")) {
                distance = (float) floor(mc.thePlayer.getDistanceToEntity(entity));
                displayName = name;

                float scale = 1f;
                double entX = entity.posX;
                double entZ = entity.posZ;
                double px = mc.thePlayer.posX;
                double pz = mc.thePlayer.posZ;
                float pYaw = mc.thePlayer.rotationYaw;
                float radius = 150f;
                double yaw = Math.toRadians(getRotations(entX, entZ, px, pz) - pYaw);
                double textX = t.getScaledWidth() / 2 + (radius - 13) * sin(yaw);
                double textY = t.getScaledHeight() / 2 - (radius - 13) * cos(yaw);
                double imgX = (t.getScaledWidth() / 2) + (radius - 18) * sin(yaw);
                double imgY = (t.getScaledHeight() / 2) - (radius - 18) * cos(yaw);
                GlStateManager.color(255f, 255f, 255f, 255f);

                if (displayName.equals("Fireball")) {
                    GlStateManager.scale(scale, scale, scale);
                    RenderUtil.image(
                            new ResourceLocation("textures/items/fireball.png"),
                            (float) (imgX / scale - 5),
                            (float) (imgY / scale - 5),
                            32.0F,
                            32.0F,
                            ColorUtil.withAlpha(Color.white,255)
                    );
                    GlStateManager.scale(1 / scale, 1 / scale, 1 / scale);
                }

                GlStateManager.scale(scale, scale, scale);
                font.drawStringWithShadow(
                        distance + "m",
                        (float) (textX / scale - (font.getStringWidth(distance + "m") / 2)),
                        (float) (textY / scale - 4),
                        -1
                );
                GlStateManager.scale(1 / scale, 1 / scale, 1 / scale);
            }
        }
    }
}
