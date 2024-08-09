/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootConditionConsumer;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.RandomRanges;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootPool {
    private final LootEntry[] lootEntries;
    private final ILootCondition[] conditions;
    private final Predicate<LootContext> combinedConditions;
    private final ILootFunction[] functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> combinedFunctions;
    private final IRandomRange rolls;
    private final RandomValueRange bonusRolls;

    private LootPool(LootEntry[] lootEntryArray, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray, IRandomRange iRandomRange, RandomValueRange randomValueRange) {
        this.lootEntries = lootEntryArray;
        this.conditions = iLootConditionArray;
        this.combinedConditions = LootConditionManager.and(iLootConditionArray);
        this.functions = iLootFunctionArray;
        this.combinedFunctions = LootFunctionManager.combine(iLootFunctionArray);
        this.rolls = iRandomRange;
        this.bonusRolls = randomValueRange;
    }

    private void generateRoll(Consumer<ItemStack> consumer, LootContext lootContext) {
        Random random2 = lootContext.getRandom();
        ArrayList<Object> arrayList = Lists.newArrayList();
        MutableInt mutableInt = new MutableInt();
        for (LootEntry object : this.lootEntries) {
            object.expand(lootContext, arg_0 -> LootPool.lambda$generateRoll$0(lootContext, arrayList, mutableInt, arg_0));
        }
        int n = arrayList.size();
        if (mutableInt.intValue() != 0 && n != 0) {
            if (n == 1) {
                ((ILootGenerator)arrayList.get(0)).func_216188_a(consumer, lootContext);
            } else {
                int n2 = random2.nextInt(mutableInt.intValue());
                for (ILootGenerator iLootGenerator : arrayList) {
                    if ((n2 -= iLootGenerator.getEffectiveWeight(lootContext.getLuck())) >= 0) continue;
                    iLootGenerator.func_216188_a(consumer, lootContext);
                    return;
                }
            }
        }
    }

    public void generate(Consumer<ItemStack> consumer, LootContext lootContext) {
        if (this.combinedConditions.test(lootContext)) {
            Consumer<ItemStack> consumer2 = ILootFunction.func_215858_a(this.combinedFunctions, consumer, lootContext);
            Random random2 = lootContext.getRandom();
            int n = this.rolls.generateInt(random2) + MathHelper.floor(this.bonusRolls.generateFloat(random2) * lootContext.getLuck());
            for (int i = 0; i < n; ++i) {
                this.generateRoll(consumer2, lootContext);
            }
        }
    }

    public void func_227505_a_(ValidationTracker validationTracker) {
        int n;
        for (n = 0; n < this.conditions.length; ++n) {
            this.conditions[n].func_225580_a_(validationTracker.func_227534_b_(".condition[" + n + "]"));
        }
        for (n = 0; n < this.functions.length; ++n) {
            this.functions[n].func_225580_a_(validationTracker.func_227534_b_(".functions[" + n + "]"));
        }
        for (n = 0; n < this.lootEntries.length; ++n) {
            this.lootEntries[n].func_225579_a_(validationTracker.func_227534_b_(".entries[" + n + "]"));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static void lambda$generateRoll$0(LootContext lootContext, List list, MutableInt mutableInt, ILootGenerator iLootGenerator) {
        int n = iLootGenerator.getEffectiveWeight(lootContext.getLuck());
        if (n > 0) {
            list.add(iLootGenerator);
            mutableInt.add(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements ILootFunctionConsumer<Builder>,
    ILootConditionConsumer<Builder> {
        private final List<LootEntry> entries = Lists.newArrayList();
        private final List<ILootCondition> conditions = Lists.newArrayList();
        private final List<ILootFunction> functions = Lists.newArrayList();
        private IRandomRange rolls = new RandomValueRange(1.0f);
        private RandomValueRange bonusRolls = new RandomValueRange(0.0f, 0.0f);

        public Builder rolls(IRandomRange iRandomRange) {
            this.rolls = iRandomRange;
            return this;
        }

        @Override
        public Builder cast() {
            return this;
        }

        public Builder addEntry(LootEntry.Builder<?> builder) {
            this.entries.add(builder.build());
            return this;
        }

        @Override
        public Builder acceptCondition(ILootCondition.IBuilder iBuilder) {
            this.conditions.add(iBuilder.build());
            return this;
        }

        @Override
        public Builder acceptFunction(ILootFunction.IBuilder iBuilder) {
            this.functions.add(iBuilder.build());
            return this;
        }

        public LootPool build() {
            if (this.rolls == null) {
                throw new IllegalArgumentException("Rolls not set");
            }
            return new LootPool(this.entries.toArray(new LootEntry[0]), this.conditions.toArray(new ILootCondition[0]), this.functions.toArray(new ILootFunction[0]), this.rolls, this.bonusRolls);
        }

        @Override
        public Object cast() {
            return this.cast();
        }

        @Override
        public Object acceptFunction(ILootFunction.IBuilder iBuilder) {
            return this.acceptFunction(iBuilder);
        }

        @Override
        public Object acceptCondition(ILootCondition.IBuilder iBuilder) {
            return this.acceptCondition(iBuilder);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<LootPool>,
    JsonSerializer<LootPool> {
        @Override
        public LootPool deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "loot pool");
            LootEntry[] lootEntryArray = JSONUtils.deserializeClass(jsonObject, "entries", jsonDeserializationContext, LootEntry[].class);
            ILootCondition[] iLootConditionArray = JSONUtils.deserializeClass(jsonObject, "conditions", new ILootCondition[0], jsonDeserializationContext, ILootCondition[].class);
            ILootFunction[] iLootFunctionArray = JSONUtils.deserializeClass(jsonObject, "functions", new ILootFunction[0], jsonDeserializationContext, ILootFunction[].class);
            IRandomRange iRandomRange = RandomRanges.deserialize(jsonObject.get("rolls"), jsonDeserializationContext);
            RandomValueRange randomValueRange = JSONUtils.deserializeClass(jsonObject, "bonus_rolls", new RandomValueRange(0.0f, 0.0f), jsonDeserializationContext, RandomValueRange.class);
            return new LootPool(lootEntryArray, iLootConditionArray, iLootFunctionArray, iRandomRange, randomValueRange);
        }

        @Override
        public JsonElement serialize(LootPool lootPool, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("rolls", RandomRanges.serialize(lootPool.rolls, jsonSerializationContext));
            jsonObject.add("entries", jsonSerializationContext.serialize(lootPool.lootEntries));
            if (lootPool.bonusRolls.getMin() != 0.0f && lootPool.bonusRolls.getMax() != 0.0f) {
                jsonObject.add("bonus_rolls", jsonSerializationContext.serialize(lootPool.bonusRolls));
            }
            if (!ArrayUtils.isEmpty(lootPool.conditions)) {
                jsonObject.add("conditions", jsonSerializationContext.serialize(lootPool.conditions));
            }
            if (!ArrayUtils.isEmpty(lootPool.functions)) {
                jsonObject.add("functions", jsonSerializationContext.serialize(lootPool.functions));
            }
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((LootPool)object, type, jsonSerializationContext);
        }
    }
}

