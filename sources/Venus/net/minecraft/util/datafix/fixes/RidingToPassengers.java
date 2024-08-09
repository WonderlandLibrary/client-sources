/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class RidingToPassengers
extends DataFix {
    public RidingToPassengers(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        Schema schema2 = this.getOutputSchema();
        Type<?> type = schema.getTypeRaw(TypeReferences.ENTITY_TYPE);
        Type<?> type2 = schema2.getTypeRaw(TypeReferences.ENTITY_TYPE);
        Type<?> type3 = schema.getTypeRaw(TypeReferences.ENTITY);
        return this.cap(schema, schema2, type, type2, type3);
    }

    private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule cap(Schema schema, Schema schema2, Type<OldEntityTree> type, Type<NewEntityTree> type2, Type<Entity> type3) {
        Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> type4 = DSL.named(TypeReferences.ENTITY_TYPE.typeName(), DSL.and(DSL.optional(DSL.field("Riding", type)), type3));
        Type<Pair<String, Pair<Either<NewEntityTree, Unit>, Entity>>> type5 = DSL.named(TypeReferences.ENTITY_TYPE.typeName(), DSL.and(DSL.optional(DSL.field("Passengers", DSL.list(type2))), type3));
        Type<?> type6 = schema.getType(TypeReferences.ENTITY_TYPE);
        Type<?> type7 = schema2.getType(TypeReferences.ENTITY_TYPE);
        if (!Objects.equals(type6, type4)) {
            throw new IllegalStateException("Old entity type is not what was expected.");
        }
        if (!type7.equals(type5, true, false)) {
            throw new IllegalStateException("New entity type is not what was expected.");
        }
        OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> opticFinder = DSL.typeFinder(type4);
        OpticFinder<Pair<String, Pair<Either<NewEntityTree, Unit>, Entity>>> opticFinder2 = DSL.typeFinder(type5);
        OpticFinder<NewEntityTree> opticFinder3 = DSL.typeFinder(type2);
        Type<?> type8 = schema.getType(TypeReferences.PLAYER);
        Type<?> type9 = schema2.getType(TypeReferences.PLAYER);
        return TypeRewriteRule.seq(this.fixTypeEverywhere("EntityRidingToPassengerFix", type4, type5, arg_0 -> RidingToPassengers.lambda$cap$6(type2, opticFinder2, opticFinder3, type, opticFinder, arg_0)), this.writeAndRead("player RootVehicle injecter", type8, type9));
    }

    private static Function lambda$cap$6(Type type, OpticFinder opticFinder, OpticFinder opticFinder2, Type type2, OpticFinder opticFinder3, DynamicOps dynamicOps) {
        return arg_0 -> RidingToPassengers.lambda$cap$5(type, dynamicOps, opticFinder, opticFinder2, type2, opticFinder3, arg_0);
    }

    private static Pair lambda$cap$5(Type type, DynamicOps dynamicOps, OpticFinder opticFinder, OpticFinder opticFinder2, Type type2, OpticFinder opticFinder3, Pair pair) {
        Optional<Object> optional = Optional.empty();
        Pair pair2 = pair;
        while (true) {
            Either either = DataFixUtils.orElse(optional.map(arg_0 -> RidingToPassengers.lambda$cap$2(type, dynamicOps, opticFinder, opticFinder2, arg_0)), Either.right(DSL.unit()));
            optional = Optional.of(Pair.of(TypeReferences.ENTITY_TYPE.typeName(), Pair.of(either, ((Pair)pair2.getSecond()).getSecond())));
            Optional optional2 = ((Either)((Pair)pair2.getSecond()).getFirst()).left();
            if (!optional2.isPresent()) {
                return (Pair)optional.orElseThrow(RidingToPassengers::lambda$cap$3);
            }
            pair2 = (Pair)new Typed(type2, dynamicOps, optional2.get()).getOptional(opticFinder3).orElseThrow(RidingToPassengers::lambda$cap$4);
        }
    }

    private static IllegalStateException lambda$cap$4() {
        return new IllegalStateException("Should always have an entity here");
    }

    private static IllegalStateException lambda$cap$3() {
        return new IllegalStateException("Should always have an entity tree here");
    }

    private static Either lambda$cap$2(Type type, DynamicOps dynamicOps, OpticFinder opticFinder, OpticFinder opticFinder2, Pair pair) {
        Typed typed = type.pointTyped(dynamicOps).orElseThrow(RidingToPassengers::lambda$cap$0);
        Object FT = typed.set(opticFinder, pair).getOptional(opticFinder2).orElseThrow(RidingToPassengers::lambda$cap$1);
        return Either.left(ImmutableList.of(FT));
    }

    private static IllegalStateException lambda$cap$1() {
        return new IllegalStateException("Should always have an entity tree here");
    }

    private static IllegalStateException lambda$cap$0() {
        return new IllegalStateException("Could not create new entity tree");
    }
}

