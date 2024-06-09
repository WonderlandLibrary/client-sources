package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.*;

public interface ISliceContext extends IInjectionPointContext
{
    MethodSlice getSlice(final String p0);
}
