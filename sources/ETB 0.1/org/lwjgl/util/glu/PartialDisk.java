package org.lwjgl.util.glu;

import java.io.PrintStream;
import org.lwjgl.opengl.GL11;










































































public class PartialDisk
  extends Quadric
{
  private static final int CACHE_SIZE = 240;
  
  public PartialDisk() {}
  
  public void draw(float innerRadius, float outerRadius, int slices, int loops, float startAngle, float sweepAngle)
  {
    float[] sinCache = new float['ð'];
    float[] cosCache = new float['ð'];
    



    float texLow = 0.0F;float texHigh = 0.0F;
    



    if (slices >= 240)
      slices = 239;
    if ((slices < 2) || (loops < 1) || (outerRadius <= 0.0F) || (innerRadius < 0.0F) || (innerRadius > outerRadius))
    {




      System.err.println("PartialDisk: GLU_INVALID_VALUE");
      return;
    }
    
    if (sweepAngle < -360.0F)
      sweepAngle = 360.0F;
    if (sweepAngle > 360.0F)
      sweepAngle = 360.0F;
    if (sweepAngle < 0.0F) {
      startAngle += sweepAngle;
      sweepAngle = -sweepAngle; }
    int slices2;
    int slices2;
    if (sweepAngle == 360.0F) {
      slices2 = slices;
    } else {
      slices2 = slices + 1;
    }
    

    float deltaRadius = outerRadius - innerRadius;
    


    float angleOffset = startAngle / 180.0F * 3.1415927F;
    for (int i = 0; i <= slices; i++) {
      float angle = angleOffset + 3.1415927F * sweepAngle / 180.0F * i / slices;
      sinCache[i] = sin(angle);
      cosCache[i] = cos(angle);
    }
    
    if (sweepAngle == 360.0F) {
      sinCache[slices] = sinCache[0];
      cosCache[slices] = cosCache[0];
    }
    
    switch (normals) {
    case 100000: 
    case 100001: 
      if (orientation == 100020) {
        GL11.glNormal3f(0.0F, 0.0F, 1.0F);
      } else {
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      }
      break;
    }
    
    
    int j;
    
    switch (drawStyle) {
    case 100012:  int finish;
      if (innerRadius == 0.0F) {
        int finish = loops - 1;
        
        GL11.glBegin(6);
        if (textureFlag) {
          GL11.glTexCoord2f(0.5F, 0.5F);
        }
        GL11.glVertex3f(0.0F, 0.0F, 0.0F);
        float radiusLow = outerRadius - deltaRadius * ((loops - 1) / loops);
        if (textureFlag) {
          texLow = radiusLow / outerRadius / 2.0F;
        }
        
        if (orientation == 100020) {
          for (i = slices; i >= 0; i--) {
            if (textureFlag) {
              GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
          }
        }
        for (i = 0; i <= slices; i++) {
          if (textureFlag) {
            GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
          }
          

          GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
        }
        
        GL11.glEnd();
      } else {
        finish = loops;
      }
      for (j = 0; j < finish;) {
        float radiusLow = outerRadius - deltaRadius * (j / loops);
        float radiusHigh = outerRadius - deltaRadius * ((j + 1) / loops);
        if (textureFlag) {
          texLow = radiusLow / outerRadius / 2.0F;
          texHigh = radiusHigh / outerRadius / 2.0F;
        }
        
        GL11.glBegin(8);
        for (i = 0; i <= slices; i++) {
          if (orientation == 100020) {
            if (textureFlag) {
              GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
            
            if (textureFlag) {
              GL11.glTexCoord2f(texHigh * sinCache[i] + 0.5F, texHigh * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusHigh * sinCache[i], radiusHigh * cosCache[i], 0.0F);

          }
          else
          {
            if (textureFlag) {
              GL11.glTexCoord2f(texHigh * sinCache[i] + 0.5F, texHigh * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusHigh * sinCache[i], radiusHigh * cosCache[i], 0.0F);
            



            if (textureFlag) {
              GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
          }
        }
        GL11.glEnd();j++; continue;
        


        GL11.glBegin(0);
        for (i = 0; i < slices2; i++) {
          float sintemp = sinCache[i];
          float costemp = cosCache[i];
          for (j = 0; j <= loops; j++) {
            radiusLow = outerRadius - deltaRadius * (j / loops);
            
            if (textureFlag) {
              texLow = radiusLow / outerRadius / 2.0F;
              
              GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
            }
            

            GL11.glVertex3f(radiusLow * sintemp, radiusLow * costemp, 0.0F);
          }
        }
        GL11.glEnd();
        break;
        
        if (innerRadius == outerRadius) {
          GL11.glBegin(3);
          
          for (i = 0; i <= slices; i++) {
            if (textureFlag) {
              GL11.glTexCoord2f(sinCache[i] / 2.0F + 0.5F, cosCache[i] / 2.0F + 0.5F);
            }
            GL11.glVertex3f(innerRadius * sinCache[i], innerRadius * cosCache[i], 0.0F);
          }
          GL11.glEnd();
        }
        else {
          for (j = 0; j <= loops; j++) {
            float radiusLow = outerRadius - deltaRadius * (j / loops);
            if (textureFlag) {
              texLow = radiusLow / outerRadius / 2.0F;
            }
            
            GL11.glBegin(3);
            for (i = 0; i <= slices; i++) {
              if (textureFlag) {
                GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
              }
              

              GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
            }
            GL11.glEnd();
          }
          for (i = 0; i < slices2;) {
            float sintemp = sinCache[i];
            float costemp = cosCache[i];
            GL11.glBegin(3);
            for (j = 0; j <= loops; j++) {
              float radiusLow = outerRadius - deltaRadius * (j / loops);
              if (textureFlag) {
                texLow = radiusLow / outerRadius / 2.0F;
              }
              
              if (textureFlag) {
                GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
              }
              

              GL11.glVertex3f(radiusLow * sintemp, radiusLow * costemp, 0.0F);
            }
            GL11.glEnd();i++; continue;
            


            if (sweepAngle < 360.0F) {
              for (i = 0; i <= slices; i += slices) {
                sintemp = sinCache[i];
                costemp = cosCache[i];
                GL11.glBegin(3);
                for (j = 0; j <= loops; j++) {
                  float radiusLow = outerRadius - deltaRadius * (j / loops);
                  
                  if (textureFlag) {
                    texLow = radiusLow / outerRadius / 2.0F;
                    GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
                  }
                  

                  GL11.glVertex3f(radiusLow * sintemp, radiusLow * costemp, 0.0F);
                }
                GL11.glEnd();
              }
            }
            for (int j = 0; j <= loops; j += loops) {
              float radiusLow = outerRadius - deltaRadius * (j / loops);
              if (textureFlag) {
                texLow = radiusLow / outerRadius / 2.0F;
              }
              
              GL11.glBegin(3);
              for (i = 0; i <= slices; i++) {
                if (textureFlag) {
                  GL11.glTexCoord2f(texLow * sinCache[i] + 0.5F, texLow * cosCache[i] + 0.5F);
                }
                

                GL11.glVertex3f(radiusLow * sinCache[i], radiusLow * cosCache[i], 0.0F);
              }
              GL11.glEnd();
              if (innerRadius == outerRadius) {
                break;
              }
            }
          }
        }
      }
    }
    int j;
  }
}
