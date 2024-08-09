/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class IglooPieces {
    private static final ResourceLocation field_202592_e = new ResourceLocation("igloo/top");
    private static final ResourceLocation field_202593_f = new ResourceLocation("igloo/middle");
    private static final ResourceLocation field_202594_g = new ResourceLocation("igloo/bottom");
    private static final Map<ResourceLocation, BlockPos> field_207621_d = ImmutableMap.of(field_202592_e, new BlockPos(3, 5, 5), field_202593_f, new BlockPos(1, 3, 1), field_202594_g, new BlockPos(3, 6, 7));
    private static final Map<ResourceLocation, BlockPos> field_207622_e = ImmutableMap.of(field_202592_e, BlockPos.ZERO, field_202593_f, new BlockPos(2, -3, 4), field_202594_g, new BlockPos(0, -3, -2));

    public static void func_236991_a_(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> list, Random random2) {
        if (random2.nextDouble() < 0.5) {
            int n = random2.nextInt(8) + 4;
            list.add(new Piece(templateManager, field_202594_g, blockPos, rotation, n * 3));
            for (int i = 0; i < n - 1; ++i) {
                list.add(new Piece(templateManager, field_202593_f, blockPos, rotation, i * 3));
            }
        }
        list.add(new Piece(templateManager, field_202592_e, blockPos, rotation, 0));
    }

    public static class Piece
    extends TemplateStructurePiece {
        private final ResourceLocation field_207615_d;
        private final Rotation field_207616_e;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, int n) {
            super(IStructurePieceType.IGLU, 0);
            this.field_207615_d = resourceLocation;
            BlockPos blockPos2 = field_207622_e.get(resourceLocation);
            this.templatePosition = blockPos.add(blockPos2.getX(), blockPos2.getY() - n, blockPos2.getZ());
            this.field_207616_e = rotation;
            this.func_207614_a(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.IGLU, compoundNBT);
            this.field_207615_d = new ResourceLocation(compoundNBT.getString("Template"));
            this.field_207616_e = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_207614_a(templateManager);
        }

        private void func_207614_a(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.field_207615_d);
            PlacementSettings placementSettings = new PlacementSettings().setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(field_207621_d.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.field_207615_d.toString());
            compoundNBT.putString("Rot", this.field_207616_e.name());
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
            if ("chest".equals(string)) {
                iServerWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileEntity = iServerWorld.getTileEntity(blockPos.down());
                if (tileEntity instanceof ChestTileEntity) {
                    ((ChestTileEntity)tileEntity).setLootTable(LootTables.CHESTS_IGLOO_CHEST, random2.nextLong());
                }
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockPos blockPos2;
            BlockState blockState;
            PlacementSettings placementSettings = new PlacementSettings().setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(field_207621_d.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockPos3 = field_207622_e.get(this.field_207615_d);
            BlockPos blockPos4 = this.templatePosition.add(Template.transformedBlockPos(placementSettings, new BlockPos(3 - blockPos3.getX(), 0, 0 - blockPos3.getZ())));
            int n = iSeedReader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockPos4.getX(), blockPos4.getZ());
            BlockPos blockPos5 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, n - 90 - 1, 0);
            boolean bl = super.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
            if (this.field_207615_d.equals(field_202592_e) && !(blockState = iSeedReader.getBlockState((blockPos2 = this.templatePosition.add(Template.transformedBlockPos(placementSettings, new BlockPos(3, 0, 5)))).down())).isAir() && !blockState.isIn(Blocks.LADDER)) {
                iSeedReader.setBlockState(blockPos2, Blocks.SNOW_BLOCK.getDefaultState(), 3);
            }
            this.templatePosition = blockPos5;
            return bl;
        }
    }
}

