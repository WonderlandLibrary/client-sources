package net;

import cc.novoline.modules.misc.WindowedFullscreen;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.a2V;
import net.aEo;
import net.abf;
import net.adG;
import net.as0;
import net.ava;
import net.gZ;
import net.minecraft.client.gui.Gui;

public class a14 {
   private final a2V d;
   private float a;
   private float c;
   public boolean g;
   public boolean e;
   public List b = new CopyOnWriteArrayList();
   private static boolean f;

   public a14(a2V var1, float var2, float var3) {
      e();
      this.d = var1;
      this.a = var2;
      this.c = var3;
      Iterator var5 = gZ.g().d().a(var1).iterator();
      if(var5.hasNext()) {
         as0 var6 = (as0)var5.next();
         if(!(var6 instanceof WindowedFullscreen)) {
            this.b.add(new adG(var6, this));
         }
      }

   }

   public void a(int var1, int var2) {
      e();
      ava var4 = (ava)gZ.g().d().b(ava.class);
      String var5 = "";
      if(this.d.name().equalsIgnoreCase("Combat")) {
         var5 = "D";
      }

      if(this.d.name().equalsIgnoreCase("Movement")) {
         var5 = "A";
      }

      if(this.d.name().equalsIgnoreCase("Player")) {
         var5 = "B";
      }

      if(this.d.name().equalsIgnoreCase("Visuals")) {
         var5 = "C";
      }

      if(this.d.name().equalsIgnoreCase("Exploits")) {
         var5 = "G";
      }

      if(this.d.name().equalsIgnoreCase("Misc")) {
         var5 = "F";
      }

      Gui.a((double)(this.a - 1.0F), (double)this.c, (double)(this.a + 101.0F), (double)(this.c + 15.0F), (new Color(29, 29, 29, 255)).getRGB());
      aEo.a.b(var5, this.a + 88.0F, this.c + 5.0F, -1);
      abf.a.a(this.d.name().charAt(0) + this.d.name().substring(1).toLowerCase(), (double)(this.a + 4.0F), (double)(this.c + 4.0F), -1, true);
      if(this.e) {
         Gui.a((double)(this.a - 1.0F), (double)(this.c + 15.0F), (double)(this.a + 101.0F), (double)(this.c + 15.0F + (float)this.g() + 1.0F), (new Color(29, 29, 29, 255)).getRGB());
         this.b.forEach(a14::lambda$drawScreen$0);
      }

      this.b.forEach(a14::lambda$drawScreen$1);
      if(PacketRemapper.b() == null) {
         b(false);
      }

   }

   public int g() {
      f();
      int var2 = 0;
      Iterator var3 = this.b.iterator();
      if(var3.hasNext()) {
         adG var4 = (adG)var3.next();
         var2 += var4.i;
      }

      return var2;
   }

   public boolean b(int var1, int var2) {
      boolean var3 = f();
      return (float)var1 > this.a && (float)var2 > this.c && (float)var1 < this.a + 101.0F && (float)var2 < this.c + 15.0F;
   }

   public void b(int var1, int var2, int var3) {
      boolean var4 = e();
      if(this.e) {
         this.b.forEach(a14::lambda$mouseReleased$2);
      }

   }

   public void a(char var1, int var2) {
      this.b.forEach(a14::lambda$keyTyped$3);
   }

   public void a(int var1, int var2, int var3) throws IOException {
      boolean var4 = e();
      if(this.b(var1, var2) && var3 == 1) {
         this.e = !this.e;
         if(this.e) {
            Iterator var5 = this.b.iterator();
            if(var5.hasNext()) {
               adG var6 = (adG)var5.next();
               var6.a = 0.0F;
            }
         }
      }

      if(this.e) {
         this.b.forEach(a14::lambda$mouseClicked$4);
      }

   }

   public boolean a(as0 var1) {
      f();
      Iterator var3 = this.b.iterator();
      if(var3.hasNext()) {
         adG var4 = (adG)var3.next();
         if(var1 == var4.b()) {
            return true;
         }
      }

      return false;
   }

   public a2V a() {
      return this.d;
   }

   public void b(float var1) {
      this.a = var1;
   }

   public void a(float var1) {
      this.c = var1;
   }

   public float c() {
      return this.c;
   }

   public float d() {
      return this.a;
   }

   public List b() {
      return this.b;
   }

   private static void lambda$mouseClicked$4(int var0, int var1, int var2, adG var3) {
      adG var10000 = var3;
      int var10001 = var0;
      int var10002 = var1;
      int var10003 = var2;

      try {
         var10000.a(var10001, var10002, var10003);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private static void lambda$keyTyped$3(char var0, int var1, adG var2) {
      var2.a(var0, var1);
   }

   private static void lambda$mouseReleased$2(int var0, int var1, int var2, adG var3) {
      var3.b(var0, var1, var2);
   }

   private static void lambda$drawScreen$1(adG var0) {
      var0.i = 0;
   }

   private static void lambda$drawScreen$0(int var0, int var1, adG var2) {
      var2.b(var0, var1);
   }

   public static void b(boolean var0) {
      f = var0;
   }

   public static boolean e() {
      return f;
   }

   public static boolean f() {
      boolean var0 = e();
      return true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }

   static {
      b(false);
   }
}
