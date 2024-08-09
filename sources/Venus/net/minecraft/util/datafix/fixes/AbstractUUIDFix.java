/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractUUIDFix
extends DataFix {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected DSL.TypeReference reference;

    public AbstractUUIDFix(Schema schema, DSL.TypeReference typeReference) {
        super(schema, false);
        this.reference = typeReference;
    }

    protected Typed<?> func_233053_a_(Typed<?> typed, String string, Function<Dynamic<?>, Dynamic<?>> function) {
        Type<?> type = this.getInputSchema().getChoiceType(this.reference, string);
        Type<?> type2 = this.getOutputSchema().getChoiceType(this.reference, string);
        return typed.updateTyped(DSL.namedChoice(string, type), type2, arg_0 -> AbstractUUIDFix.lambda$func_233053_a_$0(function, arg_0));
    }

    protected static Optional<Dynamic<?>> func_233058_a_(Dynamic<?> dynamic, String string, String string2) {
        return AbstractUUIDFix.func_233057_a_(dynamic, string).map(arg_0 -> AbstractUUIDFix.lambda$func_233058_a_$1(dynamic, string, string2, arg_0));
    }

    protected static Optional<Dynamic<?>> func_233062_b_(Dynamic<?> dynamic, String string, String string2) {
        return dynamic.get(string).result().flatMap(AbstractUUIDFix::func_233054_a_).map(arg_0 -> AbstractUUIDFix.lambda$func_233062_b_$2(dynamic, string, string2, arg_0));
    }

    protected static Optional<Dynamic<?>> func_233064_c_(Dynamic<?> dynamic, String string, String string2) {
        String string3 = string + "Most";
        String string4 = string + "Least";
        return AbstractUUIDFix.func_233065_d_(dynamic, string3, string4).map(arg_0 -> AbstractUUIDFix.lambda$func_233064_c_$3(dynamic, string3, string4, string2, arg_0));
    }

    protected static Optional<Dynamic<?>> func_233057_a_(Dynamic<?> dynamic, String string) {
        return dynamic.get(string).result().flatMap(arg_0 -> AbstractUUIDFix.lambda$func_233057_a_$4(dynamic, arg_0));
    }

    protected static Optional<Dynamic<?>> func_233054_a_(Dynamic<?> dynamic) {
        return AbstractUUIDFix.func_233065_d_(dynamic, "M", "L");
    }

    protected static Optional<Dynamic<?>> func_233065_d_(Dynamic<?> dynamic, String string, String string2) {
        long l = dynamic.get(string).asLong(0L);
        long l2 = dynamic.get(string2).asLong(0L);
        return l != 0L && l2 != 0L ? AbstractUUIDFix.func_233055_a_(dynamic, l, l2) : Optional.empty();
    }

    protected static Optional<Dynamic<?>> func_233055_a_(Dynamic<?> dynamic, long l, long l2) {
        return Optional.of(dynamic.createIntList(Arrays.stream(new int[]{(int)(l >> 32), (int)l, (int)(l2 >> 32), (int)l2})));
    }

    private static Optional lambda$func_233057_a_$4(Dynamic dynamic, Dynamic dynamic2) {
        String string = dynamic2.asString(null);
        if (string != null) {
            try {
                UUID uUID = UUID.fromString(string);
                return AbstractUUIDFix.func_233055_a_(dynamic, uUID.getMostSignificantBits(), uUID.getLeastSignificantBits());
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return Optional.empty();
    }

    private static Dynamic lambda$func_233064_c_$3(Dynamic dynamic, String string, String string2, String string3, Dynamic dynamic2) {
        return dynamic.remove(string).remove(string2).set(string3, dynamic2);
    }

    private static Dynamic lambda$func_233062_b_$2(Dynamic dynamic, String string, String string2, Dynamic dynamic2) {
        return dynamic.remove(string).set(string2, dynamic2);
    }

    private static Dynamic lambda$func_233058_a_$1(Dynamic dynamic, String string, String string2, Dynamic dynamic2) {
        return dynamic.remove(string).set(string2, dynamic2);
    }

    private static Typed lambda$func_233053_a_$0(Function function, Typed typed) {
        return typed.update(DSL.remainderFinder(), function);
    }
}

