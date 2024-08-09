/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;

public class Inverted
implements ILootCondition {
    private final ILootCondition term;

    private Inverted(ILootCondition iLootCondition) {
        this.term = iLootCondition;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.INVERTED;
    }

    @Override
    public final boolean test(LootContext lootContext) {
        return !this.term.test(lootContext);
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return this.term.getRequiredParameters();
    }

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        ILootCondition.super.func_225580_a_(validationTracker);
        this.term.func_225580_a_(validationTracker);
    }

    public static ILootCondition.IBuilder builder(ILootCondition.IBuilder iBuilder) {
        Inverted inverted = new Inverted(iBuilder.build());
        return () -> Inverted.lambda$builder$0(inverted);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(Inverted inverted) {
        return inverted;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<Inverted> {
        @Override
        public void serialize(JsonObject jsonObject, Inverted inverted, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("term", jsonSerializationContext.serialize(inverted.term));
        }

        @Override
        public Inverted deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ILootCondition iLootCondition = JSONUtils.deserializeClass(jsonObject, "term", jsonDeserializationContext, ILootCondition.class);
            return new Inverted(iLootCondition);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (Inverted)object, jsonSerializationContext);
        }
    }
}

