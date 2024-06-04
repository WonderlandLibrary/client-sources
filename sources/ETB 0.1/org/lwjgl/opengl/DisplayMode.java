package org.lwjgl.opengl;










public final class DisplayMode
{
  private final int width;
  








  private final int height;
  







  private final int bpp;
  







  private final int freq;
  







  private final boolean fullscreen;
  








  public DisplayMode(int width, int height)
  {
    this(width, height, 0, 0, false);
  }
  
  DisplayMode(int width, int height, int bpp, int freq) {
    this(width, height, bpp, freq, true);
  }
  
  private DisplayMode(int width, int height, int bpp, int freq, boolean fullscreen) {
    this.width = width;
    this.height = height;
    this.bpp = bpp;
    this.freq = freq;
    this.fullscreen = fullscreen;
  }
  
  public boolean isFullscreenCapable()
  {
    return fullscreen;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public int getBitsPerPixel() {
    return bpp;
  }
  
  public int getFrequency() {
    return freq;
  }
  




  public boolean equals(Object obj)
  {
    if ((obj == null) || (!(obj instanceof DisplayMode))) {
      return false;
    }
    
    DisplayMode dm = (DisplayMode)obj;
    return (width == width) && (height == height) && (bpp == bpp) && (freq == freq);
  }
  







  public int hashCode()
  {
    return width ^ height ^ freq ^ bpp;
  }
  




  public String toString()
  {
    StringBuilder sb = new StringBuilder(32);
    sb.append(width);
    sb.append(" x ");
    sb.append(height);
    sb.append(" x ");
    sb.append(bpp);
    sb.append(" @");
    sb.append(freq);
    sb.append("Hz");
    return sb.toString();
  }
}
