package net.minecraft.src;

import java.util.*;

public class BlockCrops extends BlockFlower
{
    private Icon[] iconArray;
    
    protected BlockCrops(final int par1) {
        super(par1);
        this.setTickRandomly(true);
        final float var2 = 0.5f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.25f, 0.5f + var2);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(BlockCrops.soundGrassFootstep);
        this.disableStats();
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return par1 == Block.tilledField.blockID;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if (var6 < 7) {
                final float var7 = this.getGrowthRate(par1World, par2, par3, par4);
                if (par5Random.nextInt((int)(25.0f / var7) + 1) == 0) {
                    ++var6;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
                }
            }
        }
    }
    
    public void fertilize(final World par1World, final int par2, final int par3, final int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4) + MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
        if (var5 > 7) {
            var5 = 7;
        }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);
    }
    
    private float getGrowthRate(final World par1World, final int par2, final int par3, final int par4) {
        float var5 = 1.0f;
        final int var6 = par1World.getBlockId(par2, par3, par4 - 1);
        final int var7 = par1World.getBlockId(par2, par3, par4 + 1);
        final int var8 = par1World.getBlockId(par2 - 1, par3, par4);
        final int var9 = par1World.getBlockId(par2 + 1, par3, par4);
        final int var10 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
        final int var11 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
        final int var12 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
        final int var13 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
        final boolean var14 = var8 == this.blockID || var9 == this.blockID;
        final boolean var15 = var6 == this.blockID || var7 == this.blockID;
        final boolean var16 = var10 == this.blockID || var11 == this.blockID || var12 == this.blockID || var13 == this.blockID;
        for (int var17 = par2 - 1; var17 <= par2 + 1; ++var17) {
            for (int var18 = par4 - 1; var18 <= par4 + 1; ++var18) {
                final int var19 = par1World.getBlockId(var17, par3 - 1, var18);
                float var20 = 0.0f;
                if (var19 == Block.tilledField.blockID) {
                    var20 = 1.0f;
                    if (par1World.getBlockMetadata(var17, par3 - 1, var18) > 0) {
                        var20 = 3.0f;
                    }
                }
                if (var17 != par2 || var18 != par4) {
                    var20 /= 4.0f;
                }
                var5 += var20;
            }
        }
        if (var16 || (var14 && var15)) {
            var5 /= 2.0f;
        }
        return var5;
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 < 0 || par2 > 7) {
            par2 = 7;
        }
        return this.iconArray[par2];
    }
    
    @Override
    public int getRenderType() {
        return 6;
    }
    
    protected int getSeedItem() {
        return Item.seeds.itemID;
    }
    
    protected int getCropItem() {
        return Item.wheat.itemID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
        if (!par1World.isRemote && par5 >= 7) {
            for (int var8 = 3 + par7, var9 = 0; var9 < var8; ++var9) {
                if (par1World.rand.nextInt(15) <= par5) {
                    this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(this.getSeedItem(), 1, 0));
                }
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return (par1 == 7) ? this.getCropItem() : this.getSeedItem();
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return this.getSeedItem();
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[8];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon("crops_" + var2);
        }
    }
}
