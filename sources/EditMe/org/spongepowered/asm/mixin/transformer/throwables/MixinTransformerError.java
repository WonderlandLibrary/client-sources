package org.spongepowered.asm.mixin.transformer.throwables;

public class MixinTransformerError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinTransformerError(String var1) {
      super(var1);
   }

   public MixinTransformerError(Throwable var1) {
      super(var1);
   }

   public MixinTransformerError(String var1, Throwable var2) {
      super(var1, var2);
   }
}
