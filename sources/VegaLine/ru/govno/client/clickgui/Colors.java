/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec2f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.clickgui.Set;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.TimerHelper;

public class Colors
extends Set {
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.15f);
    float offsetX = -1.2312312E8f;
    float offsetY = -1.2312312E8f;
    float offsetX2 = -1.2312312E8f;
    float offsetX3 = -1.2312312E8f;
    AnimationUtils arrow = new AnimationUtils(0.0f, 0.0f, 0.15f);
    boolean dragginggr = false;
    boolean dragginghsb = false;
    boolean draggingalpha = false;
    boolean open = false;
    TimerHelper soundTicker = new TimerHelper();

    @Override
    public void onGuiClosed() {
        this.dragginggr = false;
        this.dragginghsb = false;
        this.draggingalpha = false;
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
        time = (long)((double)time / MathUtils.clamp(mouseSpeed * 10.0, 1.0, 100.0));
        return time;
    }

    private void updateSliderSounds() {
        if (this.soundTicker.hasReached(this.getTimerateSoundMove(this.getMouseSpeed()))) {
            ClientTune.get.playGuiSliderMoveSong();
            this.soundTicker.reset();
        }
    }

    public Colors(Settings setting) {
        super(setting);
    }

    @Override
    public void drawScreen(float x, float y, int step, int mouseX, int mouseY, float partialTicks) {
        float toRot;
        super.drawScreen(x, y, step, mouseX, mouseY, partialTicks);
        float scaledAlphaPercent = ClickGuiScreen.globalAlpha.getAnim() / 255.0f;
        scaledAlphaPercent *= scaledAlphaPercent;
        if (ClickGuiScreen.colose) {
            scaledAlphaPercent *= scaledAlphaPercent;
        }
        GlStateManager.enableAlpha();
        this.anim.to = this.open ? 1.0f : 0.0f;
        this.arrow.to = toRot = this.open ? -90.0f : 0.0f;
        this.arrow.speed = MathUtils.getDifferenceOf(this.arrow.getAnim(), toRot) > 1.0 ? 0.1f : 0.2f;
        float posX = x + 4.0f;
        float posY = y + 1.5f;
        float w = this.getWidth() - 8.0f;
        float h = this.getHeight() - 1.0f;
        int cc = ColorUtils.getColor(0, 0, 0, 110.0f * scaledAlphaPercent);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(posX, posY + 0.5f, posX + w, posY + this.getHeight() - 2.0f, 2.0f, 0.5f, cc, cc, cc, cc, false, true, true);
        RenderUtils.drawAlphedSideways(posX, posY, posX + w, posY + 1.0f, ColorUtils.swapAlpha(ClickGuiScreen.getColor((int)((float)(step + 120) + y / this.getHeight()), this.setting.module.category), 255.0f * scaledAlphaPercent), ColorUtils.swapAlpha(ClickGuiScreen.getColor((int)((float)step + y / this.getHeight()), this.setting.module.category), 255.0f * scaledAlphaPercent));
        if (255.0f * scaledAlphaPercent > 26.0f) {
            Fonts.comfortaaBold_12.drawStringWithShadow(this.setting.getName(), posX + 22.0f, posY + 7.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * scaledAlphaPercent));
        }
        this.drawArrow(posX + w - 8.0f, posY + 5.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(26.0f * scaledAlphaPercent + 175.0f * this.anim.getAnim() * scaledAlphaPercent, 0.0f, 255.0f)));
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(posX + 5.0f, posY + 6.0f, posX + 16.0f, posY + 11.0f, 2.0f, 0.5f, -1, -1, -1, -1, false, true, false);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawAlphedRect(x + 8.5f, y + 6.0f, x + 20.0f, y + 14.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 60.0f * scaledAlphaPercent));
        for (int i2 = 0; i2 < 12; i2 += 2) {
            for (int i = 0; i < 8; i += 2) {
                GL11.glTranslated(x + 8.5f, y + 6.0f, 0.0);
                RenderUtils.drawAlphedRect(i2, i, i2 + 1, i + 1, ColorUtils.getColor(0, 0, 0, (int)(90.0f * scaledAlphaPercent)));
                GL11.glTranslated(-(x + 8.5f), -(y + 6.0f), 0.0);
            }
        }
        StencilUtil.uninitStencilBuffer();
        int finalColor = ColorUtils.swapAlpha(this.setting.color, (float)ColorUtils.getAlphaFromColor(this.setting.color) * scaledAlphaPercent);
        int finalColor2 = ColorUtils.swapAlpha(this.setting.color, (float)ColorUtils.getAlphaFromColor(this.setting.color) * scaledAlphaPercent / 2.5f);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(posX + 5.0f, posY + 6.0f, posX + 16.0f, posY + 11.0f, 2.0f, 0.5f, finalColor, finalColor, finalColor, finalColor, false, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(posX + 5.0f, posY + 6.0f, posX + 16.0f, posY + 11.0f, 2.0f, 6.0f, finalColor2, finalColor2, finalColor2, finalColor2, true, false, true);
        this.anim.speed = MathUtils.getDifferenceOf(this.anim.getAnim(), h) < 1.0 ? 0.5f : 0.1f;
        scaledAlphaPercent *= this.anim.getAnim();
        if (this.anim.getAnim() > 0.1f) {
            float grX = posX + 3.0f;
            float grY = posY + 19.0f;
            float grH = 34.0f;
            float grW = 34.0f;
            if (this.dragginggr) {
                this.dragginghsb = false;
                this.draggingalpha = false;
                this.offsetX = MathUtils.clamp((float)mouseX - grX, 0.0f, grW);
                this.offsetY = MathUtils.clamp((float)mouseY - grY, 0.0f, grH);
            } else if (ColorUtils.getSaturateFromColor(this.setting.color) != 0.0f) {
                this.offsetX = ColorUtils.getSaturateFromColor(this.setting.color) * grW;
                this.offsetY = grH - ColorUtils.getBrightnessFromColor(this.setting.color) * grH;
            }
            float draggXgr = grX + this.offsetX;
            float draggYgr = grY + this.offsetY;
            float percXgr = this.offsetX / grW;
            float percYgr = this.offsetY / grH;
            StencilUtil.initStencilToWrite();
            RenderUtils.drawAlphedRect(posX, posY + 16.0f, posX + w, posY + h, -1);
            StencilUtil.readStencilBuffer(1);
            int colHSB = -1;
            float hsbX = posX + 42.0f;
            float hsbY = posY + 45.0f;
            float hsbW = 64.0f;
            float hsbH = 6.0f;
            float alphaX = posX + 42.0f;
            float alphaY = posY + 42.0f - 14.0f;
            float alphaW = 64.0f;
            float alphaH = 6.0f;
            RenderUtils.drawLightContureRect(hsbX, hsbY, hsbX + hsbW + 0.25f, hsbY + hsbH, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            float draggXhsb = hsbX + this.offsetX2;
            float percXhsb = this.offsetX2 / hsbW;
            float percXalpha = this.offsetX3 / alphaW;
            if (255.0f * scaledAlphaPercent > 26.0f) {
                Fonts.comfortaaBold_12.drawStringWithShadow("Hsb - " + String.format("%.2f", Float.valueOf(this.offsetX2 / hsbW * 360.0f)), hsbX + 1.0f, hsbY - 7.0f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            }
            for (int i = 0; i < 359; ++i) {
                float hsb = (float)i / 360.0f;
                colHSB = Color.getHSBColor(hsb, 1.0f, 1.0f).getRGB();
                float pc = (float)i / 360.0f * hsbW;
                RenderUtils.drawAlphedRect(hsbX + pc, hsbY, hsbX + pc + 0.5f, hsbY + hsbH, ColorUtils.swapAlpha(colHSB, 255.0f * scaledAlphaPercent));
            }
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(grX, grY, grX + grW, grY + grH, 1.5f, 0.5f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent), ColorUtils.swapAlpha(Color.getHSBColor(this.offsetX2 / hsbW, 1.0f, 1.0f).getRGB(), 255.0f * scaledAlphaPercent), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * scaledAlphaPercent), ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * scaledAlphaPercent), false, true, true);
            RenderUtils.resetBlender();
            RenderUtils.drawAlphedRect(draggXgr - 1.5f, draggYgr - 1.5f, draggXgr + 1.5f, draggYgr + 1.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * scaledAlphaPercent));
            RenderUtils.drawAlphedRect(draggXgr - 1.0f, draggYgr - 1.0f, draggXgr + 1.0f, draggYgr + 1.0f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            float hsbXCursor = hsbX + this.offsetX2;
            float hsbYCursor = hsbY + hsbH / 2.0f;
            RenderUtils.drawAlphedRect(hsbXCursor - 1.5f, hsbYCursor - 2.5f, hsbXCursor + 1.5f, hsbYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * scaledAlphaPercent));
            RenderUtils.drawAlphedRect(hsbXCursor - 1.0f, hsbYCursor - 2.0f, hsbXCursor + 1.0f, hsbYCursor + 2.0f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            RenderUtils.drawLightContureRect(alphaX, alphaY, alphaX + alphaW, alphaY + alphaH, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            if (255.0f * scaledAlphaPercent > 26.0f) {
                Fonts.comfortaaBold_12.drawStringWithShadow("Alpha - " + (int)(percXalpha * 255.0f), alphaX + 1.0f, alphaY - 7.0f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            }
            alphaW *= 2.0f;
            alphaH *= 2.0f;
            int i2 = 0;
            while ((float)i2 < alphaH) {
                int i = 0;
                while ((float)i < alphaW) {
                    int colTest = (i2 == 0 || i2 == 8) && (float)i / alphaW * 16.0f != (float)((int)((float)i / alphaW * 16.0f)) || (float)i / alphaW * 16.0f == (float)((int)((float)i / alphaW * 16.0f)) && i2 != 0 && i2 != 8 ? ColorUtils.getColor(125, 125, 125, 125) : ColorUtils.getColor(200, 200, 200, 125);
                    int colAlpha = ColorUtils.getOverallColorFrom(colTest, ColorUtils.swapAlpha(this.setting.color, 255.0f), (float)i / alphaW);
                    RenderUtils.drawAlphedRect(alphaX + (float)(i / 2), alphaY + (float)(i2 / 2), alphaX + (float)(i / 2) + 2.0f, alphaY + (float)(i2 / 2) + 2.0f, ColorUtils.swapAlpha(colAlpha, 255.0f * scaledAlphaPercent * scaledAlphaPercent * scaledAlphaPercent));
                    i += 4;
                }
                i2 += 4;
            }
            alphaW /= 2.0f;
            float alphaXCursor = alphaX + this.offsetX3;
            float alphaYCursor = alphaY + (alphaH /= 2.0f) / 2.0f;
            RenderUtils.drawAlphedRect(alphaXCursor - 1.5f, alphaYCursor - 2.5f, alphaXCursor + 1.5f, alphaYCursor + 2.5f, ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * scaledAlphaPercent));
            RenderUtils.drawAlphedRect(alphaXCursor - 1.0f, alphaYCursor - 2.0f, alphaXCursor + 1.0f, alphaYCursor + 2.0f, ColorUtils.swapAlpha(-1, 255.0f * scaledAlphaPercent));
            StencilUtil.uninitStencilBuffer();
            if (this.dragginghsb) {
                this.dragginggr = false;
                this.draggingalpha = false;
                this.offsetX2 = MathUtils.clamp((float)mouseX - hsbX, 0.0f, hsbW);
            } else if (ColorUtils.getHueFromColor(this.setting.color) != 0 && !this.dragginggr) {
                this.offsetX2 = (float)ColorUtils.getHueFromColor(this.setting.color) / 360.0f * hsbW;
            }
            if (this.draggingalpha) {
                this.dragginggr = false;
                this.dragginghsb = false;
                this.offsetX3 = MathUtils.clamp((float)mouseX - alphaX, 0.0f, alphaW);
            } else {
                this.offsetX3 = (float)ColorUtils.getAlphaFromColor(this.setting.color) / 255.0f * alphaW;
            }
            float hsb = percXhsb;
            int col = Color.getHSBColor(MathUtils.clamp(percXhsb, 0.0f, 0.999f), percXgr, 1.0f - percYgr).getRGB();
            col = ColorUtils.swapAlpha(col, 255.0f * percXalpha);
            if (col != 0 && Mouse.isButtonDown(0)) {
                this.setting.color = col;
            }
        }
        if (this.draggingalpha || this.dragginggr || this.dragginghsb) {
            this.updateSliderSounds();
        }
    }

    public void drawArrow(float xpos, float ypos, int color) {
        GL11.glPushMatrix();
        float ex = 4.5f;
        float xp = xpos - ex / 2.0f * (-this.arrow.getAnim() / 90.0f);
        float yp = ypos + ex / 4.0f * (-this.arrow.getAnim() / 90.0f);
        RenderUtils.customRotatedObject2D(xp, yp, ex / 2.0f, ex, this.arrow.getAnim() - 90.0f);
        ArrayList<Vec2f> vec2fs = new ArrayList<Vec2f>();
        vec2fs.add(new Vec2f(xp, yp));
        vec2fs.add(new Vec2f(xp - ex, yp + ex));
        vec2fs.add(new Vec2f(xp + ex, yp + ex));
        RenderUtils.drawSome(vec2fs, color);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.dragginggr = false;
            this.dragginghsb = false;
            this.draggingalpha = false;
        }
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(x, y, mouseX, mouseY, mouseButton);
        float posX = x + 4;
        float posY = (float)y + 1.5f;
        float w = this.getWidth() - 8.0f;
        float h = this.getHeight() - 1.0f;
        float grX = posX + 3.0f;
        float grY = posY + 19.0f;
        float grH = 34.0f;
        float grW = 34.0f;
        float hsbX = posX + 42.0f;
        float hsbY = posY + 45.0f;
        float hsbW = 64.0f;
        float hsbH = 6.0f;
        float alphaX = posX + 42.0f;
        float alphaY = posY + 42.0f - 14.0f;
        float alphaW = 64.0f;
        float alphaH = 6.0f;
        if (mouseButton == 0 && this.ishover(grX, grY, grX + grW, grY + grH, mouseX, mouseY)) {
            this.dragginggr = true;
        }
        if (mouseButton == 0 && this.ishover(hsbX, hsbY, hsbX + hsbW, hsbY + hsbH, mouseX, mouseY)) {
            this.dragginghsb = true;
        }
        if (mouseButton == 0 && this.ishover(alphaX, alphaY, alphaX + alphaW, alphaY + alphaH, mouseX, mouseY)) {
            this.draggingalpha = true;
        }
        if (mouseButton == 1 && this.ishover(x + 5, y + 5, (float)x + this.getWidth() - 4.0f, y + 20, mouseX, mouseY)) {
            boolean bl = this.open = !this.open;
            if (this.open) {
                Client.clickGuiScreen.panels.stream().filter(panel -> panel.open).filter(panel -> panel.category == this.setting.module.category).forEach(panel -> panel.mods.stream().filter(mod -> mod.open).filter(mod -> this.setting.module == mod.module).forEach(module -> module.sets.stream().map(Set::getHasColors).filter(Objects::nonNull).filter(colors -> colors != this).filter(colors -> colors.open).forEach(set -> {
                    set.open = false;
                    ClientTune.get.playGuiCheckOpenOrCloseSong(false);
                })));
            }
            ClientTune.get.playGuiCheckOpenOrCloseSong(this.open);
        }
    }

    @Override
    public float getWidth() {
        return 118.0f;
    }

    @Override
    public float getHeight() {
        return 17.0f + 40.0f * this.anim.getAnim() + 1.0f;
    }
}

