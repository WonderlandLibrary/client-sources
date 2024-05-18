package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.player.*;
import org.apache.commons.lang3.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ContainerRepair extends Container
{
    private int materialCost;
    private World theWorld;
    private final EntityPlayer thePlayer;
    private IInventory inputSlots;
    public int maximumCost;
    private static final Logger logger;
    private BlockPos selfPosition;
    private static final String[] I;
    private IInventory outputSlot;
    private String repairedItemName;
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == "  ".length()) {
                if (!this.mergeItemStack(stack, "   ".length(), 0x3A ^ 0x1D, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else if (n != 0 && n != " ".length()) {
                if (n >= "   ".length() && n < (0x17 ^ 0x30) && !this.mergeItemStack(stack, "".length(), "  ".length(), "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "   ".length(), 0x2D ^ 0xA, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (1 < -1) {
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
    
    @Override
    public void updateProgressBar(final int n, final int maximumCost) {
        if (n == 0) {
            this.maximumCost = maximumCost;
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World world, final EntityPlayer entityPlayer) {
        this(inventoryPlayer, world, BlockPos.ORIGIN, entityPlayer);
    }
    
    public void updateItemName(final String repairedItemName) {
        this.repairedItemName = repairedItemName;
        if (this.getSlot("  ".length()).getHasStack()) {
            final ItemStack stack = this.getSlot("  ".length()).getStack();
            if (StringUtils.isBlank((CharSequence)repairedItemName)) {
                stack.clearCustomName();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                stack.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }
    
    static int access$1(final ContainerRepair containerRepair) {
        return containerRepair.materialCost;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.theWorld.isRemote) {
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < this.inputSlots.getSizeInventory()) {
                final ItemStack removeStackFromSlot = this.inputSlots.removeStackFromSlot(i);
                if (removeStackFromSlot != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, "".length() != 0);
                }
                ++i;
            }
        }
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, "".length(), this.maximumCost);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("4!\u001e\u000b\u000f\u0014", "fDnjf");
    }
    
    public void updateRepairOutput() {
        "".length();
        " ".length();
        " ".length();
        " ".length();
        "  ".length();
        " ".length();
        " ".length();
        final ItemStack stackInSlot = this.inputSlots.getStackInSlot("".length());
        this.maximumCost = " ".length();
        int length = "".length();
        final int length2 = "".length();
        int n = "".length();
        if (stackInSlot == null) {
            this.outputSlot.setInventorySlotContents("".length(), null);
            this.maximumCost = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            ItemStack copy = stackInSlot.copy();
            final ItemStack stackInSlot2 = this.inputSlots.getStackInSlot(" ".length());
            final Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(copy);
            "".length();
            final int n2 = length2 + stackInSlot.getRepairCost();
            int n3;
            if (stackInSlot2 == null) {
                n3 = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n3 = stackInSlot2.getRepairCost();
            }
            final int n4 = n2 + n3;
            this.materialCost = "".length();
            if (stackInSlot2 != null) {
                int n5;
                if (stackInSlot2.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(stackInSlot2).tagCount() > 0) {
                    n5 = " ".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    n5 = "".length();
                }
                final int n6 = n5;
                if (copy.isItemStackDamageable() && copy.getItem().getIsRepairable(stackInSlot, stackInSlot2)) {
                    int n7 = Math.min(copy.getItemDamage(), copy.getMaxDamage() / (0x19 ^ 0x1D));
                    if (n7 <= 0) {
                        this.outputSlot.setInventorySlotContents("".length(), null);
                        this.maximumCost = "".length();
                        return;
                    }
                    int length3 = "".length();
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    while (n7 > 0 && length3 < stackInSlot2.stackSize) {
                        copy.setItemDamage(copy.getItemDamage() - n7);
                        ++length;
                        n7 = Math.min(copy.getItemDamage(), copy.getMaxDamage() / (0x3F ^ 0x3B));
                        ++length3;
                    }
                    this.materialCost = length3;
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    if (n6 == 0 && (copy.getItem() != stackInSlot2.getItem() || !copy.isItemStackDamageable())) {
                        this.outputSlot.setInventorySlotContents("".length(), null);
                        this.maximumCost = "".length();
                        return;
                    }
                    if (copy.isItemStackDamageable() && n6 == 0) {
                        int length4 = copy.getMaxDamage() - (stackInSlot.getMaxDamage() - stackInSlot.getItemDamage() + (stackInSlot2.getMaxDamage() - stackInSlot2.getItemDamage() + copy.getMaxDamage() * (0x64 ^ 0x68) / (0xC6 ^ 0xA2)));
                        if (length4 < 0) {
                            length4 = "".length();
                        }
                        if (length4 < copy.getMetadata()) {
                            copy.setItemDamage(length4);
                            length += 2;
                        }
                    }
                    final Map<Integer, Integer> enchantments2 = EnchantmentHelper.getEnchantments(stackInSlot2);
                    final Iterator<Integer> iterator = enchantments2.keySet().iterator();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final int intValue = iterator.next();
                        final Enchantment enchantmentById = Enchantment.getEnchantmentById(intValue);
                        if (enchantmentById != null) {
                            int n8;
                            if (enchantments.containsKey(intValue)) {
                                n8 = enchantments.get(intValue);
                                "".length();
                                if (2 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n8 = "".length();
                            }
                            final int n9 = n8;
                            int intValue2 = enchantments2.get(intValue);
                            int max;
                            if (n9 == intValue2) {
                                max = ++intValue2;
                                "".length();
                                if (true != true) {
                                    throw null;
                                }
                            }
                            else {
                                max = Math.max(intValue2, n9);
                            }
                            int maxLevel = max;
                            int n10 = enchantmentById.canApply(stackInSlot) ? 1 : 0;
                            if (this.thePlayer.capabilities.isCreativeMode || stackInSlot.getItem() == Items.enchanted_book) {
                                n10 = " ".length();
                            }
                            final Iterator<Integer> iterator2 = enchantments.keySet().iterator();
                            "".length();
                            if (3 < 3) {
                                throw null;
                            }
                            while (iterator2.hasNext()) {
                                final int intValue3 = iterator2.next();
                                if (intValue3 != intValue && !enchantmentById.canApplyTogether(Enchantment.getEnchantmentById(intValue3))) {
                                    n10 = "".length();
                                    ++length;
                                }
                            }
                            if (n10 == 0) {
                                continue;
                            }
                            if (maxLevel > enchantmentById.getMaxLevel()) {
                                maxLevel = enchantmentById.getMaxLevel();
                            }
                            enchantments.put(intValue, maxLevel);
                            int n11 = "".length();
                            Label_1079: {
                                switch (enchantmentById.getWeight()) {
                                    case 1: {
                                        n11 = (0x56 ^ 0x5E);
                                        "".length();
                                        if (0 >= 2) {
                                            throw null;
                                        }
                                        break Label_1079;
                                    }
                                    case 2: {
                                        n11 = (0x58 ^ 0x5C);
                                        break;
                                    }
                                    case 5: {
                                        n11 = "  ".length();
                                        "".length();
                                        if (-1 != -1) {
                                            throw null;
                                        }
                                        break Label_1079;
                                    }
                                    case 10: {
                                        n11 = " ".length();
                                        break Label_1079;
                                    }
                                }
                                "".length();
                                if (-1 >= 2) {
                                    throw null;
                                }
                            }
                            if (n6 != 0) {
                                n11 = Math.max(" ".length(), n11 / "  ".length());
                            }
                            length += n11 * maxLevel;
                        }
                    }
                }
            }
            if (StringUtils.isBlank((CharSequence)this.repairedItemName)) {
                if (stackInSlot.hasDisplayName()) {
                    n = " ".length();
                    length += n;
                    copy.clearCustomName();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
            }
            else if (!this.repairedItemName.equals(stackInSlot.getDisplayName())) {
                n = " ".length();
                length += n;
                copy.setStackDisplayName(this.repairedItemName);
            }
            this.maximumCost = n4 + length;
            if (length <= 0) {
                copy = null;
            }
            if (n == length && n > 0 && this.maximumCost >= (0x31 ^ 0x19)) {
                this.maximumCost = (0x64 ^ 0x43);
            }
            if (this.maximumCost >= (0x29 ^ 0x1) && !this.thePlayer.capabilities.isCreativeMode) {
                copy = null;
            }
            if (copy != null) {
                int n12 = copy.getRepairCost();
                if (stackInSlot2 != null && n12 < stackInSlot2.getRepairCost()) {
                    n12 = stackInSlot2.getRepairCost();
                }
                copy.setRepairCost(n12 * "  ".length() + " ".length());
                EnchantmentHelper.setEnchantments(enchantments, copy);
            }
            this.outputSlot.setInventorySlotContents("".length(), copy);
            this.detectAndSendChanges();
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        if (inventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World theWorld, final BlockPos selfPosition, final EntityPlayer thePlayer) {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryBasic(this, ContainerRepair.I["".length()], " ".length(), "  ".length()) {
            final ContainerRepair this$0;
            
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
                    if (3 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
        };
        this.selfPosition = selfPosition;
        this.theWorld = theWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new Slot(this.inputSlots, "".length(), 0x65 ^ 0x7E, 0xE8 ^ 0xC7));
        this.addSlotToContainer(new Slot(this.inputSlots, " ".length(), 0xE2 ^ 0xAE, 0x64 ^ 0x4B));
        this.addSlotToContainer(new Slot(this, this.outputSlot, "  ".length(), 18 + 76 + 27 + 13, 0xAF ^ 0x80, theWorld, selfPosition) {
            private final World val$worldIn;
            final ContainerRepair this$0;
            private final BlockPos val$blockPosIn;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return "".length() != 0;
            }
            
            @Override
            public boolean canTakeStack(final EntityPlayer entityPlayer) {
                if ((entityPlayer.capabilities.isCreativeMode || entityPlayer.experienceLevel >= this.this$0.maximumCost) && this.this$0.maximumCost > 0 && this.getHasStack()) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.addExperienceLevel(-this.this$0.maximumCost);
                }
                ContainerRepair.access$0(this.this$0).setInventorySlotContents("".length(), null);
                if (ContainerRepair.access$1(this.this$0) > 0) {
                    final ItemStack stackInSlot = ContainerRepair.access$0(this.this$0).getStackInSlot(" ".length());
                    if (stackInSlot != null && stackInSlot.stackSize > ContainerRepair.access$1(this.this$0)) {
                        final ItemStack itemStack2 = stackInSlot;
                        itemStack2.stackSize -= ContainerRepair.access$1(this.this$0);
                        ContainerRepair.access$0(this.this$0).setInventorySlotContents(" ".length(), stackInSlot);
                        "".length();
                        if (2 == -1) {
                            throw null;
                        }
                    }
                    else {
                        ContainerRepair.access$0(this.this$0).setInventorySlotContents(" ".length(), null);
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                }
                else {
                    ContainerRepair.access$0(this.this$0).setInventorySlotContents(" ".length(), null);
                }
                this.this$0.maximumCost = "".length();
                final IBlockState blockState = this.val$worldIn.getBlockState(this.val$blockPosIn);
                if (!entityPlayer.capabilities.isCreativeMode && !this.val$worldIn.isRemote && blockState.getBlock() == Blocks.anvil && entityPlayer.getRNG().nextFloat() < 0.12f) {
                    int intValue = blockState.getValue((IProperty<Integer>)BlockAnvil.DAMAGE);
                    if (++intValue > "  ".length()) {
                        this.val$worldIn.setBlockToAir(this.val$blockPosIn);
                        this.val$worldIn.playAuxSFX(400 + 979 - 1018 + 659, this.val$blockPosIn, "".length());
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else {
                        this.val$worldIn.setBlockState(this.val$blockPosIn, blockState.withProperty((IProperty<Comparable>)BlockAnvil.DAMAGE, intValue), "  ".length());
                        this.val$worldIn.playAuxSFX(478 + 500 - 825 + 868, this.val$blockPosIn, "".length());
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                }
                else if (!this.val$worldIn.isRemote) {
                    this.val$worldIn.playAuxSFX(801 + 523 - 1103 + 800, this.val$blockPosIn, "".length());
                }
            }
        });
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (j < (0x4B ^ 0x42)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * (0xC9 ^ 0xC0) + (0x61 ^ 0x68), (0x11 ^ 0x19) + j * (0xE ^ 0x1C), (0x5B ^ 0xF) + i * (0xB6 ^ 0xA4)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (k < (0x9 ^ 0x0)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, (0xBB ^ 0xB3) + k * (0x99 ^ 0x8B), 95 + 7 - 47 + 87));
            ++k;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        int n;
        if (this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil) {
            n = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.selfPosition.getX() + 0.5, this.selfPosition.getY() + 0.5, this.selfPosition.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    static IInventory access$0(final ContainerRepair containerRepair) {
        return containerRepair.inputSlots;
    }
}
