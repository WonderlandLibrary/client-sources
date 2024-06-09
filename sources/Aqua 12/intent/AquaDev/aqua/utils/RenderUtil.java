// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import intent.AquaDev.aqua.modules.visual.HUD;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import intent.AquaDev.aqua.cape.GIF;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class RenderUtil
{
    private GuiScreen customcape;
    static Minecraft mc;
    
    public static void drawBlockESP(final BlockPos blockPos, final float red, final float green, final float blue, final float alpha, final float lineAlpha, final float lineWidth) {
        GlStateManager.color(red, green, blue, alpha);
        final float x = (float)(blockPos.getX() - RenderUtil.mc.getRenderManager().getRenderPosX());
        final float y = (float)(blockPos.getY() - RenderUtil.mc.getRenderManager().getRenderPosY());
        final float z = (float)(blockPos.getZ() - RenderUtil.mc.getRenderManager().getRenderPosZ());
        final Block block = RenderUtil.mc.theWorld.getBlockState(blockPos).getBlock();
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB a) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }
    
    public static void polygon(final double x, final double y, double sideLength, final double amountOfSides, final boolean filled, final Color color) {
        sideLength /= 2.0;
        start();
        if (color != null) {
            setGLColor(color);
        }
        if (!filled) {
            GL11.glLineWidth(2.0f);
        }
        GL11.glEnable(2848);
        begin(filled ? 6 : 3);
        for (double i = 0.0; i <= amountOfSides / 4.0; ++i) {
            final double angle = i * 4.0 * 6.283185307179586 / 360.0;
            vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
        }
        end();
        GL11.glDisable(2848);
        stop();
    }
    
    public static void start() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }
    
    public void circle(final double x, final double y, final double radius, final boolean filled, final Color color) {
        polygon(x, y, radius, 360.0, filled, color);
    }
    
    public void circle(final double x, final double y, final double radius, final boolean filled) {
        this.polygon(x, y, radius, 360, filled);
    }
    
    public static void circle(final double x, final double y, final double radius, final Color color) {
        polygon(x, y, radius, 360, color);
    }
    
    public static void stop() {
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        color(Color.white);
    }
    
    public static void vertex(final double x, final double y) {
        GL11.glVertex2d(x, y);
    }
    
    public static void begin(final int glMode) {
        GL11.glBegin(glMode);
    }
    
    public static void end() {
        GL11.glEnd();
    }
    
    public void polygon(final double x, final double y, final double sideLength, final int amountOfSides, final boolean filled) {
        polygon(x, y, sideLength, amountOfSides, filled, null);
    }
    
    public static void polygon(final double x, final double y, final double sideLength, final int amountOfSides, final Color color) {
        polygon(x, y, sideLength, amountOfSides, true, color);
    }
    
    public void polygon(final double x, final double y, final double sideLength, final int amountOfSides) {
        polygon(x, y, sideLength, amountOfSides, true, null);
    }
    
    public static void drawEntityServerESP(final Entity entity, final float red, final float green, final float blue, final float alpha, final float lineAlpha, final float lineWidth) {
        double d0 = entity.serverPosX / 32.0;
        double d2 = entity.serverPosY / 32.0;
        double d3 = entity.serverPosZ / 32.0;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase livingBase = (EntityLivingBase)entity;
            d0 = livingBase.realPos.xCoord;
            d2 = livingBase.realPos.yCoord;
            d3 = livingBase.realPos.zCoord;
        }
        final float x = (float)(d0 - RenderUtil.mc.getRenderManager().getRenderPosX());
        final float y = (float)(d2 - RenderUtil.mc.getRenderManager().getRenderPosY());
        final float z = (float)(d3 - RenderUtil.mc.getRenderManager().getRenderPosZ());
        GL11.glColor4f(red, green, blue, alpha);
        otherDrawBoundingBox(entity, x, y, z, entity.width - 0.2f, entity.height + 0.1f);
        if (lineWidth > 0.0f) {
            GL11.glLineWidth(lineWidth);
            GL11.glColor4f(red, green, blue, lineAlpha);
            otherDrawOutlinedBoundingBox(entity, x, y, z, entity.width - 0.2f, entity.height + 0.1f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void otherDrawOutlinedBoundingBox(final Entity entity, final float x, final float y, final float z, double width, final double height) {
        width *= 1.5;
        final float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0f;
        float newYaw1;
        if (yaw1 < 0.0f) {
            newYaw1 = 0.0f;
            newYaw1 += 360.0f - Math.abs(yaw1);
        }
        else {
            newYaw1 = yaw1;
        }
        newYaw1 *= -1.0f;
        newYaw1 *= (float)0.017453292519943295;
        final float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0f;
        float newYaw2;
        if (yaw2 < 0.0f) {
            newYaw2 = 0.0f;
            newYaw2 += 360.0f - Math.abs(yaw2);
        }
        else {
            newYaw2 = yaw2;
        }
        newYaw2 *= -1.0f;
        newYaw2 *= (float)0.017453292519943295;
        final float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0f;
        float newYaw3;
        if (yaw3 < 0.0f) {
            newYaw3 = 0.0f;
            newYaw3 += 360.0f - Math.abs(yaw3);
        }
        else {
            newYaw3 = yaw3;
        }
        newYaw3 *= -1.0f;
        newYaw3 *= (float)0.017453292519943295;
        final float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0f;
        float newYaw4;
        if (yaw4 < 0.0f) {
            newYaw4 = 0.0f;
            newYaw4 += 360.0f - Math.abs(yaw4);
        }
        else {
            newYaw4 = yaw4;
        }
        newYaw4 *= -1.0f;
        newYaw4 *= (float)0.017453292519943295;
        final float x2 = (float)(Math.sin(newYaw1) * width + x);
        final float z2 = (float)(Math.cos(newYaw1) * width + z);
        final float x3 = (float)(Math.sin(newYaw2) * width + x);
        final float z3 = (float)(Math.cos(newYaw2) * width + z);
        final float x4 = (float)(Math.sin(newYaw3) * width + x);
        final float z4 = (float)(Math.cos(newYaw3) * width + z);
        final float x5 = (float)(Math.sin(newYaw4) * width + x);
        final float z5 = (float)(Math.cos(newYaw4) * width + z);
        final float y2 = (float)(y + height);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }
    
    public static void otherDrawBoundingBox(final Entity entity, final float x, final float y, final float z, double width, final double height) {
        width *= 1.5;
        final float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0f;
        float newYaw1;
        if (yaw1 < 0.0f) {
            newYaw1 = 0.0f;
            newYaw1 += 360.0f - Math.abs(yaw1);
        }
        else {
            newYaw1 = yaw1;
        }
        newYaw1 *= -1.0f;
        newYaw1 *= (float)0.017453292519943295;
        final float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0f;
        float newYaw2;
        if (yaw2 < 0.0f) {
            newYaw2 = 0.0f;
            newYaw2 += 360.0f - Math.abs(yaw2);
        }
        else {
            newYaw2 = yaw2;
        }
        newYaw2 *= -1.0f;
        newYaw2 *= (float)0.017453292519943295;
        final float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0f;
        float newYaw3;
        if (yaw3 < 0.0f) {
            newYaw3 = 0.0f;
            newYaw3 += 360.0f - Math.abs(yaw3);
        }
        else {
            newYaw3 = yaw3;
        }
        newYaw3 *= -1.0f;
        newYaw3 *= (float)0.017453292519943295;
        final float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0f;
        float newYaw4;
        if (yaw4 < 0.0f) {
            newYaw4 = 0.0f;
            newYaw4 += 360.0f - Math.abs(yaw4);
        }
        else {
            newYaw4 = yaw4;
        }
        newYaw4 *= -1.0f;
        newYaw4 *= (float)0.017453292519943295;
        final float x2 = (float)(Math.sin(newYaw1) * width + x);
        final float z2 = (float)(Math.cos(newYaw1) * width + z);
        final float x3 = (float)(Math.sin(newYaw2) * width + x);
        final float z3 = (float)(Math.cos(newYaw2) * width + z);
        final float x4 = (float)(Math.sin(newYaw3) * width + x);
        final float z4 = (float)(Math.cos(newYaw3) * width + z);
        final float x5 = (float)(Math.sin(newYaw4) * width + x);
        final float z5 = (float)(Math.cos(newYaw4) * width + z);
        final float y2 = (float)(y + height);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x2, y, z2).endVertex();
        worldrenderer.pos(x3, y, z3).endVertex();
        worldrenderer.pos(x4, y, z4).endVertex();
        worldrenderer.pos(x5, y, z5).endVertex();
        worldrenderer.pos(x2, y2, z2).endVertex();
        worldrenderer.pos(x3, y2, z3).endVertex();
        worldrenderer.pos(x4, y2, z4).endVertex();
        worldrenderer.pos(x5, y2, z5).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }
    
    public static void drawTriangleFilled(final float x, final float y, final float width, final float height, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        glColor(color);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y + height);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width * 2.0f, y);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public static void drawTriangleFilledReversed(final float x, final float y, final float width, final float height, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        glColor(color);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y - height);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x + width, y - height);
        GL11.glVertex2d(x + width * 2.0f, y);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public static void drawTriangleFilled2(final float x, final float y, final float width, final float height, final int color) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        glColor(color);
        GL11.glBegin(9);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width * 2.0f, y);
        GL11.glVertex2d(x + width * 2.0f, y);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public static double interpolate(final double current, final double old, final double scale) {
        return old + (current - old) * scale;
    }
    
    public static void drawImage(final int x, final int y, final int width, final int height, final ResourceLocation resourceLocation) {
        GlStateManager.enableAlpha();
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawGif(final int x, final int y, final int width, final int height, final String getGif) {
        GlStateManager.enableAlpha();
        final GIF gif = Aqua.INSTANCE.GIFmgr.getGIFByName(getGif);
        RenderUtil.mc.getTextureManager().bindTexture(gif.getNext().getLocation());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawImageDarker(final int x, final int y, final int width, final int height, final ResourceLocation resourceLocation) {
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f(0.19607843f, 0.19607843f, 0.19607843f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawImageHUDColor(final int x, final int y, final int width, final int height, final ResourceLocation resourceLocation) {
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        final Color color = new Color(Aqua.setmgr.getSetting("HUDColor").getColor());
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawImageColored(final int x, final int y, final int width, final int height, final Color color, final ResourceLocation resourceLocation) {
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawRoundedRect2(final double x, final double y, final double width, final double height, final double cornerRadius, final int color) {
        drawRoundedRect2(x, y, width, height, cornerRadius, true, true, true, true, color);
    }
    
    public static void drawRoundedRect(final double x, final double y, final double width, final double height, final double cornerRadius, final int color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        setGLColor(color);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 90; i <= 180; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 180; i <= 270; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        for (int i = 270; i <= 360; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        setGLColor(Color.white);
    }
    
    public static void drawRoundedRect2(final double x, final double y, final double width, final double height, final double cornerRadius, final boolean leftTop, final boolean rightTop, final boolean rightBottom, final boolean leftBottom, final int color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        setGLColor(color);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        if (rightBottom) {
            for (int i = 0; i <= 90; ++i) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x + width, y + height);
        }
        if (rightTop) {
            cornerX = x + width - cornerRadius;
            cornerY = y + cornerRadius;
            for (int i = 90; i <= 180; ++i) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x + width, y);
        }
        if (leftTop) {
            cornerX = x + cornerRadius;
            cornerY = y + cornerRadius;
            for (int i = 180; i <= 270; ++i) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x, y);
        }
        if (leftBottom) {
            cornerX = x + cornerRadius;
            cornerY = y + height - cornerRadius;
            for (int i = 270; i <= 360; ++i) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x, y + height);
        }
        GL11.glEnd();
        setGLColor(new Color(255, 255, 255, 255));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void setGLColor(final Color color) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        final float a = color.getAlpha() / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    public static void setGLColor(final int color) {
        setGLColor(new Color(color));
    }
    
    public static void drawRoundedRectGradient(final double x, final double y, final double width, final double height, final double cornerRadius, final Color start, final Color end) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        color(start);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 90; i <= 180; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        color(end);
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 180; i <= 270; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        for (int i = 270; i <= 360; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        GL11.glEnd();
        GL11.glShadeModel(7424);
        color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void drawGradientRectHorizontal(final double x, final double y, final double width, final double height, final int startColor, final int endColor) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        glColor(startColor);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        glColor(endColor);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void color(final float red, final float green, final float blue, final float alpha) {
        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
    }
    
    public static void color(final Color color) {
        color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
    }
    
    public static void drawRoundedRect3(final double x, final double y, final double width, final double height, final double cornerRadius, final boolean leftTop, final boolean rightTop, final boolean rightBottom, final boolean leftBottom, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        setGLColor(color);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        if (rightBottom) {
            for (int i = 0; i <= 90; i += 30) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x + width, y + height);
        }
        if (rightTop) {
            cornerX = x + width - cornerRadius;
            cornerY = y + cornerRadius;
            for (int i = 90; i <= 180; i += 30) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x + width, y);
        }
        if (leftTop) {
            cornerX = x + cornerRadius;
            cornerY = y + cornerRadius;
            for (int i = 180; i <= 270; i += 30) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x, y);
        }
        if (leftBottom) {
            cornerX = x + cornerRadius;
            cornerY = y + height - cornerRadius;
            for (int i = 270; i <= 360; i += 30) {
                GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
            }
        }
        else {
            GL11.glVertex2d(x, y + height);
        }
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        setGLColor(Color.white);
    }
    
    public static void drawRoundedRect2Alpha(final double x, final double y, final double width, final double height, final double cornerRadius, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        color(color);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 90; i <= 180; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 180; i <= 270; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        for (int i = 270; i <= 360; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void drawRoundedRectSmooth(final double x, final double y, final double width, final double height, final double cornerRadius, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(2881);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        color(color);
        GL11.glBegin(9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 90; i <= 180; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        for (int i = 180; i <= 270; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        for (int i = 270; i <= 360; i += 30) {
            GL11.glVertex2d(cornerX + Math.sin(i * 3.141592653589793 / 180.0) * cornerRadius, cornerY + Math.cos(i * 3.141592653589793 / 180.0) * cornerRadius);
        }
        GL11.glEnd();
        color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2881);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void drawRoundRectTest(final float x1, final float y1, final float x2, final float y2, float radius, int steps, final boolean leftTop, final boolean rightTop, final boolean leftBottom, final boolean rightBottom, final Color color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        color(color);
        GL11.glBegin(9);
        radius = Math.min(Math.min(Math.abs(y2 - y1) / 2.0f, Math.abs(x2 - x1) / 2.0f), radius);
        steps = Math.max(1, steps - steps % 3);
        if (rightBottom) {
            for (int i = 0; i <= 90; i += steps) {
                GL11.glVertex2d(x2 - radius + Math.sin(Math.toRadians(i)) * radius, y2 - radius + Math.cos(Math.toRadians(i)) * radius);
            }
        }
        else {
            GL11.glVertex2d(x2, y2);
        }
        if (rightTop) {
            for (int i = 90; i <= 180; i += steps) {
                GL11.glVertex2d(x2 - radius + Math.sin(Math.toRadians(i)) * radius, y1 + radius + Math.cos(Math.toRadians(i)) * radius);
            }
        }
        else {
            GL11.glVertex2d(x2, y1);
        }
        if (leftTop) {
            for (int i = 180; i <= 270; i += steps) {
                GL11.glVertex2d(x1 + radius + Math.sin(Math.toRadians(i)) * radius, y1 + radius + Math.cos(Math.toRadians(i)) * radius);
            }
        }
        else {
            GL11.glVertex2d(x1, y1);
        }
        if (leftBottom) {
            for (int i = 270; i <= 360; i += steps) {
                GL11.glVertex2d(x1 + radius + Math.sin(Math.toRadians(i)) * radius, y2 - radius + Math.cos(Math.toRadians(i)) * radius);
            }
        }
        else {
            GL11.glVertex2d(x1, y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        color(Color.white);
        GL11.glPopMatrix();
    }
    
    public static void setupViewBobbing(final float partialTicks) {
        if (RenderUtil.mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)RenderUtil.mc.getRenderViewEntity();
            final float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            final float f2 = -(entityplayer.distanceWalkedModified + f * partialTicks);
            final float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
            final float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
            GlStateManager.translate(MathHelper.sin(f2 * 3.1415927f) * f3 * 0.5f, -Math.abs(MathHelper.cos(f2 * 3.1415927f) * f3), 0.0f);
            GlStateManager.rotate(MathHelper.sin(f2 * 3.1415927f) * f3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(f2 * 3.1415927f - 0.2f) * f3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f4, 1.0f, 0.0f, 0.0f);
        }
    }
    
    public static boolean isSliderHovered(final float x1, final float y1, final float x2, final float y2, final int mouseX, final int mouseY) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }
    
    public static Color getColorAlpha(final int color, final int alpha) {
        final Color color2 = new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), alpha);
        return color2;
    }
    
    public static void drawCircle(final double x, final double y, final double radius, final int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(3);
        for (int i = 0; i < 360; ++i) {
            setGLColor(color);
            GL11.glVertex2d(x, y);
            final double sin = Math.sin(Math.toRadians(i)) * radius;
            final double cos = Math.cos(Math.toRadians(i)) * radius;
            GL11.glVertex2d(x + sin, y + cos);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
    
    public static void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = new ScaledResolution(RenderUtil.mc);
        final double scale = sr.getScaleFactor();
        y = sr.getScaledHeight() - y;
        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;
        GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
    }
    
    public static void drawRGBLineHorizontal(final double x, final double y, final double width, final float linewidth, float colors, final boolean reverse) {
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(linewidth);
        GL11.glBegin(3);
        colors *= (float)width;
        final double steps = width / colors;
        double cX = x;
        double cX2 = x + steps;
        if (reverse) {
            for (float i = colors; i > 0.0f; --i) {
                final int argbColor = HUD.rainbow((int)(i * 10.0f));
                final float a = (argbColor >> 24 & 0xFF) / 255.0f;
                final float r = (argbColor >> 16 & 0xFF) / 255.0f;
                final float g = (argbColor >> 8 & 0xFF) / 255.0f;
                final float b = (argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d(cX, y);
                GL11.glVertex2d(cX2, y);
                cX = cX2;
                cX2 += steps;
            }
        }
        else {
            for (int j = 0; j < colors; ++j) {
                final int argbColor = HUD.rainbow(j * 10);
                final float a = (argbColor >> 24 & 0xFF) / 255.0f;
                final float r = (argbColor >> 16 & 0xFF) / 255.0f;
                final float g = (argbColor >> 8 & 0xFF) / 255.0f;
                final float b = (argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d(cX, y);
                GL11.glVertex2d(cX2, y);
                cX = cX2;
                cX2 += steps;
            }
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    static {
        RenderUtil.mc = Minecraft.getMinecraft();
    }
}
