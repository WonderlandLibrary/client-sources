/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import java.io.IOException;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  9:   */ 
/* 10:   */ public class C17PacketCustomPayload
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private String field_149562_a;
/* 14:   */   private int field_149560_b;
/* 15:   */   private byte[] field_149561_c;
/* 16:   */   private static final String __OBFID = "CL_00001356";
/* 17:   */   
/* 18:   */   public C17PacketCustomPayload() {}
/* 19:   */   
/* 20:   */   public C17PacketCustomPayload(String p_i45248_1_, ByteBuf p_i45248_2_)
/* 21:   */   {
/* 22:21 */     this(p_i45248_1_, p_i45248_2_.array());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public C17PacketCustomPayload(String p_i45249_1_, byte[] p_i45249_2_)
/* 26:   */   {
/* 27:26 */     this.field_149562_a = p_i45249_1_;
/* 28:27 */     this.field_149561_c = p_i45249_2_;
/* 29:29 */     if (p_i45249_2_ != null)
/* 30:   */     {
/* 31:31 */       this.field_149560_b = p_i45249_2_.length;
/* 32:33 */       if (this.field_149560_b >= 32767) {
/* 33:35 */         throw new IllegalArgumentException("Payload may not be larger than 32k");
/* 34:   */       }
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:45 */     this.field_149562_a = p_148837_1_.readStringFromBuffer(20);
/* 42:46 */     this.field_149560_b = p_148837_1_.readShort();
/* 43:48 */     if ((this.field_149560_b > 0) && (this.field_149560_b < 32767))
/* 44:   */     {
/* 45:50 */       this.field_149561_c = new byte[this.field_149560_b];
/* 46:51 */       p_148837_1_.readBytes(this.field_149561_c);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 51:   */     throws IOException
/* 52:   */   {
/* 53:60 */     p_148840_1_.writeStringToBuffer(this.field_149562_a);
/* 54:61 */     p_148840_1_.writeShort((short)this.field_149560_b);
/* 55:63 */     if (this.field_149561_c != null) {
/* 56:65 */       p_148840_1_.writeBytes(this.field_149561_c);
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void processPacket(INetHandlerPlayServer p_149557_1_)
/* 61:   */   {
/* 62:71 */     p_149557_1_.processVanilla250Packet(this);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String func_149559_c()
/* 66:   */   {
/* 67:76 */     return this.field_149562_a;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public byte[] func_149558_e()
/* 71:   */   {
/* 72:81 */     return this.field_149561_c;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void processPacket(INetHandler p_148833_1_)
/* 76:   */   {
/* 77:86 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C17PacketCustomPayload
 * JD-Core Version:    0.7.0.1
 */