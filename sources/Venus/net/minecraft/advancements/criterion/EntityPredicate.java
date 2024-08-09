/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.advancements.criterion.PlayerPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class EntityPredicate {
    public static final EntityPredicate ANY = new EntityPredicate(EntityTypePredicate.ANY, DistancePredicate.ANY, LocationPredicate.ANY, MobEffectsPredicate.ANY, NBTPredicate.ANY, EntityFlagsPredicate.ALWAYS_TRUE, EntityEquipmentPredicate.ANY, PlayerPredicate.ANY, FishingPredicate.field_234635_a_, null, null);
    private final EntityTypePredicate type;
    private final DistancePredicate distance;
    private final LocationPredicate location;
    private final MobEffectsPredicate effects;
    private final NBTPredicate nbt;
    private final EntityFlagsPredicate flags;
    private final EntityEquipmentPredicate equipment;
    private final PlayerPredicate player;
    private final FishingPredicate fishingCondition;
    private final EntityPredicate mountCondition;
    private final EntityPredicate targetCondition;
    @Nullable
    private final String team;
    @Nullable
    private final ResourceLocation catType;

    private EntityPredicate(EntityTypePredicate entityTypePredicate, DistancePredicate distancePredicate, LocationPredicate locationPredicate, MobEffectsPredicate mobEffectsPredicate, NBTPredicate nBTPredicate, EntityFlagsPredicate entityFlagsPredicate, EntityEquipmentPredicate entityEquipmentPredicate, PlayerPredicate playerPredicate, FishingPredicate fishingPredicate, @Nullable String string, @Nullable ResourceLocation resourceLocation) {
        this.type = entityTypePredicate;
        this.distance = distancePredicate;
        this.location = locationPredicate;
        this.effects = mobEffectsPredicate;
        this.nbt = nBTPredicate;
        this.flags = entityFlagsPredicate;
        this.equipment = entityEquipmentPredicate;
        this.player = playerPredicate;
        this.fishingCondition = fishingPredicate;
        this.mountCondition = this;
        this.targetCondition = this;
        this.team = string;
        this.catType = resourceLocation;
    }

    private EntityPredicate(EntityTypePredicate entityTypePredicate, DistancePredicate distancePredicate, LocationPredicate locationPredicate, MobEffectsPredicate mobEffectsPredicate, NBTPredicate nBTPredicate, EntityFlagsPredicate entityFlagsPredicate, EntityEquipmentPredicate entityEquipmentPredicate, PlayerPredicate playerPredicate, FishingPredicate fishingPredicate, EntityPredicate entityPredicate, EntityPredicate entityPredicate2, @Nullable String string, @Nullable ResourceLocation resourceLocation) {
        this.type = entityTypePredicate;
        this.distance = distancePredicate;
        this.location = locationPredicate;
        this.effects = mobEffectsPredicate;
        this.nbt = nBTPredicate;
        this.flags = entityFlagsPredicate;
        this.equipment = entityEquipmentPredicate;
        this.player = playerPredicate;
        this.fishingCondition = fishingPredicate;
        this.mountCondition = entityPredicate;
        this.targetCondition = entityPredicate2;
        this.team = string;
        this.catType = resourceLocation;
    }

    public boolean test(ServerPlayerEntity serverPlayerEntity, @Nullable Entity entity2) {
        return this.test(serverPlayerEntity.getServerWorld(), serverPlayerEntity.getPositionVec(), entity2);
    }

    public boolean test(ServerWorld serverWorld, @Nullable Vector3d vector3d, @Nullable Entity entity2) {
        Team team;
        if (this == ANY) {
            return false;
        }
        if (entity2 == null) {
            return true;
        }
        if (!this.type.test(entity2.getType())) {
            return true;
        }
        if (vector3d == null ? this.distance != DistancePredicate.ANY : !this.distance.test(vector3d.x, vector3d.y, vector3d.z, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ())) {
            return true;
        }
        if (!this.location.test(serverWorld, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ())) {
            return true;
        }
        if (!this.effects.test(entity2)) {
            return true;
        }
        if (!this.nbt.test(entity2)) {
            return true;
        }
        if (!this.flags.test(entity2)) {
            return true;
        }
        if (!this.equipment.test(entity2)) {
            return true;
        }
        if (!this.player.test(entity2)) {
            return true;
        }
        if (!this.fishingCondition.func_234638_a_(entity2)) {
            return true;
        }
        if (!this.mountCondition.test(serverWorld, vector3d, entity2.getRidingEntity())) {
            return true;
        }
        if (!this.targetCondition.test(serverWorld, vector3d, entity2 instanceof MobEntity ? ((MobEntity)entity2).getAttackTarget() : null)) {
            return true;
        }
        if (!(this.team == null || (team = entity2.getTeam()) != null && this.team.equals(team.getName()))) {
            return true;
        }
        return this.catType == null || entity2 instanceof CatEntity && ((CatEntity)entity2).getCatTypeName().equals(this.catType);
    }

    public static EntityPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "entity");
            EntityTypePredicate entityTypePredicate = EntityTypePredicate.deserialize(jsonObject.get("type"));
            DistancePredicate distancePredicate = DistancePredicate.deserialize(jsonObject.get("distance"));
            LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject.get("location"));
            MobEffectsPredicate mobEffectsPredicate = MobEffectsPredicate.deserialize(jsonObject.get("effects"));
            NBTPredicate nBTPredicate = NBTPredicate.deserialize(jsonObject.get("nbt"));
            EntityFlagsPredicate entityFlagsPredicate = EntityFlagsPredicate.deserialize(jsonObject.get("flags"));
            EntityEquipmentPredicate entityEquipmentPredicate = EntityEquipmentPredicate.deserialize(jsonObject.get("equipment"));
            PlayerPredicate playerPredicate = PlayerPredicate.deserialize(jsonObject.get("player"));
            FishingPredicate fishingPredicate = FishingPredicate.func_234639_a_(jsonObject.get("fishing_hook"));
            EntityPredicate entityPredicate = EntityPredicate.deserialize(jsonObject.get("vehicle"));
            EntityPredicate entityPredicate2 = EntityPredicate.deserialize(jsonObject.get("targeted_entity"));
            String string = JSONUtils.getString(jsonObject, "team", null);
            ResourceLocation resourceLocation = jsonObject.has("catType") ? new ResourceLocation(JSONUtils.getString(jsonObject, "catType")) : null;
            return new Builder().type(entityTypePredicate).distance(distancePredicate).location(locationPredicate).effects(mobEffectsPredicate).nbt(nBTPredicate).flags(entityFlagsPredicate).equipment(entityEquipmentPredicate).player(playerPredicate).fishing(fishingPredicate).team(string).mount(entityPredicate).target(entityPredicate2).catTypeOrNull(resourceLocation).build();
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("type", this.type.serialize());
        jsonObject.add("distance", this.distance.serialize());
        jsonObject.add("location", this.location.serialize());
        jsonObject.add("effects", this.effects.serialize());
        jsonObject.add("nbt", this.nbt.serialize());
        jsonObject.add("flags", this.flags.serialize());
        jsonObject.add("equipment", this.equipment.serialize());
        jsonObject.add("player", this.player.serialize());
        jsonObject.add("fishing_hook", this.fishingCondition.func_234637_a_());
        jsonObject.add("vehicle", this.mountCondition.serialize());
        jsonObject.add("targeted_entity", this.targetCondition.serialize());
        jsonObject.addProperty("team", this.team);
        if (this.catType != null) {
            jsonObject.addProperty("catType", this.catType.toString());
        }
        return jsonObject;
    }

    public static LootContext getLootContext(ServerPlayerEntity serverPlayerEntity, Entity entity2) {
        return new LootContext.Builder(serverPlayerEntity.getServerWorld()).withParameter(LootParameters.THIS_ENTITY, entity2).withParameter(LootParameters.field_237457_g_, serverPlayerEntity.getPositionVec()).withRandom(serverPlayerEntity.getRNG()).build(LootParameterSets.field_237454_j_);
    }

    public static class Builder {
        private EntityTypePredicate type = EntityTypePredicate.ANY;
        private DistancePredicate distance = DistancePredicate.ANY;
        private LocationPredicate location = LocationPredicate.ANY;
        private MobEffectsPredicate effects = MobEffectsPredicate.ANY;
        private NBTPredicate nbt = NBTPredicate.ANY;
        private EntityFlagsPredicate flags = EntityFlagsPredicate.ALWAYS_TRUE;
        private EntityEquipmentPredicate equipment = EntityEquipmentPredicate.ANY;
        private PlayerPredicate player = PlayerPredicate.ANY;
        private FishingPredicate fishing = FishingPredicate.field_234635_a_;
        private EntityPredicate mount = ANY;
        private EntityPredicate target = ANY;
        private String team;
        private ResourceLocation catType;

        public static Builder create() {
            return new Builder();
        }

        public Builder type(EntityType<?> entityType) {
            this.type = EntityTypePredicate.fromType(entityType);
            return this;
        }

        public Builder type(ITag<EntityType<?>> iTag) {
            this.type = EntityTypePredicate.fromTag(iTag);
            return this;
        }

        public Builder catType(ResourceLocation resourceLocation) {
            this.catType = resourceLocation;
            return this;
        }

        public Builder type(EntityTypePredicate entityTypePredicate) {
            this.type = entityTypePredicate;
            return this;
        }

        public Builder distance(DistancePredicate distancePredicate) {
            this.distance = distancePredicate;
            return this;
        }

        public Builder location(LocationPredicate locationPredicate) {
            this.location = locationPredicate;
            return this;
        }

        public Builder effects(MobEffectsPredicate mobEffectsPredicate) {
            this.effects = mobEffectsPredicate;
            return this;
        }

        public Builder nbt(NBTPredicate nBTPredicate) {
            this.nbt = nBTPredicate;
            return this;
        }

        public Builder flags(EntityFlagsPredicate entityFlagsPredicate) {
            this.flags = entityFlagsPredicate;
            return this;
        }

        public Builder equipment(EntityEquipmentPredicate entityEquipmentPredicate) {
            this.equipment = entityEquipmentPredicate;
            return this;
        }

        public Builder player(PlayerPredicate playerPredicate) {
            this.player = playerPredicate;
            return this;
        }

        public Builder fishing(FishingPredicate fishingPredicate) {
            this.fishing = fishingPredicate;
            return this;
        }

        public Builder mount(EntityPredicate entityPredicate) {
            this.mount = entityPredicate;
            return this;
        }

        public Builder target(EntityPredicate entityPredicate) {
            this.target = entityPredicate;
            return this;
        }

        public Builder team(@Nullable String string) {
            this.team = string;
            return this;
        }

        public Builder catTypeOrNull(@Nullable ResourceLocation resourceLocation) {
            this.catType = resourceLocation;
            return this;
        }

        public EntityPredicate build() {
            return new EntityPredicate(this.type, this.distance, this.location, this.effects, this.nbt, this.flags, this.equipment, this.player, this.fishing, this.mount, this.target, this.team, this.catType);
        }
    }

    public static class AndPredicate {
        public static final AndPredicate ANY_AND = new AndPredicate(new ILootCondition[0]);
        private final ILootCondition[] lootConditions;
        private final Predicate<LootContext> lootContext;

        private AndPredicate(ILootCondition[] iLootConditionArray) {
            this.lootConditions = iLootConditionArray;
            this.lootContext = LootConditionManager.and(iLootConditionArray);
        }

        public static AndPredicate serializePredicate(ILootCondition ... iLootConditionArray) {
            return new AndPredicate(iLootConditionArray);
        }

        public static AndPredicate deserializeJSONObject(JsonObject jsonObject, String string, ConditionArrayParser conditionArrayParser) {
            JsonElement jsonElement = jsonObject.get(string);
            return AndPredicate.fromJSONElement(string, conditionArrayParser, jsonElement);
        }

        public static AndPredicate[] deserialize(JsonObject jsonObject, String string, ConditionArrayParser conditionArrayParser) {
            JsonElement jsonElement = jsonObject.get(string);
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                JsonArray jsonArray = JSONUtils.getJsonArray(jsonElement, string);
                AndPredicate[] andPredicateArray = new AndPredicate[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    andPredicateArray[i] = AndPredicate.fromJSONElement(string + "[" + i + "]", conditionArrayParser, jsonArray.get(i));
                }
                return andPredicateArray;
            }
            return new AndPredicate[0];
        }

        private static AndPredicate fromJSONElement(String string, ConditionArrayParser conditionArrayParser, @Nullable JsonElement jsonElement) {
            if (jsonElement != null && jsonElement.isJsonArray()) {
                ILootCondition[] iLootConditionArray = conditionArrayParser.func_234050_a_(jsonElement.getAsJsonArray(), conditionArrayParser.func_234049_a_().toString() + "/" + string, LootParameterSets.field_237454_j_);
                return new AndPredicate(iLootConditionArray);
            }
            EntityPredicate entityPredicate = EntityPredicate.deserialize(jsonElement);
            return AndPredicate.createAndFromEntityCondition(entityPredicate);
        }

        public static AndPredicate createAndFromEntityCondition(EntityPredicate entityPredicate) {
            if (entityPredicate == ANY) {
                return ANY_AND;
            }
            ILootCondition iLootCondition = EntityHasProperty.func_237477_a_(LootContext.EntityTarget.THIS, entityPredicate).build();
            return new AndPredicate(new ILootCondition[]{iLootCondition});
        }

        public boolean testContext(LootContext lootContext) {
            return this.lootContext.test(lootContext);
        }

        public JsonElement serializeConditions(ConditionArraySerializer conditionArraySerializer) {
            return this.lootConditions.length == 0 ? JsonNull.INSTANCE : conditionArraySerializer.func_235681_a_(this.lootConditions);
        }

        public static JsonElement serializeConditionsIn(AndPredicate[] andPredicateArray, ConditionArraySerializer conditionArraySerializer) {
            if (andPredicateArray.length == 0) {
                return JsonNull.INSTANCE;
            }
            JsonArray jsonArray = new JsonArray();
            for (AndPredicate andPredicate : andPredicateArray) {
                jsonArray.add(andPredicate.serializeConditions(conditionArraySerializer));
            }
            return jsonArray;
        }
    }
}

