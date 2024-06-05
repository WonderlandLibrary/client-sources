package net;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.dropdown.Tab;
import cc.novoline.gui.screen.dropdown.config.Config;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.K6;
import net.ape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class a1I extends Tab {
   private final List i = new CopyOnWriteArrayList();
   private Config h;

   public a1I(float var1, float var2) {
      super((EnumModuleType)null, var1, var2);
   }

   public void drawScreen(int var1, int var2) {
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Config.a();
      int var5 = 0;
      Iterator var6 = this.i.iterator();
      if(var6.hasNext()) {
         Config var7 = (Config)var6.next();
         var5 += var7.getYPerConfig();
      }

      ++var5;
      if(!this.opened) {
         var5 = 0;
      }

      Gui.drawRect((double)(this.getPosX() - 1.0F), (double)this.getPosY(), (double)(this.getPosX() + 101.0F), (double)(this.getPosY() + 15.0F + (float)var5), (new Color(29, 29, 29, 255)).getRGB());
      Fonts$SF$SF_20.SF_20.drawString("Scripts", (double)(this.getPosX() + 4.0F), (double)(this.getPosY() + 4.0F), -1, true);
      if(this.opened) {
         this.i.forEach(a1I::lambda$drawScreen$0);
      }

   }

   public void b() {
      this.i.clear();
      this.a((Config)null);
      Novoline.getInstance().u().a(Novoline.getInstance());
      Novoline.getInstance().u().a().forEach(this::lambda$refreshConfigs$1);
      this.i.add(new ape("Load", this, a1I::lambda$refreshConfigs$2));
      this.i.add(new ape("Unload", this, a1I::lambda$refreshConfigs$3));
      this.i.add(new ape("Delete", this, this::lambda$refreshConfigs$4));
      this.i.add(new ape("Refresh", this, a1I::lambda$refreshConfigs$5));
   }

   public void mouseClicked(int var1, int var2, int var3) {
      boolean var4 = Config.d();
      if(this.b(var1, var2) && var3 == 1) {
         this.opened = !this.opened;
      }

      if(this.opened) {
         this.i.forEach(a1I::lambda$mouseClicked$6);
      }

   }

   public List a() {
      return this.i;
   }

   public Config c() {
      return this.h;
   }

   public void a(Config var1) {
      this.h = var1;
   }

   private static void lambda$mouseClicked$6(int var0, int var1, int var2, Config var3) {
      var3.a(var0, var1, var2);
   }

   private static void lambda$refreshConfigs$5(String var0) {
      Config.d();
      Iterator var2 = Novoline.getInstance().u().a().iterator();
      if(var2.hasNext()) {
         K6 var3 = (K6)var2.next();
         if(var3.a() == null || !var3.a().isEnabled()) {
            var3.c();
         }
      }

      Novoline.getInstance().u().a(Novoline.getInstance());
      Minecraft.getInstance().displayGuiScreen((GuiScreen)null);
      Minecraft.getInstance().displayGuiScreen(Novoline.getInstance().getDropDownGUI());
   }

   private void lambda$refreshConfigs$4(String var1) {
      boolean var2 = Config.a();
      if(var1.isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Select a script!", NotificationType.ERROR);
      }

      Novoline.getInstance().u().c(var1);
      this.b();
      Minecraft.getInstance().displayGuiScreen((GuiScreen)null);
      Minecraft.getInstance().displayGuiScreen(Novoline.getInstance().getDropDownGUI());
   }

   private static void lambda$refreshConfigs$3(String var0) {
      boolean var1 = Config.d();
      if(var0.isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Select a script!", NotificationType.ERROR);
      }

      Iterator var2 = Novoline.getInstance().u().a().iterator();
      if(var2.hasNext()) {
         K6 var3 = (K6)var2.next();
         if(var3.g() == var0) {
            var3.a(false);
            Minecraft.getInstance().displayGuiScreen((GuiScreen)null);
            Minecraft.getInstance().displayGuiScreen(Novoline.getInstance().getDropDownGUI());
         }
      }

   }

   private static void lambda$refreshConfigs$2(String var0) {
      boolean var1 = Config.a();
      if(var0.isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Select a script!", NotificationType.ERROR);
      }

      Novoline.getInstance().u().d(var0);
      Minecraft.getInstance().displayGuiScreen((GuiScreen)null);
      Minecraft.getInstance().displayGuiScreen(Novoline.getInstance().getDropDownGUI());
   }

   private void lambda$refreshConfigs$1(K6 var1) {
      this.i.add(new Config(var1, this));
   }

   private static void lambda$drawScreen$0(int var0, int var1, Config var2) {
      var2.b(var0, var1);
   }
}
