package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;

public class TileEntityDispenser extends TileEntityLockable implements IInventory
{
    private static final Random RNG;
    protected String customName;
    private static final String[] I;
    private ItemStack[] stacks;
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerDispenser(inventoryPlayer, this);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stacks[n] == null) {
            return null;
        }
        if (this.stacks[n].stackSize <= n2) {
            final ItemStack itemStack = this.stacks[n];
            this.stacks[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.stacks[n].splitStack(n2);
        if (this.stacks[n].stackSize == 0) {
            this.stacks[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (i < this.stacks.length) {
            if (this.stacks[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityDispenser.I[0xC4 ^ 0xC1], (byte)i);
                this.stacks[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(TileEntityDispenser.I[0x3 ^ 0x5], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityDispenser.I[0x79 ^ 0x7E], this.customName);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0xFC ^ 0xBC;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityDispenser.I[0x47 ^ 0x4F];
    }
    
    public TileEntityDispenser() {
        this.stacks = new ItemStack[0x86 ^ 0x8F];
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            customName = TileEntityDispenser.I["".length()];
        }
        return customName;
    }
    
    public int getDispenseSlot() {
        int n = -" ".length();
        int length = " ".length();
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < this.stacks.length) {
            if (this.stacks[i] != null && TileEntityDispenser.RNG.nextInt(length++) == 0) {
                n = i;
            }
            ++i;
        }
        return n;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < this.stacks.length) {
            this.stacks[i] = null;
            ++i;
        }
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x15 ^ 0x1C])["".length()] = I("\u0014\u0017<68\u001e\u001670w\u0013\u0011!2<\u0019\u000b70", "wxRBY");
        TileEntityDispenser.I[" ".length()] = I(":\u000e\u001d!\u0016", "szxLe");
        TileEntityDispenser.I["  ".length()] = I("\"\u0014\b\u0006", "qxgrG");
        TileEntityDispenser.I["   ".length()] = I("*\u0018\n>\u0003\u0004#\u0018'\t", "imyJl");
        TileEntityDispenser.I[0x9D ^ 0x99] = I("5\u0006\u001a\u00056\u001b=\b\u001c<", "vsiqY");
        TileEntityDispenser.I[0x9D ^ 0x98] = I("1#\u0018?", "bOwKk");
        TileEntityDispenser.I[0x59 ^ 0x5F] = I("\u0019\"\u0002*\u0017", "PVgGd");
        TileEntityDispenser.I[0xC2 ^ 0xC5] = I(".\u0011\n'\u001d\u0000*\u0018>\u0017", "mdySr");
        TileEntityDispenser.I[0xA1 ^ 0xA9] = I(";8\u0005\u0011\u0000$0\r\u0000Y28\u0018\u0004\u00068\"\u000e\u0006", "VQktc");
    }
    
    @Override
    public int getSizeInventory() {
        return 0x9A ^ 0x93;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stacks[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        RNG = new Random();
    }
    
    public int addItemStack(final ItemStack itemStack) {
        int i = "".length();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (i < this.stacks.length) {
            if (this.stacks[i] == null || this.stacks[i].getItem() == null) {
                this.setInventorySlotContents(i, itemStack);
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stacks[n] != null) {
            final ItemStack itemStack = this.stacks[n];
            this.stacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityDispenser.I[" ".length()], 0x28 ^ 0x22);
        this.stacks = new ItemStack[this.getSizeInventory()];
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final int n = compoundTag.getByte(TileEntityDispenser.I["  ".length()]) & 73 + 33 + 102 + 47;
            if (n >= 0 && n < this.stacks.length) {
                this.stacks[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
        if (nbtTagCompound.hasKey(TileEntityDispenser.I["   ".length()], 0xBF ^ 0xB7)) {
            this.customName = nbtTagCompound.getString(TileEntityDispenser.I[0x8C ^ 0x88]);
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.stacks[n];
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
}
