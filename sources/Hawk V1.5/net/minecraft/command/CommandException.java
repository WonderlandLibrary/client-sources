package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] errorObjects;
   private static final String __OBFID = "CL_00001187";

   public Object[] getErrorOjbects() {
      return this.errorObjects;
   }

   public CommandException(String var1, Object... var2) {
      super(var1);
      this.errorObjects = var2;
   }
}
