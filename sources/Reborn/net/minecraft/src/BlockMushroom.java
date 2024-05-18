package net.minecraft.src;

import java.util.*;

public class BlockMushroom extends BlockFlower
{
    private final String field_94374_a;
    
    protected BlockMushroom(final int par1, final String par2Str) {
        super(par1);
        this.field_94374_a = par2Str;
        final float var3 = 0.2f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var3 * 2.0f, 0.5f + var3);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World par1World, int par2, int par3, int par4, final Random par5Random) {
        if (par5Random.nextInt(25) == 0) {
            final byte var6 = 4;
            int var7 = 5;
            for (int var8 = par2 - var6; var8 <= par2 + var6; ++var8) {
                for (int var9 = par4 - var6; var9 <= par4 + var6; ++var9) {
                    for (int var10 = par3 - 1; var10 <= par3 + 1; ++var10) {
                        if (par1World.getBlockId(var8, var10, var9) == this.blockID && --var7 <= 0) {
                            return;
                        }
                    }
                }
            }
            int var8 = par2 + par5Random.nextInt(3) - 1;
            int var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
            int var10 = par4 + par5Random.nextInt(3) - 1;
            for (int var11 = 0; var11 < 4; ++var11) {
                if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10)) {
                    par2 = var8;
                    par3 = var9;
                    par4 = var10;
                }
                var8 = par2 + par5Random.nextInt(3) - 1;
                var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
                var10 = par4 + par5Random.nextInt(3) - 1;
            }
            if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10)) {
                par1World.setBlock(var8, var9, var10, this.blockID);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return Block.opaqueCubeLookup[par1];
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        if (par3 >= 0 && par3 < 256) {
            final int var5 = par1World.getBlockId(par2, par3 - 1, par4);
            return var5 == Block.mycelium.blockID || (par1World.getFullBlockLightValue(par2, par3, par4) < 13 && this.canThisPlantGrowOnThisBlockID(var5));
        }
        return false;
    }
    
    public boolean fertilizeMushroom(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlockToAir(par2, par3, par4);
        WorldGenBigMushroom var7 = null;
        if (this.blockID == Block.mushroomBrown.blockID) {
            var7 = new WorldGenBigMushroom(0);
        }
        else if (this.blockID == Block.mushroomRed.blockID) {
            var7 = new WorldGenBigMushroom(1);
        }
        if (var7 != null && var7.generate(par1World, par5Random, par2, par3, par4)) {
            return true;
        }
        par1World.setBlock(par2, par3, par4, this.blockID, var6, 3);
        return false;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.field_94374_a);
    }
}
