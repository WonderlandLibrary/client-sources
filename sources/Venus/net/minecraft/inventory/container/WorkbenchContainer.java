/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorkbenchContainer
extends RecipeBookContainer<CraftingInventory> {
    private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    public WorkbenchContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public WorkbenchContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.CRAFTING, n);
        int n2;
        int n3;
        this.worldPosCallable = iWorldPosCallable;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 3; ++n2) {
                this.addSlot(new Slot(this.craftMatrix, n2 + n3 * 3, 30 + n2 * 18, 17 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.addSlot(new Slot(playerInventory, n2 + n3 * 9 + 9, 8 + n2 * 18, 84 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.addSlot(new Slot(playerInventory, n3, 8 + n3 * 18, 142));
        }
    }

    protected static void updateCraftingResult(int n, World world, PlayerEntity playerEntity, CraftingInventory craftingInventory, CraftResultInventory craftResultInventory) {
        if (!world.isRemote) {
            ICraftingRecipe iCraftingRecipe;
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)playerEntity;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent() && craftResultInventory.canUseRecipe(world, serverPlayerEntity, iCraftingRecipe = optional.get())) {
                itemStack = iCraftingRecipe.getCraftingResult(craftingInventory);
            }
            craftResultInventory.setInventorySlotContents(0, itemStack);
            serverPlayerEntity.connection.sendPacket(new SSetSlotPacket(n, 0, itemStack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        this.worldPosCallable.consume(this::lambda$onCraftMatrixChanged$0);
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        this.craftMatrix.fillStackedContents(recipeItemHelper);
    }

    @Override
    public void clear() {
        this.craftMatrix.clear();
        this.craftResult.clear();
    }

    @Override
    public boolean matches(IRecipe<? super CraftingInventory> iRecipe) {
        return iRecipe.matches(this.craftMatrix, this.player.world);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$1(playerEntity, arg_0, arg_1));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return WorkbenchContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.CRAFTING_TABLE);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 0) {
                this.worldPosCallable.consume((arg_0, arg_1) -> WorkbenchContainer.lambda$transferStackInSlot$2(itemStack2, playerEntity, arg_0, arg_1));
                if (!this.mergeItemStack(itemStack2, 10, 46, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n >= 10 && n < 46 ? !this.mergeItemStack(itemStack2, 1, 10, true) && (n < 37 ? !this.mergeItemStack(itemStack2, 37, 46, true) : !this.mergeItemStack(itemStack2, 10, 37, true)) : !this.mergeItemStack(itemStack2, 10, 46, true)) {
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
            ItemStack itemStack3 = slot.onTake(playerEntity, itemStack2);
            if (n == 0) {
                playerEntity.dropItem(itemStack3, true);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
    }

    @Override
    public int getOutputSlot() {
        return 1;
    }

    @Override
    public int getWidth() {
        return this.craftMatrix.getWidth();
    }

    @Override
    public int getHeight() {
        return this.craftMatrix.getHeight();
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public RecipeBookCategory func_241850_m() {
        return RecipeBookCategory.CRAFTING;
    }

    private static void lambda$transferStackInSlot$2(ItemStack itemStack, PlayerEntity playerEntity, World world, BlockPos blockPos) {
        itemStack.getItem().onCreated(itemStack, world, playerEntity);
    }

    private void lambda$onContainerClosed$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, world, this.craftMatrix);
    }

    private void lambda$onCraftMatrixChanged$0(World world, BlockPos blockPos) {
        WorkbenchContainer.updateCraftingResult(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
    }
}

