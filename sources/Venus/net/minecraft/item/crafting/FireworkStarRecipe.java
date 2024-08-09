/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class FireworkStarRecipe
extends SpecialRecipe {
    private static final Ingredient INGREDIENT_SHAPE = Ingredient.fromItems(Items.FIRE_CHARGE, Items.FEATHER, Items.GOLD_NUGGET, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD);
    private static final Ingredient INGREDIENT_FLICKER = Ingredient.fromItems(Items.DIAMOND);
    private static final Ingredient INGREDIENT_TRAIL = Ingredient.fromItems(Items.GLOWSTONE_DUST);
    private static final Map<Item, FireworkRocketItem.Shape> ITEM_SHAPE_MAP = Util.make(Maps.newHashMap(), FireworkStarRecipe::lambda$static$0);
    private static final Ingredient INGREDIENT_GUNPOWDER = Ingredient.fromItems(Items.GUNPOWDER);

    public FireworkStarRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (INGREDIENT_SHAPE.test(itemStack)) {
                if (bl3) {
                    return true;
                }
                bl3 = true;
                continue;
            }
            if (INGREDIENT_TRAIL.test(itemStack)) {
                if (bl5) {
                    return true;
                }
                bl5 = true;
                continue;
            }
            if (INGREDIENT_FLICKER.test(itemStack)) {
                if (bl4) {
                    return true;
                }
                bl4 = true;
                continue;
            }
            if (INGREDIENT_GUNPOWDER.test(itemStack)) {
                if (bl) {
                    return true;
                }
                bl = true;
                continue;
            }
            if (!(itemStack.getItem() instanceof DyeItem)) {
                return true;
            }
            bl2 = true;
        }
        return bl && bl2;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_STAR);
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("Explosion");
        FireworkRocketItem.Shape shape = FireworkRocketItem.Shape.SMALL_BALL;
        ArrayList<Integer> arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (INGREDIENT_SHAPE.test(itemStack2)) {
                shape = ITEM_SHAPE_MAP.get(itemStack2.getItem());
                continue;
            }
            if (INGREDIENT_TRAIL.test(itemStack2)) {
                compoundNBT.putBoolean("Flicker", false);
                continue;
            }
            if (INGREDIENT_FLICKER.test(itemStack2)) {
                compoundNBT.putBoolean("Trail", false);
                continue;
            }
            if (!(itemStack2.getItem() instanceof DyeItem)) continue;
            arrayList.add(((DyeItem)itemStack2.getItem()).getDyeColor().getFireworkColor());
        }
        compoundNBT.putIntArray("Colors", arrayList);
        compoundNBT.putByte("Type", (byte)shape.getIndex());
        return itemStack;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.FIREWORK_STAR);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(Items.FIRE_CHARGE, FireworkRocketItem.Shape.LARGE_BALL);
        hashMap.put(Items.FEATHER, FireworkRocketItem.Shape.BURST);
        hashMap.put(Items.GOLD_NUGGET, FireworkRocketItem.Shape.STAR);
        hashMap.put(Items.SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
        hashMap.put(Items.WITHER_SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
        hashMap.put(Items.CREEPER_HEAD, FireworkRocketItem.Shape.CREEPER);
        hashMap.put(Items.PLAYER_HEAD, FireworkRocketItem.Shape.CREEPER);
        hashMap.put(Items.DRAGON_HEAD, FireworkRocketItem.Shape.CREEPER);
        hashMap.put(Items.ZOMBIE_HEAD, FireworkRocketItem.Shape.CREEPER);
    }
}

