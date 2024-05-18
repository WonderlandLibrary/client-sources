package org.spongepowered.asm.mixin.transformer;

import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

enum SpecialMethod
{
    MERGE(true), 
    OVERWRITE(true, (Class<? extends Annotation>)Overwrite.class), 
    SHADOW(false, (Class<? extends Annotation>)Shadow.class), 
    ACCESSOR(false, (Class<? extends Annotation>)Accessor.class), 
    INVOKER(false, (Class<? extends Annotation>)Invoker.class);
    
    final boolean isOverwrite;
    final Class<? extends Annotation> annotation;
    final String description;
    private static final SpecialMethod[] $VALUES;
    
    public static SpecialMethod[] values() {
        return SpecialMethod.$VALUES.clone();
    }
    
    public static SpecialMethod valueOf(final String s) {
        return Enum.valueOf(SpecialMethod.class, s);
    }
    
    private SpecialMethod(final boolean isOverwrite, final Class<? extends Annotation> annotation) {
        this.isOverwrite = isOverwrite;
        this.annotation = annotation;
        this.description = "@" + Bytecode.getSimpleName(annotation);
    }
    
    private SpecialMethod(final boolean isOverwrite) {
        this.isOverwrite = isOverwrite;
        this.annotation = null;
        this.description = "overwrite";
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    static {
        $VALUES = new SpecialMethod[] { SpecialMethod.MERGE, SpecialMethod.OVERWRITE, SpecialMethod.SHADOW, SpecialMethod.ACCESSOR, SpecialMethod.INVOKER };
    }
}
