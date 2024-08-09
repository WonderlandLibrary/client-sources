/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

public class SimpleBakedModel
implements IBakedModel {
    protected final List<BakedQuad> generalQuads;
    protected final Map<Direction, List<BakedQuad>> faceQuads;
    protected final boolean ambientOcclusion;
    protected final boolean gui3d;
    protected final boolean isSideLit;
    protected final TextureAtlasSprite texture;
    protected final ItemCameraTransforms cameraTransforms;
    protected final ItemOverrideList itemOverrideList;

    public SimpleBakedModel(List<BakedQuad> list, Map<Direction, List<BakedQuad>> map, boolean bl, boolean bl2, boolean bl3, TextureAtlasSprite textureAtlasSprite, ItemCameraTransforms itemCameraTransforms, ItemOverrideList itemOverrideList) {
        this.generalQuads = list;
        this.faceQuads = map;
        this.ambientOcclusion = bl;
        this.gui3d = bl3;
        this.isSideLit = bl2;
        this.texture = textureAtlasSprite;
        this.cameraTransforms = itemCameraTransforms;
        this.itemOverrideList = itemOverrideList;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random2) {
        return direction == null ? this.generalQuads : this.faceQuads.get(direction);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return this.gui3d;
    }

    @Override
    public boolean isSideLit() {
        return this.isSideLit;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.texture;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.itemOverrideList;
    }

    public static class Builder {
        private final List<BakedQuad> builderGeneralQuads = Lists.newArrayList();
        private final Map<Direction, List<BakedQuad>> builderFaceQuads = Maps.newEnumMap(Direction.class);
        private final ItemOverrideList builderItemOverrideList;
        private final boolean builderAmbientOcclusion;
        private TextureAtlasSprite builderTexture;
        private final boolean isSideLit;
        private final boolean builderGui3d;
        private final ItemCameraTransforms builderCameraTransforms;

        public Builder(BlockModel blockModel, ItemOverrideList itemOverrideList, boolean bl) {
            this(blockModel.isAmbientOcclusion(), blockModel.getGuiLight().isSideLit(), bl, blockModel.getAllTransforms(), itemOverrideList);
        }

        private Builder(boolean bl, boolean bl2, boolean bl3, ItemCameraTransforms itemCameraTransforms, ItemOverrideList itemOverrideList) {
            for (Direction direction : Direction.values()) {
                this.builderFaceQuads.put(direction, Lists.newArrayList());
            }
            this.builderItemOverrideList = itemOverrideList;
            this.builderAmbientOcclusion = bl;
            this.isSideLit = bl2;
            this.builderGui3d = bl3;
            this.builderCameraTransforms = itemCameraTransforms;
        }

        public Builder addFaceQuad(Direction direction, BakedQuad bakedQuad) {
            this.builderFaceQuads.get(direction).add(bakedQuad);
            return this;
        }

        public Builder addGeneralQuad(BakedQuad bakedQuad) {
            this.builderGeneralQuads.add(bakedQuad);
            return this;
        }

        public Builder setTexture(TextureAtlasSprite textureAtlasSprite) {
            this.builderTexture = textureAtlasSprite;
            return this;
        }

        public IBakedModel build() {
            if (this.builderTexture == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.isSideLit, this.builderGui3d, this.builderTexture, this.builderCameraTransforms, this.builderItemOverrideList);
        }
    }
}

