/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

public class CPacketEncryptionResponse
implements Packet<INetHandlerLoginServer> {
    private byte[] secretKeyEncrypted = new byte[0];
    private byte[] verifyTokenEncrypted = new byte[0];

    public CPacketEncryptionResponse() {
    }

    public CPacketEncryptionResponse(SecretKey secret, PublicKey key, byte[] verifyToken) {
        this.secretKeyEncrypted = CryptManager.encryptData(key, secret.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(key, verifyToken);
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.secretKeyEncrypted = buf.readByteArray();
        this.verifyTokenEncrypted = buf.readByteArray();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByteArray(this.secretKeyEncrypted);
        buf.writeByteArray(this.verifyTokenEncrypted);
    }

    @Override
    public void processPacket(INetHandlerLoginServer handler) {
        handler.processEncryptionResponse(this);
    }

    public SecretKey getSecretKey(PrivateKey key) {
        return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
    }

    public byte[] getVerifyToken(PrivateKey key) {
        return key == null ? this.verifyTokenEncrypted : CryptManager.decryptData(key, this.verifyTokenEncrypted);
    }
}

