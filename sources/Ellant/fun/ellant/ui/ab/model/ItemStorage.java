/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.model;

import fun.ellant.ui.ab.factory.ItemFactory;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemStorage {
    private final CopyOnWriteArrayList<IItem> items;
    private final ItemFactory itemFactory;

    public ItemStorage(CopyOnWriteArrayList<IItem> items, ItemFactory itemFactory) {
        this.items = items;
        this.itemFactory = itemFactory;
    }

    public void addItem(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        System.out.println(enchantments.size());
        if (enchantments == null) {
            enchantments = Collections.emptyMap();
        }
        this.items.add(this.itemFactory.createNewItem(item, price, quantity, damage, enchantments));
    }

    public CopyOnWriteArrayList<IItem> getItems() {
        return this.items;
    }
}

