package me.hexxed.mercury.ttf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;


public class GlyphCache
{
  private static final int TEXTURE_WIDTH = 256;
  private static final int TEXTURE_HEIGHT = 256;
  private static final int STRING_WIDTH = 256;
  private static final int STRING_HEIGHT = 64;
  private static final int GLYPH_BORDER = 1;
  private static Color BACK_COLOR = new Color(255, 255, 255, 0);
  private int fontSize = 18;
  private boolean antiAliasEnabled = false;
  private BufferedImage stringImage;
  private Graphics2D stringGraphics;
  private final BufferedImage glyphCacheImage = new BufferedImage(
    256, 256, 2);
  private final Graphics2D glyphCacheGraphics = glyphCacheImage
    .createGraphics();
  private final FontRenderContext fontRenderContext = glyphCacheGraphics
    .getFontRenderContext();
  private final int[] imageData = new int[65536];
  
  private final IntBuffer imageBuffer = ByteBuffer.allocateDirect(262144)
    .order(ByteOrder.BIG_ENDIAN).asIntBuffer();
  
  private final IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
  
  private final List<Font> allFonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
  private final List<Font> usedFonts = new ArrayList();
  private int textureName;
  private final LinkedHashMap<Font, Integer> fontCache = new LinkedHashMap();
  private final LinkedHashMap<Long, Entry> glyphCache = new LinkedHashMap();
  private int cachePosX = 1;
  private int cachePosY = 1;
  private int cacheLineHeight = 0;
  








  public GlyphCache()
  {
    glyphCacheGraphics.setBackground(BACK_COLOR);
    glyphCacheGraphics.setComposite(AlphaComposite.Src);
    
    allocateGlyphCacheTexture();
    allocateStringImage(256, 64);
    GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
    usedFonts.add(new Font("SansSerif", 0, 1));
  }
  
  void setDefaultFont(String name, int size, boolean antiAlias)
  {
    usedFonts.clear();
    usedFonts.add(new Font(name, 0, 1));
    
    fontSize = size;
    antiAliasEnabled = antiAlias;
    setRenderingHints();
  }
  
  GlyphVector layoutGlyphVector(Font font, char[] text, int start, int limit, int layoutFlags) {
    if (!fontCache.containsKey(font)) {
      fontCache.put(font, Integer.valueOf(fontCache.size()));
    }
    return font.layoutGlyphVector(fontRenderContext, text, start, limit, 
      layoutFlags);
  }
  
  Font lookupFont(char[] text, int start, int limit, int style) {
    Iterator<Font> iterator = usedFonts.iterator();
    while (iterator.hasNext())
    {
      Font font = (Font)iterator.next();
      if (font.canDisplayUpTo(text, start, limit) != start) {
        return font.deriveFont(style, fontSize);
      }
    }
    
    iterator = allFonts.iterator();
    while (iterator.hasNext()) {
      Font font = (Font)iterator.next();
      if (font.canDisplayUpTo(text, start, limit) != start)
      {

        usedFonts.add(font);
        return font.deriveFont(style, fontSize);
      }
    }
    
    Font font = (Font)usedFonts.get(0);
    
    return font.deriveFont(style, fontSize);
  }
  
  Entry lookupGlyph(Font font, int glyphCode) {
    long fontKey = ((Integer)fontCache.get(font)).intValue() << 32;
    return (Entry)glyphCache.get(Long.valueOf(fontKey | glyphCode));
  }
  

  void cacheGlyphs(Font font, char[] text, int start, int limit, int layoutFlags)
  {
    GlyphVector vector = layoutGlyphVector(font, text, start, limit, 
      layoutFlags);
    
    Rectangle vectorBounds = null;
    long fontKey = ((Integer)fontCache.get(font)).intValue() << 32;
    
    int numGlyphs = vector.getNumGlyphs();
    Rectangle dirty = null;
    boolean vectorRendered = false;
    
    for (int index = 0; index < numGlyphs; index++) {
      int glyphCode = vector.getGlyphCode(index);
      if (!glyphCache.containsKey(Long.valueOf(fontKey | glyphCode)))
      {

        if (!vectorRendered) {
          vectorRendered = true;
          for (int i = 0; i < numGlyphs; i++) {
            Point2D pos = vector.getGlyphPosition(i);
            pos.setLocation(pos.getX() + 2 * i, pos.getY());
            vector.setGlyphPosition(i, pos);
          }
          vectorBounds = vector.getPixelBounds(fontRenderContext, 0.0F, 0.0F);
          if ((stringImage == null) || 
            (width > stringImage.getWidth()) || 
            (height > stringImage.getHeight())) {
            int width = Math.max(width, 
              stringImage.getWidth());
            int height = Math.max(height, 
              stringImage.getHeight());
            allocateStringImage(width, height);
          }
          stringGraphics.clearRect(0, 0, width, 
            height);
          stringGraphics.drawGlyphVector(vector, -x, 
            -y);
        }
        Rectangle rect = vector.getGlyphPixelBounds(index, null, 
          -x, -y);
        if (cachePosX + width + 1 > 256) {
          cachePosX = 1;
          cachePosY += cacheLineHeight + 1;
          cacheLineHeight = 0;
        }
        if (cachePosY + height + 1 > 256) {
          updateTexture(dirty);
          dirty = null;
          allocateGlyphCacheTexture();
          cachePosY = (this.cachePosX = 1);
          cacheLineHeight = 0;
        }
        if (height > cacheLineHeight) {
          cacheLineHeight = height;
        }
        glyphCacheGraphics.drawImage(stringImage, cachePosX, cachePosY, 
          cachePosX + width, cachePosY + height, x, 
          y, x + width, y + height, null);
        
        rect.setLocation(cachePosX, cachePosY);
        Entry entry = new Entry();
        textureName = textureName;
        width = width;
        height = height;
        u1 = (x / 256.0F);
        v1 = (y / 256.0F);
        u2 = ((x + width) / 256.0F);
        v2 = ((y + height) / 256.0F);
        glyphCache.put(Long.valueOf(fontKey | glyphCode), entry);
        if (dirty == null) {
          dirty = new Rectangle(cachePosX, cachePosY, width, 
            height);
        } else {
          dirty.add(rect);
        }
        cachePosX += width + 1;
      }
    }
    updateTexture(dirty);
  }
  
  private void updateTexture(Rectangle dirty) { if (dirty != null) {
      updateImageBuffer(x, y, width, height);
      
      GL11.glBindTexture(3553, textureName);
      GL11.glTexSubImage2D(3553, 0, x, y, 
        width, height, 6408, 
        5121, imageBuffer);
    }
  }
  
  private void allocateStringImage(int width, int height) { stringImage = new BufferedImage(width, height, 
      2);
    stringGraphics = stringImage.createGraphics();
    setRenderingHints();
    stringGraphics.setBackground(BACK_COLOR);
    stringGraphics.setPaint(Color.WHITE);
  }
  
  private void setRenderingHints() {
    stringGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
      antiAliasEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : 
      RenderingHints.VALUE_ANTIALIAS_OFF);
    stringGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
      antiAliasEnabled ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : 
      RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    
    stringGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
      RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
  }
  
  private void allocateGlyphCacheTexture() { glyphCacheGraphics.clearRect(0, 0, 256, 256);
    singleIntBuffer.clear();
    GL11.glGenTextures(singleIntBuffer);
    textureName = singleIntBuffer.get(0);
    updateImageBuffer(0, 0, 256, 256);
    GL11.glBindTexture(3553, textureName);
    GL11.glTexImage2D(3553, 0, 32828, 256, 
      256, 0, 6408, 5121, 
      imageBuffer);
    GL11.glTexParameteri(3553, 10241, 
      9728);
    GL11.glTexParameteri(3553, 10240, 
      9728);
  }
  
  private void updateImageBuffer(int x, int y, int width, int height)
  {
    glyphCacheImage.getRGB(x, y, width, height, imageData, 0, width);
    for (int i = 0; i < width * height; i++) {
      int color = imageData[i];
      imageData[i] = (color << 8 | color >>> 24);
    }
    
    imageBuffer.clear();
    imageBuffer.put(imageData);
    imageBuffer.flip();
  }
  
  static class Entry
  {
    public int textureName;
    public int width;
    public int height;
    public float u1;
    public float v1;
    public float u2;
    public float v2;
    
    Entry() {}
  }
}
