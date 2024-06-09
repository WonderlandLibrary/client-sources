package net.minecraft.command;

public class SyntaxErrorException extends CommandException {
   private static final long serialVersionUID = 1L;

   public SyntaxErrorException() {
      this("commands.generic.snytax");
   }

   public SyntaxErrorException(String p_i1361_1_, Object... p_i1361_2_) {
      super(p_i1361_1_, p_i1361_2_);
   }
}
