package net.minecraft.network.login.server;

import net.minecraft.network.login.*;
import com.mojang.authlib.*;
import net.minecraft.network.*;
import java.util.*;
import java.io.*;

public class S02PacketLoginSuccess implements Packet<INetHandlerLoginClient>
{
    private GameProfile profile;
    private static final String[] I;
    
    @Override
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleLoginSuccess(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    public S02PacketLoginSuccess() {
    }
    
    public GameProfile getProfile() {
        return this.profile;
    }
    
    public S02PacketLoginSuccess(final GameProfile profile) {
        this.profile = profile;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "Qenxi");
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        final UUID id = this.profile.getId();
        String string;
        if (id == null) {
            string = S02PacketLoginSuccess.I["".length()];
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            string = id.toString();
        }
        packetBuffer.writeString(string);
        packetBuffer.writeString(this.profile.getName());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile(UUID.fromString(packetBuffer.readStringFromBuffer(0xC ^ 0x28)), packetBuffer.readStringFromBuffer(0xBF ^ 0xAF));
    }
    
    static {
        I();
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
