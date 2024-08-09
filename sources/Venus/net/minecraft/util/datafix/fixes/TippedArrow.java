/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.TypedEntityRenameHelper;

public class TippedArrow
extends TypedEntityRenameHelper {
    public TippedArrow(Schema schema, boolean bl) {
        super("EntityTippedArrowFix", schema, bl);
    }

    @Override
    protected String rename(String string) {
        return Objects.equals(string, "TippedArrow") ? "Arrow" : string;
    }
}

