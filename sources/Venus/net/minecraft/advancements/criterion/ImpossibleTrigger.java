/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ImpossibleTrigger
implements ICriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("impossible");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener<Instance> listener) {
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancements, ICriterionTrigger.Listener<Instance> listener) {
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancements) {
    }

    @Override
    public Instance deserialize(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        return new Instance();
    }

    @Override
    public ICriterionInstance deserialize(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        return this.deserialize(jsonObject, conditionArrayParser);
    }

    public static class Instance
    implements ICriterionInstance {
        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            return new JsonObject();
        }
    }
}

