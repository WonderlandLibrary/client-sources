/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.IServerLoginNetHandler;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;

public class CEncryptionResponsePacket
implements IPacket<IServerLoginNetHandler> {
    private byte[] secretKeyEncrypted = new byte[0];
    private byte[] verifyTokenEncrypted = new byte[0];

    public CEncryptionResponsePacket() {
    }

    public CEncryptionResponsePacket(SecretKey secretKey, PublicKey publicKey, byte[] byArray) throws CryptException {
        this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, byArray);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.secretKeyEncrypted = packetBuffer.readByteArray();
        this.verifyTokenEncrypted = packetBuffer.readByteArray();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByteArray(this.secretKeyEncrypted);
        packetBuffer.writeByteArray(this.verifyTokenEncrypted);
    }

    @Override
    public void processPacket(IServerLoginNetHandler iServerLoginNetHandler) {
        iServerLoginNetHandler.processEncryptionResponse(this);
    }

    public SecretKey getSecretKey(PrivateKey privateKey) throws CryptException {
        return CryptManager.decryptSharedKey(privateKey, this.secretKeyEncrypted);
    }

    public byte[] getVerifyToken(PrivateKey privateKey) throws CryptException {
        return CryptManager.decryptData(privateKey, this.verifyTokenEncrypted);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerLoginNetHandler)iNetHandler);
    }
}

