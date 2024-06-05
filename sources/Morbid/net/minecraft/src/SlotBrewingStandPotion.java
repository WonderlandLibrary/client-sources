package net.minecraft.src;

class SlotBrewingStandPotion extends Slot
{
    private EntityPlayer player;
    
    public SlotBrewingStandPotion(final EntityPlayer par1EntityPlayer, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.player = par1EntityPlayer;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return canHoldPotion(par1ItemStack);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        if (par2ItemStack.itemID == Item.potion.itemID && par2ItemStack.getItemDamage() > 0) {
            this.player.addStat(AchievementList.potion, 1);
        }
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }
    
    public static boolean canHoldPotion(final ItemStack par0ItemStack) {
        return par0ItemStack != null && (par0ItemStack.itemID == Item.potion.itemID || par0ItemStack.itemID == Item.glassBottle.itemID);
    }
}
