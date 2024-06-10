/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C07PacketPlayerDigging
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149511_a;
/* 13:   */   private int field_149509_b;
/* 14:   */   private int field_149510_c;
/* 15:   */   private int field_149507_d;
/* 16:   */   private int field_149508_e;
/* 17:   */   private static final String __OBFID = "CL_00001365";
/* 18:   */   
/* 19:   */   public C07PacketPlayerDigging() {}
/* 20:   */   
/* 21:   */   public C07PacketPlayerDigging(int p_i45258_1_, int p_i45258_2_, int p_i45258_3_, int p_i45258_4_, int p_i45258_5_)
/* 22:   */   {
/* 23:22 */     this.field_149508_e = p_i45258_1_;
/* 24:23 */     this.field_149511_a = p_i45258_2_;
/* 25:24 */     this.field_149509_b = p_i45258_3_;
/* 26:25 */     this.field_149510_c = p_i45258_4_;
/* 27:26 */     this.field_149507_d = p_i45258_5_;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:34 */     this.field_149508_e = p_148837_1_.readUnsignedByte();
/* 34:35 */     this.field_149511_a = p_148837_1_.readInt();
/* 35:36 */     this.field_149509_b = p_148837_1_.readUnsignedByte();
/* 36:37 */     this.field_149510_c = p_148837_1_.readInt();
/* 37:38 */     this.field_149507_d = p_148837_1_.readUnsignedByte();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:46 */     p_148840_1_.writeByte(this.field_149508_e);
/* 44:47 */     p_148840_1_.writeInt(this.field_149511_a);
/* 45:48 */     p_148840_1_.writeByte(this.field_149509_b);
/* 46:49 */     p_148840_1_.writeInt(this.field_149510_c);
/* 47:50 */     p_148840_1_.writeByte(this.field_149507_d);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void processPacket(INetHandlerPlayServer p_149504_1_)
/* 51:   */   {
/* 52:55 */     p_149504_1_.processPlayerDigging(this);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int func_149505_c()
/* 56:   */   {
/* 57:60 */     return this.field_149511_a;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int func_149503_d()
/* 61:   */   {
/* 62:65 */     return this.field_149509_b;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int func_149502_e()
/* 66:   */   {
/* 67:70 */     return this.field_149510_c;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int func_149501_f()
/* 71:   */   {
/* 72:75 */     return this.field_149507_d;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int func_149506_g()
/* 76:   */   {
/* 77:80 */     return this.field_149508_e;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void processPacket(INetHandler p_148833_1_)
/* 81:   */   {
/* 82:85 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C07PacketPlayerDigging
 * JD-Core Version:    0.7.0.1
 */