package cc.novoline.gui.screen.config;

import cc.novoline.Novoline;
import cc.novoline.commands.impl.ConfigCommand;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.gui.screen.config.ConfigMenu$1;
import cc.novoline.gui.screen.config.GuiConfig;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_24;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_35;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_26;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_16;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_20;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.acE;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public final class ConfigMenu {
   static int block = 5;
   public static boolean isTextHovered;
   private static String message = "";
   private static final Timer backspace = new Timer();
   private static acE[] c;

   public static void drawScreen(int var0, int var1) {
      b();
      Novoline var3 = Novoline.getInstance();
      DiscordGUI var4 = var3.getDiscordGUI();
      Scroll var5 = DiscordGUI.scroll();
      int var6 = var4.getXCoordinate();
      int var7 = var4.getYCoordinate();
      if(!var4.getConfigs().isEmpty()) {
         switch(ConfigMenu$1.$SwitchMap$cc$novoline$gui$screen$click$Scroll[var5.ordinal()]) {
         case 1:
            if(((GuiConfig)var4.getConfigs().get(var4.getConfigs().size() - 1)).getY() <= var7 + var4.getHeight() - 14) {
               break;
            }

            Iterator var8 = var4.getConfigs().iterator();
            if(var8.hasNext()) {
               GuiConfig var9 = (GuiConfig)var8.next();
               var9.setOffset(var9.getOffset() - 7);
            }
         case 2:
            if(((GuiConfig)var4.getConfigs().get(0)).getOffset() < 30) {
               Iterator var14 = var4.getConfigs().iterator();
               if(var14.hasNext()) {
                  GuiConfig var15 = (GuiConfig)var14.next();
                  var15.setOffset(var15.getOffset() + 7);
               }
            }
         }
      }

      if(var4.isConfigsOpened() && block <= 10) {
         ++block;
      }

      if(!var4.isConfigsOpened()) {
         block = 5;
      }

      int var16 = ((ClickGUI)var3.getModuleManager().getModule(ClickGUI.class)).getGUIColor();
      if(isHovered(var0, var1)) {
         if(var4.isConfigsOpened()) {
            Gui.drawRect(var6, var7 + 274 - block, var6 + 2, var7 + 274 + block, -1);
         }

         Gui.drawRect(var6, var7 + 269, var6 + 2, var7 + 279, -1);
      }

      if(var4.isConfigsOpened()) {
         Gui.drawRect(var6, var7 + 274 - block, var6 + 2, var7 + 274 + block, -1);
      }

      RenderUtils.drawFilledCircle((float)(var6 + 22), (float)(var7 + 274), 15.0F, -13223617);
      Fonts$ICONFONT$ICONFONT_35.ICONFONT_35.drawString("E", (float)(var6 + 16), (float)(var7 + 268), isHovered(var0, var1)?var16:-1);
      if(var4.isConfigsOpened()) {
         boolean var10 = areEnabled(var4);
         if(var10 && isLoad(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 26, var6 + 45 + 110, var7 + 40, -13223618);
         }

         if(var10 && isDelete(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 44, var6 + 45 + 110, var7 + 58, -13223618);
         }

         if(var10 && isSave(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 62, var6 + 45 + 110, var7 + 76, -13223618);
         }

         if(isRefresh(var0, var1)) {
            Gui.drawRect(var6 + 44, var7 + 80, var6 + 45 + 110, var7 + 94, -13223618);
         }

         Fonts$ICONFONT$ICONFONT_24.ICONFONT_24.drawString("I", (float)(var6 + 50), (float)(var7 + 7), -1);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString("Configs", (float)(var6 + 63), (float)(var7 + 7), -1);
         int var11 = var10?-7961722:(new Color(5131086)).getRGB();
         Fonts$SFBOLD$SFBOLD_26.SFBOLD_26.drawString("#", (float)(var6 + 50), (float)(var7 + 28), var11);
         Fonts$SFBOLD$SFBOLD_26.SFBOLD_26.drawString("#", (float)(var6 + 50), (float)(var7 + 46), var11);
         Fonts$SFBOLD$SFBOLD_26.SFBOLD_26.drawString("#", (float)(var6 + 50), (float)(var7 + 64), -7961722);
         Fonts$SFBOLD$SFBOLD_26.SFBOLD_26.drawString("#", (float)(var6 + 50), (float)(var7 + 82), -7961722);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString("Load", (float)(var6 + 63), (float)(var7 + 30), var11);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString("Delete", (float)(var6 + 63), (float)(var7 + 48), var11);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString("Save", (float)(var6 + 63), (float)(var7 + 66), -7961722);
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString("Refresh", (float)(var6 + 63), (float)(var7 + 84), -7961722);
         if(isTextHovered && Keyboard.isKeyDown(14) && backspace.delay(100.0D) && message.length() >= 1) {
            message = message.substring(0, message.length() - 1);
            backspace.reset();
         }

         RenderUtils.drawBorderedRect((float)(var6 + 50), (float)(var7 + 100), (float)(var6 + 120), (float)(var7 + 110), 1.0F, !isTextHovered(var0, var1) && !isTextHovered?1677721600:-8225316, -13684945);
         if(message.isEmpty()) {
            Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString("Config name", (float)(var6 + 52), (float)(var7 + 102), 1694498815);
         }

         if(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.stringWidth(message) > 65) {
            Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.trimStringToWidth(message, 60, true), (float)(var6 + 52), (float)(var7 + 102), -1);
         }

         Fonts$SFTHIN$SFTHIN_16.SFTHIN_16.drawString(message, (float)(var6 + 52), (float)(var7 + 102), -1);
         Iterator var12 = var4.getConfigs().iterator();
         if(var12.hasNext()) {
            GuiConfig var13 = (GuiConfig)var12.next();
            var13.update();
            if(!var13.isOutsideOfMenu()) {
               var13.drawScreen(var0, var1);
            }
         }
      }

   }

   private static boolean areEnabled(DiscordGUI var0) {
      return var0.getConfigs().stream().anyMatch(GuiConfig::isSelected);
   }

   public static void mouseClicked(int var0, int var1, int var2) {
      acE[] var3 = b();
      Novoline var4 = Novoline.getInstance();
      ModuleManager var5 = var4.getModuleManager();
      ConfigManager var6 = var5.getConfigManager();
      DiscordGUI var7 = var4.getDiscordGUI();
      List var8 = var7.getConfigs();
      boolean var9 = false;
      Iterator var10 = var8.iterator();
      if(var10.hasNext()) {
         GuiConfig var11 = (GuiConfig)var10.next();
         if(var11.isHovered(var0, var1)) {
            Iterator var12 = var8.iterator();
            if(var12.hasNext()) {
               GuiConfig var13 = (GuiConfig)var12.next();
               var13.setSelected(false);
            }

            var11.setSelected(true);
         }

         if(var11.isSelected()) {
            if(isLoad(var0, var1)) {
               ConfigCommand.loadConfig(var5.getConfigManager(), var11.getName());
               var7.initConfigs();
            }

            if(isDelete(var0, var1)) {
               ConfigCommand.deleteConfig(var5.getConfigManager(), var11.getName());
               var7.initConfigs();
            }

            if(isSave(var0, var1)) {
               ConfigCommand.saveConfig(var5.getConfigManager(), var11.getName());
               var7.initConfigs();
               var9 = true;
            }
         }
      }

      if(!var9 && isSave(var0, var1)) {
         if(message.length() > 25) {
            Novoline.getInstance().getNotificationManager().pop("Name is too long!", 2000, NotificationType.ERROR);
         }

         ConfigCommand.saveConfig(var5.getConfigManager(), message);
         var7.initConfigs();
      }

      if(isRefresh(var0, var1)) {
         var7.initConfigs();
      }

      if(isTextHovered(var0, var1)) {
         isTextHovered = !isTextHovered;
      }

      if(isTextHovered) {
         isTextHovered = false;
      }

   }

   public static void keyTyped(char var0, int var1) {
      acE[] var2 = b();
      if(var1 == 63) {
         Novoline.getInstance().getDiscordGUI().initConfigs();
      } else {
         if(isTextHovered) {
            if(var1 == 1) {
               isTextHovered = false;
            }

            if(ChatAllowedCharacters.isAllowedCharacter(var0)) {
               message = message + Character.toString(var0);
            }
         }

      }
   }

   public static boolean isLoad(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 44 && var0 <= var3.getXCoordinate() + 45 + 110 && var1 >= var3.getYCoordinate() + 26 && var1 <= var3.getYCoordinate() + 40;
   }

   public static boolean isDelete(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 44 && var0 <= var3.getXCoordinate() + 45 + 110 && var1 >= var3.getYCoordinate() + 44 && var1 <= var3.getYCoordinate() + 58;
   }

   public static boolean isSave(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 44 && var0 <= var3.getXCoordinate() + 45 + 110 && var1 >= var3.getYCoordinate() + 62 && var1 <= var3.getYCoordinate() + 76;
   }

   public static boolean isRefresh(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 44 && var0 <= var3.getXCoordinate() + 45 + 110 && var1 >= var3.getYCoordinate() + 80 && var1 <= var3.getYCoordinate() + 94;
   }

   public static boolean isHovered(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 8 && var0 <= var3.getXCoordinate() + 35 && var1 >= var3.getYCoordinate() + 259 && var1 <= var3.getYCoordinate() + 289;
   }

   public static boolean isTextHovered(int var0, int var1) {
      b();
      DiscordGUI var3 = Novoline.getInstance().getDiscordGUI();
      return var0 >= var3.getXCoordinate() + 50 && var0 <= var3.getXCoordinate() + 120 && var1 >= var3.getYCoordinate() + 100 && var1 <= var3.getYCoordinate() + 110;
   }

   static {
      b((acE[])null);
   }

   public static void b(acE[] var0) {
      c = var0;
   }

   public static acE[] b() {
      return c;
   }
}
