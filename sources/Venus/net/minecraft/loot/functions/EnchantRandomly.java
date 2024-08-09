/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomly
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Enchantment> enchantments;

    private EnchantRandomly(ILootCondition[] iLootConditionArray, Collection<Enchantment> collection) {
        super(iLootConditionArray);
        this.enchantments = ImmutableList.copyOf(collection);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.ENCHANT_RANDOMLY;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Enchantment enchantment;
        Random random2 = lootContext.getRandom();
        if (this.enchantments.isEmpty()) {
            boolean bl = itemStack.getItem() == Items.BOOK;
            List list = Registry.ENCHANTMENT.stream().filter(Enchantment::canGenerateInLoot).filter(arg_0 -> EnchantRandomly.lambda$doApply$0(bl, itemStack, arg_0)).collect(Collectors.toList());
            if (list.isEmpty()) {
                LOGGER.warn("Couldn't find a compatible enchantment for {}", (Object)itemStack);
                return itemStack;
            }
            enchantment = (Enchantment)list.get(random2.nextInt(list.size()));
        } else {
            enchantment = this.enchantments.get(random2.nextInt(this.enchantments.size()));
        }
        return EnchantRandomly.func_237420_a_(itemStack, enchantment, random2);
    }

    private static ItemStack func_237420_a_(ItemStack itemStack, Enchantment enchantment, Random random2) {
        int n = MathHelper.nextInt(random2, enchantment.getMinLevel(), enchantment.getMaxLevel());
        if (itemStack.getItem() == Items.BOOK) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(itemStack, new EnchantmentData(enchantment, n));
        } else {
            itemStack.addEnchantment(enchantment, n);
        }
        return itemStack;
    }

    public static LootFunction.Builder<?> func_215900_c() {
        return EnchantRandomly.builder(EnchantRandomly::lambda$func_215900_c$1);
    }

    private static ILootFunction lambda$func_215900_c$1(ILootCondition[] iLootConditionArray) {
        return new EnchantRandomly(iLootConditionArray, ImmutableList.of());
    }

    private static boolean lambda$doApply$0(boolean bl, ItemStack itemStack, Enchantment enchantment) {
        return bl || enchantment.canApply(itemStack);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<EnchantRandomly> {
        @Override
        public void serialize(JsonObject jsonObject, EnchantRandomly enchantRandomly, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, enchantRandomly, jsonSerializationContext);
            if (!enchantRandomly.enchantments.isEmpty()) {
                JsonArray jsonArray = new JsonArray();
                for (Enchantment enchantment : enchantRandomly.enchantments) {
                    ResourceLocation resourceLocation = Registry.ENCHANTMENT.getKey(enchantment);
                    if (resourceLocation == null) {
                        throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchantment);
                    }
                    jsonArray.add(new JsonPrimitive(resourceLocation.toString()));
                }
                jsonObject.add("enchantments", jsonArray);
            }
        }

        @Override
        public EnchantRandomly deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            ArrayList<Enchantment> arrayList = Lists.newArrayList();
            if (jsonObject.has("enchantments")) {
                for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "enchantments")) {
                    String string = JSONUtils.getString(jsonElement, "enchantment");
                    Enchantment enchantment = Registry.ENCHANTMENT.getOptional(new ResourceLocation(string)).orElseThrow(() -> Serializer.lambda$deserialize$0(string));
                    arrayList.add(enchantment);
                }
            }
            return new EnchantRandomly(iLootConditionArray, arrayList);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EnchantRandomly)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EnchantRandomly)object, jsonSerializationContext);
        }

        private static JsonSyntaxException lambda$deserialize$0(String string) {
            return new JsonSyntaxException("Unknown enchantment '" + string + "'");
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final Set<Enchantment> field_237423_a_ = Sets.newHashSet();

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder func_237424_a_(Enchantment enchantment) {
            this.field_237423_a_.add(enchantment);
            return this;
        }

        @Override
        public ILootFunction build() {
            return new EnchantRandomly(this.getConditions(), this.field_237423_a_);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }
}

