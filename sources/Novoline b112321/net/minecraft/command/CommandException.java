package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] errorObjects;

   public CommandException(String var1, Object... var2) {
      super(var1);
      this.errorObjects = var2;
   }

   public CommandException(String var1, Throwable var2, Object... var3) {
      super(var1, var2);
      this.errorObjects = var3;
   }

   public Object[] getErrorObjects() {
      return this.errorObjects;
   }
}
