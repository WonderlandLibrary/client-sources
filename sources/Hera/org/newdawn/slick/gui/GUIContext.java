package org.newdawn.slick.gui;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;

public interface GUIContext {
  Input getInput();
  
  long getTime();
  
  int getScreenWidth();
  
  int getScreenHeight();
  
  int getWidth();
  
  int getHeight();
  
  Font getDefaultFont();
  
  void setMouseCursor(String paramString, int paramInt1, int paramInt2) throws SlickException;
  
  void setMouseCursor(ImageData paramImageData, int paramInt1, int paramInt2) throws SlickException;
  
  void setMouseCursor(Cursor paramCursor, int paramInt1, int paramInt2) throws SlickException;
  
  void setDefaultMouseCursor();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\gui\GUIContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */