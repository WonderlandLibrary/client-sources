/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class LootingEnchantBonus
extends LootFunction {
    private final RandomValueRange count;
    private final int limit;

    public LootingEnchantBonus(LootCondition[] p_i47145_1_, RandomValueRange p_i47145_2_, int p_i47145_3_) {
        super(p_i47145_1_);
        this.count = p_i47145_2_;
        this.limit = p_i47145_3_;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        Entity entity = context.getKiller();
        if (entity instanceof EntityLivingBase) {
            int i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
            if (i == 0) {
                return stack;
            }
            float f = (float)i * this.count.generateFloat(rand);
            stack.func_190917_f(Math.round(f));
            if (this.limit != 0 && stack.func_190916_E() > this.limit) {
                stack.func_190920_e(this.limit);
            }
        }
        return stack;
    }

    public static class Serializer
    extends LootFunction.Serializer<LootingEnchantBonus> {
        protected Serializer() {
            super(new ResourceLocation("looting_enchant"), LootingEnchantBonus.class);
        }

        @Override
        public void serialize(JsonObject object, LootingEnchantBonus functionClazz, JsonSerializationContext serializationContext) {
            object.add("count", serializationContext.serialize(functionClazz.count));
            if (functionClazz.limit > 0) {
                object.add("limit", serializationContext.serialize(functionClazz.limit));
            }
        }

        @Override
        public LootingEnchantBonus deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            int i = JsonUtils.getInt(object, "limit", 0);
            return new LootingEnchantBonus(conditionsIn, JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class), i);
        }
    }
}

