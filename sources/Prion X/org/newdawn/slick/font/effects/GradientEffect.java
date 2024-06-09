package org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;







public class GradientEffect
  implements ConfigurableEffect
{
  private Color topColor = Color.cyan;
  
  private Color bottomColor = Color.blue;
  
  private int offset = 0;
  
  private float scale = 1.0F;
  



  private boolean cyclic;
  




  public GradientEffect() {}
  



  public GradientEffect(Color topColor, Color bottomColor, float scale)
  {
    this.topColor = topColor;
    this.bottomColor = bottomColor;
    this.scale = scale;
  }
  


  public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
  {
    int ascent = unicodeFont.getAscent();
    float height = ascent * scale;
    float top = -glyph.getYOffset() + unicodeFont.getDescent() + offset + ascent / 2 - height / 2.0F;
    g.setPaint(new GradientPaint(0.0F, top, topColor, 0.0F, top + height, bottomColor, cyclic));
    g.fill(glyph.getShape());
  }
  




  public Color getTopColor()
  {
    return topColor;
  }
  




  public void setTopColor(Color topColor)
  {
    this.topColor = topColor;
  }
  




  public Color getBottomColor()
  {
    return bottomColor;
  }
  




  public void setBottomColor(Color bottomColor)
  {
    this.bottomColor = bottomColor;
  }
  




  public int getOffset()
  {
    return offset;
  }
  





  public void setOffset(int offset)
  {
    this.offset = offset;
  }
  




  public float getScale()
  {
    return scale;
  }
  





  public void setScale(float scale)
  {
    this.scale = scale;
  }
  




  public boolean isCyclic()
  {
    return cyclic;
  }
  




  public void setCyclic(boolean cyclic)
  {
    this.cyclic = cyclic;
  }
  


  public String toString()
  {
    return "Gradient";
  }
  


  public List getValues()
  {
    List values = new ArrayList();
    values.add(EffectUtil.colorValue("Top color", topColor));
    values.add(EffectUtil.colorValue("Bottom color", bottomColor));
    values.add(EffectUtil.intValue("Offset", offset, 
      "This setting allows you to move the gradient up or down. The gradient is normally centered on the glyph."));
    values.add(EffectUtil.floatValue("Scale", scale, 0.0F, 1.0F, "This setting allows you to change the height of the gradient by apercentage. The gradient is normally the height of most glyphs in the font."));
    
    values.add(EffectUtil.booleanValue("Cyclic", cyclic, "If this setting is checked, the gradient will repeat."));
    return values;
  }
  


  public void setValues(List values)
  {
    for (Iterator iter = values.iterator(); iter.hasNext();) {
      ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
      if (value.getName().equals("Top color")) {
        topColor = ((Color)value.getObject());
      } else if (value.getName().equals("Bottom color")) {
        bottomColor = ((Color)value.getObject());
      } else if (value.getName().equals("Offset")) {
        offset = ((Integer)value.getObject()).intValue();
      } else if (value.getName().equals("Scale")) {
        scale = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("Cyclic")) {
        cyclic = ((Boolean)value.getObject()).booleanValue();
      }
    }
  }
}
