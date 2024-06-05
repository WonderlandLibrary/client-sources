package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.modules.exceptions.OutdatedConfigException;
import cc.novoline.modules.exceptions.ReadConfigException;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import cc.novoline.utils.notifications.NotificationType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.Uj;
import net.X9;
import net.a_E;
import net.minecraft.util.EnumChatFormatting;

public final class ConfigCommand extends NovoCommand {
   public static String j = "";

   public ConfigCommand(Novoline var1) {
      super(var1, "config", "Manages configs stuff", (Iterable)Arrays.asList(new String[]{"cfg", "conf", "configs", "configure", "configuration"}));
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.a("Configs help:", ".configs", new Uj[]{MessageFactory.a("list", "shows all configs"), MessageFactory.a("load (name)", "loads a config"), MessageFactory.a("save (name)", "saves a config"), MessageFactory.a("delete (name)", "removes a config")});
      } else {
         ConfigManager var3 = this.novoline.getModuleManager().getConfigManager();
         String var4 = var1[0].toLowerCase();
         byte var6 = -1;
         switch(var4.hashCode()) {
         case 1427818632:
            if(!var4.equals("download")) {
               break;
            }

            var6 = 0;
         case -838595071:
            if(!var4.equals("upload")) {
               break;
            }

            var6 = 1;
         case -934610812:
            if(!var4.equals("remove")) {
               break;
            }

            var6 = 2;
         case 3237038:
            if(!var4.equals("info")) {
               break;
            }

            var6 = 3;
         case 3327206:
            if(!var4.equals("load")) {
               break;
            }

            var6 = 4;
         case 3522941:
            if(!var4.equals("save")) {
               break;
            }

            var6 = 5;
         case -1335458389:
            if(!var4.equals("delete")) {
               break;
            }

            var6 = 6;
         case 3322014:
            if(var4.equals("list")) {
               var6 = 7;
            }
         }

         switch(var6) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            if(var1.length < 2) {
               this.send("Usage: .config " + var4 + " <name>", EnumChatFormatting.RED);
               return;
            } else {
               String var10 = var1[1];
               byte var9 = -1;
               switch(var4.hashCode()) {
               case 3327206:
                  if(!var4.equals("load")) {
                     break;
                  }

                  var9 = 0;
               case 3522941:
                  if(!var4.equals("save")) {
                     break;
                  }

                  var9 = 1;
               case -1335458389:
                  if(var4.equals("delete")) {
                     var9 = 2;
                  }
               }

               switch(var9) {
               case 0:
                  loadConfig(var3, var10);
                  return;
               case 1:
                  saveConfig(var3, var10);
                  return;
               case 2:
                  deleteConfig(var3, var10);
                  return;
               default:
                  return;
               }
            }
         case 7:
            List var7 = var3.getConfigs();
            TextMessage var8 = MessageFactory.text("List of configs:");
            if(var7.isEmpty()) {
               var8.append(" (empty)", EnumChatFormatting.RED);
            }

            this.send(var8, true);
            var7.forEach(this::lambda$process$8);
            return;
         default:
            this.notifyError("Unknown command: " + var1[0]);
         }
      }
   }

   public static void loadConfig(ConfigManager var0, String var1) {
      int[] var2 = a_E.b();
      if(var1.trim().isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Name may not be blank!", 2000, NotificationType.ERROR);
      } else {
         Throwable var3;
         try {
            ConfigManager var10000 = var0;
            String var10001 = var1;
            boolean var10002 = true;

            try {
               var10000.load(var10001, var10002);
               Novoline.getInstance().getNotificationManager().pop("Loaded config " + var1 + "!", 2000, NotificationType.SUCCESS);
               j = var1;
               return;
            } catch (Throwable var4) {
               var3 = var4;
            }
         } catch (FileNotFoundException var5) {
            Novoline.getInstance().getNotificationManager().pop("Config not found!", 2000, NotificationType.ERROR);
            return;
         } catch (IOException var6) {
            Novoline.getLogger().warn("An I/O error occurred while reading config!", var6);
            Novoline.getInstance().getNotificationManager().pop("Cannot read config!", 2000, NotificationType.ERROR);
            return;
         } catch (X9 var7) {
            Novoline.getLogger().warn("An error occurred while deserializing config!", var7);
            Novoline.getInstance().getNotificationManager().pop("Cannot load config!", 2000, NotificationType.ERROR);
            return;
         } catch (OutdatedConfigException var8) {
            Novoline.getInstance().getNotificationManager().pop("Config is outdated!", 2000, NotificationType.ERROR);
            return;
         }

         Novoline.getLogger().warn("An unexpected error occurred while loading config!", var3);
         Novoline.getInstance().getNotificationManager().pop("Cannot load config!", 2000, NotificationType.ERROR);
      }
   }

   public static void saveConfig(ConfigManager var0, String var1) {
      int[] var2 = a_E.b();
      if(var1.trim().isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Name may not be blank!", 2000, NotificationType.ERROR);
      } else {
         Throwable var3;
         try {
            ConfigManager var10000 = var0;
            String var10001 = var1;

            try {
               var10000.save(var10001);
               Novoline.getInstance().getNotificationManager().pop("Saved config " + var1 + "!", 2000, NotificationType.SUCCESS);
               return;
            } catch (Throwable var4) {
               var3 = var4;
            }
         } catch (ReadConfigException var5) {
            Novoline.getLogger().warn("An I/O error occurred while reading config!", var5);
            Novoline.getInstance().getNotificationManager().pop("Cannot read config!", 2000, NotificationType.ERROR);
            return;
         } catch (IOException var6) {
            Novoline.getLogger().warn("An I/O error occurred while saving config!", var6);
            Novoline.getInstance().getNotificationManager().pop("Cannot save config!", 2000, NotificationType.ERROR);
            return;
         } catch (X9 var7) {
            Novoline.getLogger().warn("An I/O error occurred while serializing config!", var7);
            Novoline.getInstance().getNotificationManager().pop("Cannot save config!", 2000, NotificationType.ERROR);
            return;
         }

         Novoline.getLogger().warn("An unexpected error occurred while saving config!", var3);
         Novoline.getInstance().getNotificationManager().pop("Cannot save config!", 2000, NotificationType.ERROR);
      }
   }

   public static String saveToString(ConfigManager var0, String var1) {
      int[] var2 = a_E.b();
      if(var1.trim().isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Name may not be blank!", 2000, NotificationType.ERROR);
         return null;
      } else {
         ConfigManager var10000 = var0;
         String var10001 = var1;

         try {
            return var10000.saveToString(var10001);
         } catch (IOException var4) {
            Novoline.getLogger().warn("An I/O error occurred while saving config!", var4);
            Novoline.getInstance().getNotificationManager().pop("Cannot save config!", 2000, NotificationType.ERROR);
         } catch (X9 var5) {
            Novoline.getLogger().warn("An I/O error occurred while serializing config!", var5);
            Novoline.getInstance().getNotificationManager().pop("Cannot save config!", 2000, NotificationType.ERROR);
         }

         return null;
      }
   }

   public static void deleteConfig(ConfigManager var0, String var1) {
      int[] var2 = a_E.b();
      if(var1.trim().isEmpty()) {
         Novoline.getInstance().getNotificationManager().pop("Name may not be blank!", 2000, NotificationType.ERROR);
      } else {
         Throwable var3;
         try {
            ConfigManager var10000 = var0;
            String var10001 = var1;

            try {
               var10000.delete(var10001);
               Novoline.getInstance().getNotificationManager().pop("Deleted config " + var1 + "!", 2000, NotificationType.SUCCESS);
               return;
            } catch (Throwable var4) {
               var3 = var4;
            }
         } catch (FileNotFoundException var5) {
            Novoline.getInstance().getNotificationManager().pop("Config not found!", 2000, NotificationType.ERROR);
            return;
         } catch (IOException var6) {
            Novoline.getLogger().error("An I/O error occurred while deleting config!", var6);
            Novoline.getInstance().getNotificationManager().pop("Cannot delete config!", 2000, NotificationType.ERROR);
            return;
         }

         Novoline.getLogger().warn("An unexpected error occurred while deleting config!", var3);
         Novoline.getInstance().getNotificationManager().pop("Cannot delete config!", 2000, NotificationType.ERROR);
      }
   }

   private void lambda$process$8(String var1) {
      this.send(MessageFactory.text("> ", EnumChatFormatting.GRAY).append(var1, EnumChatFormatting.GREEN));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
