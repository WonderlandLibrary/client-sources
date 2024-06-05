package client.metaware.api.gui;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.impl.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;


public class Button extends GuiButton {

    private int x;
    private int y;
    private int x1;
    private int y1;
    private String text;
    double size;

    public Button(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.size = 0;
        this.x = par2;
        this.y = par3;
        this.x1 = par4;
        this.y1 = par5;
        this.text = par6Str;
    }

    public Button(int i, int j, int k, String stringParams) {
        this(i, j, k, 200, 20, stringParams);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        final CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(19);
        boolean isOverButton = (mouseX >= this.x) && (mouseX <= this.x + this.x1) && (mouseY >= this.y) && (mouseY <= this.y + this.y1);
        int color = isOverButton ? new Color(255, 255, 255).getRGB() : new Color(200, 200, 200).getRGB();
        RenderUtil.drawRoundedRectangle(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, this.y + this.y1 + this.size, 10, new Color(1,1,1, 200).getRGB());
        fr.drawCenteredString(this.text, this.x + this.x1 / 2, this.y + this.y1 / 2, color);
        //RenderUtil.drawRoundedRect2(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, this.y + this.y1 + this.size, 6, Color.TRANSLUCENT);
    }
}

