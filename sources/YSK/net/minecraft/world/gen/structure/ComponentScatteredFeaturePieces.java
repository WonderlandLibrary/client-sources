package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public class ComponentScatteredFeaturePieces
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u00100\u0005'", "DUAwp");
        ComponentScatteredFeaturePieces.I[" ".length()] = I("\u0011\",4", "EGfdS");
        ComponentScatteredFeaturePieces.I["  ".length()] = I("\u001869\u000e", "LSjFt");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void registerScatteredFeaturePieces() {
        MapGenStructureIO.registerStructureComponent(DesertPyramid.class, ComponentScatteredFeaturePieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(JunglePyramid.class, ComponentScatteredFeaturePieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(SwampHut.class, ComponentScatteredFeaturePieces.I["  ".length()]);
    }
    
    static {
        I();
    }
    
    public static class SwampHut extends Feature
    {
        private static final String[] I;
        private boolean hasWitch;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_74935_a(world, structureBoundingBox, "".length())) {
                return "".length() != 0;
            }
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), 0xAB ^ 0xAE, " ".length(), 0xC6 ^ 0xC1, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x79 ^ 0x7D, "  ".length(), 0xAF ^ 0xAA, 0x18 ^ 0x1C, 0x6F ^ 0x68, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "".length(), 0x43 ^ 0x47, " ".length(), "".length(), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "  ".length(), "  ".length(), "   ".length(), "   ".length(), "  ".length(), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "   ".length(), " ".length(), "   ".length(), 0x75 ^ 0x73, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x50 ^ 0x55, "  ".length(), "   ".length(), 0x6D ^ 0x68, "   ".length(), 0x82 ^ 0x84, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "  ".length(), 0x69 ^ 0x6E, 0x3A ^ 0x3E, "   ".length(), 0x2C ^ 0x2B, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "  ".length(), " ".length(), "   ".length(), "  ".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2B ^ 0x2E, "".length(), "  ".length(), 0x8D ^ 0x88, "   ".length(), "  ".length(), Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0x0 ^ 0x7, " ".length(), "   ".length(), 0xA ^ 0xD, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x8F ^ 0x8A, "".length(), 0x9A ^ 0x9D, 0x33 ^ 0x36, "   ".length(), 0xC2 ^ 0xC5, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "  ".length(), "   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), "   ".length(), "   ".length(), 0xD ^ 0xA, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), "   ".length(), 0xC0 ^ 0xC4, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x3C ^ 0x39, "   ".length(), 0x1E ^ 0x1A, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x93 ^ 0x96, "   ".length(), 0x28 ^ 0x2D, structureBoundingBox);
            this.setBlockState(world, Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), " ".length(), "   ".length(), 0x77 ^ 0x72, structureBoundingBox);
            this.setBlockState(world, Blocks.crafting_table.getDefaultState(), "   ".length(), "  ".length(), 0x29 ^ 0x2F, structureBoundingBox);
            this.setBlockState(world, Blocks.cauldron.getDefaultState(), 0xB1 ^ 0xB5, "  ".length(), 0x9D ^ 0x9B, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), " ".length(), "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0x79 ^ 0x7C, "  ".length(), " ".length(), structureBoundingBox);
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.oak_stairs, " ".length());
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.oak_stairs, "".length());
            final int metadataWithOffset4 = this.getMetadataWithOffset(Blocks.oak_stairs, "  ".length());
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x90 ^ 0x94, " ".length(), 0xB3 ^ 0xB5, 0x6 ^ 0x2, " ".length(), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x78 ^ 0x7C, "  ".length(), "".length(), 0xC7 ^ 0xC3, 0x15 ^ 0x12, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset3), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset3), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBB ^ 0xBD, 0x88 ^ 0x8C, "  ".length(), 0xA2 ^ 0xA4, 0x8F ^ 0x8B, 0x1B ^ 0x1C, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset2), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset2), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x95 ^ 0x91, 0x85 ^ 0x8D, 0x96 ^ 0x90, 0x25 ^ 0x21, 0xAB ^ 0xA3, Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset4), Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset4), "".length() != 0);
            int i = "  ".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
            while (i <= (0x58 ^ 0x5F)) {
                int j = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j <= (0x8F ^ 0x8A)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.log.getDefaultState(), j, -" ".length(), i, structureBoundingBox);
                    j += 4;
                }
                i += 5;
            }
            if (!this.hasWitch) {
                final int xWithOffset = this.getXWithOffset("  ".length(), 0xB8 ^ 0xBD);
                final int yWithOffset = this.getYWithOffset("  ".length());
                final int zWithOffset = this.getZWithOffset("  ".length(), 0x28 ^ 0x2D);
                if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
                    this.hasWitch = (" ".length() != 0);
                    final EntityWitch entityWitch = new EntityWitch(world);
                    entityWitch.setLocationAndAngles(xWithOffset + 0.5, yWithOffset, zWithOffset + 0.5, 0.0f, 0.0f);
                    entityWitch.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(xWithOffset, yWithOffset, zWithOffset)), null);
                    world.spawnEntityInWorld(entityWitch);
                }
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("6>\u001d\u0019/", "aWizG");
            SwampHut.I[" ".length()] = I(":\u000b!&-", "mbUEE");
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public SwampHut(final Random random, final int n, final int n2) {
            super(random, n, 0x5A ^ 0x1A, n2, 0x11 ^ 0x16, 0x8C ^ 0x8B, 0x50 ^ 0x59);
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(SwampHut.I["".length()], this.hasWitch);
        }
        
        static {
            I();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasWitch = nbtTagCompound.getBoolean(SwampHut.I[" ".length()]);
        }
        
        public SwampHut() {
        }
    }
    
    abstract static class Feature extends StructureComponent
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        protected int scatteredFeatureSizeY;
        protected int scatteredFeatureSizeZ;
        private static final String[] I;
        protected int field_74936_d;
        protected int scatteredFeatureSizeX;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.scatteredFeatureSizeX = nbtTagCompound.getInteger(Feature.I[0x77 ^ 0x73]);
            this.scatteredFeatureSizeY = nbtTagCompound.getInteger(Feature.I[0x77 ^ 0x72]);
            this.scatteredFeatureSizeZ = nbtTagCompound.getInteger(Feature.I[0xA4 ^ 0xA2]);
            this.field_74936_d = nbtTagCompound.getInteger(Feature.I[0x6C ^ 0x6B]);
        }
        
        public Feature() {
            this.field_74936_d = -" ".length();
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setInteger(Feature.I["".length()], this.scatteredFeatureSizeX);
            nbtTagCompound.setInteger(Feature.I[" ".length()], this.scatteredFeatureSizeY);
            nbtTagCompound.setInteger(Feature.I["  ".length()], this.scatteredFeatureSizeZ);
            nbtTagCompound.setInteger(Feature.I["   ".length()], this.field_74936_d);
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Feature.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x71 ^ 0x77);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x6 ^ 0x2);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x69 ^ 0x6C);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Feature.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        protected Feature(final Random random, final int n, final int n2, final int n3, final int scatteredFeatureSizeX, final int scatteredFeatureSizeY, final int scatteredFeatureSizeZ) {
            super("".length());
            this.field_74936_d = -" ".length();
            this.scatteredFeatureSizeX = scatteredFeatureSizeX;
            this.scatteredFeatureSizeY = scatteredFeatureSizeY;
            this.scatteredFeatureSizeZ = scatteredFeatureSizeZ;
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                case 3:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n, n2, n3, n + scatteredFeatureSizeX - " ".length(), n2 + scatteredFeatureSizeY - " ".length(), n3 + scatteredFeatureSizeZ - " ".length());
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, n2, n3, n + scatteredFeatureSizeZ - " ".length(), n2 + scatteredFeatureSizeY - " ".length(), n3 + scatteredFeatureSizeX - " ".length());
                    break;
                }
            }
        }
        
        private static void I() {
            (I = new String[0x4F ^ 0x47])["".length()] = I("#\"\n2\u001c", "tKnFt");
            Feature.I[" ".length()] = I("\u0012\u001c\u001f30.", "ZyvTX");
            Feature.I["  ".length()] = I("\u0001\"82\n", "EGHFb");
            Feature.I["   ".length()] = I("1(7=", "yxXNa");
            Feature.I[0x24 ^ 0x20] = I("<\u000f\u0012;\u001e", "kfvOv");
            Feature.I[0x8C ^ 0x89] = I("81(2*\u0004", "pTAUB");
            Feature.I[0xC3 ^ 0xC5] = I("\u0001\n\u0006!>", "EovUV");
            Feature.I[0x30 ^ 0x37] = I("!*\u001e\u000b", "izqxa");
        }
        
        static {
            I();
        }
        
        protected boolean func_74935_a(final World world, final StructureBoundingBox structureBoundingBox, final int n) {
            if (this.field_74936_d >= 0) {
                return " ".length() != 0;
            }
            int length = "".length();
            int length2 = "".length();
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int i = this.boundingBox.minZ;
            "".length();
            if (true != true) {
                throw null;
            }
            while (i <= this.boundingBox.maxZ) {
                int j = this.boundingBox.minX;
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (j <= this.boundingBox.maxX) {
                    mutableBlockPos.func_181079_c(j, 0x5D ^ 0x1D, i);
                    if (structureBoundingBox.isVecInside(mutableBlockPos)) {
                        length += Math.max(world.getTopSolidOrLiquidBlock(mutableBlockPos).getY(), world.provider.getAverageGroundLevel());
                        ++length2;
                    }
                    ++j;
                }
                ++i;
            }
            if (length2 == 0) {
                return "".length() != 0;
            }
            this.field_74936_d = length / length2;
            this.boundingBox.offset("".length(), this.field_74936_d - this.boundingBox.minY + n, "".length());
            return " ".length() != 0;
        }
    }
    
    public static class DesertPyramid extends Feature
    {
        private boolean[] field_74940_h;
        private static final List<WeightedRandomChestContent> itemsToGenerateInTemple;
        private static final String[] I;
        
        private static void I() {
            (I = new String[0x2F ^ 0x27])["".length()] = I("$\u0013\u0003'\u0006-\u0011\u0015\u0013)$\u0017\u0003\u0003Z", "Lrpwj");
            DesertPyramid.I[" ".length()] = I("\u00067'\u0001\b\u000f515'\u00063'%U", "nVTQd");
            DesertPyramid.I["  ".length()] = I("\u001d-\u0012\u0001\u0019\u0014/\u000456\u001d)\u0012%G", "uLaQu");
            DesertPyramid.I["   ".length()] = I("8\u000e\u0016\b\"1\f\u0000<\r8\n\u0016,}", "PoeXN");
            DesertPyramid.I[0x6B ^ 0x6F] = I("\f\t\u0002%\u0004\u0005\u000b\u0014\u0011+\f\r\u0002\u0001X", "dhquh");
            DesertPyramid.I[0x30 ^ 0x35] = I(")$9\u001d\u0007 &/)() 99Z", "AEJMk");
            DesertPyramid.I[0x94 ^ 0x92] = I(")\u0015\u0002\u0013\u001f \u0017\u0014'0)\u0011\u00027A", "AtqCs");
            DesertPyramid.I[0xBB ^ 0xBC] = I("'\u001b$\u0016/.\u00192\"\u0000'\u001f$2p", "OzWFC");
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), -(0x42 ^ 0x46), "".length(), this.scatteredFeatureSizeX - " ".length(), "".length(), this.scatteredFeatureSizeZ - " ".length(), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            int i = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (i <= (0xA0 ^ 0xA9)) {
                this.fillWithBlocks(world, structureBoundingBox, i, i, i, this.scatteredFeatureSizeX - " ".length() - i, i, this.scatteredFeatureSizeZ - " ".length() - i, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, i + " ".length(), i, i + " ".length(), this.scatteredFeatureSizeX - "  ".length() - i, i, this.scatteredFeatureSizeZ - "  ".length() - i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                ++i;
            }
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < this.scatteredFeatureSizeX) {
                int k = "".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
                while (k < this.scatteredFeatureSizeZ) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.sandstone.getDefaultState(), j, -(0xAA ^ 0xAF), k, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.sandstone_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.sandstone_stairs, "  ".length());
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.sandstone_stairs, "".length());
            final int metadataWithOffset4 = this.getMetadataWithOffset(Blocks.sandstone_stairs, " ".length());
            final int n = (EnumDyeColor.ORANGE.getDyeDamage() ^ -" ".length()) & (0x45 ^ 0x4A);
            final int n2 = (EnumDyeColor.BLUE.getDyeDamage() ^ -" ".length()) & (0xA8 ^ 0xA7);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x95 ^ 0x91, 0x26 ^ 0x2F, 0x1D ^ 0x19, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x11 ^ 0x1B, " ".length(), "   ".length(), 0x39 ^ 0x33, "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), "  ".length(), 0x18 ^ 0x12, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset2), "  ".length(), 0x6D ^ 0x67, 0x10 ^ 0x14, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset3), "".length(), 0x9 ^ 0x3, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset4), 0x39 ^ 0x3D, 0x5A ^ 0x50, "  ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x67 ^ 0x62), "".length(), "".length(), this.scatteredFeatureSizeX - " ".length(), 0xB6 ^ 0xBF, 0x92 ^ 0x96, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x80 ^ 0x84), 0xBD ^ 0xB7, " ".length(), this.scatteredFeatureSizeX - "  ".length(), 0x28 ^ 0x22, "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), this.scatteredFeatureSizeX - "   ".length(), 0x36 ^ 0x3C, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset2), this.scatteredFeatureSizeX - "   ".length(), 0x93 ^ 0x99, 0x98 ^ 0x9C, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset3), this.scatteredFeatureSizeX - (0x3F ^ 0x3A), 0x92 ^ 0x98, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset4), this.scatteredFeatureSizeX - " ".length(), 0x5A ^ 0x50, "  ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x74 ^ 0x7C, "".length(), "".length(), 0xBD ^ 0xB1, 0x80 ^ 0x84, 0xB6 ^ 0xB2, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBA ^ 0xB3, " ".length(), "".length(), 0x63 ^ 0x68, "   ".length(), 0x62 ^ 0x66, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x8D ^ 0x84, " ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x53 ^ 0x5A, "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0xCE ^ 0xC7, "   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x5A ^ 0x50, "   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x16 ^ 0x1D, "   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0xB9 ^ 0xB2, "  ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x89 ^ 0x82, " ".length(), " ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x2C ^ 0x28, " ".length(), " ".length(), 0x48 ^ 0x40, "   ".length(), "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC5 ^ 0xC1, " ".length(), "  ".length(), 0x4E ^ 0x46, "  ".length(), "  ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xBF, " ".length(), " ".length(), 0x54 ^ 0x44, "   ".length(), "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB2 ^ 0xBE, " ".length(), "  ".length(), 0x31 ^ 0x21, "  ".length(), "  ".length(), Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBC ^ 0xB9, 0x85 ^ 0x81, 0x23 ^ 0x26, this.scatteredFeatureSizeX - (0x48 ^ 0x4E), 0x54 ^ 0x50, this.scatteredFeatureSizeZ - (0x98 ^ 0x9E), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x28 ^ 0x21, 0xA4 ^ 0xA0, 0x6D ^ 0x64, 0x7D ^ 0x76, 0x68 ^ 0x6C, 0x75 ^ 0x7E, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x3 ^ 0xB, " ".length(), 0xA1 ^ 0xA9, 0x2B ^ 0x23, "   ".length(), 0xB5 ^ 0xBD, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x15 ^ 0x19, " ".length(), 0xCE ^ 0xC6, 0x97 ^ 0x9B, "   ".length(), 0x6B ^ 0x63, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4C ^ 0x44, " ".length(), 0x59 ^ 0x55, 0xB1 ^ 0xB9, "   ".length(), 0x5B ^ 0x57, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x3 ^ 0xF, " ".length(), 0x26 ^ 0x2A, 0xCF ^ 0xC3, "   ".length(), 0x23 ^ 0x2F, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x8C ^ 0x89, 0x5B ^ 0x5F, 0x15 ^ 0x11, 0x32 ^ 0x39, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x34 ^ 0x31), " ".length(), 0x74 ^ 0x71, this.scatteredFeatureSizeX - "  ".length(), 0xA2 ^ 0xA6, 0xA0 ^ 0xAB, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x17 ^ 0x11, 0xD ^ 0xA, 0xA8 ^ 0xA1, 0x3 ^ 0x5, 0x5B ^ 0x5C, 0x54 ^ 0x5F, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x94 ^ 0x93), 0x83 ^ 0x84, 0xAD ^ 0xA4, this.scatteredFeatureSizeX - (0x40 ^ 0x47), 0x93 ^ 0x94, 0xB9 ^ 0xB2, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA9 ^ 0xAC, 0x49 ^ 0x4C, 0x9E ^ 0x97, 0x8C ^ 0x89, 0x8 ^ 0xF, 0xBD ^ 0xB6, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x38 ^ 0x3E), 0x32 ^ 0x37, 0x35 ^ 0x3C, this.scatteredFeatureSizeX - (0xAC ^ 0xAA), 0x64 ^ 0x63, 0xB1 ^ 0xBA, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x2 ^ 0x7, 0x47 ^ 0x42, 0xAF ^ 0xA5, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0xA1 ^ 0xA4, 0xC2 ^ 0xC4, 0x75 ^ 0x7F, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0xAD ^ 0xAB, 0xA4 ^ 0xA2, 0x48 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - (0x67 ^ 0x61), 0x2D ^ 0x28, 0xB5 ^ 0xBF, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - (0x44 ^ 0x42), 0x79 ^ 0x7F, 0x9 ^ 0x3, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - (0x71 ^ 0x76), 0x45 ^ 0x43, 0xB3 ^ 0xB9, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x7D ^ 0x79, 0x78 ^ 0x7C, "  ".length(), 0x4A ^ 0x4C, 0x2E ^ 0x2A, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - "   ".length(), 0x76 ^ 0x72, 0x0 ^ 0x4, this.scatteredFeatureSizeX - "   ".length(), 0x5B ^ 0x5D, 0x38 ^ 0x3C, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), "  ".length(), 0x59 ^ 0x5D, 0x11 ^ 0x14, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), "  ".length(), "   ".length(), 0x96 ^ 0x92, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), this.scatteredFeatureSizeX - "   ".length(), 0x80 ^ 0x84, 0x8D ^ 0x88, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset), this.scatteredFeatureSizeX - "   ".length(), "   ".length(), 0xA8 ^ 0xAC, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "   ".length(), "  ".length(), "  ".length(), "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - "   ".length(), " ".length(), "   ".length(), this.scatteredFeatureSizeX - "  ".length(), "  ".length(), "   ".length(), Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.sandstone_stairs.getDefaultState(), " ".length(), " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getDefaultState(), this.scatteredFeatureSizeX - "  ".length(), " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), " ".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), this.scatteredFeatureSizeX - "  ".length(), "  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset4), "  ".length(), " ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(metadataWithOffset3), this.scatteredFeatureSizeX - "   ".length(), " ".length(), "  ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x98 ^ 0x9C, "   ".length(), 0x91 ^ 0x94, 0x6C ^ 0x68, "   ".length(), 0x85 ^ 0x97, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0xBB ^ 0xBE), "   ".length(), 0x72 ^ 0x77, this.scatteredFeatureSizeX - (0x24 ^ 0x21), "   ".length(), 0x83 ^ 0x92, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x84 ^ 0x81, 0x3 ^ 0x7, "  ".length(), 0xA2 ^ 0xB2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, this.scatteredFeatureSizeX - (0x37 ^ 0x31), " ".length(), 0x18 ^ 0x1D, this.scatteredFeatureSizeX - (0x27 ^ 0x22), "  ".length(), 0x81 ^ 0x91, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            int l = 0xC ^ 0x9;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (l <= (0x5D ^ 0x4C)) {
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x8 ^ 0xC, " ".length(), l, structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0xA7 ^ 0xA3, "  ".length(), l, structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), this.scatteredFeatureSizeX - (0x83 ^ 0x86), " ".length(), l, structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), this.scatteredFeatureSizeX - (0xAD ^ 0xA8), "  ".length(), l, structureBoundingBox);
                l += 2;
            }
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x3F ^ 0x35, "".length(), 0x18 ^ 0x1F, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x22 ^ 0x28, "".length(), 0xA ^ 0x2, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x2B ^ 0x22, "".length(), 0xB9 ^ 0xB0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x60 ^ 0x6B, "".length(), 0x84 ^ 0x8D, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x4A ^ 0x42, "".length(), 0x1E ^ 0x14, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x9D ^ 0x91, "".length(), 0x9C ^ 0x96, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x68 ^ 0x6F, "".length(), 0x56 ^ 0x5C, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0xB1 ^ 0xBC, "".length(), 0x30 ^ 0x3A, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x6C ^ 0x65, "".length(), 0x90 ^ 0x9B, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x57 ^ 0x5C, "".length(), 0xBA ^ 0xB1, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x6D ^ 0x67, "".length(), 0x28 ^ 0x24, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0xCD ^ 0xC7, "".length(), 0x5B ^ 0x56, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n2), 0x7E ^ 0x74, "".length(), 0x42 ^ 0x48, structureBoundingBox);
            int length = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (length <= this.scatteredFeatureSizeX - " ".length()) {
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, "  ".length(), " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, "  ".length(), "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, "  ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, "   ".length(), " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, "   ".length(), "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, "   ".length(), "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x24 ^ 0x20, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), length, 0x57 ^ 0x53, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0xD ^ 0x9, "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, 0x3D ^ 0x38, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x4D ^ 0x48, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, 0x34 ^ 0x31, "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x86 ^ 0x80, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), length, 0x95 ^ 0x93, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x74 ^ 0x72, "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x26 ^ 0x21, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x11 ^ 0x16, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length, 0x63 ^ 0x64, "   ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, 0x90 ^ 0x98, " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, 0x3A ^ 0x32, "  ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length, 0x72 ^ 0x7A, "   ".length(), structureBoundingBox);
                length += this.scatteredFeatureSizeX - " ".length();
            }
            int length2 = "  ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (length2 <= this.scatteredFeatureSizeX - "   ".length()) {
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 - " ".length(), "  ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2, "  ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 + " ".length(), "  ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 - " ".length(), "   ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2, "   ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 + " ".length(), "   ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 - " ".length(), 0x50 ^ 0x54, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), length2, 0x88 ^ 0x8C, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 + " ".length(), 0x93 ^ 0x97, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 - " ".length(), 0x3D ^ 0x38, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2, 0x8A ^ 0x8F, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 + " ".length(), 0xB4 ^ 0xB1, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 - " ".length(), 0x9A ^ 0x9C, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), length2, 0x70 ^ 0x76, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 + " ".length(), 0x33 ^ 0x35, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 - " ".length(), 0xA0 ^ 0xA7, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2, 0x6A ^ 0x6D, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), length2 + " ".length(), 0x19 ^ 0x1E, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 - " ".length(), 0x40 ^ 0x48, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2, 0xCD ^ 0xC5, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), length2 + " ".length(), 0x33 ^ 0x3B, "".length(), structureBoundingBox);
                length2 += this.scatteredFeatureSizeX - "   ".length() - "  ".length();
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x8A ^ 0x82, 0x99 ^ 0x9D, "".length(), 0x7C ^ 0x70, 0x72 ^ 0x74, "".length(), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x56 ^ 0x5E, 0x37 ^ 0x31, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x5E ^ 0x52, 0xC0 ^ 0xC6, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x26 ^ 0x2F, 0xA4 ^ 0xA1, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0x5C ^ 0x56, 0xC2 ^ 0xC7, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(n), 0x91 ^ 0x9A, 0x53 ^ 0x56, "".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x30 ^ 0x38, -(0xA ^ 0x4), 0x57 ^ 0x5F, 0x7 ^ 0xB, -(0x69 ^ 0x62), 0x41 ^ 0x4D, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4B ^ 0x43, -(0xBE ^ 0xB4), 0x48 ^ 0x40, 0x9D ^ 0x91, -(0x37 ^ 0x3D), 0xA1 ^ 0xAD, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7D ^ 0x75, -(0x8A ^ 0x83), 0x38 ^ 0x30, 0x20 ^ 0x2C, -(0x97 ^ 0x9E), 0x4 ^ 0x8, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7 ^ 0xF, -(0x9C ^ 0x94), 0x5D ^ 0x55, 0x6F ^ 0x63, -" ".length(), 0xCC ^ 0xC0, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6E ^ 0x67, -(0x43 ^ 0x48), 0x4E ^ 0x47, 0x34 ^ 0x3F, -" ".length(), 0x24 ^ 0x2F, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.stone_pressure_plate.getDefaultState(), 0x5 ^ 0xF, -(0x5A ^ 0x51), 0x71 ^ 0x7B, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0x58 ^ 0x51, -(0xB0 ^ 0xBD), 0x1 ^ 0x8, 0x76 ^ 0x7D, -(0x23 ^ 0x2E), 0x55 ^ 0x5E, Blocks.tnt.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x72 ^ 0x7A, -(0x2F ^ 0x24), 0x2D ^ 0x27, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x77 ^ 0x7F, -(0x7E ^ 0x74), 0x64 ^ 0x6E, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0x2A ^ 0x2D, -(0x28 ^ 0x22), 0x97 ^ 0x9D, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0xA6 ^ 0xA1, -(0x38 ^ 0x33), 0x3A ^ 0x30, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x93 ^ 0x9F, -(0xB ^ 0x0), 0x92 ^ 0x98, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x65 ^ 0x69, -(0x86 ^ 0x8C), 0x33 ^ 0x39, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0x21 ^ 0x2C, -(0x12 ^ 0x18), 0xA3 ^ 0xA9, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x5E ^ 0x53, -(0xB4 ^ 0xBF), 0x68 ^ 0x62, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0xBB ^ 0xB1, -(0x9F ^ 0x94), 0x59 ^ 0x51, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x1F ^ 0x15, -(0x93 ^ 0x99), 0x51 ^ 0x59, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0x8B ^ 0x81, -(0x4E ^ 0x44), 0xC ^ 0xB, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x65 ^ 0x6F, -(0x1E ^ 0x15), 0x7 ^ 0x0, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x6B ^ 0x61, -(0x5E ^ 0x55), 0x16 ^ 0x1A, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x51 ^ 0x5B, -(0x52 ^ 0x58), 0x1 ^ 0xD, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 0x3B ^ 0x31, -(0x94 ^ 0x9E), 0x2A ^ 0x27, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 0x9A ^ 0x90, -(0xA5 ^ 0xAE), 0x76 ^ 0x7B, structureBoundingBox);
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EnumFacing enumFacing = iterator.next();
                if (!this.field_74940_h[enumFacing.getHorizontalIndex()]) {
                    final int n3 = enumFacing.getFrontOffsetX() * "  ".length();
                    final int n4 = enumFacing.getFrontOffsetZ() * "  ".length();
                    final boolean[] field_74940_h = this.field_74940_h;
                    final int horizontalIndex = enumFacing.getHorizontalIndex();
                    final int n5 = (0x50 ^ 0x5A) + n3;
                    final int n6 = -(0x25 ^ 0x2E);
                    final int n7 = (0x24 ^ 0x2E) + n4;
                    final List<WeightedRandomChestContent> itemsToGenerateInTemple = DesertPyramid.itemsToGenerateInTemple;
                    final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                    array["".length()] = Items.enchanted_book.getRandom(random);
                    field_74940_h[horizontalIndex] = this.generateChestContents(world, structureBoundingBox, random, n5, n6, n7, WeightedRandomChestContent.func_177629_a(itemsToGenerateInTemple, array), "  ".length() + random.nextInt(0xC1 ^ 0xC4));
                }
            }
            return " ".length() != 0;
        }
        
        public DesertPyramid() {
            this.field_74940_h = new boolean[0x6D ^ 0x69];
        }
        
        public DesertPyramid(final Random random, final int n, final int n2) {
            super(random, n, 0x26 ^ 0x66, n2, 0x12 ^ 0x7, 0x57 ^ 0x58, 0xD1 ^ 0xC4);
            this.field_74940_h = new boolean[0x56 ^ 0x52];
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(DesertPyramid.I["".length()], this.field_74940_h["".length()]);
            nbtTagCompound.setBoolean(DesertPyramid.I[" ".length()], this.field_74940_h[" ".length()]);
            nbtTagCompound.setBoolean(DesertPyramid.I["  ".length()], this.field_74940_h["  ".length()]);
            nbtTagCompound.setBoolean(DesertPyramid.I["   ".length()], this.field_74940_h["   ".length()]);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74940_h["".length()] = nbtTagCompound.getBoolean(DesertPyramid.I[0xB8 ^ 0xBC]);
            this.field_74940_h[" ".length()] = nbtTagCompound.getBoolean(DesertPyramid.I[0xAD ^ 0xA8]);
            this.field_74940_h["  ".length()] = nbtTagCompound.getBoolean(DesertPyramid.I[0xA4 ^ 0xA2]);
            this.field_74940_h["   ".length()] = nbtTagCompound.getBoolean(DesertPyramid.I[0x98 ^ 0x9F]);
        }
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0xCF ^ 0xC5];
            array["".length()] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "   ".length(), "   ".length());
            array[" ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0xA ^ 0xF, 0x5B ^ 0x51);
            array["  ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), "  ".length(), 0x21 ^ 0x26, 0x5 ^ 0xA);
            array["   ".length()] = new WeightedRandomChestContent(Items.emerald, "".length(), " ".length(), "   ".length(), "  ".length());
            array[0x87 ^ 0x83] = new WeightedRandomChestContent(Items.bone, "".length(), 0x7A ^ 0x7E, 0x4D ^ 0x4B, 0x34 ^ 0x20);
            array[0x33 ^ 0x36] = new WeightedRandomChestContent(Items.rotten_flesh, "".length(), "   ".length(), 0x3F ^ 0x38, 0x59 ^ 0x49);
            array[0x39 ^ 0x3F] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), "   ".length());
            array[0x6A ^ 0x6D] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0xA4 ^ 0xAC] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x91 ^ 0x98] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            itemsToGenerateInTemple = Lists.newArrayList((Object[])array);
        }
    }
    
    public static class JunglePyramid extends Feature
    {
        private static final String[] I;
        private boolean field_74945_j;
        private boolean field_74948_i;
        private static final List<WeightedRandomChestContent> field_175816_i;
        private static final List<WeightedRandomChestContent> field_175815_j;
        private boolean field_74946_k;
        private static Stones junglePyramidsRandomScatteredStones;
        private boolean field_74947_h;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.func_74935_a(world, structureBoundingBox, "".length())) {
                return "".length() != 0;
            }
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.stone_stairs, "   ".length());
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.stone_stairs, "  ".length());
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.stone_stairs, "".length());
            final int metadataWithOffset4 = this.getMetadataWithOffset(Blocks.stone_stairs, " ".length());
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "".length(), -(0x3D ^ 0x39), "".length(), this.scatteredFeatureSizeX - " ".length(), "".length(), this.scatteredFeatureSizeZ - " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "  ".length(), 0xB0 ^ 0xB9, "  ".length(), "  ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x2A ^ 0x26, 0xBD ^ 0xB4, "  ".length(), 0x58 ^ 0x54, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "   ".length(), "  ".length(), "  ".length(), 0x54 ^ 0x5F, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x58 ^ 0x51, " ".length(), "   ".length(), 0xC9 ^ 0xC0, "  ".length(), 0x2A ^ 0x21, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), "   ".length(), " ".length(), 0x8E ^ 0x84, 0xBC ^ 0xBA, " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x3B ^ 0x36, 0x89 ^ 0x83, 0xBB ^ 0xBD, 0x52 ^ 0x5F, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "  ".length(), " ".length(), 0x6A ^ 0x6C, 0xD ^ 0x1, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x19 ^ 0x13, "   ".length(), "  ".length(), 0xBD ^ 0xB7, 0x33 ^ 0x35, 0x6D ^ 0x61, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), "   ".length(), "  ".length(), 0x71 ^ 0x78, "   ".length(), 0x92 ^ 0x9E, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), 0xC5 ^ 0xC3, "  ".length(), 0x8A ^ 0x83, 0x70 ^ 0x76, 0xAB ^ 0xA7, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "   ".length(), 0x70 ^ 0x77, "   ".length(), 0x8C ^ 0x84, 0xB4 ^ 0xB3, 0x4 ^ 0xF, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x44 ^ 0x40, 0x16 ^ 0x1E, 0xBD ^ 0xB9, 0x5D ^ 0x5A, 0x7F ^ 0x77, 0xCA ^ 0xC0, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithAir(world, structureBoundingBox, "   ".length(), " ".length(), "   ".length(), 0xA ^ 0x2, "  ".length(), 0x57 ^ 0x5C);
            this.fillWithAir(world, structureBoundingBox, 0xB7 ^ 0xB3, "   ".length(), 0xA9 ^ 0xAF, 0x37 ^ 0x30, "   ".length(), 0x5F ^ 0x56);
            this.fillWithAir(world, structureBoundingBox, "  ".length(), 0x24 ^ 0x20, "  ".length(), 0x30 ^ 0x39, 0x17 ^ 0x12, 0xAE ^ 0xA2);
            this.fillWithAir(world, structureBoundingBox, 0xBF ^ 0xBB, 0xF ^ 0x9, 0x26 ^ 0x23, 0x6A ^ 0x6D, 0x76 ^ 0x70, 0x8D ^ 0x84);
            this.fillWithAir(world, structureBoundingBox, 0x10 ^ 0x15, 0x6 ^ 0x1, 0x72 ^ 0x74, 0x31 ^ 0x37, 0xB4 ^ 0xB3, 0x2C ^ 0x24);
            this.fillWithAir(world, structureBoundingBox, 0x5A ^ 0x5F, " ".length(), "  ".length(), 0xBD ^ 0xBB, "  ".length(), "  ".length());
            this.fillWithAir(world, structureBoundingBox, 0x79 ^ 0x7C, "  ".length(), 0x14 ^ 0x18, 0x9F ^ 0x99, "  ".length(), 0x92 ^ 0x9E);
            this.fillWithAir(world, structureBoundingBox, 0x3F ^ 0x3A, 0x45 ^ 0x40, " ".length(), 0x2B ^ 0x2D, 0xB2 ^ 0xB7, " ".length());
            this.fillWithAir(world, structureBoundingBox, 0x7 ^ 0x2, 0x4E ^ 0x4B, 0x8F ^ 0x82, 0xA8 ^ 0xAE, 0x72 ^ 0x77, 0xAD ^ 0xA0);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), 0xC3 ^ 0xC6, 0x3B ^ 0x3E, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x85 ^ 0x8F, 0x92 ^ 0x97, 0x93 ^ 0x96, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), " ".length(), 0xA2 ^ 0xA7, 0x16 ^ 0x1F, structureBoundingBox);
            this.setBlockState(world, Blocks.air.getDefaultState(), 0x12 ^ 0x18, 0xC4 ^ 0xC1, 0x8D ^ 0x84, structureBoundingBox);
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i <= (0xC ^ 0x2)) {
                this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), 0xAE ^ 0xAA, i, "  ".length(), 0xAF ^ 0xAA, i, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xC7 ^ 0xC3, 0xA2 ^ 0xA6, i, 0x79 ^ 0x7D, 0x4 ^ 0x1, i, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x4B ^ 0x4C, 0xA7 ^ 0xA3, i, 0x11 ^ 0x16, 0x90 ^ 0x95, i, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xA1 ^ 0xA8, 0xAB ^ 0xAF, i, 0xB3 ^ 0xBA, 0x29 ^ 0x2C, i, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                i += 14;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xA0 ^ 0xA5, 0xB3 ^ 0xB5, "".length(), 0x95 ^ 0x93, 0x27 ^ 0x21, "".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            int j = "".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (j <= (0xB8 ^ 0xB3)) {
                int k = "  ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (k <= (0x8A ^ 0x86)) {
                    this.fillWithRandomizedBlocks(world, structureBoundingBox, j, 0x56 ^ 0x52, k, j, 0xA1 ^ 0xA4, k, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                    k += 2;
                }
                this.fillWithRandomizedBlocks(world, structureBoundingBox, j, 0x33 ^ 0x35, 0x7B ^ 0x7E, j, 0x1 ^ 0x7, 0xB4 ^ 0xB1, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(world, structureBoundingBox, j, 0x44 ^ 0x42, 0x9E ^ 0x97, j, 0x7D ^ 0x7B, 0x33 ^ 0x3A, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                j += 11;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), 0x73 ^ 0x74, "  ".length(), "  ".length(), 0x8D ^ 0x84, "  ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xAF ^ 0xA6, 0x5 ^ 0x2, "  ".length(), 0x7D ^ 0x74, 0xB6 ^ 0xBF, "  ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), 0x5B ^ 0x5C, 0x68 ^ 0x64, "  ".length(), 0x8 ^ 0x1, 0x71 ^ 0x7D, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x3B ^ 0x32, 0xAD ^ 0xAA, 0x88 ^ 0x84, 0x52 ^ 0x5B, 0x9B ^ 0x92, 0xB5 ^ 0xB9, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xA9 ^ 0xAD, 0x6F ^ 0x66, 0x2C ^ 0x28, 0x9F ^ 0x9B, 0x97 ^ 0x9E, 0x41 ^ 0x45, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x4 ^ 0x3, 0x1E ^ 0x17, 0x72 ^ 0x76, 0xA0 ^ 0xA7, 0x9C ^ 0x95, 0x7 ^ 0x3, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x9 ^ 0xD, 0x4A ^ 0x43, 0x4B ^ 0x41, 0x5E ^ 0x5A, 0x28 ^ 0x21, 0xA4 ^ 0xAE, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xA7 ^ 0xA0, 0x81 ^ 0x88, 0xAE ^ 0xA4, 0x11 ^ 0x16, 0x29 ^ 0x20, 0x8B ^ 0x81, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x5A ^ 0x5F, 0xAC ^ 0xA5, 0x9D ^ 0x9A, 0xB4 ^ 0xB2, 0x11 ^ 0x18, 0x45 ^ 0x42, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x63 ^ 0x66, 0xB1 ^ 0xB8, 0x75 ^ 0x73, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x4C ^ 0x4A, 0x94 ^ 0x9D, 0x3B ^ 0x3D, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset2), 0x65 ^ 0x60, 0x6D ^ 0x64, 0x6B ^ 0x63, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset2), 0x4F ^ 0x49, 0x38 ^ 0x31, 0xA9 ^ 0xA1, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x33 ^ 0x37, "".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x41 ^ 0x44, "".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x2B ^ 0x2D, "".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x8D ^ 0x8A, "".length(), "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x54 ^ 0x50, " ".length(), 0x41 ^ 0x49, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x8A ^ 0x8E, "  ".length(), 0xAD ^ 0xA4, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0xA ^ 0xE, "   ".length(), 0x55 ^ 0x5F, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x23 ^ 0x24, " ".length(), 0x16 ^ 0x1E, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0xE ^ 0x9, "  ".length(), 0x98 ^ 0x91, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset), 0x8B ^ 0x8C, "   ".length(), 0x71 ^ 0x7B, structureBoundingBox);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xE ^ 0xA, " ".length(), 0x94 ^ 0x9D, 0xA4 ^ 0xA0, " ".length(), 0x4A ^ 0x43, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x3A ^ 0x3D, " ".length(), 0x7B ^ 0x72, 0x4B ^ 0x4C, " ".length(), 0x25 ^ 0x2C, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x3B ^ 0x3F, " ".length(), 0xB8 ^ 0xB2, 0x2D ^ 0x2A, "  ".length(), 0xC ^ 0x6, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x7F ^ 0x7A, 0xBB ^ 0xBF, 0xB6 ^ 0xB3, 0xC5 ^ 0xC3, 0xBE ^ 0xBA, 0xB7 ^ 0xB2, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset3), 0xA7 ^ 0xA3, 0x29 ^ 0x2D, 0x46 ^ 0x43, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset4), 0x79 ^ 0x7E, 0x93 ^ 0x97, 0x67 ^ 0x62, structureBoundingBox);
            int l = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (l < (0x52 ^ 0x56)) {
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset2), 0x4 ^ 0x1, "".length() - l, (0x2D ^ 0x2B) + l, structureBoundingBox);
                this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(metadataWithOffset2), 0x90 ^ 0x96, "".length() - l, (0x55 ^ 0x53) + l, structureBoundingBox);
                this.fillWithAir(world, structureBoundingBox, 0x31 ^ 0x34, "".length() - l, (0x20 ^ 0x27) + l, 0x3F ^ 0x39, "".length() - l, (0x23 ^ 0x2A) + l);
                ++l;
            }
            this.fillWithAir(world, structureBoundingBox, " ".length(), -"   ".length(), 0x33 ^ 0x3F, 0x1B ^ 0x11, -" ".length(), 0x34 ^ 0x39);
            this.fillWithAir(world, structureBoundingBox, " ".length(), -"   ".length(), " ".length(), "   ".length(), -" ".length(), 0x77 ^ 0x7A);
            this.fillWithAir(world, structureBoundingBox, " ".length(), -"   ".length(), " ".length(), 0x3A ^ 0x33, -" ".length(), 0xAE ^ 0xAB);
            int length = " ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (length <= (0x75 ^ 0x78)) {
                this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), -"   ".length(), length, " ".length(), -"  ".length(), length, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                length += 2;
            }
            int length2 = "  ".length();
            "".length();
            if (false) {
                throw null;
            }
            while (length2 <= (0x4E ^ 0x42)) {
                this.fillWithRandomizedBlocks(world, structureBoundingBox, " ".length(), -" ".length(), length2, "   ".length(), -" ".length(), length2, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
                length2 += 2;
            }
            this.fillWithRandomizedBlocks(world, structureBoundingBox, "  ".length(), -"  ".length(), " ".length(), 0x92 ^ 0x97, -"  ".length(), " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x4D ^ 0x4A, -"  ".length(), " ".length(), 0x37 ^ 0x3E, -"  ".length(), " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x33 ^ 0x35, -"   ".length(), " ".length(), 0xC3 ^ 0xC5, -"   ".length(), " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0xF ^ 0x9, -" ".length(), " ".length(), 0xB7 ^ 0xB1, -" ".length(), " ".length(), "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.setBlockState(world, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.EAST.getHorizontalIndex())).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, (boolean)(" ".length() != 0)), " ".length(), -"   ".length(), 0x3 ^ 0xB, structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.WEST.getHorizontalIndex())).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, (boolean)(" ".length() != 0)), 0x74 ^ 0x70, -"   ".length(), 0x23 ^ 0x2B, structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, (boolean)(" ".length() != 0)), "  ".length(), -"   ".length(), 0x68 ^ 0x60, structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, (boolean)(" ".length() != 0)), "   ".length(), -"   ".length(), 0x97 ^ 0x9F, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x39 ^ 0x3C, -"   ".length(), 0x66 ^ 0x61, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x80 ^ 0x85, -"   ".length(), 0x56 ^ 0x50, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x4C ^ 0x49, -"   ".length(), 0x72 ^ 0x77, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x3D ^ 0x38, -"   ".length(), 0xAF ^ 0xAB, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0xBA ^ 0xBF, -"   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x1 ^ 0x4, -"   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x22 ^ 0x27, -"   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x1D ^ 0x19, -"   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), "   ".length(), -"   ".length(), " ".length(), structureBoundingBox);
            if (!this.field_74945_j) {
                this.field_74945_j = this.generateDispenserContents(world, structureBoundingBox, random, "   ".length(), -"  ".length(), " ".length(), EnumFacing.NORTH.getIndex(), JunglePyramid.field_175815_j, "  ".length());
            }
            this.setBlockState(world, Blocks.vine.getStateFromMeta(0x4 ^ 0xB), "   ".length(), -"  ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.NORTH.getHorizontalIndex())).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, (boolean)(" ".length() != 0)), 0x2A ^ 0x2D, -"   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire_hook.getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.SOUTH.getHorizontalIndex())).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, (boolean)(" ".length() != 0)), 0x95 ^ 0x92, -"   ".length(), 0xC4 ^ 0xC1, structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, (boolean)(" ".length() != 0)), 0xA0 ^ 0xA7, -"   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, (boolean)(" ".length() != 0)), 0x1A ^ 0x1D, -"   ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, (boolean)(" ".length() != 0)), 0xD ^ 0xA, -"   ".length(), 0x45 ^ 0x41, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x3F ^ 0x37, -"   ".length(), 0x54 ^ 0x52, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x40 ^ 0x49, -"   ".length(), 0x88 ^ 0x8E, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0xCE ^ 0xC7, -"   ".length(), 0x43 ^ 0x46, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0xA4 ^ 0xAD, -"   ".length(), 0x22 ^ 0x26, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x42 ^ 0x4B, -"  ".length(), 0xD ^ 0x9, structureBoundingBox);
            if (!this.field_74946_k) {
                this.field_74946_k = this.generateDispenserContents(world, structureBoundingBox, random, 0x16 ^ 0x1F, -"  ".length(), "   ".length(), EnumFacing.WEST.getIndex(), JunglePyramid.field_175815_j, "  ".length());
            }
            this.setBlockState(world, Blocks.vine.getStateFromMeta(0x99 ^ 0x96), 0xB7 ^ 0xBF, -" ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.vine.getStateFromMeta(0x87 ^ 0x88), 0x20 ^ 0x28, -"  ".length(), "   ".length(), structureBoundingBox);
            if (!this.field_74947_h) {
                final int n = 0xA5 ^ 0xAD;
                final int n2 = -"   ".length();
                final int length3 = "   ".length();
                final List<WeightedRandomChestContent> field_175816_i = JunglePyramid.field_175816_i;
                final WeightedRandomChestContent[] array = new WeightedRandomChestContent[" ".length()];
                array["".length()] = Items.enchanted_book.getRandom(random);
                this.field_74947_h = this.generateChestContents(world, structureBoundingBox, random, n, n2, length3, WeightedRandomChestContent.func_177629_a(field_175816_i, array), "  ".length() + random.nextInt(0x10 ^ 0x15));
            }
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x3D ^ 0x34, -"   ".length(), "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0xAA ^ 0xA2, -"   ".length(), " ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x10 ^ 0x14, -"   ".length(), 0x31 ^ 0x34, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x3C ^ 0x39, -"  ".length(), 0x31 ^ 0x34, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0xA5 ^ 0xA0, -" ".length(), 0x98 ^ 0x9D, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x57 ^ 0x51, -"   ".length(), 0x4D ^ 0x48, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x89 ^ 0x8E, -"  ".length(), 0x64 ^ 0x61, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x7A ^ 0x7D, -" ".length(), 0x9A ^ 0x9F, structureBoundingBox);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0xA1 ^ 0xA9, -"   ".length(), 0xC2 ^ 0xC7, structureBoundingBox);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x5E ^ 0x57, -" ".length(), " ".length(), 0x5D ^ 0x54, -" ".length(), 0x26 ^ 0x23, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithAir(world, structureBoundingBox, 0x22 ^ 0x2A, -"   ".length(), 0xCF ^ 0xC7, 0x97 ^ 0x9D, -" ".length(), 0xAC ^ 0xA6);
            this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 0x46 ^ 0x4E, -"  ".length(), 0xCB ^ 0xC0, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 0x4 ^ 0xD, -"  ".length(), 0x7D ^ 0x76, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 0x31 ^ 0x3B, -"  ".length(), 0xD ^ 0x6, structureBoundingBox);
            this.setBlockState(world, Blocks.lever.getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 0xA7 ^ 0xAF, -"  ".length(), 0x41 ^ 0x4D, structureBoundingBox);
            this.setBlockState(world, Blocks.lever.getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 0x33 ^ 0x3A, -"  ".length(), 0x63 ^ 0x6F, structureBoundingBox);
            this.setBlockState(world, Blocks.lever.getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))), 0x44 ^ 0x4E, -"  ".length(), 0x7E ^ 0x72, structureBoundingBox);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x2C ^ 0x24, -"   ".length(), 0x4B ^ 0x43, 0x7 ^ 0xF, -"   ".length(), 0x5D ^ 0x57, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 0x60 ^ 0x6A, -"   ".length(), 0x41 ^ 0x49, 0x62 ^ 0x68, -"   ".length(), 0x7 ^ 0xD, "".length() != 0, random, JunglePyramid.junglePyramidsRandomScatteredStones);
            this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 0x65 ^ 0x6F, -"  ".length(), 0x6 ^ 0xF, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0xCA ^ 0xC2, -"  ".length(), 0x7F ^ 0x76, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0x52 ^ 0x5A, -"  ".length(), 0x8F ^ 0x85, structureBoundingBox);
            this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 0xCB ^ 0xC1, -" ".length(), 0x66 ^ 0x6F, structureBoundingBox);
            this.setBlockState(world, Blocks.sticky_piston.getStateFromMeta(EnumFacing.UP.getIndex()), 0x79 ^ 0x70, -"  ".length(), 0xF ^ 0x7, structureBoundingBox);
            this.setBlockState(world, Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())), 0x56 ^ 0x5C, -"  ".length(), 0x60 ^ 0x68, structureBoundingBox);
            this.setBlockState(world, Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())), 0x94 ^ 0x9E, -" ".length(), 0x2E ^ 0x26, structureBoundingBox);
            this.setBlockState(world, Blocks.unpowered_repeater.getStateFromMeta(this.getMetadataWithOffset(Blocks.unpowered_repeater, EnumFacing.NORTH.getHorizontalIndex())), 0x82 ^ 0x88, -"  ".length(), 0x83 ^ 0x89, structureBoundingBox);
            if (!this.field_74948_i) {
                final int n3 = 0xCD ^ 0xC4;
                final int n4 = -"   ".length();
                final int n5 = 0x13 ^ 0x19;
                final List<WeightedRandomChestContent> field_175816_i2 = JunglePyramid.field_175816_i;
                final WeightedRandomChestContent[] array2 = new WeightedRandomChestContent[" ".length()];
                array2["".length()] = Items.enchanted_book.getRandom(random);
                this.field_74948_i = this.generateChestContents(world, structureBoundingBox, random, n3, n4, n5, WeightedRandomChestContent.func_177629_a(field_175816_i2, array2), "  ".length() + random.nextInt(0x72 ^ 0x77));
            }
            return " ".length() != 0;
        }
        
        public JunglePyramid(final Random random, final int n, final int n2) {
            super(random, n, 0x66 ^ 0x26, n2, 0xAE ^ 0xA2, 0x8A ^ 0x80, 0x35 ^ 0x3A);
        }
        
        static {
            I();
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0x5D ^ 0x57];
            array["".length()] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "   ".length(), "   ".length());
            array[" ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x61 ^ 0x64, 0x42 ^ 0x48);
            array["  ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), "  ".length(), 0xA0 ^ 0xA7, 0xB5 ^ 0xBA);
            array["   ".length()] = new WeightedRandomChestContent(Items.emerald, "".length(), " ".length(), "   ".length(), "  ".length());
            array[0x30 ^ 0x34] = new WeightedRandomChestContent(Items.bone, "".length(), 0xAD ^ 0xA9, 0xD ^ 0xB, 0xAE ^ 0xBA);
            array[0x88 ^ 0x8D] = new WeightedRandomChestContent(Items.rotten_flesh, "".length(), "   ".length(), 0x14 ^ 0x13, 0x53 ^ 0x43);
            array[0xA6 ^ 0xA0] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), "   ".length());
            array[0x35 ^ 0x32] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0x16 ^ 0x1E] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            array[0xE ^ 0x7] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), " ".length());
            field_175816_i = Lists.newArrayList((Object[])array);
            final WeightedRandomChestContent[] array2 = new WeightedRandomChestContent[" ".length()];
            array2["".length()] = new WeightedRandomChestContent(Items.arrow, "".length(), "  ".length(), 0x11 ^ 0x16, 0x32 ^ 0x2C);
            field_175815_j = Lists.newArrayList((Object[])array2);
            JunglePyramid.junglePyramidsRandomScatteredStones = new Stones(null);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0xCB ^ 0xC3])["".length()] = I("\u0007\r\u0010\"\r\u0013,\u0010(\u00064\t\u00142\u001c", "waqAh");
            JunglePyramid.I[" ".length()] = I("=$(\"\u0015)\u0000 %\u0014(&\n)\u0015><", "MHIAp");
            JunglePyramid.I["  ".length()] = I("=:;-6)\u0002(/#|", "MVZNS");
            JunglePyramid.I["   ".length()] = I("\u0019!\t\u0007<\r\u0019\u001a\u0005)[", "iMhdY");
            JunglePyramid.I[0xB9 ^ 0xBD] = I("\u0007\u001b8\u00120\u0013:8\u0018;4\u001f<\u0002!", "wwYqU");
            JunglePyramid.I[0x70 ^ 0x75] = I("\u0012\n8\u0010\u0004\u0006.0\u0017\u0005\u0007\b\u001a\u001b\u0004\u0011\u0012", "bfYsa");
            JunglePyramid.I[0xA3 ^ 0xA5] = I("\u0011\u001a\"\u0005#\u0005\"1\u00076P", "avCfF");
            JunglePyramid.I[0x46 ^ 0x41] = I("\u0017\u0006\r\u0005\n\u0003>\u001e\u0007\u001fU", "gjlfo");
        }
        
        public JunglePyramid() {
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_74947_h = nbtTagCompound.getBoolean(JunglePyramid.I[0x5F ^ 0x5B]);
            this.field_74948_i = nbtTagCompound.getBoolean(JunglePyramid.I[0x35 ^ 0x30]);
            this.field_74945_j = nbtTagCompound.getBoolean(JunglePyramid.I[0x8B ^ 0x8D]);
            this.field_74946_k = nbtTagCompound.getBoolean(JunglePyramid.I[0xBE ^ 0xB9]);
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(JunglePyramid.I["".length()], this.field_74947_h);
            nbtTagCompound.setBoolean(JunglePyramid.I[" ".length()], this.field_74948_i);
            nbtTagCompound.setBoolean(JunglePyramid.I["  ".length()], this.field_74945_j);
            nbtTagCompound.setBoolean(JunglePyramid.I["   ".length()], this.field_74946_k);
        }
        
        static class Stones extends BlockSelector
        {
            @Override
            public void selectBlocks(final Random random, final int n, final int n2, final int n3, final boolean b) {
                if (random.nextFloat() < 0.4f) {
                    this.blockstate = Blocks.cobblestone.getDefaultState();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    this.blockstate = Blocks.mossy_cobblestone.getDefaultState();
                }
            }
            
            private Stones() {
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            Stones(final Stones stones) {
                this();
            }
        }
    }
}
