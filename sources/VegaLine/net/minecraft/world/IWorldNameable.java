/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.util.text.ITextComponent;

public interface IWorldNameable {
    public String getName();

    public boolean hasCustomName();

    public ITextComponent getDisplayName();
}

