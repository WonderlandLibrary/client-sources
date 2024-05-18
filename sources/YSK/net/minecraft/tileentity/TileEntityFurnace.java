package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final String[] I;
    private int furnaceBurnTime;
    private int totalCookTime;
    private int currentItemBurnTime;
    private String furnaceCustomName;
    private ItemStack[] furnaceItemStacks;
    private static final int[] slotsTop;
    private static final int[] slotsBottom;
    private static final int[] slotsSides;
    private int cookTime;
    
    static {
        I();
        slotsTop = new int[" ".length()];
        final int[] slotsBottom2 = new int["  ".length()];
        slotsBottom2["".length()] = "  ".length();
        slotsBottom2[" ".length()] = " ".length();
        slotsBottom = slotsBottom2;
        final int[] slotsSides2 = new int[" ".length()];
        slotsSides2["".length()] = " ".length();
        slotsSides = slotsSides2;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[i] = null;
            ++i;
        }
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.furnaceItemStacks[n] != null) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.furnaceItemStacks[n];
    }
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN && n == " ".length()) {
            final Item item = itemStack.getItem();
            if (item != Items.water_bucket && item != Items.bucket) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerFurnace(inventoryPlayer, this);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.furnaceItemStacks[n] == null) {
            return null;
        }
        if (this.furnaceItemStacks[n].stackSize <= n2) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.furnaceItemStacks[n].splitStack(n2);
        if (this.furnaceItemStacks[n].stackSize == 0) {
            this.furnaceItemStacks[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public int getFieldCount() {
        return 0xB ^ 0xF;
    }
    
    private boolean canSmelt() {
        if (this.furnaceItemStacks["".length()] == null) {
            return "".length() != 0;
        }
        final ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks["".length()]);
        int n;
        if (smeltingResult == null) {
            n = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (this.furnaceItemStacks["  ".length()] == null) {
            n = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (!this.furnaceItemStacks["  ".length()].isItemEqual(smeltingResult)) {
            n = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this.furnaceItemStacks["  ".length()].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks["  ".length()].stackSize < this.furnaceItemStacks["  ".length()].getMaxStackSize()) {
            n = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (this.furnaceItemStacks["  ".length()].stackSize < smeltingResult.getMaxStackSize()) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void setCustomInventoryName(final String furnaceCustomName) {
        this.furnaceCustomName = furnaceCustomName;
    }
    
    @Override
    public void setField(final int n, final int n2) {
        switch (n) {
            case 0: {
                this.furnaceBurnTime = n2;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.currentItemBurnTime = n2;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.cookTime = n2;
                "".length();
                if (2 < 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.totalCookTime = n2;
                break;
            }
        }
    }
    
    public static boolean isItemFuel(final ItemStack itemStack) {
        if (getItemBurnTime(itemStack) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityFurnace.I[0x3C ^ 0x2D];
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        int n2;
        if (itemStack != null && itemStack.isItemEqual(this.furnaceItemStacks[n]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[n])) {
            n2 = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        this.furnaceItemStacks[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (n == 0 && n3 == 0) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = "".length();
            this.markDirty();
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort(TileEntityFurnace.I[0x59 ^ 0x51], (short)this.furnaceBurnTime);
        nbtTagCompound.setShort(TileEntityFurnace.I[0x8D ^ 0x84], (short)this.cookTime);
        nbtTagCompound.setShort(TileEntityFurnace.I[0x78 ^ 0x72], (short)this.totalCookTime);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < this.furnaceItemStacks.length) {
            if (this.furnaceItemStacks[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityFurnace.I[0x9D ^ 0x96], (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(TileEntityFurnace.I[0x6E ^ 0x62], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityFurnace.I[0x10 ^ 0x1D], this.furnaceCustomName);
        }
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    public static boolean isBurning(final IInventory inventory) {
        if (inventory.getField("".length()) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void update() {
        final boolean burning = this.isBurning();
        int n = "".length();
        if (this.isBurning()) {
            this.furnaceBurnTime -= " ".length();
        }
        if (!this.worldObj.isRemote) {
            if (this.isBurning() || (this.furnaceItemStacks[" ".length()] != null && this.furnaceItemStacks["".length()] != null)) {
                if (!this.isBurning() && this.canSmelt()) {
                    final int itemBurnTime = getItemBurnTime(this.furnaceItemStacks[" ".length()]);
                    this.furnaceBurnTime = itemBurnTime;
                    this.currentItemBurnTime = itemBurnTime;
                    if (this.isBurning()) {
                        n = " ".length();
                        if (this.furnaceItemStacks[" ".length()] != null) {
                            final ItemStack itemStack = this.furnaceItemStacks[" ".length()];
                            itemStack.stackSize -= " ".length();
                            if (this.furnaceItemStacks[" ".length()].stackSize == 0) {
                                final Item containerItem = this.furnaceItemStacks[" ".length()].getItem().getContainerItem();
                                final ItemStack[] furnaceItemStacks = this.furnaceItemStacks;
                                final int length = " ".length();
                                ItemStack itemStack2;
                                if (containerItem != null) {
                                    itemStack2 = new ItemStack(containerItem);
                                    "".length();
                                    if (4 != 4) {
                                        throw null;
                                    }
                                }
                                else {
                                    itemStack2 = null;
                                }
                                furnaceItemStacks[length] = itemStack2;
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt()) {
                    this.cookTime += " ".length();
                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = "".length();
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks["".length()]);
                        this.smeltItem();
                        n = " ".length();
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                }
                else {
                    this.cookTime = "".length();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
            }
            else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - "  ".length(), "".length(), this.totalCookTime);
            }
            if (burning != this.isBurning()) {
                n = " ".length();
                BlockFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }
        if (n != 0) {
            this.markDirty();
        }
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.furnaceCustomName != null && this.furnaceCustomName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static int getItemBurnTime(final ItemStack itemStack) {
        if (itemStack == null) {
            return "".length();
        }
        final Item item = itemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
            final Block blockFromItem = Block.getBlockFromItem(item);
            if (blockFromItem == Blocks.wooden_slab) {
                return 104 + 85 - 64 + 25;
            }
            if (blockFromItem.getMaterial() == Material.wood) {
                return 143 + 147 - 131 + 141;
            }
            if (blockFromItem == Blocks.coal_block) {
                return 6694 + 5077 - 11497 + 15726;
            }
        }
        int length;
        if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals(TileEntityFurnace.I[0x3E ^ 0x30])) {
            length = 174 + 195 - 247 + 78;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals(TileEntityFurnace.I[0x46 ^ 0x49])) {
            length = 62 + 32 + 81 + 25;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals(TileEntityFurnace.I[0xD5 ^ 0xC5])) {
            length = 67 + 179 - 177 + 131;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (item == Items.stick) {
            length = (0x74 ^ 0x10);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (item == Items.coal) {
            length = 116 + 39 + 230 + 1215;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (item == Items.lava_bucket) {
            length = 18447 + 15888 - 21560 + 7225;
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (item == Item.getItemFromBlock(Blocks.sapling)) {
            length = (0x3F ^ 0x5B);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else if (item == Items.blaze_rod) {
            length = 953 + 2080 - 2759 + 2126;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityFurnace.I[" ".length()], 0x91 ^ 0x9B);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(TileEntityFurnace.I["  ".length()]);
            if (byte1 >= 0 && byte1 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
        this.furnaceBurnTime = nbtTagCompound.getShort(TileEntityFurnace.I["   ".length()]);
        this.cookTime = nbtTagCompound.getShort(TileEntityFurnace.I[0x4E ^ 0x4A]);
        this.totalCookTime = nbtTagCompound.getShort(TileEntityFurnace.I[0x34 ^ 0x31]);
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[" ".length()]);
        if (nbtTagCompound.hasKey(TileEntityFurnace.I[0xB6 ^ 0xB0], 0x56 ^ 0x5E)) {
            this.furnaceCustomName = nbtTagCompound.getString(TileEntityFurnace.I[0x1D ^ 0x1A]);
        }
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        int[] array;
        if (enumFacing == EnumFacing.DOWN) {
            array = TileEntityFurnace.slotsBottom;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.UP) {
            array = TileEntityFurnace.slotsTop;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            array = TileEntityFurnace.slotsSides;
        }
        return array;
    }
    
    public TileEntityFurnace() {
        this.furnaceItemStacks = new ItemStack["   ".length()];
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.furnaceBurnTime;
            }
            case 1: {
                return this.currentItemBurnTime;
            }
            case 2: {
                return this.cookTime;
            }
            case 3: {
                return this.totalCookTime;
            }
            default: {
                return "".length();
            }
        }
    }
    
    @Override
    public String getName() {
        String furnaceCustomName;
        if (this.hasCustomName()) {
            furnaceCustomName = this.furnaceCustomName;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            furnaceCustomName = TileEntityFurnace.I["".length()];
        }
        return furnaceCustomName;
    }
    
    public int getCookTime(final ItemStack itemStack) {
        return 94 + 27 - 37 + 116;
    }
    
    public void smeltItem() {
        if (this.canSmelt()) {
            final ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks["".length()]);
            if (this.furnaceItemStacks["  ".length()] == null) {
                this.furnaceItemStacks["  ".length()] = smeltingResult.copy();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else if (this.furnaceItemStacks["  ".length()].getItem() == smeltingResult.getItem()) {
                final ItemStack itemStack = this.furnaceItemStacks["  ".length()];
                itemStack.stackSize += " ".length();
            }
            if (this.furnaceItemStacks["".length()].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks["".length()].getMetadata() == " ".length() && this.furnaceItemStacks[" ".length()] != null && this.furnaceItemStacks[" ".length()].getItem() == Items.bucket) {
                this.furnaceItemStacks[" ".length()] = new ItemStack(Items.water_bucket);
            }
            final ItemStack itemStack2 = this.furnaceItemStacks["".length()];
            itemStack2.stackSize -= " ".length();
            if (this.furnaceItemStacks["".length()].stackSize <= 0) {
                this.furnaceItemStacks["".length()] = null;
            }
        }
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    public boolean isBurning() {
        if (this.furnaceBurnTime > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x3E ^ 0x7E;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x8C ^ 0x9E])["".length()] = I("\b\u001a!\u0016*\u0002\u001b*\u0010e\r\u0000=\f*\b\u0010", "kuObK");
        TileEntityFurnace.I[" ".length()] = I(" 0<)+", "iDYDX");
        TileEntityFurnace.I["  ".length()] = I("\u0005%:5", "VIUAV");
        TileEntityFurnace.I["   ".length()] = I("-\u0014\u0015-!\u0006\f\u0002", "oagCu");
        TileEntityFurnace.I[0x5F ^ 0x5B] = I("7.-8\u0004\u001d,'", "tABSP");
        TileEntityFurnace.I[0x11 ^ 0x14] = I(".\f9>.\u0004\u000e3\u0001\u0015\u0019\u0002:", "mcVUz");
        TileEntityFurnace.I[0xC7 ^ 0xC1] = I("'3\t\u0005\u000b\t\b\u001b\u001c\u0001", "dFzqd");
        TileEntityFurnace.I[0x1B ^ 0x1C] = I("\u001b$\"\u0000,5\u001f0\u0019&", "XQQtC");
        TileEntityFurnace.I[0xAA ^ 0xA2] = I("\u0005\u0012$8\u000e.\n3", "GgVVZ");
        TileEntityFurnace.I[0xA6 ^ 0xAF] = I("\u0001\b!\b\u0007+\n+", "BgNcS");
        TileEntityFurnace.I[0x85 ^ 0x8F] = I("$)\u000b\u0006\u0000\u000e+\u00019;\u0013'\b", "gFdmT");
        TileEntityFurnace.I[0x59 ^ 0x52] = I("\u000b\u0014\u0017=", "XxxIw");
        TileEntityFurnace.I[0x81 ^ 0x8D] = I("'\u001a<) ", "nnYDS");
        TileEntityFurnace.I[0x87 ^ 0x8A] = I(",\u001e \u0001\u0004\u0002%2\u0018\u000e", "okSuk");
        TileEntityFurnace.I[0x3E ^ 0x30] = I("\u0003>\r)", "TqBmW");
        TileEntityFurnace.I[0x68 ^ 0x67] = I("\u000f\u001f\u0016\r", "XPYIp");
        TileEntityFurnace.I[0x93 ^ 0x83] = I("\u0013.\u001d\u001e", "DaRZm");
        TileEntityFurnace.I[0x79 ^ 0x68] = I("\n-\b'4\u0015%\u00006m\u00011\u0014,6\u0004!", "gDfBW");
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        int n2;
        if (n == "  ".length()) {
            n2 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n != " ".length()) {
            n2 = " ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (!isItemFuel(itemStack) && !SlotFurnaceFuel.isBucket(itemStack)) {
            n2 = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }
}
