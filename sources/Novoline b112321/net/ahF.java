package net;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

public class ahF {
   private static String b;

   public static void a(EntityAITasks var0, int var1, EntityAIBase var2) {
      var0.addTask(var1, var2);
   }

   public static void a(EntityAITasks var0, EntityAIBase var1) {
      var0.removeTask(var1);
   }

   public static void a(EntityAITasks var0) {
      var0.onUpdateTasks();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("ezJIGb");
      }

   }
}
