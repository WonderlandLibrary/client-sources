/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public abstract class NamedEntityFix
extends DataFix {
    private final String name;
    private final String entityName;
    private final DSL.TypeReference type;

    public NamedEntityFix(Schema schema, boolean bl, String string, DSL.TypeReference typeReference, String string2) {
        super(schema, bl);
        this.name = string;
        this.type = typeReference;
        this.entityName = string2;
    }

    @Override
    public TypeRewriteRule makeRule() {
        OpticFinder<?> opticFinder = DSL.namedChoice(this.entityName, this.getInputSchema().getChoiceType(this.type, this.entityName));
        return this.fixTypeEverywhereTyped(this.name, this.getInputSchema().getType(this.type), this.getOutputSchema().getType(this.type), arg_0 -> this.lambda$makeRule$0(opticFinder, arg_0));
    }

    protected abstract Typed<?> fix(Typed<?> var1);

    private Typed lambda$makeRule$0(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, this.getOutputSchema().getChoiceType(this.type, this.entityName), this::fix);
    }
}

