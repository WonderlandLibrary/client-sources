/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class LocationCheck
implements ILootCondition {
    private final LocationPredicate predicate;
    private final BlockPos field_227564_b_;

    private LocationCheck(LocationPredicate locationPredicate, BlockPos blockPos) {
        this.predicate = locationPredicate;
        this.field_227564_b_ = blockPos;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.LOCATION_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Vector3d vector3d = lootContext.get(LootParameters.field_237457_g_);
        return vector3d != null && this.predicate.test(lootContext.getWorld(), vector3d.getX() + (double)this.field_227564_b_.getX(), vector3d.getY() + (double)this.field_227564_b_.getY(), vector3d.getZ() + (double)this.field_227564_b_.getZ());
    }

    public static ILootCondition.IBuilder builder(LocationPredicate.Builder builder) {
        return () -> LocationCheck.lambda$builder$0(builder);
    }

    public static ILootCondition.IBuilder func_241547_a_(LocationPredicate.Builder builder, BlockPos blockPos) {
        return () -> LocationCheck.lambda$func_241547_a_$1(builder, blockPos);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$func_241547_a_$1(LocationPredicate.Builder builder, BlockPos blockPos) {
        return new LocationCheck(builder.build(), blockPos);
    }

    private static ILootCondition lambda$builder$0(LocationPredicate.Builder builder) {
        return new LocationCheck(builder.build(), BlockPos.ZERO);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<LocationCheck> {
        @Override
        public void serialize(JsonObject jsonObject, LocationCheck locationCheck, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("predicate", locationCheck.predicate.serialize());
            if (locationCheck.field_227564_b_.getX() != 0) {
                jsonObject.addProperty("offsetX", locationCheck.field_227564_b_.getX());
            }
            if (locationCheck.field_227564_b_.getY() != 0) {
                jsonObject.addProperty("offsetY", locationCheck.field_227564_b_.getY());
            }
            if (locationCheck.field_227564_b_.getZ() != 0) {
                jsonObject.addProperty("offsetZ", locationCheck.field_227564_b_.getZ());
            }
        }

        @Override
        public LocationCheck deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject.get("predicate"));
            int n = JSONUtils.getInt(jsonObject, "offsetX", 0);
            int n2 = JSONUtils.getInt(jsonObject, "offsetY", 0);
            int n3 = JSONUtils.getInt(jsonObject, "offsetZ", 0);
            return new LocationCheck(locationPredicate, new BlockPos(n, n2, n3));
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (LocationCheck)object, jsonSerializationContext);
        }
    }
}

