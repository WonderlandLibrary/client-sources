package com.example.editme.commands;

import com.example.editme.modules.Module;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.ModuleParser;
import com.example.editme.util.module.ModuleManager;

public class RenameModuleCommand extends Command {
   public RenameModuleCommand() {
      super("renamemodule", (new ChunkBuilder()).append("module", true, new ModuleParser()).append("name").build());
   }

   public void call(String[] var1) {
      if (var1.length == 0) {
         sendChatMessage("Please specify a module!");
      } else {
         Module var2 = ModuleManager.getModuleByName(var1[0]);
         if (var2 == null) {
            sendChatMessage(String.valueOf((new StringBuilder()).append("Unknown module '").append(var1[0]).append("'!")));
         } else {
            String var3 = var1.length == 1 ? var2.getOriginalName() : var1[1];
            if (!var3.matches("[a-zA-Z]+")) {
               sendChatMessage("Name must be alphabetic!");
            } else {
               sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var2.getName()).append("&r renamed to &b").append(var3)));
               var2.setName(var3);
            }
         }
      }
   }
}
