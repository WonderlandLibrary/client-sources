package net.minecraft.src;

import java.util.*;

public class BlockMelon extends Block
{
    private Icon theIcon;
    
    protected BlockMelon(final int par1) {
        super(par1, Material.pumpkin);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 != 1 && par1 != 0) ? this.blockIcon : this.theIcon;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.melon.itemID;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 3 + par1Random.nextInt(5);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        int var3 = this.quantityDropped(par2Random) + par2Random.nextInt(1 + par1);
        if (var3 > 9) {
            var3 = 9;
        }
        return var3;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("melon_side");
        this.theIcon = par1IconRegister.registerIcon("melon_top");
    }
}
