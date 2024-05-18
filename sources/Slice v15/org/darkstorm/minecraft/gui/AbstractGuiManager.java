package org.darkstorm.minecraft.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.Theme;

public abstract class AbstractGuiManager
  implements GuiManager
{
  private final List<Frame> frames;
  protected Theme theme;
  
  public AbstractGuiManager()
  {
    frames = new CopyOnWriteArrayList();
  }
  
  public abstract void setup();
  
  public void addFrame(Frame frame)
  {
    frame.setTheme(theme);
    frames.add(0, frame);
  }
  
  public void removeFrame(Frame frame)
  {
    frames.remove(frame);
  }
  
  public Frame[] getFrames()
  {
    return (Frame[])frames.toArray(new Frame[frames.size()]);
  }
  
  public void bringForward(Frame frame)
  {
    if (frames.remove(frame)) {
      frames.add(0, frame);
    }
  }
  
  public Theme getTheme() {
    return theme;
  }
  
  public void setTheme(Theme theme)
  {
    this.theme = theme;
    for (Frame frame : frames)
      frame.setTheme(theme);
    resizeComponents();
  }
  
  protected abstract void resizeComponents();
  
  public void render()
  {
    Frame[] frames = getFrames();
    for (int i = frames.length - 1; i >= 0; i--) {
      frames[i].render();
    }
  }
  
  public void renderPinned() {
    Frame[] frames = getFrames();
    for (int i = frames.length - 1; i >= 0; i--) {
      if (frames[i].isPinned())
        frames[i].render();
    }
  }
  
  public void update() {
    Frame[] frames = getFrames();
    for (int i = frames.length - 1; i >= 0; i--) {
      frames[i].update();
    }
  }
}
