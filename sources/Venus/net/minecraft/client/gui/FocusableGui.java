/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;

public abstract class FocusableGui
extends AbstractGui
implements INestedGuiEventHandler {
    @Nullable
    private IGuiEventListener field_230699_a_;
    private boolean isDragging;

    @Override
    public final boolean isDragging() {
        return this.isDragging;
    }

    @Override
    public final void setDragging(boolean bl) {
        this.isDragging = bl;
    }

    @Override
    @Nullable
    public IGuiEventListener getListener() {
        return this.field_230699_a_;
    }

    @Override
    public void setListener(@Nullable IGuiEventListener iGuiEventListener) {
        this.field_230699_a_ = iGuiEventListener;
    }
}

