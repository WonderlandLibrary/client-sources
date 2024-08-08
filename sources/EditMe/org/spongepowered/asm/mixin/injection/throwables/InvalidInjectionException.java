package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidInjectionException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;
   private final InjectionInfo info;

   public InvalidInjectionException(IMixinContext var1, String var2) {
      super(var1, var2);
      this.info = null;
   }

   public InvalidInjectionException(InjectionInfo var1, String var2) {
      super(var1.getContext(), var2);
      this.info = var1;
   }

   public InvalidInjectionException(IMixinContext var1, Throwable var2) {
      super(var1, var2);
      this.info = null;
   }

   public InvalidInjectionException(InjectionInfo var1, Throwable var2) {
      super(var1.getContext(), var2);
      this.info = var1;
   }

   public InvalidInjectionException(IMixinContext var1, String var2, Throwable var3) {
      super(var1, var2, var3);
      this.info = null;
   }

   public InvalidInjectionException(InjectionInfo var1, String var2, Throwable var3) {
      super(var1.getContext(), var2, var3);
      this.info = var1;
   }

   public InjectionInfo getInjectionInfo() {
      return this.info;
   }
}
