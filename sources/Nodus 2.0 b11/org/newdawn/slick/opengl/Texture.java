package org.newdawn.slick.opengl;

public abstract interface Texture
{
  public abstract boolean hasAlpha();
  
  public abstract String getTextureRef();
  
  public abstract void bind();
  
  public abstract int getImageHeight();
  
  public abstract int getImageWidth();
  
  public abstract float getHeight();
  
  public abstract float getWidth();
  
  public abstract int getTextureHeight();
  
  public abstract int getTextureWidth();
  
  public abstract void release();
  
  public abstract int getTextureID();
  
  public abstract byte[] getTextureData();
  
  public abstract void setTextureFilter(int paramInt);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.Texture
 * JD-Core Version:    0.7.0.1
 */