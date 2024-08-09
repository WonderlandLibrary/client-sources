/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.factory;

import java.util.Map;
import mpp.venusfr.ui.ab.factory.ItemFactory;
import mpp.venusfr.ui.ab.model.IItem;
import mpp.venusfr.ui.ab.model.ItemImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemFactoryImpl
implements ItemFactory {
    @Override
    public IItem createNewItem(Item item, int n, int n2, int n3, Map<Enchantment, Integer> map) {
        return new ItemImpl(item, n, n2, n3, map);
    }
}

