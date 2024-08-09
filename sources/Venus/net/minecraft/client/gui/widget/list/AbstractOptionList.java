/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.widget.list.AbstractList;

public abstract class AbstractOptionList<E extends Entry<E>>
extends AbstractList<E> {
    public AbstractOptionList(Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
    }

    @Override
    public boolean changeFocus(boolean bl) {
        boolean bl2 = super.changeFocus(bl);
        if (bl2) {
            this.ensureVisible((Entry)this.getListener());
        }
        return bl2;
    }

    @Override
    protected boolean isSelectedItem(int n) {
        return true;
    }

    public static abstract class Entry<E extends Entry<E>>
    extends AbstractList.AbstractListEntry<E>
    implements INestedGuiEventHandler {
        @Nullable
        private IGuiEventListener field_214380_a;
        private boolean field_214381_b;

        @Override
        public boolean isDragging() {
            return this.field_214381_b;
        }

        @Override
        public void setDragging(boolean bl) {
            this.field_214381_b = bl;
        }

        @Override
        public void setListener(@Nullable IGuiEventListener iGuiEventListener) {
            this.field_214380_a = iGuiEventListener;
        }

        @Override
        @Nullable
        public IGuiEventListener getListener() {
            return this.field_214380_a;
        }
    }
}

