package org.lwjgl.input;

public abstract interface Controller
{
  public abstract String getName();
  
  public abstract int getIndex();
  
  public abstract int getButtonCount();
  
  public abstract String getButtonName(int paramInt);
  
  public abstract boolean isButtonPressed(int paramInt);
  
  public abstract void poll();
  
  public abstract float getPovX();
  
  public abstract float getPovY();
  
  public abstract float getDeadZone(int paramInt);
  
  public abstract void setDeadZone(int paramInt, float paramFloat);
  
  public abstract int getAxisCount();
  
  public abstract String getAxisName(int paramInt);
  
  public abstract float getAxisValue(int paramInt);
  
  public abstract float getXAxisValue();
  
  public abstract float getXAxisDeadZone();
  
  public abstract void setXAxisDeadZone(float paramFloat);
  
  public abstract float getYAxisValue();
  
  public abstract float getYAxisDeadZone();
  
  public abstract void setYAxisDeadZone(float paramFloat);
  
  public abstract float getZAxisValue();
  
  public abstract float getZAxisDeadZone();
  
  public abstract void setZAxisDeadZone(float paramFloat);
  
  public abstract float getRXAxisValue();
  
  public abstract float getRXAxisDeadZone();
  
  public abstract void setRXAxisDeadZone(float paramFloat);
  
  public abstract float getRYAxisValue();
  
  public abstract float getRYAxisDeadZone();
  
  public abstract void setRYAxisDeadZone(float paramFloat);
  
  public abstract float getRZAxisValue();
  
  public abstract float getRZAxisDeadZone();
  
  public abstract void setRZAxisDeadZone(float paramFloat);
  
  public abstract int getRumblerCount();
  
  public abstract String getRumblerName(int paramInt);
  
  public abstract void setRumblerStrength(int paramInt, float paramFloat);
}
