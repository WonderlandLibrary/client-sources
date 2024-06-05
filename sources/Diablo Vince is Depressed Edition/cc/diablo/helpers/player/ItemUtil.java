/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.player;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemUtil {
    public static boolean isValidSpeedPot(ItemStack stack) {
        return ItemUtil.isValidSpeedPot((ItemPotion)stack.getItem(), stack);
    }

    public static boolean isValidSpeedPot(ItemPotion potion, ItemStack stack) {
        if (ItemPotion.isSplash(stack.getItemDamage())) {
            for (PotionEffect o : potion.getEffects(stack)) {
                if (!(o instanceof PotionEffect) || o.getPotionID() != Potion.moveSpeed.id && o.getPotionID() != Potion.heal.id && o.getPotionID() != Potion.regeneration.id) continue;
                return true;
            }
        }
        return false;
    }
}

