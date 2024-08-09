/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class BannerItemColor
extends DataFix {
    public BannerItemColor(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        OpticFinder<?> opticFinder2 = type.findField("tag");
        OpticFinder<?> opticFinder3 = opticFinder2.type().findField("BlockEntityTag");
        return this.fixTypeEverywhereTyped("ItemBannerColorFix", type, arg_0 -> BannerItemColor.lambda$makeRule$0(opticFinder, opticFinder2, opticFinder3, arg_0));
    }

    private static Typed lambda$makeRule$0(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        Optional optional = typed.getOptional(opticFinder);
        if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:banner")) {
            Typed<Dynamic<?>> typed2;
            Optional optional2;
            Dynamic dynamic = typed.get(DSL.remainderFinder());
            Optional optional3 = typed.getOptionalTyped(opticFinder2);
            if (optional3.isPresent() && (optional2 = (typed2 = optional3.get()).getOptionalTyped(opticFinder3)).isPresent()) {
                Typed<Dynamic<?>> typed3 = optional2.get();
                Dynamic<?> dynamic2 = typed2.get(DSL.remainderFinder());
                Dynamic<?> dynamic3 = typed3.getOrCreate(DSL.remainderFinder());
                if (dynamic3.get("Base").asNumber().result().isPresent()) {
                    Dynamic dynamic4;
                    Dynamic dynamic5;
                    dynamic = dynamic.set("Damage", dynamic.createShort((short)(dynamic3.get("Base").asInt(0) & 0xF)));
                    Optional<Dynamic<?>> optional4 = dynamic2.get("display").result();
                    if (optional4.isPresent() && Objects.equals(dynamic5 = optional4.get(), dynamic4 = dynamic5.createMap(ImmutableMap.of(dynamic5.createString("Lore"), dynamic5.createList(Stream.of(dynamic5.createString("(+NBT"))))))) {
                        return typed.set(DSL.remainderFinder(), dynamic);
                    }
                    dynamic3.remove("Base");
                    return typed.set(DSL.remainderFinder(), dynamic).set(opticFinder2, typed2.set(opticFinder3, typed3.set(DSL.remainderFinder(), dynamic3)));
                }
            }
            return typed.set(DSL.remainderFinder(), dynamic);
        }
        return typed;
    }
}

