package net.minecraft.src;

import java.util.*;

public class ItemSkull extends Item
{
    private static final String[] skullTypes;
    public static final String[] field_94587_a;
    private Icon[] field_94586_c;
    
    static {
        skullTypes = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
        field_94587_a = new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" };
    }
    
    public ItemSkull(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par7 == 0) {
            return false;
        }
        if (!par3World.getBlockMaterial(par4, par5, par6).isSolid()) {
            return false;
        }
        if (par7 == 1) {
            ++par5;
        }
        if (par7 == 2) {
            --par6;
        }
        if (par7 == 3) {
            ++par6;
        }
        if (par7 == 4) {
            --par4;
        }
        if (par7 == 5) {
            ++par4;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (!Block.skull.canPlaceBlockAt(par3World, par4, par5, par6)) {
            return false;
        }
        par3World.setBlock(par4, par5, par6, Block.skull.blockID, par7, 2);
        int var11 = 0;
        if (par7 == 1) {
            var11 = (MathHelper.floor_double(par2EntityPlayer.rotationYaw * 16.0f / 360.0f + 0.5) & 0xF);
        }
        final TileEntity var12 = par3World.getBlockTileEntity(par4, par5, par6);
        if (var12 != null && var12 instanceof TileEntitySkull) {
            String var13 = "";
            if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("SkullOwner")) {
                var13 = par1ItemStack.getTagCompound().getString("SkullOwner");
            }
            ((TileEntitySkull)var12).setSkullType(par1ItemStack.getItemDamage(), var13);
            ((TileEntitySkull)var12).setSkullRotation(var11);
            ((BlockSkull)Block.skull).makeWither(par3World, par4, par5, par6, (TileEntitySkull)var12);
        }
        --par1ItemStack.stackSize;
        return true;
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < ItemSkull.skullTypes.length; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @Override
    public Icon getIconFromDamage(int par1) {
        if (par1 < 0 || par1 >= ItemSkull.skullTypes.length) {
            par1 = 0;
        }
        return this.field_94586_c[par1];
    }
    
    @Override
    public int getMetadata(final int par1) {
        return par1;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        int var2 = par1ItemStack.getItemDamage();
        if (var2 < 0 || var2 >= ItemSkull.skullTypes.length) {
            var2 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + ItemSkull.skullTypes[var2];
    }
    
    @Override
    public String getItemDisplayName(final ItemStack par1ItemStack) {
        return (par1ItemStack.getItemDamage() == 3 && par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("SkullOwner")) ? StatCollector.translateToLocalFormatted("item.skull.player.name", par1ItemStack.getTagCompound().getString("SkullOwner")) : super.getItemDisplayName(par1ItemStack);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94586_c = new Icon[ItemSkull.field_94587_a.length];
        for (int var2 = 0; var2 < ItemSkull.field_94587_a.length; ++var2) {
            this.field_94586_c[var2] = par1IconRegister.registerIcon(ItemSkull.field_94587_a[var2]);
        }
    }
}
