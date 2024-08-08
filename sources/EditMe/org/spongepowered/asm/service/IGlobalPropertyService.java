package org.spongepowered.asm.service;

public interface IGlobalPropertyService {
   Object getProperty(String var1);

   void setProperty(String var1, Object var2);

   Object getProperty(String var1, Object var2);

   String getPropertyString(String var1, String var2);
}
