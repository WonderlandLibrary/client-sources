package net.minecraft.src;

public class ItemColored extends ItemBlock
{
    private final Block blockRef;
    private String[] blockNames;
    
    public ItemColored(final int par1, final boolean par2) {
        super(par1);
        this.blockRef = Block.blocksList[this.getBlockID()];
        if (par2) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return this.blockRef.getRenderColor(par1ItemStack.getItemDamage());
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return this.blockRef.getIcon(0, par1);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
    
    public ItemColored setBlockNames(final String[] par1ArrayOfStr) {
        this.blockNames = par1ArrayOfStr;
        return this;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        if (this.blockNames == null) {
            return super.getUnlocalizedName(par1ItemStack);
        }
        final int var2 = par1ItemStack.getItemDamage();
        return (var2 >= 0 && var2 < this.blockNames.length) ? (String.valueOf(super.getUnlocalizedName(par1ItemStack)) + "." + this.blockNames[var2]) : super.getUnlocalizedName(par1ItemStack);
    }
}
