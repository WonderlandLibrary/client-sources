package org.lwjgl.util.glu;

import org.lwjgl.opengl.GL11;

































































public class Disk
  extends Quadric
{
  public Disk() {}
  
  public void draw(float innerRadius, float outerRadius, int slices, int loops)
  {
    if (normals != 100002) {
      if (orientation == 100020) {
        GL11.glNormal3f(0.0F, 0.0F, 1.0F);
      }
      else {
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      }
    }
    
    float da = 6.2831855F / slices;
    float dr = (outerRadius - innerRadius) / loops;
    float dtc;
    float r1; int l; switch (drawStyle)
    {




    case 100012: 
      dtc = 2.0F * outerRadius;
      
      r1 = innerRadius;
      
      for (l = 0; l < loops;) {
        float r2 = r1 + dr;
        if (orientation == 100020)
        {
          GL11.glBegin(8);
          for (int s = 0; s <= slices; s++) { float a;
            float a;
            if (s == slices) {
              a = 0.0F;
            } else
              a = s * da;
            float sa = sin(a);
            float ca = cos(a);
            TXTR_COORD(0.5F + sa * r2 / dtc, 0.5F + ca * r2 / dtc);
            GL11.glVertex2f(r2 * sa, r2 * ca);
            TXTR_COORD(0.5F + sa * r1 / dtc, 0.5F + ca * r1 / dtc);
            GL11.glVertex2f(r1 * sa, r1 * ca);
          }
          GL11.glEnd();
        }
        else
        {
          GL11.glBegin(8);
          for (int s = slices; s >= 0; s--) { float a;
            float a;
            if (s == slices) {
              a = 0.0F;
            } else
              a = s * da;
            float sa = sin(a);
            float ca = cos(a);
            TXTR_COORD(0.5F - sa * r2 / dtc, 0.5F + ca * r2 / dtc);
            GL11.glVertex2f(r2 * sa, r2 * ca);
            TXTR_COORD(0.5F - sa * r1 / dtc, 0.5F + ca * r1 / dtc);
            GL11.glVertex2f(r1 * sa, r1 * ca);
          }
          GL11.glEnd();
        }
        r1 = r2;l++; continue;
        






        for (l = 0; l <= loops; l++) {
          float r = innerRadius + l * dr;
          GL11.glBegin(2);
          for (int s = 0; s < slices; s++) {
            float a = s * da;
            GL11.glVertex2f(r * sin(a), r * cos(a));
          }
          GL11.glEnd();
        }
        
        for (s = 0; s < slices;) {
          float a = s * da;
          float x = sin(a);
          float y = cos(a);
          GL11.glBegin(3);
          for (l = 0; l <= loops; l++) {
            float r = innerRadius + l * dr;
            GL11.glVertex2f(r * x, r * y);
          }
          GL11.glEnd();s++; continue;
          





          GL11.glBegin(0);
          for (int s = 0; s < slices; s++) {
            float a = s * da;
            float x = sin(a);
            float y = cos(a);
            
            for (int l = 0; l <= loops; l++) {
              float r = innerRadius * l * dr;
              GL11.glVertex2f(r * x, r * y);
            }
          }
          GL11.glEnd();
          break;
          


          if (innerRadius != 0.0D)
          {
            GL11.glBegin(2);
            for (float a = 0.0F; a < 6.2831854820251465D; a += da) {
              float x = innerRadius * sin(a);
              float y = innerRadius * cos(a);
              GL11.glVertex2f(x, y);
            }
            GL11.glEnd();
          }
          

          GL11.glBegin(2);
          for (float a = 0.0F; a < 6.2831855F; a += da) {
            float x = outerRadius * sin(a);
            float y = outerRadius * cos(a);
            GL11.glVertex2f(x, y);
          }
          GL11.glEnd();
          
          break; return;
        }
      }
    }
    
    int l;
    int s;
  }
}
