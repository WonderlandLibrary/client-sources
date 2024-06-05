package net;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.Nv;
import net.aXr;
import net.ava;
import net.aw5;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public final class Es {
   private final Nv e;
   private String d;
   private float c = 0.0F;
   private static String[] b;

   public Es(Nv var1) {
      this.e = var1;
   }

   public Es(Nv var1, String var2) {
      this.d = var2;
      this.e = var1;
   }

   public int c() {
      String[] var1 = f();
      if(((ava)gZ.g().d().b(ava.class)).y()) {
         ava var2 = (ava)gZ.g().d().b(ava.class);
         return var2.F().a((Object)"Name")?(var2.C().equals("Normal")?15:20):5;
      } else {
         return 5;
      }
   }

   public void a() {
      float var2;
      float var3;
      label0: {
         var2 = (float)(this.c() + this.e.d().indexOf(this) * 12);
         var3 = var2 + 12.0F;
         f();
         double var4 = (double)(Minecraft.getMinecraft().getDebugFPS() / 13);
         if(this.e()) {
            if(this.c >= 3.0F) {
               break label0;
            }

            this.c = (float)MathHelper.b((double)this.c + 3.0D / var4, 0.0D, 3.0D);
         }

         if(this.c > 0.0F) {
            this.c = (float)MathHelper.b((double)this.c - 3.0D / var4, 0.0D, 3.0D);
         }
      }

      switch(aXr.a[this.e.h().f().ordinal()]) {
      case 1:
         double var6 = this.e.h().h();
         double var8 = (double)((int)(var6 * 100.0D)) / 100.0D;
         if(var8 % 1.0D == 0.0D) {
            String var10 = (new DecimalFormat("0.##")).format(var8);
         }

         String var11 = var8 + "";
         Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(119 + this.e.c().d() + this.e.a() + this.b()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(109 + this.e.c().d() + this.e.a()), (double)var3, this.e.c().a().d().a());
         aw5.a.a(var11, (double)(114 + this.e.c().d() + this.e.a()), (double)(var2 + 3.0F), -1, true);
      case 2:
         Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(129 + this.e.c().d() + this.e.a() + this.b()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         if(this.e()) {
            Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(129 + this.e.c().d() + this.e.a() + this.b()), (double)var3, this.e.c().a().d().a());
         }

         aw5.a.a(this.d, (double)(114.0F + this.c + (float)this.e.c().d() + (float)this.e.a()), (double)(var2 + 3.0F), this.e.h().k().equalsIgnoreCase(this.d)?-1:(new Color(163, 163, 163, 255)).getRGB(), true);
      case 3:
         Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(129 + this.e.c().d() + this.e.a() + this.b()), (double)var3, (new Color(20, 20, 20, 170)).getRGB());
         if(this.e()) {
            Gui.a((double)(109 + this.e.c().d() + this.e.a()), (double)var2, (double)(129 + this.e.c().d() + this.e.a() + this.b()), (double)var3, this.e.c().a().d().a());
         }

         aw5.a.a(this.d, (double)(114.0F + this.c + (float)this.e.c().d() + (float)this.e.a()), (double)(var2 + 3.0F), this.e.h().v().a((Object)this.d)?-1:(new Color(163, 163, 163, 255)).getRGB(), true);
      default:
      }
   }

   public int b() {
      f();
      int var2 = 0;
      switch(aXr.a[this.e.h().f().ordinal()]) {
      case 1:
         double var9 = this.e.h().h();
         double var5 = (double)((int)(var9 * 100.0D)) / 100.0D;
         if(var5 % 1.0D == 0.0D) {
            String var7 = (new DecimalFormat("0.##")).format(var5);
         }

         String var11 = var5 + "";
         return aw5.a.a(var11);
      case 2:
         Iterator var3 = this.e.h().E().a().iterator();
         if(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(aw5.a.a(var4) > var2) {
               var2 = aw5.a.a(var4);
            }
         }
      case 3:
         Iterator var8 = this.e.h().v().f().iterator();
         if(var8.hasNext()) {
            String var10 = (String)var8.next();
            if(aw5.a.a(var10) > var2) {
               var2 = aw5.a.a(var10);
            }
         }
      default:
         return var2;
      }
   }

   public String d() {
      return this.d;
   }

   public boolean e() {
      String[] var1 = f();
      return this.e.d().indexOf(this) == this.e.c().a().d().f();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] f() {
      return b;
   }

   static {
      b((String[])null);
   }
}
