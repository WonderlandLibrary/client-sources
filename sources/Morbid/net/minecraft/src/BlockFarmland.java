package net.minecraft.src;

import java.util.*;

public class BlockFarmland extends Block
{
    private Icon field_94441_a;
    private Icon field_94440_b;
    
    protected BlockFarmland(final int par1) {
        super(par1, Material.ground);
        this.setTickRandomly(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return AxisAlignedBB.getAABBPool().getAABB(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
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
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? ((par2 > 0) ? this.field_94441_a : this.field_94440_b) : Block.dirt.getBlockTextureFromSide(par1);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!this.isWaterNearby(par1World, par2, par3, par4) && !par1World.canLightningStrikeAt(par2, par3 + 1, par4)) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if (var6 > 0) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 - 1, 2);
            }
            else if (!this.isCropsNearby(par1World, par2, par3, par4)) {
                par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
            }
        }
        else {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 7, 2);
        }
    }
    
    @Override
    public void onFallenUpon(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity, final float par6) {
        if (!par1World.isRemote && par1World.rand.nextFloat() < par6 - 0.5f) {
            if (!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                return;
            }
            par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
        }
    }
    
    private boolean isCropsNearby(final World par1World, final int par2, final int par3, final int par4) {
        final byte var5 = 0;
        for (int var6 = par2 - var5; var6 <= par2 + var5; ++var6) {
            for (int var7 = par4 - var5; var7 <= par4 + var5; ++var7) {
                final int var8 = par1World.getBlockId(var6, par3 + 1, var7);
                if (var8 == Block.crops.blockID || var8 == Block.melonStem.blockID || var8 == Block.pumpkinStem.blockID || var8 == Block.potato.blockID || var8 == Block.carrot.blockID) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isWaterNearby(final World par1World, final int par2, final int par3, final int par4) {
        for (int var5 = par2 - 4; var5 <= par2 + 4; ++var5) {
            for (int var6 = par3; var6 <= par3 + 1; ++var6) {
                for (int var7 = par4 - 4; var7 <= par4 + 4; ++var7) {
                    if (par1World.getBlockMaterial(var5, var6, var7) == Material.water) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        final Material var6 = par1World.getBlockMaterial(par2, par3 + 1, par4);
        if (var6.isSolid()) {
            par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.dirt.idDropped(0, par2Random, par3);
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Block.dirt.blockID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94441_a = par1IconRegister.registerIcon("farmland_wet");
        this.field_94440_b = par1IconRegister.registerIcon("farmland_dry");
    }
}
