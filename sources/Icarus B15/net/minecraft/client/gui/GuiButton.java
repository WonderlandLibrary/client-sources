package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.andrewsnetwork.icarus.*;
import net.andrewsnetwork.icarus.utilities.*;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.client.audio.*;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures;
    private final UnicodeFontRenderer guiText;
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hovered;
    
    static {
        buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    }
    
    public GuiButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        this.guiText = new UnicodeFontRenderer(new Font("Verdana", 0, 22));
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
    
    protected int getHoverState(final boolean mouseOver) {
        byte var2 = 1;
        if (!this.enabled) {
            var2 = 0;
        }
        else if (mouseOver) {
            var2 = 2;
        }
        return var2;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            final FontRenderer var4 = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            final int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (Icarus.getEventManager().isCancelled()) {
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
            }
            else if (!Icarus.getEventManager().isCancelled()) {
                RenderHelper.drawBorderedCorneredRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1.0f, -16777216, this.hovered ? -2133601325 : Integer.MIN_VALUE);
            }
            this.mouseDragged(mc, mouseX, mouseY);
            int var6 = 14737632;
            if (!this.enabled) {
                var6 = 10526880;
            }
            else if (this.hovered) {
                var6 = 16777120;
            }
            if (Icarus.getEventManager().isCancelled()) {
                this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
            }
            else if (!Icarus.getEventManager().isCancelled()) {
                this.guiText.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - this.guiText.getStringWidth(this.displayString) / 4 + 4, this.yPosition + (this.height - 8) / 2 - 3, -1);
            }
        }
    }
    
    protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY) {
    }
    
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }
    
    public boolean isMouseOver() {
        return this.hovered;
    }
    
    public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
    }
    
    public void playPressSound(final SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
    }
    
    public int getButtonWidth() {
        return this.width;
    }
    
    public void func_175211_a(final int p_175211_1_) {
        this.width = p_175211_1_;
    }
}
