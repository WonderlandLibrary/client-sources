package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory
{
    public int numPlayersUsing;
    private int ticksSinceSync;
    public TileEntityChest adjacentChestXNeg;
    private ItemStack[] chestContents;
    public float prevLidAngle;
    public float lidAngle;
    public TileEntityChest adjacentChestZPos;
    public boolean adjacentChestChecked;
    public TileEntityChest adjacentChestXPos;
    private int cachedChestType;
    public TileEntityChest adjacentChestZNeg;
    private String customName;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    private static final String[] I;
    
    private void func_174910_a(final TileEntityChest tileEntityChest, final EnumFacing enumFacing) {
        if (tileEntityChest.isInvalid()) {
            this.adjacentChestChecked = ("".length() != 0);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else if (this.adjacentChestChecked) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 3: {
                    if (this.adjacentChestZNeg == tileEntityChest) {
                        break;
                    }
                    this.adjacentChestChecked = ("".length() != 0);
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    if (this.adjacentChestZPos == tileEntityChest) {
                        break;
                    }
                    this.adjacentChestChecked = ("".length() != 0);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    if (this.adjacentChestXPos == tileEntityChest) {
                        break;
                    }
                    this.adjacentChestChecked = ("".length() != 0);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    if (this.adjacentChestXNeg != tileEntityChest) {
                        this.adjacentChestChecked = ("".length() != 0);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    public TileEntityChest() {
        this.chestContents = new ItemStack[0xB1 ^ 0xAA];
        this.cachedChestType = -" ".length();
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null && this.customName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < this.chestContents.length) {
            this.chestContents[i] = null;
            ++i;
        }
    }
    
    @Override
    public int getSizeInventory() {
        return 0xDC ^ 0xC7;
    }
    
    private static void I() {
        (I = new String[0x6F ^ 0x64])["".length()] = I(" \u0000\u0016\u001b\u001b*\u0001\u001d\u001dT \u0007\u001d\u001c\u000e", "Coxoz");
        TileEntityChest.I[" ".length()] = I("\u001d#\u0001>)", "TWdSZ");
        TileEntityChest.I["  ".length()] = I("\b;\u0011$\r&\u0000\u0003=\u0007", "KNbPb");
        TileEntityChest.I["   ".length()] = I("\u0005-2##+\u0016 :)", "FXAWL");
        TileEntityChest.I[0x19 ^ 0x1D] = I("8\u0019;\u0000", "kuTtN");
        TileEntityChest.I[0xB ^ 0xE] = I("\u0003;\u001a\u0000", "PWutv");
        TileEntityChest.I[0x93 ^ 0x95] = I("%\u0017\u0012#\u0012", "lcwNa");
        TileEntityChest.I[0x8B ^ 0x8C] = I("\u0011\u0019;6'?\")/-", "RlHBH");
        TileEntityChest.I[0x79 ^ 0x71] = I("'\u0002\u0018\r\t8M\u0015\u0001\u0003&\u0017\u0019\u0019\u0003;", "Ucvif");
        TileEntityChest.I[0x2C ^ 0x25] = I("\u001f\u001b\u0017\u0000!\u0000T\u001a\f+\u001e\u000e\u001a\b!\u001e\u001f\u001d", "mzydN");
        TileEntityChest.I[0x4C ^ 0x46] = I(">\u000b\b!,!\u0003\u00000u0\n\u00037;", "SbfDO");
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.chestContents[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    protected TileEntityChest getAdjacentChest(final EnumFacing enumFacing) {
        final BlockPos offset = this.pos.offset(enumFacing);
        if (this.isChestAt(offset)) {
            final TileEntity tileEntity = this.worldObj.getTileEntity(offset);
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                tileEntityChest.func_174910_a(this, enumFacing.getOpposite());
                return tileEntityChest;
            }
        }
        return null;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0xD7 ^ 0x97;
    }
    
    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = ("".length() != 0);
    }
    
    @Override
    public String getGuiID() {
        return TileEntityChest.I[0x71 ^ 0x7B];
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public TileEntityChest(final int cachedChestType) {
        this.chestContents = new ItemStack[0x12 ^ 0x9];
        this.cachedChestType = cachedChestType;
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.chestContents[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.chestContents[n] == null) {
            return null;
        }
        if (this.chestContents[n].stackSize <= n2) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.chestContents[n].splitStack(n2);
        if (this.chestContents[n].stackSize == 0) {
            this.chestContents[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public void update() {
        this.checkForAdjacentChests();
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        this.ticksSinceSync += " ".length();
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + x + y + z) % (91 + 34 + 67 + 8) == 0) {
            this.numPlayersUsing = "".length();
            final float n = 5.0f;
            final Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityPlayer>)EntityPlayer.class, new AxisAlignedBB(x - n, y - n, z - n, x + " ".length() + n, y + " ".length() + n, z + " ".length() + n)).iterator();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer = iterator.next();
                if (entityPlayer.openContainer instanceof ContainerChest) {
                    final IInventory lowerChestInventory = ((ContainerChest)entityPlayer.openContainer).getLowerChestInventory();
                    if (lowerChestInventory != this && (!(lowerChestInventory instanceof InventoryLargeChest) || !((InventoryLargeChest)lowerChestInventory).isPartOfLargeChest(this))) {
                        continue;
                    }
                    this.numPlayersUsing += " ".length();
                }
            }
        }
        this.prevLidAngle = this.lidAngle;
        final float n2 = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double n3 = x + 0.5;
            double n4 = z + 0.5;
            if (this.adjacentChestZPos != null) {
                n4 += 0.5;
            }
            if (this.adjacentChestXPos != null) {
                n3 += 0.5;
            }
            this.worldObj.playSoundEffect(n3, y + 0.5, n4, TileEntityChest.I[0xAB ^ 0xA3], 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float lidAngle = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += n2;
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                this.lidAngle -= n2;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float n5 = 0.5f;
            if (this.lidAngle < n5 && lidAngle >= n5 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                double n6 = x + 0.5;
                double n7 = z + 0.5;
                if (this.adjacentChestZPos != null) {
                    n7 += 0.5;
                }
                if (this.adjacentChestXPos != null) {
                    n6 += 0.5;
                }
                this.worldObj.playSoundEffect(n6, y + 0.5, n7, TileEntityChest.I[0x36 ^ 0x3F], 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int numPlayersUsing) {
        if (n == " ".length()) {
            this.numPlayersUsing = numPlayersUsing;
            return " ".length() != 0;
        }
        return super.receiveClientEvent(n, numPlayersUsing);
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.isSpectator() && this.getBlockType() instanceof BlockChest) {
            this.numPlayersUsing -= " ".length();
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), " ".length(), this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.chestContents[n] != null) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    private boolean isChestAt(final BlockPos blockPos) {
        if (this.worldObj == null) {
            return "".length() != 0;
        }
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        if (block instanceof BlockChest && ((BlockChest)block).chestType == this.getChestType()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = (" ".length() != 0);
            this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
            this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
            this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
            this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < this.chestContents.length) {
            if (this.chestContents[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityChest.I[0xBC ^ 0xB9], (byte)i);
                this.chestContents[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(TileEntityChest.I[0x90 ^ 0x96], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityChest.I[0x7 ^ 0x0], this.customName);
        }
    }
    
    static {
        I();
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            customName = TileEntityChest.I["".length()];
        }
        return customName;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = "".length();
            }
            this.numPlayersUsing += " ".length();
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), " ".length(), this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }
    
    public int getChestType() {
        if (this.cachedChestType == -" ".length()) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return "".length();
            }
            this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
        }
        return this.cachedChestType;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = TileEntityChest.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x12 ^ 0x14);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x28 ^ 0x2C);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xA3 ^ 0xA6);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return TileEntityChest.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityChest.I[" ".length()], 0xBB ^ 0xB1);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (nbtTagCompound.hasKey(TileEntityChest.I["  ".length()], 0x77 ^ 0x7F)) {
            this.customName = nbtTagCompound.getString(TileEntityChest.I["   ".length()]);
        }
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final int n = compoundTag.getByte(TileEntityChest.I[0x47 ^ 0x43]) & 209 + 191 - 200 + 55;
            if (n >= 0 && n < this.chestContents.length) {
                this.chestContents[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
    }
}
