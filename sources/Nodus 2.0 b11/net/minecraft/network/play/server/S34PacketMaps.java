/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S34PacketMaps
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149191_a;
/* 13:   */   private byte[] field_149190_b;
/* 14:   */   private static final String __OBFID = "CL_00001311";
/* 15:   */   
/* 16:   */   public S34PacketMaps() {}
/* 17:   */   
/* 18:   */   public S34PacketMaps(int p_i45202_1_, byte[] p_i45202_2_)
/* 19:   */   {
/* 20:19 */     this.field_149191_a = p_i45202_1_;
/* 21:20 */     this.field_149190_b = p_i45202_2_;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:28 */     this.field_149191_a = p_148837_1_.readVarIntFromBuffer();
/* 28:29 */     this.field_149190_b = new byte[p_148837_1_.readUnsignedShort()];
/* 29:30 */     p_148837_1_.readBytes(this.field_149190_b);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:38 */     p_148840_1_.writeVarIntToBuffer(this.field_149191_a);
/* 36:39 */     p_148840_1_.writeShort(this.field_149190_b.length);
/* 37:40 */     p_148840_1_.writeBytes(this.field_149190_b);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processPacket(INetHandlerPlayClient p_149189_1_)
/* 41:   */   {
/* 42:45 */     p_149189_1_.handleMaps(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String serialize()
/* 46:   */   {
/* 47:53 */     return String.format("id=%d, length=%d", new Object[] { Integer.valueOf(this.field_149191_a), Integer.valueOf(this.field_149190_b.length) });
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int func_149188_c()
/* 51:   */   {
/* 52:58 */     return this.field_149191_a;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public byte[] func_149187_d()
/* 56:   */   {
/* 57:63 */     return this.field_149190_b;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void processPacket(INetHandler p_148833_1_)
/* 61:   */   {
/* 62:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S34PacketMaps
 * JD-Core Version:    0.7.0.1
 */