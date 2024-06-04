package optifine;

import java.lang.reflect.Field;

public class ReflectorField
{
  private ReflectorClass reflectorClass = null;
  private String targetFieldName = null;
  private boolean checked = false;
  private Field targetField = null;
  
  public ReflectorField(ReflectorClass reflectorClass, String targetFieldName)
  {
    this.reflectorClass = reflectorClass;
    this.targetFieldName = targetFieldName;
    Field f = getTargetField();
  }
  
  public Field getTargetField()
  {
    if (checked)
    {
      return targetField;
    }
    

    checked = true;
    Class cls = reflectorClass.getTargetClass();
    
    if (cls == null)
    {
      return null;
    }
    

    try
    {
      targetField = cls.getDeclaredField(targetFieldName);
      targetField.setAccessible(true);
    }
    catch (NoSuchFieldException var3)
    {
      Config.log("(Reflector) Field not present: " + cls.getName() + "." + targetFieldName);
    }
    catch (SecurityException var4)
    {
      var4.printStackTrace();
    }
    catch (Throwable var5)
    {
      var5.printStackTrace();
    }
    
    return targetField;
  }
  


  public Object getValue()
  {
    return Reflector.getFieldValue(null, this);
  }
  
  public void setValue(Object value)
  {
    Reflector.setFieldValue(null, this, value);
  }
  
  public boolean exists()
  {
    return targetField != null;
  }
}
