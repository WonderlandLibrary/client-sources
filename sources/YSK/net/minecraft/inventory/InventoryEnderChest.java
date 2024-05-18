package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class InventoryEnderChest extends InventoryBasic
{
    private static final String[] I;
    private TileEntityEnderChest associatedChest;
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.closeChest();
        }
        super.closeInventory(entityPlayer);
        this.associatedChest = null;
    }
    
    public NBTTagList saveInventoryToNBT() {
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.getSizeInventory()) {
            final ItemStack stackInSlot = this.getStackInSlot(i);
            if (stackInSlot != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte(InventoryEnderChest.I["  ".length()], (byte)i);
                stackInSlot.writeToNBT(nbtTagCompound);
                list.appendTag(nbtTagCompound);
            }
            ++i;
        }
        return list;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.associatedChest != null && !this.associatedChest.canBeUsed(entityPlayer)) {
            n = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            n = (super.isUseableByPlayer(entityPlayer) ? 1 : 0);
        }
        return n != 0;
    }
    
    public void loadInventoryFromNBT(final NBTTagList list) {
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.getSizeInventory()) {
            this.setInventorySlotContents(i, null);
            ++i;
        }
        int j = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (j < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(j);
            final int n = compoundTag.getByte(InventoryEnderChest.I[" ".length()]) & 32 + 44 + 79 + 100;
            if (n >= 0 && n < this.getSizeInventory()) {
                this.setInventorySlotContents(n, ItemStack.loadItemStackFromNBT(compoundTag));
            }
            ++j;
        }
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (this.associatedChest != null) {
            this.associatedChest.openChest();
        }
        super.openInventory(entityPlayer);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\b\t !\u0006\u0002\b+'I\u000e\b*0\u0015\b\u000e+&\u0013", "kfNUg");
        InventoryEnderChest.I[" ".length()] = I("\u0010%?\u0018", "CIPlg");
        InventoryEnderChest.I["  ".length()] = I("9\u001f\u0006\u0013", "jsigF");
    }
    
    public void setChestTileEntity(final TileEntityEnderChest associatedChest) {
        this.associatedChest = associatedChest;
    }
    
    static {
        I();
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public InventoryEnderChest() {
        super(InventoryEnderChest.I["".length()], "".length() != 0, 0x5E ^ 0x45);
    }
}
