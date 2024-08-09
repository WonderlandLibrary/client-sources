/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.ui.ab.factory.ItemFactory;
import mpp.venusfr.ui.ab.model.IItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemStorage {
    private final CopyOnWriteArrayList<IItem> items;
    private final ItemFactory itemFactory;

    public ItemStorage(CopyOnWriteArrayList<IItem> copyOnWriteArrayList, ItemFactory itemFactory) {
        this.items = copyOnWriteArrayList;
        this.itemFactory = itemFactory;
    }

    public void addItem(Item item, int n, int n2, int n3, Map<Enchantment, Integer> map) {
        System.out.println(map.size());
        if (map == null) {
            map = Collections.emptyMap();
        }
        this.items.add(this.itemFactory.createNewItem(item, n, n2, n3, map));
    }

    public CopyOnWriteArrayList<IItem> getItems() {
        return this.items;
    }
}

