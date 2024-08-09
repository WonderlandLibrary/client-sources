/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.stream.Stream;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public interface ISeedReader
extends IServerWorld {
    public long getSeed();

    public Stream<? extends StructureStart<?>> func_241827_a(SectionPos var1, Structure<?> var2);
}

