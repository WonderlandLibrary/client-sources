package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.AccessController;

public class ClassUtil
{
  public ClassUtil() {}
  
  public static Class loadClass(Class sourceClass, String className)
  {
    try {
      ClassLoader loader = sourceClass.getClassLoader();
      
      if (loader != null)
      {
        return loader.loadClass(className);
      }
      

      (Class)AccessController.doPrivileged(new java.security.PrivilegedAction()
      {
        public Object run()
        {
          try
          {
            return Class.forName(val$className);
          }
          catch (Exception localException) {}
          



          return null;
        }
      });
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    




    return null;
  }
}
