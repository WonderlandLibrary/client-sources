/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CryptException;
import net.minecraft.util.CryptManager;

public class SEncryptionRequestPacket
implements IPacket<IClientLoginNetHandler> {
    private String hashedServerId;
    private byte[] publicKey;
    private byte[] verifyToken;

    public SEncryptionRequestPacket() {
    }

    public SEncryptionRequestPacket(String string, byte[] byArray, byte[] byArray2) {
        this.hashedServerId = string;
        this.publicKey = byArray;
        this.verifyToken = byArray2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.hashedServerId = packetBuffer.readString(20);
        this.publicKey = packetBuffer.readByteArray();
        this.verifyToken = packetBuffer.readByteArray();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hashedServerId);
        packetBuffer.writeByteArray(this.publicKey);
        packetBuffer.writeByteArray(this.verifyToken);
    }

    @Override
    public void processPacket(IClientLoginNetHandler iClientLoginNetHandler) {
        iClientLoginNetHandler.handleEncryptionRequest(this);
    }

    public String getServerId() {
        return this.hashedServerId;
    }

    public PublicKey getPublicKey() throws CryptException {
        return CryptManager.decodePublicKey(this.publicKey);
    }

    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientLoginNetHandler)iNetHandler);
    }
}

