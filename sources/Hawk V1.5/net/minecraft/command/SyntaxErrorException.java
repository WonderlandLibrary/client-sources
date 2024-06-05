package net.minecraft.command;

public class SyntaxErrorException extends CommandException {
   private static final String __OBFID = "CL_00001189";

   public SyntaxErrorException(String var1, Object... var2) {
      super(var1, var2);
   }

   public SyntaxErrorException() {
      this("commands.generic.snytax");
   }
}
