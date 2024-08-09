/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ConstructBeaconTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("construct_beacon");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("level"));
        return new Instance(andPredicate, intBound);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, BeaconTileEntity beaconTileEntity) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> ConstructBeaconTrigger.lambda$trigger$0(beaconTileEntity, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(BeaconTileEntity beaconTileEntity, Instance instance) {
        return instance.test(beaconTileEntity);
    }

    public static class Instance
    extends CriterionInstance {
        private final MinMaxBounds.IntBound level;

        public Instance(EntityPredicate.AndPredicate andPredicate, MinMaxBounds.IntBound intBound) {
            super(ID, andPredicate);
            this.level = intBound;
        }

        public static Instance forLevel(MinMaxBounds.IntBound intBound) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, intBound);
        }

        public boolean test(BeaconTileEntity beaconTileEntity) {
            return this.level.test(beaconTileEntity.getLevels());
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("level", this.level.serialize());
            return jsonObject;
        }
    }
}

