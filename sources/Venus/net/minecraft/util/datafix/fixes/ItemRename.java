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
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public abstract class ItemRename
extends DataFix {
    private final String name;

    public ItemRename(Schema schema, String string) {
        super(schema, false);
        this.name = string;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<Pair<String, String>> type = DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_());
        if (!Objects.equals(this.getInputSchema().getType(TypeReferences.ITEM_NAME), type)) {
            throw new IllegalStateException("item name type is not what was expected.");
        }
        return this.fixTypeEverywhere(this.name, type, this::lambda$makeRule$1);
    }

    protected abstract String fixItem(String var1);

    public static DataFix create(Schema schema, String string, Function<String, String> function) {
        return new ItemRename(schema, string, function){
            final Function val$p_207476_2_;
            {
                this.val$p_207476_2_ = function;
                super(schema, string);
            }

            @Override
            protected String fixItem(String string) {
                return (String)this.val$p_207476_2_.apply(string);
            }
        };
    }

    private Function lambda$makeRule$1(DynamicOps dynamicOps) {
        return this::lambda$makeRule$0;
    }

    private Pair lambda$makeRule$0(Pair pair) {
        return pair.mapSecond(this::fixItem);
    }
}

