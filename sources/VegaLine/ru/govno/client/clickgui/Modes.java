/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec2f;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
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

public class Modes
extends Set {
    boolean open = false;
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.15f);
    AnimationUtils changeAnim = new AnimationUtils(0.0f, 0.0f, 0.075f);
    AnimationUtils arrow = new AnimationUtils(0.0f, 0.0f, 0.15f);
    boolean playClose;

    public Modes(Settings setting) {
        super(setting);
    }

    @Override
    public void drawScreen(float x, float y, int step, int mouseX, int mouseY, float partialTicks) {
        float toRot;
        super.drawScreen(x, y, step, mouseX, mouseY, partialTicks);
        float scaledAlphaPercent = ClickGuiScreen.globalAlpha.getAnim() / 255.0f;
        scaledAlphaPercent *= scaledAlphaPercent;
        float f = this.anim.to = this.open ? 1.0f : 0.0f;
        if (MathUtils.getDifferenceOf(this.anim.getAnim(), this.anim.to) < (double)0.03f) {
            this.anim.setAnim(this.anim.to);
        }
        this.anim.speed = 0.15f;
        this.arrow.to = toRot = this.open ? -90.0f : 0.0f;
        float f2 = this.arrow.speed = MathUtils.getDifferenceOf(this.arrow.getAnim(), toRot) > 1.0 ? 0.1f : 0.2f;
        if (this.changeAnim.getAnim() >= 4.0f) {
            this.changeAnim.to = 0.0f;
        }
        if (this.playClose) {
            ClientTune.get.playGuiCheckOpenOrCloseSong(false);
            this.playClose = false;
        }
        int cc = ColorUtils.getColor(0, 0, 0, (int)(110.0f * scaledAlphaPercent));
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + 4.0f, y + 2.0f, x + this.getWidth() - 4.0f, y + this.getHeight() - 1.0f, 2.0f, 0.5f, cc, cc, cc, cc, false, true, true);
        RenderUtils.drawAlphedSideways(x + 4.0f, y + 1.5f, x + this.getWidth() - 4.0f, y + 2.5f, ColorUtils.swapAlpha(ClickGuiScreen.getColor((int)((float)(step + 120) + y / this.getHeight()), this.setting.module.category), 255.0f * scaledAlphaPercent), ColorUtils.swapAlpha(ClickGuiScreen.getColor((int)((float)step + y / this.getHeight()), this.setting.module.category), 255.0f * scaledAlphaPercent));
        RenderUtils.fixShadows();
        GlStateManager.resetColor();
        if (scaledAlphaPercent * 255.0f >= 33.0f) {
            Fonts.comfortaaBold_12.drawString(this.setting.getName(), x + 8.0f, y + 8.5f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * scaledAlphaPercent));
            if ((175.0f - 175.0f * MathUtils.clamp(this.changeAnim.getAnim() / 3.0f, 0.0f, 1.0f)) * scaledAlphaPercent >= 33.0f) {
                Fonts.comfortaaBold_12.drawString(this.setting.currentMode, x + 11.0f + (float)Fonts.comfortaaBold_12.getStringWidth(this.setting.getName()) + this.changeAnim.getAnim(), y + 8.5f, ColorUtils.swapAlpha(-1, MathUtils.clamp((175.0f - 175.0f * MathUtils.clamp(this.changeAnim.getAnim() / 3.0f, 0.0f, 1.0f)) * scaledAlphaPercent, 26.0f, 175.0f)));
            }
        }
        GlStateManager.enableAlpha();
        this.drawArrow(x + this.getWidth() - 12.0f, y + 6.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(26.0f * scaledAlphaPercent + 175.0f * this.anim.getAnim() * scaledAlphaPercent, 0.0f, 255.0f)));
        float height = 8.0f;
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x + 5.0f, y + 17.0f, x + this.getWidth() - 5.0f, y + this.getHeight() - 3.0f, ColorUtils.getColor(255, 255, 255, 60));
        StencilUtil.readStencilBuffer(1);
        if (this.getHeight() > 18.5f) {
            for (String mode : this.setting.modes) {
                CFontRenderer modedFont = mode.equalsIgnoreCase(this.setting.currentMode) ? Fonts.comfortaaBold_17 : Fonts.comfortaaBold_13;
                float width = modedFont.getStringWidth(mode) / 2;
                if (!((height += 13.0f) < this.getHeight())) continue;
                int rectCol = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 20.0f * scaledAlphaPercent * scaledAlphaPercent);
                RenderUtils.drawAlphedRect(x + 6.0f, y + height - 4.5f, x + this.getWidth() - 6.0f, y + height + 6.5f, rectCol);
                RenderUtils.drawAlphedRect(x + 6.5f, y + height + 6.5f, x + this.getWidth() - 6.5f, y + height + 7.0f, rectCol);
                RenderUtils.drawAlphedRect(x + 6.0f, y + height - 5.0f, x + 6.5f, y + height - 4.5f, rectCol);
                RenderUtils.drawAlphedRect(x + this.getWidth() - 6.5f, y + height - 5.0f, x + this.getWidth() - 6.0f, y + height - 4.5f, rectCol);
                if (!(scaledAlphaPercent * 255.0f >= 33.0f)) continue;
                if (mode.equalsIgnoreCase(this.setting.currentMode)) {
                    float xn = x + this.getWidth() / 2.0f - width + width * 2.0f / (float)mode.length() / 4.0f;
                    float yn = y + height - (float)(mode.equalsIgnoreCase(this.setting.currentMode) ? 1 : 0);
                    xn -= (float)modedFont.getStringWidth(mode);
                    float index = 0.0f;
                    for (char c : mode.toCharArray()) {
                        int col1 = ColorUtils.getOverallColorFrom(ClickGuiScreen.getColor((int)index * 5, this.setting.module.category), ClickGuiScreen.getColor((int)index * 5 + 180, this.setting.module.category), index / (float)modedFont.getStringWidth(mode));
                        if (ColorUtils.getAlphaFromColor(col1 = ColorUtils.swapAlpha(col1, (float)ColorUtils.getAlphaFromColor(col1) * (scaledAlphaPercent * this.anim.getAnim()))) >= 32) {
                            modedFont.drawString(String.valueOf(c), (double)((float)modedFont.getStringWidth(mode) + index + xn) + 0.5, (double)yn + 0.5, ColorUtils.swapDark(col1, ColorUtils.getFullyBrightnessFromColor(col1) / 3.0f));
                            modedFont.drawString(String.valueOf(c), (float)modedFont.getStringWidth(mode) + index + xn, yn, col1);
                        }
                        index += (float)modedFont.getStringWidth(String.valueOf(c));
                    }
                    continue;
                }
                modedFont.drawStringWithShadow(mode, x + this.getWidth() / 2.0f - width, y + height - (float)(mode.equalsIgnoreCase(this.setting.currentMode) ? 1 : 0), ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(175.0f * (this.anim.getAnim() / 2.0f + 0.5f) * scaledAlphaPercent, 26.0f, 255.0f)));
            }
        }
        StencilUtil.uninitStencilBuffer();
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(x, y, mouseX, mouseY, mouseButton);
        if (this.ishover(x + 4, y + 2, (float)x + this.getWidth() - 4.0f, (float)y + this.getHeight() + 2.0f, mouseX, mouseY)) {
            if (mouseButton == 0) {
                int curMode = -1;
                float height = 8.0f;
                if (this.setting.category == Settings.Category.String_Massive && this.open) {
                    for (String mode : this.setting.modes) {
                        if (!this.ishover(x + 6, (float)y + (height += 13.0f) - 2.0f, (float)x + this.getWidth() - 6.0f, (float)y + height + 10.0f, mouseX, mouseY)) continue;
                        curMode = (int)(height / 13.0f - 1.0f);
                    }
                    try {
                        if (!this.setting.currentMode.equalsIgnoreCase(this.setting.modes[curMode]) && curMode != -1) {
                            this.setting.currentMode = this.setting.modes[curMode];
                            ClientTune.get.playGuiScreenChangeModeSong();
                            this.changeAnim.to = 4.5f;
                            if (this.setting.modes.length < 6) {
                                this.playClose = true;
                                this.open = false;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mouseButton == 1 && this.ishover(x + 4, y + 4, (float)x + this.getWidth() - 5.0f, y + (this.open ? 18 : 20), mouseX, mouseY)) {
                boolean bl = this.open = !this.open;
                if (this.open) {
                    Client.clickGuiScreen.panels.stream().filter(panel -> panel.open).filter(panel -> panel.category == this.setting.module.category).forEach(panel -> panel.mods.stream().filter(mod -> mod.open).filter(mod -> this.setting.module == mod.module).forEach(module -> module.sets.stream().map(Set::getHasModes).filter(Objects::nonNull).filter(modes -> modes != this).filter(modes -> modes.open).forEach(set -> {
                        set.open = false;
                        ClientTune.get.playGuiCheckOpenOrCloseSong(false);
                    })));
                }
                ClientTune.get.playGuiCheckOpenOrCloseSong(this.open);
            }
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
    public float getWidth() {
        return 118.0f;
    }

    @Override
    public float getHeight() {
        int count = 0;
        for (String mode : this.setting.modes) {
            ++count;
        }
        return 18.0f + 13.0f * this.anim.getAnim() * (float)count;
    }
}

