/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.BloomUtil;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiButton
extends Gui {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    protected int width = 200;
    protected int height = 20;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled = true;
    public boolean visible = true;
    protected boolean hovered;
    final AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private int opacity = 40;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, CFontRenderer font) {
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    protected int getHoverState(boolean mouseOver) {
        int i = 1;
        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }
        return i;
    }

    public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
        if (this.visible) {
            if (Panic.stop || !ComfortUi.get.isBetterButtons()) {
                FontRenderer fontrenderer = p_191745_1_.fontRendererObj;
                p_191745_1_.getTextureManager().bindTexture(BUTTON_TEXTURES);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.hovered = p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                this.mouseDragged(p_191745_1_, p_191745_2_, p_191745_3_);
                int j = 0xE0E0E0;
                if (!this.enabled) {
                    j = 0xA0A0A0;
                } else if (this.hovered) {
                    j = 0xFFFFA0;
                }
                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
            } else {
                if (!this.visible) {
                    return;
                }
                p_191745_1_.getTextureManager().bindTexture(BUTTON_TEXTURES);
                this.hovered = p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height;
                this.mouseDragged(p_191745_1_, p_191745_2_, p_191745_3_);
                float x = this.xPosition;
                float y = this.yPosition;
                float w = this.width;
                float h = this.height;
                float x2 = x + w;
                float y2 = y + h;
                this.anim.to = this.hovered ? 1.0f : 0.0f;
                float bgAlphaPC = MathUtils.clamp((0.35f + this.anim.getAnim() * 0.65f) * 1.01f, 0.0f, 1.0f);
                int colorise = ColorUtils.getColor(120, 140, 190);
                int bgC = ColorUtils.swapAlpha(colorise, 255.0f * (bgAlphaPC / 4.0f));
                int bgCOut = ColorUtils.swapAlpha(colorise, 255.0f * bgAlphaPC);
                float round = 2.0f + 3.5f * this.anim.getAnim();
                float radius = 0.25f + 2.0f * this.anim.getAnim();
                float ext = this.anim.getAnim() * 1.0f;
                GlStateManager.blendFunc(770, 1);
                RenderUtils.drawRoundOutline(x + ext, y + ext, w - ext * 2.0f, h - ext * 2.0f, round, radius, bgC, bgCOut);
                int texAlpha = (int)(125.0f + 130.0f * this.anim.getAnim());
                int textCol = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), texAlpha);
                CFontRenderer font = Fonts.mntsb_15;
                float texW = font.getStringWidth(this.displayString);
                font.drawString(this.displayString, x + w / 2.0f - texW / 2.0f, y + h / 2.0f + (float)(font.getHeight() / 4) - 2.5f, textCol);
                if (texAlpha > 140) {
                    Runnable run = () -> font.drawString(this.displayString, x + w / 2.0f - texW / 2.0f, y + h / 2.0f + (float)(font.getHeight() / 4) - 2.5f, -1);
                    BloomUtil.renderShadow(run, colorise, 1 + (int)(radius * 6.0f), 1, 2.7f * this.anim.getAnim(), false);
                }
                GlStateManager.blendFunc(770, 771);
            }
        }
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            boolean bl = this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            if (this.hovered) {
                if (this.opacity < 40) {
                    ++this.opacity;
                }
            } else if (this.opacity > 22) {
                --this.opacity;
            }
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int color = ColorUtils.getColor(25, 25, 25, 73);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            int c1 = ColorUtils.getColor(0, 0, 0, 235);
            int c2 = ColorUtils.getColor(0, 180, 255);
            RenderUtils.fullRoundFG(this.xPosition - 2, this.yPosition - 1, this.width + 3, this.height + 1, 7.0f, c2, c2, c2, c2, false);
            RenderUtils.fullRoundFG(this.xPosition - 1, this.yPosition, this.width + 2, this.height, 5.5f, c1, c1, c1, c1, false);
            this.mouseDragged(mc, mouseX, mouseY);
            String str = "Version \ufffdb" + this.displayString;
            Fonts.mntsb_18.drawString(str, this.xPosition + this.width / 2 - Fonts.mntsb_18.getStringWidth(str) / 2, (double)(this.yPosition + (this.height - 2) / 3) - 0.5, -1);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

