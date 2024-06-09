package HORIZON-6-0-SKIDPROTECTION;

import java.util.ListIterator;
import java.awt.image.WritableRaster;
import java.awt.Shape;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.RenderingHints;
import java.nio.ByteOrder;
import java.util.List;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class GlyphPage
{
    private static final SGL Ý;
    public static final int HorizonCode_Horizon_È = 256;
    private static ByteBuffer Ø­áŒŠá;
    private static IntBuffer Âµá€;
    private static BufferedImage Ó;
    private static Graphics2D à;
    public static FontRenderContext Â;
    private final UnicodeFont Ø;
    private final int áŒŠÆ;
    private final int áˆºÑ¢Õ;
    private final Image ÂµÈ;
    private int á;
    private int ˆÏ­;
    private int £á;
    private boolean Å;
    private final List £à;
    
    static {
        Ý = Renderer.HorizonCode_Horizon_È();
        (GlyphPage.Ø­áŒŠá = ByteBuffer.allocateDirect(262144)).order(ByteOrder.LITTLE_ENDIAN);
        GlyphPage.Âµá€ = GlyphPage.Ø­áŒŠá.asIntBuffer();
        GlyphPage.Ó = new BufferedImage(256, 256, 2);
        (GlyphPage.à = (Graphics2D)GlyphPage.Ó.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GlyphPage.à.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        GlyphPage.à.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        GlyphPage.Â = GlyphPage.à.getFontRenderContext();
    }
    
    public static Graphics2D HorizonCode_Horizon_È() {
        return GlyphPage.à;
    }
    
    public GlyphPage(final UnicodeFont unicodeFont, final int pageWidth, final int pageHeight) throws SlickException {
        this.£à = new ArrayList(32);
        this.Ø = unicodeFont;
        this.áŒŠÆ = pageWidth;
        this.áˆºÑ¢Õ = pageHeight;
        this.ÂµÈ = new Image(pageWidth, pageHeight);
    }
    
    public int HorizonCode_Horizon_È(final List glyphs, final int maxGlyphsToLoad) throws SlickException {
        if (this.£á != 0 && maxGlyphsToLoad == -1) {
            int testX = this.á;
            int testY = this.ˆÏ­;
            int testRowHeight = this.£á;
            final Iterator iter = this.HorizonCode_Horizon_È(glyphs);
            while (iter.hasNext()) {
                final Glyph glyph = iter.next();
                final int width = glyph.Ý();
                final int height = glyph.Ø­áŒŠá();
                if (testX + width >= this.áŒŠÆ) {
                    testX = 0;
                    testY += testRowHeight;
                    testRowHeight = height;
                }
                else if (height > testRowHeight) {
                    testRowHeight = height;
                }
                if (testY + testRowHeight >= this.áŒŠÆ) {
                    return 0;
                }
                testX += width;
            }
        }
        Color.Ý.HorizonCode_Horizon_È();
        this.ÂµÈ.Â();
        int i = 0;
        final Iterator iter2 = this.HorizonCode_Horizon_È(glyphs);
        while (iter2.hasNext()) {
            final Glyph glyph2 = iter2.next();
            final int width2 = Math.min(256, glyph2.Ý());
            final int height2 = Math.min(256, glyph2.Ø­áŒŠá());
            if (this.£á == 0) {
                this.£á = height2;
            }
            else if (this.á + width2 >= this.áŒŠÆ) {
                if (this.ˆÏ­ + this.£á + height2 >= this.áˆºÑ¢Õ) {
                    break;
                }
                this.á = 0;
                this.ˆÏ­ += this.£á;
                this.£á = height2;
            }
            else if (height2 > this.£á) {
                if (this.ˆÏ­ + height2 >= this.áˆºÑ¢Õ) {
                    break;
                }
                this.£á = height2;
            }
            this.HorizonCode_Horizon_È(glyph2, width2, height2);
            this.£à.add(glyph2);
            this.á += width2;
            iter2.remove();
            if (++i == maxGlyphsToLoad) {
                this.Å = !this.Å;
                break;
            }
        }
        TextureImpl.£à();
        this.Å = !this.Å;
        return i;
    }
    
    private void HorizonCode_Horizon_È(final Glyph glyph, final int width, final int height) throws SlickException {
        GlyphPage.à.setComposite(AlphaComposite.Clear);
        GlyphPage.à.fillRect(0, 0, 256, 256);
        GlyphPage.à.setComposite(AlphaComposite.SrcOver);
        GlyphPage.à.setColor(java.awt.Color.white);
        final Iterator iter = this.Ø.µÕ().iterator();
        while (iter.hasNext()) {
            iter.next().HorizonCode_Horizon_È(GlyphPage.Ó, GlyphPage.à, this.Ø, glyph);
        }
        glyph.HorizonCode_Horizon_È((Shape)null);
        final WritableRaster raster = GlyphPage.Ó.getRaster();
        final int[] row = new int[width];
        for (int y = 0; y < height; ++y) {
            raster.getDataElements(0, y, width, 1, row);
            GlyphPage.Âµá€.put(row);
        }
        GlyphPage.Ý.Â(3553, 0, this.á, this.ˆÏ­, width, height, 32993, 5121, GlyphPage.Ø­áŒŠá);
        GlyphPage.Âµá€.clear();
        glyph.HorizonCode_Horizon_È(this.ÂµÈ.HorizonCode_Horizon_È(this.á, this.ˆÏ­, width, height));
    }
    
    private Iterator HorizonCode_Horizon_È(final List glyphs) {
        if (this.Å) {
            return glyphs.iterator();
        }
        final ListIterator iter = glyphs.listIterator(glyphs.size());
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return iter.hasPrevious();
            }
            
            @Override
            public Object next() {
                return iter.previous();
            }
            
            @Override
            public void remove() {
                iter.remove();
            }
        };
    }
    
    public List Â() {
        return this.£à;
    }
    
    public Image Ý() {
        return this.ÂµÈ;
    }
}
