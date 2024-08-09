/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public interface IStructureReader {
    @Nullable
    public StructureStart<?> func_230342_a_(Structure<?> var1);

    public void func_230344_a_(Structure<?> var1, StructureStart<?> var2);

    public LongSet func_230346_b_(Structure<?> var1);

    public void func_230343_a_(Structure<?> var1, long var2);

    public Map<Structure<?>, LongSet> getStructureReferences();

    public void setStructureReferences(Map<Structure<?>, LongSet> var1);
}

