package net.minecraft.src;

import java.util.*;

public class TileEntityHopper extends TileEntity implements Hopper
{
    private ItemStack[] hopperItemStacks;
    private String inventoryName;
    private int transferCooldown;
    
    public TileEntityHopper() {
        this.hopperItemStacks = new ItemStack[5];
        this.transferCooldown = -1;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.hopperItemStacks = new ItemStack[this.getSizeInventory()];
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.inventoryName = par1NBTTagCompound.getString("CustomName");
        }
        this.transferCooldown = par1NBTTagCompound.getInteger("TransferCooldown");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.hopperItemStacks.length) {
                this.hopperItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.hopperItemStacks.length; ++var3) {
            if (this.hopperItemStacks[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.hopperItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
        par1NBTTagCompound.setInteger("TransferCooldown", this.transferCooldown);
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.inventoryName);
        }
    }
    
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
    }
    
    @Override
    public int getSizeInventory() {
        return this.hopperItemStacks.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.hopperItemStacks[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.hopperItemStacks[par1] == null) {
            return null;
        }
        if (this.hopperItemStacks[par1].stackSize <= par2) {
            final ItemStack var3 = this.hopperItemStacks[par1];
            this.hopperItemStacks[par1] = null;
            return var3;
        }
        final ItemStack var3 = this.hopperItemStacks[par1].splitStack(par2);
        if (this.hopperItemStacks[par1].stackSize == 0) {
            this.hopperItemStacks[par1] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.hopperItemStacks[par1] != null) {
            final ItemStack var2 = this.hopperItemStacks[par1];
            this.hopperItemStacks[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.hopperItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.inventoryName : "container.hopper";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.inventoryName != null && this.inventoryName.length() > 0;
    }
    
    public void setInventoryName(final String par1Str) {
        this.inventoryName = par1Str;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
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
        return true;
    }
    
    @Override
    public void updateEntity() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            --this.transferCooldown;
            if (!this.isCoolingDown()) {
                this.setTransferCooldown(0);
                this.func_98045_j();
            }
        }
    }
    
    public boolean func_98045_j() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (!this.isCoolingDown() && BlockHopper.getIsBlockNotPoweredFromMetadata(this.getBlockMetadata())) {
                final boolean var1 = this.insertItemToInventory() | suckItemsIntoHopper(this);
                if (var1) {
                    this.setTransferCooldown(8);
                    this.onInventoryChanged();
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    private boolean insertItemToInventory() {
        final IInventory var1 = this.getOutputInventory();
        if (var1 == null) {
            return false;
        }
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            if (this.getStackInSlot(var2) != null) {
                final ItemStack var3 = this.getStackInSlot(var2).copy();
                final ItemStack var4 = insertStack(var1, this.decrStackSize(var2, 1), Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(this.getBlockMetadata())]);
                if (var4 == null || var4.stackSize == 0) {
                    var1.onInventoryChanged();
                    return true;
                }
                this.setInventorySlotContents(var2, var3);
            }
        }
        return false;
    }
    
    public static boolean suckItemsIntoHopper(final Hopper par0Hopper) {
        final IInventory var1 = getInventoryAboveHopper(par0Hopper);
        if (var1 != null) {
            final byte var2 = 0;
            if (var1 instanceof ISidedInventory && var2 > -1) {
                final ISidedInventory var3 = (ISidedInventory)var1;
                final int[] var4 = var3.getAccessibleSlotsFromSide(var2);
                for (int var5 = 0; var5 < var4.length; ++var5) {
                    if (func_102012_a(par0Hopper, var1, var4[var5], var2)) {
                        return true;
                    }
                }
            }
            else {
                for (int var6 = var1.getSizeInventory(), var7 = 0; var7 < var6; ++var7) {
                    if (func_102012_a(par0Hopper, var1, var7, var2)) {
                        return true;
                    }
                }
            }
        }
        else {
            final EntityItem var8 = func_96119_a(par0Hopper.getWorldObj(), par0Hopper.getXPos(), par0Hopper.getYPos() + 1.0, par0Hopper.getZPos());
            if (var8 != null) {
                return func_96114_a(par0Hopper, var8);
            }
        }
        return false;
    }
    
    private static boolean func_102012_a(final Hopper par0Hopper, final IInventory par1IInventory, final int par2, final int par3) {
        final ItemStack var4 = par1IInventory.getStackInSlot(par2);
        if (var4 != null && canExtractItemFromInventory(par1IInventory, var4, par2, par3)) {
            final ItemStack var5 = var4.copy();
            final ItemStack var6 = insertStack(par0Hopper, par1IInventory.decrStackSize(par2, 1), -1);
            if (var6 == null || var6.stackSize == 0) {
                par1IInventory.onInventoryChanged();
                return true;
            }
            par1IInventory.setInventorySlotContents(par2, var5);
        }
        return false;
    }
    
    public static boolean func_96114_a(final IInventory par0IInventory, final EntityItem par1EntityItem) {
        boolean var2 = false;
        if (par1EntityItem == null) {
            return false;
        }
        final ItemStack var3 = par1EntityItem.getEntityItem().copy();
        final ItemStack var4 = insertStack(par0IInventory, var3, -1);
        if (var4 != null && var4.stackSize != 0) {
            par1EntityItem.setEntityItemStack(var4);
        }
        else {
            var2 = true;
            par1EntityItem.setDead();
        }
        return var2;
    }
    
    public static ItemStack insertStack(final IInventory par1IInventory, ItemStack par2ItemStack, final int par3) {
        if (par1IInventory instanceof ISidedInventory && par3 > -1) {
            final ISidedInventory var6 = (ISidedInventory)par1IInventory;
            final int[] var7 = var6.getAccessibleSlotsFromSide(par3);
            for (int var8 = 0; var8 < var7.length && par2ItemStack != null; par2ItemStack = func_102014_c(par1IInventory, par2ItemStack, var7[var8], par3), ++var8) {
                if (par2ItemStack.stackSize <= 0) {
                    break;
                }
            }
        }
        else {
            for (int var9 = par1IInventory.getSizeInventory(), var10 = 0; var10 < var9 && par2ItemStack != null && par2ItemStack.stackSize > 0; par2ItemStack = func_102014_c(par1IInventory, par2ItemStack, var10, par3), ++var10) {}
        }
        if (par2ItemStack != null && par2ItemStack.stackSize == 0) {
            par2ItemStack = null;
        }
        return par2ItemStack;
    }
    
    private static boolean func_102015_a(final IInventory par0IInventory, final ItemStack par1ItemStack, final int par2, final int par3) {
        return par0IInventory.isStackValidForSlot(par2, par1ItemStack) && (!(par0IInventory instanceof ISidedInventory) || ((ISidedInventory)par0IInventory).canInsertItem(par2, par1ItemStack, par3));
    }
    
    private static boolean canExtractItemFromInventory(final IInventory par0IInventory, final ItemStack par1ItemStack, final int par2, final int par3) {
        return !(par0IInventory instanceof ISidedInventory) || ((ISidedInventory)par0IInventory).canExtractItem(par2, par1ItemStack, par3);
    }
    
    private static ItemStack func_102014_c(final IInventory par0IInventory, ItemStack par1ItemStack, final int par2, final int par3) {
        final ItemStack var4 = par0IInventory.getStackInSlot(par2);
        if (func_102015_a(par0IInventory, par1ItemStack, par2, par3)) {
            boolean var5 = false;
            if (var4 == null) {
                par0IInventory.setInventorySlotContents(par2, par1ItemStack);
                par1ItemStack = null;
                var5 = true;
            }
            else if (areItemStacksEqualItem(var4, par1ItemStack)) {
                final int var6 = par1ItemStack.getMaxStackSize() - var4.stackSize;
                final int var7 = Math.min(par1ItemStack.stackSize, var6);
                final ItemStack itemStack = par1ItemStack;
                itemStack.stackSize -= var7;
                final ItemStack itemStack2 = var4;
                itemStack2.stackSize += var7;
                var5 = (var7 > 0);
            }
            if (var5) {
                if (par0IInventory instanceof TileEntityHopper) {
                    ((TileEntityHopper)par0IInventory).setTransferCooldown(8);
                }
                par0IInventory.onInventoryChanged();
            }
        }
        return par1ItemStack;
    }
    
    private IInventory getOutputInventory() {
        final int var1 = BlockHopper.getDirectionFromMetadata(this.getBlockMetadata());
        return getInventoryAtLocation(this.getWorldObj(), this.xCoord + Facing.offsetsXForSide[var1], this.yCoord + Facing.offsetsYForSide[var1], this.zCoord + Facing.offsetsZForSide[var1]);
    }
    
    public static IInventory getInventoryAboveHopper(final Hopper par0Hopper) {
        return getInventoryAtLocation(par0Hopper.getWorldObj(), par0Hopper.getXPos(), par0Hopper.getYPos() + 1.0, par0Hopper.getZPos());
    }
    
    public static EntityItem func_96119_a(final World par0World, final double par1, final double par3, final double par5) {
        final List var7 = par0World.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5, par1 + 1.0, par3 + 1.0, par5 + 1.0), IEntitySelector.selectAnything);
        return (var7.size() > 0) ? var7.get(0) : null;
    }
    
    public static IInventory getInventoryAtLocation(final World par0World, final double par1, final double par3, final double par5) {
        IInventory var7 = null;
        final int var8 = MathHelper.floor_double(par1);
        final int var9 = MathHelper.floor_double(par3);
        final int var10 = MathHelper.floor_double(par5);
        final TileEntity var11 = par0World.getBlockTileEntity(var8, var9, var10);
        if (var11 != null && var11 instanceof IInventory) {
            var7 = (IInventory)var11;
            if (var7 instanceof TileEntityChest) {
                final int var12 = par0World.getBlockId(var8, var9, var10);
                final Block var13 = Block.blocksList[var12];
                if (var13 instanceof BlockChest) {
                    var7 = ((BlockChest)var13).getInventory(par0World, var8, var9, var10);
                }
            }
        }
        if (var7 == null) {
            final List var14 = par0World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(par1, par3, par5, par1 + 1.0, par3 + 1.0, par5 + 1.0), IEntitySelector.selectInventories);
            if (var14 != null && var14.size() > 0) {
                var7 = var14.get(par0World.rand.nextInt(var14.size()));
            }
        }
        return var7;
    }
    
    private static boolean areItemStacksEqualItem(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        return par1ItemStack.itemID == par2ItemStack.itemID && par1ItemStack.getItemDamage() == par2ItemStack.getItemDamage() && par1ItemStack.stackSize <= par1ItemStack.getMaxStackSize() && ItemStack.areItemStackTagsEqual(par1ItemStack, par2ItemStack);
    }
    
    @Override
    public double getXPos() {
        return this.xCoord;
    }
    
    @Override
    public double getYPos() {
        return this.yCoord;
    }
    
    @Override
    public double getZPos() {
        return this.zCoord;
    }
    
    public void setTransferCooldown(final int par1) {
        this.transferCooldown = par1;
    }
    
    public boolean isCoolingDown() {
        return this.transferCooldown > 0;
    }
}
