/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import java.io.IOException;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S3FPacketCustomPayload
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private String field_149172_a;
/* 14:   */   private byte[] field_149171_b;
/* 15:   */   private static final String __OBFID = "CL_00001297";
/* 16:   */   
/* 17:   */   public S3FPacketCustomPayload() {}
/* 18:   */   
/* 19:   */   public S3FPacketCustomPayload(String p_i45189_1_, ByteBuf p_i45189_2_)
/* 20:   */   {
/* 21:20 */     this(p_i45189_1_, p_i45189_2_.array());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public S3FPacketCustomPayload(String p_i45190_1_, byte[] p_i45190_2_)
/* 25:   */   {
/* 26:25 */     this.field_149172_a = p_i45190_1_;
/* 27:26 */     this.field_149171_b = p_i45190_2_;
/* 28:28 */     if (p_i45190_2_.length >= 32767) {
/* 29:30 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     this.field_149172_a = p_148837_1_.readStringFromBuffer(20);
/* 37:40 */     this.field_149171_b = new byte[p_148837_1_.readUnsignedShort()];
/* 38:41 */     p_148837_1_.readBytes(this.field_149171_b);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 42:   */     throws IOException
/* 43:   */   {
/* 44:49 */     p_148840_1_.writeStringToBuffer(this.field_149172_a);
/* 45:50 */     p_148840_1_.writeShort(this.field_149171_b.length);
/* 46:51 */     p_148840_1_.writeBytes(this.field_149171_b);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void processPacket(INetHandlerPlayClient p_149170_1_)
/* 50:   */   {
/* 51:56 */     p_149170_1_.handleCustomPayload(this);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String func_149169_c()
/* 55:   */   {
/* 56:61 */     return this.field_149172_a;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public byte[] func_149168_d()
/* 60:   */   {
/* 61:66 */     return this.field_149171_b;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void processPacket(INetHandler p_148833_1_)
/* 65:   */   {
/* 66:71 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3FPacketCustomPayload
 * JD-Core Version:    0.7.0.1
 */