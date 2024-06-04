package org.lwjgl.util.glu;

import org.lwjgl.opengl.GL11;









































class PixelStoreState
  extends Util
{
  public int unpackRowLength;
  public int unpackAlignment;
  public int unpackSkipRows;
  public int unpackSkipPixels;
  public int packRowLength;
  public int packAlignment;
  public int packSkipRows;
  public int packSkipPixels;
  
  PixelStoreState()
  {
    load();
  }
  
  public void load() {
    unpackRowLength = glGetIntegerv(3314);
    unpackAlignment = glGetIntegerv(3317);
    unpackSkipRows = glGetIntegerv(3315);
    unpackSkipPixels = glGetIntegerv(3316);
    packRowLength = glGetIntegerv(3330);
    packAlignment = glGetIntegerv(3333);
    packSkipRows = glGetIntegerv(3331);
    packSkipPixels = glGetIntegerv(3332);
  }
  
  public void save() {
    GL11.glPixelStorei(3314, unpackRowLength);
    GL11.glPixelStorei(3317, unpackAlignment);
    GL11.glPixelStorei(3315, unpackSkipRows);
    GL11.glPixelStorei(3316, unpackSkipPixels);
    GL11.glPixelStorei(3330, packRowLength);
    GL11.glPixelStorei(3333, packAlignment);
    GL11.glPixelStorei(3331, packSkipRows);
    GL11.glPixelStorei(3332, packSkipPixels);
  }
}
