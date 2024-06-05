package net.minecraft.src;

import java.util.*;

public class ItemFirework extends Item
{
    public ItemFirework(final int par1) {
        super(par1);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (!par3World.isRemote) {
            final EntityFireworkRocket var11 = new EntityFireworkRocket(par3World, par4 + par8, par5 + par9, par6 + par10, par1ItemStack);
            par3World.spawnEntityInWorld(var11);
            if (!par2EntityPlayer.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        if (par1ItemStack.hasTagCompound()) {
            final NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Fireworks");
            if (var5 != null) {
                if (var5.hasKey("Flight")) {
                    par3List.add(String.valueOf(StatCollector.translateToLocal("item.fireworks.flight")) + " " + var5.getByte("Flight"));
                }
                final NBTTagList var6 = var5.getTagList("Explosions");
                if (var6 != null && var6.tagCount() > 0) {
                    for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
                        final NBTTagCompound var8 = (NBTTagCompound)var6.tagAt(var7);
                        final ArrayList var9 = new ArrayList();
                        ItemFireworkCharge.func_92107_a(var8, var9);
                        if (var9.size() > 0) {
                            for (int var10 = 1; var10 < var9.size(); ++var10) {
                                var9.set(var10, "  " + var9.get(var10));
                            }
                            par3List.addAll(var9);
                        }
                    }
                }
            }
        }
    }
}
