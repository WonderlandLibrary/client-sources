/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootType;
import net.minecraft.loot.functions.ILootFunction;

public class LootFunctionType
extends LootType<ILootFunction> {
    public LootFunctionType(ILootSerializer<? extends ILootFunction> iLootSerializer) {
        super(iLootSerializer);
    }
}

