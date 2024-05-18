package net.minecraft.src;

import java.util.*;

public class BlockCloth extends Block
{
    private Icon[] iconArray;
    
    public BlockCloth() {
        super(35, Material.cloth);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public static int getBlockFromDye(final int par0) {
        return ~par0 & 0xF;
    }
    
    public static int getDyeFromBlock(final int par0) {
        return ~par0 & 0xF;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[16];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon("cloth_" + var2);
        }
    }
}
