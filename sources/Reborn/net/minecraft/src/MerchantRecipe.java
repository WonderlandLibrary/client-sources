package net.minecraft.src;

public class MerchantRecipe
{
    private ItemStack itemToBuy;
    private ItemStack secondItemToBuy;
    private ItemStack itemToSell;
    private int toolUses;
    private int maxTradeUses;
    
    public MerchantRecipe(final NBTTagCompound par1NBTTagCompound) {
        this.readFromTags(par1NBTTagCompound);
    }
    
    public MerchantRecipe(final ItemStack par1ItemStack, final ItemStack par2ItemStack, final ItemStack par3ItemStack) {
        this.itemToBuy = par1ItemStack;
        this.secondItemToBuy = par2ItemStack;
        this.itemToSell = par3ItemStack;
        this.maxTradeUses = 7;
    }
    
    public MerchantRecipe(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        this(par1ItemStack, null, par2ItemStack);
    }
    
    public MerchantRecipe(final ItemStack par1ItemStack, final Item par2Item) {
        this(par1ItemStack, new ItemStack(par2Item));
    }
    
    public ItemStack getItemToBuy() {
        return this.itemToBuy;
    }
    
    public ItemStack getSecondItemToBuy() {
        return this.secondItemToBuy;
    }
    
    public boolean hasSecondItemToBuy() {
        return this.secondItemToBuy != null;
    }
    
    public ItemStack getItemToSell() {
        return this.itemToSell;
    }
    
    public boolean hasSameIDsAs(final MerchantRecipe par1MerchantRecipe) {
        return this.itemToBuy.itemID == par1MerchantRecipe.itemToBuy.itemID && this.itemToSell.itemID == par1MerchantRecipe.itemToSell.itemID && ((this.secondItemToBuy == null && par1MerchantRecipe.secondItemToBuy == null) || (this.secondItemToBuy != null && par1MerchantRecipe.secondItemToBuy != null && this.secondItemToBuy.itemID == par1MerchantRecipe.secondItemToBuy.itemID));
    }
    
    public boolean hasSameItemsAs(final MerchantRecipe par1MerchantRecipe) {
        return this.hasSameIDsAs(par1MerchantRecipe) && (this.itemToBuy.stackSize < par1MerchantRecipe.itemToBuy.stackSize || (this.secondItemToBuy != null && this.secondItemToBuy.stackSize < par1MerchantRecipe.secondItemToBuy.stackSize));
    }
    
    public void incrementToolUses() {
        ++this.toolUses;
    }
    
    public void func_82783_a(final int par1) {
        this.maxTradeUses += par1;
    }
    
    public boolean func_82784_g() {
        return this.toolUses >= this.maxTradeUses;
    }
    
    public void func_82785_h() {
        this.toolUses = this.maxTradeUses;
    }
    
    public void readFromTags(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("buy");
        this.itemToBuy = ItemStack.loadItemStackFromNBT(var2);
        final NBTTagCompound var3 = par1NBTTagCompound.getCompoundTag("sell");
        this.itemToSell = ItemStack.loadItemStackFromNBT(var3);
        if (par1NBTTagCompound.hasKey("buyB")) {
            this.secondItemToBuy = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("buyB"));
        }
        if (par1NBTTagCompound.hasKey("uses")) {
            this.toolUses = par1NBTTagCompound.getInteger("uses");
        }
        if (par1NBTTagCompound.hasKey("maxUses")) {
            this.maxTradeUses = par1NBTTagCompound.getInteger("maxUses");
        }
        else {
            this.maxTradeUses = 7;
        }
    }
    
    public NBTTagCompound writeToTags() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.setCompoundTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound("buy")));
        var1.setCompoundTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound("sell")));
        if (this.secondItemToBuy != null) {
            var1.setCompoundTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound("buyB")));
        }
        var1.setInteger("uses", this.toolUses);
        var1.setInteger("maxUses", this.maxTradeUses);
        return var1;
    }
}
