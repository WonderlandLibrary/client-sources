package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinTargetAlreadyLoadedException extends InvalidMixinException {
   private static final long serialVersionUID = 1L;
   private final String target;

   public MixinTargetAlreadyLoadedException(IMixinInfo var1, String var2, String var3) {
      super(var1, var2);
      this.target = var3;
   }

   public MixinTargetAlreadyLoadedException(IMixinInfo var1, String var2, String var3, Throwable var4) {
      super(var1, var2, var4);
      this.target = var3;
   }

   public String getTarget() {
      return this.target;
   }
}
