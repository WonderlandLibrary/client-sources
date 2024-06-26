package org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;







public class ColorEffect
  implements ConfigurableEffect
{
  private Color color = Color.white;
  




  public ColorEffect() {}
  




  public ColorEffect(Color color)
  {
    this.color = color;
  }
  


  public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
  {
    g.setColor(color);
    g.fill(glyph.getShape());
  }
  




  public Color getColor()
  {
    return color;
  }
  




  public void setColor(Color color)
  {
    if (color == null) throw new IllegalArgumentException("color cannot be null.");
    this.color = color;
  }
  


  public String toString()
  {
    return "Color";
  }
  


  public List getValues()
  {
    List values = new ArrayList();
    values.add(EffectUtil.colorValue("Color", color));
    return values;
  }
  


  public void setValues(List values)
  {
    for (Iterator iter = values.iterator(); iter.hasNext();) {
      ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
      if (value.getName().equals("Color")) {
        setColor((Color)value.getObject());
      }
    }
  }
}
