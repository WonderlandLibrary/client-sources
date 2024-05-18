package net.minecraft.server.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.util.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final MinecraftServer server;
    private static int[] $SWITCH_TABLE$net$minecraft$network$EnumConnectionState;
    private final NetworkManager networkManager;
    private static final String[] I;
    
    static {
        I();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$EnumConnectionState() {
        final int[] $switch_TABLE$net$minecraft$network$EnumConnectionState = NetHandlerHandshakeTCP.$SWITCH_TABLE$net$minecraft$network$EnumConnectionState;
        if ($switch_TABLE$net$minecraft$network$EnumConnectionState != null) {
            return $switch_TABLE$net$minecraft$network$EnumConnectionState;
        }
        final int[] $switch_TABLE$net$minecraft$network$EnumConnectionState2 = new int[EnumConnectionState.values().length];
        try {
            $switch_TABLE$net$minecraft$network$EnumConnectionState2[EnumConnectionState.HANDSHAKING.ordinal()] = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$EnumConnectionState2[EnumConnectionState.LOGIN.ordinal()] = (0x5F ^ 0x5B);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$EnumConnectionState2[EnumConnectionState.PLAY.ordinal()] = "  ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$EnumConnectionState2[EnumConnectionState.STATUS.ordinal()] = "   ".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return NetHandlerHandshakeTCP.$SWITCH_TABLE$net$minecraft$network$EnumConnectionState = $switch_TABLE$net$minecraft$network$EnumConnectionState2;
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\r\u00061 %6\u0016!d7'\u00013!6cS\fc)b\u00001-(.S**ds]}j|", "BsEDD");
        NetHandlerHandshakeTCP.I[" ".length()] = I("\u00071715<!'u7$-&; id\u001391)7&u!;!cdzpj{", "HDCUT");
        NetHandlerHandshakeTCP.I["  ".length()] = I("\u0002\u000f\"1:\"\u0005t98?\u0004:$?$\u000ft", "KaTPV");
    }
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        switch ($SWITCH_TABLE$net$minecraft$network$EnumConnectionState()[c00Handshake.getRequestedState().ordinal()]) {
            case 4: {
                this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
                if (c00Handshake.getProtocolVersion() > (0x59 ^ 0x76)) {
                    final ChatComponentText chatComponentText = new ChatComponentText(NetHandlerHandshakeTCP.I["".length()]);
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
                    this.networkManager.closeChannel(chatComponentText);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break;
                }
                else if (c00Handshake.getProtocolVersion() < (0x49 ^ 0x66)) {
                    final ChatComponentText chatComponentText2 = new ChatComponentText(NetHandlerHandshakeTCP.I[" ".length()]);
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText2));
                    this.networkManager.closeChannel(chatComponentText2);
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    this.networkManager.setNetHandler(new NetHandlerLoginServer(this.server, this.networkManager));
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 3: {
                this.networkManager.setConnectionState(EnumConnectionState.STATUS);
                this.networkManager.setNetHandler(new NetHandlerStatusServer(this.server, this.networkManager));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException(NetHandlerHandshakeTCP.I["  ".length()] + c00Handshake.getRequestedState());
            }
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NetHandlerHandshakeTCP(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
}
