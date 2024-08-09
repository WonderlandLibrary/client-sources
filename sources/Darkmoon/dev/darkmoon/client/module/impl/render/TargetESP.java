package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.jhlabs.vecmath.Vector4f;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.KillAura;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "Target ESP", category = Category.RENDER)
public class TargetESP extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Default", "Default", "Nova");
    public NumberSetting circleSpeed = new NumberSetting("Speed", 2, 1, 5, 0.1f, () -> mode.is("Default"));
    public static double prevCircleStep, circleStep;

    @EventTarget
    public void onRender(EventRender3D event) {
        if (!mode.get().equals("Default")) return;
        EntityLivingBase targetEntity = KillAura.targetEntity;
        if (targetEntity != null) {
            prevCircleStep = circleStep;
            circleStep += circleSpeed.get() * AnimationMath.deltaTime();

            float eyeHeight = targetEntity.getEyeHeight();
            if (targetEntity.isSneaking()) eyeHeight -= 0.2f;

            double cs = prevCircleStep + (circleStep - prevCircleStep) * mc.getRenderPartialTicks();
            double prevSinAnim = Math.abs(1 + Math.sin(cs - 0.5)) / 2;
            double sinAnim = Math.abs(1 + Math.sin(cs)) / 2;
            double x = targetEntity.lastTickPosX + (targetEntity.posX - targetEntity.lastTickPosX) * mc.getRenderPartialTicks()
                    - mc.getRenderManager().renderPosX;
            double y = targetEntity.lastTickPosY + (targetEntity.posY - targetEntity.lastTickPosY) * mc.getRenderPartialTicks()
                    - mc.getRenderManager().renderPosY + prevSinAnim * eyeHeight;
            double z = targetEntity.lastTickPosZ + (targetEntity.posZ - targetEntity.lastTickPosZ) * mc.getRenderPartialTicks()
                    - mc.getRenderManager().renderPosZ;
            double nextY = targetEntity.lastTickPosY
                    + (targetEntity.posY - targetEntity.lastTickPosY) * mc.getRenderPartialTicks()
                    - mc.getRenderManager().renderPosY + sinAnim * eyeHeight;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glBegin(GL11.GL_QUAD_STRIP);
            for (int i = 0; i <= 360; i++) {
                final Color color = Arraylist.getColor(i);
                GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.6F);
                GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * targetEntity.width * 0.8, nextY,
                        z + Math.sin(Math.toRadians(i)) * targetEntity.width * 0.8);
                GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.01F);
                GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * targetEntity.width * 0.8, y,
                        z + Math.sin(Math.toRadians(i)) * targetEntity.width * 0.8);
            }
            GL11.glEnd();
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glBegin(GL11.GL_LINE_LOOP);
            for (int i = 0; i <= 360; i++) {
                final Color color = Arraylist.getColor(i);
                GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.8F);
                GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * targetEntity.width * 0.8, nextY,
                        z + Math.sin(Math.toRadians(i)) * targetEntity.width * 0.8);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glPopMatrix();
            GlStateManager.resetColor();
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (!mode.get().equals("Nova")) return;

        EntityLivingBase target = KillAura.targetEntity;
        if (target != null) {
            if (RenderUtility.isInViewFrustum(KillAura.targetEntity)) {
                double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.getRenderPartialTicks();
                double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.getRenderPartialTicks();
                double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.getRenderPartialTicks();
                double width = target.width / 2.0F;
                double height = target.height / 1.5F;
                float size = 100 - mc.player.getDistance(target) * 5;

                Color color = ColorUtility.getColor(30);
                Color color2 = ColorUtility.getColor(180);

                final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y + height, z - width, x + width, y + height, z + width);
                final Vec3d[] vectors = {new Vec3d(aabb.minX, aabb.minY, aabb.minZ), new Vec3d(aabb.minX, aabb.maxY, aabb.minZ), new Vec3d(aabb.maxX, aabb.minY, aabb.minZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vec3d(aabb.minX, aabb.minY, aabb.maxZ), new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);

                Vector4f position = null;
                for (Vec3d vector : vectors) {
                    vector = RenderUtility.project2D(2, vector.x - mc.getRenderManager().viewerPosX, vector.y - mc.getRenderManager().viewerPosY, vector.z - mc.getRenderManager().viewerPosZ);
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
                    double center = posX + (endPosX - posX) / 2f;
                    double centerY = posY + (endPosY - posY) / 2f;

                    GL11.glPushMatrix();
                    GL11.glTranslated(center, centerY, 0);
                    GL11.glRotated(Math.sin(Math.toRadians((double) System.currentTimeMillis() / 15)) * 360, 0, 0, 1);
                    GL11.glTranslated(-center, -centerY, 0);
                    RenderUtility.applyGradientMask((float) (center - size / 2.0F), (float) (centerY - size / 2.0F), size, size, 0.8f, color, color2, color, color2, () -> {
                        RenderUtility.drawImage(new ResourceLocation("darkmoon/textures/target.png"), (float) (center - size / 2.0F), (float) (centerY - size / 2.0F), size, size);
                    });
                    GL11.glPopMatrix();

                    mc.entityRenderer.setupOverlayRendering();
                }
            }
        }
    }
}
