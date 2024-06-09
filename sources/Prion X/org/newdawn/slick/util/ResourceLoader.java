package org.newdawn.slick.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;







public class ResourceLoader
{
  private static ArrayList locations = new ArrayList();
  
  static {
    locations.add(new ClasspathLocation());
    locations.add(new FileSystemLocation(new File(".")));
  }
  

  public ResourceLoader() {}
  

  public static void addResourceLocation(ResourceLocation location)
  {
    locations.add(location);
  }
  




  public static void removeResourceLocation(ResourceLocation location)
  {
    locations.remove(location);
  }
  



  public static void removeAllResourceLocations()
  {
    locations.clear();
  }
  





  public static InputStream getResourceAsStream(String ref)
  {
    InputStream in = null;
    
    for (int i = 0; i < locations.size(); i++) {
      ResourceLocation location = (ResourceLocation)locations.get(i);
      in = location.getResourceAsStream(ref);
      if (in != null) {
        break;
      }
    }
    
    if (in == null)
    {
      throw new RuntimeException("Resource not found: " + ref);
    }
    
    return new BufferedInputStream(in);
  }
  





  public static boolean resourceExists(String ref)
  {
    URL url = null;
    
    for (int i = 0; i < locations.size(); i++) {
      ResourceLocation location = (ResourceLocation)locations.get(i);
      url = location.getResource(ref);
      if (url != null) {
        return true;
      }
    }
    
    return false;
  }
  






  public static URL getResource(String ref)
  {
    URL url = null;
    
    for (int i = 0; i < locations.size(); i++) {
      ResourceLocation location = (ResourceLocation)locations.get(i);
      url = location.getResource(ref);
      if (url != null) {
        break;
      }
    }
    
    if (url == null)
    {
      throw new RuntimeException("Resource not found: " + ref);
    }
    
    return url;
  }
}
