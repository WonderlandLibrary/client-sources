/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KilledByCrossbowTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("killed_by_crossbow");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate[] andPredicateArray = EntityPredicate.AndPredicate.deserialize(jsonObject, "victims", conditionArrayParser);
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("unique_entity_types"));
        return new Instance(andPredicate, andPredicateArray, intBound);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, Collection<Entity> collection) {
        ArrayList<LootContext> arrayList = Lists.newArrayList();
        HashSet<EntityType<?>> hashSet = Sets.newHashSet();
        for (Entity entity2 : collection) {
            hashSet.add(entity2.getType());
            arrayList.add(EntityPredicate.getLootContext(serverPlayerEntity, entity2));
        }
        this.triggerListeners(serverPlayerEntity, arg_0 -> KilledByCrossbowTrigger.lambda$test$0(arrayList, hashSet, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(List list, Set set, Instance instance) {
        return instance.test(list, set.size());
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate[] entities;
        private final MinMaxBounds.IntBound bounds;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate[] andPredicateArray, MinMaxBounds.IntBound intBound) {
            super(ID, andPredicate);
            this.entities = andPredicateArray;
            this.bounds = intBound;
        }

        public static Instance fromBuilders(EntityPredicate.Builder ... builderArray) {
            EntityPredicate.AndPredicate[] andPredicateArray = new EntityPredicate.AndPredicate[builderArray.length];
            for (int i = 0; i < builderArray.length; ++i) {
                EntityPredicate.Builder builder = builderArray[i];
                andPredicateArray[i] = EntityPredicate.AndPredicate.createAndFromEntityCondition(builder.build());
            }
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, andPredicateArray, MinMaxBounds.IntBound.UNBOUNDED);
        }

        public static Instance fromBounds(MinMaxBounds.IntBound intBound) {
            EntityPredicate.AndPredicate[] andPredicateArray = new EntityPredicate.AndPredicate[]{};
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, andPredicateArray, intBound);
        }

        public boolean test(Collection<LootContext> collection, int n) {
            if (this.entities.length > 0) {
                ArrayList<LootContext> arrayList = Lists.newArrayList(collection);
                for (EntityPredicate.AndPredicate andPredicate : this.entities) {
                    boolean bl = false;
                    Iterator iterator2 = arrayList.iterator();
                    while (iterator2.hasNext()) {
                        LootContext lootContext = (LootContext)iterator2.next();
                        if (!andPredicate.testContext(lootContext)) continue;
                        iterator2.remove();
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    return true;
                }
            }
            return this.bounds.test(n);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("victims", EntityPredicate.AndPredicate.serializeConditionsIn(this.entities, conditionArraySerializer));
            jsonObject.add("unique_entity_types", this.bounds.serialize());
            return jsonObject;
        }
    }
}

