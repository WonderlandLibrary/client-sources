// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ExpandButton extends GuiButton
{
    private int x;
    private int y;
    private int x1;
    private int y1;
    private String text;
    int alphaInc;
    int alpha;
    int size;
    
    public ExpandButton(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.alphaInc = 100;
        this.alpha = 0;
        this.size = 0;
        this.x = par2;
        this.y = par3;
        this.x1 = par4;
        this.y1 = par5;
        this.text = par6Str;
    }
    
    public ExpandButton(final int i, final int j, final int k, final String stringParams) {
        this(i, j, k, 200, 20, stringParams);
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        final boolean isOverButton = mouseX >= this.x && mouseX <= this.x + this.x1 && mouseY >= this.y && mouseY <= this.y + this.y1;
        if (isOverButton && this.alphaInc <= 150) {
            this.alphaInc += 5;
            this.alpha = this.alphaInc << 24;
        }
        else if (!isOverButton && this.alphaInc >= 100) {
            this.alphaInc -= 5;
            this.alpha = this.alphaInc << 24;
        }
        if (this.alphaInc > 150) {
            this.alphaInc = 150;
        }
        else if (this.alphaInc < 100) {
            this.alphaInc = 100;
        }
        if (isOverButton && this.size <= 1) {
            ++this.size;
        }
        else if (!isOverButton && this.size >= 0) {
            --this.size;
        }
        drawRect(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, this.y + this.y1 + this.size, this.alpha);
        this.drawCenteredString(mc.fontRendererObj, this.text, this.x + this.x1 / 2, this.y + this.y1 / 2 - 4, -1);
    }
}
