/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BedTileEntityRenderer
extends TileEntityRenderer<BedTileEntity> {
    private final ModelRenderer field_228843_a_;
    private final ModelRenderer field_228844_c_;
    private final ModelRenderer[] field_228845_d_ = new ModelRenderer[4];

    public BedTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        this.field_228843_a_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228843_a_.addBox(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 6.0f, 0.0f);
        this.field_228844_c_ = new ModelRenderer(64, 64, 0, 22);
        this.field_228844_c_.addBox(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 6.0f, 0.0f);
        this.field_228845_d_[0] = new ModelRenderer(64, 64, 50, 0);
        this.field_228845_d_[1] = new ModelRenderer(64, 64, 50, 6);
        this.field_228845_d_[2] = new ModelRenderer(64, 64, 50, 12);
        this.field_228845_d_[3] = new ModelRenderer(64, 64, 50, 18);
        this.field_228845_d_[0].addBox(0.0f, 6.0f, -16.0f, 3.0f, 3.0f, 3.0f);
        this.field_228845_d_[1].addBox(0.0f, 6.0f, 0.0f, 3.0f, 3.0f, 3.0f);
        this.field_228845_d_[2].addBox(-16.0f, 6.0f, -16.0f, 3.0f, 3.0f, 3.0f);
        this.field_228845_d_[3].addBox(-16.0f, 6.0f, 0.0f, 3.0f, 3.0f, 3.0f);
        this.field_228845_d_[0].rotateAngleX = 1.5707964f;
        this.field_228845_d_[1].rotateAngleX = 1.5707964f;
        this.field_228845_d_[2].rotateAngleX = 1.5707964f;
        this.field_228845_d_[3].rotateAngleX = 1.5707964f;
        this.field_228845_d_[0].rotateAngleZ = 0.0f;
        this.field_228845_d_[1].rotateAngleZ = 1.5707964f;
        this.field_228845_d_[2].rotateAngleZ = 4.712389f;
        this.field_228845_d_[3].rotateAngleZ = (float)Math.PI;
    }

    @Override
    public void render(BedTileEntity bedTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        RenderMaterial renderMaterial = Atlases.BED_TEXTURES[bedTileEntity.getColor().getId()];
        World world = bedTileEntity.getWorld();
        if (world != null) {
            BlockState blockState = bedTileEntity.getBlockState();
            TileEntityMerger.ICallbackWrapper<BedTileEntity> iCallbackWrapper = TileEntityMerger.func_226924_a_(TileEntityType.BED, BedBlock::getMergeType, BedBlock::getFootDirection, ChestBlock.FACING, blockState, world, bedTileEntity.getPos(), BedTileEntityRenderer::lambda$render$0);
            int n3 = ((Int2IntFunction)iCallbackWrapper.apply(new DualBrightnessCallback())).get(n);
            this.func_228847_a_(matrixStack, iRenderTypeBuffer, blockState.get(BedBlock.PART) == BedPart.HEAD, blockState.get(BedBlock.HORIZONTAL_FACING), renderMaterial, n3, n2, true);
        } else {
            this.func_228847_a_(matrixStack, iRenderTypeBuffer, true, Direction.SOUTH, renderMaterial, n, n2, true);
            this.func_228847_a_(matrixStack, iRenderTypeBuffer, false, Direction.SOUTH, renderMaterial, n, n2, false);
        }
    }

    private void func_228847_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, boolean bl, Direction direction, RenderMaterial renderMaterial, int n, int n2, boolean bl2) {
        this.field_228843_a_.showModel = bl;
        this.field_228844_c_.showModel = !bl;
        this.field_228845_d_[0].showModel = !bl;
        this.field_228845_d_[1].showModel = bl;
        this.field_228845_d_[2].showModel = !bl;
        this.field_228845_d_[3].showModel = bl;
        matrixStack.push();
        matrixStack.translate(0.0, 0.5625, bl2 ? -1.0 : 0.0);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f + direction.getHorizontalAngle()));
        matrixStack.translate(-0.5, -0.5, -0.5);
        IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
        this.field_228843_a_.render(matrixStack, iVertexBuilder, n, n2);
        this.field_228844_c_.render(matrixStack, iVertexBuilder, n, n2);
        this.field_228845_d_[0].render(matrixStack, iVertexBuilder, n, n2);
        this.field_228845_d_[1].render(matrixStack, iVertexBuilder, n, n2);
        this.field_228845_d_[2].render(matrixStack, iVertexBuilder, n, n2);
        this.field_228845_d_[3].render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.pop();
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((BedTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    private static boolean lambda$render$0(IWorld iWorld, BlockPos blockPos) {
        return true;
    }
}

