/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PlayerInventory
implements IInventory,
INameable {
    public final NonNullList<ItemStack> mainInventory = NonNullList.withSize(36, ItemStack.EMPTY);
    public final NonNullList<ItemStack> armorInventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public final NonNullList<ItemStack> offHandInventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private final List<NonNullList<ItemStack>> allInventories = ImmutableList.of(this.mainInventory, this.armorInventory, this.offHandInventory);
    public int currentItem;
    public final PlayerEntity player;
    private ItemStack itemStack = ItemStack.EMPTY;
    private int timesChanged;

    public PlayerInventory(PlayerEntity playerEntity) {
        this.player = playerEntity;
    }

    public ItemStack getCurrentItem() {
        return PlayerInventory.isHotbar(this.currentItem) ? this.mainInventory.get(this.currentItem) : ItemStack.EMPTY;
    }

    public static int getHotbarSize() {
        return 0;
    }

    private boolean canMergeStacks(ItemStack itemStack, ItemStack itemStack2) {
        return !itemStack.isEmpty() && this.stackEqualExact(itemStack, itemStack2) && itemStack.isStackable() && itemStack.getCount() < itemStack.getMaxStackSize() && itemStack.getCount() < this.getInventoryStackLimit();
    }

    private boolean stackEqualExact(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack.getItem() == itemStack2.getItem() && ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
    }

    public int getFirstEmptyStack() {
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (!this.mainInventory.get(i).isEmpty()) continue;
            return i;
        }
        return 1;
    }

    public void setPickedItemStack(ItemStack itemStack) {
        int n = this.getSlotFor(itemStack);
        if (PlayerInventory.isHotbar(n)) {
            this.currentItem = n;
        } else if (n == -1) {
            int n2;
            this.currentItem = this.getBestHotbarSlot();
            if (!this.mainInventory.get(this.currentItem).isEmpty() && (n2 = this.getFirstEmptyStack()) != -1) {
                this.mainInventory.set(n2, this.mainInventory.get(this.currentItem));
            }
            this.mainInventory.set(this.currentItem, itemStack);
        } else {
            this.pickItem(n);
        }
    }

    public void pickItem(int n) {
        this.currentItem = this.getBestHotbarSlot();
        ItemStack itemStack = this.mainInventory.get(this.currentItem);
        this.mainInventory.set(this.currentItem, this.mainInventory.get(n));
        this.mainInventory.set(n, itemStack);
    }

    public static boolean isHotbar(int n) {
        return n >= 0 && n < 9;
    }

    public int getSlotFor(ItemStack itemStack) {
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (this.mainInventory.get(i).isEmpty() || !this.stackEqualExact(itemStack, this.mainInventory.get(i))) continue;
            return i;
        }
        return 1;
    }

    public int findSlotMatchingUnusedItem(ItemStack itemStack) {
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            ItemStack itemStack2 = this.mainInventory.get(i);
            if (this.mainInventory.get(i).isEmpty() || !this.stackEqualExact(itemStack, this.mainInventory.get(i)) || this.mainInventory.get(i).isDamaged() || itemStack2.isEnchanted() || itemStack2.hasDisplayName()) continue;
            return i;
        }
        return 1;
    }

    public int getBestHotbarSlot() {
        int n;
        int n2;
        for (n2 = 0; n2 < 9; ++n2) {
            n = (this.currentItem + n2) % 9;
            if (!this.mainInventory.get(n).isEmpty()) continue;
            return n;
        }
        for (n2 = 0; n2 < 9; ++n2) {
            n = (this.currentItem + n2) % 9;
            if (this.mainInventory.get(n).isEnchanted()) continue;
            return n;
        }
        return this.currentItem;
    }

    public void changeCurrentItem(double d) {
        if (d > 0.0) {
            d = 1.0;
        }
        if (d < 0.0) {
            d = -1.0;
        }
        this.currentItem = (int)((double)this.currentItem - d);
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }

    public int func_234564_a_(Predicate<ItemStack> predicate, int n, IInventory iInventory) {
        int n2 = 0;
        boolean bl = n == 0;
        n2 += ItemStackHelper.func_233534_a_(this, predicate, n - n2, bl);
        n2 += ItemStackHelper.func_233534_a_(iInventory, predicate, n - n2, bl);
        n2 += ItemStackHelper.func_233535_a_(this.itemStack, predicate, n - n2, bl);
        if (this.itemStack.isEmpty()) {
            this.itemStack = ItemStack.EMPTY;
        }
        return n2;
    }

    private int storePartialItemStack(ItemStack itemStack) {
        int n = this.storeItemStack(itemStack);
        if (n == -1) {
            n = this.getFirstEmptyStack();
        }
        return n == -1 ? itemStack.getCount() : this.addResource(n, itemStack);
    }

    private int addResource(int n, ItemStack itemStack) {
        Item item = itemStack.getItem();
        int n2 = itemStack.getCount();
        ItemStack itemStack2 = this.getStackInSlot(n);
        if (itemStack2.isEmpty()) {
            itemStack2 = new ItemStack(item, 0);
            if (itemStack.hasTag()) {
                itemStack2.setTag(itemStack.getTag().copy());
            }
            this.setInventorySlotContents(n, itemStack2);
        }
        int n3 = n2;
        if (n2 > itemStack2.getMaxStackSize() - itemStack2.getCount()) {
            n3 = itemStack2.getMaxStackSize() - itemStack2.getCount();
        }
        if (n3 > this.getInventoryStackLimit() - itemStack2.getCount()) {
            n3 = this.getInventoryStackLimit() - itemStack2.getCount();
        }
        if (n3 == 0) {
            return n2;
        }
        itemStack2.grow(n3);
        itemStack2.setAnimationsToGo(5);
        return n2 -= n3;
    }

    public int storeItemStack(ItemStack itemStack) {
        if (this.canMergeStacks(this.getStackInSlot(this.currentItem), itemStack)) {
            return this.currentItem;
        }
        if (this.canMergeStacks(this.getStackInSlot(40), itemStack)) {
            return 1;
        }
        for (int i = 0; i < this.mainInventory.size(); ++i) {
            if (!this.canMergeStacks(this.mainInventory.get(i), itemStack)) continue;
            return i;
        }
        return 1;
    }

    public void tick() {
        for (NonNullList<ItemStack> nonNullList : this.allInventories) {
            for (int i = 0; i < nonNullList.size(); ++i) {
                if (nonNullList.get(i).isEmpty()) continue;
                nonNullList.get(i).inventoryTick(this.player.world, this.player, i, this.currentItem == i);
            }
        }
    }

    public boolean addItemStackToInventory(ItemStack itemStack) {
        return this.add(-1, itemStack);
    }

    public boolean add(int n, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return true;
        }
        try {
            int n2;
            if (itemStack.isDamaged()) {
                if (n == -1) {
                    n = this.getFirstEmptyStack();
                }
                if (n >= 0) {
                    this.mainInventory.set(n, itemStack.copy());
                    this.mainInventory.get(n).setAnimationsToGo(5);
                    itemStack.setCount(0);
                    return true;
                }
                if (this.player.abilities.isCreativeMode) {
                    itemStack.setCount(0);
                    return true;
                }
                return false;
            }
            do {
                n2 = itemStack.getCount();
                if (n == -1) {
                    itemStack.setCount(this.storePartialItemStack(itemStack));
                    continue;
                }
                itemStack.setCount(this.addResource(n, itemStack));
            } while (!itemStack.isEmpty() && itemStack.getCount() < n2);
            if (itemStack.getCount() == n2 && this.player.abilities.isCreativeMode) {
                itemStack.setCount(0);
                return true;
            }
            return itemStack.getCount() < n2;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Item being added");
            crashReportCategory.addDetail("Item ID", Item.getIdFromItem(itemStack.getItem()));
            crashReportCategory.addDetail("Item data", itemStack.getDamage());
            crashReportCategory.addDetail("Item name", () -> PlayerInventory.lambda$add$0(itemStack));
            throw new ReportedException(crashReport);
        }
    }

    public void placeItemBackInInventory(World world, ItemStack itemStack) {
        if (!world.isRemote) {
            while (!itemStack.isEmpty()) {
                int n = this.storeItemStack(itemStack);
                if (n == -1) {
                    n = this.getFirstEmptyStack();
                }
                if (n == -1) {
                    this.player.dropItem(itemStack, true);
                    break;
                }
                int n2 = itemStack.getMaxStackSize() - this.getStackInSlot(n).getCount();
                if (!this.add(n, itemStack.split(n2))) continue;
                ((ServerPlayerEntity)this.player).connection.sendPacket(new SSetSlotPacket(-2, n, this.getStackInSlot(n)));
            }
        }
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        NonNullList<ItemStack> nonNullList = null;
        for (NonNullList<ItemStack> nonNullList2 : this.allInventories) {
            if (n < nonNullList2.size()) {
                nonNullList = nonNullList2;
                break;
            }
            n -= nonNullList2.size();
        }
        return nonNullList != null && !((ItemStack)nonNullList.get(n)).isEmpty() ? ItemStackHelper.getAndSplit(nonNullList, n, n2) : ItemStack.EMPTY;
    }

    public void deleteStack(ItemStack itemStack) {
        block0: for (NonNullList<ItemStack> nonNullList : this.allInventories) {
            for (int i = 0; i < nonNullList.size(); ++i) {
                if (nonNullList.get(i) != itemStack) continue;
                nonNullList.set(i, ItemStack.EMPTY);
                continue block0;
            }
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        NonNullList<ItemStack> nonNullList = null;
        for (NonNullList<ItemStack> nonNullList2 : this.allInventories) {
            if (n < nonNullList2.size()) {
                nonNullList = nonNullList2;
                break;
            }
            n -= nonNullList2.size();
        }
        if (nonNullList != null && !((ItemStack)nonNullList.get(n)).isEmpty()) {
            ItemStack itemStack = nonNullList.get(n);
            nonNullList.set(n, ItemStack.EMPTY);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        NonNullList<ItemStack> nonNullList = null;
        for (NonNullList<ItemStack> nonNullList2 : this.allInventories) {
            if (n < nonNullList2.size()) {
                nonNullList = nonNullList2;
                break;
            }
            n -= nonNullList2.size();
        }
        if (nonNullList != null) {
            nonNullList.set(n, itemStack);
        }
    }

    public float getDestroySpeed(BlockState blockState) {
        return this.mainInventory.get(this.currentItem).getDestroySpeed(blockState);
    }

    public float getDestroySpeed(BlockState blockState, int n) {
        return this.mainInventory.get(n).getDestroySpeed(blockState);
    }

    public ListNBT write(ListNBT listNBT) {
        CompoundNBT compoundNBT;
        int n;
        for (n = 0; n < this.mainInventory.size(); ++n) {
            if (this.mainInventory.get(n).isEmpty()) continue;
            compoundNBT = new CompoundNBT();
            compoundNBT.putByte("Slot", (byte)n);
            this.mainInventory.get(n).write(compoundNBT);
            listNBT.add(compoundNBT);
        }
        for (n = 0; n < this.armorInventory.size(); ++n) {
            if (this.armorInventory.get(n).isEmpty()) continue;
            compoundNBT = new CompoundNBT();
            compoundNBT.putByte("Slot", (byte)(n + 100));
            this.armorInventory.get(n).write(compoundNBT);
            listNBT.add(compoundNBT);
        }
        for (n = 0; n < this.offHandInventory.size(); ++n) {
            if (this.offHandInventory.get(n).isEmpty()) continue;
            compoundNBT = new CompoundNBT();
            compoundNBT.putByte("Slot", (byte)(n + 150));
            this.offHandInventory.get(n).write(compoundNBT);
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    public void read(ListNBT listNBT) {
        this.mainInventory.clear();
        this.armorInventory.clear();
        this.offHandInventory.clear();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            int n = compoundNBT.getByte("Slot") & 0xFF;
            ItemStack itemStack = ItemStack.read(compoundNBT);
            if (itemStack.isEmpty()) continue;
            if (n >= 0 && n < this.mainInventory.size()) {
                this.mainInventory.set(n, itemStack);
                continue;
            }
            if (n >= 100 && n < this.armorInventory.size() + 100) {
                this.armorInventory.set(n - 100, itemStack);
                continue;
            }
            if (n < 150 || n >= this.offHandInventory.size() + 150) continue;
            this.offHandInventory.set(n - 150, itemStack);
        }
    }

    @Override
    public int getSizeInventory() {
        return this.mainInventory.size() + this.armorInventory.size() + this.offHandInventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.mainInventory) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        for (ItemStack itemStack : this.armorInventory) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        for (ItemStack itemStack : this.offHandInventory) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        NonNullList<ItemStack> nonNullList = null;
        for (NonNullList<ItemStack> nonNullList2 : this.allInventories) {
            if (n < nonNullList2.size()) {
                nonNullList = nonNullList2;
                break;
            }
            n -= nonNullList2.size();
        }
        return nonNullList == null ? ItemStack.EMPTY : (ItemStack)nonNullList.get(n);
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("container.inventory");
    }

    public ItemStack armorItemInSlot(int n) {
        return this.armorInventory.get(n);
    }

    public void func_234563_a_(DamageSource damageSource, float f) {
        if (!(f <= 0.0f)) {
            if ((f /= 4.0f) < 1.0f) {
                f = 1.0f;
            }
            for (int i = 0; i < this.armorInventory.size(); ++i) {
                ItemStack itemStack = this.armorInventory.get(i);
                if (damageSource.isFireDamage() && itemStack.getItem().isImmuneToFire() || !(itemStack.getItem() instanceof ArmorItem)) continue;
                int n = i;
                itemStack.damageItem((int)f, this.player, arg_0 -> PlayerInventory.lambda$func_234563_a_$1(n, arg_0));
            }
        }
    }

    public void dropAllItems() {
        for (List list : this.allInventories) {
            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = (ItemStack)list.get(i);
                if (itemStack.isEmpty()) continue;
                this.player.dropItem(itemStack, true, true);
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void markDirty() {
        ++this.timesChanged;
    }

    public int getTimesChanged() {
        return this.timesChanged;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        if (this.player.removed) {
            return true;
        }
        return !(playerEntity.getDistanceSq(this.player) > 64.0);
    }

    public boolean hasItemStack(ItemStack itemStack) {
        for (List list : this.allInventories) {
            for (ItemStack itemStack2 : list) {
                if (itemStack2.isEmpty() || !itemStack2.isItemEqual(itemStack)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean hasTag(ITag<Item> iTag) {
        for (List list : this.allInventories) {
            for (ItemStack itemStack : list) {
                if (itemStack.isEmpty() || !iTag.contains(itemStack.getItem())) continue;
                return false;
            }
        }
        return true;
    }

    public void copyInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, playerInventory.getStackInSlot(i));
        }
        this.currentItem = playerInventory.currentItem;
    }

    @Override
    public void clear() {
        for (List list : this.allInventories) {
            list.clear();
        }
    }

    public void accountStacks(RecipeItemHelper recipeItemHelper) {
        for (ItemStack itemStack : this.mainInventory) {
            recipeItemHelper.accountPlainStack(itemStack);
        }
    }

    private static void lambda$func_234563_a_$1(int n, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, n));
    }

    private static String lambda$add$0(ItemStack itemStack) throws Exception {
        return itemStack.getDisplayName().getString();
    }
}

