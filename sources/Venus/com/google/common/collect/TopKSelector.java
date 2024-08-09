/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
final class TopKSelector<T> {
    private final int k;
    private final Comparator<? super T> comparator;
    private final T[] buffer;
    private int bufferSize;
    private T threshold;

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int n) {
        return TopKSelector.least(n, Ordering.natural());
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int n) {
        return TopKSelector.greatest(n, Ordering.natural());
    }

    public static <T> TopKSelector<T> least(int n, Comparator<? super T> comparator) {
        return new TopKSelector<T>(comparator, n);
    }

    public static <T> TopKSelector<T> greatest(int n, Comparator<? super T> comparator) {
        return new TopKSelector(Ordering.from(comparator).reverse(), n);
    }

    private TopKSelector(Comparator<? super T> comparator, int n) {
        this.comparator = Preconditions.checkNotNull(comparator, "comparator");
        this.k = n;
        Preconditions.checkArgument(n >= 0, "k must be nonnegative, was %s", n);
        this.buffer = new Object[n * 2];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public void offer(@Nullable T t) {
        if (this.k == 0) {
            return;
        }
        if (this.bufferSize == 0) {
            this.buffer[0] = t;
            this.threshold = t;
            this.bufferSize = 1;
        } else if (this.bufferSize < this.k) {
            this.buffer[this.bufferSize++] = t;
            if (this.comparator.compare(t, this.threshold) > 0) {
                this.threshold = t;
            }
        } else if (this.comparator.compare(t, this.threshold) < 0) {
            this.buffer[this.bufferSize++] = t;
            if (this.bufferSize == 2 * this.k) {
                this.trim();
            }
        }
    }

    private void trim() {
        int n;
        int n2 = 0;
        int n3 = 2 * this.k - 1;
        int n4 = 0;
        int n5 = 0;
        int n6 = IntMath.log2(n3 - n2, RoundingMode.CEILING) * 3;
        while (n2 < n3) {
            n = n2 + n3 + 1 >>> 1;
            int n7 = this.partition(n2, n3, n);
            if (n7 > this.k) {
                n3 = n7 - 1;
            } else {
                if (n7 >= this.k) break;
                n2 = Math.max(n7, n2 + 1);
                n4 = n7;
            }
            if (++n5 < n6) continue;
            Arrays.sort(this.buffer, n2, n3, this.comparator);
            break;
        }
        this.bufferSize = this.k;
        this.threshold = this.buffer[n4];
        for (n = n4 + 1; n < this.k; ++n) {
            if (this.comparator.compare(this.buffer[n], this.threshold) <= 0) continue;
            this.threshold = this.buffer[n];
        }
    }

    private int partition(int n, int n2, int n3) {
        T t = this.buffer[n3];
        this.buffer[n3] = this.buffer[n2];
        int n4 = n;
        for (int i = n; i < n2; ++i) {
            if (this.comparator.compare(this.buffer[i], t) >= 0) continue;
            this.swap(n4, i);
            ++n4;
        }
        this.buffer[n2] = this.buffer[n4];
        this.buffer[n4] = t;
        return n4;
    }

    private void swap(int n, int n2) {
        T t = this.buffer[n];
        this.buffer[n] = this.buffer[n2];
        this.buffer[n2] = t;
    }

    TopKSelector<T> combine(TopKSelector<T> topKSelector) {
        for (int i = 0; i < topKSelector.bufferSize; ++i) {
            this.offer(topKSelector.buffer[i]);
        }
        return this;
    }

    public void offerAll(Iterable<? extends T> iterable) {
        this.offerAll(iterable.iterator());
    }

    public void offerAll(Iterator<? extends T> iterator2) {
        while (iterator2.hasNext()) {
            this.offer(iterator2.next());
        }
    }

    public List<T> topK() {
        Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
        if (this.bufferSize > this.k) {
            Arrays.fill(this.buffer, this.k, this.buffer.length, null);
            this.bufferSize = this.k;
            this.threshold = this.buffer[this.k - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
    }
}

