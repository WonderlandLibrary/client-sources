/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class BlockPredicate {
    public static final BlockPredicate ANY = new BlockPredicate(null, null, StatePropertiesPredicate.EMPTY, NBTPredicate.ANY);
    @Nullable
    private final ITag<Block> tag;
    @Nullable
    private final Block block;
    private final StatePropertiesPredicate statePredicate;
    private final NBTPredicate nbtPredicate;

    public BlockPredicate(@Nullable ITag<Block> iTag, @Nullable Block block, StatePropertiesPredicate statePropertiesPredicate, NBTPredicate nBTPredicate) {
        this.tag = iTag;
        this.block = block;
        this.statePredicate = statePropertiesPredicate;
        this.nbtPredicate = nBTPredicate;
    }

    public boolean test(ServerWorld serverWorld, BlockPos blockPos) {
        TileEntity tileEntity;
        if (this == ANY) {
            return false;
        }
        if (!serverWorld.isBlockPresent(blockPos)) {
            return true;
        }
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (this.tag != null && !this.tag.contains(block)) {
            return true;
        }
        if (this.block != null && block != this.block) {
            return true;
        }
        if (!this.statePredicate.matches(blockState)) {
            return true;
        }
        return this.nbtPredicate != NBTPredicate.ANY && ((tileEntity = serverWorld.getTileEntity(blockPos)) == null || !this.nbtPredicate.test(tileEntity.write(new CompoundNBT())));
    }

    public static BlockPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            Object object;
            ITag<Block> iTag;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "block");
            NBTPredicate nBTPredicate = NBTPredicate.deserialize(jsonObject.get("nbt"));
            Block block = null;
            if (jsonObject.has("block")) {
                iTag = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));
                block = Registry.BLOCK.getOrDefault((ResourceLocation)((Object)iTag));
            }
            iTag = null;
            if (jsonObject.has("tag")) {
                object = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
                iTag = TagCollectionManager.getManager().getBlockTags().get((ResourceLocation)object);
                if (iTag == null) {
                    throw new JsonSyntaxException("Unknown block tag '" + (ResourceLocation)object + "'");
                }
            }
            object = StatePropertiesPredicate.deserializeProperties(jsonObject.get("state"));
            return new BlockPredicate(iTag, block, (StatePropertiesPredicate)object, nBTPredicate);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.block != null) {
            jsonObject.addProperty("block", Registry.BLOCK.getKey(this.block).toString());
        }
        if (this.tag != null) {
            jsonObject.addProperty("tag", TagCollectionManager.getManager().getBlockTags().getValidatedIdFromTag(this.tag).toString());
        }
        jsonObject.add("nbt", this.nbtPredicate.serialize());
        jsonObject.add("state", this.statePredicate.toJsonElement());
        return jsonObject;
    }

    public static class Builder {
        @Nullable
        private Block block;
        @Nullable
        private ITag<Block> tag;
        private StatePropertiesPredicate statePredicate = StatePropertiesPredicate.EMPTY;
        private NBTPredicate nbtPredicate = NBTPredicate.ANY;

        private Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder setBlock(Block block) {
            this.block = block;
            return this;
        }

        public Builder setTag(ITag<Block> iTag) {
            this.tag = iTag;
            return this;
        }

        public Builder setStatePredicate(StatePropertiesPredicate statePropertiesPredicate) {
            this.statePredicate = statePropertiesPredicate;
            return this;
        }

        public BlockPredicate build() {
            return new BlockPredicate(this.tag, this.block, this.statePredicate, this.nbtPredicate);
        }
    }
}

