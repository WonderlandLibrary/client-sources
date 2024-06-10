/*  1:   */ package net.minecraft.network.login.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.login.INetHandlerLoginClient;
/*  8:   */ import net.minecraft.util.IChatComponent;
/*  9:   */ import net.minecraft.util.IChatComponent.Serializer;
/* 10:   */ 
/* 11:   */ public class S00PacketDisconnect
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private IChatComponent field_149605_a;
/* 15:   */   private static final String __OBFID = "CL_00001377";
/* 16:   */   
/* 17:   */   public S00PacketDisconnect() {}
/* 18:   */   
/* 19:   */   public S00PacketDisconnect(IChatComponent p_i45269_1_)
/* 20:   */   {
/* 21:19 */     this.field_149605_a = p_i45269_1_;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:27 */     this.field_149605_a = IChatComponent.Serializer.func_150699_a(p_148837_1_.readStringFromBuffer(32767));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:35 */     p_148840_1_.writeStringToBuffer(IChatComponent.Serializer.func_150696_a(this.field_149605_a));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void processPacket(INetHandlerLoginClient p_149604_1_)
/* 37:   */   {
/* 38:40 */     p_149604_1_.handleDisconnect(this);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean hasPriority()
/* 42:   */   {
/* 43:49 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public IChatComponent func_149603_c()
/* 47:   */   {
/* 48:54 */     return this.field_149605_a;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void processPacket(INetHandler p_148833_1_)
/* 52:   */   {
/* 53:59 */     processPacket((INetHandlerLoginClient)p_148833_1_);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.login.server.S00PacketDisconnect
 * JD-Core Version:    0.7.0.1
 */