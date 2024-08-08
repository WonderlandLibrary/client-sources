package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.ModuleParser;
import com.example.editme.util.module.ModuleManager;

public class ToggleCommand extends Command {
   public void call(String[] var1) {
      if (var1.length == 0) {
         Command.sendChatMessage("Please specify a module!");
      } else {
         Module var2 = ModuleManager.getModuleByName(var1[0]);
         if (var2 == null) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown module '").append(var1[0]).append("'")));
         } else {
            var2.toggle();
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append(var2.getName()).append(var2.isEnabled() ? " &aenabled" : " &cdisabled")));
         }
      }
   }

   public ToggleCommand() {
      super("toggle", (new ChunkBuilder()).append("module", true, new ModuleParser()).build());
   }
}
