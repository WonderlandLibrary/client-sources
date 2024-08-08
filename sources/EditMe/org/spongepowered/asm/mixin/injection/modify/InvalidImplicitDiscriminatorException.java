package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidImplicitDiscriminatorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public InvalidImplicitDiscriminatorException(String var1) {
      super(var1);
   }

   public InvalidImplicitDiscriminatorException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
