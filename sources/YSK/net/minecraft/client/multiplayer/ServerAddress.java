package net.minecraft.client.multiplayer;

import java.net.*;
import java.util.*;
import javax.naming.directory.*;

public class ServerAddress
{
    private final int serverPort;
    private final String ipAddress;
    private static final String[] I;
    
    public String getIP() {
        return IDN.toASCII(this.ipAddress);
    }
    
    private static int parseIntWithDefault(final String s, final int n) {
        try {
            return Integer.parseInt(s.trim());
        }
        catch (Exception ex) {
            return n;
        }
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static String[] getServerAddress(final String s) {
        try {
            final String s2 = ServerAddress.I[0xF ^ 0xB];
            Class.forName(ServerAddress.I[0x9A ^ 0x9F]);
            final Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put(ServerAddress.I[0x5F ^ 0x59], ServerAddress.I[0x1E ^ 0x19]);
            hashtable.put(ServerAddress.I[0x50 ^ 0x58], ServerAddress.I[0x0 ^ 0x9]);
            hashtable.put(ServerAddress.I[0x2B ^ 0x21], ServerAddress.I[0x5D ^ 0x56]);
            final InitialDirContext initialDirContext = new InitialDirContext(hashtable);
            final String string = ServerAddress.I[0x60 ^ 0x6C] + s;
            final String[] array = new String[" ".length()];
            array["".length()] = ServerAddress.I[0x4D ^ 0x40];
            final String[] split = initialDirContext.getAttributes(string, array).get(ServerAddress.I[0x1 ^ 0xF]).get().toString().split(ServerAddress.I[0xCF ^ 0xC0], 0x6B ^ 0x6F);
            final String[] array2 = new String["  ".length()];
            array2["".length()] = split["   ".length()];
            array2[" ".length()] = split["  ".length()];
            return array2;
        }
        catch (Throwable t) {
            final String[] array3 = new String["  ".length()];
            array3["".length()] = s;
            array3[" ".length()] = Integer.toString(22126 + 14754 - 34008 + 22693);
            return array3;
        }
    }
    
    private static void I() {
        (I = new String[0x1D ^ 0xD])["".length()] = I("X", "bMyJa");
        ServerAddress.I[" ".length()] = I("\u001e", "ESWQB");
        ServerAddress.I["  ".length()] = I("+", "vbRnT");
        ServerAddress.I["   ".length()] = I("^", "ddpNt");
        ServerAddress.I[0x1A ^ 0x1E] = I(",=#a0:<`%-+;`+-<|\n!0\f= ;&7&\b. ;=<6", "ORNOC");
        ServerAddress.I[0x24 ^ 0x21] = I("\u0010&,k7\u0006'o/*\u0017 o!*\u0000g\u0005+70&/1!\u000b=\u0007$'\u0007&3<", "sIAED");
        ServerAddress.I[0xA8 ^ 0xAE] = I("/\u0011;\u0007J+\u0011 \u000f\n\"^+\u0007\u00071\u001f?\u001fJ,\u001e$\u0012\r$\u001c", "EpMfd");
        ServerAddress.I[0x7B ^ 0x7C] = I("$,$}=2-g9 #*g7 4m\r==\u0004,''+?7\u000f2-3,;*", "GCISN");
        ServerAddress.I[0x6E ^ 0x66] = I("\u0000.\u0010-i\u0004.\u000b%)\ra\u0016>(\u001c&\u0002)5D:\u0014 ", "jOfLG");
        ServerAddress.I[0x86 ^ 0x8F] = I("\u0000\n\u0017W", "dddmv");
        ServerAddress.I[0x25 ^ 0x2F] = I("!\u001f/I\u00127\u001el\r\u000f&\u0019l\u0003\u000f1^6\u000e\f'\u001f7\u0013O0\u00156\u0015\b'\u0003", "BpBga");
        ServerAddress.I[0x6D ^ 0x66] = I("a", "PCilp");
        ServerAddress.I[0x4F ^ 0x43] = I("\u001d=-45!\"%<$l\u000f09 l", "BPDZP");
        ServerAddress.I[0xCC ^ 0xC1] = I("?3\"", "latZM");
        ServerAddress.I[0x74 ^ 0x7A] = I("\u0006\u001e$", "ulRzf");
        ServerAddress.I[0x66 ^ 0x69] = I("E", "enIrS");
    }
    
    static {
        I();
    }
    
    private ServerAddress(final String ipAddress, final int serverPort) {
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
    }
    
    public int getPort() {
        return this.serverPort;
    }
    
    public static ServerAddress func_78860_a(final String s) {
        if (s == null) {
            return null;
        }
        String[] split = s.split(ServerAddress.I["".length()]);
        if (s.startsWith(ServerAddress.I[" ".length()])) {
            final int index = s.indexOf(ServerAddress.I["  ".length()]);
            if (index > 0) {
                final String substring = s.substring(" ".length(), index);
                final String trim = s.substring(index + " ".length()).trim();
                if (trim.startsWith(ServerAddress.I["   ".length()]) && trim.length() > 0) {
                    final String substring2 = trim.substring(" ".length());
                    final String[] array = new String["  ".length()];
                    array["".length()] = substring;
                    array[" ".length()] = substring2;
                    split = array;
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    final String[] array2 = new String[" ".length()];
                    array2["".length()] = substring;
                    split = array2;
                }
            }
        }
        if (split.length > "  ".length()) {
            final String[] array3 = new String[" ".length()];
            array3["".length()] = s;
            split = array3;
        }
        String s2 = split["".length()];
        int intWithDefault;
        if (split.length > " ".length()) {
            intWithDefault = parseIntWithDefault(split[" ".length()], 19717 + 266 - 14294 + 19876);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            intWithDefault = 8506 + 7840 - 14479 + 23698;
        }
        int intWithDefault2 = intWithDefault;
        if (intWithDefault2 == 13377 + 2768 + 511 + 8909) {
            final String[] serverAddress = getServerAddress(s2);
            s2 = serverAddress["".length()];
            intWithDefault2 = parseIntWithDefault(serverAddress[" ".length()], 2631 + 2556 + 9710 + 10668);
        }
        return new ServerAddress(s2, intWithDefault2);
    }
}
