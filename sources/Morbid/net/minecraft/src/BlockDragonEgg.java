package net.minecraft.src;

import java.util.*;

public class BlockDragonEgg extends Block
{
    public BlockDragonEgg(final int par1) {
        super(par1, Material.dragonEgg);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
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
        this.fallIfPossible(par1World, par2, par3, par4);
    }
    
    private void fallIfPossible(final World par1World, final int par2, int par3, final int par4) {
        if (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0) {
            final byte var5 = 32;
            if (!BlockSand.fallInstantly && par1World.checkChunksExist(par2 - var5, par3 - var5, par4 - var5, par2 + var5, par3 + var5, par4 + var5)) {
                final EntityFallingSand var6 = new EntityFallingSand(par1World, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, this.blockID);
                par1World.spawnEntityInWorld(var6);
            }
            else {
                par1World.setBlockToAir(par2, par3, par4);
                while (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0) {
                    --par3;
                }
                if (par3 > 0) {
                    par1World.setBlock(par2, par3, par4, this.blockID, 0, 2);
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        this.teleportNearby(par1World, par2, par3, par4);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        this.teleportNearby(par1World, par2, par3, par4);
    }
    
    private void teleportNearby(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
            for (int var5 = 0; var5 < 1000; ++var5) {
                final int var6 = par2 + par1World.rand.nextInt(16) - par1World.rand.nextInt(16);
                final int var7 = par3 + par1World.rand.nextInt(8) - par1World.rand.nextInt(8);
                final int var8 = par4 + par1World.rand.nextInt(16) - par1World.rand.nextInt(16);
                if (par1World.getBlockId(var6, var7, var8) == 0) {
                    if (!par1World.isRemote) {
                        par1World.setBlock(var6, var7, var8, this.blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
                        par1World.setBlockToAir(par2, par3, par4);
                    }
                    else {
                        final short var9 = 128;
                        for (int var10 = 0; var10 < var9; ++var10) {
                            final double var11 = par1World.rand.nextDouble();
                            final float var12 = (par1World.rand.nextFloat() - 0.5f) * 0.2f;
                            final float var13 = (par1World.rand.nextFloat() - 0.5f) * 0.2f;
                            final float var14 = (par1World.rand.nextFloat() - 0.5f) * 0.2f;
                            final double var15 = var6 + (par2 - var6) * var11 + (par1World.rand.nextDouble() - 0.5) * 1.0 + 0.5;
                            final double var16 = var7 + (par3 - var7) * var11 + par1World.rand.nextDouble() * 1.0 - 0.5;
                            final double var17 = var8 + (par4 - var8) * var11 + (par1World.rand.nextDouble() - 0.5) * 1.0 + 0.5;
                            par1World.spawnParticle("portal", var15, var16, var17, var12, var13, var14);
                        }
                    }
                    return;
                }
            }
        }
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 5;
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
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 27;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
}
