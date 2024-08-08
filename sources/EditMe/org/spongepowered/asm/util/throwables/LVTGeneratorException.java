package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class LVTGeneratorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public LVTGeneratorException(String var1) {
      super(var1);
   }

   public LVTGeneratorException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
