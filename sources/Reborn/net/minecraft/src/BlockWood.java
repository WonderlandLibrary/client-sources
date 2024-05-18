package net.minecraft.src;

import java.util.*;

public class BlockWood extends Block
{
    public static final String[] woodType;
    public static final String[] woodTextureTypes;
    private Icon[] iconArray;
    
    static {
        woodType = new String[] { "oak", "spruce", "birch", "jungle" };
        woodTextureTypes = new String[] { "wood", "wood_spruce", "wood_birch", "wood_jungle" };
    }
    
    public BlockWood(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 < 0 || par2 >= this.iconArray.length) {
            par2 = 0;
        }
        return this.iconArray[par2];
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockWood.woodTextureTypes.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockWood.woodTextureTypes[var2]);
        }
    }
}
