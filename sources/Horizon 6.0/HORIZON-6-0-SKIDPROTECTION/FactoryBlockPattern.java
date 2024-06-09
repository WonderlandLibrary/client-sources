package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Array;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.List;
import com.google.common.base.Joiner;

public class FactoryBlockPattern
{
    private static final Joiner HorizonCode_Horizon_È;
    private final List Â;
    private final Map Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00002021";
    
    static {
        HorizonCode_Horizon_È = Joiner.on(",");
    }
    
    private FactoryBlockPattern() {
        this.Â = Lists.newArrayList();
        (this.Ý = Maps.newHashMap()).put(' ', Predicates.alwaysTrue());
    }
    
    public FactoryBlockPattern HorizonCode_Horizon_È(final String... p_177659_1_) {
        if (ArrayUtils.isEmpty((Object[])p_177659_1_) || StringUtils.isEmpty((CharSequence)p_177659_1_[0])) {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
        if (this.Â.isEmpty()) {
            this.Ø­áŒŠá = p_177659_1_.length;
            this.Âµá€ = p_177659_1_[0].length();
        }
        if (p_177659_1_.length != this.Ø­áŒŠá) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.Ø­áŒŠá + ", but was given one with a height of " + p_177659_1_.length + ")");
        }
        for (final String var5 : p_177659_1_) {
            if (var5.length() != this.Âµá€) {
                throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.Âµá€ + ", found one with " + var5.length() + ")");
            }
            for (final char var9 : var5.toCharArray()) {
                if (!this.Ý.containsKey(var9)) {
                    this.Ý.put(var9, null);
                }
            }
        }
        this.Â.add(p_177659_1_);
        return this;
    }
    
    public static FactoryBlockPattern HorizonCode_Horizon_È() {
        return new FactoryBlockPattern();
    }
    
    public FactoryBlockPattern HorizonCode_Horizon_È(final char p_177662_1_, final Predicate p_177662_2_) {
        this.Ý.put(p_177662_1_, p_177662_2_);
        return this;
    }
    
    public BlockPattern Â() {
        return new BlockPattern(this.Ý());
    }
    
    private Predicate[][][] Ý() {
        this.Ø­áŒŠá();
        final Predicate[][][] var1 = (Predicate[][][])Array.newInstance(Predicate.class, this.Â.size(), this.Ø­áŒŠá, this.Âµá€);
        for (int var2 = 0; var2 < this.Â.size(); ++var2) {
            for (int var3 = 0; var3 < this.Ø­áŒŠá; ++var3) {
                for (int var4 = 0; var4 < this.Âµá€; ++var4) {
                    var1[var2][var3][var4] = this.Ý.get(((String[])this.Â.get(var2))[var3].charAt(var4));
                }
            }
        }
        return var1;
    }
    
    private void Ø­áŒŠá() {
        final ArrayList var1 = Lists.newArrayList();
        for (final Map.Entry var3 : this.Ý.entrySet()) {
            if (var3.getValue() == null) {
                var1.add(var3.getKey());
            }
        }
        if (!var1.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + FactoryBlockPattern.HorizonCode_Horizon_È.join((Iterable)var1) + " are missing");
        }
    }
}
