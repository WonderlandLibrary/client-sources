package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import org.apache.commons.lang3.ClassUtils;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.google.common.collect.HashMultimap;
import java.util.Set;
import com.google.common.collect.Multimap;
import java.util.AbstractSet;

public class ClassInheratanceMultiMap extends AbstractSet
{
    private final Multimap HorizonCode_Horizon_È;
    private final Set Â;
    private final Class Ý;
    private static final String Ø­áŒŠá = "CL_00002266";
    
    public ClassInheratanceMultiMap(final Class p_i45909_1_) {
        this.HorizonCode_Horizon_È = (Multimap)HashMultimap.create();
        this.Â = Sets.newIdentityHashSet();
        this.Ý = p_i45909_1_;
        this.Â.add(p_i45909_1_);
    }
    
    public void HorizonCode_Horizon_È(final Class p_180213_1_) {
        for (final Object var3 : this.HorizonCode_Horizon_È.get((Object)this.HorizonCode_Horizon_È(p_180213_1_, false))) {
            if (p_180213_1_.isAssignableFrom(var3.getClass())) {
                this.HorizonCode_Horizon_È.put((Object)p_180213_1_, var3);
            }
        }
        this.Â.add(p_180213_1_);
    }
    
    protected Class HorizonCode_Horizon_È(final Class p_180212_1_, final boolean p_180212_2_) {
        for (final Class var4 : ClassUtils.hierarchy(p_180212_1_, ClassUtils.Interfaces.INCLUDE)) {
            if (this.Â.contains(var4)) {
                if (var4 == this.Ý && p_180212_2_) {
                    this.HorizonCode_Horizon_È(p_180212_1_);
                }
                return var4;
            }
        }
        throw new IllegalArgumentException("Don't know how to search for " + p_180212_1_);
    }
    
    @Override
    public boolean add(final Object p_add_1_) {
        for (final Class var3 : this.Â) {
            if (var3.isAssignableFrom(p_add_1_.getClass())) {
                this.HorizonCode_Horizon_È.put((Object)var3, p_add_1_);
            }
        }
        return true;
    }
    
    @Override
    public boolean remove(final Object p_remove_1_) {
        boolean var3 = false;
        for (final Class var5 : this.Â) {
            if (var5.isAssignableFrom(p_remove_1_.getClass())) {
                var3 |= this.HorizonCode_Horizon_È.remove((Object)var5, p_remove_1_);
            }
        }
        return var3;
    }
    
    public Iterable Â(final Class p_180215_1_) {
        return new Iterable() {
            private static final String Â = "CL_00002265";
            
            @Override
            public Iterator iterator() {
                final Iterator var1 = ClassInheratanceMultiMap.this.HorizonCode_Horizon_È.get((Object)ClassInheratanceMultiMap.this.HorizonCode_Horizon_È(p_180215_1_, true)).iterator();
                return (Iterator)Iterators.filter(var1, p_180215_1_);
            }
        };
    }
    
    @Override
    public Iterator iterator() {
        final Iterator var1 = this.HorizonCode_Horizon_È.get((Object)this.Ý).iterator();
        return (Iterator)new AbstractIterator() {
            private static final String Â = "CL_00002264";
            
            protected Object computeNext() {
                return var1.hasNext() ? var1.next() : this.endOfData();
            }
        };
    }
    
    @Override
    public int size() {
        return this.HorizonCode_Horizon_È.get((Object)this.Ý).size();
    }
}
