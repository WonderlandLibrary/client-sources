package im.expensive.ui.ab.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemDonate implements IItem {
    Item item;
    int price;
    int quantity;
    Map<Enchantment, Integer> enchantments;

    public ItemDonate(int price, int quantity, Map<Enchantment, Integer> enchantments) {
        this.item = Items.PLAYER_HEAD;
        this.price = price;
        this.quantity = quantity;
        this.enchantments = enchantments;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
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
        return enchantments;
    }
}