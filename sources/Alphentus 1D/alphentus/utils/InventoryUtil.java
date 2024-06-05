package alphentus.utils;

import alphentus.init.Init;
import alphentus.mod.mods.player.AutoArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;

import java.util.Arrays;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class InventoryUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isTrash(ItemStack is) {
        return is.getUnlocalizedName().

                contains("feather")
                || is.getUnlocalizedName().

                contains("mushroom")
                || is.getUnlocalizedName().

                contains("leather")
                || is.getUnlocalizedName().

                contains("bowl")
                || is.getUnlocalizedName().

                contains("wheat")
                || is.getUnlocalizedName().

                contains("wheat_seeds")
                || is.getUnlocalizedName().

                contains("cake")
                || is.getUnlocalizedName().

                contains("bone")
                || is.getItem() instanceof ItemSpade
                || is.getItem() instanceof ItemFishingRod;
    }

    public static boolean isBadTool(ItemStack is) {


        if (is.getItem() instanceof  ItemArmor && Init.getInstance().modManager.getModuleByClass(AutoArmor.class).getState()) {
            ItemArmor itemArmor = ((ItemArmor)is.getItem());
            if(!isBestArmor(is, itemArmor.armorType)){
                return true;
            }
        }

        return (is.getItem() instanceof ItemSword && is != bestSword())
                || (is.getItem() instanceof ItemBow && is != bestBow());
    }

    public static float getDamageReduceAmount(ItemStack itemStack) {
        float damageReduceAmount = 0.0F;
        if (itemStack.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = ((ItemArmor) itemStack.getItem());
            damageReduceAmount = itemArmor.damageReduceAmount;
        }
        return damageReduceAmount;
    }


    public static boolean isBestArmor(ItemStack itemStack, int type) {
        float damageReduceAmount = getDamageReduceAmount(itemStack);
        String armorType = "";

        if (type == 1)
            armorType = "helmet";
        if (type == 2)
            armorType = "chestplate";
        if (type == 3)
            armorType = "leggings";
        if (type == 4)
            armorType = "boots";

        if (!(itemStack.getItem().getUnlocalizedName().contains(armorType)))
            return false;

        for (int i = 5; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemStack1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamageReduceAmount(itemStack1) > damageReduceAmount && itemStack1.getItem().getUnlocalizedName().contains(armorType))
                    return false;
            }
        }

        return true;
    }


    public static ItemStack bestSword() {
        ItemStack best = null;
        float damage = -1;
        if (mc.currentScreen instanceof GuiInventory) {
            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (itemStack.getItem() instanceof ItemSword) {
                        float swordDamage = swordDamage(itemStack);
                        if (swordDamage >= damage) {
                            damage = swordDamage(itemStack);
                            best = itemStack;
                        }
                    }
                }
            }
        }
        return best;
    }

    public static ItemStack bestBow() {
        ItemStack best = null;
        float damage = -1;
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (itemStack.getItem() instanceof ItemBow) {
                    float bowDamage = bowDamage(itemStack);
                    if (bowDamage >= damage) {
                        damage = bowDamage(itemStack);
                        best = itemStack;
                    }
                }
            }
        }
        return best;
    }

    public static ItemStack bestSwordInHotbar() {
        ItemStack best = null;
        float damage = -1;
        if (mc.currentScreen instanceof GuiInventory) {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (itemStack.getItem() instanceof ItemSword) {
                        float swordDamage = swordDamage(itemStack);
                        if (swordDamage >= damage) {
                            damage = swordDamage(itemStack);
                            best = itemStack;
                        }
                    }
                }
            }
        }
        return best;
    }

    private static float swordDamage(ItemStack itemStack) {
        float damage = ((ItemSword) itemStack.getItem()).getDamageVsEntity() - (itemStack.getUnlocalizedName().contains("golden_sword") ? 0.01F : 0);
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01F;
        return damage;
    }

    private static float bowDamage(ItemStack itemStack) {
        float damage = 5;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) * 2.0F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.5F;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.1F;
        return damage;
    }
}