package cc.novoline.gui.screen.dropdown.config;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.gui.screen.dropdown.Tab;
import cc.novoline.gui.screen.dropdown.config.ConfigButton;
import cc.novoline.gui.screen.dropdown.config.ConfigTextField;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.l6;
import net.minecraft.client.gui.Gui;

public class ConfigTab extends Tab {
   private final List configs = new CopyOnWriteArrayList();
   private ConfigTextField newConfigName;
   private l6 j;

   public ConfigTab(float var1, float var2) {
      super((EnumModuleType)null, var1, var2);
      this.refreshConfigs();
   }

   public void drawScreen(int var1, int var2) {
      l6.d();
      HUD var4 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      int var5 = 0;
      Iterator var6 = this.configs.iterator();
      if(var6.hasNext()) {
         l6 var7 = (l6)var6.next();
         var5 += var7.a();
      }

      ++var5;
      if(!this.opened) {
         var5 = 0;
      }

      Gui.drawRect((double)(this.getPosX() - 1.0F), (double)this.getPosY(), (double)(this.getPosX() + 101.0F), (double)(this.getPosY() + 15.0F + (float)var5), (new Color(29, 29, 29, 255)).getRGB());
      Fonts$SF$SF_20.SF_20.drawString("Configs", (double)(this.getPosX() + 4.0F), (double)(this.getPosY() + 4.0F), -1, true);
      if(this.opened) {
         this.configs.forEach(ConfigTab::lambda$drawScreen$0);
      }

   }

   public void refreshConfigs() {
      this.configs.clear();
      this.a((l6)null);
      Novoline.getInstance().getModuleManager().getConfigManager().getConfigs().forEach(this::lambda$refreshConfigs$1);
      this.newConfigName = new ConfigTextField("Config name", this);
      this.configs.add(this.newConfigName);
      this.configs.add(new ConfigButton("Load", this, ConfigTab::lambda$refreshConfigs$2));
      this.configs.add(new ConfigButton("Save", this, this::lambda$refreshConfigs$3));
      this.configs.add(new ConfigButton("Delete", this, this::lambda$refreshConfigs$4));
   }

   public void mouseClicked(int var1, int var2, int var3) {
      String var4 = l6.d();
      if(this.b(var1, var2) && var3 == 1) {
         this.opened = !this.opened;
      }

      if(this.opened) {
         this.configs.forEach(ConfigTab::lambda$mouseClicked$5);
      }

   }

   public void keyTyped(char var1, int var2) {
      this.configs.forEach(ConfigTab::lambda$keyTyped$6);
   }

   public List getConfigs() {
      return this.configs;
   }

   public l6 d() {
      return this.j;
   }

   public void a(l6 var1) {
      this.j = var1;
   }

   private static void lambda$keyTyped$6(char var0, int var1, l6 var2) {
      var2.a(var0, var1);
   }

   private static void lambda$mouseClicked$5(int var0, int var1, int var2, l6 var3) {
      var3.a(var0, var1, var2);
   }

   private void lambda$refreshConfigs$4(String var1) {
      String var2 = l6.d();
      if(var1.isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Select a config!", NotificationType.ERROR);
      }

      ConfigCommand.deleteConfig(Novoline.getInstance().getModuleManager().getConfigManager(), var1);
      this.refreshConfigs();
   }

   private void lambda$refreshConfigs$3(String var1) {
      String var2 = l6.d();
      ConfigCommand.saveConfig(Novoline.getInstance().getModuleManager().getConfigManager(), this.d() != null?this.d().b():this.newConfigName.a());
      this.refreshConfigs();
   }

   private static void lambda$refreshConfigs$2(String var0) {
      String var1 = l6.d();
      if(var0.isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Select a config!", NotificationType.ERROR);
      }

      ConfigCommand.loadConfig(Novoline.getInstance().getModuleManager().getConfigManager(), var0);
   }

   private void lambda$refreshConfigs$1(String var1) {
      this.configs.add(new l6(var1, this));
   }

   private static void lambda$drawScreen$0(int var0, int var1, l6 var2) {
      var2.b(var0, var1);
   }
}
