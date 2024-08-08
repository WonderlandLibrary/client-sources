package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InvalidInterfaceMixinException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;

   public InvalidInterfaceMixinException(IMixinInfo var1, String var2) {
      super(var1, var2);
   }

   public InvalidInterfaceMixinException(IMixinContext var1, String var2) {
      super(var1, var2);
   }

   public InvalidInterfaceMixinException(IMixinInfo var1, Throwable var2) {
      super(var1, var2);
   }

   public InvalidInterfaceMixinException(IMixinContext var1, Throwable var2) {
      super(var1, var2);
   }

   public InvalidInterfaceMixinException(IMixinInfo var1, String var2, Throwable var3) {
      super(var1, var2, var3);
   }

   public InvalidInterfaceMixinException(IMixinContext var1, String var2, Throwable var3) {
      super(var1, var2, var3);
   }
}
