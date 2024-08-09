/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client.extensions;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.IModelData;
import net.optifine.reflect.Reflector;

public interface IForgeBakedModel {
    default public IBakedModel getBakedModel() {
        return (IBakedModel)this;
    }

    default public List<BakedQuad> getQuads(BlockState blockState, Direction direction, Random random2, IModelData iModelData) {
        return this.getBakedModel().getQuads(blockState, direction, random2);
    }

    default public boolean isAmbientOcclusion(BlockState blockState) {
        return this.getBakedModel().isAmbientOcclusion();
    }

    default public IBakedModel handlePerspective(ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack) {
        return (IBakedModel)Reflector.ForgeHooksClient_handlePerspective.call(new Object[]{this.getBakedModel(), transformType, matrixStack});
    }

    default public IModelData getModelData(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, BlockState blockState, IModelData iModelData) {
        return iModelData;
    }

    default public TextureAtlasSprite getParticleTexture(IModelData iModelData) {
        return this.getBakedModel().getParticleTexture();
    }

    default public boolean isLayered() {
        return true;
    }
}

