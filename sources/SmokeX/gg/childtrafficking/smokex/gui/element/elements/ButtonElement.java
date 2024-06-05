// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.gui.element.Element;

public final class ButtonElement extends Element
{
    private int color;
    private String text;
    
    public ButtonElement(final String identifier, final float x, final float y, final String text, final int color) {
        this(identifier, x, y, 50.0f, 25.0f, text, color);
    }
    
    public ButtonElement(final String identifier, final float x, final float y, final float width, final float height, final String text, final int color) {
        super(identifier, x, y, width, height);
        this.color = color;
        this.text = text;
        this.clickable = true;
    }
    
    public Element setColor(final int color) {
        this.color = color;
        return this;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public Element setText(final String text) {
        this.text = text;
        return this;
    }
    
    public String getText() {
        return this.text;
    }
    
    @Override
    public void render(final double partialTicks) {
        RenderingUtils.drawRect(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight(), -15592942);
        SmokeXClient.getInstance().fontRenderer.drawStringWithShadow(this.text, this.getRenderX() + this.getWidth() / 2.0f - SmokeXClient.getInstance().fontRenderer.getWidth(this.text) / 2.0f, this.getRenderY() + this.getHeight() / 2.0f - SmokeXClient.getInstance().fontRenderer.getHeight(this.text) / 2.0f, this.color);
        super.render(partialTicks);
    }
}
