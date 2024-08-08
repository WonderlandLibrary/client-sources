package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.IInjectionPointContext;

public interface ISliceContext extends IInjectionPointContext {
   MethodSlice getSlice(String var1);
}
