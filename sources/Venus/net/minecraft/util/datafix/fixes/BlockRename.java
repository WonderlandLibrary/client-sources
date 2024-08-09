/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public abstract class BlockRename
extends DataFix {
    private final String name;

    public BlockRename(Schema schema, String string) {
        super(schema, false);
        this.name = string;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<Pair<String, String>> type;
        Type<?> type2 = this.getInputSchema().getType(TypeReferences.BLOCK_NAME);
        if (!Objects.equals(type2, type = DSL.named(TypeReferences.BLOCK_NAME.typeName(), NamespacedSchema.func_233457_a_()))) {
            throw new IllegalStateException("block type is not what was expected.");
        }
        TypeRewriteRule typeRewriteRule = this.fixTypeEverywhere(this.name + " for block", type, this::lambda$makeRule$1);
        TypeRewriteRule typeRewriteRule2 = this.fixTypeEverywhereTyped(this.name + " for block_state", this.getInputSchema().getType(TypeReferences.BLOCK_STATE), this::lambda$makeRule$3);
        return TypeRewriteRule.seq(typeRewriteRule, typeRewriteRule2);
    }

    protected abstract String fixBlock(String var1);

    public static DataFix create(Schema schema, String string, Function<String, String> function) {
        return new BlockRename(schema, string, function){
            final Function val$p_207437_2_;
            {
                this.val$p_207437_2_ = function;
                super(schema, string);
            }

            @Override
            protected String fixBlock(String string) {
                return (String)this.val$p_207437_2_.apply(string);
            }
        };
    }

    private Typed lambda$makeRule$3(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$2);
    }

    private Dynamic lambda$makeRule$2(Dynamic dynamic) {
        Optional<String> optional = dynamic.get("Name").asString().result();
        return optional.isPresent() ? dynamic.set("Name", dynamic.createString(this.fixBlock(optional.get()))) : dynamic;
    }

    private Function lambda$makeRule$1(DynamicOps dynamicOps) {
        return this::lambda$makeRule$0;
    }

    private Pair lambda$makeRule$0(Pair pair) {
        return pair.mapSecond(this::fixBlock);
    }
}

