/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S25PacketBlockBreakAnim
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_148852_a;
/* 13:   */   private int field_148850_b;
/* 14:   */   private int field_148851_c;
/* 15:   */   private int field_148848_d;
/* 16:   */   private int field_148849_e;
/* 17:   */   private static final String __OBFID = "CL_00001284";
/* 18:   */   
/* 19:   */   public S25PacketBlockBreakAnim() {}
/* 20:   */   
/* 21:   */   public S25PacketBlockBreakAnim(int p_i45174_1_, int p_i45174_2_, int p_i45174_3_, int p_i45174_4_, int p_i45174_5_)
/* 22:   */   {
/* 23:22 */     this.field_148852_a = p_i45174_1_;
/* 24:23 */     this.field_148850_b = p_i45174_2_;
/* 25:24 */     this.field_148851_c = p_i45174_3_;
/* 26:25 */     this.field_148848_d = p_i45174_4_;
/* 27:26 */     this.field_148849_e = p_i45174_5_;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:34 */     this.field_148852_a = p_148837_1_.readVarIntFromBuffer();
/* 34:35 */     this.field_148850_b = p_148837_1_.readInt();
/* 35:36 */     this.field_148851_c = p_148837_1_.readInt();
/* 36:37 */     this.field_148848_d = p_148837_1_.readInt();
/* 37:38 */     this.field_148849_e = p_148837_1_.readUnsignedByte();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:46 */     p_148840_1_.writeVarIntToBuffer(this.field_148852_a);
/* 44:47 */     p_148840_1_.writeInt(this.field_148850_b);
/* 45:48 */     p_148840_1_.writeInt(this.field_148851_c);
/* 46:49 */     p_148840_1_.writeInt(this.field_148848_d);
/* 47:50 */     p_148840_1_.writeByte(this.field_148849_e);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void processPacket(INetHandlerPlayClient p_148847_1_)
/* 51:   */   {
/* 52:55 */     p_148847_1_.handleBlockBreakAnim(this);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int func_148845_c()
/* 56:   */   {
/* 57:60 */     return this.field_148852_a;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int func_148844_d()
/* 61:   */   {
/* 62:65 */     return this.field_148850_b;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int func_148843_e()
/* 66:   */   {
/* 67:70 */     return this.field_148851_c;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int func_148842_f()
/* 71:   */   {
/* 72:75 */     return this.field_148848_d;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int func_148846_g()
/* 76:   */   {
/* 77:80 */     return this.field_148849_e;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void processPacket(INetHandler p_148833_1_)
/* 81:   */   {
/* 82:85 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S25PacketBlockBreakAnim
 * JD-Core Version:    0.7.0.1
 */