package HORIZON-6-0-SKIDPROTECTION;

import java.util.NoSuchElementException;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class Cartesian
{
    private static final String HorizonCode_Horizon_È = "CL_00002327";
    
    public static Iterable HorizonCode_Horizon_È(final Class clazz, final Iterable sets) {
        return new Â(clazz, (Iterable[])Â(Iterable.class, sets), null);
    }
    
    public static Iterable HorizonCode_Horizon_È(final Iterable sets) {
        return Â(HorizonCode_Horizon_È(Object.class, sets));
    }
    
    private static Iterable Â(final Iterable arrays) {
        return Iterables.transform(arrays, (Function)new HorizonCode_Horizon_È(null));
    }
    
    private static Object[] Â(final Class clazz, final Iterable it) {
        final ArrayList var2 = Lists.newArrayList();
        for (final Object var4 : it) {
            var2.add(var4);
        }
        return var2.toArray(Â(clazz, var2.size()));
    }
    
    private static Object[] Â(final Class p_179319_0_, final int p_179319_1_) {
        return (Object[])Array.newInstance(p_179319_0_, p_179319_1_);
    }
    
    static class HorizonCode_Horizon_È implements Function
    {
        private static final String HorizonCode_Horizon_È = "CL_00002325";
        
        private HorizonCode_Horizon_È() {
        }
        
        public List HorizonCode_Horizon_È(final Object[] array) {
            return Arrays.asList(array);
        }
        
        public Object apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((Object[])p_apply_1_);
        }
        
        HorizonCode_Horizon_È(final Object p_i46022_1_) {
            this();
        }
    }
    
    static class Â implements Iterable
    {
        private final Class HorizonCode_Horizon_È;
        private final Iterable[] Â;
        private static final String Ý = "CL_00002324";
        
        private Â(final Class clazz, final Iterable[] iterables) {
            this.HorizonCode_Horizon_È = clazz;
            this.Â = iterables;
        }
        
        @Override
        public Iterator iterator() {
            return (Iterator)((this.Â.length <= 0) ? Collections.singletonList(Â(this.HorizonCode_Horizon_È, 0)).iterator() : new HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, null));
        }
        
        Â(final Class p_i46021_1_, final Iterable[] p_i46021_2_, final Object p_i46021_3_) {
            this(p_i46021_1_, p_i46021_2_);
        }
        
        static class HorizonCode_Horizon_È extends UnmodifiableIterator
        {
            private int HorizonCode_Horizon_È;
            private final Iterable[] Â;
            private final Iterator[] Ý;
            private final Object[] Ø­áŒŠá;
            private static final String Âµá€ = "CL_00002323";
            
            private HorizonCode_Horizon_È(final Class clazz, final Iterable[] iterables) {
                this.HorizonCode_Horizon_È = -2;
                this.Â = iterables;
                this.Ý = (Iterator[])Â(Iterator.class, this.Â.length);
                for (int var3 = 0; var3 < this.Â.length; ++var3) {
                    this.Ý[var3] = iterables[var3].iterator();
                }
                this.Ø­áŒŠá = Â(clazz, this.Ý.length);
            }
            
            private void Â() {
                this.HorizonCode_Horizon_È = -1;
                Arrays.fill(this.Ý, null);
                Arrays.fill(this.Ø­áŒŠá, null);
            }
            
            public boolean hasNext() {
                if (this.HorizonCode_Horizon_È == -2) {
                    this.HorizonCode_Horizon_È = 0;
                    for (final Iterator var8 : this.Ý) {
                        if (!var8.hasNext()) {
                            this.Â();
                            break;
                        }
                    }
                    return true;
                }
                if (this.HorizonCode_Horizon_È >= this.Ý.length) {
                    this.HorizonCode_Horizon_È = this.Ý.length - 1;
                    while (this.HorizonCode_Horizon_È >= 0) {
                        Iterator var9 = this.Ý[this.HorizonCode_Horizon_È];
                        if (var9.hasNext()) {
                            break;
                        }
                        if (this.HorizonCode_Horizon_È == 0) {
                            this.Â();
                            break;
                        }
                        var9 = this.Â[this.HorizonCode_Horizon_È].iterator();
                        this.Ý[this.HorizonCode_Horizon_È] = var9;
                        if (!var9.hasNext()) {
                            this.Â();
                            break;
                        }
                        --this.HorizonCode_Horizon_È;
                    }
                }
                return this.HorizonCode_Horizon_È >= 0;
            }
            
            public Object[] HorizonCode_Horizon_È() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                while (this.HorizonCode_Horizon_È < this.Ý.length) {
                    this.Ø­áŒŠá[this.HorizonCode_Horizon_È] = this.Ý[this.HorizonCode_Horizon_È].next();
                    ++this.HorizonCode_Horizon_È;
                }
                return this.Ø­áŒŠá.clone();
            }
            
            public Object next() {
                return this.HorizonCode_Horizon_È();
            }
            
            HorizonCode_Horizon_È(final Class p_i46019_1_, final Iterable[] p_i46019_2_, final Object p_i46019_3_) {
                this(p_i46019_1_, p_i46019_2_);
            }
        }
    }
}
