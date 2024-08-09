/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.advancements.criterion.LightPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.CampfireBlock;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationPredicate {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final LocationPredicate ANY = new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, null, null, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    private final MinMaxBounds.FloatBound x;
    private final MinMaxBounds.FloatBound y;
    private final MinMaxBounds.FloatBound z;
    @Nullable
    private final RegistryKey<Biome> biome;
    @Nullable
    private final Structure<?> feature;
    @Nullable
    private final RegistryKey<World> dimension;
    @Nullable
    private final Boolean smokey;
    private final LightPredicate light;
    private final BlockPredicate block;
    private final FluidPredicate fluid;

    public LocationPredicate(MinMaxBounds.FloatBound floatBound, MinMaxBounds.FloatBound floatBound2, MinMaxBounds.FloatBound floatBound3, @Nullable RegistryKey<Biome> registryKey, @Nullable Structure<?> structure, @Nullable RegistryKey<World> registryKey2, @Nullable Boolean bl, LightPredicate lightPredicate, BlockPredicate blockPredicate, FluidPredicate fluidPredicate) {
        this.x = floatBound;
        this.y = floatBound2;
        this.z = floatBound3;
        this.biome = registryKey;
        this.feature = structure;
        this.dimension = registryKey2;
        this.smokey = bl;
        this.light = lightPredicate;
        this.block = blockPredicate;
        this.fluid = fluidPredicate;
    }

    public static LocationPredicate forBiome(RegistryKey<Biome> registryKey) {
        return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, registryKey, null, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public static LocationPredicate forRegistryKey(RegistryKey<World> registryKey) {
        return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, null, null, registryKey, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public static LocationPredicate forFeature(Structure<?> structure) {
        return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, null, structure, null, null, LightPredicate.ANY, BlockPredicate.ANY, FluidPredicate.ANY);
    }

    public boolean test(ServerWorld serverWorld, double d, double d2, double d3) {
        return this.test(serverWorld, (float)d, (float)d2, (float)d3);
    }

    public boolean test(ServerWorld serverWorld, float f, float f2, float f3) {
        if (!this.x.test(f)) {
            return true;
        }
        if (!this.y.test(f2)) {
            return true;
        }
        if (!this.z.test(f3)) {
            return true;
        }
        if (this.dimension != null && this.dimension != serverWorld.getDimensionKey()) {
            return true;
        }
        BlockPos blockPos = new BlockPos(f, f2, f3);
        boolean bl = serverWorld.isBlockPresent(blockPos);
        Optional<RegistryKey<Biome>> optional = serverWorld.func_241828_r().getRegistry(Registry.BIOME_KEY).getOptionalKey(serverWorld.getBiome(blockPos));
        if (!optional.isPresent()) {
            return true;
        }
        if (this.biome == null || bl && this.biome == optional.get()) {
            if (this.feature == null || bl && serverWorld.func_241112_a_().func_235010_a_(blockPos, true, this.feature).isValid()) {
                if (this.smokey == null || bl && this.smokey == CampfireBlock.isSmokingBlockAt(serverWorld, blockPos)) {
                    if (!this.light.test(serverWorld, blockPos)) {
                        return true;
                    }
                    if (!this.block.test(serverWorld, blockPos)) {
                        return true;
                    }
                    return this.fluid.test(serverWorld, blockPos);
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (!(this.x.isUnbounded() && this.y.isUnbounded() && this.z.isUnbounded())) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.add("x", this.x.serialize());
            jsonObject2.add("y", this.y.serialize());
            jsonObject2.add("z", this.z.serialize());
            jsonObject.add("position", jsonObject2);
        }
        if (this.dimension != null) {
            World.CODEC.encodeStart(JsonOps.INSTANCE, this.dimension).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> LocationPredicate.lambda$serialize$0(jsonObject, arg_0));
        }
        if (this.feature != null) {
            jsonObject.addProperty("feature", this.feature.getStructureName());
        }
        if (this.biome != null) {
            jsonObject.addProperty("biome", this.biome.getLocation().toString());
        }
        if (this.smokey != null) {
            jsonObject.addProperty("smokey", this.smokey);
        }
        jsonObject.add("light", this.light.serialize());
        jsonObject.add("block", this.block.serialize());
        jsonObject.add("fluid", this.fluid.serialize());
        return jsonObject;
    }

    public static LocationPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            ResourceLocation resourceLocation;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "location");
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "position", new JsonObject());
            MinMaxBounds.FloatBound floatBound = MinMaxBounds.FloatBound.fromJson(jsonObject2.get("x"));
            MinMaxBounds.FloatBound floatBound2 = MinMaxBounds.FloatBound.fromJson(jsonObject2.get("y"));
            MinMaxBounds.FloatBound floatBound3 = MinMaxBounds.FloatBound.fromJson(jsonObject2.get("z"));
            RegistryKey registryKey = jsonObject.has("dimension") ? ResourceLocation.CODEC.parse(JsonOps.INSTANCE, jsonObject.get("dimension")).resultOrPartial(LOGGER::error).map(LocationPredicate::lambda$deserialize$1).orElse(null) : null;
            Structure structure = jsonObject.has("feature") ? (Structure)Structure.field_236365_a_.get(JSONUtils.getString(jsonObject, "feature")) : null;
            RegistryKey<Biome> registryKey2 = null;
            if (jsonObject.has("biome")) {
                resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "biome"));
                registryKey2 = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, resourceLocation);
            }
            resourceLocation = jsonObject.has("smokey") ? Boolean.valueOf(jsonObject.get("smokey").getAsBoolean()) : null;
            LightPredicate lightPredicate = LightPredicate.deserialize(jsonObject.get("light"));
            BlockPredicate blockPredicate = BlockPredicate.deserialize(jsonObject.get("block"));
            FluidPredicate fluidPredicate = FluidPredicate.deserialize(jsonObject.get("fluid"));
            return new LocationPredicate(floatBound, floatBound2, floatBound3, registryKey2, structure, registryKey, (Boolean)((Object)resourceLocation), lightPredicate, blockPredicate, fluidPredicate);
        }
        return ANY;
    }

    private static RegistryKey lambda$deserialize$1(ResourceLocation resourceLocation) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, resourceLocation);
    }

    private static void lambda$serialize$0(JsonObject jsonObject, JsonElement jsonElement) {
        jsonObject.add("dimension", jsonElement);
    }

    public static class Builder {
        private MinMaxBounds.FloatBound x = MinMaxBounds.FloatBound.UNBOUNDED;
        private MinMaxBounds.FloatBound y = MinMaxBounds.FloatBound.UNBOUNDED;
        private MinMaxBounds.FloatBound z = MinMaxBounds.FloatBound.UNBOUNDED;
        @Nullable
        private RegistryKey<Biome> biome;
        @Nullable
        private Structure<?> feature;
        @Nullable
        private RegistryKey<World> dimension;
        @Nullable
        private Boolean smokey;
        private LightPredicate light = LightPredicate.ANY;
        private BlockPredicate block = BlockPredicate.ANY;
        private FluidPredicate fluid = FluidPredicate.ANY;

        public static Builder builder() {
            return new Builder();
        }

        public Builder biome(@Nullable RegistryKey<Biome> registryKey) {
            this.biome = registryKey;
            return this;
        }

        public Builder block(BlockPredicate blockPredicate) {
            this.block = blockPredicate;
            return this;
        }

        public Builder smokey(Boolean bl) {
            this.smokey = bl;
            return this;
        }

        public LocationPredicate build() {
            return new LocationPredicate(this.x, this.y, this.z, this.biome, this.feature, this.dimension, this.smokey, this.light, this.block, this.fluid);
        }
    }
}

