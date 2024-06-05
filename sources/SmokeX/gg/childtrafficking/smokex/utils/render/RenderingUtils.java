// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.render;

import java.math.BigDecimal;
import java.math.MathContext;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.DefaultPlayerSkin;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import java.util.Iterator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.culling.Frustum;

public final class RenderingUtils
{
    private static final Frustum FRUSTUM;
    private static int lastScaledWidth;
    private static int lastScaledHeight;
    private static float lastGuiScale;
    private static ScaledResolution scaledResolution;
    private static int lastWidth;
    private static int lastHeight;
    private static LockedResolution lockedResolution;
    private static Map<Integer, Boolean> glCapMap;
    
    public static void setGLCap(final int cap, final boolean flag) {
        RenderingUtils.glCapMap.put(cap, GL11.glGetBoolean(cap));
        if (flag) {
            GL11.glEnable(cap);
        }
        else {
            GL11.glDisable(cap);
        }
    }
    
    public static void axisAlignedBB(final AxisAlignedBB bb, final int color) {
        enable3D();
        color(color);
        drawBoundingBox(bb);
        disable3D();
    }
    
    public static void enable3D() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static void disable3D() {
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void revertAllCaps() {
        for (final int cap : RenderingUtils.glCapMap.keySet()) {
            revertGLCap(cap);
        }
    }
    
    public static void revertGLCap(final int cap) {
        final Boolean origCap = RenderingUtils.glCapMap.get(cap);
        if (origCap != null) {
            if (origCap) {
                GL11.glEnable(cap);
            }
            else {
                GL11.glDisable(cap);
            }
        }
    }
    
    public static void drawGradientRect(final double left, final double top, final double right, final double bottom, final boolean sideways, final int startColor, final int endColor) {
        GL11.glDisable(3553);
        OGLUtils.enableBlending();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        OGLUtils.color(startColor);
        if (sideways) {
            GL11.glVertex2d(left, top);
            GL11.glVertex2d(left, bottom);
            OGLUtils.color(endColor);
            GL11.glVertex2d(right, bottom);
            GL11.glVertex2d(right, top);
        }
        else {
            GL11.glVertex2d(left, top);
            OGLUtils.color(endColor);
            GL11.glVertex2d(left, bottom);
            GL11.glVertex2d(right, bottom);
            OGLUtils.color(startColor);
            GL11.glVertex2d(right, top);
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
    }
    
    public static void drawBorderedRect(final float x, final float y, final float width, final float height, final float lineStrength, final int outerColor, final int innerColor) {
        drawRect(x, y, width, height, innerColor);
        drawOutlinedRectangle(x, y, width, height, lineStrength, outerColor);
    }
    
    public static void drawImage(final float x, final float y, final float width, final float height, final float r, final float g, final float b, final ResourceLocation image) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        final float f = 1.0f / width;
        final float f2 = 1.0f / height;
        GL11.glColor4f(r, g, b, 1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(0.0, height * f2).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex(width * f, height * f2).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex(width * f, 0.0).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }
    
    public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
    
    public static int getColorFromPercentage(final float percentage) {
        return Color.HSBtoRGB(Math.min(1.0f, Math.max(0.0f, percentage)) / 3.0f, 0.7f, 0.9f);
    }
    
    public static boolean isBBInFrustum(final Entity entity) {
        return isBBInFrustum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    public static boolean isBBInFrustum(final AxisAlignedBB aabb) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        RenderingUtils.FRUSTUM.setPosition(player.posX, player.posY, player.posZ);
        return RenderingUtils.FRUSTUM.isBoundingBoxInFrustum(aabb);
    }
    
    public static void drawFace(final EntityPlayer target, final int x, final int y) {
        final GameProfile gameProfile = target.getGameProfile();
        final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> var12 = Minecraft.getMinecraft().getSkinManager().loadSkinFromCache(gameProfile);
        ResourceLocation skin;
        if (var12.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            skin = Minecraft.getMinecraft().getSkinManager().loadSkin(var12.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
        }
        else {
            final UUID var13 = EntityPlayer.getUUID(gameProfile);
            skin = DefaultPlayerSkin.getDefaultSkin(var13);
        }
        if (skin != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 24.0f, 24.0f, 24, 24, 192.0f, 192.0f);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 120.0f, 24.0f, 24, 24, 192.0f, 192.0f);
            GL11.glDisable(3042);
        }
    }
    
    public static void drawAndRotateArrow(final float x, final float y, final float size, final boolean rotate) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y + (rotate ? (size / 2.0f) : 0.0f), 1.0f);
        OGLUtils.enableBlending();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
        if (rotate) {
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3553);
        GL11.glBegin(3);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glVertex2f(size / 2.0f, size / 2.0f);
        GL11.glVertex2f(size, 0.0f);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        OGLUtils.disableBlending();
        GL11.glPopMatrix();
    }
    
    public static int alphaComponent(final int color, final int alphaComp) {
        final int r = color >> 16 & 0xFF;
        final int g = color >> 8 & 0xFF;
        final int b = color & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) | (alphaComp & 0xFF) << 24;
    }
    
    public static double interpolate(final double old, final double now, final float partialTicks) {
        return old + (now - old) * partialTicks;
    }
    
    public static void drawOutlinedRectangle(final float x, final float y, final float width, final float height, final float lineWidth, final int color) {
        drawRect(x - lineWidth, y - lineWidth, width + lineWidth * 2.0f, lineWidth, color);
        drawRect(x - lineWidth, y, lineWidth, height + lineWidth, color);
        drawRect(x - lineWidth, y + height, width + lineWidth * 2.0f, lineWidth, color);
        drawRect(x + width, y, lineWidth, height, color);
    }
    
    public static int fadeBetween(final int startColor, final int endColor, float progress) {
        if (progress > 1.0f) {
            progress = 1.0f - progress % 1.0f;
        }
        return fadeTo(startColor, endColor, progress);
    }
    
    public static int fadeBetween(final int startColor, final int endColor) {
        return fadeBetween(startColor, endColor, System.currentTimeMillis() % 2000L / 1000.0f);
    }
    
    public static int fadeTo(final int startColor, final int endColor, final float progress) {
        final float invert = 1.0f - progress;
        final int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
        final int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
        final int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
        final int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
    
    public static int darker(final int color, final float factor) {
        final int r = (int)((color >> 16 & 0xFF) * factor);
        final int g = (int)((color >> 8 & 0xFF) * factor);
        final int b = (int)((color & 0xFF) * factor);
        final int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) | (a & 0xFF) << 24;
    }
    
    public static ScaledResolution getScaledResolution() {
        final int displayWidth = Display.getWidth();
        final int displayHeight = Display.getHeight();
        final int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (displayWidth != RenderingUtils.lastScaledWidth || displayHeight != RenderingUtils.lastScaledHeight || guiScale != RenderingUtils.lastGuiScale) {
            RenderingUtils.lastScaledWidth = displayWidth;
            RenderingUtils.lastScaledHeight = displayHeight;
            RenderingUtils.lastGuiScale = (float)guiScale;
            return RenderingUtils.scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        }
        return RenderingUtils.scaledResolution;
    }
    
    public static int darker(final int color) {
        return darker(color, 0.6f);
    }
    
    public static void drawRect(float x, float y, float width, float height, final int color) {
        if (width < 0.0f) {
            width = -width;
            x -= width;
        }
        if (height < 0.0f) {
            height = -height;
            y -= height;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        color(color);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, y, 0.0).endVertex();
        worldrenderer.pos(x, y + height, 0.0).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).endVertex();
        worldrenderer.pos(x + width, y, 0.0).endVertex();
        worldrenderer.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }
    
    public static float getScaledFactor() {
        final float scaledWidth = (float)Minecraft.getMinecraft().displayWidth;
        final float scaledHeight = (float)Minecraft.getMinecraft().displayHeight;
        float scaleFactor = 1.0f;
        final boolean flag = Minecraft.getMinecraft().isUnicode();
        float i = (float)Minecraft.getMinecraft().gameSettings.guiScale;
        if (i == 0.0f) {
            i = 1000.0f;
        }
        while (scaleFactor < i && scaledWidth / (scaleFactor + 1.0f) >= 320.0f && scaledHeight / (scaleFactor + 1.0f) >= 240.0f) {
            ++scaleFactor;
        }
        if (flag && scaleFactor % 2.0f != 0.0f && scaleFactor != 1.0f) {
            --scaleFactor;
        }
        return scaleFactor;
    }
    
    public static int reAlpha(final int color, final float alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }
    
    public static void color(final int color) {
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static LockedResolution getLockedResolution() {
        final int width = Display.getWidth();
        final int height = Display.getHeight();
        if (width != RenderingUtils.lastWidth || height != RenderingUtils.lastHeight) {
            RenderingUtils.lastWidth = width;
            RenderingUtils.lastHeight = height;
            return RenderingUtils.lockedResolution = new LockedResolution(width / 2, height / 2);
        }
        return RenderingUtils.lockedResolution;
    }
    
    public static double animate(final double target, double current, double speed) {
        final boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
        }
        else {
            current -= factor;
        }
        return current;
    }
    
    public static double progressiveAnimation(final double now, final double desired, final double speed) {
        final double dif = Math.abs(now - desired);
        final int fps = Minecraft.getDebugFPS();
        if (dif > 0.0) {
            double animationSpeed = roundToDecimalPlace(Math.min(10.0, Math.max(0.05, 144.0 / fps * (dif / 10.0) * speed)), 0.05);
            if (dif != 0.0 && dif < animationSpeed) {
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
    
    public static LockedResolution lockedResolution() {
        final int width = Display.getWidth();
        final int height = Display.getHeight();
        if (width != RenderingUtils.lastWidth || height != RenderingUtils.lastHeight) {
            RenderingUtils.lastWidth = width;
            RenderingUtils.lastHeight = height;
            return RenderingUtils.lockedResolution = new LockedResolution(width / 2, height / 2);
        }
        return RenderingUtils.lockedResolution;
    }
    
    public static int rainbow(final float seconds, final float saturation, final float brightness, final long index) {
        final float hue = (System.currentTimeMillis() + index) % (int)(seconds * 1000.0f) / (seconds * 1000.0f);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }
    
    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }
    
    public static double roundToDecimalPlace(final double value, final double inc) {
        final double halfOfInc = inc / 2.0;
        final double floored = StrictMath.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(StrictMath.ceil(value / inc) * inc, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        }
        return new BigDecimal(floored, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }
    
    static {
        FRUSTUM = new Frustum();
    }
}
