/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrindstoneContainer
extends Container {
    private final IInventory outputInventory = new CraftResultInventory();
    private final IInventory inputInventory = new Inventory(this, 2){
        final GrindstoneContainer this$0;
        {
            this.this$0 = grindstoneContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
        }
    };
    private final IWorldPosCallable worldPosCallable;

    public GrindstoneContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public GrindstoneContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.GRINDSTONE, n);
        int n2;
        this.worldPosCallable = iWorldPosCallable;
        this.addSlot(new Slot(this, this.inputInventory, 0, 49, 19){
            final GrindstoneContainer this$0;
            {
                this.this$0 = grindstoneContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.isDamageable() || itemStack.getItem() == Items.ENCHANTED_BOOK || itemStack.isEnchanted();
            }
        });
        this.addSlot(new Slot(this, this.inputInventory, 1, 49, 40){
            final GrindstoneContainer this$0;
            {
                this.this$0 = grindstoneContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.isDamageable() || itemStack.getItem() == Items.ENCHANTED_BOOK || itemStack.isEnchanted();
            }
        });
        this.addSlot(new Slot(this, this.outputInventory, 2, 129, 34, iWorldPosCallable){
            final IWorldPosCallable val$worldPosCallableIn;
            final GrindstoneContainer this$0;
            {
                this.this$0 = grindstoneContainer;
                this.val$worldPosCallableIn = iWorldPosCallable;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return true;
            }

            @Override
            public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
                this.val$worldPosCallableIn.consume(this::lambda$onTake$0);
                this.this$0.inputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                this.this$0.inputInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                return itemStack;
            }

            private int getEnchantmentXpFromInputs(World world) {
                int n = 0;
                n += this.getEnchantmentXp(this.this$0.inputInventory.getStackInSlot(0));
                if ((n += this.getEnchantmentXp(this.this$0.inputInventory.getStackInSlot(1))) > 0) {
                    int n2 = (int)Math.ceil((double)n / 2.0);
                    return n2 + world.rand.nextInt(n2);
                }
                return 1;
            }

            private int getEnchantmentXp(ItemStack itemStack) {
                int n = 0;
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    Integer n2 = entry.getValue();
                    if (enchantment.isCurse()) continue;
                    n += enchantment.getMinEnchantability(n2);
                }
                return n;
            }

            private void lambda$onTake$0(World world, BlockPos blockPos) {
                int n;
                for (int i = this.getEnchantmentXpFromInputs(world); i > 0; i -= n) {
                    n = ExperienceOrbEntity.getXPSplit(i);
                    world.addEntity(new ExperienceOrbEntity(world, blockPos.getX(), (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, n));
                }
                world.playEvent(1042, blockPos, 0);
            }
        });
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
    public void onCraftMatrixChanged(IInventory iInventory) {
        super.onCraftMatrixChanged(iInventory);
        if (iInventory == this.inputInventory) {
            this.updateRecipeOutput();
        }
    }

    private void updateRecipeOutput() {
        boolean bl;
        ItemStack itemStack = this.inputInventory.getStackInSlot(0);
        ItemStack itemStack2 = this.inputInventory.getStackInSlot(1);
        boolean bl2 = !itemStack.isEmpty() || !itemStack2.isEmpty();
        boolean bl3 = bl = !itemStack.isEmpty() && !itemStack2.isEmpty();
        if (!bl2) {
            this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            ItemStack itemStack3;
            int n;
            boolean bl4;
            boolean bl5 = bl4 = !itemStack.isEmpty() && itemStack.getItem() != Items.ENCHANTED_BOOK && !itemStack.isEnchanted() || !itemStack2.isEmpty() && itemStack2.getItem() != Items.ENCHANTED_BOOK && !itemStack2.isEnchanted();
            if (itemStack.getCount() > 1 || itemStack2.getCount() > 1 || !bl && bl4) {
                this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                this.detectAndSendChanges();
                return;
            }
            int n2 = 1;
            if (bl) {
                if (itemStack.getItem() != itemStack2.getItem()) {
                    this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                    this.detectAndSendChanges();
                    return;
                }
                Item item = itemStack.getItem();
                int n3 = item.getMaxDamage() - itemStack.getDamage();
                int n4 = item.getMaxDamage() - itemStack2.getDamage();
                int n5 = n3 + n4 + item.getMaxDamage() * 5 / 100;
                n = Math.max(item.getMaxDamage() - n5, 0);
                itemStack3 = this.copyEnchantments(itemStack, itemStack2);
                if (!itemStack3.isDamageable()) {
                    if (!ItemStack.areItemStacksEqual(itemStack, itemStack2)) {
                        this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                        this.detectAndSendChanges();
                        return;
                    }
                    n2 = 2;
                }
            } else {
                boolean bl6 = !itemStack.isEmpty();
                n = bl6 ? itemStack.getDamage() : itemStack2.getDamage();
                itemStack3 = bl6 ? itemStack : itemStack2;
            }
            this.outputInventory.setInventorySlotContents(0, this.removeEnchantments(itemStack3, n, n2));
        }
        this.detectAndSendChanges();
    }

    private ItemStack copyEnchantments(ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = itemStack.copy();
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack2);
        for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment.isCurse() && EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack3) != 0) continue;
            itemStack3.addEnchantment(enchantment, entry.getValue());
        }
        return itemStack3;
    }

    private ItemStack removeEnchantments(ItemStack itemStack, int n, int n2) {
        ItemStack itemStack2 = itemStack.copy();
        itemStack2.removeChildTag("Enchantments");
        itemStack2.removeChildTag("StoredEnchantments");
        if (n > 0) {
            itemStack2.setDamage(n);
        } else {
            itemStack2.removeChildTag("Damage");
        }
        itemStack2.setCount(n2);
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack).entrySet().stream().filter(GrindstoneContainer::lambda$removeEnchantments$0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, itemStack2);
        itemStack2.setRepairCost(0);
        if (itemStack2.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            itemStack2 = new ItemStack(Items.BOOK);
            if (itemStack.hasDisplayName()) {
                itemStack2.setDisplayName(itemStack.getDisplayName());
            }
        }
        for (int i = 0; i < map.size(); ++i) {
            itemStack2.setRepairCost(RepairContainer.getNewRepairCost(itemStack2.getRepairCost()));
        }
        return itemStack2;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$1(playerEntity, arg_0, arg_1));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return GrindstoneContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.GRINDSTONE);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            ItemStack itemStack3 = this.inputInventory.getStackInSlot(0);
            ItemStack itemStack4 = this.inputInventory.getStackInSlot(1);
            if (n == 2) {
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n != 0 && n != 1 ? (!itemStack3.isEmpty() && !itemStack4.isEmpty() ? (n >= 3 && n < 30 ? !this.mergeItemStack(itemStack2, 30, 39, true) : n >= 30 && n < 39 && !this.mergeItemStack(itemStack2, 3, 30, true)) : !this.mergeItemStack(itemStack2, 0, 2, true)) : !this.mergeItemStack(itemStack2, 3, 39, true)) {
                return ItemStack.EMPTY;
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

    private void lambda$onContainerClosed$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, world, this.inputInventory);
    }

    private static boolean lambda$removeEnchantments$0(Map.Entry entry) {
        return ((Enchantment)entry.getKey()).isCurse();
    }
}

