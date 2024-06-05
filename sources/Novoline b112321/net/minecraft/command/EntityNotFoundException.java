package net.minecraft.command;

import net.minecraft.command.CommandException;

public class EntityNotFoundException extends CommandException {
   public EntityNotFoundException() {
      this("commands.generic.entity.notFound", new Object[0]);
   }

   public EntityNotFoundException(String var1, Object... var2) {
      super(var1, var2);
   }
}
