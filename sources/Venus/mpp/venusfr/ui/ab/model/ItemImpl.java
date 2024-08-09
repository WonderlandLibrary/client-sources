/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.model;

import java.util.Map;
import mpp.venusfr.ui.ab.model.IItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemImpl
implements IItem {
    private final Item item;
    private final int price;
    private final int quantity;
    private final int damage;
    private final Map<Enchantment, Integer> enchantments;

    public ItemImpl(Item item, int n, int n2, int n3, Map<Enchantment, Integer> map) {
        this.item = item;
        this.price = n;
        this.quantity = n2;
        this.damage = n3;
        this.enchantments = map;
    }

    @Override
    public Item getItem() {
        return this.item;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public boolean isDonate() {
        return true;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }
}

