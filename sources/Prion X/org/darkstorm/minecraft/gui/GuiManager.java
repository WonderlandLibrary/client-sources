package org.darkstorm.minecraft.gui;

import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.Theme;

public abstract interface GuiManager
{
  public abstract void setup();
  
  public abstract void addFrame(Frame paramFrame);
  
  public abstract void removeFrame(Frame paramFrame);
  
  public abstract Frame[] getFrames();
  
  public abstract void bringForward(Frame paramFrame);
  
  public abstract Theme getTheme();
  
  public abstract void setTheme(Theme paramTheme);
  
  public abstract void render();
  
  public abstract void renderPinned();
  
  public abstract void update();
}
