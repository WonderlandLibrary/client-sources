package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentCountException extends IllegalArgumentException {
   private static final long serialVersionUID = 1L;

   public ArgumentCountException(int var1, int var2, String var3) {
      super("Invalid number of arguments for setAll, received " + var1 + " but expected " + var2 + ": " + var3);
   }
}
