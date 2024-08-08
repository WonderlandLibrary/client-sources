package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.settings.EnumSetting;
import com.example.editme.settings.Setting;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.ModuleParser;
import com.example.editme.util.module.ModuleManager;
import java.util.List;

public class SettingsCommand extends Command {
   public void call(String[] var1) {
      if (var1[0] == null) {
         Command.sendChatMessage("Please specify a module to display the settings of.");
      } else {
         Module var2 = ModuleManager.getModuleByName(var1[0]);
         if (var2 == null) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Couldn't find a module &b").append(var1[0]).append("!")));
         } else {
            List var3 = var2.settingList;
            String[] var4 = new String[var3.size()];

            for(int var5 = 0; var5 < var3.size(); ++var5) {
               Setting var6 = (Setting)var3.get(var5);
               var4[var5] = String.valueOf((new StringBuilder()).append("&b").append(var6.getName()).append("&3(=").append(var6.getValue()).append(")  &ftype: &3").append(var6.getValue().getClass().getSimpleName()));
               if (var6 instanceof EnumSetting) {
                  var4[var5] = String.valueOf((new StringBuilder()).append(var4[var5]).append("  ("));
                  Enum[] var7 = (Enum[])((Enum[])((EnumSetting)var6).clazz.getEnumConstants());
                  Enum[] var8 = var7;
                  int var9 = var7.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     Enum var11 = var8[var10];
                     var4[var5] = String.valueOf((new StringBuilder()).append(var4[var5]).append(var11.name()).append(", "));
                  }

                  var4[var5] = String.valueOf((new StringBuilder()).append(var4[var5].substring(0, var4[var5].length() - 2)).append(")"));
               }
            }

            Command.sendStringChatMessage(var4);
         }
      }
   }

   public SettingsCommand() {
      super("settings", (new ChunkBuilder()).append("module", true, new ModuleParser()).build());
   }
}
