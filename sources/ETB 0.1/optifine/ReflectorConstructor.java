package optifine;

import java.lang.reflect.Constructor;

public class ReflectorConstructor
{
  private ReflectorClass reflectorClass = null;
  private Class[] parameterTypes = null;
  private boolean checked = false;
  private Constructor targetConstructor = null;
  
  public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes)
  {
    this.reflectorClass = reflectorClass;
    this.parameterTypes = parameterTypes;
    Constructor c = getTargetConstructor();
  }
  
  public Constructor getTargetConstructor()
  {
    if (checked)
    {
      return targetConstructor;
    }
    

    checked = true;
    Class cls = reflectorClass.getTargetClass();
    
    if (cls == null)
    {
      return null;
    }
    

    try
    {
      targetConstructor = findConstructor(cls, parameterTypes);
      
      if (targetConstructor == null)
      {
        Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.arrayToString(parameterTypes));
      }
      
      if (targetConstructor != null)
      {
        targetConstructor.setAccessible(true);
      }
    }
    catch (Throwable var3)
    {
      var3.printStackTrace();
    }
    
    return targetConstructor;
  }
  


  private static Constructor findConstructor(Class cls, Class[] paramTypes)
  {
    Constructor[] cs = cls.getDeclaredConstructors();
    
    for (int i = 0; i < cs.length; i++)
    {
      Constructor c = cs[i];
      Class[] types = c.getParameterTypes();
      
      if (Reflector.matchesTypes(paramTypes, types))
      {
        return c;
      }
    }
    
    return null;
  }
  
  public boolean exists()
  {
    return targetConstructor != null;
  }
  
  public void deactivate()
  {
    checked = true;
    targetConstructor = null;
  }
}
