/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C09PacketHeldItemChange
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149615_a;
/* 13:   */   private static final String __OBFID = "CL_00001368";
/* 14:   */   
/* 15:   */   public C09PacketHeldItemChange() {}
/* 16:   */   
/* 17:   */   public C09PacketHeldItemChange(int p_i45262_1_)
/* 18:   */   {
/* 19:18 */     this.field_149615_a = p_i45262_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:26 */     this.field_149615_a = p_148837_1_.readShort();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:34 */     p_148840_1_.writeShort(this.field_149615_a);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void processPacket(INetHandlerPlayServer p_149613_1_)
/* 35:   */   {
/* 36:39 */     p_149613_1_.processHeldItemChange(this);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int func_149614_c()
/* 40:   */   {
/* 41:44 */     return this.field_149615_a;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processPacket(INetHandler p_148833_1_)
/* 45:   */   {
/* 46:49 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C09PacketHeldItemChange
 * JD-Core Version:    0.7.0.1
 */