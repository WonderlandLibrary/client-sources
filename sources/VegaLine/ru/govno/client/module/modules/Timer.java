/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.io.Serializable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.MusicHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.TPSDetect;

public class Timer
extends Module {
    public static Timer get;
    public Settings TX;
    public Settings TY;
    public Settings TimerF;
    public Settings Randomize;
    public Settings TimeOut;
    public Settings TimeOutMS;
    public Settings Smart;
    public Settings BoundUp;
    public Settings NCPBypass;
    public Settings SmoothWastage;
    public Settings SmartMode;
    public Settings DrawSmart;
    public Settings Render;
    public Settings TimerSFX;
    private static final TimerHelper afkWait;
    private static final TimerHelper timeOutWait;
    private boolean afk = true;
    private float yaw;
    private float pitch;
    private static float forceTimer;
    private boolean smartGo;
    private boolean critical;
    private boolean panicRegen;
    public static double percent;
    public static AnimationUtils percentSmooth;
    public static AnimationUtils smoothInt9;
    private static final AnimationUtils toShowPC;
    private final AnimationUtils maxTriggerAnim = new AnimationUtils(0.0f, 0.0f, 0.03f);
    private final AnimationUtils minTriggerAnim = new AnimationUtils(0.0f, 0.0f, 0.03f);
    private boolean isRegening = false;
    public static boolean forceWastage;
    private final TimerHelper sfxDelay = new TimerHelper();
    public static float x;
    public static float y;
    protected static final ResourceLocation BATTARY_BASE;
    protected static final ResourceLocation BATTARY_OVERLAY;
    protected static final ResourceLocation WAIST_BASE;
    protected static final ResourceLocation WAIST_OVERLAY;
    protected final Tessellator tessellator = Tessellator.getInstance();
    protected final BufferBuilder buffer = this.tessellator.getBuffer();

    public Timer() {
        super("Timer", 0, Module.Category.MOVEMENT);
        this.TX = new Settings("TX", 0.5f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.TX);
        this.TY = new Settings("TY", 0.8f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.TY);
        this.TimerF = new Settings("Timer", 2.0f, 4.0f, 0.1f, this);
        this.settings.add(this.TimerF);
        this.Randomize = new Settings("Randomize", 0.7f, 3.0f, 0.0f, this);
        this.settings.add(this.Randomize);
        this.TimeOut = new Settings("TimeOut", false, (Module)this);
        this.settings.add(this.TimeOut);
        this.TimeOutMS = new Settings("TimeOutMS", 220.0f, 1000.0f, 1.0f, this, () -> this.TimeOut.bValue);
        this.settings.add(this.TimeOutMS);
        this.Smart = new Settings("Smart", true, (Module)this);
        this.settings.add(this.Smart);
        this.BoundUp = new Settings("BoundUp", 0.05f, 0.9f, 0.0f, this, () -> this.Smart.bValue);
        this.settings.add(this.BoundUp);
        this.NCPBypass = new Settings("NCPBypass", false, (Module)this, () -> !this.Smart.bValue);
        this.settings.add(this.NCPBypass);
        this.SmoothWastage = new Settings("SmoothWastage", false, (Module)this, () -> this.Smart.bValue);
        this.settings.add(this.SmoothWastage);
        this.SmartMode = new Settings("SmartMode", "Matrix", this, new String[]{"Matrix", "NCP", "Other", "Vulcan"}, () -> this.Smart.bValue);
        this.settings.add(this.SmartMode);
        this.DrawSmart = new Settings("DrawSmart", true, (Module)this, () -> this.Smart.bValue);
        this.settings.add(this.DrawSmart);
        this.Render = new Settings("Render", "SmoothNine", this, new String[]{"Line", "Plate", "Circle", "SmoothNine"}, () -> this.Smart.bValue && this.DrawSmart.bValue);
        this.settings.add(this.Render);
        this.TimerSFX = new Settings("TimerSFX", "SF", this, new String[]{"None", "Dev", "SF"}, () -> this.Smart.bValue);
        this.settings.add(this.TimerSFX);
        get = this;
    }

    private static void forceWastage() {
        forceWastage = true;
    }

    public static void forceTimer(float value) {
        if (!Timer.get.Smart.bValue) {
            return;
        }
        afkWait.reset();
        Timer.get.afk = false;
        forceTimer = value;
        Timer.forceWastage();
    }

    public static boolean canDrawTimer() {
        return get != null && Timer.get.Smart.bValue && Timer.get.DrawSmart.bValue;
    }

    public static float getWidth() {
        return Timer.get.Render.currentMode.equalsIgnoreCase("SmoothNine") ? 18.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Waist") ? 70.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Plate") ? 28.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Line") ? 60.0f : 19.0f)));
    }

    public static float getHeight() {
        float ext;
        float f = ext = Timer.mc.currentScreen instanceof GuiChat ? 6.0f : 0.0f;
        return Timer.get.Render.currentMode.equalsIgnoreCase("SmoothNine") ? 18.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Waist") ? 14.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Plate") ? 40.0f : (Timer.get.Render.currentMode.equalsIgnoreCase("Line") ? 1.5f + toShowPC.getAnim() * 3.0f + ext : 19.0f)));
    }

    public static float[] getCoordsSettings() {
        return new float[]{Timer.get.TX.fValue, Timer.get.TY.fValue};
    }

    public static float getX(ScaledResolution sr) {
        return (float)sr.getScaledWidth() * Timer.getCoordsSettings()[0] - Timer.getWidth() / 2.0f;
    }

    public static float getY(ScaledResolution sr) {
        return (float)sr.getScaledHeight() * Timer.getCoordsSettings()[1] - Timer.getHeight() / 2.0f;
    }

    public static void setSetsX(float set) {
        ((Settings)Timer.get.settings.get((int)0)).fValue = set;
    }

    public static void setSetsY(float set) {
        ((Settings)Timer.get.settings.get((int)1)).fValue = set;
    }

    public static boolean isHoveredToTimer(int mouseX, int mouseY, ScaledResolution sr) {
        return Timer.canDrawTimer() && RenderUtils.isHovered(mouseX, mouseY, Timer.getX(sr), Timer.getY(sr), Timer.getWidth(), Timer.getHeight());
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        if (this.Smart.bValue && this.DrawSmart.bValue) {
            float dy;
            boolean middle;
            String mode = this.Render.currentMode;
            float x = Timer.getX(sr);
            float y = Timer.getY(sr);
            float w = Timer.getWidth();
            float h = Timer.getHeight();
            float dx = (float)sr.getScaledWidth() / 2.0f - (x + w / 2.0f);
            boolean bl = middle = Math.sqrt(dx * dx + (dy = (float)sr.getScaledHeight() / 2.0f - (y + h / 2.0f)) * dy) < 2.0 && !mode.equalsIgnoreCase("Plate");
            if (middle) {
                if (mode.equalsIgnoreCase("Circle")) {
                    h /= 1.25f;
                    w /= 1.25f;
                }
                x = (float)sr.getScaledWidth() / 2.0f - w / 2.0f;
                y = (float)sr.getScaledHeight() / 2.0f - h / 2.0f - 0.25f;
                x += Crosshair.get.crossPosMotions[0];
                y += Crosshair.get.crossPosMotions[1];
            }
            float x2 = x + w;
            float y2 = y + h;
            float pc = percentSmooth.getAnim();
            switch (mode) {
                case "Line": {
                    int colStep = (int)(150.0f * pc);
                    int c1 = ClientColors.getColor1(0, pc);
                    int c2 = ClientColors.getColor1(colStep, 0.5f + pc * 0.5f);
                    int c3 = ClientColors.getColor1(colStep, 0.75f + pc * 0.25f);
                    int c4 = ClientColors.getColor1(colStep * 3);
                    float extX = 0.0f;
                    float extY = this.maxTriggerAnim.getAnim() * this.maxTriggerAnim.anim * 1.5f;
                    RenderUtils.drawLightContureRect(x - extX, y - extY, x2 + extX, y2 + extY, Integer.MIN_VALUE);
                    RenderUtils.drawWaveGradient(x - extX, y - extY, x + w * pc + extX, y2 + extY, 1.0f, c1, c2, c3, c4, true, false);
                    c1 = ColorUtils.swapAlpha(c1, (float)ColorUtils.getAlphaFromColor(c1) / 10.0f);
                    c2 = ColorUtils.swapAlpha(c2, (float)ColorUtils.getAlphaFromColor(c2) / 10.0f);
                    c3 = ColorUtils.swapAlpha(c3, (float)ColorUtils.getAlphaFromColor(c3) / 10.0f);
                    c4 = ColorUtils.swapAlpha(c4, (float)ColorUtils.getAlphaFromColor(c4) / 10.0f);
                    RenderUtils.drawWaveGradient(x - extX, y - extY, x + w + extX, y2 + extY, 0.6f, c1, c2, c3, c4, true, false);
                    float showPC = toShowPC.getAnim();
                    boolean show = (double)showPC > 0.05;
                    Object str = "Timer";
                    CFontRenderer font = Fonts.mntsb_10;
                    float strW = font.getStringWidth((String)str);
                    float texX = x + w / 2.0f - strW / 2.0f;
                    float texY = y + 4.0f - extY;
                    int texCol = ColorUtils.getFixedWhiteColor();
                    if (Timer.mc.currentScreen instanceof GuiChat) {
                        font.drawStringWithShadow((String)str, texX, texY, texCol);
                        break;
                    }
                    if (!show) break;
                    str = ((double)percentSmooth.getAnim() > percent ? "<" : "") + (int)(percent * 100.0) + ((double)percentSmooth.getAnim() < percent ? ">" : "");
                    strW = font.getStringWidth((String)str);
                    texX = x + (w - strW) * pc;
                    texY = y - 1.0f - showPC * 2.5f;
                    float texAlpha = 255.0f * showPC * (0.5f + showPC * 0.5f) * (0.75f + pc * 0.25f);
                    texCol = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), texAlpha);
                    if (!(texAlpha > 32.0f)) break;
                    font.drawStringWithShadow((String)str, texX, texY, texCol);
                    break;
                }
                case "Circle": {
                    RenderUtils.resetBlender();
                    float extS = MathUtils.clamp((middle ? toShowPC.getAnim() : 1.0f) + (this.minTriggerAnim.getAnim() + this.maxTriggerAnim.getAnim()) / 5.0f, 0.0f, 1.0f);
                    boolean crit = this.critical;
                    if (!middle || Timer.mc.currentScreen instanceof GuiChat) {
                        RenderUtils.drawSmoothCircle(x + w / 2.0f, y + h / 2.0f, w / 2.0f + 1.0f, ColorUtils.getColor(0, 0, 0, 60));
                    }
                    GL11.glEnable(3042);
                    if (middle && Timer.mc.currentScreen instanceof GuiChat && Mouse.isButtonDown(0) && MathUtils.getDifferenceOf(sr.getScaledWidth(), Mouse.getX()) < (double)w && MathUtils.getDifferenceOf(sr.getScaledHeight(), Mouse.getY()) < (double)h) {
                        Fonts.comfortaaBold_12.drawStringWithOutline("Timer indicator has centered", x + w / 2.0f - (float)Fonts.comfortaaBold_12.getStringWidth("Timer indicator has centered") / 2.0f, y - 11.0f, -1);
                    }
                    if ((double)percentSmooth.getAnim() >= 0.01 || this.minTriggerAnim.getAnim() > 0.0f) {
                        if (middle && extS > 0.03f && Timer.mc.gameSettings.thirdPersonView == 0) {
                            RenderUtils.drawCircledTHud(x + w / 2.0f, y + h / 2.0f, w / 2.25f * extS * (middle ? 0.6f + 0.4f * pc : 1.0f), 1.0f, Integer.MIN_VALUE, extS * extS * 195.0f * (0.5f + 0.5f * pc) * extS, 2.0f * extS + 0.05f);
                        }
                        if (Timer.mc.gameSettings.thirdPersonView == 0 || !middle) {
                            RenderUtils.drawClientCircle(x + w / 2.0f, y + h / 2.0f, w / 2.25f * extS * (middle ? 0.6f + 0.4f * pc : 1.0f), percentSmooth.getAnim() * 359.0f, middle ? 3.0f : 3.5f + 3.0f * pc, extS * extS * (0.5f + 0.5f * pc));
                        }
                    }
                    if (!middle && extS > 0.03f) {
                        RenderUtils.drawSmoothCircle(x + w / 2.0f, y + h / 2.0f, w / 2.5f + 1.0f, ColorUtils.getColor(0, 0, 0, 150));
                    }
                    if (this.minTriggerAnim.getAnim() != 0.0f || this.maxTriggerAnim.getAnim() != 0.0f) {
                        float aPCT = MathUtils.clamp(this.minTriggerAnim.getAnim() + this.maxTriggerAnim.getAnim(), 0.0f, 1.0f);
                        float tR = w / 2.25f - (middle ? 4.0f : 3.0f) + 4.0f * toShowPC.getAnim();
                        RenderUtils.drawClientCircleWithOverallToColor(x + w / 2.0f, y + h / 2.0f, tR += aPCT * 2.0f, 359.0f, aPCT * 5.0f, aPCT, ColorUtils.getOverallColorFrom(-1, ColorUtils.getColor(255, 0, 0), this.minTriggerAnim.getAnim()), aPCT);
                    }
                    String pppc = "" + (Serializable)(crit ? "*-*" : Integer.valueOf((int)(percent * 100.0)));
                    float strW2 = Fonts.mntsb_10.getStringWidth(pppc);
                    if (!middle) {
                        Fonts.mntsb_10.drawString(pppc, x + w / 2.0f - strW2 / 2.0f, y + 9.5f, crit ? ClientColors.getColor1() : ColorUtils.getOverallColorFrom(Integer.MAX_VALUE, -1, (float)percent));
                    }
                    RenderUtils.resetBlender();
                    break;
                }
                case "Plate": {
                    int col1 = ClientColors.getColor1();
                    int col2 = ClientColors.getColor2(-324);
                    int col3 = ClientColors.getColor2(0);
                    int col4 = ClientColors.getColor1(972);
                    if (this.maxTriggerAnim.getAnim() > 0.0f) {
                        col1 = ColorUtils.getOverallColorFrom(col1, -1, this.maxTriggerAnim.getAnim());
                        col2 = ColorUtils.getOverallColorFrom(col2, -1, this.maxTriggerAnim.getAnim());
                        col3 = ColorUtils.getOverallColorFrom(col3, -1, this.maxTriggerAnim.getAnim());
                        col4 = ColorUtils.getOverallColorFrom(col4, -1, this.maxTriggerAnim.getAnim());
                    }
                    if (this.minTriggerAnim.getAnim() > 0.0f) {
                        col1 = ColorUtils.getOverallColorFrom(col1, ColorUtils.getColor(255, 0, 0), this.minTriggerAnim.getAnim());
                        col2 = ColorUtils.getOverallColorFrom(col2, ColorUtils.getColor(255, 0, 0), this.minTriggerAnim.getAnim());
                        col3 = ColorUtils.getOverallColorFrom(col3, ColorUtils.getColor(255, 0, 0), this.minTriggerAnim.getAnim());
                        col4 = ColorUtils.getOverallColorFrom(col4, ColorUtils.getColor(255, 0, 0), this.minTriggerAnim.getAnim());
                    }
                    GL11.glDisable(3008);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.shadeModel(7425);
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GL11.glTranslated(0.0, -(-this.minTriggerAnim.getAnim() + this.maxTriggerAnim.getAnim()) * 2.0f, 0.0);
                    mc.getTextureManager().bindTexture(BATTARY_BASE);
                    this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    this.buffer.pos(x, y).tex(0.0, 0.0).color(col1).endVertex();
                    this.buffer.pos(x, y2).tex(0.0, 1.0).color(col2).endVertex();
                    this.buffer.pos(x2, y2).tex(1.0, 1.0).color(col3).endVertex();
                    this.buffer.pos(x2, y).tex(1.0, 0.0).color(col4).endVertex();
                    this.tessellator.draw();
                    mc.getTextureManager().bindTexture(BATTARY_OVERLAY);
                    this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    this.buffer.pos(x, y).tex(0.0, 0.0).color(col1).endVertex();
                    this.buffer.pos(x, y2).tex(0.0, 1.0).color(col2).endVertex();
                    this.buffer.pos(x2, y2).tex(1.0, 1.0).color(col3).endVertex();
                    this.buffer.pos(x2, y).tex(1.0, 0.0).color(col4).endVertex();
                    StencilUtil.initStencilToWrite();
                    RenderUtils.drawRect(x, y + 2.5f + 34.0f * (1.0f - pc), x2, y2 - 2.0f, -1);
                    StencilUtil.readStencilBuffer(1);
                    this.tessellator.draw();
                    StencilUtil.uninitStencilBuffer();
                    GL11.glTranslated(0.0, (-this.minTriggerAnim.getAnim() + this.maxTriggerAnim.getAnim()) * 2.0f, 0.0);
                    GlStateManager.shadeModel(7424);
                    GlStateManager.depthMask(true);
                    GL11.glEnable(3008);
                    GlStateManager.resetColor();
                    break;
                }
                case "Waist": {
                    int col1 = ClientColors.getColor1();
                    int col2 = ClientColors.getColor2(-324);
                    int col3 = ClientColors.getColor2(0);
                    int col4 = ClientColors.getColor1(972);
                    int black = ColorUtils.getColor(0, 140);
                    GL11.glDisable(3008);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.shadeModel(7425);
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    mc.getTextureManager().bindTexture(WAIST_BASE);
                    this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    this.buffer.pos(x, y).tex(0.0, 0.0).color(black).endVertex();
                    this.buffer.pos(x, y2).tex(0.0, 1.0).color(black).endVertex();
                    this.buffer.pos(x2, y2).tex(1.0, 1.0).color(black).endVertex();
                    this.buffer.pos(x2, y).tex(1.0, 0.0).color(black).endVertex();
                    this.tessellator.draw();
                    mc.getTextureManager().bindTexture(WAIST_OVERLAY);
                    this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    this.buffer.pos(x, y).tex(0.0, 0.0).color(col1).endVertex();
                    this.buffer.pos(x, y2).tex(0.0, 1.0).color(col2).endVertex();
                    this.buffer.pos(x2, y2).tex(1.0, 1.0).color(col3).endVertex();
                    this.buffer.pos(x2, y).tex(1.0, 0.0).color(col4).endVertex();
                    StencilUtil.initStencilToWrite();
                    float overlayX1 = x + 22.0f;
                    float overlayX2 = overlayX1 + 45.0f * MathUtils.clamp(pc * 1.005f, 0.0f, 1.0f);
                    RenderUtils.drawRect(overlayX1, y, overlayX2, y2, -1);
                    StencilUtil.readStencilBuffer(1);
                    this.tessellator.draw();
                    StencilUtil.uninitStencilBuffer();
                    GlStateManager.shadeModel(7424);
                    GlStateManager.depthMask(true);
                    GL11.glEnable(3008);
                    GlStateManager.resetColor();
                    CFontRenderer font = Fonts.comfortaaBold_14;
                    font.drawString((int)(percent * 100.0) + "%", x + 3.0f, y + 5.5f, -65537);
                    break;
                }
                case "SmoothNine": {
                    float cosY;
                    int ecoC;
                    int bgCol = ColorUtils.getColor(18, 18, 18);
                    int bgCol2 = ColorUtils.getColor(36, 36, 36);
                    Timer.smoothInt9.to = (int)MathUtils.clamp(pc * 9.0f + 0.5f, 0.0f, 9.0f);
                    float smooth9 = smoothInt9.getAnim();
                    int startRad = ecoC = 30;
                    int endRad = (int)((float)startRad + (360.0f - (float)ecoC * 2.0f) * pc);
                    int endRadBG = (int)((float)startRad + (360.0f - (float)ecoC * 2.0f));
                    float circleW = 3.0f;
                    float circleRange = h - 6.0f - circleW / 2.0f;
                    circleRange /= 2.0f;
                    int rad = startRad;
                    float trAPC = this.maxTriggerAnim.getAnim() + this.minTriggerAnim.getAnim();
                    float f = trAPC > 1.0f ? 1.0f : (trAPC = trAPC < 0.0f ? 0.0f : trAPC);
                    if (trAPC > 0.0f) {
                        int trCol = ColorUtils.getOverallColorFrom(ColorUtils.getColor(255, (int)MathUtils.clamp(255.0f * trAPC, 0.0f, 255.0f)), ColorUtils.getColor(255, 0, 0, MathUtils.clamp(255.0f * trAPC, 0.0f, 255.0f)), (double)this.minTriggerAnim.anim > 0.03 ? 1.0f : 0.0f);
                        RenderUtils.drawSmoothCircle(x + w / 2.0f, y + h / 2.0f, circleRange + 5.0f, trCol);
                    }
                    RenderUtils.drawSmoothCircle(x + w / 2.0f, y + h / 2.0f, circleRange + 5.0f - 2.0f * trAPC, bgCol);
                    RenderUtils.enableGL2D();
                    RenderUtils.glColor(-1);
                    GL11.glDisable(2852);
                    GL11.glLineWidth(0.5f);
                    StencilUtil.initStencilToWrite();
                    GL11.glBegin(3);
                    for (rad = endRadBG; rad > startRad; rad -= 6) {
                        float sinX = (float)((double)(x + w / 2.0f) - Math.sin(Math.toRadians(rad)));
                        cosY = (float)((double)(y + h / 2.0f) + Math.cos(Math.toRadians(rad)));
                        float sinX2 = (float)((double)(x + w / 2.0f) - Math.sin(Math.toRadians(rad)) * (double)(circleRange + circleW));
                        float cosY2 = (float)((double)(y + h / 2.0f) + Math.cos(Math.toRadians(rad)) * (double)(circleRange + circleW));
                        GL11.glVertex2d(sinX, cosY);
                        GL11.glVertex2d(sinX2, cosY2);
                    }
                    GL11.glEnd();
                    StencilUtil.readStencilBuffer(1);
                    GL11.glPointSize(circleW);
                    RenderUtils.glColor(bgCol2);
                    GL11.glBegin(0);
                    for (rad = endRadBG; rad > endRad; rad -= 6) {
                        float sinX = (float)((double)(x + w / 2.0f) - Math.sin(Math.toRadians(rad)) * (double)(circleRange + circleW / 2.0f));
                        cosY = (float)((double)(y + h / 2.0f) + Math.cos(Math.toRadians(rad)) * (double)(circleRange + circleW / 2.0f));
                        GL11.glVertex2d(sinX, cosY);
                    }
                    GL11.glEnd();
                    GL11.glBegin(0);
                    for (rad = endRad; rad > startRad; rad -= 6) {
                        int cccc = ClientColors.getColor1(4800 - rad * 6, (1.0f - trAPC / 3.0f) / 1.5f);
                        RenderUtils.glColor(cccc);
                        float sinX = (float)((double)(x + w / 2.0f) - Math.sin(Math.toRadians(rad)) * (double)(circleRange + circleW / 2.0f));
                        float cosY2 = (float)((double)(y + h / 2.0f) + Math.cos(Math.toRadians(rad)) * (double)(circleRange + circleW / 2.0f));
                        GL11.glVertex2d(sinX, cosY2);
                    }
                    GL11.glEnd();
                    GL11.glPointSize(1.0f);
                    StencilUtil.uninitStencilBuffer();
                    GL11.glLineWidth(1.0f);
                    RenderUtils.disableGL2D();
                    GL11.glEnable(3042);
                    GL11.glEnable(3553);
                    Timer.smoothInt9.speed = 0.1f;
                    if (MathUtils.getDifferenceOf(Timer.smoothInt9.to, smoothInt9.getAnim()) < 0.1) {
                        smoothInt9.setAnim(Timer.smoothInt9.to);
                    }
                    int col1 = ClientColors.getColor1(0, 1.0f - trAPC / 3.0f);
                    int col2 = ClientColors.getColor2(0, 1.0f - trAPC / 3.0f);
                    CFontRenderer font = Fonts.mntsb_14;
                    StencilUtil.initStencilToWrite();
                    RenderUtils.drawSmoothCircle(x + w / 2.0f, y + h / 2.0f, 3.5f, -1);
                    StencilUtil.readStencilBuffer(1);
                    for (int i = 10; i > 0; --i) {
                        float aPCT = (float)MathUtils.clamp(1.0 - MathUtils.getDifferenceOf(y + h / 2.0f + (float)(i - 1) * 7.0f - smooth9 * 7.0f, y + h / 2.0f) / 4.0 / 2.0, 0.0, 1.0);
                        if (!(aPCT > 0.3f) || !((float)ColorUtils.getAlphaFromColor(col1) * aPCT >= 33.0f)) continue;
                        float tx = x + w / 2.0f - (float)font.getStringWidth(String.valueOf((int)((float)i - 0.5f))) / 2.0f;
                        float ty = y + h / 2.0f - 1.5f + (float)(i - 1) * 7.0f - smooth9 * 7.0f;
                        font.drawVGradientString(String.valueOf((int)((float)i - 0.5f)), tx, ty, ColorUtils.swapAlpha(col2, (float)ColorUtils.getAlphaFromColor(col2) * aPCT), ColorUtils.swapAlpha(col1, (float)ColorUtils.getAlphaFromColor(col1) * aPCT));
                    }
                    StencilUtil.uninitStencilBuffer();
                    break;
                }
            }
        }
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        Packet packet;
        if ((this.actived || forceWastage) && this.smartGo && (packet = event.getPacket()) instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook TP = (SPacketPlayerPosLook)packet;
            if (Minecraft.player.getDistance(TP.getX(), TP.getY(), TP.getZ()) > 20.0 || this.isNcpTimerDisabler()) {
                return;
            }
            this.panicRegen = true;
            this.smartGo = false;
            percent /= 1.5;
            this.critical = true;
        }
    }

    private double[] timerArgs(String mode, boolean flaged, double tpsPC20, double timerSpeed) {
        double chargeSP = 1.0;
        double dropSP = 0.0;
        double regenSP = 0.0;
        switch (mode) {
            case "Matrix": {
                chargeSP = 0.035 / tpsPC20;
                dropSP = 0.02 * timerSpeed * tpsPC20;
                regenSP = 0.5 / tpsPC20;
                break;
            }
            case "NCP": {
                chargeSP = 0.06 / tpsPC20;
                dropSP = 0.046 * timerSpeed * tpsPC20;
                regenSP = 0.75 / tpsPC20;
                break;
            }
            case "Other": {
                chargeSP = tpsPC20;
                dropSP = 0.046 * timerSpeed * tpsPC20;
                regenSP = 0.85 / tpsPC20;
                break;
            }
            case "Vulcan": {
                chargeSP = 0.45 / tpsPC20;
                dropSP = 0.11 * timerSpeed * tpsPC20;
                regenSP = tpsPC20;
            }
        }
        if (flaged) {
            chargeSP /= 1.425;
            regenSP /= 3.5;
        }
        return new double[]{chargeSP, dropSP, regenSP};
    }

    private boolean updateAfkStatus(TimerHelper timer) {
        boolean FORCE_RECHARGE;
        if (!timer.hasReached(100.0)) {
            this.yaw = Minecraft.player.lastReportedYaw;
            this.pitch = EntityPlayerSP.lastReportedPitch;
        }
        double player3DSpeed = Math.sqrt(Entity.Getmotionx * Entity.Getmotionx + Entity.Getmotiony * Entity.Getmotiony + Entity.Getmotionz * Entity.Getmotionz);
        boolean bl = FORCE_RECHARGE = Minecraft.player.ticksExisted == 1 || Minecraft.player.isDead;
        if (!FORCE_RECHARGE) {
            boolean bl2 = FORCE_RECHARGE = FreeCam.get != null && FreeCam.get.actived;
        }
        if (FORCE_RECHARGE || this.yaw == Minecraft.player.lastReportedYaw && this.pitch == EntityPlayerSP.lastReportedPitch && (player3DSpeed == 0.0784000015258789 || player3DSpeed == 0.0 || player3DSpeed == 0.02) && !forceWastage) {
            if (timer.hasReached(150.0)) {
                timer.reset();
                this.afk = true;
            }
        } else {
            this.afk = false;
            timer.reset();
        }
        if (Minecraft.player.ticksExisted == 1 || Minecraft.player.isDead) {
            this.afk = true;
            percent = percent < (double)0.8f ? (double)0.8f : percent;
            Timer.percentSmooth.to = (float)percent;
            this.critical = false;
        }
        return this.afk;
    }

    private double updateTimerPercent(double[] args, boolean isAfk, float boundUp) {
        if (!isAfk && percent < 1.0 && !this.actived) {
            double upped = (float)(args[0] * (double)0.2f) / 5.0f;
            if (args[2] / (double)(1.0f - boundUp) > upped + percent - (double)0.02f - (double)boundUp && !forceWastage) {
                percent += upped;
            }
            this.critical = false;
        }
        if (this.panicRegen && percent == 1.0) {
            this.panicRegen = false;
            if (this.critical) {
                this.critical = false;
            }
        }
        if (percent < 1.0 && isAfk) {
            percent += args[0] / (double)(1.0f - boundUp);
            this.isRegening = true;
            this.critical = false;
        }
        if (!isAfk && percent > (double)boundUp && (this.smartGo || forceWastage)) {
            percent = Math.max(percent - args[1], (double)boundUp);
        }
        percent = MathUtils.clamp(percent, 0.0, 1.0);
        return percent;
    }

    private boolean canDisableByTimeOut(boolean timeOutEnabled, int timeOutMS) {
        return this.actived && timeOutEnabled && timeOutWait.hasReached(timeOutMS);
    }

    private boolean canAbuseTimerSpeed(boolean isSmart) {
        String ebMode;
        boolean FORCE_STOP = false;
        if (ElytraBoost.get.actived && ElytraBoost.canElytra() && ((ebMode = ElytraBoost.get.Mode.currentMode).equalsIgnoreCase("MatrixFly2") && !ElytraBoost.get.NoTimerDefunction.bValue || ebMode.equalsIgnoreCase("MatrixFly3"))) {
            FORCE_STOP = true;
        }
        return forceWastage && this.smartGo || this.actived && (this.smartGo && !this.critical || !isSmart) && !FORCE_STOP;
    }

    private double getTimerBoostSpeed(boolean can, boolean smart, float boundUp) {
        double speed = 1.0;
        if (can) {
            float randomVal;
            double randomize;
            float timer = this.TimerF.fValue;
            speed = smart && this.SmoothWastage.bValue ? (double)(1.0f + (timer - 1.0f) / 4.0f) + (double)(1.0f + (timer - 1.0f) / 1.25f) * (percent - (double)boundUp) : (double)timer;
            if (speed + (randomize = (double)(-(randomVal = this.Randomize.fValue)) + (double)randomVal * Math.random() * 2.0) > 1.0 || speed < 1.0) {
                speed += randomize;
            }
            if (forceWastage) {
                speed = forceTimer;
            }
        }
        return can ? MathUtils.clamp(speed, 0.05, 20.0) : 1.0;
    }

    private boolean isNcpTimerDisabler() {
        return this.NCPBypass.bValue && !this.Smart.bValue;
    }

    private int timerSFXSleepMS() {
        return 50;
    }

    @Override
    public void alwaysUpdate() {
        if (afkWait == null || Minecraft.player == null) {
            return;
        }
        boolean smartTimer = this.Smart.bValue;
        float boundUp = this.BoundUp.fValue;
        boolean canABB = this.canAbuseTimerSpeed(smartTimer);
        String sfxMode = this.TimerSFX.currentMode;
        boolean doSfx = !sfxMode.equalsIgnoreCase("None");
        double speed = this.getTimerBoostSpeed(canABB, smartTimer, boundUp);
        if (smartTimer) {
            double prevPercent = percent;
            double[] ARGS = this.timerArgs(this.SmartMode.currentMode, this.panicRegen, TPSDetect.getTPSServer() / 20.0f, Timer.mc.timer.getGameSpeed() - 1.0);
            this.smartGo = this.updateTimerPercent(ARGS, this.updateAfkStatus(afkWait), boundUp) > (double)boundUp && !this.afk && !this.critical;
            Timer.percentSmooth.to = (float)percent;
            Timer.toShowPC.to = percent > (double)boundUp && percent < 1.0 || this.minTriggerAnim.getAnim() > 0.0f || this.maxTriggerAnim.getAnim() > 0.0f ? 1.0f : 0.0f;
            Timer.toShowPC.speed = 0.1f;
            if (doSfx && prevPercent != percent && (percent == (double)boundUp || percent == 1.0)) {
                MusicHelper.playSound((percent < prevPercent ? "timerlow" : "timermax") + sfxMode.toLowerCase() + ".wav", sfxMode.equalsIgnoreCase("Dev") ? 0.45f : 0.3f);
                if (percent < prevPercent) {
                    this.minTriggerAnim.to = 1.01f;
                } else {
                    this.maxTriggerAnim.to = 1.01f;
                }
            }
            if (doSfx && this.sfxDelay.hasReached(this.timerSFXSleepMS()) && (int)(prevPercent * 100.0) != (int)(percent * 100.0)) {
                if (prevPercent > percent && percent != 0.0 && this.smartGo) {
                    MusicHelper.playSound(this.smartGo ? "timertickdrop.wav" : "timertickcharge.wav", sfxMode.equalsIgnoreCase("Dev") ? 0.45f : 0.135f);
                    this.sfxDelay.reset();
                }
                if (prevPercent < percent && percent != 1.0 && this.isRegening && (this.afk || !this.panicRegen && (int)(prevPercent * 100.0) != (int)(percent * 100.0))) {
                    MusicHelper.playSound("timertickcharge.wav", sfxMode.equalsIgnoreCase("Dev") ? 0.45f : 0.135f);
                    this.sfxDelay.reset();
                }
            }
            if (this.maxTriggerAnim.getAnim() > 1.0f) {
                this.maxTriggerAnim.setAnim(1.0f);
                this.maxTriggerAnim.to = 0.0f;
            }
            if (this.minTriggerAnim.getAnim() > 1.0f) {
                this.minTriggerAnim.setAnim(1.0f);
                this.minTriggerAnim.to = 0.0f;
            }
            if (this.maxTriggerAnim.to == 0.0f && (double)this.maxTriggerAnim.getAnim() < 0.03) {
                this.maxTriggerAnim.setAnim(0.0f);
            }
            if (this.minTriggerAnim.to == 0.0f && (double)this.minTriggerAnim.getAnim() < 0.03) {
                this.minTriggerAnim.setAnim(0.0f);
            }
            this.minTriggerAnim.speed = 0.1f;
            this.maxTriggerAnim.speed = 0.075f;
        } else {
            if (this.actived && Minecraft.player.ticksExisted % 20 == 0 && this.isNcpTimerDisabler()) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.posX, Minecraft.player.posY - (Minecraft.player.onGround ? 0.1 : 1.1), Minecraft.player.posZ, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch, Minecraft.player.onGround));
            }
            if (percent != 1.0) {
                this.smartGo = false;
                Timer.percentSmooth.to = 1.0f;
                percent = 1.0;
                Timer.toShowPC.to = 0.0f;
            }
        }
        if (this.canDisableByTimeOut(this.TimeOut.bValue, (int)this.TimeOutMS.fValue)) {
            this.toggle(false);
            return;
        }
        Timer.mc.timer.speed = speed;
        forceWastage = false;
    }

    @Override
    public String getDisplayName() {
        return this.Smart.bValue ? this.getName() + this.getSuff() + (this.panicRegen || this.critical ? "Flagg" : "Smart") : this.getDisplayByDouble(this.TimerF.fValue);
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            timeOutWait.reset();
        } else {
            Timer.mc.timer.speed = 1.0;
        }
        super.onToggled(actived);
    }

    static {
        afkWait = new TimerHelper();
        timeOutWait = new TimerHelper();
        forceTimer = 1.0f;
        percent = 1.0;
        percentSmooth = new AnimationUtils(1.0f, 1.0f, 0.12f);
        smoothInt9 = new AnimationUtils(9.0f, 9.0f, 0.06f);
        toShowPC = new AnimationUtils(0.0f, 0.0f, 0.15f);
        forceWastage = false;
        BATTARY_BASE = new ResourceLocation("vegaline/modules/timer/battary_base.png");
        BATTARY_OVERLAY = new ResourceLocation("vegaline/modules/timer/battary_overlay.png");
        WAIST_BASE = new ResourceLocation("vegaline/modules/timer/waist_base.png");
        WAIST_OVERLAY = new ResourceLocation("vegaline/modules/timer/waist_overlay.png");
    }
}

