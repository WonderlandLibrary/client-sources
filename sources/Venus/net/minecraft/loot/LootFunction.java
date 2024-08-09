/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootConditionConsumer;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootFunction
implements ILootFunction {
    protected final ILootCondition[] conditions;
    private final Predicate<LootContext> combinedConditions;

    protected LootFunction(ILootCondition[] iLootConditionArray) {
        this.conditions = iLootConditionArray;
        this.combinedConditions = LootConditionManager.and(iLootConditionArray);
    }

    @Override
    public final ItemStack apply(ItemStack itemStack, LootContext lootContext) {
        return this.combinedConditions.test(lootContext) ? this.doApply(itemStack, lootContext) : itemStack;
    }

    protected abstract ItemStack doApply(ItemStack var1, LootContext var2);

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        ILootFunction.super.func_225580_a_(validationTracker);
        for (int i = 0; i < this.conditions.length; ++i) {
            this.conditions[i].func_225580_a_(validationTracker.func_227534_b_(".conditions[" + i + "]"));
        }
    }

    protected static Builder<?> builder(Function<ILootCondition[], ILootFunction> function) {
        return new SimpleBuilder(function);
    }

    @Override
    public Object apply(Object object, Object object2) {
        return this.apply((ItemStack)object, (LootContext)object2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class SimpleBuilder
    extends Builder<SimpleBuilder> {
        private final Function<ILootCondition[], ILootFunction> function;

        public SimpleBuilder(Function<ILootCondition[], ILootFunction> function) {
            this.function = function;
        }

        @Override
        protected SimpleBuilder doCast() {
            return this;
        }

        @Override
        public ILootFunction build() {
            return this.function.apply(this.getConditions());
        }

        @Override
        protected Builder doCast() {
            return this.doCast();
        }
    }

    public static abstract class Serializer<T extends LootFunction>
    implements ILootSerializer<T> {
        @Override
        public void serialize(JsonObject jsonObject, T t, JsonSerializationContext jsonSerializationContext) {
            if (!ArrayUtils.isEmpty(((LootFunction)t).conditions)) {
                jsonObject.add("conditions", jsonSerializationContext.serialize(((LootFunction)t).conditions));
            }
        }

        @Override
        public final T deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ILootCondition[] iLootConditionArray = JSONUtils.deserializeClass(jsonObject, "conditions", new ILootCondition[0], jsonDeserializationContext, ILootCondition[].class);
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        public abstract T deserialize(JsonObject var1, JsonDeserializationContext var2, ILootCondition[] var3);

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (T)((LootFunction)object), jsonSerializationContext);
        }
    }

    public static abstract class Builder<T extends Builder<T>>
    implements ILootFunction.IBuilder,
    ILootConditionConsumer<T> {
        private final List<ILootCondition> conditions = Lists.newArrayList();

        @Override
        public T acceptCondition(ILootCondition.IBuilder iBuilder) {
            this.conditions.add(iBuilder.build());
            return this.doCast();
        }

        @Override
        public final T cast() {
            return this.doCast();
        }

        protected abstract T doCast();

        protected ILootCondition[] getConditions() {
            return this.conditions.toArray(new ILootCondition[0]);
        }

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

