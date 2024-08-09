/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;

public final class SimpleNamedContainerProvider
implements INamedContainerProvider {
    private final ITextComponent name;
    private final IContainerProvider inner;

    public SimpleNamedContainerProvider(IContainerProvider iContainerProvider, ITextComponent iTextComponent) {
        this.inner = iContainerProvider;
        this.name = iTextComponent;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.name;
    }

    @Override
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return this.inner.createMenu(n, playerInventory, playerEntity);
    }
}

