package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;

public abstract interface ResourceLocation
{
  public abstract InputStream getResourceAsStream(String paramString);
  
  public abstract URL getResource(String paramString);
}
