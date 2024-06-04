package net.java.games.util.plugins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;



























































public class PluginLoader
  extends URLClassLoader
{
  static final boolean DEBUG = false;
  File parentDir;
  boolean localDLLs = true;
  








  public PluginLoader(File jf)
    throws MalformedURLException
  {
    super(new URL[] { jf.toURL() }, Thread.currentThread().getContextClassLoader());
    
    parentDir = jf.getParentFile();
    if (System.getProperty("net.java.games.util.plugins.nolocalnative") != null)
    {
      localDLLs = false;
    }
  }
  



















  protected String findLibrary(String libname)
  {
    if (localDLLs) {
      String libpath = parentDir.getPath() + File.separator + System.mapLibraryName(libname);
      



      return libpath;
    }
    return super.findLibrary(libname);
  }
  














  public boolean attemptPluginDefine(Class pc)
  {
    return (!pc.isInterface()) && (classImplementsPlugin(pc));
  }
  
  private boolean classImplementsPlugin(Class testClass) {
    if (testClass == null) { return false;
    }
    

    Class[] implementedInterfaces = testClass.getInterfaces();
    for (int i = 0; i < implementedInterfaces.length; i++)
    {


      if (implementedInterfaces[i] == Plugin.class)
      {


        return true;
      }
    }
    for (int i = 0; i < implementedInterfaces.length; i++) {
      if (classImplementsPlugin(implementedInterfaces[i])) {
        return true;
      }
    }
    return classImplementsPlugin(testClass.getSuperclass());
  }
}
