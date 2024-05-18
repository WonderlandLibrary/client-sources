package net.minecraft.client.renderer;

import net.minecraft.util.*;

public enum EnumFaceDirection
{
    private static final EnumFaceDirection[] ENUM$VALUES;
    
    SOUTH(s4, length4, array4), 
    DOWN(s, length, array);
    
    private static final EnumFaceDirection[] facings;
    
    UP(s2, length2, array2), 
    EAST(s6, n2, array6), 
    NORTH(s3, length3, array3);
    
    private final VertexInformation[] vertexInfos;
    private static final String[] I;
    
    WEST(s5, n, array5);
    
    static {
        I();
        final String s = EnumFaceDirection.I["".length()];
        final int length = "".length();
        final VertexInformation[] array = new VertexInformation[0x9A ^ 0x9E];
        array["".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        array[" ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array["  ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array["   ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        final String s2 = EnumFaceDirection.I[" ".length()];
        final int length2 = " ".length();
        final VertexInformation[] array2 = new VertexInformation[0x88 ^ 0x8C];
        array2["".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        array2[" ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        array2["  ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        array2["   ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        final String s3 = EnumFaceDirection.I["  ".length()];
        final int length3 = "  ".length();
        final VertexInformation[] array3 = new VertexInformation[0xA1 ^ 0xA5];
        array3["".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        array3[" ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array3["  ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array3["   ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        final String s4 = EnumFaceDirection.I["   ".length()];
        final int length4 = "   ".length();
        final VertexInformation[] array4 = new VertexInformation[0x54 ^ 0x50];
        array4["".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        array4[" ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        array4["  ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        array4["   ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        final String s5 = EnumFaceDirection.I[0x0 ^ 0x4];
        final int n = 0x3C ^ 0x38;
        final VertexInformation[] array5 = new VertexInformation[0x40 ^ 0x44];
        array5["".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        array5[" ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array5["  ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        array5["   ".length()] = new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        final String s6 = EnumFaceDirection.I[0x97 ^ 0x92];
        final int n2 = 0x78 ^ 0x7D;
        final VertexInformation[] array6 = new VertexInformation[0xAC ^ 0xA8];
        array6["".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null);
        array6[" ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null);
        array6["  ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null);
        array6["   ".length()] = new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null);
        final EnumFaceDirection[] enum$VALUES = new EnumFaceDirection[0x1B ^ 0x1D];
        enum$VALUES["".length()] = EnumFaceDirection.DOWN;
        enum$VALUES[" ".length()] = EnumFaceDirection.UP;
        enum$VALUES["  ".length()] = EnumFaceDirection.NORTH;
        enum$VALUES["   ".length()] = EnumFaceDirection.SOUTH;
        enum$VALUES[0xAC ^ 0xA8] = EnumFaceDirection.WEST;
        enum$VALUES[0x51 ^ 0x54] = EnumFaceDirection.EAST;
        ENUM$VALUES = enum$VALUES;
        (facings = new EnumFaceDirection[0x5F ^ 0x59])[Constants.DOWN_INDEX] = EnumFaceDirection.DOWN;
        EnumFaceDirection.facings[Constants.UP_INDEX] = EnumFaceDirection.UP;
        EnumFaceDirection.facings[Constants.NORTH_INDEX] = EnumFaceDirection.NORTH;
        EnumFaceDirection.facings[Constants.SOUTH_INDEX] = EnumFaceDirection.SOUTH;
        EnumFaceDirection.facings[Constants.WEST_INDEX] = EnumFaceDirection.WEST;
        EnumFaceDirection.facings[Constants.EAST_INDEX] = EnumFaceDirection.EAST;
    }
    
    public static EnumFaceDirection getFacing(final EnumFacing enumFacing) {
        return EnumFaceDirection.facings[enumFacing.getIndex()];
    }
    
    public VertexInformation func_179025_a(final int n) {
        return this.vertexInfos[n];
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x4 ^ 0x2])["".length()] = I("56<%", "qykkz");
        EnumFaceDirection.I[" ".length()] = I("\u001e*", "KzERv");
        EnumFaceDirection.I["  ".length()] = I("\u001f\u0001 3?", "QNrgw");
        EnumFaceDirection.I["   ".length()] = I("\u0006\u001b\u000110", "UTTex");
        EnumFaceDirection.I[0xC ^ 0x8] = I("\u0006&5\u0011", "QcfEJ");
        EnumFaceDirection.I[0xA4 ^ 0xA1] = I("\u0007\u0018='", "BYnsA");
    }
    
    private EnumFaceDirection(final String s, final int n, final VertexInformation[] vertexInfos) {
        this.vertexInfos = vertexInfos;
    }
    
    public static final class Constants
    {
        public static final int EAST_INDEX;
        public static final int WEST_INDEX;
        public static final int UP_INDEX;
        public static final int SOUTH_INDEX;
        public static final int DOWN_INDEX;
        public static final int NORTH_INDEX;
        
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
        
        static {
            SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
            UP_INDEX = EnumFacing.UP.getIndex();
            EAST_INDEX = EnumFacing.EAST.getIndex();
            NORTH_INDEX = EnumFacing.NORTH.getIndex();
            DOWN_INDEX = EnumFacing.DOWN.getIndex();
            WEST_INDEX = EnumFacing.WEST.getIndex();
        }
    }
    
    public static class VertexInformation
    {
        public final int field_179184_a;
        public final int field_179183_c;
        public final int field_179182_b;
        
        VertexInformation(final int n, final int n2, final int n3, final VertexInformation vertexInformation) {
            this(n, n2, n3);
        }
        
        private VertexInformation(final int field_179184_a, final int field_179182_b, final int field_179183_c) {
            this.field_179184_a = field_179184_a;
            this.field_179182_b = field_179182_b;
            this.field_179183_c = field_179183_c;
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
                if (2 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
