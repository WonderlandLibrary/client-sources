package org.newdawn.slick.font;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;





















public class Glyph
{
  private int codePoint;
  private short width;
  private short height;
  private short yOffset;
  private boolean isMissing;
  private Shape shape;
  private Image image;
  
  public Glyph(int codePoint, Rectangle bounds, GlyphVector vector, int index, UnicodeFont unicodeFont)
  {
    this.codePoint = codePoint;
    
    GlyphMetrics metrics = vector.getGlyphMetrics(index);
    int lsb = (int)metrics.getLSB();
    if (lsb > 0) lsb = 0;
    int rsb = (int)metrics.getRSB();
    if (rsb > 0) { rsb = 0;
    }
    int glyphWidth = width - lsb - rsb;
    int glyphHeight = height;
    if ((glyphWidth > 0) && (glyphHeight > 0)) {
      int padTop = unicodeFont.getPaddingTop();
      int padRight = unicodeFont.getPaddingRight();
      int padBottom = unicodeFont.getPaddingBottom();
      int padLeft = unicodeFont.getPaddingLeft();
      int glyphSpacing = 1;
      width = ((short)(glyphWidth + padLeft + padRight + glyphSpacing));
      height = ((short)(glyphHeight + padTop + padBottom + glyphSpacing));
      yOffset = ((short)(unicodeFont.getAscent() + y - padTop));
    }
    
    shape = vector.getGlyphOutline(index, -x + unicodeFont.getPaddingLeft(), -y + unicodeFont.getPaddingTop());
    
    isMissing = (!unicodeFont.getFont().canDisplay((char)codePoint));
  }
  




  public int getCodePoint()
  {
    return codePoint;
  }
  




  public boolean isMissing()
  {
    return isMissing;
  }
  




  public int getWidth()
  {
    return width;
  }
  




  public int getHeight()
  {
    return height;
  }
  





  public Shape getShape()
  {
    return shape;
  }
  




  public void setShape(Shape shape)
  {
    this.shape = shape;
  }
  





  public Image getImage()
  {
    return image;
  }
  




  public void setImage(Image image)
  {
    this.image = image;
  }
  





  public int getYOffset()
  {
    return yOffset;
  }
}
