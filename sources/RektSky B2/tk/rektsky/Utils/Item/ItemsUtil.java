package tk.rektsky.Utils.Item;

import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.enchantment.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class ItemsUtil
{
    public static class ArmorComparator implements Comparator<Slot>
    {
        @Override
        public int compare(final Slot s1, final Slot s2) {
            final ItemStack first = s1.getStack();
            final ItemStack second = s2.getStack();
            if (first.getItem() instanceof ItemArmor) {
                float reduceFirst = (float)((ItemArmor)first.getItem()).damageReduceAmount;
                if (first.isItemEnchanted()) {
                    final NBTTagList enchantments = first.getEnchantmentTagList();
                    for (int i = 0; i < enchantments.tagCount(); ++i) {
                        if (enchantments.getCompoundTagAt(i).getInteger("id") == 0) {
                            final EnchantmentProtection protection = (EnchantmentProtection)Enchantment.protection;
                            reduceFirst *= protection.calcModifierDamage(enchantments.getCompoundTagAt(i).getInteger("lvl"), DamageSource.causePlayerDamage(Minecraft.getMinecraft().thePlayer));
                        }
                    }
                }
                float reduceSecond = (float)((ItemArmor)second.getItem()).damageReduceAmount;
                if (second.isItemEnchanted()) {
                    final NBTTagList enchantments2 = second.getEnchantmentTagList();
                    for (int j = 0; j < enchantments2.tagCount(); ++j) {
                        if (enchantments2.getCompoundTagAt(j).getInteger("id") == 0) {
                            final EnchantmentProtection protection2 = (EnchantmentProtection)Enchantment.protection;
                            reduceSecond *= protection2.calcModifierDamage(enchantments2.getCompoundTagAt(j).getInteger("lvl"), DamageSource.causePlayerDamage(Minecraft.getMinecraft().thePlayer));
                        }
                    }
                }
                return Float.compare(reduceSecond, reduceFirst);
            }
            return 0;
        }
    }
}
