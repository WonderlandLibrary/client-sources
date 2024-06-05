package net;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.modules.visual.HUD;
import java.awt.Color;
import java.util.Iterator;
import net.D2;
import net.aLM;
import net.minecraft.util.EnumChatFormatting;

public class ca {
   private D2 c;
   private String a;
   private boolean e;
   private Long d = Long.valueOf(0L);
   private static String b;

   public ca(D2 var1, String var2) {
      this.c = var1;
      this.a = var2;
   }

   public void b(int var1, int var2) {
      b();
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      aLM.a.drawString(this.a + (ConfigCommand.j.equals(this.a)?EnumChatFormatting.ITALIC + " - loaded":""), this.c.g() + 3.0D, this.a() + 5.0D, ConfigCommand.j.equals(this.a)?var4.getColor().getRGB():(!this.a(var1, var2) && !this.c()?(new Color(180, 180, 180)).getRGB():-1), true);
   }

   public void a(int var1, int var2, int var3) {
      String var4 = b();
      if(this.a(var1, var2) && var3 == 0) {
         if(!this.e) {
            Iterator var5 = this.c.d().iterator();
            if(var5.hasNext()) {
               ca var6 = (ca)var5.next();
               if(var6 != this) {
                  var6.a(false);
               }
            }

            this.e = true;
            this.d = Long.valueOf(System.currentTimeMillis());
         }

         if(System.currentTimeMillis() - this.d.longValue() <= 250L) {
            ConfigCommand.loadConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.a);
            this.e = false;
         }
      }

   }

   public boolean a(int var1, int var2) {
      String var3 = b();
      return (double)var1 >= this.c.g() && (double)var1 <= this.c.g() + this.c.b() - 10.0D && (double)var2 >= this.a() && (double)var2 <= this.a() + 14.0D;
   }

   public double a() {
      b();
      double var2 = this.c.f() + 12.0D;
      Iterator var4 = this.c.d().iterator();
      if(var4.hasNext()) {
         ca var5 = (ca)var4.next();
         if(var5 == this) {
            ;
         }

         var2 += 15.0D;
      }

      var2 = var2 - this.c.c();
      return var2;
   }

   public void a(boolean var1) {
      this.e = var1;
   }

   public String d() {
      return this.a;
   }

   public boolean c() {
      return this.e;
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("nHygvc");
      }

   }
}
