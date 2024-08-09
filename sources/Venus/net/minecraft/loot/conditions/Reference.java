/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference
implements ILootCondition {
    private static final Logger field_227561_a_ = LogManager.getLogger();
    private final ResourceLocation field_227562_b_;

    private Reference(ResourceLocation resourceLocation) {
        this.field_227562_b_ = resourceLocation;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.REFERENCE;
    }

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        if (validationTracker.func_227536_b_(this.field_227562_b_)) {
            validationTracker.addProblem("Condition " + this.field_227562_b_ + " is recursively called");
        } else {
            ILootCondition.super.func_225580_a_(validationTracker);
            ILootCondition iLootCondition = validationTracker.func_227541_d_(this.field_227562_b_);
            if (iLootCondition == null) {
                validationTracker.addProblem("Unknown condition table called " + this.field_227562_b_);
            } else {
                iLootCondition.func_225580_a_(validationTracker.func_227531_a_(".{" + this.field_227562_b_ + "}", this.field_227562_b_));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean test(LootContext lootContext) {
        ILootCondition iLootCondition = lootContext.getLootCondition(this.field_227562_b_);
        if (lootContext.addCondition(iLootCondition)) {
            boolean bl;
            try {
                bl = iLootCondition.test(lootContext);
            } finally {
                lootContext.removeCondition(iLootCondition);
            }
            return bl;
        }
        field_227561_a_.warn("Detected infinite loop in loot tables");
        return true;
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<Reference> {
        @Override
        public void serialize(JsonObject jsonObject, Reference reference, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("name", reference.field_227562_b_.toString());
        }

        @Override
        public Reference deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "name"));
            return new Reference(resourceLocation);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (Reference)object, jsonSerializationContext);
        }
    }
}

