/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.model;

import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public interface IItem {
    public Item getItem();

    public int getPrice();

    public int getQuantity();

    public int getDamage();

    public boolean isDonate();

    public Map<Enchantment, Integer> getEnchantments();
}

