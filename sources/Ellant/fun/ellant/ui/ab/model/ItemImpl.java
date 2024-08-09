/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.model;

import fun.ellant.ui.ab.model.IItem;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemImpl
implements IItem {
    private final Item item;
    private final int price;
    private final int quantity;
    private final int damage;
    private final Map<Enchantment, Integer> enchantments;

    public ItemImpl(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.damage = damage;
        this.enchantments = enchantments;
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
        return false;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchantments;
    }
}

