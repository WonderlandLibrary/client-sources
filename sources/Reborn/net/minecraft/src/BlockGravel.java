package net.minecraft.src;

import java.util.*;

public class BlockGravel extends BlockSand
{
    public BlockGravel(final int par1) {
        super(par1);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, int par3) {
        if (par3 > 3) {
            par3 = 3;
        }
        return (par2Random.nextInt(10 - par3 * 3) == 0) ? Item.flint.itemID : this.blockID;
    }
}
