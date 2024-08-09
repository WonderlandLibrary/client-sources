/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.BlockModelDefinition;
import net.minecraft.data.VariantPropertyBuilder;
import net.minecraft.state.Property;

public abstract class BlockStateVariantBuilder {
    private final Map<VariantPropertyBuilder, List<BlockModelDefinition>> field_240131_a_ = Maps.newHashMap();

    protected void func_240140_a_(VariantPropertyBuilder variantPropertyBuilder, List<BlockModelDefinition> list) {
        List<BlockModelDefinition> list2 = this.field_240131_a_.put(variantPropertyBuilder, list);
        if (list2 != null) {
            throw new IllegalStateException("Value " + variantPropertyBuilder + " is already defined");
        }
    }

    Map<VariantPropertyBuilder, List<BlockModelDefinition>> func_240132_a_() {
        this.func_240141_c_();
        return ImmutableMap.copyOf(this.field_240131_a_);
    }

    private void func_240141_c_() {
        List<Property<?>> list = this.func_230527_b_();
        Stream<VariantPropertyBuilder> stream = Stream.of(VariantPropertyBuilder.func_240187_a_());
        for (Property<?> property : list) {
            stream = stream.flatMap(arg_0 -> BlockStateVariantBuilder.lambda$func_240141_c_$0(property, arg_0));
        }
        List list2 = stream.filter(this::lambda$func_240141_c_$1).collect(Collectors.toList());
        if (!list2.isEmpty()) {
            throw new IllegalStateException("Missing definition for properties: " + list2);
        }
    }

    abstract List<Property<?>> func_230527_b_();

    public static <T1 extends Comparable<T1>> One<T1> func_240133_a_(Property<T1> property) {
        return new One<T1>(property);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> Two<T1, T2> func_240134_a_(Property<T1> property, Property<T2> property2) {
        return new Two<T1, T2>(property, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> Three<T1, T2, T3> func_240135_a_(Property<T1> property, Property<T2> property2, Property<T3> property3) {
        return new Three<T1, T2, T3>(property, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> Four<T1, T2, T3, T4> func_240136_a_(Property<T1> property, Property<T2> property2, Property<T3> property3, Property<T4> property4) {
        return new Four<T1, T2, T3, T4>(property, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> Five<T1, T2, T3, T4, T5> func_240137_a_(Property<T1> property, Property<T2> property2, Property<T3> property3, Property<T4> property4, Property<T5> property5) {
        return new Five<T1, T2, T3, T4, T5>(property, property2, property3, property4, property5);
    }

    private boolean lambda$func_240141_c_$1(VariantPropertyBuilder variantPropertyBuilder) {
        return !this.field_240131_a_.containsKey(variantPropertyBuilder);
    }

    private static Stream lambda$func_240141_c_$0(Property property, VariantPropertyBuilder variantPropertyBuilder) {
        return property.func_241491_c_().map(variantPropertyBuilder::func_240188_a_);
    }

    public static class One<T1 extends Comparable<T1>>
    extends BlockStateVariantBuilder {
        private final Property<T1> field_240142_a_;

        private One(Property<T1> property) {
            this.field_240142_a_ = property;
        }

        @Override
        public List<Property<?>> func_230527_b_() {
            return ImmutableList.of(this.field_240142_a_);
        }

        public One<T1> func_240144_a_(T1 T1, List<BlockModelDefinition> list) {
            VariantPropertyBuilder variantPropertyBuilder = VariantPropertyBuilder.func_240190_a_(this.field_240142_a_.func_241490_b_(T1));
            this.func_240140_a_(variantPropertyBuilder, list);
            return this;
        }

        public One<T1> func_240143_a_(T1 T1, BlockModelDefinition blockModelDefinition) {
            return this.func_240144_a_(T1, Collections.singletonList(blockModelDefinition));
        }

        public BlockStateVariantBuilder func_240145_a_(Function<T1, BlockModelDefinition> function) {
            this.field_240142_a_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240145_a_$0(function, arg_0));
            return this;
        }

        private void lambda$func_240145_a_$0(Function function, Comparable comparable) {
            this.func_240143_a_(comparable, (BlockModelDefinition)function.apply(comparable));
        }
    }

    public static class Two<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
    extends BlockStateVariantBuilder {
        private final Property<T1> field_240147_a_;
        private final Property<T2> field_240148_b_;

        private Two(Property<T1> property, Property<T2> property2) {
            this.field_240147_a_ = property;
            this.field_240148_b_ = property2;
        }

        @Override
        public List<Property<?>> func_230527_b_() {
            return ImmutableList.of(this.field_240147_a_, this.field_240148_b_);
        }

        public Two<T1, T2> func_240150_a_(T1 T1, T2 T2, List<BlockModelDefinition> list) {
            VariantPropertyBuilder variantPropertyBuilder = VariantPropertyBuilder.func_240190_a_(this.field_240147_a_.func_241490_b_(T1), this.field_240148_b_.func_241490_b_(T2));
            this.func_240140_a_(variantPropertyBuilder, list);
            return this;
        }

        public Two<T1, T2> func_240149_a_(T1 T1, T2 T2, BlockModelDefinition blockModelDefinition) {
            return this.func_240150_a_(T1, T2, Collections.singletonList(blockModelDefinition));
        }

        public BlockStateVariantBuilder func_240152_a_(BiFunction<T1, T2, BlockModelDefinition> biFunction) {
            this.field_240147_a_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240152_a_$1(biFunction, arg_0));
            return this;
        }

        public BlockStateVariantBuilder func_240155_b_(BiFunction<T1, T2, List<BlockModelDefinition>> biFunction) {
            this.field_240147_a_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240155_b_$3(biFunction, arg_0));
            return this;
        }

        private void lambda$func_240155_b_$3(BiFunction biFunction, Comparable comparable) {
            this.field_240148_b_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240155_b_$2(comparable, biFunction, arg_0));
        }

        private void lambda$func_240155_b_$2(Comparable comparable, BiFunction biFunction, Comparable comparable2) {
            this.func_240150_a_(comparable, comparable2, (List)biFunction.apply(comparable, comparable2));
        }

        private void lambda$func_240152_a_$1(BiFunction biFunction, Comparable comparable) {
            this.field_240148_b_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240152_a_$0(comparable, biFunction, arg_0));
        }

        private void lambda$func_240152_a_$0(Comparable comparable, BiFunction biFunction, Comparable comparable2) {
            this.func_240149_a_(comparable, comparable2, (BlockModelDefinition)biFunction.apply(comparable, comparable2));
        }
    }

    public static class Three<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>>
    extends BlockStateVariantBuilder {
        private final Property<T1> field_240157_a_;
        private final Property<T2> field_240158_b_;
        private final Property<T3> field_240159_c_;

        private Three(Property<T1> property, Property<T2> property2, Property<T3> property3) {
            this.field_240157_a_ = property;
            this.field_240158_b_ = property2;
            this.field_240159_c_ = property3;
        }

        @Override
        public List<Property<?>> func_230527_b_() {
            return ImmutableList.of(this.field_240157_a_, this.field_240158_b_, this.field_240159_c_);
        }

        public Three<T1, T2, T3> func_240162_a_(T1 T1, T2 T2, T3 T3, List<BlockModelDefinition> list) {
            VariantPropertyBuilder variantPropertyBuilder = VariantPropertyBuilder.func_240190_a_(this.field_240157_a_.func_241490_b_(T1), this.field_240158_b_.func_241490_b_(T2), this.field_240159_c_.func_241490_b_(T3));
            this.func_240140_a_(variantPropertyBuilder, list);
            return this;
        }

        public Three<T1, T2, T3> func_240161_a_(T1 T1, T2 T2, T3 T3, BlockModelDefinition blockModelDefinition) {
            return this.func_240162_a_(T1, T2, T3, Collections.singletonList(blockModelDefinition));
        }

        public BlockStateVariantBuilder func_240160_a_(ITriFunction<T1, T2, T3, BlockModelDefinition> iTriFunction) {
            this.field_240157_a_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240160_a_$2(iTriFunction, arg_0));
            return this;
        }

        private void lambda$func_240160_a_$2(ITriFunction iTriFunction, Comparable comparable) {
            this.field_240158_b_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240160_a_$1(comparable, iTriFunction, arg_0));
        }

        private void lambda$func_240160_a_$1(Comparable comparable, ITriFunction iTriFunction, Comparable comparable2) {
            this.field_240159_c_.getAllowedValues().forEach(arg_0 -> this.lambda$func_240160_a_$0(comparable, comparable2, iTriFunction, arg_0));
        }

        private void lambda$func_240160_a_$0(Comparable comparable, Comparable comparable2, ITriFunction iTriFunction, Comparable comparable3) {
            this.func_240161_a_(comparable, comparable2, comparable3, (BlockModelDefinition)iTriFunction.apply(comparable, comparable2, comparable3));
        }
    }

    public static class Four<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
    extends BlockStateVariantBuilder {
        private final Property<T1> field_240166_a_;
        private final Property<T2> field_240167_b_;
        private final Property<T3> field_240168_c_;
        private final Property<T4> field_240169_d_;

        private Four(Property<T1> property, Property<T2> property2, Property<T3> property3, Property<T4> property4) {
            this.field_240166_a_ = property;
            this.field_240167_b_ = property2;
            this.field_240168_c_ = property3;
            this.field_240169_d_ = property4;
        }

        @Override
        public List<Property<?>> func_230527_b_() {
            return ImmutableList.of(this.field_240166_a_, this.field_240167_b_, this.field_240168_c_, this.field_240169_d_);
        }

        public Four<T1, T2, T3, T4> func_240171_a_(T1 T1, T2 T2, T3 T3, T4 T4, List<BlockModelDefinition> list) {
            VariantPropertyBuilder variantPropertyBuilder = VariantPropertyBuilder.func_240190_a_(this.field_240166_a_.func_241490_b_(T1), this.field_240167_b_.func_241490_b_(T2), this.field_240168_c_.func_241490_b_(T3), this.field_240169_d_.func_241490_b_(T4));
            this.func_240140_a_(variantPropertyBuilder, list);
            return this;
        }

        public Four<T1, T2, T3, T4> func_240170_a_(T1 T1, T2 T2, T3 T3, T4 T4, BlockModelDefinition blockModelDefinition) {
            return this.func_240171_a_(T1, T2, T3, T4, Collections.singletonList(blockModelDefinition));
        }
    }

    public static class Five<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
    extends BlockStateVariantBuilder {
        private final Property<T1> field_240172_a_;
        private final Property<T2> field_240173_b_;
        private final Property<T3> field_240174_c_;
        private final Property<T4> field_240175_d_;
        private final Property<T5> field_240176_e_;

        private Five(Property<T1> property, Property<T2> property2, Property<T3> property3, Property<T4> property4, Property<T5> property5) {
            this.field_240172_a_ = property;
            this.field_240173_b_ = property2;
            this.field_240174_c_ = property3;
            this.field_240175_d_ = property4;
            this.field_240176_e_ = property5;
        }

        @Override
        public List<Property<?>> func_230527_b_() {
            return ImmutableList.of(this.field_240172_a_, this.field_240173_b_, this.field_240174_c_, this.field_240175_d_, this.field_240176_e_);
        }

        public Five<T1, T2, T3, T4, T5> func_240178_a_(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5, List<BlockModelDefinition> list) {
            VariantPropertyBuilder variantPropertyBuilder = VariantPropertyBuilder.func_240190_a_(this.field_240172_a_.func_241490_b_(T1), this.field_240173_b_.func_241490_b_(T2), this.field_240174_c_.func_241490_b_(T3), this.field_240175_d_.func_241490_b_(T4), this.field_240176_e_.func_241490_b_(T5));
            this.func_240140_a_(variantPropertyBuilder, list);
            return this;
        }

        public Five<T1, T2, T3, T4, T5> func_240177_a_(T1 T1, T2 T2, T3 T3, T4 T4, T5 T5, BlockModelDefinition blockModelDefinition) {
            return this.func_240178_a_(T1, T2, T3, T4, T5, Collections.singletonList(blockModelDefinition));
        }
    }

    @FunctionalInterface
    public static interface ITriFunction<P1, P2, P3, R> {
        public R apply(P1 var1, P2 var2, P3 var3);
    }
}

