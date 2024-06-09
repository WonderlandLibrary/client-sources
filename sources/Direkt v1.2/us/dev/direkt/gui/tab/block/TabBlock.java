package us.dev.direkt.gui.tab.block;

import us.dev.direkt.gui.tab.tab.Tab;

import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Created by Foundry on 12/27/2015.
 */
public class TabBlock implements Iterable<Tab<?>> {
    private int size            = 0;
    private DoublyLinkedTab first,
            current;

    public Tab<?> getFirst() {
        return first.backingTab;
    }

    public Tab<?> getCurrent() {
        return current.backingTab;
    }

    public Tab<?> getLast() {
        return first.previous.backingTab;
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
        final DoublyLinkedTab wrapped = new DoublyLinkedTab(tab);
        if (this.first == null) {
            this.first = wrapped;
            this.first.next = this.first;
            this.first.previous = this.first;
            this.current = this.first;
        } else {
            wrapped.next = this.first;
            wrapped.previous = this.first.previous;
            this.first.previous.next = wrapped;
            this.first.previous = wrapped;
        }
        this.size++;
    }

    public int sizeOf() {
        return size;
    }

    @Override
    public Iterator<Tab<?>> iterator() {
        return new TabBlockIterator();
    }

    public Iterator<DoublyLinkedTab> linkedIterator() {
        return new LinkedTabBlockIterator();
    }

    private class TabBlockIterator implements Iterator<Tab<?>> {
        private DoublyLinkedTab localCurrent = first;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return this.count < size;
        }

        @Override
        public Tab<?> next() {
            if (this.hasNext()) {
                Tab<?> tab = this.localCurrent.getTab();
                this.localCurrent = this.localCurrent.next;
                this.count++;
                return tab;
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }

    private class LinkedTabBlockIterator implements Iterator<DoublyLinkedTab> {
        private DoublyLinkedTab localCurrent = first;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return this.count < size;
        }

        @Override
        public DoublyLinkedTab next() {
            if (this.hasNext()) {
                 DoublyLinkedTab tab = this.localCurrent;
                this.localCurrent = this.localCurrent.next;
                this.count++;
                return tab;
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }

    public static class DoublyLinkedTab {
        private Tab<?> backingTab;
        public DoublyLinkedTab next, previous;

        public DoublyLinkedTab(Tab<?> tab) {
            this.backingTab = tab;
        }

        public Tab<?> getTab() {
            return this.backingTab;
        }

    }

}
