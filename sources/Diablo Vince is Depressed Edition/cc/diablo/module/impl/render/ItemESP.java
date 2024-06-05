/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  javax.vecmath.Vector3d
 *  javax.vecmath.Vector4d
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.Render3DEvent;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.render.ItemPhysics;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ItemESP
extends Module {
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private static final Frustum frustrum = new Frustum();

    public ItemESP() {
        super("ItemESP", "Renders a box on items that are dropped", 0, Category.Render);
    }

    @Subscribe
    public void onRender(Render3DEvent e) {
        for (Object object : Minecraft.theWorld.loadedEntityList) {
            Entity entity = (Entity)object;
            if (!(entity instanceof EntityItem)) continue;
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)ItemESP.mc.timer.renderPartialTicks;
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)ItemESP.mc.timer.renderPartialTicks;
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)ItemESP.mc.timer.renderPartialTicks;
            double finalWidth = (double)entity.width / 1.75;
            double finalHeight = (double)entity.height + 0.5;
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(posX - 0.15, ModuleManager.getModule(ItemPhysics.class).toggled ? posY + 0.35 : posY + 0.65, posZ - 0.225, posX + 0.15, ModuleManager.getModule(ItemPhysics.class).toggled ? posY - 0.15 : posY + 0.15, posZ + 0.225);
            List<Vector3d> vectorList = Arrays.asList(new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
            ItemESP.mc.entityRenderer.setupCameraTransform(ItemESP.mc.timer.renderPartialTicks, 0);
            Vector4d posVec = null;
            for (Vector3d vector : vectorList) {
                FloatBuffer otherVec = GLAllocation.createDirectFloatBuffer(4);
                GL11.glGetFloat((int)2982, (FloatBuffer)modelview);
                GL11.glGetFloat((int)2983, (FloatBuffer)projection);
                GL11.glGetInteger((int)2978, (IntBuffer)viewport);
                if (GLU.gluProject((float)((float)(vector.x - ItemESP.mc.getRenderManager().viewerPosX)), (float)((float)((double)((float)vector.y) - ItemESP.mc.getRenderManager().viewerPosY)), (float)((float)((double)((float)vector.z) - ItemESP.mc.getRenderManager().viewerPosZ)), (FloatBuffer)modelview, (FloatBuffer)projection, (IntBuffer)viewport, (FloatBuffer)otherVec)) {
                    vector = new Vector3d((double)(otherVec.get(0) / (float)new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor()), (double)(((float)Display.getHeight() - otherVec.get(1)) / (float)new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor()), (double)otherVec.get(2));
                }
                if (!(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                if (posVec == null) {
                    posVec = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                posVec.x = Math.min(vector.x, posVec.x);
                posVec.y = Math.min(vector.y, posVec.y);
                posVec.z = Math.max(vector.x, posVec.z);
                posVec.w = Math.max(vector.y, posVec.w);
            }
            ItemESP.mc.entityRenderer.setupOverlayRendering();
            if (posVec == null) continue;
            int color = -1;
            GL11.glPushMatrix();
            float x = (float)posVec.x;
            float w = (float)posVec.z - x;
            float y = (float)posVec.y;
            float h = (float)posVec.w - y;
            this.drawBorderedRect(x, y, w, h, 1.0, new Color(0, 0, 0).getRGB(), 0);
            this.drawBorderedRect(x, y, w, h, 0.5, new Color(-1, true).getRGB(), 0);
            GL11.glPopMatrix();
        }
    }

    public boolean isInViewFrustrum(Entity entity) {
        return this.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
        Gui.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
        Gui.drawRect(x, y + height, x + width, y + height - lineSize, borderColor);
    }

    public void drawBar(float x, float y, float width, float height, float max, float value, int color, int color2) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        float inc = height / max;
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        float incY = y + height - inc;
        int i = 0;
        while ((float)i < value) {
            this.drawBorderedRect(x + 0.25f, incY, width - 0.5f, inc, 0.25, new Color(color2, true).getRGB(), color);
            incY -= inc;
            ++i;
        }
    }

    private int getHealthColor(EntityLivingBase player) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(player.getHealth(), player.getMaxHealth()) / player.getMaxHealth()) / 3.0f, 0.56f, 1.0f) | 0xFF000000;
    }
}

