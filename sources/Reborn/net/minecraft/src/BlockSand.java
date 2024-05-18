package net.minecraft.src;

import java.util.*;

public class BlockSand extends Block
{
    public static boolean fallInstantly;
    
    static {
        BlockSand.fallInstantly = false;
    }
    
    public BlockSand(final int par1) {
        super(par1, Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public BlockSand(final int par1, final Material par2Material) {
        super(par1, par2Material);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            this.tryToFall(par1World, par2, par3, par4);
        }
    }
    
    private void tryToFall(final World par1World, final int par2, int par3, final int par4) {
        if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0) {
            final byte var8 = 32;
            if (!BlockSand.fallInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
                if (!par1World.isRemote) {
                    final EntityFallingSand var9 = new EntityFallingSand(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, this.blockID, par1World.getBlockMetadata(par2, par3, par4));
                    this.onStartFalling(var9);
                    par1World.spawnEntityInWorld(var9);
                }
            }
            else {
                par1World.setBlockToAir(par2, par3, par4);
                while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0) {
                    --par3;
                }
                if (par3 > 0) {
                    par1World.setBlock(par2, par3, par4, this.blockID);
                }
            }
        }
    }
    
    protected void onStartFalling(final EntityFallingSand par1EntityFallingSand) {
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 2;
    }
    
    public static boolean canFallBelow(final World par0World, final int par1, final int par2, final int par3) {
        final int var4 = par0World.getBlockId(par1, par2, par3);
        if (var4 == 0) {
            return true;
        }
        if (var4 == Block.fire.blockID) {
            return true;
        }
        final Material var5 = Block.blocksList[var4].blockMaterial;
        return var5 == Material.water || var5 == Material.lava;
    }
    
    public void onFinishFalling(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
}
