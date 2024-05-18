package net.minecraft.inventory;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerEnchantment extends Container
{
    private static final String[] I;
    private BlockPos position;
    public int[] field_178151_h;
    private World worldPointer;
    public int[] enchantLevels;
    private Random rand;
    public int xpSeed;
    public IInventory tableInventory;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldPointer.getBlockState(this.position).getBlock() != Blocks.enchanting_table) {
            n = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.position.getX() + 0.5, this.position.getY() + 0.5, this.position.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.worldPointer.isRemote) {
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < this.tableInventory.getSizeInventory()) {
                final ItemStack removeStackFromSlot = this.tableInventory.removeStackFromSlot(i);
                if (removeStackFromSlot != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, "".length() != 0);
                }
                ++i;
            }
        }
    }
    
    @Override
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot("".length());
        final ItemStack stackInSlot2 = this.tableInventory.getStackInSlot(" ".length());
        final int n2 = n + " ".length();
        if ((stackInSlot2 == null || stackInSlot2.stackSize < n2) && !entityPlayer.capabilities.isCreativeMode) {
            return "".length() != 0;
        }
        if (this.enchantLevels[n] > 0 && stackInSlot != null && ((entityPlayer.experienceLevel >= n2 && entityPlayer.experienceLevel >= this.enchantLevels[n]) || entityPlayer.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isRemote) {
                final List<EnchantmentData> func_178148_a = this.func_178148_a(stackInSlot, n, this.enchantLevels[n]);
                int n3;
                if (stackInSlot.getItem() == Items.book) {
                    n3 = " ".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                if (func_178148_a != null) {
                    entityPlayer.removeExperienceLevel(n2);
                    if (n4 != 0) {
                        stackInSlot.setItem(Items.enchanted_book);
                    }
                    int i = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    while (i < func_178148_a.size()) {
                        final EnchantmentData enchantmentData = func_178148_a.get(i);
                        if (n4 != 0) {
                            Items.enchanted_book.addEnchantment(stackInSlot, enchantmentData);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            stackInSlot.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                        }
                        ++i;
                    }
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = stackInSlot2;
                        itemStack.stackSize -= n2;
                        if (stackInSlot2.stackSize <= 0) {
                            this.tableInventory.setInventorySlotContents(" ".length(), null);
                        }
                    }
                    entityPlayer.triggerAchievement(StatList.field_181739_W);
                    this.tableInventory.markDirty();
                    this.xpSeed = entityPlayer.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateProgressBar(final int n, final int xpSeed) {
        if (n >= 0 && n <= "  ".length()) {
            this.enchantLevels[n] = xpSeed;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (n == "   ".length()) {
            this.xpSeed = xpSeed;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (n >= (0x72 ^ 0x76) && n <= (0x30 ^ 0x36)) {
            this.field_178151_h[n - (0x16 ^ 0x12)] = xpSeed;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            super.updateProgressBar(n, xpSeed);
        }
    }
    
    private List<EnchantmentData> func_178148_a(final ItemStack itemStack, final int n, final int n2) {
        this.rand.setSeed(this.xpSeed + n);
        final List<EnchantmentData> buildEnchantmentList = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, n2);
        if (itemStack.getItem() == Items.book && buildEnchantmentList != null && buildEnchantmentList.size() > " ".length()) {
            buildEnchantmentList.remove(this.rand.nextInt(buildEnchantmentList.size()));
        }
        return buildEnchantmentList;
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, "".length(), this.enchantLevels["".length()]);
        crafting.sendProgressBarUpdate(this, " ".length(), this.enchantLevels[" ".length()]);
        crafting.sendProgressBarUpdate(this, "  ".length(), this.enchantLevels["  ".length()]);
        crafting.sendProgressBarUpdate(this, "   ".length(), this.xpSeed & -(0x3E ^ 0x2E));
        crafting.sendProgressBarUpdate(this, 0x5F ^ 0x5B, this.field_178151_h["".length()]);
        crafting.sendProgressBarUpdate(this, 0x9D ^ 0x98, this.field_178151_h[" ".length()]);
        crafting.sendProgressBarUpdate(this, 0x52 ^ 0x54, this.field_178151_h["  ".length()]);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("$46;\u0007\u000f.", "aZUSf");
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getLapisAmount() {
        final ItemStack stackInSlot = this.tableInventory.getStackInSlot(" ".length());
        int n;
        if (stackInSlot == null) {
            n = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = stackInSlot.stackSize;
        }
        return n;
    }
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World worldPointer, final BlockPos position) {
        this.tableInventory = new InventoryBasic(this, ContainerEnchantment.I["".length()], " ".length(), "  ".length()) {
            final ContainerEnchantment this$0;
            
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
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public int getInventoryStackLimit() {
                return 0xE8 ^ 0xA8;
            }
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
        };
        this.rand = new Random();
        this.enchantLevels = new int["   ".length()];
        final int[] field_178151_h = new int["   ".length()];
        field_178151_h["".length()] = -" ".length();
        field_178151_h[" ".length()] = -" ".length();
        field_178151_h["  ".length()] = -" ".length();
        this.field_178151_h = field_178151_h;
        this.worldPointer = worldPointer;
        this.position = position;
        this.xpSeed = inventoryPlayer.player.getXPSeed();
        this.addSlotToContainer(new Slot(this, this.tableInventory, "".length(), 0x9F ^ 0x90, 0xA3 ^ 0x8C) {
            final ContainerEnchantment this$0;
            
            @Override
            public int getSlotStackLimit() {
                return " ".length();
            }
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return " ".length() != 0;
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
                    if (2 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.addSlotToContainer(new Slot(this, this.tableInventory, " ".length(), 0x31 ^ 0x12, 0x14 ^ 0x3B) {
            final ContainerEnchantment this$0;
            
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
                    if (4 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                if (itemStack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(itemStack.getMetadata()) == EnumDyeColor.BLUE) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        });
        int i = "".length();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j < (0x67 ^ 0x6E)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * (0x96 ^ 0x9F) + (0xA3 ^ 0xAA), (0x78 ^ 0x70) + j * (0xBC ^ 0xAE), (0x7F ^ 0x2B) + i * (0x38 ^ 0x2A)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (k < (0x32 ^ 0x3B)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, (0x1 ^ 0x9) + k * (0x7C ^ 0x6E), 106 + 113 - 121 + 44));
            ++k;
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, "  ".length(), 0x86 ^ 0xA0, " ".length() != 0)) {
                    return null;
                }
            }
            else if (n == " ".length()) {
                if (!this.mergeItemStack(stack, "  ".length(), 0x48 ^ 0x6E, " ".length() != 0)) {
                    return null;
                }
            }
            else if (stack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE) {
                if (!this.mergeItemStack(stack, " ".length(), "  ".length(), " ".length() != 0)) {
                    return null;
                }
            }
            else {
                if (this.inventorySlots.get("".length()).getHasStack() || !this.inventorySlots.get("".length()).isItemValid(stack)) {
                    return null;
                }
                if (stack.hasTagCompound() && stack.stackSize == " ".length()) {
                    this.inventorySlots.get("".length()).putStack(stack.copy());
                    stack.stackSize = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (stack.stackSize >= " ".length()) {
                    this.inventorySlots.get("".length()).putStack(new ItemStack(stack.getItem(), " ".length(), stack.getMetadata()));
                    final ItemStack itemStack = stack;
                    itemStack.stackSize -= " ".length();
                }
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    public ContainerEnchantment(final InventoryPlayer inventoryPlayer, final World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(i);
            crafting.sendProgressBarUpdate(this, "".length(), this.enchantLevels["".length()]);
            crafting.sendProgressBarUpdate(this, " ".length(), this.enchantLevels[" ".length()]);
            crafting.sendProgressBarUpdate(this, "  ".length(), this.enchantLevels["  ".length()]);
            crafting.sendProgressBarUpdate(this, "   ".length(), this.xpSeed & -(0x56 ^ 0x46));
            crafting.sendProgressBarUpdate(this, 0x29 ^ 0x2D, this.field_178151_h["".length()]);
            crafting.sendProgressBarUpdate(this, 0x73 ^ 0x76, this.field_178151_h[" ".length()]);
            crafting.sendProgressBarUpdate(this, 0x81 ^ 0x87, this.field_178151_h["  ".length()]);
            ++i;
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        if (inventory == this.tableInventory) {
            final ItemStack stackInSlot = inventory.getStackInSlot("".length());
            if (stackInSlot != null && stackInSlot.isItemEnchantable()) {
                if (!this.worldPointer.isRemote) {
                    int length = "".length();
                    int i = -" ".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    while (i <= " ".length()) {
                        int j = -" ".length();
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                        while (j <= " ".length()) {
                            if ((i != 0 || j != 0) && this.worldPointer.isAirBlock(this.position.add(j, "".length(), i)) && this.worldPointer.isAirBlock(this.position.add(j, " ".length(), i))) {
                                if (this.worldPointer.getBlockState(this.position.add(j * "  ".length(), "".length(), i * "  ".length())).getBlock() == Blocks.bookshelf) {
                                    ++length;
                                }
                                if (this.worldPointer.getBlockState(this.position.add(j * "  ".length(), " ".length(), i * "  ".length())).getBlock() == Blocks.bookshelf) {
                                    ++length;
                                }
                                if (j != 0 && i != 0) {
                                    if (this.worldPointer.getBlockState(this.position.add(j * "  ".length(), "".length(), i)).getBlock() == Blocks.bookshelf) {
                                        ++length;
                                    }
                                    if (this.worldPointer.getBlockState(this.position.add(j * "  ".length(), " ".length(), i)).getBlock() == Blocks.bookshelf) {
                                        ++length;
                                    }
                                    if (this.worldPointer.getBlockState(this.position.add(j, "".length(), i * "  ".length())).getBlock() == Blocks.bookshelf) {
                                        ++length;
                                    }
                                    if (this.worldPointer.getBlockState(this.position.add(j, " ".length(), i * "  ".length())).getBlock() == Blocks.bookshelf) {
                                        ++length;
                                    }
                                }
                            }
                            ++j;
                        }
                        ++i;
                    }
                    this.rand.setSeed(this.xpSeed);
                    int k = "".length();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    while (k < "   ".length()) {
                        this.enchantLevels[k] = EnchantmentHelper.calcItemStackEnchantability(this.rand, k, length, stackInSlot);
                        this.field_178151_h[k] = -" ".length();
                        if (this.enchantLevels[k] < k + " ".length()) {
                            this.enchantLevels[k] = "".length();
                        }
                        ++k;
                    }
                    int l = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    while (l < "   ".length()) {
                        if (this.enchantLevels[l] > 0) {
                            final List<EnchantmentData> func_178148_a = this.func_178148_a(stackInSlot, l, this.enchantLevels[l]);
                            if (func_178148_a != null && !func_178148_a.isEmpty()) {
                                final EnchantmentData enchantmentData = func_178148_a.get(this.rand.nextInt(func_178148_a.size()));
                                this.field_178151_h[l] = (enchantmentData.enchantmentobj.effectId | enchantmentData.enchantmentLevel << (0xF ^ 0x7));
                            }
                        }
                        ++l;
                    }
                    this.detectAndSendChanges();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                int length2 = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (length2 < "   ".length()) {
                    this.enchantLevels[length2] = "".length();
                    this.field_178151_h[length2] = -" ".length();
                    ++length2;
                }
            }
        }
    }
}
