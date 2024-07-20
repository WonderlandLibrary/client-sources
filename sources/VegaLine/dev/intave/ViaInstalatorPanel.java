/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.intave.vialoadingbase.ViaLoadingBase;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.Vec2f;
import org.lwjgl.opengl.GL11;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.MusicHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class ViaInstalatorPanel {
    public ViaInstalatorPanel INSTANCE;
    private final String title = "Selected version";
    private final float x;
    private final float y;
    private final AnimationUtils openFactor = new AnimationUtils(0.0f, 0.0f, 0.08f);
    private final AnimationUtils changeProtocolAnim = new AnimationUtils(0.0f, 0.0f, 0.03f);
    private boolean wantOpen;
    private boolean wantClose;
    private final TimerHelper waitToOpen = new TimerHelper();
    private final TimerHelper waitToClose = new TimerHelper();
    private boolean playSelectDo;
    private boolean playSelectMiss;
    private final List<RandTimedColoredVec> PARTICLES = new ArrayList<RandTimedColoredVec>();
    private final TimerHelper particlesAddingDelay = new TimerHelper();

    public ProtocolVersion getCurrentProtocol() {
        return ViaLoadingBase.getInstance().getTargetVersion();
    }

    private boolean isSelectedProtocol(ProtocolVersion protocol) {
        return protocol.getVersion() == this.getCurrentProtocol().getVersion();
    }

    private List<ProtocolVersion> getProtocols() {
        return Arrays.asList(ProtocolVersion.v1_8, ProtocolVersion.v1_11_1, ProtocolVersion.v1_12_2, ProtocolVersion.v1_13_2, ProtocolVersion.v1_14_4, ProtocolVersion.v1_15_2, ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_4, ProtocolVersion.v1_17_1, ProtocolVersion.v1_18_2, ProtocolVersion.v1_19_4, ProtocolVersion.v1_20, ProtocolVersion.v1_20_2);
    }

    private float getWidth() {
        return 77.0f;
    }

    private float getCupHeight() {
        return 34.0f;
    }

    private float getYIndent() {
        return 3.0f;
    }

    private float getStringsStep() {
        return 7.5f;
    }

    private float getHeight(float openPC) {
        return this.getCupHeight() + (float)this.getProtocols().size() * this.getStringsStep() * openPC;
    }

    public ViaInstalatorPanel(float x, float y) {
        this.x = x;
        this.y = y;
        this.INSTANCE = this;
    }

    private void drawBackground(float width, float height, float openFactor, float alphaPC) {
        if (alphaPC == 0.0f) {
            return;
        }
        int bgColor = ColorUtils.getColor(14, (int)(200.0f * alphaPC));
        int lineColor = ColorUtils.getColor(255, (int)(115.0f * alphaPC * openFactor));
        float round = 5.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.x, this.y, this.x + width, this.y + (height += this.getYIndent() * 2.0f * openFactor), round, 0.5f, bgColor, bgColor, bgColor, bgColor, false, true, true);
        this.pointsAddAuto(this.x, this.y, width, height, (long)(120.0f - 75.0f * openFactor));
        this.pointsRemoveAuto(this.x, this.y, width, height);
        float finalHeight = height;
        float patriclesBright = 0.275f;
        float stencilOffset = 1.0f;
        this.drawAllPoints(() -> RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.x + stencilOffset, this.y + stencilOffset, this.x + width - stencilOffset, this.y + finalHeight - stencilOffset, round - stencilOffset / 2.0f, 0.0f, -1, -1, -1, -1, false, true, false), alphaPC * (0.5f + 0.5f * openFactor) * patriclesBright);
        float lineExt = 1.0f + round * (1.0f - openFactor);
        RenderUtils.drawAlphedRect(this.x + lineExt, this.y + this.getCupHeight() - 1.0f - openFactor, this.x + this.getWidth() - lineExt, this.y + this.getCupHeight() - 1.0f + openFactor, lineColor);
    }

    private void drawTitle(float openFactor, float width, float alphaPC, float changeProtocolAnim) {
        int textColor = ColorUtils.swapAlpha(ColorUtils.getColor((int)(185.0f + openFactor * 70.0f)), 255.0f * alphaPC);
        if ((float)ColorUtils.getAlphaFromColor(textColor) >= 26.0f) {
            float textX = this.x + width / 2.0f - (float)Fonts.neverlose500_16.getStringWidth("Selected version") / 2.0f;
            float textY = this.y + 5.0f;
            int textShadowColor = ColorUtils.swapAlpha(textColor, (float)ColorUtils.getAlphaFromColor(textColor) / 7.0f);
            if ((float)ColorUtils.getAlphaFromColor(textShadowColor) >= 26.0f) {
                GL11.glBlendFunc(770, 32772);
                Fonts.neverlose500_16.drawString("Selected version", textX - 1.0f, textY, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX + 1.0f, textY, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX, textY - 1.0f, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX, textY + 1.0f, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX + 0.5f, textY + 0.5f, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX - 0.5f, textY - 0.5f, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX - 0.5f, textY + 0.5f, textShadowColor);
                Fonts.neverlose500_16.drawString("Selected version", textX + 0.5f, textY - 0.5f, textShadowColor);
                GL11.glBlendFunc(770, 771);
            }
            Fonts.neverlose500_16.drawStringWithShadow("Selected version", textX, textY, textColor);
        }
        float triangleExtX = 8.0f + 6.0f * openFactor;
        float triangleExtY = 14.0f;
        float triangleHeight = 4.5f;
        int triangleColorFill = ColorUtils.swapAlpha(-1, 60.0f * alphaPC);
        int triangleColorOutline = ColorUtils.swapAlpha(-1, 225.0f * alphaPC);
        List<Vec2f> triangle = Arrays.asList(new Vec2f(this.x + triangleExtX, this.y + triangleExtY), new Vec2f(this.x + width - triangleExtX, this.y + triangleExtY), new Vec2f(this.x + width / 2.0f, this.y + triangleExtY + triangleHeight));
        RenderUtils.drawSome(triangle, triangleColorFill, 6);
        GL11.glLineWidth(0.1f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderUtils.drawSome(triangle, triangleColorOutline, 2);
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        GL11.glLineWidth(1.0f);
        if ((float)ColorUtils.getAlphaFromColor(textColor) >= 33.0f) {
            String versionName = this.getCurrentProtocol().getName();
            float textX = this.x + width / 2.0f - (float)Fonts.neverlose500_18.getStringWidth(versionName) / 2.0f;
            float textY = this.y + triangleExtY + triangleHeight + 6.0f - 2.0f * openFactor;
            int textShadowColor = ColorUtils.swapAlpha(textColor, (float)ColorUtils.getAlphaFromColor(textColor) / 7.0f);
            Fonts.neverlose500_18.drawStringWithShadow(versionName, textX, textY, textColor);
            if ((float)ColorUtils.getAlphaFromColor(textShadowColor) >= 26.0f) {
                GL11.glBlendFunc(770, 32772);
                Fonts.neverlose500_18.drawString(versionName, textX - 1.0f, textY, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX + 1.0f, textY, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX, textY - 1.0f, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX, textY + 1.0f, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX + 0.5f, textY + 0.5f, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX - 0.5f, textY - 0.5f, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX - 0.5f, textY + 0.5f, textShadowColor);
                Fonts.neverlose500_18.drawString(versionName, textX + 0.5f, textY - 0.5f, textShadowColor);
                GL11.glBlendFunc(770, 771);
            }
            if (changeProtocolAnim != 0.0f) {
                GL11.glPushMatrix();
                GL11.glTranslated(this.x + width / 2.0f, textY + 2.0f, 0.0);
                GL11.glScaled(1.0 + (double)(changeProtocolAnim * 3.0f), 1.0 + (double)(changeProtocolAnim * 3.0f), 1.0);
                GL11.glTranslated(-(this.x + width / 2.0f), -(textY + 2.0f), 0.0);
                int textColorAlphed = ColorUtils.swapAlpha(textColor, (float)ColorUtils.getAlphaFromColor(textColor) * changeProtocolAnim);
                if (ColorUtils.getAlphaFromColor(textColorAlphed) >= 33) {
                    Fonts.neverlose500_18.drawStringWithShadow(versionName, textX, textY, textColorAlphed);
                }
                GL11.glPopMatrix();
            }
        }
    }

    private void drawProtocolsList(float width, float yStep, float openFactor, float alphaPC, float changeProtocolAnim, int mouseX, int mouseY) {
        if (alphaPC == 0.0f) {
            return;
        }
        float yStepTotal = this.getCupHeight() + this.getYIndent();
        int index = 0;
        for (ProtocolVersion protocolVersion : this.getProtocols()) {
            CFontRenderer font;
            boolean isSelected = this.isSelectedProtocol(protocolVersion);
            boolean hover = (float)mouseX > this.x + 1.0f && (float)mouseX < this.x + width - 1.0f && (float)mouseY > this.y + yStepTotal && (float)mouseY < this.y + yStepTotal + yStep;
            int textColor = ColorUtils.getColor((int)((isSelected ? 255.0f : 175.0f) * openFactor * openFactor), (int)(255.0f * alphaPC * openFactor));
            String protocolName = protocolVersion.getName();
            CFontRenderer cFontRenderer = font = isSelected ? Fonts.neverlose500_16 : Fonts.neverlose500_13;
            if (255.0f * alphaPC * openFactor >= 33.0f) {
                int textShadowColor;
                float textX = this.x + width / 2.0f - (float)font.getStringWidth(protocolName) / 2.0f + (hover ? 0.5f : 0.0f);
                float textY = this.y + yStepTotal + 2.0f;
                if (isSelected && changeProtocolAnim > 0.0f) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(this.x + width / 2.0f, textY + 2.0f, 0.0);
                    GL11.glScaled(1.0f + changeProtocolAnim * changeProtocolAnim * changeProtocolAnim / 4.0f, 1.0f + changeProtocolAnim * changeProtocolAnim * changeProtocolAnim / 4.0f, 1.0);
                    GL11.glTranslated(-(this.x + width / 2.0f), -(textY + 2.0f), 0.0);
                }
                font.drawStringWithShadow(protocolName, textX, textY, textColor);
                if (isSelected && (float)ColorUtils.getAlphaFromColor(textShadowColor = ColorUtils.swapAlpha(textColor, (float)ColorUtils.getAlphaFromColor(textColor) / 8.0f)) >= 26.0f) {
                    GL11.glBlendFunc(770, 32772);
                    font.drawString(protocolName, textX - 1.0f, textY, textShadowColor);
                    font.drawString(protocolName, textX + 1.0f, textY, textShadowColor);
                    font.drawString(protocolName, textX, textY - 1.0f, textShadowColor);
                    font.drawString(protocolName, textX, textY + 1.0f, textShadowColor);
                    font.drawString(protocolName, textX + 0.5f, textY + 0.5f, textShadowColor);
                    font.drawString(protocolName, textX - 0.5f, textY - 0.5f, textShadowColor);
                    font.drawString(protocolName, textX + 0.5f, textY - 0.5f, textShadowColor);
                    font.drawString(protocolName, textX - 0.5f, textY + 0.5f, textShadowColor);
                    GL11.glBlendFunc(770, 771);
                }
                if (isSelected && changeProtocolAnim > 0.0f) {
                    GL11.glPopMatrix();
                }
            }
            ++index;
            yStepTotal += yStep * openFactor;
        }
    }

    private boolean openAnimationHasFinished() {
        return this.openFactor.to == this.openFactor.anim;
    }

    public void drawPanel(float alphaPC, int mouseX, int mouseY) {
        float changeProtocolAnim;
        this.updateOpenOrCloseWants(1000L);
        this.updatePlaySelect();
        float openFactor = this.openFactor.getAnim();
        if (MathUtils.getDifferenceOf(openFactor, this.openFactor.to) < (double)0.01f && !this.openAnimationHasFinished()) {
            this.openFactor.setAnim(this.openFactor.to);
            this.onFinishOpenAnimate(this.openFactor.to == 1.0f);
        }
        if (MathUtils.getDifferenceOf(changeProtocolAnim = this.changeProtocolAnim.getAnim(), 0.0f) != 0.0 && MathUtils.getDifferenceOf(changeProtocolAnim, 0.0f) < (double)0.05f) {
            this.changeProtocolAnim.setAnim(0.0f);
        }
        float width = this.getWidth();
        float height = this.getHeight(openFactor);
        this.drawBackground(width, height, openFactor, alphaPC);
        this.drawTitle(openFactor, width, alphaPC, changeProtocolAnim);
        this.drawProtocolsList(width, this.getStringsStep(), openFactor, alphaPC, changeProtocolAnim, mouseX, mouseY);
    }

    private boolean isHover(float x, float y, float w, float h, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x + w && (float)mouseY >= y && (float)mouseY <= y + h;
    }

    private boolean isOpen() {
        return this.openFactor.to == 1.0f;
    }

    private void wantToOpenOrClose(boolean open) {
        if (open) {
            this.wantOpen = true;
            this.waitToOpen.reset();
            return;
        }
        this.wantClose = true;
        this.waitToClose.reset();
    }

    private void updateOpenOrCloseWants(long waitOpen, long waitClose) {
        if (!this.wantOpen) {
            this.waitToOpen.reset();
        } else if (this.waitToOpen.hasReached(waitOpen)) {
            this.openOrClose(true);
            this.wantOpen = false;
        }
        if (!this.wantClose) {
            this.waitToClose.reset();
        } else if (this.waitToClose.hasReached(waitClose)) {
            this.openOrClose(false);
            this.wantClose = false;
        }
    }

    private void updateOpenOrCloseWants(long wait) {
        this.updateOpenOrCloseWants(wait, wait);
    }

    private void openOrClose(boolean open) {
        if (open == this.isOpen()) {
            return;
        }
        this.openFactor.to = open ? 1.0f : 0.0f;
        this.onOpenOrClose(this.isOpen());
    }

    private void onOpenOrClose(boolean isOpen) {
        if (isOpen) {
            MusicHelper.playSound("viapanelopen.wav", 0.8f);
        } else {
            MusicHelper.playSound("viapanelclose.wav", 0.8f);
        }
    }

    private void postPlaySelect(boolean noneMiss) {
        if (noneMiss) {
            this.playSelectDo = true;
        } else {
            this.playSelectMiss = true;
        }
    }

    private void updatePlaySelect() {
        if (this.playSelectDo) {
            MusicHelper.playSound("viapanelselectdo.wav", 0.4f);
            this.playSelectDo = false;
            return;
        }
        if (this.playSelectMiss) {
            MusicHelper.playSound("viapanelselectmiss.wav", 0.3f);
            this.playSelectMiss = false;
        }
    }

    private boolean onChangeProtocol(ProtocolVersion protocol, boolean changed) {
        if (changed) {
            this.postPlaySelect(true);
            this.wantToOpenOrClose(false);
            this.changeProtocolAnim.setAnim(1.0f);
        } else {
            this.postPlaySelect(false);
        }
        return changed;
    }

    private void onFinishOpenAnimate(boolean isOpen) {
        if (isOpen) {
            MusicHelper.playSound("viapanelonopen.wav", 0.075f);
        } else {
            MusicHelper.playSound("viapanelonclose.wav", 0.05f);
        }
    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        if (button == 1 && this.isHover(this.x, this.y, this.x + this.getWidth(), this.y + this.getCupHeight(), mouseX, mouseY)) {
            if (this.openAnimationHasFinished()) {
                this.openOrClose(!this.isOpen());
            }
        } else if (button == 0 && this.openFactor.to == 1.0f && this.isHover(this.x, this.y + this.getCupHeight(), this.x + this.getWidth(), this.y + this.getHeight(this.openFactor.anim), mouseX, mouseY)) {
            List<ProtocolVersion> protocols = this.getProtocols();
            int currentIndex = (int)(((float)mouseY - (this.y + this.getCupHeight() + this.getYIndent())) / (this.getStringsStep() * this.openFactor.anim));
            if (currentIndex >= 0 && currentIndex < protocols.size()) {
                ProtocolVersion fromIndexProtocol = protocols.get(currentIndex);
                ProtocolVersion currectProtocol = this.getCurrentProtocol();
                if (fromIndexProtocol != null && this.onChangeProtocol(fromIndexProtocol, fromIndexProtocol.getVersion() != currectProtocol.getVersion())) {
                    ViaLoadingBase.getInstance().reload(fromIndexProtocol);
                }
            }
        }
    }

    private void setRenderingPoints(Runnable drawing) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glEnable(2832);
        GL11.glDisable(3008);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 1);
        drawing.run();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPointSize(1.0f);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    private void drawAllPoints(Runnable stencilDataIn, float alphaPC) {
        StencilUtil.initStencilToWrite();
        stencilDataIn.run();
        StencilUtil.readStencilBuffer(1);
        this.setRenderingPoints(() -> this.PARTICLES.forEach(point -> point.drawVertex(alphaPC)));
        StencilUtil.uninitStencilBuffer();
    }

    private void pointsRemoveAuto(float x, float y, float w, float h) {
        if (!this.PARTICLES.isEmpty()) {
            this.PARTICLES.removeIf(point -> point.toRemove(x, y, w, h));
        }
    }

    private void pointsAddAuto(float x, float y, float w, float h, long delay) {
        if (this.particlesAddingDelay.hasReached(delay)) {
            this.PARTICLES.add(new RandTimedColoredVec(x, y, w, h, 500L + (long)(3000.0 * Math.random()), 5.0f, 90.0f));
            this.particlesAddingDelay.reset();
        }
    }

    private class RandTimedColoredVec {
        private final long startTime = System.currentTimeMillis();
        private final long maxTime;
        private final float x;
        private final float y;
        private final float scale;
        private final int color;

        public RandTimedColoredVec(float x, float y, float w, float h, long maxTime, float minScale, float maxScale) {
            this.x = x + w * (float)Math.random();
            this.y = y + h * (float)Math.random();
            float randScaleFactor = (float)Math.random();
            this.scale = minScale + (maxScale - minScale) * 0.25f + (maxScale - minScale) * 0.75f * randScaleFactor * randScaleFactor * randScaleFactor;
            this.color = Color.HSBtoRGB((float)Math.random(), 0.8f, 1.0f);
            this.maxTime = maxTime;
        }

        public float getTimePC() {
            return MathUtils.clamp((float)(System.currentTimeMillis() - this.startTime) / (float)this.maxTime, 0.0f, 1.0f);
        }

        public boolean toRemove(float x, float y, float w, float h) {
            return this.getTimePC() == 1.0f || this.x < x || this.y < y || this.x > x + w || this.y > y + h;
        }

        public int getColor(float alphaPC) {
            float aPC = 1.0f - this.getTimePC();
            return ColorUtils.swapAlpha(this.color, (float)ColorUtils.getAlphaFromColor(this.color) * (aPC > 0.5f ? 1.0f - aPC : aPC) * alphaPC);
        }

        public void drawVertex(float alphaPC) {
            if (alphaPC * 255.0f < 1.0f) {
                return;
            }
            GL11.glPointSize(this.scale * 0.6f + this.scale * 0.4f * (1.0f - this.getTimePC()));
            GL11.glBegin(0);
            RenderUtils.glColor(this.getColor(alphaPC));
            GL11.glVertex2f(this.x, this.y);
            GL11.glEnd();
        }
    }
}

