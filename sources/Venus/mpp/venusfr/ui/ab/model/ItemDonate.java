/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.model;

import java.util.Map;
import mpp.venusfr.ui.ab.model.IItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ItemDonate
implements IItem {
    private final Item item = Items.PLAYER_HEAD;
    private final int price;
    private final int quantity;
    private final Map<Enchantment, Integer> enchantments;

    public ItemDonate(int n, int n2, Map<Enchantment, Integer> map) {
        this.price = n;
        this.quantity = n2;
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
        return 1;
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

