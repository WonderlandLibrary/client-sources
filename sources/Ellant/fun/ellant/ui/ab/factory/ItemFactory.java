/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.factory;

import fun.ellant.ui.ab.model.IItem;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public interface ItemFactory {
    public IItem createNewItem(Item var1, int var2, int var3, int var4, Map<Enchantment, Integer> var5);
}

