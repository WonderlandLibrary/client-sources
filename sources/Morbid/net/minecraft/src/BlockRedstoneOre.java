package net.minecraft.src;

import java.util.*;

public class BlockRedstoneOre extends Block
{
    private boolean glowing;
    
    public BlockRedstoneOre(final int par1, final boolean par2) {
        super(par1, Material.rock);
        if (par2) {
            this.setTickRandomly(true);
        }
        this.glowing = par2;
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 30;
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        this.glow(par1World, par2, par3, par4);
        super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }
    
    @Override
    public void onEntityWalking(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        this.glow(par1World, par2, par3, par4);
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        this.glow(par1World, par2, par3, par4);
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }
    
    private void glow(final World par1World, final int par2, final int par3, final int par4) {
        this.sparkle(par1World, par2, par3, par4);
        if (this.blockID == Block.oreRedstone.blockID) {
            par1World.setBlock(par2, par3, par4, Block.oreRedstoneGlowing.blockID);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.blockID == Block.oreRedstoneGlowing.blockID) {
            par1World.setBlock(par2, par3, par4, Block.oreRedstone.blockID);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.redstone.itemID;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        return this.quantityDropped(par2Random) + par2Random.nextInt(par1 + 1);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 4 + par1Random.nextInt(2);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        if (this.idDropped(par5, par1World.rand, par7) != this.blockID) {
            final int var8 = 1 + par1World.rand.nextInt(5);
            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.glowing) {
            this.sparkle(par1World, par2, par3, par4);
        }
    }
    
    private void sparkle(final World par1World, final int par2, final int par3, final int par4) {
        final Random var5 = par1World.rand;
        final double var6 = 0.0625;
        for (int var7 = 0; var7 < 6; ++var7) {
            double var8 = par2 + var5.nextFloat();
            double var9 = par3 + var5.nextFloat();
            double var10 = par4 + var5.nextFloat();
            if (var7 == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4)) {
                var9 = par3 + 1 + var6;
            }
            if (var7 == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4)) {
                var9 = par3 + 0 - var6;
            }
            if (var7 == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1)) {
                var10 = par4 + 1 + var6;
            }
            if (var7 == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1)) {
                var10 = par4 + 0 - var6;
            }
            if (var7 == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4)) {
                var8 = par2 + 1 + var6;
            }
            if (var7 == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4)) {
                var8 = par2 + 0 - var6;
            }
            if (var8 < par2 || var8 > par2 + 1 || var9 < 0.0 || var9 > par3 + 1 || var10 < par4 || var10 > par4 + 1) {
                par1World.spawnParticle("reddust", var8, var9, var10, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(Block.oreRedstone);
    }
}
