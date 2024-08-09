/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class NetherFossilStructures {
    private static final ResourceLocation[] field_236993_a_ = new ResourceLocation[]{new ResourceLocation("nether_fossils/fossil_1"), new ResourceLocation("nether_fossils/fossil_2"), new ResourceLocation("nether_fossils/fossil_3"), new ResourceLocation("nether_fossils/fossil_4"), new ResourceLocation("nether_fossils/fossil_5"), new ResourceLocation("nether_fossils/fossil_6"), new ResourceLocation("nether_fossils/fossil_7"), new ResourceLocation("nether_fossils/fossil_8"), new ResourceLocation("nether_fossils/fossil_9"), new ResourceLocation("nether_fossils/fossil_10"), new ResourceLocation("nether_fossils/fossil_11"), new ResourceLocation("nether_fossils/fossil_12"), new ResourceLocation("nether_fossils/fossil_13"), new ResourceLocation("nether_fossils/fossil_14")};

    public static void func_236994_a_(TemplateManager templateManager, List<StructurePiece> list, Random random2, BlockPos blockPos) {
        Rotation rotation = Rotation.randomRotation(random2);
        list.add(new Piece(templateManager, Util.getRandomObject(field_236993_a_, random2), blockPos, rotation));
    }

    public static class Piece
    extends TemplateStructurePiece {
        private final ResourceLocation field_236995_d_;
        private final Rotation field_236996_e_;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation) {
            super(IStructurePieceType.NETHER_FOSSIL, 0);
            this.field_236995_d_ = resourceLocation;
            this.templatePosition = blockPos;
            this.field_236996_e_ = rotation;
            this.func_236997_a_(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NETHER_FOSSIL, compoundNBT);
            this.field_236995_d_ = new ResourceLocation(compoundNBT.getString("Template"));
            this.field_236996_e_ = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_236997_a_(templateManager);
        }

        private void func_236997_a_(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.field_236995_d_);
            PlacementSettings placementSettings = new PlacementSettings().setRotation(this.field_236996_e_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.field_236995_d_.toString());
            compoundNBT.putString("Rot", this.field_236996_e_.name());
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            mutableBoundingBox.expandTo(this.template.getMutableBoundingBox(this.placeSettings, this.templatePosition));
            return super.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
        }
    }
}

