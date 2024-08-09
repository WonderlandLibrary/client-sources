/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Enchantment {
    private final EquipmentSlotType[] applicableEquipmentTypes;
    private final Rarity rarity;
    public final EnchantmentType type;
    @Nullable
    protected String name;

    @Nullable
    public static Enchantment getEnchantmentByID(int n) {
        return (Enchantment)Registry.ENCHANTMENT.getByValue(n);
    }

    protected Enchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType[] equipmentSlotTypeArray) {
        this.rarity = rarity;
        this.type = enchantmentType;
        this.applicableEquipmentTypes = equipmentSlotTypeArray;
    }

    public Map<EquipmentSlotType, ItemStack> getEntityEquipment(LivingEntity livingEntity) {
        EnumMap<EquipmentSlotType, ItemStack> enumMap = Maps.newEnumMap(EquipmentSlotType.class);
        for (EquipmentSlotType equipmentSlotType : this.applicableEquipmentTypes) {
            ItemStack itemStack = livingEntity.getItemStackFromSlot(equipmentSlotType);
            if (itemStack.isEmpty()) continue;
            enumMap.put(equipmentSlotType, itemStack);
        }
        return enumMap;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public int getMinLevel() {
        return 0;
    }

    public int getMaxLevel() {
        return 0;
    }

    public int getMinEnchantability(int n) {
        return 1 + n * 10;
    }

    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 5;
    }

    public int calcModifierDamage(int n, DamageSource damageSource) {
        return 1;
    }

    public float calcDamageByCreature(int n, CreatureAttribute creatureAttribute) {
        return 0.0f;
    }

    public final boolean isCompatibleWith(Enchantment enchantment) {
        return this.canApplyTogether(enchantment) && enchantment.canApplyTogether(this);
    }

    protected boolean canApplyTogether(Enchantment enchantment) {
        return this != enchantment;
    }

    protected String getDefaultTranslationKey() {
        if (this.name == null) {
            this.name = Util.makeTranslationKey("enchantment", Registry.ENCHANTMENT.getKey(this));
        }
        return this.name;
    }

    public String getName() {
        return this.getDefaultTranslationKey();
    }

    public ITextComponent getDisplayName(int n) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent(this.getName());
        if (this.isCurse()) {
            translationTextComponent.mergeStyle(TextFormatting.RED);
        } else {
            translationTextComponent.mergeStyle(TextFormatting.GRAY);
        }
        if (n != 1 || this.getMaxLevel() != 1) {
            translationTextComponent.appendString(" ").append(new TranslationTextComponent("enchantment.level." + n));
        }
        return translationTextComponent;
    }

    public boolean canApply(ItemStack itemStack) {
        return this.type.canEnchantItem(itemStack.getItem());
    }

    public void onEntityDamaged(LivingEntity livingEntity, Entity entity2, int n) {
    }

    public void onUserHurt(LivingEntity livingEntity, Entity entity2, int n) {
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    public boolean isCurse() {
        return true;
    }

    public boolean canVillagerTrade() {
        return false;
    }

    public boolean canGenerateInLoot() {
        return false;
    }

    public static enum Rarity {
        COMMON(10),
        UNCOMMON(5),
        RARE(2),
        VERY_RARE(1);

        private final int weight;

        private Rarity(int n2) {
            this.weight = n2;
        }

        public int getWeight() {
            return this.weight;
        }
    }
}

