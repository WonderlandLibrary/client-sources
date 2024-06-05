package ClassSub;

import java.io.*;
import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.lang.reflect.*;

public class Class139 implements Class297
{
    private static final int DISPLAY_LIST_CACHE_SIZE = 200;
    private static final int MAX_GLYPH_CODE = 1114111;
    private static final int PAGE_SIZE = 512;
    private static final int PAGES = 2175;
    private static final Class311 GL;
    private static final Class60 EMPTY_DISPLAY_LIST;
    private static final Comparator heightComparator;
    private Font font;
    private String ttfFileRef;
    private int ascent;
    private int descent;
    private int leading;
    private int spaceWidth;
    private final Class167[][] glyphs;
    private final List glyphPages;
    private final List queuedGlyphs;
    private final List effects;
    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;
    private int paddingAdvanceX;
    private int paddingAdvanceY;
    private Class167 missingGlyph;
    private int glyphPageWidth;
    private int glyphPageHeight;
    private boolean displayListCaching;
    private int baseDisplayListID;
    private int eldestDisplayListID;
    private Class60 eldestDisplayList;
    private final LinkedHashMap displayLists;
    
    
    private static Font createFont(final String s) throws Class341 {
        try {
            return Font.createFont(0, Class337.getResourceAsStream(s));
        }
        catch (FontFormatException ex) {
            throw new Class341("Invalid font: " + s, ex);
        }
        catch (IOException ex2) {
            throw new Class341("Error reading font: " + s, ex2);
        }
    }
    
    public Class139(final String s, final String s2) throws Class341 {
        this(s, new Class258(s2));
    }
    
    public Class139(final String ttfFileRef, final Class258 class258) throws Class341 {
        this.glyphs = new Class167[2175][];
        this.glyphPages = new ArrayList();
        this.queuedGlyphs = new ArrayList(256);
        this.effects = new ArrayList();
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class276(this, 200, 1.0f, true);
        this.ttfFileRef = ttfFileRef;
        this.initializeFont(createFont(ttfFileRef), class258.getFontSize(), class258.isBold(), class258.isItalic());
        this.loadSettings(class258);
    }
    
    public Class139(final String ttfFileRef, final int n, final boolean b, final boolean b2) throws Class341 {
        this.glyphs = new Class167[2175][];
        this.glyphPages = new ArrayList();
        this.queuedGlyphs = new ArrayList(256);
        this.effects = new ArrayList();
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class276(this, 200, 1.0f, true);
        this.ttfFileRef = ttfFileRef;
        this.initializeFont(createFont(ttfFileRef), n, b, b2);
    }
    
    public Class139(final Font font, final String s) throws Class341 {
        this(font, new Class258(s));
    }
    
    public Class139(final Font font, final Class258 class258) {
        this.glyphs = new Class167[2175][];
        this.glyphPages = new ArrayList();
        this.queuedGlyphs = new ArrayList(256);
        this.effects = new ArrayList();
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class276(this, 200, 1.0f, true);
        this.initializeFont(font, class258.getFontSize(), class258.isBold(), class258.isItalic());
        this.loadSettings(class258);
    }
    
    public Class139(final Font font) {
        this.glyphs = new Class167[2175][];
        this.glyphPages = new ArrayList();
        this.queuedGlyphs = new ArrayList(256);
        this.effects = new ArrayList();
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class276(this, 200, 1.0f, true);
        this.initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
    }
    
    public Class139(final Font font, final int n, final boolean b, final boolean b2) {
        this.glyphs = new Class167[2175][];
        this.glyphPages = new ArrayList();
        this.queuedGlyphs = new ArrayList(256);
        this.effects = new ArrayList();
        this.glyphPageWidth = 512;
        this.glyphPageHeight = 512;
        this.displayListCaching = true;
        this.baseDisplayListID = -1;
        this.displayLists = new Class276(this, 200, 1.0f, true);
        this.initializeFont(font, n, b, b2);
    }
    
    private void initializeFont(final Font font, final int n, final boolean b, final boolean b2) {
        final Map<TextAttribute, ?> attributes = font.getAttributes();
        attributes.put(TextAttribute.SIZE, new Float(n));
        attributes.put(TextAttribute.WEIGHT, b ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
        attributes.put(TextAttribute.POSTURE, b2 ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
        try {
            attributes.put((TextAttribute)TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField("KERNING_ON").get(null));
        }
        catch (Exception ex) {}
        this.font = font.deriveFont(attributes);
        final FontMetrics fontMetrics = Class20.getScratchGraphics().getFontMetrics(this.font);
        this.ascent = fontMetrics.getAscent();
        this.descent = fontMetrics.getDescent();
        this.leading = fontMetrics.getLeading();
        final char[] array = " ".toCharArray();
        this.spaceWidth = this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0).getGlyphLogicalBounds(0).getBounds().width;
    }
    
    private void loadSettings(final Class258 class258) {
        this.paddingTop = class258.getPaddingTop();
        this.paddingLeft = class258.getPaddingLeft();
        this.paddingBottom = class258.getPaddingBottom();
        this.paddingRight = class258.getPaddingRight();
        this.paddingAdvanceX = class258.getPaddingAdvanceX();
        this.paddingAdvanceY = class258.getPaddingAdvanceY();
        this.glyphPageWidth = class258.getGlyphPageWidth();
        this.glyphPageHeight = class258.getGlyphPageHeight();
        this.effects.addAll(class258.getEffects());
    }
    
    public void addGlyphs(final int n, final int n2) {
        for (int i = n; i <= n2; ++i) {
            this.addGlyphs(new String(Character.toChars(i)));
        }
    }
    
    public void addGlyphs(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        final char[] array = s.toCharArray();
        final GlyphVector layoutGlyphVector = this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0);
        for (int i = 0; i < layoutGlyphVector.getNumGlyphs(); ++i) {
            final int codePoint = s.codePointAt(layoutGlyphVector.getGlyphCharIndex(i));
            this.getGlyph(layoutGlyphVector.getGlyphCode(i), codePoint, this.getGlyphBounds(layoutGlyphVector, i, codePoint), layoutGlyphVector, i);
        }
    }
    
    public void addAsciiGlyphs() {
        this.addGlyphs(32, 255);
    }
    
    public void addNeheGlyphs() {
        this.addGlyphs(32, 128);
    }
    
    public boolean loadGlyphs() throws Class341 {
        return this.loadGlyphs(-1);
    }
    
    public boolean loadGlyphs(int n) throws Class341 {
        if (this.queuedGlyphs.isEmpty()) {
            return false;
        }
        if (this.effects.isEmpty()) {
            throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
        }
        final Iterator<Class167> iterator = (Iterator<Class167>)this.queuedGlyphs.iterator();
        while (iterator.hasNext()) {
            final Class167 missingGlyph = iterator.next();
            final int codePoint = missingGlyph.getCodePoint();
            if (missingGlyph.getWidth() == 0 || codePoint == 32) {
                iterator.remove();
            }
            else {
                if (!missingGlyph.isMissing()) {
                    continue;
                }
                if (this.missingGlyph != null) {
                    if (missingGlyph == this.missingGlyph) {
                        continue;
                    }
                    iterator.remove();
                }
                else {
                    this.missingGlyph = missingGlyph;
                }
            }
        }
        Collections.sort((List<Object>)this.queuedGlyphs, Class139.heightComparator);
        final Iterator<Class20> iterator2 = this.glyphPages.iterator();
        while (iterator2.hasNext()) {
            n -= iterator2.next().loadGlyphs(this.queuedGlyphs, n);
            if (n == 0 || this.queuedGlyphs.isEmpty()) {
                return true;
            }
        }
        while (!this.queuedGlyphs.isEmpty()) {
            final Class20 class20 = new Class20(this, this.glyphPageWidth, this.glyphPageHeight);
            this.glyphPages.add(class20);
            n -= class20.loadGlyphs(this.queuedGlyphs, n);
            if (n == 0) {
                return true;
            }
        }
        return true;
    }
    
    public void clearGlyphs() {
        for (int i = 0; i < 2175; ++i) {
            this.glyphs[i] = null;
        }
        for (final Class20 class20 : this.glyphPages) {
            try {
                class20.getImage().destroy();
            }
            catch (Class341 class21) {}
        }
        this.glyphPages.clear();
        if (this.baseDisplayListID != -1) {
            Class139.GL.glDeleteLists(this.baseDisplayListID, this.displayLists.size());
            this.baseDisplayListID = -1;
        }
        this.queuedGlyphs.clear();
        this.missingGlyph = null;
    }
    
    public void destroy() {
        this.clearGlyphs();
    }
    
    public Class60 drawDisplayList(float n, float n2, final String s, final Class26 class26, final int n3, final int n4) {
        if (s == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (s.length() == 0) {
            return Class139.EMPTY_DISPLAY_LIST;
        }
        if (class26 == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        n -= this.paddingLeft;
        n2 -= this.paddingTop;
        final String substring = s.substring(n3, n4);
        class26.bind();
        Class237.bindNone();
        Class60 class27 = null;
        if (this.displayListCaching && this.queuedGlyphs.isEmpty()) {
            if (this.baseDisplayListID == -1) {
                this.baseDisplayListID = Class139.GL.glGenLists(200);
                if (this.baseDisplayListID == 0) {
                    this.baseDisplayListID = -1;
                    this.displayListCaching = false;
                    return new Class60();
                }
            }
            class27 = this.displayLists.get(substring);
            if (class27 != null) {
                if (!class27.invalid) {
                    Class139.GL.glTranslatef(n, n2, 0.0f);
                    Class139.GL.glCallList(class27.id);
                    Class139.GL.glTranslatef(-n, -n2, 0.0f);
                    return class27;
                }
                class27.invalid = false;
            }
            else if (class27 == null) {
                class27 = new Class60();
                final int size = this.displayLists.size();
                this.displayLists.put(substring, class27);
                if (size < 200) {
                    class27.id = this.baseDisplayListID + size;
                }
                else {
                    class27.id = this.eldestDisplayListID;
                }
            }
            this.displayLists.put(substring, class27);
        }
        Class139.GL.glTranslatef(n, n2, 0.0f);
        if (class27 != null) {
            Class139.GL.glNewList(class27.id, 4865);
        }
        final char[] array = s.substring(0, n4).toCharArray();
        final GlyphVector layoutGlyphVector = this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0);
        int max = 0;
        int max2 = 0;
        int n5 = 0;
        int n6 = 0;
        int ascent = this.ascent;
        int n7 = 0;
        Class282 class28 = null;
        for (int i = 0; i < layoutGlyphVector.getNumGlyphs(); ++i) {
            final int glyphCharIndex = layoutGlyphVector.getGlyphCharIndex(i);
            if (glyphCharIndex >= n3) {
                if (glyphCharIndex > n4) {
                    break;
                }
                final int codePoint = s.codePointAt(glyphCharIndex);
                final Rectangle glyphBounds = this.getGlyphBounds(layoutGlyphVector, i, codePoint);
                final Class167 glyph = this.getGlyph(layoutGlyphVector.getGlyphCode(i), codePoint, glyphBounds, layoutGlyphVector, i);
                if (n7 != 0 && codePoint != 10) {
                    n6 = -glyphBounds.x;
                    n7 = 0;
                }
                Class220 class29 = glyph.getImage();
                if (class29 == null && this.missingGlyph != null && glyph.isMissing()) {
                    class29 = this.missingGlyph.getImage();
                }
                if (class29 != null) {
                    final Class282 texture = class29.getTexture();
                    if (class28 != null && class28 != texture) {
                        Class139.GL.glEnd();
                        class28 = null;
                    }
                    if (class28 == null) {
                        texture.bind();
                        Class139.GL.glBegin(7);
                        class28 = texture;
                    }
                    class29.drawEmbedded(glyphBounds.x + n6, glyphBounds.y + ascent, class29.getWidth(), class29.getHeight());
                }
                if (i >= 0) {
                    n6 += this.paddingRight + this.paddingLeft + this.paddingAdvanceX;
                }
                max = Math.max(max, glyphBounds.x + n6 + glyphBounds.width);
                max2 = Math.max(max2, this.ascent + glyphBounds.y + glyphBounds.height);
                if (codePoint == 10) {
                    n7 = 1;
                    ascent += this.getLineHeight();
                    ++n5;
                    max2 = 0;
                }
            }
        }
        if (class28 != null) {
            Class139.GL.glEnd();
        }
        if (class27 != null) {
            Class139.GL.glEndList();
            if (!this.queuedGlyphs.isEmpty()) {
                class27.invalid = true;
            }
        }
        Class139.GL.glTranslatef(-n, -n2, 0.0f);
        if (class27 == null) {
            class27 = new Class60();
        }
        class27.width = (short)max;
        class27.height = (short)(n5 * this.getLineHeight() + max2);
        return class27;
    }
    
    @Override
    public void drawString(final float n, final float n2, final String s, final Class26 class26, final int n3, final int n4) {
        this.drawDisplayList(n, n2, s, class26, n3, n4);
    }
    
    @Override
    public void drawString(final float n, final float n2, final String s) {
        this.drawString(n, n2, s, Class26.white);
    }
    
    @Override
    public void drawString(final float n, final float n2, final String s, final Class26 class26) {
        this.drawString(n, n2, s, class26, 0, s.length());
    }
    
    private Class167 getGlyph(final int n, final int n2, final Rectangle rectangle, final GlyphVector glyphVector, final int n3) {
        class Class256 extends Class167
        {
            final Class139 this$0;
            
            
            Class256(final Class139 this$0, final int n, final Rectangle rectangle, final GlyphVector glyphVector, final int n2, final Class139 class139) {
                this.this$0 = this$0;
                super(n, rectangle, glyphVector, n2, class139);
            }
            
            @Override
            public boolean isMissing() {
                return true;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: iload_1        
        //     6: iflt            15
        //     9: iload_1        
        //    10: ldc             1114111
        //    12: if_icmplt       36
        //    15: new             LClassSub/Class256;
        //    18: dup            
        //    19: aload_0        
        //    20: iload_2        
        //    21: aload_3        
        //    22: aload           4
        //    24: iload           5
        //    26: aload_0        
        //    27: invokespecial   ClassSub/Class256.<init>:(LClassSub/Class139;ILjava/awt/Rectangle;Ljava/awt/font/GlyphVector;ILClassSub/Class139;)V
        //    30: areturn        
        //    31: nop            
        //    32: nop            
        //    33: nop            
        //    34: nop            
        //    35: athrow         
        //    36: iload_1        
        //    37: ldc             512
        //    39: idiv           
        //    40: istore          6
        //    42: iload_1        
        //    43: ldc_w           511
        //    46: iand           
        //    47: istore          7
        //    49: aconst_null    
        //    50: astore          8
        //    52: aload_0        
        //    53: getfield        ClassSub/Class139.glyphs:[[LClassSub/Class167;
        //    56: iload           6
        //    58: aaload         
        //    59: astore          9
        //    61: aload           9
        //    63: ifnull          86
        //    66: aload           9
        //    68: iload           7
        //    70: aaload         
        //    71: astore          8
        //    73: aload           8
        //    75: ifnull          101
        //    78: aload           8
        //    80: areturn        
        //    81: nop            
        //    82: nop            
        //    83: nop            
        //    84: nop            
        //    85: athrow         
        //    86: aload_0        
        //    87: getfield        ClassSub/Class139.glyphs:[[LClassSub/Class167;
        //    90: iload           6
        //    92: ldc             512
        //    94: anewarray       LClassSub/Class167;
        //    97: dup_x2         
        //    98: aastore        
        //    99: astore          9
        //   101: aload           9
        //   103: iload           7
        //   105: new             LClassSub/Class167;
        //   108: dup            
        //   109: iload_2        
        //   110: aload_3        
        //   111: aload           4
        //   113: iload           5
        //   115: aload_0        
        //   116: invokespecial   ClassSub/Class167.<init>:(ILjava/awt/Rectangle;Ljava/awt/font/GlyphVector;ILClassSub/Class139;)V
        //   119: dup_x2         
        //   120: aastore        
        //   121: astore          8
        //   123: aload_0        
        //   124: getfield        ClassSub/Class139.queuedGlyphs:Ljava/util/List;
        //   127: aload           8
        //   129: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   134: pop            
        //   135: aload           8
        //   137: areturn        
        //   138: nop            
        //   139: nop            
        //   140: nop            
        //   141: nop            
        //   142: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Rectangle getGlyphBounds(final GlyphVector glyphVector, final int n, final int n2) {
        final Rectangle glyphPixelBounds = glyphVector.getGlyphPixelBounds(n, Class20.renderContext, 0.0f, 0.0f);
        if (n2 == 32) {
            glyphPixelBounds.width = this.spaceWidth;
        }
        return glyphPixelBounds;
    }
    
    public int getSpaceWidth() {
        return this.spaceWidth;
    }
    
    @Override
    public int getWidth(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (s.length() == 0) {
            return 0;
        }
        if (this.displayListCaching) {
            final Class60 class60 = this.displayLists.get(s);
            if (class60 != null) {
                return class60.width;
            }
        }
        final char[] array = s.toCharArray();
        final GlyphVector layoutGlyphVector = this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0);
        int max = 0;
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < layoutGlyphVector.getNumGlyphs(); ++i) {
            final int codePoint = s.codePointAt(layoutGlyphVector.getGlyphCharIndex(i));
            final Rectangle glyphBounds = this.getGlyphBounds(layoutGlyphVector, i, codePoint);
            if (n2 != 0 && codePoint != 10) {
                n = -glyphBounds.x;
            }
            if (i > 0) {
                n += this.paddingLeft + this.paddingRight + this.paddingAdvanceX;
            }
            max = Math.max(max, glyphBounds.x + n + glyphBounds.width);
            if (codePoint == 10) {
                n2 = 1;
            }
        }
        return max;
    }
    
    @Override
    public int getHeight(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (s.length() == 0) {
            return 0;
        }
        if (this.displayListCaching) {
            final Class60 class60 = this.displayLists.get(s);
            if (class60 != null) {
                return class60.height;
            }
        }
        final char[] array = s.toCharArray();
        final GlyphVector layoutGlyphVector = this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0);
        int n = 0;
        int max = 0;
        for (int i = 0; i < layoutGlyphVector.getNumGlyphs(); ++i) {
            final int codePoint = s.codePointAt(layoutGlyphVector.getGlyphCharIndex(i));
            if (codePoint != 32) {
                final Rectangle glyphBounds = this.getGlyphBounds(layoutGlyphVector, i, codePoint);
                max = Math.max(max, this.ascent + glyphBounds.y + glyphBounds.height);
                if (codePoint == 10) {
                    ++n;
                    max = 0;
                }
            }
        }
        return n * this.getLineHeight() + max;
    }
    
    public int getYOffset(String substring) {
        if (substring == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        Class60 class60 = null;
        if (this.displayListCaching) {
            class60 = this.displayLists.get(substring);
            if (class60 != null && class60.yOffset != null) {
                return class60.yOffset;
            }
        }
        final int index = substring.indexOf(10);
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        final char[] array = substring.toCharArray();
        final int n = this.ascent + this.font.layoutGlyphVector(Class20.renderContext, array, 0, array.length, 0).getPixelBounds(null, 0.0f, 0.0f).y;
        if (class60 != null) {
            class60.yOffset = new Short((short)n);
        }
        return n;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public int getPaddingTop() {
        return this.paddingTop;
    }
    
    public void setPaddingTop(final int paddingTop) {
        this.paddingTop = paddingTop;
    }
    
    public int getPaddingLeft() {
        return this.paddingLeft;
    }
    
    public void setPaddingLeft(final int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }
    
    public int getPaddingBottom() {
        return this.paddingBottom;
    }
    
    public void setPaddingBottom(final int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }
    
    public int getPaddingRight() {
        return this.paddingRight;
    }
    
    public void setPaddingRight(final int paddingRight) {
        this.paddingRight = paddingRight;
    }
    
    public int getPaddingAdvanceX() {
        return this.paddingAdvanceX;
    }
    
    public void setPaddingAdvanceX(final int paddingAdvanceX) {
        this.paddingAdvanceX = paddingAdvanceX;
    }
    
    public int getPaddingAdvanceY() {
        return this.paddingAdvanceY;
    }
    
    public void setPaddingAdvanceY(final int paddingAdvanceY) {
        this.paddingAdvanceY = paddingAdvanceY;
    }
    
    @Override
    public int getLineHeight() {
        return this.descent + this.ascent + this.leading + this.paddingTop + this.paddingBottom + this.paddingAdvanceY;
    }
    
    public int getAscent() {
        return this.ascent;
    }
    
    public int getDescent() {
        return this.descent;
    }
    
    public int getLeading() {
        return this.leading;
    }
    
    public int getGlyphPageWidth() {
        return this.glyphPageWidth;
    }
    
    public void setGlyphPageWidth(final int glyphPageWidth) {
        this.glyphPageWidth = glyphPageWidth;
    }
    
    public int getGlyphPageHeight() {
        return this.glyphPageHeight;
    }
    
    public void setGlyphPageHeight(final int glyphPageHeight) {
        this.glyphPageHeight = glyphPageHeight;
    }
    
    public List getGlyphPages() {
        return this.glyphPages;
    }
    
    public List getEffects() {
        return this.effects;
    }
    
    public boolean isCaching() {
        return this.displayListCaching;
    }
    
    public void setDisplayListCaching(final boolean displayListCaching) {
        this.displayListCaching = displayListCaching;
    }
    
    public String getFontFile() {
        if (this.ttfFileRef == null) {
            try {
                final Object invoke = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", Font.class).invoke(null, this.font);
                final Field declaredField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
                declaredField.setAccessible(true);
                this.ttfFileRef = (String)declaredField.get(invoke);
            }
            catch (Throwable t) {}
            if (this.ttfFileRef == null) {
                this.ttfFileRef = "";
            }
        }
        if (this.ttfFileRef.length() == 0) {
            return null;
        }
        return this.ttfFileRef;
    }
    
    static int access$002(final Class139 class139, final int eldestDisplayListID) {
        return class139.eldestDisplayListID = eldestDisplayListID;
    }
    
    static {
        GL = Class197.get();
        EMPTY_DISPLAY_LIST = new Class60();
        heightComparator = new Class239();
    }
    
    public static class Class60
    {
        boolean invalid;
        int id;
        Short yOffset;
        public short width;
        public short height;
        public Object userData;
        
    }
}
