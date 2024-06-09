package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.module.Module;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.util.StringConversions;
import exhibition.util.misc.ChatUtil;
import java.util.Iterator;
import net.minecraft.util.EnumChatFormatting;

public class Settings extends Command {
   public Settings(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         Module module = null;
         if (args.length > 0) {
            module = Client.getModuleManager().get(args[0]);
         }

         if (module == null) {
            this.printUsage();
         } else {
            if (args.length == 1) {
               SettingsMap moduleSettings = module.getSettings();
               ChatUtil.printChat("§4[§cE§4]§8 [" + EnumChatFormatting.DARK_RED + module.getName() + EnumChatFormatting.DARK_GRAY + "] - Settings: " + EnumChatFormatting.DARK_RED + moduleSettings.size());
               Iterator var4 = moduleSettings.values().iterator();

               while(var4.hasNext()) {
                  Setting setting = (Setting)var4.next();
                  if (setting != null) {
                     this.printSetting(setting);
                  }
               }
            } else if (args.length >= 2) {
               Setting setting = this.getSetting(module.getSettings(), args[1]);
               if (setting == null) {
                  this.printUsage();
                  return;
               }

               if (args.length == 2) {
                  this.printSetting(setting);
               } else if (args.length >= 3) {
                  String objText = args[2];

                  try {
                     if (setting.getValue() instanceof Number) {
                        Object newValue = StringConversions.castNumber(objText, setting.getValue());
                        if (newValue != null) {
                           ChatUtil.printChat("§4[§cE§4]§8 " + module.getName() + "'s " + setting.getName().toLowerCase() + " has been changed to: " + EnumChatFormatting.DARK_AQUA + newValue);
                           setting.setValue(newValue);
                           module.getSettings();
                           module.save();
                           return;
                        }
                     } else {
                        if (setting.getValue().getClass().equals(String.class)) {
                           String parsed = objText.toString().replaceAll("_", " ");
                           ChatUtil.printChat("§4[§cE§4]§8 " + module.getName() + "'s " + setting.getName().toLowerCase() + " has been changed to: " + EnumChatFormatting.DARK_RED + parsed);
                           setting.setValue(parsed);
                           module.getSettings();
                           module.save();
                           return;
                        }

                        if (setting.getValue().getClass().equals(Boolean.class)) {
                           ChatUtil.printChat("§4[§cE§4]§8 " + module.getName() + "'s " + setting.getName().toLowerCase() + " has been changed to: " + EnumChatFormatting.DARK_RED + objText);
                           setting.setValue(Boolean.parseBoolean(objText));
                           module.getSettings();
                           module.save();
                           return;
                        }
                     }
                  } catch (Exception var6) {
                     ;
                  }

                  ChatUtil.printChat("§4[§cE§4]§8 ERROR: Could not apply the value '" + objText + "' to " + module.getName() + "'s " + setting.getName());
               }
            }

         }
      }
   }

   private Setting getSetting(SettingsMap map, String settingText) {
      settingText = settingText.toUpperCase();
      if (map.containsKey(settingText)) {
         return (Setting)map.get(settingText);
      } else {
         Iterator var3 = map.keySet().iterator();

         String key;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            key = (String)var3.next();
         } while(!key.startsWith(settingText));

         return (Setting)map.get(key);
      }
   }

   private void printSetting(Setting setting) {
      if (setting == null) {
         this.printUsage();
      } else {
         String typeStr = setting.getType() == null ? setting.getValue().getClass().getSimpleName() : setting.getType().getTypeName();
         if (typeStr.contains(".")) {
            typeStr.substring(typeStr.lastIndexOf(".") + 1);
         }

         String settingText = EnumChatFormatting.GRAY + "" + setting.getName().toLowerCase() + ": " + EnumChatFormatting.RESET + EnumChatFormatting.DARK_RED + setting.getValue();
         ChatUtil.printChat(settingText);
      }
   }

   public String getUsage() {
      return "set <Module> " + EnumChatFormatting.ITALIC + "[optional]" + EnumChatFormatting.RESET + "<Option> " + EnumChatFormatting.ITALIC + "[optional]" + EnumChatFormatting.RESET + "<Value>";
   }
}
