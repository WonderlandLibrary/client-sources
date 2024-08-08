package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;

public class Blackboard implements IGlobalPropertyService {
   public final Object getProperty(String var1) {
      return Launch.blackboard.get(var1);
   }

   public final void setProperty(String var1, Object var2) {
      Launch.blackboard.put(var1, var2);
   }

   public final Object getProperty(String var1, Object var2) {
      Object var3 = Launch.blackboard.get(var1);
      return var3 != null ? var3 : var2;
   }

   public final String getPropertyString(String var1, String var2) {
      Object var3 = Launch.blackboard.get(var1);
      return var3 != null ? var3.toString() : var2;
   }
}
