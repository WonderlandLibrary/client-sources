package net.minecraft.src;

import java.util.*;

public class BlockWall extends Block
{
    public static final String[] types;
    
    static {
        types = new String[] { "normal", "mossy" };
    }
    
    public BlockWall(final int par1, final Block par2Block) {
        super(par1, par2Block.blockMaterial);
        this.setHardness(par2Block.blockHardness);
        this.setResistance(par2Block.blockResistance / 3.0f);
        this.setStepSound(par2Block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par2 == 1) ? Block.cobblestoneMossy.getBlockTextureFromSide(par1) : Block.cobblestone.getBlockTextureFromSide(par1);
    }
    
    @Override
    public int getRenderType() {
        return 32;
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
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final boolean var5 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 - 1);
        final boolean var6 = this.canConnectWallTo(par1IBlockAccess, par2, par3, par4 + 1);
        final boolean var7 = this.canConnectWallTo(par1IBlockAccess, par2 - 1, par3, par4);
        final boolean var8 = this.canConnectWallTo(par1IBlockAccess, par2 + 1, par3, par4);
        float var9 = 0.25f;
        float var10 = 0.75f;
        float var11 = 0.25f;
        float var12 = 0.75f;
        float var13 = 1.0f;
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
        if (var5 && var6 && !var7 && !var8) {
            var13 = 0.8125f;
            var9 = 0.3125f;
            var10 = 0.6875f;
        }
        else if (!var5 && !var6 && var7 && var8) {
            var13 = 0.8125f;
            var11 = 0.3125f;
            var12 = 0.6875f;
        }
        this.setBlockBounds(var9, 0.0f, var11, var10, var13, var12);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        this.maxY = 1.5;
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    public boolean canConnectWallTo(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);
        if (var5 != this.blockID && var5 != Block.fenceGate.blockID) {
            final Block var6 = Block.blocksList[var5];
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() && var6.blockMaterial != Material.pumpkin;
        }
        return true;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return par5 != 0 || super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
