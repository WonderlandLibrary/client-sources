package org.newdawn.slick.gui;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;

public abstract interface GUIContext
{
  public abstract Input getInput();
  
  public abstract long getTime();
  
  public abstract int getScreenWidth();
  
  public abstract int getScreenHeight();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract Font getDefaultFont();
  
  public abstract void setMouseCursor(String paramString, int paramInt1, int paramInt2)
    throws SlickException;
  
  public abstract void setMouseCursor(ImageData paramImageData, int paramInt1, int paramInt2)
    throws SlickException;
  
  public abstract void setMouseCursor(Cursor paramCursor, int paramInt1, int paramInt2)
    throws SlickException;
  
  public abstract void setDefaultMouseCursor();
}
