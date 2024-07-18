package net.shoreline.client.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;

/**
 * Implementation of https://guava.dev/releases/15.0/api/docs/com/google/common/collect/EvictingQueue.html
 * backed by a first-in last-out {@link ArrayDeque}.
 *
 * @param <E>
 * @author linus
 * @see ArrayDeque
 * @since 1.0
 */
public class EvictingQueue<E> extends ArrayDeque<E> {
    //
    private final int limit;
    //
    private int size;

    /**
     * @param limit
     */
    public EvictingQueue(int limit) {
        this.limit = limit;
        size = 0;
    }

    /**
     * @param element element whose presence in this collection is to be ensured
     * @return
     */
    @Override
    public boolean add(@NotNull E element) {
        boolean add = super.add(element);
        while (add && size() > limit) {
            super.remove();
            size--;
        }
        size++;
        return add;
    }

    /**
     * @param element element whose presence in this collection is to be ensured
     */
    @Override
    public void addFirst(@NotNull E element) {
        super.addFirst(element);
        size++;
        while (size() > limit) {
            super.removeLast();
            size--;
        }
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * @return
     */
    public int limit() {
        return limit;
    }
}
