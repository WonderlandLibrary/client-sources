/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C01PacketChatMessage
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private String field_149440_a;
/* 13:   */   private static final String __OBFID = "CL_00001347";
/* 14:   */   
/* 15:   */   public C01PacketChatMessage() {}
/* 16:   */   
/* 17:   */   public C01PacketChatMessage(String p_i45240_1_)
/* 18:   */   {
/* 19:18 */     if (p_i45240_1_.length() > 100) {
/* 20:20 */       p_i45240_1_ = p_i45240_1_.substring(0, 100);
/* 21:   */     }
/* 22:23 */     this.field_149440_a = p_i45240_1_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:31 */     this.field_149440_a = p_148837_1_.readStringFromBuffer(100);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:39 */     p_148840_1_.writeStringToBuffer(this.field_149440_a);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void processPacket(INetHandlerPlayServer p_149438_1_)
/* 38:   */   {
/* 39:44 */     p_149438_1_.processChatMessage(this);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String serialize()
/* 43:   */   {
/* 44:52 */     return String.format("message='%s'", new Object[] { this.field_149440_a });
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String func_149439_c()
/* 48:   */   {
/* 49:57 */     return this.field_149440_a;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void processPacket(INetHandler p_148833_1_)
/* 53:   */   {
/* 54:62 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C01PacketChatMessage
 * JD-Core Version:    0.7.0.1
 */