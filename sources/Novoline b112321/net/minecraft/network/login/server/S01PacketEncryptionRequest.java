package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet {
   private String hashedServerId;
   private PublicKey publicKey;
   private byte[] verifyToken;

   public S01PacketEncryptionRequest() {
   }

   public S01PacketEncryptionRequest(String var1, PublicKey var2, byte[] var3) {
      this.hashedServerId = var1;
      this.publicKey = var2;
      this.verifyToken = var3;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.hashedServerId = var1.a(20);
      this.publicKey = CryptManager.decodePublicKey(var1.readByteArray());
      this.verifyToken = var1.readByteArray();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.hashedServerId);
      var1.writeByteArray(this.publicKey.getEncoded());
      var1.writeByteArray(this.verifyToken);
   }

   public void processPacket(INetHandlerLoginClient var1) {
      var1.handleEncryptionRequest(this);
   }

   public String getServerId() {
      return this.hashedServerId;
   }

   public PublicKey getPublicKey() {
      return this.publicKey;
   }

   public byte[] getVerifyToken() {
      return this.verifyToken;
   }
}
