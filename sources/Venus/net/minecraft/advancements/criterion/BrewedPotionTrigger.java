/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BrewedPotionTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("brewed_potion");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        Potion potion = null;
        if (jsonObject.has("potion")) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "potion"));
            potion = Registry.POTION.getOptional(resourceLocation).orElseThrow(() -> BrewedPotionTrigger.lambda$deserializeTrigger$0(resourceLocation));
        }
        return new Instance(andPredicate, potion);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Potion potion) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> BrewedPotionTrigger.lambda$trigger$1(potion, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$1(Potion potion, Instance instance) {
        return instance.test(potion);
    }

    private static JsonSyntaxException lambda$deserializeTrigger$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown potion '" + resourceLocation + "'");
    }

    public static class Instance
    extends CriterionInstance {
        private final Potion potion;

        public Instance(EntityPredicate.AndPredicate andPredicate, @Nullable Potion potion) {
            super(ID, andPredicate);
            this.potion = potion;
        }

        public static Instance brewedPotion() {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, (Potion)null);
        }

        public boolean test(Potion potion) {
            return this.potion == null || this.potion == potion;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (this.potion != null) {
                jsonObject.addProperty("potion", Registry.POTION.getKey(this.potion).toString());
            }
            return jsonObject;
        }
    }
}

