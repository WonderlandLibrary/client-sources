/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureTileEntityRenderer
extends TileEntityRenderer<StructureBlockTileEntity> {
    public StructureTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(StructureBlockTileEntity structureBlockTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        if (Minecraft.getInstance().player.canUseCommandBlock() || Minecraft.getInstance().player.isSpectator()) {
            BlockPos blockPos = structureBlockTileEntity.getPosition();
            BlockPos blockPos2 = structureBlockTileEntity.getStructureSize();
            if (blockPos2.getX() >= 1 && blockPos2.getY() >= 1 && blockPos2.getZ() >= 1 && (structureBlockTileEntity.getMode() == StructureMode.SAVE || structureBlockTileEntity.getMode() == StructureMode.LOAD)) {
                double d;
                double d2;
                double d3;
                double d4;
                double d5 = blockPos.getX();
                double d6 = blockPos.getZ();
                double d7 = blockPos.getY();
                double d8 = d7 + (double)blockPos2.getY();
                double d9 = switch (structureBlockTileEntity.getMirror()) {
                    case Mirror.LEFT_RIGHT -> {
                        d4 = blockPos2.getX();
                        yield -blockPos2.getZ();
                    }
                    case Mirror.FRONT_BACK -> {
                        d4 = -blockPos2.getX();
                        yield blockPos2.getZ();
                    }
                    default -> {
                        d4 = blockPos2.getX();
                        yield blockPos2.getZ();
                    }
                };
                double d10 = switch (structureBlockTileEntity.getRotation()) {
                    case Rotation.CLOCKWISE_90 -> {
                        d3 = d9 < 0.0 ? d5 : d5 + 1.0;
                        d2 = d4 < 0.0 ? d6 + 1.0 : d6;
                        d = d3 - d9;
                        yield d2 + d4;
                    }
                    case Rotation.CLOCKWISE_180 -> {
                        d3 = d4 < 0.0 ? d5 : d5 + 1.0;
                        d2 = d9 < 0.0 ? d6 : d6 + 1.0;
                        d = d3 - d4;
                        yield d2 - d9;
                    }
                    case Rotation.COUNTERCLOCKWISE_90 -> {
                        d3 = d9 < 0.0 ? d5 + 1.0 : d5;
                        d2 = d4 < 0.0 ? d6 : d6 + 1.0;
                        d = d3 + d9;
                        yield d2 - d4;
                    }
                    default -> {
                        d3 = d4 < 0.0 ? d5 + 1.0 : d5;
                        d2 = d9 < 0.0 ? d6 + 1.0 : d6;
                        d = d3 + d4;
                        yield d2 + d9;
                    }
                };
                float f2 = 1.0f;
                float f3 = 0.9f;
                float f4 = 0.5f;
                IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getLines());
                if (structureBlockTileEntity.getMode() == StructureMode.SAVE || structureBlockTileEntity.showsBoundingBox()) {
                    WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, d3, d7, d2, d, d8, d10, 0.9f, 0.9f, 0.9f, 1.0f, 0.5f, 0.5f, 0.5f);
                }
                if (structureBlockTileEntity.getMode() == StructureMode.SAVE && structureBlockTileEntity.showsAir()) {
                    this.func_228880_a_(structureBlockTileEntity, iVertexBuilder, blockPos, true, matrixStack);
                    this.func_228880_a_(structureBlockTileEntity, iVertexBuilder, blockPos, false, matrixStack);
                }
            }
        }
    }

    private void func_228880_a_(StructureBlockTileEntity structureBlockTileEntity, IVertexBuilder iVertexBuilder, BlockPos blockPos, boolean bl, MatrixStack matrixStack) {
        World world = structureBlockTileEntity.getWorld();
        BlockPos blockPos2 = structureBlockTileEntity.getPos();
        BlockPos blockPos3 = blockPos2.add(blockPos);
        for (BlockPos blockPos4 : BlockPos.getAllInBoxMutable(blockPos3, blockPos3.add(structureBlockTileEntity.getStructureSize()).add(-1, -1, -1))) {
            BlockState blockState = world.getBlockState(blockPos4);
            boolean bl2 = blockState.isAir();
            boolean bl3 = blockState.isIn(Blocks.STRUCTURE_VOID);
            if (!bl2 && !bl3) continue;
            float f = bl2 ? 0.05f : 0.0f;
            double d = (float)(blockPos4.getX() - blockPos2.getX()) + 0.45f - f;
            double d2 = (float)(blockPos4.getY() - blockPos2.getY()) + 0.45f - f;
            double d3 = (float)(blockPos4.getZ() - blockPos2.getZ()) + 0.45f - f;
            double d4 = (float)(blockPos4.getX() - blockPos2.getX()) + 0.55f + f;
            double d5 = (float)(blockPos4.getY() - blockPos2.getY()) + 0.55f + f;
            double d6 = (float)(blockPos4.getZ() - blockPos2.getZ()) + 0.55f + f;
            if (bl) {
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, d, d2, d3, d4, d5, d6, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f);
                continue;
            }
            if (bl2) {
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, d, d2, d3, d4, d5, d6, 0.5f, 0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f);
                continue;
            }
            WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, d, d2, d3, d4, d5, d6, 1.0f, 0.25f, 0.25f, 1.0f, 1.0f, 0.25f, 0.25f);
        }
    }

    @Override
    public boolean isGlobalRenderer(StructureBlockTileEntity structureBlockTileEntity) {
        return false;
    }

    @Override
    public boolean isGlobalRenderer(TileEntity tileEntity) {
        return this.isGlobalRenderer((StructureBlockTileEntity)tileEntity);
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((StructureBlockTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

