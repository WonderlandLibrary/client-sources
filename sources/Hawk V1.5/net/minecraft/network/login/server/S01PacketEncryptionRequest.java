package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet {
   private PublicKey publicKey;
   private String hashedServerId;
   private byte[] field_149611_c;
   private static final String __OBFID = "CL_00001376";

   public String func_149609_c() {
      return this.hashedServerId;
   }

   public void processPacket(INetHandlerLoginClient var1) {
      var1.handleEncryptionRequest(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.hashedServerId = var1.readStringFromBuffer(20);
      this.publicKey = CryptManager.decodePublicKey(var1.readByteArray());
      this.field_149611_c = var1.readByteArray();
   }

   public PublicKey func_149608_d() {
      return this.publicKey;
   }

   public byte[] func_149607_e() {
      return this.field_149611_c;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerLoginClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.hashedServerId);
      var1.writeByteArray(this.publicKey.getEncoded());
      var1.writeByteArray(this.field_149611_c);
   }

   public S01PacketEncryptionRequest(String var1, PublicKey var2, byte[] var3) {
      this.hashedServerId = var1;
      this.publicKey = var2;
      this.field_149611_c = var3;
   }

   public S01PacketEncryptionRequest() {
   }
}
