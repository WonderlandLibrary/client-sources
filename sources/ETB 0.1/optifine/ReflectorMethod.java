package optifine;

import java.lang.reflect.Method;

public class ReflectorMethod
{
  private ReflectorClass reflectorClass;
  private String targetMethodName;
  private Class[] targetMethodParameterTypes;
  private boolean checked;
  private Method targetMethod;
  
  public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName)
  {
    this(reflectorClass, targetMethodName, null, false);
  }
  
  public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes)
  {
    this(reflectorClass, targetMethodName, targetMethodParameterTypes, false);
  }
  
  public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes, boolean lazyResolve)
  {
    this.reflectorClass = null;
    this.targetMethodName = null;
    this.targetMethodParameterTypes = null;
    checked = false;
    targetMethod = null;
    this.reflectorClass = reflectorClass;
    this.targetMethodName = targetMethodName;
    this.targetMethodParameterTypes = targetMethodParameterTypes;
    
    if (!lazyResolve)
    {
      Method localMethod = getTargetMethod();
    }
  }
  
  public Method getTargetMethod()
  {
    if (checked)
    {
      return targetMethod;
    }
    

    checked = true;
    Class cls = reflectorClass.getTargetClass();
    
    if (cls == null)
    {
      return null;
    }
    

    try
    {
      if (targetMethodParameterTypes == null)
      {
        Method[] e = Reflector.getMethods(cls, targetMethodName);
        
        if (e.length <= 0)
        {
          Config.log("(Reflector) Method not present: " + cls.getName() + "." + targetMethodName);
          return null;
        }
        
        if (e.length > 1)
        {
          Config.warn("(Reflector) More than one method found: " + cls.getName() + "." + targetMethodName);
          
          for (int i = 0; i < e.length; i++)
          {
            Method m = e[i];
            Config.warn("(Reflector)  - " + m);
          }
          
          return null;
        }
        
        targetMethod = e[0];
      }
      else
      {
        targetMethod = Reflector.getMethod(cls, targetMethodName, targetMethodParameterTypes);
      }
      
      if (targetMethod == null)
      {
        Config.log("(Reflector) Method not present: " + cls.getName() + "." + targetMethodName);
        return null;
      }
      

      targetMethod.setAccessible(true);
      return targetMethod;

    }
    catch (Throwable var5)
    {
      var5.printStackTrace(); }
    return null;
  }
  



  public boolean exists()
  {
    return targetMethod != null;
  }
  
  public Class getReturnType()
  {
    Method tm = getTargetMethod();
    return tm == null ? null : tm.getReturnType();
  }
  
  public void deactivate()
  {
    checked = true;
    targetMethod = null;
  }
}
