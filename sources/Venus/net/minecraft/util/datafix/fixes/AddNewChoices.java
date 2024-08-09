/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;

public class AddNewChoices
extends DataFix {
    private final String name;
    private final DSL.TypeReference type;

    public AddNewChoices(Schema schema, String string, DSL.TypeReference typeReference) {
        super(schema, true);
        this.name = string;
        this.type = typeReference;
    }

    @Override
    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(this.type);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType2 = this.getOutputSchema().findChoiceType(this.type);
        return this.cap(this.name, taggedChoiceType, taggedChoiceType2);
    }

    protected final <K> TypeRewriteRule cap(String string, TaggedChoice.TaggedChoiceType<K> taggedChoiceType, TaggedChoice.TaggedChoiceType<?> taggedChoiceType2) {
        if (taggedChoiceType.getKeyType() != taggedChoiceType2.getKeyType()) {
            throw new IllegalStateException("Could not inject: key type is not the same");
        }
        return this.fixTypeEverywhere(string, taggedChoiceType, taggedChoiceType2, arg_0 -> this.lambda$cap$1(taggedChoiceType2, arg_0));
    }

    private Function lambda$cap$1(TaggedChoice.TaggedChoiceType taggedChoiceType, DynamicOps dynamicOps) {
        return arg_0 -> this.lambda$cap$0(taggedChoiceType, arg_0);
    }

    private Pair lambda$cap$0(TaggedChoice.TaggedChoiceType taggedChoiceType, Pair pair) {
        if (!taggedChoiceType.hasType(pair.getFirst())) {
            throw new IllegalArgumentException(String.format("Unknown type %s in %s ", pair.getFirst(), this.type));
        }
        return pair;
    }
}

