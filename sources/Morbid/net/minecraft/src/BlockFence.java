package net.minecraft.src;

import java.util.*;

public class BlockFence extends Block
{
    private final String field_94464_a;
    
    public BlockFence(final int par1, final String par2Str, final Material par3Material) {
        super(par1, par3Material);
        this.field_94464_a = par2Str;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        final boolean var8 = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
        final boolean var9 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
        final boolean var10 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
        final boolean var11 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
        float var12 = 0.375f;
        float var13 = 0.625f;
        float var14 = 0.375f;
        float var15 = 0.625f;
        if (var8) {
            var14 = 0.0f;
        }
        if (var9) {
            var15 = 1.0f;
        }
        if (var8 || var9) {
            this.setBlockBounds(var12, 0.0f, var14, var13, 1.5f, var15);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        var14 = 0.375f;
        var15 = 0.625f;
        if (var10) {
            var12 = 0.0f;
        }
        if (var11) {
            var13 = 1.0f;
        }
        if (var10 || var11 || (!var8 && !var9)) {
            this.setBlockBounds(var12, 0.0f, var14, var13, 1.5f, var15);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        if (var8) {
            var14 = 0.0f;
        }
        if (var9) {
            var15 = 1.0f;
        }
        this.setBlockBounds(var12, 0.0f, var14, var13, 1.0f, var15);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final boolean var5 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
        final boolean var6 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
        final boolean var7 = this.canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
        final boolean var8 = this.canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
        float var9 = 0.375f;
        float var10 = 0.625f;
        float var11 = 0.375f;
        float var12 = 0.625f;
        if (var5) {
            var11 = 0.0f;
        }
        if (var6) {
            var12 = 1.0f;
        }
        if (var7) {
            var9 = 0.0f;
        }
        if (var8) {
            var10 = 1.0f;
        }
        this.setBlockBounds(var9, 0.0f, var11, var10, 1.0f, var12);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 11;
    }
    
    public boolean canConnectFenceTo(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);
        if (var5 != this.blockID && var5 != Block.fenceGate.blockID) {
            final Block var6 = Block.blocksList[var5];
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() && var6.blockMaterial != Material.pumpkin;
        }
        return true;
    }
    
    public static boolean isIdAFence(final int par0) {
        return par0 == Block.fence.blockID || par0 == Block.netherFence.blockID;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.field_94464_a);
    }
}
