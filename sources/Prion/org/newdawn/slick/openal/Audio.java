package org.newdawn.slick.openal;

public abstract interface Audio
{
  public abstract void stop();
  
  public abstract int getBufferID();
  
  public abstract boolean isPlaying();
  
  public abstract int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  public abstract int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5);
  
  public abstract int playAsMusic(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  public abstract boolean setPosition(float paramFloat);
  
  public abstract float getPosition();
}
