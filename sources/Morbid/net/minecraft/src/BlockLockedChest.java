package net.minecraft.src;

import java.util.*;

public class BlockLockedChest extends Block
{
    protected BlockLockedChest(final int par1) {
        super(par1, Material.wood);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return true;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        par1World.setBlockToAir(par2, par3, par4);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
