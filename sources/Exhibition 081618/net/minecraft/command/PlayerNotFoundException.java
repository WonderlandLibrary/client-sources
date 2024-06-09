package net.minecraft.command;

public class PlayerNotFoundException extends CommandException {
   private static final long serialVersionUID = 1L;

   public PlayerNotFoundException() {
      this("commands.generic.player.notFound");
   }

   public PlayerNotFoundException(String p_i1362_1_, Object... p_i1362_2_) {
      super(p_i1362_1_, p_i1362_2_);
   }
}
