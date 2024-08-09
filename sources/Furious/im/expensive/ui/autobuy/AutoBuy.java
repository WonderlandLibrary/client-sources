package im.expensive.ui.autobuy;

import lombok.Value;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.HashMap;

@Value
public class AutoBuy {
    Item item;
    int price;
    int count;
    HashMap<Enchantment, Integer> enchanments;
    boolean enchantment, items, fake, don;
}
