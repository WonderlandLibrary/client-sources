package net.minecraft.src;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import me.enrythebest.reborn.cracked.*;

public class GuiButton extends Gui
{
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean drawButton;
    protected boolean field_82253_i;
    long lastScale;
    int scaledWidth;
    int scaledHeight;
    int alpha;
    
    public GuiButton(final int par1, final int par2, final int par3, final String par4Str) {
        this(par1, par2, par3, 200, 20, par4Str);
    }
    
    public GuiButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        this.scaledWidth = 0;
        this.scaledHeight = 0;
        this.alpha = 10;
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.drawButton = true;
        this.id = par1;
        this.xPosition = par2;
        this.yPosition = par3;
        this.width = par4;
        this.height = par5;
        this.displayString = par6Str;
    }
    
    protected int getHoverState(final boolean par1) {
        byte var2 = 1;
        if (!this.enabled) {
            var2 = 0;
        }
        else if (par1) {
            var2 = 2;
        }
        return var2;
    }
    
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        Morbid.getManager();
        if (ModManager.getMod("vanilla").isEnabled()) {
            if (this.drawButton) {
                final FontRenderer var4 = par1Minecraft.fontRenderer;
                par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.field_82253_i = (par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height);
                final int var5 = this.getHoverState(this.field_82253_i);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
                this.mouseDragged(par1Minecraft, par2, par3);
                int var6 = 14737632;
                if (!this.enabled) {
                    var6 = -6250336;
                }
                else if (this.field_82253_i) {
                    var6 = 16777120;
                }
                this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
            }
        }
        else if (this.drawButton) {
            final Color black = new Color(0, 0, 0, 100);
            final FontRenderer var7 = par1Minecraft.fontRenderer;
            par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_82253_i = (par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height);
            final int var8 = this.getHoverState(this.field_82253_i);
            this.updateButton();
            Gui.drawRect(this.xPosition - this.scaledWidth, this.yPosition - this.scaledHeight, this.xPosition + this.width + this.scaledWidth, this.yPosition + this.height + this.scaledHeight, black.getRGB());
            this.drawVerticalLine(this.xPosition - this.scaledWidth, this.yPosition - this.scaledHeight, this.yPosition + this.height + this.scaledHeight, -12);
            this.drawVerticalLine(this.xPosition + this.width + this.scaledWidth, this.yPosition - this.scaledHeight, this.yPosition + this.height + this.scaledHeight, -12);
            this.drawHorizontalLine(this.xPosition + this.width + this.scaledWidth, this.xPosition - this.scaledWidth, this.yPosition + this.height + this.scaledHeight, -12);
            this.drawHorizontalLine(this.xPosition + this.width + this.scaledWidth, this.xPosition - this.scaledWidth, this.yPosition - this.scaledHeight, -12);
            this.mouseDragged(par1Minecraft, par2, par3);
            int var9 = 14737632;
            if (!this.enabled) {
                var9 = 14079702;
            }
            else if (this.field_82253_i) {
                var9 = 16777215;
            }
            this.drawCenteresdString(var7, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 10) / 2 + 2, var9);
        }
    }
    
    public void updateButton() {
        Morbid.getManager();
        if (!ModManager.getMod("vanilla").isEnabled()) {
            if (MorbidWrapper.getSystemTime() - this.lastScale >= 5L) {
                if (this.field_82253_i && this.enabled) {
                    this.alpha += 5;
                }
                else {
                    this.alpha -= 5;
                }
                this.lastScale = MorbidWrapper.getSystemTime();
            }
            if (this.alpha < 75) {
                this.alpha = 75;
            }
            if (this.alpha > 175) {
                this.alpha = 175;
            }
        }
    }
    
    public void drawCenteresdString(final FontRenderer par1FontRenderer, final String par2Str, final int par3, final int par4, final int par5) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(par2Str, par3 - Minecraft.getMinecraft().fontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }
    
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
    }
    
    public void mouseReleased(final int par1, final int par2) {
    }
    
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        return this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
    }
    
    public boolean func_82252_a() {
        return this.field_82253_i;
    }
    
    public void func_82251_b(final int par1, final int par2) {
    }
}
