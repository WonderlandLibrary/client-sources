/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S28PacketEffect
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149251_a;
/* 13:   */   private int field_149249_b;
/* 14:   */   private int field_149250_c;
/* 15:   */   private int field_149247_d;
/* 16:   */   private int field_149248_e;
/* 17:   */   private boolean field_149246_f;
/* 18:   */   private static final String __OBFID = "CL_00001307";
/* 19:   */   
/* 20:   */   public S28PacketEffect() {}
/* 21:   */   
/* 22:   */   public S28PacketEffect(int p_i45198_1_, int p_i45198_2_, int p_i45198_3_, int p_i45198_4_, int p_i45198_5_, boolean p_i45198_6_)
/* 23:   */   {
/* 24:23 */     this.field_149251_a = p_i45198_1_;
/* 25:24 */     this.field_149250_c = p_i45198_2_;
/* 26:25 */     this.field_149247_d = p_i45198_3_;
/* 27:26 */     this.field_149248_e = p_i45198_4_;
/* 28:27 */     this.field_149249_b = p_i45198_5_;
/* 29:28 */     this.field_149246_f = p_i45198_6_;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_149251_a = p_148837_1_.readInt();
/* 36:37 */     this.field_149250_c = p_148837_1_.readInt();
/* 37:38 */     this.field_149247_d = (p_148837_1_.readByte() & 0xFF);
/* 38:39 */     this.field_149248_e = p_148837_1_.readInt();
/* 39:40 */     this.field_149249_b = p_148837_1_.readInt();
/* 40:41 */     this.field_149246_f = p_148837_1_.readBoolean();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 44:   */     throws IOException
/* 45:   */   {
/* 46:49 */     p_148840_1_.writeInt(this.field_149251_a);
/* 47:50 */     p_148840_1_.writeInt(this.field_149250_c);
/* 48:51 */     p_148840_1_.writeByte(this.field_149247_d & 0xFF);
/* 49:52 */     p_148840_1_.writeInt(this.field_149248_e);
/* 50:53 */     p_148840_1_.writeInt(this.field_149249_b);
/* 51:54 */     p_148840_1_.writeBoolean(this.field_149246_f);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandlerPlayClient p_149245_1_)
/* 55:   */   {
/* 56:59 */     p_149245_1_.handleEffect(this);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean func_149244_c()
/* 60:   */   {
/* 61:64 */     return this.field_149246_f;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public int func_149242_d()
/* 65:   */   {
/* 66:69 */     return this.field_149251_a;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public int func_149241_e()
/* 70:   */   {
/* 71:74 */     return this.field_149249_b;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public int func_149240_f()
/* 75:   */   {
/* 76:79 */     return this.field_149250_c;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int func_149243_g()
/* 80:   */   {
/* 81:84 */     return this.field_149247_d;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public int func_149239_h()
/* 85:   */   {
/* 86:89 */     return this.field_149248_e;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void processPacket(INetHandler p_148833_1_)
/* 90:   */   {
/* 91:94 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S28PacketEffect
 * JD-Core Version:    0.7.0.1
 */