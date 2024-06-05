package net.minecraft.src;

final class DispenserBehaviorFilledBucket extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior;
    
    DispenserBehaviorFilledBucket() {
        this.defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
    }
    
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final ItemBucket var3 = (ItemBucket)par2ItemStack.getItem();
        final int var4 = par1IBlockSource.getXInt();
        final int var5 = par1IBlockSource.getYInt();
        final int var6 = par1IBlockSource.getZInt();
        final EnumFacing var7 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        if (var3.tryPlaceContainedLiquid(par1IBlockSource.getWorld(), var4, var5, var6, var4 + var7.getFrontOffsetX(), var5 + var7.getFrontOffsetY(), var6 + var7.getFrontOffsetZ())) {
            par2ItemStack.itemID = Item.bucketEmpty.itemID;
            par2ItemStack.stackSize = 1;
            return par2ItemStack;
        }
        return this.defaultDispenserItemBehavior.dispense(par1IBlockSource, par2ItemStack);
    }
}
