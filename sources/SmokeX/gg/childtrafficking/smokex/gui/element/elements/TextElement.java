// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.gui.element.Element;

public final class TextElement extends Element
{
    private String text;
    private final boolean dropShadow;
    public int color;
    
    public TextElement(final String identifier, final float x, final float y, final String text, final int color) {
        this(identifier, x, y, text, color, true);
    }
    
    public TextElement(final String identifier, final float x, final float y, final String text, final int color, final boolean dropShadow) {
        super(identifier, x, y);
        this.color = color;
        this.dropShadow = dropShadow;
        this.setText(text);
        this.clickable = false;
    }
    
    public void setText(final String text) {
        this.setWidth(SmokeXClient.getInstance().fontRenderer.getWidth(text));
        this.setHeight(SmokeXClient.getInstance().fontRenderer.getHeight(text));
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    @Override
    public void render(final double partialTicks) {
        this.setWidth(SmokeXClient.getInstance().fontRenderer.getWidth(this.text));
        this.setHeight(SmokeXClient.getInstance().fontRenderer.getHeight(this.text));
        if (this.dropShadow) {
            SmokeXClient.getInstance().fontRenderer.drawStringWithShadow(this.text, this.getRenderX(), this.getRenderY(), this.color);
        }
        else {
            SmokeXClient.getInstance().fontRenderer.drawString(this.text, this.getRenderX(), this.getRenderY(), this.color);
        }
        super.render(partialTicks);
    }
}
