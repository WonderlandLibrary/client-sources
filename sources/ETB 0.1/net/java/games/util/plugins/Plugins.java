package net.java.games.util.plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



















































public class Plugins
{
  static final boolean DEBUG = true;
  List pluginList = new ArrayList();
  


  public Plugins(File pluginRoot)
    throws IOException
  {
    scanPlugins(pluginRoot);
  }
  
  private void scanPlugins(File dir) throws IOException {
    File[] files = dir.listFiles();
    if (files == null) {
      throw new FileNotFoundException("Plugin directory " + dir.getName() + " not found.");
    }
    
    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      if (f.getName().endsWith(".jar")) {
        processJar(f);
      } else if (f.isDirectory()) {
        scanPlugins(f);
      }
    }
  }
  

  private void processJar(File f)
  {
    try
    {
      System.out.println("Scanning jar: " + f.getName());
      
      loader = new PluginLoader(f);
      JarFile jf = new JarFile(f);
      for (en = jf.entries(); en.hasMoreElements();) {
        JarEntry je = (JarEntry)en.nextElement();
        
        System.out.println("Examining file : " + je.getName());
        
        if (je.getName().endsWith("Plugin.class"))
        {
          System.out.println("Found candidate class: " + je.getName());
          
          String cname = je.getName();
          cname = cname.substring(0, cname.length() - 6);
          cname = cname.replace('/', '.');
          Class pc = loader.loadClass(cname);
          if (loader.attemptPluginDefine(pc))
          {
            System.out.println("Adding class to plugins:" + pc.getName());
            
            pluginList.add(pc);
          }
        }
      } } catch (Exception e) { PluginLoader loader;
      Enumeration en;
      e.printStackTrace();
    }
  }
  




  public Class[] get()
  {
    Class[] pluginArray = new Class[pluginList.size()];
    return (Class[])pluginList.toArray(pluginArray);
  }
  







  public Class[] getImplementsAny(Class[] interfaces)
  {
    List matchList = new ArrayList(pluginList.size());
    Set interfaceSet = new HashSet();
    for (int i = 0; i < interfaces.length; i++) {
      interfaceSet.add(interfaces[i]);
    }
    for (Iterator i = pluginList.iterator(); i.hasNext();) {
      Class pluginClass = (Class)i.next();
      if (classImplementsAny(pluginClass, interfaceSet)) {
        matchList.add(pluginClass);
      }
    }
    Class[] pluginArray = new Class[matchList.size()];
    return (Class[])matchList.toArray(pluginArray);
  }
  
  private boolean classImplementsAny(Class testClass, Set interfaces) {
    if (testClass == null) return false;
    Class[] implementedInterfaces = testClass.getInterfaces();
    for (int i = 0; i < implementedInterfaces.length; i++) {
      if (interfaces.contains(implementedInterfaces[i])) {
        return true;
      }
    }
    for (int i = 0; i < implementedInterfaces.length; i++) {
      if (classImplementsAny(implementedInterfaces[i], interfaces)) {
        return true;
      }
    }
    return classImplementsAny(testClass.getSuperclass(), interfaces);
  }
  







  public Class[] getImplementsAll(Class[] interfaces)
  {
    List matchList = new ArrayList(pluginList.size());
    Set interfaceSet = new HashSet();
    for (int i = 0; i < interfaces.length; i++) {
      interfaceSet.add(interfaces[i]);
    }
    for (Iterator i = pluginList.iterator(); i.hasNext();) {
      Class pluginClass = (Class)i.next();
      if (classImplementsAll(pluginClass, interfaceSet)) {
        matchList.add(pluginClass);
      }
    }
    Class[] pluginArray = new Class[matchList.size()];
    return (Class[])matchList.toArray(pluginArray);
  }
  
  private boolean classImplementsAll(Class testClass, Set interfaces) {
    if (testClass == null) return false;
    Class[] implementedInterfaces = testClass.getInterfaces();
    for (int i = 0; i < implementedInterfaces.length; i++) {
      if (interfaces.contains(implementedInterfaces[i])) {
        interfaces.remove(implementedInterfaces[i]);
        if (interfaces.size() == 0) {
          return true;
        }
      }
    }
    for (int i = 0; i < implementedInterfaces.length; i++) {
      if (classImplementsAll(implementedInterfaces[i], interfaces)) {
        return true;
      }
    }
    return classImplementsAll(testClass.getSuperclass(), interfaces);
  }
  






  public Class[] getExtends(Class superclass)
  {
    List matchList = new ArrayList(pluginList.size());
    for (Iterator i = pluginList.iterator(); i.hasNext();) {
      Class pluginClass = (Class)i.next();
      if (classExtends(pluginClass, superclass)) {
        matchList.add(pluginClass);
      }
    }
    Class[] pluginArray = new Class[matchList.size()];
    return (Class[])matchList.toArray(pluginArray);
  }
  
  private boolean classExtends(Class testClass, Class superclass) {
    if (testClass == null) {
      return false;
    }
    if (testClass == superclass) {
      return true;
    }
    return classExtends(testClass.getSuperclass(), superclass);
  }
}
