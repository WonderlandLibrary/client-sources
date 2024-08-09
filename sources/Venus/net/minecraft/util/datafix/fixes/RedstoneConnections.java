/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;

public class RedstoneConnections
extends DataFix {
    public RedstoneConnections(Schema schema) {
        super(schema, false);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        return this.fixTypeEverywhereTyped("RedstoneConnectionsFix", schema.getType(TypeReferences.BLOCK_STATE), this::lambda$makeRule$0);
    }

    private <T> Dynamic<T> func_233368_a_(Dynamic<T> dynamic) {
        boolean bl = dynamic.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
        return !bl ? dynamic : dynamic.update("Properties", RedstoneConnections::lambda$func_233368_a_$5);
    }

    private static boolean func_233369_a_(String string) {
        return !"none".equals(string);
    }

    private static Dynamic lambda$func_233368_a_$5(Dynamic dynamic) {
        String string = dynamic.get("east").asString("none");
        String string2 = dynamic.get("west").asString("none");
        String string3 = dynamic.get("north").asString("none");
        String string4 = dynamic.get("south").asString("none");
        boolean bl = RedstoneConnections.func_233369_a_(string) || RedstoneConnections.func_233369_a_(string2);
        boolean bl2 = RedstoneConnections.func_233369_a_(string3) || RedstoneConnections.func_233369_a_(string4);
        String string5 = !RedstoneConnections.func_233369_a_(string) && !bl2 ? "side" : string;
        String string6 = !RedstoneConnections.func_233369_a_(string2) && !bl2 ? "side" : string2;
        String string7 = !RedstoneConnections.func_233369_a_(string3) && !bl ? "side" : string3;
        String string8 = !RedstoneConnections.func_233369_a_(string4) && !bl ? "side" : string4;
        return dynamic.update("east", arg_0 -> RedstoneConnections.lambda$func_233368_a_$1(string5, arg_0)).update("west", arg_0 -> RedstoneConnections.lambda$func_233368_a_$2(string6, arg_0)).update("north", arg_0 -> RedstoneConnections.lambda$func_233368_a_$3(string7, arg_0)).update("south", arg_0 -> RedstoneConnections.lambda$func_233368_a_$4(string8, arg_0));
    }

    private static Dynamic lambda$func_233368_a_$4(String string, Dynamic dynamic) {
        return dynamic.createString(string);
    }

    private static Dynamic lambda$func_233368_a_$3(String string, Dynamic dynamic) {
        return dynamic.createString(string);
    }

    private static Dynamic lambda$func_233368_a_$2(String string, Dynamic dynamic) {
        return dynamic.createString(string);
    }

    private static Dynamic lambda$func_233368_a_$1(String string, Dynamic dynamic) {
        return dynamic.createString(string);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::func_233368_a_);
    }
}

