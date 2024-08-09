/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import net.minecraft.util.datafix.fixes.TypedEntityRenameHelper;

public class EntityCodSalmonFix
extends TypedEntityRenameHelper {
    public static final Map<String, String> field_207460_a = ImmutableMap.builder().put("minecraft:salmon_mob", "minecraft:salmon").put("minecraft:cod_mob", "minecraft:cod").build();
    public static final Map<String, String> field_209759_b = ImmutableMap.builder().put("minecraft:salmon_mob_spawn_egg", "minecraft:salmon_spawn_egg").put("minecraft:cod_mob_spawn_egg", "minecraft:cod_spawn_egg").build();

    public EntityCodSalmonFix(Schema schema, boolean bl) {
        super("EntityCodSalmonFix", schema, bl);
    }

    @Override
    protected String rename(String string) {
        return field_207460_a.getOrDefault(string, string);
    }
}

