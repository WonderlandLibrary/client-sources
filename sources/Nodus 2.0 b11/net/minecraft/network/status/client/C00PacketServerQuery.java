/*  1:   */ package net.minecraft.network.status.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.status.INetHandlerStatusServer;
/*  8:   */ 
/*  9:   */ public class C00PacketServerQuery
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00001393";
/* 13:   */   
/* 14:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 15:   */     throws IOException
/* 16:   */   {}
/* 17:   */   
/* 18:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 19:   */     throws IOException
/* 20:   */   {}
/* 21:   */   
/* 22:   */   public void processPacket(INetHandlerStatusServer p_149287_1_)
/* 23:   */   {
/* 24:25 */     p_149287_1_.processServerQuery(this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasPriority()
/* 28:   */   {
/* 29:34 */     return true;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void processPacket(INetHandler p_148833_1_)
/* 33:   */   {
/* 34:39 */     processPacket((INetHandlerStatusServer)p_148833_1_);
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.status.client.C00PacketServerQuery
 * JD-Core Version:    0.7.0.1
 */