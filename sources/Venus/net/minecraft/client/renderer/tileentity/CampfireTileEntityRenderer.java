/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;

public class CampfireTileEntityRenderer
extends TileEntityRenderer<CampfireTileEntity> {
    public CampfireTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(CampfireTileEntity campfireTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        Direction direction = campfireTileEntity.getBlockState().get(CampfireBlock.FACING);
        NonNullList<ItemStack> nonNullList = campfireTileEntity.getInventory();
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = nonNullList.get(i);
            if (itemStack == ItemStack.EMPTY) continue;
            matrixStack.push();
            matrixStack.translate(0.5, 0.44921875, 0.5);
            Direction direction2 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
            float f2 = -direction2.getHorizontalAngle();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f2));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
            matrixStack.translate(-0.3125, -0.3125, 0.0);
            matrixStack.scale(0.375f, 0.375f, 0.375f);
            Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED, n, n2, matrixStack, iRenderTypeBuffer);
            matrixStack.pop();
        }
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((CampfireTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

