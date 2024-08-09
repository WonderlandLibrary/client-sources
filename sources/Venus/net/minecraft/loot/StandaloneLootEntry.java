/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;

public abstract class StandaloneLootEntry
extends LootEntry {
    protected final int weight;
    protected final int quality;
    protected final ILootFunction[] functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> combinedFunctions;
    private final ILootGenerator generator = new Generator(this){
        final StandaloneLootEntry this$0;
        {
            this.this$0 = standaloneLootEntry;
            super(standaloneLootEntry);
        }

        @Override
        public void func_216188_a(Consumer<ItemStack> consumer, LootContext lootContext) {
            this.this$0.func_216154_a(ILootFunction.func_215858_a(this.this$0.combinedFunctions, consumer, lootContext), lootContext);
        }
    };

    protected StandaloneLootEntry(int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(iLootConditionArray);
        this.weight = n;
        this.quality = n2;
        this.functions = iLootFunctionArray;
        this.combinedFunctions = LootFunctionManager.combine(iLootFunctionArray);
    }

    @Override
    public void func_225579_a_(ValidationTracker validationTracker) {
        super.func_225579_a_(validationTracker);
        for (int i = 0; i < this.functions.length; ++i) {
            this.functions[i].func_225580_a_(validationTracker.func_227534_b_(".functions[" + i + "]"));
        }
    }

    protected abstract void func_216154_a(Consumer<ItemStack> var1, LootContext var2);

    @Override
    public boolean expand(LootContext lootContext, Consumer<ILootGenerator> consumer) {
        if (this.test(lootContext)) {
            consumer.accept(this.generator);
            return false;
        }
        return true;
    }

    public static Builder<?> builder(ILootEntryBuilder iLootEntryBuilder) {
        return new BuilderImpl(iLootEntryBuilder);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class BuilderImpl
    extends Builder<BuilderImpl> {
        private final ILootEntryBuilder builder;

        public BuilderImpl(ILootEntryBuilder iLootEntryBuilder) {
            this.builder = iLootEntryBuilder;
        }

        @Override
        protected BuilderImpl func_212845_d_() {
            return this;
        }

        @Override
        public LootEntry build() {
            return this.builder.build(this.weight, this.quality, this.func_216079_f(), this.getFunctions());
        }

        @Override
        protected LootEntry.Builder func_212845_d_() {
            return this.func_212845_d_();
        }
    }

    @FunctionalInterface
    public static interface ILootEntryBuilder {
        public StandaloneLootEntry build(int var1, int var2, ILootCondition[] var3, ILootFunction[] var4);
    }

    public static abstract class Serializer<T extends StandaloneLootEntry>
    extends LootEntry.Serializer<T> {
        @Override
        public void doSerialize(JsonObject jsonObject, T t, JsonSerializationContext jsonSerializationContext) {
            if (((StandaloneLootEntry)t).weight != 1) {
                jsonObject.addProperty("weight", ((StandaloneLootEntry)t).weight);
            }
            if (((StandaloneLootEntry)t).quality != 0) {
                jsonObject.addProperty("quality", ((StandaloneLootEntry)t).quality);
            }
            if (!ArrayUtils.isEmpty(((StandaloneLootEntry)t).functions)) {
                jsonObject.add("functions", jsonSerializationContext.serialize(((StandaloneLootEntry)t).functions));
            }
        }

        @Override
        public final T deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            int n = JSONUtils.getInt(jsonObject, "weight", 1);
            int n2 = JSONUtils.getInt(jsonObject, "quality", 0);
            ILootFunction[] iLootFunctionArray = JSONUtils.deserializeClass(jsonObject, "functions", new ILootFunction[0], jsonDeserializationContext, ILootFunction[].class);
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        protected abstract T deserialize(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, ILootCondition[] var5, ILootFunction[] var6);

        @Override
        public LootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (T)((StandaloneLootEntry)lootEntry), jsonSerializationContext);
        }
    }

    public abstract class Generator
    implements ILootGenerator {
        final StandaloneLootEntry this$0;

        protected Generator(StandaloneLootEntry standaloneLootEntry) {
            this.this$0 = standaloneLootEntry;
        }

        @Override
        public int getEffectiveWeight(float f) {
            return Math.max(MathHelper.floor((float)this.this$0.weight + (float)this.this$0.quality * f), 0);
        }
    }

    public static abstract class Builder<T extends Builder<T>>
    extends LootEntry.Builder<T>
    implements ILootFunctionConsumer<T> {
        protected int weight = 1;
        protected int quality = 0;
        private final List<ILootFunction> functions = Lists.newArrayList();

        @Override
        public T acceptFunction(ILootFunction.IBuilder iBuilder) {
            this.functions.add(iBuilder.build());
            return (T)((Builder)this.func_212845_d_());
        }

        protected ILootFunction[] getFunctions() {
            return this.functions.toArray(new ILootFunction[0]);
        }

        public T weight(int n) {
            this.weight = n;
            return (T)((Builder)this.func_212845_d_());
        }

        public T quality(int n) {
            this.quality = n;
            return (T)((Builder)this.func_212845_d_());
        }

        @Override
        public Object acceptFunction(ILootFunction.IBuilder iBuilder) {
            return this.acceptFunction(iBuilder);
        }
    }
}

