package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class StructureNetherBridgePieces
{
    private static final String[] I;
    private static final PieceWeight[] secondaryComponents;
    private static final PieceWeight[] primaryComponents;
    
    static Piece access$0(final PieceWeight pieceWeight, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175887_b(pieceWeight, list, random, n, n2, n3, enumFacing, n4);
    }
    
    static PieceWeight[] access$1() {
        return StructureNetherBridgePieces.primaryComponents;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void registerNetherFortressPieces() {
        MapGenStructureIO.registerStructureComponent(Crossing3.class, StructureNetherBridgePieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(End.class, StructureNetherBridgePieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(Straight.class, StructureNetherBridgePieces.I["  ".length()]);
        MapGenStructureIO.registerStructureComponent(Corridor3.class, StructureNetherBridgePieces.I["   ".length()]);
        MapGenStructureIO.registerStructureComponent(Corridor4.class, StructureNetherBridgePieces.I[0x74 ^ 0x70]);
        MapGenStructureIO.registerStructureComponent(Entrance.class, StructureNetherBridgePieces.I[0x11 ^ 0x14]);
        MapGenStructureIO.registerStructureComponent(Crossing2.class, StructureNetherBridgePieces.I[0x3E ^ 0x38]);
        MapGenStructureIO.registerStructureComponent(Corridor.class, StructureNetherBridgePieces.I[0x85 ^ 0x82]);
        MapGenStructureIO.registerStructureComponent(Corridor5.class, StructureNetherBridgePieces.I[0xA8 ^ 0xA0]);
        MapGenStructureIO.registerStructureComponent(Corridor2.class, StructureNetherBridgePieces.I[0x50 ^ 0x59]);
        MapGenStructureIO.registerStructureComponent(NetherStalkRoom.class, StructureNetherBridgePieces.I[0x4D ^ 0x47]);
        MapGenStructureIO.registerStructureComponent(Throne.class, StructureNetherBridgePieces.I[0x53 ^ 0x58]);
        MapGenStructureIO.registerStructureComponent(Crossing.class, StructureNetherBridgePieces.I[0x45 ^ 0x49]);
        MapGenStructureIO.registerStructureComponent(Stairs.class, StructureNetherBridgePieces.I[0x9F ^ 0x92]);
        MapGenStructureIO.registerStructureComponent(Start.class, StructureNetherBridgePieces.I[0xB8 ^ 0xB6]);
    }
    
    static {
        I();
        final PieceWeight[] primaryComponents2 = new PieceWeight[0xAC ^ 0xAA];
        primaryComponents2["".length()] = new PieceWeight(Straight.class, 0xDE ^ 0xC0, "".length(), " ".length() != 0);
        primaryComponents2[" ".length()] = new PieceWeight(Crossing3.class, 0x15 ^ 0x1F, 0x5 ^ 0x1);
        primaryComponents2["  ".length()] = new PieceWeight(Crossing.class, 0x19 ^ 0x13, 0x16 ^ 0x12);
        primaryComponents2["   ".length()] = new PieceWeight(Stairs.class, 0x80 ^ 0x8A, "   ".length());
        primaryComponents2[0x4B ^ 0x4F] = new PieceWeight(Throne.class, 0x4F ^ 0x4A, "  ".length());
        primaryComponents2[0x74 ^ 0x71] = new PieceWeight(Entrance.class, 0x60 ^ 0x65, " ".length());
        primaryComponents = primaryComponents2;
        final PieceWeight[] secondaryComponents2 = new PieceWeight[0x2D ^ 0x2A];
        secondaryComponents2["".length()] = new PieceWeight(Corridor5.class, 0x0 ^ 0x19, "".length(), " ".length() != 0);
        secondaryComponents2[" ".length()] = new PieceWeight(Crossing2.class, 0x85 ^ 0x8A, 0x59 ^ 0x5C);
        secondaryComponents2["  ".length()] = new PieceWeight(Corridor2.class, 0xC2 ^ 0xC7, 0x2C ^ 0x26);
        secondaryComponents2["   ".length()] = new PieceWeight(Corridor.class, 0x29 ^ 0x2C, 0xF ^ 0x5);
        secondaryComponents2[0xC4 ^ 0xC0] = new PieceWeight(Corridor3.class, 0x3D ^ 0x37, "   ".length(), " ".length() != 0);
        secondaryComponents2[0xC1 ^ 0xC4] = new PieceWeight(Corridor4.class, 0x34 ^ 0x33, "  ".length());
        secondaryComponents2[0xA6 ^ 0xA0] = new PieceWeight(NetherStalkRoom.class, 0xBA ^ 0xBF, "  ".length());
        secondaryComponents = secondaryComponents2;
    }
    
    private static void I() {
        (I = new String[0xB5 ^ 0xBA])["".length()] = I(">6\u001b\u0010\u0015", "pSYSg");
        StructureNetherBridgePieces.I[" ".length()] = I("\u0001\u0017 \u001c?", "OrbYy");
        StructureNetherBridgePieces.I["  ".length()] = I("?,\u000e ", "qILsW");
        StructureNetherBridgePieces.I["   ".length()] = I("862\u00151", "vSqVb");
        StructureNetherBridgePieces.I[0x68 ^ 0x6C] = I("7\u0014\u00015\f", "yqBaN");
        StructureNetherBridgePieces.I[0x29 ^ 0x2C] = I("\u001a\u0012\u0015\u0011", "TwVTX");
        StructureNetherBridgePieces.I[0x97 ^ 0x91] = I("86\u0007\u001a\u00025", "vSTYQ");
        StructureNetherBridgePieces.I[0x0 ^ 0x7] = I("?\u0014\u00044\u0016%", "qqWwZ");
        StructureNetherBridgePieces.I[0x53 ^ 0x5B] = I("=6)\u0005", "sSzFz");
        StructureNetherBridgePieces.I[0x60 ^ 0x69] = I("\f\u0015=(:\u0016", "Bpnkh");
        StructureNetherBridgePieces.I[0x7B ^ 0x71] = I("*\u001d\u000e\u0011\u000b", "dxMBY");
        StructureNetherBridgePieces.I[0x3 ^ 0x8] = I("\u001d)\u000b&", "SLFrB");
        StructureNetherBridgePieces.I[0xB8 ^ 0xB4] = I("&<\u0010\u0019", "hYBZb");
        StructureNetherBridgePieces.I[0x2C ^ 0x21] = I("\f3\"\b", "BVqZm");
        StructureNetherBridgePieces.I[0x80 ^ 0x8E] = I("*0\u0000,\u0019\u0016!", "dUSXx");
    }
    
    static PieceWeight[] access$2() {
        return StructureNetherBridgePieces.secondaryComponents;
    }
    
    private static Piece func_175887_b(final PieceWeight pieceWeight, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final Class<? extends Piece> weightClass = pieceWeight.weightClass;
        Piece piece = null;
        if (weightClass == Straight.class) {
            piece = Straight.func_175882_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (weightClass == Crossing3.class) {
            piece = Crossing3.func_175885_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (weightClass == Crossing.class) {
            piece = Crossing.func_175873_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else if (weightClass == Stairs.class) {
            piece = Stairs.func_175872_a(list, random, n, n2, n3, n4, enumFacing);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else if (weightClass == Throne.class) {
            piece = Throne.func_175874_a(list, random, n, n2, n3, n4, enumFacing);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (weightClass == Entrance.class) {
            piece = Entrance.func_175881_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (weightClass == Corridor5.class) {
            piece = Corridor5.func_175877_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (weightClass == Corridor2.class) {
            piece = Corridor2.func_175876_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else if (weightClass == Corridor.class) {
            piece = Corridor.func_175879_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (weightClass == Corridor3.class) {
            piece = Corridor3.func_175883_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else if (weightClass == Corridor4.class) {
            piece = Corridor4.func_175880_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (weightClass == Crossing2.class) {
            piece = Crossing2.func_175878_a(list, random, n, n2, n3, enumFacing, n4);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (weightClass == NetherStalkRoom.class) {
            piece = NetherStalkRoom.func_175875_a(list, random, n, n2, n3, enumFacing, n4);
        }
        return piece;
    }
    
    public static class Crossing3 extends Piece
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        public static Crossing3 func_175885_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0x51 ^ 0x59), -"   ".length(), "".length(), 0x76 ^ 0x65, 0x49 ^ 0x43, 0x95 ^ 0x86, enumFacing);
            Crossing3 crossing3;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                crossing3 = new Crossing3(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                crossing3 = null;
            }
            return crossing3;
        }
        
        protected Crossing3(final Random random, final int n, final int n2) {
            super("".length());
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                case 3:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n, 0x3A ^ 0x7A, n2, n + (0x75 ^ 0x66) - " ".length(), 0x41 ^ 0x8, n2 + (0x81 ^ 0x92) - " ".length());
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, 0x70 ^ 0x30, n2, n + (0xAA ^ 0xB9) - " ".length(), 0xF2 ^ 0xBB, n2 + (0x12 ^ 0x1) - " ".length());
                    break;
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, 0xF ^ 0x8, "   ".length(), "".length(), 0xA9 ^ 0xA2, 0x5 ^ 0x1, 0xBC ^ 0xAE, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x53 ^ 0x54, 0x88 ^ 0x9A, 0x71 ^ 0x75, 0xA0 ^ 0xAB, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x93 ^ 0x9B, 0x32 ^ 0x37, "".length(), 0x3D ^ 0x37, 0x86 ^ 0x81, 0xB5 ^ 0xA7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x4A ^ 0x4F, 0x6A ^ 0x62, 0x26 ^ 0x34, 0x9B ^ 0x9C, 0x26 ^ 0x2C, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAE ^ 0xA9, 0x3D ^ 0x38, "".length(), 0x68 ^ 0x6F, 0x9B ^ 0x9E, 0x23 ^ 0x24, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6A ^ 0x6D, 0x83 ^ 0x86, 0x8F ^ 0x84, 0x49 ^ 0x4E, 0xA ^ 0xF, 0x94 ^ 0x86, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x51 ^ 0x5A, 0x8B ^ 0x8E, "".length(), 0x8 ^ 0x3, 0x59 ^ 0x5C, 0x8B ^ 0x8C, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x14 ^ 0x1F, 0x4B ^ 0x4E, 0x4F ^ 0x44, 0x3A ^ 0x31, 0xC3 ^ 0xC6, 0x32 ^ 0x20, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x12 ^ 0x17, 0xB4 ^ 0xB3, 0x46 ^ 0x41, 0x58 ^ 0x5D, 0x2 ^ 0x5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x81 ^ 0x8A, 0x4A ^ 0x4F, 0x1B ^ 0x1C, 0xD ^ 0x1F, 0x75 ^ 0x70, 0x10 ^ 0x17, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xC4 ^ 0xC1, 0xB4 ^ 0xBF, 0x8 ^ 0xF, 0x45 ^ 0x40, 0x1E ^ 0x15, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x15 ^ 0x1E, 0x22 ^ 0x27, 0xB9 ^ 0xB2, 0x9F ^ 0x8D, 0xB7 ^ 0xB2, 0x7F ^ 0x74, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x57 ^ 0x50, "  ".length(), "".length(), 0xB3 ^ 0xB8, "  ".length(), 0xAE ^ 0xAB, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7B ^ 0x7C, "  ".length(), 0x46 ^ 0x4B, 0xBA ^ 0xB1, "  ".length(), 0x8A ^ 0x98, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x42 ^ 0x45, "".length(), "".length(), 0x5F ^ 0x54, " ".length(), "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x92 ^ 0x95, "".length(), 0x65 ^ 0x6A, 0x36 ^ 0x3D, " ".length(), 0x9F ^ 0x8D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = 0x51 ^ 0x56;
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (i <= (0xA8 ^ 0xA3)) {
                int j = "".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (j <= "  ".length()) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), (0x1E ^ 0xC) - j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x93 ^ 0x94, 0xBB ^ 0xBE, "  ".length(), 0x26 ^ 0x2D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x36 ^ 0x3B, "  ".length(), 0x8A ^ 0x8D, 0x32 ^ 0x20, "  ".length(), 0x96 ^ 0x9D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0x27 ^ 0x20, "   ".length(), " ".length(), 0xB8 ^ 0xB3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x58 ^ 0x57, "".length(), 0x47 ^ 0x40, 0x23 ^ 0x31, " ".length(), 0x0 ^ 0xB, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int k = "".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
            while (k <= "  ".length()) {
                int l = 0x95 ^ 0x92;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (l <= (0xA6 ^ 0xAD)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), k, -" ".length(), l, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), (0x6B ^ 0x79) - k, -" ".length(), l, structureBoundingBox);
                    ++l;
                }
                ++k;
            }
            return " ".length() != 0;
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
                if (0 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 0x15 ^ 0x1D, "   ".length(), "".length() != 0);
            this.getNextComponentX((Start)structureComponent, list, random, "   ".length(), 0x22 ^ 0x2A, "".length() != 0);
            this.getNextComponentZ((Start)structureComponent, list, random, "   ".length(), 0x43 ^ 0x4B, "".length() != 0);
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Crossing3.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x30 ^ 0x36);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x93 ^ 0x97);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x53 ^ 0x56);
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Crossing3.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        public Crossing3() {
        }
        
        public Crossing3(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Start extends Crossing3
    {
        public List<PieceWeight> primaryWeights;
        public List<StructureComponent> field_74967_d;
        public PieceWeight theNetherBridgePieceWeight;
        public List<PieceWeight> secondaryWeights;
        
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
                if (1 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Start(final Random random, final int n, final int n2) {
            super(random, n, n2);
            this.field_74967_d = (List<StructureComponent>)Lists.newArrayList();
            this.primaryWeights = (List<PieceWeight>)Lists.newArrayList();
            final PieceWeight[] access$1;
            final int length = (access$1 = StructureNetherBridgePieces.access$1()).length;
            int i = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (i < length) {
                final PieceWeight pieceWeight = access$1[i];
                pieceWeight.field_78827_c = "".length();
                this.primaryWeights.add(pieceWeight);
                ++i;
            }
            this.secondaryWeights = (List<PieceWeight>)Lists.newArrayList();
            final PieceWeight[] access$2;
            final int length2 = (access$2 = StructureNetherBridgePieces.access$2()).length;
            int j = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (j < length2) {
                final PieceWeight pieceWeight2 = access$2[j];
                pieceWeight2.field_78827_c = "".length();
                this.secondaryWeights.add(pieceWeight2);
                ++j;
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
        }
        
        public Start() {
            this.field_74967_d = (List<StructureComponent>)Lists.newArrayList();
        }
    }
    
    static class PieceWeight
    {
        public int field_78827_c;
        public boolean field_78825_e;
        public Class<? extends Piece> weightClass;
        public int field_78824_d;
        public final int field_78826_b;
        
        public PieceWeight(final Class<? extends Piece> clazz, final int n, final int n2) {
            this(clazz, n, n2, "".length() != 0);
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
                if (1 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public PieceWeight(final Class<? extends Piece> weightClass, final int field_78826_b, final int field_78824_d, final boolean field_78825_e) {
            this.weightClass = weightClass;
            this.field_78826_b = field_78826_b;
            this.field_78824_d = field_78824_d;
            this.field_78825_e = field_78825_e;
        }
        
        public boolean func_78822_a(final int n) {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public boolean func_78823_a() {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
    }
    
    abstract static class Piece extends StructureComponent
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        protected static final List<WeightedRandomChestContent> field_111019_a;
        
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
        
        protected StructureComponent getNextComponentX(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.WEST, this.getComponentType(), b);
                    }
                    case 5: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType(), b);
                    }
                    case 6: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - " ".length(), EnumFacing.NORTH, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
        
        protected Piece(final int n) {
            super(n);
        }
        
        private int getTotalWeight(final List<PieceWeight> list) {
            int n = "".length();
            int length = "".length();
            final Iterator<PieceWeight> iterator = list.iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final PieceWeight pieceWeight = iterator.next();
                if (pieceWeight.field_78824_d > 0 && pieceWeight.field_78827_c < pieceWeight.field_78824_d) {
                    n = " ".length();
                }
                length += pieceWeight.field_78826_b;
            }
            int n2;
            if (n != 0) {
                n2 = length;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n2 = -" ".length();
            }
            return n2;
        }
        
        private StructureComponent func_175870_a(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4, final boolean b) {
            if (Math.abs(n - start.getBoundingBox().minX) <= (0xDC ^ 0xAC) && Math.abs(n3 - start.getBoundingBox().minZ) <= (0xB6 ^ 0xC6)) {
                List<PieceWeight> list2 = start.primaryWeights;
                if (b) {
                    list2 = start.secondaryWeights;
                }
                final Piece func_175871_a = this.func_175871_a(start, list2, list, random, n, n2, n3, enumFacing, n4 + " ".length());
                if (func_175871_a != null) {
                    list.add(func_175871_a);
                    start.field_74967_d.add(func_175871_a);
                }
                return func_175871_a;
            }
            return End.func_175884_a(list, random, n, n2, n3, enumFacing, n4);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        static {
            final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0xA9 ^ 0xA5];
            array["".length()] = new WeightedRandomChestContent(Items.diamond, "".length(), " ".length(), "   ".length(), 0x25 ^ 0x20);
            array[" ".length()] = new WeightedRandomChestContent(Items.iron_ingot, "".length(), " ".length(), 0x85 ^ 0x80, 0xBE ^ 0xBB);
            array["  ".length()] = new WeightedRandomChestContent(Items.gold_ingot, "".length(), " ".length(), "   ".length(), 0x25 ^ 0x2A);
            array["   ".length()] = new WeightedRandomChestContent(Items.golden_sword, "".length(), " ".length(), " ".length(), 0x2F ^ 0x2A);
            array[0xA2 ^ 0xA6] = new WeightedRandomChestContent(Items.golden_chestplate, "".length(), " ".length(), " ".length(), 0x25 ^ 0x20);
            array[0x62 ^ 0x67] = new WeightedRandomChestContent(Items.flint_and_steel, "".length(), " ".length(), " ".length(), 0xBD ^ 0xB8);
            array[0x97 ^ 0x91] = new WeightedRandomChestContent(Items.nether_wart, "".length(), "   ".length(), 0x43 ^ 0x44, 0x72 ^ 0x77);
            array[0x8D ^ 0x8A] = new WeightedRandomChestContent(Items.saddle, "".length(), " ".length(), " ".length(), 0x12 ^ 0x18);
            array[0x82 ^ 0x8A] = new WeightedRandomChestContent(Items.golden_horse_armor, "".length(), " ".length(), " ".length(), 0x3B ^ 0x33);
            array[0x96 ^ 0x9F] = new WeightedRandomChestContent(Items.iron_horse_armor, "".length(), " ".length(), " ".length(), 0xA5 ^ 0xA0);
            array[0x46 ^ 0x4C] = new WeightedRandomChestContent(Items.diamond_horse_armor, "".length(), " ".length(), " ".length(), "   ".length());
            array[0x39 ^ 0x32] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), "".length(), "  ".length(), 0x22 ^ 0x26, "  ".length());
            field_111019_a = Lists.newArrayList((Object[])array);
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = Piece.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x4E ^ 0x48);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x1F ^ 0x1B);
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xBC ^ 0xB9);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Piece.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        protected static boolean isAboveGround(final StructureBoundingBox structureBoundingBox) {
            if (structureBoundingBox != null && structureBoundingBox.minY > (0xBC ^ 0xB6)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public Piece() {
        }
        
        private Piece func_175871_a(final Start start, final List<PieceWeight> list, final List<StructureComponent> list2, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final int totalWeight = this.getTotalWeight(list);
            int n5;
            if (totalWeight > 0 && n4 <= (0x38 ^ 0x26)) {
                n5 = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            final int n6 = n5;
            int length = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (length < (0x4F ^ 0x4A) && n6 != 0) {
                ++length;
                int nextInt = random.nextInt(totalWeight);
                final Iterator<PieceWeight> iterator = list.iterator();
                "".length();
                if (4 < -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final PieceWeight theNetherBridgePieceWeight = iterator.next();
                    nextInt -= theNetherBridgePieceWeight.field_78826_b;
                    if (nextInt < 0) {
                        if (!theNetherBridgePieceWeight.func_78822_a(n4)) {
                            break;
                        }
                        if (theNetherBridgePieceWeight == start.theNetherBridgePieceWeight && !theNetherBridgePieceWeight.field_78825_e) {
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            final Piece access$0 = StructureNetherBridgePieces.access$0(theNetherBridgePieceWeight, list2, random, n, n2, n3, enumFacing, n4);
                            if (access$0 != null) {
                                final PieceWeight pieceWeight = theNetherBridgePieceWeight;
                                pieceWeight.field_78827_c += " ".length();
                                start.theNetherBridgePieceWeight = theNetherBridgePieceWeight;
                                if (!theNetherBridgePieceWeight.func_78823_a()) {
                                    list.remove(theNetherBridgePieceWeight);
                                }
                                return access$0;
                            }
                            continue;
                        }
                    }
                }
            }
            return End.func_175884_a(list2, random, n, n2, n3, enumFacing, n4);
        }
        
        protected StructureComponent getNextComponentNormal(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - " ".length(), this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + " ".length(), this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 5: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX - " ".length(), this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType(), b);
                    }
                    case 6: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n2, this.boundingBox.minZ + n, this.coordBaseMode, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent getNextComponentZ(final Start start, final List<StructureComponent> list, final Random random, final int n, final int n2, final boolean b) {
            if (this.coordBaseMode != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                    case 3: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType(), b);
                    }
                    case 4: {
                        return this.func_175870_a(start, list, random, this.boundingBox.maxX + " ".length(), this.boundingBox.minY + n, this.boundingBox.minZ + n2, EnumFacing.EAST, this.getComponentType(), b);
                    }
                    case 5: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType(), b);
                    }
                    case 6: {
                        return this.func_175870_a(start, list, random, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + " ".length(), EnumFacing.SOUTH, this.getComponentType(), b);
                    }
                }
            }
            return null;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
    }
    
    public static class End extends Piece
    {
        private static final String[] I;
        private int fillSeed;
        
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
                if (2 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final Random random2 = new Random(this.fillSeed);
            int i = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (i <= (0x6D ^ 0x69)) {
                int j = "   ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (j <= (0x97 ^ 0x93)) {
                    this.fillWithBlocks(world, structureBoundingBox, i, j, "".length(), i, j, random2.nextInt(0x8C ^ 0x84), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x82 ^ 0x87, "".length(), "".length(), 0x95 ^ 0x90, random2.nextInt(0x36 ^ 0x3E), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5C ^ 0x58, 0x4D ^ 0x48, "".length(), 0x8B ^ 0x8F, 0xC2 ^ 0xC7, random2.nextInt(0x68 ^ 0x60), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int k = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (k <= (0x80 ^ 0x84)) {
                this.fillWithBlocks(world, structureBoundingBox, k, "  ".length(), "".length(), k, "  ".length(), random2.nextInt(0x58 ^ 0x5D), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                ++k;
            }
            int l = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (l <= (0x64 ^ 0x60)) {
                int length = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (length <= " ".length()) {
                    this.fillWithBlocks(world, structureBoundingBox, l, length, "".length(), l, length, random2.nextInt("   ".length()), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                    ++length;
                }
                ++l;
            }
            return " ".length() != 0;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\"\u000f6\u001d", "qjSyE");
            End.I[" ".length()] = I("\u0001$1\"", "RATFH");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setInteger(End.I[" ".length()], this.fillSeed);
        }
        
        public End(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.fillSeed = random.nextInt();
        }
        
        public static End func_175884_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -"   ".length(), "".length(), 0xAE ^ 0xAB, 0x42 ^ 0x48, 0x7C ^ 0x74, enumFacing);
            End end;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                end = new End(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                end = null;
            }
            return end;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.fillSeed = nbtTagCompound.getInteger(End.I["".length()]);
        }
        
        public End() {
        }
    }
    
    public static class Corridor2 extends Piece
    {
        private static final String[] I;
        private boolean field_111020_b;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0xB9 ^ 0xBD, " ".length(), 0x26 ^ 0x22, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x5E ^ 0x5A, 0x6 ^ 0x3, 0x19 ^ 0x1D, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0x8E ^ 0x8B, 0xA4 ^ 0xA0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), " ".length(), "".length(), 0x64 ^ 0x60, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "   ".length(), "".length(), 0x20 ^ 0x24, "   ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x8D ^ 0x89, "  ".length(), "".length(), 0xA5 ^ 0xA1, 0xA1 ^ 0xA4, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0xA0 ^ 0xA4, 0x63 ^ 0x67, 0x54 ^ 0x51, 0x76 ^ 0x72, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x59 ^ 0x5D, " ".length(), 0x7A ^ 0x7E, 0x2 ^ 0x6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), 0x7B ^ 0x7F, "   ".length(), 0x60 ^ 0x64, 0x55 ^ 0x51, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            if (this.field_111020_b && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(" ".length(), "   ".length()), this.getYWithOffset("  ".length()), this.getZWithOffset(" ".length(), "   ".length())))) {
                this.field_111020_b = ("".length() != 0);
                this.generateChestContents(world, structureBoundingBox, random, " ".length(), "  ".length(), "   ".length(), Corridor2.field_111019_a, "  ".length() + random.nextInt(0xB3 ^ 0xB7));
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x19 ^ 0x1F, "".length(), 0x89 ^ 0x8D, 0x49 ^ 0x4F, 0x2B ^ 0x2F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (i <= (0x72 ^ 0x76)) {
                int j = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (j <= (0xB3 ^ 0xB7)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public Corridor2(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            int field_111020_b;
            if (random.nextInt("   ".length()) == 0) {
                field_111020_b = " ".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                field_111020_b = "".length();
            }
            this.field_111020_b = (field_111020_b != 0);
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\b)2\u001b=", "KAWhI");
            Corridor2.I[" ".length()] = I("\u0007<\u0014\u0015\u000e", "DTqfz");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Corridor2.I[" ".length()], this.field_111020_b);
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
                if (0 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentZ((Start)structureComponent, list, random, "".length(), " ".length(), " ".length() != 0);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_111020_b = nbtTagCompound.getBoolean(Corridor2.I["".length()]);
        }
        
        public static Corridor2 func_175876_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), "".length(), "".length(), 0x35 ^ 0x30, 0xA5 ^ 0xA2, 0x73 ^ 0x76, enumFacing);
            Corridor2 corridor2;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                corridor2 = new Corridor2(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                corridor2 = null;
            }
            return corridor2;
        }
        
        public Corridor2() {
        }
        
        static {
            I();
        }
    }
    
    public static class Crossing2 extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0xC0 ^ 0xC4, " ".length(), 0xAB ^ 0xAF, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x83 ^ 0x87, 0xAA ^ 0xAF, 0x27 ^ 0x23, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0x18 ^ 0x1D, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBD ^ 0xB9, "  ".length(), "".length(), 0xC4 ^ 0xC0, 0x4B ^ 0x4E, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x6A ^ 0x6E, "".length(), 0x9 ^ 0xC, 0x68 ^ 0x6C, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA1 ^ 0xA5, "  ".length(), 0x47 ^ 0x43, 0x16 ^ 0x12, 0xA9 ^ 0xAC, 0x9 ^ 0xD, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x5B ^ 0x5D, "".length(), 0x3A ^ 0x3E, 0x28 ^ 0x2E, 0x8 ^ 0xC, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
            while (i <= (0x65 ^ 0x61)) {
                int j = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
                while (j <= (0x5D ^ 0x59)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Crossing2(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public Crossing2() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, " ".length(), "".length(), " ".length() != 0);
            this.getNextComponentX((Start)structureComponent, list, random, "".length(), " ".length(), " ".length() != 0);
            this.getNextComponentZ((Start)structureComponent, list, random, "".length(), " ".length(), " ".length() != 0);
        }
        
        public static Crossing2 func_175878_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), "".length(), "".length(), 0x26 ^ 0x23, 0x39 ^ 0x3E, 0x6B ^ 0x6E, enumFacing);
            Crossing2 crossing2;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                crossing2 = new Crossing2(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                crossing2 = null;
            }
            return crossing2;
        }
    }
    
    public static class Straight extends Piece
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, " ".length(), "   ".length(), "".length() != 0);
        }
        
        public Straight() {
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
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), 0x5D ^ 0x59, 0x48 ^ 0x4C, 0x5F ^ 0x4D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xC0 ^ 0xC5, "".length(), "   ".length(), 0xAF ^ 0xA8, 0x95 ^ 0x87, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x6B ^ 0x6E, "".length(), "".length(), 0x56 ^ 0x53, 0x2F ^ 0x3D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2E ^ 0x2A, 0x59 ^ 0x5C, "".length(), 0x85 ^ 0x81, 0x19 ^ 0x1C, 0x6E ^ 0x7C, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0xB3 ^ 0xB7, "  ".length(), 0xC4 ^ 0xC1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x7E ^ 0x73, 0x24 ^ 0x20, "  ".length(), 0x36 ^ 0x24, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x43 ^ 0x47, " ".length(), "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0xA4 ^ 0xAB, 0x40 ^ 0x44, " ".length(), 0x88 ^ 0x9A, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (i <= (0x96 ^ 0x92)) {
                int j = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (j <= "  ".length()) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), (0xB9 ^ 0xAB) - j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), 0x38 ^ 0x3C, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0xA7 ^ 0xA3, "".length(), 0xBF ^ 0xBB, 0x15 ^ 0x11, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x31 ^ 0x3F, "".length(), 0x59 ^ 0x5D, 0x88 ^ 0x86, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x4C ^ 0x5D, "".length(), 0x48 ^ 0x4C, 0x7D ^ 0x6C, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7 ^ 0x3, " ".length(), " ".length(), 0xC0 ^ 0xC4, 0xA1 ^ 0xA5, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAA ^ 0xAE, "   ".length(), 0x73 ^ 0x77, 0x22 ^ 0x26, 0x84 ^ 0x80, 0x47 ^ 0x43, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x19 ^ 0x1D, "   ".length(), 0x8 ^ 0x6, 0x3 ^ 0x7, 0xAB ^ 0xAF, 0x9 ^ 0x7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x47 ^ 0x43, " ".length(), 0x97 ^ 0x86, 0x8A ^ 0x8E, 0x67 ^ 0x63, 0x26 ^ 0x37, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            return " ".length() != 0;
        }
        
        public Straight(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public static Straight func_175882_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -"   ".length(), "".length(), 0xAE ^ 0xAB, 0x94 ^ 0x9E, 0x26 ^ 0x35, enumFacing);
            Straight straight;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                straight = new Straight(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                straight = null;
            }
            return straight;
        }
    }
    
    public static class Crossing extends Piece
    {
        public Crossing(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public Crossing() {
        }
        
        public static Crossing func_175873_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -"  ".length(), "".length(), "".length(), 0xA7 ^ 0xA0, 0xA8 ^ 0xA1, 0xB4 ^ 0xB3, enumFacing);
            Crossing crossing;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                crossing = new Crossing(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                crossing = null;
            }
            return crossing;
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, "  ".length(), "".length(), "".length() != 0);
            this.getNextComponentX((Start)structureComponent, list, random, "".length(), "  ".length(), "".length() != 0);
            this.getNextComponentZ((Start)structureComponent, list, random, "".length(), "  ".length(), "".length() != 0);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x1F ^ 0x19, " ".length(), 0x53 ^ 0x55, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x13 ^ 0x15, 0x1D ^ 0x1A, 0x1B ^ 0x1D, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), " ".length(), 0x32 ^ 0x34, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0xB8 ^ 0xBE, " ".length(), 0x75 ^ 0x73, 0x9A ^ 0x9C, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC0 ^ 0xC5, "  ".length(), "".length(), 0x8D ^ 0x8B, 0xB ^ 0xD, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x78 ^ 0x7D, "  ".length(), 0xB5 ^ 0xB3, 0x53 ^ 0x55, 0x4E ^ 0x48, 0x71 ^ 0x77, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0x85 ^ 0x83, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x99 ^ 0x9C, "".length(), 0x97 ^ 0x91, 0x72 ^ 0x74, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4 ^ 0x2, "  ".length(), "".length(), 0x80 ^ 0x86, 0x1D ^ 0x1B, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x24 ^ 0x22, "  ".length(), 0x93 ^ 0x96, 0x8 ^ 0xE, 0x41 ^ 0x47, 0x48 ^ 0x4E, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x2B ^ 0x2D, "".length(), 0x48 ^ 0x4C, 0x3C ^ 0x3A, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x4A ^ 0x4F, "".length(), 0x4E ^ 0x4A, 0xB3 ^ 0xB6, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x43 ^ 0x45, 0x42 ^ 0x44, 0x52 ^ 0x56, 0x5D ^ 0x5B, 0x9C ^ 0x9A, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x51 ^ 0x54, 0x74 ^ 0x72, 0x7C ^ 0x78, 0x27 ^ 0x22, 0x1F ^ 0x19, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x15 ^ 0x13, "  ".length(), "".length(), 0x37 ^ 0x31, 0x1F ^ 0x1B, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xAA ^ 0xAF, "  ".length(), "".length(), 0x4B ^ 0x4E, 0x39 ^ 0x3D, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB1 ^ 0xB7, 0xA8 ^ 0xAE, "  ".length(), 0x4D ^ 0x4B, 0x48 ^ 0x4E, 0x34 ^ 0x30, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7F ^ 0x79, 0xA ^ 0xF, "  ".length(), 0xA ^ 0xC, 0x2 ^ 0x7, 0x37 ^ 0x33, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i <= (0x68 ^ 0x6E)) {
                int j = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (j <= (0x3F ^ 0x39)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
    }
    
    public static class Corridor extends Piece
    {
        private boolean field_111021_b;
        private static final String[] I;
        
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x4A ^ 0x4E, " ".length(), 0xA8 ^ 0xAC, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0xC ^ 0x8, 0x12 ^ 0x17, 0x0 ^ 0x4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x42 ^ 0x46, "  ".length(), "".length(), 0xB8 ^ 0xBC, 0x24 ^ 0x21, 0x79 ^ 0x7D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6C ^ 0x68, "   ".length(), " ".length(), 0x43 ^ 0x47, 0x64 ^ 0x60, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x64 ^ 0x60, "   ".length(), "   ".length(), 0x27 ^ 0x23, 0xBB ^ 0xBF, "   ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0xAD ^ 0xA8, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0xAA ^ 0xAE, "   ".length(), 0xC0 ^ 0xC5, 0xA8 ^ 0xAC, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x72 ^ 0x76, " ".length(), 0x5A ^ 0x5E, 0x13 ^ 0x17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), 0x50 ^ 0x54, "   ".length(), 0x13 ^ 0x17, 0xB8 ^ 0xBC, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            if (this.field_111021_b && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset("   ".length(), "   ".length()), this.getYWithOffset("  ".length()), this.getZWithOffset("   ".length(), "   ".length())))) {
                this.field_111021_b = ("".length() != 0);
                this.generateChestContents(world, structureBoundingBox, random, "   ".length(), "  ".length(), "   ".length(), Corridor.field_111019_a, "  ".length() + random.nextInt(0xBF ^ 0xBB));
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xC4 ^ 0xC2, "".length(), 0x4A ^ 0x4E, 0x8A ^ 0x8C, 0x23 ^ 0x27, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i <= (0x84 ^ 0x80)) {
                int j = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (j <= (0xAC ^ 0xA8)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.field_111021_b = nbtTagCompound.getBoolean(Corridor.I["".length()]);
        }
        
        public Corridor() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentX((Start)structureComponent, list, random, "".length(), " ".length(), " ".length() != 0);
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u0001\u0000\u000b!\u0007", "BhnRs");
            Corridor.I[" ".length()] = I("60\u001f\u001b\u0001", "uXzhu");
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Corridor.I[" ".length()], this.field_111021_b);
        }
        
        public static Corridor func_175879_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), "".length(), "".length(), 0x3 ^ 0x6, 0x4C ^ 0x4B, 0xA6 ^ 0xA3, enumFacing);
            Corridor corridor;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                corridor = new Corridor(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                corridor = null;
            }
            return corridor;
        }
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            int field_111021_b;
            if (random.nextInt("   ".length()) == 0) {
                field_111021_b = " ".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                field_111021_b = "".length();
            }
            this.field_111021_b = (field_111021_b != 0);
        }
    }
    
    public static class Corridor3 extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.nether_brick_stairs, "  ".length());
            int i = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (i <= (0x9B ^ 0x92)) {
                final int max = Math.max(" ".length(), (0xAB ^ 0xAC) - i);
                final int min = Math.min(Math.max(max + (0x94 ^ 0x91), (0x3D ^ 0x33) - i), 0xA ^ 0x7);
                final int n = i;
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), i, 0x5D ^ 0x59, max, i, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), max + " ".length(), i, "   ".length(), min - " ".length(), i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                if (i <= (0x5B ^ 0x5D)) {
                    this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), " ".length(), max + " ".length(), i, structureBoundingBox);
                    this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), "  ".length(), max + " ".length(), i, structureBoundingBox);
                    this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), "   ".length(), max + " ".length(), i, structureBoundingBox);
                }
                this.fillWithBlocks(world, structureBoundingBox, "".length(), min, i, 0xC7 ^ 0xC3, min, i, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), max + " ".length(), i, "".length(), min - " ".length(), i, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x71 ^ 0x75, max + " ".length(), i, 0x43 ^ 0x47, min - " ".length(), i, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                if ((i & " ".length()) == 0x0) {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), max + "  ".length(), i, "".length(), max + "   ".length(), i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xB7, max + "  ".length(), i, 0x0 ^ 0x4, max + "   ".length(), i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                }
                int j = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                while (j <= (0xA7 ^ 0xA3)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), j, -" ".length(), n, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public Corridor3(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, " ".length(), "".length(), " ".length() != 0);
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static Corridor3 func_175883_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), -(0xD ^ 0xA), "".length(), 0xA4 ^ 0xA1, 0x6B ^ 0x65, 0x0 ^ 0xA, enumFacing);
            Corridor3 corridor3;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                corridor3 = new Corridor3(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                corridor3 = null;
            }
            return corridor3;
        }
        
        public Corridor3() {
        }
    }
    
    public static class Stairs extends Piece
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentZ((Start)structureComponent, list, random, 0xBC ^ 0xBA, "  ".length(), "".length() != 0);
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0xD ^ 0xB, " ".length(), 0x41 ^ 0x47, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x4B ^ 0x4D, 0x52 ^ 0x58, 0x7B ^ 0x7D, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), " ".length(), 0xBD ^ 0xB5, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6 ^ 0x3, "  ".length(), "".length(), 0x9A ^ 0x9C, 0x2 ^ 0xA, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), " ".length(), "".length(), 0xC ^ 0x4, 0x9C ^ 0x9A, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA2 ^ 0xA4, "  ".length(), " ".length(), 0x98 ^ 0x9E, 0x51 ^ 0x59, 0x86 ^ 0x80, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0xA7 ^ 0xA1, 0x74 ^ 0x71, 0x9F ^ 0x97, 0x5F ^ 0x59, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "  ".length(), "".length(), 0x20 ^ 0x25, 0x92 ^ 0x96, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9F ^ 0x99, "   ".length(), "  ".length(), 0x9C ^ 0x9A, 0x93 ^ 0x96, "  ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1E ^ 0x18, "   ".length(), 0x3C ^ 0x38, 0x4 ^ 0x2, 0xBA ^ 0xBF, 0x78 ^ 0x7C, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0x97 ^ 0x92, "  ".length(), 0xA3 ^ 0xA6, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0xA2 ^ 0xA6, "  ".length(), 0x62 ^ 0x67, 0x20 ^ 0x24, "   ".length(), 0x2 ^ 0x7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), 0x7D ^ 0x78, "   ".length(), 0x28 ^ 0x2C, 0x3 ^ 0x6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "  ".length(), 0x2C ^ 0x29, "  ".length(), 0x45 ^ 0x40, 0xC7 ^ 0xC2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x71 ^ 0x74, " ".length(), 0x15 ^ 0x13, 0x9A ^ 0x9F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x7 ^ 0x0, " ".length(), 0x83 ^ 0x86, 0x69 ^ 0x6E, 0xA2 ^ 0xA6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA0 ^ 0xA6, 0x45 ^ 0x4D, "  ".length(), 0xB5 ^ 0xB3, 0x7F ^ 0x77, 0x26 ^ 0x22, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x8B ^ 0x8D, "".length(), 0x92 ^ 0x96, 0xB9 ^ 0xB1, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x9E ^ 0x9B, "".length(), 0x36 ^ 0x32, 0x44 ^ 0x41, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i <= (0x23 ^ 0x25)) {
                int j = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (j <= (0x97 ^ 0x91)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public static Stairs func_175872_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final int n4, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -"  ".length(), "".length(), "".length(), 0x66 ^ 0x61, 0x3C ^ 0x37, 0x34 ^ 0x33, enumFacing);
            Stairs stairs;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                stairs = new Stairs(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                stairs = null;
            }
            return stairs;
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Stairs() {
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Corridor5 extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x41 ^ 0x45, " ".length(), 0x9B ^ 0x9F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x3D ^ 0x39, 0x3E ^ 0x3B, 0x6A ^ 0x6E, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), 0x80 ^ 0x85, 0x69 ^ 0x6D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x19 ^ 0x1D, "  ".length(), "".length(), 0x72 ^ 0x76, 0x4D ^ 0x48, 0x92 ^ 0x96, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), " ".length(), "".length(), 0x80 ^ 0x84, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "   ".length(), "".length(), 0x7A ^ 0x7E, "   ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x31 ^ 0x35, "   ".length(), " ".length(), 0xAB ^ 0xAF, 0x54 ^ 0x50, " ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x86 ^ 0x82, "   ".length(), "   ".length(), 0x43 ^ 0x47, 0xA4 ^ 0xA0, "   ".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x16 ^ 0x10, "".length(), 0x31 ^ 0x35, 0x3A ^ 0x3C, 0x63 ^ 0x67, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (i <= (0xA3 ^ 0xA7)) {
                int j = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (j <= (0x41 ^ 0x45)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public static Corridor5 func_175877_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -" ".length(), "".length(), "".length(), 0xC1 ^ 0xC4, 0x29 ^ 0x2E, 0x6F ^ 0x6A, enumFacing);
            Corridor5 corridor5;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                corridor5 = new Corridor5(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                corridor5 = null;
            }
            return corridor5;
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
                if (3 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, " ".length(), "".length(), " ".length() != 0);
        }
        
        public Corridor5() {
        }
        
        public Corridor5(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class NetherStalkRoom extends Piece
    {
        public NetherStalkRoom(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        public NetherStalkRoom() {
        }
        
        public static NetherStalkRoom func_175875_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0x4F ^ 0x4A), -"   ".length(), "".length(), 0x28 ^ 0x25, 0x63 ^ 0x6D, 0x53 ^ 0x5E, enumFacing);
            NetherStalkRoom netherStalkRoom;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                netherStalkRoom = new NetherStalkRoom(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                netherStalkRoom = null;
            }
            return netherStalkRoom;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), 0x53 ^ 0x5F, 0xAC ^ 0xA8, 0xAC ^ 0xA0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x8A ^ 0x8F, "".length(), 0x62 ^ 0x6E, 0x51 ^ 0x5C, 0x78 ^ 0x74, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xC4 ^ 0xC1, "".length(), " ".length(), 0x43 ^ 0x4F, 0x65 ^ 0x69, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x40 ^ 0x4B, 0x4F ^ 0x4A, "".length(), 0x55 ^ 0x59, 0xA1 ^ 0xAD, 0xC ^ 0x0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x87 ^ 0x82, 0xAC ^ 0xA7, 0x11 ^ 0x15, 0x30 ^ 0x3C, 0x52 ^ 0x5E, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1F ^ 0x17, 0x41 ^ 0x44, 0xB9 ^ 0xB2, 0xA4 ^ 0xAE, 0xCB ^ 0xC7, 0xCC ^ 0xC0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2D ^ 0x28, 0x8B ^ 0x82, 0x27 ^ 0x2C, 0xBB ^ 0xBC, 0x1E ^ 0x12, 0x68 ^ 0x64, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x65 ^ 0x60, "".length(), 0xB4 ^ 0xB0, 0xB0 ^ 0xBC, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6E ^ 0x66, 0x8A ^ 0x8F, "".length(), 0xA7 ^ 0xAD, 0x10 ^ 0x1C, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x21 ^ 0x24, 0x5E ^ 0x57, "".length(), 0xAB ^ 0xAC, 0x8E ^ 0x82, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x8F ^ 0x84, "  ".length(), 0xA3 ^ 0xA9, 0x42 ^ 0x4E, 0x6C ^ 0x66, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int i = " ".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i <= (0x60 ^ 0x6B)) {
                this.fillWithBlocks(world, structureBoundingBox, i, 0x60 ^ 0x6A, "".length(), i, 0xB ^ 0x0, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, i, 0x4C ^ 0x46, 0x10 ^ 0x1C, i, 0x2B ^ 0x20, 0x9 ^ 0x5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x49 ^ 0x43, i, "".length(), 0x2E ^ 0x25, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x97 ^ 0x9B, 0xB7 ^ 0xBD, i, 0x5E ^ 0x52, 0x8E ^ 0x85, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), i, 0xA5 ^ 0xA8, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), i, 0x72 ^ 0x7F, 0x3C ^ 0x30, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), "".length(), 0x80 ^ 0x8D, i, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0x21 ^ 0x2D, 0x93 ^ 0x9E, i, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), i + " ".length(), 0x99 ^ 0x94, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), i + " ".length(), 0x60 ^ 0x6D, 0x59 ^ 0x55, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x62 ^ 0x6F, i + " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0xBF ^ 0xB3, 0x7D ^ 0x70, i + " ".length(), structureBoundingBox);
                i += 2;
            }
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x1A ^ 0x17, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x58 ^ 0x55, 0x7F ^ 0x73, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x1D ^ 0x10, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0xB9 ^ 0xB5, 0xA0 ^ 0xAD, "".length(), structureBoundingBox);
            int j = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j <= (0x9D ^ 0x94)) {
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x3C ^ 0x3B, j, " ".length(), 0x4F ^ 0x47, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x9C ^ 0x97, 0x39 ^ 0x3E, j, 0x61 ^ 0x6A, 0x1D ^ 0x15, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                j += 2;
            }
            final int metadataWithOffset = this.getMetadataWithOffset(Blocks.nether_brick_stairs, "   ".length());
            int k = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (k <= (0x47 ^ 0x41)) {
                final int n = k + (0xD ^ 0x9);
                int l = 0xB ^ 0xE;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (l <= (0xBA ^ 0xBD)) {
                    this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), l, (0x73 ^ 0x76) + k, n, structureBoundingBox);
                    ++l;
                }
                if (n >= (0x5E ^ 0x5B) && n <= (0x7E ^ 0x76)) {
                    this.fillWithBlocks(world, structureBoundingBox, 0x97 ^ 0x92, 0x44 ^ 0x41, n, 0xA4 ^ 0xA3, k + (0x76 ^ 0x72), n, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (n >= (0x16 ^ 0x1F) && n <= (0x49 ^ 0x43)) {
                    this.fillWithBlocks(world, structureBoundingBox, 0xAF ^ 0xAA, 0x16 ^ 0x1E, n, 0x97 ^ 0x90, k + (0x96 ^ 0x92), n, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
                }
                if (k >= " ".length()) {
                    this.fillWithBlocks(world, structureBoundingBox, 0x87 ^ 0x82, (0x4 ^ 0x2) + k, n, 0x9C ^ 0x9B, (0xAC ^ 0xA5) + k, n, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
                }
                ++k;
            }
            int n2 = 0xAC ^ 0xA9;
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (n2 <= (0xB0 ^ 0xB7)) {
                this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset), n2, 0x95 ^ 0x99, 0x3B ^ 0x30, structureBoundingBox);
                ++n2;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x99 ^ 0x9C, 0x8 ^ 0xE, 0x67 ^ 0x60, 0x37 ^ 0x32, 0x5C ^ 0x5B, 0x77 ^ 0x70, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x80 ^ 0x87, 0x8D ^ 0x8B, 0xA0 ^ 0xA7, 0x83 ^ 0x84, 0x22 ^ 0x25, 0x5E ^ 0x59, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x62 ^ 0x67, 0x5E ^ 0x53, 0x27 ^ 0x2B, 0x13 ^ 0x14, 0x10 ^ 0x1D, 0x40 ^ 0x4C, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x80 ^ 0x85, "  ".length(), "   ".length(), 0x59 ^ 0x5C, "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x6 ^ 0x3, 0x3D ^ 0x34, "   ".length(), 0x9E ^ 0x9B, 0xBA ^ 0xB0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x2A ^ 0x2F, 0x7C ^ 0x78, "  ".length(), 0x59 ^ 0x5C, 0x8A ^ 0x82, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB8 ^ 0xB1, 0xE ^ 0xB, "  ".length(), 0x6A ^ 0x60, 0x5A ^ 0x5F, "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7C ^ 0x75, 0x16 ^ 0x13, 0x5E ^ 0x57, 0xE ^ 0x4, 0xAF ^ 0xAA, 0x97 ^ 0x9D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x49 ^ 0x43, 0x50 ^ 0x55, 0x72 ^ 0x76, 0x83 ^ 0x89, 0x35 ^ 0x30, 0x87 ^ 0x8F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            final int metadataWithOffset2 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, "".length());
            final int metadataWithOffset3 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, " ".length());
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset3), 0xF ^ 0xB, 0x65 ^ 0x60, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset3), 0xA8 ^ 0xAC, 0x70 ^ 0x75, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset3), 0x25 ^ 0x21, 0xB4 ^ 0xB1, 0x4F ^ 0x46, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset3), 0xAC ^ 0xA8, 0xC4 ^ 0xC1, 0x9E ^ 0x94, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset2), 0x6F ^ 0x67, 0x93 ^ 0x96, "  ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset2), 0xA4 ^ 0xAC, 0x6B ^ 0x6E, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset2), 0x17 ^ 0x1F, 0x68 ^ 0x6D, 0x97 ^ 0x9E, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_stairs.getStateFromMeta(metadataWithOffset2), 0xBB ^ 0xB3, 0x9B ^ 0x9E, 0x28 ^ 0x22, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), 0x8E ^ 0x8A, 0xB5 ^ 0xB1, 0xAD ^ 0xA9, 0xA6 ^ 0xA2, 0x6B ^ 0x63, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x50 ^ 0x58, 0x70 ^ 0x74, 0x15 ^ 0x11, 0x24 ^ 0x2D, 0xB4 ^ 0xB0, 0x62 ^ 0x6A, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), 0x74 ^ 0x71, 0xA2 ^ 0xA6, 0x18 ^ 0x1C, 0x4B ^ 0x4E, 0x7A ^ 0x72, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1C ^ 0x14, 0xC7 ^ 0xC2, 0xB8 ^ 0xBC, 0x69 ^ 0x60, 0x16 ^ 0x13, 0x1C ^ 0x14, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x56 ^ 0x52, "  ".length(), "".length(), 0x2B ^ 0x23, "  ".length(), 0x87 ^ 0x8B, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x1C ^ 0x18, 0x18 ^ 0x14, "  ".length(), 0x27 ^ 0x2F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC0 ^ 0xC4, "".length(), "".length(), 0x63 ^ 0x6B, " ".length(), "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBD ^ 0xB9, "".length(), 0x2A ^ 0x23, 0x88 ^ 0x80, " ".length(), 0xC9 ^ 0xC5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0xC ^ 0x8, "   ".length(), " ".length(), 0xA7 ^ 0xAF, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x12 ^ 0x1B, "".length(), 0x52 ^ 0x56, 0xB2 ^ 0xBE, " ".length(), 0x86 ^ 0x8E, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int n3 = 0x1D ^ 0x19;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (n3 <= (0x17 ^ 0x1F)) {
                int length = "".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                while (length <= "  ".length()) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), n3, -" ".length(), length, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), n3, -" ".length(), (0x3B ^ 0x37) - length, structureBoundingBox);
                    ++length;
                }
                ++n3;
            }
            int length2 = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (length2 <= "  ".length()) {
                int n4 = 0x4F ^ 0x4B;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (n4 <= (0xB3 ^ 0xBB)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), length2, -" ".length(), n4, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), (0xC8 ^ 0xC4) - length2, -" ".length(), n4, structureBoundingBox);
                    ++n4;
                }
                ++length2;
            }
            return " ".length() != 0;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 0xB7 ^ 0xB2, "   ".length(), " ".length() != 0);
            this.getNextComponentNormal((Start)structureComponent, list, random, 0x87 ^ 0x82, 0xB0 ^ 0xBB, " ".length() != 0);
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
                if (2 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class Corridor4 extends Piece
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            int length = " ".length();
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
                length = (0x9D ^ 0x98);
            }
            final Start start = (Start)structureComponent;
            final int length2 = "".length();
            final int n = length;
            int n2;
            if (random.nextInt(0x1B ^ 0x13) > 0) {
                n2 = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            this.getNextComponentX(start, list, random, length2, n, n2 != 0);
            final Start start2 = (Start)structureComponent;
            final int length3 = "".length();
            final int n3 = length;
            int n4;
            if (random.nextInt(0xAF ^ 0xA7) > 0) {
                n4 = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            this.getNextComponentZ(start2, list, random, length3, n3, n4 != 0);
        }
        
        public static Corridor4 func_175880_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -"   ".length(), "".length(), "".length(), 0x6D ^ 0x64, 0x6D ^ 0x6A, 0xB4 ^ 0xBD, enumFacing);
            Corridor4 corridor4;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                corridor4 = new Corridor4(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                corridor4 = null;
            }
            return corridor4;
        }
        
        public Corridor4(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
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
        
        public Corridor4() {
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x9 ^ 0x1, " ".length(), 0x91 ^ 0x99, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0xC9 ^ 0xC1, 0x15 ^ 0x10, 0x94 ^ 0x9C, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x1F ^ 0x19, "".length(), 0x60 ^ 0x68, 0x4C ^ 0x4A, 0x86 ^ 0x83, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "  ".length(), 0x5A ^ 0x5F, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5A ^ 0x5C, "  ".length(), "".length(), 0x41 ^ 0x49, 0x49 ^ 0x4C, "".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), " ".length(), 0xA2 ^ 0xA6, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x20 ^ 0x27, "   ".length(), "".length(), 0xC6 ^ 0xC1, 0x8 ^ 0xC, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x98 ^ 0x9C, 0x53 ^ 0x5B, "  ".length(), 0x6C ^ 0x64, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0xC3 ^ 0xC7, "  ".length(), "  ".length(), 0x1A ^ 0x1E, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6 ^ 0x0, " ".length(), 0x27 ^ 0x23, 0x6E ^ 0x69, "  ".length(), 0xBE ^ 0xBA, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x4C ^ 0x44, 0x3B ^ 0x33, "   ".length(), 0x86 ^ 0x8E, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x2 ^ 0x4, "".length(), "   ".length(), 0xC1 ^ 0xC6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xBB, "   ".length(), 0x9D ^ 0x9B, 0x88 ^ 0x80, "   ".length(), 0x88 ^ 0x8F, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x76 ^ 0x72, "".length(), 0x3A ^ 0x3F, 0x4B ^ 0x4E, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x56 ^ 0x5E, "   ".length(), 0x89 ^ 0x8D, 0x31 ^ 0x39, 0x11 ^ 0x14, 0x2A ^ 0x2F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x83 ^ 0x86, "  ".length(), 0x12 ^ 0x17, 0x58 ^ 0x5D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x41 ^ 0x47, "   ".length(), 0x5 ^ 0x0, 0x97 ^ 0x90, 0x52 ^ 0x57, 0x83 ^ 0x86, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xAA ^ 0xAE, 0x3A ^ 0x3F, " ".length(), 0x27 ^ 0x22, 0x7C ^ 0x79, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5B ^ 0x5C, 0x30 ^ 0x34, 0x8 ^ 0xD, 0x14 ^ 0x13, 0x1B ^ 0x1E, 0x84 ^ 0x81, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            int i = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (i <= (0x8A ^ 0x8F)) {
                int j = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (j <= (0x1A ^ 0x12)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), j, -" ".length(), i, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
    }
    
    public static class Entrance extends Piece
    {
        public Entrance(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), 0xBC ^ 0xB0, 0x62 ^ 0x66, 0x7B ^ 0x77, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xA8 ^ 0xAD, "".length(), 0xA6 ^ 0xAA, 0x4D ^ 0x40, 0x87 ^ 0x8B, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x6E ^ 0x6B, "".length(), " ".length(), 0x9A ^ 0x96, 0x88 ^ 0x84, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9C ^ 0x97, 0x79 ^ 0x7C, "".length(), 0x88 ^ 0x84, 0x8 ^ 0x4, 0x71 ^ 0x7D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x19 ^ 0x1C, 0x4C ^ 0x47, 0xA7 ^ 0xA3, 0x5D ^ 0x51, 0x12 ^ 0x1E, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x27 ^ 0x2F, 0xB3 ^ 0xB6, 0x13 ^ 0x18, 0x9 ^ 0x3, 0x9 ^ 0x5, 0x49 ^ 0x45, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x51 ^ 0x54, 0x58 ^ 0x51, 0x84 ^ 0x8F, 0x1D ^ 0x1A, 0x59 ^ 0x55, 0x81 ^ 0x8D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x9E ^ 0x9B, "".length(), 0x1 ^ 0x5, 0x90 ^ 0x9C, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4C ^ 0x44, 0x9D ^ 0x98, "".length(), 0x3 ^ 0x9, 0x65 ^ 0x69, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5E ^ 0x5B, 0x59 ^ 0x50, "".length(), 0x68 ^ 0x6F, 0x80 ^ 0x8C, " ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x1A ^ 0x11, "  ".length(), 0x18 ^ 0x12, 0x89 ^ 0x85, 0x92 ^ 0x98, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAB ^ 0xAE, 0x95 ^ 0x9D, "".length(), 0x3D ^ 0x3A, 0xAC ^ 0xA4, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            int i = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (i <= (0x7E ^ 0x75)) {
                this.fillWithBlocks(world, structureBoundingBox, i, 0x59 ^ 0x53, "".length(), i, 0x25 ^ 0x2E, "".length(), Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, i, 0x42 ^ 0x48, 0xAF ^ 0xA3, i, 0xAE ^ 0xA5, 0xAE ^ 0xA2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), 0xCD ^ 0xC7, i, "".length(), 0x0 ^ 0xB, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xCB ^ 0xC7, 0xD ^ 0x7, i, 0x3E ^ 0x32, 0x69 ^ 0x62, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), i, 0xE ^ 0x3, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), i, 0x29 ^ 0x24, 0xB5 ^ 0xB9, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), "".length(), 0x80 ^ 0x8D, i, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0x90 ^ 0x9C, 0x3F ^ 0x32, i, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), i + " ".length(), 0x47 ^ 0x4A, "".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), i + " ".length(), 0x47 ^ 0x4A, 0xAF ^ 0xA3, structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x37 ^ 0x3A, i + " ".length(), structureBoundingBox);
                this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0x38 ^ 0x34, 0x48 ^ 0x45, i + " ".length(), structureBoundingBox);
                i += 2;
            }
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x38 ^ 0x35, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x64 ^ 0x69, 0x6C ^ 0x60, structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), "".length(), 0x80 ^ 0x8D, "".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0x8F ^ 0x83, 0xAA ^ 0xA7, "".length(), structureBoundingBox);
            int j = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j <= (0x50 ^ 0x59)) {
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xB3 ^ 0xB4, j, " ".length(), 0xB ^ 0x3, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x40 ^ 0x4B, 0x8B ^ 0x8C, j, 0x67 ^ 0x6C, 0xA8 ^ 0xA0, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
                j += 2;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x78 ^ 0x7C, "  ".length(), "".length(), 0x80 ^ 0x88, "  ".length(), 0x1 ^ 0xD, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x11 ^ 0x15, 0x35 ^ 0x39, "  ".length(), 0x87 ^ 0x8F, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x43 ^ 0x47, "".length(), "".length(), 0x28 ^ 0x20, " ".length(), "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x19 ^ 0x1D, "".length(), 0x7C ^ 0x75, 0x19 ^ 0x11, " ".length(), 0x3E ^ 0x32, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0x2A ^ 0x2E, "   ".length(), " ".length(), 0xA4 ^ 0xAC, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xCE ^ 0xC7, "".length(), 0xB ^ 0xF, 0x32 ^ 0x3E, " ".length(), 0xB7 ^ 0xBF, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            int k = 0x3E ^ 0x3A;
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (k <= (0x76 ^ 0x7E)) {
                int l = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (l <= "  ".length()) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), k, -" ".length(), l, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), k, -" ".length(), (0xB4 ^ 0xB8) - l, structureBoundingBox);
                    ++l;
                }
                ++k;
            }
            int length = "".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (length <= "  ".length()) {
                int n = 0xA6 ^ 0xA2;
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (n <= (0xB5 ^ 0xBD)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), length, -" ".length(), n, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), (0x18 ^ 0x14) - length, -" ".length(), n, structureBoundingBox);
                    ++n;
                }
                ++length;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x12 ^ 0x17, 0x70 ^ 0x75, 0x47 ^ 0x42, 0x67 ^ 0x60, 0x0 ^ 0x5, 0x80 ^ 0x87, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x37 ^ 0x31, " ".length(), 0x8A ^ 0x8C, 0x5F ^ 0x59, 0x93 ^ 0x97, 0x78 ^ 0x7E, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.nether_brick.getDefaultState(), 0x83 ^ 0x85, "".length(), 0xA7 ^ 0xA1, structureBoundingBox);
            this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 0x40 ^ 0x46, 0x92 ^ 0x97, 0xAC ^ 0xAA, structureBoundingBox);
            final BlockPos blockPos = new BlockPos(this.getXWithOffset(0xB8 ^ 0xBE, 0x7F ^ 0x79), this.getYWithOffset(0x2 ^ 0x7), this.getZWithOffset(0x2B ^ 0x2D, 0x67 ^ 0x61));
            if (structureBoundingBox.isVecInside(blockPos)) {
                world.forceBlockUpdateTick(Blocks.flowing_lava, blockPos, random);
            }
            return " ".length() != 0;
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
            this.getNextComponentNormal((Start)structureComponent, list, random, 0x32 ^ 0x37, "   ".length(), " ".length() != 0);
        }
        
        public static Entrance func_175881_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -(0xAD ^ 0xA8), -"   ".length(), "".length(), 0x9 ^ 0x4, 0x3D ^ 0x33, 0x84 ^ 0x89, enumFacing);
            Entrance entrance;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                entrance = new Entrance(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                entrance = null;
            }
            return entrance;
        }
        
        public Entrance() {
        }
    }
    
    public static class Throne extends Piece
    {
        private static final String[] I;
        private boolean hasSpawner;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), 0x45 ^ 0x43, 0xF ^ 0x8, 0x1B ^ 0x1C, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0x6C ^ 0x69, " ".length(), 0x62 ^ 0x65, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), " ".length(), 0x7E ^ 0x7B, "  ".length(), 0x9A ^ 0x9D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "  ".length(), 0x97 ^ 0x92, "   ".length(), 0xA0 ^ 0xA7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xAB ^ 0xAF, "   ".length(), 0xAA ^ 0xAF, 0x79 ^ 0x7D, 0x5A ^ 0x5D, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), " ".length(), 0xC7 ^ 0xC3, "  ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x48 ^ 0x4D, "  ".length(), "".length(), 0x55 ^ 0x50, 0xC7 ^ 0xC3, "  ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x91 ^ 0x94, "  ".length(), " ".length(), 0x6 ^ 0x3, "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAB ^ 0xAE, 0xB4 ^ 0xB1, "  ".length(), 0xE ^ 0xB, 0xB1 ^ 0xB4, "   ".length(), Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x49 ^ 0x4C, "   ".length(), "".length(), 0xC1 ^ 0xC4, 0x61 ^ 0x69, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x28 ^ 0x2E, 0xB3 ^ 0xB6, "   ".length(), 0xF ^ 0x9, 0x44 ^ 0x41, 0x94 ^ 0x9C, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x3A ^ 0x3F, 0x61 ^ 0x69, 0x2E ^ 0x2B, 0xAD ^ 0xA8, 0x78 ^ 0x70, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), "".length() != 0);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), " ".length(), 0xA2 ^ 0xA4, "   ".length(), structureBoundingBox);
            this.setBlockState(world, Blocks.nether_brick_fence.getDefaultState(), 0xC4 ^ 0xC1, 0x44 ^ 0x42, "   ".length(), structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x71 ^ 0x77, "   ".length(), "".length(), 0xBD ^ 0xBB, 0xBA ^ 0xB2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA4 ^ 0xA2, 0x5F ^ 0x59, "   ".length(), 0x98 ^ 0x9E, 0x8B ^ 0x8D, 0x8E ^ 0x86, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x25 ^ 0x23, 0x1E ^ 0x16, 0x65 ^ 0x60, 0x8F ^ 0x88, 0xA ^ 0x2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0x21 ^ 0x29, 0x85 ^ 0x8D, 0x4C ^ 0x48, 0xAB ^ 0xA3, 0x10 ^ 0x18, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), "".length() != 0);
            if (!this.hasSpawner) {
                final BlockPos blockPos = new BlockPos(this.getXWithOffset("   ".length(), 0x20 ^ 0x25), this.getYWithOffset(0x6C ^ 0x69), this.getZWithOffset("   ".length(), 0x29 ^ 0x2C));
                if (structureBoundingBox.isVecInside(blockPos)) {
                    this.hasSpawner = (" ".length() != 0);
                    world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), "  ".length());
                    final TileEntity tileEntity = world.getTileEntity(blockPos);
                    if (tileEntity instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName(Throne.I["  ".length()]);
                    }
                }
            }
            int i = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (i <= (0x91 ^ 0x97)) {
                int j = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (j <= (0x38 ^ 0x3E)) {
                    this.replaceAirAndLiquidDownwards(world, Blocks.nether_brick.getDefaultState(), i, -" ".length(), j, structureBoundingBox);
                    ++j;
                }
                ++i;
            }
            return " ".length() != 0;
        }
        
        public Throne() {
        }
        
        static {
            I();
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readStructureFromNBT(nbtTagCompound);
            this.hasSpawner = nbtTagCompound.getBoolean(Throne.I["".length()]);
        }
        
        public static Throne func_175874_a(final List<StructureComponent> list, final Random random, final int n, final int n2, final int n3, final int n4, final EnumFacing enumFacing) {
            final StructureBoundingBox componentToAddBoundingBox = StructureBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -"  ".length(), "".length(), "".length(), 0xB6 ^ 0xB1, 0xA2 ^ 0xAA, 0x10 ^ 0x19, enumFacing);
            Throne throne;
            if (Piece.isAboveGround(componentToAddBoundingBox) && StructureComponent.findIntersecting(list, componentToAddBoundingBox) == null) {
                throne = new Throne(n4, random, componentToAddBoundingBox, enumFacing);
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                throne = null;
            }
            return throne;
        }
        
        public Throne(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeStructureToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Throne.I[" ".length()], this.hasSpawner);
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("*+\u0012", "gDpOh");
            Throne.I[" ".length()] = I("'\u0006\b", "jijvE");
            Throne.I["  ".length()] = I("*>\u0015\b\u0006", "hRtrc");
        }
    }
}
