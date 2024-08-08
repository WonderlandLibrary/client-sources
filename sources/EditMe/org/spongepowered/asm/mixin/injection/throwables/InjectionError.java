package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error {
   private static final long serialVersionUID = 1L;

   public InjectionError() {
   }

   public InjectionError(String var1) {
      super(var1);
   }

   public InjectionError(Throwable var1) {
      super(var1);
   }

   public InjectionError(String var1, Throwable var2) {
      super(var1, var2);
   }
}
