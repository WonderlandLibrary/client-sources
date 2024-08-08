package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidAccessorException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;
   private final AccessorInfo info;

   public InvalidAccessorException(IMixinContext var1, String var2) {
      super(var1, var2);
      this.info = null;
   }

   public InvalidAccessorException(AccessorInfo var1, String var2) {
      super(var1.getContext(), var2);
      this.info = var1;
   }

   public InvalidAccessorException(IMixinContext var1, Throwable var2) {
      super(var1, var2);
      this.info = null;
   }

   public InvalidAccessorException(AccessorInfo var1, Throwable var2) {
      super(var1.getContext(), var2);
      this.info = var1;
   }

   public InvalidAccessorException(IMixinContext var1, String var2, Throwable var3) {
      super(var1, var2, var3);
      this.info = null;
   }

   public InvalidAccessorException(AccessorInfo var1, String var2, Throwable var3) {
      super(var1.getContext(), var2, var3);
      this.info = var1;
   }

   public AccessorInfo getAccessorInfo() {
      return this.info;
   }
}
