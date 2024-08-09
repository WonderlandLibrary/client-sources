/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;

public class Alternative
implements ILootCondition {
    private final ILootCondition[] conditions;
    private final Predicate<LootContext> field_215963_b;

    private Alternative(ILootCondition[] iLootConditionArray) {
        this.conditions = iLootConditionArray;
        this.field_215963_b = LootConditionManager.or(iLootConditionArray);
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.ALTERNATIVE;
    }

    @Override
    public final boolean test(LootContext lootContext) {
        return this.field_215963_b.test(lootContext);
    }

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        ILootCondition.super.func_225580_a_(validationTracker);
        for (int i = 0; i < this.conditions.length; ++i) {
            this.conditions[i].func_225580_a_(validationTracker.func_227534_b_(".term[" + i + "]"));
        }
    }

    public static Builder builder(ILootCondition.IBuilder ... iBuilderArray) {
        return new Builder(iBuilderArray);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Builder
    implements ILootCondition.IBuilder {
        private final List<ILootCondition> conditions = Lists.newArrayList();

        public Builder(ILootCondition.IBuilder ... iBuilderArray) {
            for (ILootCondition.IBuilder iBuilder : iBuilderArray) {
                this.conditions.add(iBuilder.build());
            }
        }

        @Override
        public Builder alternative(ILootCondition.IBuilder iBuilder) {
            this.conditions.add(iBuilder.build());
            return this;
        }

        @Override
        public ILootCondition build() {
            return new Alternative(this.conditions.toArray(new ILootCondition[0]));
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<Alternative> {
        @Override
        public void serialize(JsonObject jsonObject, Alternative alternative, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("terms", jsonSerializationContext.serialize(alternative.conditions));
        }

        @Override
        public Alternative deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ILootCondition[] iLootConditionArray = JSONUtils.deserializeClass(jsonObject, "terms", jsonDeserializationContext, ILootCondition[].class);
            return new Alternative(iLootConditionArray);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (Alternative)object, jsonSerializationContext);
        }
    }
}

