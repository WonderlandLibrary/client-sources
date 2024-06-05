package cc.novoline.gui.screen.click;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI$1;
import cc.novoline.gui.screen.click.Module;
import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.gui.screen.click.Tab;
import cc.novoline.gui.screen.config.ConfigMenu;
import cc.novoline.gui.screen.config.GuiConfig;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_35;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_16;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

public class DiscordGUI extends GuiScreen {
   private final Novoline novoline;
   private int scaling;
   private static final Timer timerScroll = new Timer();
   private int yTab = 63;
   private int xCoordinate = 100;
   private int yCoordinate = 100;
   private int width = 190;
   private int height = 300;
   private final List tabs;
   private final List configs;
   private boolean configsOpened;
   private int x2;
   private int y2;
   private int resX;
   private int resY;
   private boolean dragging;
   private boolean resizing;

   public int sWidth() {
      return super.width * 2;
   }

   public int sHeight() {
      return super.height * 2;
   }

   public DiscordGUI(@NotNull Novoline var1) {
      Scroll.b();
      this.tabs = new ObjectArrayList();
      this.configs = new CopyOnWriteArrayList();
      this.novoline = var1;
      EnumModuleType[] var3 = EnumModuleType.values();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         EnumModuleType var6 = var3[var5];
         this.tabs.add(new Tab(this.novoline, var6, this.yTab));
         this.yTab += 35;
         ++var5;
      }

   }

   public void initGui() {
      super.initGui();
   }

   public void onGuiClosed() {
      Scroll.b();
      Iterator var2 = Manager.getSettingList().iterator();
      if(var2.hasNext()) {
         Setting var3 = (Setting)var2.next();
         switch(DiscordGUI$1.$SwitchMap$cc$novoline$gui$screen$setting$SettingType[var3.getSettingType().ordinal()]) {
         case 1:
            var3.setDragging(false);
         case 2:
         case 3:
            var3.setOpened(false);
         case 4:
            var3.setTextHovered(false);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      int[] var4 = Scroll.b();
      if(this.dragging) {
         this.xCoordinate = this.x2 + var1;
         this.yCoordinate = this.y2 + var2;
      }

      if(this.resizing) {
         if(this.resX + var1 > 190) {
            this.width = this.resX + var1;
         }

         if(this.resY + var2 > 300) {
            this.height = this.resY + var2;
         }
      }

      boolean var5 = false;
      RenderUtils.drawRoundedRect((float)this.xCoordinate, (float)(this.yCoordinate - 10), (float)(150 + this.width), (float)(this.yCoordinate + 5), 8.0F, var5?(new Color(22, 23, 26)).getRGB():-14671323);
      RenderUtils.drawRoundedRect((float)this.xCoordinate, (float)this.yCoordinate, 49.0F, (float)this.height, 9.0F, var5?(new Color(29, 30, 34)).getRGB():-14671323);
      String var6 = "MATERIALINE";
      var6 = "NOVOLINE";
      drawRect(this.xCoordinate, this.yCoordinate, this.xCoordinate + 10, this.yCoordinate + 4, var5?(new Color(29, 30, 34)).getRGB():-14671323);
      RenderUtils.drawRoundedRect((float)(this.xCoordinate + 45 + 105), (float)this.yCoordinate, (float)this.width, (float)this.height, 4.0F, var5?(new Color(32, 34, 37)).getRGB():-13223618);
      RenderUtils.drawRoundedRect((float)(this.xCoordinate + 7), (float)(this.yCoordinate + 40), 31.0F, 3.0F, 0.0F, var5?(new Color(22, 23, 26)).getRGB():-13684426);
      drawRect(this.xCoordinate + 44, this.yCoordinate, this.xCoordinate + 45 + 110, this.yCoordinate + this.height, var5?(new Color(22, 23, 26)).getRGB():-13684426);
      drawRect(this.xCoordinate + 44, this.yCoordinate + 20, this.xCoordinate + 45 + 105 + this.width, this.yCoordinate + 21, var5?(new Color(17, 18, 20)).getRGB():-14671323);
      if(!this.isAnyTabSelected() && !this.configsOpened) {
         int var7 = -9801351;
         FontRenderer var8 = this.mc.fontRendererObj;
         var8.drawStringWithShadow("<------------", (float)(this.xCoordinate + 59), (float)(this.yCoordinate + 65), -9801351);
         var8.drawStringWithShadow("Select one of", (float)(this.xCoordinate + 67), (float)(this.yCoordinate + 75), -9801351);
         var8.drawStringWithShadow("these", (float)(this.xCoordinate + 85), (float)(this.yCoordinate + 85), -9801351);
         var8.drawStringWithShadow("-------------", (float)(this.xCoordinate + 59), (float)(this.yCoordinate + 95), -9801351);
         var8.drawStringWithShadow("Enjoy the Novoline", (float)(this.xCoordinate + 54), (float)(this.yCoordinate + 105), -9801351);
         var8.drawStringWithShadow("Experience", (float)(this.xCoordinate + 70), (float)(this.yCoordinate + 117), -9801351);
         var8.drawStringWithShadow("N O V O L I N E", (float)(this.xCoordinate + 64), (float)(this.yCoordinate + 7), -9801351);
         var8.drawStringWithShadow("Build " + this.novoline.getVersion(), (float)(this.xCoordinate + 45 + 105) + ((float)this.width / 2.0F - (float)var8.d("091019 - B E T A") / 2.0F), (float)(this.yCoordinate + 7), -9801351);
      }

      this.tabs.forEach(DiscordGUI::lambda$drawScreen$0);
      ConfigMenu.drawScreen(var1, var2);
      RenderUtils.drawFilledCircle((float)(this.xCoordinate + 22), (float)(this.yCoordinate + 20), 15.0F, -13223617);
      Fonts$ICONFONT$ICONFONT_35.ICONFONT_35.drawString("?", (float)(this.xCoordinate + 14), (float)(this.yCoordinate + 14), -1);
      if(this.isDiscord(var1, var2)) {
         RenderUtils.drawRoundedRect((float)(this.xCoordinate - Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth("Discord Server") - 12), (float)(this.yCoordinate + 14), (float)(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth("Discord Server") + 7), 10.0F, 5.0F, -13684945);
         Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString("Discord Server", (float)(this.xCoordinate - Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth("Discord Server") - 10), (float)(this.yCoordinate + 16), -1);
      }

      super.drawScreen(var1, var2, var3);
   }

   private boolean isAnyTabSelected() {
      return this.tabs.stream().anyMatch(Tab::isSelected);
   }

   @Nullable
   public Tab getSelectedTab() {
      return (Tab)this.tabs.stream().filter(Tab::isSelected).findAny().orElse((Object)null);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      int[] var4 = Scroll.b();
      Iterator var5 = this.tabs.iterator();
      if(var5.hasNext()) {
         Tab var6 = (Tab)var5.next();
         if(var6.isHovered(var1, var2)) {
            Iterator var7 = this.tabs.iterator();
            if(var7.hasNext()) {
               Tab var8 = (Tab)var7.next();
               var8.setSelected(false);
            }

            var6.setSelected(true);
            this.configsOpened = false;
         }
      }

      if(this.isHovered(var1, var2)) {
         this.x2 = this.xCoordinate - var1;
         this.y2 = this.yCoordinate - var2;
         this.dragging = true;
      }

      if(this.isHoveredResize(var1, var2)) {
         this.resX = this.width - var1;
         this.resY = this.height - var2;
         this.resizing = true;
      }

      if(ConfigMenu.isHovered(var1, var2)) {
         if(!this.configsOpened) {
            this.initConfigs();
         }

         var5 = this.tabs.iterator();
         if(var5.hasNext()) {
            Tab var12 = (Tab)var5.next();
            var12.setSelected(false);
         }

         this.configsOpened = true;
      }

      Tab var11 = this.getSelectedTab();
      if(var11 != null) {
         var11.mouseClicked(var1, var2, var3);
      }

      if(this.isDiscord(var1, var2)) {
         try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://discord.gg/qXHPgHQ");
         } catch (Exception var9) {
            ;
         }
      }

      if(this.configsOpened) {
         ConfigMenu.mouseClicked(var1, var2, var3);
      }

      super.mouseClicked(var1, var2, var3);
   }

   @Nullable
   public static Scroll scroll() {
      Scroll.b();
      int var1 = Mouse.getDWheel();
      return var1 > 0?Scroll.UP:(var1 < 0?Scroll.DOWN:null);
   }

   public void mouseReleased(int var1, int var2, int var3) {
      int[] var4 = Scroll.b();
      this.dragging = false;
      this.resizing = false;
      this.tabs.stream().filter(Tab::isSelected).findFirst().ifPresent(DiscordGUI::lambda$mouseReleased$1);
   }

   protected void keyTyped(char var1, int var2) {
      Scroll.b();
      Tab var4 = this.getSelectedTab();
      if(var2 == 1 && (var4 == null || var4.getModuleList().stream().noneMatch(Module::isListening)) && Manager.getSettingList().stream().noneMatch(DiscordGUI::lambda$keyTyped$2)) {
         if(ConfigMenu.isTextHovered) {
            return;
         }

         this.mc.displayGuiScreen((GuiScreen)null);
         if(this.mc.currentScreen != null) {
            return;
         }

         this.mc.setIngameFocus();
      }

      if(var4 != null) {
         var4.keyTyped(var1, var2);
      }

      ConfigMenu.keyTyped(var1, var2);
   }

   private boolean isHovered(int var1, int var2) {
      int[] var3 = Scroll.b();
      return var1 >= this.xCoordinate && var1 <= this.xCoordinate + 45 + 105 + this.width && var2 >= this.yCoordinate - 7 && var2 <= this.yCoordinate + 20;
   }

   private boolean isHoveredResize(int var1, int var2) {
      int[] var3 = Scroll.b();
      return var1 >= this.xCoordinate + 45 + 105 + this.width - 5 && var1 <= this.xCoordinate + 45 + 105 + this.width && var2 >= this.yCoordinate + this.height - 5 && var2 <= this.yCoordinate + this.height;
   }

   private boolean isDiscord(int var1, int var2) {
      int[] var3 = Scroll.b();
      return var1 >= this.xCoordinate + 7 && var1 <= this.xCoordinate + 37 && var2 >= this.yCoordinate + 5 && var2 <= this.yCoordinate + 35;
   }

   public void initConfigs() {
      Scroll.b();
      this.configs.clear();

      List var2;
      try {
         String var3 = ConfigManager.getExtension();
         var2 = (List)Files.walk(this.novoline.getModuleManager().getConfigManager().getConfigsFolder(), new FileVisitOption[0]).filter(DiscordGUI::lambda$initConfigs$3).filter(DiscordGUI::lambda$initConfigs$4).collect(Collectors.toCollection(ObjectArrayList::<init>));
      } catch (IOException var6) {
         Novoline.getLogger().error("An I/O error occurred while getting contents of configs folder", var6);
         return;
      }

      List var10000 = var2;

      try {
         Iterator var7 = var10000.iterator();
         if(var7.hasNext()) {
            Path var4 = (Path)var7.next();
            this.configs.add(GuiConfig.of(var4));
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
      }

   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   @NotNull
   public List getConfigs() {
      return this.configs;
   }

   public boolean isConfigsOpened() {
      return this.configsOpened;
   }

   public int getXCoordinate() {
      return this.xCoordinate;
   }

   public int getYCoordinate() {
      return this.yCoordinate;
   }

   private static boolean lambda$initConfigs$4(String var0, Path var1) {
      return var1.getFileName().toString().endsWith(var0);
   }

   private static boolean lambda$initConfigs$3(Path var0) {
      return Files.isRegularFile(var0, new LinkOption[0]);
   }

   private static boolean lambda$keyTyped$2(Setting var0) {
      int[] var1 = Scroll.b();
      return (var0.getSettingType() == SettingType.SELECTBOX || var0.getSettingType() == SettingType.COMBOBOX) && var0.isOpened() || var0.getSettingType() == SettingType.TEXTBOX && var0.isTextHovered();
   }

   private static void lambda$mouseReleased$1(int var0, int var1, int var2, Tab var3) {
      var3.mouseReleased(var0, var1, var2);
   }

   private static void lambda$drawScreen$0(int var0, int var1, Tab var2) {
      var2.drawScreen(var0, var1);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
