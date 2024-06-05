package net.minecraft.command;

public class CommandNotFoundException extends CommandException {
   private static final String __OBFID = "CL_00001191";

   public CommandNotFoundException() {
      this("commands.generic.notFound");
   }

   public CommandNotFoundException(String var1, Object... var2) {
      super(var1, var2);
   }
}
