// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import gg.childtrafficking.smokex.gui.element.Element;

public final class GradientRectElement extends Element
{
    private int color;
    private int secondaryColor;
    private boolean sideways;
    
    public GradientRectElement(final String identifier, final float x, final float y, final float width, final float height, final boolean sideways, final int color, final int secondaryColor) {
        super(identifier, x, y, width, height);
        this.sideways = sideways;
        this.color = color;
        this.secondaryColor = secondaryColor;
        this.clickable = false;
    }
    
    public Element setColor(final int color, final int secondaryColor) {
        this.color = color;
        this.secondaryColor = secondaryColor;
        return this;
    }
    
    public int[] getColor() {
        return new int[] { this.color, this.secondaryColor };
    }
    
    @Override
    public void render(final double partialTicks) {
        RenderingUtils.drawGradientRect(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight(), this.sideways, this.color, this.secondaryColor);
        super.render(partialTicks);
    }
}
