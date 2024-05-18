package net.minecraft.src;

public class ItemLeaves extends ItemBlock
{
    public ItemLeaves(final int par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1 | 0x4;
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return Block.leaves.getIcon(0, par1);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        final int var3 = par1ItemStack.getItemDamage();
        return ((var3 & 0x1) == 0x1) ? ColorizerFoliage.getFoliageColorPine() : (((var3 & 0x2) == 0x2) ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        int var2 = par1ItemStack.getItemDamage();
        if (var2 < 0 || var2 >= BlockLeaves.LEAF_TYPES.length) {
            var2 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + BlockLeaves.LEAF_TYPES[var2];
    }
}
