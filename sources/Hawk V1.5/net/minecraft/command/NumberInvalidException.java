package net.minecraft.command;

public class NumberInvalidException extends CommandException {
   private static final String __OBFID = "CL_00001188";

   public NumberInvalidException(String var1, Object... var2) {
      super(var1, var2);
   }

   public NumberInvalidException() {
      this("commands.generic.num.invalid");
   }
}
