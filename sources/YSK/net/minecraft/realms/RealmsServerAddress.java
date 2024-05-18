package net.minecraft.realms;

import net.minecraft.client.multiplayer.*;

public class RealmsServerAddress
{
    private final String host;
    private final int port;
    
    public int getPort() {
        return this.port;
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static RealmsServerAddress parseString(final String s) {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(s);
        return new RealmsServerAddress(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    public String getHost() {
        return this.host;
    }
    
    protected RealmsServerAddress(final String host, final int port) {
        this.host = host;
        this.port = port;
    }
}
