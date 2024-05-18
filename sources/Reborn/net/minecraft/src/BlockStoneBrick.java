package net.minecraft.src;

import java.util.*;

public class BlockStoneBrick extends Block
{
    public static final String[] STONE_BRICK_TYPES;
    public static final String[] field_94407_b;
    private Icon[] field_94408_c;
    
    static {
        STONE_BRICK_TYPES = new String[] { "default", "mossy", "cracked", "chiseled" };
        field_94407_b = new String[] { "stonebricksmooth", "stonebricksmooth_mossy", "stonebricksmooth_cracked", "stonebricksmooth_carved" };
    }
    
    public BlockStoneBrick(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 < 0 || par2 >= BlockStoneBrick.field_94407_b.length) {
            par2 = 0;
        }
        return this.field_94408_c[par2];
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < 4; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94408_c = new Icon[BlockStoneBrick.field_94407_b.length];
        for (int var2 = 0; var2 < this.field_94408_c.length; ++var2) {
            this.field_94408_c[var2] = par1IconRegister.registerIcon(BlockStoneBrick.field_94407_b[var2]);
        }
    }
}
