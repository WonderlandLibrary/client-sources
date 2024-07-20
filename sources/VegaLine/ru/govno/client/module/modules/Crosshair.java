/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Crosshair
extends Module {
    public static Crosshair get;
    public Settings Mode;
    public Settings PickColor;
    final AnimationUtils anim = new AnimationUtils(360.0f, 360.0f, 0.08f);
    AnimationUtils mousePreX = new AnimationUtils(0.0f, 0.0f, 0.025f);
    AnimationUtils mousePreY = new AnimationUtils(0.0f, 0.0f, 0.025f);
    public float[] crossPosMotions = new float[]{0.0f, 0.0f};

    public Crosshair() {
        super("Crosshair", 0, Module.Category.RENDER);
        this.Mode = new Settings("Mode", "Sniper", (Module)this, new String[]{"Sniper", "Quad", "Circle", "Fortnite", "NewBalance"});
        this.settings.add(this.Mode);
        this.PickColor = new Settings("PickColor", ColorUtils.getColor(30, 80, 255), (Module)this, () -> !this.Mode.currentMode.equalsIgnoreCase("Fortnite"));
        this.settings.add(this.PickColor);
        get = this;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Mode.currentMode);
    }

    @Override
    public void onRender2D(ScaledResolution sr) {
        int crosshairColor = this.PickColor.color;
        float scaledWidth = GuiScreen.width;
        float scaledHeight = GuiScreen.height;
        int nullAlpha = ColorUtils.swapAlpha(crosshairColor, 0.0f);
        this.mousePreX.to = 0.0f;
        this.mousePreY.to = 0.0f;
        if (Crosshair.mc.gameSettings.thirdPersonView == 0 && !Crosshair.mc.gameSettings.showDebugInfo) {
            if (this.Mode.currentMode.equalsIgnoreCase("Circle")) {
                RenderUtils.fixShadows();
                this.anim.to = MathHelper.clamp(Minecraft.player.getCooledAttackStrength(0.0f) * 380.0f, 0.0f, 361.0f);
                this.anim.speed = 0.1f + (float)((double)0.1f * (MathUtils.getDifferenceOf(360.0f, this.anim.getAnim()) / 360.0));
                RenderUtils.drawCroneShadow(scaledWidth / 2.0f, scaledHeight / 2.0f, 0, 360, 4.0f, 1.25f, ColorUtils.getColor(0, 0, 0, 50), ColorUtils.getColor(0, 0, 0, 50), false);
                RenderUtils.drawCroneShadow(scaledWidth / 2.0f, scaledHeight / 2.0f, 360 - (int)this.anim.getAnim() + 90, 450, 3.0f, 1.5f, 0, crosshairColor, false);
                RenderUtils.drawCroneShadow(scaledWidth / 2.0f, scaledHeight / 2.0f, 360 - (int)this.anim.getAnim() + 90, 450, 4.5f, 1.5f, crosshairColor, 0, false);
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Sniper")) {
                RenderUtils.drawRect(scaledWidth / 2.0f - 0.25f, scaledHeight / 2.0f - 0.25f, scaledWidth / 2.0f + 0.25f, scaledHeight / 2.0f + 0.25f, crosshairColor);
                RenderUtils.fixShadows();
                RenderUtils.drawVGradientRect(scaledWidth / 2.0f - 0.25f, scaledHeight / 2.0f - 7.0f, scaledWidth / 2.0f + 0.25f, scaledHeight / 2.0f - 1.0f, nullAlpha, crosshairColor);
                RenderUtils.drawVGradientRect(scaledWidth / 2.0f - 0.25f, scaledHeight / 2.0f + 1.25f, scaledWidth / 2.0f + 0.25f, scaledHeight / 2.0f + 7.0f, crosshairColor, nullAlpha);
                RenderUtils.drawGradientSideways(scaledWidth / 2.0f - 7.0f, scaledHeight / 2.0f - 0.25f, scaledWidth / 2.0f - 1.25f, scaledHeight / 2.0f + 0.25f, nullAlpha, crosshairColor);
                RenderUtils.drawGradientSideways(scaledWidth / 2.0f + 1.25f, scaledHeight / 2.0f - 0.25f, scaledWidth / 2.0f + 7.0f, scaledHeight / 2.0f + 0.25f, crosshairColor, nullAlpha);
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Quad")) {
                float climb = 4.0f;
                float radius = climb * Minecraft.player.getCooledAttackStrength(mc.getRenderPartialTicks());
                RenderUtils.drawRect(scaledWidth / 2.0f - radius - 0.5f, scaledHeight / 2.0f - radius, scaledWidth / 2.0f - radius, scaledHeight / 2.0f + radius, crosshairColor);
                RenderUtils.drawRect(scaledWidth / 2.0f + radius, scaledHeight / 2.0f - radius, scaledWidth / 2.0f + radius + 0.5f, scaledHeight / 2.0f + radius, crosshairColor);
                RenderUtils.drawRect(scaledWidth / 2.0f - radius, scaledHeight / 2.0f - radius - 0.5f, scaledWidth / 2.0f + radius, scaledHeight / 2.0f - radius, crosshairColor);
                RenderUtils.drawRect(scaledWidth / 2.0f - radius, scaledHeight / 2.0f + radius, scaledWidth / 2.0f + radius, scaledHeight / 2.0f + radius + 0.5f, crosshairColor);
            }
            if (this.Mode.currentMode.equalsIgnoreCase("Fortnite")) {
                RenderUtils.drawSmoothCircle(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 1.75f, ColorUtils.getColor(10, 10, 10));
                RenderUtils.drawSmoothCircle(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 1.2f, -1);
            }
            if (this.Mode.currentMode.equalsIgnoreCase("NewBalance")) {
                this.updateMotions();
                this.draw((float)(sr.getScaledWidth() / 2) - this.getMouseMotion()[0], (float)(sr.getScaledHeight() / 2) - this.getMouseMotion()[1], crosshairColor);
            }
        }
    }

    void updateMotions() {
        boolean vantuz = Minecraft.player.ticksExisted >= 10;
        this.mousePreX.to = (Minecraft.player.lastReportedPreYaw - Minecraft.player.rotationYaw) * 3.5f * (float)vantuz;
        this.mousePreY.to = (EntityPlayerSP.lastReportedPrePitch - Minecraft.player.rotationPitch) * 5.0f * (float)vantuz;
        this.crossPosMotions[0] = -this.mousePreX.getAnim();
        this.crossPosMotions[1] = -this.mousePreY.getAnim();
    }

    public float[] getMouseMotion() {
        return new float[]{this.mousePreX.getAnim(), this.mousePreY.getAnim()};
    }

    public void draw(float x, float y, int color) {
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderUtils.drawPolygonPartsGlowBackSAlpha(x, y, 4.0f, 1, color, 0, ColorUtils.getGLAlphaFromColor(color), true);
    }
}

