/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

public class GravityStructureProcessor
extends StructureProcessor {
    public static final Codec<GravityStructureProcessor> field_237081_a_ = RecordCodecBuilder.create(GravityStructureProcessor::lambda$static$2);
    private final Heightmap.Type heightmap;
    private final int offset;

    public GravityStructureProcessor(Heightmap.Type type, int n) {
        this.heightmap = type;
        this.offset = n;
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        Heightmap.Type type = iWorldReader instanceof ServerWorld ? (this.heightmap == Heightmap.Type.WORLD_SURFACE_WG ? Heightmap.Type.WORLD_SURFACE : (this.heightmap == Heightmap.Type.OCEAN_FLOOR_WG ? Heightmap.Type.OCEAN_FLOOR : this.heightmap)) : this.heightmap;
        int n = iWorldReader.getHeight(type, blockInfo2.pos.getX(), blockInfo2.pos.getZ()) + this.offset;
        int n2 = blockInfo.pos.getY();
        return new Template.BlockInfo(new BlockPos(blockInfo2.pos.getX(), n + n2, blockInfo2.pos.getZ()), blockInfo2.state, blockInfo2.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.GRAVITY;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Heightmap.Type.field_236078_g_.fieldOf("heightmap")).orElse(Heightmap.Type.WORLD_SURFACE_WG).forGetter(GravityStructureProcessor::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("offset")).orElse(0).forGetter(GravityStructureProcessor::lambda$static$1)).apply(instance, GravityStructureProcessor::new);
    }

    private static Integer lambda$static$1(GravityStructureProcessor gravityStructureProcessor) {
        return gravityStructureProcessor.offset;
    }

    private static Heightmap.Type lambda$static$0(GravityStructureProcessor gravityStructureProcessor) {
        return gravityStructureProcessor.heightmap;
    }
}

