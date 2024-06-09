package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.*;

public class MixinClassWriter extends ClassWriter
{
    public MixinClassWriter(final int n) {
        super(n);
    }
    
    public MixinClassWriter(final ClassReader classReader, final int n) {
        super(classReader, n);
    }
    
    @Override
    protected String getCommonSuperClass(final String s, final String s2) {
        return ClassInfo.getCommonSuperClass(s, s2).getName();
    }
}
