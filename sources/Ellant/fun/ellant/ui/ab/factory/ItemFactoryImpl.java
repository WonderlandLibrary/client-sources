/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.factory;

import fun.ellant.ui.ab.factory.ItemFactory;
import fun.ellant.ui.ab.model.IItem;
import fun.ellant.ui.ab.model.ItemImpl;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemFactoryImpl
implements ItemFactory {
    @Override
    public IItem createNewItem(Item item, int price, int quantity, int damage, Map<Enchantment, Integer> enchantments) {
        return new ItemImpl(item, price, quantity, damage, enchantments);
    }
}

