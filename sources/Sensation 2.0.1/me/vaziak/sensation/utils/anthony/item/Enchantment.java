package me.vaziak.sensation.utils.anthony.item;

import java.util.ArrayList;
import java.util.Arrays;

public enum Enchantment {
    AQUA_AFFINITY(net.minecraft.enchantment.Enchantment.aquaAffinity.effectId, 1, EnchantableItem.HELMET),
    BANE_OF_ARTHROPODS(net.minecraft.enchantment.Enchantment.baneOfArthropods.effectId, 2, EnchantableItem.SWORD, EnchantableItem.AXE),
    BLAST_PROTECTION(net.minecraft.enchantment.Enchantment.blastProtection.effectId, 2, EnchantableItem.HELMET, EnchantableItem.CHESTPLATE, EnchantableItem.LEGGINGS, EnchantableItem.BOOTS),
    EFFICIENCY(net.minecraft.enchantment.Enchantment.efficiency.effectId, 5, EnchantableItem.PICKAXE, EnchantableItem.AXE, EnchantableItem.SHOVEL),
    FEATHER_FALLING(net.minecraft.enchantment.Enchantment.featherFalling.effectId, 1, EnchantableItem.BOOTS),
    FIRE_ASPECT(net.minecraft.enchantment.Enchantment.fireAspect.effectId, 2, EnchantableItem.SWORD, EnchantableItem.AXE),
    FIRE_PROECTION(net.minecraft.enchantment.Enchantment.fireProtection.effectId, 2, EnchantableItem.HELMET, EnchantableItem.CHESTPLATE, EnchantableItem.LEGGINGS, EnchantableItem.BOOTS),
    FLAME(net.minecraft.enchantment.Enchantment.flame.effectId, 2, EnchantableItem.BOW),
    FORTUNE(net.minecraft.enchantment.Enchantment.fortune.effectId, 3, EnchantableItem.PICKAXE),
    INFINITY(net.minecraft.enchantment.Enchantment.infinity.effectId, 1, EnchantableItem.BOW),
    KNOCKBACK(net.minecraft.enchantment.Enchantment.knockback.effectId, 2, EnchantableItem.SWORD),
    LOOTING(net.minecraft.enchantment.Enchantment.looting.effectId, 1, EnchantableItem.SWORD),
    LUCK_OF_THE_SEA(net.minecraft.enchantment.Enchantment.luckOfTheSea.effectId, 5, EnchantableItem.FISHING_ROD),
    LURE(net.minecraft.enchantment.Enchantment.lure.effectId, 3, EnchantableItem.FISHING_ROD),
    POWER(net.minecraft.enchantment.Enchantment.power.effectId, 5, EnchantableItem.BOW),
    PROJECTILE_PROTECTION(net.minecraft.enchantment.Enchantment.projectileProtection.effectId, 2, EnchantableItem.HELMET, EnchantableItem.CHESTPLATE, EnchantableItem.LEGGINGS, EnchantableItem.BOOTS),
    PROTECTION(net.minecraft.enchantment.Enchantment.protection.effectId, -1, EnchantableItem.HELMET, EnchantableItem.CHESTPLATE, EnchantableItem.LEGGINGS, EnchantableItem.BOOTS),
    PUNCH(net.minecraft.enchantment.Enchantment.punch.effectId, 2, EnchantableItem.BOW),
    RESPIRATION(net.minecraft.enchantment.Enchantment.respiration.effectId, 1, EnchantableItem.HELMET),
    SHARPNESS(net.minecraft.enchantment.Enchantment.sharpness.effectId, -1, EnchantableItem.SWORD, EnchantableItem.AXE),
    SILK_TOUCH(net.minecraft.enchantment.Enchantment.silkTouch.effectId, 2, EnchantableItem.PICKAXE, EnchantableItem.AXE, EnchantableItem.SHOVEL),
    SMITE(net.minecraft.enchantment.Enchantment.smite.effectId, 2, EnchantableItem.SWORD, EnchantableItem.AXE),
    UNBREAKING(net.minecraft.enchantment.Enchantment.unbreaking.effectId, 3, EnchantableItem.HELMET, EnchantableItem.CHESTPLATE, EnchantableItem.LEGGINGS, EnchantableItem.BOOTS,
            EnchantableItem.PICKAXE, EnchantableItem.AXE, EnchantableItem.SHOVEL, EnchantableItem.HOE, EnchantableItem.SWORD, EnchantableItem.BOW, EnchantableItem.FISHING_ROD);


    private int enchId;
    private int score;
    private ArrayList<EnchantableItem> enchantableItems;


    Enchantment(int enchId, int score, EnchantableItem... enchantableItems) {
        this.enchantableItems = new ArrayList<>(Arrays.asList(enchantableItems));
        this.enchId = enchId;
        this.score = score;
    }

    public int getEnchId() {
        return enchId;
    }

    public int getScore() {
        return score;
    }
}
