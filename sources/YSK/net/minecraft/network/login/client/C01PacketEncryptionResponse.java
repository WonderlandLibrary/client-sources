package net.minecraft.network.login.client;

import net.minecraft.network.login.*;
import javax.crypto.*;
import net.minecraft.util.*;
import java.security.*;
import net.minecraft.network.*;
import java.io.*;

public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer>
{
    private byte[] verifyTokenEncrypted;
    private byte[] secretKeyEncrypted;
    
    public C01PacketEncryptionResponse(final SecretKey secretKey, final PublicKey publicKey, final byte[] array) {
        this.secretKeyEncrypted = new byte["".length()];
        this.verifyTokenEncrypted = new byte["".length()];
        this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, array);
    }
    
    public C01PacketEncryptionResponse() {
        this.secretKeyEncrypted = new byte["".length()];
        this.verifyTokenEncrypted = new byte["".length()];
    }
    
    public SecretKey getSecretKey(final PrivateKey privateKey) {
        return CryptManager.decryptSharedKey(privateKey, this.secretKeyEncrypted);
    }
    
    @Override
    public void processPacket(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processEncryptionResponse(this);
    }
    
    public byte[] getVerifyToken(final PrivateKey privateKey) {
        byte[] array;
        if (privateKey == null) {
            array = this.verifyTokenEncrypted;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            array = CryptManager.decryptData(privateKey, this.verifyTokenEncrypted);
        }
        return array;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByteArray(this.secretKeyEncrypted);
        packetBuffer.writeByteArray(this.verifyTokenEncrypted);
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.secretKeyEncrypted = packetBuffer.readByteArray();
        this.verifyTokenEncrypted = packetBuffer.readByteArray();
    }
}
