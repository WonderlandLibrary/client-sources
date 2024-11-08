/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SimpleBakedModel
implements IBakedModel {
    protected final List<BakedQuad> generalQuads;
    protected final Map<EnumFacing, List<BakedQuad>> faceQuads;
    protected final boolean ambientOcclusion;
    protected final boolean gui3d;
    protected final TextureAtlasSprite texture;
    protected final ItemCameraTransforms cameraTransforms;
    protected final ItemOverrideList itemOverrideList;

    public SimpleBakedModel(List<BakedQuad> generalQuadsIn, Map<EnumFacing, List<BakedQuad>> faceQuadsIn, boolean ambientOcclusionIn, boolean gui3dIn, TextureAtlasSprite textureIn, ItemCameraTransforms cameraTransformsIn, ItemOverrideList itemOverrideListIn) {
        this.generalQuads = generalQuadsIn;
        this.faceQuads = faceQuadsIn;
        this.ambientOcclusion = ambientOcclusionIn;
        this.gui3d = gui3dIn;
        this.texture = textureIn;
        this.cameraTransforms = cameraTransformsIn;
        this.itemOverrideList = itemOverrideListIn;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return side == null ? this.generalQuads : this.faceQuads.get(side);
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
    public boolean isBuiltInRenderer() {
        return false;
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
        private final Map<EnumFacing, List<BakedQuad>> builderFaceQuads = Maps.newEnumMap(EnumFacing.class);
        private final ItemOverrideList builderItemOverrideList;
        private final boolean builderAmbientOcclusion;
        private TextureAtlasSprite builderTexture;
        private final boolean builderGui3d;
        private final ItemCameraTransforms builderCameraTransforms;

        public Builder(ModelBlock model, ItemOverrideList overrides) {
            this(model.isAmbientOcclusion(), model.isGui3d(), model.getAllTransforms(), overrides);
        }

        public Builder(IBlockState state, IBakedModel model, TextureAtlasSprite texture, BlockPos pos) {
            this(model.isAmbientOcclusion(), model.isGui3d(), model.getItemCameraTransforms(), model.getOverrides());
            this.builderTexture = model.getParticleTexture();
            long i = MathHelper.getPositionRandom(pos);
            for (EnumFacing enumfacing : EnumFacing.values()) {
                this.addFaceQuads(state, model, texture, enumfacing, i);
            }
            this.addGeneralQuads(state, model, texture, i);
        }

        private Builder(boolean ambientOcclusion, boolean gui3d, ItemCameraTransforms transforms, ItemOverrideList overrides) {
            for (EnumFacing enumfacing : EnumFacing.values()) {
                this.builderFaceQuads.put(enumfacing, Lists.newArrayList());
            }
            this.builderItemOverrideList = overrides;
            this.builderAmbientOcclusion = ambientOcclusion;
            this.builderGui3d = gui3d;
            this.builderCameraTransforms = transforms;
        }

        private void addFaceQuads(IBlockState p_188644_1_, IBakedModel p_188644_2_, TextureAtlasSprite p_188644_3_, EnumFacing p_188644_4_, long p_188644_5_) {
            for (BakedQuad bakedquad : p_188644_2_.getQuads(p_188644_1_, p_188644_4_, p_188644_5_)) {
                this.addFaceQuad(p_188644_4_, new BakedQuadRetextured(bakedquad, p_188644_3_));
            }
        }

        private void addGeneralQuads(IBlockState p_188645_1_, IBakedModel p_188645_2_, TextureAtlasSprite p_188645_3_, long p_188645_4_) {
            for (BakedQuad bakedquad : p_188645_2_.getQuads(p_188645_1_, null, p_188645_4_)) {
                this.addGeneralQuad(new BakedQuadRetextured(bakedquad, p_188645_3_));
            }
        }

        public Builder addFaceQuad(EnumFacing facing, BakedQuad quad) {
            this.builderFaceQuads.get(facing).add(quad);
            return this;
        }

        public Builder addGeneralQuad(BakedQuad quad) {
            this.builderGeneralQuads.add(quad);
            return this;
        }

        public Builder setTexture(TextureAtlasSprite texture) {
            this.builderTexture = texture;
            return this;
        }

        public IBakedModel makeBakedModel() {
            if (this.builderTexture == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms, this.builderItemOverrideList);
        }
    }
}

