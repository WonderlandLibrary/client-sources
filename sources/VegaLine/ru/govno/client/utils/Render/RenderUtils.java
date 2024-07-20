/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathHelper;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.GLUtil;
import ru.govno.client.utils.Render.GaussianBlur;
import ru.govno.client.utils.Render.ShaderUtility;
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.Render.Vec2fColored;
import ru.govno.client.utils.Render.glsandbox.animbackground;
import ru.govno.client.utils.Wrapper;

public class RenderUtils {
    protected static Minecraft mc = Minecraft.getMinecraft();
    private static final Frustum frustrum = new Frustum();
    private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
    private static final Vec3d LIGHT0_POS = new Vec3d(0.2f, 1.0, -0.7f).normalize();
    private static final Vec3d LIGHT1_POS = new Vec3d(-0.2f, 1.0, 0.7f).normalize();
    private static final Frustum frustum = new Frustum();
    public static ShaderUtility roundedShader = new ShaderUtility("roundedRect");
    public static ShaderUtility roundedOutlineShader = new ShaderUtility("roundRectOutline");
    public static Tessellator tessellator = Tessellator.getInstance();
    public static BufferBuilder buffer = tessellator.getBuffer();
    private static final ResourceLocation ITEM_WARN_DUR = new ResourceLocation("vegaline/system/durablitywarn/itemwarn.png");

    public static void anialisON(boolean line, boolean polygon, boolean point) {
        if (line) {
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
        }
        if (polygon) {
            GL11.glEnable(2881);
            GL11.glHint(3155, 4354);
        }
        if (point) {
            GL11.glEnable(2832);
            GL11.glHint(3153, 4354);
        }
    }

    public static void anialisOFF(boolean line, boolean polygon, boolean point) {
        if (line) {
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
        }
        if (polygon) {
            GL11.glHint(3155, 4352);
            GL11.glDisable(2881);
        }
        if (point) {
            GL11.glHint(3153, 4352);
            GL11.glDisable(2832);
        }
    }

    public static int red(int color) {
        return color >> 16 & 0xFF;
    }

    public static int green(int color) {
        return color >> 8 & 0xFF;
    }

    public static int blue(int color) {
        return color & 0xFF;
    }

    public static int alpha(int color) {
        return color >> 24 & 0xFF;
    }

    public static void drawClientHudRect3(float x, float y, float x2, float y2, float alphaPC, float extend) {
        int cli1 = ClientColors.getColor1(0, alphaPC);
        int cli2 = ClientColors.getColor2(-324, alphaPC);
        int cli3 = ClientColors.getColor2(0, alphaPC);
        int cli4 = ClientColors.getColor1(972, alphaPC);
        float alphaPCM = 0.3f;
        int cc1 = ColorUtils.getOverallColorFrom(ColorUtils.swapAlpha(cli1, (float)RenderUtils.alpha(cli1) * alphaPCM), ColorUtils.getColor(0, 0, 0, 95.0f * alphaPC / 2.55f), 0.2f);
        int cc2 = ColorUtils.getOverallColorFrom(ColorUtils.swapAlpha(cli2, (float)RenderUtils.alpha(cli2) * alphaPCM), ColorUtils.getColor(0, 0, 0, 95.0f * alphaPC / 2.55f), 0.2f);
        int cc3 = ColorUtils.getOverallColorFrom(ColorUtils.swapAlpha(cli3, (float)RenderUtils.alpha(cli3) * alphaPCM), ColorUtils.getColor(0, 0, 0, 95.0f * alphaPC / 2.55f), 0.75f);
        int cc4 = ColorUtils.getOverallColorFrom(ColorUtils.swapAlpha(cli4, (float)RenderUtils.alpha(cli4) * alphaPCM), ColorUtils.getColor(0, 0, 0, 95.0f * alphaPC / 2.55f), 0.75f);
        int cs1 = ColorUtils.getOverallColorFrom(cli1, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.1f);
        int cs2 = ColorUtils.getOverallColorFrom(cli2, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.1f);
        int cs5 = ColorUtils.getOverallColorFrom(cli3, cc2, 0.75f);
        int cs6 = ColorUtils.getOverallColorFrom(cli4, cc1, 0.75f);
        int cs7 = ColorUtils.getOverallColorFrom(cli3, cc3, 0.85f);
        int cs8 = ColorUtils.getOverallColorFrom(cli4, cc4, 0.85f);
        if (alphaPC >= 0.05f) {
            GaussianBlur.drawBlur(1.0f + alphaPC * 4.0f, () -> RenderUtils.drawRect(x, y + extend, x2, y2, -1));
        }
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + 0.5f, y + 0.5f + extend, x2 - 0.5f, y2 - 0.5f, 0.0f, 0.0f, cc1, cc2, cc3, cc4, false, true, false);
        RenderUtils.drawLightContureRectFullGradient(x + 0.5f, y + extend + 0.5f, x2 - 0.5f, y2 - 0.5f, cs6, cs5, cs7, cs8, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y + extend, 0.0f, 0.0f, cs1, cs2, cs5, cs6, false, true, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 0.0f, 6.0f, cc1, cc2, cc3, cc4, true, false, true);
    }

    public static void drawClientHudRect3(float x, float y, float x2, float y2, float alphaPC) {
        RenderUtils.drawClientHudRect3(x, y, x2, y2, alphaPC, 12.0f);
    }

    public static void drawClientHudRect4(float x, float y, float x2, float y2, float alphaPC, float extend) {
        int cli1 = ClientColors.getColor1(0, alphaPC);
        int cli2 = ClientColors.getColor2(-324, alphaPC);
        int cli3 = ClientColors.getColor2(0, alphaPC);
        int cli4 = ClientColors.getColor1(972, alphaPC);
        float alphaPCM = 0.25f;
        int clc1 = ClientColors.getColor1(0, alphaPC * alphaPCM);
        int clc2 = ClientColors.getColor2(-324, alphaPC * alphaPCM);
        int clc3 = ClientColors.getColor2(0, alphaPC * alphaPCM);
        int clc4 = ClientColors.getColor1(972, alphaPC * alphaPCM);
        int bgC = ColorUtils.swapAlpha(Integer.MIN_VALUE, 30.0f);
        float colAToB = 0.7f;
        int clb1 = ColorUtils.getOverallColorFrom(cli1, bgC, colAToB);
        int clb2 = ColorUtils.getOverallColorFrom(cli2, bgC, colAToB);
        int clb3 = ColorUtils.getOverallColorFrom(cli3, bgC, colAToB);
        int clb4 = ColorUtils.getOverallColorFrom(cli4, bgC, colAToB);
        RenderUtils.drawFullGradientRectPro(x, y, x2, y2, clb4, clb3, clb2, clb1, false);
        float lw = 0.5f;
        RenderUtils.drawAlphedVGradient(x, y, x + lw, y + (y2 - y) / 2.0f, ColorUtils.swapAlpha(cli1, 0.0f), ColorUtils.getOverallColorFrom(cli1, cli4), true);
        RenderUtils.drawAlphedVGradient(x, y + (y2 - y) / 2.0f, x + lw, y2, ColorUtils.getOverallColorFrom(cli1, cli4), ColorUtils.swapAlpha(cli4, 0.0f), true);
        RenderUtils.drawAlphedVGradient(x2 - lw, y, x2, y + (y2 - y) / 2.0f, ColorUtils.swapAlpha(cli2, 0.0f), ColorUtils.getOverallColorFrom(cli2, cli3), true);
        RenderUtils.drawAlphedVGradient(x2 - lw, y + (y2 - y) / 2.0f, x2, y2, ColorUtils.getOverallColorFrom(cli2, cli3), ColorUtils.swapAlpha(cli3, 0.0f), true);
        RenderUtils.drawAlphedSideways(x, y, x + (x2 - x) / 2.0f, y + lw, ColorUtils.swapAlpha(cli1, 0.0f), ColorUtils.getOverallColorFrom(cli1, cli2), true);
        RenderUtils.drawAlphedSideways(x + (x2 - x) / 2.0f, y, x2, y + lw, ColorUtils.getOverallColorFrom(cli1, cli2), ColorUtils.swapAlpha(cli2, 0.0f), true);
        RenderUtils.drawAlphedSideways(x, y2 - lw, x + (x2 - x) / 2.0f, y2, ColorUtils.swapAlpha(cli4, 0.0f), ColorUtils.getOverallColorFrom(cli4, cli3), true);
        RenderUtils.drawAlphedSideways(x + (x2 - x) / 2.0f, y2 - lw, x2, y2, ColorUtils.getOverallColorFrom(cli4, cli3), ColorUtils.swapAlpha(cli3, 0.0f), true);
        RenderUtils.drawRoundedFullGradientShadow(x, y, x2, y2, 0.0f, 7.0f, clc1, clc2, clc3, clc4, true);
        RenderUtils.glRenderStart();
        GL11.glEnable(2832);
        GL11.glPointSize(2.3f);
        GL11.glBegin(0);
        for (float i = x + 5.0f; i < x2 - 3.0f; i += 3.0f) {
            float wPC = (i - x) / (x2 - x);
            float wPCCenter = (wPC > 0.5f ? 1.0f - wPC : wPC) * 2.0f;
            int c = ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(cli1, cli4, extend / (y2 - y)), ColorUtils.getOverallColorFrom(cli2, cli3, extend / (y2 - y)), wPC);
            RenderUtils.glColor(ColorUtils.swapAlpha(c, (float)ColorUtils.getAlphaFromColor(c) * wPCCenter));
            GL11.glVertex2f(i, y + extend);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        RenderUtils.glRenderStop();
    }

    public static void drawClientHudRect4(float x, float y, float x2, float y2, float alphaPC) {
        RenderUtils.drawClientHudRect4(x, y, x2, y2, alphaPC, 13.0f);
    }

    public static void drawClientHudRect2(float x, float y, float x2, float y2, float alphaPC, float extend) {
        float extALL = 1.5f;
        float extY = extend - extALL;
        float extIns = -0.5f;
        int cli1 = ClientColors.getColor1(0, alphaPC);
        int cli2 = ClientColors.getColor2(-324, alphaPC);
        int cli3 = ClientColors.getColor2(0, alphaPC);
        int cli4 = ClientColors.getColor1(972, alphaPC);
        int cc1 = ColorUtils.getOverallColorFrom(cli1, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC / 2.55f), 0.5f);
        int cc2 = ColorUtils.getOverallColorFrom(cli2, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC / 2.55f), 0.5f);
        int cc3 = ColorUtils.getOverallColorFrom(cli3, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC / 2.55f), 0.65f);
        int cc4 = ColorUtils.getOverallColorFrom(cli4, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC / 2.55f), 0.65f);
        int cs1 = ColorUtils.getOverallColorFrom(cli1, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.1f);
        int cs2 = ColorUtils.getOverallColorFrom(cli2, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.1f);
        int cs3 = ColorUtils.getOverallColorFrom(cli3, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.45f);
        int cs4 = ColorUtils.getOverallColorFrom(cli4, ColorUtils.getColor(0, 0, 0, 160.0f * alphaPC), 0.45f);
        int cm = ColorUtils.getOverallColorFrom(cli1, cli2);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + extALL, y + extALL + extY, x2 - extALL, y2 - extALL, 3.0f, 0.0f, -1, -1, -1, -1, false, true, false);
        StencilUtil.readStencilBuffer(0);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 5.0f, 1.5f, cs1, cs2, cs3, cs4, false, true, true);
        StencilUtil.uninitStencilBuffer();
        RenderUtils.fullRoundFG(x + extALL + extIns, y + extALL + extY + extIns, x2 - extALL - extIns, y2 - extALL - extIns, 6.0f, cc1, cc2, cc3, cc4, false);
    }

    public static void drawClientHudRect2(float x, float y, float x2, float y2, float alphaPC) {
        RenderUtils.drawClientHudRect2(x, y, x2, y2, alphaPC, 13.0f);
    }

    public static void drawClientHudRect2(float x, float y, float x2, float y2) {
        RenderUtils.drawClientHudRect2(x, y, x2, y2, 1.0f);
    }

    public static void drawClientHudRect(float x, float y, float x2, float y2, float alphaPC) {
        int cli1 = ClientColors.getColor1(0, alphaPC);
        int cli2 = ClientColors.getColor2(-324, alphaPC);
        int cli3 = ClientColors.getColor2(0, alphaPC);
        int cli4 = ClientColors.getColor1(972, alphaPC);
        int cc1 = ColorUtils.getOverallColorFrom(cli1, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.7f);
        int cc2 = ColorUtils.getOverallColorFrom(cli2, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.7f);
        int cc3 = ColorUtils.getOverallColorFrom(cli3, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.75f);
        int cc4 = ColorUtils.getOverallColorFrom(cli4, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.75f);
        int cs1 = ColorUtils.getOverallColorFrom(cli1, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.4f);
        int cs2 = ColorUtils.getOverallColorFrom(cli2, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.4f);
        int cs3 = ColorUtils.getOverallColorFrom(cli3, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.7f);
        int cs4 = ColorUtils.getOverallColorFrom(cli4, ColorUtils.getColor(0, (int)(160.0f * alphaPC)), 0.7f);
        int cm = ColorUtils.getOverallColorFrom(cli1, cli2);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 2.375f, 0.5f, cc1, cc2, cc3, cc4, false, true, true);
        RenderUtils.drawRoundedFullGradientShadow(x, y, x2, y2, 2.5f, 7.5f, cs1, cs2, cs3, cs4, true);
        RenderUtils.drawRoundedFullGradientInsideShadow(x, y, x2, y2, 5.0f, cs1, cs2, cs3, cs4, true);
        RenderUtils.drawAlphedSideways(x + 2.0f, y + 1.5f, x + (x2 - x) / 2.0f, y + 3.0f, ColorUtils.swapAlpha(cli1, 0.0f), cm, true);
        RenderUtils.drawAlphedSideways(x + (x2 - x) / 2.0f, y + 1.5f, x2 - 2.0f, y + 3.0f, cm, ColorUtils.swapAlpha(cli2, 0.0f), true);
    }

    public static void drawClientHudRect(float x, float y, float x2, float y2) {
        RenderUtils.drawClientHudRect(x, y, x2, y2, 1.0f);
    }

    public static void hudRectWithString(float x, float y, float x2, float y2, String elementName, String renderMode, float alphaPC) {
        if (renderMode.isEmpty()) {
            return;
        }
        float extYText = 0.0f;
        switch (renderMode) {
            case "Glow": {
                RenderUtils.drawClientHudRect(x, y, x2, y2, alphaPC);
                extYText = 7.0f;
                break;
            }
            case "Window": {
                RenderUtils.drawClientHudRect2(x, y, x2, y2, alphaPC);
                extYText = 4.5f;
                break;
            }
            case "Plain": {
                RenderUtils.drawClientHudRect3(x, y, x2, y2, alphaPC);
                extYText = 4.0f;
                break;
            }
            case "Stipple": {
                RenderUtils.drawClientHudRect4(x, y, x2, y2, alphaPC);
                extYText = 4.5f;
                break;
            }
            default: {
                return;
            }
        }
        if (255.0f * alphaPC < 33.0f) {
            return;
        }
        int texCol = ColorUtils.swapAlpha(-1, 255.0f * alphaPC);
        Fonts.mntsb_16.drawStringWithShadow(elementName, x + 3.0f, y + extYText, texCol);
        texCol = ColorUtils.swapAlpha(-1, 65.0f * alphaPC);
        if (65.0f * alphaPC < 33.0f) {
            return;
        }
        String draw = null;
        switch (elementName) {
            case "Potions": {
                draw = "C";
                break;
            }
            case "Staff list": {
                draw = "B";
                break;
            }
            case "Keybinds": {
                draw = "L";
                break;
            }
            case "Pickups list": {
                draw = "M";
                break;
            }
            default: {
                return;
            }
        }
        if (draw != null) {
            Fonts.stylesicons_18.drawString(draw, x2 - 12.5f, y + extYText + 0.5f, texCol);
        }
    }

    public static void hudRectWithString(float x, float y, float x2, float y2, String elementName, String renderMode) {
        RenderUtils.hudRectWithString(x, y, x2, y2, elementName, renderMode, 1.0f);
    }

    public static final void setup3dForBlockPos(Runnable render, boolean bloom) {
        double glX = RenderManager.viewerPosX;
        double glY = RenderManager.viewerPosY;
        double glZ = RenderManager.viewerPosZ;
        GL11.glPushMatrix();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderUtils.mc.entityRenderer.disableLightmap();
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glShadeModel(7425);
        GL11.glTranslated(-glX, -glY, -glZ);
        render.run();
        GL11.glTranslated(glX, glY, glZ);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    public static void drawCircledTHud(float cx, double cy, float r, float percent, int color, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        cx *= 2.0f;
        cy *= 2.0;
        GlStateManager.glLineWidth(2.0f);
        float theta = 0.0175f;
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        int[] counter = new int[]{1};
        for (float ii = 0.0f; ii < 360.0f * percent; ii += 1.0f) {
            RenderUtils.setupColor(color, alpha);
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            counter[0] = counter[0] + 1;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
        RenderUtils.resetBlender();
        GL11.glPopMatrix();
    }

    public static void drawCircledTHudWithOverallColor(float cx, double cy, float r, float percent, int color, float alpha, float lineWidth, int color2, float pcColor2) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        cx *= 2.0f;
        cy *= 2.0;
        GlStateManager.glLineWidth(2.0f);
        float theta = 0.0175f;
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        int[] counter = new int[]{1};
        for (float ii = 0.0f; ii < 360.0f * percent; ii += 1.0f) {
            RenderUtils.setupColor(ColorUtils.getOverallColorFrom(color, color2, pcColor2), alpha);
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            counter[0] = counter[0] + 1;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
        RenderUtils.resetBlender();
        GL11.glPopMatrix();
    }

    public static void enableGUIStandardItemLighting() {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(165.0f, 1.0f, 0.0f, 0.0f);
        RenderUtils.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        GlStateManager.glLight(16384, 4611, RenderUtils.setColorBuffer(RenderUtils.LIGHT0_POS.xCoord, RenderUtils.LIGHT0_POS.yCoord, RenderUtils.LIGHT0_POS.zCoord, 0.0));
        GlStateManager.glLight(16384, 4609, RenderUtils.setColorBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        GlStateManager.glLight(16384, 4608, RenderUtils.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.glLight(16384, 4610, RenderUtils.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.glLight(16385, 4611, RenderUtils.setColorBuffer(RenderUtils.LIGHT1_POS.xCoord, RenderUtils.LIGHT1_POS.yCoord, RenderUtils.LIGHT1_POS.zCoord, 0.0));
        GlStateManager.glLight(16385, 4609, RenderUtils.setColorBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        GlStateManager.glLight(16385, 4608, RenderUtils.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.glLight(16385, 4610, RenderUtils.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GlStateManager.shadeModel(7424);
        GlStateManager.glLightModel(2899, RenderUtils.setColorBuffer(0.4f, 0.4f, 0.4f, 1.0f));
    }

    private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
        return RenderUtils.setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }

    public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
        COLOR_BUFFER.clear();
        COLOR_BUFFER.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        COLOR_BUFFER.flip();
        return COLOR_BUFFER;
    }

    public static void drawClientCircle(float cx, double cy, float r, float minus, float lineW, float alphaPC) {
        RenderUtils.enableGL2D();
        GL11.glPointSize(lineW);
        GL11.glEnable(2832);
        GL11.glBegin(0);
        int ii = 180;
        while ((float)ii <= minus + 180.0f) {
            RenderUtils.glColor(ClientColors.getColor1((ii += 6) * 3, 0.75f * alphaPC));
            double x1 = (double)cx + Math.sin((double)ii * Math.PI / 180.0) * (double)r;
            double y1 = cy + Math.cos((double)ii * Math.PI / 180.0) * (double)r;
            GL11.glVertex2d(x1, y1);
            GlStateManager.resetColor();
        }
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glPointSize(1.0f);
        RenderUtils.disableGL2D();
        GL11.glEnable(3042);
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
    }

    public static void drawClientCircleWithOverallToColor(float cx, double cy, float r, float minus, float lineW, float alphaPC, int color2, float pcColor2) {
        RenderUtils.enableGL2D();
        GL11.glPointSize(lineW);
        GL11.glEnable(2832);
        GL11.glBegin(0);
        int ii = 180;
        while ((float)ii <= minus + 180.0f) {
            RenderUtils.glColor(ColorUtils.getOverallColorFrom(ClientColors.getColor1((ii += 6) * 3, 0.75f * alphaPC), ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) * alphaPC), pcColor2));
            double x1 = (double)cx + Math.sin((double)ii * Math.PI / 180.0) * (double)r;
            double y1 = cy + Math.cos((double)ii * Math.PI / 180.0) * (double)r;
            GL11.glVertex2d(x1, y1);
            GlStateManager.resetColor();
        }
        GL11.glEnd();
        GL11.glDisable(2832);
        GL11.glPointSize(1.0f);
        RenderUtils.disableGL2D();
        GL11.glEnable(3042);
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
    }

    public static void drawClientCircle(float cx, double cy, float r, float minus, float lineW) {
        RenderUtils.drawClientCircle(cx, cy, r, minus, lineW, 1.0f);
    }

    public static void drawCanisterBox(AxisAlignedBB axisalignedbb, boolean outlineBox, boolean decussationBox, boolean fullBox, int outlineColor, int decussationColor, int fullColor) {
        AxisAlignedBB boundingBox = axisalignedbb;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.glLineWidth(0.01f);
        GL11.glDisable(3008);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (outlineBox) {
            RenderUtils.glColor(outlineColor);
            vertexbuffer.begin(2, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            tessellator.draw();
        }
        if (decussationBox) {
            RenderUtils.glColor(decussationColor);
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
            vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
            vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
            tessellator.draw();
        }
        if (fullBox) {
            RenderUtils.glColor(fullColor);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            tessellator.draw();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
            vertexbuffer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
            tessellator.draw();
        }
        GL11.glEnable(3008);
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    }

    public static void drawGradientAlphaBox(AxisAlignedBB bb, boolean outlineBox, boolean fullBox, int outlineColor, int fullColor) {
        AxisAlignedBB ab = bb;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glShadeModel(7425);
        GL11.glEnable(2848);
        double x1 = ab.minX;
        double y1 = ab.minY;
        double z1 = ab.minZ;
        double x2 = ab.maxX;
        double y2 = ab.maxY;
        double z2 = ab.maxZ;
        double wx = x2 - x1;
        double wy = y2 - y1;
        double wz = z2 - z1;
        if (outlineBox) {
            GlStateManager.glLineWidth(2.0f);
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(2);
            GL11.glVertex3d(x1, y1, z1);
            GL11.glVertex3d(x2, y1, z1);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glVertex3d(x1, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x2, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z1);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x1, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z2);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x2, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z2);
            GL11.glEnd();
        }
        if (fullBox) {
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z1);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x1, y1, z1 + wz / 2.0);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x2, y1, z1);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z1 + wz / 2.0);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z1 + wz / 2.0);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z2);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x1, y1, z1 + wz / 2.0);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glVertex3d(x2, y2, z1);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z1);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glVertex3d(x1, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x1, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z2);
            GL11.glVertex3d(x2, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x2, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z1);
            GL11.glVertex3d(x2, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glEnd();
        }
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.popMatrix();
    }

    public static void drawGradientAlphaBoxWithBooleanDownPool(AxisAlignedBB bb, boolean outlineBox, boolean fullBox, boolean downPull, int outlineColor, int fullColor) {
        AxisAlignedBB ab = bb;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glShadeModel(7425);
        GL11.glEnable(2848);
        double x1 = ab.minX;
        double y1 = ab.minY;
        double z1 = ab.minZ;
        double x2 = ab.maxX;
        double y2 = ab.maxY;
        double z2 = ab.maxZ;
        double wx = x2 - x1;
        double wy = y2 - y1;
        double wz = z2 - z1;
        if (outlineBox) {
            GlStateManager.glLineWidth(2.0f);
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(2);
            GL11.glVertex3d(x1, y1, z1);
            GL11.glVertex3d(x2, y1, z1);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glVertex3d(x1, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x2, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z1);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x1, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z2);
            GL11.glEnd();
            RenderUtils.glColor(outlineColor);
            GL11.glBegin(3);
            GL11.glVertex3d(x2, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z2);
            GL11.glEnd();
        }
        if (fullBox) {
            if (downPull) {
                RenderUtils.glColor(fullColor);
                GL11.glBegin(7);
                GL11.glVertex3d(x1, y1, z1);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1);
                RenderUtils.glColor(0);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
                RenderUtils.glColor(fullColor);
                GL11.glVertex3d(x1, y1, z1 + wz / 2.0);
                GL11.glEnd();
                RenderUtils.glColor(fullColor);
                GL11.glBegin(7);
                GL11.glVertex3d(x2, y1, z1);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1);
                RenderUtils.glColor(0);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
                RenderUtils.glColor(fullColor);
                GL11.glVertex3d(x2, y1, z1 + wz / 2.0);
                GL11.glEnd();
                RenderUtils.glColor(fullColor);
                GL11.glBegin(7);
                GL11.glVertex3d(x2, y1, z2);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z2);
                RenderUtils.glColor(0);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
                RenderUtils.glColor(fullColor);
                GL11.glVertex3d(x2, y1, z1 + wz / 2.0);
                GL11.glEnd();
                RenderUtils.glColor(fullColor);
                GL11.glBegin(7);
                GL11.glVertex3d(x1, y1, z2);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z2);
                RenderUtils.glColor(0);
                GL11.glVertex3d(x1 + wx / 2.0, y1, z1 + wz / 2.0);
                RenderUtils.glColor(fullColor);
                GL11.glVertex3d(x1, y1, z1 + wz / 2.0);
                GL11.glEnd();
            }
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glVertex3d(x2, y2, z1);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z1);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z1);
            GL11.glVertex3d(x1, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x1, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x1, y1, z2);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x1, y2, z2);
            GL11.glVertex3d(x2, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glEnd();
            RenderUtils.glColor(fullColor);
            GL11.glBegin(7);
            GL11.glVertex3d(x2, y1, z1);
            RenderUtils.glColor(0);
            GL11.glVertex3d(x2, y2, z1);
            GL11.glVertex3d(x2, y2, z2);
            RenderUtils.glColor(fullColor);
            GL11.glVertex3d(x2, y1, z2);
            GL11.glEnd();
        }
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.popMatrix();
    }

    public static void enableGL2D3() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D3() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        Minecraft mc = Minecraft.getMinecraft();
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        }
        return framebuffer;
    }

    public static boolean isInView(Entity ent) {
        frustum.setPosition(RenderUtils.mc.getRenderViewEntity().posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        return ent instanceof EntityPlayerSP || frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }

    public static void drawRound(float x, float y, float width, float height, float radius, int color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        roundedShader.init();
        ShaderUtility.setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
        roundedShader.setUniformi("blur", 0);
        roundedShader.setUniformf("color", ColorUtils.getGLRedFromColor(color), ColorUtils.getGLGreenFromColor(color), ColorUtils.getGLBlueFromColor(color), ColorUtils.getGLAlphaFromColor(color));
        ShaderUtility.drawQuads(x - 1.0f, y - 1.0f, width + 2.0f, height + 2.0f);
        roundedShader.unload();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, int color, int outlineColor) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GL11.glDisable(3008);
        roundedOutlineShader.init();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        ShaderUtility.setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
        if ((float)roundedOutlineShader.getUniform("outlineThickness") != outlineThickness * (float)ScaledResolution.getScaleFactor()) {
            roundedOutlineShader.setUniformf("outlineThickness", outlineThickness * (float)ScaledResolution.getScaleFactor());
        }
        roundedOutlineShader.setUniformColor("color", color);
        roundedOutlineShader.setUniformColor("outlineColor", outlineColor);
        ShaderUtility.drawQuads(x - (2.0f + outlineThickness), y - (2.0f + outlineThickness), width + (4.0f + outlineThickness * 2.0f), height + (4.0f + outlineThickness * 2.0f));
        roundedOutlineShader.unload();
        GL11.glEnable(3008);
    }

    public static void dispose() {
        GL11.glDisable(2960);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    public static void drawPlayerPing(float x, float y, EntityPlayer entity, float alpha) {
        if (Minecraft.player.connection.getPlayerInfo(entity.getUniqueID()) == null) {
            return;
        }
        Gui gui = new Gui();
        NetworkPlayerInfo networkPlayerInfoIn = Minecraft.player.connection.getPlayerInfo(entity.getUniqueID());
        ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
        RenderUtils.setupColor(ColorUtils.getFixedWhiteColor(), alpha);
        mc.getTextureManager().bindTexture(ICONS);
        boolean i = false;
        int j = networkPlayerInfoIn.getResponseTime() < 0 ? 5 : (networkPlayerInfoIn.getResponseTime() < 150 ? 0 : (networkPlayerInfoIn.getResponseTime() < 300 ? 1 : (networkPlayerInfoIn.getResponseTime() < 600 ? 2 : (networkPlayerInfoIn.getResponseTime() < 1000 ? 3 : 4))));
        GL11.glEnable(3042);
        GlStateManager.disableDepth();
        gui.zLevel += 100.0f;
        gui.drawTexturedModalRect(x, y, 0, 176 + j * 8, 10, 8);
        gui.zLevel -= 100.0f;
        GlStateManager.enableDepth();
        GlStateManager.resetColor();
    }

    public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
        double i;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(9);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 3.0) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 90.0; i <= 180.0; i += 3.0) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 180.0; i <= 270.0; i += 3.0) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius);
        }
        for (i = 270.0; i <= 360.0; i += 3.0) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
    }

    public static void erase(boolean invert) {
        GL11.glStencilFunc(invert ? 514 : 517, 1, 65535);
        GL11.glStencilOp(7680, 7680, 7681);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glAlphaFunc(516, 0.0f);
    }

    public static void write(boolean renderClipLayer) {
        RenderUtils.checkSetupFBO1();
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, 65535);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.colorMask(false, false, false, false);
        }
    }

    public static void write(boolean renderClipLayer, Framebuffer fb, boolean clearStencil, boolean invert) {
        RenderUtils.checkSetupFBO(fb);
        if (clearStencil) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
            GL11.glEnable(2960);
        }
        GL11.glStencilFunc(519, invert ? 0 : 1, 65535);
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.colorMask(false, false, false, false);
        }
    }

    public static void checkSetupFBO1() {
        Framebuffer fbo = mc.getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            RenderUtils.setupFBO1(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void checkSetupFBO(Framebuffer fbo) {
        if (fbo != null && fbo.depthBuffer > -1) {
            RenderUtils.setupFBO1(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void setupFBO1(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }

    public static void drawTexture(ResourceLocation rs, double f, double f2, double f3, double f4, double f5, double f6, double f7, double f8) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.99f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(rs);
        double f9 = 1.0 / f7;
        double f10 = 1.0 / f8;
        Tessellator bly2 = Tessellator.getInstance();
        BufferBuilder ali2 = bly2.getBuffer();
        ali2.begin(6, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        ali2.pos(f, f2 + f6, 0.0).tex(f3 * f9, (f4 + f6) * f10).endVertex();
        ali2.pos(f + f5, f2 + f6, 0.0).tex((f3 + f5) * f9, (f4 + f6) * f10).endVertex();
        ali2.pos(f + f5, f2, 0.0).tex((f3 + f5) * f9, f4 * f10).endVertex();
        ali2.pos(f, f2, 0.0).tex(f3 * f9, f4 * f10).endVertex();
        bly2.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    public static void drawWaveGradient(float x, float y, float x2, float y2, float aPC, int colorStep1, int colorStep2, int colorStep3, int colorStep4, boolean blend, boolean toNull) {
        float rect1X1 = x;
        float rect1X2 = x + (x2 - x) / 3.0f;
        float rect1Y1 = y;
        float rect1Y2 = y2;
        float rect2X1 = rect1X2;
        float rect2X2 = x + (x2 - x) / 3.0f * 2.0f;
        float rect2Y1 = y;
        float rect2Y2 = y2;
        float rect3X1 = x + (x2 - x) / 3.0f * 2.0f;
        float rect3X2 = x2;
        float rect3Y1 = y;
        float rect3Y2 = y2;
        float bright = aPC;
        int c1 = ColorUtils.getOverallColorFrom(colorStep1, ColorUtils.swapAlpha(-1, bright * (float)ColorUtils.getAlphaFromColor(colorStep1)), bright);
        int c2 = ColorUtils.getOverallColorFrom(colorStep2, ColorUtils.swapAlpha(-1, bright * (float)ColorUtils.getAlphaFromColor(colorStep2)), bright);
        int c3 = ColorUtils.getOverallColorFrom(colorStep3, ColorUtils.swapAlpha(-1, bright * (float)ColorUtils.getAlphaFromColor(colorStep3)), bright);
        int c4 = ColorUtils.getOverallColorFrom(colorStep4, ColorUtils.swapAlpha(-1, bright * (float)ColorUtils.getAlphaFromColor(colorStep4)), bright);
        RenderUtils.drawFullGradientRectPro(rect1X2, rect1Y2, rect1X1, rect1Y1, toNull ? 0 : colorStep2, toNull ? 0 : colorStep1, c1, c2, blend);
        RenderUtils.drawFullGradientRectPro(rect2X2, rect2Y2, rect2X1, rect2Y1, toNull ? 0 : colorStep3, toNull ? 0 : colorStep2, c2, c3, blend);
        RenderUtils.drawFullGradientRectPro(rect3X2, rect3Y2, rect3X1, rect3Y1, toNull ? 0 : colorStep4, toNull ? 0 : colorStep3, c3, c4, blend);
    }

    public static void drawCircleAkrien(float cx, double cy, float radius, float c360, boolean astolfo, float lineWidthPercent) {
        GL11.glPushMatrix();
        RenderUtils.customRotatedObject2D(cx, (float)cy, 0.0f, 0.0f, -90.0f);
        cx *= 2.0f;
        cy *= 2.0;
        GlStateManager.glLineWidth(2.5f * lineWidthPercent);
        float theta = -0.0175f;
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = radius *= 2.0f;
        float y = 0.0f;
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glBegin(3);
        for (float ii = 0.0f; ii < c360; ii += 1.0f) {
            int color = ColorUtils.getColor(42, 42, 42);
            if (astolfo) {
                color = ColorUtils.astolfoColorsCool(1, 1000 + (int)(ii / 2.2f));
            }
            RenderUtils.glColor(color);
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }

    public static void drawCircle2D(float cx, double cy, float radius, float c360, int color, float lineWidth, boolean astolfo) {
        GL11.glPushMatrix();
        RenderUtils.customRotatedObject2D(cx, (float)cy, 0.0f, 0.0f, -90.0f);
        cx *= 2.0f;
        cy *= 2.0;
        float theta = -0.0175f;
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = radius *= 2.0f;
        float y = 0.0f;
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GlStateManager.glLineWidth(lineWidth);
        GL11.glBegin(3);
        for (float ii = 0.0f; ii < c360; ii += 6.0f) {
            RenderUtils.glColor(astolfo ? ColorUtils.swapAlpha(ColorUtils.astolfoColorsCool(1, 1000 + (int)(ii / 2.2f)), ColorUtils.getAlphaFromColor(color)) : color);
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            for (float i = 0.0f; i < 6.0f; i += 1.0f) {
                x = p * x - s * y;
                y = s * t + p * y;
            }
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }

    public static void drawSome(List<Vec2f> pos, int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(9);
        for (Vec2f vec2f : pos) {
            GL11.glVertex2f(vec2f.x, vec2f.y);
        }
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawSome(List<Vec2f> pos, int color, int begin) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        RenderUtils.glColor(color);
        GL11.glBegin(begin);
        for (Vec2f vec2f : pos) {
            GL11.glVertex2f(vec2f.x, vec2f.y);
        }
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawSome2(List<net.minecraft.util.math.Vec2f> pos, int color, int begin) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        RenderUtils.glColor(color);
        GL11.glBegin(begin);
        for (net.minecraft.util.math.Vec2f vec2f : pos) {
            GL11.glVertex2f(vec2f.x, vec2f.y);
        }
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawVec2Colored(List<Vec2fColored> pos) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        RenderUtils.anialisON(false, true, false);
        GL11.glBegin(9);
        for (Vec2fColored vec : pos) {
            RenderUtils.glColor(vec.getColor());
            GL11.glVertex2f(vec.getXY()[0], vec.getXY()[1]);
        }
        GL11.glEnd();
        RenderUtils.anialisOFF(false, true, false);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    public static void drawPolygonPartsGlowBackSAlpha(double x, double y, int radius, int part, int color, int endcolor, float Alpha) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha1 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red1 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green1 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue1 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, Alpha).endVertex();
        double TWICE_PI = Math.PI * 8;
        for (int i = part * 90; i <= part * 90 + 90; i += 6) {
            double angle = TWICE_PI * (double)i / 360.0 + Math.toRadians(30.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red1, green1, blue1, alpha1).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonPartsGlowBackSAlpha(double x, double y, float radius, int part, int color, int endcolor, float Alpha, boolean bloom) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha1 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red1 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green1 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue1 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, Alpha).endVertex();
        double TWICE_PI = Math.PI * 8;
        for (int i = part * 90; i <= part * 90 + 90; i += 6) {
            double angle = TWICE_PI * (double)i / 360.0 + Math.toRadians(30.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red1, green1, blue1, alpha1).endVertex();
        }
        tessellator.draw();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void fixShadows() {
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    public static void drawFullGradientRect(float x, float y, float w, float h, int color, int color2, int color3, int color4) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableTexture2D();
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        GL11.glBegin(7);
        RenderUtils.glColor(color);
        GL11.glVertex2f(x, y + h);
        RenderUtils.glColor(color2);
        GL11.glVertex2f(x + w, y + h);
        RenderUtils.glColor(color3);
        GL11.glVertex2f(x + w, y);
        RenderUtils.glColor(color4);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        RenderUtils.resetColor();
    }

    public static void drawFullGradientRectPro(float x, float y, float x2, float y2, int color, int color2, int color3, int color4, boolean blend) {
        GlStateManager.enableBlend();
        if (blend) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.disableTexture2D();
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x2, y, 0.0).color(color3).endVertex();
        buffer.pos(x, y, 0.0).color(color4).endVertex();
        buffer.pos(x, y2, 0.0).color(color).endVertex();
        buffer.pos(x2, y2, 0.0).color(color2).endVertex();
        tessellator.draw();
        if (blend) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
    }

    public static void resetBlender() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableTexture2D();
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public static void drawMinecraftRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 300.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, top, 300.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, 300.0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(right, bottom, 300.0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static Vec3d getRenderPos(double x, double y, double z) {
        return new Vec3d(x -= RenderManager.viewerPosX, y -= RenderManager.viewerPosY, z -= RenderManager.viewerPosZ);
    }

    public static void drawBorderedRect(float x, float y, float width, float height, float outlineThickness, int rectColor, int outlineColor) {
        Gui.drawRect2(x, y, width, height, rectColor);
        GL11.glEnable(2848);
        GLUtil.setup2DRendering(() -> {
            RenderUtils.color(outlineColor);
            GL11.glLineWidth(outlineThickness);
            float cornerValue = (float)((double)outlineThickness * 0.19);
            GLUtil.render(1, () -> {
                GL11.glVertex2d(x, y - cornerValue);
                GL11.glVertex2d(x, y + height + cornerValue);
                GL11.glVertex2d(x + width, y + height + cornerValue);
                GL11.glVertex2d(x + width, y - cornerValue);
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x + width, y);
                GL11.glVertex2d(x, y + height);
                GL11.glVertex2d(x + width, y + height);
            });
        });
        GL11.glDisable(2848);
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
        RenderUtils.drawAlphedRect(x + radius / 2.0f, y + radius / 2.0f, x + width - radius / 2.0f, y + height - radius / 2.0f, color);
    }

    public static void fullRoundFG(float x, float y, float x2, float y2, float r, int c, int c2, int c3, int c4, boolean bloom) {
        RenderUtils.drawFullGradientRectPro(x + r / 2.0f, y + r / 2.0f, x2 - r / 2.0f, y2 - r / 2.0f, c4, c3, c2, c, bloom);
        RenderUtils.drawFullGradientRectPro(x + r / 2.0f, y, x2 - r / 2.0f, y + r / 2.0f, c, c2, c2, c, bloom);
        RenderUtils.drawFullGradientRectPro(x + r / 2.0f, y2 - r / 2.0f, x2 - r / 2.0f, y2, c4, c3, c3, c4, bloom);
        RenderUtils.drawFullGradientRectPro(x, y + r / 2.0f, x + r / 2.0f, y2 - r / 2.0f, c4, c4, c, c, bloom);
        RenderUtils.drawFullGradientRectPro(x2 - r / 2.0f, y + r / 2.0f, x2, y2 - r / 2.0f, c3, c3, c2, c2, bloom);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x, y + r / 2.0f, x2, y2 - r / 2.0f, -1);
        RenderUtils.drawRect(x + r / 2.0f, y, x2 - r / 2.0f, y2, -1);
        StencilUtil.readStencilBuffer(0);
        RenderUtils.drawSmoothCircle(x + r / 2.0f, y + r / 2.0f + 0.125f, r / 2.0f, c, bloom);
        RenderUtils.drawSmoothCircle(x2 - r / 2.0f, y + r / 2.0f + 0.125f, r / 2.0f, c2, bloom);
        RenderUtils.drawSmoothCircle(x2 - r / 2.0f, y2 - r / 2.0f + 0.125f, r / 2.0f, c3, bloom);
        RenderUtils.drawSmoothCircle(x + r / 2.0f, y2 - r / 2.0f + 0.125f, r / 2.0f, c4, bloom);
        StencilUtil.uninitStencilBuffer();
    }

    public static void drawSmoothCircle(double x, double y, float radius, int color) {
        RenderUtils.runGLColor(color);
        RenderUtils.setup2D(() -> {
            GL11.glDisable(3008);
            GL11.glEnable(2832);
            GL11.glPointSize(radius * (float)(2 * Minecraft.getMinecraft().gameSettings.guiScale));
            RenderUtils.renderObj(0, () -> GL11.glVertex2d(x, y));
            GL11.glEnable(3008);
        });
        GlStateManager.resetColor();
    }

    public static void drawSmoothCircle(double x, double y, float radius, int color, boolean bloom) {
        RenderUtils.runGLColor(color);
        RenderUtils.setup2D(() -> {
            if (bloom) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }
            GL11.glDisable(3008);
            GL11.glEnable(2832);
            GL11.glHint(3153, 4354);
            ScaledResolution rs = new ScaledResolution(mc);
            float scale = (float)((double)ScaledResolution.getScaleFactor() / Math.pow(ScaledResolution.getScaleFactor(), 2.0));
            GL11.glPointSize(radius / scale * 2.0f);
            RenderUtils.renderObj(0, () -> GL11.glVertex2d(x, y));
            GL11.glEnable(3008);
            if (bloom) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }
        });
    }

    public static void drawCroneRect(double x, double y, double width, double height, int color) {
        RenderUtils.resetColor();
        RenderUtils.setup2D(() -> RenderUtils.renderObj(7, () -> {
            RenderUtils.runGLColor(color);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x, y + height);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x + width, y);
        }));
    }

    public static void setup2D(Runnable f) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        f.run();
        GL11.glEnable(3553);
    }

    public static void renderObj(int mode, Runnable render) {
        GL11.glBegin(mode);
        render.run();
        GL11.glEnd();
    }

    public static void runGLColor(int orRGB) {
        float c1 = (float)(orRGB >> 16 & 0xFF) / 255.0f;
        float c2 = (float)(orRGB >> 8 & 0xFF) / 255.0f;
        float c3 = (float)(orRGB & 0xFF) / 255.0f;
        float c4 = (float)(orRGB >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(c1, c2, c3, c4);
    }

    public static void scale(float x, float y, float scale, Runnable data) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0f);
        GL11.glScalef(scale, scale, 1.0f);
        GL11.glTranslatef(-x, -y, 0.0f);
        data.run();
        GL11.glPopMatrix();
    }

    public static void scaleStart(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(scale, scale, 1.0f);
        GlStateManager.translate(-x, -y, 0.0f);
    }

    public static void scaleEnd() {
        GlStateManager.popMatrix();
    }

    public static void fakeCircleGlow(float posX, float posY, float radius, Color color, float maxAlpha) {
        RenderUtils.setAlphaLimit(0.0f);
        GL11.glShadeModel(7425);
        GLUtil.setup2DRendering(() -> GLUtil.render(6, () -> {
            RenderUtils.color(color.getRGB(), maxAlpha);
            GL11.glVertex2d(posX, posY);
            RenderUtils.color(color.getRGB(), 0.0f);
            for (int i = 0; i <= 100; i += 2) {
                double angle = (double)i * 0.06283 + 3.1415;
                double x2 = Math.sin(angle) * (double)radius;
                double y2 = Math.cos(angle) * (double)radius;
                GL11.glVertex2d((double)posX + x2, (double)posY + y2);
            }
        }));
        GL11.glShadeModel(7424);
        RenderUtils.setAlphaLimit(1.0f);
    }

    public static double animate(double endPoint, double current, double speed) {
        boolean shouldContinueAnimation;
        boolean bl = shouldContinueAnimation = endPoint > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        double factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : -factor);
    }

    public static void drawCircleNotSmooth(double x, double y, double radius, int color) {
        radius /= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        RenderUtils.color(color);
        GL11.glBegin(6);
        for (double i = 0.0; i <= 30.0; i += 3.0) {
            double angle = i * 0.01745 * 12.0;
            GL11.glVertex2d(x + radius * Math.cos(angle) + radius, y + radius * Math.sin(angle) + radius);
        }
        GL11.glEnd();
        GL11.glEnable(2884);
        GL11.glEnable(3553);
    }

    public static void scissor(double x, double y, double width, double height, Runnable data) {
        GL11.glEnable(3089);
        RenderUtils.scissor(x, y, width, height);
        data.run();
        GL11.glDisable(3089);
    }

    public static void scissor(double x, double y, double width, double height) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        double scale = ScaledResolution.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = ((double)sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        GL11.glScissor((int)finalX, (int)(finalY - finalHeight), (int)finalWidth, (int)finalHeight);
    }

    public static void scissorRected(double x, double y, double x2, double y2) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        double scale = ScaledResolution.getScaleFactor();
        double finalHeight = (y2 - y) * scale;
        double finalY = ((double)sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = (x2 - y) * scale;
        GL11.glScissor((int)finalX, (int)(finalY - finalHeight), (int)finalWidth, (int)finalHeight);
    }

    public static void scissorCoord(double x, double y, double x2, double y2) {
        Minecraft mc = Minecraft.getMinecraft();
        double xPos1 = x < x2 ? x : x2;
        double xPos2 = x2 > x ? x2 : x;
        double yPos1 = y < y2 ? y : y2;
        double yPos2 = y2 > y ? y2 : y;
        GL11.glScissor((int)xPos1, (int)xPos2, (int)yPos1, (int)yPos2);
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float)((double)limit * 0.01));
    }

    public static void color(int color, float alpha) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.color(r, g, b, alpha);
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static void resetColor() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static void drawLightShot(float x, float y, int colorIn, int colorOut) {
        RenderUtils.glRenderStart();
        GL11.glEnable(2848);
        RenderUtils.setupColor(colorIn, colorOut);
        GL11.glTranslated(x, y, 0.0);
        GL11.glBegin(7);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d(5.0, 6.0);
        GL11.glVertex2d(-5.0, -6.0);
        GL11.glVertex2d(0.0, 6.0);
        GL11.glEnd();
        GL11.glTranslated(-1.0, 9.5, 0.0);
        GL11.glBegin(7);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d(-5.0, -6.0);
        GL11.glVertex2d(5.0, 6.0);
        GL11.glVertex2d(0.0, -6.0);
        GL11.glEnd();
        GL11.glDisable(2848);
        RenderUtils.glRenderStop();
    }

    public static void drawBorder(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        RenderUtils.drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0), top - (!borderIncludedInBounds ? borderWidth : 0.0), right + (!borderIncludedInBounds ? borderWidth : 0.0), bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
        RenderUtils.drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0), top + (borderIncludedInBounds ? borderWidth : 0.0), right - (borderIncludedInBounds ? borderWidth : 0.0), bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }

    public static void renderOne() {
        RenderUtils.checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(3.0f);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderFour() {
        RenderUtils.setColor(new Color(255, 255, 255));
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }

    public static void scissorRect(float x, float y, float width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        int factor = ScaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * (float)factor), (int)(((double)sr.getScaledHeight() - height) * (double)factor), (int)((width - x) * (float)factor), (int)((height - (double)y) * (double)factor));
    }

    public static void setColor(Color c) {
        GL11.glColor4d((float)c.getRed() / 255.0f, (float)c.getGreen() / 255.0f, (float)c.getBlue() / 255.0f, (float)c.getAlpha() / 255.0f);
    }

    public static void drawSkeetRect(float x, float y, float right, float bottom) {
        RenderUtils.drawRect(x - 46.5f, y - 66.5f, right + 46.5f, bottom + 66.5f, new Color(0, 0, 0, 255).getRGB());
        RenderUtils.drawRect(x - 46.0f, y - 66.0f, right + 46.0f, bottom + 66.0f, new Color(48, 48, 48, 255).getRGB());
        RenderUtils.drawRect(x - 44.5f, y - 64.5f, right + 44.5f, bottom + 64.5f, new Color(33, 33, 33, 255).getRGB());
        RenderUtils.drawRect(x - 43.5f, y - 63.5f, right + 43.5f, bottom + 63.5f, new Color(0, 0, 0, 255).getRGB());
        RenderUtils.drawRect(x - 43.0f, y - 63.0f, right + 43.0f, bottom + 63.0f, new Color(9, 9, 9, 255).getRGB());
        RenderUtils.drawRect(x - 40.5f, y - 60.5f, right + 40.5f, bottom + 60.5f, new Color(48, 48, 48, 255).getRGB());
        RenderUtils.drawRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }

    public static void drawSkeetButton(float x, float y, float right, float bottom) {
        RenderUtils.drawRect(x - 31.0f, y - 43.0f, right + 31.0f, bottom - 30.0f, new Color(0, 0, 0, 255).getRGB());
        RenderUtils.drawRect(x - 30.5f, y - 42.5f, right + 30.5f, bottom - 30.5f, new Color(45, 45, 45, 255).getRGB());
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            RenderUtils.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void drawPolygonParts(double x, double y, int radius, int part, int color, int endcolor) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha1 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red1 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green1 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue1 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; i += 6) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red1, green1, blue1, alpha1).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonParts(double x, double y, float radius, int part, int color, int endcolor, boolean bloom) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(color).endVertex();
        double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; i += 18) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(endcolor).endVertex();
        }
        tessellator.draw();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawTenasityRect(float left, float top, float right, float bottom, float smoth, int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RenderUtils.drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtils.drawPolygonParts(left * 2.0f, top * 2.0f, (int)smoth, 0, color, color);
        RenderUtils.drawPolygonParts(left * 2.0f, bottom * 2.0f - smoth, (int)smoth, 5, color, color);
        RenderUtils.drawPolygonParts(right * 2.0f, top * 2.0f, (int)smoth, 7, color, color);
        RenderUtils.drawPolygonParts(right * 2.0f, bottom * 2.0f - smoth, (int)smoth, 6, color, color);
        RenderUtils.drawAlphedRect(left * 2.0f - smoth, top * 2.0f, left * 2.0f, bottom * 2.0f - smoth, color);
        RenderUtils.drawAlphedRect(left * 2.0f, top * 2.0f - smoth, right * 2.0f, top * 2.0f, color);
        RenderUtils.drawAlphedRect(right * 2.0f, top * 2.0f, right * 2.0f + smoth, bottom * 2.0f - smoth, color);
        RenderUtils.drawAlphedRect(left * 2.0f, bottom * 2.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawCircledTo(float r, int c) {
        GL11.glPushMatrix();
        GlStateManager.glLineWidth(2.0f);
        float theta = 0.0175f;
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        RenderUtils.enableGL2D();
        GL11.glDisable(3008);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glBegin(3);
        for (int ii = 0; ii < 90; ++ii) {
            RenderUtils.glColor(c);
            GL11.glVertex2f(x, (float)((double)y));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3008);
        RenderUtils.disableGL2D();
        GlStateManager.resetColor();
        GlStateManager.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }

    public static void roundedFullRoundedOutline(float x, float y, float x2, float y2, float round1, float round2, float round3, float round4, int color) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + round1, y + round1, 0.0);
        GL11.glRotated(-180.0, 0.0, 0.0, 180.0);
        RenderUtils.drawCircledTo(round1, color);
        RenderUtils.fixShadows();
        GL11.glRotated(180.0, 0.0, 0.0, -180.0);
        GL11.glTranslated(-x - round1, -y - round1, 0.0);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x2 - round2, y + round2, 0.0);
        GL11.glRotated(-90.0, 0.0, 0.0, 90.0);
        RenderUtils.drawCircledTo(round2, color);
        RenderUtils.fixShadows();
        GL11.glRotated(90.0, 0.0, 0.0, -90.0);
        GL11.glTranslated(-x2 + round2, -y - round2, 0.0);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x2 - round3, y2 - round3, 0.0);
        GL11.glRotated(-360.0, 0.0, 0.0, 360.0);
        RenderUtils.drawCircledTo(round3, color);
        RenderUtils.fixShadows();
        GL11.glRotated(360.0, 0.0, 0.0, -360.0);
        GL11.glTranslated(-x2 + round3, -y2 + round3, 0.0);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x + round4, y2 - round4, 0.0);
        GL11.glRotated(-270.0, 0.0, 0.0, 270.0);
        RenderUtils.drawCircledTo(round4, color);
        RenderUtils.fixShadows();
        GL11.glRotated(270.0, 0.0, 0.0, -270.0);
        GL11.glTranslated(-x - round4, -y2 + round4, 0.0);
        GL11.glPopMatrix();
        RenderUtils.drawAlphedRect(x + round1 - 1.0f, y - 0.5f, x2 - round2, y + 0.5f, color);
        RenderUtils.drawAlphedRect(x2 - 0.5f, y + round2 - 1.0f, x2 + 0.5f, y2 - round3, color);
        RenderUtils.drawAlphedRect(x - 0.5f, y + round1, x + 0.5f, y2 - round4, color);
        RenderUtils.drawAlphedRect(x + round4, y2 - 0.5f, x2 - round3 + 1.0f, y2 + 0.5f, color);
    }

    public static void drawAlphedVGradient(double x, double y, double x2, double y2, int col1, int col2) {
        RenderUtils.drawAlphedVGradient(x, y, x2, y2, col1, col2, false);
    }

    public static void drawAlphedVGradient(double x, double y, double x2, double y2, int col1, int col2, boolean bloom) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, bloom ? 32772 : 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        if (bloom) {
            GL11.glBlendFunc(770, 771);
        }
        GL11.glEnable(3008);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawRoundedFullGradient(float left, float top, float right, float bottom, float smoth, int color, int color2, int color3, int color4, boolean bloom) {
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RenderUtils.drawFullGradientRect(left, top, right - left, bottom - top - smoth / 2.0f, color3, color4, color2, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtils.drawPolygonParts(left * 2.0f, top * 2.0f, (int)smoth, 0, color, color);
        RenderUtils.drawPolygonParts(left * 2.0f, bottom * 2.0f - smoth, (int)smoth, 5, color, color3);
        RenderUtils.drawPolygonParts(right * 2.0f, top * 2.0f, (int)smoth, 7, color, color2);
        RenderUtils.drawPolygonParts(right * 2.0f, bottom * 2.0f - smoth, (int)smoth, 6, color, color4);
        RenderUtils.drawAlphedVGradient(left * 2.0f - smoth, top * 2.0f, left * 2.0f, bottom * 2.0f - smoth, color, color3);
        RenderUtils.drawAlphedSideways(left * 2.0f, top * 2.0f - smoth, right * 2.0f, top * 2.0f, color, color2);
        RenderUtils.drawAlphedVGradient(right * 2.0f, top * 2.0f, right * 2.0f + smoth, bottom * 2.0f - smoth, color2, color4);
        RenderUtils.drawAlphedSideways(left * 2.0f, bottom * 2.0f - smoth, right * 2.0f, bottom * 2.0f, color3, color4);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawRoundedFullGradientPro(float left, float top, float right, float bottom, float smoth, int color, int color2, int color3, int color4, boolean bloom) {
        left += smoth;
        right -= smoth;
        top += smoth;
        GL11.glPushMatrix();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RenderUtils.drawFullGradientRect(left, top, right - left, bottom - top - smoth / 2.0f, color3, color4, color2, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtils.drawPolygonParts(left * 2.0f, top * 2.0f, (int)smoth, 0, color, color);
        RenderUtils.drawPolygonParts(left * 2.0f, bottom * 2.0f - smoth, (int)smoth, 5, color, color3);
        RenderUtils.drawPolygonParts(right * 2.0f, top * 2.0f, (int)smoth, 7, color, color2);
        RenderUtils.drawPolygonParts(right * 2.0f, bottom * 2.0f - smoth, (int)smoth, 6, color, color4);
        RenderUtils.drawAlphedVGradient(left * 2.0f - smoth, top * 2.0f, left * 2.0f, bottom * 2.0f - smoth, color, color3);
        RenderUtils.drawAlphedSideways(left * 2.0f, top * 2.0f - smoth, right * 2.0f, top * 2.0f, color, color2);
        RenderUtils.drawAlphedVGradient(right * 2.0f, top * 2.0f, right * 2.0f + smoth, bottom * 2.0f - smoth, color2, color4);
        RenderUtils.drawAlphedSideways(left * 2.0f, bottom * 2.0f - smoth, right * 2.0f, bottom * 2.0f, color3, color4);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    public static void drawRoundedFullGradient(float left, float top, float right, float bottom, float smoth, int color, int color2, boolean bloom) {
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        RenderUtils.drawFullGradientRect(left, top, right - left, bottom - top - smoth / 2.0f, color2, color, color2, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtils.drawPolygonParts(left * 2.0f, top * 2.0f, (int)smoth, 0, color, color);
        RenderUtils.drawPolygonParts(left * 2.0f, bottom * 2.0f - smoth, (int)smoth, 5, color, color2);
        RenderUtils.drawPolygonParts(right * 2.0f, top * 2.0f, (int)smoth, 7, color, color2);
        RenderUtils.drawPolygonParts(right * 2.0f, bottom * 2.0f - smoth, (int)smoth, 6, color, color);
        RenderUtils.drawAlphedVGradient(left * 2.0f - smoth, top * 2.0f, left * 2.0f, bottom * 2.0f - smoth, color, color2);
        RenderUtils.drawAlphedSideways(left * 2.0f, top * 2.0f - smoth, right * 2.0f, top * 2.0f, color, color2);
        RenderUtils.drawAlphedVGradient(right * 2.0f, top * 2.0f, right * 2.0f + smoth, bottom * 2.0f - smoth, color2, color);
        RenderUtils.drawAlphedSideways(left * 2.0f, bottom * 2.0f - smoth, right * 2.0f, bottom * 2.0f, color2, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawRoundedFullGradientRectPro(float x, float y, float x2, float y2, float round, int color, int color2, int color3, int color4, boolean bloom) {
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.drawFullGradientRectPro(x + round / 2.0f, y + round / 2.0f, x2 - round / 2.0f, y2 - round / 2.0f, color4, color3, color2, color, bloom);
        RenderUtils.drawPolygonParts(x + round / 2.0f, y + round / 2.0f, round / 2.0f, 0, color, color, bloom);
        RenderUtils.drawPolygonParts(x + round / 2.0f, y2 - round / 2.0f, round / 2.0f, 5, color4, color4, bloom);
        RenderUtils.drawPolygonParts(x2 - round / 2.0f, y + round / 2.0f, round / 2.0f, 7, color2, color2, bloom);
        RenderUtils.drawPolygonParts(x2 - round / 2.0f, y2 - round / 2.0f, round / 2.0f, 6, color3, color3, bloom);
        RenderUtils.drawBloomedFullGradientRect(x, y + round / 2.0f, x + round / 2.0f, y2 - round / 2.0f, color4, color4, color, color, bloom);
        RenderUtils.drawBloomedFullGradientRect(x + round / 2.0f, y, x2 - round / 2.0f, y + round / 2.0f, color, color2, color2, color, bloom);
        RenderUtils.drawBloomedFullGradientRect(x2 - round / 2.0f, y + round / 2.0f, x2, y2 - round / 2.0f, color3, color3, color2, color2, bloom);
        RenderUtils.drawBloomedFullGradientRect(x + round / 2.0f, y2 - round / 2.0f, x2 - round / 2.0f, y2, color4, color3, color3, color4, bloom);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawTenasityGlichOBF(float left, float top, float right, float bottom, float smoth, int color, int twocolor, int threecolor, int four) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(3.75f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtils.drawPolygonPartss(left * 2.0f, bottom * 2.0f - smoth, (int)smoth, 5, color, color);
        RenderUtils.drawPolygonPartss(left * 2.0f, top * 2.0f, (int)smoth, 0, four, four);
        RenderUtils.drawPolygonPartss(right * 2.0f, top * 2.0f, (int)smoth, 7, color, color);
        RenderUtils.drawPolygonPartss(right * 2.0f, bottom * 2.0f - smoth, (int)smoth, 6, four, four);
        RenderUtils.drawGlichRect3OBF(left * 2.0f - smoth, top * 2.0f, left * 2.0f - smoth, bottom * 2.0f - smoth, color, four);
        RenderUtils.drawGlichRect2OBF(left * 2.0f, top * 2.0f - smoth, right * 2.0f, top * 2.0f - smoth, four, color);
        RenderUtils.drawGlichRect3OBF(right * 2.0f + smoth, top * 2.0f, right * 2.0f + smoth, bottom * 2.0f - smoth, four, color);
        RenderUtils.drawGlichRect2OBF(left * 2.0f, bottom * 2.0f, right * 2.0f, bottom * 2.0f, color, four);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawGlichRect3OBF(double x, double y, double width, double height, int color, int twocolor) {
        if (x < width) {
            float i = (float)x;
            x = width;
            width = i;
        }
        if (y < height) {
            float j = (float)y;
            y = height;
            height = j;
        }
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha2 = (float)(twocolor >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(twocolor >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(twocolor >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(twocolor & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableAlpha();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
    }

    public static void drawGlichRect2OBF(double x, double y, double width, double height, int color, int twocolor) {
        if (x < width) {
            float i = (float)x;
            x = width;
            width = i;
        }
        if (y < height) {
            float j = (float)y;
            y = height;
            height = j;
        }
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha2 = (float)(twocolor >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(twocolor >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(twocolor >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(twocolor & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableAlpha();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, height, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
    }

    public static void drawPolygonPartss(double x, double y, int radius, int part, int color, int endcolor) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha1 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red1 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green1 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue1 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; i += 6) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red1, green1, blue1, alpha1).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void customScaledObject2D(float oXpos, float oYpos, float oWidth, float oHeight, float oScale) {
        GL11.glTranslated(oWidth / 2.0f, oHeight / 2.0f, 1.0);
        GL11.glTranslated(-oXpos * oScale + oXpos + oWidth / 2.0f * -oScale, -oYpos * oScale + oYpos + oHeight / 2.0f * -oScale, 1.0);
        GL11.glScaled(oScale, oScale, 0.0);
    }

    public static void customScaledObject2DCoords(float oXpos, float oYpos, float oXpos2, float oYpos2, float oScale) {
        RenderUtils.customScaledObject2D(oXpos, oYpos, oXpos2 - oXpos, oYpos2 - oYpos, oScale);
    }

    public static void customScaledObject2DPro(float oXpos, float oYpos, float oWidth, float oHeight, float oScaleX, float oScaleY) {
        GL11.glTranslated(oWidth / 2.0f, oHeight / 2.0f, 1.0);
        GL11.glTranslated(-oXpos * oScaleX + oXpos + oWidth / 2.0f * -oScaleX, -oYpos * oScaleY + oYpos + oHeight / 2.0f * -oScaleY, 1.0);
        GL11.glScaled(oScaleX, oScaleY, 0.0);
    }

    public static void customRotatedObject2D(float oXpos, float oYpos, float oWidth, float oHeight, float rotate) {
        GL11.glTranslated(oXpos + oWidth / 2.0f, oYpos + oHeight / 2.0f, 0.0);
        GL11.glRotated(rotate, 0.0, 0.0, 1.0);
        GL11.glTranslated(-oXpos - oWidth / 2.0f, -oYpos - oHeight / 2.0f, 0.0);
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }

    public static void drawRect(double x, double y, double d, double e, int color) {
        RenderUtils.glRenderStart();
        RenderUtils.glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(d, y);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(x, e);
        GL11.glEnd();
        RenderUtils.glRenderStop();
    }

    public static void drawRectSs(double left, double top, double right, double bottom, int color) {
        right += left;
        bottom += top;
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFpsRect(float xPos, float yPos, float x2Pos, float y2Pos, int color) {
        float x = xPos < x2Pos ? xPos : x2Pos;
        float y = yPos < y2Pos ? xPos : x2Pos;
        float y2 = yPos > y2Pos ? yPos : y2Pos;
        float x2 = xPos > x2Pos ? xPos : x2Pos;
        float dx = Math.abs(x2 - x) > Math.abs(x - x2) ? Math.abs(x - x2) : Math.abs(x2 - x);
        float dy = Math.abs(y2 - y) > Math.abs(y - y2) ? Math.abs(y - y2) : Math.abs(y2 - y);
        float lw = dx > dy ? dy : dx;
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glColor4d((double)(color >> 16 & 0xFF) / 255.0, (double)(color >> 8 & 0xFF) / 255.0, (double)(color & 0xFF) / 255.0, (double)(color >> 24 & 0xFF) / 255.0);
        GL11.glLineWidth(lw * (float)RenderUtils.mc.gameSettings.guiScale);
        GL11.glBegin(1);
        GL11.glVertex2d(lw == dx ? (double)(x + dx / 2.0f) : (double)x, lw == dy ? (double)(y + dy / 2.0f) : (double)y);
        GL11.glVertex2d(lw == dx ? (double)(x + dx / 2.0f) : (double)x2, lw == dy ? (double)(y + dy / 2.0f) : (double)y2);
        GL11.glEnd();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }

    public static void drawLineW(float xPos, float yPos, float x2Pos, float h, int color, int color2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(h * (float)RenderUtils.mc.gameSettings.guiScale + 0.25f);
        GL11.glBegin(1);
        RenderUtils.glColor(color);
        GL11.glVertex2d(xPos, yPos + h / 2.0f);
        RenderUtils.glColor(color2);
        GL11.glVertex2d(x2Pos, yPos + h / 2.0f);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GlStateManager.resetColor();
    }

    public static void drawLineH(float xPos, float yPos, float y2Pos, float w, int color, int color2) {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(w * (float)RenderUtils.mc.gameSettings.guiScale);
        GL11.glBegin(1);
        RenderUtils.glColor(color);
        GL11.glVertex2d(xPos + w / 2.0f, yPos);
        RenderUtils.glColor(color2);
        GL11.glVertex2d(xPos + w / 2.0f, y2Pos);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }

    public static void drawLightContureRect(double x, double y, double x2, double y2, int color) {
        RenderUtils.drawAlphedRect(x - 0.5, y - 0.5, x2 + 0.5, y, color);
        RenderUtils.drawAlphedRect(x - 0.5, y2, x2 + 0.5, y2 + 0.5, color);
        RenderUtils.drawAlphedRect(x - 0.5, y, x, y2, color);
        RenderUtils.drawAlphedRect(x2, y, x2 + 0.5, y2, color);
    }

    public static void drawLightContureRectSmooth(double x, double y, double x2, double y2, int color) {
        RenderUtils.drawAlphedRect(x, y - 0.5, x2, y, color);
        RenderUtils.drawAlphedRect(x, y2, x2, y2 + 0.5, color);
        RenderUtils.drawAlphedRect(x - 0.5, y, x, y2, color);
        RenderUtils.drawAlphedRect(x2, y, x2 + 0.5, y2, color);
    }

    public static void drawLightContureRectSideways(double x, double y, double x2, double y2, int color, int color2) {
        RenderUtils.drawAlphedSideways(x - 0.5, y - 0.5, x2 + 0.5, y, color, color2);
        RenderUtils.drawAlphedSideways(x - 0.5, y2, x2 + 0.5, y2 + 0.5, color, color2);
        RenderUtils.drawAlphedRect(x - 0.5, y, x, y2, color);
        RenderUtils.drawAlphedRect(x2, y, x2 + 0.5, y2, color2);
    }

    public static void drawLightContureRectSidewaysSmooth(double x, double y, double x2, double y2, int color, int color2) {
        RenderUtils.drawAlphedSideways(x, y - 0.5, x2, y, color, color2);
        RenderUtils.drawAlphedSideways(x, y2, x2, y2 + 0.5, color, color2);
        RenderUtils.drawAlphedRect(x - 0.5, y, x, y2, color);
        RenderUtils.drawAlphedRect(x2, y, x2 + 0.5, y2, color2);
    }

    public static void drawLightContureRectFullGradient(float x, float y, float x2, float y2, int c1, int c2, boolean bloom) {
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y - 0.5f, x2 + 0.5f, y, c1, c2, c2, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y2, x2 + 0.5f, y2 + 0.5f, c2, c1, c1, c2, bloom);
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y, x, y2, c2, c2, c1, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x2, y, x2 + 0.5f, y2, c1, c1, c2, c2, bloom);
    }

    public static void drawLightContureRectFullGradient(float x, float y, float x2, float y2, int c1, int c2, int c3, int c4, boolean bloom) {
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y - 0.5f, x2 + 0.5f, y, c1, c2, c2, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y2, x2 + 0.5f, y2 + 0.5f, c4, c3, c3, c4, bloom);
        RenderUtils.drawFullGradientRectPro(x - 0.5f, y, x, y2, c4, c4, c1, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x2, y, x2 + 0.5f, y2, c3, c3, c2, c2, bloom);
    }

    public static void startVertexRect() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void drawVertexRect(double x, double y, double x2, double y2, int c) {
        double xPos1 = x > x2 ? x2 : x;
        double yPos1 = y > y2 ? y2 : y;
        double xPos2 = x2 > x ? x2 : x;
        double yPos2 = y2 > y ? y2 : y;
        float a = (float)(c >> 24 & 0xFF) / 255.0f;
        float r = (float)(c >> 16 & 0xFF) / 255.0f;
        float g = (float)(c >> 8 & 0xFF) / 255.0f;
        float b = (float)(c & 0xFF) / 255.0f;
        buffer.pos(xPos2, yPos2, 300.0).color(r, g, b, a).endVertex();
        buffer.pos(xPos1, yPos2, 300.0).color(r, g, b, a).endVertex();
        buffer.pos(xPos1, yPos1, 300.0).color(r, g, b, a).endVertex();
        buffer.pos(xPos2, yPos1, 300.0).color(r, g, b, a).endVertex();
    }

    public static void stopVertexRect() {
        Tessellator.getInstance().draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
    }

    public static void drawAlphedRect(double x, double y, double d, double e, int color) {
        RenderUtils.glRenderStart();
        GL11.glDisable(3008);
        RenderUtils.glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(d, y);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(x, e);
        GL11.glEnd();
        GL11.glEnable(3008);
        RenderUtils.glRenderStop();
    }

    public static void drawAlphedRectWithBloom(double x, double y, double x2, double y2, int color, boolean bloom) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(3008);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.glColor(color);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, y2, 0.0).endVertex();
        bufferbuilder.pos(x2, y2, 0.0).endVertex();
        bufferbuilder.pos(x2, y, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GL11.glEnable(3008);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public static void drawAlphedGradientRectWithBloom(double x, double y, double x2, double y2, int color, int color2, boolean bloom) {
        RenderUtils.glRenderStart();
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glBegin(7);
        RenderUtils.glColor(color2);
        GL11.glVertex2d(x, y2);
        RenderUtils.glColor(color2);
        GL11.glVertex2d(x2, y2);
        RenderUtils.glColor(color);
        GL11.glVertex2d(x2, y);
        RenderUtils.glColor(color);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        RenderUtils.glRenderStop();
    }

    public static int reAlpha(int color, float alpha) {
        try {
            Color c = new Color(color);
            float r = 0.003921569f * (float)c.getRed();
            float g = 0.003921569f * (float)c.getGreen();
            float b = 0.003921569f * (float)c.getBlue();
            return new Color(r, g, b, alpha).getRGB();
        } catch (Throwable e) {
            e.printStackTrace();
            return color;
        }
    }

    public static void drawAlphedGradient(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawShadowRect(double startX, double startY, double endX, double endY, int radius) {
        RenderUtils.drawGradientRect(startX, startY - (double)radius, endX, startY, false, true, new Color(0, 0, 0, 100).getRGB(), new Color(0, 0, 0, 0).getRGB());
        RenderUtils.drawGradientRect(startX, endY, endX, endY + (double)radius, false, false, new Color(0, 0, 0, 100).getRGB(), new Color(0, 0, 0, 0).getRGB());
        RenderUtils.drawSector2(endX, endY, 0, 90, radius);
        RenderUtils.drawSector2(endX, startY, 90, 180, radius);
        RenderUtils.drawSector2(startX, startY, 180, 270, radius);
        RenderUtils.drawSector2(startX, endY, 270, 360, radius);
        RenderUtils.drawGradientRect(startX - (double)radius, startY, startX, endY, true, true, new Color(0, 0, 0, 100).getRGB(), new Color(0, 0, 0, 0).getRGB());
        RenderUtils.drawGradientRect(endX, startY, endX + (double)radius, endY, true, false, new Color(0, 0, 0, 100).getRGB(), new Color(0, 0, 0, 0).getRGB());
    }

    public static void drawShadowRectColored(double startX, double startY, double endX, double endY, int radius, int color, int alpha) {
        RenderUtils.drawGradientRect2(startX, startY - (double)radius, endX, startY, false, true, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawGradientRect2(startX, endY, endX, endY + (double)radius, false, false, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawSector3(endX, endY, 0, 90, radius, color, alpha);
        RenderUtils.drawSector3(endX, startY, 90, 180, radius, color, alpha);
        RenderUtils.drawSector3(startX, startY, 180, 270, radius, color, alpha);
        RenderUtils.drawSector3(startX, endY, 270, 360, radius, color, alpha);
        RenderUtils.drawGradientRect2(startX - (double)radius, startY, startX, endY, true, true, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawGradientRect2(endX, startY, endX + (double)radius, endY, true, false, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    public static void drawShadowRectColored(double startX, double startY, double endX, double endY, float radius, int color, int alpha) {
        RenderUtils.drawGradientRect2(startX, startY - (double)radius, endX, startY, false, true, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawGradientRect2(startX, endY, endX, endY + (double)radius, false, false, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawSector3(endX, endY, 0, 90, radius, color, alpha);
        RenderUtils.drawSector3(endX, startY, 90, 180, radius, color, alpha);
        RenderUtils.drawSector3(startX, startY, 180, 270, radius, color, alpha);
        RenderUtils.drawSector3(startX, endY, 270, 360, radius, color, alpha);
        RenderUtils.drawGradientRect2(startX - (double)radius, startY, startX, endY, true, true, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        RenderUtils.drawGradientRect2(endX, startY, endX + (double)radius, endY, true, false, ColorUtils.swapAlpha(color, alpha), 0, alpha);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    public static void drawFullGradientShadowRectColored(double startX, double startY, double endX, double endY, float radius, int color1, int color2, int color3, int color4, int alpha) {
        RenderUtils.drawFullGradientRectPro((float)startX, (float)startY - radius, (float)startX + ((float)endX - (float)startX) / 2.0f, (float)startY, color1, ColorUtils.getOverallColorFrom(color1, color2), 0, 0, true);
        RenderUtils.drawFullGradientRectPro((float)startX + ((float)endX - (float)startX) / 2.0f, (float)startY - radius, (float)endX, (float)startY, ColorUtils.getOverallColorFrom(color1, color2), color2, 0, 0, true);
        RenderUtils.drawFullGradientRectPro((float)startX, (float)endY, (float)startX + ((float)endX - (float)startX) / 2.0f, (float)endY + radius, 0, 0, ColorUtils.getOverallColorFrom(color3, color4), color4, true);
        RenderUtils.drawFullGradientRectPro((float)startX + ((float)endX - (float)startX) / 2.0f, (float)endY, (float)endX, (float)endY + radius, 0, 0, color3, ColorUtils.getOverallColorFrom(color3, color4), true);
        RenderUtils.drawSector3(endX, endY, 0, 90, radius, color3, alpha);
        RenderUtils.drawSector3(endX, startY, 90, 180, radius, color2, alpha);
        RenderUtils.drawSector3(startX, startY, 180, 270, radius, color1, alpha);
        RenderUtils.drawSector3(startX, endY, 270, 360, radius, color4, alpha);
        RenderUtils.drawFullGradientRectPro((float)startX - radius, (float)startY, (float)startX, (float)startY + ((float)endY - (float)startY) / 2.0f, 0, ColorUtils.getOverallColorFrom(color4, color1), color1, 0, true);
        RenderUtils.drawFullGradientRectPro((float)startX - radius, (float)startY + ((float)endY - (float)startY) / 2.0f, (float)startX, (float)endY, 0, color4, ColorUtils.getOverallColorFrom(color4, color1), 0, true);
        RenderUtils.drawFullGradientRectPro((float)endX, (float)startY, (float)endX + radius, (float)startY + ((float)endY - (float)startY) / 2.0f, ColorUtils.getOverallColorFrom(color3, color2), 0, 0, color2, true);
        RenderUtils.drawFullGradientRectPro((float)endX, (float)startY + ((float)endY - (float)startY) / 2.0f, (float)endX + radius, (float)endY, color3, 0, 0, ColorUtils.getOverallColorFrom(color3, color2), true);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    public static void drawFullGradientShadowRectColored(double startX, double startY, double endX, double endY, float radius, int color1, int color2, int color3, int color4, int alpha, boolean bloom) {
        RenderUtils.drawFullGradientRectPro((float)startX, (float)startY - radius, (float)startX + ((float)endX - (float)startX) / 2.0f, (float)startY, color1, ColorUtils.getOverallColorFrom(color1, color2), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color1, color2), 0.0f), ColorUtils.swapAlpha(color1, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro((float)startX + ((float)endX - (float)startX) / 2.0f, (float)startY - radius, (float)endX, (float)startY, ColorUtils.getOverallColorFrom(color1, color2), color2, ColorUtils.swapAlpha(color2, 0.0f), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color1, color2), 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro((float)startX, (float)endY, (float)startX + ((float)endX - (float)startX) / 2.0f, (float)endY + radius, ColorUtils.swapAlpha(color4, 0.0f), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color3, color4), 0.0f), ColorUtils.getOverallColorFrom(color3, color4), color4, bloom);
        RenderUtils.drawFullGradientRectPro((float)startX + ((float)endX - (float)startX) / 2.0f, (float)endY, (float)endX, (float)endY + radius, ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color3, color4), 0.0f), ColorUtils.swapAlpha(color3, 0.0f), color3, ColorUtils.getOverallColorFrom(color3, color4), bloom);
        RenderUtils.drawSector4(endX, endY, 0, 90, radius, color3, alpha, bloom);
        RenderUtils.drawSector4(endX, startY, 90, 180, radius, color2, alpha, bloom);
        RenderUtils.drawSector4(startX, startY, 180, 270, radius, color1, alpha, bloom);
        RenderUtils.drawSector4(startX, endY, 270, 360, radius, color4, alpha, bloom);
        RenderUtils.drawFullGradientRectPro((float)startX - radius, (float)startY, (float)startX, (float)startY + ((float)endY - (float)startY) / 2.0f, ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color4, color1), 0.0f), ColorUtils.getOverallColorFrom(color4, color1), color1, ColorUtils.swapAlpha(color1, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro((float)startX - radius, (float)startY + ((float)endY - (float)startY) / 2.0f, (float)startX, (float)endY, ColorUtils.swapAlpha(color4, 0.0f), color4, ColorUtils.getOverallColorFrom(color4, color1), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color4, color1), 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro((float)endX, (float)startY, (float)endX + radius, (float)startY + ((float)endY - (float)startY) / 2.0f, ColorUtils.getOverallColorFrom(color3, color2), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color3, color2), 0.0f), ColorUtils.swapAlpha(color2, 0.0f), color2, bloom);
        RenderUtils.drawFullGradientRectPro((float)endX, (float)startY + ((float)endY - (float)startY) / 2.0f, (float)endX + radius, (float)endY, color3, ColorUtils.swapAlpha(color3, 0.0f), ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(color3, color2), 0.0f), ColorUtils.getOverallColorFrom(color3, color2), bloom);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    public static void drawShadowRectGlow(double startX, double startY, double endX, double endY, int radius, int startcolor, int endcolor, int Alpha) {
        RenderUtils.drawGradientRectGlow(startX, startY - (double)radius, endX, startY, false, true, startcolor, endcolor, Alpha);
        RenderUtils.drawGradientRectGlow(startX, endY, endX, endY + (double)radius, false, false, startcolor, endcolor, Alpha);
        RenderUtils.drawSector2Glow(endX, endY, 0, 90, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(endX, startY, 90, 180, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(startX, startY, 180, 270, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(startX, endY, 270, 360, radius, startcolor, Alpha);
        RenderUtils.drawGradientRectGlow(startX - (double)radius, startY, startX, endY, true, true, startcolor, endcolor, Alpha);
        RenderUtils.drawGradientRectGlow(endX, startY, endX + (double)radius, endY, true, false, startcolor, endcolor, Alpha);
    }

    public static void drawShadowAlphedRectGlow(double startX, double startY, double endX, double endY, int radius, int startcolor, int endcolor, int Alpha, int Alpha2) {
        RenderUtils.drawGradientRectGlow2(startX, startY - (double)radius, endX, startY, false, true, startcolor, endcolor, Alpha, Alpha2);
        RenderUtils.drawGradientRectGlow2(startX, endY, endX, endY + (double)radius, false, false, startcolor, endcolor, Alpha, Alpha2);
        RenderUtils.drawSector2Glow(endX, endY, 0, 90, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(endX, startY, 90, 180, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(startX, startY, 180, 270, radius, startcolor, Alpha);
        RenderUtils.drawSector2Glow(startX, endY, 270, 360, radius, startcolor, Alpha);
        RenderUtils.drawGradientRectGlow2(startX - (double)radius, startY, startX, endY, true, true, startcolor, endcolor, Alpha, Alpha2);
        RenderUtils.drawGradientRectGlow2(endX, startY, endX + (double)radius, endY, true, false, startcolor, endcolor, Alpha, Alpha2);
    }

    public static void drawGradientRectGlow(double startX, double startY, double endX, double endY, boolean sideways, boolean reversed, int startColor, int endColor, int Alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(7);
        RenderUtils.setColor(startColor);
        if (sideways) {
            if (reversed) {
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
                RenderUtils.setupColor(endColor, Alpha);
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
            } else {
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
                RenderUtils.setupColor(endColor, Alpha);
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
            }
        } else if (reversed) {
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(endColor, Alpha);
            GL11.glVertex2d(endX, startY);
            GL11.glVertex2d(startX, startY);
            RenderUtils.setColor(startColor);
            GL11.glVertex2d(startX, endY);
        } else {
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(endColor, Alpha);
            GL11.glVertex2d(startX, endY);
            GL11.glVertex2d(endX, endY);
            RenderUtils.setColor(startColor);
            GL11.glVertex2d(endX, startY);
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectGlow2(double startX, double startY, double endX, double endY, boolean sideways, boolean reversed, int startColor, int endColor, int Alpha, int Alpha2) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(7);
        RenderUtils.setupColor(startColor, Alpha2);
        if (sideways) {
            if (reversed) {
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
                RenderUtils.setupColor(endColor, Alpha);
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
            } else {
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
                RenderUtils.setupColor(endColor, Alpha);
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
            }
        } else if (reversed) {
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(endColor, Alpha);
            GL11.glVertex2d(endX, startY);
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(startColor, Alpha2);
            GL11.glVertex2d(startX, endY);
        } else {
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(endColor, Alpha);
            GL11.glVertex2d(startX, endY);
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(startColor, Alpha2);
            GL11.glVertex2d(endX, startY);
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawBloomedFullShadowFullGradientRect(float xpos, float ypos, float x2pos, float y2pos, float radius, int color1, int color2, int color3, int color4, int alpha, boolean bloom) {
        float x = xpos;
        float y = ypos;
        float w = x2pos - xpos;
        float h = y2pos - ypos;
        int colorid1 = ColorUtils.swapAlpha(color1, alpha);
        int colorid2 = ColorUtils.swapAlpha(color2, alpha);
        int colorid3 = ColorUtils.swapAlpha(color3, alpha);
        int colorid4 = ColorUtils.swapAlpha(color4, alpha);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderUtils.drawFullGradientShadowRectColored(x, y, x + w, y + h, radius, colorid1, colorid2, colorid3, colorid4, alpha, bloom);
        RenderUtils.drawFullGradientRectPro(x, y, x + w / 2.0f, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid1, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid2), colorid1, bloom);
        RenderUtils.drawFullGradientRectPro(x, y + h / 2.0f, x + w / 2.0f, y + h, colorid4, ColorUtils.getOverallColorFrom(colorid3, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid4), bloom);
        RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y + h / 2.0f, x + w, y + h, ColorUtils.getOverallColorFrom(colorid3, colorid4), colorid3, ColorUtils.getOverallColorFrom(colorid3, colorid2), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), bloom);
        RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y, x + w, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid3, colorid2), colorid2, ColorUtils.getOverallColorFrom(colorid2, colorid1), bloom);
    }

    public static void drawBloomedFullGradientRect(float xpos, float ypos, float x2pos, float y2pos, int color1, int color2, int color3, int color4, boolean bloom) {
        float x = xpos;
        float y = ypos;
        float w = x2pos - xpos;
        float h = y2pos - ypos;
        int colorid1 = color1;
        int colorid2 = color2;
        int colorid3 = color3;
        int colorid4 = color4;
        RenderUtils.drawFullGradientRectPro(x, y, x + w, y + h, color1, color2, color3, color4, bloom);
    }

    public static void drawBloomedFullShadowFullGradientRectBool(float xpos, float ypos, float x2pos, float y2pos, float radius, int color1, int color2, int color3, int color4, int alpha, boolean bloom, boolean rect, boolean shadow) {
        float x = xpos;
        float y = ypos;
        float w = x2pos - xpos;
        float h = y2pos - ypos;
        int colorid1 = ColorUtils.swapAlpha(color1, alpha);
        int colorid2 = ColorUtils.swapAlpha(color2, alpha);
        int colorid3 = ColorUtils.swapAlpha(color3, alpha);
        int colorid4 = ColorUtils.swapAlpha(color4, alpha);
        if (shadow) {
            RenderUtils.drawFullGradientShadowRectColored(x, y, x + w, y + h, radius, colorid1, colorid2, colorid3, colorid4, alpha, bloom);
        }
        if (rect) {
            RenderUtils.drawFullGradientRectPro(x, y, x + w / 2.0f, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid1, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid2), colorid1, bloom);
            RenderUtils.drawFullGradientRectPro(x, y + h / 2.0f, x + w / 2.0f, y + h, colorid4, ColorUtils.getOverallColorFrom(colorid3, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid4), bloom);
            RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y + h / 2.0f, x + w, y + h, ColorUtils.getOverallColorFrom(colorid3, colorid4), colorid3, ColorUtils.getOverallColorFrom(colorid3, colorid2), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), bloom);
            RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y, x + w, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid3, colorid2), colorid2, ColorUtils.getOverallColorFrom(colorid2, colorid1), bloom);
        }
    }

    public static void drawBloomedFullShadowFullGradientRectBool(float xpos, float ypos, float x2pos, float y2pos, float radius, int color1, int color2, int color3, int color4, int alphaRect, int alphaGlow, boolean bloom, boolean rect, boolean shadow) {
        float x = xpos;
        float y = ypos;
        float w = x2pos - xpos;
        float h = y2pos - ypos;
        int colorid1 = ColorUtils.swapAlpha(color1, alphaRect);
        int colorid2 = ColorUtils.swapAlpha(color2, alphaRect);
        int colorid3 = ColorUtils.swapAlpha(color3, alphaRect);
        int colorid4 = ColorUtils.swapAlpha(color4, alphaRect);
        int colorid5 = ColorUtils.swapAlpha(color1, alphaGlow);
        int colorid6 = ColorUtils.swapAlpha(color2, alphaGlow);
        int colorid7 = ColorUtils.swapAlpha(color3, alphaGlow);
        int colorid8 = ColorUtils.swapAlpha(color4, alphaGlow);
        if (shadow) {
            RenderUtils.drawFullGradientShadowRectColored(x, y, x + w, y + h, radius, colorid5, colorid6, colorid7, colorid8, alphaGlow, bloom);
        }
        if (rect) {
            RenderUtils.drawFullGradientRectPro(x, y, x + w / 2.0f, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid1, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid2), colorid1, bloom);
            RenderUtils.drawFullGradientRectPro(x, y + h / 2.0f, x + w / 2.0f, y + h, colorid4, ColorUtils.getOverallColorFrom(colorid3, colorid4), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid1, colorid4), bloom);
            RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y + h / 2.0f, x + w, y + h, ColorUtils.getOverallColorFrom(colorid3, colorid4), colorid3, ColorUtils.getOverallColorFrom(colorid3, colorid2), ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), bloom);
            RenderUtils.drawFullGradientRectPro(x + w / 2.0f, y, x + w, y + h / 2.0f, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid3, ColorUtils.getOverallColorFrom(colorid1, colorid2))), ColorUtils.getOverallColorFrom(colorid3, colorid2), colorid2, ColorUtils.getOverallColorFrom(colorid2, colorid1), bloom);
        }
    }

    public static void drawRoundedFullGradientOutsideShadow(float x, float y, float x2, float y2, float round, float shadowSize, int color, int color2, int color3, int color4, boolean bloom) {
        x += round;
        x2 -= round;
        y += round;
        y2 -= round;
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.drawCroneShadow(x, y, -180, -90, round, shadowSize, color, bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x, y - round - shadowSize, x2, y - round, color, color2, bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        RenderUtils.drawCroneShadow(x2, y, 90, 180, round, shadowSize, color2, bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x2 + round, y, x2 + round + shadowSize, y2, color3, bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), color2, bloom);
        RenderUtils.drawCroneShadow(x2, y2, 0, 90, round, shadowSize, color3, bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x, y2 + round, x2, y2 + round + shadowSize, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), color3, color4, bloom);
        RenderUtils.drawCroneShadow(x, y2, -90, 0, round, shadowSize, color4, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x - round - shadowSize, y, x - round, y2, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), color4, color, bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawInsideFullRoundedFullGradientShadowRectWithBloomBool(float x, float y, float x2, float y2, float round, float shadowSize, int c1, int c2, int c3, int c4, boolean bloom) {
        RenderUtils.drawCroneShadow(x + shadowSize + round, y + shadowSize + round, -180, -90, round, shadowSize, 0, c1, bloom);
        RenderUtils.drawCroneShadow(x2 - shadowSize - round, y + shadowSize + round, -270, -180, round, shadowSize, 0, c2, bloom);
        RenderUtils.drawCroneShadow(x2 - shadowSize - round, y2 - shadowSize - round, 0, 90, round, shadowSize, 0, c3, bloom);
        RenderUtils.drawCroneShadow(x + shadowSize + round, y2 - shadowSize - round, -90, 0, round, shadowSize, 0, c4, bloom);
        RenderUtils.drawFullGradientRectPro(x + shadowSize + round, y, x2 - shadowSize - round, y + shadowSize, 0, 0, c2, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x + shadowSize + round, y2 - shadowSize, x2 - shadowSize - round, y2, c4, c3, 0, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x, y + shadowSize + round, x + shadowSize, y2 - shadowSize - round, c4, 0, 0, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x2 - shadowSize, y + shadowSize + round, x2, y2 - shadowSize - round, 0, c3, c2, 0, bloom);
    }

    public static void drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(float x, float y, float x2, float y2, float round, float shadowSizeInside, float shadowSizeOutside, int c1, int c2, int c3, int c4, boolean bloom, boolean insideShadow, boolean outsideShadow) {
        if (insideShadow) {
            RenderUtils.drawInsideFullRoundedFullGradientShadowRectWithBloomBool(x, y, x2, y2, round, shadowSizeInside, c1, c2, c3, c4, bloom);
        }
        if (outsideShadow) {
            RenderUtils.drawRoundedFullGradientOutsideShadow(x, y, x2, y2, round + round / 2.0f, shadowSizeOutside, c1, c2, c3, c4, bloom);
        }
    }

    public static void drawFullGradientFullsideShadowRectWithBloomBool(float x, float y, float x2, float y2, float shadowSize, int c1, int c2, int c3, int c4, boolean bloom) {
        RenderUtils.drawFullGradientRectPro(x, y, x + shadowSize, y + shadowSize, c1, 0, c1, c1, bloom);
        RenderUtils.customRotatedObject2D(x2 - shadowSize, y, shadowSize, shadowSize, 90.0f);
        RenderUtils.drawFullGradientRectPro(x2 - shadowSize, y, x2, y + shadowSize, c2, 0, c2, c2, bloom);
        RenderUtils.customRotatedObject2D(x2 - shadowSize, y, shadowSize, shadowSize, -90.0f);
        RenderUtils.drawFullGradientRectPro(x2 - shadowSize, y2 - shadowSize, x2, y2, c3, c3, c3, 0, bloom);
        RenderUtils.customRotatedObject2D(x, y2 - shadowSize, shadowSize, shadowSize, 90.0f);
        RenderUtils.drawFullGradientRectPro(x, y2 - shadowSize, x + shadowSize, y2, c4, c4, c4, 0, bloom);
        RenderUtils.customRotatedObject2D(x, y2 - shadowSize, shadowSize, shadowSize, -90.0f);
        RenderUtils.drawFullGradientRectPro(x + shadowSize, y, x2 - shadowSize, y + shadowSize, 0, 0, c2, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x, y + shadowSize, x + shadowSize, y2 - shadowSize, c4, 0, 0, c1, bloom);
        RenderUtils.drawFullGradientRectPro(x + shadowSize, y2 - shadowSize, x2 - shadowSize, y2, c4, c3, 0, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x2 - shadowSize, y + shadowSize, x2, y2 - shadowSize, 0, c3, c2, 0, bloom);
        RenderUtils.drawRoundedFullGradientOutsideShadow(x, y, x2, y2, 0.0f, shadowSize, c1, c2, c3, c4, bloom);
    }

    public static void drawSector2Glow(double x, double y, int startAngle, int endAngle, int radius, int color, int Alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(6);
        RenderUtils.setupColor(color, Alpha);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = startAngle; i <= endAngle; i += 6) {
            GL11.glVertex2d(x + Math.sin((double)i * Math.PI / 180.0) * (double)radius, y + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRect(double startX, double startY, double endX, double endY, boolean sideways, int startColor, int endColor) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        RenderUtils.glColor(startColor);
        GL11.glBegin(7);
        if (sideways) {
            GL11.glVertex2d(startX, startY);
            GL11.glVertex2d(startX, endY);
            RenderUtils.glColor(endColor);
            GL11.glVertex2d(endX, endY);
            GL11.glVertex2d(endX, startY);
        } else {
            GL11.glVertex2d(startX, startY);
            RenderUtils.glColor(endColor);
            GL11.glVertex2d(startX, endY);
            GL11.glVertex2d(endX, endY);
            RenderUtils.glColor(startColor);
            GL11.glVertex2d(endX, startY);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawGradientRect(double startX, double startY, double endX, double endY, boolean sideways, boolean reversed, int startColor, int endColor) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(7);
        RenderUtils.setColor(startColor);
        if (sideways) {
            if (reversed) {
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
                RenderUtils.setupColor(endColor, 0.0f);
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
            } else {
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
                RenderUtils.setupColor(endColor, 0.0f);
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
            }
        } else if (reversed) {
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(endColor, 0.0f);
            GL11.glVertex2d(endX, startY);
            GL11.glVertex2d(startX, startY);
            RenderUtils.setColor(startColor);
            GL11.glVertex2d(startX, endY);
        } else {
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(endColor, 0.0f);
            GL11.glVertex2d(startX, endY);
            GL11.glVertex2d(endX, endY);
            RenderUtils.setColor(startColor);
            GL11.glVertex2d(endX, startY);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawGradientRect2(double startX, double startY, double endX, double endY, boolean sideways, boolean reversed, int startColor, int endColor, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GL11.glDisable(3008);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(7);
        RenderUtils.setupColor(startColor, alpha);
        if (sideways) {
            if (reversed) {
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
                RenderUtils.setupColor(endColor, 0.0f);
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
            } else {
                GL11.glVertex2d(startX, startY);
                GL11.glVertex2d(startX, endY);
                RenderUtils.setupColor(endColor, 0.0f);
                GL11.glVertex2d(endX, endY);
                GL11.glVertex2d(endX, startY);
            }
        } else if (reversed) {
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(endColor, 0.0f);
            GL11.glVertex2d(endX, startY);
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(startColor, alpha);
            GL11.glVertex2d(startX, endY);
        } else {
            GL11.glVertex2d(startX, startY);
            RenderUtils.setupColor(endColor, 0.0f);
            GL11.glVertex2d(startX, endY);
            GL11.glVertex2d(endX, endY);
            RenderUtils.setupColor(startColor, alpha);
            GL11.glVertex2d(endX, startY);
        }
        GL11.glEnd();
        GL11.glEnable(3008);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawSector2(double x, double y, int startAngle, int endAngle, int radius) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(6);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = startAngle; i <= endAngle; i += 6) {
            GL11.glVertex2d(x + Math.sin((double)i * Math.PI / 180.0) * (double)radius, y + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawSector3(double x, double y, int startAngle, int endAngle, float radius, int color, int alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GL11.glDisable(3008);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(6);
        RenderUtils.setupColor(color, alpha);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = startAngle; i <= endAngle; i += 6) {
            GL11.glVertex2d(x + Math.sin((double)i * Math.PI / 180.0) * (double)radius, y + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        GL11.glEnd();
        GL11.glEnable(3008);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawSector4(double x, double y, int startAngle, int endAngle, float radius, int color, int alpha, boolean bloom) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GL11.glDepthMask(false);
        GL11.glDisable(3008);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        }
        GlStateManager.shadeModel(7425);
        GL11.glBegin(6);
        RenderUtils.setupColor(color, alpha);
        GL11.glVertex2d(x, y);
        RenderUtils.setupColor(color, 0.0f);
        for (int i = startAngle; i <= endAngle; i += 30) {
            GL11.glVertex2d(x + Math.sin((double)i * Math.PI / 180.0) * (double)radius, y + Math.cos((double)i * Math.PI / 180.0) * (double)radius);
        }
        GL11.glEnd();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        }
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
    }

    public static void drawCroneShadow(double x, double y, int startAngle, int endAngle, float radius, float shadowSize, int color, int endColor, boolean bloom) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GL11.glDisable(3008);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        }
        GlStateManager.shadeModel(7425);
        GL11.glBegin(5);
        for (int i = startAngle; i <= endAngle; i += 18) {
            RenderUtils.glColor(color);
            double x1 = x + Math.sin((double)i * Math.PI / 180.0) * (double)radius;
            double y1 = y + Math.cos((double)i * Math.PI / 180.0) * (double)radius;
            double x2 = x + Math.sin((double)i * Math.PI / 180.0) * (double)(radius + shadowSize);
            double y2 = y + Math.cos((double)i * Math.PI / 180.0) * (double)(radius + shadowSize);
            if (MathUtils.getDifferenceOf(x1, x2) >= (double)0.35f || MathUtils.getDifferenceOf(y1, y2) >= (double)0.35f) {
                GL11.glVertex2d(x1, y1);
            }
            RenderUtils.glColor(endColor);
            if (!(MathUtils.getDifferenceOf(x1, x2) >= (double)0.35f) && !(MathUtils.getDifferenceOf(y1, y2) >= (double)0.35f)) continue;
            GL11.glVertex2d(x2, y2);
        }
        GL11.glEnd();
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        }
        GL11.glEnable(3008);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.resetColor();
    }

    public static void drawRoundedFullGradientInsideShadow(float x, float y, float x2, float y2, float round, int color, int color2, int color3, int color4, boolean bloom) {
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        float rd = round / 2.0f;
        RenderUtils.drawPolygonParts(x + rd, y + rd, rd, 0, 0, color, bloom);
        RenderUtils.drawPolygonParts(x + rd, y2 - rd, rd, 5, 0, color4, bloom);
        RenderUtils.drawPolygonParts(x2 - rd, y + rd, rd, 7, 0, color2, bloom);
        RenderUtils.drawPolygonParts(x2 - rd, y2 - rd, rd, 6, 0, color3, bloom);
        RenderUtils.drawBloomedFullGradientRect(x, y + rd, x + rd, y2 - rd, color4, 0, 0, color, bloom);
        RenderUtils.drawBloomedFullGradientRect(x + rd, y, x2 - rd, y + rd, 0, 0, color2, color, bloom);
        RenderUtils.drawBloomedFullGradientRect(x2 - rd, y + rd, x2, y2 - rd, 0, color3, color2, 0, bloom);
        RenderUtils.drawBloomedFullGradientRect(x + rd, y2 - rd, x2 - rd, y2, color4, color3, 0, 0, bloom);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawRoundedShadow(float x, float y, float x2, float y2, float round, float shadowSize, int color, boolean bloom) {
        x += round;
        x2 -= round;
        y += round;
        y2 -= round;
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.drawCroneShadow(x, y, -180, -90, round, shadowSize, color, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x, y - round - shadowSize, x2, y - round, color, color, 0, 0, bloom);
        RenderUtils.drawCroneShadow(x2, y, 90, 180, round, shadowSize, color, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x2 + round, y, x2 + round + shadowSize, y2, color, 0, 0, color, bloom);
        RenderUtils.drawCroneShadow(x2, y2, 0, 90, round, shadowSize, color, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x, y2 + round, x2, y2 + round + shadowSize, 0, 0, color, color, bloom);
        RenderUtils.drawCroneShadow(x, y2, -90, 0, round, shadowSize, color, 0, bloom);
        RenderUtils.drawFullGradientRectPro(x - round - shadowSize, y, x - round, y2, 0, color, color, 0, bloom);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    public static void drawRoundedFullGradientShadow(float x, float y, float x2, float y2, float round, float shadowSize, int color, int color2, int color3, int color4, boolean bloom) {
        x += round;
        x2 -= round;
        y += round;
        y2 -= round;
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.drawCroneShadow(x, y, -180, -90, round, shadowSize, color, bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x, y - round - shadowSize, x2, y - round, color, color2, bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        RenderUtils.drawCroneShadow(x2, y, 90, 180, round, shadowSize, color2, bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x2 + round, y, x2 + round + shadowSize, y2, color3, bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color2, 0.0f), color2, bloom);
        RenderUtils.drawCroneShadow(x2, y2, 0, 90, round, shadowSize, color3, bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x, y2 + round, x2, y2 + round + shadowSize, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), bloom ? 0 : ColorUtils.swapAlpha(color3, 0.0f), color3, color4, bloom);
        RenderUtils.drawCroneShadow(x, y2, -90, 0, round, shadowSize, color4, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), bloom);
        RenderUtils.drawFullGradientRectPro(x - round - shadowSize, y, x - round, y2, bloom ? 0 : ColorUtils.swapAlpha(color4, 0.0f), color4, color, bloom ? 0 : ColorUtils.swapAlpha(color, 0.0f), bloom);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(2929);
    }

    public static void drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(float x, float y, float x2, float y2, float round, float shadowSize, int color, int color2, int color3, int color4, boolean bloom, boolean rect, boolean shadow) {
        GlStateManager.disableDepth();
        if (shadow) {
            RenderUtils.drawRoundedFullGradientShadow(x, y, x2, y2, round, shadowSize, color, color2, color3, color4, bloom);
        }
        if (rect) {
            RenderUtils.drawRoundedFullGradientRectPro(x, y, x2, y2, round * 2.0f, color, color2, color3, color4, bloom);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    public static void drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBool(float x, float y, float x2, float y2, float round, int color, int color2, int color3, int color4, boolean bloom) {
        GlStateManager.disableDepth();
        RenderUtils.drawRoundedFullGradientShadow(x, y, x2, y2, round, round, color, color2, color3, color4, bloom);
        RenderUtils.drawRoundedFullGradientInsideShadow(x, y, x2, y2, round * 2.0f, color, color2, color3, color4, bloom);
        GlStateManager.enableDepth();
    }

    public static void smoothAngleRect(float xPos, float yPos, float x2Pos, float y2Pos, int color) {
        RenderUtils.drawRect(xPos + 3.0f, yPos - 3.0f, x2Pos - 3.0f, yPos - 2.5f, color);
        RenderUtils.drawRect(xPos + 2.0f, yPos - 2.5f, x2Pos - 2.0f, yPos - 2.0f, color);
        RenderUtils.drawRect(xPos + 1.5f, yPos - 2.0f, x2Pos - 1.5f, yPos - 1.5f, color);
        RenderUtils.drawRect(xPos + 1.0f, yPos - 1.5f, x2Pos - 1.0f, yPos - 1.0f, color);
        RenderUtils.drawRect(xPos + 0.5f, yPos - 1.0f, x2Pos - 0.5f, yPos, color);
        RenderUtils.drawRect(xPos, yPos, x2Pos, y2Pos, color);
        RenderUtils.drawRect(xPos + 2.0f, y2Pos + 2.5f, x2Pos - 2.0f, y2Pos + 2.0f, color);
        RenderUtils.drawRect(xPos + 1.5f, y2Pos + 2.0f, x2Pos - 1.5f, y2Pos + 1.5f, color);
        RenderUtils.drawRect(xPos + 1.0f, y2Pos + 1.5f, x2Pos - 1.0f, y2Pos + 1.0f, color);
        RenderUtils.drawRect(xPos + 0.5f, y2Pos + 1.0f, x2Pos - 0.5f, y2Pos, color);
        RenderUtils.drawRect(xPos + 3.0f, y2Pos + 3.0f, x2Pos - 3.0f, y2Pos + 2.5f, color);
    }

    public static void smoothAngleRectProof(float xPos, float yPos, float x2Pos, float y2Pos, int color) {
        RenderUtils.drawRect(xPos + 6.5f, yPos - 6.0f, x2Pos - 6.5f, yPos - 5.5f, color);
        RenderUtils.drawRect(xPos + 5.0f, yPos - 5.5f, x2Pos - 4.5f, yPos - 5.0f, color);
        RenderUtils.drawRect(xPos + 4.0f, yPos - 5.0f, x2Pos - 4.0f, yPos - 4.5f, color);
        RenderUtils.drawRect(xPos + 3.5f, yPos - 4.5f, x2Pos - 3.5f, yPos - 4.0f, color);
        RenderUtils.drawRect(xPos + 3.0f, yPos - 4.0f, x2Pos - 3.0f, yPos - 3.5f, color);
        RenderUtils.drawRect(xPos + 2.5f, yPos - 3.5f, x2Pos - 2.5f, yPos - 3.0f, color);
        RenderUtils.drawRect(xPos + 2.0f, yPos - 3.0f, x2Pos - 2.0f, yPos - 2.5f, color);
        RenderUtils.drawRect(xPos + 1.5f, yPos - 2.5f, x2Pos - 1.5f, yPos - 2.0f, color);
        RenderUtils.drawRect(xPos + 1.0f, yPos - 2.0f, x2Pos - 1.0f, yPos - 1.0f, color);
        RenderUtils.drawRect(xPos + 0.5f, yPos - 1.0f, x2Pos - 0.5f, yPos + 0.5f, color);
        RenderUtils.drawRect(xPos, yPos + 0.5f, x2Pos, (double)y2Pos - 0.5, color);
        RenderUtils.drawRect(xPos + 6.5f, y2Pos + 6.0f, x2Pos - 6.5f, y2Pos + 5.5f, color);
        RenderUtils.drawRect(xPos + 5.0f, y2Pos + 5.5f, x2Pos - 4.5f, y2Pos + 5.0f, color);
        RenderUtils.drawRect(xPos + 4.0f, y2Pos + 5.0f, x2Pos - 4.0f, y2Pos + 4.5f, color);
        RenderUtils.drawRect(xPos + 3.5f, y2Pos + 4.5f, x2Pos - 3.5f, y2Pos + 4.0f, color);
        RenderUtils.drawRect(xPos + 3.0f, y2Pos + 4.0f, x2Pos - 3.0f, y2Pos + 3.5f, color);
        RenderUtils.drawRect(xPos + 2.5f, y2Pos + 3.5f, x2Pos - 2.5f, y2Pos + 3.0f, color);
        RenderUtils.drawRect(xPos + 2.0f, y2Pos + 3.0f, x2Pos - 2.0f, y2Pos + 2.5f, color);
        RenderUtils.drawRect(xPos + 1.5f, y2Pos + 2.5f, x2Pos - 1.5f, y2Pos + 2.0f, color);
        RenderUtils.drawRect(xPos + 1.0f, y2Pos + 2.0f, x2Pos - 1.0f, y2Pos + 1.0f, color);
        RenderUtils.drawRect(xPos + 0.5f, y2Pos + 1.0f, x2Pos - 0.5f, y2Pos - 0.5f, color);
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset, int alpha) {
        if (offset > 1.0) {
            double left = offset % 1.0;
            int off = (int)offset;
            offset = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offset;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart, alpha);
    }

    public static void color(int argb) {
        float alpha = (float)(argb >> 24 & 0xFF) / 255.0f;
        float red = (float)(argb >> 16 & 0xFF) / 255.0f;
        float green = (float)(argb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(argb & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static int setColor(int colorHex) {
        float alpha = (float)(colorHex >> 24 & 0xFF) / 255.0f;
        float red = (float)(colorHex >> 16 & 0xFF) / 255.0f;
        float green = (float)(colorHex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(colorHex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha == 0.0f ? 1.0f : alpha);
        return colorHex;
    }

    public static int glColor(int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        return color;
    }

    public static int setColorNormal(int colorHex, int alpha) {
        float red = colorHex >> 16 & 0xFF;
        float green = colorHex >> 8 & 0xFF;
        float blue = colorHex & 0xFF;
        ColorUtils.getColor((int)red, (int)green, (int)blue, alpha);
        return colorHex;
    }

    public static void esp(EntityPlayer player, double x, double y, double z) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + (double)(player.height / 2.0f) - (double)0.225f, z);
        GL11.glColor4f(0.38f, 0.55f, 0.38f, 1.0f);
        GL11.glLineWidth(2.0f);
        GL11.glTranslated(0.0, 0.0, 0.075f);
        Cylinder shaft = new Cylinder();
        shaft.setDrawStyle(100011);
        shaft.draw(0.1f, 0.11f, 0.4f, 25, 20);
        GL11.glColor4f(0.38f, 0.85f, 0.38f, 1.0f);
        GL11.glLineWidth(2.0f);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        Sphere right = new Sphere();
        right.setDrawStyle(100011);
        right.draw(0.14f, 10, 20);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        Sphere left = new Sphere();
        left.setDrawStyle(100011);
        left.draw(0.14f, 10, 20);
        GL11.glColor4f(0.35f, 0.0f, 0.0f, 1.0f);
        GL11.glLineWidth(2.0f);
        GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
        Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw(0.13f, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
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

    public static Color setAlpha(org.lwjgl.util.Color color, int alpha) {
        alpha = (int)MathHelper.clamp(alpha, 0.0, 255.0);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static void setupColor(int color, float alpha) {
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(f, f1, f2, alpha / 255.0f);
    }

    public static void render2D(int mode, VertexFormat formats, float lineWidth, Runnable runnable) {
        boolean isLines = mode == 6913 || mode == 2 || mode == 3 || mode == 1;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        if (isLines) {
            GL11.glEnable(2848);
            GlStateManager.glLineWidth(lineWidth);
        }
        buffer.begin(mode, formats);
        runnable.run();
        tessellator.draw();
        if (isLines) {
            GL11.glDisable(2848);
        }
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public void multiRender2D(int mode, int twoMode, VertexFormat formats, VertexFormat twoFormats, boolean isLines, float lineWidth, Runnable runnable, Runnable twoRunnable) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        if (isLines) {
            GL11.glEnable(2848);
            GlStateManager.glLineWidth(lineWidth);
        }
        buffer.begin(mode, formats);
        runnable.run();
        tessellator.draw();
        buffer.begin(twoMode, twoFormats);
        twoRunnable.run();
        tessellator.draw();
        if (isLines) {
            GL11.glDisable(2848);
        }
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void renderItem(ItemStack itemStack, float x, float y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int)x, (int)y);
        mc.getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, itemStack, (int)x, (int)y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }

    public static void drawOutlinedString(String str, CFontRenderer font, float x, float y, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        font.drawString(str, x - 0.3f, y, ColorUtils.getColor(0));
        font.drawString(str, x + 0.3f, y, ColorUtils.getColor(0));
        font.drawString(str, x, y + 0.3f, ColorUtils.getColor(0));
        font.drawString(str, x, y - 0.3f, ColorUtils.getColor(0));
        font.drawString(str, x, y, color);
    }

    public static void drawOutlinedString(String str, FontRenderer font, float x, float y, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        font.drawString(str, x - 0.3f, y, ColorUtils.getColor(0));
        font.drawString(str, x + 0.3f, y, ColorUtils.getColor(0));
        font.drawString(str, x, y + 0.3f, ColorUtils.getColor(0));
        font.drawString(str, x, y - 0.3f, ColorUtils.getColor(0));
        font.drawString(str, x, y, color);
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawNewRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(left, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, top, 0.0).endVertex();
        vertexbuffer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectOpacity(double left, double top, double right, double bottom, float opacity) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(0.1f, 0.1f, 0.1f, opacity);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void relativeRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        RenderUtils.drawFullGradientRectPro((float)left, (float)top, (float)right, (float)bottom, col1, col2, col2, col1, false);
    }

    public static void drawAlphedSideways(double left, double top, double right, double bottom, int col1, int col2, boolean bloom) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glDisable(3008);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        if (bloom) {
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        RenderUtils.resetBlender();
    }

    public static void drawAlphedSideways(double left, double top, double right, double bottom, int col1, int col2) {
        RenderUtils.drawAlphedSideways(left < right ? left : right, top, left >= right ? left : right, bottom, col1, col2, false);
    }

    public static void drawTwoAlphedSideways(double left, double top, double right, double bottom, int col1, int col2, boolean bloom) {
        RenderUtils.drawAlphedSideways(left, top, left + (right - left) / 2.0, bottom, col2, col1, bloom);
        RenderUtils.drawAlphedSideways(left + (right - left) / 2.0, top, right, bottom, col1, col2, bloom);
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0f, 0.0f, (int)width, (int)height, (float)((int)width), (float)((int)height));
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImageWithAlpha(ResourceLocation image, float x, float y, float width, float height, int color, int alpha) {
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL11.glDisable(3008);
        mc.getTextureManager().bindTexture(image);
        RenderUtils.setupColor(color, alpha);
        GL11.glTranslated(x, y, 0.0);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, (int)width, (int)height, (float)((int)width), (float)((int)height));
        GL11.glTranslated(-x, -y, 0.0);
        GlStateManager.resetColor();
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
    }

    public static void drawPolygonPart(double x, double y, int radius, int part, int color, int endcolor) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha2 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; ++i) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonPartBloom(double x, double y, int radius, int part, int color, int endcolor) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha2 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        double TWICE_PI = Math.PI * 2;
        for (int i = part * 90; i <= part * 90 + 90; ++i) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        }
        tessellator.draw();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawVGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(startColor & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawVGradientRectBloom(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(startColor & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos(left, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGlow(double x, double y, double x1, double y1, int color, double alpha) {
        RenderUtils.drawVGradientRect((int)x, (int)y, (int)x1, (int)(y + (y1 - y) / 2.0), ColorUtils.injectAlpha(new Color(color), 0).getRGB(), ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB());
        RenderUtils.drawVGradientRect((int)x, (int)(y + (y1 - y) / 2.0), (int)x1, (int)y1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        int radius = (int)((y1 - y) / 2.0);
        RenderUtils.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 0, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        RenderUtils.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        RenderUtils.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 2, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        RenderUtils.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 3, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
    }

    public static void drawGlowedRect(double x, double y, double x1, double y1, int color, double alpha, boolean bloom) {
        if (bloom) {
            RenderUtils.drawVGradientRectBloom((int)x, (int)y, (int)x1, (int)(y + (y1 - y) / 2.0), ColorUtils.injectAlpha(new Color(color), 0).getRGB(), ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB());
            RenderUtils.drawVGradientRectBloom((int)x, (int)(y + (y1 - y) / 2.0), (int)x1, (int)y1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        } else {
            RenderUtils.drawVGradientRect((int)x, (int)y, (int)x1, (int)(y + (y1 - y) / 2.0), ColorUtils.injectAlpha(new Color(color), 0).getRGB(), ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB());
            RenderUtils.drawVGradientRect((int)x, (int)(y + (y1 - y) / 2.0), (int)x1, (int)y1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        }
        int radius = (int)((y1 - y) / 2.0);
        if (bloom) {
            RenderUtils.drawPolygonPartBloom(x, y + (y1 - y) / 2.0, radius, 0, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPartBloom(x, y + (y1 - y) / 2.0, radius, 1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPartBloom(x1, y + (y1 - y) / 2.0, radius, 2, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPartBloom(x1, y + (y1 - y) / 2.0, radius, 3, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        } else {
            RenderUtils.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 0, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 1, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 2, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
            RenderUtils.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 3, ColorUtils.injectAlpha(new Color(color), (int)alpha).getRGB(), ColorUtils.injectAlpha(new Color(color), 0).getRGB());
        }
    }

    public static void drawESP(Entity entity, float colorRed, float colorGreen, float colorBlue, float colorAlpha, float ticks) {
        try {
            double renderPosX = RenderManager.viewerPosX;
            double renderPosY = RenderManager.viewerPosY;
            double renderPosZ = RenderManager.viewerPosZ;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)ticks - renderPosX;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)ticks + (double)(entity.height / 2.0f) - renderPosY;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)ticks - renderPosZ;
            float playerViewY = Wrapper.mc().getRenderManager().playerViewY;
            float playerViewX = Wrapper.mc().getRenderManager().playerViewX;
            boolean thirdPersonView = Wrapper.mc().getRenderManager().options.thirdPersonView == 2;
            GL11.glPushMatrix();
            GlStateManager.translate(xPos, yPos, zPos);
            GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate((float)(thirdPersonView ? -1 : 1) * playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0f);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void glRenderStart() {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
    }

    public static void glRenderStop() {
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawCircle(float cx, double cy, float r, int num_segments, int c) {
        GL11.glPushMatrix();
        cx *= 2.0f;
        cy *= 2.0;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glEnable(2848);
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(3);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static void drawCircleDK(float cx, double cy, float r, int num_segments, int c) {
        GL11.glPushMatrix();
        cx *= 2.0f;
        cy *= 2.0;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glLineWidth(1.4f);
        RenderUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(3);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, (float)((double)y + cy));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        RenderUtils.disableGL2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void enableGL2D() {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void drawSolidBox(AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(7);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
    }

    public static void drawItemWarnIfLowDur(ItemStack stack, float x, float y, float alphaPC, float scale) {
        RenderUtils.drawItemWarnIfLowDur(stack, x, y, alphaPC, scale, 1);
    }

    public static void drawItemWarnIfLowDur(ItemStack stack, float x, float y, float alphaPC, float scale, int count) {
        if (stack.isItemDamaged()) {
            float f;
            float dmgPC = (float)stack.getItemDamage() / (float)stack.getMaxDamage();
            if ((double)f >= 0.7) {
                float timePC;
                long timeDelay = (long)(1000.0f - 650.0f * (dmgPC - 0.9f) * 10.0f);
                float prevTimePC = timePC = (float)(System.currentTimeMillis() % timeDelay) / (float)timeDelay;
                if ((double)((timePC = ((double)timePC > 0.5 ? 1.0f - timePC : timePC) * 2.0f) * alphaPC) < 0.02) {
                    return;
                }
                int color = ColorUtils.getColor(255, 40, 0, MathUtils.clamp(510.0f * timePC * alphaPC, 0.0f, 255.0f));
                mc.getTextureManager().bindTexture(ITEM_WARN_DUR);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 32772);
                if (x != 0.0f || y != 0.0f) {
                    GL11.glTranslated(x, y, 0.0);
                }
                RenderUtils.glColor(color);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                for (int i = 0; i < count; ++i) {
                    Gui.drawModalRectWithCustomSizedTexture(-2, -2, 0.0f, 0.0f, 20, 20, 20.0f, 20.0f);
                }
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GlStateManager.resetColor();
                if (x != 0.0f || y != 0.0f) {
                    GL11.glTranslated(-x, -y, 0.0);
                }
                GL11.glBlendFunc(770, 771);
            }
        }
    }

    public static void drawScreenShaderBackground(ScaledResolution sr, int mouseX, int mouseY) {
        if (Client.screenshader == null) {
            Client.screenshader = new animbackground("/assets/minecraft/vegaline/ui/mainmenu/shaders/backgroundshader.fsh");
            mc.toggleFullscreen();
            mc.toggleFullscreen();
        }
        if (Client.screenshader == null || !Display.isVisible()) {
            return;
        }
        GlStateManager.disableCull();
        GlStateManager.disableDepth();
        RenderUtils.fixShadows();
        GlStateManager.enableTexture2D();
        Client.screenshader.useShader(RenderUtils.mc.displayWidth, RenderUtils.mc.displayHeight, mouseX, mouseY, (float)(System.currentTimeMillis() - Client.initTime) / 1000.0f);
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        RenderUtils.fixShadows();
        GlStateManager.enableDepth();
        if (Client.mainGuiNoise != null) {
            Client.mainGuiNoise.setPlaying(!Panic.stop && GuiMainMenu.quit.to == 0.0f && GuiMainMenu.quit2.to == 0.0f);
        }
    }
}

