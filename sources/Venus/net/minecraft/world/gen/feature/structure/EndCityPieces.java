/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class EndCityPieces {
    private static final PlacementSettings OVERWRITE = new PlacementSettings().setIgnoreEntities(false).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
    private static final PlacementSettings INSERT = new PlacementSettings().setIgnoreEntities(false).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
    private static final IGenerator HOUSE_TOWER_GENERATOR = new IGenerator(){

        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager templateManager, int n, CityTemplate cityTemplate, BlockPos blockPos, List<StructurePiece> list, Random random2) {
            if (n > 8) {
                return true;
            }
            Rotation rotation = cityTemplate.placeSettings.getRotation();
            CityTemplate cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, blockPos, "base_floor", rotation, true));
            int n2 = random2.nextInt(3);
            if (n2 == 0) {
                EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
            } else if (n2 == 1) {
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
                EndCityPieces.recursiveChildren(templateManager, TOWER_GENERATOR, n + 1, cityTemplate2, null, list, random2);
            } else if (n2 == 2) {
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
                EndCityPieces.recursiveChildren(templateManager, TOWER_GENERATOR, n + 1, cityTemplate2, null, list, random2);
            }
            return false;
        }
    };
    private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList(new Tuple<Rotation, BlockPos>(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple<Rotation, BlockPos>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)));
    private static final IGenerator TOWER_GENERATOR = new IGenerator(){

        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager templateManager, int n, CityTemplate cityTemplate, BlockPos blockPos, List<StructurePiece> list, Random random2) {
            Rotation rotation = cityTemplate.placeSettings.getRotation();
            CityTemplate cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(3 + random2.nextInt(2), -3, 3 + random2.nextInt(2)), "tower_base", rotation, true));
            cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
            CityTemplate cityTemplate3 = random2.nextInt(3) == 0 ? cityTemplate2 : null;
            int n2 = 1 + random2.nextInt(3);
            for (int i = 0; i < n2; ++i) {
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, 4, 0), "tower_piece", rotation, true));
                if (i >= n2 - 1 || !random2.nextBoolean()) continue;
                cityTemplate3 = cityTemplate2;
            }
            if (cityTemplate3 != null) {
                for (Tuple<Rotation, BlockPos> tuple : TOWER_BRIDGES) {
                    if (!random2.nextBoolean()) continue;
                    CityTemplate cityTemplate4 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate3, tuple.getB(), "bridge_end", rotation.add(tuple.getA()), true));
                    EndCityPieces.recursiveChildren(templateManager, TOWER_BRIDGE_GENERATOR, n + 1, cityTemplate4, null, list, random2);
                }
                EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
            } else {
                if (n != 7) {
                    return EndCityPieces.recursiveChildren(templateManager, FAT_TOWER_GENERATOR, n + 1, cityTemplate2, null, list, random2);
                }
                EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
            }
            return false;
        }
    };
    private static final IGenerator TOWER_BRIDGE_GENERATOR = new IGenerator(){
        public boolean shipCreated;

        @Override
        public void init() {
            this.shipCreated = false;
        }

        @Override
        public boolean generate(TemplateManager templateManager, int n, CityTemplate cityTemplate, BlockPos blockPos, List<StructurePiece> list, Random random2) {
            Rotation rotation = cityTemplate.placeSettings.getRotation();
            int n2 = random2.nextInt(4) + 1;
            CityTemplate cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
            cityTemplate2.componentType = -1;
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                if (random2.nextBoolean()) {
                    cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, n3, -4), "bridge_piece", rotation, true));
                    n3 = 0;
                    continue;
                }
                cityTemplate2 = random2.nextBoolean() ? EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, n3, -4), "bridge_steep_stairs", rotation, true)) : EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, n3, -8), "bridge_gentle_stairs", rotation, true));
                n3 = 4;
            }
            if (!this.shipCreated && random2.nextInt(10 - n) == 0) {
                EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-8 + random2.nextInt(8), n3, -70 + random2.nextInt(10)), "ship", rotation, true));
                this.shipCreated = true;
            } else if (!EndCityPieces.recursiveChildren(templateManager, HOUSE_TOWER_GENERATOR, n + 1, cityTemplate2, new BlockPos(-3, n3 + 1, -11), list, random2)) {
                return true;
            }
            cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(4, n3, 0), "bridge_end", rotation.add(Rotation.CLOCKWISE_180), true));
            cityTemplate2.componentType = -1;
            return false;
        }
    };
    private static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.newArrayList(new Tuple<Rotation, BlockPos>(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple<Rotation, BlockPos>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)));
    private static final IGenerator FAT_TOWER_GENERATOR = new IGenerator(){

        @Override
        public void init() {
        }

        @Override
        public boolean generate(TemplateManager templateManager, int n, CityTemplate cityTemplate, BlockPos blockPos, List<StructurePiece> list, Random random2) {
            Rotation rotation = cityTemplate.placeSettings.getRotation();
            CityTemplate cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
            cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));
            for (int i = 0; i < 2 && random2.nextInt(3) != 0; ++i) {
                cityTemplate2 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));
                for (Tuple<Rotation, BlockPos> tuple : FAT_TOWER_BRIDGES) {
                    if (!random2.nextBoolean()) continue;
                    CityTemplate cityTemplate3 = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, tuple.getB(), "bridge_end", rotation.add(tuple.getA()), true));
                    EndCityPieces.recursiveChildren(templateManager, TOWER_BRIDGE_GENERATOR, n + 1, cityTemplate3, null, list, random2);
                }
            }
            EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate2, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
            return false;
        }
    };

    private static CityTemplate addPiece(TemplateManager templateManager, CityTemplate cityTemplate, BlockPos blockPos, String string, Rotation rotation, boolean bl) {
        CityTemplate cityTemplate2 = new CityTemplate(templateManager, string, cityTemplate.templatePosition, rotation, bl);
        BlockPos blockPos2 = cityTemplate.template.calculateConnectedPos(cityTemplate.placeSettings, blockPos, cityTemplate2.placeSettings, BlockPos.ZERO);
        cityTemplate2.offset(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
        return cityTemplate2;
    }

    public static void startHouseTower(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<StructurePiece> list, Random random2) {
        FAT_TOWER_GENERATOR.init();
        HOUSE_TOWER_GENERATOR.init();
        TOWER_BRIDGE_GENERATOR.init();
        TOWER_GENERATOR.init();
        CityTemplate cityTemplate = EndCityPieces.addHelper(list, new CityTemplate(templateManager, "base_floor", blockPos, rotation, true));
        cityTemplate = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(-1, 0, -1), "second_floor_1", rotation, false));
        cityTemplate = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(-1, 4, -1), "third_floor_1", rotation, false));
        cityTemplate = EndCityPieces.addHelper(list, EndCityPieces.addPiece(templateManager, cityTemplate, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
        EndCityPieces.recursiveChildren(templateManager, TOWER_GENERATOR, 1, cityTemplate, null, list, random2);
    }

    private static CityTemplate addHelper(List<StructurePiece> list, CityTemplate cityTemplate) {
        list.add(cityTemplate);
        return cityTemplate;
    }

    private static boolean recursiveChildren(TemplateManager templateManager, IGenerator iGenerator, int n, CityTemplate cityTemplate, BlockPos blockPos, List<StructurePiece> list, Random random2) {
        if (n > 8) {
            return true;
        }
        ArrayList<StructurePiece> arrayList = Lists.newArrayList();
        if (iGenerator.generate(templateManager, n, cityTemplate, blockPos, arrayList, random2)) {
            boolean bl = false;
            int n2 = random2.nextInt();
            for (StructurePiece structurePiece : arrayList) {
                structurePiece.componentType = n2;
                StructurePiece structurePiece2 = StructurePiece.findIntersecting(list, structurePiece.getBoundingBox());
                if (structurePiece2 == null || structurePiece2.componentType == cityTemplate.componentType) continue;
                bl = true;
                break;
            }
            if (!bl) {
                list.addAll(arrayList);
                return false;
            }
        }
        return true;
    }

    public static class CityTemplate
    extends TemplateStructurePiece {
        private final String pieceName;
        private final Rotation rotation;
        private final boolean overwrite;

        public CityTemplate(TemplateManager templateManager, String string, BlockPos blockPos, Rotation rotation, boolean bl) {
            super(IStructurePieceType.ECP, 0);
            this.pieceName = string;
            this.templatePosition = blockPos;
            this.rotation = rotation;
            this.overwrite = bl;
            this.loadTemplate(templateManager);
        }

        public CityTemplate(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.ECP, compoundNBT);
            this.pieceName = compoundNBT.getString("Template");
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.overwrite = compoundNBT.getBoolean("OW");
            this.loadTemplate(templateManager);
        }

        private void loadTemplate(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(new ResourceLocation("end_city/" + this.pieceName));
            PlacementSettings placementSettings = (this.overwrite ? OVERWRITE : INSERT).copy().setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.pieceName);
            compoundNBT.putString("Rot", this.rotation.name());
            compoundNBT.putBoolean("OW", this.overwrite);
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
            if (string.startsWith("Chest")) {
                BlockPos blockPos2 = blockPos.down();
                if (mutableBoundingBox.isVecInside(blockPos2)) {
                    LockableLootTileEntity.setLootTable(iServerWorld, random2, blockPos2, LootTables.CHESTS_END_CITY_TREASURE);
                }
            } else if (string.startsWith("Sentry")) {
                ShulkerEntity shulkerEntity = EntityType.SHULKER.create(iServerWorld.getWorld());
                shulkerEntity.setPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
                shulkerEntity.setAttachmentPos(blockPos);
                iServerWorld.addEntity(shulkerEntity);
            } else if (string.startsWith("Elytra")) {
                ItemFrameEntity itemFrameEntity = new ItemFrameEntity(iServerWorld.getWorld(), blockPos, this.rotation.rotate(Direction.SOUTH));
                itemFrameEntity.setDisplayedItemWithUpdate(new ItemStack(Items.ELYTRA), true);
                iServerWorld.addEntity(itemFrameEntity);
            }
        }
    }

    static interface IGenerator {
        public void init();

        public boolean generate(TemplateManager var1, int var2, CityTemplate var3, BlockPos var4, List<StructurePiece> var5, Random var6);
    }
}

