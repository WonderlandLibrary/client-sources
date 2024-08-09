/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ILootConditionConsumer;
import net.minecraft.loot.ILootEntry;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootEntry
implements ILootEntry {
    protected final ILootCondition[] conditions;
    private final Predicate<LootContext> field_216143_c;

    protected LootEntry(ILootCondition[] iLootConditionArray) {
        this.conditions = iLootConditionArray;
        this.field_216143_c = LootConditionManager.and(iLootConditionArray);
    }

    public void func_225579_a_(ValidationTracker validationTracker) {
        for (int i = 0; i < this.conditions.length; ++i) {
            this.conditions[i].func_225580_a_(validationTracker.func_227534_b_(".condition[" + i + "]"));
        }
    }

    protected final boolean test(LootContext lootContext) {
        return this.field_216143_c.test(lootContext);
    }

    public abstract LootPoolEntryType func_230420_a_();

    public static abstract class Serializer<T extends LootEntry>
    implements ILootSerializer<T> {
        @Override
        public final void serialize(JsonObject jsonObject, T t, JsonSerializationContext jsonSerializationContext) {
            if (!ArrayUtils.isEmpty(((LootEntry)t).conditions)) {
                jsonObject.add("conditions", jsonSerializationContext.serialize(((LootEntry)t).conditions));
            }
            this.doSerialize(jsonObject, t, jsonSerializationContext);
        }

        @Override
        public final T deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ILootCondition[] iLootConditionArray = JSONUtils.deserializeClass(jsonObject, "conditions", new ILootCondition[0], jsonDeserializationContext, ILootCondition[].class);
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        public abstract void doSerialize(JsonObject var1, T var2, JsonSerializationContext var3);

        public abstract T deserialize(JsonObject var1, JsonDeserializationContext var2, ILootCondition[] var3);

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (T)((LootEntry)object), jsonSerializationContext);
        }
    }

    public static abstract class Builder<T extends Builder<T>>
    implements ILootConditionConsumer<T> {
        private final List<ILootCondition> field_216082_a = Lists.newArrayList();

        protected abstract T func_212845_d_();

        @Override
        public T acceptCondition(ILootCondition.IBuilder iBuilder) {
            this.field_216082_a.add(iBuilder.build());
            return this.func_212845_d_();
        }

        @Override
        public final T cast() {
            return this.func_212845_d_();
        }

        protected ILootCondition[] func_216079_f() {
            return this.field_216082_a.toArray(new ILootCondition[0]);
        }

        public AlternativesLootEntry.Builder alternatively(Builder<?> builder) {
            return new AlternativesLootEntry.Builder(this, builder);
        }

        public abstract LootEntry build();

        @Override
        public Object cast() {
            return this.cast();
        }

        @Override
        public Object acceptCondition(ILootCondition.IBuilder iBuilder) {
            return this.acceptCondition(iBuilder);
        }
    }
}

