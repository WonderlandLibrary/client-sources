/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.fixes.PointOfInterestRename;

public class RenameBeehivePointOfInterest
extends PointOfInterestRename {
    public RenameBeehivePointOfInterest(Schema schema) {
        super(schema, false);
    }

    @Override
    protected String func_225501_a_(String string) {
        return string.equals("minecraft:bee_hive") ? "minecraft:beehive" : string;
    }
}

