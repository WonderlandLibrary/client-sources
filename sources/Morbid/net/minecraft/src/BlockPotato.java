package net.minecraft.src;

public class BlockPotato extends BlockCrops
{
    private Icon[] iconArray;
    
    public BlockPotato(final int par1) {
        super(par1);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 < 7) {
            if (par2 == 6) {
                par2 = 5;
            }
            return this.iconArray[par2 >> 1];
        }
        return this.iconArray[3];
    }
    
    @Override
    protected int getSeedItem() {
        return Item.potato.itemID;
    }
    
    @Override
    protected int getCropItem() {
        return Item.potato.itemID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        if (!par1World.isRemote && par5 >= 7 && par1World.rand.nextInt(50) == 0) {
            this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.poisonousPotato));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[4];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon("potatoes_" + var2);
        }
    }
}
