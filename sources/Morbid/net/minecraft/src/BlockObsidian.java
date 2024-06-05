package net.minecraft.src;

import java.util.*;

public class BlockObsidian extends BlockStone
{
    public BlockObsidian(final int par1) {
        super(par1);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.obsidian.blockID;
    }
}
