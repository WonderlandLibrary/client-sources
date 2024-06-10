package me.kaimson.melonclient.gui.elements;

import me.kaimson.melonclient.*;
import java.awt.*;

public class ElementCategory extends Element
{
    private final String text;
    
    public ElementCategory(final int x, final int y, final int width, final int height, final String text, final boolean shouldScissor) {
        super(x, y, width, height, shouldScissor);
        this.text = text;
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        Client.titleRenderer2.drawString(this.text.toUpperCase(), (float)(this.getX() + 1), this.getY() + (this.height - 10) / 2.0f, new Color(40, 40, 40, 200).getRGB());
        Client.titleRenderer2.drawString(this.text.toUpperCase(), (float)this.getX(), this.getY() + (this.height - 10) / 2.0f, new Color(150, 150, 150, 200).getRGB());
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
    }
}
