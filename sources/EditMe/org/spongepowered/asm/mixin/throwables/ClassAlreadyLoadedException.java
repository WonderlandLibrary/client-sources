package org.spongepowered.asm.mixin.throwables;

public class ClassAlreadyLoadedException extends MixinException {
   private static final long serialVersionUID = 1L;

   public ClassAlreadyLoadedException(String var1) {
      super(var1);
   }

   public ClassAlreadyLoadedException(Throwable var1) {
      super(var1);
   }

   public ClassAlreadyLoadedException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
