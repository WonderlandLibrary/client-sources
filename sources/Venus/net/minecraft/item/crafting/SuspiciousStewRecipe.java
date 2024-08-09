/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SuspiciousStewRecipe
extends SpecialRecipe {
    public SuspiciousStewRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !bl3) {
                bl3 = true;
                continue;
            }
            if (itemStack.getItem() == Blocks.RED_MUSHROOM.asItem() && !bl2) {
                bl2 = true;
                continue;
            }
            if (itemStack.getItem().isIn(ItemTags.SMALL_FLOWERS) && !bl) {
                bl = true;
                continue;
            }
            if (itemStack.getItem() != Items.BOWL || bl4) {
                return true;
            }
            bl4 = true;
        }
        return bl && bl3 && bl2 && bl4;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        Object object;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            object = craftingInventory.getStackInSlot(i);
            if (((ItemStack)object).isEmpty() || !((ItemStack)object).getItem().isIn(ItemTags.SMALL_FLOWERS)) continue;
            itemStack = object;
            break;
        }
        ItemStack itemStack2 = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof FlowerBlock) {
            object = (FlowerBlock)((BlockItem)itemStack.getItem()).getBlock();
            Effect effect = ((FlowerBlock)object).getStewEffect();
            SuspiciousStewItem.addEffect(itemStack2, effect, ((FlowerBlock)object).getStewEffectDuration());
        }
        return itemStack2;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n >= 2 && n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_SUSPICIOUSSTEW;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }
}

