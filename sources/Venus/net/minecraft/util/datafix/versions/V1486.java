/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;

public class V1486
extends NamespacedSchema {
    public V1486(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        map.put("minecraft:cod", map.remove("minecraft:cod_mob"));
        map.put("minecraft:salmon", map.remove("minecraft:salmon_mob"));
        return map;
    }
}

