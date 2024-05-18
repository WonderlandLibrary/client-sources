package net.SliceClient.mcleaks;

import java.lang.reflect.Field;

public class SessionManager
{
  public SessionManager() {}
  
  public static void setSession(String mcName)
  {
    setSession(new net.minecraft.util.Session(mcName, "", "", "mojang"));
  }
  
  public static void setSession(net.minecraft.util.Session session)
  {
    setFieldByClass(net.minecraft.client.Minecraft.getMinecraft(), session);
  }
  
  public static void setFieldByClass(Object parentObject, Object newObject)
  {
    Field field = null;
    for (Field f : parentObject.getClass().getDeclaredFields()) {
      if (f.getType().isInstance(newObject))
      {
        field = f;
        break;
      }
    }
    if (field == null) {
      return;
    }
    try
    {
      boolean accessible = field.isAccessible();
      field.setAccessible(true);
      field.set(parentObject, newObject);
      field.setAccessible(accessible);
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
  }
}
