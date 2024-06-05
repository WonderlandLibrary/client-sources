package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class CustomSkyLayer
{
    public String source;
    private int startFadeIn;
    private int endFadeIn;
    private int startFadeOut;
    private int endFadeOut;
    private int blend;
    private boolean rotate;
    private float speed;
    private float[] axis;
    public int textureId;
    public static final int BLEND_ADD = 0;
    public static final int BLEND_SUBSTRACT = 1;
    public static final int BLEND_MULTIPLY = 2;
    public static final int BLEND_DODGE = 3;
    public static final int BLEND_BURN = 4;
    public static final int BLEND_SCREEN = 5;
    public static final int BLEND_REPLACE = 6;
    public static final float[] DEFAULT_AXIS;
    
    static {
        DEFAULT_AXIS = new float[] { 1.0f, 0.0f, 0.0f };
    }
    
    public CustomSkyLayer(final Properties var1, final String var2) {
        this.source = null;
        this.startFadeIn = -1;
        this.endFadeIn = -1;
        this.startFadeOut = -1;
        this.endFadeOut = -1;
        this.blend = 0;
        this.rotate = false;
        this.speed = 1.0f;
        this.axis = CustomSkyLayer.DEFAULT_AXIS;
        this.textureId = -1;
        this.source = var1.getProperty("source", var2);
        this.startFadeIn = this.parseTime(var1.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(var1.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(var1.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(var1.getProperty("endFadeOut"));
        this.blend = this.parseBlend(var1.getProperty("blend"));
        this.rotate = this.parseBoolean(var1.getProperty("rotate"), true);
        this.speed = this.parseFloat(var1.getProperty("speed"), 1.0f);
        this.axis = this.parseAxis(var1.getProperty("axis"), CustomSkyLayer.DEFAULT_AXIS);
    }
    
    private int parseTime(final String var1) {
        if (var1 == null) {
            return -1;
        }
        final String[] var2 = Config.tokenize(var1, ":");
        if (var2.length != 2) {
            Config.dbg("Invalid time: " + var1);
            return -1;
        }
        final String var3 = var2[0];
        final String var4 = var2[1];
        int var5 = Config.parseInt(var3, -1);
        final int var6 = Config.parseInt(var4, -1);
        if (var5 >= 0 && var5 <= 23 && var6 >= 0 && var6 <= 59) {
            var5 -= 6;
            if (var5 < 0) {
                var5 += 24;
            }
            final int var7 = var5 * 1000 + (int)(var6 / 60.0 * 1000.0);
            return var7;
        }
        Config.dbg("Invalid time: " + var1);
        return -1;
    }
    
    private int parseBlend(final String var1) {
        if (var1 == null) {
            return 0;
        }
        if (var1.equals("add")) {
            return 0;
        }
        if (var1.equals("subtract")) {
            return 1;
        }
        if (var1.equals("multiply")) {
            return 2;
        }
        if (var1.equals("dodge")) {
            return 3;
        }
        if (var1.equals("burn")) {
            return 4;
        }
        if (var1.equals("screen")) {
            return 5;
        }
        if (var1.equals("replace")) {
            return 6;
        }
        Config.dbg("Unknown blend: " + var1);
        return 0;
    }
    
    private boolean parseBoolean(final String var1, final boolean var2) {
        if (var1 == null) {
            return var2;
        }
        if (var1.toLowerCase().equals("true")) {
            return true;
        }
        if (var1.toLowerCase().equals("false")) {
            return false;
        }
        Config.dbg("Unknown boolean: " + var1);
        return var2;
    }
    
    private float parseFloat(final String var1, final float var2) {
        if (var1 == null) {
            return var2;
        }
        final float var3 = Config.parseFloat(var1, Float.MIN_VALUE);
        if (var3 == Float.MIN_VALUE) {
            Config.dbg("Invalid value: " + var1);
            return var2;
        }
        return var3;
    }
    
    private float[] parseAxis(final String var1, final float[] var2) {
        if (var1 == null) {
            return var2;
        }
        final String[] var3 = Config.tokenize(var1, " ");
        if (var3.length != 3) {
            Config.dbg("Invalid axis: " + var1);
            return var2;
        }
        final float[] var4 = new float[3];
        for (int var5 = 0; var5 < var3.length; ++var5) {
            var4[var5] = Config.parseFloat(var3[var5], Float.MIN_VALUE);
            if (var4[var5] == Float.MIN_VALUE) {
                Config.dbg("Invalid axis: " + var1);
                return var2;
            }
            if (var4[var5] < -1.0f || var4[var5] > 1.0f) {
                Config.dbg("Invalid axis values: " + var1);
                return var2;
            }
        }
        final float var6 = var4[0];
        final float var7 = var4[1];
        final float var8 = var4[2];
        if (var6 * var6 + var7 * var7 + var8 * var8 < 1.0E-5f) {
            Config.dbg("Invalid axis values: " + var1);
            return var2;
        }
        final float[] var9 = { var8, var7, -var6 };
        return var9;
    }
    
    public boolean isValid(final String var1) {
        if (this.source == null) {
            Config.dbg("No source texture: " + var1);
            return false;
        }
        if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
            Config.dbg("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
        final int var2 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
        if (this.startFadeOut < 0) {
            this.startFadeOut = this.normalizeTime(this.endFadeOut - var2);
        }
        final int var3 = this.normalizeTime(this.startFadeOut - this.endFadeIn);
        final int var4 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
        final int var5 = this.normalizeTime(this.startFadeIn - this.endFadeOut);
        final int var6 = var2 + var3 + var4 + var5;
        if (var6 != 24000) {
            Config.dbg("Invalid fadeIn/fadeOut times, sum is more than 24h");
            return false;
        }
        if (this.speed < 0.0f) {
            Config.dbg("Invalid speed: " + this.speed);
            return false;
        }
        return true;
    }
    
    private int normalizeTime(int var1) {
        while (var1 >= 24000) {
            var1 -= 24000;
        }
        while (var1 < 0) {
            var1 += 24000;
        }
        return var1;
    }
    
    public void render(final int var1, final RenderEngine var2, final float var3, final float var4) {
        float var5 = var4 * this.getFadeBrightness(var1);
        var5 = Config.limit(var5, 0.0f, 1.0f);
        if (var5 >= 1.0E-4f) {
            var2.bindTexture(this.textureId);
            this.setupBlend(var5);
            GL11.glPushMatrix();
            if (this.rotate) {
                GL11.glRotatef(var3 * 360.0f * this.speed, this.axis[0], this.axis[1], this.axis[2]);
            }
            final Tessellator var6 = Tessellator.instance;
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(var6, 4);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(var6, 1);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(var6, 0);
            GL11.glPopMatrix();
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(var6, 5);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(var6, 2);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(var6, 3);
            GL11.glPopMatrix();
        }
    }
    
    private float getFadeBrightness(final int var1) {
        if (this.timeBetween(var1, this.startFadeIn, this.endFadeIn)) {
            final int var2 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            final int var3 = this.normalizeTime(var1 - this.startFadeIn);
            return var3 / var2;
        }
        if (this.timeBetween(var1, this.endFadeIn, this.startFadeOut)) {
            return 1.0f;
        }
        if (this.timeBetween(var1, this.startFadeOut, this.endFadeOut)) {
            final int var2 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            final int var3 = this.normalizeTime(var1 - this.startFadeOut);
            return 1.0f - var3 / var2;
        }
        return 0.0f;
    }
    
    private void renderSide(final Tessellator var1, final int var2) {
        final double var3 = var2 % 3 / 3.0;
        final double var4 = var2 / 3 / 2.0;
        var1.startDrawingQuads();
        var1.addVertexWithUV(-100.0, -100.0, -100.0, var3, var4);
        var1.addVertexWithUV(-100.0, -100.0, 100.0, var3, var4 + 0.5);
        var1.addVertexWithUV(100.0, -100.0, 100.0, var3 + 0.3333333333333333, var4 + 0.5);
        var1.addVertexWithUV(100.0, -100.0, -100.0, var3 + 0.3333333333333333, var4);
        var1.draw();
    }
    
    void setupBlend(final float var1) {
        switch (this.blend) {
            case 0: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 1);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, var1);
                break;
            }
            case 1: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(775, 0);
                GL11.glColor4f(var1, var1, var1, 1.0f);
                break;
            }
            case 2: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(774, 771);
                GL11.glColor4f(var1, var1, var1, var1);
                break;
            }
            case 3: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                GL11.glColor4f(var1, var1, var1, 1.0f);
                break;
            }
            case 4: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(0, 769);
                GL11.glColor4f(var1, var1, var1, 1.0f);
                break;
            }
            case 5: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 769);
                GL11.glColor4f(var1, var1, var1, 1.0f);
                break;
            }
            case 6: {
                GL11.glEnable(3008);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, var1);
                break;
            }
        }
        GL11.glEnable(3553);
    }
    
    public boolean isActive(final int var1) {
        return !this.timeBetween(var1, this.endFadeOut, this.startFadeIn);
    }
    
    private boolean timeBetween(final int var1, final int var2, final int var3) {
        return (var2 <= var3) ? (var1 >= var2 && var1 <= var3) : (var1 >= var2 || var1 <= var3);
    }
}
