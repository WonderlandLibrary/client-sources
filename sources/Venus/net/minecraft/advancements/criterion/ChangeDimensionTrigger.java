/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChangeDimensionTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("changed_dimension");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        RegistryKey<World> registryKey = jsonObject.has("from") ? RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(JSONUtils.getString(jsonObject, "from"))) : null;
        RegistryKey<World> registryKey2 = jsonObject.has("to") ? RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(JSONUtils.getString(jsonObject, "to"))) : null;
        return new Instance(andPredicate, registryKey, registryKey2);
    }

    public void testForAll(ServerPlayerEntity serverPlayerEntity, RegistryKey<World> registryKey, RegistryKey<World> registryKey2) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> ChangeDimensionTrigger.lambda$testForAll$0(registryKey, registryKey2, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$testForAll$0(RegistryKey registryKey, RegistryKey registryKey2, Instance instance) {
        return instance.test(registryKey, registryKey2);
    }

    public static class Instance
    extends CriterionInstance {
        @Nullable
        private final RegistryKey<World> from;
        @Nullable
        private final RegistryKey<World> to;

        public Instance(EntityPredicate.AndPredicate andPredicate, @Nullable RegistryKey<World> registryKey, @Nullable RegistryKey<World> registryKey2) {
            super(ID, andPredicate);
            this.from = registryKey;
            this.to = registryKey2;
        }

        public static Instance toWorld(RegistryKey<World> registryKey) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, null, registryKey);
        }

        public boolean test(RegistryKey<World> registryKey, RegistryKey<World> registryKey2) {
            if (this.from != null && this.from != registryKey) {
                return true;
            }
            return this.to == null || this.to == registryKey2;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (this.from != null) {
                jsonObject.addProperty("from", this.from.getLocation().toString());
            }
            if (this.to != null) {
                jsonObject.addProperty("to", this.to.getLocation().toString());
            }
            return jsonObject;
        }
    }
}

