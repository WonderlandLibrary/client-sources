package org.newdawn.slick.font.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;








public class ShadowEffect
  implements ConfigurableEffect
{
  public static final int NUM_KERNELS = 16;
  public static final float[][] GAUSSIAN_BLUR_KERNELS = generateGaussianBlurKernels(16);
  

  private Color color = Color.black;
  
  private float opacity = 0.6F;
  
  private float xDistance = 2.0F;
  
  private float yDistance = 2.0F;
  
  private int blurKernelSize = 0;
  
  private int blurPasses = 1;
  






  public ShadowEffect() {}
  





  public ShadowEffect(Color color, int xDistance, int yDistance, float opacity)
  {
    this.color = color;
    this.xDistance = xDistance;
    this.yDistance = yDistance;
    this.opacity = opacity;
  }
  


  public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
  {
    g = (Graphics2D)g.create();
    g.translate(xDistance, yDistance);
    g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(opacity * 255.0F)));
    g.fill(glyph.getShape());
    

    for (Iterator iter = unicodeFont.getEffects().iterator(); iter.hasNext();) {
      Effect effect = (Effect)iter.next();
      if ((effect instanceof OutlineEffect)) {
        Composite composite = g.getComposite();
        g.setComposite(AlphaComposite.Src);
        
        g.setStroke(((OutlineEffect)effect).getStroke());
        g.draw(glyph.getShape());
        
        g.setComposite(composite);
        break;
      }
    }
    
    g.dispose();
    if ((blurKernelSize > 1) && (blurKernelSize < 16) && (blurPasses > 0)) { blur(image);
    }
  }
  



  private void blur(BufferedImage image)
  {
    float[] matrix = GAUSSIAN_BLUR_KERNELS[(blurKernelSize - 1)];
    Kernel gaussianBlur1 = new Kernel(matrix.length, 1, matrix);
    Kernel gaussianBlur2 = new Kernel(1, matrix.length, matrix);
    RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    ConvolveOp gaussianOp1 = new ConvolveOp(gaussianBlur1, 1, hints);
    ConvolveOp gaussianOp2 = new ConvolveOp(gaussianBlur2, 1, hints);
    BufferedImage scratchImage = EffectUtil.getScratchImage();
    for (int i = 0; i < blurPasses; i++) {
      gaussianOp1.filter(image, scratchImage);
      gaussianOp2.filter(scratchImage, image);
    }
  }
  




  public Color getColor()
  {
    return color;
  }
  




  public void setColor(Color color)
  {
    this.color = color;
  }
  





  public float getXDistance()
  {
    return xDistance;
  }
  





  public void setXDistance(float distance)
  {
    xDistance = distance;
  }
  





  public float getYDistance()
  {
    return yDistance;
  }
  





  public void setYDistance(float distance)
  {
    yDistance = distance;
  }
  




  public int getBlurKernelSize()
  {
    return blurKernelSize;
  }
  




  public void setBlurKernelSize(int blurKernelSize)
  {
    this.blurKernelSize = blurKernelSize;
  }
  




  public int getBlurPasses()
  {
    return blurPasses;
  }
  




  public void setBlurPasses(int blurPasses)
  {
    this.blurPasses = blurPasses;
  }
  




  public float getOpacity()
  {
    return opacity;
  }
  




  public void setOpacity(float opacity)
  {
    this.opacity = opacity;
  }
  


  public String toString()
  {
    return "Shadow";
  }
  


  public List getValues()
  {
    List values = new ArrayList();
    values.add(EffectUtil.colorValue("Color", color));
    values.add(EffectUtil.floatValue("Opacity", opacity, 0.0F, 1.0F, "This setting sets the translucency of the shadow."));
    values.add(EffectUtil.floatValue("X distance", xDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the x axis. The glyphs will need padding so the shadow doesn't get clipped."));
    
    values.add(EffectUtil.floatValue("Y distance", yDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the y axis. The glyphs will need padding so the shadow doesn't get clipped."));
    

    List options = new ArrayList();
    options.add(new String[] { "None", "0" });
    for (int i = 2; i < 16; i++)
      options.add(new String[] { String.valueOf(i) });
    String[][] optionsArray = (String[][])options.toArray(new String[options.size()][]);
    values.add(EffectUtil.optionValue("Blur kernel size", String.valueOf(blurKernelSize), optionsArray, 
      "This setting controls how many neighboring pixels are used to blur the shadow. Set to \"None\" for no blur."));
    
    values.add(EffectUtil.intValue("Blur passes", blurPasses, 
      "The setting is the number of times to apply a blur to the shadow. Set to \"0\" for no blur."));
    return values;
  }
  


  public void setValues(List values)
  {
    for (Iterator iter = values.iterator(); iter.hasNext();) {
      ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
      if (value.getName().equals("Color")) {
        color = ((Color)value.getObject());
      } else if (value.getName().equals("Opacity")) {
        opacity = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("X distance")) {
        xDistance = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("Y distance")) {
        yDistance = ((Float)value.getObject()).floatValue();
      } else if (value.getName().equals("Blur kernel size")) {
        blurKernelSize = Integer.parseInt((String)value.getObject());
      } else if (value.getName().equals("Blur passes")) {
        blurPasses = ((Integer)value.getObject()).intValue();
      }
    }
  }
  





  private static float[][] generateGaussianBlurKernels(int level)
  {
    float[][] pascalsTriangle = generatePascalsTriangle(level);
    float[][] gaussianTriangle = new float[pascalsTriangle.length][];
    for (int i = 0; i < gaussianTriangle.length; i++) {
      float total = 0.0F;
      gaussianTriangle[i] = new float[pascalsTriangle[i].length];
      for (int j = 0; j < pascalsTriangle[i].length; j++)
        total += pascalsTriangle[i][j];
      float coefficient = 1.0F / total;
      for (int j = 0; j < pascalsTriangle[i].length; j++)
        gaussianTriangle[i][j] = (coefficient * pascalsTriangle[i][j]);
    }
    return gaussianTriangle;
  }
  





  private static float[][] generatePascalsTriangle(int level)
  {
    if (level < 2) level = 2;
    float[][] triangle = new float[level][];
    triangle[0] = new float[1];
    triangle[1] = new float[2];
    triangle[0][0] = 1.0F;
    triangle[1][0] = 1.0F;
    triangle[1][1] = 1.0F;
    for (int i = 2; i < level; i++) {
      triangle[i] = new float[i + 1];
      triangle[i][0] = 1.0F;
      triangle[i][i] = 1.0F;
      for (int j = 1; j < triangle[i].length - 1; j++)
        triangle[i][j] = (triangle[(i - 1)][(j - 1)] + triangle[(i - 1)][j]);
    }
    return triangle;
  }
}
