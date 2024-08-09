/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.util.SearchTreeReloadable;
import net.minecraft.client.util.SuffixArray;
import net.minecraft.util.ResourceLocation;

public class SearchTree<T>
extends SearchTreeReloadable<T> {
    protected SuffixArray<T> byName = new SuffixArray();
    private final Function<T, Stream<String>> nameFunc;

    public SearchTree(Function<T, Stream<String>> function, Function<T, Stream<ResourceLocation>> function2) {
        super(function2);
        this.nameFunc = function;
    }

    @Override
    public void recalculate() {
        this.byName = new SuffixArray();
        super.recalculate();
        this.byName.generate();
    }

    @Override
    protected void index(T t) {
        super.index(t);
        this.nameFunc.apply(t).forEach(arg_0 -> this.lambda$index$0(t, arg_0));
    }

    @Override
    public List<T> search(String string) {
        int n = string.indexOf(58);
        if (n < 0) {
            return this.byName.search(string);
        }
        List list = this.namespaceList.search(string.substring(0, n).trim());
        String string2 = string.substring(n + 1).trim();
        List list2 = this.pathList.search(string2);
        List<T> list3 = this.byName.search(string2);
        return Lists.newArrayList(new SearchTreeReloadable.JoinedIterator(list.iterator(), new MergingIterator(list2.iterator(), list3.iterator(), this::compare), this::compare));
    }

    private void lambda$index$0(Object object, String string) {
        this.byName.add(object, string.toLowerCase(Locale.ROOT));
    }

    static class MergingIterator<T>
    extends AbstractIterator<T> {
        private final PeekingIterator<T> leftItr;
        private final PeekingIterator<T> rightItr;
        private final Comparator<T> numbers;

        public MergingIterator(Iterator<T> iterator2, Iterator<T> iterator3, Comparator<T> comparator) {
            this.leftItr = Iterators.peekingIterator(iterator2);
            this.rightItr = Iterators.peekingIterator(iterator3);
            this.numbers = comparator;
        }

        @Override
        protected T computeNext() {
            boolean bl;
            boolean bl2 = !this.leftItr.hasNext();
            boolean bl3 = bl = !this.rightItr.hasNext();
            if (bl2 && bl) {
                return this.endOfData();
            }
            if (bl2) {
                return this.rightItr.next();
            }
            if (bl) {
                return this.leftItr.next();
            }
            int n = this.numbers.compare(this.leftItr.peek(), this.rightItr.peek());
            if (n == 0) {
                this.rightItr.next();
            }
            return n <= 0 ? this.leftItr.next() : this.rightItr.next();
        }
    }
}

