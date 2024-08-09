/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.Comparator;
import net.optifine.Config;
import net.optifine.CustomItemProperties;

public class CustomItemsComparator
implements Comparator {
    public int compare(Object object, Object object2) {
        CustomItemProperties customItemProperties = (CustomItemProperties)object;
        CustomItemProperties customItemProperties2 = (CustomItemProperties)object2;
        if (customItemProperties.weight != customItemProperties2.weight) {
            return customItemProperties2.weight - customItemProperties.weight;
        }
        return !Config.equals(customItemProperties.basePath, customItemProperties2.basePath) ? customItemProperties.basePath.compareTo(customItemProperties2.basePath) : customItemProperties.name.compareTo(customItemProperties2.name);
    }
}

