package net.minecraft.client.gui;

import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

import static club.bluezenith.util.font.FontUtil.*;
import static club.bluezenith.util.render.RenderUtil.delta;
import static net.minecraft.util.MathHelper.clamp;

public class GuiButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /**
     * Button width in pixels
     */
    protected int width;

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

    /**
     * True if this control is enabled, false to disable.
     */
    public boolean enabled;

    /**
     * Hides the button completely if false.
     */
    public boolean visible;
    protected boolean hovered, useOutline;

    private int alpha;

    boolean useMediumFont;
    protected TFontRenderer fontRenderer;

    Runnable onClickFunc;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.alpha = 100;
    }

    public GuiButton useMediumFont() {
        this.useMediumFont = true;
        return this;
    }

    public GuiButton useDefaultFont() {
        this.useMediumFont = false;
        return this;
    }

    public GuiButton onClick(Runnable onClickFunc) {
        this.onClickFunc = onClickFunc;
        return this;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
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

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            //drawVanillaButton(mc);
            drawModifiedButton(mc, mouseX, mouseY);
        }
    }

    public GuiButton useOutline() {
        this.useOutline = true;
        return this;
    }

    protected void determineFontRenderer() {
        fontRenderer = useMediumFont ? vkMedium35 : rubikMedium37;
    }

    protected void drawModifiedButton(Minecraft mc, int mouseX, int mouseY) {
        determineFontRenderer();

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        if(this.useOutline)
            RenderUtil.hollowRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, 1, -1);

        if (this.enabled) {
            alpha = (int) clamp(alpha + (hovered ? 0.3F : -0.3F) * delta, 100, 180);
            if(this.hovered) {
                alpha = (int) clamp(alpha + 0.3f * delta, 100, 180);
            } else {
                alpha = (int) clamp(alpha - 0.3f * delta, 100, 180);
            }
            Color c = new Color(0, 0, 8 / 255f, alpha / 255f);
            Color c1 = new Color(0, 0, 16 / 255f, alpha / 255f);
            this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, c.getRGB(), c1.getRGB());
        } else {
            this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, new Color(16, 16, 32, 100).getRGB(), new Color(32, 32, 64, 100).getRGB());
        }
        fontRenderer.drawCenteredString(this.displayString, (this.xPosition + this.width / 2f) + 0.01F, this.yPosition + (fontRenderer.FONT_HEIGHT / 2f) - (useMediumFont ? 1.5f : -0.8F), Color.WHITE.getRGB());
        this.mouseDragged(mc, mouseX, mouseY);
    }

    private void drawVanillaButton(Minecraft mc, int mouseX, int mouseY) {
        FontRenderer fontrenderer = mc.fontRendererObj;
        mc.getTextureManager().bindTexture(buttonTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int i = this.getHoverState(this.hovered);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.mouseDragged(mc, mouseX, mouseY);
        int j = 14737632;

        if (!this.enabled) {
            j = 10526880;
        } else if (this.hovered) {
            j = 16777120;
        }

        this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
    }

    public final void runClickCallback() {
        if(this.onClickFunc != null)
        this.onClickFunc.run();
    }
    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
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
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
