/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class EntityArmorAndHeld
extends DataFix {
    public EntityArmorAndHeld(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.cap(this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK));
    }

    private <IS> TypeRewriteRule cap(Type<IS> type) {
        Type<Pair<Either<IS, Unit>, Dynamic<?>>> type2 = DSL.and(DSL.optional(DSL.field("Equipment", DSL.list(type))), DSL.remainderType());
        Type<Pair<Either<IS, Unit>, Pair<Either<IS, Unit>, Dynamic<?>>>> type3 = DSL.and(DSL.optional(DSL.field("ArmorItems", DSL.list(type))), DSL.optional(DSL.field("HandItems", DSL.list(type))), DSL.remainderType());
        OpticFinder<Pair<Either<IS, Unit>, Dynamic<?>>> opticFinder = DSL.typeFinder(type2);
        OpticFinder<IS> opticFinder2 = DSL.fieldFinder("Equipment", DSL.list(type));
        return this.fixTypeEverywhereTyped("EntityEquipmentToArmorAndHandFix", this.getInputSchema().getType(TypeReferences.ENTITY), this.getOutputSchema().getType(TypeReferences.ENTITY), arg_0 -> EntityArmorAndHeld.lambda$cap$2(opticFinder2, type, opticFinder, type3, arg_0));
    }

    private static Typed lambda$cap$2(OpticFinder opticFinder, Type type, OpticFinder opticFinder2, Type type2, Typed typed) {
        Object object;
        Optional<Stream<Dynamic<Object>>> optional;
        Object object2;
        Either<Object, Unit> either = Either.right(DSL.unit());
        Either<Object, Unit> either2 = Either.right(DSL.unit());
        Dynamic dynamic = typed.getOrCreate(DSL.remainderFinder());
        Optional optional2 = typed.getOptional(opticFinder);
        if (optional2.isPresent()) {
            object2 = (List)optional2.get();
            optional = type.read(dynamic.emptyMap()).result().orElseThrow(EntityArmorAndHeld::lambda$cap$0).getFirst();
            if (!object2.isEmpty()) {
                either = Either.left(Lists.newArrayList(object2.get(0), optional));
            }
            if (object2.size() > 1) {
                object = Lists.newArrayList(optional, optional, optional, optional);
                for (int i = 1; i < Math.min(object2.size(), 5); ++i) {
                    object.set(i - 1, object2.get(i));
                }
                either2 = Either.left(object);
            }
        }
        object2 = dynamic;
        optional = dynamic.get("DropChances").asStreamOpt().result();
        if (optional.isPresent()) {
            Dynamic dynamic2;
            object = Stream.concat(optional.get(), Stream.generate(() -> EntityArmorAndHeld.lambda$cap$1((Dynamic)object2))).iterator();
            float f = ((Dynamic)object.next()).asFloat(0.0f);
            if (!dynamic.get("HandDropChances").result().isPresent()) {
                dynamic2 = dynamic.createList(Stream.of(Float.valueOf(f), Float.valueOf(0.0f)).map(dynamic::createFloat));
                dynamic = dynamic.set("HandDropChances", dynamic2);
            }
            if (!dynamic.get("ArmorDropChances").result().isPresent()) {
                dynamic2 = dynamic.createList(Stream.of(Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f))).map(dynamic::createFloat));
                dynamic = dynamic.set("ArmorDropChances", dynamic2);
            }
            dynamic = dynamic.remove("DropChances");
        }
        return typed.set(opticFinder2, type2, Pair.of(either, Pair.of(either2, dynamic)));
    }

    private static Dynamic lambda$cap$1(Dynamic dynamic) {
        return dynamic.createInt(0);
    }

    private static IllegalStateException lambda$cap$0() {
        return new IllegalStateException("Could not parse newly created empty itemstack.");
    }
}

