package me.satisfactory.base.gui.Buttons;

import java.awt.*;
import net.minecraft.client.gui.*;

public class Checkbox extends GuiScreen
{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean value;
    
    public Checkbox(final int x, final int y, final int width, final int height, final boolean value) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.value = value;
    }
    
    public void valueChanged(final boolean value) {
    }
    
    public void drawScreen(final Color color) {
        GuiScreen.drawOutline(color.getRGB(), this.x, this.y, this.width + this.x, this.height + this.y);
        if (this.value) {
            Gui.drawRect(this.x, this.y, this.width + this.x, this.height + this.y - 1, Color.GREEN.getRGB());
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY) {
        final boolean bl;
        final boolean hovered = bl = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
        if (hovered) {
            this.valueChanged(this.value = !this.value);
        }
    }
    
    public void drawBoxString(final String string, final Color color) {
        Gui.drawCenteredString(Checkbox.mc.fontRendererObj, string, this.x - this.width - Checkbox.mc.fontRendererObj.getStringWidth(string) / 2, this.y, color.getRGB());
    }
    
    public boolean isChecked() {
        return this.value;
    }
    
    public void setChecked(final boolean value) {
        this.value = value;
    }
}
