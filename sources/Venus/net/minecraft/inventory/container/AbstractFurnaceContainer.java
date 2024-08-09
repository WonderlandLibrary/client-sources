/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacerFurnace;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public abstract class AbstractFurnaceContainer
extends RecipeBookContainer<IInventory> {
    private final IInventory furnaceInventory;
    private final IIntArray furnaceData;
    protected final World world;
    private final IRecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookCategory field_242384_g;

    protected AbstractFurnaceContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> iRecipeType, RecipeBookCategory recipeBookCategory, int n, PlayerInventory playerInventory) {
        this(containerType, iRecipeType, recipeBookCategory, n, playerInventory, new Inventory(3), new IntArray(4));
    }

    protected AbstractFurnaceContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> iRecipeType, RecipeBookCategory recipeBookCategory, int n, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray) {
        super(containerType, n);
        int n2;
        this.recipeType = iRecipeType;
        this.field_242384_g = recipeBookCategory;
        AbstractFurnaceContainer.assertInventorySize(iInventory, 3);
        AbstractFurnaceContainer.assertIntArraySize(iIntArray, 4);
        this.furnaceInventory = iInventory;
        this.furnaceData = iIntArray;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(iInventory, 0, 56, 17));
        this.addSlot(new FurnaceFuelSlot(this, iInventory, 1, 56, 53));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, iInventory, 2, 116, 35));
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + n2 * 9 + 9, 8 + i * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 8 + n2 * 18, 142));
        }
        this.trackIntArray(iIntArray);
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        if (this.furnaceInventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)((Object)this.furnaceInventory)).fillStackedContents(recipeItemHelper);
        }
    }

    @Override
    public void clear() {
        this.furnaceInventory.clear();
    }

    @Override
    public void func_217056_a(boolean bl, IRecipe<?> iRecipe, ServerPlayerEntity serverPlayerEntity) {
        new ServerRecipePlacerFurnace<IInventory>(this).place(serverPlayerEntity, iRecipe, bl);
    }

    @Override
    public boolean matches(IRecipe<? super IInventory> iRecipe) {
        return iRecipe.matches(this.furnaceInventory, this.world);
    }

    @Override
    public int getOutputSlot() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.furnaceInventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 2) {
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n != 1 && n != 0 ? (this.hasRecipe(itemStack2) ? !this.mergeItemStack(itemStack2, 0, 1, true) : (this.isFuel(itemStack2) ? !this.mergeItemStack(itemStack2, 1, 2, true) : (n >= 3 && n < 30 ? !this.mergeItemStack(itemStack2, 30, 39, true) : n >= 30 && n < 39 && !this.mergeItemStack(itemStack2, 3, 30, true)))) : !this.mergeItemStack(itemStack2, 3, 39, true)) {
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

    protected boolean hasRecipe(ItemStack itemStack) {
        return this.world.getRecipeManager().getRecipe(this.recipeType, new Inventory(itemStack), this.world).isPresent();
    }

    protected boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceTileEntity.isFuel(itemStack);
    }

    public int getCookProgressionScaled() {
        int n = this.furnaceData.get(2);
        int n2 = this.furnaceData.get(3);
        return n2 != 0 && n != 0 ? n * 24 / n2 : 0;
    }

    public int getBurnLeftScaled() {
        int n = this.furnaceData.get(1);
        if (n == 0) {
            n = 200;
        }
        return this.furnaceData.get(0) * 13 / n;
    }

    public boolean isBurning() {
        return this.furnaceData.get(0) > 0;
    }

    @Override
    public RecipeBookCategory func_241850_m() {
        return this.field_242384_g;
    }
}

