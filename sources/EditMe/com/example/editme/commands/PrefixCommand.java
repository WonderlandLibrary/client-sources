package com.example.editme.commands;

import com.example.editme.util.command.syntax.ChunkBuilder;

public class PrefixCommand extends Command {
   public void call(String[] var1) {
      if (var1.length == 0) {
         Command.sendChatMessage("Please specify a new prefix!");
      } else {
         Command.commandPrefix.setValue(var1[0]);
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Prefix set to &b").append((String)Command.commandPrefix.getValue())));
      }
   }

   public PrefixCommand() {
      super("prefix", (new ChunkBuilder()).append("character").build());
   }
}
