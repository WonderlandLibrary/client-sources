package net.minecraft.entity.player;

import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class InventoryPlayer implements IInventory
{
    public int currentItem;
    public ItemStack[] mainInventory;
    private static final String[] I;
    public EntityPlayer player;
    private ItemStack itemStack;
    public ItemStack[] armorInventory;
    public boolean inventoryChanged;
    
    @Override
    public String getName() {
        return InventoryPlayer.I[0x34 ^ 0x3C];
    }
    
    @Override
    public boolean hasCustomName() {
        return "".length() != 0;
    }
    
    public float getStrVsBlock(final Block block) {
        float n = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            n *= this.mainInventory[this.currentItem].getStrVsBlock(block);
        }
        return n;
    }
    
    public NBTTagList writeToNBT(final NBTTagList list) {
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte(InventoryPlayer.I[0x10 ^ 0x15], (byte)i);
                this.mainInventory[i].writeToNBT(nbtTagCompound);
                list.appendTag(nbtTagCompound);
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(InventoryPlayer.I[0x19 ^ 0x1F], (byte)(j + (0x1A ^ 0x7E)));
                this.armorInventory[j].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++j;
        }
        return list;
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    public void readFromNBT(final NBTTagList list) {
        this.mainInventory = new ItemStack[0xE7 ^ 0xC3];
        this.armorInventory = new ItemStack[0xB7 ^ 0xB3];
        int i = "".length();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            final int n = compoundTag.getByte(InventoryPlayer.I[0xBF ^ 0xB8]) & 152 + 204 - 302 + 201;
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(compoundTag);
            if (loadItemStackFromNBT != null) {
                if (n >= 0 && n < this.mainInventory.length) {
                    this.mainInventory[n] = loadItemStackFromNBT;
                }
                if (n >= (0x6F ^ 0xB) && n < this.armorInventory.length + (0x27 ^ 0x43)) {
                    this.armorInventory[n - (0x5E ^ 0x3A)] = loadItemStackFromNBT;
                }
            }
            ++i;
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.player.isDead) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSqToEntity(this.player) <= 64.0) {
            n = " ".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
    
    @Override
    public void markDirty() {
        this.inventoryChanged = (" ".length() != 0);
    }
    
    public void damageArmor(float n) {
        n /= 4.0f;
        if (n < 1.0f) {
            n = 1.0f;
        }
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < this.armorInventory.length) {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
                this.armorInventory[i].damageItem((int)n, this.player);
                if (this.armorInventory[i].stackSize == 0) {
                    this.armorInventory[i] = null;
                }
            }
            ++i;
        }
    }
    
    public void decrementAnimations() {
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null) {
                final ItemStack itemStack = this.mainInventory[i];
                final World worldObj = this.player.worldObj;
                final EntityPlayer player = this.player;
                final int n;
                int n2;
                if (this.currentItem == (n = i)) {
                    n2 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                itemStack.updateAnimation(worldObj, player, n, n2 != 0);
            }
            ++i;
        }
    }
    
    @Override
    public ItemStack getStackInSlot(int n) {
        ItemStack[] array = this.mainInventory;
        if (n >= array.length) {
            n -= array.length;
            array = this.armorInventory;
        }
        return array[n];
    }
    
    public int getTotalArmorValue() {
        int length = "".length();
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < this.armorInventory.length) {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
                length += ((ItemArmor)this.armorInventory[i].getItem()).damageReduceAmount;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x6F ^ 0x2F;
    }
    
    public boolean hasItemStack(final ItemStack itemStack) {
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.armorInventory.length) {
            if (this.armorInventory[i] != null && this.armorInventory[i].isItemEqual(itemStack)) {
                return " ".length() != 0;
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (j < this.mainInventory.length) {
            if (this.mainInventory[j] != null && this.mainInventory[j].isItemEqual(itemStack)) {
                return " ".length() != 0;
            }
            ++j;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x1B ^ 0x12])["".length()] = I("## %9\u0005g-82\u000fg0#w\u000b)2)9\u0016(65", "bGDLW");
        InventoryPlayer.I[" ".length()] = I("\u000f$ /Q$5,,\u0016f1!&\u0014\"", "FPEBq");
        InventoryPlayer.I["  ".length()] = I("\u0005-\u0006\fh\u0005\u001d", "LYcaH");
        InventoryPlayer.I["   ".length()] = I(">\u00052\u0019G\u0013\u0010#\u0015", "wqWtg");
        InventoryPlayer.I[0x87 ^ 0x83] = I("\n\u0010\f5K-\u0005\u0004=", "CdiXk");
        InventoryPlayer.I[0x1D ^ 0x18] = I("\u0006\u001f\u000e0", "UsaDG");
        InventoryPlayer.I[0x4E ^ 0x48] = I("?\n',", "lfHXc");
        InventoryPlayer.I[0xBE ^ 0xB9] = I("\u0004\u000b\u000e\u001d", "WgaiW");
        InventoryPlayer.I[0x77 ^ 0x7F] = I("\r(\u000b:)\u0007)\u0000<f\u0007)\u0013+&\u001a(\u00177", "nGeNH");
    }
    
    private int storeItemStack(final ItemStack itemStack) {
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemStack.getItem() && this.mainInventory[i].isStackable() && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getMetadata() == itemStack.getMetadata()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], itemStack)) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public ItemStack removeStackFromSlot(int n) {
        ItemStack[] array = this.mainInventory;
        if (n >= this.mainInventory.length) {
            array = this.armorInventory;
            n -= this.mainInventory.length;
        }
        if (array[n] != null) {
            final ItemStack itemStack = array[n];
            array[n] = null;
            return itemStack;
        }
        return null;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public boolean canHeldItemHarvest(final Block block) {
        if (block.getMaterial().isToolNotRequired()) {
            return " ".length() != 0;
        }
        final ItemStack stackInSlot = this.getStackInSlot(this.currentItem);
        int n;
        if (stackInSlot != null) {
            n = (stackInSlot.canHarvestBlock(block) ? 1 : 0);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void dropAllItems() {
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null) {
                this.player.dropItem(this.mainInventory[i], " ".length() != 0, "".length() != 0);
                this.mainInventory[i] = null;
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (j < this.armorInventory.length) {
            if (this.armorInventory[j] != null) {
                this.player.dropItem(this.armorInventory[j], " ".length() != 0, "".length() != 0);
                this.armorInventory[j] = null;
            }
            ++j;
        }
    }
    
    public boolean hasItem(final Item item) {
        if (this.getInventorySlotContainItem(item) >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    public ItemStack getCurrentItem() {
        ItemStack itemStack;
        if (this.currentItem < (0x44 ^ 0x4D) && this.currentItem >= 0) {
            itemStack = this.mainInventory[this.currentItem];
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            itemStack = null;
        }
        return itemStack;
    }
    
    @Override
    public void setInventorySlotContents(int n, final ItemStack itemStack) {
        ItemStack[] array = this.mainInventory;
        if (n >= array.length) {
            n -= array.length;
            array = this.armorInventory;
        }
        array[n] = itemStack;
    }
    
    public ItemStack armorItemInSlot(final int n) {
        return this.armorInventory[n];
    }
    
    public static int getHotbarSize() {
        return 0x18 ^ 0x11;
    }
    
    private int getInventorySlotContainItemAndDamage(final Item item, final int n) {
        int i = "".length();
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == item && this.mainInventory[i].getMetadata() == n) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    public int clearMatchingItems(final Item item, final int n, final int n2, final NBTTagCompound nbtTagCompound) {
        int length = "".length();
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            final ItemStack itemStack = this.mainInventory[i];
            if (itemStack != null && (item == null || itemStack.getItem() == item) && (n <= -" ".length() || itemStack.getMetadata() == n) && (nbtTagCompound == null || NBTUtil.func_181123_a(nbtTagCompound, itemStack.getTagCompound(), " ".length() != 0))) {
                int n3;
                if (n2 <= 0) {
                    n3 = itemStack.stackSize;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n3 = Math.min(n2 - length, itemStack.stackSize);
                }
                final int n4 = n3;
                length += n4;
                if (n2 != 0) {
                    final ItemStack itemStack2 = this.mainInventory[i];
                    itemStack2.stackSize -= n4;
                    if (this.mainInventory[i].stackSize == 0) {
                        this.mainInventory[i] = null;
                    }
                    if (n2 > 0 && length >= n2) {
                        return length;
                    }
                }
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (j < this.armorInventory.length) {
            final ItemStack itemStack3 = this.armorInventory[j];
            if (itemStack3 != null && (item == null || itemStack3.getItem() == item) && (n <= -" ".length() || itemStack3.getMetadata() == n) && (nbtTagCompound == null || NBTUtil.func_181123_a(nbtTagCompound, itemStack3.getTagCompound(), "".length() != 0))) {
                int n5;
                if (n2 <= 0) {
                    n5 = itemStack3.stackSize;
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    n5 = Math.min(n2 - length, itemStack3.stackSize);
                }
                final int n6 = n5;
                length += n6;
                if (n2 != 0) {
                    final ItemStack itemStack4 = this.armorInventory[j];
                    itemStack4.stackSize -= n6;
                    if (this.armorInventory[j].stackSize == 0) {
                        this.armorInventory[j] = null;
                    }
                    if (n2 > 0 && length >= n2) {
                        return length;
                    }
                }
            }
            ++j;
        }
        if (this.itemStack != null) {
            if (item != null && this.itemStack.getItem() != item) {
                return length;
            }
            if (n > -" ".length() && this.itemStack.getMetadata() != n) {
                return length;
            }
            if (nbtTagCompound != null && !NBTUtil.func_181123_a(nbtTagCompound, this.itemStack.getTagCompound(), "".length() != 0)) {
                return length;
            }
            int n7;
            if (n2 <= 0) {
                n7 = this.itemStack.stackSize;
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                n7 = Math.min(n2 - length, this.itemStack.stackSize);
            }
            final int n8 = n7;
            length += n8;
            if (n2 != 0) {
                final ItemStack itemStack5 = this.itemStack;
                itemStack5.stackSize -= n8;
                if (this.itemStack.stackSize == 0) {
                    this.itemStack = null;
                }
                if (n2 > 0 && length >= n2) {
                    return length;
                }
            }
        }
        return length;
    }
    
    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + (0x59 ^ 0x5D);
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public ItemStack decrStackSize(int n, final int n2) {
        ItemStack[] array = this.mainInventory;
        if (n >= this.mainInventory.length) {
            array = this.armorInventory;
            n -= this.mainInventory.length;
        }
        if (array[n] == null) {
            return null;
        }
        if (array[n].stackSize <= n2) {
            final ItemStack itemStack = array[n];
            array[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = array[n].splitStack(n2);
        if (array[n].stackSize == 0) {
            array[n] = null;
        }
        return splitStack;
    }
    
    static {
        I();
    }
    
    public void copyInventory(final InventoryPlayer inventoryPlayer) {
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            this.mainInventory[i] = ItemStack.copyItemStack(inventoryPlayer.mainInventory[i]);
            ++i;
        }
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < this.armorInventory.length) {
            this.armorInventory[j] = ItemStack.copyItemStack(inventoryPlayer.armorInventory[j]);
            ++j;
        }
        this.currentItem = inventoryPlayer.currentItem;
    }
    
    public int getFirstEmptyStack() {
        int i = "".length();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] == null) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    public void setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    public InventoryPlayer(final EntityPlayer player) {
        this.mainInventory = new ItemStack[0xAD ^ 0x89];
        this.armorInventory = new ItemStack[0x67 ^ 0x63];
        this.player = player;
    }
    
    private int getInventorySlotContainItem(final Item item) {
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == item) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    public void setCurrentItem(final Item item, final int n, final boolean b, final boolean b2) {
        final ItemStack currentItem = this.getCurrentItem();
        int n2;
        if (b) {
            n2 = this.getInventorySlotContainItemAndDamage(item, n);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n2 = this.getInventorySlotContainItem(item);
        }
        final int currentItem2 = n2;
        if (currentItem2 >= 0 && currentItem2 < (0x50 ^ 0x59)) {
            this.currentItem = currentItem2;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else if (b2 && item != null) {
            final int firstEmptyStack = this.getFirstEmptyStack();
            if (firstEmptyStack >= 0 && firstEmptyStack < (0x95 ^ 0x9C)) {
                this.currentItem = firstEmptyStack;
            }
            if (currentItem == null || !currentItem.isItemEnchantable() || this.getInventorySlotContainItemAndDamage(currentItem.getItem(), currentItem.getItemDamage()) != this.currentItem) {
                final int inventorySlotContainItemAndDamage = this.getInventorySlotContainItemAndDamage(item, n);
                int n3;
                if (inventorySlotContainItemAndDamage >= 0) {
                    n3 = this.mainInventory[inventorySlotContainItemAndDamage].stackSize;
                    this.mainInventory[inventorySlotContainItemAndDamage] = this.mainInventory[this.currentItem];
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    n3 = " ".length();
                }
                this.mainInventory[this.currentItem] = new ItemStack(item, n3, n);
            }
        }
    }
    
    public boolean consumeInventoryItem(final Item item) {
        final int inventorySlotContainItem = this.getInventorySlotContainItem(item);
        if (inventorySlotContainItem < 0) {
            return "".length() != 0;
        }
        final ItemStack itemStack = this.mainInventory[inventorySlotContainItem];
        if ((itemStack.stackSize -= " ".length()) <= 0) {
            this.mainInventory[inventorySlotContainItem] = null;
        }
        return " ".length() != 0;
    }
    
    private int storePartialItemStack(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        final int stackSize = itemStack.stackSize;
        int n = this.storeItemStack(itemStack);
        if (n < 0) {
            n = this.getFirstEmptyStack();
        }
        if (n < 0) {
            return stackSize;
        }
        if (this.mainInventory[n] == null) {
            this.mainInventory[n] = new ItemStack(item, "".length(), itemStack.getMetadata());
            if (itemStack.hasTagCompound()) {
                this.mainInventory[n].setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            }
        }
        int n2;
        if ((n2 = stackSize) > this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize) {
            n2 = this.mainInventory[n].getMaxStackSize() - this.mainInventory[n].stackSize;
        }
        if (n2 > this.getInventoryStackLimit() - this.mainInventory[n].stackSize) {
            n2 = this.getInventoryStackLimit() - this.mainInventory[n].stackSize;
        }
        if (n2 == 0) {
            return stackSize;
        }
        final int n3 = stackSize - n2;
        final ItemStack itemStack2 = this.mainInventory[n];
        itemStack2.stackSize += n2;
        this.mainInventory[n].animationsToGo = (0x74 ^ 0x71);
        return n3;
    }
    
    public void changeCurrentItem(int length) {
        if (length > 0) {
            length = " ".length();
        }
        if (length < 0) {
            length = -" ".length();
        }
        this.currentItem -= length;
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (this.currentItem < 0) {
            this.currentItem += (0x65 ^ 0x6C);
        }
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (this.currentItem >= (0x47 ^ 0x4E)) {
            this.currentItem -= (0x60 ^ 0x69);
        }
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    public boolean addItemStackToInventory(final ItemStack itemStack) {
        if (itemStack != null && itemStack.stackSize != 0 && itemStack.getItem() != null) {
            try {
                if (itemStack.isItemDamaged()) {
                    final int firstEmptyStack = this.getFirstEmptyStack();
                    if (firstEmptyStack >= 0) {
                        this.mainInventory[firstEmptyStack] = ItemStack.copyItemStack(itemStack);
                        this.mainInventory[firstEmptyStack].animationsToGo = (0xC2 ^ 0xC7);
                        itemStack.stackSize = "".length();
                        return " ".length() != 0;
                    }
                    if (this.player.capabilities.isCreativeMode) {
                        itemStack.stackSize = "".length();
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
                else {
                    int stackSize;
                    do {
                        stackSize = itemStack.stackSize;
                        itemStack.stackSize = this.storePartialItemStack(itemStack);
                    } while (itemStack.stackSize > 0 && itemStack.stackSize < stackSize);
                    if (itemStack.stackSize == stackSize && this.player.capabilities.isCreativeMode) {
                        itemStack.stackSize = "".length();
                        return " ".length() != 0;
                    }
                    if (itemStack.stackSize < stackSize) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, InventoryPlayer.I["".length()]);
                final CrashReportCategory category = crashReport.makeCategory(InventoryPlayer.I[" ".length()]);
                category.addCrashSection(InventoryPlayer.I["  ".length()], Item.getIdFromItem(itemStack.getItem()));
                category.addCrashSection(InventoryPlayer.I["   ".length()], itemStack.getMetadata());
                category.addCrashSectionCallable(InventoryPlayer.I[0x35 ^ 0x31], new Callable<String>(this, itemStack) {
                    final InventoryPlayer this$0;
                    private final ItemStack val$itemStackIn;
                    
                    @Override
                    public String call() throws Exception {
                        return this.val$itemStackIn.getDisplayName();
                    }
                    
                    private static String I(final String s, final String s2) {
                        final StringBuilder sb = new StringBuilder();
                        final char[] charArray = s2.toCharArray();
                        int length = "".length();
                        final char[] charArray2 = s.toCharArray();
                        final int length2 = charArray2.length;
                        int i = "".length();
                        while (i < length2) {
                            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                            ++length;
                            ++i;
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                });
                throw new ReportedException(crashReport);
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (i < this.mainInventory.length) {
            this.mainInventory[i] = null;
            ++i;
        }
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < this.armorInventory.length) {
            this.armorInventory[j] = null;
            ++j;
        }
    }
}
