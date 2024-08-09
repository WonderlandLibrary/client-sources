/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmithingTableContainer
extends AbstractRepairContainer {
    private final World field_234651_g_;
    @Nullable
    private SmithingRecipe field_234652_h_;
    private final List<SmithingRecipe> field_241443_i_;

    public SmithingTableContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public SmithingTableContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.SMITHING, n, playerInventory, iWorldPosCallable);
        this.field_234651_g_ = playerInventory.player.world;
        this.field_241443_i_ = this.field_234651_g_.getRecipeManager().getRecipesForType(IRecipeType.SMITHING);
    }

    @Override
    protected boolean func_230302_a_(BlockState blockState) {
        return blockState.isIn(Blocks.SMITHING_TABLE);
    }

    @Override
    protected boolean func_230303_b_(PlayerEntity playerEntity, boolean bl) {
        return this.field_234652_h_ != null && this.field_234652_h_.matches(this.field_234643_d_, this.field_234651_g_);
    }

    @Override
    protected ItemStack func_230301_a_(PlayerEntity playerEntity, ItemStack itemStack) {
        itemStack.onCrafting(playerEntity.world, playerEntity, itemStack.getCount());
        this.field_234642_c_.onCrafting(playerEntity);
        this.func_234654_d_(0);
        this.func_234654_d_(1);
        this.field_234644_e_.consume(SmithingTableContainer::lambda$func_230301_a_$0);
        return itemStack;
    }

    private void func_234654_d_(int n) {
        ItemStack itemStack = this.field_234643_d_.getStackInSlot(n);
        itemStack.shrink(1);
        this.field_234643_d_.setInventorySlotContents(n, itemStack);
    }

    @Override
    public void updateRepairOutput() {
        List<SmithingRecipe> list = this.field_234651_g_.getRecipeManager().getRecipes(IRecipeType.SMITHING, this.field_234643_d_, this.field_234651_g_);
        if (list.isEmpty()) {
            this.field_234642_c_.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            this.field_234652_h_ = list.get(0);
            ItemStack itemStack = this.field_234652_h_.getCraftingResult(this.field_234643_d_);
            this.field_234642_c_.setRecipeUsed(this.field_234652_h_);
            this.field_234642_c_.setInventorySlotContents(0, itemStack);
        }
    }

    @Override
    protected boolean func_241210_a_(ItemStack itemStack) {
        return this.field_241443_i_.stream().anyMatch(arg_0 -> SmithingTableContainer.lambda$func_241210_a_$1(itemStack, arg_0));
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.field_234642_c_ && super.canMergeSlot(itemStack, slot);
    }

    private static boolean lambda$func_241210_a_$1(ItemStack itemStack, SmithingRecipe smithingRecipe) {
        return smithingRecipe.isValidAdditionItem(itemStack);
    }

    private static void lambda$func_230301_a_$0(World world, BlockPos blockPos) {
        world.playEvent(1044, blockPos, 0);
    }
}

