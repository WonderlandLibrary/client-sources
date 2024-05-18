package net.minecraft.src;

import java.util.*;

public class BlockClay extends Block
{
    public BlockClay(final int par1) {
        super(par1, Material.clay);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.clay.itemID;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 4;
    }
}
