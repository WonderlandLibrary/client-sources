/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
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
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.Render.Vec2fColored;
import ru.govno.client.utils.TimerHelper;

public class Slider
extends Set {
    boolean dragging = false;
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils animL = new AnimationUtils(1.0f, 1.0f, 0.1f);
    AnimationUtils animR = new AnimationUtils(1.0f, 1.0f, 0.1f);
    AnimationUtils notify = new AnimationUtils(0.0f, 0.0f, 0.2f);
    TimerHelper soundTicker = new TimerHelper();

    @Override
    public void onGuiClosed() {
        this.dragging = false;
    }

    private double getMouseSpeed() {
        float dX = Mouse.getDX();
        float dY = Mouse.getDX();
        return Math.sqrt(dX * dX + dY * dY);
    }

    private long getTimerateSoundMove(double mouseSpeed) {
        if (mouseSpeed == 0.0) {
            return Long.MAX_VALUE;
        }
        long time = 1000L;
        time = (long)((double)time / MathUtils.clamp(mouseSpeed * 15.0, 1.0, 200.0));
        return time;
    }

    private void updateSliderSounds() {
        if (this.soundTicker.hasReached(this.getTimerateSoundMove(this.getMouseSpeed()))) {
            ClientTune.get.playGuiSliderMoveSong();
            this.soundTicker.reset();
        }
    }

    public Slider(Settings setting) {
        super(setting);
    }

    void drawNotify(String val, float x, float y, float alphaPC) {
        CFontRenderer font = Fonts.mntsb_10;
        float w = font.getStringWidth(val);
        float texX = x - w / 2.0f;
        float smooth = 3.0f;
        w = w < smooth * 2.0f ? smooth * 2.0f : w;
        int bgCol = ColorUtils.swapAlpha(Integer.MIN_VALUE, 160.0f * alphaPC);
        GL11.glPushMatrix();
        RenderUtils.customScaledObject2D(x, y + 3.0f, 0.0f, 0.0f, alphaPC);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - 3.0f - w / 2.0f, y - 8.0f, x + w / 2.0f + 3.0f, y, smooth, 0.5f, bgCol, bgCol, bgCol, bgCol, false, true, true);
        if ((double)(255.0f * alphaPC) >= 33.0) {
            font.drawString(val, texX, y - 4.0f, ColorUtils.swapAlpha(-1, 255.0f * alphaPC));
        }
        RenderUtils.drawVec2Colored(Arrays.asList(new Vec2fColored(x - 3.0f, y, bgCol), new Vec2fColored(x + 3.0f, y, bgCol), new Vec2fColored(x, y + 3.0f, bgCol)));
        GL11.glPopMatrix();
    }

    @Override
    public void drawScreen(float x, float y, int step, int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(x, y, step, mouseX, mouseY, partialTicks);
        float scaledAlphaPercent = ClickGuiScreen.globalAlpha.getAnim() / 255.0f;
        scaledAlphaPercent *= scaledAlphaPercent;
        if (ClickGuiScreen.colose) {
            scaledAlphaPercent *= scaledAlphaPercent;
        }
        float delRound = 20.0f;
        if (this.setting.fMin == 0.0f && this.setting.fMax == 1.0f) {
            delRound = 100.0f;
        }
        if (this.setting.fMax - this.setting.fMin > 5.0f) {
            delRound = 10.0f;
        }
        if (this.setting.fMax >= 100.0f || (this.setting.fMax == 255.0f || this.setting.fMax % 50.0f == 0.0f) && (this.setting.fMin == 0.0f || this.setting.fMin == 50.0f || this.setting.fMin == 26.0f)) {
            delRound = 1.0f;
        }
        double finVal = MathUtils.roundPROBLYA(delRound == 1.0f ? (float)((int)this.setting.fValue) : this.setting.fValue, 1.0f / delRound);
        Object val = "" + finVal;
        if (finVal == (double)((int)finVal)) {
            val = ((String)val).replace(".0", "");
        }
        if (this.dragging) {
            if (Keyboard.isKeyDown(42)) {
                float dx = Mouse.getDX();
                if (dx != 0.0f && this.setting.fValue != (dx > 0.0f ? this.setting.fMax : this.setting.fMin)) {
                    this.setting.fValue = dx > 0.0f ? this.setting.fMax : this.setting.fMin;
                    ClientTune.get.playGuiSliderMoveSong();
                }
            } else {
                this.setting.fValue = (float)MathUtils.roundPROBLYA((float)((double)((float)mouseX - (x + 6.0f)) * (double)(this.setting.fMax - this.setting.fMin) / (double)(this.getWidth() - 12.0f) + (double)this.setting.fMin), 1.0f / delRound);
                this.setting.fValue = MathUtils.clamp(this.setting.fValue, this.setting.fMin, this.setting.fMax);
                this.updateSliderSounds();
            }
        }
        this.notify.to = this.dragging && this.setting.fValue != this.setting.fMax && this.setting.fValue != this.setting.fMin ? 1.0f : 0.0f;
        this.notify.speed = 0.1f;
        float xExtSlider = 6.0f;
        float settingPC = this.setting.fValue / (this.setting.fMax - this.setting.fMin);
        double optionValue = MathUtils.roundPROBLYA(MathUtils.clamp(this.setting.fValue, this.setting.fMin, this.setting.fMax), 0.01);
        double renderPerc = (double)(this.getWidth() - xExtSlider * 2.0f) / (double)(this.setting.fMax - this.setting.fMin);
        this.anim.to = (float)(renderPerc * optionValue - renderPerc * (double)this.setting.fMin);
        float x1 = x + xExtSlider;
        float x2 = x + this.getWidth() - xExtSlider;
        float xSlider = x + xExtSlider + this.anim.getAnim();
        float y1 = y + 11.5f;
        float y2 = y + 14.0f;
        int texCol = ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent);
        int colSlider1 = ClickGuiScreen.getColor(step + (int)y1, this.setting.module.category);
        int colSlider2 = ClickGuiScreen.getColor(step + (int)y1 + 180, this.setting.module.category);
        colSlider1 = ColorUtils.swapAlpha(colSlider1, (float)ColorUtils.getAlphaFromColor(colSlider1) * scaledAlphaPercent);
        colSlider2 = ColorUtils.swapAlpha(colSlider2, (float)ColorUtils.getAlphaFromColor(colSlider2) * scaledAlphaPercent);
        colSlider2 = ColorUtils.getOverallColorFrom(colSlider1, colSlider2, (xSlider - x1) / (x2 - x1));
        int bgCol = ColorUtils.getColor(0, 0, 0, 180.0f * scaledAlphaPercent);
        int bgCol2 = ColorUtils.getColor(0, 0, 0, 80.0f * scaledAlphaPercent);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x1 - 0.5f, y1 - 0.5f, x2 + 0.5f, y2 + 0.5f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawGradientSideways(x1, y1, xSlider, y2, colSlider1, colSlider2);
        RenderUtils.drawAlphedRect(xSlider, y1, x2, y2, bgCol2);
        RenderUtils.drawLightContureRectSmooth(x1, y1, x2, y2, bgCol);
        StencilUtil.readStencilBuffer(0);
        if ((double)this.notify.getAnim() > 0.08) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(xSlider, y1 + (y2 - y1) / 2.0f, 0.0f, 0.0f, this.notify.getAnim());
            GL11.glScaled(0.5, 0.5, 1.0);
            GL11.glTranslated(xSlider * 2.0f + 4.0f, (y1 - 9.0f) * 2.0f, 0.0);
            int texCol2 = ColorUtils.swapAlpha(texCol, (float)ColorUtils.getAlphaFromColor(texCol) * (float)(1.0 - MathUtils.getDifferenceOf(xSlider, x1 + (x2 - x1) / 2.0f) / (double)((x2 - x1) / 2.0f)));
            if (ColorUtils.getAlphaFromColor(texCol2) >= 33) {
                Fonts.minecraftia_14.drawString("+", 16.0, 0.0, texCol2);
                Fonts.minecraftia_14.drawString("-", -28.0, 0.0, texCol2);
            }
            GL11.glPopMatrix();
        }
        StencilUtil.uninitStencilBuffer();
        if (xSlider > x1 && xSlider < x2) {
            boolean e1 = xSlider > x1 + (x2 - x1) / 2.0f;
            double pointSize = MathUtils.clamp(MathUtils.getDifferenceOf(e1 ? x2 + 2.0f : x1 - 2.0f, xSlider) / 2.0 - 1.0, 0.0, 3.0);
            if (pointSize > 0.0) {
                RenderUtils.drawSmoothCircle(xSlider, y1 + (y2 - y1) / 2.0f, (float)pointSize, bgCol2);
            }
            if (pointSize > 0.5) {
                RenderUtils.drawSmoothCircle(xSlider, y1 + (y2 - y1) / 2.0f, (float)pointSize - 0.5f, colSlider2);
            }
            if (pointSize > 1.5) {
                RenderUtils.drawSmoothCircle(xSlider, y1 + (y2 - y1) / 2.0f, (float)pointSize - 1.5f, bgCol2);
            }
        }
        if (255.0f * scaledAlphaPercent >= 33.0f) {
            float scale = 1.0f - this.notify.getAnim();
            if (scale > 0.98f) {
                scale = 1.0f;
            }
            if (scale != 1.0f) {
                GL11.glPushMatrix();
                RenderUtils.customScaledObject2D(x + (this.getWidth() - this.anim.getAnim() / ((this.getWidth() - xExtSlider) / this.getWidth())), y1, 0.0f, 0.0f, scale);
            }
            if (255.0f * scaledAlphaPercent * scale >= 33.0f) {
                Fonts.comfortaaBold_15.drawString(this.setting.getName(), x + this.getWidth() / 2.0f - (float)Fonts.comfortaaBold_15.getStringWidth(this.setting.getName()) / 2.0f, y + 3.0f, ColorUtils.swapAlpha(texCol, (float)ColorUtils.getAlphaFromColor(texCol) * scale));
            }
            if (scale != 1.0f) {
                GL11.glPopMatrix();
            }
        }
        double diffL = MathUtils.getDifferenceOf(x1, xSlider);
        this.animL.to = !ClickGuiScreen.colose && this.dragging && diffL < 15.0 ? 1.0f - this.notify.getAnim() : 1.0f;
        double diffR = MathUtils.getDifferenceOf(x2, xSlider);
        float f = this.animR.to = !ClickGuiScreen.colose && this.dragging && diffR < 15.0 ? 1.0f - this.notify.getAnim() : 1.0f;
        if ((double)this.animL.getAnim() < 0.98) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2DCoords(x1 + 0.5f, y + 0.5f, x1 + (float)Fonts.comfortaaBold_12.getStringWidth("" + this.setting.fMin) + 5.5f, y1 - 2.0f, this.animL.getAnim());
        }
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x1 + 0.5f, y + 0.5f, x1 + (float)Fonts.comfortaaBold_12.getStringWidth("" + this.setting.fMin) + 5.5f, y1 - 2.0f, 2.0f, 0.5f, bgCol2, bgCol2, bgCol2, bgCol2, false, true, true);
        if (255.0f * scaledAlphaPercent * this.animL.getAnim() >= 33.0f) {
            Fonts.comfortaaBold_12.drawString("" + this.setting.fMin, x1 + 3.0f, y1 - 7.5f, ColorUtils.swapAlpha(texCol, (float)ColorUtils.getAlphaFromColor(texCol) * this.animL.getAnim()));
        }
        if ((double)this.animL.getAnim() < 0.98) {
            GL11.glPopMatrix();
        }
        if ((double)this.animR.getAnim() < 0.98) {
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2DCoords(x2 - (float)Fonts.comfortaaBold_12.getStringWidth("" + this.setting.fMax) - 5.5f, y + 0.5f, x2 - 0.5f, y1 - 2.0f, this.animR.getAnim());
        }
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x2 - (float)Fonts.comfortaaBold_12.getStringWidth("" + this.setting.fMax) - 5.5f, y + 0.5f, x2 - 0.5f, y1 - 2.0f, 2.0f, 0.5f, bgCol2, bgCol2, bgCol2, bgCol2, false, true, true);
        if (255.0f * scaledAlphaPercent * this.animR.getAnim() >= 33.0f) {
            Fonts.comfortaaBold_12.drawString("" + this.setting.fMax, x2 - (float)Fonts.comfortaaBold_12.getStringWidth("" + this.setting.fMax) - 3.0f, y1 - 7.5f, texCol);
        }
        if ((double)this.animR.getAnim() < 0.98) {
            GL11.glPopMatrix();
        }
        if ((double)this.notify.getAnim() > 0.08) {
            this.drawNotify((String)val, xSlider, y1 - 5.0f, this.notify.getAnim() * scaledAlphaPercent);
        }
        if (ClickGuiScreen.colose) {
            this.dragging = false;
        }
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(x, y, mouseX, mouseY, mouseButton);
        if (this.ishover(x, y + 10, (float)x + this.getWidth(), y + 16, mouseX, mouseY) && mouseButton == 0) {
            this.dragging = true;
            ClickGuiScreen.resetHolds();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            this.dragging = false;
        }
    }

    @Override
    public float getWidth() {
        return 118.0f;
    }

    @Override
    public float getHeight() {
        return 17.0f;
    }
}

