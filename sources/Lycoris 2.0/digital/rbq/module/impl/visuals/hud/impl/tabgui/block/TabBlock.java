/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.block;

import java.util.Iterator;
import java.util.NoSuchElementException;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;

public class TabBlock
implements Iterable<Tab<?>> {
    private int size = 0;
    private DoublyLinkedTab first;
    private DoublyLinkedTab current;

    public Tab<?> getFirst() {
        return this.first.backingTab;
    }

    public Tab<?> getCurrent() {
        return this.current.backingTab;
    }

    public Tab<?> getLast() {
        return this.first.previous.backingTab;
    }

    public boolean hasNext() {
        return true;
    }

    public boolean hasPrevious() {
        return true;
    }

    public Tab<?> getNext() {
        return this.current.next.backingTab;
    }

    public Tab<?> getPrevious() {
        return this.current.previous.backingTab;
    }

    public void cycleToNext() {
        this.current = this.current.next;
    }

    public void cycleToPrevious() {
        this.current = this.current.previous;
    }

    public void restartIteration() {
        this.current = this.first;
    }

    public void appendTab(Tab<?> tab) {
        DoublyLinkedTab wrapped = new DoublyLinkedTab(tab);
        if (this.first == null) {
            this.first.next = this.first = wrapped;
            this.first.previous = this.first;
            this.current = this.first;
        } else {
            wrapped.next = this.first;
            wrapped.previous = this.first.previous;
            this.first.previous.next = wrapped;
            this.first.previous = wrapped;
        }
        ++this.size;
    }

    public int sizeOf() {
        return this.size;
    }

    @Override
    public Iterator<Tab<?>> iterator() {
        return new TabBlockIterator();
    }

    public Iterator<DoublyLinkedTab> linkedIterator() {
        return new LinkedTabBlockIterator();
    }

    public static class DoublyLinkedTab {
        private Tab<?> backingTab;
        public DoublyLinkedTab next;
        public DoublyLinkedTab previous;

        public DoublyLinkedTab(Tab<?> tab) {
            this.backingTab = tab;
        }

        public Tab<?> getTab() {
            return this.backingTab;
        }
    }

    private class LinkedTabBlockIterator
    implements Iterator<DoublyLinkedTab> {
        private DoublyLinkedTab localCurrent;
        private int count;

        private LinkedTabBlockIterator() {
            this.localCurrent = TabBlock.this.first;
            this.count = 0;
        }

        @Override
        public boolean hasNext() {
            return this.count < TabBlock.this.size;
        }

        @Override
        public DoublyLinkedTab next() {
            if (this.hasNext()) {
                DoublyLinkedTab tab = this.localCurrent;
                this.localCurrent = this.localCurrent.next;
                ++this.count;
                return tab;
            }
            throw new NoSuchElementException();
        }
    }

    private class TabBlockIterator
    implements Iterator<Tab<?>> {
        private DoublyLinkedTab localCurrent;
        private int count;

        private TabBlockIterator() {
            this.localCurrent = TabBlock.this.first;
            this.count = 0;
        }

        @Override
        public boolean hasNext() {
            return this.count < TabBlock.this.size;
        }

        @Override
        public Tab<?> next() {
            if (this.hasNext()) {
                Tab<?> tab = this.localCurrent.getTab();
                this.localCurrent = this.localCurrent.next;
                ++this.count;
                return tab;
            }
            throw new NoSuchElementException();
        }
    }
}

