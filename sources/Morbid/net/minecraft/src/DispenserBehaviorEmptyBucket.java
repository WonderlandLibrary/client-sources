package net.minecraft.src;

final class DispenserBehaviorEmptyBucket extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior;
    
    DispenserBehaviorEmptyBucket() {
        this.defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
    }
    
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final World var4 = par1IBlockSource.getWorld();
        final int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
        final int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
        final int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
        final Material var8 = var4.getBlockMaterial(var5, var6, var7);
        final int var9 = var4.getBlockMetadata(var5, var6, var7);
        Item var10;
        if (Material.water.equals(var8) && var9 == 0) {
            var10 = Item.bucketWater;
        }
        else {
            if (!Material.lava.equals(var8) || var9 != 0) {
                return super.dispenseStack(par1IBlockSource, par2ItemStack);
            }
            var10 = Item.bucketLava;
        }
        var4.setBlockToAir(var5, var6, var7);
        final int stackSize = par2ItemStack.stackSize - 1;
        par2ItemStack.stackSize = stackSize;
        if (stackSize == 0) {
            par2ItemStack.itemID = var10.itemID;
            par2ItemStack.stackSize = 1;
        }
        else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).addItem(new ItemStack(var10)) < 0) {
            this.defaultDispenserItemBehavior.dispense(par1IBlockSource, new ItemStack(var10));
        }
        return par2ItemStack;
    }
}
