/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.EntityRenameHelper;

public class ElderGuardianSplit
extends EntityRenameHelper {
    public ElderGuardianSplit(Schema schema, boolean bl) {
        super("EntityElderGuardianSplitFix", schema, bl);
    }

    @Override
    protected Pair<String, Dynamic<?>> getNewNameAndTag(String string, Dynamic<?> dynamic) {
        return Pair.of(Objects.equals(string, "Guardian") && dynamic.get("Elder").asBoolean(true) ? "ElderGuardian" : string, dynamic);
    }
}

