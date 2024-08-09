/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;

public interface INameable {
    public ITextComponent getName();

    default public boolean hasCustomName() {
        return this.getCustomName() != null;
    }

    default public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    default public ITextComponent getCustomName() {
        return null;
    }
}

