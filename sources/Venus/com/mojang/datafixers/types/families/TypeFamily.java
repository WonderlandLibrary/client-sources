/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.families;

import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.types.Type;
import java.util.function.IntFunction;

public interface TypeFamily {
    public Type<?> apply(int var1);

    public static <A, B> FamilyOptic<A, B> familyOptic(IntFunction<OpticParts<A, B>> intFunction) {
        return intFunction::apply;
    }
}

