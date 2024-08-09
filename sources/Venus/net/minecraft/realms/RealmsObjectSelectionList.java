/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;

public abstract class RealmsObjectSelectionList<E extends ExtendedList.AbstractListEntry<E>>
extends ExtendedList<E> {
    protected RealmsObjectSelectionList(int n, int n2, int n3, int n4, int n5) {
        super(Minecraft.getInstance(), n, n2, n3, n4, n5);
    }

    public void func_239561_k_(int n) {
        if (n == -1) {
            this.setSelected(null);
        } else if (super.getItemCount() != 0) {
            this.setSelected((ExtendedList.AbstractListEntry)this.getEntry(n));
        }
    }

    public void func_231400_a_(int n) {
        this.func_239561_k_(n);
    }

    public void func_231401_a_(int n, int n2, double d, double d2, int n3) {
    }

    @Override
    public int getMaxPosition() {
        return 1;
    }

    @Override
    public int getScrollbarPosition() {
        return this.getRowLeft() + this.getRowWidth();
    }

    @Override
    public int getRowWidth() {
        return (int)((double)this.width * 0.6);
    }

    @Override
    public void replaceEntries(Collection<E> collection) {
        super.replaceEntries(collection);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getRowTop(int n) {
        return super.getRowTop(n);
    }

    @Override
    public int getRowLeft() {
        return super.getRowLeft();
    }

    @Override
    public int addEntry(E e) {
        return super.addEntry(e);
    }

    public void func_231409_q_() {
        this.clearEntries();
    }

    @Override
    public int addEntry(AbstractList.AbstractListEntry abstractListEntry) {
        return this.addEntry((E)((ExtendedList.AbstractListEntry)abstractListEntry));
    }
}

