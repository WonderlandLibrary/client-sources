package me.connorm.Nodus.module.core;

public abstract interface IModule
{
  public abstract String getName();
  
  public abstract Category getCategory();
  
  public abstract int getKeyBind();
  
  public abstract boolean isToggled();
  
  public abstract byte getSerialID();
  
  public abstract void setKeyBind(int paramInt);
  
  public abstract void setToggled(boolean paramBoolean);
  
  public abstract void setSerialID(byte paramByte);
  
  public abstract void toggleModule();
  
  public abstract void onToggle();
  
  public abstract void onEnable();
  
  public abstract void onDisable();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.core.IModule
 * JD-Core Version:    0.7.0.1
 */