package net.minecraft.network.login.client;

import javax.crypto.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import java.security.*;
import net.minecraft.network.*;

public class C01PacketEncryptionResponse implements Packet
{
    private byte[] field_149302_a;
    private byte[] field_149301_b;
    
    public C01PacketEncryptionResponse() {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
    }
    
    public C01PacketEncryptionResponse(final SecretKey p_i45271_1_, final PublicKey p_i45271_2_, final byte[] p_i45271_3_) {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
        this.field_149302_a = CryptManager.encryptData(p_i45271_2_, p_i45271_1_.getEncoded());
        this.field_149301_b = CryptManager.encryptData(p_i45271_2_, p_i45271_3_);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_149302_a = data.readByteArray();
        this.field_149301_b = data.readByteArray();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeByteArray(this.field_149302_a);
        data.writeByteArray(this.field_149301_b);
    }
    
    public void processPacket(final INetHandlerLoginServer handler) {
        handler.processEncryptionResponse(this);
    }
    
    public SecretKey func_149300_a(final PrivateKey key) {
        return CryptManager.decryptSharedKey(key, this.field_149302_a);
    }
    
    public byte[] func_149299_b(final PrivateKey p_149299_1_) {
        return (p_149299_1_ == null) ? this.field_149301_b : CryptManager.decryptData(p_149299_1_, this.field_149301_b);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerLoginServer)handler);
    }
}
