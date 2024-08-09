/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class VillageStructure
extends JigsawStructure {
    public VillageStructure(Codec<VillageConfig> codec) {
        super(codec, 0, true, true);
    }
}

