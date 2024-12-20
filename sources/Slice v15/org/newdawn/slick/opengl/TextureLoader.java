package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;












public class TextureLoader
{
  public TextureLoader() {}
  
  public static Texture getTexture(String format, InputStream in)
    throws IOException
  {
    return getTexture(format, in, false, 9729);
  }
  







  public static Texture getTexture(String format, InputStream in, boolean flipped)
    throws IOException
  {
    return getTexture(format, in, flipped, 9729);
  }
  







  public static Texture getTexture(String format, InputStream in, int filter)
    throws IOException
  {
    return getTexture(format, in, false, filter);
  }
  








  public static Texture getTexture(String format, InputStream in, boolean flipped, int filter)
    throws IOException
  {
    return InternalTextureLoader.get().getTexture(in, in.toString() + "." + format, flipped, filter);
  }
}
