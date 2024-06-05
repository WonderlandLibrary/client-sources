package net.minecraft.src;

public class SlotCrafting extends Slot
{
    private final IInventory craftMatrix;
    private EntityPlayer thePlayer;
    private int amountCrafted;
    
    public SlotCrafting(final EntityPlayer par1EntityPlayer, final IInventory par2IInventory, final IInventory par3IInventory, final int par4, final int par5, final int par6) {
        super(par3IInventory, par4, par5, par6);
        this.thePlayer = par1EntityPlayer;
        this.craftMatrix = par2IInventory;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(par1, this.getStack().stackSize);
        }
        return super.decrStackSize(par1);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack, final int par2) {
        this.amountCrafted += par2;
        this.onCrafting(par1ItemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        this.amountCrafted = 0;
        if (par1ItemStack.itemID == Block.workbench.blockID) {
            this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
        }
        else if (par1ItemStack.itemID == Item.pickaxeWood.itemID) {
            this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
        }
        else if (par1ItemStack.itemID == Block.furnaceIdle.blockID) {
            this.thePlayer.addStat(AchievementList.buildFurnace, 1);
        }
        else if (par1ItemStack.itemID == Item.hoeWood.itemID) {
            this.thePlayer.addStat(AchievementList.buildHoe, 1);
        }
        else if (par1ItemStack.itemID == Item.bread.itemID) {
            this.thePlayer.addStat(AchievementList.makeBread, 1);
        }
        else if (par1ItemStack.itemID == Item.cake.itemID) {
            this.thePlayer.addStat(AchievementList.bakeCake, 1);
        }
        else if (par1ItemStack.itemID == Item.pickaxeStone.itemID) {
            this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
        }
        else if (par1ItemStack.itemID == Item.swordWood.itemID) {
            this.thePlayer.addStat(AchievementList.buildSword, 1);
        }
        else if (par1ItemStack.itemID == Block.enchantmentTable.blockID) {
            this.thePlayer.addStat(AchievementList.enchantments, 1);
        }
        else if (par1ItemStack.itemID == Block.bookShelf.blockID) {
            this.thePlayer.addStat(AchievementList.bookcase, 1);
        }
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        for (int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); ++var3) {
            final ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
            if (var4 != null) {
                this.craftMatrix.decrStackSize(var3, 1);
                if (var4.getItem().hasContainerItem()) {
                    final ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
                    if (!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !this.thePlayer.inventory.addItemStackToInventory(var5)) {
                        if (this.craftMatrix.getStackInSlot(var3) == null) {
                            this.craftMatrix.setInventorySlotContents(var3, var5);
                        }
                        else {
                            this.thePlayer.dropPlayerItem(var5);
                        }
                    }
                }
            }
        }
    }
}
