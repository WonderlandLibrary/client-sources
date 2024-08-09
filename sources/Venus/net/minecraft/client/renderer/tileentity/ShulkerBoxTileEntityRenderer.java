/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.state.StateHolder;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ShulkerBoxTileEntityRenderer
extends TileEntityRenderer<ShulkerBoxTileEntity> {
    private final ShulkerModel<?> model;

    public ShulkerBoxTileEntityRenderer(ShulkerModel<?> shulkerModel, TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        this.model = shulkerModel;
    }

    @Override
    public void render(ShulkerBoxTileEntity shulkerBoxTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        Object object;
        Direction direction = Direction.UP;
        if (shulkerBoxTileEntity.hasWorld() && ((AbstractBlock.AbstractBlockState)(object = shulkerBoxTileEntity.getWorld().getBlockState(shulkerBoxTileEntity.getPos()))).getBlock() instanceof ShulkerBoxBlock) {
            direction = ((StateHolder)object).get(ShulkerBoxBlock.FACING);
        }
        RenderMaterial renderMaterial = (object = shulkerBoxTileEntity.getColor()) == null ? Atlases.DEFAULT_SHULKER_TEXTURE : Atlases.SHULKER_TEXTURES.get(((DyeColor)object).getId());
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        float f2 = 0.9995f;
        matrixStack.scale(0.9995f, 0.9995f, 0.9995f);
        matrixStack.rotate(direction.getRotation());
        matrixStack.scale(1.0f, -1.0f, -1.0f);
        matrixStack.translate(0.0, -1.0, 0.0);
        IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(iRenderTypeBuffer, RenderType::getEntityCutoutNoCull);
        this.model.getBase().render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.translate(0.0, -shulkerBoxTileEntity.getProgress(f) * 0.5f, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(270.0f * shulkerBoxTileEntity.getProgress(f)));
        this.model.getLid().render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.pop();
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((ShulkerBoxTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

