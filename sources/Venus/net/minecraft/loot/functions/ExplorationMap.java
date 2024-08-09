/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Locale;
import java.util.Set;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplorationMap
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Structure<?> field_237425_a_ = Structure.field_236380_p_;
    public static final MapDecoration.Type field_215910_a = MapDecoration.Type.MANSION;
    private final Structure<?> destination;
    private final MapDecoration.Type decoration;
    private final byte zoom;
    private final int searchRadius;
    private final boolean skipExistingChunks;

    private ExplorationMap(ILootCondition[] iLootConditionArray, Structure<?> structure, MapDecoration.Type type, byte by, int n, boolean bl) {
        super(iLootConditionArray);
        this.destination = structure;
        this.decoration = type;
        this.zoom = by;
        this.searchRadius = n;
        this.skipExistingChunks = bl;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.EXPLORATION_MAP;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.field_237457_g_);
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        ServerWorld serverWorld;
        BlockPos blockPos;
        if (itemStack.getItem() != Items.MAP) {
            return itemStack;
        }
        Vector3d vector3d = lootContext.get(LootParameters.field_237457_g_);
        if (vector3d != null && (blockPos = (serverWorld = lootContext.getWorld()).func_241117_a_(this.destination, new BlockPos(vector3d), this.searchRadius, this.skipExistingChunks)) != null) {
            ItemStack itemStack2 = FilledMapItem.setupNewMap(serverWorld, blockPos.getX(), blockPos.getZ(), this.zoom, true, true);
            FilledMapItem.func_226642_a_(serverWorld, itemStack2);
            MapData.addTargetDecoration(itemStack2, blockPos, "+", this.decoration);
            itemStack2.setDisplayName(new TranslationTextComponent("filled_map." + this.destination.getStructureName().toLowerCase(Locale.ROOT)));
            return itemStack2;
        }
        return itemStack;
    }

    public static Builder func_215903_b() {
        return new Builder();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private Structure<?> field_216066_a = field_237425_a_;
        private MapDecoration.Type field_216067_b = field_215910_a;
        private byte field_216068_c = (byte)2;
        private int field_216069_d = 50;
        private boolean field_216070_e = true;

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder func_237427_a_(Structure<?> structure) {
            this.field_216066_a = structure;
            return this;
        }

        public Builder func_216064_a(MapDecoration.Type type) {
            this.field_216067_b = type;
            return this;
        }

        public Builder func_216062_a(byte by) {
            this.field_216068_c = by;
            return this;
        }

        public Builder func_216063_a(boolean bl) {
            this.field_216070_e = bl;
            return this;
        }

        @Override
        public ILootFunction build() {
            return new ExplorationMap(this.getConditions(), this.field_216066_a, this.field_216067_b, this.field_216068_c, this.field_216069_d, this.field_216070_e);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<ExplorationMap> {
        @Override
        public void serialize(JsonObject jsonObject, ExplorationMap explorationMap, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, explorationMap, jsonSerializationContext);
            if (!explorationMap.destination.equals(field_237425_a_)) {
                jsonObject.add("destination", jsonSerializationContext.serialize(explorationMap.destination.getStructureName()));
            }
            if (explorationMap.decoration != field_215910_a) {
                jsonObject.add("decoration", jsonSerializationContext.serialize(explorationMap.decoration.toString().toLowerCase(Locale.ROOT)));
            }
            if (explorationMap.zoom != 2) {
                jsonObject.addProperty("zoom", explorationMap.zoom);
            }
            if (explorationMap.searchRadius != 50) {
                jsonObject.addProperty("search_radius", explorationMap.searchRadius);
            }
            if (!explorationMap.skipExistingChunks) {
                jsonObject.addProperty("skip_existing_chunks", explorationMap.skipExistingChunks);
            }
        }

        @Override
        public ExplorationMap deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            Structure<?> structure = Serializer.func_237428_a_(jsonObject);
            String string = jsonObject.has("decoration") ? JSONUtils.getString(jsonObject, "decoration") : "mansion";
            MapDecoration.Type type = field_215910_a;
            try {
                type = MapDecoration.Type.valueOf(string.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException illegalArgumentException) {
                LOGGER.error("Error while parsing loot table decoration entry. Found {}. Defaulting to " + field_215910_a, (Object)string);
            }
            byte by = JSONUtils.getByte(jsonObject, "zoom", (byte)2);
            int n = JSONUtils.getInt(jsonObject, "search_radius", 50);
            boolean bl = JSONUtils.getBoolean(jsonObject, "skip_existing_chunks", true);
            return new ExplorationMap(iLootConditionArray, structure, type, by, n, bl);
        }

        private static Structure<?> func_237428_a_(JsonObject jsonObject) {
            String string;
            Structure structure;
            if (jsonObject.has("destination") && (structure = (Structure)Structure.field_236365_a_.get((string = JSONUtils.getString(jsonObject, "destination")).toLowerCase(Locale.ROOT))) != null) {
                return structure;
            }
            return field_237425_a_;
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (ExplorationMap)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (ExplorationMap)object, jsonSerializationContext);
        }
    }
}

