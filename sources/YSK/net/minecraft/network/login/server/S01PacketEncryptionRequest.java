package net.minecraft.network.login.server;

import net.minecraft.network.login.*;
import java.security.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class S01PacketEncryptionRequest implements Packet<INetHandlerLoginClient>
{
    private byte[] verifyToken;
    private PublicKey publicKey;
    private String hashedServerId;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hashedServerId);
        packetBuffer.writeByteArray(this.publicKey.getEncoded());
        packetBuffer.writeByteArray(this.verifyToken);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    public S01PacketEncryptionRequest(final String hashedServerId, final PublicKey publicKey, final byte[] verifyToken) {
        this.hashedServerId = hashedServerId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.hashedServerId = packetBuffer.readStringFromBuffer(0x4 ^ 0x10);
        this.publicKey = CryptManager.decodePublicKey(packetBuffer.readByteArray());
        this.verifyToken = packetBuffer.readByteArray();
    }
    
    public S01PacketEncryptionRequest() {
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getServerId() {
        return this.hashedServerId;
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    @Override
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleEncryptionRequest(this);
    }
}
