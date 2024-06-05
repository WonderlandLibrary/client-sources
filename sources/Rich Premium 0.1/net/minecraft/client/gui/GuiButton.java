package net.minecraft.client.gui;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import white.floor.Main;
import white.floor.features.Feature;
import white.floor.font.CFontRenderer;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;

public class GuiButton extends Gui {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");

    /**
     * Button width in pixels
     */
    protected int width;
    // Fade.
    private int opacity = 40;
    private double blob = 0;

    /**
     * Button height in pixels
     */
    protected int height;

    /**
     * The x position of this control.
     */
    public int xPosition;

    /**
     * The y position of this control.
     */
    public int yPosition;

    /**
     * The string displayed on this control.
     */
    public String displayString;
    public int id;
    public String info;
    /**
     * True if this control is enabled, false to disable.
     */
    public boolean enabled;

    /**
     * Hides the button completely if false.
     */
    public boolean visible;
    protected boolean hovered;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, String buttonText, String info) {
        this(buttonId, x, y, 200, 20, buttonText, info);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String info) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.info = info;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this
     * button and 2 if it IS hovering over this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    public void func_191745_a(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
                    && mouseY < this.yPosition + this.height);


            if (hovered) {
                if (this.opacity < 40) {
                    this.opacity += 2;
                }
            } else if (this.opacity > 22) {
                this.opacity -= 2;
            }

            if (hovered) {
                if (this.blob < this.width / 2) {
                    this.blob = MathHelper.lerp(this.blob, this.width / 2, 1 * Feature.deltaTime() * 6);
                }
            } else if (this.blob > this.width / this.width - 1){
                this.blob = MathHelper.lerp(this.blob, this.width / this.width - 1, 1 * Feature.deltaTime() * 6);
            }

            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height; // Flag, tells if your mouse is hovering the button
            int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (flag) {
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(opacity, opacity, opacity, 175).getRGB());
                Gui.drawRect(this.xPosition + this.width / 2 - blob + 2, this.yPosition + this.height - 2.5f, this.xPosition + this.width / 2 + blob - 2, this.yPosition + this.height - 1.5f, Main.getClientColor().getRGB());
                Fonts.neverlose500_15.drawCenteredString(displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 2) / 2, -1);
                Fonts.neverlose500_13.drawCenteredString(info, this.xPosition + this.width / 2, this.yPosition + (this.height) / 2 + 7.5, -1);
            } else {
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(opacity, opacity, opacity, 175).getRGB());
                Gui.drawRect(this.xPosition + this.width / 2 - blob, this.yPosition + this.height - 2.5f, this.xPosition + this.width / 2 + blob, this.yPosition + this.height - 1.5f, Main.getClientColor().getRGB());
                Fonts.neverlose500_15.drawCenteredString(displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 2) / 2, -1);
                Fonts.neverlose500_13.drawCenteredString(info, this.xPosition + this.width / 2, this.yPosition + (this.height) / 2 + 7.5, -1);
            }
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
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
        float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
        float f = (float) (color >> 16 & 0xFF) / 255.0f;
        float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
        float f2 = (float) (color & 0xFF) / 255.0f;
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

    public void setWidth(int width) {
        this.width = width;
    }
}