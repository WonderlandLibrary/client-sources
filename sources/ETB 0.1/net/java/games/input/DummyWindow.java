package net.java.games.input;

import java.io.IOException;









































final class DummyWindow
{
  private final long hwnd_address;
  
  public DummyWindow()
    throws IOException
  {
    hwnd_address = createWindow();
  }
  
  private static final native long createWindow() throws IOException;
  
  public final void destroy() throws IOException { nDestroy(hwnd_address); }
  
  private static final native void nDestroy(long paramLong) throws IOException;
  
  public final long getHwnd() {
    return hwnd_address;
  }
}
