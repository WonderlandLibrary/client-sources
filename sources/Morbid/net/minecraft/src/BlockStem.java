package net.minecraft.src;

import java.util.*;

public class BlockStem extends BlockFlower
{
    private final Block fruitType;
    private Icon theIcon;
    
    protected BlockStem(final int par1, final Block par2Block) {
        super(par1);
        this.fruitType = par2Block;
        this.setTickRandomly(true);
        final float var3 = 0.125f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 0.25f, 0.5f + var3);
        this.setCreativeTab(null);
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return par1 == Block.tilledField.blockID;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
            final float var6 = this.getGrowthModifier(par1World, par2, par3, par4);
            if (par5Random.nextInt((int)(25.0f / var6) + 1) == 0) {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);
                if (var7 < 7) {
                    ++var7;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
                }
                else {
                    if (par1World.getBlockId(par2 - 1, par3, par4) == this.fruitType.blockID) {
                        return;
                    }
                    if (par1World.getBlockId(par2 + 1, par3, par4) == this.fruitType.blockID) {
                        return;
                    }
                    if (par1World.getBlockId(par2, par3, par4 - 1) == this.fruitType.blockID) {
                        return;
                    }
                    if (par1World.getBlockId(par2, par3, par4 + 1) == this.fruitType.blockID) {
                        return;
                    }
                    final int var8 = par5Random.nextInt(4);
                    int var9 = par2;
                    int var10 = par4;
                    if (var8 == 0) {
                        var9 = par2 - 1;
                    }
                    if (var8 == 1) {
                        ++var9;
                    }
                    if (var8 == 2) {
                        var10 = par4 - 1;
                    }
                    if (var8 == 3) {
                        ++var10;
                    }
                    final int var11 = par1World.getBlockId(var9, par3 - 1, var10);
                    if (par1World.getBlockId(var9, par3, var10) == 0 && (var11 == Block.tilledField.blockID || var11 == Block.dirt.blockID || var11 == Block.grass.blockID)) {
                        par1World.setBlock(var9, par3, var10, this.fruitType.blockID);
                    }
                }
            }
        }
    }
    
    public void fertilizeStem(final World par1World, final int par2, final int par3, final int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4) + MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
        if (var5 > 7) {
            var5 = 7;
        }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);
    }
    
    private float getGrowthModifier(final World par1World, final int par2, final int par3, final int par4) {
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
    public int getRenderColor(final int par1) {
        final int var2 = par1 * 32;
        final int var3 = 255 - par1 * 8;
        final int var4 = par1 * 4;
        return var2 << 16 | var3 << 8 | var4;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.getRenderColor(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.125f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.maxY = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) * 2 + 2) / 16.0f;
        final float var5 = 0.125f;
        this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var5, 0.5f + var5, (float)this.maxY, 0.5f + var5);
    }
    
    @Override
    public int getRenderType() {
        return 19;
    }
    
    public int getState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        return (var5 < 7) ? -1 : ((par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.fruitType.blockID) ? 0 : ((par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.fruitType.blockID) ? 1 : ((par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.fruitType.blockID) ? 2 : ((par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.fruitType.blockID) ? 3 : -1))));
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        if (!par1World.isRemote) {
            Item var8 = null;
            if (this.fruitType == Block.pumpkin) {
                var8 = Item.pumpkinSeeds;
            }
            if (this.fruitType == Block.melon) {
                var8 = Item.melonSeeds;
            }
            for (int var9 = 0; var9 < 3; ++var9) {
                if (par1World.rand.nextInt(15) <= par5) {
                    this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var8));
                }
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return -1;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return (this.fruitType == Block.pumpkin) ? Item.pumpkinSeeds.itemID : ((this.fruitType == Block.melon) ? Item.melonSeeds.itemID : 0);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("stem_straight");
        this.theIcon = par1IconRegister.registerIcon("stem_bent");
    }
    
    public Icon func_94368_p() {
        return this.theIcon;
    }
}
