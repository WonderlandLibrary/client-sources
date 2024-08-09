/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class EmptyJigsawPiece
extends JigsawPiece {
    public static final Codec<EmptyJigsawPiece> field_236814_a_;
    public static final EmptyJigsawPiece INSTANCE;

    private EmptyJigsawPiece() {
        super(JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING);
    }

    @Override
    public List<Template.BlockInfo> getJigsawBlocks(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, Random random2) {
        return Collections.emptyList();
    }

    @Override
    public MutableBoundingBox getBoundingBox(TemplateManager templateManager, BlockPos blockPos, Rotation rotation) {
        return MutableBoundingBox.getNewBoundingBox();
    }

    @Override
    public boolean func_230378_a_(TemplateManager templateManager, ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos blockPos2, Rotation rotation, MutableBoundingBox mutableBoundingBox, Random random2, boolean bl) {
        return false;
    }

    @Override
    public IJigsawDeserializer<?> getType() {
        return IJigsawDeserializer.EMPTY_POOL_ELEMENT;
    }

    public String toString() {
        return "Empty";
    }

    private static EmptyJigsawPiece lambda$static$0() {
        return INSTANCE;
    }

    static {
        INSTANCE = new EmptyJigsawPiece();
        field_236814_a_ = Codec.unit(EmptyJigsawPiece::lambda$static$0);
    }
}

