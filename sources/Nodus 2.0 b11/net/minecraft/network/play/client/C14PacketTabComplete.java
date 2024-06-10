/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ import org.apache.commons.lang3.StringUtils;
/*  9:   */ 
/* 10:   */ public class C14PacketTabComplete
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private String field_149420_a;
/* 14:   */   private static final String __OBFID = "CL_00001346";
/* 15:   */   
/* 16:   */   public C14PacketTabComplete() {}
/* 17:   */   
/* 18:   */   public C14PacketTabComplete(String p_i45239_1_)
/* 19:   */   {
/* 20:19 */     this.field_149420_a = p_i45239_1_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:27 */     this.field_149420_a = p_148837_1_.readStringFromBuffer(32767);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:35 */     p_148840_1_.writeStringToBuffer(StringUtils.substring(this.field_149420_a, 0, 32767));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void processPacket(INetHandlerPlayServer p_149418_1_)
/* 36:   */   {
/* 37:40 */     p_149418_1_.processTabComplete(this);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String func_149419_c()
/* 41:   */   {
/* 42:45 */     return this.field_149420_a;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String serialize()
/* 46:   */   {
/* 47:53 */     return String.format("message='%s'", new Object[] { this.field_149420_a });
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void processPacket(INetHandler p_148833_1_)
/* 51:   */   {
/* 52:58 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C14PacketTabComplete
 * JD-Core Version:    0.7.0.1
 */