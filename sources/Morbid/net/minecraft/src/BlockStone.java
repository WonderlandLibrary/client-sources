package net.minecraft.src;

import java.util.*;

public class BlockStone extends Block
{
    public BlockStone(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.cobblestone.blockID;
    }
}
