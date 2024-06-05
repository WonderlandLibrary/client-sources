package net.minecraft.src;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(final int par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return Block.cloth.getIcon(2, BlockCloth.getBlockFromDye(par1));
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return String.valueOf(super.getUnlocalizedName()) + "." + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage())];
    }
}
