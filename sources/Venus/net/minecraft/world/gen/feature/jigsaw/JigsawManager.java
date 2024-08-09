/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JigsawManager {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void func_242837_a(DynamicRegistries dynamicRegistries, VillageConfig villageConfig, IPieceFactory iPieceFactory, ChunkGenerator chunkGenerator, TemplateManager templateManager, BlockPos blockPos, List<? super AbstractVillagePiece> list, Random random2, boolean bl, boolean bl2) {
        Structure.func_236397_g_();
        MutableRegistry<JigsawPattern> mutableRegistry = dynamicRegistries.getRegistry(Registry.JIGSAW_POOL_KEY);
        Rotation rotation = Rotation.randomRotation(random2);
        JigsawPattern jigsawPattern = villageConfig.func_242810_c().get();
        JigsawPiece jigsawPiece = jigsawPattern.getRandomPiece(random2);
        AbstractVillagePiece abstractVillagePiece = iPieceFactory.create(templateManager, jigsawPiece, blockPos, jigsawPiece.getGroundLevelDelta(), rotation, jigsawPiece.getBoundingBox(templateManager, blockPos, rotation));
        MutableBoundingBox mutableBoundingBox = abstractVillagePiece.getBoundingBox();
        int n = (mutableBoundingBox.maxX + mutableBoundingBox.minX) / 2;
        int n2 = (mutableBoundingBox.maxZ + mutableBoundingBox.minZ) / 2;
        int n3 = bl2 ? blockPos.getY() + chunkGenerator.getNoiseHeight(n, n2, Heightmap.Type.WORLD_SURFACE_WG) : blockPos.getY();
        int n4 = mutableBoundingBox.minY + abstractVillagePiece.getGroundLevelDelta();
        abstractVillagePiece.offset(0, n3 - n4, 0);
        list.add(abstractVillagePiece);
        if (villageConfig.func_236534_a_() > 0) {
            int n5 = 80;
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n - 80, n3 - 80, n2 - 80, n + 80 + 1, n3 + 80 + 1, n2 + 80 + 1);
            Assembler assembler = new Assembler(mutableRegistry, villageConfig.func_236534_a_(), iPieceFactory, chunkGenerator, templateManager, list, random2);
            assembler.availablePieces.addLast(new Entry(abstractVillagePiece, new MutableObject<VoxelShape>(VoxelShapes.combineAndSimplify(VoxelShapes.create(axisAlignedBB), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableBoundingBox)), IBooleanFunction.ONLY_FIRST)), n3 + 80, 0));
            while (!assembler.availablePieces.isEmpty()) {
                Entry entry = assembler.availablePieces.removeFirst();
                assembler.func_236831_a_(entry.villagePiece, entry.free, entry.boundsTop, entry.depth, bl);
            }
        }
    }

    public static void func_242838_a(DynamicRegistries dynamicRegistries, AbstractVillagePiece abstractVillagePiece, int n, IPieceFactory iPieceFactory, ChunkGenerator chunkGenerator, TemplateManager templateManager, List<? super AbstractVillagePiece> list, Random random2) {
        MutableRegistry<JigsawPattern> mutableRegistry = dynamicRegistries.getRegistry(Registry.JIGSAW_POOL_KEY);
        Assembler assembler = new Assembler(mutableRegistry, n, iPieceFactory, chunkGenerator, templateManager, list, random2);
        assembler.availablePieces.addLast(new Entry(abstractVillagePiece, new MutableObject<VoxelShape>(VoxelShapes.INFINITY), 0, 0));
        while (!assembler.availablePieces.isEmpty()) {
            Entry entry = assembler.availablePieces.removeFirst();
            assembler.func_236831_a_(entry.villagePiece, entry.free, entry.boundsTop, entry.depth, true);
        }
    }

    public static interface IPieceFactory {
        public AbstractVillagePiece create(TemplateManager var1, JigsawPiece var2, BlockPos var3, int var4, Rotation var5, MutableBoundingBox var6);
    }

    static final class Assembler {
        private final Registry<JigsawPattern> field_242839_a;
        private final int maxDepth;
        private final IPieceFactory pieceFactory;
        private final ChunkGenerator chunkGenerator;
        private final TemplateManager templateManager;
        private final List<? super AbstractVillagePiece> structurePieces;
        private final Random rand;
        private final Deque<Entry> availablePieces = Queues.newArrayDeque();

        private Assembler(Registry<JigsawPattern> registry, int n, IPieceFactory iPieceFactory, ChunkGenerator chunkGenerator, TemplateManager templateManager, List<? super AbstractVillagePiece> list, Random random2) {
            this.field_242839_a = registry;
            this.maxDepth = n;
            this.pieceFactory = iPieceFactory;
            this.chunkGenerator = chunkGenerator;
            this.templateManager = templateManager;
            this.structurePieces = list;
            this.rand = random2;
        }

        private void func_236831_a_(AbstractVillagePiece abstractVillagePiece, MutableObject<VoxelShape> mutableObject, int n, int n2, boolean bl) {
            JigsawPiece jigsawPiece = abstractVillagePiece.getJigsawPiece();
            BlockPos blockPos = abstractVillagePiece.getPos();
            Rotation rotation = abstractVillagePiece.getRotation();
            JigsawPattern.PlacementBehaviour placementBehaviour = jigsawPiece.getPlacementBehaviour();
            boolean bl2 = placementBehaviour == JigsawPattern.PlacementBehaviour.RIGID;
            MutableObject<VoxelShape> mutableObject2 = new MutableObject<VoxelShape>();
            MutableBoundingBox mutableBoundingBox = abstractVillagePiece.getBoundingBox();
            int n3 = mutableBoundingBox.minY;
            block0: for (Template.BlockInfo blockInfo : jigsawPiece.getJigsawBlocks(this.templateManager, blockPos, rotation, this.rand)) {
                Direction direction = JigsawBlock.getConnectingDirection(blockInfo.state);
                BlockPos blockPos2 = blockInfo.pos;
                BlockPos blockPos3 = blockPos2.offset(direction);
                int n4 = blockPos2.getY() - n3;
                int n5 = -1;
                ResourceLocation resourceLocation = new ResourceLocation(blockInfo.nbt.getString("pool"));
                Optional<JigsawPattern> optional = this.field_242839_a.getOptional(resourceLocation);
                if (optional.isPresent() && (optional.get().getNumberOfPieces() != 0 || Objects.equals(resourceLocation, JigsawPatternRegistry.field_244091_a.getLocation()))) {
                    ResourceLocation resourceLocation2 = optional.get().getFallback();
                    Optional<JigsawPattern> optional2 = this.field_242839_a.getOptional(resourceLocation2);
                    if (optional2.isPresent() && (optional2.get().getNumberOfPieces() != 0 || Objects.equals(resourceLocation2, JigsawPatternRegistry.field_244091_a.getLocation()))) {
                        JigsawPiece jigsawPiece2;
                        int n6;
                        MutableObject<Object> mutableObject3;
                        boolean bl3 = mutableBoundingBox.isVecInside(blockPos3);
                        if (bl3) {
                            mutableObject3 = mutableObject2;
                            n6 = n3;
                            if (mutableObject2.getValue() == null) {
                                mutableObject2.setValue(VoxelShapes.create(AxisAlignedBB.toImmutable(mutableBoundingBox)));
                            }
                        } else {
                            mutableObject3 = mutableObject;
                            n6 = n;
                        }
                        ArrayList<JigsawPiece> arrayList = Lists.newArrayList();
                        if (n2 != this.maxDepth) {
                            arrayList.addAll(optional.get().getShuffledPieces(this.rand));
                        }
                        arrayList.addAll(optional2.get().getShuffledPieces(this.rand));
                        Iterator iterator2 = arrayList.iterator();
                        while (iterator2.hasNext() && (jigsawPiece2 = (JigsawPiece)iterator2.next()) != EmptyJigsawPiece.INSTANCE) {
                            for (Rotation rotation2 : Rotation.shuffledRotations(this.rand)) {
                                List<Template.BlockInfo> list = jigsawPiece2.getJigsawBlocks(this.templateManager, BlockPos.ZERO, rotation2, this.rand);
                                MutableBoundingBox mutableBoundingBox2 = jigsawPiece2.getBoundingBox(this.templateManager, BlockPos.ZERO, rotation2);
                                int n7 = bl && mutableBoundingBox2.getYSize() <= 16 ? list.stream().mapToInt(arg_0 -> this.lambda$func_236831_a_$3(mutableBoundingBox2, arg_0)).max().orElse(0) : 0;
                                for (Template.BlockInfo blockInfo2 : list) {
                                    int n8;
                                    int n9;
                                    int n10;
                                    if (!JigsawBlock.hasJigsawMatch(blockInfo, blockInfo2)) continue;
                                    BlockPos blockPos4 = blockInfo2.pos;
                                    BlockPos blockPos5 = new BlockPos(blockPos3.getX() - blockPos4.getX(), blockPos3.getY() - blockPos4.getY(), blockPos3.getZ() - blockPos4.getZ());
                                    MutableBoundingBox mutableBoundingBox3 = jigsawPiece2.getBoundingBox(this.templateManager, blockPos5, rotation2);
                                    int n11 = mutableBoundingBox3.minY;
                                    JigsawPattern.PlacementBehaviour placementBehaviour2 = jigsawPiece2.getPlacementBehaviour();
                                    boolean bl4 = placementBehaviour2 == JigsawPattern.PlacementBehaviour.RIGID;
                                    int n12 = blockPos4.getY();
                                    int n13 = n4 - n12 + JigsawBlock.getConnectingDirection(blockInfo.state).getYOffset();
                                    if (bl2 && bl4) {
                                        n10 = n3 + n13;
                                    } else {
                                        if (n5 == -1) {
                                            n5 = this.chunkGenerator.getNoiseHeight(blockPos2.getX(), blockPos2.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                        }
                                        n10 = n5 - n12;
                                    }
                                    int n14 = n10 - n11;
                                    MutableBoundingBox mutableBoundingBox4 = mutableBoundingBox3.func_215127_b(0, n14, 0);
                                    BlockPos blockPos6 = blockPos5.add(0, n14, 0);
                                    if (n7 > 0) {
                                        n9 = Math.max(n7 + 1, mutableBoundingBox4.maxY - mutableBoundingBox4.minY);
                                        mutableBoundingBox4.maxY = mutableBoundingBox4.minY + n9;
                                    }
                                    if (VoxelShapes.compare((VoxelShape)mutableObject3.getValue(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableBoundingBox4).shrink(0.25)), IBooleanFunction.ONLY_SECOND)) continue;
                                    mutableObject3.setValue(VoxelShapes.combine((VoxelShape)mutableObject3.getValue(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableBoundingBox4)), IBooleanFunction.ONLY_FIRST));
                                    n9 = abstractVillagePiece.getGroundLevelDelta();
                                    int n15 = bl4 ? n9 - n13 : jigsawPiece2.getGroundLevelDelta();
                                    AbstractVillagePiece abstractVillagePiece2 = this.pieceFactory.create(this.templateManager, jigsawPiece2, blockPos6, n15, rotation2, mutableBoundingBox4);
                                    if (bl2) {
                                        n8 = n3 + n4;
                                    } else if (bl4) {
                                        n8 = n10 + n12;
                                    } else {
                                        if (n5 == -1) {
                                            n5 = this.chunkGenerator.getNoiseHeight(blockPos2.getX(), blockPos2.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                        }
                                        n8 = n5 + n13 / 2;
                                    }
                                    abstractVillagePiece.addJunction(new JigsawJunction(blockPos3.getX(), n8 - n4 + n9, blockPos3.getZ(), n13, placementBehaviour2));
                                    abstractVillagePiece2.addJunction(new JigsawJunction(blockPos2.getX(), n8 - n12 + n15, blockPos2.getZ(), -n13, placementBehaviour));
                                    this.structurePieces.add(abstractVillagePiece2);
                                    if (n2 + 1 > this.maxDepth) continue block0;
                                    this.availablePieces.addLast(new Entry(abstractVillagePiece2, mutableObject3, n6, n2 + 1));
                                    continue block0;
                                }
                            }
                        }
                        continue;
                    }
                    LOGGER.warn("Empty or none existent fallback pool: {}", (Object)resourceLocation2);
                    continue;
                }
                LOGGER.warn("Empty or none existent pool: {}", (Object)resourceLocation);
            }
        }

        private int lambda$func_236831_a_$3(MutableBoundingBox mutableBoundingBox, Template.BlockInfo blockInfo) {
            if (!mutableBoundingBox.isVecInside(blockInfo.pos.offset(JigsawBlock.getConnectingDirection(blockInfo.state)))) {
                return 1;
            }
            ResourceLocation resourceLocation = new ResourceLocation(blockInfo.nbt.getString("pool"));
            Optional<JigsawPattern> optional = this.field_242839_a.getOptional(resourceLocation);
            Optional<Integer> optional2 = optional.flatMap(this::lambda$func_236831_a_$0);
            int n = optional.map(this::lambda$func_236831_a_$1).orElse(0);
            int n2 = optional2.map(this::lambda$func_236831_a_$2).orElse(0);
            return Math.max(n, n2);
        }

        private Integer lambda$func_236831_a_$2(JigsawPattern jigsawPattern) {
            return jigsawPattern.getMaxSize(this.templateManager);
        }

        private Integer lambda$func_236831_a_$1(JigsawPattern jigsawPattern) {
            return jigsawPattern.getMaxSize(this.templateManager);
        }

        private Optional lambda$func_236831_a_$0(JigsawPattern jigsawPattern) {
            return this.field_242839_a.getOptional(jigsawPattern.getFallback());
        }
    }

    static final class Entry {
        private final AbstractVillagePiece villagePiece;
        private final MutableObject<VoxelShape> free;
        private final int boundsTop;
        private final int depth;

        private Entry(AbstractVillagePiece abstractVillagePiece, MutableObject<VoxelShape> mutableObject, int n, int n2) {
            this.villagePiece = abstractVillagePiece;
            this.free = mutableObject;
            this.boundsTop = n;
            this.depth = n2;
        }
    }
}

