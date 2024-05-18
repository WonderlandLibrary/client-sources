/*    */ package net.minecraft.network.login.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.PublicKey;
/*    */ import javax.crypto.SecretKey;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginServer;
/*    */ import net.minecraft.util.CryptManager;
/*    */ 
/*    */ public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer> {
/* 14 */   private byte[] secretKeyEncrypted = new byte[0];
/* 15 */   private byte[] verifyTokenEncrypted = new byte[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public C01PacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
/* 23 */     this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
/* 24 */     this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.secretKeyEncrypted = buf.readByteArray();
/* 33 */     this.verifyTokenEncrypted = buf.readByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeByteArray(this.secretKeyEncrypted);
/* 42 */     buf.writeByteArray(this.verifyTokenEncrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginServer handler) {
/* 50 */     handler.processEncryptionResponse(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public SecretKey getSecretKey(PrivateKey key) {
/* 55 */     return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getVerifyToken(PrivateKey key) {
/* 60 */     return (key == null) ? this.verifyTokenEncrypted : CryptManager.decryptData(key, this.verifyTokenEncrypted);
/*    */   }
/*    */   
/*    */   public C01PacketEncryptionResponse() {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\login\client\C01PacketEncryptionResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */