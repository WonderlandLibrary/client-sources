package net.minecraft.src;

public class ItemGlassBottle extends Item
{
    public ItemGlassBottle(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    @Override
    public Icon getIconFromDamage(final int par1) {
        return Item.potion.getIconFromDamage(0);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        if (var4 == null) {
            return par1ItemStack;
        }
        if (var4.typeOfHit == EnumMovingObjectType.TILE) {
            final int var5 = var4.blockX;
            final int var6 = var4.blockY;
            final int var7 = var4.blockZ;
            if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
                return par1ItemStack;
            }
            if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
                return par1ItemStack;
            }
            if (par2World.getBlockMaterial(var5, var6, var7) == Material.water) {
                --par1ItemStack.stackSize;
                if (par1ItemStack.stackSize <= 0) {
                    return new ItemStack(Item.potion);
                }
                if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.potion))) {
                    par3EntityPlayer.dropPlayerItem(new ItemStack(Item.potion.itemID, 1, 0));
                }
            }
        }
        return par1ItemStack;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
