/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.model;

import fun.ellant.ui.ab.model.IItem;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ItemDonate
implements IItem {
    private final Item item = Items.PLAYER_HEAD;
    private final int price;
    private final int quantity;
    private final Map<Enchantment, Integer> enchantments;

    public ItemDonate(int price, int quantity, Map<Enchantment, Integer> enchantments) {
        this.price = price;
        this.quantity = quantity;
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
        return 0;
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

