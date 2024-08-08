package org.spongepowered.asm.mixin.throwables;

public class MixinPrepareError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinPrepareError(String var1) {
      super(var1);
   }

   public MixinPrepareError(Throwable var1) {
      super(var1);
   }

   public MixinPrepareError(String var1, Throwable var2) {
      super(var1, var2);
   }
}
