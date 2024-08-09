/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.datafixers.DataFixUtils;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IStructureReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

public class StructureManager {
    private final IWorld field_235003_a_;
    private final DimensionGeneratorSettings field_235004_b_;

    public StructureManager(IWorld iWorld, DimensionGeneratorSettings dimensionGeneratorSettings) {
        this.field_235003_a_ = iWorld;
        this.field_235004_b_ = dimensionGeneratorSettings;
    }

    public StructureManager func_241464_a_(WorldGenRegion worldGenRegion) {
        if (worldGenRegion.getWorld() != this.field_235003_a_) {
            throw new IllegalStateException("Using invalid feature manager (source level: " + worldGenRegion.getWorld() + ", region: " + worldGenRegion);
        }
        return new StructureManager(worldGenRegion, this.field_235004_b_);
    }

    public Stream<? extends StructureStart<?>> func_235011_a_(SectionPos sectionPos, Structure<?> structure) {
        return this.field_235003_a_.getChunk(sectionPos.getSectionX(), sectionPos.getSectionZ(), ChunkStatus.STRUCTURE_REFERENCES).func_230346_b_(structure).stream().map(StructureManager::lambda$func_235011_a_$0).map(arg_0 -> this.lambda$func_235011_a_$1(structure, arg_0)).filter(StructureManager::lambda$func_235011_a_$2);
    }

    @Nullable
    public StructureStart<?> func_235013_a_(SectionPos sectionPos, Structure<?> structure, IStructureReader iStructureReader) {
        return iStructureReader.func_230342_a_(structure);
    }

    public void func_235014_a_(SectionPos sectionPos, Structure<?> structure, StructureStart<?> structureStart, IStructureReader iStructureReader) {
        iStructureReader.func_230344_a_(structure, structureStart);
    }

    public void func_235012_a_(SectionPos sectionPos, Structure<?> structure, long l, IStructureReader iStructureReader) {
        iStructureReader.func_230343_a_(structure, l);
    }

    public boolean func_235005_a_() {
        return this.field_235004_b_.doesGenerateFeatures();
    }

    public StructureStart<?> func_235010_a_(BlockPos blockPos, boolean bl, Structure<?> structure) {
        return DataFixUtils.orElse(this.func_235011_a_(SectionPos.from(blockPos), structure).filter(arg_0 -> StructureManager.lambda$func_235010_a_$3(blockPos, arg_0)).filter(arg_0 -> StructureManager.lambda$func_235010_a_$5(bl, blockPos, arg_0)).findFirst(), StructureStart.DUMMY);
    }

    private static boolean lambda$func_235010_a_$5(boolean bl, BlockPos blockPos, StructureStart structureStart) {
        return !bl || structureStart.getComponents().stream().anyMatch(arg_0 -> StructureManager.lambda$func_235010_a_$4(blockPos, arg_0));
    }

    private static boolean lambda$func_235010_a_$4(BlockPos blockPos, StructurePiece structurePiece) {
        return structurePiece.getBoundingBox().isVecInside(blockPos);
    }

    private static boolean lambda$func_235010_a_$3(BlockPos blockPos, StructureStart structureStart) {
        return structureStart.getBoundingBox().isVecInside(blockPos);
    }

    private static boolean lambda$func_235011_a_$2(StructureStart structureStart) {
        return structureStart != null && structureStart.isValid();
    }

    private StructureStart lambda$func_235011_a_$1(Structure structure, SectionPos sectionPos) {
        return this.func_235013_a_(sectionPos, structure, this.field_235003_a_.getChunk(sectionPos.getSectionX(), sectionPos.getSectionZ(), ChunkStatus.STRUCTURE_STARTS));
    }

    private static SectionPos lambda$func_235011_a_$0(Long l) {
        return SectionPos.from(new ChunkPos(l), 0);
    }
}

