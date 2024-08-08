package com.example.editme.commands;

import com.example.editme.EditmeMod;
import com.example.editme.util.command.syntax.SyntaxChunk;
import java.util.Comparator;

public class CommandsCommand extends Command {
   public CommandsCommand() {
      super("commands", SyntaxChunk.EMPTY);
   }

   private static String lambda$call$0(Command var0) {
      return var0.getLabel();
   }

   private static void lambda$call$1(Command var0) {
      Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&7").append(Command.getCommandPrefix()).append(var0.getLabel()).append("&r ~ &8").append(var0.getDescription())));
   }

   public void call(String[] var1) {
      EditmeMod.getInstance().getCommandManager().getCommands().stream().sorted(Comparator.comparing(CommandsCommand::lambda$call$0)).forEach(CommandsCommand::lambda$call$1);
   }
}
