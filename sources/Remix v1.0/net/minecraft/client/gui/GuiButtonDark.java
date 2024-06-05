package net.minecraft.client.gui;

import net.minecraft.util.*;
import me.mees.remix.ui.font.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import me.satisfactory.base.*;
import java.awt.*;
import net.minecraft.client.audio.*;

public class GuiButtonDark extends Gui
{
    MinecraftFontRenderer testfont;
    protected static final ResourceLocation buttonTextures;
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hovered;
    private static final String __OBFID = "CL_00000668";
    
    public GuiButtonDark(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public GuiButtonDark(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        this.testfont = FontManager.clickGuiFont;
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
            mc.getTextureManager().bindTexture(GuiButtonDark.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            final int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            Gui.drawOutlinedDiagonallyRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), 1.0f, Base.INSTANCE.GetMainColor(), new Color(40, 40, 40).getRGB(), 5);
            if (this.hovered) {
                Gui.drawOutlinedDiagonallyRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), 1.0f, Base.INSTANCE.GetMainColor(), -1, 5);
            }
            this.mouseDragged(mc, mouseX, mouseY);
            this.testfont.drawCenteredString(this.displayString, (float)(this.xPosition + this.width / 2), (float)(this.yPosition + this.height / 2 - this.testfont.getStringWidth(this.displayString) / 2), this.hovered ? Base.INSTANCE.GetMainColor() : -1);
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
    
    static {
        buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    }
}
