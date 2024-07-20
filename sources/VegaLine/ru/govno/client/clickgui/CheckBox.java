/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.clickgui.Set;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class CheckBox
extends Set {
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils anim2 = new AnimationUtils(0.0f, 0.0f, 0.06f);
    AnimationUtils blya = new AnimationUtils(0.0f, 0.0f, 0.12f);

    public CheckBox(Settings setting) {
        super(setting);
    }

    @Override
    public void onGuiClosed() {
    }

    @Override
    public void drawScreen(float x, float y, int step, int mouseX, int mouseY, float partialTicks) {
        float pX2;
        super.drawScreen(x, y, step, mouseX, mouseY, partialTicks);
        float scaledAlphaPercent = ClickGuiScreen.globalAlpha.getAnim() / 255.0f;
        scaledAlphaPercent *= scaledAlphaPercent;
        this.anim2.speed = 0.0175f / (float)MathUtils.clamp(MathUtils.getDifferenceOf(this.anim2.getAnim(), this.anim.getAnim()), 0.5, 10.0);
        this.anim.to = this.setting.bValue ? 1.0f : 0.0f;
        float f = this.anim2.to = this.setting.bValue ? 1.0f : 0.0f;
        if (this.blya.getAnim() > 1.0f) {
            this.blya.to = 0.0f;
            this.blya.setAnim(1.0f);
        }
        float xPos = x + 6.0f;
        float yPos = y + 1.5f;
        float h = this.getHeight() - 3.0f;
        float w = 18.0f;
        float extX = 5.0f;
        float extY = 2.0f;
        float pX1 = xPos + 5.0f + 8.0f * this.anim.getAnim();
        float progX1 = pX1 < (pX2 = xPos + 5.0f + 8.0f * this.anim2.getAnim()) ? pX1 : pX2;
        float progX2 = pX1 > pX2 ? pX1 : pX2;
        int color = ClickGuiScreen.getColor((int)(y / 3.6f), this.setting.module.category);
        int offC = ColorUtils.getOverallColorFrom(ColorUtils.getColor(0, 0, 0, 255), color, this.anim2.getAnim() / 3.0f + 0.33333334f);
        int onC = ColorUtils.swapAlpha(color, 255.0f);
        int colBG = ColorUtils.getOverallColorFrom(offC, onC, this.anim.getAnim());
        int colBGShadow = ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(offC, ColorUtils.getColor(0, 0, 0, 255), 0.5f), onC, 1.0f - this.anim2.getAnim());
        colBG = ColorUtils.swapAlpha(colBG, (float)ColorUtils.getAlphaFromColor(colBG) * scaledAlphaPercent);
        colBGShadow = ColorUtils.swapAlpha(colBGShadow, (float)ColorUtils.getAlphaFromColor(colBGShadow) * scaledAlphaPercent);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(xPos, yPos, xPos + 18.0f, yPos + h, 4.0f, 0.5f, colBG, colBG, colBG, colBG, false, true, true);
        float r = (h - 4.0f) / 2.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(progX1 - r, yPos + 2.0f, progX2 + r, yPos + h - 2.0f, r, 0.5f + this.anim2.getAnim(), colBGShadow, colBGShadow, colBGShadow, colBGShadow, false, true, true);
        CFontRenderer font = Fonts.comfortaaBold_14;
        if (255.0f * scaledAlphaPercent >= 33.0f) {
            font.drawString(this.setting.getName(), xPos + 18.0f + 3.0f + this.blya.getAnim() * 2.0f, yPos + 3.5f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * scaledAlphaPercent));
        }
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(x, y, mouseX, mouseY, mouseButton);
        if (this.ishover((float)x + 3.5f, (float)y + this.getHeight() / 2.0f - 6.0f, (float)x + 24.5f, (float)y + this.getHeight() / 2.0f + 6.0f, mouseX, mouseY) && mouseButton == 0) {
            ClientTune.get.playGuiScreenCheckBox(this.setting.bValue);
            this.setting.bValue = !this.setting.bValue;
            this.blya.to = 1.1f;
        }
    }

    @Override
    public float getWidth() {
        return 118.0f;
    }

    @Override
    public float getHeight() {
        return 13.0f;
    }
}

