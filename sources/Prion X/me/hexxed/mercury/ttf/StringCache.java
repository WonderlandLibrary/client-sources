package me.hexxed.mercury.ttf;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.lang.ref.WeakReference;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import org.lwjgl.opengl.GL11;

public class StringCache
{
  private static final int BASELINE_OFFSET = 7;
  private static final int UNDERLINE_OFFSET = 1;
  private static final int UNDERLINE_THICKNESS = 2;
  private static final int STRIKETHROUGH_OFFSET = -6;
  private static final int STRIKETHROUGH_THICKNESS = 2;
  private final GlyphCache glyphCache;
  private final int[] colorTable;
  private final WeakHashMap<Key, Entry> stringCache = new WeakHashMap();
  private final WeakHashMap<String, Key> weakRefCache = new WeakHashMap();
  private final Key lookupKey = new Key(null);
  private final Glyph[][] digitGlyphs = new Glyph[4][];
  private boolean digitGlyphsReady = false;
  private boolean antiAliasEnabled = false;
  private final Thread mainThread;
  
  private static class Key {
    private Key() {}
    
    public int hashCode() { int code = 0;
      int length = str.length();
      boolean colorCode = false;
      
      for (int index = 0; index < length; index++) {
        char c = str.charAt(index);
        if ((c >= '0') && (c <= '9') && (!colorCode)) {
          c = '0';
        }
        code = code * 31 + c;
        colorCode = c == '§';
      }
      
      return code;
    }
    
    public boolean equals(Object o) {
      if (o == null) {
        return false;
      }
      String other = o.toString();
      int length = str.length();
      
      if (length != other.length()) {
        return false;
      }
      boolean colorCode = false;
      
      for (int index = 0; index < length; index++) {
        char c1 = str.charAt(index);
        char c2 = other.charAt(index);
        
        if ((c1 != c2) && (
          (c1 < '0') || (c1 > '9') || (c2 < '0') || 
          (c2 > '9') || (colorCode))) {
          return false;
        }
        colorCode = c1 == '§';
      }
      
      return true;
    }
    
    public String toString() {
      return str;
    }
    
    public String str;
  }
  
  private static class ColorCode implements Comparable<Integer>
  {
    public static final byte UNDERLINE = 1;
    public static final byte STRIKETHROUGH = 2;
    public int stringIndex;
    public int stripIndex;
    public byte colorCode;
    public byte fontStyle;
    public byte renderStyle;
    
    private ColorCode() {}
    
    public int compareTo(Integer i)
    {
      return 
        stringIndex < i.intValue() ? -1 : stringIndex == i.intValue() ? 0 : 1;
    } }
  
  private static class Glyph implements Comparable<Glyph> { public int stringIndex;
    public GlyphCache.Entry texture;
    public int x;
    public int y;
    public int advance;
    
    private Glyph() {}
    
    public int compareTo(Glyph o) { return 
        stringIndex < stringIndex ? -1 : stringIndex == stringIndex ? 0 : 1;
    }
  }
  
  public StringCache(int[] colors) { mainThread = Thread.currentThread();
    
    glyphCache = new GlyphCache();
    colorTable = colors;
    
    cacheDightGlyphs();
  }
  
  public void setDefaultFont(String name, int size, boolean antiAlias) {
    glyphCache.setDefaultFont(name, size, antiAlias);
    antiAliasEnabled = antiAlias;
    weakRefCache.clear();
    stringCache.clear();
    cacheDightGlyphs();
  }
  
  private void cacheDightGlyphs() { digitGlyphsReady = false;
    digitGlyphs[0] = cacheString"0123456789"glyphs;
    digitGlyphs[1] = cacheString"???l0123456789"glyphs;
    digitGlyphs[2] = cacheString"???o0123456789"glyphs;
    digitGlyphs[3] = cacheString"???l???o0123456789"glyphs;
    digitGlyphsReady = true;
  }
  
  public int renderString(String str, float startX, float startY, int initialColor, boolean shadowFlag) {
    if ((str == null) || (str.isEmpty())) {
      return 0;
    }
    Entry entry = cacheString(str);
    startY += 7.0F;
    int color = initialColor;
    int boundTextureName = 0;
    GL11.glColor3f(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
    if (antiAliasEnabled) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
    }
    WorldRenderer tessellator = Tessellator.instance.getWorldRenderer();
    tessellator.startDrawingQuads();
    int fontStyle = 0;
    
    int glyphIndex = 0; for (int colorIndex = 0; glyphIndex < glyphs.length; glyphIndex++) {
      while ((colorIndex < colors.length) && (
        glyphs[glyphIndex].stringIndex >= colors[colorIndex].stringIndex)) {
        color = applyColorCode(colors[colorIndex].colorCode, 
          initialColor, shadowFlag);
        fontStyle = colors[colorIndex].fontStyle;
        colorIndex++;
      }
      Glyph glyph = glyphs[glyphIndex];
      GlyphCache.Entry texture = texture;
      int glyphX = x;
      char c = str.charAt(stringIndex);
      if ((c >= '0') && (c <= '9')) {
        int oldWidth = width;
        texture = digitGlyphs[fontStyle][(c - '0')].texture;
        int newWidth = width;
        glyphX += (oldWidth - newWidth >> 1);
      }
      if (boundTextureName != textureName) {
        tessellator.draw();
        tessellator.startDrawingQuads();
        
        GL11.glBindTexture(3553, textureName);
        boundTextureName = textureName;
      }
      float x1 = startX + glyphX / 2.0F;
      float x2 = startX + (glyphX + width) / 2.0F;
      float y1 = startY + y / 2.0F;
      float y2 = startY + (y + height) / 2.0F;
      
      tessellator.addVertexWithUV(x1, y1, 0.0D, u1, v1);
      tessellator.addVertexWithUV(x1, y2, 0.0D, u1, v2);
      tessellator.addVertexWithUV(x2, y2, 0.0D, u2, v2);
      tessellator.addVertexWithUV(x2, y1, 0.0D, u2, v1);
    }
    tessellator.draw();
    if (specialRender) {
      int renderStyle = 0;
      color = initialColor;
      GL11.glDisable(3553);
      tessellator.startDrawingQuads();
      
      int glyphIndex = 0; for (int colorIndex = 0; glyphIndex < glyphs.length; glyphIndex++) {
        while ((colorIndex < colors.length) && (
          glyphs[glyphIndex].stringIndex >= colors[colorIndex].stringIndex)) {
          color = applyColorCode(colors[colorIndex].colorCode, 
            initialColor, shadowFlag);
          renderStyle = colors[colorIndex].renderStyle;
          colorIndex++;
        }
        Glyph glyph = glyphs[glyphIndex];
        int glyphSpace = advance - texture.width;
        if ((renderStyle & 0x1) != 0) {
          float x1 = startX + (x - glyphSpace) / 2.0F;
          float x2 = startX + 
            (x + advance) / 2.0F;
          float y1 = startY + 0.5F;
          float y2 = startY + 
            1.5F;
          
          tessellator.addVertex(x1, y1, 0.0D);
          tessellator.addVertex(x1, y2, 0.0D);
          tessellator.addVertex(x2, y2, 0.0D);
          tessellator.addVertex(x2, y1, 0.0D);
        }
        if ((renderStyle & 0x2) != 0) {
          float x1 = startX + (x - glyphSpace) / 2.0F;
          float x2 = startX + 
            (x + advance) / 2.0F;
          float y1 = startY + -3.0F;
          float y2 = startY + 
            -2.0F;
          
          tessellator.addVertex(x1, y1, 0.0D);
          tessellator.addVertex(x1, y2, 0.0D);
          tessellator.addVertex(x2, y2, 0.0D);
          tessellator.addVertex(x2, y1, 0.0D);
        }
      }
      tessellator.draw();
      GL11.glEnable(3553);
    }
    
    if (antiAliasEnabled) {
      GL11.glDisable(3042);
    }
    return advance / 2;
  }
  
  public int getStringWidth(String str) { if ((str == null) || (str.isEmpty())) {
      return 0;
    }
    Entry entry = cacheString(str);
    return advance / 2;
  }
  
  public int getStringHeight() {
    Entry entry = cacheString("|");
    Glyph glyph = glyphs[0];
    return texture.height / 2 - 1;
  }
  
  private int sizeString(String str, int width, boolean breakAtSpaces) {
    if ((str == null) || (str.isEmpty())) {
      return 0;
    }
    width += width;
    Glyph[] glyphs = cacheStringglyphs;
    int wsIndex = -1;
    int advance = 0;int index = 0;
    while ((index < glyphs.length) && (advance <= width)) {
      if (breakAtSpaces) {
        char c = str.charAt(stringIndex);
        if (c == ' ') {
          wsIndex = index;
        } else if (c == '\n') {
          wsIndex = index;
          break;
        }
      }
      
      advance += advance;
      index++;
    }
    if ((index < glyphs.length) && (wsIndex != -1) && (wsIndex < index)) {
      index = wsIndex;
    }
    return index < glyphs.length ? stringIndex : str.length();
  }
  
  public int sizeStringToWidth(String str, int width) { return sizeString(str, width, true); }
  
  public String trimStringToWidth(String str, int width, boolean reverse)
  {
    int length = sizeString(str, width, false);
    str = str.substring(0, length);
    
    if (reverse) {
      str = new StringBuilder(str).reverse().toString();
    }
    
    return str;
  }
  

  private int applyColorCode(int colorCode, int color, boolean shadowFlag)
  {
    if (colorCode != -1) {
      colorCode = shadowFlag ? colorCode + 16 : colorCode;
      color = colorTable[colorCode] & 0xFFFFFF | color & 0xFF000000;
    }
    return color;
  }
  
  private Entry cacheString(String str) {
    Entry entry = null;
    if (mainThread == Thread.currentThread()) {
      lookupKey.str = str;
      entry = (Entry)stringCache.get(lookupKey);
    }
    if (entry == null) {
      char[] text = str.toCharArray();
      entry = new Entry(null);
      int length = stripColorCodes(entry, str, text);
      List<Glyph> glyphList = new ArrayList();
      advance = layoutBidiString(glyphList, text, 0, length, 
        colors);
      glyphs = new Glyph[glyphList.size()];
      glyphs = ((Glyph[])glyphList.toArray(glyphs));
      Arrays.sort(glyphs);
      int colorIndex = 0;int shift = 0;
      for (Glyph glyph2 : glyphs) {
        Glyph glyph = glyph2;
        while ((colorIndex < colors.length) && (
          stringIndex + shift >= colors[colorIndex].stringIndex)) {
          shift += 2;
          colorIndex++;
        }
        stringIndex += shift;
      }
      if (mainThread == Thread.currentThread()) {
        Key key = new Key(null);
        str = new String(str);
        keyRef = new WeakReference(key);
        stringCache.put(key, entry);
      }
    }
    if (mainThread == Thread.currentThread()) {
      Key oldKey = (Key)keyRef.get();
      if (oldKey != null) {
        weakRefCache.put(str, oldKey);
      }
      lookupKey.str = null;
    }
    return entry;
  }
  
  private int stripColorCodes(Entry cacheEntry, String str, char[] text) {
    List<ColorCode> colorList = new ArrayList();
    int start = 0;int shift = 0;
    
    byte fontStyle = 0;
    byte renderStyle = 0;
    byte colorCode = -1;
    int next; while (((next = str.indexOf('§', start)) != -1) && (
      next + 1 < str.length())) { int next;
      System.arraycopy(text, next - shift + 2, text, next - shift, 
        text.length - next - 2);
      int code = "0123456789abcdefklmnor".indexOf(
        Character.toLowerCase(str.charAt(next + 1)));
      switch (code) {
      case 16: 
        break;
      case 17: 
        fontStyle = (byte)(fontStyle | 0x1);
        break;
      case 18: 
        renderStyle = (byte)(renderStyle | 0x2);
        specialRender = true;
        break;
      case 19: 
        renderStyle = (byte)(renderStyle | 0x1);
        specialRender = true;
        break;
      case 20: 
        fontStyle = (byte)(fontStyle | 0x2);
        break;
      case 21: 
        fontStyle = 0;
        renderStyle = 0;
        colorCode = -1;
        break;
      default: 
        if ((code >= 0) && (code <= 15)) {
          colorCode = (byte)code;
          fontStyle = 0;
          renderStyle = 0;
        }
        break;
      }
      ColorCode entry = new ColorCode(null);
      stringIndex = next;
      stripIndex = (next - shift);
      colorCode = colorCode;
      fontStyle = fontStyle;
      renderStyle = renderStyle;
      colorList.add(entry);
      start = next + 2;
      shift += 2;
    }
    colors = new ColorCode[colorList.size()];
    colors = ((ColorCode[])colorList.toArray(colors));
    return text.length - shift;
  }
  
  private int layoutBidiString(List<Glyph> glyphList, char[] text, int start, int limit, ColorCode[] colors)
  {
    int advance = 0;
    if (Bidi.requiresBidi(text, start, limit)) {
      Bidi bidi = new Bidi(text, start, null, 0, limit - start, 
        -2);
      if (bidi.isRightToLeft()) {
        return layoutStyle(glyphList, text, start, limit, 
          1, advance, colors);
      }
      
      int runCount = bidi.getRunCount();
      byte[] levels = new byte[runCount];
      Integer[] ranges = new Integer[runCount];
      for (int index = 0; index < runCount; index++) {
        levels[index] = ((byte)bidi.getRunLevel(index));
        ranges[index] = new Integer(index);
      }
      Bidi.reorderVisually(levels, 0, ranges, 0, runCount);
      for (int visualIndex = 0; visualIndex < runCount; visualIndex++) {
        int logicalIndex = ranges[visualIndex].intValue();
        int layoutFlag = (bidi.getRunLevel(logicalIndex) & 0x1) == 1 ? 1 : 
          0;
        advance = layoutStyle(glyphList, text, 
          start + bidi.getRunStart(logicalIndex), start + 
          bidi.getRunLimit(logicalIndex), 
          layoutFlag, advance, colors);
      }
      

      return advance;
    }
    
    return layoutStyle(glyphList, text, start, limit, 
      0, advance, colors);
  }
  


  private int layoutStyle(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, ColorCode[] colors)
  {
    int currentFontStyle = 0;
    int colorIndex = Arrays.binarySearch(colors, Integer.valueOf(start));
    if (colorIndex < 0) {
      colorIndex = -colorIndex - 2;
    }
    while (start < limit) {
      int next = limit;
      while ((colorIndex >= 0) && 
        (colorIndex < colors.length - 1) && 
        (stripIndex == 1stripIndex)) {
        colorIndex++;
      }
      if ((colorIndex >= 0) && (colorIndex < colors.length)) {
        currentFontStyle = fontStyle;
      }
      do {
        if (fontStyle != currentFontStyle) {
          next = stripIndex;
          break;
        }
        colorIndex++; } while (colorIndex < colors.length);
      




      advance = layoutString(glyphList, text, start, next, layoutFlags, 
        advance, currentFontStyle);
      start = next;
    }
    
    return advance;
  }
  

  private int layoutString(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, int style)
  {
    if (digitGlyphsReady) {
      for (int index = start; index < limit; index++) {
        if ((text[index] >= '0') && (text[index] <= '9')) {
          text[index] = '0';
        }
      }
    }
    while (start < limit) {
      Font font = glyphCache.lookupFont(text, start, limit, style);
      int next = font.canDisplayUpTo(text, start, limit);
      if (next == -1) {
        next = limit;
      }
      if (next == start) {
        next++;
      }
      
      advance = layoutFont(glyphList, text, start, next, layoutFlags, 
        advance, font);
      start = next;
    }
    
    return advance;
  }
  
  private int layoutFont(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, Font font)
  {
    if (mainThread == Thread.currentThread()) {
      glyphCache.cacheGlyphs(font, text, start, limit, layoutFlags);
    }
    GlyphVector vector = glyphCache.layoutGlyphVector(font, text, 
      start, limit, layoutFlags);
    Glyph glyph = null;
    int numGlyphs = vector.getNumGlyphs();
    for (int index = 0; index < numGlyphs; index++) {
      Point position = vector.getGlyphPixelBounds(index, null, 
        advance, 0.0F).getLocation();
      if (glyph != null) {
        advance = (x - x);
      }
      glyph = new Glyph(null);
      stringIndex = (start + vector.getGlyphCharIndex(index));
      texture = glyphCache.lookupGlyph(font, 
        vector.getGlyphCode(index));
      x = x;
      y = y;
      glyphList.add(glyph);
    }
    advance += (int)vector.getGlyphPosition(numGlyphs).getX();
    if (glyph != null) {
      advance = (advance - x);
    }
    return advance;
  }
  
  private static class Entry
  {
    public WeakReference<StringCache.Key> keyRef;
    public int advance;
    public StringCache.Glyph[] glyphs;
    public StringCache.ColorCode[] colors;
    public boolean specialRender;
    
    private Entry() {}
  }
}
