package net.minecraft.realms;

public class RealmsServerPing
{
    public long lastPingSnapshot;
    public String playerList;
    private static final String[] I;
    public String nrOfPlayers;
    
    static {
        I();
    }
    
    public RealmsServerPing() {
        this.nrOfPlayers = RealmsServerPing.I["".length()];
        this.lastPingSnapshot = 0L;
        this.playerList = RealmsServerPing.I[" ".length()];
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("S", "ciCpS");
        RealmsServerPing.I[" ".length()] = I("", "uQkvO");
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
