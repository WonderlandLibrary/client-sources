package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityHopper extends TileEntityLockable implements ITickable, IHopper
{
    private int transferCooldown;
    private ItemStack[] inventory;
    private String customName;
    private static final String[] I;
    
    public static IInventory getHopperInventory(final IHopper hopper) {
        return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0, hopper.getZPos());
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public double getYPos() {
        return this.pos.getY() + 0.5;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityHopper.I["".length()], 0xB ^ 0x1);
        this.inventory = new ItemStack[this.getSizeInventory()];
        if (nbtTagCompound.hasKey(TileEntityHopper.I[" ".length()], 0xA4 ^ 0xAC)) {
            this.customName = nbtTagCompound.getString(TileEntityHopper.I["  ".length()]);
        }
        this.transferCooldown = nbtTagCompound.getInteger(TileEntityHopper.I["   ".length()]);
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(TileEntityHopper.I[0x54 ^ 0x50]);
            if (byte1 >= 0 && byte1 < this.inventory.length) {
                this.inventory[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
    }
    
    @Override
    public void markDirty() {
        super.markDirty();
    }
    
    public boolean isOnTransferCooldown() {
        if (this.transferCooldown > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    public void setTransferCooldown(final int transferCooldown) {
        this.transferCooldown = transferCooldown;
    }
    
    public boolean mayTransfer() {
        if (this.transferCooldown <= " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.inventory[n] != null) {
            final ItemStack itemStack = this.inventory[n];
            this.inventory[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static boolean captureDroppedItems(final IHopper hopper) {
        final IInventory hopperInventory = getHopperInventory(hopper);
        if (hopperInventory != null) {
            final EnumFacing down = EnumFacing.DOWN;
            if (isInventoryEmpty(hopperInventory, down)) {
                return "".length() != 0;
            }
            if (hopperInventory instanceof ISidedInventory) {
                final int[] slotsForFace = ((ISidedInventory)hopperInventory).getSlotsForFace(down);
                int i = "".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                while (i < slotsForFace.length) {
                    if (pullItemFromSlot(hopper, hopperInventory, slotsForFace[i], down)) {
                        return " ".length() != 0;
                    }
                    ++i;
                }
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                final int sizeInventory = hopperInventory.getSizeInventory();
                int j = "".length();
                "".length();
                if (4 == 2) {
                    throw null;
                }
                while (j < sizeInventory) {
                    if (pullItemFromSlot(hopper, hopperInventory, j, down)) {
                        return " ".length() != 0;
                    }
                    ++j;
                }
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
        }
        else {
            final Iterator<EntityItem> iterator = func_181556_a(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0, hopper.getZPos()).iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (putDropInInventoryAllSlots(hopper, iterator.next())) {
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    public boolean updateHopper() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (!this.isOnTransferCooldown() && BlockHopper.isEnabled(this.getBlockMetadata())) {
                int n = "".length();
                if (!this.isEmpty()) {
                    n = (this.transferItemsOut() ? 1 : 0);
                }
                if (!this.isFull()) {
                    int n2;
                    if (!captureDroppedItems(this) && n == 0) {
                        n2 = "".length();
                        "".length();
                        if (1 == 2) {
                            throw null;
                        }
                    }
                    else {
                        n2 = " ".length();
                    }
                    n = n2;
                }
                if (n != 0) {
                    this.setTransferCooldown(0xBB ^ 0xB3);
                    this.markDirty();
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean isFull() {
        final ItemStack[] inventory;
        final int length = (inventory = this.inventory).length;
        int i = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i < length) {
            final ItemStack itemStack = inventory[i];
            if (itemStack == null || itemStack.stackSize != itemStack.getMaxStackSize()) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    private boolean isInventoryFull(final IInventory inventory, final EnumFacing enumFacing) {
        if (inventory instanceof ISidedInventory) {
            final ISidedInventory sidedInventory = (ISidedInventory)inventory;
            final int[] slotsForFace = sidedInventory.getSlotsForFace(enumFacing);
            int i = "".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (i < slotsForFace.length) {
                final ItemStack stackInSlot = sidedInventory.getStackInSlot(slotsForFace[i]);
                if (stackInSlot == null || stackInSlot.stackSize != stackInSlot.getMaxStackSize()) {
                    return "".length() != 0;
                }
                ++i;
            }
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            final int sizeInventory = inventory.getSizeInventory();
            int j = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (j < sizeInventory) {
                final ItemStack stackInSlot2 = inventory.getStackInSlot(j);
                if (stackInSlot2 == null || stackInSlot2.stackSize != stackInSlot2.getMaxStackSize()) {
                    return "".length() != 0;
                }
                ++j;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.inventory[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public double getZPos() {
        return this.pos.getZ() + 0.5;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    private static boolean isInventoryEmpty(final IInventory inventory, final EnumFacing enumFacing) {
        if (inventory instanceof ISidedInventory) {
            final ISidedInventory sidedInventory = (ISidedInventory)inventory;
            final int[] slotsForFace = sidedInventory.getSlotsForFace(enumFacing);
            int i = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (i < slotsForFace.length) {
                if (sidedInventory.getStackInSlot(slotsForFace[i]) != null) {
                    return "".length() != 0;
                }
                ++i;
            }
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            final int sizeInventory = inventory.getSizeInventory();
            int j = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (j < sizeInventory) {
                if (inventory.getStackInSlot(j) != null) {
                    return "".length() != 0;
                }
                ++j;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < this.inventory.length) {
            this.inventory[i] = null;
            ++i;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (i < this.inventory.length) {
            if (this.inventory[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityHopper.I[0x2F ^ 0x2A], (byte)i);
                this.inventory[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(TileEntityHopper.I[0x1A ^ 0x1C], list);
        nbtTagCompound.setInteger(TileEntityHopper.I[0x1C ^ 0x1B], this.transferCooldown);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityHopper.I[0x32 ^ 0x3A], this.customName);
        }
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null && this.customName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean transferItemsOut() {
        final IInventory inventoryForHopperTransfer = this.getInventoryForHopperTransfer();
        if (inventoryForHopperTransfer == null) {
            return "".length() != 0;
        }
        final EnumFacing opposite = BlockHopper.getFacing(this.getBlockMetadata()).getOpposite();
        if (this.isInventoryFull(inventoryForHopperTransfer, opposite)) {
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.getSizeInventory()) {
            if (this.getStackInSlot(i) != null) {
                final ItemStack copy = this.getStackInSlot(i).copy();
                final ItemStack putStackInInventoryAllSlots = putStackInInventoryAllSlots(inventoryForHopperTransfer, this.decrStackSize(i, " ".length()), opposite);
                if (putStackInInventoryAllSlots == null || putStackInInventoryAllSlots.stackSize == 0) {
                    inventoryForHopperTransfer.markDirty();
                    return " ".length() != 0;
                }
                this.setInventorySlotContents(i, copy);
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public static IInventory getInventoryAtPosition(final World world, final double n, final double n2, final double n3) {
        IInventory lockableContainer = null;
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block.hasTileEntity()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof IInventory) {
                lockableContainer = (IInventory)tileEntity;
                if (lockableContainer instanceof TileEntityChest && block instanceof BlockChest) {
                    lockableContainer = ((BlockChest)block).getLockableContainer(world, blockPos);
                }
            }
        }
        if (lockableContainer == null) {
            final List<Entity> entitiesInAABBexcluding = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(n - 0.5, n2 - 0.5, n3 - 0.5, n + 0.5, n2 + 0.5, n3 + 0.5), EntitySelectors.selectInventories);
            if (entitiesInAABBexcluding.size() > 0) {
                lockableContainer = (IInventory)entitiesInAABBexcluding.get(world.rand.nextInt(entitiesInAABBexcluding.size()));
            }
        }
        return lockableContainer;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.inventory[n];
    }
    
    private static boolean pullItemFromSlot(final IHopper hopper, final IInventory inventory, final int n, final EnumFacing enumFacing) {
        final ItemStack stackInSlot = inventory.getStackInSlot(n);
        if (stackInSlot != null && canExtractItemFromSlot(inventory, stackInSlot, n, enumFacing)) {
            final ItemStack copy = stackInSlot.copy();
            final ItemStack putStackInInventoryAllSlots = putStackInInventoryAllSlots(hopper, inventory.decrStackSize(n, " ".length()), null);
            if (putStackInInventoryAllSlots == null || putStackInInventoryAllSlots.stackSize == 0) {
                inventory.markDirty();
                return " ".length() != 0;
            }
            inventory.setInventorySlotContents(n, copy);
        }
        return "".length() != 0;
    }
    
    private IInventory getInventoryForHopperTransfer() {
        final EnumFacing facing = BlockHopper.getFacing(this.getBlockMetadata());
        return getInventoryAtPosition(this.getWorld(), this.pos.getX() + facing.getFrontOffsetX(), this.pos.getY() + facing.getFrontOffsetY(), this.pos.getZ() + facing.getFrontOffsetZ());
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static boolean putDropInInventoryAllSlots(final IInventory inventory, final EntityItem entityItem) {
        int n = "".length();
        if (entityItem == null) {
            return "".length() != 0;
        }
        final ItemStack putStackInInventoryAllSlots = putStackInInventoryAllSlots(inventory, entityItem.getEntityItem().copy(), null);
        if (putStackInInventoryAllSlots != null && putStackInInventoryAllSlots.stackSize != 0) {
            entityItem.setEntityItemStack(putStackInInventoryAllSlots);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
            entityItem.setDead();
        }
        return n != 0;
    }
    
    public static ItemStack putStackInInventoryAllSlots(final IInventory inventory, ItemStack itemStack, final EnumFacing enumFacing) {
        if (inventory instanceof ISidedInventory && enumFacing != null) {
            final int[] slotsForFace = ((ISidedInventory)inventory).getSlotsForFace(enumFacing);
            int length = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (length < slotsForFace.length && itemStack != null) {
                if (itemStack.stackSize <= 0) {
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    itemStack = insertStack(inventory, itemStack, slotsForFace[length], enumFacing);
                    ++length;
                }
            }
        }
        else {
            final int sizeInventory = inventory.getSizeInventory();
            int length2 = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (length2 < sizeInventory && itemStack != null && itemStack.stackSize > 0) {
                itemStack = insertStack(inventory, itemStack, length2, enumFacing);
                ++length2;
            }
        }
        if (itemStack != null && itemStack.stackSize == 0) {
            itemStack = null;
        }
        return itemStack;
    }
    
    @Override
    public double getXPos() {
        return this.pos.getX() + 0.5;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    private static boolean canInsertItemInSlot(final IInventory inventory, final ItemStack itemStack, final int n, final EnumFacing enumFacing) {
        int n2;
        if (!inventory.isItemValidForSlot(n, itemStack)) {
            n2 = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (inventory instanceof ISidedInventory && !((ISidedInventory)inventory).canInsertItem(n, itemStack, enumFacing)) {
            n2 = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    public static List<EntityItem> func_181556_a(final World world, final double n, final double n2, final double n3) {
        return world.getEntitiesWithinAABB((Class<? extends EntityItem>)EntityItem.class, new AxisAlignedBB(n - 0.5, n2 - 0.5, n3 - 0.5, n + 0.5, n2 + 0.5, n3 + 0.5), (com.google.common.base.Predicate<? super EntityItem>)EntitySelectors.selectAnything);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.inventory[n] == null) {
            return null;
        }
        if (this.inventory[n].stackSize <= n2) {
            final ItemStack itemStack = this.inventory[n];
            this.inventory[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.inventory[n].splitStack(n2);
        if (this.inventory[n].stackSize == 0) {
            this.inventory[n] = null;
        }
        return splitStack;
    }
    
    private static void I() {
        (I = new String[0x69 ^ 0x62])["".length()] = I("\u0000&\u0001+=", "IRdFN");
        TileEntityHopper.I[" ".length()] = I("3!\u0002\u0018:\u001d\u001a\u0010\u00010", "pTqlU");
        TileEntityHopper.I["  ".length()] = I(":\u0002;\f\u0002\u00149)\u0015\b", "ywHxm");
        TileEntityHopper.I["   ".length()] = I("\u001c6/\r1.!< -'(*\f5&", "HDNcB");
        TileEntityHopper.I[0x3B ^ 0x3F] = I("\u0016&(,", "EJGXO");
        TileEntityHopper.I[0xB8 ^ 0xBD] = I("4\u0003$$", "goKPJ");
        TileEntityHopper.I[0x9A ^ 0x9C] = I("\"&\u0011= ", "kRtPS");
        TileEntityHopper.I[0x2A ^ 0x2D] = I("\u0011\u0002\u0015\u001c #\u0015\u00061<*\u001c\u0010\u001d$+", "EptrS");
        TileEntityHopper.I[0x52 ^ 0x5A] = I("2<>\u0018\u0003\u001c\u0007,\u0001\t", "qIMll");
        TileEntityHopper.I[0x4C ^ 0x45] = I("\u0000\u0006\u0004&)\n\u0007\u000f f\u000b\u0006\u001a\"-\u0011", "cijRH");
        TileEntityHopper.I[0xB3 ^ 0xB9] = I("\"\u0001\b/;=\t\u0000>b'\u0007\u0016:==", "OhfJX");
    }
    
    @Override
    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            this.transferCooldown -= " ".length();
            if (!this.isOnTransferCooldown()) {
                this.setTransferCooldown("".length());
                this.updateHopper();
            }
        }
    }
    
    private static ItemStack insertStack(final IInventory inventory, ItemStack itemStack, final int n, final EnumFacing enumFacing) {
        final ItemStack stackInSlot = inventory.getStackInSlot(n);
        if (canInsertItemInSlot(inventory, itemStack, n, enumFacing)) {
            int n2 = "".length();
            if (stackInSlot == null) {
                inventory.setInventorySlotContents(n, itemStack);
                itemStack = null;
                n2 = " ".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else if (canCombine(stackInSlot, itemStack)) {
                final int min = Math.min(itemStack.stackSize, itemStack.getMaxStackSize() - stackInSlot.stackSize);
                final ItemStack itemStack2 = itemStack;
                itemStack2.stackSize -= min;
                final ItemStack itemStack3 = stackInSlot;
                itemStack3.stackSize += min;
                int n3;
                if (min > 0) {
                    n3 = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                n2 = n3;
            }
            if (n2 != 0) {
                if (inventory instanceof TileEntityHopper) {
                    final TileEntityHopper tileEntityHopper = (TileEntityHopper)inventory;
                    if (tileEntityHopper.mayTransfer()) {
                        tileEntityHopper.setTransferCooldown(0x83 ^ 0x8B);
                    }
                    inventory.markDirty();
                }
                inventory.markDirty();
            }
        }
        return itemStack;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerHopper(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public String getGuiID() {
        return TileEntityHopper.I[0x6D ^ 0x67];
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    private boolean isEmpty() {
        final ItemStack[] inventory;
        final int length = (inventory = this.inventory).length;
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < length) {
            if (inventory[i] != null) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            customName = TileEntityHopper.I[0x1E ^ 0x17];
        }
        return customName;
    }
    
    static {
        I();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x45 ^ 0x5;
    }
    
    private static boolean canExtractItemFromSlot(final IInventory inventory, final ItemStack itemStack, final int n, final EnumFacing enumFacing) {
        if (inventory instanceof ISidedInventory && !((ISidedInventory)inventory).canExtractItem(n, itemStack, enumFacing)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static boolean canCombine(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (itemStack.getItem() != itemStack2.getItem()) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (itemStack.getMetadata() != itemStack2.getMetadata()) {
            n = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (itemStack.stackSize > itemStack.getMaxStackSize()) {
            n = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n = (ItemStack.areItemStackTagsEqual(itemStack, itemStack2) ? 1 : 0);
        }
        return n != 0;
    }
    
    public TileEntityHopper() {
        this.inventory = new ItemStack[0x13 ^ 0x16];
        this.transferCooldown = -" ".length();
    }
}
