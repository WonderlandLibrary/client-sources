// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.render;

import net.minecraft.client.renderer.GLAllocation;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import javax.vecmath.Vector3f;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.utils.animation.Animation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.AxisAlignedBB;
import java.awt.Color;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.culling.Frustum;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import xyz.niggfaclient.utils.Utils;

public class RenderUtils extends Utils
{
    public static FloatBuffer windowPosition;
    public static IntBuffer viewport;
    public static FloatBuffer modelMatrix;
    public static FloatBuffer projectionMatrix;
    public static Frustum frustum;
    
    public static void renderFace(final Entity target) {
        if (RenderUtils.mc.getNetHandler() != null && target.getUniqueID() != null) {
            final NetworkPlayerInfo i = RenderUtils.mc.getNetHandler().getPlayerInfo(target.getUniqueID());
            if (i != null) {
                RenderUtils.mc.getTextureManager().bindTexture(i.getLocationSkin());
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                GL11.glEnable(3042);
                Gui.drawModalRectWithCustomSizedTexture(5, 5, 35.0f, 35.0f, 35, 35, 280.0f, 280.0f);
                GL11.glDisable(3042);
            }
        }
    }
    
    public static void drawGradient(final float width, final float height) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Color firstColor = ColorUtil.interpolateColorsBackAndForth(5, 0, new Color(89, 181, 255), new Color(116, 97, 255));
        final Color secondColor = ColorUtil.interpolateColorsBackAndForth(5, 90, new Color(89, 181, 255), new Color(116, 97, 255));
        final Color thirdColor = ColorUtil.interpolateColorsBackAndForth(5, 180, new Color(89, 181, 255), new Color(116, 97, 255));
        final Color fourthColor = ColorUtil.interpolateColorsBackAndForth(5, 270, new Color(89, 181, 255), new Color(116, 97, 255));
        GradientUtil.drawGradient(0.0f, 0.0f, width, height, 255.0f, firstColor, secondColor, thirdColor, fourthColor);
    }
    
    public static boolean isHovering(final float x, final float y, final float width, final float height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
    
    public static boolean isInViewFrustum(final Entity entity) {
        return isInViewFrustum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    public static boolean isBBInFrustum(final AxisAlignedBB aabb) {
        RenderUtils.frustum.setPosition(RenderUtils.mc.thePlayer.posX, RenderUtils.mc.thePlayer.posY, RenderUtils.mc.thePlayer.posZ);
        return RenderUtils.frustum.isBoundingBoxInFrustum(aabb);
    }
    
    public static boolean isInViewFrustum(final AxisAlignedBB bb) {
        RenderUtils.frustum.setPosition(RenderUtils.mc.getRenderViewEntity().posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        return RenderUtils.frustum.isBoundingBoxInFrustum(bb);
    }
    
    public static Framebuffer createFrameBuffer(final Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != RenderUtils.mc.displayWidth || framebuffer.framebufferHeight != RenderUtils.mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(RenderUtils.mc.displayWidth, RenderUtils.mc.displayHeight, true);
        }
        return framebuffer;
    }
    
    public static void drawRoundedRect2(final double x, final double y, final double width, final double height, final double radius, final int color) {
        drawRoundedRect(x, y, width - x, height - y, radius, color);
    }
    
    public static void drawRoundedRect(double x, double y, final double width, final double height, final double radius, final int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double x2 = x + width;
        double y2 = y + height;
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x2 *= 2.0;
        y2 *= 2.0;
        GL11.glDisable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawCustomRounded(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, final float rTL, final float rTR, final float rBR, final float rBL, final int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        if (paramXStart > paramXEnd) {
            final float z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            final float z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        final double xTL = paramXStart + rTL;
        final double yTL = paramYStart + rTL;
        final double xTR = paramXEnd - rTR;
        final double yTR = paramYStart + rTR;
        final double xBR = paramXEnd - rBR;
        final double yBR = paramYEnd - rBR;
        final double xBL = paramXStart + rBL;
        final double yBL = paramYEnd - rBL;
        GL11.glPushAttrib(0);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        final double degree = 0.017453292519943295;
        for (double i = 0.0; i <= 90.0; i += 0.25) {
            GL11.glVertex2d(xBR + Math.sin(i * degree) * rBR, yBR + Math.cos(i * degree) * rBR);
        }
        for (double i = 90.0; i <= 180.0; i += 0.25) {
            GL11.glVertex2d(xTR + Math.sin(i * degree) * rTR, yTR + Math.cos(i * degree) * rTR);
        }
        for (double i = 180.0; i <= 270.0; i += 0.25) {
            GL11.glVertex2d(xTL + Math.sin(i * degree) * rTL, yTL + Math.cos(i * degree) * rTL);
        }
        for (double i = 270.0; i <= 360.0; i += 0.25) {
            GL11.glVertex2d(xBL + Math.sin(i * degree) * rBL, yBL + Math.cos(i * degree) * rBL);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopAttrib();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawOutlinedRoundedRect(double x, double y, final double width, final double height, final double radius, final float linewidth, final int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double x2 = x + width;
        double y2 = y + height;
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x2 *= 2.0;
        y2 *= 2.0;
        GL11.glLineWidth(linewidth);
        GL11.glDisable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawOutlinedRoundedRect2(final double x, final double y, final double width, final double height, final double radius, final float linewidth, final int color) {
        drawOutlinedRoundedRect(x, y, width - x, height - y, radius, linewidth, color);
    }
    
    public static void drawLinesAroundPlayer(final Entity entity, final double radius, final int points, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * RenderUtils.mc.timer.renderPartialTicks - RenderManager.viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * RenderUtils.mc.timer.renderPartialTicks - RenderManager.viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * RenderUtils.mc.timer.renderPartialTicks - RenderManager.viewerPosZ;
        for (int i = 0; i <= points; ++i) {
            GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GL11.glVertex3d(x + radius * Math.cos(i * 6.283185307179586 / points), y, z + radius * Math.sin(i * 6.283185307179586 / points));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static void setColor(final int color) {
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    public static double transition(final double now, final double desired, final double speed) {
        final double dif = Math.abs(now - desired);
        final int fps = Minecraft.getDebugFPS();
        if (dif > 0.0) {
            double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(10.0, Math.max(0.0625, 144.0 / fps * (dif / 10.0) * speed)), 0.0625);
            if (dif < animationSpeed) {
                animationSpeed = dif;
            }
            if (now < desired) {
                return now + animationSpeed;
            }
            if (now > desired) {
                return now - animationSpeed;
            }
        }
        return now;
    }
    
    public static void scale() {
        switch (RenderUtils.mc.gameSettings.guiScale) {
            case 0: {
                GlStateManager.scale(0.5, 0.5, 0.5);
                break;
            }
            case 1: {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                break;
            }
            case 3: {
                GlStateManager.scale(0.6666666666666667, 0.6666666666666667, 0.6666666666666667);
                break;
            }
        }
    }
    
    public static void drawBorderRect(final double x, final double y, final double width, final double height, final int color, final double linewidth) {
        drawHLine(x, y, width, y, (float)linewidth, color);
        drawHLine(width, y, width, height, (float)linewidth, color);
        drawHLine(x, height, width, height, (float)linewidth, color);
        drawHLine(x, height, x, y, (float)linewidth, color);
    }
    
    public static void drawStack(final boolean renderOverlay, final ItemStack stack, final float x, final float y) {
        GL11.glPushMatrix();
        if (RenderUtils.mc.theWorld != null) {
            RenderHelper.enableGUIStandardItemLighting();
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
        RenderUtils.mc.getRenderItem().zLevel = -150.0f;
        RenderUtils.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        if (renderOverlay) {
            RenderUtils.mc.getRenderItem().renderItemOverlayIntoGUI(stack, x, y, String.valueOf(stack.stackSize));
        }
        RenderUtils.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.enableBlend();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }
    
    public static void drawHLine(final double x, final double y, final double x1, final double y1, final float width, final int color) {
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        GL11.glPushMatrix();
        GL11.glLineWidth(width);
        GL11.glBegin(3);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void bindTexture(final int texture) {
        GL11.glBindTexture(3553, texture);
    }
    
    public static void drawAndRotateArrow(final float x, final float y, final float size, final Animation animation) {
        GL11.glTranslatef(x, y, 0.0f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glBegin(5);
        color(-1);
        final double interpolation = interpolate(0.0, size / 2.0, animation.getOutput());
        if (animation.getOutput() >= 0.48) {
            GL11.glVertex2d(size / 2.0f, interpolate(size / 2.0, 0.0, animation.getOutput()));
        }
        GL11.glVertex2d(0.0, interpolation);
        if (animation.getOutput() < 0.48) {
            GL11.glVertex2d(size / 2.0f, interpolate(size / 2.0, 0.0, animation.getOutput()));
        }
        GL11.glVertex2d(size, interpolation);
        GL11.glEnd();
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
        GL11.glTranslatef(-x, -y, 0.0f);
    }
    
    public static void startScissorBox(final int x, final int y, final int width, final int height) {
        final int factor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        GL11.glEnable(3089);
        GL11.glScissor(x * factor, (new ScaledResolution(RenderUtils.mc).getScaledHeight() - (y + height)) * factor, width * factor, height * factor);
    }
    
    public static void endScissorBox() {
        GL11.glDisable(3089);
    }
    
    public static void startBlending() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }
    
    public static void endBlending() {
        GL11.glDisable(3042);
    }
    
    public static void prepareScissorBox(final double x, final double y, final double x2, final double y2) {
        final int factor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
    
    public static Vector3f project2D(final float x, final float y, final float z, final int scaleFactor) {
        GL11.glGetFloat(2982, RenderUtils.modelMatrix);
        GL11.glGetFloat(2983, RenderUtils.projectionMatrix);
        GL11.glGetInteger(2978, RenderUtils.viewport);
        if (GLU.gluProject(x, y, z, RenderUtils.modelMatrix, RenderUtils.projectionMatrix, RenderUtils.viewport, RenderUtils.windowPosition)) {
            return new Vector3f(RenderUtils.windowPosition.get(0) / scaleFactor, (Display.getHeight() - RenderUtils.windowPosition.get(1)) / scaleFactor, RenderUtils.windowPosition.get(2));
        }
        return null;
    }
    
    public static double interpolate(final double old, final double now, final double partialTicks) {
        return old + (now - old) * partialTicks;
    }
    
    public static double interpolate(final double old, final double now, final float partialTicks) {
        return old + (now - old) * partialTicks;
    }
    
    public static float interpolate(final float old, final float now, final float partialTicks) {
        return old + (now - old) * partialTicks;
    }
    
    public static void drawImage(final ResourceLocation image, final float x, final float y, final float width, final float height) {
        GL11.glPushMatrix();
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtils.mc.getTextureManager().bindTexture(image);
        GL11.glTexParameteri(3553, 10240, 9729);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
    
    public static void drawLogo(final ResourceLocation image, final float x, final float y, final float width, final float height) {
        RenderUtils.mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
    }
    
    public static void drawWaveString(final String str, float x, final float y, final boolean font) {
        for (int i = 0; i < str.length(); ++i) {
            final String ch = str.charAt(i) + "";
            if (font) {
                Fonts.sf21.drawStringWithShadow(ch, (float)(int)x, (float)(int)y, ColorUtil.getHUDColor(i * 200));
            }
            else {
                RenderUtils.mc.fontRendererObj.drawStringWithShadow(ch, (float)(int)x, (float)(int)y, ColorUtil.getHUDColor(i * 200));
            }
            x += (font ? Fonts.sf21.getStringWidth(ch) : RenderUtils.mc.fontRendererObj.getStringWidth(ch));
        }
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 256.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void color(final int color, final float alpha) {
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GlStateManager.color(r, g, b, alpha);
    }
    
    public static void setAlphaLimit(final float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float)(limit * 0.01));
    }
    
    public static void color(final int color) {
        color(color, (color >> 24 & 0xFF) / 255.0f);
    }
    
    public static void color(final Color color) {
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    static {
        RenderUtils.windowPosition = GLAllocation.createDirectFloatBuffer(4);
        RenderUtils.viewport = GLAllocation.createDirectIntBuffer(16);
        RenderUtils.modelMatrix = GLAllocation.createDirectFloatBuffer(16);
        RenderUtils.projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
        RenderUtils.frustum = new Frustum();
    }
}
