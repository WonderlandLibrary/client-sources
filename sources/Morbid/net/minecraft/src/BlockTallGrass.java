package net.minecraft.src;

import java.util.*;

public class BlockTallGrass extends BlockFlower
{
    private static final String[] grassTypes;
    private Icon[] iconArray;
    
    static {
        grassTypes = new String[] { "deadbush", "tallgrass", "fern" };
    }
    
    protected BlockTallGrass(final int par1) {
        super(par1, Material.vine);
        final float var2 = 0.4f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.8f, 0.5f + var2);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 >= this.iconArray.length) {
            par2 = 0;
        }
        return this.iconArray[par2];
    }
    
    @Override
    public int getBlockColor() {
        final double var1 = 0.5;
        final double var2 = 1.0;
        return ColorizerGrass.getGrassColor(var1, var2);
    }
    
    @Override
    public int getRenderColor(final int par1) {
        return (par1 == 0) ? 16777215 : ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        return (var5 == 0) ? 16777215 : par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return (par2Random.nextInt(8) == 0) ? Item.seeds.itemID : -1;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        return 1 + par2Random.nextInt(par1 * 2 + 1);
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.tallGrass, 1, par6));
        }
        else {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 1; var4 < 3; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockTallGrass.grassTypes.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockTallGrass.grassTypes[var2]);
        }
    }
}
