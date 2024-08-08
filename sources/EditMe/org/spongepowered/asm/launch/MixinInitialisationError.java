package org.spongepowered.asm.launch;

public class MixinInitialisationError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinInitialisationError() {
   }

   public MixinInitialisationError(String var1) {
      super(var1);
   }

   public MixinInitialisationError(Throwable var1) {
      super(var1);
   }

   public MixinInitialisationError(String var1, Throwable var2) {
      super(var1, var2);
   }
}
