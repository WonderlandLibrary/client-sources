/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.state.pattern.BlockPattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FactoryBlockPattern {
    private static final Joiner field_177667_a = Joiner.on((String)",");
    private final List field_177665_b = Lists.newArrayList();
    private final Map field_177666_c = Maps.newHashMap();
    private int field_177663_d;
    private int field_177664_e;
    private static final String __OBFID = "CL_00002021";

    private FactoryBlockPattern() {
        this.field_177666_c.put(Character.valueOf(' '), Predicates.alwaysTrue());
    }

    public /* varargs */ FactoryBlockPattern aisle(String ... p_177659_1_) {
        if (!ArrayUtils.isEmpty((Object[])p_177659_1_) && !StringUtils.isEmpty((CharSequence)p_177659_1_[0])) {
            if (this.field_177665_b.isEmpty()) {
                this.field_177663_d = p_177659_1_.length;
                this.field_177664_e = p_177659_1_[0].length();
            }
            if (p_177659_1_.length != this.field_177663_d) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.field_177663_d + ", but was given one with a height of " + p_177659_1_.length + ")");
            }
            String[] var2 = p_177659_1_;
            int var3 = p_177659_1_.length;
            int var4 = 0;
            while (var4 < var3) {
                String var5 = var2[var4];
                if (var5.length() != this.field_177664_e) {
                    throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.field_177664_e + ", found one with " + var5.length() + ")");
                }
                char[] var6 = var5.toCharArray();
                int var7 = var6.length;
                int var8 = 0;
                while (var8 < var7) {
                    char var9 = var6[var8];
                    if (!this.field_177666_c.containsKey(Character.valueOf(var9))) {
                        this.field_177666_c.put(Character.valueOf(var9), null);
                    }
                    ++var8;
                }
                ++var4;
            }
            this.field_177665_b.add(p_177659_1_);
            return this;
        }
        throw new IllegalArgumentException("Empty pattern for aisle");
    }

    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }

    public FactoryBlockPattern where(char p_177662_1_, Predicate p_177662_2_) {
        this.field_177666_c.put(Character.valueOf(p_177662_1_), p_177662_2_);
        return this;
    }

    public BlockPattern build() {
        return new BlockPattern(this.func_177658_c());
    }

    private Predicate[][][] func_177658_c() {
        this.func_177657_d();
        Predicate[][][] var1 = (Predicate[][][])Array.newInstance(Predicate.class, this.field_177665_b.size(), this.field_177663_d, this.field_177664_e);
        int var2 = 0;
        while (var2 < this.field_177665_b.size()) {
            int var3 = 0;
            while (var3 < this.field_177663_d) {
                int var4 = 0;
                while (var4 < this.field_177664_e) {
                    var1[var2][var3][var4] = (Predicate)this.field_177666_c.get(Character.valueOf(((String[])this.field_177665_b.get(var2))[var3].charAt(var4)));
                    ++var4;
                }
                ++var3;
            }
            ++var2;
        }
        return var1;
    }

    private void func_177657_d() {
        ArrayList var1 = Lists.newArrayList();
        for (Map.Entry var3 : this.field_177666_c.entrySet()) {
            if (var3.getValue() != null) continue;
            var1.add(var3.getKey());
        }
        if (!var1.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + field_177667_a.join((Iterable)var1) + " are missing");
        }
    }
}

