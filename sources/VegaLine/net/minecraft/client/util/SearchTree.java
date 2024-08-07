/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.client.util.ISearchTree;
import net.minecraft.client.util.SuffixArray;
import net.minecraft.util.ResourceLocation;

public class SearchTree<T>
implements ISearchTree<T> {
    protected SuffixArray<T> field_194044_a = new SuffixArray();
    protected SuffixArray<T> field_194045_b = new SuffixArray();
    private final Function<T, Iterable<String>> field_194046_c;
    private final Function<T, Iterable<ResourceLocation>> field_194047_d;
    private final List<T> field_194048_e = Lists.newArrayList();
    private Object2IntMap<T> field_194049_f = new Object2IntOpenHashMap<T>();

    public SearchTree(Function<T, Iterable<String>> p_i47612_1_, Function<T, Iterable<ResourceLocation>> p_i47612_2_) {
        this.field_194046_c = p_i47612_1_;
        this.field_194047_d = p_i47612_2_;
    }

    public void func_194040_a() {
        this.field_194044_a = new SuffixArray();
        this.field_194045_b = new SuffixArray();
        for (T t : this.field_194048_e) {
            this.func_194042_b(t);
        }
        this.field_194044_a.func_194058_a();
        this.field_194045_b.func_194058_a();
    }

    public void func_194043_a(T p_194043_1_) {
        this.field_194049_f.put(p_194043_1_, this.field_194048_e.size());
        this.field_194048_e.add(p_194043_1_);
        this.func_194042_b(p_194043_1_);
    }

    private void func_194042_b(T p_194042_1_) {
        this.field_194047_d.apply(p_194042_1_).forEach(p_194039_2_ -> this.field_194045_b.func_194057_a(p_194042_1_, p_194039_2_.toString().toLowerCase(Locale.ROOT)));
        this.field_194046_c.apply(p_194042_1_).forEach(p_194041_2_ -> this.field_194044_a.func_194057_a(p_194042_1_, p_194041_2_.toLowerCase(Locale.ROOT)));
    }

    @Override
    public List<T> func_194038_a(String p_194038_1_) {
        List<T> list = this.field_194044_a.func_194055_a(p_194038_1_);
        if (p_194038_1_.indexOf(58) < 0) {
            return list;
        }
        List<T> list1 = this.field_194045_b.func_194055_a(p_194038_1_);
        return list1.isEmpty() ? list : Lists.newArrayList(new MergingIterator<T>(list.iterator(), list1.iterator(), this.field_194049_f));
    }

    static class MergingIterator<T>
    extends AbstractIterator<T> {
        private final Iterator<T> field_194033_a;
        private final Iterator<T> field_194034_b;
        private final Object2IntMap<T> field_194035_c;
        private T field_194036_d;
        private T field_194037_e;

        public MergingIterator(Iterator<T> p_i47606_1_, Iterator<T> p_i47606_2_, Object2IntMap<T> p_i47606_3_) {
            this.field_194033_a = p_i47606_1_;
            this.field_194034_b = p_i47606_2_;
            this.field_194035_c = p_i47606_3_;
            this.field_194036_d = p_i47606_1_.hasNext() ? p_i47606_1_.next() : null;
            this.field_194037_e = p_i47606_2_.hasNext() ? p_i47606_2_.next() : null;
        }

        @Override
        protected T computeNext() {
            T t;
            if (this.field_194036_d == null && this.field_194037_e == null) {
                return this.endOfData();
            }
            int i = this.field_194036_d == this.field_194037_e ? 0 : (this.field_194036_d == null ? 1 : (this.field_194037_e == null ? -1 : Integer.compare(this.field_194035_c.getInt(this.field_194036_d), this.field_194035_c.getInt(this.field_194037_e))));
            T t2 = t = i <= 0 ? this.field_194036_d : this.field_194037_e;
            if (i <= 0) {
                this.field_194036_d = this.field_194033_a.hasNext() ? this.field_194033_a.next() : null;
                Object v1 = this.field_194036_d;
            }
            if (i >= 0) {
                this.field_194037_e = this.field_194034_b.hasNext() ? this.field_194034_b.next() : null;
            }
            return t;
        }
    }
}

