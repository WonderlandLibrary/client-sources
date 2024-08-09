/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.structure.RuinedPortalPiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class RuinedPortalStructure
extends Structure<RuinedPortalFeature> {
    private static final String[] field_236331_u_ = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
    private static final String[] field_236332_v_ = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

    public RuinedPortalStructure(Codec<RuinedPortalFeature> codec) {
        super(codec);
    }

    @Override
    public Structure.IStartFactory<RuinedPortalFeature> getStartFactory() {
        return Start::new;
    }

    private static boolean func_236337_b_(BlockPos blockPos, Biome biome) {
        return biome.getTemperature(blockPos) < 0.15f;
    }

    private static int func_236339_b_(Random random2, ChunkGenerator chunkGenerator, RuinedPortalPiece.Location location, boolean bl, int n, int n2, MutableBoundingBox mutableBoundingBox) {
        int n3;
        if (location == RuinedPortalPiece.Location.IN_NETHER) {
            var7_7 = bl ? RuinedPortalStructure.func_236335_a_(random2, 32, 100) : (random2.nextFloat() < 0.5f ? RuinedPortalStructure.func_236335_a_(random2, 27, 29) : RuinedPortalStructure.func_236335_a_(random2, 29, 100));
        } else if (location == RuinedPortalPiece.Location.IN_MOUNTAIN) {
            var8_8 = n - n2;
            var7_7 = RuinedPortalStructure.func_236338_b_(random2, 70, var8_8);
        } else if (location == RuinedPortalPiece.Location.UNDERGROUND) {
            var8_8 = n - n2;
            var7_7 = RuinedPortalStructure.func_236338_b_(random2, 15, var8_8);
        } else {
            var7_7 = location == RuinedPortalPiece.Location.PARTLY_BURIED ? n - n2 + RuinedPortalStructure.func_236335_a_(random2, 2, 8) : n;
        }
        ImmutableList<BlockPos> immutableList = ImmutableList.of(new BlockPos(mutableBoundingBox.minX, 0, mutableBoundingBox.minZ), new BlockPos(mutableBoundingBox.maxX, 0, mutableBoundingBox.minZ), new BlockPos(mutableBoundingBox.minX, 0, mutableBoundingBox.maxZ), new BlockPos(mutableBoundingBox.maxX, 0, mutableBoundingBox.maxZ));
        List list = immutableList.stream().map(arg_0 -> RuinedPortalStructure.lambda$func_236339_b_$0(chunkGenerator, arg_0)).collect(Collectors.toList());
        Heightmap.Type type = location == RuinedPortalPiece.Location.ON_OCEAN_FLOOR ? Heightmap.Type.OCEAN_FLOOR_WG : Heightmap.Type.WORLD_SURFACE_WG;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (n3 = var7_7; n3 > 15; --n3) {
            int n4 = 0;
            mutable.setPos(0, n3, 0);
            for (IBlockReader iBlockReader : list) {
                BlockState blockState = iBlockReader.getBlockState(mutable);
                if (blockState == null || !type.getHeightLimitPredicate().test(blockState) || ++n4 != 3) continue;
                return n3;
            }
        }
        return n3;
    }

    private static int func_236335_a_(Random random2, int n, int n2) {
        return random2.nextInt(n2 - n + 1) + n;
    }

    private static int func_236338_b_(Random random2, int n, int n2) {
        return n < n2 ? RuinedPortalStructure.func_236335_a_(random2, n, n2) : n2;
    }

    private static IBlockReader lambda$func_236339_b_$0(ChunkGenerator chunkGenerator, BlockPos blockPos) {
        return chunkGenerator.func_230348_a_(blockPos.getX(), blockPos.getZ());
    }

    public static class Start
    extends StructureStart<RuinedPortalFeature> {
        protected Start(Structure<RuinedPortalFeature> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
            super(structure, n, n2, mutableBoundingBox, n3, l);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, RuinedPortalFeature ruinedPortalFeature) {
            RuinedPortalPiece.Location location;
            RuinedPortalPiece.Serializer serializer = new RuinedPortalPiece.Serializer();
            if (ruinedPortalFeature.field_236628_b_ == Location.DESERT) {
                location = RuinedPortalPiece.Location.PARTLY_BURIED;
                serializer.field_237027_d_ = false;
                serializer.field_237026_c_ = 0.0f;
            } else if (ruinedPortalFeature.field_236628_b_ == Location.JUNGLE) {
                location = RuinedPortalPiece.Location.ON_LAND_SURFACE;
                serializer.field_237027_d_ = this.rand.nextFloat() < 0.5f;
                serializer.field_237026_c_ = 0.8f;
                serializer.field_237028_e_ = true;
                serializer.field_237029_f_ = true;
            } else if (ruinedPortalFeature.field_236628_b_ == Location.SWAMP) {
                location = RuinedPortalPiece.Location.ON_OCEAN_FLOOR;
                serializer.field_237027_d_ = false;
                serializer.field_237026_c_ = 0.5f;
                serializer.field_237029_f_ = true;
            } else if (ruinedPortalFeature.field_236628_b_ == Location.MOUNTAIN) {
                var10_10 = this.rand.nextFloat() < 0.5f;
                location = var10_10 ? RuinedPortalPiece.Location.IN_MOUNTAIN : RuinedPortalPiece.Location.ON_LAND_SURFACE;
                serializer.field_237027_d_ = var10_10 || this.rand.nextFloat() < 0.5f;
            } else if (ruinedPortalFeature.field_236628_b_ == Location.OCEAN) {
                location = RuinedPortalPiece.Location.ON_OCEAN_FLOOR;
                serializer.field_237027_d_ = false;
                serializer.field_237026_c_ = 0.8f;
            } else if (ruinedPortalFeature.field_236628_b_ == Location.NETHER) {
                location = RuinedPortalPiece.Location.IN_NETHER;
                serializer.field_237027_d_ = this.rand.nextFloat() < 0.5f;
                serializer.field_237026_c_ = 0.0f;
                serializer.field_237030_g_ = true;
            } else {
                var10_10 = this.rand.nextFloat() < 0.5f;
                location = var10_10 ? RuinedPortalPiece.Location.UNDERGROUND : RuinedPortalPiece.Location.ON_LAND_SURFACE;
                serializer.field_237027_d_ = var10_10 || this.rand.nextFloat() < 0.5f;
            }
            ResourceLocation resourceLocation = this.rand.nextFloat() < 0.05f ? new ResourceLocation(field_236332_v_[this.rand.nextInt(field_236332_v_.length)]) : new ResourceLocation(field_236331_u_[this.rand.nextInt(field_236331_u_.length)]);
            Template template = templateManager.getTemplateDefaulted(resourceLocation);
            Rotation rotation = Util.getRandomObject(Rotation.values(), this.rand);
            Mirror mirror = this.rand.nextFloat() < 0.5f ? Mirror.NONE : Mirror.FRONT_BACK;
            BlockPos blockPos = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
            BlockPos blockPos2 = new ChunkPos(n, n2).asBlockPos();
            MutableBoundingBox mutableBoundingBox = template.func_237150_a_(blockPos2, rotation, blockPos, mirror);
            Vector3i vector3i = mutableBoundingBox.func_215126_f();
            int n3 = vector3i.getX();
            int n4 = vector3i.getZ();
            int n5 = chunkGenerator.getHeight(n3, n4, RuinedPortalPiece.func_237013_a_(location)) - 1;
            int n6 = RuinedPortalStructure.func_236339_b_(this.rand, chunkGenerator, location, serializer.field_237027_d_, n5, mutableBoundingBox.getYSize(), mutableBoundingBox);
            BlockPos blockPos3 = new BlockPos(blockPos2.getX(), n6, blockPos2.getZ());
            if (ruinedPortalFeature.field_236628_b_ == Location.MOUNTAIN || ruinedPortalFeature.field_236628_b_ == Location.OCEAN || ruinedPortalFeature.field_236628_b_ == Location.STANDARD) {
                serializer.field_237025_b_ = RuinedPortalStructure.func_236337_b_(blockPos3, biome);
            }
            this.components.add(new RuinedPortalPiece(blockPos3, location, serializer, resourceLocation, template, rotation, mirror, blockPos));
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (RuinedPortalFeature)iFeatureConfig);
        }
    }

    public static enum Location implements IStringSerializable
    {
        STANDARD("standard"),
        DESERT("desert"),
        JUNGLE("jungle"),
        SWAMP("swamp"),
        MOUNTAIN("mountain"),
        OCEAN("ocean"),
        NETHER("nether");

        public static final Codec<Location> field_236342_h_;
        private static final Map<String, Location> field_236343_i_;
        private final String field_236344_j_;

        private Location(String string2) {
            this.field_236344_j_ = string2;
        }

        public String func_236347_b_() {
            return this.field_236344_j_;
        }

        public static Location func_236346_a_(String string) {
            return field_236343_i_.get(string);
        }

        @Override
        public String getString() {
            return this.field_236344_j_;
        }

        private static Location lambda$static$0(Location location) {
            return location;
        }

        static {
            field_236342_h_ = IStringSerializable.createEnumCodec(Location::values, Location::func_236346_a_);
            field_236343_i_ = Arrays.stream(Location.values()).collect(Collectors.toMap(Location::func_236347_b_, Location::lambda$static$0));
        }
    }
}

