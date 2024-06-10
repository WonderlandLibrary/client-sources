package org.newdawn.slick;

public abstract interface Font
{
  public abstract int getWidth(String paramString);
  
  public abstract int getHeight(String paramString);
  
  public abstract int getLineHeight();
  
  public abstract void drawString(float paramFloat1, float paramFloat2, String paramString);
  
  public abstract void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor);
  
  public abstract void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor, int paramInt1, int paramInt2);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Font
 * JD-Core Version:    0.7.0.1
 */