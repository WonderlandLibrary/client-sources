/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S33PacketUpdateSign
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149352_a;
/* 13:   */   private int field_149350_b;
/* 14:   */   private int field_149351_c;
/* 15:   */   private String[] field_149349_d;
/* 16:   */   private static final String __OBFID = "CL_00001338";
/* 17:   */   
/* 18:   */   public S33PacketUpdateSign() {}
/* 19:   */   
/* 20:   */   public S33PacketUpdateSign(int p_i45231_1_, int p_i45231_2_, int p_i45231_3_, String[] p_i45231_4_)
/* 21:   */   {
/* 22:21 */     this.field_149352_a = p_i45231_1_;
/* 23:22 */     this.field_149350_b = p_i45231_2_;
/* 24:23 */     this.field_149351_c = p_i45231_3_;
/* 25:24 */     this.field_149349_d = new String[] { p_i45231_4_[0], p_i45231_4_[1], p_i45231_4_[2], p_i45231_4_[3] };
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:32 */     this.field_149352_a = p_148837_1_.readInt();
/* 32:33 */     this.field_149350_b = p_148837_1_.readShort();
/* 33:34 */     this.field_149351_c = p_148837_1_.readInt();
/* 34:35 */     this.field_149349_d = new String[4];
/* 35:37 */     for (int var2 = 0; var2 < 4; var2++) {
/* 36:39 */       this.field_149349_d[var2] = p_148837_1_.readStringFromBuffer(15);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:48 */     p_148840_1_.writeInt(this.field_149352_a);
/* 44:49 */     p_148840_1_.writeShort(this.field_149350_b);
/* 45:50 */     p_148840_1_.writeInt(this.field_149351_c);
/* 46:52 */     for (int var2 = 0; var2 < 4; var2++) {
/* 47:54 */       p_148840_1_.writeStringToBuffer(this.field_149349_d[var2]);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void processPacket(INetHandlerPlayClient p_149348_1_)
/* 52:   */   {
/* 53:60 */     p_149348_1_.handleUpdateSign(this);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int func_149346_c()
/* 57:   */   {
/* 58:65 */     return this.field_149352_a;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int func_149345_d()
/* 62:   */   {
/* 63:70 */     return this.field_149350_b;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int func_149344_e()
/* 67:   */   {
/* 68:75 */     return this.field_149351_c;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String[] func_149347_f()
/* 72:   */   {
/* 73:80 */     return this.field_149349_d;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void processPacket(INetHandler p_148833_1_)
/* 77:   */   {
/* 78:85 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S33PacketUpdateSign
 * JD-Core Version:    0.7.0.1
 */