package org.newdawn.slick;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.BufferedImageUtil;









public class TrueTypeFont
  implements Font
{
  private static final SGL GL = ;
  

  private IntObject[] charArray = new IntObject['Ā'];
  

  private Map customChars = new HashMap();
  

  private boolean antiAlias;
  

  private int fontSize = 0;
  

  private int fontHeight = 0;
  

  private Texture fontTexture;
  

  private int textureWidth = 512;
  

  private int textureHeight = 512;
  











  private java.awt.Font font;
  










  private FontMetrics fontMetrics;
  











  public TrueTypeFont(java.awt.Font font, boolean antiAlias, char[] additionalChars)
  {
    GLUtils.checkGLContext();
    
    this.font = font;
    fontSize = font.getSize();
    this.antiAlias = antiAlias;
    
    createSet(additionalChars);
  }
  









  public TrueTypeFont(java.awt.Font font, boolean antiAlias)
  {
    this(font, antiAlias, null);
  }
  








  private BufferedImage getFontImage(char ch)
  {
    BufferedImage tempfontImage = new BufferedImage(1, 1, 
      2);
    Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
    if (antiAlias) {
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
    }
    g.setFont(font);
    fontMetrics = g.getFontMetrics();
    int charwidth = fontMetrics.charWidth(ch);
    
    if (charwidth <= 0) {
      charwidth = 1;
    }
    int charheight = fontMetrics.getHeight();
    if (charheight <= 0) {
      charheight = fontSize;
    }
    


    BufferedImage fontImage = new BufferedImage(charwidth, charheight, 
      2);
    Graphics2D gt = (Graphics2D)fontImage.getGraphics();
    if (antiAlias) {
      gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
    }
    gt.setFont(font);
    
    gt.setColor(java.awt.Color.WHITE);
    int charx = 0;
    int chary = 0;
    gt.drawString(String.valueOf(ch), charx, chary + 
      fontMetrics.getAscent());
    
    return fontImage;
  }
  






  private void createSet(char[] customCharsArray)
  {
    if ((customCharsArray != null) && (customCharsArray.length > 0)) {
      textureWidth *= 2;
    }
    




    try
    {
      BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, 2);
      Graphics2D g = (Graphics2D)imgTemp.getGraphics();
      
      g.setColor(new java.awt.Color(255, 255, 255, 1));
      g.fillRect(0, 0, textureWidth, textureHeight);
      
      int rowHeight = 0;
      int positionX = 0;
      int positionY = 0;
      
      int customCharsLength = customCharsArray != null ? customCharsArray.length : 0;
      
      for (int i = 0; i < 256 + customCharsLength; i++)
      {

        char ch = i < 256 ? (char)i : customCharsArray[(i - 256)];
        
        BufferedImage fontImage = getFontImage(ch);
        
        IntObject newIntObject = new IntObject(null);
        
        width = fontImage.getWidth();
        height = fontImage.getHeight();
        
        if (positionX + width >= textureWidth) {
          positionX = 0;
          positionY += rowHeight;
          rowHeight = 0;
        }
        
        storedX = positionX;
        storedY = positionY;
        
        if (height > fontHeight) {
          fontHeight = height;
        }
        
        if (height > rowHeight) {
          rowHeight = height;
        }
        

        g.drawImage(fontImage, positionX, positionY, null);
        
        positionX += width;
        
        if (i < 256) {
          charArray[i] = newIntObject;
        } else {
          customChars.put(new Character(ch), newIntObject);
        }
        
        fontImage = null;
      }
      
      fontTexture = 
        BufferedImageUtil.getTexture(font.toString(), imgTemp);
    }
    catch (IOException e) {
      System.err.println("Failed to create font.");
      e.printStackTrace();
    }
  }
  





















  private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2)
  {
    float DrawWidth = drawX2 - drawX;
    float DrawHeight = drawY2 - drawY;
    float TextureSrcX = srcX / textureWidth;
    float TextureSrcY = srcY / textureHeight;
    float SrcWidth = srcX2 - srcX;
    float SrcHeight = srcY2 - srcY;
    float RenderWidth = SrcWidth / textureWidth;
    float RenderHeight = SrcHeight / textureHeight;
    
    GL.glTexCoord2f(TextureSrcX, TextureSrcY);
    GL.glVertex2f(drawX, drawY);
    GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
    GL.glVertex2f(drawX, drawY + DrawHeight);
    GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
    GL.glVertex2f(drawX + DrawWidth, drawY + DrawHeight);
    GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
    GL.glVertex2f(drawX + DrawWidth, drawY);
  }
  







  public int getWidth(String whatchars)
  {
    int totalwidth = 0;
    IntObject intObject = null;
    int currentChar = 0;
    for (int i = 0; i < whatchars.length(); i++) {
      currentChar = whatchars.charAt(i);
      if (currentChar < 256) {
        intObject = charArray[currentChar];
      } else {
        intObject = (IntObject)customChars.get(new Character((char)currentChar));
      }
      
      if (intObject != null)
        totalwidth += width;
    }
    return totalwidth;
  }
  




  public int getHeight()
  {
    return fontHeight;
  }
  




  public int getHeight(String HeightString)
  {
    return fontHeight;
  }
  




  public int getLineHeight()
  {
    return fontHeight;
  }
  












  public void drawString(float x, float y, String whatchars, Color color)
  {
    drawString(x, y, whatchars, color, 0, whatchars.length() - 1);
  }
  



  public void drawString(float x, float y, String whatchars, Color color, int startIndex, int endIndex)
  {
    color.bind();
    fontTexture.bind();
    
    IntObject intObject = null;
    

    GL.glBegin(7);
    
    int totalwidth = 0;
    for (int i = 0; i < whatchars.length(); i++) {
      int charCurrent = whatchars.charAt(i);
      if (charCurrent < 256) {
        intObject = charArray[charCurrent];
      } else {
        intObject = (IntObject)customChars.get(new Character((char)charCurrent));
      }
      
      if (intObject != null) {
        if ((i >= startIndex) || (i <= endIndex)) {
          drawQuad(x + totalwidth, y, 
            x + totalwidth + width, 
            y + height, storedX, 
            storedY, storedX + width, 
            storedY + height);
        }
        totalwidth += width;
      }
    }
    
    GL.glEnd();
  }
  









  public void drawString(float x, float y, String whatchars)
  {
    drawString(x, y, whatchars, Color.white);
  }
  
  private class IntObject
  {
    public int width;
    public int height;
    public int storedX;
    public int storedY;
    
    private IntObject() {}
  }
}
