/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootType;
import net.minecraft.loot.conditions.ILootCondition;

public class LootConditionType
extends LootType<ILootCondition> {
    public LootConditionType(ILootSerializer<? extends ILootCondition> iLootSerializer) {
        super(iLootSerializer);
    }
}

