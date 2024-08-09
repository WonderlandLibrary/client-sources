/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Widget
extends AbstractGui
implements IRenderable,
IGuiEventListener {
    public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
    protected int width;
    protected int height;
    public int x;
    public int y;
    private ITextComponent message;
    private boolean wasHovered;
    protected boolean isHovered;
    public boolean active = true;
    public boolean visible = true;
    protected float alpha = 1.0f;
    protected long nextNarration = Long.MAX_VALUE;
    private boolean focused;

    public Widget(int n, int n2, int n3, int n4, ITextComponent iTextComponent) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
        this.message = iTextComponent;
    }

    public int getHeightRealms() {
        return this.height;
    }

    protected int getYImage(boolean bl) {
        int n = 1;
        if (!this.active) {
            n = 0;
        } else if (bl) {
            n = 2;
        }
        return n;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.visible) {
            boolean bl = this.isHovered = n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height;
            if (this.wasHovered != this.isHovered()) {
                if (this.isHovered()) {
                    if (this.focused) {
                        this.queueNarration(200);
                    } else {
                        this.queueNarration(750);
                    }
                } else {
                    this.nextNarration = Long.MAX_VALUE;
                }
            }
            if (this.visible) {
                this.renderButton(matrixStack, n, n2, f);
            }
            this.narrate();
            this.wasHovered = this.isHovered();
        }
    }

    protected void narrate() {
        String string;
        if (this.active && this.isHovered() && Util.milliTime() > this.nextNarration && !(string = this.getNarrationMessage().getString()).isEmpty()) {
            NarratorChatListener.INSTANCE.say(string);
            this.nextNarration = Long.MAX_VALUE;
        }
    }

    protected IFormattableTextComponent getNarrationMessage() {
        return new TranslationTextComponent("gui.narrate.button", this.getMessage());
    }

    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, this.alpha);
        int n3 = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 46 + n3 * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + n3 * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, minecraft, n, n2);
        int n4 = this.active ? 0xFFFFFF : 0xA0A0A0;
        Widget.drawCenteredString(matrixStack, fontRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, n4 | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int n, int n2) {
    }

    public void onClick(double d, double d2) {
    }

    public void onRelease(double d, double d2) {
    }

    protected void onDrag(double d, double d2, double d3, double d4) {
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.active && this.visible) {
            boolean bl;
            if (this.isValidClickButton(n) && (bl = this.clicked(d, d2))) {
                this.playDownSound(Minecraft.getInstance().getSoundHandler());
                this.onClick(d, d2);
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (this.isValidClickButton(n)) {
            this.onRelease(d, d2);
            return false;
        }
        return true;
    }

    protected boolean isValidClickButton(int n) {
        return n == 0;
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.isValidClickButton(n)) {
            this.onDrag(d, d2, d3, d4);
            return false;
        }
        return true;
    }

    protected boolean clicked(double d, double d2) {
        return this.active && this.visible && d >= (double)this.x && d2 >= (double)this.y && d < (double)(this.x + this.width) && d2 < (double)(this.y + this.height);
    }

    public boolean isHovered() {
        return this.isHovered || this.focused;
    }

    @Override
    public boolean changeFocus(boolean bl) {
        if (this.active && this.visible) {
            this.focused = !this.focused;
            this.onFocusedChanged(this.focused);
            return this.focused;
        }
        return true;
    }

    protected void onFocusedChanged(boolean bl) {
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return this.active && this.visible && d >= (double)this.x && d2 >= (double)this.y && d < (double)(this.x + this.width) && d2 < (double)(this.y + this.height);
    }

    public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
    }

    public void playDownSound(SoundHandler soundHandler) {
        soundHandler.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int n) {
        this.width = n;
    }

    public void setAlpha(float f) {
        this.alpha = f;
    }

    public void setMessage(ITextComponent iTextComponent) {
        if (!Objects.equals(iTextComponent.getString(), this.message.getString())) {
            this.queueNarration(250);
        }
        this.message = iTextComponent;
    }

    public void queueNarration(int n) {
        this.nextNarration = Util.milliTime() + (long)n;
    }

    public ITextComponent getMessage() {
        return this.message;
    }

    public boolean isFocused() {
        return this.focused;
    }

    protected void setFocused(boolean bl) {
        this.focused = bl;
    }
}

