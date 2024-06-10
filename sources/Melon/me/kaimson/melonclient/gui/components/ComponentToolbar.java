package me.kaimson.melonclient.gui.components;

import me.kaimson.melonclient.gui.elements.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public class ComponentToolbar extends Component
{
    private final LayoutBuilder top;
    private final LayoutBuilder bottom;
    private final Layout layout;
    private int elementOffsetX;
    private int elementOffsetY;
    private int topIndex;
    private int bottomIndex;
    
    public ComponentToolbar(final int x, final int y, final int width, final int height, final int gap, final int size, final Layout layout) {
        super(x, y, width - x, height - y);
        this.top = new LayoutBuilder((layout == Layout.HORIZONTAL) ? x : y, gap, size);
        this.bottom = new LayoutBuilder((layout == Layout.HORIZONTAL) ? (x + this.width - size) : (y + this.height - size), gap, size).reverse();
        this.layout = layout;
        this.topIndex = 0;
        this.bottomIndex = 0;
    }
    
    public ComponentToolbar setOffset(final int elementOffsetX, final int elementOffsetY) {
        this.elementOffsetX = elementOffsetX;
        this.elementOffsetY = elementOffsetY;
        return this;
    }
    
    public ComponentToolbar required(final Element element, final Position position) {
        this.elements.add(element);
        final boolean horizontal = position == Position.POSITIVE;
        if (this.layout == Layout.HORIZONTAL) {
            element.x = (horizontal ? (this.top.getCoordinateForIndex(this.topIndex++) + this.elementOffsetX) : (this.bottom.getCoordinateForIndex(this.bottomIndex++) - this.elementOffsetX));
            element.y = this.y + (horizontal ? this.elementOffsetY : (-this.elementOffsetY));
        }
        else {
            element.x = this.x + this.elementOffsetX;
            element.y = (horizontal ? (this.top.getCoordinateForIndex(this.topIndex++) + this.elementOffsetY) : (this.bottom.getCoordinateForIndex(this.bottomIndex++) - this.elementOffsetY));
        }
        return this;
    }
    
    public ComponentToolbar optional(final Element element, final Position position, final boolean enabled) {
        element.enabled = enabled;
        return this.required(element, position);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 3.0f, new Color(0, 0, 0, 140).getRGB());
    }
    
    public enum Layout
    {
        HORIZONTAL, 
        VERTICAL;
    }
    
    public enum Position
    {
        POSITIVE, 
        NEGATIVE;
    }
}
