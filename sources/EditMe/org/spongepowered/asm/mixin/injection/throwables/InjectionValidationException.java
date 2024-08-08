package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

public class InjectionValidationException extends Exception {
   private static final long serialVersionUID = 1L;
   private final InjectorGroupInfo group;

   public InjectionValidationException(InjectorGroupInfo var1, String var2) {
      super(var2);
      this.group = var1;
   }

   public InjectorGroupInfo getGroup() {
      return this.group;
   }
}
