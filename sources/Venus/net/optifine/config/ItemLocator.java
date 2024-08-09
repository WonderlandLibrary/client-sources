/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.IObjectLocator;
import net.optifine.util.ItemUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ItemLocator
implements IObjectLocator<Item> {
    @Override
    public Item getObject(ResourceLocation resourceLocation) {
        return ItemUtils.getItem(resourceLocation);
    }

    @Override
    public Object getObject(ResourceLocation resourceLocation) {
        return this.getObject(resourceLocation);
    }
}

