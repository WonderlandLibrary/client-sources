/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StonecutterContainer
extends Container {
    private final IWorldPosCallable worldPosCallable;
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    private final World world;
    private List<StonecuttingRecipe> recipes = Lists.newArrayList();
    private ItemStack itemStackInput = ItemStack.EMPTY;
    private long lastOnTake;
    final Slot inputInventorySlot;
    final Slot outputInventorySlot;
    private Runnable inventoryUpdateListener = StonecutterContainer::lambda$new$0;
    public final IInventory inputInventory = new Inventory(this, 1){
        final StonecutterContainer this$0;
        {
            this.this$0 = stonecutterContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
            this.this$0.inventoryUpdateListener.run();
        }
    };
    private final CraftResultInventory inventory = new CraftResultInventory();

    public StonecutterContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public StonecutterContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.STONECUTTER, n);
        int n2;
        this.worldPosCallable = iWorldPosCallable;
        this.world = playerInventory.player.world;
        this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
        this.outputInventorySlot = this.addSlot(new Slot(this, this.inventory, 1, 143, 33, iWorldPosCallable){
            final IWorldPosCallable val$worldPosCallableIn;
            final StonecutterContainer this$0;
            {
                this.this$0 = stonecutterContainer;
                this.val$worldPosCallableIn = iWorldPosCallable;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return true;
            }

            @Override
            public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
                itemStack.onCrafting(playerEntity.world, playerEntity, itemStack.getCount());
                this.this$0.inventory.onCrafting(playerEntity);
                ItemStack itemStack2 = this.this$0.inputInventorySlot.decrStackSize(1);
                if (!itemStack2.isEmpty()) {
                    this.this$0.updateRecipeResultSlot();
                }
                this.val$worldPosCallableIn.consume(this::lambda$onTake$0);
                return super.onTake(playerEntity, itemStack);
            }

            private void lambda$onTake$0(World world, BlockPos blockPos) {
                long l = world.getGameTime();
                if (this.this$0.lastOnTake != l) {
                    world.playSound(null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    this.this$0.lastOnTake = l;
                }
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
        this.trackInt(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<StonecuttingRecipe> getRecipeList() {
        return this.recipes;
    }

    public int getRecipeListSize() {
        return this.recipes.size();
    }

    public boolean hasItemsinInputSlot() {
        return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return StonecutterContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.STONECUTTER);
    }

    @Override
    public boolean enchantItem(PlayerEntity playerEntity, int n) {
        if (this.func_241818_d_(n)) {
            this.selectedRecipe.set(n);
            this.updateRecipeResultSlot();
        }
        return false;
    }

    private boolean func_241818_d_(int n) {
        return n >= 0 && n < this.recipes.size();
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        ItemStack itemStack = this.inputInventorySlot.getStack();
        if (itemStack.getItem() != this.itemStackInput.getItem()) {
            this.itemStackInput = itemStack.copy();
            this.updateAvailableRecipes(iInventory, itemStack);
        }
    }

    private void updateAvailableRecipes(IInventory iInventory, ItemStack itemStack) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.putStack(ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.recipes = this.world.getRecipeManager().getRecipes(IRecipeType.STONECUTTING, iInventory, this.world);
        }
    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.func_241818_d_(this.selectedRecipe.get())) {
            StonecuttingRecipe stonecuttingRecipe = this.recipes.get(this.selectedRecipe.get());
            this.inventory.setRecipeUsed(stonecuttingRecipe);
            this.outputInventorySlot.putStack(stonecuttingRecipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputInventorySlot.putStack(ItemStack.EMPTY);
        }
        this.detectAndSendChanges();
    }

    @Override
    public ContainerType<?> getType() {
        return ContainerType.STONECUTTER;
    }

    public void setInventoryUpdateListener(Runnable runnable) {
        this.inventoryUpdateListener = runnable;
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.inventory && super.canMergeSlot(itemStack, slot);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (n == 1) {
                item.onCreated(itemStack2, playerEntity.world, playerEntity);
                if (!this.mergeItemStack(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n == 0 ? !this.mergeItemStack(itemStack2, 2, 38, true) : (this.world.getRecipeManager().getRecipe(IRecipeType.STONECUTTING, new Inventory(itemStack2), this.world).isPresent() ? !this.mergeItemStack(itemStack2, 0, 1, true) : (n >= 2 && n < 29 ? !this.mergeItemStack(itemStack2, 29, 38, true) : n >= 29 && n < 38 && !this.mergeItemStack(itemStack2, 2, 29, true)))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            slot.onSlotChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerEntity, itemStack2);
            this.detectAndSendChanges();
        }
        return itemStack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.inventory.removeStackFromSlot(1);
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$1(playerEntity, arg_0, arg_1));
    }

    private void lambda$onContainerClosed$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, playerEntity.world, this.inputInventory);
    }

    private static void lambda$new$0() {
    }
}

