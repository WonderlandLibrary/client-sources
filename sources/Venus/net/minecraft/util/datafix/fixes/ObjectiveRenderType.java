/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.datafix.TypeReferences;

public class ObjectiveRenderType
extends DataFix {
    public ObjectiveRenderType(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private static ScoreCriteria.RenderType getRenderType(String string) {
        return string.equals("health") ? ScoreCriteria.RenderType.HEARTS : ScoreCriteria.RenderType.INTEGER;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> type = DSL.named(TypeReferences.OBJECTIVE.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.OBJECTIVE))) {
            throw new IllegalStateException("Objective type is not what was expected.");
        }
        return this.fixTypeEverywhere("ObjectiveRenderTypeFix", type, ObjectiveRenderType::lambda$makeRule$2);
    }

    private static Function lambda$makeRule$2(DynamicOps dynamicOps) {
        return ObjectiveRenderType::lambda$makeRule$1;
    }

    private static Pair lambda$makeRule$1(Pair pair) {
        return pair.mapSecond(ObjectiveRenderType::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        Optional<String> optional = dynamic.get("RenderType").asString().result();
        if (!optional.isPresent()) {
            String string = dynamic.get("CriteriaName").asString("");
            ScoreCriteria.RenderType renderType = ObjectiveRenderType.getRenderType(string);
            return dynamic.set("RenderType", dynamic.createString(renderType.getId()));
        }
        return dynamic;
    }
}

