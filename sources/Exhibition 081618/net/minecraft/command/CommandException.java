package net.minecraft.command;

public class CommandException extends Exception {
   private static final long serialVersionUID = 1L;
   private final Object[] errorObjects;

   public CommandException(String p_i1359_1_, Object... p_i1359_2_) {
      super(p_i1359_1_);
      this.errorObjects = p_i1359_2_;
   }

   public Object[] getErrorOjbects() {
      return this.errorObjects;
   }
}
