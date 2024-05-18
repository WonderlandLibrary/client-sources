package net.minecraft.network.login.client;

import net.minecraft.network.login.*;
import com.mojang.authlib.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class C00PacketLoginStart implements Packet<INetHandlerLoginServer>
{
    private GameProfile profile;
    
    public GameProfile getProfile() {
        return this.profile;
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.profile.getName());
    }
    
    @Override
    public void processPacket(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processLoginStart(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile((UUID)null, packetBuffer.readStringFromBuffer(0x40 ^ 0x50));
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginServer)netHandler);
    }
    
    public C00PacketLoginStart(final GameProfile profile) {
        this.profile = profile;
    }
    
    public C00PacketLoginStart() {
    }
}
