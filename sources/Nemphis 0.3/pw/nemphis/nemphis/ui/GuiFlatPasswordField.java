/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.ui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class GuiFlatPasswordField
extends GuiTextField {
    public int tick = 0;

    public GuiFlatPasswordField(int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_) {
        super(p_i45542_1_, p_i45542_2_, p_i45542_3_, p_i45542_4_, p_i45542_5_, p_i45542_6_);
    }

    @Override
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.isFocused) {
                Gui.drawRect(this.xPosition - 1, this.yPosition, this.xPosition + this.width + 1, this.yPosition + this.height, -2131956500);
            } else {
                Gui.drawRect(this.xPosition - 1, this.yPosition, this.xPosition + this.width + 1, this.yPosition + this.height, -2131956500);
            }
            int var1 = this.isEnabled ? this.enabledColor : this.disabledColor;
            int var2 = this.cursorPosition - this.lineScrollOffset;
            int var3 = this.selectionEnd - this.lineScrollOffset;
            String var4 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean var5 = var2 >= 0 && var2 <= var4.length();
            boolean var6 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var5;
            int var7 = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int var8 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int var9 = var7;
            if (var3 > var4.length()) {
                var3 = var4.length();
            }
            if (var4.length() > 0) {
                String var10 = var5 ? var4.substring(0, var2) : var4;
                var9 = this.fontRendererInstance.drawStringWithShadow(var10.replaceAll(".", "*"), var7, var8, var1);
            }
            boolean var11 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int var12 = var9;
            if (!var5) {
                var12 = var2 > 0 ? var7 + this.width : var7;
            } else if (var11) {
                var12 = var9 - 1;
                --var9;
            }
            if (var4.length() > 0 && var5 && var2 < var4.length()) {
                var9 = this.fontRendererInstance.drawStringWithShadow(var4.substring(var2).replaceAll(".", "*"), var9, var8, var1);
            }
            if (var6) {
                if (var11) {
                    Gui.drawRect(var12, var8 - 1, var12 + 1, var8 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                } else {
                    this.fontRendererInstance.drawStringWithShadow("_", var12, var8, var1);
                }
            }
            if (var3 != var2) {
                int var13 = var7 + this.fontRendererInstance.getStringWidth(var4.substring(0, var3).replaceAll(".", "*"));
                this.drawCursorVertical(var12, var8 - 1, var13 - 1, var8 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }
}

