/*
 * Decompiled with CFR 0.143.
 */
package me.blitzthunder.neonscreen.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiCustomButton
extends GuiButton {
    private int x;
    private int y;
    private int x1;
    private int y1;
    private String text;
    int alphaInc = 100;
    int alpha = 0;
    int size = 0;

    public GuiCustomButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.x = par2;
        this.y = par3;
        this.x1 = par4;
        this.y1 = par5;
        this.text = par6Str;
    }

    public GuiCustomButton(int i, int j, int k, String stringParams) {
        this(i, j, k, 200, 20, stringParams);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean isOverButton;
        boolean bl = isOverButton = mouseX >= this.x && mouseX <= this.x + this.x1 && mouseY >= this.y && mouseY <= this.y + this.y1;
        if (isOverButton && this.alphaInc <= 2000) {
            this.alphaInc += 20;
            this.alpha = this.alphaInc << 24;
        } else if (!isOverButton && this.alphaInc >= 10) {
            --this.alphaInc;
            this.alpha = this.alphaInc << 24;
        }
        if (this.alphaInc > 150) {
            this.alphaInc = 150;
        } else if (this.alphaInc < 100) {
            this.alphaInc = 100;
        }
        if (isOverButton && this.size <= 2) {
            ++this.size;
        } else if (!isOverButton && this.size >= 0) {
            --this.size;
        }
        Gui.drawRect(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, this.y + this.y1 + this.size, this.alpha);
        Gui.drawCenteredString(mc.fontRendererObj, this.text, this.x + this.x1 / 2, this.y + this.y1 / 2 - 4, -1);
    }
}

