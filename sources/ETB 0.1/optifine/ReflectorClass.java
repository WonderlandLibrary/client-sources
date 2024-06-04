package optifine;

public class ReflectorClass
{
  private String targetClassName;
  private boolean checked;
  private Class targetClass;
  
  public ReflectorClass(String targetClassName)
  {
    this(targetClassName, false);
  }
  
  public ReflectorClass(String targetClassName, boolean lazyResolve)
  {
    this.targetClassName = null;
    checked = false;
    targetClass = null;
    this.targetClassName = targetClassName;
    
    if (!lazyResolve)
    {
      Class localClass = getTargetClass();
    }
  }
  
  public ReflectorClass(Class targetClass)
  {
    targetClassName = null;
    checked = false;
    this.targetClass = null;
    this.targetClass = targetClass;
    targetClassName = targetClass.getName();
    checked = true;
  }
  
  public Class getTargetClass()
  {
    if (checked)
    {
      return targetClass;
    }
    

    checked = true;
    
    try
    {
      targetClass = Class.forName(targetClassName);
    }
    catch (ClassNotFoundException var2)
    {
      Config.log("(Reflector) Class not present: " + targetClassName);
    }
    catch (Throwable var3)
    {
      var3.printStackTrace();
    }
    
    return targetClass;
  }
  

  public boolean exists()
  {
    return getTargetClass() != null;
  }
  
  public String getTargetClassName()
  {
    return targetClassName;
  }
  
  public boolean isInstance(Object obj)
  {
    return getTargetClass() == null ? false : getTargetClass().isInstance(obj);
  }
  
  public ReflectorField makeField(String name)
  {
    return new ReflectorField(this, name);
  }
  
  public ReflectorMethod makeMethod(String name)
  {
    return new ReflectorMethod(this, name);
  }
  
  public ReflectorMethod makeMethod(String name, Class[] paramTypes)
  {
    return new ReflectorMethod(this, name, paramTypes);
  }
  
  public ReflectorMethod makeMethod(String name, Class[] paramTypes, boolean lazyResolve)
  {
    return new ReflectorMethod(this, name, paramTypes, lazyResolve);
  }
}
