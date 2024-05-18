package net.minecraft.src;

class SlotCreativeInventory extends Slot
{
    private final Slot theSlot;
    final GuiContainerCreative theCreativeInventory;
    
    public SlotCreativeInventory(final GuiContainerCreative par1GuiContainerCreative, final Slot par2Slot, final int par3) {
        super(par2Slot.inventory, par3, 0, 0);
        this.theCreativeInventory = par1GuiContainerCreative;
        this.theSlot = par2Slot;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        this.theSlot.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return this.theSlot.isItemValid(par1ItemStack);
    }
    
    @Override
    public ItemStack getStack() {
        return this.theSlot.getStack();
    }
    
    @Override
    public boolean getHasStack() {
        return this.theSlot.getHasStack();
    }
    
    @Override
    public void putStack(final ItemStack par1ItemStack) {
        this.theSlot.putStack(par1ItemStack);
    }
    
    @Override
    public void onSlotChanged() {
        this.theSlot.onSlotChanged();
    }
    
    @Override
    public int getSlotStackLimit() {
        return this.theSlot.getSlotStackLimit();
    }
    
    @Override
    public Icon getBackgroundIconIndex() {
        return this.theSlot.getBackgroundIconIndex();
    }
    
    @Override
    public ItemStack decrStackSize(final int par1) {
        return this.theSlot.decrStackSize(par1);
    }
    
    @Override
    public boolean isSlotInInventory(final IInventory par1IInventory, final int par2) {
        return this.theSlot.isSlotInInventory(par1IInventory, par2);
    }
    
    static Slot func_75240_a(final SlotCreativeInventory par0SlotCreativeInventory) {
        return par0SlotCreativeInventory.theSlot;
    }
}
