package net.minecraft.src;

import java.util.*;

public class BlockMobSpawner extends BlockContainer
{
    protected BlockMobSpawner(final int par1) {
        super(par1, Material.rock);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityMobSpawner();
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        final int var8 = 15 + par1World.rand.nextInt(15) + par1World.rand.nextInt(15);
        this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
}
