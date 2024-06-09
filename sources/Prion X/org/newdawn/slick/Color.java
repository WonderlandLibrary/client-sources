package org.newdawn.slick;

import java.io.Serializable;
import java.nio.FloatBuffer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;








public class Color
  implements Serializable
{
  private static final long serialVersionUID = 1393939L;
  protected transient SGL GL = Renderer.get();
  

  public static final Color transparent = new Color(0.0F, 0.0F, 0.0F, 0.0F);
  
  public static final Color white = new Color(1.0F, 1.0F, 1.0F, 1.0F);
  
  public static final Color yellow = new Color(1.0F, 1.0F, 0.0F, 1.0F);
  
  public static final Color red = new Color(1.0F, 0.0F, 0.0F, 1.0F);
  
  public static final Color blue = new Color(0.0F, 0.0F, 1.0F, 1.0F);
  
  public static final Color green = new Color(0.0F, 1.0F, 0.0F, 1.0F);
  
  public static final Color black = new Color(0.0F, 0.0F, 0.0F, 1.0F);
  
  public static final Color gray = new Color(0.5F, 0.5F, 0.5F, 1.0F);
  
  public static final Color cyan = new Color(0.0F, 1.0F, 1.0F, 1.0F);
  
  public static final Color darkGray = new Color(0.3F, 0.3F, 0.3F, 1.0F);
  
  public static final Color lightGray = new Color(0.7F, 0.7F, 0.7F, 1.0F);
  
  public static final Color pink = new Color(255, 175, 175, 255);
  
  public static final Color orange = new Color(255, 200, 0, 255);
  
  public static final Color magenta = new Color(255, 0, 255, 255);
  

  public float r;
  
  public float g;
  
  public float b;
  
  public float a = 1.0F;
  




  public Color(Color color)
  {
    r = r;
    g = g;
    b = b;
    a = a;
  }
  




  public Color(FloatBuffer buffer)
  {
    r = buffer.get();
    g = buffer.get();
    b = buffer.get();
    a = buffer.get();
  }
  






  public Color(float r, float g, float b)
  {
    this.r = r;
    this.g = g;
    this.b = b;
    a = 1.0F;
  }
  







  public Color(float r, float g, float b, float a)
  {
    this.r = Math.min(r, 1.0F);
    this.g = Math.min(g, 1.0F);
    this.b = Math.min(b, 1.0F);
    this.a = Math.min(a, 1.0F);
  }
  






  public Color(int r, int g, int b)
  {
    this.r = (r / 255.0F);
    this.g = (g / 255.0F);
    this.b = (b / 255.0F);
    a = 1.0F;
  }
  







  public Color(int r, int g, int b, int a)
  {
    this.r = (r / 255.0F);
    this.g = (g / 255.0F);
    this.b = (b / 255.0F);
    this.a = (a / 255.0F);
  }
  






  public Color(int value)
  {
    int r = (value & 0xFF0000) >> 16;
    int g = (value & 0xFF00) >> 8;
    int b = value & 0xFF;
    int a = (value & 0xFF000000) >> 24;
    
    if (a < 0) {
      a += 256;
    }
    if (a == 0) {
      a = 255;
    }
    
    this.r = (r / 255.0F);
    this.g = (g / 255.0F);
    this.b = (b / 255.0F);
    this.a = (a / 255.0F);
  }
  






  public static Color decode(String nm)
  {
    return new Color(Integer.decode(nm).intValue());
  }
  


  public void bind()
  {
    GL.glColor4f(r, g, b, a);
  }
  


  public int hashCode()
  {
    return (int)(r + g + b + a) * 255;
  }
  


  public boolean equals(Object other)
  {
    if ((other instanceof Color)) {
      Color o = (Color)other;
      return (r == r) && (g == g) && (b == b) && (a == a);
    }
    
    return false;
  }
  


  public String toString()
  {
    return "Color (" + r + "," + g + "," + b + "," + a + ")";
  }
  




  public Color darker()
  {
    return darker(0.5F);
  }
  





  public Color darker(float scale)
  {
    scale = 1.0F - scale;
    Color temp = new Color(r * scale, g * scale, b * scale, a);
    
    return temp;
  }
  




  public Color brighter()
  {
    return brighter(0.2F);
  }
  




  public int getRed()
  {
    return (int)(r * 255.0F);
  }
  




  public int getGreen()
  {
    return (int)(g * 255.0F);
  }
  




  public int getBlue()
  {
    return (int)(b * 255.0F);
  }
  




  public int getAlpha()
  {
    return (int)(a * 255.0F);
  }
  




  public int getRedByte()
  {
    return (int)(r * 255.0F);
  }
  




  public int getGreenByte()
  {
    return (int)(g * 255.0F);
  }
  




  public int getBlueByte()
  {
    return (int)(b * 255.0F);
  }
  




  public int getAlphaByte()
  {
    return (int)(a * 255.0F);
  }
  





  public Color brighter(float scale)
  {
    scale += 1.0F;
    Color temp = new Color(r * scale, g * scale, b * scale, a);
    
    return temp;
  }
  





  public Color multiply(Color c)
  {
    return new Color(r * r, g * g, b * b, a * a);
  }
  




  public void add(Color c)
  {
    r += r;
    g += g;
    b += b;
    a += a;
  }
  




  public void scale(float value)
  {
    r *= value;
    g *= value;
    b *= value;
    a *= value;
  }
  





  public Color addToCopy(Color c)
  {
    Color copy = new Color(r, g, b, a);
    r += r;
    g += g;
    b += b;
    a += a;
    
    return copy;
  }
  





  public Color scaleCopy(float value)
  {
    Color copy = new Color(r, g, b, a);
    r *= value;
    g *= value;
    b *= value;
    a *= value;
    
    return copy;
  }
}
