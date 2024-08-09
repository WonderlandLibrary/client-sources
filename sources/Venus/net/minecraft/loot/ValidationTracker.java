/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.loot.IParameterized;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;

public class ValidationTracker {
    private final Multimap<String, String> field_227519_a_;
    private final Supplier<String> field_227520_b_;
    private final LootParameterSet field_227521_c_;
    private final Function<ResourceLocation, ILootCondition> field_227522_d_;
    private final Set<ResourceLocation> field_227523_e_;
    private final Function<ResourceLocation, LootTable> field_227524_f_;
    private final Set<ResourceLocation> field_227525_g_;
    private String field_227526_h_;

    public ValidationTracker(LootParameterSet lootParameterSet, Function<ResourceLocation, ILootCondition> function, Function<ResourceLocation, LootTable> function2) {
        this(HashMultimap.create(), ValidationTracker::lambda$new$0, lootParameterSet, function, ImmutableSet.of(), function2, ImmutableSet.of());
    }

    public ValidationTracker(Multimap<String, String> multimap, Supplier<String> supplier, LootParameterSet lootParameterSet, Function<ResourceLocation, ILootCondition> function, Set<ResourceLocation> set, Function<ResourceLocation, LootTable> function2, Set<ResourceLocation> set2) {
        this.field_227519_a_ = multimap;
        this.field_227520_b_ = supplier;
        this.field_227521_c_ = lootParameterSet;
        this.field_227522_d_ = function;
        this.field_227523_e_ = set;
        this.field_227524_f_ = function2;
        this.field_227525_g_ = set2;
    }

    private String func_227533_b_() {
        if (this.field_227526_h_ == null) {
            this.field_227526_h_ = this.field_227520_b_.get();
        }
        return this.field_227526_h_;
    }

    public void addProblem(String string) {
        this.field_227519_a_.put(this.func_227533_b_(), string);
    }

    public ValidationTracker func_227534_b_(String string) {
        return new ValidationTracker(this.field_227519_a_, () -> this.lambda$func_227534_b_$1(string), this.field_227521_c_, this.field_227522_d_, this.field_227523_e_, this.field_227524_f_, this.field_227525_g_);
    }

    public ValidationTracker func_227531_a_(String string, ResourceLocation resourceLocation) {
        ImmutableCollection immutableCollection = ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(this.field_227525_g_)).add(resourceLocation)).build();
        return new ValidationTracker(this.field_227519_a_, () -> this.lambda$func_227531_a_$2(string), this.field_227521_c_, this.field_227522_d_, this.field_227523_e_, this.field_227524_f_, (Set<ResourceLocation>)((Object)immutableCollection));
    }

    public ValidationTracker func_227535_b_(String string, ResourceLocation resourceLocation) {
        ImmutableCollection immutableCollection = ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().addAll(this.field_227523_e_)).add(resourceLocation)).build();
        return new ValidationTracker(this.field_227519_a_, () -> this.lambda$func_227535_b_$3(string), this.field_227521_c_, this.field_227522_d_, (Set<ResourceLocation>)((Object)immutableCollection), this.field_227524_f_, this.field_227525_g_);
    }

    public boolean func_227532_a_(ResourceLocation resourceLocation) {
        return this.field_227525_g_.contains(resourceLocation);
    }

    public boolean func_227536_b_(ResourceLocation resourceLocation) {
        return this.field_227523_e_.contains(resourceLocation);
    }

    public Multimap<String, String> getProblems() {
        return ImmutableMultimap.copyOf(this.field_227519_a_);
    }

    public void func_227528_a_(IParameterized iParameterized) {
        this.field_227521_c_.func_227556_a_(this, iParameterized);
    }

    @Nullable
    public LootTable func_227539_c_(ResourceLocation resourceLocation) {
        return this.field_227524_f_.apply(resourceLocation);
    }

    @Nullable
    public ILootCondition func_227541_d_(ResourceLocation resourceLocation) {
        return this.field_227522_d_.apply(resourceLocation);
    }

    public ValidationTracker func_227529_a_(LootParameterSet lootParameterSet) {
        return new ValidationTracker(this.field_227519_a_, this.field_227520_b_, lootParameterSet, this.field_227522_d_, this.field_227523_e_, this.field_227524_f_, this.field_227525_g_);
    }

    private String lambda$func_227535_b_$3(String string) {
        return this.func_227533_b_() + string;
    }

    private String lambda$func_227531_a_$2(String string) {
        return this.func_227533_b_() + string;
    }

    private String lambda$func_227534_b_$1(String string) {
        return this.func_227533_b_() + string;
    }

    private static String lambda$new$0() {
        return "";
    }
}

