package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;












public class PackedSpriteSheet
{
  private Image image;
  private String basePath;
  private HashMap sections = new HashMap();
  
  private int filter = 2;
  




  public PackedSpriteSheet(String def)
    throws SlickException
  {
    this(def, null);
  }
  





  public PackedSpriteSheet(String def, Color trans)
    throws SlickException
  {
    def = def.replace('\\', '/');
    basePath = def.substring(0, def.lastIndexOf("/") + 1);
    
    loadDefinition(def, trans);
  }
  





  public PackedSpriteSheet(String def, int filter)
    throws SlickException
  {
    this(def, filter, null);
  }
  






  public PackedSpriteSheet(String def, int filter, Color trans)
    throws SlickException
  {
    this.filter = filter;
    
    def = def.replace('\\', '/');
    basePath = def.substring(0, def.lastIndexOf("/") + 1);
    
    loadDefinition(def, trans);
  }
  




  public Image getFullImage()
  {
    return image;
  }
  





  public Image getSprite(String name)
  {
    Section section = (Section)sections.get(name);
    
    if (section == null) {
      throw new RuntimeException("Unknown sprite from packed sheet: " + name);
    }
    
    return image.getSubImage(x, y, width, height);
  }
  





  public SpriteSheet getSpriteSheet(String name)
  {
    Image image = getSprite(name);
    Section section = (Section)sections.get(name);
    
    return new SpriteSheet(image, width / tilesx, height / tilesy);
  }
  






  private void loadDefinition(String def, Color trans)
    throws SlickException
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(def)));
    try
    {
      image = new Image(basePath + reader.readLine(), false, filter, trans);
      while (reader.ready()) {
        if (reader.readLine() == null) {
          break;
        }
        
        Section sect = new Section(reader);
        sections.put(name, sect);
        
        if (reader.readLine() == null) {
          break;
        }
      }
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to process definitions file - invalid format?", e);
    }
  }
  


  private class Section
  {
    public int x;
    

    public int y;
    

    public int width;
    

    public int height;
    

    public int tilesx;
    

    public int tilesy;
    

    public String name;
    

    public Section(BufferedReader reader)
      throws IOException
    {
      name = reader.readLine().trim();
      
      x = Integer.parseInt(reader.readLine().trim());
      y = Integer.parseInt(reader.readLine().trim());
      width = Integer.parseInt(reader.readLine().trim());
      height = Integer.parseInt(reader.readLine().trim());
      tilesx = Integer.parseInt(reader.readLine().trim());
      tilesy = Integer.parseInt(reader.readLine().trim());
      reader.readLine().trim();
      reader.readLine().trim();
      
      tilesx = Math.max(1, tilesx);
      tilesy = Math.max(1, tilesy);
    }
  }
}
