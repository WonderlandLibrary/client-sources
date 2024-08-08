package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidMemberDescriptorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public InvalidMemberDescriptorException(String var1) {
      super(var1);
   }

   public InvalidMemberDescriptorException(Throwable var1) {
      super(var1);
   }

   public InvalidMemberDescriptorException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
