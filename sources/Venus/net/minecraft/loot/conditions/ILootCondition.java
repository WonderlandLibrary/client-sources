/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import java.util.function.Predicate;
import net.minecraft.loot.IParameterized;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.Inverted;

public interface ILootCondition
extends IParameterized,
Predicate<LootContext> {
    public LootConditionType func_230419_b_();

    @FunctionalInterface
    public static interface IBuilder {
        public ILootCondition build();

        default public IBuilder inverted() {
            return Inverted.builder(this);
        }

        default public Alternative.Builder alternative(IBuilder iBuilder) {
            return Alternative.builder(this, iBuilder);
        }
    }
}

