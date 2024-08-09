/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.inventory.container.Container;

public interface IHasContainer<T extends Container> {
    public T getContainer();
}

