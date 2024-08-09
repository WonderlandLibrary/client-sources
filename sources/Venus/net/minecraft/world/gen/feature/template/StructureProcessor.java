/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

public abstract class StructureProcessor {
    @Nullable
    public abstract Template.BlockInfo func_230386_a_(IWorldReader var1, BlockPos var2, BlockPos var3, Template.BlockInfo var4, Template.BlockInfo var5, PlacementSettings var6);

    protected abstract IStructureProcessorType<?> getType();
}

