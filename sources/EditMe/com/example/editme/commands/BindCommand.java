package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.builders.SettingBuilder;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.ModuleParser;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;

public class BindCommand extends Command {
   public static Setting modifiersEnabled = SettingBuilder.register(SettingsManager.b("modifiersEnabled", false), "binds");

   public BindCommand() {
      super("bind", (new ChunkBuilder()).append("[module]|modifiers", true, new ModuleParser()).append("[key]|[on|off]", true).build());
   }

   public void call(String[] var1) {
      if (var1.length == 1) {
         Command.sendChatMessage("Please specify a module.");
      } else {
         String var2 = var1[0];
         String var3 = var1[1];
         if (var2.equalsIgnoreCase("modifiers")) {
            if (var3 == null) {
               sendChatMessage("Expected: on or off");
            } else {
               if (var3.equalsIgnoreCase("on")) {
                  modifiersEnabled.setValue(true);
                  sendChatMessage("Turned modifiers on.");
               } else if (var3.equalsIgnoreCase("off")) {
                  modifiersEnabled.setValue(false);
                  sendChatMessage("Turned modifiers off.");
               } else {
                  sendChatMessage("Expected: on or off");
               }

            }
         } else {
            Module var4 = ModuleManager.getModuleByName(var2);
            if (var4 == null) {
               sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown module '").append(var2).append("'!")));
            } else if (var3 == null) {
               sendChatMessage(String.valueOf((new StringBuilder()).append(var4.getName()).append(" is bound to &b").append(var4.getBindName())));
            } else {
               int var5 = Wrapper.getKey(var3);
               if (var3.equalsIgnoreCase("none")) {
                  var5 = -1;
               }

               if (var5 == 0) {
                  sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown key '").append(var3).append("'!")));
               } else {
                  var4.getBind().setKey(var5);
                  sendChatMessage(String.valueOf((new StringBuilder()).append("Bind for &b").append(var4.getName()).append("&r set to &b").append(var3.toUpperCase())));
               }
            }
         }
      }
   }
}
