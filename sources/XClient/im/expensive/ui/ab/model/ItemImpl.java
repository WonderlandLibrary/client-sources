package im.expensive.ui.ab.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemImpl implements IItem {
    Item item;
    int price;
    int quantity;

    int damage;
    Map<Enchantment, Integer> enchantments;

    public ItemImpl(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.damage = damage;
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
        return damage;
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