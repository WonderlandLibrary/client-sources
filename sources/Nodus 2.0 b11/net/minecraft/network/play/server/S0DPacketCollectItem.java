/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S0DPacketCollectItem
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149357_a;
/* 13:   */   private int field_149356_b;
/* 14:   */   private static final String __OBFID = "CL_00001339";
/* 15:   */   
/* 16:   */   public S0DPacketCollectItem() {}
/* 17:   */   
/* 18:   */   public S0DPacketCollectItem(int p_i45232_1_, int p_i45232_2_)
/* 19:   */   {
/* 20:19 */     this.field_149357_a = p_i45232_1_;
/* 21:20 */     this.field_149356_b = p_i45232_2_;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:28 */     this.field_149357_a = p_148837_1_.readInt();
/* 28:29 */     this.field_149356_b = p_148837_1_.readInt();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:37 */     p_148840_1_.writeInt(this.field_149357_a);
/* 35:38 */     p_148840_1_.writeInt(this.field_149356_b);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void processPacket(INetHandlerPlayClient p_149355_1_)
/* 39:   */   {
/* 40:43 */     p_149355_1_.handleCollectItem(this);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int func_149354_c()
/* 44:   */   {
/* 45:48 */     return this.field_149357_a;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_149353_d()
/* 49:   */   {
/* 50:53 */     return this.field_149356_b;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void processPacket(INetHandler p_148833_1_)
/* 54:   */   {
/* 55:58 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0DPacketCollectItem
 * JD-Core Version:    0.7.0.1
 */