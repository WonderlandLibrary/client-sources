package net.minecraft.command;

public class EntityNotFoundException extends CommandException {
   private static final String __OBFID = "CL_00002335";

   public EntityNotFoundException() {
      this("commands.generic.entity.notFound");
   }

   public EntityNotFoundException(String var1, Object... var2) {
      super(var1, var2);
   }
}
