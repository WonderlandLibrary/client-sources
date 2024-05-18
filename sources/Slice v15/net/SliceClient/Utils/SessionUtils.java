package net.SliceClient.Utils;

import java.io.PrintStream;
import java.lang.reflect.Field;
import net.SliceClient.Slice;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionUtils
{
  private static Minecraft mc = ;
  
  public SessionUtils() {}
  
  public static Session getSession() {
    try {
      Field session = mc.getClass().getDeclaredField("session");
      session.setAccessible(true);
      return (Session)session.get(mc);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static void setSession(Session session)
  {
    try
    {
      Field f = mc.getClass().getDeclaredField("session");
      f.setAccessible(true);
      f.set(mc, session);
    }
    catch (Exception e)
    {
      System.out.println("[" + Slice.G + "]: WARNING! Could not set session.");
    }
  }
}
