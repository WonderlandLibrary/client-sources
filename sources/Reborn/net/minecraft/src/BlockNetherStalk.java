package net.minecraft.src;

import java.util.*;

public class BlockNetherStalk extends BlockFlower
{
    private static final String[] field_94373_a;
    private Icon[] iconArray;
    
    static {
        field_94373_a = new String[] { "netherStalk_0", "netherStalk_1", "netherStalk_2" };
    }
    
    protected BlockNetherStalk(final int par1) {
        super(par1);
        this.setTickRandomly(true);
        final float var2 = 0.5f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.25f, 0.5f + var2);
        this.setCreativeTab(null);
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return par1 == Block.slowSand.blockID;
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        return this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if (var6 < 3 && par5Random.nextInt(10) == 0) {
            ++var6;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
        }
        super.updateTick(par1World, par2, par3, par4, par5Random);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par2 >= 3) ? this.iconArray[2] : ((par2 > 0) ? this.iconArray[1] : this.iconArray[0]);
    }
    
    @Override
    public int getRenderType() {
        return 6;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!par1World.isRemote) {
            int var8 = 1;
            if (par5 >= 3) {
                var8 = 2 + par1World.rand.nextInt(3);
                if (par7 > 0) {
                    var8 += par1World.rand.nextInt(par7 + 1);
                }
            }
            for (int var9 = 0; var9 < var8; ++var9) {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.netherStalkSeeds));
            }
        }
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
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.netherStalkSeeds.itemID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockNetherStalk.field_94373_a.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockNetherStalk.field_94373_a[var2]);
        }
    }
}
