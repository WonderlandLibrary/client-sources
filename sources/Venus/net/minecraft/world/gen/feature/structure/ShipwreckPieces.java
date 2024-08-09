/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
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
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ShipwreckPieces {
    private static final BlockPos STRUCTURE_OFFSET = new BlockPos(4, 0, 15);
    private static final ResourceLocation[] STRUCTURE_VARIANT_A = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
    private static final ResourceLocation[] field_204762_b = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};

    public static void func_204760_a(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> list, Random random2, ShipwreckConfig shipwreckConfig) {
        ResourceLocation resourceLocation = Util.getRandomObject(shipwreckConfig.isBeached ? STRUCTURE_VARIANT_A : field_204762_b, random2);
        list.add(new Piece(templateManager, resourceLocation, blockPos, rotation, shipwreckConfig.isBeached));
    }

    public static class Piece
    extends TemplateStructurePiece {
        private final Rotation rotation;
        private final ResourceLocation field_204756_e;
        private final boolean isBeached;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, boolean bl) {
            super(IStructurePieceType.SHIPWRECK, 0);
            this.templatePosition = blockPos;
            this.rotation = rotation;
            this.field_204756_e = resourceLocation;
            this.isBeached = bl;
            this.func_204754_a(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHIPWRECK, compoundNBT);
            this.field_204756_e = new ResourceLocation(compoundNBT.getString("Template"));
            this.isBeached = compoundNBT.getBoolean("isBeached");
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_204754_a(templateManager);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.field_204756_e.toString());
            compoundNBT.putBoolean("isBeached", this.isBeached);
            compoundNBT.putString("Rot", this.rotation.name());
        }

        private void func_204754_a(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.field_204756_e);
            PlacementSettings placementSettings = new PlacementSettings().setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(STRUCTURE_OFFSET).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
            if ("map_chest".equals(string)) {
                LockableLootTileEntity.setLootTable(iServerWorld, random2, blockPos.down(), LootTables.CHESTS_SHIPWRECK_MAP);
            } else if ("treasure_chest".equals(string)) {
                LockableLootTileEntity.setLootTable(iServerWorld, random2, blockPos.down(), LootTables.CHESTS_SHIPWRECK_TREASURE);
            } else if ("supply_chest".equals(string)) {
                LockableLootTileEntity.setLootTable(iServerWorld, random2, blockPos.down(), LootTables.CHESTS_SHIPWRECK_SUPPLY);
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n = 256;
            int n2 = 0;
            BlockPos blockPos2 = this.template.getSize();
            Heightmap.Type type = this.isBeached ? Heightmap.Type.WORLD_SURFACE_WG : Heightmap.Type.OCEAN_FLOOR_WG;
            int n3 = blockPos2.getX() * blockPos2.getZ();
            if (n3 == 0) {
                n2 = iSeedReader.getHeight(type, this.templatePosition.getX(), this.templatePosition.getZ());
            } else {
                BlockPos blockPos3 = this.templatePosition.add(blockPos2.getX() - 1, 0, blockPos2.getZ() - 1);
                for (BlockPos blockPos4 : BlockPos.getAllInBoxMutable(this.templatePosition, blockPos3)) {
                    int n4 = iSeedReader.getHeight(type, blockPos4.getX(), blockPos4.getZ());
                    n2 += n4;
                    n = Math.min(n, n4);
                }
                n2 /= n3;
            }
            int n5 = this.isBeached ? n - blockPos2.getY() / 2 - random2.nextInt(3) : n2;
            this.templatePosition = new BlockPos(this.templatePosition.getX(), n5, this.templatePosition.getZ());
            return super.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
        }
    }
}

