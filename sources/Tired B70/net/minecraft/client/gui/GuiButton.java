package net.minecraft.client.gui;

import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.interfaces.FHook;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.list.OutlineShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /**
     * Hides the button completely if false.
     */
    public double radius = 2.5;
    public boolean alternativRender = false;

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
     * Button width in pixels
     */
    protected int width;
    public boolean visible;
    protected boolean hovered;
    /**
     * Button height in pixels
     */
    protected int height;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
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

    public boolean isAlternativRender() {
        return alternativRender;
    }

    public void setAlternativRender(boolean alternativRender) {
        this.alternativRender = alternativRender;
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

    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean rect, int xPosition, int yPosition) {
        if (this.visible) {
            //  FontRenderer fontrenderer = mc.fontRendererObj;
            //   mc.getTextureManager().bindTexture(buttonTextures);
            //  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            //    GlStateManager.enableBlend();
            //    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            //   GlStateManager.blendFunc(770, 771);
            //   this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
            ShaderManager.shaderBy(OutlineShader.class).startESP();
            //   this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            if (rect) {
                RenderProcessor.drawRoundedRectangle(xPosition, yPosition, xPosition + width, yPosition + height, 2, Integer.MIN_VALUE);
            }

            if (hovered) {
                RenderProcessor.drawRoundedRectangle(xPosition, yPosition, xPosition + width, yPosition + height, 2, new Color(1, 1, 1, 30).getRGB());
            }
            ShaderManager.shaderBy(OutlineShader.class).stopESP();

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }

//            FHook.fontRenderer.drawCenteredString(this.displayString, xPosition + this.width / 2, yPosition + (this.height - 8) / 2 + 2, -1);
        }
    }


    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean rect) {
        if (this.visible) {
            //  FontRenderer fontrenderer = mc.fontRendererObj;
            //   mc.getTextureManager().bindTexture(buttonTextures);
            //  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);


            if (rect) {
                RenderProcessor.drawRoundedRectangle(xPosition, yPosition, xPosition + width, yPosition + height, 6, Integer.MIN_VALUE);
            }

            if (hovered) {
                RenderProcessor.drawRoundedRectangle(xPosition, yPosition, xPosition + width, yPosition + height, 6, new Color(1, 1, 1, 30).getRGB());
            }

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }

            FHook.fontRenderer.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2 + 2, -1);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    protected void drawOtherShit() {

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
