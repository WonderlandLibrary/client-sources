/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.WorldGenSettings;

public class MissingDimensionFix
extends DataFix {
    public MissingDimensionFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private static <A> Type<Pair<A, Dynamic<?>>> func_241312_a_(String string, Type<A> type) {
        return DSL.and(DSL.field(string, type), DSL.remainderType());
    }

    private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> func_241314_b_(String string, Type<A> type) {
        return DSL.and(DSL.optional(DSL.field(string, type)), DSL.remainderType());
    }

    private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> func_241313_a_(String string, Type<A1> type, String string2, Type<A2> type2) {
        return DSL.and(DSL.optional(DSL.field(string, type)), DSL.optional(DSL.field(string2, type2)), DSL.remainderType());
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        TaggedChoice.TaggedChoiceType<String> taggedChoiceType = new TaggedChoice.TaggedChoiceType<String>("type", DSL.string(), ImmutableMap.of("minecraft:debug", DSL.remainderType(), "minecraft:flat", MissingDimensionFix.func_241314_b_("settings", MissingDimensionFix.func_241313_a_("biome", schema.getType(TypeReferences.BIOME), "layers", DSL.list(MissingDimensionFix.func_241314_b_("block", schema.getType(TypeReferences.BLOCK_NAME))))), "minecraft:noise", MissingDimensionFix.func_241313_a_("biome_source", DSL.taggedChoiceType("type", DSL.string(), ImmutableMap.of("minecraft:fixed", MissingDimensionFix.func_241312_a_("biome", schema.getType(TypeReferences.BIOME)), "minecraft:multi_noise", DSL.list(MissingDimensionFix.func_241312_a_("biome", schema.getType(TypeReferences.BIOME))), "minecraft:checkerboard", MissingDimensionFix.func_241312_a_("biomes", DSL.list(schema.getType(TypeReferences.BIOME))), "minecraft:vanilla_layered", DSL.remainderType(), "minecraft:the_end", DSL.remainderType())), "settings", DSL.or(DSL.string(), MissingDimensionFix.func_241313_a_("default_block", schema.getType(TypeReferences.BLOCK_NAME), "default_fluid", schema.getType(TypeReferences.BLOCK_NAME))))));
        CompoundList.CompoundListType<String, Pair<String, Dynamic<?>>> compoundListType = DSL.compoundList(NamespacedSchema.func_233457_a_(), MissingDimensionFix.func_241312_a_("generator", taggedChoiceType));
        Type type = DSL.and(compoundListType, DSL.remainderType());
        Type<?> type2 = schema.getType(TypeReferences.WORLD_GEN_SETTINGS);
        FieldFinder fieldFinder = new FieldFinder("dimensions", type);
        if (!type2.findFieldType("dimensions").equals(type)) {
            throw new IllegalStateException();
        }
        OpticFinder opticFinder = compoundListType.finder();
        return this.fixTypeEverywhereTyped("MissingDimensionFix", type2, arg_0 -> this.lambda$makeRule$2(fieldFinder, opticFinder, compoundListType, arg_0));
    }

    private <T> Dynamic<T> func_241311_a_(Dynamic<T> dynamic) {
        long l = dynamic.get("seed").asLong(0L);
        return new Dynamic(dynamic.getOps(), WorldGenSettings.func_241323_a_(dynamic, l, WorldGenSettings.func_241322_a_(dynamic, l), false));
    }

    private Typed lambda$makeRule$2(FieldFinder fieldFinder, OpticFinder opticFinder, CompoundList.CompoundListType compoundListType, Typed typed) {
        return typed.updateTyped(fieldFinder, arg_0 -> this.lambda$makeRule$1(opticFinder, typed, compoundListType, arg_0));
    }

    private Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed, CompoundList.CompoundListType compoundListType, Typed typed2) {
        return typed2.updateTyped(opticFinder, arg_0 -> this.lambda$makeRule$0(typed, compoundListType, arg_0));
    }

    private Typed lambda$makeRule$0(Typed typed, CompoundList.CompoundListType compoundListType, Typed typed2) {
        if (!(typed2.getValue() instanceof List)) {
            throw new IllegalStateException("List exptected");
        }
        if (((List)typed2.getValue()).isEmpty()) {
            Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
            Dynamic<?> dynamic2 = this.func_241311_a_(dynamic);
            return DataFixUtils.orElse(compoundListType.readTyped(dynamic2).result().map(Pair::getFirst), typed2);
        }
        return typed2;
    }
}

