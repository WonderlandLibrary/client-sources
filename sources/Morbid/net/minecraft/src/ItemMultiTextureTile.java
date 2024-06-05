package net.minecraft.src;

public class ItemMultiTextureTile extends ItemBlock
{
    private final Block theBlock;
    private final String[] field_82804_b;
    
    public ItemMultiTextureTile(final int par1, final Block par2Block, final String[] par3ArrayOfStr) {
        super(par1);
        this.theBlock = par2Block;
        this.field_82804_b = par3ArrayOfStr;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return this.theBlock.getIcon(2, par1);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        int var2 = par1ItemStack.getItemDamage();
        if (var2 < 0 || var2 >= this.field_82804_b.length) {
            var2 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + this.field_82804_b[var2];
    }
}
