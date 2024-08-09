/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class BrewingStandContainer
extends Container {
    private final IInventory tileBrewingStand;
    private final IIntArray field_216983_d;
    private final Slot slot;

    public BrewingStandContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, new Inventory(5), new IntArray(2));
    }

    public BrewingStandContainer(int n, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray) {
        super(ContainerType.BREWING_STAND, n);
        int n2;
        BrewingStandContainer.assertInventorySize(iInventory, 5);
        BrewingStandContainer.assertIntArraySize(iIntArray, 2);
        this.tileBrewingStand = iInventory;
        this.field_216983_d = iIntArray;
        this.addSlot(new PotionSlot(iInventory, 0, 56, 51));
        this.addSlot(new PotionSlot(iInventory, 1, 79, 58));
        this.addSlot(new PotionSlot(iInventory, 2, 102, 51));
        this.slot = this.addSlot(new IngredientSlot(iInventory, 3, 79, 17));
        this.addSlot(new FuelSlot(iInventory, 4, 17, 17));
        this.trackIntArray(iIntArray);
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + n2 * 9 + 9, 8 + i * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 8 + n2 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.tileBrewingStand.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if ((n < 0 || n > 2) && n != 3 && n != 4) {
                if (FuelSlot.isValidBrewingFuel(itemStack) ? this.mergeItemStack(itemStack2, 4, 5, true) || this.slot.isItemValid(itemStack2) && !this.mergeItemStack(itemStack2, 3, 4, true) : (this.slot.isItemValid(itemStack2) ? !this.mergeItemStack(itemStack2, 3, 4, true) : (PotionSlot.canHoldPotion(itemStack) && itemStack.getCount() == 1 ? !this.mergeItemStack(itemStack2, 0, 3, true) : (n >= 5 && n < 32 ? !this.mergeItemStack(itemStack2, 32, 41, true) : (n >= 32 && n < 41 ? !this.mergeItemStack(itemStack2, 5, 32, true) : !this.mergeItemStack(itemStack2, 5, 41, true)))))) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.mergeItemStack(itemStack2, 5, 41, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerEntity, itemStack2);
        }
        return itemStack;
    }

    public int func_216982_e() {
        return this.field_216983_d.get(1);
    }

    public int func_216981_f() {
        return this.field_216983_d.get(0);
    }

    static class PotionSlot
    extends Slot {
        public PotionSlot(IInventory iInventory, int n, int n2, int n3) {
            super(iInventory, n, n2, n3);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return PotionSlot.canHoldPotion(itemStack);
        }

        @Override
        public int getSlotStackLimit() {
            return 0;
        }

        @Override
        public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
            Potion potion = PotionUtils.getPotionFromItem(itemStack);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.BREWED_POTION.trigger((ServerPlayerEntity)playerEntity, potion);
            }
            super.onTake(playerEntity, itemStack);
            return itemStack;
        }

        public static boolean canHoldPotion(ItemStack itemStack) {
            Item item = itemStack.getItem();
            return item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE;
        }
    }

    static class IngredientSlot
    extends Slot {
        public IngredientSlot(IInventory iInventory, int n, int n2, int n3) {
            super(iInventory, n, n2, n3);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return PotionBrewing.isReagent(itemStack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

    static class FuelSlot
    extends Slot {
        public FuelSlot(IInventory iInventory, int n, int n2, int n3) {
            super(iInventory, n, n2, n3);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return FuelSlot.isValidBrewingFuel(itemStack);
        }

        public static boolean isValidBrewingFuel(ItemStack itemStack) {
            return itemStack.getItem() == Items.BLAZE_POWDER;
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }
}

