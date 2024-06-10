/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C00PacketKeepAlive
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149461_a;
/* 13:   */   private static final String __OBFID = "CL_00001359";
/* 14:   */   
/* 15:   */   public C00PacketKeepAlive() {}
/* 16:   */   
/* 17:   */   public C00PacketKeepAlive(int p_i45252_1_)
/* 18:   */   {
/* 19:18 */     this.field_149461_a = p_i45252_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processPacket(INetHandlerPlayServer p_149459_1_)
/* 23:   */   {
/* 24:23 */     p_149459_1_.processKeepAlive(this);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:31 */     this.field_149461_a = p_148837_1_.readInt();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     p_148840_1_.writeInt(this.field_149461_a);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean hasPriority()
/* 40:   */   {
/* 41:48 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int func_149460_c()
/* 45:   */   {
/* 46:53 */     return this.field_149461_a;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void processPacket(INetHandler p_148833_1_)
/* 50:   */   {
/* 51:58 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C00PacketKeepAlive
 * JD-Core Version:    0.7.0.1
 */