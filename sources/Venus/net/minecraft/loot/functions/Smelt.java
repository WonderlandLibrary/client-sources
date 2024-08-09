/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Smelt
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();

    private Smelt(ILootCondition[] iLootConditionArray) {
        super(iLootConditionArray);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.FURNACE_SMELT;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        ItemStack itemStack2;
        if (itemStack.isEmpty()) {
            return itemStack;
        }
        Optional<FurnaceRecipe> optional = lootContext.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(itemStack), lootContext.getWorld());
        if (optional.isPresent() && !(itemStack2 = optional.get().getRecipeOutput()).isEmpty()) {
            ItemStack itemStack3 = itemStack2.copy();
            itemStack3.setCount(itemStack.getCount());
            return itemStack3;
        }
        LOGGER.warn("Couldn't smelt {} because there is no smelting recipe", (Object)itemStack);
        return itemStack;
    }

    public static LootFunction.Builder<?> func_215953_b() {
        return Smelt.builder(Smelt::new);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<Smelt> {
        @Override
        public Smelt deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return new Smelt(iLootConditionArray);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }
    }
}

