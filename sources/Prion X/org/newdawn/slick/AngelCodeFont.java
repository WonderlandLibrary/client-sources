package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;














public class AngelCodeFont
  implements Font
{
  private static SGL GL = ;
  


  private static final int DISPLAY_LIST_CACHE_SIZE = 200;
  


  private static final int MAX_CHAR = 255;
  


  private boolean displayListCaching = true;
  

  private Image fontImage;
  
  private CharDef[] chars;
  
  private int lineHeight;
  
  private int baseDisplayListID = -1;
  

  private int eldestDisplayListID;
  
  private DisplayList eldestDisplayList;
  
  private final LinkedHashMap displayLists = new LinkedHashMap(200, 1.0F, true) {
    protected boolean removeEldestEntry(Map.Entry eldest) {
      eldestDisplayList = ((AngelCodeFont.DisplayList)eldest.getValue());
      eldestDisplayListID = eldestDisplayList.id;
      
      return false;
    }
  };
  










  public AngelCodeFont(String fntFile, Image image)
    throws SlickException
  {
    fontImage = image;
    
    parseFnt(ResourceLoader.getResourceAsStream(fntFile));
  }
  









  public AngelCodeFont(String fntFile, String imgFile)
    throws SlickException
  {
    fontImage = new Image(imgFile);
    
    parseFnt(ResourceLoader.getResourceAsStream(fntFile));
  }
  












  public AngelCodeFont(String fntFile, Image image, boolean caching)
    throws SlickException
  {
    fontImage = image;
    displayListCaching = caching;
    parseFnt(ResourceLoader.getResourceAsStream(fntFile));
  }
  












  public AngelCodeFont(String fntFile, String imgFile, boolean caching)
    throws SlickException
  {
    fontImage = new Image(imgFile);
    displayListCaching = caching;
    parseFnt(ResourceLoader.getResourceAsStream(fntFile));
  }
  












  public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile)
    throws SlickException
  {
    fontImage = new Image(imgFile, name, false);
    
    parseFnt(fntFile);
  }
  














  public AngelCodeFont(String name, InputStream fntFile, InputStream imgFile, boolean caching)
    throws SlickException
  {
    fontImage = new Image(imgFile, name, false);
    
    displayListCaching = caching;
    parseFnt(fntFile);
  }
  





  private void parseFnt(InputStream fntFile)
    throws SlickException
  {
    if (displayListCaching) {
      baseDisplayListID = GL.glGenLists(200);
      if (baseDisplayListID == 0) { displayListCaching = false;
      }
    }
    try
    {
      BufferedReader in = new BufferedReader(new InputStreamReader(
        fntFile));
      String info = in.readLine();
      String common = in.readLine();
      String page = in.readLine();
      
      Map kerning = new HashMap(64);
      List charDefs = new ArrayList(255);
      int maxChar = 0;
      boolean done = false;
      while (!done) {
        String line = in.readLine();
        if (line == null) {
          done = true;
        } else {
          if (!line.startsWith("chars c"))
          {
            if (line.startsWith("char")) {
              CharDef def = parseChar(line);
              if (def != null) {
                maxChar = Math.max(maxChar, id);
                charDefs.add(def);
              }
            } }
          if (!line.startsWith("kernings c"))
          {
            if (line.startsWith("kerning")) {
              StringTokenizer tokens = new StringTokenizer(line, " =");
              tokens.nextToken();
              tokens.nextToken();
              short first = Short.parseShort(tokens.nextToken());
              tokens.nextToken();
              int second = Integer.parseInt(tokens.nextToken());
              tokens.nextToken();
              int offset = Integer.parseInt(tokens.nextToken());
              List values = (List)kerning.get(new Short(first));
              if (values == null) {
                values = new ArrayList();
                kerning.put(new Short(first), values);
              }
              
              values.add(new Short((short)(offset << 8 | second)));
            }
          }
        }
      }
      chars = new CharDef[maxChar + 1];
      for (Iterator iter = charDefs.iterator(); iter.hasNext();) {
        CharDef def = (CharDef)iter.next();
        chars[id] = def;
      }
      

      for (Iterator iter = kerning.entrySet().iterator(); iter.hasNext();) {
        Map.Entry entry = (Map.Entry)iter.next();
        short first = ((Short)entry.getKey()).shortValue();
        List valueList = (List)entry.getValue();
        short[] valueArray = new short[valueList.size()];
        int i = 0;
        for (Iterator valueIter = valueList.iterator(); valueIter.hasNext(); i++)
          valueArray[i] = ((Short)valueIter.next()).shortValue();
        chars[first].kerning = valueArray;
      }
    } catch (IOException e) {
      Log.error(e);
      throw new SlickException("Failed to parse font file: " + fntFile);
    }
  }
  






  private CharDef parseChar(String line)
    throws SlickException
  {
    CharDef def = new CharDef(null);
    StringTokenizer tokens = new StringTokenizer(line, " =");
    
    tokens.nextToken();
    tokens.nextToken();
    id = Short.parseShort(tokens.nextToken());
    if (id < 0) {
      return null;
    }
    if (id > 255) {
      throw new SlickException("Invalid character '" + id + 
        "': AngelCodeFont does not support characters above " + 255);
    }
    
    tokens.nextToken();
    x = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    y = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    width = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    height = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    xoffset = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    yoffset = Short.parseShort(tokens.nextToken());
    tokens.nextToken();
    xadvance = Short.parseShort(tokens.nextToken());
    
    def.init();
    
    if (id != 32) {
      lineHeight = Math.max(height + yoffset, lineHeight);
    }
    
    return def;
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
    fontImage.bind();
    col.bind();
    
    GL.glTranslatef(x, y, 0.0F);
    if ((displayListCaching) && (startIndex == 0) && (endIndex == text.length() - 1)) {
      DisplayList displayList = (DisplayList)displayLists.get(text);
      if (displayList != null) {
        GL.glCallList(id);
      }
      else {
        displayList = new DisplayList(null);
        text = text;
        int displayListCount = displayLists.size();
        if (displayListCount < 200) {
          id = (baseDisplayListID + displayListCount);
        } else {
          id = eldestDisplayListID;
          displayLists.remove(eldestDisplayList.text);
        }
        
        displayLists.put(text, displayList);
        
        GL.glNewList(id, 4865);
        render(text, startIndex, endIndex);
        GL.glEndList();
      }
    } else {
      render(text, startIndex, endIndex);
    }
    GL.glTranslatef(-x, -y, 0.0F);
  }
  






  private void render(String text, int start, int end)
  {
    GL.glBegin(7);
    
    int x = 0;int y = 0;
    CharDef lastCharDef = null;
    char[] data = text.toCharArray();
    for (int i = 0; i < data.length; i++) {
      int id = data[i];
      if (id == 10) {
        x = 0;
        y += getLineHeight();

      }
      else if (id < chars.length)
      {

        CharDef charDef = chars[id];
        if (charDef != null)
        {


          if (lastCharDef != null) x += lastCharDef.getKerning(id);
          lastCharDef = charDef;
          
          if ((i >= start) && (i <= end)) {
            charDef.draw(x, y);
          }
          
          x += xadvance;
        } } }
    GL.glEnd();
  }
  






  public int getYOffset(String text)
  {
    DisplayList displayList = null;
    if (displayListCaching) {
      displayList = (DisplayList)displayLists.get(text);
      if ((displayList != null) && (yOffset != null)) { return yOffset.intValue();
      }
    }
    int stopIndex = text.indexOf('\n');
    if (stopIndex == -1) { stopIndex = text.length();
    }
    int minYOffset = 10000;
    for (int i = 0; i < stopIndex; i++) {
      int id = text.charAt(i);
      CharDef charDef = chars[id];
      if (charDef != null)
      {

        minYOffset = Math.min(yoffset, minYOffset);
      }
    }
    if (displayList != null) { yOffset = new Short((short)minYOffset);
    }
    return minYOffset;
  }
  


  public int getHeight(String text)
  {
    DisplayList displayList = null;
    if (displayListCaching) {
      displayList = (DisplayList)displayLists.get(text);
      if ((displayList != null) && (height != null)) { return height.intValue();
      }
    }
    int lines = 0;
    int maxHeight = 0;
    for (int i = 0; i < text.length(); i++) {
      int id = text.charAt(i);
      if (id == 10) {
        lines++;
        maxHeight = 0;


      }
      else if (id != 32)
      {

        CharDef charDef = chars[id];
        if (charDef != null)
        {


          maxHeight = Math.max(height + yoffset, 
            maxHeight); }
      }
    }
    maxHeight += lines * getLineHeight();
    
    if (displayList != null) { height = new Short((short)maxHeight);
    }
    return maxHeight;
  }
  


  public int getWidth(String text)
  {
    DisplayList displayList = null;
    if (displayListCaching) {
      displayList = (DisplayList)displayLists.get(text);
      if ((displayList != null) && (width != null)) { return width.intValue();
      }
    }
    int maxWidth = 0;
    int width = 0;
    CharDef lastCharDef = null;
    int i = 0; for (int n = text.length(); i < n; i++) {
      int id = text.charAt(i);
      if (id == 10) {
        width = 0;

      }
      else if (id < chars.length)
      {

        CharDef charDef = chars[id];
        if (charDef != null)
        {


          if (lastCharDef != null) width += lastCharDef.getKerning(id);
          lastCharDef = charDef;
          
          if (i < n - 1) {
            width += xadvance;
          } else {
            width += width;
          }
          maxWidth = Math.max(maxWidth, width);
        }
      } }
    if (displayList != null) { width = new Short((short)maxWidth);
    }
    return maxWidth;
  }
  


  private class CharDef
  {
    public short id;
    

    public short x;
    

    public short y;
    

    public short width;
    

    public short height;
    
    public short xoffset;
    
    public short yoffset;
    
    public short xadvance;
    
    public Image image;
    
    public short dlIndex;
    
    public short[] kerning;
    

    private CharDef() {}
    

    public void init()
    {
      image = fontImage.getSubImage(x, y, width, height);
    }
    


    public String toString()
    {
      return "[CharDef id=" + id + " x=" + x + " y=" + y + "]";
    }
    







    public void draw(float x, float y)
    {
      image.drawEmbedded(x + xoffset, y + yoffset, width, height);
    }
    




    public int getKerning(int otherCodePoint)
    {
      if (kerning == null) return 0;
      int low = 0;
      int high = kerning.length - 1;
      while (low <= high) {
        int midIndex = low + high >>> 1;
        int value = kerning[midIndex];
        int foundCodePoint = value & 0xFF;
        if (foundCodePoint < otherCodePoint) {
          low = midIndex + 1;
        } else if (foundCodePoint > otherCodePoint) {
          high = midIndex - 1;
        } else
          return value >> 8;
      }
      return 0;
    }
  }
  


  public int getLineHeight()
  {
    return lineHeight;
  }
  
  private static class DisplayList
  {
    int id;
    Short yOffset;
    Short width;
    Short height;
    String text;
    
    private DisplayList() {}
  }
}
