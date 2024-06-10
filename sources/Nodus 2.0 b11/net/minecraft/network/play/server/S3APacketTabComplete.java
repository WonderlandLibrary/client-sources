/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import org.apache.commons.lang3.ArrayUtils;
/*  9:   */ 
/* 10:   */ public class S3APacketTabComplete
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private String[] field_149632_a;
/* 14:   */   private static final String __OBFID = "CL_00001288";
/* 15:   */   
/* 16:   */   public S3APacketTabComplete() {}
/* 17:   */   
/* 18:   */   public S3APacketTabComplete(String[] p_i45178_1_)
/* 19:   */   {
/* 20:19 */     this.field_149632_a = p_i45178_1_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:27 */     this.field_149632_a = new String[p_148837_1_.readVarIntFromBuffer()];
/* 27:29 */     for (int var2 = 0; var2 < this.field_149632_a.length; var2++) {
/* 28:31 */       this.field_149632_a[var2] = p_148837_1_.readStringFromBuffer(32767);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:40 */     p_148840_1_.writeVarIntToBuffer(this.field_149632_a.length);
/* 36:41 */     String[] var2 = this.field_149632_a;
/* 37:42 */     int var3 = var2.length;
/* 38:44 */     for (int var4 = 0; var4 < var3; var4++)
/* 39:   */     {
/* 40:46 */       String var5 = var2[var4];
/* 41:47 */       p_148840_1_.writeStringToBuffer(var5);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void processPacket(INetHandlerPlayClient p_149631_1_)
/* 46:   */   {
/* 47:53 */     p_149631_1_.handleTabComplete(this);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String[] func_149630_c()
/* 51:   */   {
/* 52:58 */     return this.field_149632_a;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String serialize()
/* 56:   */   {
/* 57:66 */     return String.format("candidates='%s'", new Object[] { ArrayUtils.toString(this.field_149632_a) });
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void processPacket(INetHandler p_148833_1_)
/* 61:   */   {
/* 62:71 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3APacketTabComplete
 * JD-Core Version:    0.7.0.1
 */