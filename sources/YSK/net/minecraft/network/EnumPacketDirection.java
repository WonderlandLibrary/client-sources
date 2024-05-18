package net.minecraft.network;

public enum EnumPacketDirection
{
    CLIENTBOUND(EnumPacketDirection.I[" ".length()], " ".length()), 
    SERVERBOUND(EnumPacketDirection.I["".length()], "".length());
    
    private static final EnumPacketDirection[] ENUM$VALUES;
    private static final String[] I;
    
    private EnumPacketDirection(final String s, final int n) {
    }
    
    static {
        I();
        final EnumPacketDirection[] enum$VALUES = new EnumPacketDirection["  ".length()];
        enum$VALUES["".length()] = EnumPacketDirection.SERVERBOUND;
        enum$VALUES[" ".length()] = EnumPacketDirection.CLIENTBOUND;
        ENUM$VALUES = enum$VALUES;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0000\u0014\u0014\u0012(\u0001\u0013\t\u0011#\u0017", "SQFDm");
        EnumPacketDirection.I[" ".length()] = I("\u0004*/\"9\u0013$)29\u0003", "Gffgw");
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
