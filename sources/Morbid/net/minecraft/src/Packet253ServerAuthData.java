package net.minecraft.src;

import java.security.*;
import java.io.*;

public class Packet253ServerAuthData extends Packet
{
    private String serverId;
    private PublicKey publicKey;
    private byte[] verifyToken;
    
    public Packet253ServerAuthData() {
        this.verifyToken = new byte[0];
    }
    
    public Packet253ServerAuthData(final String par1Str, final PublicKey par2PublicKey, final byte[] par3ArrayOfByte) {
        this.verifyToken = new byte[0];
        this.serverId = par1Str;
        this.publicKey = par2PublicKey;
        this.verifyToken = par3ArrayOfByte;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.serverId = Packet.readString(par1DataInputStream, 20);
        this.publicKey = CryptManager.decodePublicKey(Packet.readBytesFromStream(par1DataInputStream));
        this.verifyToken = Packet.readBytesFromStream(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.serverId, par1DataOutputStream);
        Packet.writeByteArray(par1DataOutputStream, this.publicKey.getEncoded());
        Packet.writeByteArray(par1DataOutputStream, this.verifyToken);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleServerAuthData(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.serverId.length() * 2 + 2 + this.publicKey.getEncoded().length + 2 + this.verifyToken.length;
    }
    
    public String getServerId() {
        return this.serverId;
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
}
