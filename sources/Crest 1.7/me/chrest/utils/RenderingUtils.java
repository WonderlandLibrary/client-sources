// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemBow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import java.util.List;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import java.awt.Color;
import java.awt.Rectangle;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import me.chrest.event.events.Render3DEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;

public final class RenderingUtils
{
    public static final RenderItem RENDER_ITEM;
    private static ScaledResolution scaledResolution;
    private static int Y;
    private static int pY;
    private static float pY2;
    private static int rectY;
    
    static {
        RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().modelManager);
        RenderingUtils.Y = 22;
        RenderingUtils.pY = 0;
        RenderingUtils.pY2 = 0.0f;
        RenderingUtils.rectY = 32;
    }
    
    public static void drawSearchBlock(final Block block, final BlockPos blockPos, final Render3DEvent event) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        GlStateManager.pushMatrix();
        GL11.glLineWidth(1.0f);
        GlStateManager.disableDepth();
        disableLighting();
        final double var8 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
        final double var9 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
        final double var10 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
        RenderGlobal.drawOutlinedBoundingBox(block.getSelectedBoundingBox(Minecraft.getMinecraft().theWorld, blockPos).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).offset(-var8, -var9, -var10), -1);
        GlStateManager.popMatrix();
    }
    
    public static void drawEsp(final EntityLivingBase ent, final float pTicks, final int hexColor, final int hexColorIn) {
        if (!ent.isEntityAlive()) {
            return;
        }
        final double x = getDiff(ent.lastTickPosX, ent.posX, pTicks, RenderManager.renderPosX);
        final double y = getDiff(ent.lastTickPosY, ent.posY, pTicks, RenderManager.renderPosY);
        final double z = getDiff(ent.lastTickPosZ, ent.posZ, pTicks, RenderManager.renderPosZ);
        boundingBox(ent, x, y, z, hexColor, hexColorIn);
    }
    
    public static void boundingBox(final Entity entity, final double x, final double y, final double z, final int color, final int colorIn) {
        GlStateManager.pushMatrix();
        GL11.glLineWidth(1.0f);
        final AxisAlignedBB var11 = entity.getEntityBoundingBox();
        final AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - entity.posX + x, var11.minY - entity.posY + y, var11.minZ - entity.posZ + z, var11.maxX - entity.posX + x, var11.maxY - entity.posY + y, var11.maxZ - entity.posZ + z);
        if (color != 0) {
            GlStateManager.disableDepth();
            filledBox(var12, colorIn, true);
            disableLighting();
            RenderGlobal.drawOutlinedBoundingBox(var12, color);
        }
        GlStateManager.popMatrix();
    }
    
    public static void enableLighting() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        final float var3 = 0.0039063f;
        GL11.glScalef(var3, var3, var3);
        GL11.glTranslatef(8.0f, 8.0f, 8.0f);
        GL11.glMatrixMode(5888);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public static void disableLighting() {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void enableGL3D(final float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }
    
    public static void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x1, final float y1, final float width, final int internalColor, final int borderColor) {
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x + width, y, x1 - width, y + width);
        drawRect(x, y, x + width, y1);
        drawRect(x1 - width, y, x1, y1);
        drawRect(x + width, y1 - width, x1 - width, y1);
        disableGL2D();
    }
    
    public static void drawBorderedRect(int x, int y, int x1, int y1, final int insideC, final int borderC) {
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x *= 2, y *= 2, (y1 *= 2) - 1, borderC);
        drawVLine((x1 *= 2) - 1, y, y1, borderC);
        drawHLine(x, x1 - 1, y, borderC);
        drawHLine(x, x1 - 2, y1 - 1, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int inside, final int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawGradientBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int border, final int bottom, final int top) {
        enableGL2D();
        drawGradientRect(x, y, x1, y1, top, bottom);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawRoundedRect(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawBorderedRect(final Rectangle rectangle, final float width, final int internalColor, final int borderColor) {
        final float x = rectangle.x;
        final float y = rectangle.y;
        final float x2 = rectangle.x + rectangle.width;
        final float y2 = rectangle.y + rectangle.height;
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x2 - width, y2 - width);
        glColor(borderColor);
        drawRect(x + 1.0f, y, x2 - 1.0f, y + width);
        drawRect(x, y, x + width, y2);
        drawRect(x2 - width, y, x2, y2);
        drawRect(x + 1.0f, y2 - width, x2 - 1.0f, y2);
        disableGL2D();
    }
    
    public static void drawGradientRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void drawGradientHRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void drawGradientRect(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        glColor(col2);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawGradientBorderedRect(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2, final int col3) {
        enableGL2D();
        GL11.glPushMatrix();
        glColor(col1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(x, y, x2, y2, col2, col3);
        disableGL2D();
    }
    
    public static void drawStrip(final int x, final int y, final float width, final double angle, final float points, final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i < angle; ++i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * radius);
                final float yc = (float)(Math.sin(a) * radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i > angle; --i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * -radius);
                final float yc = (float)(Math.sin(a) * -radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static void drawHLine(float x, float y, final float x1, final int y1) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }
    
    public static void drawVLine(final float x, float y, float x1, final int y1) {
        if (x1 < y) {
            final float var5 = y;
            y = x1;
            x1 = var5;
        }
        drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }
    
    public static void drawHLine(float x, float y, final float x1, final int y1, final int y2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final float r, final float g, final float b, final float a) {
        enableGL2D();
        GL11.glColor4f(r, g, b, a);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }
    
    public static void drawCircle(float cx, float cy, float r, final int num_segments, final int c) {
        GL11.glPushMatrix();
        cx *= 2.0f;
        cy *= 2.0f;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        final float theta = (float)(6.2831852 / num_segments);
        final float p = (float)Math.cos(theta);
        final float s = (float)Math.sin(theta);
        float x;
        r = (x = r * 2.0f);
        float y = 0.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, y + cy);
            final float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
        GL11.glPopMatrix();
    }
    
    public static void drawFullCircle(int cx, int cy, double r, final int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void updateScaledResolution() {
        RenderingUtils.scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }
    
    public static ScaledResolution getScaledResolution() {
        return RenderingUtils.scaledResolution;
    }
    
    public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        updateScaledResolution();
        final int factor = RenderingUtils.scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((RenderingUtils.scaledResolution.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB boundingBox) {
        if (boundingBox == null) {
            return;
        }
        GL11.glBegin(3);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxZ, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxZ, boundingBox.maxZ);
        GL11.glEnd();
    }
    
    public static void drawBox(final RenderBox box) {
        GL11.glEnable(1537);
        if (box == null) {
            return;
        }
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxZ, box.maxZ);
        GL11.glVertex3d(box.maxZ, box.maxZ, box.maxZ);
        GL11.glEnd();
    }
    
    public static void filledBox(final AxisAlignedBB aa, final int color, final boolean shouldColor) {
        GlStateManager.pushMatrix();
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        final Tessellator var15 = Tessellator.getInstance();
        final WorldRenderer t = var15.getWorldRenderer();
        if (shouldColor) {
            GlStateManager.color(var12, var13, var14, var11);
        }
        final int draw = 7;
        t.startDrawing(draw);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        var15.draw();
        t.startDrawing(draw);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        var15.draw();
        t.startDrawing(draw);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        var15.draw();
        t.startDrawing(draw);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        var15.draw();
        t.startDrawing(draw);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        var15.draw();
        t.startDrawing(draw);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        var15.draw();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }
    
    private static double getDiff(final double lastI, final double i, final float ticks, final double ownI) {
        return lastI + (i - lastI) * ticks - ownI;
    }
    
    public static void drawBeacon(final BlockPos pos, final int color, final int colorIn, final float partialTicks) {
        GlStateManager.pushMatrix();
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GL11.glLineWidth(1.0f);
        final AxisAlignedBB var11 = new AxisAlignedBB(pos.getX() + 1, pos.getY(), pos.getZ() + 1, pos.getX(), pos.getY() + 200, pos.getZ());
        final AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - x, var11.minY - y, var11.minZ - z, var11.maxX - x, var11.maxY - y, var11.maxZ - z);
        if (color != 0) {
            GlStateManager.disableDepth();
            filledBox(var12, colorIn, true);
            disableLighting();
            RenderGlobal.drawOutlinedBoundingBox(var12, color);
        }
        GlStateManager.popMatrix();
    }
    
    public static Color getRainbow(final long offset, final float fade) {
        final float hue = (System.nanoTime() + offset) / 5.0E9f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0f, 1.0f))), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
    
    public static void drawPotionStatus(final ScaledResolution sr) {
        RenderingUtils.pY = 0;
        for (final PotionEffect effect : ClientUtils.mc().thePlayer.getActivePotionEffects()) {
            final Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName(), new Object[0]);
            if (effect.getAmplifier() == 1) {
                PType = String.valueOf(PType) + " II";
            }
            else if (effect.getAmplifier() == 2) {
                PType = String.valueOf(PType) + " III";
            }
            else if (effect.getAmplifier() == 3) {
                PType = String.valueOf(PType) + " IV";
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = String.valueOf(PType) + "§7:§6 " + Potion.getDurationString(effect);
            }
            else if (effect.getDuration() < 300) {
                PType = String.valueOf(PType) + "§7:§c " + Potion.getDurationString(effect);
            }
            else if (effect.getDuration() > 600) {
                PType = String.valueOf(PType) + "§7:§7 " + Potion.getDurationString(effect);
            }
            ClientUtils.clientFont().drawStringWithShadow(PType, sr.getScaledWidth() - ClientUtils.clientFont().getStringWidth(PType), sr.getScaledHeight() - ClientUtils.clientFont().FONT_HEIGHT + RenderingUtils.pY, potion.getLiquidColor());
            RenderingUtils.pY -= 9;
        }
    }
    
    public static void drawStuffStatus(final ScaledResolution scaledRes) {
        final boolean currentItem = true;
        GL11.glPushMatrix();
        final RenderItem ir = new RenderItem(ClientUtils.mc().getTextureManager(), ClientUtils.mc().modelManager);
        final List<ItemStack> stuff = new ArrayList<ItemStack>();
        final boolean onwater = ClientUtils.mc().thePlayer.isEntityAlive() && ClientUtils.mc().thePlayer.isInsideOfMaterial(Material.water);
        int split = -3;
        for (int index = 3; index >= 0; --index) {
            final ItemStack armer = ClientUtils.mc().thePlayer.inventory.armorInventory[index];
            if (armer != null) {
                stuff.add(armer);
            }
        }
        if (ClientUtils.mc().thePlayer.getCurrentEquippedItem() != null && currentItem) {
            stuff.add(ClientUtils.mc().thePlayer.getCurrentEquippedItem());
        }
        for (final ItemStack errything : stuff) {
            if (ClientUtils.mc().theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                split += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            ClientUtils.mc().getRenderItem().zLevel = -150.0f;
            ClientUtils.mc().getRenderItem().func_180450_b(errything, split + scaledRes.getScaledWidth() / 2 - 4, scaledRes.getScaledHeight() - (onwater ? 65 : 55));
            ClientUtils.mc().getRenderItem().func_175030_a(ClientUtils.mc().fontRendererObj, errything, split + scaledRes.getScaledWidth() / 2 - 4, scaledRes.getScaledHeight() - (onwater ? 65 : 55));
            ClientUtils.mc().getRenderItem().zLevel = 0.0f;
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            errything.getEnchantmentTagList();
        }
        GL11.glPopMatrix();
    }
    
    public static void renderEnchantText(final ItemStack stack, final int x, final int y) {
        int encY = y + 276;
        if (stack.getItem() instanceof ItemArmor) {
            final int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            final int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            final int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (pLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fp" + pLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (tLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§ft" + tLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fu" + uLevel, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemBow) {
            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            final int uLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fd" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fk" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§ff" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel2 > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fu" + uLevel2, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemSword) {
            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            final int uLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fs" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fk" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§ff" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel2 > 0) {
                ClientUtils.clientFont().drawStringWithShadow("§fu" + uLevel2, x * 2, encY, 16777215);
            }
        }
    }
}
