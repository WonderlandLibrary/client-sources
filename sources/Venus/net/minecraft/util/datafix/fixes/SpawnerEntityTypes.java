/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class SpawnerEntityTypes
extends DataFix {
    public SpawnerEntityTypes(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private Dynamic<?> fix(Dynamic<?> dynamic) {
        Dynamic dynamic2;
        if (!"MobSpawner".equals(dynamic.get("id").asString(""))) {
            return dynamic;
        }
        Optional<String> optional = dynamic.get("EntityId").asString().result();
        if (optional.isPresent()) {
            dynamic2 = DataFixUtils.orElse(dynamic.get("SpawnData").result(), dynamic.emptyMap());
            dynamic2 = dynamic2.set("id", dynamic2.createString(optional.get().isEmpty() ? "Pig" : optional.get()));
            dynamic = dynamic.set("SpawnData", dynamic2);
            dynamic = dynamic.remove("EntityId");
        }
        if (((Optional)((Object)(dynamic2 = dynamic.get("SpawnPotentials").asStreamOpt().result()))).isPresent()) {
            dynamic = dynamic.set("SpawnPotentials", dynamic.createList(((Stream)((Optional)((Object)dynamic2)).get()).map(SpawnerEntityTypes::lambda$fix$0)));
        }
        return dynamic;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.UNTAGGED_SPAWNER);
        return this.fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", this.getInputSchema().getType(TypeReferences.UNTAGGED_SPAWNER), type, arg_0 -> this.lambda$makeRule$1(type, arg_0));
    }

    private Typed lambda$makeRule$1(Type type, Typed typed) {
        Dynamic dynamic = typed.get(DSL.remainderFinder());
        DataResult dataResult = type.readTyped(this.fix(dynamic = dynamic.set("id", dynamic.createString("MobSpawner"))));
        return !dataResult.result().isPresent() ? typed : dataResult.result().get().getFirst();
    }

    private static Dynamic lambda$fix$0(Dynamic dynamic) {
        Optional<String> optional = dynamic.get("Type").asString().result();
        if (optional.isPresent()) {
            Dynamic dynamic2 = DataFixUtils.orElse(dynamic.get("Properties").result(), dynamic.emptyMap()).set("id", dynamic.createString(optional.get()));
            return dynamic.set("Entity", dynamic2).remove("Type").remove("Properties");
        }
        return dynamic;
    }
}

