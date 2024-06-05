package net;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_17;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.Jq;
import net.acE;
import net.ad_;
import net.minecraft.client.gui.Gui;

public class ayv {
   private ad_ d;
   private String c;
   private List e;
   private static int b;

   public ayv(ad_ var1, String var2) {
      int var10000 = e();
      this.e = new CopyOnWriteArrayList();
      this.c = var2;
      this.d = var1;
      int var3 = var10000;
      this.e.add(new Jq(110.0D, "Load", this, ayv::lambda$new$0));
      this.e.add(new Jq(140.0D, "Save", this, ayv::lambda$new$1));
      this.e.add(new Jq(170.0D, "Delete", this, ayv::lambda$new$2));
      if(acE.b() == null) {
         ++var3;
         b(var3);
      }

   }

   public void a(int var1, int var2) {
      double var4 = 5.0D;
      d();
      double var6 = this.a();
      Gui.drawRect(var4, var6, var4 + 200.0D, var6 + 17.0D, this.d.d().indexOf(this) % 2 != 0?(new Color(40, 40, 40)).getRGB():(new Color(56, 56, 56)).getRGB());
      Fonts$SF$SF_17.SF_17.drawString(this.c, var4 + 5.0D, var6 + 5.5D, -1, true);
      this.e.forEach(ayv::lambda$drawScreen$3);
   }

   public void a(int var1, int var2, int var3) {
      this.e.forEach(ayv::lambda$mouseClicked$4);
   }

   public double a() {
      e();
      double var2 = 30.0D;
      Iterator var4 = this.d.d().iterator();
      if(var4.hasNext()) {
         ayv var5 = (ayv)var4.next();
         if(var5 == this) {
            ;
         }

         var2 += 17.0D;
      }

      return var2;
   }

   public ad_ c() {
      return this.d;
   }

   public String b() {
      return this.c;
   }

   private static void lambda$mouseClicked$4(int var0, int var1, int var2, Jq var3) {
      var3.a(var0, var1, var2);
   }

   private static void lambda$drawScreen$3(int var0, int var1, Jq var2) {
      var2.b(var0, var1);
   }

   private static void lambda$new$2(ad_ var0, String var1) {
      ConfigCommand.deleteConfig(Novoline.getInstance().getModuleManager().getConfigManager(), var1);
      var0.b();
   }

   private static void lambda$new$1(String var0) {
      ConfigCommand.saveConfig(Novoline.getInstance().getModuleManager().getConfigManager(), var0);
   }

   private static void lambda$new$0(String var0) {
      ConfigCommand.loadConfig(Novoline.getInstance().getModuleManager().getConfigManager(), var0);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int d() {
      return b;
   }

   public static int e() {
      int var0 = d();
      return 81;
   }

   static {
      b(106);
   }
}
