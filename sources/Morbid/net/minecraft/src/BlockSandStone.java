package net.minecraft.src;

import java.util.*;

public class BlockSandStone extends Block
{
    public static final String[] SAND_STONE_TYPES;
    private static final String[] field_94405_b;
    private Icon[] field_94406_c;
    private Icon field_94403_cO;
    private Icon field_94404_cP;
    
    static {
        SAND_STONE_TYPES = new String[] { "default", "chiseled", "smooth" };
        field_94405_b = new String[] { "sandstone_side", "sandstone_carved", "sandstone_smooth" };
    }
    
    public BlockSandStone(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par1 == 1 || (par1 == 0 && (par2 == 1 || par2 == 2))) {
            return this.field_94403_cO;
        }
        if (par1 == 0) {
            return this.field_94404_cP;
        }
        if (par2 < 0 || par2 >= this.field_94406_c.length) {
            par2 = 0;
        }
        return this.field_94406_c[par2];
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
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94406_c = new Icon[BlockSandStone.field_94405_b.length];
        for (int var2 = 0; var2 < this.field_94406_c.length; ++var2) {
            this.field_94406_c[var2] = par1IconRegister.registerIcon(BlockSandStone.field_94405_b[var2]);
        }
        this.field_94403_cO = par1IconRegister.registerIcon("sandstone_top");
        this.field_94404_cP = par1IconRegister.registerIcon("sandstone_bottom");
    }
}
