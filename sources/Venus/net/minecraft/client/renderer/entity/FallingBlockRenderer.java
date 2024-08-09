/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingBlockRenderer
extends EntityRenderer<FallingBlockEntity> {
    public FallingBlockRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.5f;
    }

    @Override
    public void render(FallingBlockEntity fallingBlockEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        World world;
        BlockState blockState = fallingBlockEntity.getBlockState();
        if (blockState.getRenderType() == BlockRenderType.MODEL && blockState != (world = fallingBlockEntity.getWorldObj()).getBlockState(fallingBlockEntity.getPosition()) && blockState.getRenderType() != BlockRenderType.INVISIBLE) {
            matrixStack.push();
            BlockPos blockPos = new BlockPos(fallingBlockEntity.getPosX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getPosZ());
            matrixStack.translate(-0.5, 0.0, -0.5);
            BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            blockRendererDispatcher.getBlockModelRenderer().renderModel(world, blockRendererDispatcher.getModelForState(blockState), blockState, blockPos, matrixStack, iRenderTypeBuffer.getBuffer(RenderTypeLookup.func_239221_b_(blockState)), false, new Random(), blockState.getPositionRandom(fallingBlockEntity.getOrigin()), OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
            super.render(fallingBlockEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(FallingBlockEntity fallingBlockEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((FallingBlockEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((FallingBlockEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

