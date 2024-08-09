/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.donate;

import fun.ellant.ui.ab.donate.CustomItem;
import java.util.ArrayList;
import java.util.List;

public class CustomItemRegistrar {
    private static final List<CustomItem> customItems = new ArrayList<CustomItem>();

    public static void registerItem(CustomItem item) {
        customItems.add(item);
    }

    public static List<CustomItem> getCustomItems() {
        return customItems;
    }
}

