package org.lwjgl.util.glu;

import org.lwjgl.opengl.GL11;












































public class Quadric
{
  protected int drawStyle;
  protected int orientation;
  protected boolean textureFlag;
  protected int normals;
  
  public Quadric()
  {
    drawStyle = 100012;
    orientation = 100020;
    textureFlag = false;
    normals = 100000;
  }
  








  protected void normal3f(float x, float y, float z)
  {
    float mag = (float)Math.sqrt(x * x + y * y + z * z);
    if (mag > 1.0E-5F) {
      x /= mag;
      y /= mag;
      z /= mag;
    }
    GL11.glNormal3f(x, y, z);
  }
  

















  public void setDrawStyle(int drawStyle)
  {
    this.drawStyle = drawStyle;
  }
  












  public void setNormals(int normals)
  {
    this.normals = normals;
  }
  












  public void setOrientation(int orientation)
  {
    this.orientation = orientation;
  }
  










  public void setTextureFlag(boolean textureFlag)
  {
    this.textureFlag = textureFlag;
  }
  




  public int getDrawStyle()
  {
    return drawStyle;
  }
  



  public int getNormals()
  {
    return normals;
  }
  



  public int getOrientation()
  {
    return orientation;
  }
  



  public boolean getTextureFlag()
  {
    return textureFlag;
  }
  
  protected void TXTR_COORD(float x, float y) {
    if (textureFlag) GL11.glTexCoord2f(x, y);
  }
  
  protected float sin(float r)
  {
    return (float)Math.sin(r);
  }
  
  protected float cos(float r) {
    return (float)Math.cos(r);
  }
}
