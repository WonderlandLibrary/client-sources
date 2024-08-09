/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class MinecartEntityTypes
extends DataFix {
    private static final List<String> MINECART_TYPE_LIST = Lists.newArrayList("MinecartRideable", "MinecartChest", "MinecartFurnace");

    public MinecartEntityTypes(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType2 = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
        return this.fixTypeEverywhere("EntityMinecartIdentifiersFix", taggedChoiceType, taggedChoiceType2, arg_0 -> MinecartEntityTypes.lambda$makeRule$3(taggedChoiceType, taggedChoiceType2, arg_0));
    }

    private static Function lambda$makeRule$3(TaggedChoice.TaggedChoiceType taggedChoiceType, TaggedChoice.TaggedChoiceType taggedChoiceType2, DynamicOps dynamicOps) {
        return arg_0 -> MinecartEntityTypes.lambda$makeRule$2(taggedChoiceType, dynamicOps, taggedChoiceType2, arg_0);
    }

    private static Pair lambda$makeRule$2(TaggedChoice.TaggedChoiceType taggedChoiceType, DynamicOps dynamicOps, TaggedChoice.TaggedChoiceType taggedChoiceType2, Pair pair) {
        if (!Objects.equals(pair.getFirst(), "Minecart")) {
            return pair;
        }
        Typed<Pair<String, ?>> typed = taggedChoiceType.point(dynamicOps, "Minecart", pair.getSecond()).orElseThrow(IllegalStateException::new);
        Dynamic<?> dynamic = typed.getOrCreate(DSL.remainderFinder());
        int n = dynamic.get("Type").asInt(0);
        String string = n > 0 && n < MINECART_TYPE_LIST.size() ? MINECART_TYPE_LIST.get(n) : "MinecartRideable";
        return Pair.of(string, typed.write().map(arg_0 -> MinecartEntityTypes.lambda$makeRule$0(taggedChoiceType2, string, arg_0)).result().orElseThrow(MinecartEntityTypes::lambda$makeRule$1));
    }

    private static IllegalStateException lambda$makeRule$1() {
        return new IllegalStateException("Could not read the new minecart.");
    }

    private static DataResult lambda$makeRule$0(TaggedChoice.TaggedChoiceType taggedChoiceType, String string, Dynamic dynamic) {
        return taggedChoiceType.types().get(string).read(dynamic);
    }
}

