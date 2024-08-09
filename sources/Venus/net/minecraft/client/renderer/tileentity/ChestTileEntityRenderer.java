/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ChestTileEntityRenderer<T extends TileEntity>
extends TileEntityRenderer<T> {
    private final ModelRenderer singleLid;
    private final ModelRenderer singleBottom;
    private final ModelRenderer singleLatch;
    private final ModelRenderer rightLid;
    private final ModelRenderer rightBottom;
    private final ModelRenderer rightLatch;
    private final ModelRenderer leftLid;
    private final ModelRenderer leftBottom;
    private final ModelRenderer leftLatch;
    private boolean isChristmas;

    public ChestTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
            this.isChristmas = true;
        }
        this.singleBottom = new ModelRenderer(64, 64, 0, 19);
        this.singleBottom.addBox(1.0f, 0.0f, 1.0f, 14.0f, 10.0f, 14.0f, 0.0f);
        this.singleLid = new ModelRenderer(64, 64, 0, 0);
        this.singleLid.addBox(1.0f, 0.0f, 0.0f, 14.0f, 5.0f, 14.0f, 0.0f);
        this.singleLid.rotationPointY = 9.0f;
        this.singleLid.rotationPointZ = 1.0f;
        this.singleLatch = new ModelRenderer(64, 64, 0, 0);
        this.singleLatch.addBox(7.0f, -1.0f, 15.0f, 2.0f, 4.0f, 1.0f, 0.0f);
        this.singleLatch.rotationPointY = 8.0f;
        this.rightBottom = new ModelRenderer(64, 64, 0, 19);
        this.rightBottom.addBox(1.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f, 0.0f);
        this.rightLid = new ModelRenderer(64, 64, 0, 0);
        this.rightLid.addBox(1.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f, 0.0f);
        this.rightLid.rotationPointY = 9.0f;
        this.rightLid.rotationPointZ = 1.0f;
        this.rightLatch = new ModelRenderer(64, 64, 0, 0);
        this.rightLatch.addBox(15.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.rightLatch.rotationPointY = 8.0f;
        this.leftBottom = new ModelRenderer(64, 64, 0, 19);
        this.leftBottom.addBox(0.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f, 0.0f);
        this.leftLid = new ModelRenderer(64, 64, 0, 0);
        this.leftLid.addBox(0.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f, 0.0f);
        this.leftLid.rotationPointY = 9.0f;
        this.leftLid.rotationPointZ = 1.0f;
        this.leftLatch = new ModelRenderer(64, 64, 0, 0);
        this.leftLatch.addBox(0.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.leftLatch.rotationPointY = 8.0f;
    }

    @Override
    public void render(T t, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        World world = ((TileEntity)t).getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? ((TileEntity)t).getBlockState() : (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock abstractChestBlock = (AbstractChestBlock)block;
            boolean bl2 = chestType != ChestType.SINGLE;
            matrixStack.push();
            float f2 = blockState.get(ChestBlock.FACING).getHorizontalAngle();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-f2));
            matrixStack.translate(-0.5, -0.5, -0.5);
            TileEntityMerger.ICallbackWrapper<Object> iCallbackWrapper = bl ? abstractChestBlock.combine(blockState, world, ((TileEntity)t).getPos(), false) : TileEntityMerger.ICallback::func_225537_b_;
            float f3 = iCallbackWrapper.apply(ChestBlock.getLidRotationCallback((IChestLid)t)).get(f);
            f3 = 1.0f - f3;
            f3 = 1.0f - f3 * f3 * f3;
            int n3 = ((Int2IntFunction)iCallbackWrapper.apply(new DualBrightnessCallback())).applyAsInt(n);
            RenderMaterial renderMaterial = Atlases.getChestMaterial(t, chestType, this.isChristmas);
            IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(iRenderTypeBuffer, RenderType::getEntityCutout);
            if (bl2) {
                if (chestType == ChestType.LEFT) {
                    this.renderModels(matrixStack, iVertexBuilder, this.leftLid, this.leftLatch, this.leftBottom, f3, n3, n2);
                } else {
                    this.renderModels(matrixStack, iVertexBuilder, this.rightLid, this.rightLatch, this.rightBottom, f3, n3, n2);
                }
            } else {
                this.renderModels(matrixStack, iVertexBuilder, this.singleLid, this.singleLatch, this.singleBottom, f3, n3, n2);
            }
            matrixStack.pop();
        }
    }

    private void renderModels(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, ModelRenderer modelRenderer, ModelRenderer modelRenderer2, ModelRenderer modelRenderer3, float f, int n, int n2) {
        modelRenderer2.rotateAngleX = modelRenderer.rotateAngleX = -(f * 1.5707964f);
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2);
        modelRenderer2.render(matrixStack, iVertexBuilder, n, n2);
        modelRenderer3.render(matrixStack, iVertexBuilder, n, n2);
    }
}

