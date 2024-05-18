package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Field;
import java.awt.font.FontRenderContext;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Rectangle;
import java.util.Collection;
import java.awt.font.GlyphVector;
import java.awt.FontMetrics;
import java.text.AttributedCharacterIterator;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Comparator;

public class UnicodeFont implements Font
{
    private static final int HorizonCode_Horizon_È = 200;
    private static final int Â = 1114111;
    private static final int Ý = 512;
    private static final int Ø­áŒŠá = 2175;
    private static final SGL Âµá€;
    private static final HorizonCode_Horizon_È Ó;
    private static final Comparator à;
    private java.awt.Font Ø;
    private String áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private int ˆÏ­;
    private final Glyph[][] £á;
    private final List Å;
    private final List £à;
    private final List µà;
    private int ˆà;
    private int ¥Æ;
    private int Ø­à;
    private int µÕ;
    private int Æ;
    private int Šáƒ;
    private Glyph Ï­Ðƒà;
    private int áŒŠà;
    private int ŠÄ;
    private boolean Ñ¢á;
    private int ŒÏ;
    private int Çªà¢;
    private HorizonCode_Horizon_È Ê;
    private final LinkedHashMap ÇŽÉ;
    
    static {
        Âµá€ = Renderer.HorizonCode_Horizon_È();
        Ó = new HorizonCode_Horizon_È();
        à = new Comparator() {
            @Override
            public int compare(final Object o1, final Object o2) {
                return ((Glyph)o1).Ø­áŒŠá() - ((Glyph)o2).Ø­áŒŠá();
            }
        };
    }
    
    private static java.awt.Font Âµá€(final String ttfFileRef) throws SlickException {
        try {
            return java.awt.Font.createFont(0, ResourceLoader.HorizonCode_Horizon_È(ttfFileRef));
        }
        catch (FontFormatException ex) {
            throw new SlickException("Invalid font: " + ttfFileRef, ex);
        }
        catch (IOException ex2) {
            throw new SlickException("Error reading font: " + ttfFileRef, ex2);
        }
    }
    
    public UnicodeFont(final String ttfFileRef, final String hieroFileRef) throws SlickException {
        this(ttfFileRef, new HieroSettings(hieroFileRef));
    }
    
    public UnicodeFont(final String ttfFileRef, final HieroSettings settings) throws SlickException {
        this.£á = new Glyph[2175][];
        this.Å = new ArrayList();
        this.£à = new ArrayList(256);
        this.µà = new ArrayList();
        this.áŒŠà = 512;
        this.ŠÄ = 512;
        this.Ñ¢á = true;
        this.ŒÏ = -1;
        this.ÇŽÉ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                final HorizonCode_Horizon_È displayList = eldest.getValue();
                if (displayList != null) {
                    UnicodeFont.HorizonCode_Horizon_È(UnicodeFont.this, displayList.Â);
                }
                return this.size() > 200;
            }
        };
        this.áŒŠÆ = ttfFileRef;
        final java.awt.Font font = Âµá€(ttfFileRef);
        this.HorizonCode_Horizon_È(font, settings.áŒŠÆ(), settings.áˆºÑ¢Õ(), settings.ÂµÈ());
        this.HorizonCode_Horizon_È(settings);
    }
    
    public UnicodeFont(final String ttfFileRef, final int size, final boolean bold, final boolean italic) throws SlickException {
        this.£á = new Glyph[2175][];
        this.Å = new ArrayList();
        this.£à = new ArrayList(256);
        this.µà = new ArrayList();
        this.áŒŠà = 512;
        this.ŠÄ = 512;
        this.Ñ¢á = true;
        this.ŒÏ = -1;
        this.ÇŽÉ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                final HorizonCode_Horizon_È displayList = eldest.getValue();
                if (displayList != null) {
                    UnicodeFont.HorizonCode_Horizon_È(UnicodeFont.this, displayList.Â);
                }
                return this.size() > 200;
            }
        };
        this.áŒŠÆ = ttfFileRef;
        this.HorizonCode_Horizon_È(Âµá€(ttfFileRef), size, bold, italic);
    }
    
    public UnicodeFont(final java.awt.Font font, final String hieroFileRef) throws SlickException {
        this(font, new HieroSettings(hieroFileRef));
    }
    
    public UnicodeFont(final java.awt.Font font, final HieroSettings settings) {
        this.£á = new Glyph[2175][];
        this.Å = new ArrayList();
        this.£à = new ArrayList(256);
        this.µà = new ArrayList();
        this.áŒŠà = 512;
        this.ŠÄ = 512;
        this.Ñ¢á = true;
        this.ŒÏ = -1;
        this.ÇŽÉ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                final HorizonCode_Horizon_È displayList = eldest.getValue();
                if (displayList != null) {
                    UnicodeFont.HorizonCode_Horizon_È(UnicodeFont.this, displayList.Â);
                }
                return this.size() > 200;
            }
        };
        this.HorizonCode_Horizon_È(font, settings.áŒŠÆ(), settings.áˆºÑ¢Õ(), settings.ÂµÈ());
        this.HorizonCode_Horizon_È(settings);
    }
    
    public UnicodeFont(final java.awt.Font font) {
        this.£á = new Glyph[2175][];
        this.Å = new ArrayList();
        this.£à = new ArrayList(256);
        this.µà = new ArrayList();
        this.áŒŠà = 512;
        this.ŠÄ = 512;
        this.Ñ¢á = true;
        this.ŒÏ = -1;
        this.ÇŽÉ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                final HorizonCode_Horizon_È displayList = eldest.getValue();
                if (displayList != null) {
                    UnicodeFont.HorizonCode_Horizon_È(UnicodeFont.this, displayList.Â);
                }
                return this.size() > 200;
            }
        };
        this.HorizonCode_Horizon_È(font, font.getSize(), font.isBold(), font.isItalic());
    }
    
    public UnicodeFont(final java.awt.Font font, final int size, final boolean bold, final boolean italic) {
        this.£á = new Glyph[2175][];
        this.Å = new ArrayList();
        this.£à = new ArrayList(256);
        this.µà = new ArrayList();
        this.áŒŠà = 512;
        this.ŠÄ = 512;
        this.Ñ¢á = true;
        this.ŒÏ = -1;
        this.ÇŽÉ = new LinkedHashMap(200, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                final HorizonCode_Horizon_È displayList = eldest.getValue();
                if (displayList != null) {
                    UnicodeFont.HorizonCode_Horizon_È(UnicodeFont.this, displayList.Â);
                }
                return this.size() > 200;
            }
        };
        this.HorizonCode_Horizon_È(font, size, bold, italic);
    }
    
    private void HorizonCode_Horizon_È(final java.awt.Font baseFont, final int size, final boolean bold, final boolean italic) {
        final Map attributes = baseFont.getAttributes();
        attributes.put(TextAttribute.SIZE, new Float(size));
        attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
        attributes.put(TextAttribute.POSTURE, italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
        try {
            attributes.put(TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField("KERNING_ON").get(null));
        }
        catch (Exception ex) {}
        this.Ø = baseFont.deriveFont(attributes);
        final FontMetrics metrics = GlyphPage.HorizonCode_Horizon_È().getFontMetrics(this.Ø);
        this.áˆºÑ¢Õ = metrics.getAscent();
        this.ÂµÈ = metrics.getDescent();
        this.á = metrics.getLeading();
        final char[] chars = " ".toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        this.ˆÏ­ = vector.getGlyphLogicalBounds(0).getBounds().width;
    }
    
    private void HorizonCode_Horizon_È(final HieroSettings settings) {
        this.ˆà = settings.HorizonCode_Horizon_È();
        this.¥Æ = settings.Â();
        this.Ø­à = settings.Ý();
        this.µÕ = settings.Ø­áŒŠá();
        this.Æ = settings.Âµá€();
        this.Šáƒ = settings.Ó();
        this.áŒŠà = settings.à();
        this.ŠÄ = settings.Ø();
        this.µà.addAll(settings.á());
    }
    
    public void HorizonCode_Horizon_È(final int startCodePoint, final int endCodePoint) {
        for (int codePoint = startCodePoint; codePoint <= endCodePoint; ++codePoint) {
            this.HorizonCode_Horizon_È(new String(Character.toChars(codePoint)));
        }
    }
    
    public void HorizonCode_Horizon_È(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        final char[] chars = text.toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        for (int i = 0, n = vector.getNumGlyphs(); i < n; ++i) {
            final int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
            final Rectangle bounds = this.HorizonCode_Horizon_È(vector, i, codePoint);
            this.HorizonCode_Horizon_È(vector.getGlyphCode(i), codePoint, bounds, vector, i);
        }
    }
    
    public void Â() {
        this.HorizonCode_Horizon_È(32, 255);
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È(32, 128);
    }
    
    public boolean Ø­áŒŠá() throws SlickException {
        return this.HorizonCode_Horizon_È(-1);
    }
    
    public boolean HorizonCode_Horizon_È(int maxGlyphsToLoad) throws SlickException {
        if (this.£à.isEmpty()) {
            return false;
        }
        if (this.µà.isEmpty()) {
            throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
        }
        Iterator iter = this.£à.iterator();
        while (iter.hasNext()) {
            final Glyph glyph = iter.next();
            final int codePoint = glyph.Â();
            if (glyph.Ý() == 0 || codePoint == 32) {
                iter.remove();
            }
            else {
                if (!glyph.HorizonCode_Horizon_È()) {
                    continue;
                }
                if (this.Ï­Ðƒà != null) {
                    if (glyph == this.Ï­Ðƒà) {
                        continue;
                    }
                    iter.remove();
                }
                else {
                    this.Ï­Ðƒà = glyph;
                }
            }
        }
        Collections.sort((List<Object>)this.£à, UnicodeFont.à);
        iter = this.Å.iterator();
        while (iter.hasNext()) {
            final GlyphPage glyphPage = iter.next();
            maxGlyphsToLoad -= glyphPage.HorizonCode_Horizon_È(this.£à, maxGlyphsToLoad);
            if (maxGlyphsToLoad == 0 || this.£à.isEmpty()) {
                return true;
            }
        }
        while (!this.£à.isEmpty()) {
            final GlyphPage glyphPage2 = new GlyphPage(this, this.áŒŠà, this.ŠÄ);
            this.Å.add(glyphPage2);
            maxGlyphsToLoad -= glyphPage2.HorizonCode_Horizon_È(this.£à, maxGlyphsToLoad);
            if (maxGlyphsToLoad == 0) {
                return true;
            }
        }
        return true;
    }
    
    public void Âµá€() {
        for (int i = 0; i < 2175; ++i) {
            this.£á[i] = null;
        }
        for (final GlyphPage page : this.Å) {
            try {
                page.Ý().£á();
            }
            catch (SlickException ex) {}
        }
        this.Å.clear();
        if (this.ŒÏ != -1) {
            UnicodeFont.Âµá€.Ó(this.ŒÏ, this.ÇŽÉ.size());
            this.ŒÏ = -1;
        }
        this.£à.clear();
        this.Ï­Ðƒà = null;
    }
    
    public void Ó() {
        this.Âµá€();
    }
    
    public HorizonCode_Horizon_È Â(float x, float y, final String text, final Color color, final int startIndex, final int endIndex) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (text.length() == 0) {
            return UnicodeFont.Ó;
        }
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        x -= this.¥Æ;
        y -= this.ˆà;
        final String displayListKey = text.substring(startIndex, endIndex);
        color.HorizonCode_Horizon_È();
        TextureImpl.£à();
        HorizonCode_Horizon_È displayList = null;
        if (this.Ñ¢á && this.£à.isEmpty()) {
            if (this.ŒÏ == -1) {
                this.ŒÏ = UnicodeFont.Âµá€.Ó(200);
                if (this.ŒÏ == 0) {
                    this.ŒÏ = -1;
                    this.Ñ¢á = false;
                    return new HorizonCode_Horizon_È();
                }
            }
            displayList = this.ÇŽÉ.get(displayListKey);
            if (displayList != null) {
                if (!displayList.HorizonCode_Horizon_È) {
                    UnicodeFont.Âµá€.Â(x, y, 0.0f);
                    UnicodeFont.Âµá€.Â(displayList.Â);
                    UnicodeFont.Âµá€.Â(-x, -y, 0.0f);
                    return displayList;
                }
                displayList.HorizonCode_Horizon_È = false;
            }
            else if (displayList == null) {
                displayList = new HorizonCode_Horizon_È();
                final int displayListCount = this.ÇŽÉ.size();
                this.ÇŽÉ.put(displayListKey, displayList);
                if (displayListCount < 200) {
                    displayList.Â = this.ŒÏ + displayListCount;
                }
                else {
                    displayList.Â = this.Çªà¢;
                }
            }
            this.ÇŽÉ.put(displayListKey, displayList);
        }
        UnicodeFont.Âµá€.Â(x, y, 0.0f);
        if (displayList != null) {
            UnicodeFont.Âµá€.Âµá€(displayList.Â, 4865);
        }
        final char[] chars = text.substring(0, endIndex).toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        int maxWidth = 0;
        int totalHeight = 0;
        int lines = 0;
        int extraX = 0;
        int extraY = this.áˆºÑ¢Õ;
        boolean startNewLine = false;
        Texture lastBind = null;
        for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; ++glyphIndex) {
            final int charIndex = vector.getGlyphCharIndex(glyphIndex);
            if (charIndex >= startIndex) {
                if (charIndex > endIndex) {
                    break;
                }
                final int codePoint = text.codePointAt(charIndex);
                final Rectangle bounds = this.HorizonCode_Horizon_È(vector, glyphIndex, codePoint);
                final Glyph glyph = this.HorizonCode_Horizon_È(vector.getGlyphCode(glyphIndex), codePoint, bounds, vector, glyphIndex);
                if (startNewLine && codePoint != 10) {
                    extraX = -bounds.x;
                    startNewLine = false;
                }
                Image image = glyph.Ó();
                if (image == null && this.Ï­Ðƒà != null && glyph.HorizonCode_Horizon_È()) {
                    image = this.Ï­Ðƒà.Ó();
                }
                if (image != null) {
                    final Texture texture = image.áŒŠÆ();
                    if (lastBind != null && lastBind != texture) {
                        UnicodeFont.Âµá€.HorizonCode_Horizon_È();
                        lastBind = null;
                    }
                    if (lastBind == null) {
                        texture.Ý();
                        UnicodeFont.Âµá€.HorizonCode_Horizon_È(7);
                        lastBind = texture;
                    }
                    image.Â(bounds.x + extraX, bounds.y + extraY, image.ŒÏ(), image.Çªà¢());
                }
                if (glyphIndex >= 0) {
                    extraX += this.µÕ + this.¥Æ + this.Æ;
                }
                maxWidth = Math.max(maxWidth, bounds.x + extraX + bounds.width);
                totalHeight = Math.max(totalHeight, this.áˆºÑ¢Õ + bounds.y + bounds.height);
                if (codePoint == 10) {
                    startNewLine = true;
                    extraY += this.HorizonCode_Horizon_È();
                    ++lines;
                    totalHeight = 0;
                }
            }
        }
        if (lastBind != null) {
            UnicodeFont.Âµá€.HorizonCode_Horizon_È();
        }
        if (displayList != null) {
            UnicodeFont.Âµá€.Â();
            if (!this.£à.isEmpty()) {
                displayList.HorizonCode_Horizon_È = true;
            }
        }
        UnicodeFont.Âµá€.Â(-x, -y, 0.0f);
        if (displayList == null) {
            displayList = new HorizonCode_Horizon_È();
        }
        displayList.Ø­áŒŠá = (short)maxWidth;
        displayList.Âµá€ = (short)(lines * this.HorizonCode_Horizon_È() + totalHeight);
        return displayList;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color color, final int startIndex, final int endIndex) {
        this.Â(x, y, text, color, startIndex, endIndex);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text) {
        this.HorizonCode_Horizon_È(x, y, text, Color.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color col) {
        this.HorizonCode_Horizon_È(x, y, text, col, 0, text.length());
    }
    
    private Glyph HorizonCode_Horizon_È(final int glyphCode, final int codePoint, final Rectangle bounds, final GlyphVector vector, final int index) {
        if (glyphCode < 0 || glyphCode >= 1114111) {
            return new Glyph(codePoint, bounds, vector, index, this) {
                @Override
                public boolean HorizonCode_Horizon_È() {
                    return true;
                }
            };
        }
        final int pageIndex = glyphCode / 512;
        final int glyphIndex = glyphCode & 0x1FF;
        Glyph glyph = null;
        Glyph[] page = this.£á[pageIndex];
        if (page != null) {
            glyph = page[glyphIndex];
            if (glyph != null) {
                return glyph;
            }
        }
        else {
            final Glyph[][] £á = this.£á;
            final int n = pageIndex;
            final Glyph[] array = new Glyph[512];
            £á[n] = array;
            page = array;
        }
        final Glyph[] array2 = page;
        final int n2 = glyphIndex;
        final Glyph glyph2 = new Glyph(codePoint, bounds, vector, index, this);
        array2[n2] = glyph2;
        glyph = glyph2;
        this.£à.add(glyph);
        return glyph;
    }
    
    private Rectangle HorizonCode_Horizon_È(final GlyphVector vector, final int index, final int codePoint) {
        final Rectangle bounds = vector.getGlyphPixelBounds(index, GlyphPage.Â, 0.0f, 0.0f);
        if (codePoint == 32) {
            bounds.width = this.ˆÏ­;
        }
        return bounds;
    }
    
    public int à() {
        return this.ˆÏ­;
    }
    
    @Override
    public int Ý(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (text.length() == 0) {
            return 0;
        }
        if (this.Ñ¢á) {
            final HorizonCode_Horizon_È displayList = this.ÇŽÉ.get(text);
            if (displayList != null) {
                return displayList.Ø­áŒŠá;
            }
        }
        final char[] chars = text.toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        int width = 0;
        int extraX = 0;
        boolean startNewLine = false;
        for (int glyphIndex = 0, n = vector.getNumGlyphs(); glyphIndex < n; ++glyphIndex) {
            final int charIndex = vector.getGlyphCharIndex(glyphIndex);
            final int codePoint = text.codePointAt(charIndex);
            final Rectangle bounds = this.HorizonCode_Horizon_È(vector, glyphIndex, codePoint);
            if (startNewLine && codePoint != 10) {
                extraX = -bounds.x;
            }
            if (glyphIndex > 0) {
                extraX += this.¥Æ + this.µÕ + this.Æ;
            }
            width = Math.max(width, bounds.x + extraX + bounds.width);
            if (codePoint == 10) {
                startNewLine = true;
            }
        }
        return width;
    }
    
    @Override
    public int Â(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (text.length() == 0) {
            return 0;
        }
        if (this.Ñ¢á) {
            final HorizonCode_Horizon_È displayList = this.ÇŽÉ.get(text);
            if (displayList != null) {
                return displayList.Âµá€;
            }
        }
        final char[] chars = text.toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        int lines = 0;
        int height = 0;
        for (int i = 0, n = vector.getNumGlyphs(); i < n; ++i) {
            final int charIndex = vector.getGlyphCharIndex(i);
            final int codePoint = text.codePointAt(charIndex);
            if (codePoint != 32) {
                final Rectangle bounds = this.HorizonCode_Horizon_È(vector, i, codePoint);
                height = Math.max(height, this.áˆºÑ¢Õ + bounds.y + bounds.height);
                if (codePoint == 10) {
                    ++lines;
                    height = 0;
                }
            }
        }
        return lines * this.HorizonCode_Horizon_È() + height;
    }
    
    public int Ø­áŒŠá(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        HorizonCode_Horizon_È displayList = null;
        if (this.Ñ¢á) {
            displayList = this.ÇŽÉ.get(text);
            if (displayList != null && displayList.Ý != null) {
                return displayList.Ý;
            }
        }
        final int index = text.indexOf(10);
        if (index != -1) {
            text = text.substring(0, index);
        }
        final char[] chars = text.toCharArray();
        final GlyphVector vector = this.Ø.layoutGlyphVector(GlyphPage.Â, chars, 0, chars.length, 0);
        final int yOffset = this.áˆºÑ¢Õ + vector.getPixelBounds(null, 0.0f, 0.0f).y;
        if (displayList != null) {
            displayList.Ý = new Short((short)yOffset);
        }
        return yOffset;
    }
    
    public java.awt.Font Ø() {
        return this.Ø;
    }
    
    public int áŒŠÆ() {
        return this.ˆà;
    }
    
    public void Â(final int paddingTop) {
        this.ˆà = paddingTop;
    }
    
    public int áˆºÑ¢Õ() {
        return this.¥Æ;
    }
    
    public void Ý(final int paddingLeft) {
        this.¥Æ = paddingLeft;
    }
    
    public int ÂµÈ() {
        return this.Ø­à;
    }
    
    public void Ø­áŒŠá(final int paddingBottom) {
        this.Ø­à = paddingBottom;
    }
    
    public int á() {
        return this.µÕ;
    }
    
    public void Âµá€(final int paddingRight) {
        this.µÕ = paddingRight;
    }
    
    public int ˆÏ­() {
        return this.Æ;
    }
    
    public void Ó(final int paddingAdvanceX) {
        this.Æ = paddingAdvanceX;
    }
    
    public int £á() {
        return this.Šáƒ;
    }
    
    public void à(final int paddingAdvanceY) {
        this.Šáƒ = paddingAdvanceY;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.ÂµÈ + this.áˆºÑ¢Õ + this.á + this.ˆà + this.Ø­à + this.Šáƒ;
    }
    
    public int Å() {
        return this.áˆºÑ¢Õ;
    }
    
    public int £à() {
        return this.ÂµÈ;
    }
    
    public int µà() {
        return this.á;
    }
    
    public int ˆà() {
        return this.áŒŠà;
    }
    
    public void Ø(final int glyphPageWidth) {
        this.áŒŠà = glyphPageWidth;
    }
    
    public int ¥Æ() {
        return this.ŠÄ;
    }
    
    public void áŒŠÆ(final int glyphPageHeight) {
        this.ŠÄ = glyphPageHeight;
    }
    
    public List Ø­à() {
        return this.Å;
    }
    
    public List µÕ() {
        return this.µà;
    }
    
    public boolean Æ() {
        return this.Ñ¢á;
    }
    
    public void HorizonCode_Horizon_È(final boolean displayListCaching) {
        this.Ñ¢á = displayListCaching;
    }
    
    public String Šáƒ() {
        if (this.áŒŠÆ == null) {
            try {
                final Object font2D = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", java.awt.Font.class).invoke(null, this.Ø);
                final Field platNameField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
                platNameField.setAccessible(true);
                this.áŒŠÆ = (String)platNameField.get(font2D);
            }
            catch (Throwable t) {}
            if (this.áŒŠÆ == null) {
                this.áŒŠÆ = "";
            }
        }
        if (this.áŒŠÆ.length() == 0) {
            return null;
        }
        return this.áŒŠÆ;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final UnicodeFont unicodeFont, final int çªà¢) {
        unicodeFont.Çªà¢ = çªà¢;
    }
    
    public static class HorizonCode_Horizon_È
    {
        boolean HorizonCode_Horizon_È;
        int Â;
        Short Ý;
        public short Ø­áŒŠá;
        public short Âµá€;
        public Object Ó;
    }
}
