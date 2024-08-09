/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.EntityRenameHelper;

public class SkeletonSplit
extends EntityRenameHelper {
    public SkeletonSplit(Schema schema, boolean bl) {
        super("EntitySkeletonSplitFix", schema, bl);
    }

    @Override
    protected Pair<String, Dynamic<?>> getNewNameAndTag(String string, Dynamic<?> dynamic) {
        if (Objects.equals(string, "Skeleton")) {
            int n = dynamic.get("SkeletonType").asInt(0);
            if (n == 1) {
                string = "WitherSkeleton";
            } else if (n == 2) {
                string = "Stray";
            }
        }
        return Pair.of(string, dynamic);
    }
}

