/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.ResourceLocation;

public class PlayerContainer
extends RecipeBookContainer<CraftingInventory> {
    public static final ResourceLocation LOCATION_BLOCKS_TEXTURE = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    private final CraftingInventory craftMatrix = new CraftingInventory(this, 2, 2);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    public final boolean isLocalWorld;
    private final PlayerEntity player;

    public PlayerContainer(PlayerInventory playerInventory, boolean bl, PlayerEntity playerEntity) {
        super(null, 0);
        int n;
        this.isLocalWorld = bl;
        this.player = playerEntity;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));
        for (n = 0; n < 2; ++n) {
            for (int i = 0; i < 2; ++i) {
                this.addSlot(new Slot(this.craftMatrix, i + n * 2, 98 + i * 18, 18 + n * 18));
            }
        }
        for (n = 0; n < 4; ++n) {
            EquipmentSlotType equipmentSlotType = VALID_EQUIPMENT_SLOTS[n];
            this.addSlot(new Slot(this, playerInventory, 39 - n, 8, 8 + n * 18, equipmentSlotType){
                final EquipmentSlotType val$equipmentslottype;
                final PlayerContainer this$0;
                {
                    this.this$0 = playerContainer;
                    this.val$equipmentslottype = equipmentSlotType;
                    super(iInventory, n, n2, n3);
                }

                @Override
                public int getSlotStackLimit() {
                    return 0;
                }

                @Override
                public boolean isItemValid(ItemStack itemStack) {
                    return this.val$equipmentslottype == MobEntity.getSlotForItemStack(itemStack);
                }

                @Override
                public boolean canTakeStack(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack) ? false : super.canTakeStack(playerEntity);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getBackground() {
                    return Pair.of(LOCATION_BLOCKS_TEXTURE, ARMOR_SLOT_TEXTURES[this.val$equipmentslottype.getIndex()]);
                }
            });
        }
        for (n = 0; n < 3; ++n) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + (n + 1) * 9, 8 + i * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 142));
        }
        this.addSlot(new Slot(this, playerInventory, 40, 77, 62){
            final PlayerContainer this$0;
            {
                this.this$0 = playerContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getBackground() {
                return Pair.of(LOCATION_BLOCKS_TEXTURE, EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        this.craftMatrix.fillStackedContents(recipeItemHelper);
    }

    @Override
    public void clear() {
        this.craftResult.clear();
        this.craftMatrix.clear();
    }

    @Override
    public boolean matches(IRecipe<? super CraftingInventory> iRecipe) {
        return iRecipe.matches(this.craftMatrix, this.player.world);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        WorkbenchContainer.updateCraftingResult(this.windowId, this.player.world, this.player, this.craftMatrix, this.craftResult);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.craftResult.clear();
        if (!playerEntity.world.isRemote) {
            this.clearContainer(playerEntity, playerEntity.world, this.craftMatrix);
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            int n2;
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
            if (n == 0) {
                if (!this.mergeItemStack(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n >= 1 && n < 5 ? !this.mergeItemStack(itemStack2, 9, 45, true) : (n >= 5 && n < 9 ? !this.mergeItemStack(itemStack2, 9, 45, true) : (equipmentSlotType.getSlotType() == EquipmentSlotType.Group.ARMOR && !((Slot)this.inventorySlots.get(8 - equipmentSlotType.getIndex())).getHasStack() ? !this.mergeItemStack(itemStack2, n2 = 8 - equipmentSlotType.getIndex(), n2 + 1, true) : (equipmentSlotType == EquipmentSlotType.OFFHAND && !((Slot)this.inventorySlots.get(45)).getHasStack() ? !this.mergeItemStack(itemStack2, 45, 46, true) : (n >= 9 && n < 36 ? !this.mergeItemStack(itemStack2, 36, 45, true) : (n >= 36 && n < 45 ? !this.mergeItemStack(itemStack2, 9, 36, true) : !this.mergeItemStack(itemStack2, 9, 45, true))))))) {
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
        return 0;
    }

    public CraftingInventory func_234641_j_() {
        return this.craftMatrix;
    }

    @Override
    public RecipeBookCategory func_241850_m() {
        return RecipeBookCategory.CRAFTING;
    }
}

