package net.minecraft.src;

import java.util.*;

public class BlockEnchantmentTable extends BlockContainer
{
    private Icon field_94461_a;
    private Icon field_94460_b;
    
    protected BlockEnchantmentTable(final int par1) {
        super(par1, Material.rock);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        for (int var6 = par2 - 2; var6 <= par2 + 2; ++var6) {
            for (int var7 = par4 - 2; var7 <= par4 + 2; ++var7) {
                if (var6 > par2 - 2 && var6 < par2 + 2 && var7 == par4 - 1) {
                    var7 = par4 + 2;
                }
                if (par5Random.nextInt(16) == 0) {
                    for (int var8 = par3; var8 <= par3 + 1; ++var8) {
                        if (par1World.getBlockId(var6, var8, var7) == Block.bookShelf.blockID) {
                            if (!par1World.isAirBlock((var6 - par2) / 2 + par2, var8, (var7 - par4) / 2 + par4)) {
                                break;
                            }
                            par1World.spawnParticle("enchantmenttable", par2 + 0.5, par3 + 2.0, par4 + 0.5, var6 - par2 + par5Random.nextFloat() - 0.5, var8 - par3 - par5Random.nextFloat() - 1.0f, var7 - par4 + par5Random.nextFloat() - 0.5);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 0) ? this.field_94460_b : ((par1 == 1) ? this.field_94461_a : this.blockIcon);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityEnchantmentTable();
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable)par1World.getBlockTileEntity(par2, par3, par4);
        par5EntityPlayer.displayGUIEnchantment(par2, par3, par4, var10.func_94135_b() ? var10.func_94133_a() : null);
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityEnchantmentTable)par1World.getBlockTileEntity(par2, par3, par4)).func_94134_a(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("enchantment_side");
        this.field_94461_a = par1IconRegister.registerIcon("enchantment_top");
        this.field_94460_b = par1IconRegister.registerIcon("enchantment_bottom");
    }
}
