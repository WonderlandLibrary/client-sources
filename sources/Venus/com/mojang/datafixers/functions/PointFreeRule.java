/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Apply;
import com.mojang.datafixers.functions.Bang;
import com.mojang.datafixers.functions.Comp;
import com.mojang.datafixers.functions.Fold;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.ProfunctorTransformer;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.constant.EmptyPart;
import com.mojang.datafixers.types.families.ListAlgebra;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

public interface PointFreeRule {
    public <A> Optional<? extends PointFree<A>> rewrite(Type<A> var1, PointFree<A> var2);

    default public <A, B> Optional<View<A, B>> rewrite(View<A, B> view) {
        return this.rewrite(view.getFuncType(), view.function()).map(arg_0 -> PointFreeRule.lambda$rewrite$0(view, arg_0));
    }

    default public <A> PointFree<A> rewriteOrNop(Type<A> type, PointFree<A> pointFree) {
        return DataFixUtils.orElse(this.rewrite(type, pointFree), pointFree);
    }

    default public <A, B> View<A, B> rewriteOrNop(View<A, B> view) {
        return DataFixUtils.orElse(this.rewrite(view), view);
    }

    public static PointFreeRule nop() {
        return Nop.INSTANCE;
    }

    public static PointFreeRule seq(PointFreeRule pointFreeRule, Supplier<PointFreeRule> supplier) {
        return PointFreeRule.seq(ImmutableList.of(() -> PointFreeRule.lambda$seq$1(pointFreeRule), supplier));
    }

    public static PointFreeRule seq(List<Supplier<PointFreeRule>> list) {
        return new Seq(list);
    }

    public static PointFreeRule orElse(PointFreeRule pointFreeRule, PointFreeRule pointFreeRule2) {
        return new OrElse(pointFreeRule, () -> PointFreeRule.lambda$orElse$2(pointFreeRule2));
    }

    public static PointFreeRule orElseStrict(PointFreeRule pointFreeRule, Supplier<PointFreeRule> supplier) {
        return new OrElse(pointFreeRule, supplier);
    }

    public static PointFreeRule all(PointFreeRule pointFreeRule) {
        return new All(pointFreeRule);
    }

    public static PointFreeRule one(PointFreeRule pointFreeRule) {
        return new One(pointFreeRule);
    }

    public static PointFreeRule once(PointFreeRule pointFreeRule) {
        return PointFreeRule.orElseStrict(pointFreeRule, () -> PointFreeRule.lambda$once$3(pointFreeRule));
    }

    public static PointFreeRule many(PointFreeRule pointFreeRule) {
        return new Many(pointFreeRule);
    }

    public static PointFreeRule everywhere(PointFreeRule pointFreeRule) {
        return PointFreeRule.seq(PointFreeRule.orElse(pointFreeRule, Nop.INSTANCE), () -> PointFreeRule.lambda$everywhere$4(pointFreeRule));
    }

    private static PointFreeRule lambda$everywhere$4(PointFreeRule pointFreeRule) {
        return PointFreeRule.all(PointFreeRule.everywhere(pointFreeRule));
    }

    private static PointFreeRule lambda$once$3(PointFreeRule pointFreeRule) {
        return PointFreeRule.one(PointFreeRule.once(pointFreeRule));
    }

    private static PointFreeRule lambda$orElse$2(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static PointFreeRule lambda$seq$1(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static View lambda$rewrite$0(View view, PointFree pointFree) {
        return View.create(view.type(), view.newType(), pointFree);
    }

    public static final class Many
    implements PointFreeRule {
        private final PointFreeRule rule;

        public Many(PointFreeRule pointFreeRule) {
            this.rule = pointFreeRule;
        }

        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            Optional<PointFree<Object>> optional = Optional.of(pointFree);
            Optional optional2;
            while ((optional2 = optional.flatMap(arg_0 -> this.lambda$rewrite$1(type, arg_0))).isPresent()) {
                optional = optional2;
            }
            return optional;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Many many = (Many)object;
            return Objects.equals(this.rule, many.rule);
        }

        public int hashCode() {
            return Objects.hash(this.rule);
        }

        private Optional lambda$rewrite$1(Type type, PointFree pointFree) {
            return this.rule.rewrite(type, pointFree).map(Many::lambda$null$0);
        }

        private static PointFree lambda$null$0(PointFree pointFree) {
            return pointFree;
        }
    }

    public static final class One
    implements PointFreeRule {
        private final PointFreeRule rule;

        public One(PointFreeRule pointFreeRule) {
            this.rule = pointFreeRule;
        }

        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            return pointFree.one(this.rule, type);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof One)) {
                return true;
            }
            One one = (One)object;
            return Objects.equals(this.rule, one.rule);
        }

        public int hashCode() {
            return this.rule.hashCode();
        }
    }

    public static final class All
    implements PointFreeRule {
        private final PointFreeRule rule;

        public All(PointFreeRule pointFreeRule) {
            this.rule = pointFreeRule;
        }

        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            return pointFree.all(this.rule, type);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof All)) {
                return true;
            }
            All all = (All)object;
            return Objects.equals(this.rule, all.rule);
        }

        public int hashCode() {
            return this.rule.hashCode();
        }
    }

    public static final class OrElse
    implements PointFreeRule {
        protected final PointFreeRule first;
        protected final Supplier<PointFreeRule> second;

        public OrElse(PointFreeRule pointFreeRule, Supplier<PointFreeRule> supplier) {
            this.first = pointFreeRule;
            this.second = supplier;
        }

        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            Optional<PointFree<A>> optional = this.first.rewrite(type, pointFree);
            if (optional.isPresent()) {
                return optional;
            }
            return this.second.get().rewrite(type, pointFree);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof OrElse)) {
                return true;
            }
            OrElse orElse = (OrElse)object;
            return Objects.equals(this.first, orElse.first) && Objects.equals(this.second, orElse.second);
        }

        public int hashCode() {
            return Objects.hash(this.first, this.second);
        }
    }

    public static final class Seq
    implements PointFreeRule {
        private final List<Supplier<PointFreeRule>> rules;

        public Seq(List<Supplier<PointFreeRule>> list) {
            this.rules = ImmutableList.copyOf(list);
        }

        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            Optional<PointFree<Object>> optional = Optional.of(pointFree);
            for (Supplier<PointFreeRule> supplier : this.rules) {
                optional = optional.flatMap(arg_0 -> Seq.lambda$rewrite$0(supplier, type, arg_0));
            }
            return optional;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Seq)) {
                return true;
            }
            Seq seq = (Seq)object;
            return Objects.equals(this.rules, seq.rules);
        }

        public int hashCode() {
            return Objects.hash(this.rules);
        }

        private static Optional lambda$rewrite$0(Supplier supplier, Type type, PointFree pointFree) {
            return ((PointFreeRule)supplier.get()).rewrite(type, pointFree);
        }
    }

    public static enum CataFuseDifferent implements CompRewrite
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> type, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree, PointFree<? extends Function<?, ?>> pointFree2) {
            if (pointFree instanceof Fold && pointFree2 instanceof Fold) {
                Fold fold = (Fold)pointFree;
                Fold fold2 = (Fold)pointFree2;
                RecursiveTypeFamily recursiveTypeFamily = fold.aType.family();
                if (Objects.equals(recursiveTypeFamily, fold2.aType.family()) && fold.index == fold2.index) {
                    RewriteResult<?, ?> rewriteResult;
                    ArrayList<RewriteResult<?, ?>> arrayList = Lists.newArrayList();
                    BitSet bitSet = new BitSet(recursiveTypeFamily.size());
                    BitSet bitSet2 = new BitSet(recursiveTypeFamily.size());
                    for (int i = 0; i < recursiveTypeFamily.size(); ++i) {
                        RewriteResult<?, ?> rewriteResult2 = fold.algebra.apply(i);
                        rewriteResult = fold2.algebra.apply(i);
                        boolean bl = Objects.equals(CompAssocRight.INSTANCE.rewriteOrNop(rewriteResult2.view()).function(), Functions.id());
                        boolean bl2 = Objects.equals(rewriteResult.view().function(), Functions.id());
                        bitSet.set(i, !bl);
                        bitSet2.set(i, !bl2);
                    }
                    BitSet bitSet3 = ObjectUtils.clone(bitSet);
                    bitSet3.or(bitSet2);
                    for (int i = 0; i < recursiveTypeFamily.size(); ++i) {
                        rewriteResult = fold.algebra.apply(i);
                        RewriteResult<?, ?> rewriteResult3 = fold2.algebra.apply(i);
                        PointFree<Function<?, ?>> pointFree3 = CompAssocRight.INSTANCE.rewriteOrNop(rewriteResult.view()).function();
                        PointFree<Function<?, ?>> pointFree4 = CompAssocRight.INSTANCE.rewriteOrNop(rewriteResult3.view()).function();
                        boolean bl = Objects.equals(pointFree3, Functions.id());
                        boolean bl3 = Objects.equals(pointFree4, Functions.id());
                        if (rewriteResult.recData().intersects(bitSet2) || rewriteResult3.recData().intersects(bitSet)) {
                            return Optional.empty();
                        }
                        if (bl) {
                            arrayList.add(rewriteResult3);
                            continue;
                        }
                        if (bl3) {
                            arrayList.add(rewriteResult);
                            continue;
                        }
                        return Optional.empty();
                    }
                    ListAlgebra listAlgebra = new ListAlgebra("FusedDifferent", arrayList);
                    return Optional.of(recursiveTypeFamily.fold(listAlgebra).apply(fold.index).view().function());
                }
            }
            return Optional.empty();
        }
    }

    public static enum CataFuseSame implements CompRewrite
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> type, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree, PointFree<? extends Function<?, ?>> pointFree2) {
            if (pointFree instanceof Fold && pointFree2 instanceof Fold) {
                Fold fold = (Fold)pointFree;
                Fold fold2 = (Fold)pointFree2;
                RecursiveTypeFamily recursiveTypeFamily = fold.aType.family();
                if (Objects.equals(recursiveTypeFamily, fold2.aType.family()) && fold.index == fold2.index) {
                    ArrayList<RewriteResult<?, ?>> arrayList = Lists.newArrayList();
                    boolean bl = false;
                    for (int i = 0; i < recursiveTypeFamily.size(); ++i) {
                        RewriteResult<?, ?> rewriteResult = fold.algebra.apply(i);
                        RewriteResult<?, ?> rewriteResult2 = fold2.algebra.apply(i);
                        boolean bl2 = Objects.equals(CompAssocRight.INSTANCE.rewriteOrNop(rewriteResult.view()).function(), Functions.id());
                        boolean bl3 = Objects.equals(rewriteResult2.view().function(), Functions.id());
                        if (bl2 && bl3) {
                            arrayList.add(fold.algebra.apply(i));
                            continue;
                        }
                        if (!(bl || bl2 || bl3)) {
                            arrayList.add(this.getCompose(rewriteResult, rewriteResult2));
                            bl = true;
                            continue;
                        }
                        return Optional.empty();
                    }
                    ListAlgebra listAlgebra = new ListAlgebra("FusedSame", arrayList);
                    return Optional.of(recursiveTypeFamily.fold(listAlgebra).apply(fold.index).view().function());
                }
            }
            return Optional.empty();
        }

        private <B> RewriteResult<?, ?> getCompose(RewriteResult<B, ?> rewriteResult, RewriteResult<?, ?> rewriteResult2) {
            return rewriteResult.compose(rewriteResult2);
        }
    }

    public static enum LensComp implements CompRewrite
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> type, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree, PointFree<? extends Function<?, ?>> pointFree2) {
            if (pointFree instanceof Apply && pointFree2 instanceof Apply) {
                Apply apply = (Apply)pointFree;
                Apply apply2 = (Apply)pointFree2;
                PointFree pointFree3 = apply.func;
                PointFree pointFree4 = apply2.func;
                if (pointFree3 instanceof ProfunctorTransformer && pointFree4 instanceof ProfunctorTransformer) {
                    ProfunctorTransformer profunctorTransformer = (ProfunctorTransformer)pointFree3;
                    ProfunctorTransformer profunctorTransformer2 = (ProfunctorTransformer)pointFree4;
                    if (Objects.equals(profunctorTransformer.optic, profunctorTransformer2.optic)) {
                        Func func = (Func)apply.argType;
                        Func func2 = (Func)apply2.argType;
                        return this.cap(profunctorTransformer, profunctorTransformer2, apply.arg, apply2.arg, func, func2);
                    }
                }
            }
            return Optional.empty();
        }

        private <R, A, B, C, S, T, U> Optional<? extends PointFree<R>> cap(ProfunctorTransformer<S, T, A, B> profunctorTransformer, ProfunctorTransformer<?, U, ?, C> profunctorTransformer2, PointFree<?> pointFree, PointFree<?> pointFree2, Func<?, ?> func, Func<?, ?> func2) {
            return this.cap2(profunctorTransformer, profunctorTransformer2, pointFree, pointFree2, func, func2);
        }

        private <R, P extends K2, Proof extends K1, A, B, C, S, T, U> Optional<? extends PointFree<R>> cap2(ProfunctorTransformer<S, T, A, B> profunctorTransformer, ProfunctorTransformer<T, U, B, C> profunctorTransformer2, PointFree<Function<B, C>> pointFree, PointFree<Function<A, B>> pointFree2, Func<B, C> func, Func<A, B> func2) {
            ProfunctorTransformer<S, T, A, B> profunctorTransformer3 = profunctorTransformer;
            PointFree<Function<A, C>> pointFree3 = Functions.comp(func.first(), pointFree, pointFree2);
            return Optional.of(Functions.app(profunctorTransformer3, pointFree3, DSL.func(func2.first(), func.second())));
        }
    }

    public static enum LensCompFunc implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp = (Comp)pointFree;
                PointFree pointFree2 = comp.first;
                PointFree pointFree3 = comp.second;
                if (pointFree2 instanceof ProfunctorTransformer && pointFree3 instanceof ProfunctorTransformer) {
                    ProfunctorTransformer profunctorTransformer = (ProfunctorTransformer)pointFree2;
                    ProfunctorTransformer profunctorTransformer2 = (ProfunctorTransformer)pointFree3;
                    return Optional.of(this.cap(profunctorTransformer, profunctorTransformer2));
                }
            }
            return Optional.empty();
        }

        private <R, X, Y, S, T, A, B> R cap(ProfunctorTransformer<X, Y, ?, ?> profunctorTransformer, ProfunctorTransformer<S, T, A, B> profunctorTransformer2) {
            ProfunctorTransformer<X, Y, ?, ?> profunctorTransformer3 = profunctorTransformer;
            return (R)Functions.profunctorTransformer(profunctorTransformer3.optic.compose(profunctorTransformer2.optic));
        }
    }

    public static enum SortInj implements CompRewrite
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> type, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree, PointFree<? extends Function<?, ?>> pointFree2) {
            if (pointFree instanceof Apply && pointFree2 instanceof Apply) {
                Apply apply = (Apply)pointFree;
                Apply apply2 = (Apply)pointFree2;
                PointFree pointFree3 = apply.func;
                PointFree pointFree4 = apply2.func;
                if (pointFree3 instanceof ProfunctorTransformer && pointFree4 instanceof ProfunctorTransformer) {
                    ProfunctorTransformer profunctorTransformer = (ProfunctorTransformer)pointFree3;
                    ProfunctorTransformer profunctorTransformer2 = (ProfunctorTransformer)pointFree4;
                    Optic optic = profunctorTransformer.optic;
                    while (optic instanceof Optic.CompositionOptic) {
                        optic = ((Optic.CompositionOptic)optic).outer();
                    }
                    Optic optic2 = profunctorTransformer2.optic;
                    while (optic2 instanceof Optic.CompositionOptic) {
                        optic2 = ((Optic.CompositionOptic)optic2).outer();
                    }
                    if (Objects.equals(optic, Optics.inj2()) && Objects.equals(optic2, Optics.inj1())) {
                        Func func = (Func)apply.argType;
                        Func func2 = (Func)apply2.argType;
                        return Optional.of(this.cap(func, func2, apply, apply2));
                    }
                }
            }
            return Optional.empty();
        }

        private <R, A, A2, B, B2> R cap(Func<B, B2> func, Func<A, A2> func2, Apply<?, ?> apply, Apply<?, ?> apply2) {
            return (R)Functions.comp(DSL.or(func2.first(), func.second()), apply2, apply);
        }
    }

    public static enum SortProj implements CompRewrite
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> type, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree, PointFree<? extends Function<?, ?>> pointFree2) {
            if (pointFree instanceof Apply && pointFree2 instanceof Apply) {
                Apply apply = (Apply)pointFree;
                Apply apply2 = (Apply)pointFree2;
                PointFree pointFree3 = apply.func;
                PointFree pointFree4 = apply2.func;
                if (pointFree3 instanceof ProfunctorTransformer && pointFree4 instanceof ProfunctorTransformer) {
                    ProfunctorTransformer profunctorTransformer = (ProfunctorTransformer)pointFree3;
                    ProfunctorTransformer profunctorTransformer2 = (ProfunctorTransformer)pointFree4;
                    Optic optic = profunctorTransformer.optic;
                    while (optic instanceof Optic.CompositionOptic) {
                        optic = ((Optic.CompositionOptic)optic).outer();
                    }
                    Optic optic2 = profunctorTransformer2.optic;
                    while (optic2 instanceof Optic.CompositionOptic) {
                        optic2 = ((Optic.CompositionOptic)optic2).outer();
                    }
                    if (Objects.equals(optic, Optics.proj2()) && Objects.equals(optic2, Optics.proj1())) {
                        Func func = (Func)apply.argType;
                        Func func2 = (Func)apply2.argType;
                        return Optional.of(this.cap(func, func2, apply, apply2));
                    }
                }
            }
            return Optional.empty();
        }

        private <R, A, A2, B, B2> R cap(Func<B, B2> func, Func<A, A2> func2, Apply<?, ?> apply, Apply<?, ?> apply2) {
            return (R)Functions.comp(DSL.and(func2.first(), func.second()), apply2, apply);
        }
    }

    public static interface CompRewrite
    extends PointFreeRule {
        @Override
        default public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp = (Comp)pointFree;
                PointFree pointFree2 = comp.first;
                PointFree pointFree3 = comp.second;
                if (pointFree2 instanceof Comp) {
                    Comp comp2 = (Comp)pointFree2;
                    return this.doRewrite(type, comp.middleType, comp2.second, comp.second).map(arg_0 -> CompRewrite.lambda$rewrite$0(comp2, arg_0));
                }
                if (pointFree3 instanceof Comp) {
                    Comp comp3 = (Comp)pointFree3;
                    return this.doRewrite(type, comp.middleType, comp.first, comp3.first).map(arg_0 -> CompRewrite.lambda$rewrite$1(comp3, arg_0));
                }
                return this.doRewrite(type, comp.middleType, comp.first, comp.second);
            }
            return Optional.empty();
        }

        public static <A, B, C, D> PointFree<D> buildLeft(PointFree<?> pointFree, Comp<A, B, C> comp) {
            return new Comp(comp.middleType, pointFree, comp.second);
        }

        public static <A, B, C, D> PointFree<D> buildRight(Comp<A, B, C> comp, PointFree<?> pointFree) {
            return new Comp(comp.middleType, comp.first, pointFree);
        }

        public static <A, B, C, D, E> PointFree<E> buildLeftNested(Comp<A, B, C> comp, Comp<?, ?, D> comp2) {
            Comp<?, ?, D> comp3 = comp2;
            return new Comp(comp.middleType, new Comp(comp3.middleType, comp3.first, comp.first), comp.second);
        }

        public static <A, B, C, D, E> PointFree<E> buildRightNested(Comp<A, B, D> comp, Comp<?, C, ?> comp2) {
            Comp<?, C, ?> comp3 = comp2;
            return new Comp(comp3.middleType, comp3.first, new Comp(comp.middleType, comp3.second, comp.second));
        }

        public <A> Optional<? extends PointFree<?>> doRewrite(Type<A> var1, Type<?> var2, PointFree<? extends Function<?, ?>> var3, PointFree<? extends Function<?, ?>> var4);

        private static PointFree lambda$rewrite$1(Comp comp, PointFree pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp2 = (Comp)pointFree;
                return CompRewrite.buildRightNested(comp, comp2);
            }
            return CompRewrite.buildLeft(pointFree, comp);
        }

        private static PointFree lambda$rewrite$0(Comp comp, PointFree pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp2 = (Comp)pointFree;
                return CompRewrite.buildLeftNested(comp2, comp);
            }
            return CompRewrite.buildRight(comp, pointFree);
        }
    }

    public static enum AppNest implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Apply) {
                Apply apply = (Apply)pointFree;
                if (apply.arg instanceof Apply) {
                    Apply apply2 = (Apply)apply.arg;
                    return this.cap(apply, apply2);
                }
            }
            return Optional.empty();
        }

        private <A, B, C, D, E> Optional<? extends PointFree<A>> cap(Apply<D, E> apply, Apply<B, C> apply2) {
            PointFree pointFree = apply2.func;
            return Optional.of(Functions.app(Functions.comp(apply.argType, apply.func, pointFree), apply2.arg, apply2.argType));
        }
    }

    public static enum LensAppId implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Apply) {
                Apply apply = (Apply)pointFree;
                PointFree pointFree2 = apply.func;
                if (pointFree2 instanceof ProfunctorTransformer && Objects.equals(apply.arg, Functions.id())) {
                    return Optional.of(Functions.id());
                }
            }
            return Optional.empty();
        }
    }

    public static enum CompAssocRight implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp = (Comp)pointFree;
                PointFree pointFree2 = comp.first;
                if (pointFree2 instanceof Comp) {
                    Comp comp2 = (Comp)pointFree2;
                    return CompAssocRight.swap(comp, comp2);
                }
            }
            return Optional.empty();
        }

        private static <A, B, C, D, E> Optional<PointFree<E>> swap(Comp<A, B, D> comp, Comp<?, C, ?> comp2) {
            Comp<?, C, ?> comp3 = comp2;
            return Optional.of(new Comp(comp3.middleType, comp3.first, new Comp(comp.middleType, comp3.second, comp.second)));
        }
    }

    public static enum CompAssocLeft implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            if (pointFree instanceof Comp) {
                Comp comp = (Comp)pointFree;
                PointFree pointFree2 = comp.second;
                if (pointFree2 instanceof Comp) {
                    Comp comp2 = (Comp)pointFree2;
                    return CompAssocLeft.swap(comp2, comp);
                }
            }
            return Optional.empty();
        }

        private static <A, B, C, D, E> Optional<PointFree<E>> swap(Comp<A, B, C> comp, Comp<?, ?, D> comp2) {
            Comp<?, ?, D> comp3 = comp2;
            return Optional.of(new Comp(comp.middleType, new Comp(comp3.middleType, comp3.first, comp.first), comp.second));
        }
    }

    public static enum BangEta implements PointFreeRule
    {
        INSTANCE;


        @Override
        public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            Func func;
            if (pointFree instanceof Bang) {
                return Optional.empty();
            }
            if (type instanceof Func && (func = (Func)type).second() instanceof EmptyPart) {
                return Optional.of(Functions.bang());
            }
            return Optional.empty();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static enum Nop implements PointFreeRule,
    Supplier<PointFreeRule>
    {
        INSTANCE;


        public <A> Optional<PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree) {
            return Optional.of(pointFree);
        }

        @Override
        public PointFreeRule get() {
            return this;
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

