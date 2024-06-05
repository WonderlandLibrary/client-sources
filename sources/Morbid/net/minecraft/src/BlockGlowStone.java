package net.minecraft.src;

import java.util.*;

public class BlockGlowStone extends Block
{
    public BlockGlowStone(final int par1, final Material par2Material) {
        super(par1, par2Material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        return MathHelper.clamp_int(this.quantityDropped(par2Random) + par2Random.nextInt(par1 + 1), 1, 4);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 2 + par1Random.nextInt(3);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.lightStoneDust.itemID;
    }
}
