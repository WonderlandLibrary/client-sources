/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.SuffixArray;
import net.minecraft.util.ResourceLocation;

public class SearchTreeReloadable<T>
implements IMutableSearchTree<T> {
    protected SuffixArray<T> namespaceList = new SuffixArray();
    protected SuffixArray<T> pathList = new SuffixArray();
    private final Function<T, Stream<ResourceLocation>> field_217877_c;
    private final List<T> field_217878_d = Lists.newArrayList();
    private final Object2IntMap<T> field_217879_e = new Object2IntOpenHashMap<T>();

    public SearchTreeReloadable(Function<T, Stream<ResourceLocation>> function) {
        this.field_217877_c = function;
    }

    @Override
    public void recalculate() {
        this.namespaceList = new SuffixArray();
        this.pathList = new SuffixArray();
        for (T t : this.field_217878_d) {
            this.index(t);
        }
        this.namespaceList.generate();
        this.pathList.generate();
    }

    @Override
    public void func_217872_a(T t) {
        this.field_217879_e.put(t, this.field_217878_d.size());
        this.field_217878_d.add(t);
        this.index(t);
    }

    @Override
    public void clear() {
        this.field_217878_d.clear();
        this.field_217879_e.clear();
    }

    protected void index(T t) {
        this.field_217877_c.apply(t).forEach(arg_0 -> this.lambda$index$0(t, arg_0));
    }

    protected int compare(T t, T t2) {
        return Integer.compare(this.field_217879_e.getInt(t), this.field_217879_e.getInt(t2));
    }

    @Override
    public List<T> search(String string) {
        int n = string.indexOf(58);
        if (n == -1) {
            return this.pathList.search(string);
        }
        List<T> list = this.namespaceList.search(string.substring(0, n).trim());
        String string2 = string.substring(n + 1).trim();
        List<T> list2 = this.pathList.search(string2);
        return Lists.newArrayList(new JoinedIterator<T>(list.iterator(), list2.iterator(), this::compare));
    }

    private void lambda$index$0(Object object, ResourceLocation resourceLocation) {
        this.namespaceList.add(object, resourceLocation.getNamespace().toLowerCase(Locale.ROOT));
        this.pathList.add(object, resourceLocation.getPath().toLowerCase(Locale.ROOT));
    }

    public static class JoinedIterator<T>
    extends AbstractIterator<T> {
        private final PeekingIterator<T> field_217881_a;
        private final PeekingIterator<T> field_217882_b;
        private final Comparator<T> field_217883_c;

        public JoinedIterator(Iterator<T> iterator2, Iterator<T> iterator3, Comparator<T> comparator) {
            this.field_217881_a = Iterators.peekingIterator(iterator2);
            this.field_217882_b = Iterators.peekingIterator(iterator3);
            this.field_217883_c = comparator;
        }

        @Override
        protected T computeNext() {
            while (this.field_217881_a.hasNext() && this.field_217882_b.hasNext()) {
                int n = this.field_217883_c.compare(this.field_217881_a.peek(), this.field_217882_b.peek());
                if (n == 0) {
                    this.field_217882_b.next();
                    return this.field_217881_a.next();
                }
                if (n < 0) {
                    this.field_217881_a.next();
                    continue;
                }
                this.field_217882_b.next();
            }
            return this.endOfData();
        }
    }
}

