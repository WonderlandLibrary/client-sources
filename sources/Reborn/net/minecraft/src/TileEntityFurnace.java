package net.minecraft.src;

public class TileEntityFurnace extends TileEntity implements ISidedInventory
{
    private static final int[] field_102010_d;
    private static final int[] field_102011_e;
    private static final int[] field_102009_f;
    private ItemStack[] furnaceItemStacks;
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;
    private String field_94130_e;
    
    static {
        field_102010_d = new int[1];
        field_102011_e = new int[] { 2, 1 };
        field_102009_f = new int[] { 1 };
    }
    
    public TileEntityFurnace() {
        this.furnaceItemStacks = new ItemStack[3];
        this.furnaceBurnTime = 0;
        this.currentItemBurnTime = 0;
        this.furnaceCookTime = 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.furnaceItemStacks[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.furnaceItemStacks[par1] == null) {
            return null;
        }
        if (this.furnaceItemStacks[par1].stackSize <= par2) {
            final ItemStack var3 = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return var3;
        }
        final ItemStack var3 = this.furnaceItemStacks[par1].splitStack(par2);
        if (this.furnaceItemStacks[par1].stackSize == 0) {
            this.furnaceItemStacks[par1] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.furnaceItemStacks[par1] != null) {
            final ItemStack var2 = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.furnaceItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94130_e : "container.furnace";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }
    
    public void func_94129_a(final String par1Str) {
        this.field_94130_e = par1Str;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94130_e = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3) {
            if (this.furnaceItemStacks[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.field_94130_e);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public int getCookProgressScaled(final int par1) {
        return this.furnaceCookTime * par1 / 200;
    }
    
    public int getBurnTimeRemainingScaled(final int par1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }
    
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }
    
    @Override
    public void updateEntity() {
        final boolean var1 = this.furnaceBurnTime > 0;
        boolean var2 = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }
        if (!this.worldObj.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                final int itemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                this.furnaceBurnTime = itemBurnTime;
                this.currentItemBurnTime = itemBurnTime;
                if (this.furnaceBurnTime > 0) {
                    var2 = true;
                    if (this.furnaceItemStacks[1] != null) {
                        final ItemStack itemStack = this.furnaceItemStacks[1];
                        --itemStack.stackSize;
                        if (this.furnaceItemStacks[1].stackSize == 0) {
                            final Item var3 = this.furnaceItemStacks[1].getItem().getContainerItem();
                            this.furnaceItemStacks[1] = ((var3 != null) ? new ItemStack(var3) : null);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt()) {
                ++this.furnaceCookTime;
                if (this.furnaceCookTime == 200) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            }
            else {
                this.furnaceCookTime = 0;
            }
            if (var1 != this.furnaceBurnTime > 0) {
                var2 = true;
                BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (var2) {
            this.onInventoryChanged();
        }
    }
    
    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        final ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().itemID);
        return var1 != null && (this.furnaceItemStacks[2] == null || (this.furnaceItemStacks[2].isItemEqual(var1) && ((this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize()) || this.furnaceItemStacks[2].stackSize < var1.getMaxStackSize())));
    }
    
    public void smeltItem() {
        if (this.canSmelt()) {
            final ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().itemID);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = var1.copy();
            }
            else if (this.furnaceItemStacks[2].itemID == var1.itemID) {
                final ItemStack itemStack = this.furnaceItemStacks[2];
                ++itemStack.stackSize;
            }
            final ItemStack itemStack2 = this.furnaceItemStacks[0];
            --itemStack2.stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }
    
    public static int getItemBurnTime(final ItemStack par0ItemStack) {
        if (par0ItemStack == null) {
            return 0;
        }
        final int var1 = par0ItemStack.getItem().itemID;
        final Item var2 = par0ItemStack.getItem();
        if (var1 < 256 && Block.blocksList[var1] != null) {
            final Block var3 = Block.blocksList[var1];
            if (var3 == Block.woodSingleSlab) {
                return 150;
            }
            if (var3.blockMaterial == Material.wood) {
                return 300;
            }
        }
        return (var2 instanceof ItemTool && ((ItemTool)var2).getToolMaterialName().equals("WOOD")) ? 200 : ((var2 instanceof ItemSword && ((ItemSword)var2).getToolMaterialName().equals("WOOD")) ? 200 : ((var2 instanceof ItemHoe && ((ItemHoe)var2).getMaterialName().equals("WOOD")) ? 200 : ((var1 == Item.stick.itemID) ? 100 : ((var1 == Item.coal.itemID) ? 1600 : ((var1 == Item.bucketLava.itemID) ? 20000 : ((var1 == Block.sapling.blockID) ? 100 : ((var1 == Item.blazeRod.itemID) ? 2400 : 0)))))));
    }
    
    public static boolean isItemFuel(final ItemStack par0ItemStack) {
        return getItemBurnTime(par0ItemStack) > 0;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return par1 != 2 && (par1 != 1 || isItemFuel(par2ItemStack));
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(final int par1) {
        return (par1 == 0) ? TileEntityFurnace.field_102011_e : ((par1 == 1) ? TileEntityFurnace.field_102010_d : TileEntityFurnace.field_102009_f);
    }
    
    @Override
    public boolean canInsertItem(final int par1, final ItemStack par2ItemStack, final int par3) {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }
    
    @Override
    public boolean canExtractItem(final int par1, final ItemStack par2ItemStack, final int par3) {
        return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }
}
