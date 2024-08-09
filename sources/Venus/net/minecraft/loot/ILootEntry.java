/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;

@FunctionalInterface
interface ILootEntry {
    public static final ILootEntry field_216139_a = ILootEntry::lambda$static$0;
    public static final ILootEntry field_216140_b = ILootEntry::lambda$static$1;

    public boolean expand(LootContext var1, Consumer<ILootGenerator> var2);

    default public ILootEntry sequence(ILootEntry iLootEntry) {
        Objects.requireNonNull(iLootEntry);
        return (arg_0, arg_1) -> this.lambda$sequence$2(iLootEntry, arg_0, arg_1);
    }

    default public ILootEntry alternate(ILootEntry iLootEntry) {
        Objects.requireNonNull(iLootEntry);
        return (arg_0, arg_1) -> this.lambda$alternate$3(iLootEntry, arg_0, arg_1);
    }

    private boolean lambda$alternate$3(ILootEntry iLootEntry, LootContext lootContext, Consumer consumer) {
        return this.expand(lootContext, consumer) || iLootEntry.expand(lootContext, consumer);
    }

    private boolean lambda$sequence$2(ILootEntry iLootEntry, LootContext lootContext, Consumer consumer) {
        return this.expand(lootContext, consumer) && iLootEntry.expand(lootContext, consumer);
    }

    private static boolean lambda$static$1(LootContext lootContext, Consumer consumer) {
        return false;
    }

    private static boolean lambda$static$0(LootContext lootContext, Consumer consumer) {
        return true;
    }
}

