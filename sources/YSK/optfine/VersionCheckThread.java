package optfine;

import java.net.*;
import net.minecraft.client.*;
import java.io.*;

public class VersionCheckThread extends Thread
{
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0xD3 ^ 0xC1])["".length()] = I("/862\u001a\u0005>4q\u0017\u0003\"s?\u0014\u001bp%4\u0003\u001f9<?", "lPSQq");
        VersionCheckThread.I[" ".length()] = I("\u0002\u00113\u0001bEJ(\u0001,\u0003\u0003.\u001f=D\u000b\"\u0005w\u001c\u00005\u00021\u0005\u000bh@vRK\u007f^\u0010.:\u0012_,\u0012\u0011", "jeGqX");
        VersionCheckThread.I["  ".length()] = I("& \\\u001b\"D0\u0014$\u0012\u0000\t\u001f", "ifqVa");
        VersionCheckThread.I["   ".length()] = I("gkLji", "VEtDQ");
        VersionCheckThread.I[0x61 ^ 0x65] = I("\u0019\u0000w\u0017\t{\u0004(;$2", "VFZZJ");
        VersionCheckThread.I[0x64 ^ 0x61] = I("!6g*\f\u0007\u0004#\u0000\u0006", "npJoh");
        VersionCheckThread.I[0x95 ^ 0x93] = I("\"-\u0015\u001b", "jiJNo");
        VersionCheckThread.I[0x5F ^ 0x58] = I("<)K\u00044\u001f\n\u0007%4", "sofVQ");
        VersionCheckThread.I[0x68 ^ 0x60] = I("\u0004C", "ArPAQ");
        VersionCheckThread.I[0xCC ^ 0xC5] = I("\u001b\u0011@\u0000\u0014\"6@\u001c\u0010&$\u0004%\u001b", "TWmJu");
        VersionCheckThread.I[0x6D ^ 0x67] = I("889\u0005]$<=\u0017\u001a=7", "RYOds");
        VersionCheckThread.I[0x92 ^ 0x99] = I(".\u000e@\f1\u0014\u000b\u0002:/\u0015", "aHmOA");
        VersionCheckThread.I[0x3 ^ 0xF] = I(":\u0017D5<\u0010?.6a#4\u001b\t%\u001a?", "uQizL");
        VersionCheckThread.I[0x4D ^ 0x40] = I("\u001c\u0007Y$\n6/3'W\u0005$\u001a\u000f\u0015!", "SAtkz");
        VersionCheckThread.I[0x33 ^ 0x3D] = I("eu", "oxrnQ");
        VersionCheckThread.I[0x1F ^ 0x10] = I("\u0000\u0016!\u0000\u00029\u001ds\u0015\u0004#\u001d7IK", "VsSsk");
        VersionCheckThread.I[0xB0 ^ 0xA0] = I(" h", "eYgYB");
        VersionCheckThread.I[0x51 ^ 0x40] = I("qP", "KpwSw");
    }
    
    static {
        I();
    }
    
    @Override
    public void run() {
        try {
            Config.dbg(VersionCheckThread.I["".length()]);
            final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(VersionCheckThread.I[" ".length()]).openConnection();
            if (Config.getGameSettings().snooperEnabled) {
                httpURLConnection.setRequestProperty(VersionCheckThread.I["  ".length()], VersionCheckThread.I["   ".length()]);
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0x29 ^ 0x2D], new StringBuilder().append(ClientBrandRetriever.getClientModName()).toString());
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0xC2 ^ 0xC7], VersionCheckThread.I[0xB7 ^ 0xB1]);
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0xAC ^ 0xAB], VersionCheckThread.I[0x87 ^ 0x8F]);
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0x58 ^ 0x51], new StringBuilder().append(System.getProperty(VersionCheckThread.I[0x5C ^ 0x56])).toString());
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0x93 ^ 0x98], new StringBuilder().append(Config.getAvailableProcessors()).toString());
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0x38 ^ 0x34], new StringBuilder().append(Config.openGlVersion).toString());
                httpURLConnection.setRequestProperty(VersionCheckThread.I[0x64 ^ 0x69], new StringBuilder().append(Config.openGlVendor).toString());
            }
            httpURLConnection.setDoInput(" ".length() != 0);
            httpURLConnection.setDoOutput("".length() != 0);
            httpURLConnection.connect();
            try {
                final InputStream inputStream = httpURLConnection.getInputStream();
                final String inputStream2 = Config.readInputStream(inputStream);
                inputStream.close();
                final String[] tokenize = Config.tokenize(inputStream2, VersionCheckThread.I[0xC9 ^ 0xC7]);
                if (tokenize.length >= " ".length()) {
                    final String newRelease = tokenize["".length()];
                    Config.dbg(VersionCheckThread.I[0x1D ^ 0x12] + newRelease);
                    if (Config.compareRelease(newRelease, VersionCheckThread.I[0x11 ^ 0x1]) <= 0) {
                        return;
                    }
                    Config.setNewRelease(newRelease);
                    return;
                }
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            Config.dbg(String.valueOf(ex.getClass().getName()) + VersionCheckThread.I[0x12 ^ 0x3] + ex.getMessage());
        }
    }
}
