/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EnchantmentContainer
extends Container {
    private final IInventory tableInventory = new Inventory(this, 2){
        final EnchantmentContainer this$0;
        {
            this.this$0 = enchantmentContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
        }
    };
    private final IWorldPosCallable worldPosCallable;
    private final Random rand = new Random();
    private final IntReferenceHolder xpSeed = IntReferenceHolder.single();
    public final int[] enchantLevels = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] worldClue = new int[]{-1, -1, -1};

    public EnchantmentContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public EnchantmentContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.ENCHANTMENT, n);
        int n2;
        this.worldPosCallable = iWorldPosCallable;
        this.addSlot(new Slot(this, this.tableInventory, 0, 15, 47){
            final EnchantmentContainer this$0;
            {
                this.this$0 = enchantmentContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return false;
            }

            @Override
            public int getSlotStackLimit() {
                return 0;
            }
        });
        this.addSlot(new Slot(this, this.tableInventory, 1, 35, 47){
            final EnchantmentContainer this$0;
            {
                this.this$0 = enchantmentContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() == Items.LAPIS_LAZULI;
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
        this.trackInt(IntReferenceHolder.create(this.enchantLevels, 0));
        this.trackInt(IntReferenceHolder.create(this.enchantLevels, 1));
        this.trackInt(IntReferenceHolder.create(this.enchantLevels, 2));
        this.trackInt(this.xpSeed).set(playerInventory.player.getXPSeed());
        this.trackInt(IntReferenceHolder.create(this.enchantClue, 0));
        this.trackInt(IntReferenceHolder.create(this.enchantClue, 1));
        this.trackInt(IntReferenceHolder.create(this.enchantClue, 2));
        this.trackInt(IntReferenceHolder.create(this.worldClue, 0));
        this.trackInt(IntReferenceHolder.create(this.worldClue, 1));
        this.trackInt(IntReferenceHolder.create(this.worldClue, 2));
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        if (iInventory == this.tableInventory) {
            ItemStack itemStack = iInventory.getStackInSlot(0);
            if (!itemStack.isEmpty() && itemStack.isEnchantable()) {
                this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onCraftMatrixChanged$0(itemStack, arg_0, arg_1));
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantLevels[i] = 0;
                    this.enchantClue[i] = -1;
                    this.worldClue[i] = -1;
                }
            }
        }
    }

    @Override
    public boolean enchantItem(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = this.tableInventory.getStackInSlot(0);
        ItemStack itemStack2 = this.tableInventory.getStackInSlot(1);
        int n2 = n + 1;
        if ((itemStack2.isEmpty() || itemStack2.getCount() < n2) && !playerEntity.abilities.isCreativeMode) {
            return true;
        }
        if (this.enchantLevels[n] <= 0 || itemStack.isEmpty() || (playerEntity.experienceLevel < n2 || playerEntity.experienceLevel < this.enchantLevels[n]) && !playerEntity.abilities.isCreativeMode) {
            return true;
        }
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$enchantItem$1(itemStack, n, playerEntity, n2, itemStack2, arg_0, arg_1));
        return false;
    }

    private List<EnchantmentData> getEnchantmentList(ItemStack itemStack, int n, int n2) {
        this.rand.setSeed(this.xpSeed.get() + n);
        List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, itemStack, n2, false);
        if (itemStack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.rand.nextInt(list.size()));
        }
        return list;
    }

    public int getLapisAmount() {
        ItemStack itemStack = this.tableInventory.getStackInSlot(1);
        return itemStack.isEmpty() ? 0 : itemStack.getCount();
    }

    public int func_217005_f() {
        return this.xpSeed.get();
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$2(playerEntity, arg_0, arg_1));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return EnchantmentContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.ENCHANTING_TABLE);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 0) {
                if (!this.mergeItemStack(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (n == 1) {
                if (!this.mergeItemStack(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.getItem() == Items.LAPIS_LAZULI) {
                if (!this.mergeItemStack(itemStack2, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemStack2)) {
                    return ItemStack.EMPTY;
                }
                ItemStack itemStack3 = itemStack2.copy();
                itemStack3.setCount(1);
                itemStack2.shrink(1);
                ((Slot)this.inventorySlots.get(0)).putStack(itemStack3);
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

    private void lambda$onContainerClosed$2(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, playerEntity.world, this.tableInventory);
    }

    private void lambda$enchantItem$1(ItemStack itemStack, int n, PlayerEntity playerEntity, int n2, ItemStack itemStack2, World world, BlockPos blockPos) {
        ItemStack itemStack3 = itemStack;
        List<EnchantmentData> list = this.getEnchantmentList(itemStack, n, this.enchantLevels[n]);
        if (!list.isEmpty()) {
            boolean bl;
            playerEntity.onEnchant(itemStack, n2);
            boolean bl2 = bl = itemStack.getItem() == Items.BOOK;
            if (bl) {
                itemStack3 = new ItemStack(Items.ENCHANTED_BOOK);
                CompoundNBT compoundNBT = itemStack.getTag();
                if (compoundNBT != null) {
                    itemStack3.setTag(compoundNBT.copy());
                }
                this.tableInventory.setInventorySlotContents(0, itemStack3);
            }
            for (int i = 0; i < list.size(); ++i) {
                EnchantmentData enchantmentData = list.get(i);
                if (bl) {
                    EnchantedBookItem.addEnchantment(itemStack3, enchantmentData);
                    continue;
                }
                itemStack3.addEnchantment(enchantmentData.enchantment, enchantmentData.enchantmentLevel);
            }
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack2.shrink(n2);
                if (itemStack2.isEmpty()) {
                    this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                }
            }
            playerEntity.addStat(Stats.ENCHANT_ITEM);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayerEntity)playerEntity, itemStack3, n2);
            }
            this.tableInventory.markDirty();
            this.xpSeed.set(playerEntity.getXPSeed());
            this.onCraftMatrixChanged(this.tableInventory);
            world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1f + 0.9f);
        }
    }

    private void lambda$onCraftMatrixChanged$0(ItemStack itemStack, World world, BlockPos blockPos) {
        int n;
        int n2 = 0;
        for (n = -1; n <= 1; ++n) {
            for (int i = -1; i <= 1; ++i) {
                if (n == 0 && i == 0 || !world.isAirBlock(blockPos.add(i, 0, n)) || !world.isAirBlock(blockPos.add(i, 1, n))) continue;
                if (world.getBlockState(blockPos.add(i * 2, 0, n * 2)).isIn(Blocks.BOOKSHELF)) {
                    ++n2;
                }
                if (world.getBlockState(blockPos.add(i * 2, 1, n * 2)).isIn(Blocks.BOOKSHELF)) {
                    ++n2;
                }
                if (i == 0 || n == 0) continue;
                if (world.getBlockState(blockPos.add(i * 2, 0, n)).isIn(Blocks.BOOKSHELF)) {
                    ++n2;
                }
                if (world.getBlockState(blockPos.add(i * 2, 1, n)).isIn(Blocks.BOOKSHELF)) {
                    ++n2;
                }
                if (world.getBlockState(blockPos.add(i, 0, n * 2)).isIn(Blocks.BOOKSHELF)) {
                    ++n2;
                }
                if (!world.getBlockState(blockPos.add(i, 1, n * 2)).isIn(Blocks.BOOKSHELF)) continue;
                ++n2;
            }
        }
        this.rand.setSeed(this.xpSeed.get());
        for (n = 0; n < 3; ++n) {
            this.enchantLevels[n] = EnchantmentHelper.calcItemStackEnchantability(this.rand, n, n2, itemStack);
            this.enchantClue[n] = -1;
            this.worldClue[n] = -1;
            if (this.enchantLevels[n] >= n + 1) continue;
            this.enchantLevels[n] = 0;
        }
        for (n = 0; n < 3; ++n) {
            List<EnchantmentData> list;
            if (this.enchantLevels[n] <= 0 || (list = this.getEnchantmentList(itemStack, n, this.enchantLevels[n])) == null || list.isEmpty()) continue;
            EnchantmentData enchantmentData = list.get(this.rand.nextInt(list.size()));
            this.enchantClue[n] = Registry.ENCHANTMENT.getId(enchantmentData.enchantment);
            this.worldClue[n] = enchantmentData.enchantmentLevel;
        }
        this.detectAndSendChanges();
    }
}

