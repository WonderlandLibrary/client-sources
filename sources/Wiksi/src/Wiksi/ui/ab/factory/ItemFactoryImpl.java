package src.Wiksi.ui.ab.factory;

import src.Wiksi.ui.ab.model.IItem;
import src.Wiksi.ui.ab.model.ItemImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;

public class ItemFactoryImpl implements ItemFactory {
    @Override
    public IItem createNewItem(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        return new ItemImpl(item, price, quantity, damage, enchantments);
    }
}
