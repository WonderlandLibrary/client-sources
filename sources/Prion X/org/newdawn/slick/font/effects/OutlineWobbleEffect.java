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




















public class OutlineWobbleEffect
  extends OutlineEffect
{
  private float detail = 1.0F;
  
  private float amplitude = 1.0F;
  


  public OutlineWobbleEffect()
  {
    setStroke(new WobbleStroke(null));
  }
  




  public float getDetail()
  {
    return detail;
  }
  




  public void setDetail(float detail)
  {
    this.detail = detail;
  }
  




  public float getAmplitude()
  {
    return amplitude;
  }
  




  public void setAmplitude(float amplitude)
  {
    this.amplitude = amplitude;
  }
  





  public OutlineWobbleEffect(int width, Color color)
  {
    super(width, color);
  }
  


  public String toString()
  {
    return "Outline (Wobble)";
  }
  


  public List getValues()
  {
    List values = super.getValues();
    values.remove(2);
    values.add(EffectUtil.floatValue("Detail", detail, 1.0F, 50.0F, "This setting controls how detailed the outline will be. Smaller numbers cause the outline to have more detail."));
    
    values.add(EffectUtil.floatValue("Amplitude", amplitude, 0.5F, 50.0F, "This setting controls the amplitude of the outline."));
    return values;
  }
  


  public void setValues(List values)
  {
    super.setValues(values);
    for (Iterator iter = values.iterator(); iter.hasNext();) {
      ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
      if (value.getName().equals("Detail")) {
        detail = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("Amplitude")) {
        amplitude = ((Float)value.getObject()).floatValue();
      }
    }
  }
  


  private class WobbleStroke
    implements Stroke
  {
    private static final float FLATNESS = 1.0F;
    


    private WobbleStroke() {}
    

    public Shape createStrokedShape(Shape shape)
    {
      GeneralPath result = new GeneralPath();
      shape = new BasicStroke(getWidth(), 2, getJoin()).createStrokedShape(shape);
      PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0D);
      float[] points = new float[6];
      float moveX = 0.0F;float moveY = 0.0F;
      float lastX = 0.0F;float lastY = 0.0F;
      float thisX = 0.0F;float thisY = 0.0F;
      int type = 0;
      float next = 0.0F;
      while (!it.isDone()) {
        type = it.currentSegment(points);
        switch (type) {
        case 0: 
          moveX = lastX = randomize(points[0]);
          moveY = lastY = randomize(points[1]);
          result.moveTo(moveX, moveY);
          next = 0.0F;
          break;
        
        case 4: 
          points[0] = moveX;
          points[1] = moveY;
        

        case 1: 
          thisX = randomize(points[0]);
          thisY = randomize(points[1]);
          float dx = thisX - lastX;
          float dy = thisY - lastY;
          float distance = (float)Math.sqrt(dx * dx + dy * dy);
          if (distance >= next) {
            float r = 1.0F / distance;
            while (distance >= next) {
              float x = lastX + next * dx * r;
              float y = lastY + next * dy * r;
              result.lineTo(randomize(x), randomize(y));
              next += detail;
            }
          }
          next -= distance;
          lastX = thisX;
          lastY = thisY;
        }
        
        it.next();
      }
      
      return result;
    }
    





    private float randomize(float x)
    {
      return x + (float)Math.random() * amplitude * 2.0F - 1.0F;
    }
  }
}
