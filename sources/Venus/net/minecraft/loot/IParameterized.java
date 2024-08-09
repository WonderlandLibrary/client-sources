/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.ValidationTracker;

public interface IParameterized {
    default public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    default public void func_225580_a_(ValidationTracker validationTracker) {
        validationTracker.func_227528_a_(this);
    }
}

