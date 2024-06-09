package HORIZON-6-0-SKIDPROTECTION;

import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.Rectangle;
import java.awt.Shape;

public class Glyph
{
    private int HorizonCode_Horizon_È;
    private short Â;
    private short Ý;
    private short Ø­áŒŠá;
    private boolean Âµá€;
    private Shape Ó;
    private Image à;
    
    public Glyph(final int codePoint, final Rectangle bounds, final GlyphVector vector, final int index, final UnicodeFont unicodeFont) {
        this.HorizonCode_Horizon_È = codePoint;
        final GlyphMetrics metrics = vector.getGlyphMetrics(index);
        int lsb = (int)metrics.getLSB();
        if (lsb > 0) {
            lsb = 0;
        }
        int rsb = (int)metrics.getRSB();
        if (rsb > 0) {
            rsb = 0;
        }
        final int glyphWidth = bounds.width - lsb - rsb;
        final int glyphHeight = bounds.height;
        if (glyphWidth > 0 && glyphHeight > 0) {
            final int padTop = unicodeFont.áŒŠÆ();
            final int padRight = unicodeFont.á();
            final int padBottom = unicodeFont.ÂµÈ();
            final int padLeft = unicodeFont.áˆºÑ¢Õ();
            final int glyphSpacing = 1;
            this.Â = (short)(glyphWidth + padLeft + padRight + glyphSpacing);
            this.Ý = (short)(glyphHeight + padTop + padBottom + glyphSpacing);
            this.Ø­áŒŠá = (short)(unicodeFont.Å() + bounds.y - padTop);
        }
        this.Ó = vector.getGlyphOutline(index, -bounds.x + unicodeFont.áˆºÑ¢Õ(), -bounds.y + unicodeFont.áŒŠÆ());
        this.Âµá€ = !unicodeFont.Ø().canDisplay((char)codePoint);
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public Shape Âµá€() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final Shape shape) {
        this.Ó = shape;
    }
    
    public Image Ó() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final Image image) {
        this.à = image;
    }
    
    public int à() {
        return this.Ø­áŒŠá;
    }
}
