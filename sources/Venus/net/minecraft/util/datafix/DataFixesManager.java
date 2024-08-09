/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.util.datafix.FakeDataFixer;
import net.minecraft.util.datafix.NamespacedSchema;

public class DataFixesManager {
    private static final BiFunction<Integer, Schema, Schema> SCHEMA_FACTORY = Schema::new;
    private static final BiFunction<Integer, Schema, Schema> NAMESPACED_SCHEMA_FACTORY = NamespacedSchema::new;
    private static final DataFixer DATA_FIXER = DataFixesManager.createFixer();

    private static DataFixer createFixer() {
        return new FakeDataFixer();
    }

    public static DataFixer getDataFixer() {
        return DATA_FIXER;
    }

    private static UnaryOperator<String> rename(Map<String, String> map) {
        return arg_0 -> DataFixesManager.lambda$rename$0(map, arg_0);
    }

    private static UnaryOperator<String> rename(String string, String string2) {
        return arg_0 -> DataFixesManager.lambda$rename$1(string, string2, arg_0);
    }

    private static String lambda$rename$1(String string, String string2, String string3) {
        return Objects.equals(string3, string) ? string2 : string3;
    }

    private static String lambda$rename$0(Map map, String string) {
        return map.getOrDefault(string, string);
    }
}

