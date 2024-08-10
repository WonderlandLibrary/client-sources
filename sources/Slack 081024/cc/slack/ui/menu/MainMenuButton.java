package cc.slack.ui.menu;


import cc.slack.utils.font.Fonts;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class MainMenuButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /** Button width in pixels */
    protected int width;

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;

    private double hoverPercent;

    private Color color;

    public MainMenuButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x - 50, y, 100, 20, buttonText);
    }

    public MainMenuButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {

        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.color = new Color(255, 255, 255);
    }

    public MainMenuButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Color c)
    {

        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.color = c;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition + MainMenu.animY && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height + MainMenu.animY;
            if(this.hovered) {
                hoverPercent += (1 - hoverPercent) / 4;
            }else{
                hoverPercent += (0 - hoverPercent) / 4;
            }

            RenderUtil.drawRoundedRect(this.xPosition, this.yPosition + MainMenu.animY, this.xPosition + width, this.yPosition + this.height+ MainMenu.animY, 5, new Color(color.getRed(), color.getGreen(), color.getBlue(), 10 + (int) (hoverPercent * 20)).getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtil.drawRoundedRectBorder(this.xPosition - 1, this.yPosition + MainMenu.animY - 1, this.xPosition + width + 1, this.yPosition + this.height + MainMenu.animY + 1, 5,  new Color(10, 10, 10, 120 ).getRGB(), 2);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtil.drawRoundedRectBorder(this.xPosition, this.yPosition + MainMenu.animY, this.xPosition + width, this.yPosition + this.height+ MainMenu.animY, 5,  new Color(255, 255, 255, 120 ).getRGB(), 1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 16777215;

            if (!this.enabled)
            {
                j = 10526880;
            }

            Fonts.apple18.drawCenteredStringWithShadow(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 6) / 2 + MainMenu.animY, j);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
}
