package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class PacketEncryptionResponse extends Packet
{
    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketEncryptionResponse(SecretKey key, PublicKey publicKey, byte[] hash)
    {
        this.sharedSecret = CryptManager.encryptData(publicKey, key.getEncoded());
        this.verifyToken = CryptManager.encryptData(publicKey, hash);
    }

    public PacketEncryptionResponse()
    {
    }

    public byte[] getSharedSecret()
    {
        return this.sharedSecret;
    }

    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }

    public void read(PacketBuf buf)
    {
        this.sharedSecret = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }

    public void write(PacketBuf buf)
    {
        buf.writeByteArray(this.sharedSecret);
        buf.writeByteArray(this.verifyToken);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }
}
