/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum ArmorMaterial implements IArmorMaterial
{
    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, ArmorMaterial::lambda$static$0),
    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0f, 0.0f, ArmorMaterial::lambda$static$1),
    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, ArmorMaterial::lambda$static$2),
    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f, ArmorMaterial::lambda$static$3),
    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f, ArmorMaterial::lambda$static$4),
    TURTLE("turtle", 25, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0f, 0.0f, ArmorMaterial::lambda$static$5),
    NETHERITE("netherite", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, ArmorMaterial::lambda$static$6);

    private static final int[] MAX_DAMAGE_ARRAY;
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    private ArmorMaterial(String string2, int n2, int[] nArray, int n3, SoundEvent soundEvent, float f, float f2, Supplier<Ingredient> supplier) {
        this.name = string2;
        this.maxDamageFactor = n2;
        this.damageReductionAmountArray = nArray;
        this.enchantability = n3;
        this.soundEvent = soundEvent;
        this.toughness = f;
        this.knockbackResistance = f2;
        this.repairMaterial = new LazyValue<Ingredient>(supplier);
    }

    @Override
    public int getDurability(EquipmentSlotType equipmentSlotType) {
        return MAX_DAMAGE_ARRAY[equipmentSlotType.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType equipmentSlotType) {
        return this.damageReductionAmountArray[equipmentSlotType.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    private static Ingredient lambda$static$6() {
        return Ingredient.fromItems(Items.NETHERITE_INGOT);
    }

    private static Ingredient lambda$static$5() {
        return Ingredient.fromItems(Items.SCUTE);
    }

    private static Ingredient lambda$static$4() {
        return Ingredient.fromItems(Items.DIAMOND);
    }

    private static Ingredient lambda$static$3() {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }

    private static Ingredient lambda$static$2() {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }

    private static Ingredient lambda$static$1() {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }

    private static Ingredient lambda$static$0() {
        return Ingredient.fromItems(Items.LEATHER);
    }

    static {
        MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    }
}

