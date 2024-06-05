package ClassSub;

import java.awt.*;
import java.awt.font.*;

public class Class167
{
    private int codePoint;
    private short width;
    private short height;
    private short yOffset;
    private boolean isMissing;
    private Shape shape;
    private Class220 image;
    
    
    public Class167(final int codePoint, final Rectangle rectangle, final GlyphVector glyphVector, final int n, final Class139 class139) {
        this.codePoint = codePoint;
        final GlyphMetrics glyphMetrics = glyphVector.getGlyphMetrics(n);
        int n2 = (int)glyphMetrics.getLSB();
        if (n2 > 0) {
            n2 = 0;
        }
        int n3 = (int)glyphMetrics.getRSB();
        if (n3 > 0) {
            n3 = 0;
        }
        final int n4 = rectangle.width - n2 - n3;
        final int height = rectangle.height;
        if (n4 > 0 && height > 0) {
            final int paddingTop = class139.getPaddingTop();
            final int paddingRight = class139.getPaddingRight();
            final int paddingBottom = class139.getPaddingBottom();
            final int paddingLeft = class139.getPaddingLeft();
            final int n5 = 1;
            this.width = (short)(n4 + paddingLeft + paddingRight + n5);
            this.height = (short)(height + paddingTop + paddingBottom + n5);
            this.yOffset = (short)(class139.getAscent() + rectangle.y - paddingTop);
        }
        this.shape = glyphVector.getGlyphOutline(n, -rectangle.x + class139.getPaddingLeft(), -rectangle.y + class139.getPaddingTop());
        this.isMissing = !class139.getFont().canDisplay((char)codePoint);
    }
    
    public int getCodePoint() {
        return this.codePoint;
    }
    
    public boolean isMissing() {
        return this.isMissing;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Shape getShape() {
        return this.shape;
    }
    
    public void setShape(final Shape shape) {
        this.shape = shape;
    }
    
    public Class220 getImage() {
        return this.image;
    }
    
    public void setImage(final Class220 image) {
        this.image = image;
    }
    
    public int getYOffset() {
        return this.yOffset;
    }
}
