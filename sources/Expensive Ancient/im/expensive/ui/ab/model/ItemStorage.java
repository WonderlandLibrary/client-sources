package im.expensive.ui.ab.model;

import im.expensive.ui.ab.factory.ItemFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.*;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemStorage {
    @Getter
    final CopyOnWriteArrayList<IItem> items;
    final ItemFactory itemFactory;

    public ItemStorage(CopyOnWriteArrayList<IItem> items, ItemFactory itemFactory) {
        this.items = items;
        this.itemFactory = itemFactory;
    }

    public void addItem(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        System.out.println(enchantments.size());
        if (enchantments == null) {
            enchantments = Collections.emptyMap();
        }

        items.add(itemFactory.createNewItem(item, price, quantity, damage,enchantments));
    }

}
