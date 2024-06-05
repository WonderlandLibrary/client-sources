package net.minecraft.src;

public class BlockPoweredOre extends BlockOreStorage
{
    public BlockPoweredOre(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return 15;
    }
}
