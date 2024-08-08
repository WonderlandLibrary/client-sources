package com.example.editme.commands;

import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.EnumParser;

public class BaritoneCommand extends Command {
   public BaritoneCommand() {
      super("baritone", (new ChunkBuilder()).append("command", true, new EnumParser(new String[]{"goto", "mine", "tunnel", "farm", "explore", "click", "build", "cancel", "pause", "resume", "help"})).build());
   }

   public void call(String[] var1) {
   }
}
