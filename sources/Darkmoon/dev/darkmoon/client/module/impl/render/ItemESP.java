package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.jhlabs.vecmath.Vector4f;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "ItemESP", category = Category.RENDER)
public class ItemESP extends Module {
    public BooleanSetting box = new BooleanSetting("Box", true);
    @EventTarget
    public void onDisplay(EventRender2D eventRender2D) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                if (RenderUtility.isInViewFrustum(entity)) {
                    double x = entity.lastTickPosX
                            + (entity.posX - entity.lastTickPosX) * mc.getRenderPartialTicks();
                    double y = entity.lastTickPosY
                            + (entity.posY - entity.lastTickPosY) * mc.getRenderPartialTicks();
                    double z = entity.lastTickPosZ
                            + (entity.posZ - entity.lastTickPosZ) * mc.getRenderPartialTicks();
                    double width = entity.width / 1.5;
                    double height = entity.height + 0.2F;
                    final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                    final Vec3d[] vectors = {new Vec3d(aabb.minX, aabb.minY, aabb.minZ), new Vec3d(aabb.minX, aabb.maxY, aabb.minZ), new Vec3d(aabb.maxX, aabb.minY, aabb.minZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vec3d(aabb.minX, aabb.minY, aabb.maxZ), new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ)};


                    mc.entityRenderer.setupCameraTransform(mc.getRenderPartialTicks(), 0);

                    ScaledResolution sr = eventRender2D.getResolution();
                    Vector4f position = null;
                    for (Vec3d vector : vectors) {
                        vector = RenderUtility.project2D(2,
                                vector.x - mc.getRenderManager().renderPosX,
                                vector.y - mc.getRenderManager().renderPosY,
                                vector.z - mc.getRenderManager().renderPosZ);
                        if (vector != null && vector.z > 0 && vector.z < 1) {

                            if (position == null) {
                                position = new Vector4f((float) vector.x, (float) vector.y, (float) vector.z, 0);
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
                        int build = -1;
                        int black = RenderUtility.rgba(25, 25, 25, 255);
                        int boxcolor = -1;
                        if (box.get()) {
                            //
                            RenderUtility.drawRect(posX, posY - 1f, endPosX, posY + 0.5f, black);
                            RenderUtility.drawRect(posX, posY - 0.5f, endPosX, posY, boxcolor);
                            //
                            RenderUtility.drawRect(posX - 0.5f, posY, posX + 1, endPosY, black);
                            RenderUtility.drawRect(posX, posY - 0.5f, posX + 0.5f, endPosY, boxcolor);
                            //
                            RenderUtility.drawRect(endPosX - 0.5f, posY, endPosX + 1, endPosY, black);
                            RenderUtility.drawRect(endPosX, posY - 0.5f, endPosX + 0.5f, endPosY, boxcolor);
                            //
                            RenderUtility.drawRect(posX + 0.5f, endPosY - 1f, endPosX, endPosY + 0.5f, black);
                            RenderUtility.drawRect(posX + 0.5f, endPosY - 0.5f, endPosX, endPosY, boxcolor);
                        }

                        String tagUpper = ((EntityItem) entity).getItem().getDisplayName()
                                + (((EntityItem) entity).getItem().stackSize < 1 ? ""
                                : " x" + ((EntityItem) entity).getItem().stackSize);
                        String tag = tagUpper.toLowerCase();
                        RenderUtility.drawRoundedRect((float) ((posX + ((endPosX - posX) / 2) - Fonts.comfortaa12.getStringWidth(tag) / 2)), (float) (posY - 12),
                                Fonts.comfortaa12.getStringWidth(tag), Fonts.comfortaa12.getStringHeight(tag) + 5, 5,
                                new Color(21, 20, 22, 195).getRGB());
                        Fonts.nunitoBold12.drawString(tag,
                                (float) ((posX + ((endPosX - posX) / 2)
                                        - Fonts.nunitoBold12.getStringWidth(tag) / 2)),
                                (float) (posY - 10), build);
                    }

                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GlStateManager.enableBlend();
                    mc.entityRenderer.setupOverlayRendering();
                }
            }
        }

    }
}
