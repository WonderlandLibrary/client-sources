/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.EntityRenameHelper;

public class EntityCatSplitFix
extends EntityRenameHelper {
    public EntityCatSplitFix(Schema schema, boolean bl) {
        super("EntityCatSplitFix", schema, bl);
    }

    @Override
    protected Pair<String, Dynamic<?>> getNewNameAndTag(String string, Dynamic<?> dynamic) {
        if (Objects.equals("minecraft:ocelot", string)) {
            int n = dynamic.get("CatType").asInt(0);
            if (n == 0) {
                String string2 = dynamic.get("Owner").asString("");
                String string3 = dynamic.get("OwnerUUID").asString("");
                if (string2.length() > 0 || string3.length() > 0) {
                    dynamic.set("Trusting", dynamic.createBoolean(false));
                }
            } else if (n > 0 && n < 4) {
                dynamic = dynamic.set("CatType", dynamic.createInt(n));
                dynamic = dynamic.set("OwnerUUID", dynamic.createString(dynamic.get("OwnerUUID").asString("")));
                return Pair.of("minecraft:cat", dynamic);
            }
        }
        return Pair.of(string, dynamic);
    }
}

