package net.minecraft.client.multiplayer;

import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ServerData
{
    private String serverIcon;
    public boolean field_78841_f;
    private static final String[] I;
    private ServerResourceMode resourceMode;
    public String populationInfo;
    public String serverIP;
    public String serverName;
    public String serverMOTD;
    private boolean field_181042_l;
    public String gameVersion;
    public int version;
    public long pingToServer;
    public String playerList;
    
    public void setBase64EncodedIconData(final String serverIcon) {
        this.serverIcon = serverIcon;
    }
    
    private static void I() {
        (I = new String[0x37 ^ 0x3B])["".length()] = I("xtvGK", "IZNis");
        ServerData.I[" ".length()] = I("\u0000\u001b\u0005\u0003", "nzhfM");
        ServerData.I["  ".length()] = I("\u000f\u0006", "fvqvd");
        ServerData.I["   ".length()] = I("\u0007'\u001e\f", "nDqbq");
        ServerData.I[0x83 ^ 0x87] = I("\u0014\u001a$-\u0019\u0001-\"0\u001d\u0000\u000b\";", "uyGHi");
        ServerData.I[0x42 ^ 0x47] = I("1)7$7$\u001e193%812", "PJTAG");
        ServerData.I[0x55 ^ 0x53] = I(";->\u0011", "ULStE");
        ServerData.I[0x6A ^ 0x6D] = I("\u001d<", "tLpeA");
        ServerData.I[0xCA ^ 0xC2] = I("\u0013*\u000e\u0019", "zIawr");
        ServerData.I[0x4D ^ 0x44] = I("/5\u00076", "FVhXr");
        ServerData.I[0x9A ^ 0x90] = I("$-\b\"\u00151\u001a\u000e?\u00110<\u000e4", "ENkGe");
        ServerData.I[0x34 ^ 0x3F] = I("8%!\u0002\u0007-\u0012'\u001f\u0003,4'\u0014", "YFBgw");
    }
    
    static {
        I();
    }
    
    public ServerResourceMode getResourceMode() {
        return this.resourceMode;
    }
    
    public static ServerData getServerDataFromNBTCompound(final NBTTagCompound nbtTagCompound) {
        final ServerData serverData = new ServerData(nbtTagCompound.getString(ServerData.I[0x57 ^ 0x51]), nbtTagCompound.getString(ServerData.I[0x11 ^ 0x16]), "".length() != 0);
        if (nbtTagCompound.hasKey(ServerData.I[0x7D ^ 0x75], 0x57 ^ 0x5F)) {
            serverData.setBase64EncodedIconData(nbtTagCompound.getString(ServerData.I[0x83 ^ 0x8A]));
        }
        if (nbtTagCompound.hasKey(ServerData.I[0x84 ^ 0x8E], " ".length())) {
            if (nbtTagCompound.getBoolean(ServerData.I[0x75 ^ 0x7E])) {
                serverData.setResourceMode(ServerResourceMode.ENABLED);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                serverData.setResourceMode(ServerResourceMode.DISABLED);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        else {
            serverData.setResourceMode(ServerResourceMode.PROMPT);
        }
        return serverData;
    }
    
    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }
    
    public boolean func_181041_d() {
        return this.field_181042_l;
    }
    
    public ServerData(final String serverName, final String serverIP, final boolean field_181042_l) {
        this.version = (0x5A ^ 0x75);
        this.gameVersion = ServerData.I["".length()];
        this.resourceMode = ServerResourceMode.PROMPT;
        this.serverName = serverName;
        this.serverIP = serverIP;
        this.field_181042_l = field_181042_l;
    }
    
    public void setResourceMode(final ServerResourceMode resourceMode) {
        this.resourceMode = resourceMode;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NBTTagCompound getNBTCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString(ServerData.I[" ".length()], this.serverName);
        nbtTagCompound.setString(ServerData.I["  ".length()], this.serverIP);
        if (this.serverIcon != null) {
            nbtTagCompound.setString(ServerData.I["   ".length()], this.serverIcon);
        }
        if (this.resourceMode == ServerResourceMode.ENABLED) {
            nbtTagCompound.setBoolean(ServerData.I[0x43 ^ 0x47], " ".length() != 0);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (this.resourceMode == ServerResourceMode.DISABLED) {
            nbtTagCompound.setBoolean(ServerData.I[0x2E ^ 0x2B], "".length() != 0);
        }
        return nbtTagCompound;
    }
    
    public void copyFrom(final ServerData serverData) {
        this.serverIP = serverData.serverIP;
        this.serverName = serverData.serverName;
        this.setResourceMode(serverData.getResourceMode());
        this.serverIcon = serverData.serverIcon;
        this.field_181042_l = serverData.field_181042_l;
    }
    
    public enum ServerResourceMode
    {
        DISABLED(ServerResourceMode.I["  ".length()], " ".length(), ServerResourceMode.I["   ".length()]), 
        ENABLED(ServerResourceMode.I["".length()], "".length(), ServerResourceMode.I[" ".length()]);
        
        private final IChatComponent motd;
        private static final String[] I;
        private static final ServerResourceMode[] ENUM$VALUES;
        
        PROMPT(ServerResourceMode.I[0x29 ^ 0x2D], "  ".length(), ServerResourceMode.I[0x1E ^ 0x1B]);
        
        static {
            I();
            final ServerResourceMode[] enum$VALUES = new ServerResourceMode["   ".length()];
            enum$VALUES["".length()] = ServerResourceMode.ENABLED;
            enum$VALUES[" ".length()] = ServerResourceMode.DISABLED;
            enum$VALUES["  ".length()] = ServerResourceMode.PROMPT;
            ENUM$VALUES = enum$VALUES;
        }
        
        public IChatComponent getMotd() {
            return this.motd;
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
                if (-1 >= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private ServerResourceMode(final String s, final int n, final String s2) {
            this.motd = new ChatComponentTranslation(ServerResourceMode.I[0x2E ^ 0x28] + s2, new Object["".length()]);
        }
        
        private static void I() {
            (I = new String[0x4B ^ 0x4C])["".length()] = I("=\u0007+\u001b4=\r", "xIjYx");
            ServerResourceMode.I[" ".length()] = I("'4\f\u0003+'>", "BZmaG");
            ServerResourceMode.I["  ".length()] = I("\u0016**6;\u001e&=", "Rcywy");
            ServerResourceMode.I["   ".length()] = I("-\u001f#\b\n%\u00134", "IvPih");
            ServerResourceMode.I[0xB1 ^ 0xB5] = I("=\b\u0016\u0003(9", "mZYNx");
            ServerResourceMode.I[0x98 ^ 0x9D] = I("\n\u0011\u000b\u001d6\u000e", "zcdpF");
            ServerResourceMode.I[0x87 ^ 0x81] = I("\u0015</\t\u0000\u0006..(K\u0006=85\u0010\u0006;.\n\u0004\u00173e", "tXKZe");
        }
    }
}
