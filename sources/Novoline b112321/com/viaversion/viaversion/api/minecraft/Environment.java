package com.viaversion.viaversion.api.minecraft;

public enum Environment {
   NORMAL(0),
   NETHER(-1),
   END(1);

   private final int id;

   private Environment(int var3) {
      this.id = var3;
   }

   public int getId() {
      return this.id;
   }

   public static Environment getEnvironmentById(int var0) {
      switch(var0) {
      case -1:
      default:
         return NETHER;
      case 0:
         return NORMAL;
      case 1:
         return END;
      }
   }
}
