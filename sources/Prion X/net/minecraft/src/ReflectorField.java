package net.minecraft.src;

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
      
      if (!targetField.isAccessible())
      {
        targetField.setAccessible(true);
      }
    }
    catch (SecurityException var3)
    {
      var3.printStackTrace();
    }
    catch (NoSuchFieldException var4)
    {
      Config.log("(Reflector) Field not present: " + cls.getName() + "." + targetFieldName);
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
