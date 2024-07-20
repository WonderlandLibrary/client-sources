/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;

public abstract class BaseItemType
extends Type<Item> {
    protected BaseItemType() {
        super(Item.class);
    }

    protected BaseItemType(String typeName) {
        super(typeName, Item.class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseItemType.class;
    }
}

