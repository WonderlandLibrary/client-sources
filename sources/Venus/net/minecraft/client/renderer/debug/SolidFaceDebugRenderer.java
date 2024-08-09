/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public class SolidFaceDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;

    public SolidFaceDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        World world = this.minecraft.player.world;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        BlockPos blockPos = new BlockPos(d, d2, d3);
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-6, -6, -6), blockPos.add(6, 6, 6))) {
            BlockState blockState = world.getBlockState(blockPos2);
            if (blockState.isIn(Blocks.AIR)) continue;
            VoxelShape voxelShape = blockState.getShape(world, blockPos2);
            for (AxisAlignedBB axisAlignedBB : voxelShape.toBoundingBoxList()) {
                BufferBuilder bufferBuilder;
                Tessellator tessellator;
                AxisAlignedBB axisAlignedBB2 = axisAlignedBB.offset(blockPos2).grow(0.002).offset(-d, -d2, -d3);
                double d4 = axisAlignedBB2.minX;
                double d5 = axisAlignedBB2.minY;
                double d6 = axisAlignedBB2.minZ;
                double d7 = axisAlignedBB2.maxX;
                double d8 = axisAlignedBB2.maxY;
                double d9 = axisAlignedBB2.maxZ;
                float f = 1.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.5f;
                if (blockState.isSolidSide(world, blockPos2, Direction.WEST)) {
                    tessellator = Tessellator.getInstance();
                    bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    bufferBuilder.pos(d4, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    tessellator.draw();
                }
                if (blockState.isSolidSide(world, blockPos2, Direction.SOUTH)) {
                    tessellator = Tessellator.getInstance();
                    bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    bufferBuilder.pos(d4, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    tessellator.draw();
                }
                if (blockState.isSolidSide(world, blockPos2, Direction.EAST)) {
                    tessellator = Tessellator.getInstance();
                    bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    bufferBuilder.pos(d7, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    tessellator.draw();
                }
                if (blockState.isSolidSide(world, blockPos2, Direction.NORTH)) {
                    tessellator = Tessellator.getInstance();
                    bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    bufferBuilder.pos(d7, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    tessellator.draw();
                }
                if (blockState.isSolidSide(world, blockPos2, Direction.DOWN)) {
                    tessellator = Tessellator.getInstance();
                    bufferBuilder = tessellator.getBuffer();
                    bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    bufferBuilder.pos(d4, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d5, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d4, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d7, d5, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    tessellator.draw();
                }
                if (!blockState.isSolidSide(world, blockPos2, Direction.UP)) continue;
                tessellator = Tessellator.getInstance();
                bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferBuilder.pos(d4, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferBuilder.pos(d4, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferBuilder.pos(d7, d8, d6).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferBuilder.pos(d7, d8, d9).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator.draw();
            }
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}

