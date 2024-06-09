package org.darkstorm.minecraft.gui.component;

public abstract interface Frame
  extends Container, DraggableComponent
{
  public abstract String getTitle();
  
  public abstract void setTitle(String paramString);
  
  public abstract boolean isPinned();
  
  public abstract void setPinned(boolean paramBoolean);
  
  public abstract boolean isPinnable();
  
  public abstract void setPinnable(boolean paramBoolean);
  
  public abstract boolean isMinimized();
  
  public abstract void setMinimized(boolean paramBoolean);
  
  public abstract boolean isMinimizable();
  
  public abstract void setMinimizable(boolean paramBoolean);
  
  public abstract void close();
  
  public abstract boolean isClosable();
  
  public abstract void setClosable(boolean paramBoolean);
}
