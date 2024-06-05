package net.minecraft.command;

import net.minecraft.command.CommandException;

public class SyntaxErrorException extends CommandException {
   public SyntaxErrorException() {
      this("commands.generic.snytax", new Object[0]);
   }

   public SyntaxErrorException(String var1, Object... var2) {
      super(var1, var2);
   }
}
