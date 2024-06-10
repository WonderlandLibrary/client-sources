package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;

public abstract interface ResourceLocation
{
  public abstract InputStream getResourceAsStream(String paramString);
  
  public abstract URL getResource(String paramString);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.ResourceLocation
 * JD-Core Version:    0.7.0.1
 */