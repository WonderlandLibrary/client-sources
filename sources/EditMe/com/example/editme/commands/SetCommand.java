package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.settings.ISettingSafe;
import com.example.editme.settings.Setting;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.ModuleParser;
import com.example.editme.util.module.ModuleManager;
import java.util.Optional;
import java.util.stream.Collectors;

public class SetCommand extends Command {
   private static boolean lambda$call$1(String[] var0, Setting var1) {
      return var1.getName().equalsIgnoreCase(var0[1]);
   }

   private static String lambda$call$0(Setting var0) {
      return var0.getName();
   }

   public SetCommand() {
      super("set", (new ChunkBuilder()).append("module", true, new ModuleParser()).append("setting", true).append("value", true).build());
   }

   public void call(String[] var1) {
      if (var1[0] == null) {
         Command.sendChatMessage("Please specify a module!");
      } else {
         Module var2 = ModuleManager.getModuleByName(var1[0]);
         if (var2 == null) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown module &b").append(var1[0]).append("&r!")));
         } else if (var1[1] == null) {
            String var7 = String.join(", ", (Iterable)var2.settingList.stream().map(SetCommand::lambda$call$0).collect(Collectors.toList()));
            if (var7.isEmpty()) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Module &b").append(var2.getName()).append("&r has no settings.")));
            } else {
               Command.sendStringChatMessage(new String[]{"Please specify a setting! Choose one of the following:", var7});
            }

         } else {
            Optional var3 = var2.settingList.stream().filter(SetCommand::lambda$call$1).findFirst();
            if (!var3.isPresent()) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown setting &b").append(var1[1]).append("&r in &b").append(var2.getName()).append("&r!")));
            } else {
               ISettingSafe var4 = (ISettingSafe)var3.get();
               if (var1[2] == null) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var4.getName()).append("&r is a &3").append(var4.getValueClass().getSimpleName()).append("&r. Its current value is &3").append(var4.getValueAsString())));
               } else {
                  try {
                     var4.setValueFromString(var1[2]);
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Set &b").append(var4.getName()).append("&r to &3").append(var1[2]).append("&r.")));
                  } catch (Exception var6) {
                     var6.printStackTrace();
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Unable to set value! &6").append(var6.getMessage())));
                  }

               }
            }
         }
      }
   }
}
