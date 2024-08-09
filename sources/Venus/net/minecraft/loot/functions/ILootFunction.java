/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.IParameterized;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunctionType;

public interface ILootFunction
extends IParameterized,
BiFunction<ItemStack, LootContext, ItemStack> {
    public LootFunctionType getFunctionType();

    public static Consumer<ItemStack> func_215858_a(BiFunction<ItemStack, LootContext, ItemStack> biFunction, Consumer<ItemStack> consumer, LootContext lootContext) {
        return arg_0 -> ILootFunction.lambda$func_215858_a$0(consumer, biFunction, lootContext, arg_0);
    }

    private static void lambda$func_215858_a$0(Consumer consumer, BiFunction biFunction, LootContext lootContext, ItemStack itemStack) {
        consumer.accept((ItemStack)biFunction.apply(itemStack, lootContext));
    }

    public static interface IBuilder {
        public ILootFunction build();
    }
}

