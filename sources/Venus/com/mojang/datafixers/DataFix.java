/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.DataFixerUpper;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.View;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.BitSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataFix {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Schema outputSchema;
    private final boolean changesType;
    @Nullable
    private TypeRewriteRule rule;

    public DataFix(Schema schema, boolean bl) {
        this.outputSchema = schema;
        this.changesType = bl;
    }

    protected <A> TypeRewriteRule fixTypeEverywhere(String string, Type<A> type, Function<DynamicOps<?>, Function<A, A>> function) {
        return this.fixTypeEverywhere(string, type, type, function, new BitSet());
    }

    protected <A, B> TypeRewriteRule convertUnchecked(String string, Type<A> type, Type<B> type2) {
        return this.fixTypeEverywhere(string, type, type2, DataFix::lambda$convertUnchecked$0, new BitSet());
    }

    protected TypeRewriteRule writeAndRead(String string, Type<?> type, Type<?> type2) {
        return this.writeFixAndRead(string, type, type2, Function.identity());
    }

    protected <A, B> TypeRewriteRule writeFixAndRead(String string, Type<A> type, Type<B> type2, Function<Dynamic<?>, Dynamic<?>> function) {
        return this.fixTypeEverywhere(string, type, type2, arg_0 -> DataFix.lambda$writeFixAndRead$2(type, string, type2, function, arg_0));
    }

    protected <A, B> TypeRewriteRule fixTypeEverywhere(String string, Type<A> type, Type<B> type2, Function<DynamicOps<?>, Function<A, B>> function) {
        return this.fixTypeEverywhere(string, type, type2, function, new BitSet());
    }

    protected <A, B> TypeRewriteRule fixTypeEverywhere(String string, Type<A> type, Type<B> type2, Function<DynamicOps<?>, Function<A, B>> function, BitSet bitSet) {
        return this.fixTypeEverywhere(type, RewriteResult.create(View.create(string, type, type2, new NamedFunctionWrapper<A, B>(string, function)), bitSet));
    }

    protected <A> TypeRewriteRule fixTypeEverywhereTyped(String string, Type<A> type, Function<Typed<?>, Typed<?>> function) {
        return this.fixTypeEverywhereTyped(string, type, function, new BitSet());
    }

    protected <A> TypeRewriteRule fixTypeEverywhereTyped(String string, Type<A> type, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
        return this.fixTypeEverywhereTyped(string, type, type, function, bitSet);
    }

    protected <A, B> TypeRewriteRule fixTypeEverywhereTyped(String string, Type<A> type, Type<B> type2, Function<Typed<?>, Typed<?>> function) {
        return this.fixTypeEverywhereTyped(string, type, type2, function, new BitSet());
    }

    protected <A, B> TypeRewriteRule fixTypeEverywhereTyped(String string, Type<A> type, Type<B> type2, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
        return this.fixTypeEverywhere(type, DataFix.checked(string, type, type2, function, bitSet));
    }

    public static <A, B> RewriteResult<A, B> checked(String string, Type<A> type, Type<B> type2, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
        return RewriteResult.create(View.create(string, type, type2, new NamedFunctionWrapper(string, arg_0 -> DataFix.lambda$checked$4(function, type, type2, arg_0))), bitSet);
    }

    protected <A, B> TypeRewriteRule fixTypeEverywhere(Type<A> type, RewriteResult<A, B> rewriteResult) {
        return TypeRewriteRule.checkOnce(TypeRewriteRule.everywhere(TypeRewriteRule.ifSame(type, rewriteResult), DataFixerUpper.OPTIMIZATION_RULE, true, true), this::onFail);
    }

    protected void onFail(Type<?> type) {
        LOGGER.info("Not matched: " + this + " " + type);
    }

    public final int getVersionKey() {
        return this.getOutputSchema().getVersionKey();
    }

    public TypeRewriteRule getRule() {
        if (this.rule == null) {
            this.rule = this.makeRule();
        }
        return this.rule;
    }

    protected abstract TypeRewriteRule makeRule();

    protected Schema getInputSchema() {
        if (this.changesType) {
            return this.outputSchema.getParent();
        }
        return this.getOutputSchema();
    }

    protected Schema getOutputSchema() {
        return this.outputSchema;
    }

    private static Function lambda$checked$4(Function function, Type type, Type type2, DynamicOps dynamicOps) {
        return arg_0 -> DataFix.lambda$null$3(function, type, dynamicOps, type2, arg_0);
    }

    private static Object lambda$null$3(Function function, Type type, DynamicOps dynamicOps, Type type2, Object object) {
        Typed typed = (Typed)function.apply(new Typed<Object>(type, dynamicOps, object));
        if (!type2.equals(typed.type, true, true)) {
            throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type2, typed.type));
        }
        return typed.value;
    }

    private static Function lambda$writeFixAndRead$2(Type type, String string, Type type2, Function function, DynamicOps dynamicOps) {
        return arg_0 -> DataFix.lambda$null$1(type, dynamicOps, string, type2, function, arg_0);
    }

    private static Object lambda$null$1(Type type, DynamicOps dynamicOps, String string, Type type2, Function function, Object object) {
        Optional optional = type.writeDynamic(dynamicOps, object).resultOrPartial(LOGGER::error);
        if (!optional.isPresent()) {
            throw new RuntimeException("Could not write the object in " + string);
        }
        Optional optional2 = type2.readTyped((Dynamic)function.apply(optional.get())).resultOrPartial(LOGGER::error);
        if (!optional2.isPresent()) {
            throw new RuntimeException("Could not read the new object in " + string);
        }
        return optional2.get().getFirst().getValue();
    }

    private static Function lambda$convertUnchecked$0(DynamicOps dynamicOps) {
        return Function.identity();
    }

    private static final class NamedFunctionWrapper<A, B>
    implements Function<DynamicOps<?>, Function<A, B>> {
        private final String name;
        private final Function<DynamicOps<?>, Function<A, B>> delegate;

        public NamedFunctionWrapper(String string, Function<DynamicOps<?>, Function<A, B>> function) {
            this.name = string;
            this.delegate = function;
        }

        @Override
        public Function<A, B> apply(DynamicOps<?> dynamicOps) {
            return this.delegate.apply(dynamicOps);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            NamedFunctionWrapper namedFunctionWrapper = (NamedFunctionWrapper)object;
            return Objects.equals(this.name, namedFunctionWrapper.name);
        }

        public int hashCode() {
            return Objects.hash(this.name);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((DynamicOps)object);
        }
    }
}

