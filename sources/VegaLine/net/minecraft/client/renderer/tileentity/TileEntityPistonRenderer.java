/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer
extends TileEntitySpecialRenderer<TileEntityPiston> {
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    @Override
    public void func_192841_a(TileEntityPiston p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        BlockPos blockpos = p_192841_1_.getPos();
        IBlockState iblockstate = p_192841_1_.getPistonState();
        Block block = iblockstate.getBlock();
        if (iblockstate.getMaterial() != Material.AIR && p_192841_1_.getProgress(p_192841_8_) < 1.0f) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(7425);
            } else {
                GlStateManager.shadeModel(7424);
            }
            bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
            bufferbuilder.setTranslation(p_192841_2_ - (double)blockpos.getX() + (double)p_192841_1_.getOffsetX(p_192841_8_), p_192841_4_ - (double)blockpos.getY() + (double)p_192841_1_.getOffsetY(p_192841_8_), p_192841_6_ - (double)blockpos.getZ() + (double)p_192841_1_.getOffsetZ(p_192841_8_));
            World world = this.getWorld();
            if (block == Blocks.PISTON_HEAD && p_192841_1_.getProgress(p_192841_8_) <= 0.25f) {
                iblockstate = iblockstate.withProperty(BlockPistonExtension.SHORT, true);
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
            } else if (p_192841_1_.shouldPistonHeadBeRendered() && !p_192841_1_.isExtending()) {
                BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = block == Blocks.STICKY_PISTON ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
                IBlockState iblockstate1 = Blocks.PISTON_HEAD.getDefaultState().withProperty(BlockPistonExtension.TYPE, blockpistonextension$enumpistontype).withProperty(BlockPistonExtension.FACING, iblockstate.getValue(BlockPistonBase.FACING));
                iblockstate1 = iblockstate1.withProperty(BlockPistonExtension.SHORT, p_192841_1_.getProgress(p_192841_8_) >= 0.5f);
                this.renderStateModel(blockpos, iblockstate1, bufferbuilder, world, true);
                bufferbuilder.setTranslation(p_192841_2_ - (double)blockpos.getX(), p_192841_4_ - (double)blockpos.getY(), p_192841_6_ - (double)blockpos.getZ());
                iblockstate = iblockstate.withProperty(BlockPistonBase.EXTENDED, true);
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
            } else {
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, false);
            }
            bufferbuilder.setTranslation(0.0, 0.0, 0.0);
            tessellator.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }

    private boolean renderStateModel(BlockPos p_188186_1_, IBlockState p_188186_2_, BufferBuilder p_188186_3_, World p_188186_4_, boolean p_188186_5_) {
        return this.blockRenderer.getBlockModelRenderer().renderModel(p_188186_4_, this.blockRenderer.getModelForState(p_188186_2_), p_188186_2_, p_188186_1_, p_188186_3_, p_188186_5_);
    }
}

