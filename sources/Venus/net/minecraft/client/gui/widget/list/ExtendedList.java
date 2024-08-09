/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractList;

public abstract class ExtendedList<E extends AbstractList.AbstractListEntry<E>>
extends AbstractList<E> {
    private boolean field_230698_a_;

    public ExtendedList(Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
    }

    @Override
    public boolean changeFocus(boolean bl) {
        if (!this.field_230698_a_ && this.getItemCount() == 0) {
            return true;
        }
        boolean bl2 = this.field_230698_a_ = !this.field_230698_a_;
        if (this.field_230698_a_ && this.getSelected() == null && this.getItemCount() > 0) {
            this.moveSelection(AbstractList.Ordering.DOWN);
        } else if (this.field_230698_a_ && this.getSelected() != null) {
            this.func_241574_n_();
        }
        return this.field_230698_a_;
    }

    public static abstract class AbstractListEntry<E extends AbstractListEntry<E>>
    extends AbstractList.AbstractListEntry<E> {
        @Override
        public boolean changeFocus(boolean bl) {
            return true;
        }
    }
}

