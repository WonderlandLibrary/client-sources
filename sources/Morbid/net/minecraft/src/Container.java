package net.minecraft.src;

import java.util.*;

public abstract class Container
{
    public List inventoryItemStacks;
    public List inventorySlots;
    public int windowId;
    private short transactionID;
    private int field_94535_f;
    private int field_94536_g;
    private final Set field_94537_h;
    protected List crafters;
    private Set playerList;
    
    public Container() {
        this.inventoryItemStacks = new ArrayList();
        this.inventorySlots = new ArrayList();
        this.windowId = 0;
        this.transactionID = 0;
        this.field_94535_f = -1;
        this.field_94536_g = 0;
        this.field_94537_h = new HashSet();
        this.crafters = new ArrayList();
        this.playerList = new HashSet();
    }
    
    protected Slot addSlotToContainer(final Slot par1Slot) {
        par1Slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(par1Slot);
        this.inventoryItemStacks.add(null);
        return par1Slot;
    }
    
    public void addCraftingToCrafters(final ICrafting par1ICrafting) {
        if (this.crafters.contains(par1ICrafting)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.crafters.add(par1ICrafting);
        par1ICrafting.sendContainerAndContentsToPlayer(this, this.getInventory());
        this.detectAndSendChanges();
    }
    
    public void removeCraftingFromCrafters(final ICrafting par1ICrafting) {
        this.crafters.remove(par1ICrafting);
    }
    
    public List getInventory() {
        final ArrayList var1 = new ArrayList();
        for (int var2 = 0; var2 < this.inventorySlots.size(); ++var2) {
            var1.add(this.inventorySlots.get(var2).getStack());
        }
        return var1;
    }
    
    public void detectAndSendChanges() {
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
            final ItemStack var2 = this.inventorySlots.get(var1).getStack();
            ItemStack var3 = this.inventoryItemStacks.get(var1);
            if (!ItemStack.areItemStacksEqual(var3, var2)) {
                var3 = ((var2 == null) ? null : var2.copy());
                this.inventoryItemStacks.set(var1, var3);
                for (int var4 = 0; var4 < this.crafters.size(); ++var4) {
                    this.crafters.get(var4).sendSlotContents(this, var1, var3);
                }
            }
        }
    }
    
    public boolean enchantItem(final EntityPlayer par1EntityPlayer, final int par2) {
        return false;
    }
    
    public Slot getSlotFromInventory(final IInventory par1IInventory, final int par2) {
        for (int var3 = 0; var3 < this.inventorySlots.size(); ++var3) {
            final Slot var4 = this.inventorySlots.get(var3);
            if (var4.isSlotInInventory(par1IInventory, par2)) {
                return var4;
            }
        }
        return null;
    }
    
    public Slot getSlot(final int par1) {
        return this.inventorySlots.get(par1);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        final Slot var3 = this.inventorySlots.get(par2);
        return (var3 != null) ? var3.getStack() : null;
    }
    
    public ItemStack slotClick(final int par1, final int par2, final int par3, final EntityPlayer par4EntityPlayer) {
        ItemStack var5 = null;
        final InventoryPlayer var6 = par4EntityPlayer.inventory;
        if (par3 == 5) {
            final int var7 = this.field_94536_g;
            this.field_94536_g = func_94532_c(par2);
            if ((var7 != 1 || this.field_94536_g != 2) && var7 != this.field_94536_g) {
                this.func_94533_d();
            }
            else if (var6.getItemStack() == null) {
                this.func_94533_d();
            }
            else if (this.field_94536_g == 0) {
                this.field_94535_f = func_94529_b(par2);
                if (func_94528_d(this.field_94535_f)) {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                }
                else {
                    this.func_94533_d();
                }
            }
            else if (this.field_94536_g == 1) {
                final Slot var8 = this.inventorySlots.get(par1);
                if (var8 != null && func_94527_a(var8, var6.getItemStack(), true) && var8.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.field_94537_h.size() && this.func_94531_b(var8)) {
                    this.field_94537_h.add(var8);
                }
            }
            else if (this.field_94536_g == 2) {
                if (!this.field_94537_h.isEmpty()) {
                    ItemStack var9 = var6.getItemStack().copy();
                    int var10 = var6.getItemStack().stackSize;
                    for (final Slot var12 : this.field_94537_h) {
                        if (var12 != null && func_94527_a(var12, var6.getItemStack(), true) && var12.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.field_94537_h.size() && this.func_94531_b(var12)) {
                            final ItemStack var13 = var9.copy();
                            final int var14 = var12.getHasStack() ? var12.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, var13, var14);
                            if (var13.stackSize > var13.getMaxStackSize()) {
                                var13.stackSize = var13.getMaxStackSize();
                            }
                            if (var13.stackSize > var12.getSlotStackLimit()) {
                                var13.stackSize = var12.getSlotStackLimit();
                            }
                            var10 -= var13.stackSize - var14;
                            var12.putStack(var13);
                        }
                    }
                    var9.stackSize = var10;
                    if (var9.stackSize <= 0) {
                        var9 = null;
                    }
                    var6.setItemStack(var9);
                }
                this.func_94533_d();
            }
            else {
                this.func_94533_d();
            }
        }
        else if (this.field_94536_g != 0) {
            this.func_94533_d();
        }
        else if ((par3 == 0 || par3 == 1) && (par2 == 0 || par2 == 1)) {
            if (par1 == -999) {
                if (var6.getItemStack() != null && par1 == -999) {
                    if (par2 == 0) {
                        par4EntityPlayer.dropPlayerItem(var6.getItemStack());
                        var6.setItemStack(null);
                    }
                    if (par2 == 1) {
                        par4EntityPlayer.dropPlayerItem(var6.getItemStack().splitStack(1));
                        if (var6.getItemStack().stackSize == 0) {
                            var6.setItemStack(null);
                        }
                    }
                }
            }
            else if (par3 == 1) {
                if (par1 < 0) {
                    return null;
                }
                final Slot var15 = this.inventorySlots.get(par1);
                if (var15 != null && var15.canTakeStack(par4EntityPlayer)) {
                    final ItemStack var9 = this.transferStackInSlot(par4EntityPlayer, par1);
                    if (var9 != null) {
                        final int var10 = var9.itemID;
                        var5 = var9.copy();
                        if (var15 != null && var15.getStack() != null && var15.getStack().itemID == var10) {
                            this.retrySlotClick(par1, par2, true, par4EntityPlayer);
                        }
                    }
                }
            }
            else {
                if (par1 < 0) {
                    return null;
                }
                final Slot var15 = this.inventorySlots.get(par1);
                if (var15 != null) {
                    ItemStack var9 = var15.getStack();
                    final ItemStack var16 = var6.getItemStack();
                    if (var9 != null) {
                        var5 = var9.copy();
                    }
                    if (var9 == null) {
                        if (var16 != null && var15.isItemValid(var16)) {
                            int var17 = (par2 == 0) ? var16.stackSize : 1;
                            if (var17 > var15.getSlotStackLimit()) {
                                var17 = var15.getSlotStackLimit();
                            }
                            var15.putStack(var16.splitStack(var17));
                            if (var16.stackSize == 0) {
                                var6.setItemStack(null);
                            }
                        }
                    }
                    else if (var15.canTakeStack(par4EntityPlayer)) {
                        if (var16 == null) {
                            final int var17 = (par2 == 0) ? var9.stackSize : ((var9.stackSize + 1) / 2);
                            final ItemStack var18 = var15.decrStackSize(var17);
                            var6.setItemStack(var18);
                            if (var9.stackSize == 0) {
                                var15.putStack(null);
                            }
                            var15.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
                        }
                        else if (var15.isItemValid(var16)) {
                            if (var9.itemID == var16.itemID && var9.getItemDamage() == var16.getItemDamage() && ItemStack.areItemStackTagsEqual(var9, var16)) {
                                int var17 = (par2 == 0) ? var16.stackSize : 1;
                                if (var17 > var15.getSlotStackLimit() - var9.stackSize) {
                                    var17 = var15.getSlotStackLimit() - var9.stackSize;
                                }
                                if (var17 > var16.getMaxStackSize() - var9.stackSize) {
                                    var17 = var16.getMaxStackSize() - var9.stackSize;
                                }
                                var16.splitStack(var17);
                                if (var16.stackSize == 0) {
                                    var6.setItemStack(null);
                                }
                                final ItemStack itemStack = var9;
                                itemStack.stackSize += var17;
                            }
                            else if (var16.stackSize <= var15.getSlotStackLimit()) {
                                var15.putStack(var16);
                                var6.setItemStack(var9);
                            }
                        }
                        else if (var9.itemID == var16.itemID && var16.getMaxStackSize() > 1 && (!var9.getHasSubtypes() || var9.getItemDamage() == var16.getItemDamage()) && ItemStack.areItemStackTagsEqual(var9, var16)) {
                            final int var17 = var9.stackSize;
                            if (var17 > 0 && var17 + var16.stackSize <= var16.getMaxStackSize()) {
                                final ItemStack itemStack2 = var16;
                                itemStack2.stackSize += var17;
                                var9 = var15.decrStackSize(var17);
                                if (var9.stackSize == 0) {
                                    var15.putStack(null);
                                }
                                var15.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
                            }
                        }
                    }
                    var15.onSlotChanged();
                }
            }
        }
        else if (par3 == 2 && par2 >= 0 && par2 < 9) {
            final Slot var15 = this.inventorySlots.get(par1);
            if (var15.canTakeStack(par4EntityPlayer)) {
                final ItemStack var9 = var6.getStackInSlot(par2);
                boolean var19 = var9 == null || (var15.inventory == var6 && var15.isItemValid(var9));
                int var17 = -1;
                if (!var19) {
                    var17 = var6.getFirstEmptyStack();
                    var19 |= (var17 > -1);
                }
                if (var15.getHasStack() && var19) {
                    final ItemStack var18 = var15.getStack();
                    var6.setInventorySlotContents(par2, var18.copy());
                    if ((var15.inventory != var6 || !var15.isItemValid(var9)) && var9 != null) {
                        if (var17 > -1) {
                            var6.addItemStackToInventory(var9);
                            var15.decrStackSize(var18.stackSize);
                            var15.putStack(null);
                            var15.onPickupFromSlot(par4EntityPlayer, var18);
                        }
                    }
                    else {
                        var15.decrStackSize(var18.stackSize);
                        var15.putStack(var9);
                        var15.onPickupFromSlot(par4EntityPlayer, var18);
                    }
                }
                else if (!var15.getHasStack() && var9 != null && var15.isItemValid(var9)) {
                    var6.setInventorySlotContents(par2, null);
                    var15.putStack(var9);
                }
            }
        }
        else if (par3 == 3 && par4EntityPlayer.capabilities.isCreativeMode && var6.getItemStack() == null && par1 >= 0) {
            final Slot var15 = this.inventorySlots.get(par1);
            if (var15 != null && var15.getHasStack()) {
                final ItemStack var9 = var15.getStack().copy();
                var9.stackSize = var9.getMaxStackSize();
                var6.setItemStack(var9);
            }
        }
        else if (par3 == 4 && var6.getItemStack() == null && par1 >= 0) {
            final Slot var15 = this.inventorySlots.get(par1);
            if (var15 != null && var15.getHasStack() && var15.canTakeStack(par4EntityPlayer)) {
                final ItemStack var9 = var15.decrStackSize((par2 == 0) ? 1 : var15.getStack().stackSize);
                var15.onPickupFromSlot(par4EntityPlayer, var9);
                par4EntityPlayer.dropPlayerItem(var9);
            }
        }
        else if (par3 == 6 && par1 >= 0) {
            final Slot var15 = this.inventorySlots.get(par1);
            final ItemStack var9 = var6.getItemStack();
            if (var9 != null && (var15 == null || !var15.getHasStack() || !var15.canTakeStack(par4EntityPlayer))) {
                final int var10 = (par2 == 0) ? 0 : (this.inventorySlots.size() - 1);
                final int var17 = (par2 == 0) ? 1 : -1;
                for (int var20 = 0; var20 < 2; ++var20) {
                    for (int var21 = var10; var21 >= 0 && var21 < this.inventorySlots.size() && var9.stackSize < var9.getMaxStackSize(); var21 += var17) {
                        final Slot var22 = this.inventorySlots.get(var21);
                        if (var22.getHasStack() && func_94527_a(var22, var9, true) && var22.canTakeStack(par4EntityPlayer) && this.func_94530_a(var9, var22) && (var20 != 0 || var22.getStack().stackSize != var22.getStack().getMaxStackSize())) {
                            final int var23 = Math.min(var9.getMaxStackSize() - var9.stackSize, var22.getStack().stackSize);
                            final ItemStack var24 = var22.decrStackSize(var23);
                            final ItemStack itemStack3 = var9;
                            itemStack3.stackSize += var23;
                            if (var24.stackSize <= 0) {
                                var22.putStack(null);
                            }
                            var22.onPickupFromSlot(par4EntityPlayer, var24);
                        }
                    }
                }
            }
            this.detectAndSendChanges();
        }
        return var5;
    }
    
    public boolean func_94530_a(final ItemStack par1ItemStack, final Slot par2Slot) {
        return true;
    }
    
    protected void retrySlotClick(final int par1, final int par2, final boolean par3, final EntityPlayer par4EntityPlayer) {
        this.slotClick(par1, par2, 1, par4EntityPlayer);
    }
    
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        final InventoryPlayer var2 = par1EntityPlayer.inventory;
        if (var2.getItemStack() != null) {
            par1EntityPlayer.dropPlayerItem(var2.getItemStack());
            var2.setItemStack(null);
        }
    }
    
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.detectAndSendChanges();
    }
    
    public void putStackInSlot(final int par1, final ItemStack par2ItemStack) {
        this.getSlot(par1).putStack(par2ItemStack);
    }
    
    public void putStacksInSlots(final ItemStack[] par1ArrayOfItemStack) {
        for (int var2 = 0; var2 < par1ArrayOfItemStack.length; ++var2) {
            this.getSlot(var2).putStack(par1ArrayOfItemStack[var2]);
        }
    }
    
    public void updateProgressBar(final int par1, final int par2) {
    }
    
    public short getNextTransactionID(final InventoryPlayer par1InventoryPlayer) {
        return (short)(++this.transactionID);
    }
    
    public boolean isPlayerNotUsingContainer(final EntityPlayer par1EntityPlayer) {
        return !this.playerList.contains(par1EntityPlayer);
    }
    
    public void setPlayerIsPresent(final EntityPlayer par1EntityPlayer, final boolean par2) {
        if (par2) {
            this.playerList.remove(par1EntityPlayer);
        }
        else {
            this.playerList.add(par1EntityPlayer);
        }
    }
    
    public abstract boolean canInteractWith(final EntityPlayer p0);
    
    protected boolean mergeItemStack(final ItemStack par1ItemStack, final int par2, final int par3, final boolean par4) {
        boolean var5 = false;
        int var6 = par2;
        if (par4) {
            var6 = par3 - 1;
        }
        if (par1ItemStack.isStackable()) {
            while (par1ItemStack.stackSize > 0 && ((!par4 && var6 < par3) || (par4 && var6 >= par2))) {
                final Slot var7 = this.inventorySlots.get(var6);
                final ItemStack var8 = var7.getStack();
                if (var8 != null && var8.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, var8)) {
                    final int var9 = var8.stackSize + par1ItemStack.stackSize;
                    if (var9 <= par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize = 0;
                        var8.stackSize = var9;
                        var7.onSlotChanged();
                        var5 = true;
                    }
                    else if (var8.stackSize < par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - var8.stackSize;
                        var8.stackSize = par1ItemStack.getMaxStackSize();
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }
                if (par4) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        if (par1ItemStack.stackSize > 0) {
            if (par4) {
                var6 = par3 - 1;
            }
            else {
                var6 = par2;
            }
            while ((!par4 && var6 < par3) || (par4 && var6 >= par2)) {
                final Slot var7 = this.inventorySlots.get(var6);
                final ItemStack var8 = var7.getStack();
                if (var8 == null) {
                    var7.putStack(par1ItemStack.copy());
                    var7.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    var5 = true;
                    break;
                }
                if (par4) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        return var5;
    }
    
    public static int func_94529_b(final int par0) {
        return par0 >> 2 & 0x3;
    }
    
    public static int func_94532_c(final int par0) {
        return par0 & 0x3;
    }
    
    public static int func_94534_d(final int par0, final int par1) {
        return (par0 & 0x3) | (par1 & 0x3) << 2;
    }
    
    public static boolean func_94528_d(final int par0) {
        return par0 == 0 || par0 == 1;
    }
    
    protected void func_94533_d() {
        this.field_94536_g = 0;
        this.field_94537_h.clear();
    }
    
    public static boolean func_94527_a(final Slot par0Slot, final ItemStack par1ItemStack, final boolean par2) {
        boolean var3 = par0Slot == null || !par0Slot.getHasStack();
        if (par0Slot != null && par0Slot.getHasStack() && par1ItemStack != null && par1ItemStack.isItemEqual(par0Slot.getStack()) && ItemStack.areItemStackTagsEqual(par0Slot.getStack(), par1ItemStack)) {
            final int var4 = par2 ? 0 : par1ItemStack.stackSize;
            var3 |= (par0Slot.getStack().stackSize + var4 <= par1ItemStack.getMaxStackSize());
        }
        return var3;
    }
    
    public static void func_94525_a(final Set par0Set, final int par1, final ItemStack par2ItemStack, final int par3) {
        switch (par1) {
            case 0: {
                par2ItemStack.stackSize = MathHelper.floor_float(par2ItemStack.stackSize / par0Set.size());
                break;
            }
            case 1: {
                par2ItemStack.stackSize = 1;
                break;
            }
        }
        par2ItemStack.stackSize += par3;
    }
    
    public boolean func_94531_b(final Slot par1Slot) {
        return true;
    }
    
    public static int calcRedstoneFromInventory(final IInventory par0IInventory) {
        if (par0IInventory == null) {
            return 0;
        }
        int var1 = 0;
        float var2 = 0.0f;
        for (int var3 = 0; var3 < par0IInventory.getSizeInventory(); ++var3) {
            final ItemStack var4 = par0IInventory.getStackInSlot(var3);
            if (var4 != null) {
                var2 += var4.stackSize / Math.min(par0IInventory.getInventoryStackLimit(), var4.getMaxStackSize());
                ++var1;
            }
        }
        var2 /= par0IInventory.getSizeInventory();
        return MathHelper.floor_float(var2 * 14.0f) + ((var1 > 0) ? 1 : 0);
    }
}
