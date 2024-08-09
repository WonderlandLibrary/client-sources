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
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ObjectiveDisplayName
extends DataFix {
    public ObjectiveDisplayName(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> type = DSL.named(TypeReferences.OBJECTIVE.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.OBJECTIVE))) {
            throw new IllegalStateException("Objective type is not what was expected.");
        }
        return this.fixTypeEverywhere("ObjectiveDisplayNameFix", type, ObjectiveDisplayName::lambda$makeRule$4);
    }

    private static Function lambda$makeRule$4(DynamicOps dynamicOps) {
        return ObjectiveDisplayName::lambda$makeRule$3;
    }

    private static Pair lambda$makeRule$3(Pair pair) {
        return pair.mapSecond(ObjectiveDisplayName::lambda$makeRule$2);
    }

    private static Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return dynamic.update("DisplayName", arg_0 -> ObjectiveDisplayName.lambda$makeRule$1(dynamic, arg_0));
    }

    private static Dynamic lambda$makeRule$1(Dynamic dynamic, Dynamic dynamic2) {
        return DataFixUtils.orElse(dynamic2.asString().map(ObjectiveDisplayName::lambda$makeRule$0).map(dynamic::createString).result(), dynamic2);
    }

    private static String lambda$makeRule$0(String string) {
        return ITextComponent.Serializer.toJson(new StringTextComponent(string));
    }
}

