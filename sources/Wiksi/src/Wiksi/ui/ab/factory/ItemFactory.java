package src.Wiksi.ui.ab.factory;

import src.Wiksi.ui.ab.model.IItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;

public interface ItemFactory {
    IItem createNewItem(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments);
}
