/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface TypeRewriteRule {
    public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> var1);

    public static TypeRewriteRule nop() {
        return Nop.INSTANCE;
    }

    public static TypeRewriteRule seq(List<TypeRewriteRule> list) {
        return new Seq(list);
    }

    public static TypeRewriteRule seq(TypeRewriteRule typeRewriteRule, TypeRewriteRule typeRewriteRule2) {
        if (Objects.equals(typeRewriteRule, TypeRewriteRule.nop())) {
            return typeRewriteRule2;
        }
        if (Objects.equals(typeRewriteRule2, TypeRewriteRule.nop())) {
            return typeRewriteRule;
        }
        return TypeRewriteRule.seq(ImmutableList.of(typeRewriteRule, typeRewriteRule2));
    }

    public static TypeRewriteRule seq(TypeRewriteRule typeRewriteRule, TypeRewriteRule ... typeRewriteRuleArray) {
        if (typeRewriteRuleArray.length == 0) {
            return typeRewriteRule;
        }
        int n = typeRewriteRuleArray.length - 1;
        TypeRewriteRule typeRewriteRule2 = typeRewriteRuleArray[n];
        while (n > 0) {
            typeRewriteRule2 = TypeRewriteRule.seq(typeRewriteRuleArray[--n], typeRewriteRule2);
        }
        return TypeRewriteRule.seq(typeRewriteRule, typeRewriteRule2);
    }

    public static TypeRewriteRule orElse(TypeRewriteRule typeRewriteRule, TypeRewriteRule typeRewriteRule2) {
        return TypeRewriteRule.orElse(typeRewriteRule, () -> TypeRewriteRule.lambda$orElse$0(typeRewriteRule2));
    }

    public static TypeRewriteRule orElse(TypeRewriteRule typeRewriteRule, Supplier<TypeRewriteRule> supplier) {
        return new OrElse(typeRewriteRule, supplier);
    }

    public static TypeRewriteRule all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
        return new All(typeRewriteRule, bl, bl2);
    }

    public static TypeRewriteRule one(TypeRewriteRule typeRewriteRule) {
        return new One(typeRewriteRule);
    }

    public static TypeRewriteRule once(TypeRewriteRule typeRewriteRule) {
        return TypeRewriteRule.orElse(typeRewriteRule, () -> TypeRewriteRule.lambda$once$1(typeRewriteRule));
    }

    public static TypeRewriteRule checkOnce(TypeRewriteRule typeRewriteRule, Consumer<Type<?>> consumer) {
        return typeRewriteRule;
    }

    public static TypeRewriteRule everywhere(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, boolean bl2) {
        return new Everywhere(typeRewriteRule, pointFreeRule, bl, bl2);
    }

    public static <B> TypeRewriteRule ifSame(Type<B> type, RewriteResult<B, ?> rewriteResult) {
        return new IfSame<B>(type, rewriteResult);
    }

    private static TypeRewriteRule lambda$once$1(TypeRewriteRule typeRewriteRule) {
        return TypeRewriteRule.one(TypeRewriteRule.once(typeRewriteRule));
    }

    private static TypeRewriteRule lambda$orElse$0(TypeRewriteRule typeRewriteRule) {
        return typeRewriteRule;
    }

    public static class IfSame<B>
    implements TypeRewriteRule {
        private final Type<B> targetType;
        private final RewriteResult<B, ?> value;
        private final int hashCode;

        public IfSame(Type<B> type, RewriteResult<B, ?> rewriteResult) {
            this.targetType = type;
            this.value = rewriteResult;
            this.hashCode = Objects.hash(type, rewriteResult);
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            return type.ifSame(this.targetType, this.value);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof IfSame)) {
                return true;
            }
            IfSame ifSame = (IfSame)object;
            return Objects.equals(this.targetType, ifSame.targetType) && Objects.equals(this.value, ifSame.value);
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    public static class Everywhere
    implements TypeRewriteRule {
        protected final TypeRewriteRule rule;
        protected final PointFreeRule optimizationRule;
        protected final boolean recurse;
        private final boolean checkIndex;
        private final int hashCode;

        public Everywhere(TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, boolean bl2) {
            this.rule = typeRewriteRule;
            this.optimizationRule = pointFreeRule;
            this.recurse = bl;
            this.checkIndex = bl2;
            this.hashCode = Objects.hash(typeRewriteRule, pointFreeRule, bl, bl2);
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            return type.everywhere(this.rule, this.optimizationRule, this.recurse, this.checkIndex);
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Everywhere)) {
                return true;
            }
            Everywhere everywhere = (Everywhere)object;
            return Objects.equals(this.rule, everywhere.rule) && Objects.equals(this.optimizationRule, everywhere.optimizationRule) && this.recurse == everywhere.recurse && this.checkIndex == everywhere.checkIndex;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    public static class CheckOnce
    implements TypeRewriteRule {
        private final TypeRewriteRule rule;
        private final Consumer<Type<?>> onFail;

        public CheckOnce(TypeRewriteRule typeRewriteRule, Consumer<Type<?>> consumer) {
            this.rule = typeRewriteRule;
            this.onFail = consumer;
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            Optional<RewriteResult<A, ?>> optional = this.rule.rewrite(type);
            if (!optional.isPresent() || Objects.equals(optional.get().view.function(), Functions.id())) {
                this.onFail.accept(type);
            }
            return optional;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            return object instanceof CheckOnce && Objects.equals(this.rule, ((CheckOnce)object).rule);
        }

        public int hashCode() {
            return Objects.hash(this.rule);
        }
    }

    public static class One
    implements TypeRewriteRule {
        private final TypeRewriteRule rule;

        public One(TypeRewriteRule typeRewriteRule) {
            this.rule = typeRewriteRule;
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            return type.one(this.rule);
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

    public static class All
    implements TypeRewriteRule {
        private final TypeRewriteRule rule;
        private final boolean recurse;
        private final boolean checkIndex;
        private final int hashCode;

        public All(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            this.rule = typeRewriteRule;
            this.recurse = bl;
            this.checkIndex = bl2;
            this.hashCode = Objects.hash(typeRewriteRule, bl, bl2);
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            return Optional.of(type.all(this.rule, this.recurse, this.checkIndex));
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof All)) {
                return true;
            }
            All all = (All)object;
            return Objects.equals(this.rule, all.rule) && this.recurse == all.recurse && this.checkIndex == all.checkIndex;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    public static final class OrElse
    implements TypeRewriteRule {
        protected final TypeRewriteRule first;
        protected final Supplier<TypeRewriteRule> second;
        private final int hashCode;

        public OrElse(TypeRewriteRule typeRewriteRule, Supplier<TypeRewriteRule> supplier) {
            this.first = typeRewriteRule;
            this.second = supplier;
            this.hashCode = Objects.hash(typeRewriteRule, supplier);
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            Optional<RewriteResult<A, ?>> optional = this.first.rewrite(type);
            if (optional.isPresent()) {
                return optional;
            }
            return this.second.get().rewrite(type);
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
            return this.hashCode;
        }
    }

    public static final class Seq
    implements TypeRewriteRule {
        protected final List<TypeRewriteRule> rules;
        private final int hashCode;

        public Seq(List<TypeRewriteRule> list) {
            this.rules = ImmutableList.copyOf(list);
            this.hashCode = this.rules.hashCode();
        }

        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            RewriteResult<A, Object> rewriteResult = RewriteResult.nop(type);
            for (TypeRewriteRule typeRewriteRule : this.rules) {
                Optional<RewriteResult<A, ?>> optional = this.cap1(typeRewriteRule, rewriteResult);
                if (!optional.isPresent()) {
                    return Optional.empty();
                }
                rewriteResult = optional.get();
            }
            return Optional.of(rewriteResult);
        }

        protected <A, B> Optional<RewriteResult<A, ?>> cap1(TypeRewriteRule typeRewriteRule, RewriteResult<A, B> rewriteResult) {
            return typeRewriteRule.rewrite(rewriteResult.view.newType).map(arg_0 -> Seq.lambda$cap1$0(rewriteResult, arg_0));
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
            return this.hashCode;
        }

        private static RewriteResult lambda$cap1$0(RewriteResult rewriteResult, RewriteResult rewriteResult2) {
            return rewriteResult2.compose(rewriteResult);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static enum Nop implements TypeRewriteRule,
    Supplier<TypeRewriteRule>
    {
        INSTANCE;


        @Override
        public <A> Optional<RewriteResult<A, ?>> rewrite(Type<A> type) {
            return Optional.of(RewriteResult.nop(type));
        }

        @Override
        public TypeRewriteRule get() {
            return this;
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

