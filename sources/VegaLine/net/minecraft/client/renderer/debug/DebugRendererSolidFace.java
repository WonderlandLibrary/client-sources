/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DebugRendererSolidFace
implements DebugRenderer.IDebugRenderer {
    private final Minecraft field_193851_a;

    public DebugRendererSolidFace(Minecraft p_i47478_1_) {
        this.field_193851_a = p_i47478_1_;
    }

    @Override
    public void render(float p_190060_1_, long p_190060_2_) {
        EntityPlayerSP entityplayer = Minecraft.player;
        double d0 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)p_190060_1_;
        double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)p_190060_1_;
        double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)p_190060_1_;
        World world = Minecraft.player.world;
        Iterable<BlockPos> iterable = BlockPos.func_191532_a(MathHelper.floor(entityplayer.posX - 6.0), MathHelper.floor(entityplayer.posY - 6.0), MathHelper.floor(entityplayer.posZ - 6.0), MathHelper.floor(entityplayer.posX + 6.0), MathHelper.floor(entityplayer.posY + 6.0), MathHelper.floor(entityplayer.posZ + 6.0));
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        for (BlockPos blockpos : iterable) {
            IBlockState iblockstate = world.getBlockState(blockpos);
            if (iblockstate.getBlock() == Blocks.AIR) continue;
            AxisAlignedBB axisalignedbb = iblockstate.getSelectedBoundingBox(world, blockpos).expandXyz(0.002).offset(-d0, -d1, -d2);
            double d3 = axisalignedbb.minX;
            double d4 = axisalignedbb.minY;
            double d5 = axisalignedbb.minZ;
            double d6 = axisalignedbb.maxX;
            double d7 = axisalignedbb.maxY;
            double d8 = axisalignedbb.maxZ;
            float f = 1.0f;
            float f1 = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.5f;
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.WEST) == BlockFaceShape.SOLID) {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder.pos(d3, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder.pos(d3, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder.pos(d3, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder.pos(d3, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator.draw();
            }
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.SOUTH) == BlockFaceShape.SOLID) {
                Tessellator tessellator1 = Tessellator.getInstance();
                BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
                bufferbuilder1.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder1.pos(d3, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder1.pos(d3, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder1.pos(d6, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder1.pos(d6, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator1.draw();
            }
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.EAST) == BlockFaceShape.SOLID) {
                Tessellator tessellator2 = Tessellator.getInstance();
                BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
                bufferbuilder2.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder2.pos(d6, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder2.pos(d6, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder2.pos(d6, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder2.pos(d6, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator2.draw();
            }
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.NORTH) == BlockFaceShape.SOLID) {
                Tessellator tessellator3 = Tessellator.getInstance();
                BufferBuilder bufferbuilder3 = tessellator3.getBuffer();
                bufferbuilder3.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder3.pos(d6, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder3.pos(d6, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder3.pos(d3, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder3.pos(d3, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator3.draw();
            }
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID) {
                Tessellator tessellator4 = Tessellator.getInstance();
                BufferBuilder bufferbuilder4 = tessellator4.getBuffer();
                bufferbuilder4.begin(5, DefaultVertexFormats.POSITION_COLOR);
                bufferbuilder4.pos(d3, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder4.pos(d6, d4, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder4.pos(d3, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                bufferbuilder4.pos(d6, d4, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                tessellator4.draw();
            }
            if (iblockstate.func_193401_d(world, blockpos, EnumFacing.UP) != BlockFaceShape.SOLID) continue;
            Tessellator tessellator5 = Tessellator.getInstance();
            BufferBuilder bufferbuilder5 = tessellator5.getBuffer();
            bufferbuilder5.begin(5, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder5.pos(d3, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
            bufferbuilder5.pos(d3, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
            bufferbuilder5.pos(d6, d7, d5).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
            bufferbuilder5.pos(d6, d7, d8).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
            tessellator5.draw();
        }
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

