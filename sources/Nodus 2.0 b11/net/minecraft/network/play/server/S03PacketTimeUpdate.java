/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S03PacketTimeUpdate
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private long field_149369_a;
/* 13:   */   private long field_149368_b;
/* 14:   */   private static final String __OBFID = "CL_00001337";
/* 15:   */   
/* 16:   */   public S03PacketTimeUpdate() {}
/* 17:   */   
/* 18:   */   public S03PacketTimeUpdate(long p_i45230_1_, long p_i45230_3_, boolean p_i45230_5_)
/* 19:   */   {
/* 20:19 */     this.field_149369_a = p_i45230_1_;
/* 21:20 */     this.field_149368_b = p_i45230_3_;
/* 22:22 */     if (!p_i45230_5_)
/* 23:   */     {
/* 24:24 */       this.field_149368_b = (-this.field_149368_b);
/* 25:26 */       if (this.field_149368_b == 0L) {
/* 26:28 */         this.field_149368_b = -1L;
/* 27:   */       }
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:38 */     this.field_149369_a = p_148837_1_.readLong();
/* 35:39 */     this.field_149368_b = p_148837_1_.readLong();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:47 */     p_148840_1_.writeLong(this.field_149369_a);
/* 42:48 */     p_148840_1_.writeLong(this.field_149368_b);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void processPacket(INetHandlerPlayClient p_149367_1_)
/* 46:   */   {
/* 47:53 */     p_149367_1_.handleTimeUpdate(this);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String serialize()
/* 51:   */   {
/* 52:61 */     return String.format("time=%d,dtime=%d", new Object[] { Long.valueOf(this.field_149369_a), Long.valueOf(this.field_149368_b) });
/* 53:   */   }
/* 54:   */   
/* 55:   */   public long func_149366_c()
/* 56:   */   {
/* 57:66 */     return this.field_149369_a;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public long func_149365_d()
/* 61:   */   {
/* 62:71 */     return this.field_149368_b;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void processPacket(INetHandler p_148833_1_)
/* 66:   */   {
/* 67:76 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S03PacketTimeUpdate
 * JD-Core Version:    0.7.0.1
 */