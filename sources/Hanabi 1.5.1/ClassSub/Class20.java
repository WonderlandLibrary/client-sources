package ClassSub;

import java.awt.font.*;
import java.awt.image.*;
import java.util.*;
import java.nio.*;
import java.awt.*;

public class Class20
{
    private static final Class311 GL;
    public static final int MAX_GLYPH_SIZE = 256;
    private static ByteBuffer scratchByteBuffer;
    private static IntBuffer scratchIntBuffer;
    private static BufferedImage scratchImage;
    private static Graphics2D scratchGraphics;
    public static FontRenderContext renderContext;
    private final Class139 unicodeFont;
    private final int pageWidth;
    private final int pageHeight;
    private final Class220 pageImage;
    private int pageX;
    private int pageY;
    private int rowHeight;
    private boolean orderAscending;
    private final List pageGlyphs;
    
    
    public static Graphics2D getScratchGraphics() {
        return Class20.scratchGraphics;
    }
    
    public Class20(final Class139 unicodeFont, final int pageWidth, final int pageHeight) throws Class341 {
        this.pageGlyphs = new ArrayList(32);
        this.unicodeFont = unicodeFont;
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
        this.pageImage = new Class220(pageWidth, pageHeight);
    }
    
    public int loadGlyphs(final List list, final int n) throws Class341 {
        if (this.rowHeight != 0 && n == -1) {
            int pageX = this.pageX;
            int pageY = this.pageY;
            int rowHeight = this.rowHeight;
            final Iterator iterator = this.getIterator(list);
            while (iterator.hasNext()) {
                final Class167 class167 = iterator.next();
                final int width = class167.getWidth();
                final int height = class167.getHeight();
                if (pageX + width >= this.pageWidth) {
                    pageX = 0;
                    pageY += rowHeight;
                    rowHeight = height;
                }
                else if (height > rowHeight) {
                    rowHeight = height;
                }
                if (pageY + rowHeight >= this.pageWidth) {
                    return 0;
                }
                pageX += width;
            }
        }
        Class26.white.bind();
        this.pageImage.bind();
        int n2 = 0;
        final Iterator iterator2 = this.getIterator(list);
        while (iterator2.hasNext()) {
            final Class167 class168 = iterator2.next();
            final int min = Math.min(256, class168.getWidth());
            final int min2 = Math.min(256, class168.getHeight());
            if (this.rowHeight == 0) {
                this.rowHeight = min2;
            }
            else if (this.pageX + min >= this.pageWidth) {
                if (this.pageY + this.rowHeight + min2 >= this.pageHeight) {
                    break;
                }
                this.pageX = 0;
                this.pageY += this.rowHeight;
                this.rowHeight = min2;
            }
            else if (min2 > this.rowHeight) {
                if (this.pageY + min2 >= this.pageHeight) {
                    break;
                }
                this.rowHeight = min2;
            }
            this.renderGlyph(class168, min, min2);
            this.pageGlyphs.add(class168);
            this.pageX += min;
            iterator2.remove();
            if (++n2 == n) {
                this.orderAscending = !this.orderAscending;
                break;
            }
        }
        Class237.bindNone();
        this.orderAscending = !this.orderAscending;
        return n2;
    }
    
    private void renderGlyph(final Class167 class167, final int n, final int n2) throws Class341 {
        Class20.scratchGraphics.setComposite(AlphaComposite.Clear);
        Class20.scratchGraphics.fillRect(0, 0, 256, 256);
        Class20.scratchGraphics.setComposite(AlphaComposite.SrcOver);
        Class20.scratchGraphics.setColor(Color.white);
        final Iterator<Class1> iterator = (Iterator<Class1>)this.unicodeFont.getEffects().iterator();
        while (iterator.hasNext()) {
            iterator.next().draw(Class20.scratchImage, Class20.scratchGraphics, this.unicodeFont, class167);
        }
        class167.setShape(null);
        final WritableRaster raster = Class20.scratchImage.getRaster();
        final int[] array = new int[n];
        for (int i = 0; i < n2; ++i) {
            raster.getDataElements(0, i, n, 1, array);
            Class20.scratchIntBuffer.put(array);
        }
        Class20.GL.glTexSubImage2D(3553, 0, this.pageX, this.pageY, n, n2, 32993, 5121, Class20.scratchByteBuffer);
        Class20.scratchIntBuffer.clear();
        class167.setImage(this.pageImage.getSubImage(this.pageX, this.pageY, n, n2));
    }
    
    private Iterator getIterator(final List list) {
        class Class216 implements Iterator
        {
            final ListIterator val$iter;
            final Class20 this$0;
            
            
            Class216(final Class20 this$0, final ListIterator val$iter) {
                this.this$0 = this$0;
                this.val$iter = val$iter;
            }
            
            @Override
            public boolean hasNext() {
                return this.val$iter.hasPrevious();
            }
            
            @Override
            public Object next() {
                return this.val$iter.previous();
            }
            
            @Override
            public void remove() {
                this.val$iter.remove();
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: aload_0        
        //     6: getfield        ClassSub/Class20.orderAscending:Z
        //     9: ifeq            23
        //    12: aload_1        
        //    13: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    18: areturn        
        //    19: nop            
        //    20: nop            
        //    21: nop            
        //    22: athrow         
        //    23: aload_1        
        //    24: aload_1        
        //    25: invokeinterface java/util/List.size:()I
        //    30: invokeinterface java/util/List.listIterator:(I)Ljava/util/ListIterator;
        //    35: astore_2       
        //    36: new             LClassSub/Class216;
        //    39: dup            
        //    40: aload_0        
        //    41: aload_2        
        //    42: invokespecial   ClassSub/Class216.<init>:(LClassSub/Class20;Ljava/util/ListIterator;)V
        //    45: areturn        
        //    46: nop            
        //    47: nop            
        //    48: nop            
        //    49: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public List getGlyphs() {
        return this.pageGlyphs;
    }
    
    public Class220 getImage() {
        return this.pageImage;
    }
    
    static {
        GL = Class197.get();
        (Class20.scratchByteBuffer = ByteBuffer.allocateDirect(262144)).order(ByteOrder.LITTLE_ENDIAN);
        Class20.scratchIntBuffer = Class20.scratchByteBuffer.asIntBuffer();
        Class20.scratchImage = new BufferedImage(256, 256, 2);
        (Class20.scratchGraphics = (Graphics2D)Class20.scratchImage.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Class20.scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Class20.scratchGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        Class20.renderContext = Class20.scratchGraphics.getFontRenderContext();
    }
}
