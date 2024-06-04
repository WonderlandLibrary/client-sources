package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.Graphics;


































final class MacOSXGLCanvas
  extends Canvas
{
  private static final long serialVersionUID = 6916664741667434870L;
  private boolean canvas_painted;
  private boolean dirty;
  
  MacOSXGLCanvas() {}
  
  public void update(Graphics g)
  {
    paint(g);
  }
  
  public void paint(Graphics g) {
    synchronized (this) {
      dirty = true;
      canvas_painted = true;
    }
  }
  
  public boolean syncCanvasPainted() {
    boolean result;
    synchronized (this) {
      result = canvas_painted;
      canvas_painted = false;
    }
    return result;
  }
  
  public boolean syncIsDirty() {
    boolean result;
    synchronized (this) {
      result = dirty;
      dirty = false;
    }
    return result;
  }
}
