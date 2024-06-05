package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.Fc;
import net.H3;
import net.UW;
import net.XD;
import net.Xt;
import net.a6_;
import net.aBk;
import net.aEo;
import net.aHo;
import net.aIH;
import net.aN4;
import net.av2;
import net.azi;
import net.d3;
import net.gZ;
import net.pH;
import net.z6;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public final class V9 {
   static int b = 5;
   public static boolean d;
   private static String e = "";
   private static final d3 a = new d3();
   private static PacketRemapper[] c;

   public static void g(int var0, int var1) {
      b();
      gZ var3 = gZ.g();
      aHo var4 = var3.s();
      z6 var5 = aHo.l();
      int var6 = var4.f();
      int var7 = var4.h();
      if(!var4.d().isEmpty()) {
         switch(aN4.a[var5.ordinal()]) {
         case 1:
            if(((pH)var4.d().get(var4.d().size() - 1)).f() <= var7 + var4.a() - 14) {
               break;
            }

            Iterator var8 = var4.d().iterator();
            if(var8.hasNext()) {
               pH var9 = (pH)var8.next();
               var9.a(var9.e() - 7);
            }
         case 2:
            if(((pH)var4.d().get(0)).e() < 30) {
               Iterator var14 = var4.d().iterator();
               if(var14.hasNext()) {
                  pH var15 = (pH)var14.next();
                  var15.a(var15.e() + 7);
               }
            }
         }
      }

      if(var4.c() && b <= 10) {
         ++b;
      }

      if(!var4.c()) {
         b = 5;
      }

      int var16 = ((av2)var3.d().b(av2.class)).x();
      if(e(var0, var1)) {
         if(var4.c()) {
            Gui.drawRect(var6, var7 + 274 - b, var6 + 2, var7 + 274 + b, -1);
         }

         Gui.drawRect(var6, var7 + 269, var6 + 2, var7 + 279, -1);
      }

      if(var4.c()) {
         Gui.drawRect(var6, var7 + 274 - b, var6 + 2, var7 + 274 + b, -1);
      }

      a6_.a((float)(var6 + 22), (float)(var7 + 274), 15.0F, -13223617);
      Xt.a.b("E", (float)(var6 + 16), (float)(var7 + 268), e(var0, var1)?var16:-1);
      if(var4.c()) {
         boolean var10 = a(var4);
         if(var10 && a(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 26, var6 + 45 + 110, var7 + 40, -13223618);
         }

         if(var10 && b(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 44, var6 + 45 + 110, var7 + 58, -13223618);
         }

         if(var10 && d(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 62, var6 + 45 + 110, var7 + 76, -13223618);
         }

         if(f(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 80, var6 + 45 + 110, var7 + 94, -13223618);
         }

         aEo.a.b("I", (float)(var6 + 50), (float)(var7 + 7), -1);
         XD.a.b("Configs", (float)(var6 + 63), (float)(var7 + 7), -1);
         int var11 = var10?-7961722:(new Color(5131086)).getRGB();
         aIH.a.b("#", (float)(var6 + 50), (float)(var7 + 28), var11);
         aIH.a.b("#", (float)(var6 + 50), (float)(var7 + 46), var11);
         aIH.a.b("#", (float)(var6 + 50), (float)(var7 + 64), -7961722);
         aIH.a.b("#", (float)(var6 + 50), (float)(var7 + 82), -7961722);
         XD.a.b("Load", (float)(var6 + 63), (float)(var7 + 30), var11);
         XD.a.b("Delete", (float)(var6 + 63), (float)(var7 + 48), var11);
         XD.a.b("Save", (float)(var6 + 63), (float)(var7 + 66), -7961722);
         XD.a.b("Refresh", (float)(var6 + 63), (float)(var7 + 84), -7961722);
         if(d && Keyboard.isKeyDown(14) && a.a(100.0D) && e.length() >= 1) {
            e = e.substring(0, e.length() - 1);
            a.b();
         }

         a6_.a((float)(var6 + 50), (float)(var7 + 100), (float)(var6 + 120), (float)(var7 + 110), 1.0F, !c(var0, var1) && !d?1677721600:-8225316, -13684945);
         if(e.isEmpty()) {
            H3.a.b("Config name", (float)(var6 + 52), (float)(var7 + 102), 1694498815);
         }

         if(H3.a.a(e) > 65) {
            H3.a.b(H3.a.a(e, 60, true), (float)(var6 + 52), (float)(var7 + 102), -1);
         }

         H3.a.b(e, (float)(var6 + 52), (float)(var7 + 102), -1);
         Iterator var12 = var4.d().iterator();
         if(var12.hasNext()) {
            pH var13 = (pH)var12.next();
            var13.c();
            if(!var13.d()) {
               var13.b(var0, var1);
            }
         }
      }

   }

   private static boolean a(aHo var0) {
      return var0.d().stream().anyMatch(pH::b);
   }

   public static void a(int var0, int var1, int var2) {
      PacketRemapper[] var3 = b();
      gZ var4 = gZ.g();
      UW var5 = var4.d();
      aBk var6 = var5.b();
      aHo var7 = var4.s();
      List var8 = var7.d();
      boolean var9 = false;
      Iterator var10 = var8.iterator();
      if(var10.hasNext()) {
         pH var11 = (pH)var10.next();
         if(var11.a(var0, var1)) {
            Iterator var12 = var8.iterator();
            if(var12.hasNext()) {
               pH var13 = (pH)var12.next();
               var13.a(false);
            }

            var11.a(true);
         }

         if(var11.b()) {
            if(a(var0, var1)) {
               Fc.a(var5.b(), var11.a());
               var7.j();
            }

            if(b(var0, var1)) {
               Fc.b(var5.b(), var11.a());
               var7.j();
            }

            if(d(var0, var1)) {
               Fc.c(var5.b(), var11.a());
               var7.j();
               var9 = true;
            }
         }
      }

      if(!var9 && d(var0, var1)) {
         if(e.length() > 25) {
            gZ.g().t().a("Name is too long!", 2000, azi.ERROR);
         }

         Fc.c(var5.b(), e);
         var7.j();
      }

      if(f(var0, var1)) {
         var7.j();
      }

      if(c(var0, var1)) {
         d = !d;
      }

      if(d) {
         d = false;
      }

   }

   public static void a(char var0, int var1) {
      PacketRemapper[] var2 = b();
      if(var1 == 63) {
         gZ.g().s().j();
      } else {
         if(d) {
            if(var1 == 1) {
               d = false;
            }

            if(ChatAllowedCharacters.isAllowedCharacter(var0)) {
               e = e + Character.toString(var0);
            }
         }

      }
   }

   public static boolean a(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 44 && var0 <= var3.f() + 45 + 110 && var1 >= var3.h() + 26 && var1 <= var3.h() + 40;
   }

   public static boolean b(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 44 && var0 <= var3.f() + 45 + 110 && var1 >= var3.h() + 44 && var1 <= var3.h() + 58;
   }

   public static boolean d(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 44 && var0 <= var3.f() + 45 + 110 && var1 >= var3.h() + 62 && var1 <= var3.h() + 76;
   }

   public static boolean f(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 44 && var0 <= var3.f() + 45 + 110 && var1 >= var3.h() + 80 && var1 <= var3.h() + 94;
   }

   public static boolean e(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 8 && var0 <= var3.f() + 35 && var1 >= var3.h() + 259 && var1 <= var3.h() + 289;
   }

   public static boolean c(int var0, int var1) {
      b();
      aHo var3 = gZ.g().s();
      return var0 >= var3.f() + 50 && var0 <= var3.f() + 120 && var1 >= var3.h() + 100 && var1 <= var3.h() + 110;
   }

   static {
      b((PacketRemapper[])null);
   }

   public static void b(PacketRemapper[] var0) {
      c = var0;
   }

   public static PacketRemapper[] b() {
      return c;
   }
}
