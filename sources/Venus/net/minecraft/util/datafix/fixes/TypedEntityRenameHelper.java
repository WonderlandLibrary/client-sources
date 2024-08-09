/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public abstract class TypedEntityRenameHelper
extends DataFix {
    private final String name;

    public TypedEntityRenameHelper(String string, Schema schema, boolean bl) {
        super(schema, bl);
        this.name = string;
    }

    @Override
    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType2 = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
        Type<Pair<String, String>> type = DSL.named(TypeReferences.ENTITY_NAME.typeName(), NamespacedSchema.func_233457_a_());
        if (!Objects.equals(this.getOutputSchema().getType(TypeReferences.ENTITY_NAME), type)) {
            throw new IllegalStateException("Entity name type is not what was expected.");
        }
        return TypeRewriteRule.seq(this.fixTypeEverywhere(this.name, taggedChoiceType, taggedChoiceType2, arg_0 -> this.lambda$makeRule$2(taggedChoiceType, taggedChoiceType2, arg_0)), this.fixTypeEverywhere(this.name + " for entity name", type, this::lambda$makeRule$4));
    }

    protected abstract String rename(String var1);

    private Function lambda$makeRule$4(DynamicOps dynamicOps) {
        return this::lambda$makeRule$3;
    }

    private Pair lambda$makeRule$3(Pair pair) {
        return pair.mapSecond(this::rename);
    }

    private Function lambda$makeRule$2(TaggedChoice.TaggedChoiceType taggedChoiceType, TaggedChoice.TaggedChoiceType taggedChoiceType2, DynamicOps dynamicOps) {
        return arg_0 -> this.lambda$makeRule$1(taggedChoiceType, taggedChoiceType2, arg_0);
    }

    private Pair lambda$makeRule$1(TaggedChoice.TaggedChoiceType taggedChoiceType, TaggedChoice.TaggedChoiceType taggedChoiceType2, Pair pair) {
        return pair.mapFirst(arg_0 -> this.lambda$makeRule$0(taggedChoiceType, taggedChoiceType2, arg_0));
    }

    private String lambda$makeRule$0(TaggedChoice.TaggedChoiceType taggedChoiceType, TaggedChoice.TaggedChoiceType taggedChoiceType2, String string) {
        String string2 = this.rename(string);
        Type<?> type = taggedChoiceType.types().get(string);
        Type<?> type2 = taggedChoiceType2.types().get(string2);
        if (!type2.equals(type, true, false)) {
            throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type2, type));
        }
        return string2;
    }
}

