/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel
implements IBakedModel {
    private final int totalWeight;
    private final List<WeightedModel> models;
    private final IBakedModel baseModel;

    public WeightedBakedModel(List<WeightedModel> list) {
        this.models = list;
        this.totalWeight = WeightedRandom.getTotalWeight(list);
        this.baseModel = list.get((int)0).model;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random2) {
        return WeightedRandom.getRandomItem(this.models, (int)(Math.abs((int)((int)random2.nextLong())) % this.totalWeight)).model.getQuads(blockState, direction, random2);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.baseModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.baseModel.isGui3d();
    }

    @Override
    public boolean isSideLit() {
        return this.baseModel.isSideLit();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return this.baseModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.baseModel.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.baseModel.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.baseModel.getOverrides();
    }

    static class WeightedModel
    extends WeightedRandom.Item {
        protected final IBakedModel model;

        public WeightedModel(IBakedModel iBakedModel, int n) {
            super(n);
            this.model = iBakedModel;
        }
    }

    public static class Builder {
        private final List<WeightedModel> listItems = Lists.newArrayList();

        public Builder add(@Nullable IBakedModel iBakedModel, int n) {
            if (iBakedModel != null) {
                this.listItems.add(new WeightedModel(iBakedModel, n));
            }
            return this;
        }

        @Nullable
        public IBakedModel build() {
            if (this.listItems.isEmpty()) {
                return null;
            }
            return this.listItems.size() == 1 ? this.listItems.get((int)0).model : new WeightedBakedModel(this.listItems);
        }
    }
}

