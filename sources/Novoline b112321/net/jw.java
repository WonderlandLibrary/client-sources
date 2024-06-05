package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.DK;
import net.KL;
import net.XD;
import net.aHo;
import net.aIH;
import net.adZ;
import net.ae9;
import net.aqA;
import net.as0;
import net.av2;
import net.gZ;
import net.z6;
import net.zZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class jw {
   private final as0 d;
   private boolean b;
   private boolean c;
   private int a;

   public jw(as0 var1, int var2) {
      this.d = var1;
      this.a = var2;
   }

   public void c(int var1, int var2) {
      z6.b();
      aHo var4 = gZ.g().s();
      av2 var5 = (av2)gZ.g().d().b(av2.class);
      boolean var6 = false;
      int var7 = var4.f();
      int var8 = var4.h();
      if(this.b(var1, var2) || this.b) {
         Gui.a((double)(var7 + 45), (double)(var8 + this.a - 4), (double)(var7 + 45 + 110), var8 + this.a + XD.a.c() + 4 > var8 + var4.a()?(double)MathHelper.a((float)(var8 + this.a + XD.a.c() + 4), (float)(var8 + this.a - 4), (float)(var8 + var4.a())):(double)(var8 + this.a + XD.a.c() + 4), -13223618);
      }

      aIH.a.b("#", (float)(var7 + 50), (float)(var8 + this.a - 2), -10461856);
      if(this.d.y()) {
         XD.a.b(this.d.j(), (float)(var7 + 63), (float)(var8 + this.a), -1843229);
      }

      XD.a.b(this.d.j(), (float)(var7 + 63), (float)(var8 + this.a), -7961722);

      try {
         DK var9 = var4.g();
         if(this.b(var1, var2) && (var9 == null || var9.d().stream().noneMatch(jw::e)) && StringUtils.isNotBlank(this.c().t())) {
            String var10 = this.c().t();
            zZ.a.a(var10, (double)(var7 + 147 + var4.m() - zZ.a.a(var10)), (double)(var8 - 6), 16777215, true);
         }
      } catch (NullPointerException var11) {
         ;
      }

   }

   void a(int var1, int var2) {
      z6.b();
      aHo var4 = gZ.g().s();
      List var5 = ae9.a(this.d);
      if(this.b) {
         z6 var6 = aHo.l();
         if(var5 != null && !var5.isEmpty()) {
            switch(KL.a[var6.ordinal()]) {
            case 1:
               if(((adZ)var5.get(var5.size() - 1)).x() <= var4.h() + var4.a() - 14) {
                  break;
               }

               Iterator var7 = var5.iterator();
               if(var7.hasNext()) {
                  adZ var8 = (adZ)var7.next();
                  var8.b(var8.F() - 5);
               }
            case 2:
               if(((adZ)var5.get(0)).x() < var4.h() + 30) {
                  Iterator var10 = var5.iterator();
                  if(var10.hasNext()) {
                     adZ var12 = (adZ)var10.next();
                     var12.b(var12.F() + 5);
                  }
               }
            }
         }
      }

      if(this.b) {
         XD.a.b(this.d.j() + " Settings", (float)(var4.f() + 165), (float)(var4.h() + 6), -1);
         if(var5 == null || var5.isEmpty()) {
            String var9 = "NO SETTINGS ;(";
            FontRenderer var11 = Minecraft.getMinecraft().fontRendererObj;
            var11.drawStringWithShadow("NO SETTINGS ;(", (float)(var4.f() + 150) + (float)(var4.m() - var11.d("NO SETTINGS ;(")) / 2.0F, (float)var4.h() + (float)(var4.a() - var11.f()) / 2.0F, -9801351);
         }
      }

      GL11.glPushMatrix();
      GL11.glScissor(0, var4.e() - var4.h() * 2 - var4.a() * 2, 1920, var4.a() * 2 - 42);
      GL11.glEnable(3089);
      ae9.a().stream().filter(jw::lambda$drawScreen2$0).filter(this::lambda$drawScreen2$1).sorted(jw::lambda$drawScreen2$2).forEach(jw::lambda$drawScreen2$3);
      GL11.glDisable(3089);
      GL11.glPopMatrix();
   }

   protected void b(int var1, int var2, int var3) {
      z6.b();
      aHo var5 = gZ.g().s();
      if(var2 >= var5.h() + 20 && var2 <= var5.h() + var5.a() && this.b(var1, var2) && var3 == 2) {
         DK var6 = gZ.g().s().g();
         if(var6 == null) {
            return;
         }

         Iterator var7 = var6.d().iterator();
         if(var7.hasNext()) {
            jw var8 = (jw)var7.next();
            if(var8.c) {
               var8.c = false;
            }
         }

         this.c = true;
      }

      if(this.b) {
         Iterator var9 = ae9.a().iterator();
         if(var9.hasNext()) {
            adZ var10 = (adZ)var9.next();
            if(var2 >= var5.h() + 20 && var2 <= var5.h() + var5.a() && this.d.equals(var10.Q()) && var10.b(var1, var2, var3)) {
               return;
            }
         }
      }

   }

   protected void a(char var1, int var2) {
      z6.b();
      aHo var4 = gZ.g().s();
      if(this.c) {
         if(var2 != 1) {
            this.d.a((ProtocolPathEntry)aqA.b(var2));
            this.d.s().d().c().b();
         }

         this.c = false;
      }

      if(this.b) {
         Iterator var5 = ae9.a().iterator();
         if(var5.hasNext()) {
            adZ var6 = (adZ)var5.next();
            if(this.d.equals(var6.Q())) {
               var6.a(var1, var2);
            }
         }
      }

   }

   public void a(int var1, int var2, int var3) {
      if(this.b) {
         ae9.a().stream().filter(this::lambda$mouseReleased$4).forEach(jw::lambda$mouseReleased$5);
      }

   }

   public boolean b(int var1, int var2) {
      z6.b();
      aHo var4 = gZ.g().s();
      int var5 = var4.f();
      int var6 = var4.h();
      return var1 >= var5 + 45 && var1 <= var5 + 45 + 110 && var2 >= var6 + this.a - 4 && var2 <= var6 + this.a + XD.a.c() + 4;
   }

   public boolean d() {
      z6.b();
      aHo var2 = gZ.g().s();
      int var3 = var2.h();
      return var3 + this.a <= var3 + var2.a() - 9 && var3 + this.a >= var3 + 23;
   }

   public as0 c() {
      return this.d;
   }

   public int a() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public boolean e() {
      return this.c;
   }

   public boolean b() {
      return this.b;
   }

   public void a(boolean var1) {
      this.b = var1;
   }

   private static void lambda$mouseReleased$5(int var0, int var1, int var2, adZ var3) {
      var3.a(var0, var1, var2);
   }

   private boolean lambda$mouseReleased$4(adZ var1) {
      int[] var2 = z6.b();
      return var1.Q().equals(this.d) && var1.i();
   }

   private static void lambda$drawScreen2$3(int var0, int var1, adZ var2) {
      var2.J();
      var2.c(var0, var1);
   }

   private static int lambda$drawScreen2$2(adZ var0, adZ var1) {
      return Boolean.compare(var0.D(), var1.D());
   }

   private boolean lambda$drawScreen2$1(adZ var1) {
      int[] var2 = z6.b();
      return var1.Q().equals(this.d) && this.b;
   }

   private static boolean lambda$drawScreen2$0(adZ var0) {
      int[] var1 = z6.b();
      return var0.l() != null?((Boolean)var0.l().get()).booleanValue():true;
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
