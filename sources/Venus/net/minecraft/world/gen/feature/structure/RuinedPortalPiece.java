/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.BlackStoneReplacementProcessor;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.BlockMosinessProcessor;
import net.minecraft.world.gen.feature.template.LavaSubmergingProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuinedPortalPiece
extends TemplateStructurePiece {
    private static final Logger field_237003_d_ = LogManager.getLogger();
    private final ResourceLocation field_237004_e_;
    private final Rotation field_237005_f_;
    private final Mirror field_237006_g_;
    private final Location field_237007_h_;
    private final Serializer field_237008_i_;

    public RuinedPortalPiece(BlockPos blockPos, Location location, Serializer serializer, ResourceLocation resourceLocation, Template template, Rotation rotation, Mirror mirror, BlockPos blockPos2) {
        super(IStructurePieceType.RUINED_PORTAL, 0);
        this.templatePosition = blockPos;
        this.field_237004_e_ = resourceLocation;
        this.field_237005_f_ = rotation;
        this.field_237006_g_ = mirror;
        this.field_237007_h_ = location;
        this.field_237008_i_ = serializer;
        this.func_237014_a_(template, blockPos2);
    }

    public RuinedPortalPiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(IStructurePieceType.RUINED_PORTAL, compoundNBT);
        this.field_237004_e_ = new ResourceLocation(compoundNBT.getString("Template"));
        this.field_237005_f_ = Rotation.valueOf(compoundNBT.getString("Rotation"));
        this.field_237006_g_ = Mirror.valueOf(compoundNBT.getString("Mirror"));
        this.field_237007_h_ = Location.func_237042_a_(compoundNBT.getString("VerticalPlacement"));
        this.field_237008_i_ = (Serializer)Serializer.field_237024_a_.parse(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("Properties"))).getOrThrow(true, field_237003_d_::error);
        Template template = templateManager.getTemplateDefaulted(this.field_237004_e_);
        this.func_237014_a_(template, new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2));
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        compoundNBT.putString("Template", this.field_237004_e_.toString());
        compoundNBT.putString("Rotation", this.field_237005_f_.name());
        compoundNBT.putString("Mirror", this.field_237006_g_.name());
        compoundNBT.putString("VerticalPlacement", this.field_237007_h_.func_237040_a_());
        Serializer.field_237024_a_.encodeStart(NBTDynamicOps.INSTANCE, this.field_237008_i_).resultOrPartial(field_237003_d_::error).ifPresent(arg_0 -> RuinedPortalPiece.lambda$readAdditional$0(compoundNBT, arg_0));
    }

    private void func_237014_a_(Template template, BlockPos blockPos) {
        BlockIgnoreStructureProcessor blockIgnoreStructureProcessor = this.field_237008_i_.field_237027_d_ ? BlockIgnoreStructureProcessor.STRUCTURE_BLOCK : BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK;
        ArrayList<RuleEntry> arrayList = Lists.newArrayList();
        arrayList.add(RuinedPortalPiece.func_237011_a_(Blocks.GOLD_BLOCK, 0.3f, Blocks.AIR));
        arrayList.add(this.func_237021_c_());
        if (!this.field_237008_i_.field_237025_b_) {
            arrayList.add(RuinedPortalPiece.func_237011_a_(Blocks.NETHERRACK, 0.07f, Blocks.MAGMA_BLOCK));
        }
        PlacementSettings placementSettings = new PlacementSettings().setRotation(this.field_237005_f_).setMirror(this.field_237006_g_).setCenterOffset(blockPos).addProcessor(blockIgnoreStructureProcessor).addProcessor(new RuleStructureProcessor(arrayList)).addProcessor(new BlockMosinessProcessor(this.field_237008_i_.field_237026_c_)).addProcessor(new LavaSubmergingProcessor());
        if (this.field_237008_i_.field_237030_g_) {
            placementSettings.addProcessor(BlackStoneReplacementProcessor.field_237058_b_);
        }
        this.setup(template, this.templatePosition, placementSettings);
    }

    private RuleEntry func_237021_c_() {
        if (this.field_237007_h_ == Location.ON_OCEAN_FLOOR) {
            return RuinedPortalPiece.func_237012_a_(Blocks.LAVA, Blocks.MAGMA_BLOCK);
        }
        return this.field_237008_i_.field_237025_b_ ? RuinedPortalPiece.func_237012_a_(Blocks.LAVA, Blocks.NETHERRACK) : RuinedPortalPiece.func_237011_a_(Blocks.LAVA, 0.2f, Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        if (!mutableBoundingBox.isVecInside(this.templatePosition)) {
            return false;
        }
        mutableBoundingBox.expandTo(this.template.getMutableBoundingBox(this.placeSettings, this.templatePosition));
        boolean bl = super.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
        this.func_237019_b_(random2, iSeedReader);
        this.func_237015_a_(random2, iSeedReader);
        if (this.field_237008_i_.field_237029_f_ || this.field_237008_i_.field_237028_e_) {
            BlockPos.getAllInBox(this.getBoundingBox()).forEach(arg_0 -> this.lambda$func_230383_a_$1(random2, iSeedReader, arg_0));
        }
        return bl;
    }

    @Override
    protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
    }

    private void func_237016_a_(Random random2, IWorld iWorld, BlockPos blockPos) {
        Direction direction;
        BlockPos blockPos2;
        BlockState blockState;
        BlockState blockState2 = iWorld.getBlockState(blockPos);
        if (!blockState2.isAir() && !blockState2.isIn(Blocks.VINE) && (blockState = iWorld.getBlockState(blockPos2 = blockPos.offset(direction = Direction.Plane.HORIZONTAL.random(random2)))).isAir() && Block.doesSideFillSquare(blockState2.getCollisionShape(iWorld, blockPos), direction)) {
            BooleanProperty booleanProperty = VineBlock.getPropertyFor(direction.getOpposite());
            iWorld.setBlockState(blockPos2, (BlockState)Blocks.VINE.getDefaultState().with(booleanProperty, true), 3);
        }
    }

    private void func_237020_b_(Random random2, IWorld iWorld, BlockPos blockPos) {
        if (random2.nextFloat() < 0.5f && iWorld.getBlockState(blockPos).isIn(Blocks.NETHERRACK) && iWorld.getBlockState(blockPos.up()).isAir()) {
            iWorld.setBlockState(blockPos.up(), (BlockState)Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 3);
        }
    }

    private void func_237015_a_(Random random2, IWorld iWorld) {
        for (int i = this.boundingBox.minX + 1; i < this.boundingBox.maxX; ++i) {
            for (int j = this.boundingBox.minZ + 1; j < this.boundingBox.maxZ; ++j) {
                BlockPos blockPos = new BlockPos(i, this.boundingBox.minY, j);
                if (!iWorld.getBlockState(blockPos).isIn(Blocks.NETHERRACK)) continue;
                this.func_237022_c_(random2, iWorld, blockPos.down());
            }
        }
    }

    private void func_237022_c_(Random random2, IWorld iWorld, BlockPos blockPos) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        this.func_237023_d_(random2, iWorld, mutable);
        for (int i = 8; i > 0 && random2.nextFloat() < 0.5f; --i) {
            mutable.move(Direction.DOWN);
            this.func_237023_d_(random2, iWorld, mutable);
        }
    }

    private void func_237019_b_(Random random2, IWorld iWorld) {
        boolean bl = this.field_237007_h_ == Location.ON_LAND_SURFACE || this.field_237007_h_ == Location.ON_OCEAN_FLOOR;
        Vector3i vector3i = this.boundingBox.func_215126_f();
        int n = vector3i.getX();
        int n2 = vector3i.getZ();
        float[] fArray = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.9f, 0.9f, 0.8f, 0.7f, 0.6f, 0.4f, 0.2f};
        int n3 = fArray.length;
        int n4 = (this.boundingBox.getXSize() + this.boundingBox.getZSize()) / 2;
        int n5 = random2.nextInt(Math.max(1, 8 - n4 / 2));
        int n6 = 3;
        BlockPos.Mutable mutable = BlockPos.ZERO.toMutable();
        for (int i = n - n3; i <= n + n3; ++i) {
            for (int j = n2 - n3; j <= n2 + n3; ++j) {
                int n7 = Math.abs(i - n) + Math.abs(j - n2);
                int n8 = Math.max(0, n7 + n5);
                if (n8 >= n3) continue;
                float f = fArray[n8];
                if (!(random2.nextDouble() < (double)f)) continue;
                int n9 = RuinedPortalPiece.func_237009_a_(iWorld, i, j, this.field_237007_h_);
                int n10 = bl ? n9 : Math.min(this.boundingBox.minY, n9);
                mutable.setPos(i, n10, j);
                if (Math.abs(n10 - this.boundingBox.minY) > 3 || !this.func_237010_a_(iWorld, mutable)) continue;
                this.func_237023_d_(random2, iWorld, mutable);
                if (this.field_237008_i_.field_237028_e_) {
                    this.func_237020_b_(random2, iWorld, mutable);
                }
                this.func_237022_c_(random2, iWorld, (BlockPos)mutable.down());
            }
        }
    }

    private boolean func_237010_a_(IWorld iWorld, BlockPos blockPos) {
        BlockState blockState = iWorld.getBlockState(blockPos);
        return !blockState.isIn(Blocks.AIR) && !blockState.isIn(Blocks.OBSIDIAN) && !blockState.isIn(Blocks.CHEST) && (this.field_237007_h_ == Location.IN_NETHER || !blockState.isIn(Blocks.LAVA));
    }

    private void func_237023_d_(Random random2, IWorld iWorld, BlockPos blockPos) {
        if (!this.field_237008_i_.field_237025_b_ && random2.nextFloat() < 0.07f) {
            iWorld.setBlockState(blockPos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
        } else {
            iWorld.setBlockState(blockPos, Blocks.NETHERRACK.getDefaultState(), 3);
        }
    }

    private static int func_237009_a_(IWorld iWorld, int n, int n2, Location location) {
        return iWorld.getHeight(RuinedPortalPiece.func_237013_a_(location), n, n2) - 1;
    }

    public static Heightmap.Type func_237013_a_(Location location) {
        return location == Location.ON_OCEAN_FLOOR ? Heightmap.Type.OCEAN_FLOOR_WG : Heightmap.Type.WORLD_SURFACE_WG;
    }

    private static RuleEntry func_237011_a_(Block block, float f, Block block2) {
        return new RuleEntry(new RandomBlockMatchRuleTest(block, f), AlwaysTrueRuleTest.INSTANCE, block2.getDefaultState());
    }

    private static RuleEntry func_237012_a_(Block block, Block block2) {
        return new RuleEntry(new BlockMatchRuleTest(block), AlwaysTrueRuleTest.INSTANCE, block2.getDefaultState());
    }

    private void lambda$func_230383_a_$1(Random random2, ISeedReader iSeedReader, BlockPos blockPos) {
        if (this.field_237008_i_.field_237029_f_) {
            this.func_237016_a_(random2, iSeedReader, blockPos);
        }
        if (this.field_237008_i_.field_237028_e_) {
            this.func_237020_b_(random2, iSeedReader, blockPos);
        }
    }

    private static void lambda$readAdditional$0(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("Properties", iNBT);
    }

    public static enum Location {
        ON_LAND_SURFACE("on_land_surface"),
        PARTLY_BURIED("partly_buried"),
        ON_OCEAN_FLOOR("on_ocean_floor"),
        IN_MOUNTAIN("in_mountain"),
        UNDERGROUND("underground"),
        IN_NETHER("in_nether");

        private static final Map<String, Location> field_237038_g_;
        private final String field_237039_h_;

        private Location(String string2) {
            this.field_237039_h_ = string2;
        }

        public String func_237040_a_() {
            return this.field_237039_h_;
        }

        public static Location func_237042_a_(String string) {
            return field_237038_g_.get(string);
        }

        private static Location lambda$static$0(Location location) {
            return location;
        }

        static {
            field_237038_g_ = Arrays.stream(Location.values()).collect(Collectors.toMap(Location::func_237040_a_, Location::lambda$static$0));
        }
    }

    public static class Serializer {
        public static final Codec<Serializer> field_237024_a_ = RecordCodecBuilder.create(Serializer::lambda$static$6);
        public boolean field_237025_b_;
        public float field_237026_c_ = 0.2f;
        public boolean field_237027_d_;
        public boolean field_237028_e_;
        public boolean field_237029_f_;
        public boolean field_237030_g_;

        public Serializer() {
        }

        public <T> Serializer(boolean bl, float f, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
            this.field_237025_b_ = bl;
            this.field_237026_c_ = f;
            this.field_237027_d_ = bl2;
            this.field_237028_e_ = bl3;
            this.field_237029_f_ = bl4;
            this.field_237030_g_ = bl5;
        }

        private static App lambda$static$6(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.BOOL.fieldOf("cold")).forGetter(Serializer::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("mossiness")).forGetter(Serializer::lambda$static$1), ((MapCodec)Codec.BOOL.fieldOf("air_pocket")).forGetter(Serializer::lambda$static$2), ((MapCodec)Codec.BOOL.fieldOf("overgrown")).forGetter(Serializer::lambda$static$3), ((MapCodec)Codec.BOOL.fieldOf("vines")).forGetter(Serializer::lambda$static$4), ((MapCodec)Codec.BOOL.fieldOf("replace_with_blackstone")).forGetter(Serializer::lambda$static$5)).apply(instance, Serializer::new);
        }

        private static Boolean lambda$static$5(Serializer serializer) {
            return serializer.field_237030_g_;
        }

        private static Boolean lambda$static$4(Serializer serializer) {
            return serializer.field_237029_f_;
        }

        private static Boolean lambda$static$3(Serializer serializer) {
            return serializer.field_237028_e_;
        }

        private static Boolean lambda$static$2(Serializer serializer) {
            return serializer.field_237027_d_;
        }

        private static Float lambda$static$1(Serializer serializer) {
            return Float.valueOf(serializer.field_237026_c_);
        }

        private static Boolean lambda$static$0(Serializer serializer) {
            return serializer.field_237025_b_;
        }
    }
}

