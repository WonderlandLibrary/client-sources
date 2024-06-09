package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$1
{
    AccessorInfo$AccessorType$1(final String s, final int n, final Set set) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
        return new AccessorGeneratorFieldGetter(accessorInfo);
    }
}