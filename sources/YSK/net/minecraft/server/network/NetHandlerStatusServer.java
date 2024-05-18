package net.minecraft.server.network;

import net.minecraft.network.status.*;
import net.minecraft.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private static final String[] I;
    private final MinecraftServer server;
    private static final IChatComponent field_183007_a;
    private final NetworkManager networkManager;
    private boolean field_183008_d;
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    @Override
    public void processPing(final C01PacketPing c01PacketPing) {
        this.networkManager.sendPacket(new S01PacketPong(c01PacketPing.getClientTime()));
        this.networkManager.closeChannel(NetHandlerStatusServer.field_183007_a);
    }
    
    static {
        I();
        field_183007_a = new ChatComponentText(NetHandlerStatusServer.I["".length()]);
    }
    
    @Override
    public void processServerQuery(final C00PacketServerQuery c00PacketServerQuery) {
        if (this.field_183008_d) {
            this.networkManager.closeChannel(NetHandlerStatusServer.field_183007_a);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            this.field_183008_d = (" ".length() != 0);
            this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NetHandlerStatusServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("=\"\u0015!!\u001dv\u00060%\u001b3\u0007!t\u00067\u0007u6\u000b3\u001au<\u000f8\u001091\nx", "nVtUT");
    }
}
