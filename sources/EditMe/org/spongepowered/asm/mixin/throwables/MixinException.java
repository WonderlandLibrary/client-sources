package org.spongepowered.asm.mixin.throwables;

public class MixinException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public MixinException() {
   }

   public MixinException(String var1) {
      super(var1);
   }

   public MixinException(Throwable var1) {
      super(var1);
   }

   public MixinException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
