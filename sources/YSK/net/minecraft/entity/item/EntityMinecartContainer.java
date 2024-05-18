package net.minecraft.entity.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer
{
    private static final String[] I;
    private ItemStack[] minecartContainerItems;
    private boolean dropContentsWhenDead;
    
    @Override
    public boolean isLocked() {
        return "".length() != 0;
    }
    
    @Override
    public void travelToDimension(final int n) {
        this.dropContentsWhenDead = ("".length() != 0);
        super.travelToDimension(n);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(EntityMinecartContainer.I[0xA7 ^ 0xA3], 0x64 ^ 0x6E);
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final int n = compoundTag.getByte(EntityMinecartContainer.I[0x81 ^ 0x84]) & 235 + 251 - 424 + 193;
            if (n >= 0 && n < this.minecartContainerItems.length) {
                this.minecartContainerItems[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.minecartContainerItems[n] == null) {
            return null;
        }
        if (this.minecartContainerItems[n].stackSize <= n2) {
            final ItemStack itemStack = this.minecartContainerItems[n];
            this.minecartContainerItems[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.minecartContainerItems[n].splitStack(n2);
        if (this.minecartContainerItems[n].stackSize == 0) {
            this.minecartContainerItems[n] = null;
        }
        return splitStack;
    }
    
    public EntityMinecartContainer(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.minecartContainerItems = new ItemStack[0x7A ^ 0x5E];
        this.dropContentsWhenDead = (" ".length() != 0);
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
    }
    
    @Override
    public void setDead() {
        if (this.dropContentsWhenDead) {
            InventoryHelper.func_180176_a(this.worldObj, this, this);
        }
        super.setDead();
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public String getName() {
        String customNameTag;
        if (this.hasCustomName()) {
            customNameTag = this.getCustomNameTag();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            customNameTag = EntityMinecartContainer.I[" ".length()];
        }
        return customNameTag;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    static {
        I();
    }
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.minecartContainerItems[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSqToEntity(this) <= 64.0) {
            n = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public EntityMinecartContainer(final World world) {
        super(world);
        this.minecartContainerItems = new ItemStack[0x24 ^ 0x0];
        this.dropContentsWhenDead = (" ".length() != 0);
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < this.minecartContainerItems.length) {
            this.minecartContainerItems[i] = null;
            ++i;
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            entityPlayer.displayGUIChest(this);
        }
        return " ".length() != 0;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x6D ^ 0x2D;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.minecartContainerItems[n];
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    private static void I() {
        (I = new String[0x2C ^ 0x2A])["".length()] = I("'\n\u0013\u0002>*\u0011/(8,\u0015%", "CeVlJ");
        EntityMinecartContainer.I[" ".length()] = I("\r#+\u0001;\u0007\" \u0007t\u0003%+\u00109\u000f>1", "nLEuZ");
        EntityMinecartContainer.I["  ".length()] = I("7'\u00016", "dKnBH");
        EntityMinecartContainer.I["   ".length()] = I("\f'3\t\u0003", "ESVdp");
        EntityMinecartContainer.I[0x92 ^ 0x96] = I("\u0011\u0005#(%", "XqFEV");
        EntityMinecartContainer.I[0xBC ^ 0xB9] = I("\u0010\n\u000b;", "CfdOr");
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void applyDrag() {
        final float n = 0.98f + ((0xC9 ^ 0xC6) - Container.calcRedstoneFromInventory(this)) * 0.001f;
        this.motionX *= n;
        this.motionY *= 0.0;
        this.motionZ *= n;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.worldObj.getGameRules().getBoolean(EntityMinecartContainer.I["".length()])) {
            InventoryHelper.func_180176_a(this.worldObj, this, this);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.minecartContainerItems.length) {
            if (this.minecartContainerItems[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(EntityMinecartContainer.I["  ".length()], (byte)i);
                this.minecartContainerItems[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(EntityMinecartContainer.I["   ".length()], list);
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.minecartContainerItems[n] != null) {
            final ItemStack itemStack = this.minecartContainerItems[n];
            this.minecartContainerItems[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }
}
