/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Smelt
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();

    public Smelt(LootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (stack.func_190926_b()) {
            return stack;
        }
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stack);
        if (itemstack.func_190926_b()) {
            LOGGER.warn("Couldn't smelt {} because there is no smelting recipe", (Object)stack);
            return stack;
        }
        ItemStack itemstack1 = itemstack.copy();
        itemstack1.func_190920_e(stack.func_190916_E());
        return itemstack1;
    }

    public static class Serializer
    extends LootFunction.Serializer<Smelt> {
        protected Serializer() {
            super(new ResourceLocation("furnace_smelt"), Smelt.class);
        }

        @Override
        public void serialize(JsonObject object, Smelt functionClazz, JsonSerializationContext serializationContext) {
        }

        @Override
        public Smelt deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            return new Smelt(conditionsIn);
        }
    }
}

