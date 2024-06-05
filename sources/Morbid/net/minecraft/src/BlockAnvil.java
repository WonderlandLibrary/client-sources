package net.minecraft.src;

import java.util.*;

public class BlockAnvil extends BlockSand
{
    public static final String[] statuses;
    private static final String[] anvilIconNames;
    public int field_82521_b;
    private Icon[] iconArray;
    
    static {
        statuses = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
        anvilIconNames = new String[] { "anvil_top", "anvil_top_damaged_1", "anvil_top_damaged_2" };
    }
    
    protected BlockAnvil(final int par1) {
        super(par1, Material.anvil);
        this.setLightOpacity(this.field_82521_b = 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        if (this.field_82521_b == 3 && par1 == 1) {
            final int var3 = (par2 >> 2) % this.iconArray.length;
            return this.iconArray[var3];
        }
        return this.blockIcon;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("anvil_base");
        this.iconArray = new Icon[BlockAnvil.anvilIconNames.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockAnvil.anvilIconNames[var2]);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        int var7 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final int var8 = par1World.getBlockMetadata(par2, par3, par4) >> 2;
        var7 = ++var7 % 4;
        if (var7 == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x2 | var8 << 2, 2);
        }
        if (var7 == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x3 | var8 << 2, 2);
        }
        if (var7 == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var8 << 2, 2);
        }
        if (var7 == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x1 | var8 << 2, 2);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        par5EntityPlayer.displayGUIAnvil(par2, par3, par4);
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 35;
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1 >> 2;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x3;
        if (var5 != 3 && var5 != 1) {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }
    
    @Override
    protected void onStartFalling(final EntityFallingSand par1EntityFallingSand) {
        par1EntityFallingSand.setIsAnvil(true);
    }
    
    @Override
    public void onFinishFalling(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        par1World.playAuxSFX(1022, par2, par3, par4, 0);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
}
