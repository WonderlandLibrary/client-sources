package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

public class C01PacketEncryptionResponse implements Packet {
   private static final String __OBFID = "CL_00001380";
   private byte[] field_149302_a = new byte[0];
   private byte[] field_149301_b = new byte[0];

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149302_a = var1.readByteArray();
      this.field_149301_b = var1.readByteArray();
   }

   public byte[] func_149299_b(PrivateKey var1) {
      return var1 == null ? this.field_149301_b : CryptManager.decryptData(var1, this.field_149301_b);
   }

   public void processPacket(INetHandlerLoginServer var1) {
      var1.processEncryptionResponse(this);
   }

   public SecretKey func_149300_a(PrivateKey var1) {
      return CryptManager.decryptSharedKey(var1, this.field_149302_a);
   }

   public C01PacketEncryptionResponse() {
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerLoginServer)var1);
   }

   public C01PacketEncryptionResponse(SecretKey var1, PublicKey var2, byte[] var3) {
      this.field_149302_a = CryptManager.encryptData(var2, var1.getEncoded());
      this.field_149301_b = CryptManager.encryptData(var2, var3);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByteArray(this.field_149302_a);
      var1.writeByteArray(this.field_149301_b);
   }
}
