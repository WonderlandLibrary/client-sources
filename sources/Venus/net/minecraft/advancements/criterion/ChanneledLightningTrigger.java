/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChanneledLightningTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("channeled_lightning");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate[] andPredicateArray = EntityPredicate.AndPredicate.deserialize(jsonObject, "victims", conditionArrayParser);
        return new Instance(andPredicate, andPredicateArray);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Collection<? extends Entity> collection) {
        List list = collection.stream().map(arg_0 -> ChanneledLightningTrigger.lambda$trigger$0(serverPlayerEntity, arg_0)).collect(Collectors.toList());
        this.triggerListeners(serverPlayerEntity, arg_0 -> ChanneledLightningTrigger.lambda$trigger$1(list, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$1(List list, Instance instance) {
        return instance.test(list);
    }

    private static LootContext lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, Entity entity2) {
        return EntityPredicate.getLootContext(serverPlayerEntity, entity2);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate[] victims;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate[] andPredicateArray) {
            super(ID, andPredicate);
            this.victims = andPredicateArray;
        }

        public static Instance channeledLightning(EntityPredicate ... entityPredicateArray) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, (EntityPredicate.AndPredicate[])Stream.of(entityPredicateArray).map(EntityPredicate.AndPredicate::createAndFromEntityCondition).toArray(Instance::lambda$channeledLightning$0));
        }

        public boolean test(Collection<? extends LootContext> collection) {
            for (EntityPredicate.AndPredicate andPredicate : this.victims) {
                boolean bl = false;
                for (LootContext lootContext : collection) {
                    if (!andPredicate.testContext(lootContext)) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
                return true;
            }
            return false;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("victims", EntityPredicate.AndPredicate.serializeConditionsIn(this.victims, conditionArraySerializer));
            return jsonObject;
        }

        private static EntityPredicate.AndPredicate[] lambda$channeledLightning$0(int n) {
            return new EntityPredicate.AndPredicate[n];
        }
    }
}

