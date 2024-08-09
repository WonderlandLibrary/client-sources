/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonTileEntityRenderer
extends TileEntityRenderer<PistonTileEntity> {
    private final BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();

    public PistonTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(PistonTileEntity pistonTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        World world = pistonTileEntity.getWorld();
        if (world != null) {
            BlockPos blockPos = pistonTileEntity.getPos().offset(pistonTileEntity.getMotionDirection().getOpposite());
            BlockState blockState = pistonTileEntity.getPistonState();
            if (!blockState.isAir()) {
                BlockModelRenderer.enableCache();
                matrixStack.push();
                matrixStack.translate(pistonTileEntity.getOffsetX(f), pistonTileEntity.getOffsetY(f), pistonTileEntity.getOffsetZ(f));
                if (blockState.isIn(Blocks.PISTON_HEAD) && pistonTileEntity.getProgress(f) <= 4.0f) {
                    blockState = (BlockState)blockState.with(PistonHeadBlock.SHORT, pistonTileEntity.getProgress(f) <= 0.5f);
                    this.func_228876_a_(blockPos, blockState, matrixStack, iRenderTypeBuffer, world, false, n2);
                } else if (pistonTileEntity.shouldPistonHeadBeRendered() && !pistonTileEntity.isExtending()) {
                    PistonType pistonType = blockState.isIn(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT;
                    BlockState blockState2 = (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.TYPE, pistonType)).with(PistonHeadBlock.FACING, blockState.get(PistonBlock.FACING));
                    blockState2 = (BlockState)blockState2.with(PistonHeadBlock.SHORT, pistonTileEntity.getProgress(f) >= 0.5f);
                    this.func_228876_a_(blockPos, blockState2, matrixStack, iRenderTypeBuffer, world, false, n2);
                    BlockPos blockPos2 = blockPos.offset(pistonTileEntity.getMotionDirection());
                    matrixStack.pop();
                    matrixStack.push();
                    blockState = (BlockState)blockState.with(PistonBlock.EXTENDED, true);
                    this.func_228876_a_(blockPos2, blockState, matrixStack, iRenderTypeBuffer, world, true, n2);
                } else {
                    this.func_228876_a_(blockPos, blockState, matrixStack, iRenderTypeBuffer, world, false, n2);
                }
                matrixStack.pop();
                BlockModelRenderer.disableCache();
            }
        }
    }

    private void func_228876_a_(BlockPos blockPos, BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, World world, boolean bl, int n) {
        RenderType renderType = RenderTypeLookup.func_239221_b_(blockState);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(renderType);
        this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelForState(blockState), blockState, blockPos, matrixStack, iVertexBuilder, bl, new Random(), blockState.getPositionRandom(blockPos), n);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((PistonTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

