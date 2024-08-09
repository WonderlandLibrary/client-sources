/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class PlacementSettings {
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private BlockPos centerOffset = BlockPos.ZERO;
    private boolean ignoreEntities;
    @Nullable
    private ChunkPos chunk;
    @Nullable
    private MutableBoundingBox boundingBox;
    private boolean field_204765_h = true;
    @Nullable
    private Random random;
    private int field_204767_m;
    private final List<StructureProcessor> processors = Lists.newArrayList();
    private boolean field_215225_l;
    private boolean field_237131_l_;

    public PlacementSettings copy() {
        PlacementSettings placementSettings = new PlacementSettings();
        placementSettings.mirror = this.mirror;
        placementSettings.rotation = this.rotation;
        placementSettings.centerOffset = this.centerOffset;
        placementSettings.ignoreEntities = this.ignoreEntities;
        placementSettings.chunk = this.chunk;
        placementSettings.boundingBox = this.boundingBox;
        placementSettings.field_204765_h = this.field_204765_h;
        placementSettings.random = this.random;
        placementSettings.field_204767_m = this.field_204767_m;
        placementSettings.processors.addAll(this.processors);
        placementSettings.field_215225_l = this.field_215225_l;
        placementSettings.field_237131_l_ = this.field_237131_l_;
        return placementSettings;
    }

    public PlacementSettings setMirror(Mirror mirror) {
        this.mirror = mirror;
        return this;
    }

    public PlacementSettings setRotation(Rotation rotation) {
        this.rotation = rotation;
        return this;
    }

    public PlacementSettings setCenterOffset(BlockPos blockPos) {
        this.centerOffset = blockPos;
        return this;
    }

    public PlacementSettings setIgnoreEntities(boolean bl) {
        this.ignoreEntities = bl;
        return this;
    }

    public PlacementSettings setChunk(ChunkPos chunkPos) {
        this.chunk = chunkPos;
        return this;
    }

    public PlacementSettings setBoundingBox(MutableBoundingBox mutableBoundingBox) {
        this.boundingBox = mutableBoundingBox;
        return this;
    }

    public PlacementSettings setRandom(@Nullable Random random2) {
        this.random = random2;
        return this;
    }

    public PlacementSettings func_215223_c(boolean bl) {
        this.field_215225_l = bl;
        return this;
    }

    public PlacementSettings clearProcessors() {
        this.processors.clear();
        return this;
    }

    public PlacementSettings addProcessor(StructureProcessor structureProcessor) {
        this.processors.add(structureProcessor);
        return this;
    }

    public PlacementSettings removeProcessor(StructureProcessor structureProcessor) {
        this.processors.remove(structureProcessor);
        return this;
    }

    public Mirror getMirror() {
        return this.mirror;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public BlockPos getCenterOffset() {
        return this.centerOffset;
    }

    public Random getRandom(@Nullable BlockPos blockPos) {
        if (this.random != null) {
            return this.random;
        }
        return blockPos == null ? new Random(Util.milliTime()) : new Random(MathHelper.getPositionRandom(blockPos));
    }

    public boolean getIgnoreEntities() {
        return this.ignoreEntities;
    }

    @Nullable
    public MutableBoundingBox getBoundingBox() {
        if (this.boundingBox == null && this.chunk != null) {
            this.setBoundingBoxFromChunk();
        }
        return this.boundingBox;
    }

    public boolean func_215218_i() {
        return this.field_215225_l;
    }

    public List<StructureProcessor> getProcessors() {
        return this.processors;
    }

    void setBoundingBoxFromChunk() {
        if (this.chunk != null) {
            this.boundingBox = this.getBoundingBoxFromChunk(this.chunk);
        }
    }

    public boolean func_204763_l() {
        return this.field_204765_h;
    }

    public Template.Palette func_237132_a_(List<Template.Palette> list, @Nullable BlockPos blockPos) {
        int n = list.size();
        if (n == 0) {
            throw new IllegalStateException("No palettes");
        }
        return list.get(this.getRandom(blockPos).nextInt(n));
    }

    @Nullable
    private MutableBoundingBox getBoundingBoxFromChunk(@Nullable ChunkPos chunkPos) {
        if (chunkPos == null) {
            return this.boundingBox;
        }
        int n = chunkPos.x * 16;
        int n2 = chunkPos.z * 16;
        return new MutableBoundingBox(n, 0, n2, n + 16 - 1, 255, n2 + 16 - 1);
    }

    public PlacementSettings func_237133_d_(boolean bl) {
        this.field_237131_l_ = bl;
        return this;
    }

    public boolean func_237134_m_() {
        return this.field_237131_l_;
    }
}

