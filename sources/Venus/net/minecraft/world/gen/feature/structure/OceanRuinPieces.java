/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanRuinPieces {
    private static final ResourceLocation[] STRUCTURE_WARM = new ResourceLocation[]{new ResourceLocation("underwater_ruin/warm_1"), new ResourceLocation("underwater_ruin/warm_2"), new ResourceLocation("underwater_ruin/warm_3"), new ResourceLocation("underwater_ruin/warm_4"), new ResourceLocation("underwater_ruin/warm_5"), new ResourceLocation("underwater_ruin/warm_6"), new ResourceLocation("underwater_ruin/warm_7"), new ResourceLocation("underwater_ruin/warm_8")};
    private static final ResourceLocation[] STRUCTURE_BRICK = new ResourceLocation[]{new ResourceLocation("underwater_ruin/brick_1"), new ResourceLocation("underwater_ruin/brick_2"), new ResourceLocation("underwater_ruin/brick_3"), new ResourceLocation("underwater_ruin/brick_4"), new ResourceLocation("underwater_ruin/brick_5"), new ResourceLocation("underwater_ruin/brick_6"), new ResourceLocation("underwater_ruin/brick_7"), new ResourceLocation("underwater_ruin/brick_8")};
    private static final ResourceLocation[] STRUCTURE_CRACKED = new ResourceLocation[]{new ResourceLocation("underwater_ruin/cracked_1"), new ResourceLocation("underwater_ruin/cracked_2"), new ResourceLocation("underwater_ruin/cracked_3"), new ResourceLocation("underwater_ruin/cracked_4"), new ResourceLocation("underwater_ruin/cracked_5"), new ResourceLocation("underwater_ruin/cracked_6"), new ResourceLocation("underwater_ruin/cracked_7"), new ResourceLocation("underwater_ruin/cracked_8")};
    private static final ResourceLocation[] STRUCTURE_MOSSY = new ResourceLocation[]{new ResourceLocation("underwater_ruin/mossy_1"), new ResourceLocation("underwater_ruin/mossy_2"), new ResourceLocation("underwater_ruin/mossy_3"), new ResourceLocation("underwater_ruin/mossy_4"), new ResourceLocation("underwater_ruin/mossy_5"), new ResourceLocation("underwater_ruin/mossy_6"), new ResourceLocation("underwater_ruin/mossy_7"), new ResourceLocation("underwater_ruin/mossy_8")};
    private static final ResourceLocation[] STRUCTURE_BRICK_BIG = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_brick_1"), new ResourceLocation("underwater_ruin/big_brick_2"), new ResourceLocation("underwater_ruin/big_brick_3"), new ResourceLocation("underwater_ruin/big_brick_8")};
    private static final ResourceLocation[] STRUCTURE_MOSSY_BIG = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_mossy_1"), new ResourceLocation("underwater_ruin/big_mossy_2"), new ResourceLocation("underwater_ruin/big_mossy_3"), new ResourceLocation("underwater_ruin/big_mossy_8")};
    private static final ResourceLocation[] STRUCTURE_CRACKED_BIG = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_cracked_1"), new ResourceLocation("underwater_ruin/big_cracked_2"), new ResourceLocation("underwater_ruin/big_cracked_3"), new ResourceLocation("underwater_ruin/big_cracked_8")};
    private static final ResourceLocation[] STRUCTURE_WARM_BIG = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_warm_4"), new ResourceLocation("underwater_ruin/big_warm_5"), new ResourceLocation("underwater_ruin/big_warm_6"), new ResourceLocation("underwater_ruin/big_warm_7")};

    private static ResourceLocation getRandomPieceWarm(Random random2) {
        return Util.getRandomObject(STRUCTURE_WARM, random2);
    }

    private static ResourceLocation getRandomPieceWarmBig(Random random2) {
        return Util.getRandomObject(STRUCTURE_WARM_BIG, random2);
    }

    public static void func_204041_a(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> list, Random random2, OceanRuinConfig oceanRuinConfig) {
        boolean bl = random2.nextFloat() <= oceanRuinConfig.largeProbability;
        float f = bl ? 0.9f : 0.8f;
        OceanRuinPieces.func_204045_a(templateManager, blockPos, rotation, list, random2, oceanRuinConfig, bl, f);
        if (bl && random2.nextFloat() <= oceanRuinConfig.clusterProbability) {
            OceanRuinPieces.func_204047_a(templateManager, random2, rotation, blockPos, oceanRuinConfig, list);
        }
    }

    private static void func_204047_a(TemplateManager templateManager, Random random2, Rotation rotation, BlockPos blockPos, OceanRuinConfig oceanRuinConfig, List<StructurePiece> list) {
        int n = blockPos.getX();
        int n2 = blockPos.getZ();
        BlockPos blockPos2 = Template.getTransformedPos(new BlockPos(15, 0, 15), Mirror.NONE, rotation, BlockPos.ZERO).add(n, 0, n2);
        MutableBoundingBox mutableBoundingBox = MutableBoundingBox.createProper(n, 0, n2, blockPos2.getX(), 0, blockPos2.getZ());
        BlockPos blockPos3 = new BlockPos(Math.min(n, blockPos2.getX()), 0, Math.min(n2, blockPos2.getZ()));
        List<BlockPos> list2 = OceanRuinPieces.func_204044_a(random2, blockPos3.getX(), blockPos3.getZ());
        int n3 = MathHelper.nextInt(random2, 4, 8);
        for (int i = 0; i < n3; ++i) {
            Rotation rotation2;
            BlockPos blockPos4;
            int n4;
            int n5;
            BlockPos blockPos5;
            int n6;
            MutableBoundingBox mutableBoundingBox2;
            if (list2.isEmpty() || (mutableBoundingBox2 = MutableBoundingBox.createProper(n6 = (blockPos5 = list2.remove(n5 = random2.nextInt(list2.size()))).getX(), 0, n4 = blockPos5.getZ(), (blockPos4 = Template.getTransformedPos(new BlockPos(5, 0, 6), Mirror.NONE, rotation2 = Rotation.randomRotation(random2), BlockPos.ZERO).add(n6, 0, n4)).getX(), 0, blockPos4.getZ())).intersectsWith(mutableBoundingBox)) continue;
            OceanRuinPieces.func_204045_a(templateManager, blockPos5, rotation2, list, random2, oceanRuinConfig, false, 0.8f);
        }
    }

    private static List<BlockPos> func_204044_a(Random random2, int n, int n2) {
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        arrayList.add(new BlockPos(n - 16 + MathHelper.nextInt(random2, 1, 8), 90, n2 + 16 + MathHelper.nextInt(random2, 1, 7)));
        arrayList.add(new BlockPos(n - 16 + MathHelper.nextInt(random2, 1, 8), 90, n2 + MathHelper.nextInt(random2, 1, 7)));
        arrayList.add(new BlockPos(n - 16 + MathHelper.nextInt(random2, 1, 8), 90, n2 - 16 + MathHelper.nextInt(random2, 4, 8)));
        arrayList.add(new BlockPos(n + MathHelper.nextInt(random2, 1, 7), 90, n2 + 16 + MathHelper.nextInt(random2, 1, 7)));
        arrayList.add(new BlockPos(n + MathHelper.nextInt(random2, 1, 7), 90, n2 - 16 + MathHelper.nextInt(random2, 4, 6)));
        arrayList.add(new BlockPos(n + 16 + MathHelper.nextInt(random2, 1, 7), 90, n2 + 16 + MathHelper.nextInt(random2, 3, 8)));
        arrayList.add(new BlockPos(n + 16 + MathHelper.nextInt(random2, 1, 7), 90, n2 + MathHelper.nextInt(random2, 1, 7)));
        arrayList.add(new BlockPos(n + 16 + MathHelper.nextInt(random2, 1, 7), 90, n2 - 16 + MathHelper.nextInt(random2, 4, 8)));
        return arrayList;
    }

    private static void func_204045_a(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> list, Random random2, OceanRuinConfig oceanRuinConfig, boolean bl, float f) {
        if (oceanRuinConfig.field_204031_a == OceanRuinStructure.Type.WARM) {
            ResourceLocation resourceLocation = bl ? OceanRuinPieces.getRandomPieceWarmBig(random2) : OceanRuinPieces.getRandomPieceWarm(random2);
            list.add(new Piece(templateManager, resourceLocation, blockPos, rotation, f, oceanRuinConfig.field_204031_a, bl));
        } else if (oceanRuinConfig.field_204031_a == OceanRuinStructure.Type.COLD) {
            ResourceLocation[] resourceLocationArray = bl ? STRUCTURE_BRICK_BIG : STRUCTURE_BRICK;
            ResourceLocation[] resourceLocationArray2 = bl ? STRUCTURE_CRACKED_BIG : STRUCTURE_CRACKED;
            ResourceLocation[] resourceLocationArray3 = bl ? STRUCTURE_MOSSY_BIG : STRUCTURE_MOSSY;
            int n = random2.nextInt(resourceLocationArray.length);
            list.add(new Piece(templateManager, resourceLocationArray[n], blockPos, rotation, f, oceanRuinConfig.field_204031_a, bl));
            list.add(new Piece(templateManager, resourceLocationArray2[n], blockPos, rotation, 0.7f, oceanRuinConfig.field_204031_a, bl));
            list.add(new Piece(templateManager, resourceLocationArray3[n], blockPos, rotation, 0.5f, oceanRuinConfig.field_204031_a, bl));
        }
    }

    public static class Piece
    extends TemplateStructurePiece {
        private final OceanRuinStructure.Type biomeType;
        private final float integrity;
        private final ResourceLocation templateName;
        private final Rotation rotation;
        private final boolean isLarge;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos blockPos, Rotation rotation, float f, OceanRuinStructure.Type type, boolean bl) {
            super(IStructurePieceType.ORP, 0);
            this.templateName = resourceLocation;
            this.templatePosition = blockPos;
            this.rotation = rotation;
            this.integrity = f;
            this.biomeType = type;
            this.isLarge = bl;
            this.setup(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.ORP, compoundNBT);
            this.templateName = new ResourceLocation(compoundNBT.getString("Template"));
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.integrity = compoundNBT.getFloat("Integrity");
            this.biomeType = OceanRuinStructure.Type.valueOf(compoundNBT.getString("BiomeType"));
            this.isLarge = compoundNBT.getBoolean("IsLarge");
            this.setup(templateManager);
        }

        private void setup(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.templateName);
            PlacementSettings placementSettings = new PlacementSettings().setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.templateName.toString());
            compoundNBT.putString("Rot", this.rotation.name());
            compoundNBT.putFloat("Integrity", this.integrity);
            compoundNBT.putString("BiomeType", this.biomeType.toString());
            compoundNBT.putBoolean("IsLarge", this.isLarge);
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
            if ("chest".equals(string)) {
                iServerWorld.setBlockState(blockPos, (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.WATERLOGGED, iServerWorld.getFluidState(blockPos).isTagged(FluidTags.WATER)), 2);
                TileEntity tileEntity = iServerWorld.getTileEntity(blockPos);
                if (tileEntity instanceof ChestTileEntity) {
                    ((ChestTileEntity)tileEntity).setLootTable(this.isLarge ? LootTables.CHESTS_UNDERWATER_RUIN_BIG : LootTables.CHESTS_UNDERWATER_RUIN_SMALL, random2.nextLong());
                }
            } else if ("drowned".equals(string)) {
                DrownedEntity drownedEntity = EntityType.DROWNED.create(iServerWorld.getWorld());
                drownedEntity.enablePersistence();
                drownedEntity.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
                drownedEntity.onInitialSpawn(iServerWorld, iServerWorld.getDifficultyForLocation(blockPos), SpawnReason.STRUCTURE, null, null);
                iServerWorld.func_242417_l(drownedEntity);
                if (blockPos.getY() > iServerWorld.getSeaLevel()) {
                    iServerWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                } else {
                    iServerWorld.setBlockState(blockPos, Blocks.WATER.getDefaultState(), 2);
                }
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.placeSettings.clearProcessors().addProcessor(new IntegrityProcessor(this.integrity)).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            int n = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), n, this.templatePosition.getZ());
            BlockPos blockPos2 = Template.getTransformedPos(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.rotation, BlockPos.ZERO).add(this.templatePosition);
            this.templatePosition = new BlockPos(this.templatePosition.getX(), this.func_204035_a(this.templatePosition, iSeedReader, blockPos2), this.templatePosition.getZ());
            return super.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
        }

        private int func_204035_a(BlockPos blockPos, IBlockReader iBlockReader, BlockPos blockPos2) {
            int n = blockPos.getY();
            int n2 = 512;
            int n3 = n - 1;
            int n4 = 0;
            for (BlockPos blockPos3 : BlockPos.getAllInBoxMutable(blockPos, blockPos2)) {
                int n5 = blockPos3.getX();
                int n6 = blockPos3.getZ();
                int n7 = blockPos.getY() - 1;
                BlockPos.Mutable mutable = new BlockPos.Mutable(n5, n7, n6);
                BlockState blockState = iBlockReader.getBlockState(mutable);
                FluidState fluidState = iBlockReader.getFluidState(mutable);
                while ((blockState.isAir() || fluidState.isTagged(FluidTags.WATER) || blockState.getBlock().isIn(BlockTags.ICE)) && n7 > 1) {
                    mutable.setPos(n5, --n7, n6);
                    blockState = iBlockReader.getBlockState(mutable);
                    fluidState = iBlockReader.getFluidState(mutable);
                }
                n2 = Math.min(n2, n7);
                if (n7 >= n3 - 2) continue;
                ++n4;
            }
            int n8 = Math.abs(blockPos.getX() - blockPos2.getX());
            if (n3 - n2 > 2 && n4 > n8 - 2) {
                n = n2 + 1;
            }
            return n;
        }
    }
}

