/*  1:   */ package net.minecraft.network.status.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.status.INetHandlerStatusServer;
/*  8:   */ 
/*  9:   */ public class C01PacketPing
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private long field_149290_a;
/* 13:   */   private static final String __OBFID = "CL_00001392";
/* 14:   */   
/* 15:   */   public C01PacketPing() {}
/* 16:   */   
/* 17:   */   public C01PacketPing(long p_i45276_1_)
/* 18:   */   {
/* 19:18 */     this.field_149290_a = p_i45276_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:26 */     this.field_149290_a = p_148837_1_.readLong();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:34 */     p_148840_1_.writeLong(this.field_149290_a);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void processPacket(INetHandlerStatusServer p_149288_1_)
/* 35:   */   {
/* 36:39 */     p_149288_1_.processPing(this);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean hasPriority()
/* 40:   */   {
/* 41:48 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public long func_149289_c()
/* 45:   */   {
/* 46:53 */     return this.field_149290_a;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void processPacket(INetHandler p_148833_1_)
/* 50:   */   {
/* 51:58 */     processPacket((INetHandlerStatusServer)p_148833_1_);
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.status.client.C01PacketPing
 * JD-Core Version:    0.7.0.1
 */