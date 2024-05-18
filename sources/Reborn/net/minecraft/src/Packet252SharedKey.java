package net.minecraft.src;

import javax.crypto.*;
import java.io.*;
import java.security.*;

public class Packet252SharedKey extends Packet
{
    private byte[] sharedSecret;
    private byte[] verifyToken;
    private SecretKey sharedKey;
    
    public Packet252SharedKey() {
        this.sharedSecret = new byte[0];
        this.verifyToken = new byte[0];
    }
    
    public Packet252SharedKey(final SecretKey par1SecretKey, final PublicKey par2PublicKey, final byte[] par3ArrayOfByte) {
        this.sharedSecret = new byte[0];
        this.verifyToken = new byte[0];
        this.sharedKey = par1SecretKey;
        this.sharedSecret = CryptManager.encryptData(par2PublicKey, par1SecretKey.getEncoded());
        this.verifyToken = CryptManager.encryptData(par2PublicKey, par3ArrayOfByte);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.sharedSecret = Packet.readBytesFromStream(par1DataInputStream);
        this.verifyToken = Packet.readBytesFromStream(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeByteArray(par1DataOutputStream, this.sharedSecret);
        Packet.writeByteArray(par1DataOutputStream, this.verifyToken);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSharedKey(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.sharedSecret.length + 2 + this.verifyToken.length;
    }
    
    public SecretKey getSharedKey(final PrivateKey par1PrivateKey) {
        return (par1PrivateKey == null) ? this.sharedKey : (this.sharedKey = CryptManager.decryptSharedKey(par1PrivateKey, this.sharedSecret));
    }
    
    public SecretKey getSharedKey() {
        return this.getSharedKey(null);
    }
    
    public byte[] getVerifyToken(final PrivateKey par1PrivateKey) {
        return (par1PrivateKey == null) ? this.verifyToken : CryptManager.decryptData(par1PrivateKey, this.verifyToken);
    }
}
