package net;

import net.minecraft.world.GameRules$ValueType;

class a6X {
   private String e;
   private boolean a;
   private int c;
   private double f;
   private final GameRules$ValueType d;
   private static final String b = "CL_00000137";

   public a6X(String var1, GameRules$ValueType var2) {
      this.d = var2;
      this.a(var1);
   }

   public void a(String var1) {
      this.e = var1;
      if(var1.equals("false")) {
         this.a = false;
      } else if(var1.equals("true")) {
         this.a = true;
      } else {
         this.a = Boolean.parseBoolean(var1);
         this.c = this.a?1:0;

         try {
            this.c = Integer.parseInt(var1);
         } catch (NumberFormatException var4) {
            ;
         }

         try {
            this.f = Double.parseDouble(var1);
         } catch (NumberFormatException var3) {
            ;
         }

      }
   }

   public String b() {
      return this.e;
   }

   public boolean a() {
      return this.a;
   }

   public int c() {
      return this.c;
   }

   public GameRules$ValueType d() {
      return this.d;
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
