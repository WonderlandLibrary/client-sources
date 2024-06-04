package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

































final class MacOSXCanvasListener
  implements ComponentListener, HierarchyListener
{
  private final Canvas canvas;
  private int width;
  private int height;
  private boolean context_update;
  private boolean resized;
  
  MacOSXCanvasListener(Canvas canvas)
  {
    this.canvas = canvas;
    canvas.addComponentListener(this);
    canvas.addHierarchyListener(this);
    setUpdate();
  }
  
  public void disableListeners()
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        canvas.removeComponentListener(MacOSXCanvasListener.this);
        canvas.removeHierarchyListener(MacOSXCanvasListener.this);
      }
    });
  }
  
  public boolean syncShouldUpdateContext() {
    boolean should_update;
    synchronized (this) {
      should_update = context_update;
      context_update = false;
    }
    return should_update;
  }
  
  private synchronized void setUpdate() {
    synchronized (this) {
      width = canvas.getWidth();
      height = canvas.getHeight();
      context_update = true;
    }
  }
  
  public int syncGetWidth() {
    synchronized (this) {
      return width;
    }
  }
  
  public int syncGetHeight() {
    synchronized (this) {
      return height;
    }
  }
  

  public void componentShown(ComponentEvent e) {}
  
  public void componentHidden(ComponentEvent e) {}
  
  public void componentResized(ComponentEvent e)
  {
    setUpdate();
    resized = true;
  }
  
  public void componentMoved(ComponentEvent e) {
    setUpdate();
  }
  
  public void hierarchyChanged(HierarchyEvent e) {
    setUpdate();
  }
  
  public boolean wasResized() {
    if (resized) {
      resized = false;
      return true;
    }
    
    return false;
  }
}
