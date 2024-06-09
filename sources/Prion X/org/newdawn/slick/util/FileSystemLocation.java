package org.newdawn.slick.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;








public class FileSystemLocation
  implements ResourceLocation
{
  private File root;
  
  public FileSystemLocation(File root)
  {
    this.root = root;
  }
  

  public URL getResource(String ref)
  {
    try
    {
      File file = new File(root, ref);
      if (!file.exists()) {
        file = new File(ref);
      }
      if (!file.exists()) {
        return null;
      }
      
      return file.toURI().toURL();
    } catch (IOException e) {}
    return null;
  }
  


  public InputStream getResourceAsStream(String ref)
  {
    try
    {
      File file = new File(root, ref);
      if (!file.exists()) {
        file = new File(ref);
      }
      return new FileInputStream(file);
    } catch (IOException e) {}
    return null;
  }
}
