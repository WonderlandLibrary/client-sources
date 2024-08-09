/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.util.text.ITextComponent;

public interface INamedContainerProvider
extends IContainerProvider {
    public ITextComponent getDisplayName();
}

