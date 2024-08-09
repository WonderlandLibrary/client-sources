/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public abstract class PointOfInterestRename
extends DataFix {
    public PointOfInterestRename(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> type = DSL.named(TypeReferences.POI_CHUNK.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere("POI rename", type, this::lambda$makeRule$1);
    }

    private <T> Dynamic<T> func_226201_a_(Dynamic<T> dynamic) {
        return dynamic.update("Sections", this::lambda$func_226201_a_$5);
    }

    private <T> Optional<Dynamic<T>> func_226205_b_(Dynamic<T> dynamic) {
        return dynamic.asStreamOpt().map(arg_0 -> this.lambda$func_226205_b_$8(dynamic, arg_0)).result();
    }

    protected abstract String func_225501_a_(String var1);

    private Dynamic lambda$func_226205_b_$8(Dynamic dynamic, Stream stream) {
        return dynamic.createList(stream.map(this::lambda$func_226205_b_$7));
    }

    private Dynamic lambda$func_226205_b_$7(Dynamic dynamic) {
        return dynamic.update("type", this::lambda$func_226205_b_$6);
    }

    private Dynamic lambda$func_226205_b_$6(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asString().map(this::func_225501_a_).map(dynamic::createString).result(), dynamic);
    }

    private Dynamic lambda$func_226201_a_$5(Dynamic dynamic) {
        return dynamic.updateMapValues(this::lambda$func_226201_a_$4);
    }

    private Pair lambda$func_226201_a_$4(Pair pair) {
        return pair.mapSecond(this::lambda$func_226201_a_$3);
    }

    private Dynamic lambda$func_226201_a_$3(Dynamic dynamic) {
        return dynamic.update("Records", this::lambda$func_226201_a_$2);
    }

    private Dynamic lambda$func_226201_a_$2(Dynamic dynamic) {
        return DataFixUtils.orElse(this.func_226205_b_(dynamic), dynamic);
    }

    private Function lambda$makeRule$1(DynamicOps dynamicOps) {
        return this::lambda$makeRule$0;
    }

    private Pair lambda$makeRule$0(Pair pair) {
        return pair.mapSecond(this::func_226201_a_);
    }
}

