package me.dinozoid.strife.util.render;

import me.dinozoid.strife.module.implementations.visuals.OverlayModule;
import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil extends MinecraftUtil {

    private static final FloatBuffer windowPosition = BufferUtils.createFloatBuffer(4);
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);

    public static int rainbow(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (seconds * 1000);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static int astolfo(float seconds, float saturation, float brightness, float index) {
        float speed = 3000f;
        float hue = (System.currentTimeMillis() % (int)(seconds * 1000)) + index;
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5)
            hue = 0.5F - (hue - 0.5f);
        hue += 0.5F;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static int fade(Color color, int count, int index) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        return Color.HSBtoRGB(hsb[0], hsb[1], brightness % 2.0f);
    }

    public static void drawWaveString(String str, float x, float y) {
        float posX = x;
        for (int i = 0; i < str.length(); i++) {
            String ch = str.charAt(i) + "";
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(ch, (int) posX, (int) y, OverlayModule.getColor(i * 200));
            posX += Minecraft.getMinecraft().fontRendererObj.getStringWidth(ch);
        }
    }

    public static float animate(double target, double current, double speed) {
        boolean larger = (target > current);
        if (speed < 0.0F) speed = 0.0F;
        else if (speed > 1.0F) speed = 1.0F;
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1f) factor = 0.1F;
        if (larger) current += factor;
        else current -= factor;
        return (float) current;
    }

    public static float interpolate(double current, double old, double scale) {
        return (float) (old + (current - old) * scale);
    }

    public static Vector3f project2D(int scaleFactor, float x, float y, float z) {
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelMatrix);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        if (GLU.gluProject(x, y, z, modelMatrix, projectionMatrix, viewport, windowPosition)) return new Vector3f(windowPosition.get(0) / scaleFactor, (mc.displayHeight - windowPosition.get(1)) / scaleFactor, windowPosition.get(2));
        return null;
    }

    public static boolean isHovered(float x, float y, float w, float h, int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h);
    }

    public static boolean isHoveredFull(float x, float y, float w, float h, int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= w && mouseY >= y && mouseY <= h);
    }

    public static void color(Color color) {
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public static float[] convertRGB(int rgb) {
        float a = (rgb >> 24 & 0xFF) / 255.0f;
        float r = (rgb >> 16 & 0xFF) / 255.0f;
        float g = (rgb >> 8 & 0xFF) / 255.0f;
        float b = (rgb & 0xFF) / 255.0f;
        return new float[] { r, g, b, a };
    }

    public static void color(int color) {
        float[] rgba = convertRGB(color);
        GL11.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    public static Color brighter(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();
        int i = (int)(1.0/(1.0-factor));
        if (r == 0 && g == 0 && b == 0) return new Color(i, i, i, alpha);
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;
        return new Color(Math.min((int)(r/factor), 255), Math.min((int)(g/factor), 255), Math.min((int)(b/factor), 255), alpha);
    }

    public static Color darker(Color color, float factor) {
        return new Color(Math.max((int)(color.getRed() * factor), 0), Math.max((int)(color.getGreen()*factor), 0), Math.max((int)(color.getBlue() *factor), 0), color.getAlpha());
    }

    public static void prepareScissorBox(float x, float y, float width, float height) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        prepareScissorBox(x, y, width, height, scaledResolution);
    }

    public static void prepareScissorBox(float x, float y, float width, float height, ScaledResolution scaledResolution) {
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - height) * factor), (int)((width - x) * factor), (int)((height - y) * factor));
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
        drawImage(image, x, y, width, height, 255);
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height, float opacity) {
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        glColor4f(1.0f, 1.0f, 1.0f, opacity / 255);
        mc.getTextureManager().bindTexture(image);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }
    public static void drawDynamicTexture(DynamicTexture texture, float x, float y, float width, float height) {
        drawDynamicTexture(texture, x, y, width, height, 255);
    }

    public static void drawDynamicTexture(DynamicTexture texture, float x, float y, float width, float height, float opacity) {
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        glColor4f(1.0f, 1.0f, 1.0f, opacity / 255);
        DynamicTextureUtil.bindTexture(texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        glPushMatrix();
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        color(color);
        glBegin(GL_QUADS);
        glVertex2d(width, y);
        glVertex2d(x, y);
        glVertex2d(x, height);
        glVertex2d(width, height);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glPopMatrix();
    }



    public static void drawFilledCircle(float cx, float cy, float radius, float num_segments, Color color) {
        double theta = 2 * Math.PI / num_segments;
        double c = Math.cos(theta); //precalculate the sine and cosine
        double s = Math.sin(theta);
        double t;
        double x = radius; //we start at angle = 0
        double y = 0;
        glBegin(GL_LINE_LOOP);
        for(int ii = 0; ii < num_segments; ii++) {
            color(color);
            glVertex2d(x + cx, y + cy); //output vertex
            //apply the rotation matrix
            t = x;
            x = c * x - s * y;
            y = s * t + c * y;
        }
        glEnd();
    }

}
