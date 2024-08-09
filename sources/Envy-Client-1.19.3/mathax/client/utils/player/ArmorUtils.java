package mathax.client.utils.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ArmorUtils {
    public static boolean checkThreshold(ItemStack i, double threshold) {
        return getDamage(i) <= threshold;
    }

    public static double getDamage(ItemStack i) {return (((double) (i.getMaxDamage() - i.getDamage()) / i.getMaxDamage()) * 100);}

    public static ItemStack getArmor(PlayerEntity player, int slot) {
        return player.getInventory().armor.get(slot);
    }

    public static boolean isHelmet(ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItem() == Items.NETHERITE_HELMET || itemStack.getItem() == Items.DIAMOND_HELMET || itemStack.getItem() == Items.GOLDEN_HELMET || itemStack.getItem() == Items.IRON_HELMET || itemStack.getItem() == Items.CHAINMAIL_HELMET || itemStack.getItem() == Items.LEATHER_HELMET;
    }

    public static boolean isChestplate(ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItem() == Items.NETHERITE_CHESTPLATE || itemStack.getItem() == Items.DIAMOND_CHESTPLATE || itemStack.getItem() == Items.GOLDEN_CHESTPLATE || itemStack.getItem() == Items.IRON_CHESTPLATE || itemStack.getItem() == Items.CHAINMAIL_CHESTPLATE || itemStack.getItem() == Items.LEATHER_CHESTPLATE;
    }

    public static boolean areLeggings(ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItem() == Items.NETHERITE_LEGGINGS || itemStack.getItem() == Items.DIAMOND_LEGGINGS || itemStack.getItem() == Items.GOLDEN_LEGGINGS || itemStack.getItem() == Items.IRON_LEGGINGS || itemStack.getItem() == Items.CHAINMAIL_LEGGINGS || itemStack.getItem() == Items.LEATHER_LEGGINGS;
    }

    public static boolean areBoots(ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItem() == Items.NETHERITE_BOOTS || itemStack.getItem() == Items.DIAMOND_BOOTS || itemStack.getItem() == Items.GOLDEN_BOOTS || itemStack.getItem() == Items.IRON_BOOTS || itemStack.getItem() == Items.CHAINMAIL_BOOTS || itemStack.getItem() == Items.LEATHER_BOOTS;
    }
}
