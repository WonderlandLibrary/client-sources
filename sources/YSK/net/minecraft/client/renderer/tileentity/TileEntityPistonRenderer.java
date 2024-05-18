package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston>
{
    private final BlockRendererDispatcher blockRenderer;
    
    @Override
    public void renderTileEntityAt(final TileEntityPiston tileEntityPiston, final double n, final double n2, final double n3, final float n4, final int n5) {
        final BlockPos pos = tileEntityPiston.getPos();
        final IBlockState pistonState = tileEntityPiston.getPistonState();
        final Block block = pistonState.getBlock();
        if (block.getMaterial() != Material.air && tileEntityPiston.getProgress(n4) < 1.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.blendFunc(124 + 186 + 151 + 309, 448 + 431 - 801 + 693);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(1221 + 3131 + 1934 + 1139);
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
            else {
                GlStateManager.shadeModel(1087 + 2095 - 2525 + 6767);
            }
            worldRenderer.begin(0xC3 ^ 0xC4, DefaultVertexFormats.BLOCK);
            worldRenderer.setTranslation((float)n - pos.getX() + tileEntityPiston.getOffsetX(n4), (float)n2 - pos.getY() + tileEntityPiston.getOffsetY(n4), (float)n3 - pos.getZ() + tileEntityPiston.getOffsetZ(n4));
            final World world = this.getWorld();
            if (block == Blocks.piston_head && tileEntityPiston.getProgress(n4) < 0.5f) {
                final IBlockState withProperty = pistonState.withProperty((IProperty<Comparable>)BlockPistonExtension.SHORT, " ".length() != 0);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(withProperty, world, pos), withProperty, pos, worldRenderer, " ".length() != 0);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else if (tileEntityPiston.shouldPistonHeadBeRendered() && !tileEntityPiston.isExtending()) {
                BlockPistonExtension.EnumPistonType enumPistonType;
                if (block == Blocks.sticky_piston) {
                    enumPistonType = BlockPistonExtension.EnumPistonType.STICKY;
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
                else {
                    enumPistonType = BlockPistonExtension.EnumPistonType.DEFAULT;
                }
                final IBlockState withProperty2 = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.TYPE, enumPistonType).withProperty((IProperty<Comparable>)BlockPistonExtension.FACING, (EnumFacing)pistonState.getValue((IProperty<V>)BlockPistonBase.FACING));
                final PropertyBool short1 = BlockPistonExtension.SHORT;
                int n6;
                if (tileEntityPiston.getProgress(n4) >= 0.5f) {
                    n6 = " ".length();
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                }
                final IBlockState withProperty3 = withProperty2.withProperty((IProperty<Comparable>)short1, n6 != 0);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(withProperty3, world, pos), withProperty3, pos, worldRenderer, " ".length() != 0);
                worldRenderer.setTranslation((float)n - pos.getX(), (float)n2 - pos.getY(), (float)n3 - pos.getZ());
                pistonState.withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, " ".length() != 0);
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(pistonState, world, pos), pistonState, pos, worldRenderer, " ".length() != 0);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(pistonState, world, pos), pistonState, pos, worldRenderer, "".length() != 0);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            instance.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityPiston)tileEntity, n, n2, n3, n4, n5);
    }
    
    public TileEntityPistonRenderer() {
        this.blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
    }
}
