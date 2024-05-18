package org.newdawn.slick;

import java.io.UnsupportedEncodingException;
import org.newdawn.slick.util.Log;



























public class SpriteSheetFont
  implements Font
{
  private SpriteSheet font;
  private char startingCharacter;
  private int charWidth;
  private int charHeight;
  private int horizontalCount;
  private int numChars;
  
  public SpriteSheetFont(SpriteSheet font, char startingCharacter)
  {
    this.font = font;
    this.startingCharacter = startingCharacter;
    horizontalCount = font.getHorizontalCount();
    int verticalCount = font.getVerticalCount();
    charWidth = (font.getWidth() / horizontalCount);
    charHeight = (font.getHeight() / verticalCount);
    numChars = (horizontalCount * verticalCount);
  }
  


  public void drawString(float x, float y, String text)
  {
    drawString(x, y, text, Color.white);
  }
  


  public void drawString(float x, float y, String text, Color col)
  {
    drawString(x, y, text, col, 0, text.length() - 1);
  }
  

  public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex)
  {
    try
    {
      byte[] data = text.getBytes("US-ASCII");
      for (int i = 0; i < data.length; i++) {
        int index = data[i] - startingCharacter;
        if (index < numChars) {
          int xPos = index % horizontalCount;
          int yPos = index / horizontalCount;
          
          if ((i >= startIndex) || (i <= endIndex))
          {
            font.getSprite(xPos, yPos).draw(x + i * charWidth, y, col);
          }
        }
      }
    }
    catch (UnsupportedEncodingException e) {
      Log.error(e);
    }
  }
  


  public int getHeight(String text)
  {
    return charHeight;
  }
  


  public int getWidth(String text)
  {
    return charWidth * text.length();
  }
  


  public int getLineHeight()
  {
    return charHeight;
  }
}
