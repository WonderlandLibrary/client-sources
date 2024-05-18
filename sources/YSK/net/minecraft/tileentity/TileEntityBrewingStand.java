package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] inputSlots;
    private int brewTime;
    private static final String[] I;
    private ItemStack[] brewingItemStacks;
    private Item ingredientID;
    private String customName;
    private boolean[] filledSlots;
    private static final int[] outputSlots;
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }
    
    public boolean[] func_174902_m() {
        final boolean[] array = new boolean["   ".length()];
        int i = "".length();
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (i < "   ".length()) {
            if (this.brewingItemStacks[i] != null) {
                array[i] = (" ".length() != 0);
            }
            ++i;
        }
        return array;
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x1 ^ 0x41;
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            customName = TileEntityBrewingStand.I["".length()];
        }
        return customName;
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setField(final int n, final int brewTime) {
        switch (n) {
            case 0: {
                this.brewTime = brewTime;
                break;
            }
        }
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.brewTime;
            }
            default: {
                return "".length();
            }
        }
    }
    
    private int getPotionResult(final int n, final ItemStack itemStack) {
        int applyIngredient;
        if (itemStack == null) {
            applyIngredient = n;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (itemStack.getItem().isPotionIngredient(itemStack)) {
            applyIngredient = PotionHelper.applyIngredient(n, itemStack.getItem().getPotionEffect(itemStack));
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            applyIngredient = n;
        }
        return applyIngredient;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < this.brewingItemStacks.length) {
            this.brewingItemStacks[i] = null;
            ++i;
        }
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null && this.customName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityBrewingStand.I[" ".length()], 0xE ^ 0x4);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(TileEntityBrewingStand.I["  ".length()]);
            if (byte1 >= 0 && byte1 < this.brewingItemStacks.length) {
                this.brewingItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            ++i;
        }
        this.brewTime = nbtTagCompound.getShort(TileEntityBrewingStand.I["   ".length()]);
        if (nbtTagCompound.hasKey(TileEntityBrewingStand.I[0x7C ^ 0x78], 0x61 ^ 0x69)) {
            this.customName = nbtTagCompound.getString(TileEntityBrewingStand.I[0x7 ^ 0x2]);
        }
    }
    
    private static void I() {
        (I = new String[0x91 ^ 0x9A])["".length()] = I("'\u001b \u0018 -\u001a+\u001eo&\u0006+\u001b(*\u0013", "DtNlA");
        TileEntityBrewingStand.I[" ".length()] = I("\u0011 -$\u0012", "XTHIa");
        TileEntityBrewingStand.I["  ".length()] = I(":-\u001f\u0018", "iAplU");
        TileEntityBrewingStand.I["   ".length()] = I("4+\b\u0005\u0005\u001f4\b", "vYmrQ");
        TileEntityBrewingStand.I[0xB9 ^ 0xBD] = I("\u000f2\u0017!\n!\t\u00058\u0000", "LGdUe");
        TileEntityBrewingStand.I[0xB5 ^ 0xB0] = I("(\u001c\u0012\u001a\u001a\u0006'\u0000\u0003\u0010", "kianu");
        TileEntityBrewingStand.I[0x4B ^ 0x4D] = I("8*\u000e'0\u00135\u000e", "zXkPd");
        TileEntityBrewingStand.I[0x63 ^ 0x64] = I("\u001b\u001a-\"", "HvBVm");
        TileEntityBrewingStand.I[0x76 ^ 0x7E] = I("\u0013<\r\b>", "ZHheM");
        TileEntityBrewingStand.I[0xB8 ^ 0xB1] = I("\u000b3\u0003$\r%\b\u0011=\u0007", "HFpPb");
        TileEntityBrewingStand.I[0x4D ^ 0x47] = I("\u00033#\r\u000b\u001c;+\u001cR\f((\u001f\u0001\u0000=\u0012\u001b\u001c\u000f4)", "nZMhh");
    }
    
    static {
        I();
        final int[] inputSlots2 = new int[" ".length()];
        inputSlots2["".length()] = "   ".length();
        inputSlots = inputSlots2;
        final int[] outputSlots2 = new int["   ".length()];
        outputSlots2[" ".length()] = " ".length();
        outputSlots2["  ".length()] = "  ".length();
        outputSlots = outputSlots2;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort(TileEntityBrewingStand.I[0xA7 ^ 0xA1], (short)this.brewTime);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < this.brewingItemStacks.length) {
            if (this.brewingItemStacks[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityBrewingStand.I[0x46 ^ 0x41], (byte)i);
                this.brewingItemStacks[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            ++i;
        }
        nbtTagCompound.setTag(TileEntityBrewingStand.I[0x44 ^ 0x4C], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityBrewingStand.I[0xA2 ^ 0xAB], this.customName);
        }
    }
    
    public TileEntityBrewingStand() {
        this.brewingItemStacks = new ItemStack[0x68 ^ 0x6C];
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBrewingStand(inventoryPlayer, this);
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public int getFieldCount() {
        return " ".length();
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        ItemStack itemStack;
        if (n >= 0 && n < this.brewingItemStacks.length) {
            itemStack = this.brewingItemStacks[n];
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            itemStack = null;
        }
        return itemStack;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            this.brewingItemStacks[n] = itemStack;
        }
    }
    
    private void brewPotions() {
        if (this.canBrew()) {
            final ItemStack itemStack = this.brewingItemStacks["   ".length()];
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < "   ".length()) {
                if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                    final int metadata = this.brewingItemStacks[i].getMetadata();
                    final int potionResult = this.getPotionResult(metadata, itemStack);
                    final List<PotionEffect> effects = Items.potionitem.getEffects(metadata);
                    final List<PotionEffect> effects2 = Items.potionitem.getEffects(potionResult);
                    if ((metadata > 0 && effects == effects2) || (effects != null && (effects.equals(effects2) || effects2 == null))) {
                        if (!ItemPotion.isSplash(metadata) && ItemPotion.isSplash(potionResult)) {
                            this.brewingItemStacks[i].setItemDamage(potionResult);
                            "".length();
                            if (2 == 1) {
                                throw null;
                            }
                        }
                    }
                    else if (metadata != potionResult) {
                        this.brewingItemStacks[i].setItemDamage(potionResult);
                    }
                }
                ++i;
            }
            if (itemStack.getItem().hasContainerItem()) {
                this.brewingItemStacks["   ".length()] = new ItemStack(itemStack.getItem().getContainerItem());
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                final ItemStack itemStack2 = this.brewingItemStacks["   ".length()];
                itemStack2.stackSize -= " ".length();
                if (this.brewingItemStacks["   ".length()].stackSize <= 0) {
                    this.brewingItemStacks["   ".length()] = null;
                }
            }
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityBrewingStand.I[0x40 ^ 0x4A];
    }
    
    @Override
    public void update() {
        if (this.brewTime > 0) {
            this.brewTime -= " ".length();
            if (this.brewTime == 0) {
                this.brewPotions();
                this.markDirty();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (!this.canBrew()) {
                this.brewTime = "".length();
                this.markDirty();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            else if (this.ingredientID != this.brewingItemStacks["   ".length()].getItem()) {
                this.brewTime = "".length();
                this.markDirty();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else if (this.canBrew()) {
            this.brewTime = 142 + 212 - 11 + 57;
            this.ingredientID = this.brewingItemStacks["   ".length()].getItem();
        }
        if (!this.worldObj.isRemote) {
            final boolean[] func_174902_m = this.func_174902_m();
            if (!Arrays.equals(func_174902_m, this.filledSlots)) {
                this.filledSlots = func_174902_m;
                IBlockState blockState = this.worldObj.getBlockState(this.getPos());
                if (!(blockState.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }
                int i = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                while (i < BlockBrewingStand.HAS_BOTTLE.length) {
                    blockState = blockState.withProperty((IProperty<Comparable>)BlockBrewingStand.HAS_BOTTLE[i], func_174902_m[i]);
                    ++i;
                }
                this.worldObj.setBlockState(this.pos, blockState, "  ".length());
            }
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        int n2;
        if (n == "   ".length()) {
            n2 = (itemStack.getItem().isPotionIngredient(itemStack) ? 1 : 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (itemStack.getItem() != Items.potionitem && itemStack.getItem() != Items.glass_bottle) {
            n2 = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        int[] array;
        if (enumFacing == EnumFacing.UP) {
            array = TileEntityBrewingStand.inputSlots;
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            array = TileEntityBrewingStand.outputSlots;
        }
        return array;
    }
    
    private boolean canBrew() {
        if (this.brewingItemStacks["   ".length()] == null || this.brewingItemStacks["   ".length()].stackSize <= 0) {
            return "".length() != 0;
        }
        final ItemStack itemStack = this.brewingItemStacks["   ".length()];
        if (!itemStack.getItem().isPotionIngredient(itemStack)) {
            return "".length() != 0;
        }
        int n = "".length();
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < "   ".length()) {
            if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                final int metadata = this.brewingItemStacks[i].getMetadata();
                final int potionResult = this.getPotionResult(metadata, itemStack);
                if (!ItemPotion.isSplash(metadata) && ItemPotion.isSplash(potionResult)) {
                    n = " ".length();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    final List<PotionEffect> effects = Items.potionitem.getEffects(metadata);
                    final List<PotionEffect> effects2 = Items.potionitem.getEffects(potionResult);
                    if ((metadata <= 0 || effects != effects2) && (effects == null || (!effects.equals(effects2) && effects2 != null)) && metadata != potionResult) {
                        n = " ".length();
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                        break;
                    }
                }
            }
            ++i;
        }
        return n != 0;
    }
}
