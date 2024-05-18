package net.minecraft.src;

public class ItemEmptyMap extends ItemMapBase
{
    protected ItemEmptyMap(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final ItemStack var4 = new ItemStack(Item.map, 1, par2World.getUniqueDataId("map"));
        final String var5 = "map_" + var4.getItemDamage();
        final MapData var6 = new MapData(var5);
        par2World.setItemData(var5, var6);
        var6.scale = 0;
        final int var7 = 128 * (1 << var6.scale);
        var6.xCenter = (int)(Math.round(par3EntityPlayer.posX / var7) * var7);
        var6.zCenter = (int)(Math.round(par3EntityPlayer.posZ / var7) * var7);
        var6.dimension = (byte)par2World.provider.dimensionId;
        var6.markDirty();
        --par1ItemStack.stackSize;
        if (par1ItemStack.stackSize <= 0) {
            return var4;
        }
        if (!par3EntityPlayer.inventory.addItemStackToInventory(var4.copy())) {
            par3EntityPlayer.dropPlayerItem(var4);
        }
        return par1ItemStack;
    }
}
