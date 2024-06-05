// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.gui.element.Element;

public final class RectElement extends Element
{
    private int color;
    
    public RectElement(final String identifier, final float x, final float y, final float width, final float height, final int color) {
        super(identifier, x, y, width, height);
        this.color = color;
        this.clickable = false;
    }
    
    public Element setColor(final int color) {
        this.color = color;
        return this;
    }
    
    public int getColor() {
        return this.color;
    }
    
    @Override
    public void render(final double partialTicks) {
        RenderingUtils.drawRect(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight(), this.color);
        super.render(partialTicks);
    }
}
