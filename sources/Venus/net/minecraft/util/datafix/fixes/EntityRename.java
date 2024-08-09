/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public abstract class EntityRename
extends DataFix {
    protected final String name;

    public EntityRename(String string, Schema schema, boolean bl) {
        super(schema, bl);
        this.name = string;
    }

    @Override
    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType2 = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
        return this.fixTypeEverywhere(this.name, taggedChoiceType, taggedChoiceType2, arg_0 -> this.lambda$makeRule$1(taggedChoiceType, taggedChoiceType2, arg_0));
    }

    private <A> Typed<A> getEntity(Object object, DynamicOps<?> dynamicOps, Type<A> type) {
        return new Typed<Object>(type, dynamicOps, object);
    }

    protected abstract Pair<String, Typed<?>> fix(String var1, Typed<?> var2);

    private Function lambda$makeRule$1(TaggedChoice.TaggedChoiceType taggedChoiceType, TaggedChoice.TaggedChoiceType taggedChoiceType2, DynamicOps dynamicOps) {
        return arg_0 -> this.lambda$makeRule$0(taggedChoiceType, dynamicOps, taggedChoiceType2, arg_0);
    }

    private Pair lambda$makeRule$0(TaggedChoice.TaggedChoiceType taggedChoiceType, DynamicOps dynamicOps, TaggedChoice.TaggedChoiceType taggedChoiceType2, Pair pair) {
        String string = (String)pair.getFirst();
        Type<?> type = taggedChoiceType.types().get(string);
        Pair<String, Typed<?>> pair2 = this.fix(string, this.getEntity(pair.getSecond(), dynamicOps, type));
        Type<?> type2 = taggedChoiceType2.types().get(pair2.getFirst());
        if (!type2.equals(pair2.getSecond().getType(), true, false)) {
            throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type2, pair2.getSecond().getType()));
        }
        return Pair.of(pair2.getFirst(), pair2.getSecond().getValue());
    }
}

