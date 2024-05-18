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
