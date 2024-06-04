package org.lwjgl.util.glu;

import org.lwjgl.opengl.GL11;



































































public class Sphere
  extends Quadric
{
  public Sphere() {}
  
  public void draw(float radius, int slices, int stacks)
  {
    boolean normals = this.normals != 100002;
    float nsign;
    float nsign; if (orientation == 100021) {
      nsign = -1.0F;
    } else {
      nsign = 1.0F;
    }
    
    float drho = 3.1415927F / stacks;
    float dtheta = 6.2831855F / slices;
    int i;
    int j; if (drawStyle == 100012) {
      if (!textureFlag)
      {
        GL11.glBegin(6);
        GL11.glNormal3f(0.0F, 0.0F, 1.0F);
        GL11.glVertex3f(0.0F, 0.0F, nsign * radius);
        for (int j = 0; j <= slices; j++) {
          float theta = j == slices ? 0.0F : j * dtheta;
          float x = -sin(theta) * sin(drho);
          float y = cos(theta) * sin(drho);
          float z = nsign * cos(drho);
          if (normals) {
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          }
          GL11.glVertex3f(x * radius, y * radius, z * radius);
        }
        GL11.glEnd();
      }
      
      float ds = 1.0F / slices;
      float dt = 1.0F / stacks;
      float t = 1.0F;
      int imax; int imin; int imax; if (textureFlag) {
        int imin = 0;
        imax = stacks;
      } else {
        imin = 1;
        imax = stacks - 1;
      }
      

      for (int i = imin; i < imax; i++) {
        float rho = i * drho;
        GL11.glBegin(8);
        float s = 0.0F;
        for (int j = 0; j <= slices; j++) {
          float theta = j == slices ? 0.0F : j * dtheta;
          float x = -sin(theta) * sin(rho);
          float y = cos(theta) * sin(rho);
          float z = nsign * cos(rho);
          if (normals) {
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          }
          TXTR_COORD(s, t);
          GL11.glVertex3f(x * radius, y * radius, z * radius);
          x = -sin(theta) * sin(rho + drho);
          y = cos(theta) * sin(rho + drho);
          z = nsign * cos(rho + drho);
          if (normals) {
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          }
          TXTR_COORD(s, t - dt);
          s += ds;
          GL11.glVertex3f(x * radius, y * radius, z * radius);
        }
        GL11.glEnd();
        t -= dt;
      }
      
      if (!textureFlag)
      {
        GL11.glBegin(6);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        GL11.glVertex3f(0.0F, 0.0F, -radius * nsign);
        float rho = 3.1415927F - drho;
        float s = 1.0F;
        for (int j = slices; j >= 0; j--) {
          float theta = j == slices ? 0.0F : j * dtheta;
          float x = -sin(theta) * sin(rho);
          float y = cos(theta) * sin(rho);
          float z = nsign * cos(rho);
          if (normals)
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          s -= ds;
          GL11.glVertex3f(x * radius, y * radius, z * radius);
        }
        GL11.glEnd();
      }
    } else if ((drawStyle == 100011) || (drawStyle == 100013))
    {


      for (i = 1; 
          i < stacks; 
          i++) {
        float rho = i * drho;
        GL11.glBegin(2);
        for (int j = 0; j < slices; j++) {
          float theta = j * dtheta;
          float x = cos(theta) * sin(rho);
          float y = sin(theta) * sin(rho);
          float z = cos(rho);
          if (normals)
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          GL11.glVertex3f(x * radius, y * radius, z * radius);
        }
        GL11.glEnd();
      }
      
      for (j = 0; j < slices;) {
        float theta = j * dtheta;
        GL11.glBegin(3);
        for (i = 0; i <= stacks; i++) {
          float rho = i * drho;
          float x = cos(theta) * sin(rho);
          float y = sin(theta) * sin(rho);
          float z = cos(rho);
          if (normals)
            GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
          GL11.glVertex3f(x * radius, y * radius, z * radius);
        }
        GL11.glEnd();j++; continue;
        
        if (drawStyle == 100010)
        {
          GL11.glBegin(0);
          if (normals)
            GL11.glNormal3f(0.0F, 0.0F, nsign);
          GL11.glVertex3f(0.0F, 0.0F, radius);
          if (normals)
            GL11.glNormal3f(0.0F, 0.0F, -nsign);
          GL11.glVertex3f(0.0F, 0.0F, -radius);
          

          for (int i = 1; i < stacks - 1; i++) {
            float rho = i * drho;
            for (int j = 0; j < slices; j++) {
              float theta = j * dtheta;
              float x = cos(theta) * sin(rho);
              float y = sin(theta) * sin(rho);
              float z = cos(rho);
              if (normals)
                GL11.glNormal3f(x * nsign, y * nsign, z * nsign);
              GL11.glVertex3f(x * radius, y * radius, z * radius);
            }
          }
          GL11.glEnd();
        }
      }
    }
  }
}
