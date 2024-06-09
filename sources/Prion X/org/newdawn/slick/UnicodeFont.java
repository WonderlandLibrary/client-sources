package org.newdawn.slick;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.GlyphPage;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;











public class UnicodeFont
  implements Font
{
  private static final int DISPLAY_LIST_CACHE_SIZE = 200;
  private static final int MAX_GLYPH_CODE = 1114111;
  private static final int PAGE_SIZE = 512;
  private static final int PAGES = 2175;
  private static final SGL GL = ;
  
  private static final DisplayList EMPTY_DISPLAY_LIST = new DisplayList();
  





  private static java.awt.Font createFont(String ttfFileRef)
    throws SlickException
  {
    try
    {
      return java.awt.Font.createFont(0, ResourceLoader.getResourceAsStream(ttfFileRef));
    } catch (FontFormatException ex) {
      throw new SlickException("Invalid font: " + ttfFileRef, ex);
    } catch (IOException ex) {
      throw new SlickException("Error reading font: " + ttfFileRef, ex);
    }
  }
  



  private static final Comparator heightComparator = new Comparator() {
    public int compare(Object o1, Object o2) {
      return ((Glyph)o1).getHeight() - ((Glyph)o2).getHeight();
    }
  };
  

  private java.awt.Font font;
  
  private String ttfFileRef;
  
  private int ascent;
  
  private int descent;
  
  private int leading;
  
  private int spaceWidth;
  
  private final Glyph[][] glyphs = new Glyph['ࡿ'][];
  
  private final List glyphPages = new ArrayList();
  
  private final List queuedGlyphs = new ArrayList(256);
  
  private final List effects = new ArrayList();
  

  private int paddingTop;
  

  private int paddingLeft;
  
  private int paddingBottom;
  
  private int paddingRight;
  
  private int paddingAdvanceX;
  
  private int paddingAdvanceY;
  
  private Glyph missingGlyph;
  
  private int glyphPageWidth = 512;
  
  private int glyphPageHeight = 512;
  

  private boolean displayListCaching = true;
  
  private int baseDisplayListID = -1;
  

  private int eldestDisplayListID;
  
  private DisplayList eldestDisplayList;
  
  private final LinkedHashMap displayLists = new LinkedHashMap(200, 1.0F, true) {
    protected boolean removeEldestEntry(Map.Entry eldest) {
      UnicodeFont.DisplayList displayList = (UnicodeFont.DisplayList)eldest.getValue();
      if (displayList != null) eldestDisplayListID = id;
      return size() > 200;
    }
  };
  





  public UnicodeFont(String ttfFileRef, String hieroFileRef)
    throws SlickException
  {
    this(ttfFileRef, new HieroSettings(hieroFileRef));
  }
  





  public UnicodeFont(String ttfFileRef, HieroSettings settings)
    throws SlickException
  {
    this.ttfFileRef = ttfFileRef;
    java.awt.Font font = createFont(ttfFileRef);
    initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
    loadSettings(settings);
  }
  







  public UnicodeFont(String ttfFileRef, int size, boolean bold, boolean italic)
    throws SlickException
  {
    this.ttfFileRef = ttfFileRef;
    initializeFont(createFont(ttfFileRef), size, bold, italic);
  }
  





  public UnicodeFont(java.awt.Font font, String hieroFileRef)
    throws SlickException
  {
    this(font, new HieroSettings(hieroFileRef));
  }
  





  public UnicodeFont(java.awt.Font font, HieroSettings settings)
  {
    initializeFont(font, settings.getFontSize(), settings.isBold(), settings.isItalic());
    loadSettings(settings);
  }
  




  public UnicodeFont(java.awt.Font font)
  {
    initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
  }
  







  public UnicodeFont(java.awt.Font font, int size, boolean bold, boolean italic)
  {
    initializeFont(font, size, bold, italic);
  }
  







  private void initializeFont(java.awt.Font baseFont, int size, boolean bold, boolean italic)
  {
    Map attributes = baseFont.getAttributes();
    attributes.put(TextAttribute.SIZE, new Float(size));
    attributes.put(TextAttribute.WEIGHT, bold ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
    attributes.put(TextAttribute.POSTURE, italic ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
    try {
      attributes.put(TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField(
        "KERNING_ON").get(null));
    }
    catch (Exception localException) {}
    font = baseFont.deriveFont(attributes);
    
    FontMetrics metrics = GlyphPage.getScratchGraphics().getFontMetrics(font);
    ascent = metrics.getAscent();
    descent = metrics.getDescent();
    leading = metrics.getLeading();
    

    char[] chars = " ".toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    spaceWidth = getGlyphLogicalBounds0getBoundswidth;
  }
  




  private void loadSettings(HieroSettings settings)
  {
    paddingTop = settings.getPaddingTop();
    paddingLeft = settings.getPaddingLeft();
    paddingBottom = settings.getPaddingBottom();
    paddingRight = settings.getPaddingRight();
    paddingAdvanceX = settings.getPaddingAdvanceX();
    paddingAdvanceY = settings.getPaddingAdvanceY();
    glyphPageWidth = settings.getGlyphPageWidth();
    glyphPageHeight = settings.getGlyphPageHeight();
    effects.addAll(settings.getEffects());
  }
  









  public void addGlyphs(int startCodePoint, int endCodePoint)
  {
    for (int codePoint = startCodePoint; codePoint <= endCodePoint; codePoint++) {
      addGlyphs(new String(Character.toChars(codePoint)));
    }
  }
  




  public void addGlyphs(String text)
  {
    if (text == null) { throw new IllegalArgumentException("text cannot be null.");
    }
    char[] chars = text.toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    int i = 0; for (int n = vector.getNumGlyphs(); i < n; i++) {
      int codePoint = text.codePointAt(vector.getGlyphCharIndex(i));
      Rectangle bounds = getGlyphBounds(vector, i, codePoint);
      getGlyph(vector.getGlyphCode(i), codePoint, bounds, vector, i);
    }
  }
  



  public void addAsciiGlyphs()
  {
    addGlyphs(32, 255);
  }
  



  public void addNeheGlyphs()
  {
    addGlyphs(32, 128);
  }
  






  public boolean loadGlyphs()
    throws SlickException
  {
    return loadGlyphs(-1);
  }
  






  public boolean loadGlyphs(int maxGlyphsToLoad)
    throws SlickException
  {
    if (queuedGlyphs.isEmpty()) { return false;
    }
    if (effects.isEmpty()) {
      throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
    }
    for (Iterator iter = queuedGlyphs.iterator(); iter.hasNext();) {
      Glyph glyph = (Glyph)iter.next();
      int codePoint = glyph.getCodePoint();
      

      if ((glyph.getWidth() == 0) || (codePoint == 32)) {
        iter.remove();



      }
      else if (glyph.isMissing()) {
        if (missingGlyph != null) {
          if (glyph != missingGlyph) iter.remove();
        }
        else {
          missingGlyph = glyph;
        }
      }
    }
    Collections.sort(queuedGlyphs, heightComparator);
    

    for (Iterator iter = glyphPages.iterator(); iter.hasNext();) {
      GlyphPage glyphPage = (GlyphPage)iter.next();
      maxGlyphsToLoad -= glyphPage.loadGlyphs(queuedGlyphs, maxGlyphsToLoad);
      if ((maxGlyphsToLoad == 0) || (queuedGlyphs.isEmpty())) {
        return true;
      }
    }
    
    while (!queuedGlyphs.isEmpty()) {
      GlyphPage glyphPage = new GlyphPage(this, glyphPageWidth, glyphPageHeight);
      glyphPages.add(glyphPage);
      maxGlyphsToLoad -= glyphPage.loadGlyphs(queuedGlyphs, maxGlyphsToLoad);
      if (maxGlyphsToLoad == 0) { return true;
      }
    }
    return true;
  }
  


  public void clearGlyphs()
  {
    for (int i = 0; i < 2175; i++) {
      glyphs[i] = null;
    }
    for (Iterator iter = glyphPages.iterator(); iter.hasNext();) {
      GlyphPage page = (GlyphPage)iter.next();
      try {
        page.getImage().destroy();
      }
      catch (SlickException localSlickException) {}
    }
    glyphPages.clear();
    
    if (baseDisplayListID != -1) {
      GL.glDeleteLists(baseDisplayListID, displayLists.size());
      baseDisplayListID = -1;
    }
    
    queuedGlyphs.clear();
    missingGlyph = null;
  }
  




  public void destroy()
  {
    clearGlyphs();
  }
  











  public DisplayList drawDisplayList(float x, float y, String text, Color color, int startIndex, int endIndex)
  {
    if (text == null) throw new IllegalArgumentException("text cannot be null.");
    if (text.length() == 0) return EMPTY_DISPLAY_LIST;
    if (color == null) { throw new IllegalArgumentException("color cannot be null.");
    }
    x -= paddingLeft;
    y -= paddingTop;
    
    String displayListKey = text.substring(startIndex, endIndex);
    
    color.bind();
    TextureImpl.bindNone();
    
    DisplayList displayList = null;
    if ((displayListCaching) && (queuedGlyphs.isEmpty())) {
      if (baseDisplayListID == -1) {
        baseDisplayListID = GL.glGenLists(200);
        if (baseDisplayListID == 0) {
          baseDisplayListID = -1;
          displayListCaching = false;
          return new DisplayList();
        }
      }
      
      displayList = (DisplayList)displayLists.get(displayListKey);
      if (displayList != null) {
        if (invalid) {
          invalid = false;
        } else {
          GL.glTranslatef(x, y, 0.0F);
          GL.glCallList(id);
          GL.glTranslatef(-x, -y, 0.0F);
          return displayList;
        }
      } else if (displayList == null)
      {
        displayList = new DisplayList();
        int displayListCount = displayLists.size();
        displayLists.put(displayListKey, displayList);
        if (displayListCount < 200) {
          id = (baseDisplayListID + displayListCount);
        } else
          id = eldestDisplayListID;
      }
      displayLists.put(displayListKey, displayList);
    }
    
    GL.glTranslatef(x, y, 0.0F);
    
    if (displayList != null) { GL.glNewList(id, 4865);
    }
    char[] chars = text.substring(0, endIndex).toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    
    int maxWidth = 0;int totalHeight = 0;int lines = 0;
    int extraX = 0;int extraY = ascent;
    boolean startNewLine = false;
    Texture lastBind = null;
    int glyphIndex = 0; for (int n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
      int charIndex = vector.getGlyphCharIndex(glyphIndex);
      if (charIndex >= startIndex) {
        if (charIndex > endIndex)
          break;
        int codePoint = text.codePointAt(charIndex);
        
        Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
        Glyph glyph = getGlyph(vector.getGlyphCode(glyphIndex), codePoint, bounds, vector, glyphIndex);
        
        if ((startNewLine) && (codePoint != 10)) {
          extraX = -x;
          startNewLine = false;
        }
        
        Image image = glyph.getImage();
        if ((image == null) && (missingGlyph != null) && (glyph.isMissing())) image = missingGlyph.getImage();
        if (image != null)
        {
          Texture texture = image.getTexture();
          if ((lastBind != null) && (lastBind != texture)) {
            GL.glEnd();
            lastBind = null;
          }
          if (lastBind == null) {
            texture.bind();
            GL.glBegin(7);
            lastBind = texture;
          }
          image.drawEmbedded(x + extraX, y + extraY, image.getWidth(), image.getHeight());
        }
        
        if (glyphIndex >= 0) extraX += paddingRight + paddingLeft + paddingAdvanceX;
        maxWidth = Math.max(maxWidth, x + extraX + width);
        totalHeight = Math.max(totalHeight, ascent + y + height);
        
        if (codePoint == 10) {
          startNewLine = true;
          extraY += getLineHeight();
          lines++;
          totalHeight = 0;
        }
      } }
    if (lastBind != null) { GL.glEnd();
    }
    if (displayList != null) {
      GL.glEndList();
      
      if (!queuedGlyphs.isEmpty()) { invalid = true;
      }
    }
    GL.glTranslatef(-x, -y, 0.0F);
    
    if (displayList == null) displayList = new DisplayList();
    width = ((short)maxWidth);
    height = ((short)(lines * getLineHeight() + totalHeight));
    return displayList;
  }
  
  public void drawString(float x, float y, String text, Color color, int startIndex, int endIndex) {
    drawDisplayList(x, y, text, color, startIndex, endIndex);
  }
  
  public void drawString(float x, float y, String text) {
    drawString(x, y, text, Color.white);
  }
  
  public void drawString(float x, float y, String text, Color col) {
    drawString(x, y, text, col, 0, text.length());
  }
  










  private Glyph getGlyph(int glyphCode, int codePoint, Rectangle bounds, GlyphVector vector, int index)
  {
    if ((glyphCode < 0) || (glyphCode >= 1114111))
    {
      new Glyph(codePoint, bounds, vector, index, this) {
        public boolean isMissing() {
          return true;
        }
      };
    }
    int pageIndex = glyphCode / 512;
    int glyphIndex = glyphCode & 0x1FF;
    Glyph glyph = null;
    Glyph[] page = glyphs[pageIndex];
    if (page != null) {
      glyph = page[glyphIndex];
      if (glyph != null) return glyph;
    } else {
      page = glyphs[pageIndex] =  = new Glyph['Ȁ'];
    }
    glyph = page[glyphIndex] =  = new Glyph(codePoint, bounds, vector, index, this);
    queuedGlyphs.add(glyph);
    return glyph;
  }
  






  private Rectangle getGlyphBounds(GlyphVector vector, int index, int codePoint)
  {
    Rectangle bounds = vector.getGlyphPixelBounds(index, GlyphPage.renderContext, 0.0F, 0.0F);
    if (codePoint == 32) width = spaceWidth;
    return bounds;
  }
  


  public int getSpaceWidth()
  {
    return spaceWidth;
  }
  


  public int getWidth(String text)
  {
    if (text == null) throw new IllegalArgumentException("text cannot be null.");
    if (text.length() == 0) { return 0;
    }
    if (displayListCaching) {
      DisplayList displayList = (DisplayList)displayLists.get(text);
      if (displayList != null) { return width;
      }
    }
    char[] chars = text.toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    
    int width = 0;
    int extraX = 0;
    boolean startNewLine = false;
    int glyphIndex = 0; for (int n = vector.getNumGlyphs(); glyphIndex < n; glyphIndex++) {
      int charIndex = vector.getGlyphCharIndex(glyphIndex);
      int codePoint = text.codePointAt(charIndex);
      Rectangle bounds = getGlyphBounds(vector, glyphIndex, codePoint);
      
      if ((startNewLine) && (codePoint != 10)) { extraX = -x;
      }
      if (glyphIndex > 0) extraX += paddingLeft + paddingRight + paddingAdvanceX;
      width = Math.max(width, x + extraX + width);
      
      if (codePoint == 10) { startNewLine = true;
      }
    }
    return width;
  }
  


  public int getHeight(String text)
  {
    if (text == null) throw new IllegalArgumentException("text cannot be null.");
    if (text.length() == 0) { return 0;
    }
    if (displayListCaching) {
      DisplayList displayList = (DisplayList)displayLists.get(text);
      if (displayList != null) { return height;
      }
    }
    char[] chars = text.toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    
    int lines = 0;int height = 0;
    int i = 0; for (int n = vector.getNumGlyphs(); i < n; i++) {
      int charIndex = vector.getGlyphCharIndex(i);
      int codePoint = text.codePointAt(charIndex);
      if (codePoint != 32) {
        Rectangle bounds = getGlyphBounds(vector, i, codePoint);
        
        height = Math.max(height, ascent + y + height);
        
        if (codePoint == 10) {
          lines++;
          height = 0;
        }
      } }
    return lines * getLineHeight() + height;
  }
  






  public int getYOffset(String text)
  {
    if (text == null) { throw new IllegalArgumentException("text cannot be null.");
    }
    DisplayList displayList = null;
    if (displayListCaching) {
      displayList = (DisplayList)displayLists.get(text);
      if ((displayList != null) && (yOffset != null)) { return yOffset.intValue();
      }
    }
    int index = text.indexOf('\n');
    if (index != -1) text = text.substring(0, index);
    char[] chars = text.toCharArray();
    GlyphVector vector = font.layoutGlyphVector(GlyphPage.renderContext, chars, 0, chars.length, 0);
    int yOffset = ascent + getPixelBounds0.0F0.0Fy;
    
    if (displayList != null) { yOffset = new Short((short)yOffset);
    }
    return yOffset;
  }
  




  public java.awt.Font getFont()
  {
    return font;
  }
  




  public int getPaddingTop()
  {
    return paddingTop;
  }
  




  public void setPaddingTop(int paddingTop)
  {
    this.paddingTop = paddingTop;
  }
  




  public int getPaddingLeft()
  {
    return paddingLeft;
  }
  




  public void setPaddingLeft(int paddingLeft)
  {
    this.paddingLeft = paddingLeft;
  }
  




  public int getPaddingBottom()
  {
    return paddingBottom;
  }
  




  public void setPaddingBottom(int paddingBottom)
  {
    this.paddingBottom = paddingBottom;
  }
  




  public int getPaddingRight()
  {
    return paddingRight;
  }
  




  public void setPaddingRight(int paddingRight)
  {
    this.paddingRight = paddingRight;
  }
  




  public int getPaddingAdvanceX()
  {
    return paddingAdvanceX;
  }
  





  public void setPaddingAdvanceX(int paddingAdvanceX)
  {
    this.paddingAdvanceX = paddingAdvanceX;
  }
  




  public int getPaddingAdvanceY()
  {
    return paddingAdvanceY;
  }
  





  public void setPaddingAdvanceY(int paddingAdvanceY)
  {
    this.paddingAdvanceY = paddingAdvanceY;
  }
  



  public int getLineHeight()
  {
    return descent + ascent + leading + paddingTop + paddingBottom + paddingAdvanceY;
  }
  




  public int getAscent()
  {
    return ascent;
  }
  





  public int getDescent()
  {
    return descent;
  }
  




  public int getLeading()
  {
    return leading;
  }
  




  public int getGlyphPageWidth()
  {
    return glyphPageWidth;
  }
  




  public void setGlyphPageWidth(int glyphPageWidth)
  {
    this.glyphPageWidth = glyphPageWidth;
  }
  




  public int getGlyphPageHeight()
  {
    return glyphPageHeight;
  }
  




  public void setGlyphPageHeight(int glyphPageHeight)
  {
    this.glyphPageHeight = glyphPageHeight;
  }
  




  public List getGlyphPages()
  {
    return glyphPages;
  }
  





  public List getEffects()
  {
    return effects;
  }
  





  public boolean isCaching()
  {
    return displayListCaching;
  }
  





  public void setDisplayListCaching(boolean displayListCaching)
  {
    this.displayListCaching = displayListCaching;
  }
  





  public String getFontFile()
  {
    if (ttfFileRef == null)
    {
      try {
        Object font2D = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", new Class[] { java.awt.Font.class })
          .invoke(null, new Object[] { font });
        Field platNameField = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
        platNameField.setAccessible(true);
        ttfFileRef = ((String)platNameField.get(font2D));
      }
      catch (Throwable localThrowable) {}
      if (ttfFileRef == null) ttfFileRef = "";
    }
    if (ttfFileRef.length() == 0) return null;
    return ttfFileRef;
  }
  
  public static class DisplayList
  {
    boolean invalid;
    int id;
    Short yOffset;
    public short width;
    public short height;
    public Object userData;
    
    DisplayList() {}
  }
}
