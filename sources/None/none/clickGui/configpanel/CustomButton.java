package none.clickGui.configpanel;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import none.Client;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class CustomButton extends GuiButton{
	
//	protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets_none.png");
//
//    /** Button width in pixels */
//    protected int width;
//
//    /** Button height in pixels */
//    protected int height;
//
//    /** The x position of this control. */
//    public int xPosition;
//
//    /** The y position of this control. */
//    public int yPosition;
//
//    /** The string displayed on this control. */
//    public String displayString;
//    public int id;
//
//    /** True if this control is enabled, false to disable. */
//    public boolean enabled;
//
//    /** Hides the button completely if false. */
//    public boolean visible;
//    protected boolean hovered;
	
	public CustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	/**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
	@Override
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            TTFFontRenderer fontrenderer = Client.fm.getFont("BebasNeue");
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//            GlStateManager.pushMatrix();
//            GlStateManager.enableBlend();
//            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//            GlStateManager.blendFunc(770, 771);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            int x = 0;
            if (i == 2) {
            	x = 4;
            }
            Gui.drawRect((this.xPosition + x), this.yPosition, (this.xPosition + this.width) - x, this.yPosition + this.height, Colors.getColor(Color.BLACK, 170));
            fontrenderer.drawString(displayString, (this.xPosition + this.xPosition + this.width) / 2 - (fontrenderer.getStringWidth(displayString) / 2), (this.yPosition + this.yPosition + this.height) / 2 - (fontrenderer.getHeight(displayString) / 2), Colors.getColor(Color.WHITE, 255));
//            GlStateManager.popMatrix();
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
	@Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
	@Override
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
	@Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
	@Override
    public boolean isMouseOver()
    {
        return this.hovered;
    }
	@Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }
	@Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
	@Override
    public int getButtonWidth()
    {
        return this.width;
    }
	@Override
    public void setWidth(int width)
    {
        this.width = width;
    }
}
