package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;
import java.util.Collections;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.base.Objects;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Comparator;
import com.google.common.collect.ImmutableList;
import com.google.common.base.Function;
import com.google.common.base.Joiner;

public class BlockState
{
    private static final Joiner HorizonCode_Horizon_È;
    private static final Function Â;
    private final Block Ý;
    private final ImmutableList Ø­áŒŠá;
    private final ImmutableList Âµá€;
    private static final String Ó = "CL_00002030";
    
    static {
        HorizonCode_Horizon_È = Joiner.on(", ");
        Â = (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002029";
            
            public String HorizonCode_Horizon_È(final IProperty property) {
                return (property == null) ? "<NULL>" : property.HorizonCode_Horizon_È();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((IProperty)p_apply_1_);
            }
        };
    }
    
    public BlockState(final Block blockIn, final IProperty... properties) {
        this.Ý = blockIn;
        Arrays.sort(properties, new Comparator() {
            private static final String Â = "CL_00002028";
            
            public int HorizonCode_Horizon_È(final IProperty left, final IProperty right) {
                return left.HorizonCode_Horizon_È().compareTo(right.HorizonCode_Horizon_È());
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.HorizonCode_Horizon_È((IProperty)p_compare_1_, (IProperty)p_compare_2_);
            }
        });
        this.Ø­áŒŠá = ImmutableList.copyOf((Object[])properties);
        final LinkedHashMap var3 = Maps.newLinkedHashMap();
        final ArrayList var4 = Lists.newArrayList();
        final Iterable var5 = Cartesian.HorizonCode_Horizon_È(this.Âµá€());
        for (final List var7 : var5) {
            final Map var8 = MapPopulator.HorizonCode_Horizon_È((Iterable)this.Ø­áŒŠá, var7);
            final HorizonCode_Horizon_È var9 = new HorizonCode_Horizon_È(blockIn, ImmutableMap.copyOf(var8), null);
            var3.put(var8, var9);
            var4.add(var9);
        }
        for (final HorizonCode_Horizon_È var10 : var4) {
            var10.HorizonCode_Horizon_È(var3);
        }
        this.Âµá€ = ImmutableList.copyOf((Collection)var4);
    }
    
    public ImmutableList HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    private List Âµá€() {
        final ArrayList var1 = Lists.newArrayList();
        for (int var2 = 0; var2 < this.Ø­áŒŠá.size(); ++var2) {
            var1.add(((IProperty)this.Ø­áŒŠá.get(var2)).Â());
        }
        return var1;
    }
    
    public IBlockState Â() {
        return (IBlockState)this.Âµá€.get(0);
    }
    
    public Block Ý() {
        return this.Ý;
    }
    
    public Collection Ø­áŒŠá() {
        return (Collection)this.Ø­áŒŠá;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("block", Block.HorizonCode_Horizon_È.Â(this.Ý)).add("properties", (Object)Iterables.transform((Iterable)this.Ø­áŒŠá, BlockState.Â)).toString();
    }
    
    static class HorizonCode_Horizon_È extends BlockStateBase
    {
        private final Block HorizonCode_Horizon_È;
        private final ImmutableMap Â;
        private ImmutableTable Ý;
        private static final String Ø­áŒŠá = "CL_00002027";
        
        private HorizonCode_Horizon_È(final Block p_i45660_1_, final ImmutableMap p_i45660_2_) {
            this.HorizonCode_Horizon_È = p_i45660_1_;
            this.Â = p_i45660_2_;
        }
        
        @Override
        public Collection HorizonCode_Horizon_È() {
            return Collections.unmodifiableCollection((Collection<?>)this.Â.keySet());
        }
        
        @Override
        public Comparable HorizonCode_Horizon_È(final IProperty property) {
            if (!this.Â.containsKey((Object)property)) {
                throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.HorizonCode_Horizon_È.ŠÂµà());
            }
            return property.Ý().cast(this.Â.get((Object)property));
        }
        
        @Override
        public IBlockState HorizonCode_Horizon_È(final IProperty property, final Comparable value) {
            if (!this.Â.containsKey((Object)property)) {
                throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.HorizonCode_Horizon_È.ŠÂµà());
            }
            if (!property.Â().contains(value)) {
                throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.HorizonCode_Horizon_È.Â(this.HorizonCode_Horizon_È) + ", it is not an allowed value");
            }
            return (this.Â.get((Object)property) == value) ? this : ((IBlockState)this.Ý.get((Object)property, (Object)value));
        }
        
        @Override
        public ImmutableMap Â() {
            return this.Â;
        }
        
        @Override
        public Block Ý() {
            return this.HorizonCode_Horizon_È;
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            return this == p_equals_1_;
        }
        
        @Override
        public int hashCode() {
            return this.Â.hashCode();
        }
        
        public void HorizonCode_Horizon_È(final Map map) {
            if (this.Ý != null) {
                throw new IllegalStateException();
            }
            final HashBasedTable var2 = HashBasedTable.create();
            for (final IProperty var4 : this.Â.keySet()) {
                for (final Comparable var6 : var4.Â()) {
                    if (var6 != this.Â.get((Object)var4)) {
                        var2.put((Object)var4, (Object)var6, map.get(this.Â(var4, var6)));
                    }
                }
            }
            this.Ý = ImmutableTable.copyOf((Table)var2);
        }
        
        private Map Â(final IProperty property, final Comparable value) {
            final HashMap var3 = Maps.newHashMap((Map)this.Â);
            var3.put(property, value);
            return var3;
        }
        
        HorizonCode_Horizon_È(final Block p_i45661_1_, final ImmutableMap p_i45661_2_, final Object p_i45661_3_) {
            this(p_i45661_1_, p_i45661_2_);
        }
    }
}
