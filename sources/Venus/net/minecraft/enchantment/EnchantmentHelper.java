/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
    public static int getEnchantmentLevel(Enchantment enchantment, ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 1;
        }
        ResourceLocation resourceLocation = Registry.ENCHANTMENT.getKey(enchantment);
        ListNBT listNBT = itemStack.getEnchantmentTagList();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            ResourceLocation resourceLocation2 = ResourceLocation.tryCreate(compoundNBT.getString("id"));
            if (resourceLocation2 == null || !resourceLocation2.equals(resourceLocation)) continue;
            return MathHelper.clamp(compoundNBT.getInt("lvl"), 0, 255);
        }
        return 1;
    }

    public static Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
        ListNBT listNBT = itemStack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
        return EnchantmentHelper.deserializeEnchantments(listNBT);
    }

    public static Map<Enchantment, Integer> deserializeEnchantments(ListNBT listNBT) {
        LinkedHashMap<Enchantment, Integer> linkedHashMap = Maps.newLinkedHashMap();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundNBT.getString("id"))).ifPresent(arg_0 -> EnchantmentHelper.lambda$deserializeEnchantments$0(linkedHashMap, compoundNBT, arg_0));
        }
        return linkedHashMap;
    }

    public static void setEnchantments(Map<Enchantment, Integer> map, ItemStack itemStack) {
        ListNBT listNBT = new ListNBT();
        for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment == null) continue;
            int n = entry.getValue();
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("id", String.valueOf(Registry.ENCHANTMENT.getKey(enchantment)));
            compoundNBT.putShort("lvl", (short)n);
            listNBT.add(compoundNBT);
            if (itemStack.getItem() != Items.ENCHANTED_BOOK) continue;
            EnchantedBookItem.addEnchantment(itemStack, new EnchantmentData(enchantment, n));
        }
        if (listNBT.isEmpty()) {
            itemStack.removeChildTag("Enchantments");
        } else if (itemStack.getItem() != Items.ENCHANTED_BOOK) {
            itemStack.setTagInfo("Enchantments", listNBT);
        }
    }

    private static void applyEnchantmentModifier(IEnchantmentVisitor iEnchantmentVisitor, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            ListNBT listNBT = itemStack.getEnchantmentTagList();
            for (int i = 0; i < listNBT.size(); ++i) {
                String string = listNBT.getCompound(i).getString("id");
                int n = listNBT.getCompound(i).getInt("lvl");
                Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(string)).ifPresent(arg_0 -> EnchantmentHelper.lambda$applyEnchantmentModifier$1(iEnchantmentVisitor, n, arg_0));
            }
        }
    }

    private static void applyEnchantmentModifierArray(IEnchantmentVisitor iEnchantmentVisitor, Iterable<ItemStack> iterable) {
        for (ItemStack itemStack : iterable) {
            EnchantmentHelper.applyEnchantmentModifier(iEnchantmentVisitor, itemStack);
        }
    }

    public static int getEnchantmentModifierDamage(Iterable<ItemStack> iterable, DamageSource damageSource) {
        MutableInt mutableInt = new MutableInt();
        EnchantmentHelper.applyEnchantmentModifierArray((arg_0, arg_1) -> EnchantmentHelper.lambda$getEnchantmentModifierDamage$2(mutableInt, damageSource, arg_0, arg_1), iterable);
        return mutableInt.intValue();
    }

    public static float getModifierForCreature(ItemStack itemStack, CreatureAttribute creatureAttribute) {
        MutableFloat mutableFloat = new MutableFloat();
        EnchantmentHelper.applyEnchantmentModifier((arg_0, arg_1) -> EnchantmentHelper.lambda$getModifierForCreature$3(mutableFloat, creatureAttribute, arg_0, arg_1), itemStack);
        return mutableFloat.floatValue();
    }

    public static float getSweepingDamageRatio(LivingEntity livingEntity) {
        int n = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SWEEPING, livingEntity);
        return n > 0 ? SweepingEnchantment.getSweepingDamageRatio(n) : 0.0f;
    }

    public static void applyThornEnchantments(LivingEntity livingEntity, Entity entity2) {
        IEnchantmentVisitor iEnchantmentVisitor = (arg_0, arg_1) -> EnchantmentHelper.lambda$applyThornEnchantments$4(livingEntity, entity2, arg_0, arg_1);
        if (livingEntity != null) {
            EnchantmentHelper.applyEnchantmentModifierArray(iEnchantmentVisitor, livingEntity.getEquipmentAndArmor());
        }
        if (entity2 instanceof PlayerEntity) {
            EnchantmentHelper.applyEnchantmentModifier(iEnchantmentVisitor, livingEntity.getHeldItemMainhand());
        }
    }

    public static void applyArthropodEnchantments(LivingEntity livingEntity, Entity entity2) {
        IEnchantmentVisitor iEnchantmentVisitor = (arg_0, arg_1) -> EnchantmentHelper.lambda$applyArthropodEnchantments$5(livingEntity, entity2, arg_0, arg_1);
        if (livingEntity != null) {
            EnchantmentHelper.applyEnchantmentModifierArray(iEnchantmentVisitor, livingEntity.getEquipmentAndArmor());
        }
        if (livingEntity instanceof PlayerEntity) {
            EnchantmentHelper.applyEnchantmentModifier(iEnchantmentVisitor, livingEntity.getHeldItemMainhand());
        }
    }

    public static int getMaxEnchantmentLevel(Enchantment enchantment, LivingEntity livingEntity) {
        Collection<ItemStack> collection = enchantment.getEntityEquipment(livingEntity).values();
        if (collection == null) {
            return 1;
        }
        int n = 0;
        for (ItemStack itemStack : collection) {
            int n2 = EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack);
            if (n2 <= n) continue;
            n = n2;
        }
        return n;
    }

    public static int getKnockbackModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.KNOCKBACK, livingEntity);
    }

    public static int getFireAspectModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FIRE_ASPECT, livingEntity);
    }

    public static int getRespirationModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.RESPIRATION, livingEntity);
    }

    public static int getDepthStriderModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.DEPTH_STRIDER, livingEntity);
    }

    public static int getEfficiencyModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.EFFICIENCY, livingEntity);
    }

    public static int getFishingLuckBonus(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, itemStack);
    }

    public static int getFishingSpeedBonus(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.LURE, itemStack);
    }

    public static int getLootingModifier(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.LOOTING, livingEntity);
    }

    public static boolean hasAquaAffinity(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.AQUA_AFFINITY, livingEntity) > 0;
    }

    public static boolean hasFrostWalker(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FROST_WALKER, livingEntity) > 0;
    }

    public static boolean hasSoulSpeed(LivingEntity livingEntity) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SOUL_SPEED, livingEntity) > 0;
    }

    public static boolean hasBindingCurse(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.BINDING_CURSE, itemStack) > 0;
    }

    public static boolean hasVanishingCurse(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.VANISHING_CURSE, itemStack) > 0;
    }

    public static int getLoyaltyModifier(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.LOYALTY, itemStack);
    }

    public static int getRiptideModifier(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.RIPTIDE, itemStack);
    }

    public static boolean hasChanneling(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.CHANNELING, itemStack) > 0;
    }

    @Nullable
    public static Map.Entry<EquipmentSlotType, ItemStack> getRandomItemWithEnchantment(Enchantment enchantment, LivingEntity livingEntity) {
        return EnchantmentHelper.getRandomEquippedWithEnchantment(enchantment, livingEntity, EnchantmentHelper::lambda$getRandomItemWithEnchantment$6);
    }

    @Nullable
    public static Map.Entry<EquipmentSlotType, ItemStack> getRandomEquippedWithEnchantment(Enchantment enchantment, LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        Map<EquipmentSlotType, ItemStack> map = enchantment.getEntityEquipment(livingEntity);
        if (map.isEmpty()) {
            return null;
        }
        ArrayList<Map.Entry<EquipmentSlotType, ItemStack>> arrayList = Lists.newArrayList();
        for (Map.Entry<EquipmentSlotType, ItemStack> entry : map.entrySet()) {
            ItemStack itemStack = entry.getValue();
            if (itemStack.isEmpty() || EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack) <= 0 || !predicate.test(itemStack)) continue;
            arrayList.add(entry);
        }
        return arrayList.isEmpty() ? null : (Map.Entry)arrayList.get(livingEntity.getRNG().nextInt(arrayList.size()));
    }

    public static int calcItemStackEnchantability(Random random2, int n, int n2, ItemStack itemStack) {
        Item item = itemStack.getItem();
        int n3 = item.getItemEnchantability();
        if (n3 <= 0) {
            return 1;
        }
        if (n2 > 15) {
            n2 = 15;
        }
        int n4 = random2.nextInt(8) + 1 + (n2 >> 1) + random2.nextInt(n2 + 1);
        if (n == 0) {
            return Math.max(n4 / 3, 1);
        }
        return n == 1 ? n4 * 2 / 3 + 1 : Math.max(n4, n2 * 2);
    }

    public static ItemStack addRandomEnchantment(Random random2, ItemStack itemStack, int n, boolean bl) {
        boolean bl2;
        List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(random2, itemStack, n, bl);
        boolean bl3 = bl2 = itemStack.getItem() == Items.BOOK;
        if (bl2) {
            itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        }
        for (EnchantmentData enchantmentData : list) {
            if (bl2) {
                EnchantedBookItem.addEnchantment(itemStack, enchantmentData);
                continue;
            }
            itemStack.addEnchantment(enchantmentData.enchantment, enchantmentData.enchantmentLevel);
        }
        return itemStack;
    }

    public static List<EnchantmentData> buildEnchantmentList(Random random2, ItemStack itemStack, int n, boolean bl) {
        ArrayList<EnchantmentData> arrayList = Lists.newArrayList();
        Item item = itemStack.getItem();
        int n2 = item.getItemEnchantability();
        if (n2 <= 0) {
            return arrayList;
        }
        n = n + 1 + random2.nextInt(n2 / 4 + 1) + random2.nextInt(n2 / 4 + 1);
        float f = (random2.nextFloat() + random2.nextFloat() - 1.0f) * 0.15f;
        List<EnchantmentData> list = EnchantmentHelper.getEnchantmentDatas(n = MathHelper.clamp(Math.round((float)n + (float)n * f), 1, Integer.MAX_VALUE), itemStack, bl);
        if (!list.isEmpty()) {
            arrayList.add(WeightedRandom.getRandomItem(random2, list));
            while (random2.nextInt(50) <= n) {
                EnchantmentHelper.removeIncompatible(list, Util.getLast(arrayList));
                if (list.isEmpty()) break;
                arrayList.add(WeightedRandom.getRandomItem(random2, list));
                n /= 2;
            }
        }
        return arrayList;
    }

    public static void removeIncompatible(List<EnchantmentData> list, EnchantmentData enchantmentData) {
        Iterator<EnchantmentData> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            if (enchantmentData.enchantment.isCompatibleWith(iterator2.next().enchantment)) continue;
            iterator2.remove();
        }
    }

    public static boolean areAllCompatibleWith(Collection<Enchantment> collection, Enchantment enchantment) {
        for (Enchantment enchantment2 : collection) {
            if (enchantment2.isCompatibleWith(enchantment)) continue;
            return true;
        }
        return false;
    }

    public static List<EnchantmentData> getEnchantmentDatas(int n, ItemStack itemStack, boolean bl) {
        ArrayList<EnchantmentData> arrayList = Lists.newArrayList();
        Item item = itemStack.getItem();
        boolean bl2 = itemStack.getItem() == Items.BOOK;
        block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment.isTreasureEnchantment() && !bl || !enchantment.canGenerateInLoot() || !enchantment.type.canEnchantItem(item) && !bl2) continue;
            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (n < enchantment.getMinEnchantability(i) || n > enchantment.getMaxEnchantability(i)) continue;
                arrayList.add(new EnchantmentData(enchantment, i));
                continue block0;
            }
        }
        return arrayList;
    }

    private static boolean lambda$getRandomItemWithEnchantment$6(ItemStack itemStack) {
        return false;
    }

    private static void lambda$applyArthropodEnchantments$5(LivingEntity livingEntity, Entity entity2, Enchantment enchantment, int n) {
        enchantment.onEntityDamaged(livingEntity, entity2, n);
    }

    private static void lambda$applyThornEnchantments$4(LivingEntity livingEntity, Entity entity2, Enchantment enchantment, int n) {
        enchantment.onUserHurt(livingEntity, entity2, n);
    }

    private static void lambda$getModifierForCreature$3(MutableFloat mutableFloat, CreatureAttribute creatureAttribute, Enchantment enchantment, int n) {
        mutableFloat.add(enchantment.calcDamageByCreature(n, creatureAttribute));
    }

    private static void lambda$getEnchantmentModifierDamage$2(MutableInt mutableInt, DamageSource damageSource, Enchantment enchantment, int n) {
        mutableInt.add(enchantment.calcModifierDamage(n, damageSource));
    }

    private static void lambda$applyEnchantmentModifier$1(IEnchantmentVisitor iEnchantmentVisitor, int n, Enchantment enchantment) {
        iEnchantmentVisitor.accept(enchantment, n);
    }

    private static void lambda$deserializeEnchantments$0(Map map, CompoundNBT compoundNBT, Enchantment enchantment) {
        Integer n = map.put(enchantment, compoundNBT.getInt("lvl"));
    }

    @FunctionalInterface
    static interface IEnchantmentVisitor {
        public void accept(Enchantment var1, int var2);
    }
}

