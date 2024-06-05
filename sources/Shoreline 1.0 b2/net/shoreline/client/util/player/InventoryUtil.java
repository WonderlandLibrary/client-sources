package net.shoreline.client.util.player;

import net.shoreline.client.util.Globals;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class InventoryUtil implements Globals
{
    /**
     *
     *
     * @return
     */
    public static boolean isHolding32k()
    {
        return isHolding32k(1000);
    }

    /**
     *
     *
     * @param lvl
     * @return
     */
    public static boolean isHolding32k(int lvl)
    {
        final ItemStack mainhand = mc.player.getMainHandStack();
        return EnchantmentHelper.getLevel(Enchantments.SHARPNESS, mainhand) >= lvl;
    }
}
