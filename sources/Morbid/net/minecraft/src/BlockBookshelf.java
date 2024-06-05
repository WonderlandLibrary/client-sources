package net.minecraft.src;

import java.util.*;

public class BlockBookshelf extends Block
{
    public BlockBookshelf(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 != 1 && par1 != 0) ? super.getIcon(par1, par2) : Block.planks.getBlockTextureFromSide(par1);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 3;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.book.itemID;
    }
}
