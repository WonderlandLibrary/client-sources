/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;

public class LecternTileEntityRenderer
extends TileEntityRenderer<LecternTileEntity> {
    private final BookModel field_217656_d = new BookModel();

    public LecternTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(LecternTileEntity lecternTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        BlockState blockState = lecternTileEntity.getBlockState();
        if (blockState.get(LecternBlock.HAS_BOOK).booleanValue()) {
            matrixStack.push();
            matrixStack.translate(0.5, 1.0625, 0.5);
            float f2 = blockState.get(LecternBlock.FACING).rotateY().getHorizontalAngle();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-f2));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(67.5f));
            matrixStack.translate(0.0, -0.125, 0.0);
            this.field_217656_d.setBookState(0.0f, 0.1f, 0.9f, 1.2f);
            IVertexBuilder iVertexBuilder = EnchantmentTableTileEntityRenderer.TEXTURE_BOOK.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
            this.field_217656_d.renderAll(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((LecternTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

