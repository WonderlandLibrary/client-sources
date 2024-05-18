package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import java.util.*;

public class StructureOceanMonumentPieces
{
    private static final String[] I;
    
    public static void registerOceanMonumentPieces() {
        MapGenStructureIO.registerStructureComponent(MonumentBuilding.class, StructureOceanMonumentPieces.I["".length()]);
        MapGenStructureIO.registerStructureComponent(MonumentCoreRoom.class, StructureOceanMonumentPieces.I[" ".length()]);
        MapGenStructureIO.registerStructureComponent(DoubleXRoom.class, StructureOceanMonumentPieces.I["  ".length()]);
        MapGenStructureIO.registerStructureComponent(DoubleXYRoom.class, StructureOceanMonumentPieces.I["   ".length()]);
        MapGenStructureIO.registerStructureComponent(DoubleYRoom.class, StructureOceanMonumentPieces.I[0x2 ^ 0x6]);
        MapGenStructureIO.registerStructureComponent(DoubleYZRoom.class, StructureOceanMonumentPieces.I[0x48 ^ 0x4D]);
        MapGenStructureIO.registerStructureComponent(DoubleZRoom.class, StructureOceanMonumentPieces.I[0x62 ^ 0x64]);
        MapGenStructureIO.registerStructureComponent(EntryRoom.class, StructureOceanMonumentPieces.I[0xC5 ^ 0xC2]);
        MapGenStructureIO.registerStructureComponent(Penthouse.class, StructureOceanMonumentPieces.I[0x1 ^ 0x9]);
        MapGenStructureIO.registerStructureComponent(SimpleRoom.class, StructureOceanMonumentPieces.I[0x3F ^ 0x36]);
        MapGenStructureIO.registerStructureComponent(SimpleTopRoom.class, StructureOceanMonumentPieces.I[0xC8 ^ 0xC2]);
    }
    
    private static void I() {
        (I = new String[0x4 ^ 0xF])["".length()] = I("+\u000f;", "dBywc");
        StructureOceanMonumentPieces.I[" ".length()] = I("\n\u001f5\u0004", "ERvVQ");
        StructureOceanMonumentPieces.I["  ".length()] = I(")\t\f\u000b?", "fDHSm");
        StructureOceanMonumentPieces.I["   ".length()] = I("\b\f\u000f+7\u0015", "GAKsn");
        StructureOceanMonumentPieces.I[0x11 ^ 0x15] = I("95>2\u0005", "vxzkW");
        StructureOceanMonumentPieces.I[0xB2 ^ 0xB7] = I("\u000e\u0017(*\u000f\u0013", "AZlsU");
        StructureOceanMonumentPieces.I[0xB6 ^ 0xB0] = I("\u00024'=3", "Mycga");
        StructureOceanMonumentPieces.I[0x94 ^ 0x93] = I("\u00064(8\u0017;\u0000", "IymVc");
        StructureOceanMonumentPieces.I[0x1F ^ 0x17] = I("<\u000e9?)\u0007+\u0006/4\u0016", "sCiZG");
        StructureOceanMonumentPieces.I[0x30 ^ 0x39] = I("\r4 1\u001e2\u0015\u0016", "BysXs");
        StructureOceanMonumentPieces.I[0x83 ^ 0x89] = I("\u001e\u000f\u000b\u0006.!.=;", "QBXoC");
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
    
    static {
        I();
    }
    
    static class RoomDefinition
    {
        boolean field_175963_d;
        int field_175962_f;
        int field_175967_a;
        boolean[] field_175966_c;
        boolean field_175964_e;
        RoomDefinition[] field_175965_b;
        
        public boolean func_175961_b() {
            if (this.field_175967_a >= (0x49 ^ 0x2)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public boolean func_175959_a(final int field_175962_f) {
            if (this.field_175964_e) {
                return " ".length() != 0;
            }
            this.field_175962_f = field_175962_f;
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < (0x49 ^ 0x4F)) {
                if (this.field_175965_b[i] != null && this.field_175966_c[i] && this.field_175965_b[i].field_175962_f != field_175962_f && this.field_175965_b[i].func_175959_a(field_175962_f)) {
                    return " ".length() != 0;
                }
                ++i;
            }
            return "".length() != 0;
        }
        
        public void func_175957_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition) {
            this.field_175965_b[enumFacing.getIndex()] = roomDefinition;
            roomDefinition.field_175965_b[enumFacing.getOpposite().getIndex()] = this;
        }
        
        public RoomDefinition(final int field_175967_a) {
            this.field_175965_b = new RoomDefinition[0xAC ^ 0xAA];
            this.field_175966_c = new boolean[0x7 ^ 0x1];
            this.field_175967_a = field_175967_a;
        }
        
        public void func_175958_a() {
            int i = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (i < (0xA5 ^ 0xA3)) {
                final boolean[] field_175966_c = this.field_175966_c;
                final int n = i;
                int n2;
                if (this.field_175965_b[i] != null) {
                    n2 = " ".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                field_175966_c[n] = (n2 != 0);
                ++i;
            }
        }
        
        public int func_175960_c() {
            int length = "".length();
            int i = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (i < (0x7A ^ 0x7C)) {
                if (this.field_175966_c[i]) {
                    ++length;
                }
                ++i;
            }
            return length;
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
                if (4 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class FitSimpleRoomTopHelper implements MonumentRoomFitHelper
    {
        FitSimpleRoomTopHelper(final FitSimpleRoomTopHelper fitSimpleRoomTopHelper) {
            this();
        }
        
        private FitSimpleRoomTopHelper() {
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (!roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()] && !roomDefinition.field_175966_c[EnumFacing.UP.getIndex()]) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            return new SimpleTopRoom(enumFacing, roomDefinition, random);
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
    }
    
    public static class SimpleTopRoom extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / (0x98 ^ 0x81) > 0) {
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0xA8 ^ 0xAC, " ".length(), 0x7D ^ 0x7B, 0xD ^ 0x9, 0xAF ^ 0xA9, SimpleTopRoom.field_175828_a);
            }
            int i = " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i <= (0x1 ^ 0x7)) {
                int j = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (j <= (0xA9 ^ 0xAF)) {
                    if (random.nextInt("   ".length()) != 0) {
                        final int length = "  ".length();
                        int n;
                        if (random.nextInt(0x13 ^ 0x17) == 0) {
                            n = "".length();
                            "".length();
                            if (-1 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            n = " ".length();
                        }
                        this.fillWithBlocks(world, structureBoundingBox, i, length + n, j, i, "   ".length(), j, Blocks.sponge.getStateFromMeta(" ".length()), Blocks.sponge.getStateFromMeta(" ".length()), "".length() != 0);
                    }
                    ++j;
                }
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0x3F ^ 0x38, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7F ^ 0x78, " ".length(), "".length(), 0x42 ^ 0x45, " ".length(), 0x2 ^ 0x5, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), 0x31 ^ 0x37, " ".length(), "".length(), SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x31 ^ 0x36, 0x41 ^ 0x47, " ".length(), 0x11 ^ 0x16, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), "  ".length(), 0xBF ^ 0xB8, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x14 ^ 0x13, "  ".length(), "".length(), 0xA5 ^ 0xA2, "  ".length(), 0xA7 ^ 0xA0, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0x6D ^ 0x6B, "  ".length(), "".length(), SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x40 ^ 0x47, 0x57 ^ 0x51, "  ".length(), 0xA9 ^ 0xAE, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "".length(), "   ".length(), 0x10 ^ 0x17, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x45 ^ 0x42, "   ".length(), "".length(), 0x28 ^ 0x2F, "   ".length(), 0x8A ^ 0x8D, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), 0x6B ^ 0x6D, "   ".length(), "".length(), SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0xAE ^ 0xA9, 0x7 ^ 0x1, "   ".length(), 0x91 ^ 0x96, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0xB2 ^ 0xB6, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7C ^ 0x7B, " ".length(), "   ".length(), 0x16 ^ 0x11, "  ".length(), 0x2B ^ 0x2F, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x21 ^ 0x25, "  ".length(), "".length(), SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x76 ^ 0x71, 0x3 ^ 0x7, "  ".length(), 0x31 ^ 0x36, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, "".length() != 0);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x53 ^ 0x57, "  ".length(), "".length(), "".length() != 0);
            }
            return " ".length() != 0;
        }
        
        public SimpleTopRoom() {
        }
        
        public SimpleTopRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), " ".length(), " ".length());
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
    }
    
    public abstract static class Piece extends StructureComponent
    {
        protected static final IBlockState field_175822_f;
        protected static final int field_175832_i;
        protected static final IBlockState field_175825_e;
        protected static final IBlockState field_175827_c;
        protected static final IBlockState field_175824_d;
        protected static final int field_175823_g;
        protected static final IBlockState field_175826_b;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        protected static final IBlockState field_175828_a;
        protected static final int field_175831_h;
        protected static final int field_175829_j;
        protected RoomDefinition field_175830_k;
        
        protected void func_175819_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState) {
            int i = n2;
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (i <= n5) {
                int j = n;
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (j <= n4) {
                    int k = n3;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                    while (k <= n6) {
                        if (this.getBlockStateFromPos(world, j, i, k, structureBoundingBox) == Piece.field_175822_f) {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        
        public Piece(final int n) {
            super(n);
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
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        protected Piece(final int n, final EnumFacing coordBaseMode, final RoomDefinition field_175830_k, final int n2, final int n3, final int n4) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.field_175830_k = field_175830_k;
            final int field_175967_a = field_175830_k.field_175967_a;
            final int n5 = field_175967_a % (0x64 ^ 0x61);
            final int n6 = field_175967_a / (0xA7 ^ 0xA2) % (0x3A ^ 0x3F);
            final int n7 = field_175967_a / (0x8E ^ 0x97);
            if (coordBaseMode != EnumFacing.NORTH && coordBaseMode != EnumFacing.SOUTH) {
                this.boundingBox = new StructureBoundingBox("".length(), "".length(), "".length(), n4 * (0x4E ^ 0x46) - " ".length(), n3 * (0x18 ^ 0x1C) - " ".length(), n2 * (0x37 ^ 0x3F) - " ".length());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                this.boundingBox = new StructureBoundingBox("".length(), "".length(), "".length(), n2 * (0x2F ^ 0x27) - " ".length(), n3 * (0x65 ^ 0x61) - " ".length(), n4 * (0x45 ^ 0x4D) - " ".length());
            }
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[coordBaseMode.ordinal()]) {
                case 3: {
                    this.boundingBox.offset(n5 * (0x89 ^ 0x81), n7 * (0xBD ^ 0xB9), -(n6 + n4) * (0xAE ^ 0xA6) + " ".length());
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.boundingBox.offset(n5 * (0x58 ^ 0x50), n7 * (0xA8 ^ 0xAC), n6 * (0x97 ^ 0x9F));
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    this.boundingBox.offset(-(n6 + n4) * (0x20 ^ 0x28) + " ".length(), n7 * (0x1E ^ 0x1A), n5 * (0x9A ^ 0x92));
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox.offset(n6 * (0x8B ^ 0x83), n7 * (0xBD ^ 0xB9), n5 * (0xA7 ^ 0xAF));
                    break;
                }
            }
        }
        
        protected static final int func_175820_a(final int n, final int n2, final int n3) {
            return n2 * (0xB6 ^ 0xAF) + n3 * (0x53 ^ 0x56) + n;
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        protected void func_175821_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final boolean b) {
            if (b) {
                this.fillWithBlocks(world, structureBoundingBox, n + "".length(), "".length(), n2 + "".length(), n + "  ".length(), "".length(), n2 + (0xB8 ^ 0xB0) - " ".length(), Piece.field_175828_a, Piece.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0x52 ^ 0x57), "".length(), n2 + "".length(), n + (0xCC ^ 0xC4) - " ".length(), "".length(), n2 + (0x38 ^ 0x30) - " ".length(), Piece.field_175828_a, Piece.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + "   ".length(), "".length(), n2 + "".length(), n + (0x48 ^ 0x4C), "".length(), n2 + "  ".length(), Piece.field_175828_a, Piece.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + "   ".length(), "".length(), n2 + (0x2F ^ 0x2A), n + (0x2A ^ 0x2E), "".length(), n2 + (0x90 ^ 0x98) - " ".length(), Piece.field_175828_a, Piece.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + "   ".length(), "".length(), n2 + "  ".length(), n + (0x54 ^ 0x50), "".length(), n2 + "  ".length(), Piece.field_175826_b, Piece.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + "   ".length(), "".length(), n2 + (0x2D ^ 0x28), n + (0x5F ^ 0x5B), "".length(), n2 + (0x5F ^ 0x5A), Piece.field_175826_b, Piece.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + "  ".length(), "".length(), n2 + "   ".length(), n + "  ".length(), "".length(), n2 + (0x70 ^ 0x74), Piece.field_175826_b, Piece.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0x61 ^ 0x64), "".length(), n2 + "   ".length(), n + (0xC0 ^ 0xC5), "".length(), n2 + (0xAD ^ 0xA9), Piece.field_175826_b, Piece.field_175826_b, "".length() != 0);
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, n + "".length(), "".length(), n2 + "".length(), n + (0xA2 ^ 0xAA) - " ".length(), "".length(), n2 + (0x65 ^ 0x6D) - " ".length(), Piece.field_175828_a, Piece.field_175828_a, "".length() != 0);
            }
        }
        
        protected boolean func_175818_a(final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4) {
            final int xWithOffset = this.getXWithOffset(n, n2);
            final int zWithOffset = this.getZWithOffset(n, n2);
            final int xWithOffset2 = this.getXWithOffset(n3, n4);
            final int zWithOffset2 = this.getZWithOffset(n3, n4);
            return structureBoundingBox.intersectsWith(Math.min(xWithOffset, xWithOffset2), Math.min(zWithOffset, zWithOffset2), Math.max(xWithOffset, xWithOffset2), Math.max(zWithOffset, zWithOffset2));
        }
        
        public Piece() {
            super("".length());
        }
        
        protected void func_181655_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
            int i = n2;
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (i <= n5) {
                int j = n;
                "".length();
                if (2 == 1) {
                    throw null;
                }
                while (j <= n4) {
                    int k = n3;
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                    while (k <= n6) {
                        if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                            if (this.getYWithOffset(i) >= world.func_181545_F()) {
                                this.setBlockState(world, Blocks.air.getDefaultState(), j, i, k, structureBoundingBox);
                                "".length();
                                if (4 <= 3) {
                                    throw null;
                                }
                            }
                            else {
                                this.setBlockState(world, Piece.field_175822_f, j, i, k, structureBoundingBox);
                            }
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        
        static {
            field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGH_META);
            field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKS_META);
            field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARK_META);
            field_175824_d = Piece.field_175826_b;
            field_175825_e = Blocks.sea_lantern.getDefaultState();
            field_175822_f = Blocks.water.getDefaultState();
            field_175823_g = func_175820_a("  ".length(), "".length(), "".length());
            field_175831_h = func_175820_a("  ".length(), "  ".length(), "".length());
            field_175832_i = func_175820_a("".length(), " ".length(), "".length());
            field_175829_j = func_175820_a(0x4F ^ 0x4B, " ".length(), "".length());
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
                if (1 == 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xE ^ 0x8);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xA7 ^ 0xA3);
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x56 ^ 0x53);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return Piece.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        public Piece(final EnumFacing coordBaseMode, final StructureBoundingBox boundingBox) {
            super(" ".length());
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
        
        protected boolean func_175817_a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3) {
            final int xWithOffset = this.getXWithOffset(n, n3);
            final int yWithOffset = this.getYWithOffset(n2);
            final int zWithOffset = this.getZWithOffset(n, n3);
            if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
                final EntityGuardian entityGuardian = new EntityGuardian(world);
                entityGuardian.setElder(" ".length() != 0);
                entityGuardian.heal(entityGuardian.getMaxHealth());
                entityGuardian.setLocationAndAngles(xWithOffset + 0.5, yWithOffset, zWithOffset + 0.5, 0.0f, 0.0f);
                entityGuardian.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityGuardian)), null);
                world.spawnEntityInWorld(entityGuardian);
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    interface MonumentRoomFitHelper
    {
        boolean func_175969_a(final RoomDefinition p0);
        
        Piece func_175968_a(final EnumFacing p0, final RoomDefinition p1, final Random p2);
    }
    
    static class ZDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
                return " ".length() != 0;
            }
            return "".length() != 0;
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
                if (false == true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            RoomDefinition roomDefinition2 = roomDefinition;
            if (!roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] || roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
                roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.SOUTH.getIndex()];
            }
            roomDefinition2.field_175963_d = (" ".length() != 0);
            roomDefinition2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = (" ".length() != 0);
            return new DoubleZRoom(enumFacing, roomDefinition2, random);
        }
        
        private ZDoubleRoomFitHelper() {
        }
        
        ZDoubleRoomFitHelper(final ZDoubleRoomFitHelper zDoubleRoomFitHelper) {
            this();
        }
    }
    
    public static class DoubleZRoom extends Piece
    {
        public DoubleZRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), " ".length(), "  ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            if (this.field_175830_k.field_175967_a / (0x11 ^ 0x8) > 0) {
                this.func_175821_a(world, structureBoundingBox, "".length(), 0x94 ^ 0x9C, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0xA4 ^ 0xA0, " ".length(), 0x0 ^ 0x6, 0xB7 ^ 0xB3, 0x28 ^ 0x2F, DoubleZRoom.field_175828_a);
            }
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x46 ^ 0x42, 0x15 ^ 0x1D, 0x6A ^ 0x6C, 0xB ^ 0xF, 0x6F ^ 0x61, DoubleZRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "".length(), "   ".length(), 0x22 ^ 0x2D, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1A ^ 0x1D, "   ".length(), "".length(), 0x13 ^ 0x14, "   ".length(), 0x9D ^ 0x92, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), 0x2 ^ 0x5, "   ".length(), "".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0xB1 ^ 0xBE, 0x2E ^ 0x28, "   ".length(), 0xA5 ^ 0xAA, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), "  ".length(), 0xAD ^ 0xA2, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x82 ^ 0x85, "  ".length(), "".length(), 0xC7 ^ 0xC0, "  ".length(), 0x3 ^ 0xC, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0x57 ^ 0x50, "  ".length(), "".length(), DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x1E ^ 0x11, 0xBD ^ 0xBB, "  ".length(), 0x44 ^ 0x4B, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0x1D ^ 0x12, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x94 ^ 0x93, " ".length(), "".length(), 0x8F ^ 0x88, " ".length(), 0x3D ^ 0x32, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), 0x6D ^ 0x6A, " ".length(), "".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x4D ^ 0x42, 0x8B ^ 0x8D, " ".length(), 0x81 ^ 0x8E, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), " ".length(), " ".length(), " ".length(), "  ".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x83 ^ 0x85, " ".length(), " ".length(), 0x54 ^ 0x52, " ".length(), "  ".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), " ".length(), " ".length(), "   ".length(), "  ".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7C ^ 0x7A, "   ".length(), " ".length(), 0x22 ^ 0x24, "   ".length(), "  ".length(), DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0xC8 ^ 0xC5, " ".length(), " ".length(), 0x51 ^ 0x5F, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6D ^ 0x6B, " ".length(), 0xB1 ^ 0xBC, 0xC4 ^ 0xC2, " ".length(), 0x7A ^ 0x74, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x61 ^ 0x6C, " ".length(), "   ".length(), 0x7 ^ 0x9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x65 ^ 0x63, "   ".length(), 0xA7 ^ 0xAA, 0x41 ^ 0x47, "   ".length(), 0x2A ^ 0x24, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x8 ^ 0xE, "  ".length(), "   ".length(), 0x21 ^ 0x27, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5C ^ 0x59, " ".length(), 0xC3 ^ 0xC5, 0x2 ^ 0x7, "   ".length(), 0x68 ^ 0x6E, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x93 ^ 0x9A, "  ".length(), "   ".length(), 0x1F ^ 0x16, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x58 ^ 0x5D, " ".length(), 0x65 ^ 0x6C, 0x36 ^ 0x33, "   ".length(), 0xBA ^ 0xB3, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), 0xC6 ^ 0xC0, 0x42 ^ 0x46, "  ".length(), 0x5C ^ 0x5A, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), 0x68 ^ 0x61, 0x93 ^ 0x97, "  ".length(), 0x5D ^ 0x54, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "  ".length(), 0xB6 ^ 0xB1, "  ".length(), "  ".length(), 0x52 ^ 0x5A, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5B ^ 0x5E, "  ".length(), 0xD ^ 0xA, 0x60 ^ 0x65, "  ".length(), 0x7B ^ 0x73, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, "".length() != 0);
            this.setBlockState(world, DoubleZRoom.field_175825_e, "  ".length(), "  ".length(), 0x69 ^ 0x6C, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 0x46 ^ 0x43, "  ".length(), 0x4F ^ 0x4A, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, "  ".length(), "  ".length(), 0x48 ^ 0x42, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175825_e, 0x1F ^ 0x1A, "  ".length(), 0x58 ^ 0x52, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, "  ".length(), "   ".length(), 0xA9 ^ 0xAC, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 0xAA ^ 0xAF, "   ".length(), 0xC5 ^ 0xC0, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, "  ".length(), "   ".length(), 0x65 ^ 0x6F, structureBoundingBox);
            this.setBlockState(world, DoubleZRoom.field_175826_b, 0x15 ^ 0x10, "   ".length(), 0x27 ^ 0x2D, structureBoundingBox);
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x7E ^ 0x7A, "  ".length(), "".length(), "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x3C ^ 0x3B, " ".length(), "   ".length(), 0xC6 ^ 0xC1, "  ".length(), 0xC1 ^ 0xC5, "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0xA3 ^ 0xA7, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0x2F ^ 0x20, 0x9A ^ 0x9E, "  ".length(), 0x6F ^ 0x60, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), 0x89 ^ 0x82, "".length(), "  ".length(), 0x5B ^ 0x57, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xA7 ^ 0xA0, " ".length(), 0xCC ^ 0xC7, 0xA6 ^ 0xA1, "  ".length(), 0x3 ^ 0xF, "".length() != 0);
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public DoubleZRoom() {
        }
    }
    
    public static class MonumentBuilding extends Piece
    {
        private RoomDefinition field_175844_p;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        private RoomDefinition field_175845_o;
        private List<Piece> field_175843_q;
        
        private void func_175841_d(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0x5E ^ 0x4B, 0xBC ^ 0xA9, 0xF ^ 0x2B, 0x8A ^ 0xAE)) {
                this.fillWithBlocks(world, structureBoundingBox, 0x63 ^ 0x76, "".length(), 0x20 ^ 0x36, 0x26 ^ 0x2, "".length(), 0x22 ^ 0x6, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x99 ^ 0x8C, " ".length(), 0x17 ^ 0x1, 0x56 ^ 0x72, 0xD6 ^ 0xC1, 0x58 ^ 0x7C, "".length() != 0);
                int i = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
                while (i < (0x21 ^ 0x25)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0xA2 ^ 0xB7) + i, (0x20 ^ 0x2D) + i, (0xB0 ^ 0xA5) + i, (0x7C ^ 0x58) - i, (0x70 ^ 0x7D) + i, (0x33 ^ 0x26) + i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, (0x14 ^ 0x1) + i, (0x1A ^ 0x17) + i, (0xA4 ^ 0x80) - i, (0x98 ^ 0xBC) - i, (0xCB ^ 0xC6) + i, (0x1D ^ 0x39) - i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, (0x54 ^ 0x41) + i, (0x36 ^ 0x3B) + i, (0x97 ^ 0x81) + i, (0x34 ^ 0x21) + i, (0x52 ^ 0x5F) + i, (0x58 ^ 0x7B) - i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, (0x7A ^ 0x5E) - i, (0x75 ^ 0x78) + i, (0xA0 ^ 0xB6) + i, (0xAB ^ 0x8F) - i, (0x35 ^ 0x38) + i, (0x98 ^ 0xBB) - i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++i;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0x6C ^ 0x75, 0x65 ^ 0x75, 0x1A ^ 0x3, 0x8D ^ 0xAD, 0x6D ^ 0x7D, 0x37 ^ 0x17, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x4 ^ 0x1D, 0x4F ^ 0x5E, 0x3E ^ 0x27, 0xAC ^ 0xB5, 0xD5 ^ 0xC6, 0x85 ^ 0x9C, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xBD ^ 0x9D, 0xD1 ^ 0xC0, 0x3C ^ 0x25, 0xE ^ 0x2E, 0x49 ^ 0x5A, 0x80 ^ 0x99, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xBC ^ 0xA5, 0x9E ^ 0x8F, 0x57 ^ 0x77, 0x11 ^ 0x8, 0x76 ^ 0x65, 0x55 ^ 0x75, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x58 ^ 0x78, 0x76 ^ 0x67, 0x9D ^ 0xBD, 0x4F ^ 0x6F, 0x4C ^ 0x5F, 0x25 ^ 0x5, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x75 ^ 0x6F, 0xB8 ^ 0xAC, 0x26 ^ 0x3C, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xBD ^ 0xA6, 0x65 ^ 0x70, 0xB5 ^ 0xAE, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 0x5C ^ 0x47, 0xB3 ^ 0xA7, 0x69 ^ 0x72, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xAC ^ 0xB6, 0x80 ^ 0x94, 0xF ^ 0x10, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xDC ^ 0xC7, 0x94 ^ 0x81, 0x74 ^ 0x6A, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 0x28 ^ 0x33, 0x78 ^ 0x6C, 0x1 ^ 0x1F, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xBF ^ 0xA0, 0x44 ^ 0x50, 0x78 ^ 0x67, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x82 ^ 0x9C, 0x19 ^ 0xC, 0x3D ^ 0x23, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 0x71 ^ 0x6F, 0x84 ^ 0x90, 0x14 ^ 0xA, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x6A ^ 0x75, 0x3B ^ 0x2F, 0x8B ^ 0x91, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x43 ^ 0x5D, 0x83 ^ 0x96, 0x55 ^ 0x4E, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175825_e, 0x46 ^ 0x58, 0xA1 ^ 0xB5, 0xA6 ^ 0xBD, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0x22 ^ 0x3E, 0x64 ^ 0x71, 0x6E ^ 0x75, 0xB3 ^ 0xAE, 0x2B ^ 0x3E, 0x10 ^ 0xB, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x24 ^ 0x3F, 0x27 ^ 0x32, 0x83 ^ 0x9F, 0xAB ^ 0xB0, 0xAA ^ 0xBF, 0xD8 ^ 0xC5, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB ^ 0x17, 0xAA ^ 0xBF, 0x9F ^ 0x81, 0x8D ^ 0x90, 0x40 ^ 0x55, 0x5B ^ 0x45, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x1 ^ 0x1F, 0xAB ^ 0xBE, 0xBF ^ 0xA3, 0x9C ^ 0x82, 0x76 ^ 0x63, 0x1 ^ 0x1C, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
            }
        }
        
        private void func_175840_a(final boolean b, final int n, final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, n, "".length(), n + (0x33 ^ 0x24), 0x5B ^ 0x4F)) {
                this.fillWithBlocks(world, structureBoundingBox, n + "".length(), "".length(), "".length(), n + (0x12 ^ 0xA), "".length(), 0x13 ^ 0x7, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, n + "".length(), " ".length(), "".length(), n + (0x68 ^ 0x70), 0x54 ^ 0x5E, 0x49 ^ 0x5D, "".length() != 0);
                int i = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (i < (0x5 ^ 0x1)) {
                    this.fillWithBlocks(world, structureBoundingBox, n + i, i + " ".length(), i, n + i, i + " ".length(), 0xD2 ^ 0xC6, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n + i + (0x86 ^ 0x81), i + (0x43 ^ 0x46), i + (0x89 ^ 0x8E), n + i + (0xAC ^ 0xAB), i + (0x57 ^ 0x52), 0x6B ^ 0x7F, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n + (0x1B ^ 0xA) - i, i + (0x63 ^ 0x66), i + (0x7 ^ 0x0), n + (0x4 ^ 0x15) - i, i + (0x30 ^ 0x35), 0x5D ^ 0x49, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n + (0xDF ^ 0xC7) - i, i + " ".length(), i, n + (0x93 ^ 0x8B) - i, i + " ".length(), 0x2A ^ 0x3E, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n + i + " ".length(), i + " ".length(), i, n + (0x7F ^ 0x68) - i, i + " ".length(), i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n + i + (0x61 ^ 0x69), i + (0x68 ^ 0x6D), i + (0xA0 ^ 0xA7), n + (0xAA ^ 0xBA) - i, i + (0x6C ^ 0x69), i + (0x98 ^ 0x9F), MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++i;
                }
                this.fillWithBlocks(world, structureBoundingBox, n + (0xB2 ^ 0xB6), 0x2 ^ 0x6, 0x9A ^ 0x9E, n + (0x12 ^ 0x14), 0x38 ^ 0x3C, 0x2F ^ 0x3B, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0x37 ^ 0x30), 0x3F ^ 0x3B, 0x3E ^ 0x3A, n + (0xD2 ^ 0xC3), 0xA6 ^ 0xA2, 0x50 ^ 0x56, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0xD ^ 0x1F), 0xA6 ^ 0xA2, 0x1A ^ 0x1E, n + (0x25 ^ 0x31), 0x93 ^ 0x97, 0xA ^ 0x1E, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0x94 ^ 0x9F), 0x52 ^ 0x5A, 0x4F ^ 0x44, n + (0x79 ^ 0x74), 0x53 ^ 0x5B, 0x24 ^ 0x30, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.setBlockState(world, MonumentBuilding.field_175824_d, n + (0x59 ^ 0x55), 0x66 ^ 0x6F, 0x67 ^ 0x6B, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175824_d, n + (0xA3 ^ 0xAF), 0x8F ^ 0x86, 0x73 ^ 0x7C, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175824_d, n + (0x56 ^ 0x5A), 0xB6 ^ 0xBF, 0x51 ^ 0x43, structureBoundingBox);
                int n2;
                if (b) {
                    n2 = n + (0x1A ^ 0x9);
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                }
                else {
                    n2 = n + (0x6D ^ 0x68);
                }
                final int n3 = n2;
                int n4;
                if (b) {
                    n4 = n + (0x59 ^ 0x5C);
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    n4 = n + (0x19 ^ 0xA);
                }
                final int n5 = n4;
                int j = 0x88 ^ 0x9C;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (j >= (0x1C ^ 0x19)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n3, 0xC7 ^ 0xC2, j, structureBoundingBox);
                    j -= 3;
                }
                int k = 0x8F ^ 0x9C;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
                while (k >= (0x68 ^ 0x6F)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n5, 0x42 ^ 0x47, k, structureBoundingBox);
                    k -= 3;
                }
                int l = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (l < (0xC1 ^ 0xC5)) {
                    int n6;
                    if (b) {
                        n6 = n + ((0x26 ^ 0x3E) - ((0x29 ^ 0x38) - l * "   ".length()));
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else {
                        n6 = n + (0x79 ^ 0x68) - l * "   ".length();
                    }
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n6, 0x84 ^ 0x81, 0x25 ^ 0x20, structureBoundingBox);
                    ++l;
                }
                this.setBlockState(world, MonumentBuilding.field_175824_d, n5, 0xBA ^ 0xBF, 0x43 ^ 0x46, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, n + (0x5C ^ 0x57), " ".length(), 0x6D ^ 0x61, n + (0xA4 ^ 0xA9), 0x24 ^ 0x23, 0x77 ^ 0x7B, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, n + (0xCE ^ 0xC2), " ".length(), 0x99 ^ 0x92, n + (0xA0 ^ 0xAC), 0x38 ^ 0x3F, 0x3D ^ 0x30, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
            }
        }
        
        private void func_175842_f(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0x35 ^ 0x32, 0x5A ^ 0x4F, 0xBC ^ 0xB1, 0x15 ^ 0x27)) {
                this.fillWithBlocks(world, structureBoundingBox, 0xA5 ^ 0xA2, "".length(), 0x97 ^ 0x82, 0x4D ^ 0x40, "".length(), 0x77 ^ 0x45, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x6A ^ 0x6D, " ".length(), 0x55 ^ 0x40, 0x9E ^ 0x93, 0x97 ^ 0x9D, 0x21 ^ 0x13, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x9C ^ 0x97, 0x21 ^ 0x29, 0x97 ^ 0x82, 0x9A ^ 0x97, 0x24 ^ 0x2C, 0x7C ^ 0x49, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                int i = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (i < (0xB6 ^ 0xB2)) {
                    this.fillWithBlocks(world, structureBoundingBox, i + (0xB6 ^ 0xB1), i + (0x49 ^ 0x4C), 0x49 ^ 0x5C, i + (0xBE ^ 0xB9), i + (0x9D ^ 0x98), 0x59 ^ 0x6F, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++i;
                }
                int j = 0x6 ^ 0x13;
                "".length();
                if (0 == 2) {
                    throw null;
                }
                while (j <= (0x42 ^ 0x6F)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0x92 ^ 0x9E, 0x39 ^ 0x30, j, structureBoundingBox);
                    j += 3;
                }
            }
            if (this.func_175818_a(structureBoundingBox, 0x81 ^ 0xAD, 0x3F ^ 0x2A, 0x2C ^ 0x1E, 0x56 ^ 0x60)) {
                this.fillWithBlocks(world, structureBoundingBox, 0xF ^ 0x23, "".length(), 0x36 ^ 0x23, 0xB7 ^ 0x85, "".length(), 0x2B ^ 0x19, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x5F ^ 0x73, " ".length(), 0xA3 ^ 0xB6, 0x25 ^ 0x17, 0x35 ^ 0x3F, 0x87 ^ 0xB5, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xA ^ 0x26, 0x14 ^ 0x1C, 0x89 ^ 0x9C, 0x4B ^ 0x65, 0xCE ^ 0xC6, 0x92 ^ 0xA7, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                int k = "".length();
                "".length();
                if (4 == 1) {
                    throw null;
                }
                while (k < (0xB8 ^ 0xBC)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0x99 ^ 0xAB) - k, k + (0x9B ^ 0x9E), 0x8F ^ 0x9A, (0xBA ^ 0x88) - k, k + (0x6 ^ 0x3), 0x3B ^ 0xD, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++k;
                }
                int l = 0xAA ^ 0xBF;
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (l <= (0xBF ^ 0x92)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0x7D ^ 0x50, 0x36 ^ 0x3F, l, structureBoundingBox);
                    l += 3;
                }
            }
            if (this.func_175818_a(structureBoundingBox, 0xA3 ^ 0xAB, 0x20 ^ 0xC, 0xF1 ^ 0xC0, 0xB2 ^ 0x84)) {
                this.fillWithBlocks(world, structureBoundingBox, 0xC9 ^ 0xC7, "".length(), 0x69 ^ 0x45, 0x72 ^ 0x59, "".length(), 0xB9 ^ 0x8B, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x8 ^ 0x6, " ".length(), 0x5 ^ 0x29, 0x5E ^ 0x75, 0xB7 ^ 0xBD, 0x89 ^ 0xBB, "".length() != 0);
                int n = 0xC8 ^ 0xC4;
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (n <= (0xA3 ^ 0x8E)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x9 ^ 0x0, 0x50 ^ 0x7D, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x86 ^ 0x8F, 0xF6 ^ 0xC2, structureBoundingBox);
                    if (n == (0x69 ^ 0x65) || n == (0x72 ^ 0x60) || n == (0x77 ^ 0x6F) || n == (0x69 ^ 0x48) || n == (0x25 ^ 0x2) || n == (0xAF ^ 0x82)) {
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x30 ^ 0x39, 0x3 ^ 0x2C, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x4B ^ 0x42, 0x8D ^ 0xBF, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x26 ^ 0x2C, 0x30 ^ 0x1D, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0xA5 ^ 0xAF, 0x83 ^ 0xAD, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x50 ^ 0x5A, 0x97 ^ 0xA4, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x1B ^ 0x11, 0x52 ^ 0x66, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x30 ^ 0x3B, 0x75 ^ 0x5A, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x6B ^ 0x60, 0xE ^ 0x3C, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x2A ^ 0x26, 0x74 ^ 0x44, structureBoundingBox);
                        this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x3E ^ 0x32, 0xF4 ^ 0xC5, structureBoundingBox);
                    }
                    n += 3;
                }
                int length = "".length();
                "".length();
                if (3 == 1) {
                    throw null;
                }
                while (length < "   ".length()) {
                    this.fillWithBlocks(world, structureBoundingBox, (0x9A ^ 0x92) + length, (0x84 ^ 0x81) + length, 0x90 ^ 0xA6, (0xBE ^ 0x8F) - length, (0xC0 ^ 0xC5) + length, 0x8A ^ 0xBC, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                    ++length;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0x5F ^ 0x54, 0x8B ^ 0x83, 0x8 ^ 0x3E, 0x36 ^ 0x18, 0xB6 ^ 0xBE, 0x58 ^ 0x6E, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x45 ^ 0x4B, 0xA3 ^ 0xAB, 0x49 ^ 0x65, 0xB9 ^ 0x92, 0x74 ^ 0x7C, 0x73 ^ 0x46, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
            }
        }
        
        public MonumentBuilding() {
            this.field_175843_q = (List<Piece>)Lists.newArrayList();
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private void func_175839_b(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0x49 ^ 0x5F, 0x27 ^ 0x22, 0x4B ^ 0x68, 0x1F ^ 0xE)) {
                this.func_181655_a(world, structureBoundingBox, 0xB8 ^ 0xA1, "".length(), "".length(), 0x29 ^ 0x9, 0x77 ^ 0x7F, 0x67 ^ 0x73, "".length() != 0);
                int i = "".length();
                "".length();
                if (0 == 4) {
                    throw null;
                }
                while (i < (0x91 ^ 0x95)) {
                    this.fillWithBlocks(world, structureBoundingBox, 0x16 ^ 0xE, "  ".length(), (0xC0 ^ 0xC5) + i * (0x2E ^ 0x2A), 0x29 ^ 0x31, 0x85 ^ 0x81, (0x6C ^ 0x69) + i * (0x55 ^ 0x51), MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0xA4 ^ 0xB2, 0x96 ^ 0x92, (0x36 ^ 0x33) + i * (0x7 ^ 0x3), 0x66 ^ 0x71, 0x9C ^ 0x98, (0x12 ^ 0x17) + i * (0x35 ^ 0x31), MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.setBlockState(world, MonumentBuilding.field_175826_b, 0x35 ^ 0x2C, 0x8F ^ 0x8A, (0x52 ^ 0x57) + i * (0x7E ^ 0x7A), structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175826_b, 0xE ^ 0x14, 0xA3 ^ 0xA5, (0x49 ^ 0x4C) + i * (0x82 ^ 0x86), structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175825_e, 0xDC ^ 0xC6, 0x22 ^ 0x27, (0x26 ^ 0x23) + i * (0x53 ^ 0x57), structureBoundingBox);
                    this.fillWithBlocks(world, structureBoundingBox, 0x3B ^ 0x1A, "  ".length(), (0xBE ^ 0xBB) + i * (0xB3 ^ 0xB7), 0x70 ^ 0x51, 0x89 ^ 0x8D, (0x23 ^ 0x26) + i * (0x2A ^ 0x2E), MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x0 ^ 0x22, 0x19 ^ 0x1D, (0x13 ^ 0x16) + i * (0x4F ^ 0x4B), 0xB8 ^ 0x9B, 0x94 ^ 0x90, (0x37 ^ 0x32) + i * (0x44 ^ 0x40), MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    this.setBlockState(world, MonumentBuilding.field_175826_b, 0x7F ^ 0x5F, 0x4 ^ 0x1, (0xC6 ^ 0xC3) + i * (0xC2 ^ 0xC6), structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175826_b, 0x2C ^ 0x33, 0x3F ^ 0x39, (0x2F ^ 0x2A) + i * (0x9F ^ 0x9B), structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175825_e, 0x14 ^ 0xB, 0x53 ^ 0x56, (0x91 ^ 0x94) + i * (0xAA ^ 0xAE), structureBoundingBox);
                    this.fillWithBlocks(world, structureBoundingBox, 0xB5 ^ 0xAE, 0x14 ^ 0x12, (0x2D ^ 0x28) + i * (0x95 ^ 0x91), 0x91 ^ 0x8F, 0x2D ^ 0x2B, (0x8F ^ 0x8A) + i * (0xB7 ^ 0xB3), MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                    ++i;
                }
            }
        }
        
        private void func_175835_e(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, "".length(), 0xC ^ 0x19, 0x19 ^ 0x1F, 0xAC ^ 0x96)) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), 0x2F ^ 0x3A, 0x6D ^ 0x6B, "".length(), 0x6C ^ 0x55, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), 0x77 ^ 0x62, 0xA0 ^ 0xA6, 0x16 ^ 0x11, 0x59 ^ 0x60, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x6 ^ 0x2, 0xA3 ^ 0xA7, 0x9A ^ 0x8F, 0xA5 ^ 0xA3, 0x58 ^ 0x5C, 0x52 ^ 0x67, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                int i = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (i < (0x74 ^ 0x70)) {
                    this.fillWithBlocks(world, structureBoundingBox, i, i + " ".length(), 0x5D ^ 0x48, i, i + " ".length(), (0x1C ^ 0x25) - i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++i;
                }
                int j = 0x9D ^ 0x8A;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (j < (0xD ^ 0x38)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0xA1 ^ 0xA4, 0x67 ^ 0x62, j, structureBoundingBox);
                    j += 3;
                }
                this.setBlockState(world, MonumentBuilding.field_175824_d, 0x6 ^ 0x3, 0x66 ^ 0x63, 0x68 ^ 0x5C, structureBoundingBox);
                int k = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (k < (0xB1 ^ 0xB5)) {
                    this.fillWithBlocks(world, structureBoundingBox, k, k + " ".length(), 0x61 ^ 0x74, k, k + " ".length(), (0x8F ^ 0xB6) - k, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++k;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0xC ^ 0x8, " ".length(), 0x65 ^ 0x51, 0x86 ^ 0x80, "   ".length(), 0x73 ^ 0x47, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB8 ^ 0xBD, " ".length(), 0xF6 ^ 0xC5, 0xC7 ^ 0xC2, "   ".length(), 0x19 ^ 0x2C, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
            }
            if (this.func_175818_a(structureBoundingBox, 0xB5 ^ 0x86, 0x14 ^ 0x1, 0x77 ^ 0x4D, 0x44 ^ 0x7E)) {
                this.fillWithBlocks(world, structureBoundingBox, 0x22 ^ 0x11, "".length(), 0xD6 ^ 0xC3, 0x66 ^ 0x5F, "".length(), 0xB6 ^ 0x8F, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x4B ^ 0x78, " ".length(), 0x45 ^ 0x50, 0x62 ^ 0x5B, 0x76 ^ 0x71, 0x7C ^ 0x45, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xA1 ^ 0x92, 0x13 ^ 0x17, 0xB0 ^ 0xA5, 0x16 ^ 0x23, 0x6F ^ 0x6B, 0xF2 ^ 0xC7, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                int l = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (l < (0xC ^ 0x8)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0xA5 ^ 0x9C) - l, l + " ".length(), 0x2 ^ 0x17, (0x36 ^ 0xF) - l, l + " ".length(), (0x56 ^ 0x6F) - l, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++l;
                }
                int n = 0x9A ^ 0x8D;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (n < (0x63 ^ 0x56)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0x8B ^ 0xBF, 0xC0 ^ 0xC5, n, structureBoundingBox);
                    n += 3;
                }
                this.setBlockState(world, MonumentBuilding.field_175824_d, 0x13 ^ 0x27, 0x1A ^ 0x1F, 0x31 ^ 0x5, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0x30 ^ 0x3, " ".length(), 0x95 ^ 0xA1, 0x78 ^ 0x4D, "   ".length(), 0xA ^ 0x3E, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x1C ^ 0x28, " ".length(), 0x6C ^ 0x5F, 0x10 ^ 0x24, "   ".length(), 0xF ^ 0x3A, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
            }
            if (this.func_175818_a(structureBoundingBox, "".length(), 0x30 ^ 0x3, 0x26 ^ 0x1F, 0xAC ^ 0x95)) {
                this.fillWithBlocks(world, structureBoundingBox, 0xD ^ 0xA, "".length(), 0x80 ^ 0xB3, 0x29 ^ 0x1B, "".length(), 0xA0 ^ 0x99, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x8F ^ 0x88, " ".length(), 0x53 ^ 0x60, 0x8D ^ 0xBF, 0x8C ^ 0x86, 0x2F ^ 0x16, "".length() != 0);
                int length = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
                while (length < (0x28 ^ 0x2C)) {
                    this.fillWithBlocks(world, structureBoundingBox, length + " ".length(), length + " ".length(), (0x45 ^ 0x7C) - length, (0x3A ^ 0x2) - length, length + " ".length(), (0x8A ^ 0xB3) - length, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++length;
                }
            }
        }
        
        private void func_175837_c(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0x85 ^ 0x8A, 0x3 ^ 0x17, 0xA8 ^ 0x82, 0xA1 ^ 0xB4)) {
                this.fillWithBlocks(world, structureBoundingBox, 0x5 ^ 0xA, "".length(), 0x5 ^ 0x10, 0x10 ^ 0x3A, "".length(), 0x37 ^ 0x22, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x9A ^ 0x80, " ".length(), 0x51 ^ 0x44, 0x63 ^ 0x7C, "   ".length(), 0x33 ^ 0x26, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x2C ^ 0x39, 0x3A ^ 0x36, 0x23 ^ 0x36, 0x4E ^ 0x6A, 0x68 ^ 0x64, 0xBB ^ 0xAE, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x42 ^ 0x53, 0x32 ^ 0x39, 0xA1 ^ 0xB4, 0x4B ^ 0x63, 0xB4 ^ 0xBF, 0x46 ^ 0x53, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xA2 ^ 0xB2, 0xCC ^ 0xC6, 0x6D ^ 0x78, 0x7F ^ 0x56, 0x6C ^ 0x66, 0x57 ^ 0x42, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x5B ^ 0x54, 0xB0 ^ 0xB7, 0x82 ^ 0x97, 0xBD ^ 0x97, 0xD ^ 0x4, 0x23 ^ 0x36, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x7 ^ 0x17, 0x1B ^ 0x1D, 0xBF ^ 0xAA, 0x76 ^ 0x5F, 0x82 ^ 0x84, 0x43 ^ 0x56, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x45 ^ 0x54, 0x70 ^ 0x75, 0x79 ^ 0x6C, 0x45 ^ 0x6D, 0x49 ^ 0x4C, 0x5C ^ 0x49, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x4D ^ 0x58, 0x9E ^ 0x9A, 0xD6 ^ 0xC3, 0x2C ^ 0x8, 0xF ^ 0xB, 0xB2 ^ 0xA7, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xD4 ^ 0xC2, "   ".length(), 0xAF ^ 0xBA, 0x1A ^ 0x0, "   ".length(), 0xA0 ^ 0xB5, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x1A ^ 0x5, "   ".length(), 0x52 ^ 0x47, 0x5D ^ 0x7E, "   ".length(), 0xAA ^ 0xBF, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x23 ^ 0x34, "  ".length(), 0x29 ^ 0x3C, 0x18 ^ 0x1, "  ".length(), 0x3D ^ 0x28, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x67 ^ 0x47, "  ".length(), 0x1E ^ 0xB, 0x4F ^ 0x6D, "  ".length(), 0x94 ^ 0x81, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x62 ^ 0x7E, 0x8D ^ 0x89, 0x7D ^ 0x69, 0x4F ^ 0x52, 0x9E ^ 0x9A, 0x35 ^ 0x20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xD ^ 0x16, "   ".length(), 0x37 ^ 0x22, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0xA ^ 0x14, "   ".length(), 0x40 ^ 0x55, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x89 ^ 0x93, "  ".length(), 0xA7 ^ 0xB2, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x10 ^ 0xF, "  ".length(), 0xA1 ^ 0xB4, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x56 ^ 0x4F, " ".length(), 0xBE ^ 0xAB, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175826_b, 0x5C ^ 0x7C, " ".length(), 0x2A ^ 0x3F, structureBoundingBox);
                int i = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (i < (0x7 ^ 0x0)) {
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0x2D ^ 0x31) - i, (0xE ^ 0x8) + i, 0x2E ^ 0x3B, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0xB ^ 0x16) + i, (0xB1 ^ 0xB7) + i, 0x2B ^ 0x3E, structureBoundingBox);
                    ++i;
                }
                int j = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (j < (0xA2 ^ 0xA6)) {
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0xC ^ 0x10) - j, (0x3 ^ 0xA) + j, 0xA2 ^ 0xB7, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0xAF ^ 0xB2) + j, (0xA6 ^ 0xAF) + j, 0x27 ^ 0x32, structureBoundingBox);
                    ++j;
                }
                this.setBlockState(world, MonumentBuilding.field_175827_c, 0x8F ^ 0x93, 0x76 ^ 0x7A, 0x46 ^ 0x53, structureBoundingBox);
                this.setBlockState(world, MonumentBuilding.field_175827_c, 0xDB ^ 0xC6, 0xA8 ^ 0xA4, 0x7E ^ 0x6B, structureBoundingBox);
                int k = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (k < "   ".length()) {
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0x5A ^ 0x4C) - k * "  ".length(), 0xBB ^ 0xB3, 0x14 ^ 0x1, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0xF ^ 0x19) - k * "  ".length(), 0x6E ^ 0x67, 0x2C ^ 0x39, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0x67 ^ 0x44) + k * "  ".length(), 0x99 ^ 0x91, 0xF ^ 0x1A, structureBoundingBox);
                    this.setBlockState(world, MonumentBuilding.field_175827_c, (0x60 ^ 0x43) + k * "  ".length(), 0xB8 ^ 0xB1, 0xA8 ^ 0xBD, structureBoundingBox);
                    ++k;
                }
                this.func_181655_a(world, structureBoundingBox, 0x8D ^ 0x82, 0x51 ^ 0x5C, 0x68 ^ 0x7D, 0xEC ^ 0xC6, 0x51 ^ 0x5E, 0x7 ^ 0x12, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x3 ^ 0xC, " ".length(), 0x2B ^ 0x3E, 0x28 ^ 0x27, 0x6E ^ 0x68, 0x73 ^ 0x66, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xD6 ^ 0xC6, " ".length(), 0x47 ^ 0x52, 0x9A ^ 0x8A, 0x98 ^ 0x9D, 0x73 ^ 0x66, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x6F ^ 0x7E, " ".length(), 0x1E ^ 0xB, 0x9 ^ 0x1D, 0x9F ^ 0x9B, 0x84 ^ 0x91, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x36 ^ 0x23, " ".length(), 0x35 ^ 0x20, 0x67 ^ 0x72, "   ".length(), 0x6F ^ 0x7A, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xD3 ^ 0xC5, " ".length(), 0x60 ^ 0x75, 0x3D ^ 0x2B, "  ".length(), 0x8F ^ 0x9A, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x4B ^ 0x5C, " ".length(), 0xB9 ^ 0xAC, 0x89 ^ 0x91, " ".length(), 0x10 ^ 0x5, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x5F ^ 0x75, " ".length(), 0xAC ^ 0xB9, 0xBF ^ 0x95, 0x88 ^ 0x8E, 0xD2 ^ 0xC7, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xA2 ^ 0x8B, " ".length(), 0x7F ^ 0x6A, 0x4D ^ 0x64, 0x2A ^ 0x2F, 0x45 ^ 0x50, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xB1 ^ 0x94, " ".length(), 0x42 ^ 0x57, 0x78 ^ 0x50, 0x63 ^ 0x67, 0xA7 ^ 0xB2, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x12 ^ 0x36, " ".length(), 0x1D ^ 0x8, 0x2D ^ 0x9, "   ".length(), 0x4F ^ 0x5A, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xB8 ^ 0x99, " ".length(), 0xB3 ^ 0xA6, 0x96 ^ 0xB4, " ".length(), 0x96 ^ 0x83, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x15 ^ 0x36, " ".length(), 0x2D ^ 0x38, 0x5 ^ 0x26, "  ".length(), 0x6B ^ 0x7E, "".length() != 0);
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = MonumentBuilding.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x95 ^ 0x93);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x77 ^ 0x73);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x1 ^ 0x4);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return MonumentBuilding.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        private List<RoomDefinition> func_175836_a(final Random random) {
            final RoomDefinition[] array = new RoomDefinition[0x2C ^ 0x67];
            int i = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (i < (0x2B ^ 0x2E)) {
                int j = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
                while (j < (0x27 ^ 0x23)) {
                    final int func_175820_a = Piece.func_175820_a(i, "".length(), j);
                    array[func_175820_a] = new RoomDefinition(func_175820_a);
                    ++j;
                }
                ++i;
            }
            int k = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (k < (0x78 ^ 0x7D)) {
                int l = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (l < (0xAC ^ 0xA8)) {
                    final int func_175820_a2 = Piece.func_175820_a(k, " ".length(), l);
                    array[func_175820_a2] = new RoomDefinition(func_175820_a2);
                    ++l;
                }
                ++k;
            }
            int length = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (length < (0xA9 ^ 0xAD)) {
                int length2 = "".length();
                "".length();
                if (2 == 1) {
                    throw null;
                }
                while (length2 < "  ".length()) {
                    final int func_175820_a3 = Piece.func_175820_a(length, "  ".length(), length2);
                    array[func_175820_a3] = new RoomDefinition(func_175820_a3);
                    ++length2;
                }
                ++length;
            }
            this.field_175845_o = array[MonumentBuilding.field_175823_g];
            int length3 = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (length3 < (0x8D ^ 0x88)) {
                int length4 = "".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
                while (length4 < (0x2F ^ 0x2A)) {
                    int length5 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (length5 < "   ".length()) {
                        final int func_175820_a4 = Piece.func_175820_a(length3, length5, length4);
                        if (array[func_175820_a4] != null) {
                            final EnumFacing[] values;
                            final int length6 = (values = EnumFacing.values()).length;
                            int length7 = "".length();
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                            while (length7 < length6) {
                                final EnumFacing enumFacing = values[length7];
                                final int n = length3 + enumFacing.getFrontOffsetX();
                                final int n2 = length5 + enumFacing.getFrontOffsetY();
                                final int n3 = length4 + enumFacing.getFrontOffsetZ();
                                if (n >= 0 && n < (0x2B ^ 0x2E) && n3 >= 0 && n3 < (0x8D ^ 0x88) && n2 >= 0 && n2 < "   ".length()) {
                                    final int func_175820_a5 = Piece.func_175820_a(n, n2, n3);
                                    if (array[func_175820_a5] != null) {
                                        if (n3 != length4) {
                                            array[func_175820_a4].func_175957_a(enumFacing.getOpposite(), array[func_175820_a5]);
                                            "".length();
                                            if (0 >= 4) {
                                                throw null;
                                            }
                                        }
                                        else {
                                            array[func_175820_a4].func_175957_a(enumFacing, array[func_175820_a5]);
                                        }
                                    }
                                }
                                ++length7;
                            }
                        }
                        ++length5;
                    }
                    ++length4;
                }
                ++length3;
            }
            final RoomDefinition roomDefinition;
            array[MonumentBuilding.field_175831_h].func_175957_a(EnumFacing.UP, roomDefinition = new RoomDefinition(815 + 505 - 594 + 277));
            final RoomDefinition roomDefinition2;
            array[MonumentBuilding.field_175832_i].func_175957_a(EnumFacing.SOUTH, roomDefinition2 = new RoomDefinition(762 + 393 - 302 + 148));
            final RoomDefinition roomDefinition3;
            array[MonumentBuilding.field_175829_j].func_175957_a(EnumFacing.SOUTH, roomDefinition3 = new RoomDefinition(390 + 797 - 1104 + 919));
            roomDefinition.field_175963_d = (" ".length() != 0);
            roomDefinition2.field_175963_d = (" ".length() != 0);
            roomDefinition3.field_175963_d = (" ".length() != 0);
            this.field_175845_o.field_175964_e = (" ".length() != 0);
            this.field_175844_p = array[Piece.func_175820_a(random.nextInt(0x51 ^ 0x55), "".length(), "  ".length())];
            this.field_175844_p.field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            final ArrayList arrayList = Lists.newArrayList();
            final RoomDefinition[] array2;
            final int length8 = (array2 = array).length;
            int length9 = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (length9 < length8) {
                final RoomDefinition roomDefinition4 = array2[length9];
                if (roomDefinition4 != null) {
                    roomDefinition4.func_175958_a();
                    arrayList.add(roomDefinition4);
                }
                ++length9;
            }
            roomDefinition.func_175958_a();
            Collections.shuffle(arrayList, random);
            int length10 = " ".length();
            final Iterator<RoomDefinition> iterator = (Iterator<RoomDefinition>)arrayList.iterator();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final RoomDefinition roomDefinition5 = iterator.next();
                int length11 = "".length();
                int length12 = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (length11 < "  ".length() && length12 < (0x68 ^ 0x6D)) {
                    ++length12;
                    final int nextInt = random.nextInt(0xE ^ 0x8);
                    if (roomDefinition5.field_175966_c[nextInt]) {
                        final int index = EnumFacing.getFront(nextInt).getOpposite().getIndex();
                        roomDefinition5.field_175966_c[nextInt] = ("".length() != 0);
                        roomDefinition5.field_175965_b[nextInt].field_175966_c[index] = ("".length() != 0);
                        if (roomDefinition5.func_175959_a(length10++) && roomDefinition5.field_175965_b[nextInt].func_175959_a(length10++)) {
                            ++length11;
                            "".length();
                            if (2 < 0) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            roomDefinition5.field_175966_c[nextInt] = (" ".length() != 0);
                            roomDefinition5.field_175965_b[nextInt].field_175966_c[index] = (" ".length() != 0);
                        }
                    }
                }
            }
            arrayList.add(roomDefinition);
            arrayList.add(roomDefinition2);
            arrayList.add(roomDefinition3);
            return (List<RoomDefinition>)arrayList;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.func_181655_a(world, structureBoundingBox, "".length(), "".length(), "".length(), 0x76 ^ 0x4C, Math.max(world.func_181545_F(), 0x61 ^ 0x21) - this.boundingBox.minY, 0x5C ^ 0x66, "".length() != 0);
            this.func_175840_a("".length() != 0, "".length(), world, random, structureBoundingBox);
            this.func_175840_a(" ".length() != 0, 0xB7 ^ 0x96, world, random, structureBoundingBox);
            this.func_175839_b(world, random, structureBoundingBox);
            this.func_175837_c(world, random, structureBoundingBox);
            this.func_175841_d(world, random, structureBoundingBox);
            this.func_175835_e(world, random, structureBoundingBox);
            this.func_175842_f(world, random, structureBoundingBox);
            this.func_175838_g(world, random, structureBoundingBox);
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i < (0x5A ^ 0x5D)) {
                int j = "".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
                while (j < (0x75 ^ 0x72)) {
                    if (j == 0 && i == "   ".length()) {
                        j = (0x6F ^ 0x69);
                    }
                    final int n = i * (0xA9 ^ 0xA0);
                    final int n2 = j * (0xBC ^ 0xB5);
                    int k = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    while (k < (0x9B ^ 0x9F)) {
                        int l = "".length();
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                        while (l < (0x7A ^ 0x7E)) {
                            this.setBlockState(world, MonumentBuilding.field_175826_b, n + k, "".length(), n2 + l, structureBoundingBox);
                            this.replaceAirAndLiquidDownwards(world, MonumentBuilding.field_175826_b, n + k, -" ".length(), n2 + l, structureBoundingBox);
                            ++l;
                        }
                        ++k;
                    }
                    if (i != 0 && i != (0x2D ^ 0x2B)) {
                        j += 6;
                        "".length();
                        if (2 < 1) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        ++j;
                    }
                }
                ++i;
            }
            int length = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (length < (0x2D ^ 0x28)) {
                this.func_181655_a(world, structureBoundingBox, -" ".length() - length, "".length() + length * "  ".length(), -" ".length() - length, -" ".length() - length, 0x92 ^ 0x85, (0x61 ^ 0x5B) + length, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, (0x84 ^ 0xBE) + length, "".length() + length * "  ".length(), -" ".length() - length, (0x62 ^ 0x58) + length, 0xB5 ^ 0xA2, (0xFA ^ 0xC0) + length, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, "".length() - length, "".length() + length * "  ".length(), -" ".length() - length, (0x5A ^ 0x63) + length, 0xB2 ^ 0xA5, -" ".length() - length, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, "".length() - length, "".length() + length * "  ".length(), (0x3F ^ 0x5) + length, (0x98 ^ 0xA1) + length, 0xBB ^ 0xAC, (0x64 ^ 0x5E) + length, "".length() != 0);
                ++length;
            }
            final Iterator<Piece> iterator = this.field_175843_q.iterator();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Piece piece = iterator.next();
                if (piece.getBoundingBox().intersectsWith(structureBoundingBox)) {
                    piece.addComponentParts(world, random, structureBoundingBox);
                }
            }
            return " ".length() != 0;
        }
        
        private void func_175838_g(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.func_175818_a(structureBoundingBox, 0x3 ^ 0xD, 0x2D ^ 0x38, 0x60 ^ 0x74, 0x8F ^ 0xA4)) {
                this.fillWithBlocks(world, structureBoundingBox, 0x80 ^ 0x8E, "".length(), 0x31 ^ 0x24, 0x6F ^ 0x7B, "".length(), 0xE9 ^ 0xC2, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0xA5 ^ 0xAB, " ".length(), 0x35 ^ 0x23, 0x64 ^ 0x70, 0x6B ^ 0x65, 0x80 ^ 0xAB, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x8E ^ 0x9C, 0x88 ^ 0x84, 0xD ^ 0x1B, 0x62 ^ 0x76, 0x9C ^ 0x90, 0x65 ^ 0x42, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x9D ^ 0x8F, 0x73 ^ 0x7F, 0x62 ^ 0x77, 0x66 ^ 0x72, 0x1 ^ 0xD, 0x35 ^ 0x20, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                int i = "".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (i < (0x6B ^ 0x6F)) {
                    this.fillWithBlocks(world, structureBoundingBox, i + (0x26 ^ 0x28), i + (0x5D ^ 0x54), 0x8 ^ 0x1D, i + (0x5A ^ 0x54), i + (0xCC ^ 0xC5), (0x61 ^ 0x4A) - i, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++i;
                }
                int j = 0x22 ^ 0x35;
                "".length();
                if (2 < 2) {
                    throw null;
                }
                while (j <= (0x5B ^ 0x7C)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0xB2 ^ 0xA1, 0x32 ^ 0x3F, j, structureBoundingBox);
                    j += 3;
                }
            }
            if (this.func_175818_a(structureBoundingBox, 0x68 ^ 0x4D, 0x1A ^ 0xF, 0x8 ^ 0x23, 0x3E ^ 0x15)) {
                this.fillWithBlocks(world, structureBoundingBox, 0xE7 ^ 0xC2, "".length(), 0x5 ^ 0x10, 0x6B ^ 0x40, "".length(), 0x90 ^ 0xBB, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x5E ^ 0x7B, " ".length(), 0x7E ^ 0x68, 0x54 ^ 0x7F, 0xA4 ^ 0xAA, 0xBC ^ 0x97, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x7B ^ 0x5E, 0x87 ^ 0x8B, 0x54 ^ 0x42, 0xA ^ 0x2D, 0xAB ^ 0xA7, 0x17 ^ 0x30, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x4E ^ 0x6B, 0x89 ^ 0x85, 0xA8 ^ 0xBD, 0x3B ^ 0x1C, 0x18 ^ 0x14, 0xB8 ^ 0xAD, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                int k = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (k < (0x3 ^ 0x7)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0x42 ^ 0x69) - k, k + (0xA2 ^ 0xAB), 0x5D ^ 0x48, (0xAA ^ 0x81) - k, k + (0x8B ^ 0x82), (0x4A ^ 0x61) - k, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++k;
                }
                int l = 0xAD ^ 0xBA;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (l <= (0xAB ^ 0x8C)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, 0x96 ^ 0xB0, 0xB2 ^ 0xBF, l, structureBoundingBox);
                    l += 3;
                }
            }
            if (this.func_175818_a(structureBoundingBox, 0xB1 ^ 0xBE, 0x1F ^ 0x3A, 0xBB ^ 0x91, 0x7B ^ 0x50)) {
                this.fillWithBlocks(world, structureBoundingBox, 0x95 ^ 0x80, "".length(), 0x75 ^ 0x50, 0xAB ^ 0x8F, "".length(), 0x89 ^ 0xA2, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                this.func_181655_a(world, structureBoundingBox, 0x1B ^ 0xE, " ".length(), 0x1B ^ 0x3E, 0x4B ^ 0x6F, 0xF ^ 0x1, 0x7A ^ 0x51, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x63 ^ 0x76, 0x87 ^ 0x8B, 0xA5 ^ 0x80, 0xBE ^ 0x9A, 0x2D ^ 0x21, 0x59 ^ 0x7E, MonumentBuilding.field_175828_a, MonumentBuilding.field_175828_a, "".length() != 0);
                int length = "".length();
                "".length();
                if (3 == 2) {
                    throw null;
                }
                while (length < (0xBA ^ 0xBE)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0x3B ^ 0x34) + length, length + (0xA6 ^ 0xAF), (0x12 ^ 0x39) - length, (0xB8 ^ 0x92) - length, length + (0x74 ^ 0x7D), (0x67 ^ 0x4C) - length, MonumentBuilding.field_175826_b, MonumentBuilding.field_175826_b, "".length() != 0);
                    ++length;
                }
                int n = 0xB ^ 0x1E;
                "".length();
                if (4 < 2) {
                    throw null;
                }
                while (n <= (0x47 ^ 0x63)) {
                    this.setBlockState(world, MonumentBuilding.field_175824_d, n, 0x6B ^ 0x66, 0xA9 ^ 0x8F, structureBoundingBox);
                    n += 3;
                }
            }
        }
        
        public MonumentBuilding(final Random random, final int n, final int n2, final EnumFacing coordBaseMode) {
            super("".length());
            this.field_175843_q = (List<Piece>)Lists.newArrayList();
            this.coordBaseMode = coordBaseMode;
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
                case 3:
                case 4: {
                    this.boundingBox = new StructureBoundingBox(n, 0x3A ^ 0x1D, n2, n + (0x3E ^ 0x4) - " ".length(), 0x6 ^ 0x3B, n2 + (0x0 ^ 0x3A) - " ".length());
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(n, 0x5C ^ 0x7B, n2, n + (0x79 ^ 0x43) - " ".length(), 0x22 ^ 0x1F, n2 + (0x4C ^ 0x76) - " ".length());
                    break;
                }
            }
            final List<RoomDefinition> func_175836_a = this.func_175836_a(random);
            this.field_175845_o.field_175963_d = (" ".length() != 0);
            this.field_175843_q.add(new EntryRoom(this.coordBaseMode, this.field_175845_o));
            this.field_175843_q.add(new MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, random));
            final ArrayList arrayList = Lists.newArrayList();
            arrayList.add(new XYDoubleRoomFitHelper(null));
            arrayList.add(new YZDoubleRoomFitHelper(null));
            arrayList.add(new ZDoubleRoomFitHelper(null));
            arrayList.add(new XDoubleRoomFitHelper(null));
            arrayList.add(new YDoubleRoomFitHelper(null));
            arrayList.add(new FitSimpleRoomTopHelper(null));
            arrayList.add(new FitSimpleRoomHelper(null));
            final Iterator<RoomDefinition> iterator = func_175836_a.iterator();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        Label_0482:
            while (iterator.hasNext()) {
                final RoomDefinition roomDefinition = iterator.next();
                if (!roomDefinition.field_175963_d && !roomDefinition.func_175961_b()) {
                    for (final MonumentRoomFitHelper monumentRoomFitHelper : arrayList) {
                        if (monumentRoomFitHelper.func_175969_a(roomDefinition)) {
                            this.field_175843_q.add(monumentRoomFitHelper.func_175968_a(this.coordBaseMode, roomDefinition, random));
                            continue Label_0482;
                        }
                    }
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    continue;
                }
            }
            final int minY = this.boundingBox.minY;
            final int xWithOffset = this.getXWithOffset(0x9E ^ 0x97, 0x7A ^ 0x6C);
            final int zWithOffset = this.getZWithOffset(0x11 ^ 0x18, 0xC ^ 0x1A);
            final Iterator<Piece> iterator3 = this.field_175843_q.iterator();
            "".length();
            if (1 == 4) {
                throw null;
            }
            while (iterator3.hasNext()) {
                iterator3.next().getBoundingBox().offset(xWithOffset, minY, zWithOffset);
            }
            final StructureBoundingBox func_175899_a = StructureBoundingBox.func_175899_a(this.getXWithOffset(" ".length(), " ".length()), this.getYWithOffset(" ".length()), this.getZWithOffset(" ".length(), " ".length()), this.getXWithOffset(0x5D ^ 0x4A, 0xB5 ^ 0xA0), this.getYWithOffset(0x5E ^ 0x56), this.getZWithOffset(0x31 ^ 0x26, 0x2C ^ 0x39));
            final StructureBoundingBox func_175899_a2 = StructureBoundingBox.func_175899_a(this.getXWithOffset(0x9A ^ 0xB8, " ".length()), this.getYWithOffset(" ".length()), this.getZWithOffset(0x4F ^ 0x6D, " ".length()), this.getXWithOffset(0x1E ^ 0x26, 0x6F ^ 0x7A), this.getYWithOffset(0x7B ^ 0x73), this.getZWithOffset(0xBD ^ 0x85, 0x6F ^ 0x7A));
            final StructureBoundingBox func_175899_a3 = StructureBoundingBox.func_175899_a(this.getXWithOffset(0x82 ^ 0x94, 0x17 ^ 0x1), this.getYWithOffset(0x8 ^ 0x5), this.getZWithOffset(0x69 ^ 0x7F, 0x6A ^ 0x7C), this.getXWithOffset(0xA9 ^ 0x8A, 0x6C ^ 0x4F), this.getYWithOffset(0x50 ^ 0x41), this.getZWithOffset(0x97 ^ 0xB4, 0xB7 ^ 0x94));
            int nextInt = random.nextInt();
            this.field_175843_q.add(new WingRoom(this.coordBaseMode, func_175899_a, nextInt++));
            this.field_175843_q.add(new WingRoom(this.coordBaseMode, func_175899_a2, nextInt++));
            this.field_175843_q.add(new Penthouse(this.coordBaseMode, func_175899_a3));
        }
    }
    
    static class XYDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (!roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] || roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d || !roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] || roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                return "".length() != 0;
            }
            final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()];
            if (roomDefinition2.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        XYDoubleRoomFitHelper(final XYDoubleRoomFitHelper xyDoubleRoomFitHelper) {
            this();
        }
        
        private XYDoubleRoomFitHelper() {
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            return new DoubleXYRoom(enumFacing, roomDefinition, random);
        }
    }
    
    public static class DoubleXYRoom extends Piece
    {
        public DoubleXYRoom() {
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
        
        public DoubleXYRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, "  ".length(), "  ".length(), " ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            final RoomDefinition roomDefinition2 = field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            final RoomDefinition roomDefinition3 = roomDefinition.field_175965_b[EnumFacing.UP.getIndex()];
            if (this.field_175830_k.field_175967_a / (0xBA ^ 0xA3) > 0) {
                this.func_175821_a(world, structureBoundingBox, 0x57 ^ 0x5F, "".length(), roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x9E ^ 0x96, " ".length(), 0x63 ^ 0x64, 0x82 ^ 0x8A, 0x13 ^ 0x15, DoubleXYRoom.field_175828_a);
            }
            if (roomDefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 0x57 ^ 0x5F, 0x43 ^ 0x4B, " ".length(), 0x2E ^ 0x20, 0x32 ^ 0x3A, 0x38 ^ 0x3E, DoubleXYRoom.field_175828_a);
            }
            int i = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i <= (0x51 ^ 0x56)) {
                IBlockState blockState = DoubleXYRoom.field_175826_b;
                if (i == "  ".length() || i == (0x7E ^ 0x78)) {
                    blockState = DoubleXYRoom.field_175828_a;
                }
                this.fillWithBlocks(world, structureBoundingBox, "".length(), i, "".length(), "".length(), i, 0x1 ^ 0x6, blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x3E ^ 0x31, i, "".length(), 0x54 ^ 0x5B, i, 0xC3 ^ 0xC4, blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, "".length(), 0xAF ^ 0xA0, i, "".length(), blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, 0xAF ^ 0xA8, 0x5D ^ 0x53, i, 0x8A ^ 0x8D, blockState, blockState, "".length() != 0);
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "   ".length(), "  ".length(), 0x7E ^ 0x79, 0xA8 ^ 0xAC, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "  ".length(), 0x52 ^ 0x56, 0x84 ^ 0x83, "  ".length(), DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0xC3 ^ 0xC6, 0x85 ^ 0x81, 0xB2 ^ 0xB5, 0x7C ^ 0x79, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x6C ^ 0x61, " ".length(), "   ".length(), 0x1F ^ 0x12, 0x42 ^ 0x45, 0x43 ^ 0x47, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x42 ^ 0x49, " ".length(), "  ".length(), 0x20 ^ 0x2C, 0x3B ^ 0x3C, "  ".length(), DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2A ^ 0x21, " ".length(), 0x9C ^ 0x99, 0xBC ^ 0xB0, 0x68 ^ 0x6F, 0x3F ^ 0x3A, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x26 ^ 0x23, " ".length(), "   ".length(), 0x9E ^ 0x9B, "   ".length(), 0xE ^ 0xA, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5B ^ 0x51, " ".length(), "   ".length(), 0xB8 ^ 0xB2, "   ".length(), 0x8E ^ 0x8A, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x51 ^ 0x54, 0xAA ^ 0xAD, "  ".length(), 0x17 ^ 0x1D, 0x48 ^ 0x4F, 0xA3 ^ 0xA6, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xC0 ^ 0xC5, 0x8A ^ 0x8F, "  ".length(), 0x79 ^ 0x7C, 0x2B ^ 0x2C, "  ".length(), DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x63 ^ 0x69, 0x4E ^ 0x4B, "  ".length(), 0x99 ^ 0x93, 0x93 ^ 0x94, "  ".length(), DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1A ^ 0x1F, 0x89 ^ 0x8C, 0x6A ^ 0x6F, 0x11 ^ 0x14, 0x24 ^ 0x23, 0x96 ^ 0x93, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x21 ^ 0x2B, 0x15 ^ 0x10, 0xA3 ^ 0xA6, 0x60 ^ 0x6A, 0x22 ^ 0x25, 0x4 ^ 0x1, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.setBlockState(world, DoubleXYRoom.field_175826_b, 0x37 ^ 0x31, 0x53 ^ 0x55, "  ".length(), structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175826_b, 0x6E ^ 0x67, 0x77 ^ 0x71, "  ".length(), structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175826_b, 0x30 ^ 0x36, 0x56 ^ 0x50, 0x77 ^ 0x72, structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175826_b, 0xA3 ^ 0xAA, 0xAD ^ 0xAB, 0x96 ^ 0x93, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0xBB ^ 0xBE, 0x1A ^ 0x1E, "   ".length(), 0x62 ^ 0x64, 0x17 ^ 0x13, 0xF ^ 0xB, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x78 ^ 0x71, 0x9 ^ 0xD, "   ".length(), 0x90 ^ 0x9A, 0x41 ^ 0x45, 0xA7 ^ 0xA3, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, "".length() != 0);
            this.setBlockState(world, DoubleXYRoom.field_175825_e, 0x7C ^ 0x79, 0xA2 ^ 0xA6, "  ".length(), structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175825_e, 0x3D ^ 0x38, 0xE ^ 0xA, 0x7C ^ 0x79, structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175825_e, 0x3D ^ 0x37, 0x94 ^ 0x90, "  ".length(), structureBoundingBox);
            this.setBlockState(world, DoubleXYRoom.field_175825_e, 0x7 ^ 0xD, 0x88 ^ 0x8C, 0xAF ^ 0xAA, structureBoundingBox);
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0xBE ^ 0xBA, "  ".length(), "".length(), "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0x94 ^ 0x93, 0x3 ^ 0x7, "  ".length(), 0x14 ^ 0x13, "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0xA1 ^ 0xA5, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x9A ^ 0x91, " ".length(), "".length(), 0x6E ^ 0x62, "  ".length(), "".length(), "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x24 ^ 0x2F, " ".length(), 0x8 ^ 0xF, 0x6E ^ 0x62, "  ".length(), 0x60 ^ 0x67, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x18 ^ 0x17, " ".length(), "   ".length(), 0x31 ^ 0x3E, "  ".length(), 0x8C ^ 0x88, "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), 0x8A ^ 0x8F, "".length(), 0x34 ^ 0x30, 0x9D ^ 0x9B, "".length(), "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), 0x2C ^ 0x29, 0x1C ^ 0x1B, 0x8C ^ 0x88, 0x12 ^ 0x14, 0x21 ^ 0x26, "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), 0x99 ^ 0x9C, "   ".length(), "".length(), 0x6D ^ 0x6B, 0xB ^ 0xF, "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x26 ^ 0x2D, 0x92 ^ 0x97, "".length(), 0x5F ^ 0x53, 0x40 ^ 0x46, "".length(), "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x6D ^ 0x66, 0x3C ^ 0x39, 0x19 ^ 0x1E, 0xC8 ^ 0xC4, 0xC4 ^ 0xC2, 0x15 ^ 0x12, "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x26 ^ 0x29, 0x5F ^ 0x5A, "   ".length(), 0x58 ^ 0x57, 0x7 ^ 0x1, 0x97 ^ 0x93, "".length() != 0);
            }
            return " ".length() != 0;
        }
    }
    
    public static class Penthouse extends Piece
    {
        public Penthouse(final EnumFacing enumFacing, final StructureBoundingBox structureBoundingBox) {
            super(enumFacing, structureBoundingBox);
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
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), -" ".length(), "  ".length(), 0x8 ^ 0x3, -" ".length(), 0x10 ^ 0x1B, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), -" ".length(), "".length(), " ".length(), -" ".length(), 0x93 ^ 0x98, Penthouse.field_175828_a, Penthouse.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x11 ^ 0x1D, -" ".length(), "".length(), 0x67 ^ 0x6A, -" ".length(), 0x6A ^ 0x61, Penthouse.field_175828_a, Penthouse.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), -" ".length(), "".length(), 0x5A ^ 0x51, -" ".length(), " ".length(), Penthouse.field_175828_a, Penthouse.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), -" ".length(), 0x13 ^ 0x1F, 0x69 ^ 0x62, -" ".length(), 0x99 ^ 0x94, Penthouse.field_175828_a, Penthouse.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "".length(), "".length(), "".length(), "".length(), 0x77 ^ 0x7A, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xCE ^ 0xC3, "".length(), "".length(), 0x5 ^ 0x8, "".length(), 0xC9 ^ 0xC4, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), "".length(), 0x8 ^ 0x4, "".length(), "".length(), Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "".length(), 0xB1 ^ 0xBC, 0xB6 ^ 0xBA, "".length(), 0xCF ^ 0xC2, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            int i = "  ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i <= (0x17 ^ 0x1C)) {
                this.setBlockState(world, Penthouse.field_175825_e, "".length(), "".length(), i, structureBoundingBox);
                this.setBlockState(world, Penthouse.field_175825_e, 0x5F ^ 0x52, "".length(), i, structureBoundingBox);
                this.setBlockState(world, Penthouse.field_175825_e, i, "".length(), "".length(), structureBoundingBox);
                i += 3;
            }
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), "".length(), "   ".length(), 0x34 ^ 0x30, "".length(), 0x65 ^ 0x6C, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9A ^ 0x93, "".length(), "   ".length(), 0xB5 ^ 0xBE, "".length(), 0xA4 ^ 0xAD, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x69 ^ 0x6D, "".length(), 0x9E ^ 0x97, 0xB1 ^ 0xB8, "".length(), 0x37 ^ 0x3C, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.setBlockState(world, Penthouse.field_175826_b, 0x42 ^ 0x47, "".length(), 0x3 ^ 0xB, structureBoundingBox);
            this.setBlockState(world, Penthouse.field_175826_b, 0x94 ^ 0x9C, "".length(), 0xAF ^ 0xA7, structureBoundingBox);
            this.setBlockState(world, Penthouse.field_175826_b, 0x67 ^ 0x6D, "".length(), 0x4D ^ 0x47, structureBoundingBox);
            this.setBlockState(world, Penthouse.field_175826_b, "   ".length(), "".length(), 0x8B ^ 0x81, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "".length(), "   ".length(), "   ".length(), "".length(), 0x65 ^ 0x62, Penthouse.field_175827_c, Penthouse.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB8 ^ 0xB2, "".length(), "   ".length(), 0x8E ^ 0x84, "".length(), 0x1C ^ 0x1B, Penthouse.field_175827_c, Penthouse.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x60 ^ 0x66, "".length(), 0x42 ^ 0x48, 0x8C ^ 0x8B, "".length(), 0xA1 ^ 0xAB, Penthouse.field_175827_c, Penthouse.field_175827_c, "".length() != 0);
            int length = "   ".length();
            int j = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (j < "  ".length()) {
                int k = "  ".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (k <= (0x12 ^ 0x1A)) {
                    this.fillWithBlocks(world, structureBoundingBox, length, "".length(), k, length, "  ".length(), k, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
                    k += 3;
                }
                length = (0xA4 ^ 0xAE);
                ++j;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0xC4 ^ 0xC1, "".length(), 0x16 ^ 0x1C, 0xA1 ^ 0xA4, "  ".length(), 0x7B ^ 0x71, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x9E ^ 0x96, "".length(), 0x86 ^ 0x8C, 0x85 ^ 0x8D, "  ".length(), 0xA7 ^ 0xAD, Penthouse.field_175826_b, Penthouse.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x3 ^ 0x5, -" ".length(), 0x45 ^ 0x42, 0x14 ^ 0x13, -" ".length(), 0x98 ^ 0x90, Penthouse.field_175827_c, Penthouse.field_175827_c, "".length() != 0);
            this.func_181655_a(world, structureBoundingBox, 0x58 ^ 0x5E, -" ".length(), "   ".length(), 0x74 ^ 0x73, -" ".length(), 0x64 ^ 0x60, "".length() != 0);
            this.func_175817_a(world, structureBoundingBox, 0xA5 ^ 0xA3, " ".length(), 0x31 ^ 0x37);
            return " ".length() != 0;
        }
        
        public Penthouse() {
        }
    }
    
    static class YZDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        YZDoubleRoomFitHelper(final YZDoubleRoomFitHelper yzDoubleRoomFitHelper) {
            this();
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            return new DoubleYZRoom(enumFacing, roomDefinition, random);
        }
        
        private YZDoubleRoomFitHelper() {
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (!roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] || roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d || !roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] || roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                return "".length() != 0;
            }
            final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()];
            if (roomDefinition2.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    public static class DoubleYZRoom extends Piece
    {
        public DoubleYZRoom() {
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
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            final RoomDefinition roomDefinition2 = roomDefinition.field_175965_b[EnumFacing.UP.getIndex()];
            final RoomDefinition roomDefinition3 = field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            if (this.field_175830_k.field_175967_a / (0xDF ^ 0xC6) > 0) {
                this.func_175821_a(world, structureBoundingBox, "".length(), 0x2B ^ 0x23, roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (roomDefinition3.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x2B ^ 0x23, " ".length(), 0xBC ^ 0xBA, 0xA8 ^ 0xA0, 0x72 ^ 0x75, DoubleYZRoom.field_175828_a);
            }
            if (roomDefinition2.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x46 ^ 0x4E, 0x1B ^ 0x13, 0xA2 ^ 0xA4, 0xCD ^ 0xC5, 0x14 ^ 0x1A, DoubleYZRoom.field_175828_a);
            }
            int i = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (i <= (0x77 ^ 0x70)) {
                IBlockState blockState = DoubleYZRoom.field_175826_b;
                if (i == "  ".length() || i == (0x6F ^ 0x69)) {
                    blockState = DoubleYZRoom.field_175828_a;
                }
                this.fillWithBlocks(world, structureBoundingBox, "".length(), i, "".length(), "".length(), i, 0xA6 ^ 0xA9, blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x39 ^ 0x3E, i, "".length(), 0x31 ^ 0x36, i, 0x69 ^ 0x66, blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, "".length(), 0x57 ^ 0x51, i, "".length(), blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, 0x5 ^ 0xA, 0xC6 ^ 0xC0, i, 0x35 ^ 0x3A, blockState, blockState, "".length() != 0);
                ++i;
            }
            int j = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (j <= (0x34 ^ 0x33)) {
                IBlockState blockState2 = DoubleYZRoom.field_175827_c;
                if (j == "  ".length() || j == (0x29 ^ 0x2F)) {
                    blockState2 = DoubleYZRoom.field_175825_e;
                }
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), j, 0x42 ^ 0x45, 0x8F ^ 0x8B, j, 0xBF ^ 0xB7, blockState2, blockState2, "".length() != 0);
                ++j;
            }
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0xB7 ^ 0xB3, "  ".length(), "".length(), "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xB6 ^ 0xB1, " ".length(), "   ".length(), 0x73 ^ 0x74, "  ".length(), 0x2D ^ 0x29, "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0x89 ^ 0x8D, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0xA2 ^ 0xAD, 0x7C ^ 0x78, "  ".length(), 0x3B ^ 0x34, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), 0xB9 ^ 0xB2, "".length(), "  ".length(), 0xCE ^ 0xC2, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x3B ^ 0x3C, " ".length(), 0x1B ^ 0x10, 0xBD ^ 0xBA, "  ".length(), 0xA0 ^ 0xAC, "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), 0x7C ^ 0x79, "".length(), 0x24 ^ 0x20, 0xA0 ^ 0xA6, "".length(), "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x63 ^ 0x64, 0xC6 ^ 0xC3, "   ".length(), 0x66 ^ 0x61, 0x35 ^ 0x33, 0x71 ^ 0x75, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x64 ^ 0x61, 0x2 ^ 0x6, "  ".length(), 0xC5 ^ 0xC3, 0x45 ^ 0x41, 0x3E ^ 0x3B, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x6B ^ 0x6D, " ".length(), "  ".length(), 0x6 ^ 0x0, "   ".length(), "  ".length(), DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xAB ^ 0xAD, " ".length(), 0x7D ^ 0x78, 0xA0 ^ 0xA6, "   ".length(), 0x88 ^ 0x8D, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
            }
            if (roomDefinition3.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), 0x21 ^ 0x24, "   ".length(), "".length(), 0x12 ^ 0x14, 0x58 ^ 0x5C, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x4F ^ 0x4B, "  ".length(), "  ".length(), 0x8A ^ 0x8E, 0xAF ^ 0xAA, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "  ".length(), " ".length(), "   ".length(), "  ".length(), DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x79 ^ 0x7C, " ".length(), "   ".length(), 0xA8 ^ 0xAD, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), 0x54 ^ 0x51, 0x8C ^ 0x83, 0x7 ^ 0x3, 0x6F ^ 0x69, 0x9F ^ 0x90, "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), 0x7E ^ 0x7B, 0xBF ^ 0xB4, "".length(), 0x72 ^ 0x74, 0x8B ^ 0x87, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x21 ^ 0x25, 0x5C ^ 0x56, "  ".length(), 0x74 ^ 0x70, 0xAC ^ 0xA1, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x59 ^ 0x53, " ".length(), "   ".length(), 0x99 ^ 0x93, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x69 ^ 0x64, " ".length(), "   ".length(), 0x4C ^ 0x41, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
            }
            if (roomDefinition2.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xB ^ 0xC, 0x88 ^ 0x8D, 0x9B ^ 0x90, 0x8B ^ 0x8C, 0x1F ^ 0x19, 0x78 ^ 0x74, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x19 ^ 0x1C, 0x14 ^ 0x10, 0xB7 ^ 0xBD, 0xA5 ^ 0xA3, 0x60 ^ 0x64, 0x33 ^ 0x3E, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x67 ^ 0x61, " ".length(), 0x92 ^ 0x98, 0xC ^ 0xA, "   ".length(), 0xB0 ^ 0xBA, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xAC ^ 0xAA, " ".length(), 0x24 ^ 0x29, 0x36 ^ 0x30, "   ".length(), 0x66 ^ 0x6B, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, "".length() != 0);
            }
            return " ".length() != 0;
        }
        
        public DoubleYZRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), "  ".length(), "  ".length());
        }
    }
    
    public static class WingRoom extends Piece
    {
        private int field_175834_o;
        
        public WingRoom() {
        }
        
        public WingRoom(final EnumFacing enumFacing, final StructureBoundingBox structureBoundingBox, final int n) {
            super(enumFacing, structureBoundingBox);
            this.field_175834_o = (n & " ".length());
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175834_o == 0) {
                int i = "".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                while (i < (0x32 ^ 0x36)) {
                    this.fillWithBlocks(world, structureBoundingBox, (0x43 ^ 0x49) - i, "   ".length() - i, (0x3E ^ 0x2A) - i, (0x7F ^ 0x73) + i, "   ".length() - i, 0x63 ^ 0x77, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                    ++i;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0x3B ^ 0x3C, "".length(), 0xC6 ^ 0xC0, 0xA9 ^ 0xA6, "".length(), 0x55 ^ 0x45, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x92 ^ 0x94, "".length(), 0x11 ^ 0x17, 0xC1 ^ 0xC7, "   ".length(), 0x89 ^ 0x9D, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xBD ^ 0xAD, "".length(), 0x5F ^ 0x59, 0x41 ^ 0x51, "   ".length(), 0xA8 ^ 0xBC, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x9B ^ 0x9C, " ".length(), 0x0 ^ 0x7, 0x6A ^ 0x6D, " ".length(), 0x17 ^ 0x3, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x98 ^ 0x97, " ".length(), 0x79 ^ 0x7E, 0x87 ^ 0x88, " ".length(), 0x31 ^ 0x25, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB7 ^ 0xB0, " ".length(), 0x42 ^ 0x44, 0x63 ^ 0x6A, "   ".length(), 0xC7 ^ 0xC1, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x4C ^ 0x41, " ".length(), 0xA7 ^ 0xA1, 0x83 ^ 0x8C, "   ".length(), 0x8F ^ 0x89, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x1 ^ 0x9, " ".length(), 0x6A ^ 0x6D, 0xB8 ^ 0xB1, " ".length(), 0x30 ^ 0x37, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x39 ^ 0x34, " ".length(), 0x9B ^ 0x9C, 0x2D ^ 0x23, " ".length(), 0x94 ^ 0x93, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x40 ^ 0x49, "".length(), 0x95 ^ 0x90, 0xCB ^ 0xC6, "".length(), 0x6E ^ 0x6B, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x77 ^ 0x7D, "".length(), 0x3C ^ 0x3B, 0x86 ^ 0x8A, "".length(), 0x4F ^ 0x48, WingRoom.field_175827_c, WingRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x6 ^ 0xE, "".length(), 0x5C ^ 0x56, 0x8A ^ 0x82, "".length(), 0xBC ^ 0xB0, WingRoom.field_175827_c, WingRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x54 ^ 0x5A, "".length(), 0x89 ^ 0x83, 0x97 ^ 0x99, "".length(), 0x5A ^ 0x56, WingRoom.field_175827_c, WingRoom.field_175827_c, "".length() != 0);
                int j = 0xA7 ^ 0xB5;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (j >= (0x15 ^ 0x12)) {
                    this.setBlockState(world, WingRoom.field_175825_e, 0x1A ^ 0x1C, "   ".length(), j, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, 0x1C ^ 0xC, "   ".length(), j, structureBoundingBox);
                    j -= 3;
                }
                this.setBlockState(world, WingRoom.field_175825_e, 0x4A ^ 0x40, "".length(), 0x55 ^ 0x5F, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0xB6 ^ 0xBA, "".length(), 0x44 ^ 0x4E, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x25 ^ 0x2F, "".length(), 0xB3 ^ 0xBF, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x8D ^ 0x81, "".length(), 0x8A ^ 0x86, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0xAB ^ 0xA3, "   ".length(), 0x59 ^ 0x5F, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x1C ^ 0x12, "   ".length(), 0x1C ^ 0x1A, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x62 ^ 0x66, "  ".length(), 0x79 ^ 0x7D, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x20 ^ 0x24, " ".length(), 0xA0 ^ 0xA4, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x1A ^ 0x1E, "".length(), 0xC ^ 0x8, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x1C ^ 0xE, "  ".length(), 0x3D ^ 0x39, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x6A ^ 0x78, " ".length(), 0xC ^ 0x8, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0xAC ^ 0xBE, "".length(), 0x2A ^ 0x2E, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x12 ^ 0x16, "  ".length(), 0x7B ^ 0x69, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0xC3 ^ 0xC7, " ".length(), 0xD3 ^ 0xC1, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x67 ^ 0x63, "".length(), 0x21 ^ 0x33, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x5 ^ 0x17, "  ".length(), 0x1A ^ 0x8, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175825_e, 0x8 ^ 0x1A, " ".length(), 0xB3 ^ 0xA1, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0xC ^ 0x1E, "".length(), 0xD3 ^ 0xC1, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0x66 ^ 0x6F, 0x3B ^ 0x3C, 0x2E ^ 0x3A, structureBoundingBox);
                this.setBlockState(world, WingRoom.field_175826_b, 0xA5 ^ 0xA8, 0x2A ^ 0x2D, 0x18 ^ 0xC, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0xAE ^ 0xA8, "".length(), 0x3 ^ 0x16, 0x38 ^ 0x3F, 0x76 ^ 0x72, 0x38 ^ 0x2D, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB ^ 0x4, "".length(), 0x3F ^ 0x2A, 0x5D ^ 0x4D, 0xBE ^ 0xBA, 0xB9 ^ 0xAC, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.func_175817_a(world, structureBoundingBox, 0x64 ^ 0x6F, "  ".length(), 0x67 ^ 0x77);
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else if (this.field_175834_o == " ".length()) {
                this.fillWithBlocks(world, structureBoundingBox, 0x1F ^ 0x16, "   ".length(), 0x12 ^ 0x0, 0x8A ^ 0x87, "   ".length(), 0x8F ^ 0x9B, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x74 ^ 0x7D, "".length(), 0x98 ^ 0x8A, 0x77 ^ 0x7E, "  ".length(), 0x7F ^ 0x6D, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x3B ^ 0x36, "".length(), 0x25 ^ 0x37, 0x47 ^ 0x4A, "  ".length(), 0xAC ^ 0xBE, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                int n = 0x92 ^ 0x9B;
                final int n2 = 0xD5 ^ 0xC1;
                final int n3 = 0x30 ^ 0x35;
                int k = "".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                while (k < "  ".length()) {
                    this.setBlockState(world, WingRoom.field_175826_b, n, n3 + " ".length(), n2, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, n, n3, n2, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175826_b, n, n3 - " ".length(), n2, structureBoundingBox);
                    n = (0xF ^ 0x2);
                    ++k;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0x86 ^ 0x81, "   ".length(), 0x0 ^ 0x7, 0xA9 ^ 0xA6, "   ".length(), 0xBE ^ 0xB0, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                int n4 = 0x95 ^ 0x9F;
                int l = "".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
                while (l < "  ".length()) {
                    this.fillWithBlocks(world, structureBoundingBox, n4, "".length(), 0x91 ^ 0x9B, n4, 0x39 ^ 0x3F, 0xB7 ^ 0xBD, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n4, "".length(), 0x88 ^ 0x84, n4, 0x96 ^ 0x90, 0x73 ^ 0x7F, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                    this.setBlockState(world, WingRoom.field_175825_e, n4, "".length(), 0x8F ^ 0x85, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, n4, "".length(), 0x15 ^ 0x19, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, n4, 0x51 ^ 0x55, 0x9D ^ 0x97, structureBoundingBox);
                    this.setBlockState(world, WingRoom.field_175825_e, n4, 0x94 ^ 0x90, 0x41 ^ 0x4D, structureBoundingBox);
                    n4 = (0x4F ^ 0x43);
                    ++l;
                }
                int n5 = 0x59 ^ 0x51;
                int length = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (length < "  ".length()) {
                    this.fillWithBlocks(world, structureBoundingBox, n5, "".length(), 0xB2 ^ 0xB5, n5, "  ".length(), 0x98 ^ 0x9F, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n5, "".length(), 0x19 ^ 0x17, n5, "  ".length(), 0x1A ^ 0x14, WingRoom.field_175826_b, WingRoom.field_175826_b, "".length() != 0);
                    n5 = (0x68 ^ 0x66);
                    ++length;
                }
                this.fillWithBlocks(world, structureBoundingBox, 0xCE ^ 0xC6, "   ".length(), 0x84 ^ 0x8C, 0x64 ^ 0x6C, "   ".length(), 0x3A ^ 0x37, WingRoom.field_175827_c, WingRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x8B ^ 0x85, "   ".length(), 0x58 ^ 0x50, 0x99 ^ 0x97, "   ".length(), 0x19 ^ 0x14, WingRoom.field_175827_c, WingRoom.field_175827_c, "".length() != 0);
                this.func_175817_a(world, structureBoundingBox, 0xA9 ^ 0xA2, 0x7C ^ 0x79, 0x4F ^ 0x42);
            }
            return " ".length() != 0;
        }
    }
    
    static class YDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        private YDoubleRoomFitHelper() {
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = (" ".length() != 0);
            return new DoubleYRoom(enumFacing, roomDefinition, random);
        }
        
        YDoubleRoomFitHelper(final YDoubleRoomFitHelper yDoubleRoomFitHelper) {
            this();
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
                return " ".length() != 0;
            }
            return "".length() != 0;
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
    }
    
    public static class DoubleYRoom extends Piece
    {
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / (0xA7 ^ 0xBE) > 0) {
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x28 ^ 0x20, " ".length(), 0xB3 ^ 0xB5, 0xB4 ^ 0xBC, 0x56 ^ 0x50, DoubleYRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), 0x6D ^ 0x69, "".length(), "".length(), 0x36 ^ 0x32, 0x6B ^ 0x6C, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x15 ^ 0x12, 0x8D ^ 0x89, "".length(), 0xBF ^ 0xB8, 0xB4 ^ 0xB0, 0x3B ^ 0x3C, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x74 ^ 0x70, "".length(), 0x8A ^ 0x8C, 0x33 ^ 0x37, "".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0xA6 ^ 0xA2, 0x47 ^ 0x40, 0xB7 ^ 0xB1, 0x2E ^ 0x2A, 0x8D ^ 0x8A, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0xAC ^ 0xA8, " ".length(), "  ".length(), 0x23 ^ 0x27, "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x6E ^ 0x6A, "  ".length(), " ".length(), 0xB1 ^ 0xB5, "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x87 ^ 0x82, 0x8A ^ 0x8E, " ".length(), 0x59 ^ 0x5C, 0x46 ^ 0x42, "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x88 ^ 0x8E, 0x2B ^ 0x2F, "  ".length(), 0x59 ^ 0x5F, 0xC5 ^ 0xC1, "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), 0xAC ^ 0xA8, 0xAA ^ 0xAF, "  ".length(), 0x92 ^ 0x96, 0x7D ^ 0x7B, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x11 ^ 0x15, 0x64 ^ 0x61, " ".length(), 0xA5 ^ 0xA1, 0x6 ^ 0x3, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x16 ^ 0x13, 0x4B ^ 0x4F, 0xBB ^ 0xBE, 0x9C ^ 0x99, 0x8A ^ 0x8E, 0xAB ^ 0xAD, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB ^ 0xD, 0x97 ^ 0x93, 0x13 ^ 0x16, 0x64 ^ 0x62, 0x35 ^ 0x31, 0xBD ^ 0xB8, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
            RoomDefinition field_175830_k = this.field_175830_k;
            int i = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (i <= (0xA2 ^ 0xA7)) {
                final int length = "".length();
                if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "  ".length(), i, length, "  ".length(), i + "  ".length(), length, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0xB ^ 0xE, i, length, 0xBE ^ 0xBB, i + "  ".length(), length, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), i + "  ".length(), length, 0x7E ^ 0x7A, i + "  ".length(), length, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), i, length, 0x74 ^ 0x73, i + "  ".length(), length, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), i + " ".length(), length, 0xF ^ 0x8, i + " ".length(), length, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, "".length() != 0);
                }
                final int n = 0xA1 ^ 0xA6;
                if (field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "  ".length(), i, n, "  ".length(), i + "  ".length(), n, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x22 ^ 0x27, i, n, 0x2D ^ 0x28, i + "  ".length(), n, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), i + "  ".length(), n, 0xB7 ^ 0xB3, i + "  ".length(), n, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), i, n, 0x3F ^ 0x38, i + "  ".length(), n, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), i + " ".length(), n, 0x8F ^ 0x88, i + " ".length(), n, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, "".length() != 0);
                }
                final int length2 = "".length();
                if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, length2, i, "  ".length(), length2, i + "  ".length(), "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, length2, i, 0x33 ^ 0x36, length2, i + "  ".length(), 0xB ^ 0xE, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, length2, i + "  ".length(), "   ".length(), length2, i + "  ".length(), 0x87 ^ 0x83, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, length2, i, "".length(), length2, i + "  ".length(), 0x2D ^ 0x2A, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, length2, i + " ".length(), "".length(), length2, i + " ".length(), 0xBE ^ 0xB9, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, "".length() != 0);
                }
                final int n2 = 0x68 ^ 0x6F;
                if (field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, n2, i, "  ".length(), n2, i + "  ".length(), "  ".length(), DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n2, i, 0x5D ^ 0x58, n2, i + "  ".length(), 0x21 ^ 0x24, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n2, i + "  ".length(), "   ".length(), n2, i + "  ".length(), 0x47 ^ 0x43, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, n2, i, "".length(), n2, i + "  ".length(), 0x8B ^ 0x8C, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, n2, i + " ".length(), "".length(), n2, i + " ".length(), 0x42 ^ 0x45, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, "".length() != 0);
                }
                field_175830_k = roomDefinition;
                i += 4;
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public DoubleYRoom() {
        }
        
        public DoubleYRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), "  ".length(), " ".length());
        }
    }
    
    public static class MonumentCoreRoom extends Piece
    {
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
                if (3 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public MonumentCoreRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, "  ".length(), "  ".length(), "  ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.func_175819_a(world, structureBoundingBox, " ".length(), 0x6C ^ 0x64, "".length(), 0xB ^ 0x5, 0x37 ^ 0x3F, 0x89 ^ 0x87, MonumentCoreRoom.field_175828_a);
            final int n = 0x30 ^ 0x37;
            final IBlockState field_175826_b = MonumentCoreRoom.field_175826_b;
            this.fillWithBlocks(world, structureBoundingBox, "".length(), n, "".length(), "".length(), n, 0xA8 ^ 0xA7, field_175826_b, field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x68 ^ 0x67, n, "".length(), 0x78 ^ 0x77, n, 0x83 ^ 0x8C, field_175826_b, field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), n, "".length(), 0x80 ^ 0x8F, n, "".length(), field_175826_b, field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), n, 0x88 ^ 0x87, 0xB1 ^ 0xBF, n, 0x9D ^ 0x92, field_175826_b, field_175826_b, "".length() != 0);
            int i = " ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (i <= (0x56 ^ 0x50)) {
                IBlockState blockState = MonumentCoreRoom.field_175826_b;
                if (i == "  ".length() || i == (0x77 ^ 0x71)) {
                    blockState = MonumentCoreRoom.field_175828_a;
                }
                int j = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
                while (j <= (0x6F ^ 0x60)) {
                    this.fillWithBlocks(world, structureBoundingBox, j, i, "".length(), j, i, " ".length(), blockState, blockState, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, j, i, 0xC0 ^ 0xC6, j, i, 0x68 ^ 0x61, blockState, blockState, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, j, i, 0x91 ^ 0x9F, j, i, 0xA8 ^ 0xA7, blockState, blockState, "".length() != 0);
                    j += 15;
                }
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, "".length(), " ".length(), i, "".length(), blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x92 ^ 0x94, i, "".length(), 0xA ^ 0x3, i, "".length(), blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x3 ^ 0xD, i, "".length(), 0x66 ^ 0x68, i, "".length(), blockState, blockState, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), i, 0xAB ^ 0xA4, 0x2C ^ 0x22, i, 0xA5 ^ 0xAA, blockState, blockState, "".length() != 0);
                ++i;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x26 ^ 0x20, "   ".length(), 0xA2 ^ 0xA4, 0xA2 ^ 0xAB, 0x1 ^ 0x7, 0x1F ^ 0x16, MonumentCoreRoom.field_175827_c, MonumentCoreRoom.field_175827_c, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x94 ^ 0x93, 0x8E ^ 0x8A, 0xA ^ 0xD, 0xAB ^ 0xA3, 0xA5 ^ 0xA0, 0x24 ^ 0x2C, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), "".length() != 0);
            int k = "   ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (k <= (0x96 ^ 0x90)) {
                int l = 0x40 ^ 0x46;
                "".length();
                if (3 == 0) {
                    throw null;
                }
                while (l <= (0x58 ^ 0x51)) {
                    this.setBlockState(world, MonumentCoreRoom.field_175825_e, l, k, 0x49 ^ 0x4F, structureBoundingBox);
                    this.setBlockState(world, MonumentCoreRoom.field_175825_e, l, k, 0x5A ^ 0x53, structureBoundingBox);
                    l += 3;
                }
                k += 3;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0x91 ^ 0x94, " ".length(), 0x9B ^ 0x9D, 0x61 ^ 0x64, "  ".length(), 0x55 ^ 0x53, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5 ^ 0x0, " ".length(), 0x42 ^ 0x4B, 0x74 ^ 0x71, "  ".length(), 0x6C ^ 0x65, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xCA ^ 0xC0, " ".length(), 0x47 ^ 0x41, 0xBC ^ 0xB6, "  ".length(), 0x99 ^ 0x9F, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2E ^ 0x24, " ".length(), 0x58 ^ 0x51, 0x48 ^ 0x42, "  ".length(), 0xA6 ^ 0xAF, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x74 ^ 0x72, " ".length(), 0x3A ^ 0x3F, 0x4D ^ 0x4B, "  ".length(), 0xAF ^ 0xAA, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5B ^ 0x52, " ".length(), 0x3F ^ 0x3A, 0x8D ^ 0x84, "  ".length(), 0x9B ^ 0x9E, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x7E ^ 0x78, " ".length(), 0xB3 ^ 0xB9, 0x75 ^ 0x73, "  ".length(), 0x4B ^ 0x41, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB9 ^ 0xB0, " ".length(), 0xB5 ^ 0xBF, 0xA9 ^ 0xA0, "  ".length(), 0x68 ^ 0x62, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x31 ^ 0x34, "  ".length(), 0x39 ^ 0x3C, 0xBC ^ 0xB9, 0x8C ^ 0x8A, 0xBC ^ 0xB9, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB7 ^ 0xB2, "  ".length(), 0xA8 ^ 0xA2, 0xA3 ^ 0xA6, 0x9E ^ 0x98, 0x83 ^ 0x89, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x78 ^ 0x72, "  ".length(), 0x11 ^ 0x14, 0x2 ^ 0x8, 0xA9 ^ 0xAF, 0x94 ^ 0x91, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x72 ^ 0x78, "  ".length(), 0x9A ^ 0x90, 0xBA ^ 0xB0, 0x28 ^ 0x2E, 0x18 ^ 0x12, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2F ^ 0x2A, 0xC2 ^ 0xC5, " ".length(), 0x58 ^ 0x5D, 0x5A ^ 0x5D, 0xBD ^ 0xBB, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xA6 ^ 0xAC, 0xA ^ 0xD, " ".length(), 0x54 ^ 0x5E, 0x57 ^ 0x50, 0x8A ^ 0x8C, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x3D ^ 0x38, 0x98 ^ 0x9F, 0x8B ^ 0x82, 0x61 ^ 0x64, 0x94 ^ 0x93, 0x51 ^ 0x5F, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x8 ^ 0x2, 0x98 ^ 0x9F, 0xCC ^ 0xC5, 0x1A ^ 0x10, 0xB0 ^ 0xB7, 0x53 ^ 0x5D, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x32 ^ 0x35, 0x98 ^ 0x9D, 0x42 ^ 0x44, 0xB6 ^ 0xB1, 0xA9 ^ 0xAC, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), 0x79 ^ 0x7E, 0xCD ^ 0xC7, 0xBC ^ 0xBA, 0x76 ^ 0x71, 0x8B ^ 0x81, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x4D ^ 0x44, 0x86 ^ 0x81, 0xC6 ^ 0xC3, 0x39 ^ 0x37, 0x7 ^ 0x0, 0x2B ^ 0x2E, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x90 ^ 0x99, 0x1F ^ 0x18, 0x5B ^ 0x51, 0x49 ^ 0x47, 0xC1 ^ 0xC6, 0x3E ^ 0x34, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "  ".length(), "  ".length(), " ".length(), "   ".length(), MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "  ".length(), "   ".length(), " ".length(), "  ".length(), MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x86 ^ 0x8B, " ".length(), "  ".length(), 0x1A ^ 0x17, " ".length(), "   ".length(), MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2C ^ 0x20, " ".length(), "  ".length(), 0x63 ^ 0x6F, " ".length(), "  ".length(), MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x9 ^ 0x5, "  ".length(), " ".length(), 0xCB ^ 0xC6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x7A ^ 0x77, "   ".length(), " ".length(), 0xCA ^ 0xC7, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x97 ^ 0x9A, " ".length(), 0x2E ^ 0x22, 0xB6 ^ 0xBB, " ".length(), 0x20 ^ 0x2D, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x0 ^ 0xC, " ".length(), 0x6F ^ 0x62, 0x72 ^ 0x7E, " ".length(), 0xCB ^ 0xC6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, "".length() != 0);
            return " ".length() != 0;
        }
        
        public MonumentCoreRoom() {
        }
    }
    
    static class FitSimpleRoomHelper implements MonumentRoomFitHelper
    {
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            return " ".length() != 0;
        }
        
        private FitSimpleRoomHelper() {
        }
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            return new SimpleRoom(enumFacing, roomDefinition, random);
        }
        
        FitSimpleRoomHelper(final FitSimpleRoomHelper fitSimpleRoomHelper) {
            this();
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
                if (4 <= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public static class SimpleRoom extends Piece
    {
        private int field_175833_o;
        
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
                if (-1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public SimpleRoom() {
        }
        
        public SimpleRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), " ".length(), " ".length());
            this.field_175833_o = random.nextInt("   ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.field_175830_k.field_175967_a / (0xB8 ^ 0xA1) > 0) {
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x86 ^ 0x82, " ".length(), 0xB2 ^ 0xB4, 0x20 ^ 0x24, 0x8F ^ 0x89, SimpleRoom.field_175828_a);
            }
            int n;
            if (this.field_175833_o != 0 && random.nextBoolean() && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()] && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()] && this.field_175830_k.func_175960_c() > " ".length()) {
                n = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            if (this.field_175833_o == 0) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "  ".length(), " ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "  ".length(), "   ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), "  ".length(), "  ".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), "  ".length(), "  ".length(), "".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175825_e, " ".length(), "  ".length(), " ".length(), structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0x7F ^ 0x7A, " ".length(), "".length(), 0xBF ^ 0xB8, " ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x0 ^ 0x5, "   ".length(), "".length(), 0x52 ^ 0x55, "   ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xB4, "  ".length(), "".length(), 0x46 ^ 0x41, "  ".length(), "  ".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x28 ^ 0x2D, "  ".length(), "".length(), 0x51 ^ 0x57, "  ".length(), "".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175825_e, 0x60 ^ 0x66, "  ".length(), " ".length(), structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x75 ^ 0x70, "  ".length(), " ".length(), 0x9E ^ 0x99, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), 0x3B ^ 0x3E, "  ".length(), "   ".length(), 0x58 ^ 0x5F, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), 0x1B ^ 0x1E, "".length(), "  ".length(), 0x4A ^ 0x4D, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0xBB ^ 0xBC, "  ".length(), "  ".length(), 0x7C ^ 0x7B, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175825_e, " ".length(), "  ".length(), 0xAB ^ 0xAD, structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, 0x4E ^ 0x4B, " ".length(), 0xC6 ^ 0xC3, 0x1C ^ 0x1B, " ".length(), 0x9B ^ 0x9C, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x28 ^ 0x2D, "   ".length(), 0xAF ^ 0xAA, 0x7E ^ 0x79, "   ".length(), 0x22 ^ 0x25, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x7A ^ 0x7D, "  ".length(), 0xD ^ 0x8, 0x57 ^ 0x50, "  ".length(), 0x4B ^ 0x4C, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x24 ^ 0x21, "  ".length(), 0xBC ^ 0xBB, 0x24 ^ 0x22, "  ".length(), 0x99 ^ 0x9E, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175825_e, 0x32 ^ 0x34, "  ".length(), 0x2A ^ 0x2C, structureBoundingBox);
                if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), "".length(), 0x78 ^ 0x7C, "   ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), "".length(), 0x87 ^ 0x83, "   ".length(), " ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), "".length(), 0xBB ^ 0xBF, "  ".length(), "".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x4F ^ 0x4B, " ".length(), " ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), 0x53 ^ 0x54, 0x76 ^ 0x72, "   ".length(), 0x0 ^ 0x7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), 0x5D ^ 0x5B, 0xB7 ^ 0xB3, "   ".length(), 0x6F ^ 0x68, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), 0x47 ^ 0x40, 0x6 ^ 0x2, "  ".length(), 0x6A ^ 0x6D, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x3B ^ 0x3D, 0xC4 ^ 0xC0, " ".length(), 0x5F ^ 0x58, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "   ".length(), "".length(), "   ".length(), 0x68 ^ 0x6C, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "   ".length(), " ".length(), "   ".length(), 0x37 ^ 0x33, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "   ".length(), "".length(), "  ".length(), 0x5A ^ 0x5E, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), " ".length(), " ".length(), 0x6D ^ 0x69, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 0x6B ^ 0x6C, "   ".length(), "   ".length(), 0xB4 ^ 0xB3, "   ".length(), 0x5C ^ 0x58, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 0x36 ^ 0x30, "   ".length(), "   ".length(), 0x15 ^ 0x12, "   ".length(), 0xA ^ 0xE, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x1B ^ 0x1C, "  ".length(), "   ".length(), 0x3E ^ 0x39, "  ".length(), 0x2A ^ 0x2E, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x3 ^ 0x5, " ".length(), "   ".length(), 0xA1 ^ 0xA6, " ".length(), 0x7 ^ 0x3, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
            }
            else if (this.field_175833_o == " ".length()) {
                this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), "  ".length(), "  ".length(), "   ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "  ".length(), " ".length(), 0x75 ^ 0x70, "  ".length(), "   ".length(), 0xC0 ^ 0xC5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x8C ^ 0x89, " ".length(), 0x4C ^ 0x49, 0x2A ^ 0x2F, "   ".length(), 0x7 ^ 0x2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x94 ^ 0x91, " ".length(), "  ".length(), 0xA9 ^ 0xAC, "   ".length(), "  ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175825_e, "  ".length(), "  ".length(), "  ".length(), structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, "  ".length(), "  ".length(), 0xAE ^ 0xAB, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, 0x72 ^ 0x77, "  ".length(), 0x9 ^ 0xC, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175825_e, 0x2 ^ 0x7, "  ".length(), "  ".length(), structureBoundingBox);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), " ".length(), "   ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), "   ".length(), " ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x33 ^ 0x34, " ".length(), "   ".length(), 0x2F ^ 0x28, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0x27 ^ 0x21, "".length(), "   ".length(), 0x70 ^ 0x76, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xA2 ^ 0xA4, " ".length(), 0x8B ^ 0x8C, 0x4F ^ 0x48, "   ".length(), 0x5C ^ 0x5B, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x98 ^ 0x9F, " ".length(), 0x64 ^ 0x62, 0x46 ^ 0x41, "   ".length(), 0x48 ^ 0x4E, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x65 ^ 0x63, " ".length(), "".length(), 0x91 ^ 0x96, "   ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x7E ^ 0x79, " ".length(), " ".length(), 0xBE ^ 0xB9, "   ".length(), " ".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.setBlockState(world, SimpleRoom.field_175828_a, " ".length(), "  ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, "".length(), "  ".length(), " ".length(), structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, " ".length(), "  ".length(), 0x65 ^ 0x62, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, "".length(), "  ".length(), 0x1E ^ 0x18, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0xC1 ^ 0xC7, "  ".length(), 0x3A ^ 0x3D, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0x3 ^ 0x4, "  ".length(), 0x44 ^ 0x42, structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0x73 ^ 0x75, "  ".length(), "".length(), structureBoundingBox);
                this.setBlockState(world, SimpleRoom.field_175828_a, 0x5A ^ 0x5D, "  ".length(), " ".length(), structureBoundingBox);
                if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), 0xA4 ^ 0xA2, "   ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0x7A ^ 0x7C, "  ".length(), "".length(), SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), 0x84 ^ 0x82, " ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0x4A ^ 0x4D, 0x63 ^ 0x65, "   ".length(), 0x21 ^ 0x26, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0xBB ^ 0xBC, 0x88 ^ 0x8E, "  ".length(), 0x9A ^ 0x9D, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x6E ^ 0x69, 0x35 ^ 0x33, " ".length(), 0x3E ^ 0x39, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), " ".length(), "".length(), "   ".length(), 0x10 ^ 0x16, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), " ".length(), "".length(), "  ".length(), 0x4C ^ 0x4A, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), " ".length(), "".length(), " ".length(), 0x1E ^ 0x18, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                }
                if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.fillWithBlocks(world, structureBoundingBox, 0x42 ^ 0x45, "   ".length(), " ".length(), 0xB3 ^ 0xB4, "   ".length(), 0x5A ^ 0x5C, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0xB3 ^ 0xB4, "  ".length(), " ".length(), 0x20 ^ 0x27, "  ".length(), 0xA1 ^ 0xA7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                    this.fillWithBlocks(world, structureBoundingBox, 0x96 ^ 0x91, " ".length(), " ".length(), 0x42 ^ 0x45, " ".length(), 0x43 ^ 0x45, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
            }
            else if (this.field_175833_o == "  ".length()) {
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0x86 ^ 0x81, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xA ^ 0xD, " ".length(), "".length(), 0xC4 ^ 0xC3, " ".length(), 0x28 ^ 0x2F, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), 0xAA ^ 0xAC, " ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x58 ^ 0x5F, 0xA7 ^ 0xA1, " ".length(), 0x2D ^ 0x2A, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), "  ".length(), 0xD ^ 0xA, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0xC3 ^ 0xC4, "  ".length(), "".length(), 0xA0 ^ 0xA7, "  ".length(), 0x35 ^ 0x32, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0xA ^ 0xC, "  ".length(), "".length(), SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x65 ^ 0x62, 0x9B ^ 0x9D, "  ".length(), 0x3B ^ 0x3C, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "".length(), "   ".length(), 0xBC ^ 0xBB, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x71 ^ 0x76, "   ".length(), "".length(), 0x76 ^ 0x71, "   ".length(), 0xC2 ^ 0xC5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), 0xA3 ^ 0xA5, "   ".length(), "".length(), SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0xB5 ^ 0xB2, 0x7F ^ 0x79, "   ".length(), 0x85 ^ 0x82, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0x2B ^ 0x2F, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, 0x71 ^ 0x76, " ".length(), "   ".length(), 0x12 ^ 0x15, "  ".length(), 0x5A ^ 0x5E, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x2E ^ 0x2A, "  ".length(), "".length(), SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), 0x3D ^ 0x3A, 0x3F ^ 0x3B, "  ".length(), 0x3E ^ 0x39, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, "".length() != 0);
                if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0x8C ^ 0x88, "  ".length(), "".length(), "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0x89 ^ 0x8E, 0xB2 ^ 0xB6, "  ".length(), 0x8C ^ 0x8B, "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0x71 ^ 0x75, "".length() != 0);
                }
                if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                    this.func_181655_a(world, structureBoundingBox, 0x30 ^ 0x37, " ".length(), "   ".length(), 0x1E ^ 0x19, "  ".length(), 0x31 ^ 0x35, "".length() != 0);
                }
            }
            if (n2 != 0) {
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), " ".length(), "   ".length(), 0x3E ^ 0x3A, " ".length(), 0x3A ^ 0x3E, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "  ".length(), "   ".length(), 0xB0 ^ 0xB4, "  ".length(), 0x9B ^ 0x9F, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, "".length() != 0);
                this.fillWithBlocks(world, structureBoundingBox, "   ".length(), "   ".length(), "   ".length(), 0xB8 ^ 0xBC, "   ".length(), 0x29 ^ 0x2D, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, "".length() != 0);
            }
            return " ".length() != 0;
        }
    }
    
    static class XDoubleRoomFitHelper implements MonumentRoomFitHelper
    {
        private XDoubleRoomFitHelper() {
        }
        
        XDoubleRoomFitHelper(final XDoubleRoomFitHelper xDoubleRoomFitHelper) {
            this();
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
        
        @Override
        public Piece func_175968_a(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            roomDefinition.field_175963_d = (" ".length() != 0);
            roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = (" ".length() != 0);
            return new DoubleXRoom(enumFacing, roomDefinition, random);
        }
        
        @Override
        public boolean func_175969_a(final RoomDefinition roomDefinition) {
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    public static class DoubleXRoom extends Piece
    {
        public DoubleXRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition, final Random random) {
            super(" ".length(), enumFacing, roomDefinition, "  ".length(), " ".length(), " ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            final RoomDefinition roomDefinition = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
            final RoomDefinition field_175830_k = this.field_175830_k;
            if (this.field_175830_k.field_175967_a / (0x1F ^ 0x6) > 0) {
                this.func_175821_a(world, structureBoundingBox, 0xA2 ^ 0xAA, "".length(), roomDefinition.field_175966_c[EnumFacing.DOWN.getIndex()]);
                this.func_175821_a(world, structureBoundingBox, "".length(), "".length(), field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
            }
            if (field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, " ".length(), 0x56 ^ 0x52, " ".length(), 0x6D ^ 0x6A, 0xBB ^ 0xBF, 0x26 ^ 0x20, DoubleXRoom.field_175828_a);
            }
            if (roomDefinition.field_175965_b[EnumFacing.UP.getIndex()] == null) {
                this.func_175819_a(world, structureBoundingBox, 0x36 ^ 0x3E, 0x26 ^ 0x22, " ".length(), 0x9B ^ 0x95, 0x14 ^ 0x10, 0x30 ^ 0x36, DoubleXRoom.field_175828_a);
            }
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "".length(), "   ".length(), 0x8E ^ 0x89, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xBE ^ 0xB1, "   ".length(), "".length(), 0xB1 ^ 0xBE, "   ".length(), 0x19 ^ 0x1E, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), "".length(), 0x8 ^ 0x7, "   ".length(), "".length(), DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "   ".length(), 0xA1 ^ 0xA6, 0x53 ^ 0x5D, "   ".length(), 0x20 ^ 0x27, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), "".length(), "  ".length(), 0xC ^ 0xB, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x5D ^ 0x52, "  ".length(), "".length(), 0x88 ^ 0x87, "  ".length(), 0xBB ^ 0xBC, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), "".length(), 0x8A ^ 0x85, "  ".length(), "".length(), DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), "  ".length(), 0x8 ^ 0xF, 0x3F ^ 0x31, "  ".length(), 0xC7 ^ 0xC0, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0x7C ^ 0x7B, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAE ^ 0xA1, " ".length(), "".length(), 0x69 ^ 0x66, " ".length(), 0x72 ^ 0x75, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), 0x12 ^ 0x1D, " ".length(), "".length(), DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), 0x7F ^ 0x78, 0x99 ^ 0x97, " ".length(), 0x1F ^ 0x18, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xAA ^ 0xAF, " ".length(), "".length(), 0x16 ^ 0x1C, " ".length(), 0x17 ^ 0x13, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1F ^ 0x19, "  ".length(), "".length(), 0xA ^ 0x3, "  ".length(), "   ".length(), DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x85 ^ 0x80, "   ".length(), "".length(), 0x6B ^ 0x61, "   ".length(), 0x12 ^ 0x16, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, "".length() != 0);
            this.setBlockState(world, DoubleXRoom.field_175825_e, 0x6 ^ 0x0, "  ".length(), "   ".length(), structureBoundingBox);
            this.setBlockState(world, DoubleXRoom.field_175825_e, 0x99 ^ 0x90, "  ".length(), "   ".length(), structureBoundingBox);
            if (field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), "".length(), 0xA3 ^ 0xA7, "  ".length(), "".length(), "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0x39 ^ 0x3E, 0x67 ^ 0x63, "  ".length(), 0xC3 ^ 0xC4, "".length() != 0);
            }
            if (field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), "".length(), "  ".length(), 0xC1 ^ 0xC5, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xA0 ^ 0xAB, " ".length(), "".length(), 0xAC ^ 0xA0, "  ".length(), "".length(), "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xAC ^ 0xA7, " ".length(), 0x1B ^ 0x1C, 0x25 ^ 0x29, "  ".length(), 0x92 ^ 0x95, "".length() != 0);
            }
            if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0xBE ^ 0xB1, " ".length(), "   ".length(), 0x1B ^ 0x14, "  ".length(), 0xA7 ^ 0xA3, "".length() != 0);
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public DoubleXRoom() {
        }
    }
    
    public static class EntryRoom extends Piece
    {
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
                if (4 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public EntryRoom() {
        }
        
        public EntryRoom(final EnumFacing enumFacing, final RoomDefinition roomDefinition) {
            super(" ".length(), enumFacing, roomDefinition, " ".length(), " ".length(), " ".length());
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "   ".length(), "".length(), "  ".length(), "   ".length(), 0x8D ^ 0x8A, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0xB4 ^ 0xB1, "   ".length(), "".length(), 0xAC ^ 0xAB, "   ".length(), 0x60 ^ 0x67, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), "  ".length(), "".length(), " ".length(), "  ".length(), 0x9E ^ 0x99, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x2E ^ 0x28, "  ".length(), "".length(), 0x98 ^ 0x9F, "  ".length(), 0x47 ^ 0x40, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), "".length(), "".length(), " ".length(), 0xB4 ^ 0xB3, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x1F ^ 0x18, " ".length(), "".length(), 0x30 ^ 0x37, " ".length(), 0x45 ^ 0x42, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, "".length(), " ".length(), 0xB3 ^ 0xB4, 0x2A ^ 0x2D, "   ".length(), 0x5C ^ 0x5B, EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, " ".length(), " ".length(), "".length(), "  ".length(), "   ".length(), "".length(), EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            this.fillWithBlocks(world, structureBoundingBox, 0x67 ^ 0x62, " ".length(), "".length(), 0x15 ^ 0x13, "   ".length(), "".length(), EntryRoom.field_175826_b, EntryRoom.field_175826_b, "".length() != 0);
            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "   ".length(), " ".length(), 0x4F ^ 0x48, 0x1E ^ 0x1A, "  ".length(), 0x1B ^ 0x1C, "".length() != 0);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, "".length(), " ".length(), "   ".length(), " ".length(), "  ".length(), 0x27 ^ 0x23, "".length() != 0);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_181655_a(world, structureBoundingBox, 0x63 ^ 0x65, " ".length(), "   ".length(), 0x23 ^ 0x24, "  ".length(), 0x1F ^ 0x1B, "".length() != 0);
            }
            return " ".length() != 0;
        }
    }
}
