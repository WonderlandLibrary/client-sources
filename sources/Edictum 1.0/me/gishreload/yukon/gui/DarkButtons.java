package me.gishreload.yukon.gui;

import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

public class DarkButtons extends GuiButton
{
    public int cs;
    public int alpha;
    
    public DarkButtons(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public DarkButtons(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.cs = 0;
        this.alpha = 255;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        final Color color1 = new Color(0F, 0F, 0F);
        WatermarkRenderer.colorProvider.update();
        final int col1 = color1.getRGB();
        if (this.visible) {
            final FontRenderer var4 = Minecraft.fontRendererObj;
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            this.updatefade();
            if (this.hovered) {
                if (this.cs >= 4) {
                    this.cs = 4;
                }
                ++this.cs;
            }
            else {
                if (this.cs <= 0) {
                    this.cs = 0;
                }
                --this.cs;
            }
            if (this.enabled) {
                Gui.drawRect(this.xPosition + this.cs, this.yPosition, this.xPosition + this.width - this.cs, this.yPosition + this.height, col1);
            }
            else {
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -1875621538);
            }
            this.mouseDragged(mc, mouseX, mouseY);
            final int var2 = 14737632;
            this.drawCenteredString(var4, StringUtils.stripControlCodes(this.displayString), this.xPosition + this.width / 2, this.yPosition + (this.height - 5) / 2, var2);
        }
    }
    
    public void setDisplayString(final String display) {
        this.displayString = display;
    }
    
    public void updatefade() {
        if (this.enabled) {
            if (this.hovered) {
                this.alpha += 5;
                if (this.alpha >= 210) {
                    this.alpha = 210;
                }
            }
            else {
                this.alpha -= 25;
                if (this.alpha <= 120) {
                    this.alpha = 120;
                }
            }
        }
    }
    
    public GameSettings.Options returnEnumOptions() {
        return null;
    }
}
