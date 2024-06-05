package club.mega.util;

import club.mega.gui.altmanager.GuiAltManager;
import club.mega.interfaces.MinecraftInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

public class TextFieldUtil extends GuiTextField implements MinecraftInterface {

    private final Color color;
    private final double cr;

    public TextFieldUtil(final int id, final double x, final double y, final double width, final double height, final double cr) {
        this(id, x, y, width, height, cr, ColorUtil.getMainColor());
    }

    public TextFieldUtil(final int id, final double x, final double y, final double width, final double height, final double cr, final Color color) {
        super(id, MC.fontRendererObj, (int)x, (int)y, (int)width, (int)height);
        this.color = color;
        this.cr = cr;
    }

    @Override
    public void drawTextBox()
    {
        if (this.getVisible())
        {
            if (this.getEnableBackgroundDrawing())
            {
                RenderUtil.drawRoundedRect(xPosition, yPosition, width, height, cr, color);
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length())
            {
                k = s.length();
            }

            if (s.length() > 0)
            {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.fontRendererInstance.drawStringWithShadow(s1, (float)l, (float)i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag)
            {
                k1 = j > 0 ? l + this.width : l;
            }
            else if (flag2)
            {
                k1 = j1 - 1;
                --j1;
            }

            if (s.length() > 0 && flag && j < s.length())
            {
                j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), (float)j1, (float)i1, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                }
                else
                {
                    this.fontRendererInstance.drawStringWithShadow("_", (float)k1, (float)i1, i);
                }
            }

            if (k != j)
            {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }

}
