package net.minecraft.command;

import net.minecraft.command.CommandException;

public class PlayerNotFoundException extends CommandException {
   public PlayerNotFoundException() {
      this("commands.generic.player.notFound", new Object[0]);
   }

   public PlayerNotFoundException(String var1, Object... var2) {
      super(var1, var2);
   }
}
