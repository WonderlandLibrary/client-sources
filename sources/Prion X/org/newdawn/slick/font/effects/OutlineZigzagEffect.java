package org.newdawn.slick.font.effects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.Iterator;
import java.util.List;




















public class OutlineZigzagEffect
  extends OutlineEffect
{
  private float amplitude = 1.0F;
  
  private float wavelength = 3.0F;
  


  public OutlineZigzagEffect()
  {
    setStroke(new ZigzagStroke(null));
  }
  




  public float getWavelength()
  {
    return wavelength;
  }
  




  public void setWavelength(float wavelength)
  {
    this.wavelength = wavelength;
  }
  




  public float getAmplitude()
  {
    return amplitude;
  }
  




  public void setAmplitude(float amplitude)
  {
    this.amplitude = amplitude;
  }
  





  public OutlineZigzagEffect(int width, Color color)
  {
    super(width, color);
  }
  


  public String toString()
  {
    return "Outline (Zigzag)";
  }
  


  public List getValues()
  {
    List values = super.getValues();
    values.add(EffectUtil.floatValue("Wavelength", wavelength, 1.0F, 100.0F, "This setting controls the wavelength of the outline. The smaller the value, the more segments will be used to draw the outline."));
    
    values.add(EffectUtil.floatValue("Amplitude", amplitude, 0.5F, 50.0F, "This setting controls the amplitude of the outline. The bigger the value, the more the zigzags will vary."));
    
    return values;
  }
  


  public void setValues(List values)
  {
    super.setValues(values);
    for (Iterator iter = values.iterator(); iter.hasNext();) {
      ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
      if (value.getName().equals("Wavelength")) {
        wavelength = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("Amplitude")) {
        amplitude = ((Float)value.getObject()).floatValue();
      }
    }
  }
  


  private class ZigzagStroke
    implements Stroke
  {
    private static final float FLATNESS = 1.0F;
    


    private ZigzagStroke() {}
    

    public Shape createStrokedShape(Shape shape)
    {
      GeneralPath result = new GeneralPath();
      PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0D);
      float[] points = new float[6];
      float moveX = 0.0F;float moveY = 0.0F;
      float lastX = 0.0F;float lastY = 0.0F;
      float thisX = 0.0F;float thisY = 0.0F;
      int type = 0;
      float next = 0.0F;
      int phase = 0;
      while (!it.isDone()) {
        type = it.currentSegment(points);
        switch (type) {
        case 0: 
          moveX = lastX = points[0];
          moveY = lastY = points[1];
          result.moveTo(moveX, moveY);
          next = wavelength / 2.0F;
          break;
        
        case 4: 
          points[0] = moveX;
          points[1] = moveY;
        

        case 1: 
          thisX = points[0];
          thisY = points[1];
          float dx = thisX - lastX;
          float dy = thisY - lastY;
          float distance = (float)Math.sqrt(dx * dx + dy * dy);
          if (distance >= next) {
            float r = 1.0F / distance;
            while (distance >= next) {
              float x = lastX + next * dx * r;
              float y = lastY + next * dy * r;
              if ((phase & 0x1) == 0) {
                result.lineTo(x + amplitude * dy * r, y - amplitude * dx * r);
              } else
                result.lineTo(x - amplitude * dy * r, y + amplitude * dx * r);
              next += wavelength;
              phase++;
            }
          }
          next -= distance;
          lastX = thisX;
          lastY = thisY;
          if (type == 4) result.closePath();
          break;
        }
        it.next();
      }
      return new BasicStroke(getWidth(), 2, getJoin()).createStrokedShape(result);
    }
  }
}
