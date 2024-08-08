package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InvalidInjectionPointException extends InvalidInjectionException {
   private static final long serialVersionUID = 2L;

   public InvalidInjectionPointException(IMixinContext var1, String var2, Object... var3) {
      super(var1, String.format(var2, var3));
   }

   public InvalidInjectionPointException(InjectionInfo var1, String var2, Object... var3) {
      super(var1, String.format(var2, var3));
   }

   public InvalidInjectionPointException(IMixinContext var1, Throwable var2, String var3, Object... var4) {
      super(var1, String.format(var3, var4), var2);
   }

   public InvalidInjectionPointException(InjectionInfo var1, Throwable var2, String var3, Object... var4) {
      super(var1, String.format(var3, var4), var2);
   }
}
