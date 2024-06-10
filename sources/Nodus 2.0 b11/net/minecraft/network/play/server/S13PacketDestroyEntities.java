/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S13PacketDestroyEntities
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int[] field_149100_a;
/* 13:   */   private static final String __OBFID = "CL_00001320";
/* 14:   */   
/* 15:   */   public S13PacketDestroyEntities() {}
/* 16:   */   
/* 17:   */   public S13PacketDestroyEntities(int... p_i45211_1_)
/* 18:   */   {
/* 19:18 */     this.field_149100_a = p_i45211_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:26 */     this.field_149100_a = new int[p_148837_1_.readByte()];
/* 26:28 */     for (int var2 = 0; var2 < this.field_149100_a.length; var2++) {
/* 27:30 */       this.field_149100_a[var2] = p_148837_1_.readInt();
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:39 */     p_148840_1_.writeByte(this.field_149100_a.length);
/* 35:41 */     for (int var2 = 0; var2 < this.field_149100_a.length; var2++) {
/* 36:43 */       p_148840_1_.writeInt(this.field_149100_a[var2]);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processPacket(INetHandlerPlayClient p_149099_1_)
/* 41:   */   {
/* 42:49 */     p_149099_1_.handleDestroyEntities(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String serialize()
/* 46:   */   {
/* 47:57 */     StringBuilder var1 = new StringBuilder();
/* 48:59 */     for (int var2 = 0; var2 < this.field_149100_a.length; var2++)
/* 49:   */     {
/* 50:61 */       if (var2 > 0) {
/* 51:63 */         var1.append(", ");
/* 52:   */       }
/* 53:66 */       var1.append(this.field_149100_a[var2]);
/* 54:   */     }
/* 55:69 */     return String.format("entities=%d[%s]", new Object[] { Integer.valueOf(this.field_149100_a.length), var1 });
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int[] func_149098_c()
/* 59:   */   {
/* 60:74 */     return this.field_149100_a;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:79 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S13PacketDestroyEntities
 * JD-Core Version:    0.7.0.1
 */