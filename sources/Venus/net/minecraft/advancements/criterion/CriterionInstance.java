/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public abstract class CriterionInstance
implements ICriterionInstance {
    private final ResourceLocation criterion;
    private final EntityPredicate.AndPredicate playerCondition;

    public CriterionInstance(ResourceLocation resourceLocation, EntityPredicate.AndPredicate andPredicate) {
        this.criterion = resourceLocation;
        this.playerCondition = andPredicate;
    }

    @Override
    public ResourceLocation getId() {
        return this.criterion;
    }

    protected EntityPredicate.AndPredicate getPlayerCondition() {
        return this.playerCondition;
    }

    @Override
    public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("player", this.playerCondition.serializeConditions(conditionArraySerializer));
        return jsonObject;
    }

    public String toString() {
        return "AbstractCriterionInstance{criterion=" + this.criterion + "}";
    }
}

