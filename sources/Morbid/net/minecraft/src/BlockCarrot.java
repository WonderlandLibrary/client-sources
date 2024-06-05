package net.minecraft.src;

public class BlockCarrot extends BlockCrops
{
    private Icon[] iconArray;
    
    public BlockCarrot(final int par1) {
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
        return Item.carrot.itemID;
    }
    
    @Override
    protected int getCropItem() {
        return Item.carrot.itemID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[4];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon("carrots_" + var2);
        }
    }
}
