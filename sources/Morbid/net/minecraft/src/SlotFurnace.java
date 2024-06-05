package net.minecraft.src;

public class SlotFurnace extends Slot
{
    private EntityPlayer thePlayer;
    private int field_75228_b;
    
    public SlotFurnace(final EntityPlayer par1EntityPlayer, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.thePlayer = par1EntityPlayer;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1) {
        if (this.getHasStack()) {
            this.field_75228_b += Math.min(par1, this.getStack().stackSize);
        }
        return super.decrStackSize(par1);
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack, final int par2) {
        this.field_75228_b += par2;
        this.onCrafting(par1ItemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
        if (!this.thePlayer.worldObj.isRemote) {
            int var2 = this.field_75228_b;
            final float var3 = FurnaceRecipes.smelting().getExperience(par1ItemStack.itemID);
            if (var3 == 0.0f) {
                var2 = 0;
            }
            else if (var3 < 1.0f) {
                int var4 = MathHelper.floor_float(var2 * var3);
                if (var4 < MathHelper.ceiling_float_int(var2 * var3) && (float)Math.random() < var2 * var3 - var4) {
                    ++var4;
                }
                var2 = var4;
            }
            while (var2 > 0) {
                final int var4 = EntityXPOrb.getXPSplit(var2);
                var2 -= var4;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5, this.thePlayer.posZ + 0.5, var4));
            }
        }
        this.field_75228_b = 0;
        if (par1ItemStack.itemID == Item.ingotIron.itemID) {
            this.thePlayer.addStat(AchievementList.acquireIron, 1);
        }
        if (par1ItemStack.itemID == Item.fishCooked.itemID) {
            this.thePlayer.addStat(AchievementList.cookFish, 1);
        }
    }
}
