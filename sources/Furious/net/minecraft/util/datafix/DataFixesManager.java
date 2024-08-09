package net.minecraft.util.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class DataFixesManager
{
    private static final BiFunction<Integer, Schema, Schema> SCHEMA_FACTORY = Schema::new;
    private static final BiFunction<Integer, Schema, Schema> NAMESPACED_SCHEMA_FACTORY = NamespacedSchema::new;
    private static final DataFixer DATA_FIXER = createFixer();

    private static DataFixer createFixer()
    {
        return new FakeDataFixer();
    }

    public static DataFixer getDataFixer()
    {
        return DATA_FIXER;
    }

    private static UnaryOperator<String> rename(Map<String, String> renameMap)
    {
        return (name) ->
        {
            return renameMap.getOrDefault(name, name);
        };
    }

    private static UnaryOperator<String> rename(String oldName, String newName)
    {
        return (name) ->
        {
            return Objects.equals(name, oldName) ? newName : name;
        };
    }
}
