package im.expensive.ui.ab.model;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;

public interface IItem {

    Item getItem();
    int getPrice();
    int getQuantity();
    int getDamage();
    boolean isDonate();
    Map<Enchantment, Integer> getEnchantments();
}
