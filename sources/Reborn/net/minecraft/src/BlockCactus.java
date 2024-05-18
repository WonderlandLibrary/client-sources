package net.minecraft.src;

import java.util.*;

public class BlockCactus extends Block
{
    private Icon cactusTopIcon;
    private Icon cactusBottomIcon;
    
    protected BlockCactus(final int par1) {
        super(par1, Material.cactus);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.isAirBlock(par2, par3 + 1, par4)) {
            int var6;
            for (var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == this.blockID; ++var6) {}
            if (var6 < 3) {
                final int var7 = par1World.getBlockMetadata(par2, par3, par4);
                if (var7 == 15) {
                    par1World.setBlock(par2, par3 + 1, par4, this.blockID);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
                    this.onNeighborBlockChange(par1World, par2, par3 + 1, par4, this.blockID);
                }
                else {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1, 4);
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final float var5 = 0.0625f;
        return AxisAlignedBB.getAABBPool().getAABB(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final float var5 = 0.0625f;
        return AxisAlignedBB.getAABBPool().getAABB(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1, par4 + 1 - var5);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.cactusTopIcon : ((par1 == 0) ? this.cactusBottomIcon : this.blockIcon);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 13;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            par1World.destroyBlock(par2, par3, par4, true);
        }
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) {
            return false;
        }
        if (par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) {
            return false;
        }
        if (par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) {
            return false;
        }
        if (par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) {
            return false;
        }
        final int var5 = par1World.getBlockId(par2, par3 - 1, par4);
        return var5 == Block.cactus.blockID || var5 == Block.sand.blockID;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        par5Entity.attackEntityFrom(DamageSource.cactus, 1);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("cactus_side");
        this.cactusTopIcon = par1IconRegister.registerIcon("cactus_top");
        this.cactusBottomIcon = par1IconRegister.registerIcon("cactus_bottom");
    }
}
