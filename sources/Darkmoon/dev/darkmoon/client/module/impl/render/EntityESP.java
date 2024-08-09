package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.jhlabs.vecmath.Vector4f;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleAnnotation(name = "EntityESP", category = Category.RENDER)
public class EntityESP extends Module {
    public final List<Entity> collectedEntities;
    private final IntBuffer viewport;
    private final FloatBuffer modelview;
    private final FloatBuffer projection;
    private final FloatBuffer vector;

    public MultiBooleanSetting elements = new MultiBooleanSetting("Elements", Arrays.asList("Box", "Name", "Item", "Health"));

    public EntityESP() {
        this.collectedEntities = new ArrayList<>();
        this.viewport = GLAllocation.createDirectIntBuffer(16);
        this.modelview = GLAllocation.createDirectFloatBuffer(16);
        this.projection = GLAllocation.createDirectFloatBuffer(16);
        this.vector = GLAllocation.createDirectFloatBuffer(4);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();

        final RenderManager renderMng = mc.getRenderManager();
        final EntityRenderer entityRenderer = mc.entityRenderer;

        for (EntityPlayer entity : mc.world.playerEntities) {
            if (mc.player == entity && mc.gameSettings.thirdPersonView == 0) continue;
            if (entity.isDead) continue;

            if (RenderUtility.isInViewFrustum(entity)) {
                DarkMoon.getInstance().getScaleMath().pushScale();
                double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks());
                double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks());
                double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks());
                double width = entity.width / 1.5;
                double height = entity.height + 0.2f - (entity.isSneaking() ? 0.2f : 0.0f);

                final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                final Vec3d[] vectors = {new Vec3d(aabb.minX, aabb.minY, aabb.minZ), new Vec3d(aabb.minX, aabb.maxY, aabb.minZ), new Vec3d(aabb.maxX, aabb.minY, aabb.minZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vec3d(aabb.minX, aabb.minY, aabb.maxZ), new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);

                Vector4f position = null;
                for (Vec3d vector : vectors) {
                    vector = RenderUtility.project2D(2, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                    if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4f((float) vector.x, (float) vector.y, (float) vector.z, 1.0f);
                        }
                        position.x = (float) Math.min(vector.x, position.x);
                        position.y = (float) Math.min(vector.y, position.y);
                        position.z = (float) Math.max(vector.x, position.z);
                        position.w = (float) Math.max(vector.y, position.w);
                    }
                }

                if (position != null) {
                    mc.entityRenderer.setupOverlayRendering(2);
                    double posX = position.x;
                    double posY = position.y;
                    double endPosX = position.z;
                    double endPosY = position.w;

                    if (elements.get(0)) {
                        RenderUtility.drawRectNoWH(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, Color.black.getRGB());
                        RenderUtility.drawRectNoWH(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, Color.black.getRGB());
                        RenderUtility.drawRectNoWH(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, Color.black.getRGB());
                        RenderUtility.drawRectNoWH(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, Color.black.getRGB());
                        RenderUtility.drawRectNoWH(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, -1);
                        RenderUtility.drawRectNoWH(posX, endPosY - 0.5D, endPosX, endPosY, -1);
                        RenderUtility.drawRectNoWH(posX - 0.5D, posY, endPosX, posY + 0.5D, -1);
                        RenderUtility.drawRectNoWH(endPosX - 0.5D, posY, endPosX, endPosY, -1);
                    }


                    final double hpPercentage = entity.getHealth() / entity.getMaxHealth();
                    final double hpHeight2 = (endPosY - posY) * hpPercentage;
                    final double hpHeight3 = (endPosY - posY);
                    double dif = (endPosX - posX) / 2.0D;
                    double textWidth = (Fonts.nunito12.getStringWidth(entity.getName()));

                    if (elements.get(1)) {
                        Fonts.nunito12.drawStringWithOutline(ChatFormatting.stripFormatting(entity.getName()), (float) ((posX + dif - textWidth / 2.0D)) - 1, (float) (posY) - 20 + 15, -1);
                    }

                     if (elements.get(3)) {
                        RenderUtility.drawRectNoWH((float) (posX - 3.5), (float) (endPosY + 0.5), (float) (posX - 1.5), (float) (endPosY - hpHeight3 - 0.5), new Color(0, 0, 0, 255).getRGB());
                        RenderUtility.drawGradientGlow((float) (posX - 3.0), (float) (posY), 1, (float) hpHeight3, 4, new Color(color2), new Color(color2), new Color(color), new Color(color));
                        RenderUtility.drawVGradientRect((float) (posX - 3.0), (float) (endPosY), (float) (posX - 2.0), (float) (endPosY - hpHeight3), color, color2);
                        RenderUtility.drawRectNoWH(posX - 3.5F, posY, posX - 1.5, (endPosY - hpHeight2), new Color(0, 0, 0, 255).getRGB());
                     }

                    if (!entity.getHeldItemMainhand().isEmpty()) {
                        if (elements.get(2))
                            Fonts.nunito12.drawCenteredStringWithOutline(ChatFormatting.stripFormatting(entity.getHeldItemMainhand().getDisplayName()), (float) (posX + (endPosX - posX) / 2.0D), (float) (endPosY + 0.5D) + 4, -1);
                    }

                    mc.entityRenderer.setupOverlayRendering();
                }
                DarkMoon.getInstance().getScaleMath().popScale();
            }
        }
    }

    private boolean isValid(final Entity entity) {
        if (entity == mc.player && mc.gameSettings.thirdPersonView == 0) {
            return false;
        }
        return !entity.isDead;
    }

    private void collectEntities() {
        this.collectedEntities.clear();
        for (final EntityPlayer entity : mc.world.playerEntities) {
            if (this.isValid(entity)) {
                this.collectedEntities.add(entity);
            }
        }
    }

    private Vec3d project2D(final int scaleFactor, final double x, final double y, final double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        if (GLU.gluProject((float) x, (float) y, (float) z, this.modelview, this.projection, this.viewport, this.vector)) {
            return new Vec3d(this.vector.get(0) / scaleFactor, (Display.getHeight() - this.vector.get(1)) / scaleFactor, this.vector.get(2));
        }
        return null;
    }

    public static int getHealthColor(final EntityLivingBase entity, final int c1, final int c2) {
        final float health = entity.getHealth();
        final float maxHealth = entity.getMaxHealth();
        final float hpPercentage = health / maxHealth;
        final int red = (int) ((c2 >> 16 & 0xFF) * hpPercentage + (c1 >> 16 & 0xFF) * (1.0f - hpPercentage));
        final int green = (int) ((c2 >> 8 & 0xFF) * hpPercentage + (c1 >> 8 & 0xFF) * (1.0f - hpPercentage));
        final int blue = (int) ((c2 & 0xFF) * hpPercentage + (c1 & 0xFF) * (1.0f - hpPercentage));
        return new Color(red, green, blue).getRGB();
    }
}
