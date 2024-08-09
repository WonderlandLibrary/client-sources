/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RepairItemRecipe
extends SpecialRecipe {
    public RepairItemRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            arrayList.add(itemStack);
            if (arrayList.size() <= 1) continue;
            ItemStack itemStack2 = (ItemStack)arrayList.get(0);
            if (itemStack.getItem() == itemStack2.getItem() && itemStack2.getCount() == 1 && itemStack.getCount() == 1 && itemStack2.getItem().isDamageable()) continue;
            return true;
        }
        return arrayList.size() == 2;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        Object object;
        ItemStack itemStack;
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            arrayList.add(itemStack);
            if (arrayList.size() <= 1) continue;
            object = (ItemStack)arrayList.get(0);
            if (itemStack.getItem() == ((ItemStack)object).getItem() && ((ItemStack)object).getCount() == 1 && itemStack.getCount() == 1 && ((ItemStack)object).getItem().isDamageable()) continue;
            return ItemStack.EMPTY;
        }
        if (arrayList.size() == 2) {
            ItemStack itemStack2 = (ItemStack)arrayList.get(0);
            itemStack = (ItemStack)arrayList.get(1);
            if (itemStack2.getItem() == itemStack.getItem() && itemStack2.getCount() == 1 && itemStack.getCount() == 1 && itemStack2.getItem().isDamageable()) {
                object = itemStack2.getItem();
                int n = ((Item)object).getMaxDamage() - itemStack2.getDamage();
                int n2 = ((Item)object).getMaxDamage() - itemStack.getDamage();
                int n3 = n + n2 + ((Item)object).getMaxDamage() * 5 / 100;
                int n4 = ((Item)object).getMaxDamage() - n3;
                if (n4 < 0) {
                    n4 = 0;
                }
                ItemStack itemStack3 = new ItemStack(itemStack2.getItem());
                itemStack3.setDamage(n4);
                HashMap<Enchantment, Integer> hashMap = Maps.newHashMap();
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack2);
                Map<Enchantment, Integer> map2 = EnchantmentHelper.getEnchantments(itemStack);
                Registry.ENCHANTMENT.stream().filter(Enchantment::isCurse).forEach(arg_0 -> RepairItemRecipe.lambda$getCraftingResult$0(map, map2, hashMap, arg_0));
                if (!hashMap.isEmpty()) {
                    EnchantmentHelper.setEnchantments(hashMap, itemStack3);
                }
                return itemStack3;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_REPAIRITEM;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }

    private static void lambda$getCraftingResult$0(Map map, Map map2, Map map3, Enchantment enchantment) {
        int n = Math.max(map.getOrDefault(enchantment, 0), map2.getOrDefault(enchantment, 0));
        if (n > 0) {
            map3.put(enchantment, n);
        }
    }
}

