package me.gishreload.yukon.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class PasswordField extends Gui
{
    private final FontRenderer fontRenderer;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    public boolean isFocused = false;
    public boolean isEnabled = true;
    private GuiScreen parentGuiScreen;

    public PasswordField(GuiScreen var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7)
    {
        this.parentGuiScreen = var1;
        this.fontRenderer = var2;
        this.xPos = var3;
        this.yPos = var4;
        this.width = var5;
        this.height = var6;
        this.setText(var7);
    }

    public void setText(String var1)
    {
        this.text = var1;
    }

    public String getText()
    {
        return this.text;
    }

    public void updateCursorCounter()
    {
        ++this.cursorCounter;
    }

    public void textboxKeyTyped(char var1, int var2)
    {
        if (this.isEnabled && this.isFocused)
        {
            if (var1 == 9)
            {
                this.parentGuiScreen.confirmClicked(this.isEnabled, var2);
            }

            if (var1 == 22)
            {
                String var3 = GuiScreen.getClipboardString();

                if (var3 == null)
                {
                    var3 = "";
                }

                int var4 = 32 - this.text.length();

                if (var4 > var3.length())
                {
                    var4 = var3.length();
                }

                if (var4 > 0)
                {
                    this.text = this.text + var3.substring(0, var4);
                }
            }

            if (var2 == 14 && this.text.length() > 0)
            {
                this.text = this.text.substring(0, this.text.length() - 1);
            }

            if ((ChatAllowedCharacters.ILLEGAL_FILE_CHARACTERS.length >= 0 || var1 > 32) && (this.text.length() < this.maxStringLength || this.maxStringLength == 0))
            {
                this.text = this.text + var1;
            }
        }
    }

    public void mouseClicked(int var1, int var2, int var3)
    {
        boolean var4 = this.isEnabled && var1 >= this.xPos && var1 < this.xPos + this.width && var2 >= this.yPos && var2 < this.yPos + this.height;
        this.setFocused(var4);
    }

    public void setFocused(boolean var1)
    {
        if (var1 && !this.isFocused)
        {
            this.cursorCounter = 0;
        }

        this.isFocused = var1;
    }

    public void drawTextBox()
    {
        drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -13382401);
        drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -13421773);
        String s = this.text.replaceAll(".", "*");

        if (this.isEnabled)
        {
            boolean var1 = this.isFocused && this.cursorCounter / 6 % 2 == 0;
            this.drawString(this.fontRenderer, s + (var1 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 14737632);
        }
        else
        {
            this.drawString(this.fontRenderer, s, this.xPos + 4, this.yPos + (this.height - 8) / 2, 7368816);
        }
    }

    public void setMaxStringLength(int var1)
    {
        this.maxStringLength = var1;
    }
}
